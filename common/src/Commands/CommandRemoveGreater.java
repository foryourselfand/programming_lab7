package Commands;

import Input.Flat;
import Utils.Context;
import Utils.FlatCreator;

import java.util.List;
import java.util.stream.Collectors;

public class CommandRemoveGreater extends CommandWithNotEmptyCollection {
	private Flat flatNew;
	
	public CommandRemoveGreater() {
		super();
	}
	
	@Override
	public void preExecute() {
		flatNew = getCreatedFlat();
	}
	
	@Override
	public void execute() {
		int collectionSizeStart = this.context.collectionManager.getCollectionSize();
		
		this.context.collectionManager.getCollection().removeIf(flat->flat.compareTo(flatNew) < 0);
		Context.idGenerator.getIds().removeIf(id->flatNew.getId().equals(id));
		
		int collectionSizeEnd = this.context.collectionManager.getCollectionSize();
		boolean isCollectionSizeChanged = collectionSizeEnd != collectionSizeStart;
		
		if (isCollectionSizeChanged) {
			List<Long> idsGreater = context.collectionManager.getCollection()
					.stream()
					.map(Flat::getId)
					.sorted()
					.collect(Collectors.toList());
			stringBuilderResponse.append("Удаленные элементы").append(idsGreater.toString()).append("\n");
		} else {
			stringBuilderResponse.append("В коллекции нет элементов превышающих заданный").append("\n");
		}
	}
	
	private Flat getCreatedFlat() {
		Flat createdFlat = FlatCreator.getCreatedFlatFromTerminal(Context.lineReader);
		stringBuilderResponse.append("Созданный элемент для сравнения ").append(createdFlat.toString());
		return createdFlat;
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
