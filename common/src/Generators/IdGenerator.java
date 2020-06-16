package Generators;

import Utils.RandomHolder;

import java.util.HashSet;
import java.util.Set;


public class IdGenerator {
	private final Set<Long> ids = new HashSet<>();
	
	public Set<Long> getIds() {
		return ids;
	}
	
	public void clear() {
		ids.clear();
	}
	
	public boolean containsId(long id) {
		return ids.contains(id);
	}
	
	public void addId(long id) {
		ids.add(id);
	}
	
	public void removeId(Long id) {
		ids.remove(id);
	}
}
