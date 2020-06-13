package Commands;

public class CommandExit extends Command {
	public CommandExit() {
		super();
	}
	
	@Override
	public void execute() {
		exit();
	}
	
	public void exit() {
		System.exit(42);
	}
	
	@Override
	public String getName() {
		return "exit";
	}
	
	@Override
	public String getDescription() {
		return "завершить программу (с сохранением в временный файл)";
	}
}
