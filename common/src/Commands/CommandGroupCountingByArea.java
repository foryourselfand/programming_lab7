package Commands;

import Input.Flat;
import Session.SessionServerClient;
import Utils.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class CommandGroupCountingByArea extends CommandWithNotEmptyCollection implements CommandAuthorized {
	public CommandGroupCountingByArea() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		Map<Integer, List<Flat>> areaToFlatListMap = context.collectionManager.getCollection().stream()
				.collect(groupingBy(Flat::getArea));
		printGroupCountingByArea(areaToFlatListMap, session);
	}
	
	private void printGroupCountingByArea(Map<Integer, List<Flat>> areaToFlatListMap, SessionServerClient session) {
		for (Map.Entry<Integer, List<Flat>> areaToFlatList : areaToFlatListMap.entrySet()) {
			session.append("Группа area=").append(areaToFlatList.getKey()).append("; Количество=").append(areaToFlatList.getValue().size()).append("\n");
			List<Flat> flatList = areaToFlatList.getValue();
			
			List<Flat> flatListSorted = new ArrayList<>(flatList);
			flatListSorted.sort(Flat.comparatorByName);
			
			for (Flat flat : flatListSorted) {
				session.append(flat.toString()).append("\n");
			}
		}
	}
	
	@Override
	public String getName() {
		return "group_counting_by_area";
	}
	
	@Override
	public String getDescription() {
		return "сгруппировать элементы коллекции по значению поле area, вывести количество элементов в каждой группе";
	}
}
