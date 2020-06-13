package Commands;

import java.util.Iterator;

/**
 * Команда вывода справки по доступным коммандам
 */
public class CommandHelp extends Command {
	public CommandHelp() {
		super();
	}
	
	
	@Override
	public void execute() {
		Iterator<Command> commands = this.context.commandsHolder.getCommands();
		commands.forEachRemaining(command->stringBuilderResponse.append(command.getFullInformation()).append("\n"));
	}
	
	@Override
	public String getName() {
		return "help";
	}
	
	@Override
	public String getDescription() {
		return "вывести справку по доступным командам";
	}
}
