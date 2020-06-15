import Commands.Command;
import Commands.CommandExit;
import Errors.ConnectionError;
import Errors.TimeoutError;
import Session.SessionClientServer;
import Session.SessionServerClient;
import Session.User;
import Utils.CommandsHistoryManager;
import Utils.SerializationManager;

import java.io.IOException;
import java.net.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
	private static final int DEFAULT_BUFFER_SIZE = 65536;
	private static final int TIMEOUT = 5000;
	private static final SerializationManager<SessionClientServer> sessionClientServerSerializationManager = new SerializationManager<>();
	private static final SerializationManager<SessionServerClient> sessionServerClientSerializationManager = new SerializationManager<>();
	
	public static User user = new User();
	public static CommandsHistoryManager commandsHistoryManager = new CommandsHistoryManager();
	
	private static SocketAddress socketAddress;
	private static DatagramSocket datagramSocket;
	
	public static void connect(String host, int port) {
		try {
			InetAddress inetAddress = InetAddress.getByName(host);
			System.out.println("Подключение к " + inetAddress);
			socketAddress = new InetSocketAddress(inetAddress, port);
			datagramSocket = new DatagramSocket();
			datagramSocket.connect(socketAddress);
		} catch (UnknownHostException | SocketException e) {
			throw new ConnectionError();
		}
		
	}
	
	public static void sendSessionAndReceiveAnswer(Command command) {
		try {
			byte[] sessionInBytes = sessionClientServerSerializationManager.writeObject(new SessionClientServer(command, user, commandsHistoryManager));
			DatagramPacket datagramPacket = new DatagramPacket(sessionInBytes, sessionInBytes.length, socketAddress);
			datagramSocket.send(datagramPacket);
			System.out.println("Запрос отправлен на сервер...");
			byte[] answerInBytes = new byte[DEFAULT_BUFFER_SIZE];
			datagramPacket = new DatagramPacket(answerInBytes, answerInBytes.length);
			datagramSocket.setSoTimeout(TIMEOUT);
			try {
				datagramSocket.receive(datagramPacket);
			} catch (SocketTimeoutException | PortUnreachableException exception) {
				throw new TimeoutError();
			}
			
			SessionServerClient sessionServerClient = sessionServerClientSerializationManager.readObject(answerInBytes);
			
			String messageReceived = sessionServerClient.getMessage();
			CommandsHistoryManager commandsHistoryManagerReceived = sessionServerClient.getCommandsHistoryManager();
			System.out.format("Получен ответ от сервера: %s\n", messageReceived);
			
			commandsHistoryManager = commandsHistoryManagerReceived;
			
			if (command instanceof CommandExit)
				System.exit(42);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void setUserIfNotNull(User userToSet) {
		if (userToSet != null)
			user = userToSet;
	}
}
