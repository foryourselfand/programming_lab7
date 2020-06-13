package Commands;

import Expectations.Argument;
import Expectations.ExpectedIdExist;
import Expectations.ExpectedType.ExpectedLong;
import Input.Flat;
import Utils.Context;
import Utils.FlatCreator;

import java.util.List;

public class CommandUpdateById extends CommandWithNotEmptyCollection {
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
	public void execute() {
		long idToRemove = Long.parseLong(commandArguments[0]);
		removeFlatOld(idToRemove);
		addFlatNew(flatNew);
	}
	
	private Flat createFlatNew() {
		return FlatCreator.getCreatedFlatFromTerminal(Context.lineReader);
	}
	
	private void removeFlatOld(long idToRemove) {
		this.context.collectionManager.removeFlatFromCollectionById(idToRemove);
		stringBuilderResponse.append("Из коллекции удален элемент с id ").append(idToRemove).append("\n");
	}
	
	private void addFlatNew(Flat flatNew) {
		context.collectionManager.addFlatToCollection(flatNew);
		stringBuilderResponse.append("В коллекцию добавлен элемент ").append(flatNew.toString()).append("\n");
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
