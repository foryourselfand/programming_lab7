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
		
		context.collectionManager.getCollection().forEach(flat->{
			if (flat.getId() == idToRemove && flat.getUserName().equals(session.getUser().getUsername())) {
				flatNew.setId(flat.getId());
				flatNew.setUserName(flat.getUserName());
				
				context.dataBaseManager.updateFlatById(idToRemove, flatNew);
				
				context.collectionManager.removeFlatFromCollectionById(idToRemove);
				context.collectionManager.addFlatToCollection(flatNew);
				
				session.append("Обновлен элемент с id ").append(idToRemove).append("\n");
			}
		});
	}
	
	private Flat createFlatNew() {
		return FlatCreator.getCreatedFlatFromTerminal(Context.lineReader);
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
