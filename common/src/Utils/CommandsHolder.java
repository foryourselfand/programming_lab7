package Utils;

import Commands.*;
import Errors.NoSuchCommandError;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandsHolder {
	private final Map<String, Command> commands;
	
	public CommandsHolder() {
		commands = new LinkedHashMap<>();
		setUpCommands();
	}
	
	public CommandsHolder addCommand(Command command) {
		commands.put(command.getName(), command);
		return this;
	}
	
	public Command getCommandByName(String name) {
		if (! this.commands.containsKey(name))
			throw new NoSuchCommandError(name);
		
		return this.commands.get(name);
	}
	
	public void setUpCommands() {
		this
				.addCommand(new CommandHelp())
				.addCommand(new CommandInfo())
				.addCommand(new CommandShow())
				.addCommand(new CommandAdd())
				.addCommand(new CommandUpdateById())
				.addCommand(new CommandRemoveById())
				.addCommand(new CommandClear())
				.addCommand(new CommandLoad())
				.addCommand(new CommandExecuteScript())
				.addCommand(new CommandExit())
				.addCommand(new CommandAddIfMax())
				.addCommand(new CommandRemoveGreater())
				.addCommand(new CommandHistory())
				.addCommand(new CommandAverageOfHeight())
				.addCommand(new CommandGroupCountingByArea())
				.addCommand(new CommandPrintUniqueHouse());
	}
	
	public Iterator<Command> getCommands() {
		return commands.values().iterator();
	}
}
