package Commands;

import Session.User;

import java.util.Iterator;

/**
 * Команда вывода справки по доступным коммандам
 */
public class CommandHelp extends Command {
	public CommandHelp() {
		super();
	}
	
	@Override
	public User getUser() {
		return new User("login1", "password1", "salt1");
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
