package Commands;

import Input.Flat;
import Session.SessionServerClient;
import Utils.Context;

public class CommandAverageOfHeight extends CommandWithNotEmptyCollection implements CommandAuthorized {
	public CommandAverageOfHeight() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		double heightAverage = context.collectionManager.getCollection().stream().
				mapToDouble(Flat::getHeight).
				average().
				orElse(0);
		
		session.append(String.format("Среднее значение поля height для всех элементов коллекции: %.2f\n", heightAverage));
	}
	
	@Override
	public String getName() {
		return "average_of_height";
	}
	
	@Override
	public String getDescription() {
		return "вывести среднее значение поля height для всех элементов коллекции";
	}
}
