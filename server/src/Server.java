import Commands.Command;
import Commands.CommandAuthorized;
import Commands.CommandExit;
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
	public static final int nThreadFixed = 10;
	
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
	
	public static void run(Context context) {
		try {
			readRequest(context);
		} catch (ClassCastException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private static void readRequest(Context context) throws InterruptedException, ExecutionException {
		Callable<SocketAddress> callableRequestReader = new CallableRequestReader();
		ExecutorService executorServiceRequestReader = Executors.newFixedThreadPool(nThreadFixed);
		while (true) {
			Future<SocketAddress> addressFuture = executorServiceRequestReader.submit(callableRequestReader);
			address = addressFuture.get();
			byte[] copyData = new byte[buffer.length];
			System.arraycopy(buffer, 0, copyData, 0, buffer.length);
			
			proceedRequest(context, copyData);
		}
	}
	
	private static void proceedRequest(Context context, byte[] copyData) throws InterruptedException, ExecutionException {
		Callable<SessionServerClient> callableRequestProceed = new CallableRequestProceed(context, copyData);
		ExecutorService executorServiceRequestProceed = Executors.newCachedThreadPool();
		
		Future<SessionServerClient> responseFuture = executorServiceRequestProceed.submit(callableRequestProceed);
		SessionServerClient session = responseFuture.get();
		
		sendResponse(session);
	}
	
	private static void sendResponse(SessionServerClient session) {
		Callable<Void> callableRequestSender = new CallableRequestSender(session);
		ExecutorService executorServiceRequestSender = Executors.newCachedThreadPool();
		
		executorServiceRequestSender.submit(callableRequestSender);
	}
	
	public static String processCommand(Context context, Command command, User user, CommandsHistoryManager commandsHistoryManager) {
		if (command instanceof CommandExit) {
//			try {
//				Command commandSave = new CommandSave();
//				commandSave.validateArguments(new String[]{TempFileManager.getTempFilePath()});
//				commandSave.showDescriptionAndExecute(context);
//			} catch (InputError ignored) {
//			}
			return command.getDescription();
		}
		
		if (command instanceof CommandAuthorized) {
			System.out.println(user);
		}
		
		try {
			command.setCommandsHistoryManager(commandsHistoryManager);
			command.showDescriptionAndExecute(context);
			commandsHistoryManager.addCommandToHistory(command);
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
	
	static class CallableRequestProceed implements Callable<SessionServerClient> {
		private final Context context;
		private final byte[] copyData;
		
		public CallableRequestProceed(Context context, byte[] copyData) {
			this.context = context;
			this.copyData = copyData;
		}
		
		@Override
		public SessionServerClient call() throws IOException, ClassNotFoundException {
			SessionClientServer sessionClientServer = sessionClientServerSerializationManager.readObject(copyData);
			Command commandReceived = sessionClientServer.getCommand();
			User userReceived = sessionClientServer.getUser();
			CommandsHistoryManager commandsHistoryManagerReceived = sessionClientServer.getCommandsHistoryManager();
			
			logger.log(Level.INFO, "Server receive command" + commandReceived);
			String response = processCommand(context, commandReceived, userReceived, commandsHistoryManagerReceived);
			logger.log(Level.INFO, "Command " + commandReceived + " executed, sending response to client");
			return new SessionServerClient(commandsHistoryManagerReceived, response);
		}
	}
	
	static class CallableRequestSender implements Callable<Void> {
		private final SessionServerClient session;
		
		public CallableRequestSender(SessionServerClient session) {
			this.session = session;
		}
		
		@Override
		public Void call() throws IOException {
			byte[] sessionBytes = sessionServerClientSerializationManager.writeObject(session);
			ByteBuffer byteBuffer = ByteBuffer.wrap(sessionBytes);
			channel.send(byteBuffer, address);
			return null;
		}
	}
}
