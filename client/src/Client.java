import Commands.CommandExit;
import Errors.ConnectionError;
import Errors.TimeoutError;
import Session.SessionClientServer;
import Session.SessionServerClient;
import Utils.CommandsHistoryManager;
import Utils.SerializationManager;

import java.io.IOException;
import java.net.*;

public class Client {
	public static SessionClientServer sessionClientServer = new SessionClientServer();
	private static final int DEFAULT_BUFFER_SIZE = 65536;
	private static final int TIMEOUT = 5000;
	private static final SerializationManager<SessionClientServer> sessionClientServerSerializationManager = new SerializationManager<>();
	private static final SerializationManager<SessionServerClient> sessionServerClientSerializationManager = new SerializationManager<>();
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
	
	public static void sendSessionAndReceiveAnswer() {
		try {
			byte[] sessionInBytes = sessionClientServerSerializationManager.writeObject(sessionClientServer);
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
			String message = sessionServerClient.getMessage();
			CommandsHistoryManager commandsHistoryManager = sessionServerClient.getCommandsHistoryManager();
			System.out.println("Получен ответ от сервера: ");
			System.out.print(message);
			
			sessionClientServer.setCommandsHistoryManager(commandsHistoryManager);
			
			if (sessionClientServer.getCommand() instanceof CommandExit) {
				sessionClientServer.getCommand().execute();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
