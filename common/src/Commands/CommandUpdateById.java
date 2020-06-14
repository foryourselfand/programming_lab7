package Commands;

import Expectations.Argument;
import Expectations.ExpectedIdExist;
import Expectations.ExpectedType.ExpectedLong;
import Input.Flat;
import Session.SessionServerClient;
import Utils.Context;
import Utils.FlatCreator;

import java.util.List;

public class CommandUpdateById extends CommandWithNotEmptyCollection implements CommandAuthorized {
	private Flat flatNew;
	
	public CommandUpdateById() {
		super();
	}
	
	@Override
	protected void addArgumentValidators(List<Argument> arguments) {
		arguments.add(new Argument("id",
				new ExpectedLong(),
				new ExpectedIdExist()));
	}
	
	@Override
	public void preExecute() {
		flatNew = createFlatNew();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		long idToRemove = Long.parseLong(commandArguments[0]);
		removeFlatOld(context, session, idToRemove);
		addFlatNew(context, session, flatNew);
	}
	
	private Flat createFlatNew() {
		return FlatCreator.getCreatedFlatFromTerminal(Context.lineReader);
	}
	
	private void removeFlatOld(Context context, SessionServerClient session, long idToRemove) {
		context.collectionManager.removeFlatFromCollectionById(idToRemove);
		session.append("Из коллекции удален элемент с id ").append(idToRemove).append("\n");
	}
	
	private void addFlatNew(Context context, SessionServerClient session, Flat flatNew) {
		context.collectionManager.addFlatToCollection(flatNew);
		session.append("В коллекцию добавлен элемент ").append(flatNew.toString()).append("\n");
	}
	
	@Override
	public String getName() {
		return "update_by_id";
	}
	
	@Override
	public String getDescription() {
		return "обновить значение элемента коллекции, id которого равен заданному";
	}
}
