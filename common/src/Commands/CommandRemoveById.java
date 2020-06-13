package Commands;

import Expectations.Argument;
import Expectations.ExpectedIdExist;
import Expectations.ExpectedType.ExpectedLong;

import java.util.List;

public class CommandRemoveById extends CommandWithNotEmptyCollection {
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
	public void execute() {
		long idToRemove = Long.parseLong(commandArguments[0]);
		this.context.collectionManager.removeFlatFromCollectionById(idToRemove);
		stringBuilderResponse.append("Из коллекции удален элемент с id ").append(idToRemove).append("\n");
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
