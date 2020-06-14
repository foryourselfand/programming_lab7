package Session;

import Commands.Command;
import Utils.CommandsHistoryManager;

import java.io.Serializable;

public class SessionClientServer implements Serializable {
	private Command command;
	private User user;
	private CommandsHistoryManager commandsHistoryManager;
	
	public SessionClientServer(Command command, User user, CommandsHistoryManager commandsHistoryManager) {
		this.command = command;
		this.user = user;
		this.commandsHistoryManager = commandsHistoryManager;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public CommandsHistoryManager getCommandsHistoryManager() {
		return commandsHistoryManager;
	}
	
	public void setCommandsHistoryManager(CommandsHistoryManager commandsHistoryManager) {
		this.commandsHistoryManager = commandsHistoryManager;
	}
}
