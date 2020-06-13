package Commands;

import Utils.Context;

import java.util.Iterator;

/**
 * Команда вывода последних 12 команд (без их аргументов)
 */
public class CommandHistory extends Command {
	public CommandHistory() {
		super();
	}
	
	@Override
	public void execute() {
		Iterator<Command> commandHistory = this.context.commandsHistoryManager.getCommandsHistory();
		stringBuilderResponse.append(String.format("Последние %d команд (без их аргументов):\n", Context.HISTORY_SIZE));
		commandHistory.forEachRemaining(command->stringBuilderResponse.append(command.getName()).append("\n"));
	}
	
	@Override
	public String getName() {
		return "history";
	}
	
	@Override
	public String getDescription() {
		return "вывести последние 12 команд (без их аргументов)";
	}
}
