package Commands;

import Errors.NotEnoughRightsToDeleteError;
import Expectations.Argument;
import Expectations.ExpectedIdExist;
import Expectations.ExpectedType.ExpectedLong;
import Session.SessionServerClient;
import Utils.Context;

import java.util.List;

public class CommandRemoveById extends CommandWithNotEmptyCollection implements CommandAuthorized {
	public CommandRemoveById() {
		super();
	}
	
	@Override
	protected void addArgumentValidators(List<Argument> arguments) {
		arguments.add(new Argument("id",
				new ExpectedLong(),
				new ExpectedIdExist()));
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		long idToRemove = Long.parseLong(commandArguments[0]);
		context.collectionManager.getCollection().forEach(flat->{
			if (flat.getId() == idToRemove) {
				if (! flat.getUserName().equals(session.getUser().getUsername()))
					throw new NotEnoughRightsToDeleteError();
				context.collectionManager.removeFlatFromCollectionById(idToRemove);
			}
		});
		context.dataBaseManager.removeFlatById(idToRemove);
		session.append("Из коллекции удален элемент с id ").append(idToRemove).append("\n");
	}
	
	@Override
	public String getName() {
		return "remove_by_id";
	}
	
	@Override
	public String getDescription() {
		return "удалить элемент из коллекции по его id";
	}
}
