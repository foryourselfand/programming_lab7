package Session;

import Commands.Command;
import Utils.CommandsHistoryManager;

import java.io.Serializable;

public class SessionClientServer implements Serializable {
	private Command command;
	private CommandsHistoryManager commandsHistoryManager;
	
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
