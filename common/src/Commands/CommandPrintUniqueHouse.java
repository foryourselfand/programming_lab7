package Commands;

import Input.Flat;
import Input.House;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда вывода уникальных значений поля дом
 */
public class CommandPrintUniqueHouse extends CommandWithNotEmptyCollection {
	public CommandPrintUniqueHouse() {
		super();
	}
	
	@Override
	public void execute() {
		List<House> uniqueHousesSort =
				context.collectionManager.getCollection().stream()
						.map(Flat::getHouse)
						.distinct()
						.sorted(Comparator.comparing(House::getHouseName))
						.collect(Collectors.toList());
		
		printUniqueHouses(uniqueHousesSort);
	}
	
	private void printUniqueHouses(List<House> uniqueHouses) {
		if (uniqueHouses.isEmpty())
			stringBuilderResponse.append("Уникальных значений поля house - нет\n");
		else {
			stringBuilderResponse.append("Уникальные значения поля house: \n");
			for (House house : uniqueHouses)
				stringBuilderResponse.append(house.toString()).append("\n");
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
