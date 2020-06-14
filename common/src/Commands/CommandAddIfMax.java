package Commands;

import Input.Flat;
import Session.SessionServerClient;
import Utils.Context;
import Utils.FlatCreator;

public class CommandAddIfMax extends Command implements CommandAuthorized {
	private Flat flatNew;
	
	@Override
	public void preExecute() {
		flatNew = getFlatNew();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		session.append("Новый элемент ").append(flatNew.toString()).append("\n");
		if (context.collectionManager.getIsCollectionEmpty()) {
			context.collectionManager.addFlatToCollection(flatNew);
			session.append("В коллекцию добавлен элемент ").append(flatNew.toString()).append("\n");
		} else {
			Flat flatMax = getFlatMax(context, session);
			addFlatNewToCollectionIfGreaterThatFlatMax(context, session, flatNew, flatMax);
		}
	}
	
	private Flat getFlatNew() {
		return FlatCreator.getCreatedFlatFromTerminal(Context.lineReader);
	}
	
	private Flat getFlatMax(Context context, SessionServerClient session) {
		Flat flatMax = context.collectionManager.getFlatMax();
		session.append("Наибольший элемент коллекции ").append(flatMax.toString()).append("\n");
		System.out.println();
		return flatMax;
	}
	
	private void addFlatNewToCollectionIfGreaterThatFlatMax(Context context, SessionServerClient session, Flat flatNew, Flat flatMax) {
		if (flatNew.compareTo(flatMax) > 0)
			addFlatNewToCollection(context, session, flatNew);
		else
			dontAddFlatNewToCollection(session);
	}
	
	private void addFlatNewToCollection(Context context, SessionServerClient session, Flat flatNew) {
		session.append("Значение нового элемента превышает значение наибольшего элемента коллекции\n");
		context.collectionManager.addFlatToCollection(flatNew);
		session.append("В коллекцию добавлен элемент ").append(flatNew.toString()).append("\n");
	}
	
	private void dontAddFlatNewToCollection(SessionServerClient session) {
		session.append("Значение нового элемента не превышает значение наибольшего элемента коллекции\n");
		session.append("В коллекцию элемент не добавлен");
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
