import Commands.Command;
import Commands.CommandAuthorized;
import Commands.CommandExit;
import Commands.CommandSave;
import Errors.ConnectionError;
import Errors.InputErrors.InputError;
import Session.SessionClientServer;
import Session.SessionServerClient;
import Session.User;
import Utils.CommandsHistoryManager;
import Utils.Context;
import Utils.SerializationManager;
import Utils.TempFileManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	public static final Logger logger = Logger.getLogger(Server.class.getName());
	
	private static final int DEFAULT_BUFFER_SIZE = 65536;
	private static final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	
	private static final SerializationManager<SessionClientServer> sessionClientServerSerializationManager = new SerializationManager<>();
	private static final SerializationManager<SessionServerClient> sessionServerClientSerializationManager = new SerializationManager<>();
	
	private static SocketAddress address;
	private static DatagramChannel channel;
	
	public static void connect(int port) {
		logger.info("Start server initialization");
		
		address = new InetSocketAddress(port);
		try {
			channel = DatagramChannel.open();
			channel.configureBlocking(false);
			channel.bind(address);
		} catch (IOException e) {
			throw new ConnectionError();
		}
		
		logger.info("Connection is established, listen port: " + port);
		Runtime.getRuntime().addShutdownHook(new Thread(()->logger.info("Сервер закончил работу")));
	}
	
	public static void readRequests(Context context) {
		try {
			Callable<SocketAddress> callableRequestReader = new CallableRequestReader();
			ExecutorService executorServiceRequestReader = Executors.newFixedThreadPool(10);
			while (true) {
				Future<SocketAddress> addressFuture = executorServiceRequestReader.submit(callableRequestReader);
				address = addressFuture.get();
				byte[] copyData = new byte[buffer.length];
				System.arraycopy(buffer, 0, copyData, 0, buffer.length);
				
				Callable<String> callableRequestProceed = new CallableRequestProceed(context, copyData);
				ExecutorService executorServiceRequestProceed = Executors.newCachedThreadPool();
				Future<String> responseFuture = executorServiceRequestProceed.submit(callableRequestProceed);
				String response = responseFuture.get();
				
				Callable<Void> callableRequestSender = new CallableRequestSender(response, context.commandsHistoryManager);
				ExecutorService executorServiceRequestSender = Executors.newCachedThreadPool();
				executorServiceRequestSender.submit(callableRequestSender);
			}
		} catch (ClassCastException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public static String processCommand(Context context, Command command, User user) {
		if (command instanceof CommandExit) {
			try {
				Command commandSave = new CommandSave();
				commandSave.validateArguments(new String[]{TempFileManager.getTempFilePath()});
				commandSave.showDescriptionAndExecute(context);
			} catch (InputError ignored) {
			}
			return command.getDescription();
		}
		
		if (command instanceof CommandAuthorized) {
			System.out.println(user);
		}
		
		try {
			command.showDescriptionAndExecute(context);
			context.commandsHistoryManager.addCommandToHistory(command);
			return command.getResponse();
		} catch (InputError inputError) {
			return inputError.getMessage() + "\n";
		}
	}
	
	static class CallableRequestReader implements Callable<SocketAddress> {
		@Override
		public SocketAddress call() throws IOException {
			ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
			SocketAddress socketAddress;
			do {
				socketAddress = channel.receive(byteBuffer);
			} while (socketAddress == null);
			return socketAddress;
		}
	}
	
	static class CallableRequestProceed implements Callable<String> {
		private final Context context;
		private final byte[] copyData;
		
		public CallableRequestProceed(Context context, byte[] copyData) {
			this.context = context;
			this.copyData = copyData;
		}
		
		@Override
		public String call() throws IOException, ClassNotFoundException {
			SessionClientServer sessionClientServer = sessionClientServerSerializationManager.readObject(copyData);
			Command commandReceived = sessionClientServer.getCommand();
			User userReceived = sessionClientServer.getUser();
			CommandsHistoryManager commandsHistoryManagerReceived = sessionClientServer.getCommandsHistoryManager();
			context.setCommandsHistoryManager(commandsHistoryManagerReceived);
			logger.log(Level.INFO, "Server receive command" + commandReceived);
			String response = processCommand(context, commandReceived, userReceived);
			logger.log(Level.INFO, "Command " + commandReceived + " executed, sending response to client");
			return response;
		}
	}
	
	static class CallableRequestSender implements Callable<Void> {
		private final String response;
		private final CommandsHistoryManager commandsHistoryManager;
		
		public CallableRequestSender(String response, CommandsHistoryManager commandsHistoryManager) {
			this.response = response;
			this.commandsHistoryManager = commandsHistoryManager;
		}
		
		@Override
		public Void call() throws IOException {
			SessionServerClient sessionServerClient = new SessionServerClient(response, commandsHistoryManager);
			byte[] sessionBytes = sessionServerClientSerializationManager.writeObject(sessionServerClient);
			ByteBuffer byteBuffer = ByteBuffer.wrap(sessionBytes);
			channel.send(byteBuffer, address);
			return null;
		}
	}
}
