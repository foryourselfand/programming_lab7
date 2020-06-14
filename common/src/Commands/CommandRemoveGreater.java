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
		
		context.collectionManager.getCollection().removeIf(flat->flat.compareTo(flatNew) < 0);
		Context.idGenerator.getIds().removeIf(id->flatNew.getId().equals(id));
		
		int collectionSizeEnd = context.collectionManager.getCollectionSize();
		boolean isCollectionSizeChanged = collectionSizeEnd != collectionSizeStart;
		
		if (isCollectionSizeChanged) {
			List<Long> idsGreater = context.collectionManager.getCollection()
					.stream()
					.map(Flat::getId)
					.sorted()
					.collect(Collectors.toList());
			session.append("Удаленные элементы").append(idsGreater.toString()).append("\n");
		} else {
			session.append("В коллекции нет элементов превышающих заданный").append("\n");
		}
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
