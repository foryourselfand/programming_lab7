package Commands;

import Input.Flat;
import Session.SessionServerClient;
import Utils.Context;
import Utils.FlatCreator;

import java.util.List;
import java.util.stream.Collectors;

public class CommandRemoveGreater extends CommandWithNotEmptyCollection implements CommandAuthorized {
	private Flat flatNew;
	
	public CommandRemoveGreater() {
		super();
	}
	
	@Override
	public void preExecute() {
		flatNew = getCreatedFlat();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		session.append("Созданный элемент для сравнения ").append(flatNew.toString());
		
		int collectionSizeStart = context.collectionManager.getCollectionSize();
		
		List<Long> idsToRemove = context.collectionManager.getCollection()
				.stream()
				.filter(flat->flat.compareTo(flatNew) < 0 && flat.getUserName().equals(session.getUser().getUsername()))
				.map(Flat::getId)
				.collect(Collectors.toList());
		
		idsToRemove.forEach(id->{
			context.collectionManager.removeFlatFromCollectionById(id);
			context.dataBaseManager.removeFlatById(id);
		});
		session.append("Из коллекции удалены принадлежащие вам элементы превышающие заданные");
	}
	
	private Flat getCreatedFlat() {
		return FlatCreator.getCreatedFlatFromTerminal(Context.lineReader);
	}
	
	@Override
	public String getName() {
		return "remove_greater";
	}
	
	@Override
	public String getDescription() {
		return "удалить из коллекции все элементы, превышающие заданный";
	}
}
