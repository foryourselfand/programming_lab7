import Commands.Command;
import Commands.CommandExit;
import Commands.CommandSave;
import Errors.ConnectionError;
import Errors.InputErrors.InputError;
import Session.SessionClientServer;
import Session.SessionServerClient;
import Utils.CommandsHistoryManager;
import Utils.Context;
import Utils.SerializationManager;
import Utils.TempFileManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
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
	}
	
	public static void run(Context context) {
		try {
			while (true) {
				ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
				do {
					address = channel.receive(byteBuffer);
				} while (address == null);
				SessionClientServer sessionClientServer = sessionClientServerSerializationManager.readObject(buffer);
				
				Command commandReceived = sessionClientServer.getCommand();
				CommandsHistoryManager commandsHistoryManager = sessionClientServer.getCommandsHistoryManager();
				context.setCommandsHistoryManager(commandsHistoryManager);
				logger.log(Level.INFO, "Server receive command" + commandReceived);
				
				String response = processCommand(context, commandReceived);
				
				logger.log(Level.INFO, "Command " + commandReceived + " executed, sending response to client");
				
				SessionServerClient sessionServerClient = new SessionServerClient(response, commandsHistoryManager);
				
				byte[] sessionBytes = sessionServerClientSerializationManager.writeObject(sessionServerClient);
				byteBuffer = ByteBuffer.wrap(sessionBytes);
				channel.send(byteBuffer, address);
			}
		} catch (ClassNotFoundException | IOException | ClassCastException e) {
			e.printStackTrace();
		}
	}
	
	public static String processCommand(Context context, Command command) {
		if (command instanceof CommandExit) {
			try {
				Command commandSave = new CommandSave();
				commandSave.validateArguments(new String[]{TempFileManager.getTempFilePath()});
				commandSave.showDescriptionAndExecute(context);
			} catch (InputError ignored) {
			}
			return command.getDescription();
		}
		
		try {
			command.showDescriptionAndExecute(context);
			context.commandsHistoryManager.addCommandToHistory(command);
			return command.getResponse();
		} catch (InputError inputError) {
			return inputError.getMessage() + "\n";
		}
	}
}
