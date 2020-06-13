package Commands;

import Input.Flat;
import Utils.Context;
import Utils.FlatCreator;

public class CommandAddIfMax extends Command {
	private Flat flatNew;
	
	@Override
	public void preExecute() {
		flatNew = getFlatNew();
	}
	
	@Override
	public void execute() {
		if (context.collectionManager.getIsCollectionEmpty()) {
			context.collectionManager.addFlatToCollection(flatNew);
			stringBuilderResponse.append("В коллекцию добавлен элемент ").append(flatNew.toString()).append("\n");
		} else {
			Flat flatMax = getFlatMax();
			addFlatNewToCollectionIfGreaterThatFlatMax(flatNew, flatMax);
		}
	}
	
	private Flat getFlatNew() {
		Flat flatNew = FlatCreator.getCreatedFlatFromTerminal(Context.lineReader);
		stringBuilderResponse.append("Новый элемент ").append(flatNew.toString()).append("\n");
		return flatNew;
	}
	
	private Flat getFlatMax() {
		Flat flatMax = context.collectionManager.getFlatMax();
		stringBuilderResponse.append("Наибольший элемент коллекции ").append(flatMax.toString()).append("\n");
		System.out.println();
		return flatMax;
	}
	
	private void addFlatNewToCollectionIfGreaterThatFlatMax(Flat flatNew, Flat flatMax) {
		if (flatNew.compareTo(flatMax) > 0)
			addFlatNewToCollection(flatNew);
		else
			dontAddFlatNewToCollection();
	}
	
	private void addFlatNewToCollection(Flat flatNew) {
		stringBuilderResponse.append("Значение нового элемента превышает значение наибольшего элемента коллекции\n");
		context.collectionManager.addFlatToCollection(flatNew);
		stringBuilderResponse.append("В коллекцию добавлен элемент ").append(flatNew.toString()).append("\n");
	}
	
	private void dontAddFlatNewToCollection() {
		stringBuilderResponse.append("Значение нового элемента не превышает значение наибольшего элемента коллекции\n");
		stringBuilderResponse.append("В коллекцию элемент не добавлен");
	}
	
	@Override
	public String getName() {
		return "add_if_max";
	}
	
	@Override
	public String getDescription() {
		return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
	}
}
