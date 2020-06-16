package Commands;

import Session.SessionServerClient;
import Utils.Context;

public class CommandExit extends Command {
	public CommandExit() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
	}
	
	@Override
	public void postExecute() {
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
		return "завершить программу";
	}
}
