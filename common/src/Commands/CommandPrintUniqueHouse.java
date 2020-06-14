package Commands;

import Input.Flat;
import Input.House;
import Session.SessionServerClient;
import Utils.Context;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда вывода уникальных значений поля дом
 */
public class CommandPrintUniqueHouse extends CommandWithNotEmptyCollection implements CommandAuthorized {
	public CommandPrintUniqueHouse() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		List<House> uniqueHousesSort =
				context.collectionManager.getCollection().stream()
						.map(Flat::getHouse)
						.distinct()
						.sorted(Comparator.comparing(House::getHouseName))
						.collect(Collectors.toList());
		
		printUniqueHouses(uniqueHousesSort, session);
	}
	
	private void printUniqueHouses(List<House> uniqueHouses, SessionServerClient session) {
		if (uniqueHouses.isEmpty())
			session.append("Уникальных значений поля house - нет\n");
		else {
			session.append("Уникальные значения поля house: \n");
			for (House house : uniqueHouses)
				session.append(house.toString()).append("\n");
		}
	}
	
	
	@Override
	public String getName() {
		return "print_unique_house";
	}
	
	@Override
	public String getDescription() {
		return "вывести уникальные значения поля house";
	}
}
