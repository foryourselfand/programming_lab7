package Utils;

import Input.Flat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class CollectionManager {
	private final LinkedHashSet<Flat> collection;
	
	private LocalDate initializationDate;
	
	public CollectionManager() {
		this.collection = new LinkedHashSet<>();
		this.initializationDate = Context.INITIALIZATION_DATE;
	}
	
	public LinkedHashSet<Flat> getCollection() {
		return collection;
	}
	
	public List<Flat> getCollectionSortedByName() {
		List<Flat> collectionList = new ArrayList<>(collection);
		collectionList.sort(Flat.comparatorByName);
		return collectionList;
	}
	
	public Flat getFlatMax() {
		return collection.stream().max(Flat::compareTo).get();
	}
	
	public void clearCollection() {
		this.collection.clear();
	}
	
	public void addFlatToCollection(Flat flatToAdd) {
		this.collection.add(flatToAdd);
		Context.idGenerator.addId(flatToAdd.getId());
	}
	
	public void removeFlatFromCollectionById(Long idToRemove) {
		getCollection().removeIf(flat->flat.getId().equals(idToRemove));
		Context.idGenerator.removeId(idToRemove);
	}
	
	public String getCollectionString(Iterable<Flat> collection) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Flat flat : collection)
			stringBuilder.append(flat).append("\n");
		return stringBuilder.toString();
	}
	
	public String getCollectionSortedString() {
		return getCollectionString(getCollectionSortedByName());
	}
	
	public void changeInitializationDate() {
		if (! collection.isEmpty())
			this.initializationDate = this.getInitializationDateMin();
		else
			this.initializationDate = Context.INITIALIZATION_DATE;
	}
	
	public LocalDate getCollectionInitializationDate() {
		return initializationDate;
	}
	
	private LocalDate getInitializationDateMin() {
		LocalDate initializationLocalDate = LocalDate.MAX;
		for (Flat flat : this.collection) {
			LocalDate flatLocalDate = flat.getCreationDate();
			if (flatLocalDate.compareTo(initializationLocalDate) < 0) {
				initializationLocalDate = flatLocalDate;
			}
		}
		return initializationLocalDate;
	}
	
	public String getCollectionType() {
		return LinkedHashSet.class.getSimpleName();
	}
	
	public String getCollectionElementType() {
		return Flat.class.getSimpleName();
	}
	
	public int getCollectionSize() {
		return this.collection.size();
	}
	
	public boolean getIsCollectionEmpty() {
		return this.collection.isEmpty();
	}
}
