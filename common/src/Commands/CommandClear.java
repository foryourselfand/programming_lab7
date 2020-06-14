package Commands;

import Session.SessionServerClient;
import Utils.Context;

public class CommandClear extends CommandWithNotEmptyCollection implements CommandAuthorized {
	public CommandClear() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		context.collectionManager.getCollection().clear();
		session.append("Коллекция очищена");
	}
	
	@Override
	public String getName() {
		return "clear";
	}
	
	@Override
	public String getDescription() {
		return "очистить коллекцию";
	}
}
