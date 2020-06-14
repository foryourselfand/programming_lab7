package Session;

import Utils.CommandsHistoryManager;

import java.io.Serializable;
import java.time.LocalDate;

public class SessionServerClient implements Serializable {
	private CommandsHistoryManager commandsHistoryManager;
	private User user;
	private StringBuilder messageBuilder;
	private String message;
	
	public SessionServerClient(CommandsHistoryManager commandsHistoryManager, User user) {
		this.commandsHistoryManager = commandsHistoryManager;
		this.user = user;
		this.messageBuilder = new StringBuilder();
	}
	
	public void createMessage() {
		message = messageBuilder.toString();
		messageBuilder = null;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public StringBuilder getMessageBuilder() {
		return messageBuilder;
	}
	
	public void setMessageBuilder(StringBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public SessionServerClient append(String message) {
		messageBuilder.append(message);
		return this;
	}
	
	public SessionServerClient append(int message) {
		return append(String.valueOf(message));
	}
	
	public SessionServerClient append(long message) {
		return append(String.valueOf(message));
	}
	
	public SessionServerClient append(LocalDate message) {
		return append(message.toString());
	}
	
	public CommandsHistoryManager getCommandsHistoryManager() {
		return commandsHistoryManager;
	}
	
	public void setCommandsHistoryManager(CommandsHistoryManager commandsHistoryManager) {
		this.commandsHistoryManager = commandsHistoryManager;
	}
	
	@Override
	public String toString() {
		return "SessionServerClient{" +
				"commandsHistoryManager=" + commandsHistoryManager +
				", user=" + user +
				", messageBuilder=" + messageBuilder +
				'}';
	}
}
