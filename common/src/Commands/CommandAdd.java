package Commands;

import Errors.ElementNotAddedError;
import Input.Flat;
import Session.SessionServerClient;
import Utils.Context;
import Utils.FlatCreator;

public class CommandAdd extends Command implements CommandAuthorized {
	private Flat flatNew;
	
	public CommandAdd() {
		super();
	}
	
	@Override
	public void preExecute() {
		flatNew = FlatCreator.getCreatedFlatFromTerminal(Context.lineReader);
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		if (! context.dataBaseManager.addFlat(flatNew, session))
			throw new ElementNotAddedError();
		context.collectionManager.addFlatToCollection(flatNew);
		session.append("В коллекцию добавлен элемент ").append(flatNew.toString()).append("\n");
	}
	
	@Override
	public String getName() {
		return "add";
	}
	
	@Override
	public String getDescription() {
		return "добавить новый элемент в коллекцию";
	}
}
