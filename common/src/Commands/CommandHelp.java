package Commands;

import Session.SessionServerClient;
import Utils.Context;

import java.util.Iterator;

/**
 * Команда вывода справки по доступным командам
 */
public class CommandHelp extends Command {
	public CommandHelp() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		Iterator<Command> commands = context.commandsHolder.getCommands();
		commands.forEachRemaining(command->session.append(command.getFullInformation()).append("\n"));
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
