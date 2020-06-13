package Utils;

import Commands.Command;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class CommandsHistoryManager {
	private final Deque<Command> commandsHistory;
	
	public CommandsHistoryManager() {
		commandsHistory = new ArrayDeque<>();
	}
	
	public void addCommandToHistory(Command command) {
		if (commandsHistory.size() == Context.HISTORY_SIZE)
			commandsHistory.pollFirst();
		commandsHistory.addLast(command);
	}
	
	public Iterator<Command> getCommandsHistory() {
		return commandsHistory.iterator();
	}
}
