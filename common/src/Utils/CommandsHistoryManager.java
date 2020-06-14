package Utils;

import Commands.Command;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class CommandsHistoryManager implements Serializable {
	public static final int HISTORY_SIZE = 12;
	private final Deque<String> commandsHistory;
	
	public CommandsHistoryManager() {
		commandsHistory = new ArrayDeque<>();
	}
	
	public void addCommandToHistory(Command command) {
		if (commandsHistory.size() == HISTORY_SIZE)
			commandsHistory.pollFirst();
		commandsHistory.addLast(command.getName());
	}
	
	public Iterator<String> getCommandsHistory() {
		return commandsHistory.iterator();
	}
}
