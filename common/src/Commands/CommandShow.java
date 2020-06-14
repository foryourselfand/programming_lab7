package Commands;


import Session.SessionServerClient;
import Utils.Context;

/**
 * Команда вывода в стандартный поток всех элементов коллекции в строковом представлении
 */
public class CommandShow extends CommandWithNotEmptyCollection implements CommandAuthorized{
	public CommandShow() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		session.append(context.collectionManager.getCollectionSortedString());
	}
	
	@Override
	public String getName() {
		return "show";
	}
	
	@Override
	public String getDescription() {
		return "вывести в стандартный поток все элементы коллекции в строковом представлении";
	}
}
