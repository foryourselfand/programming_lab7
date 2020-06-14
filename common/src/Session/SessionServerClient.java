package Session;

import Utils.CommandsHistoryManager;

import java.io.Serializable;

public class SessionServerClient implements Serializable {
	private CommandsHistoryManager commandsHistoryManager;
	private String message;
	
	public SessionServerClient(CommandsHistoryManager commandsHistoryManager, String message) {
		this.commandsHistoryManager = commandsHistoryManager;
		this.message = message;
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
