package Commands;

import Session.SessionServerClient;
import Utils.Context;

public class CommandClear extends CommandWithNotEmptyCollection implements CommandAuthorized {
	public CommandClear() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		context.dataBaseManager.removeFlatsByUser(session.getUser());
		context.collectionManager.getCollection().forEach(flat -> {
			if (flat.getUserName().equals(session.getUser().getUsername()))
				context.collectionManager.removeFlatFromCollectionById(flat.getId());
		});
		session.append("Из коллекции удалены элементы принадлежащие пользователю");
	}
	
	@Override
	public String getName() {
		return "clear";
	}
	
	@Override
	public String getDescription() {
		return "удалить из коллекции элементы принадлежащие пользователю";
	}
}
