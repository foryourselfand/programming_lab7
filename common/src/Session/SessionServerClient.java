package Session;

import Utils.CommandsHistoryManager;

import java.io.Serializable;

public class SessionServerClient implements Serializable {
	private String message;
	private CommandsHistoryManager commandsHistoryManager;
	
	public SessionServerClient(String message, CommandsHistoryManager commandsHistoryManager) {
		this.message = message;
		this.commandsHistoryManager = commandsHistoryManager;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public CommandsHistoryManager getCommandsHistoryManager() {
		return commandsHistoryManager;
	}
	
	public void setCommandsHistoryManager(CommandsHistoryManager commandsHistoryManager) {
		this.commandsHistoryManager = commandsHistoryManager;
	}
}
