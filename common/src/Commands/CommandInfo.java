package Commands;

import Session.SessionServerClient;
import Utils.Context;

/**
 * Команда вывода в стандартный поток информации о коллекции (тип, дата инициализации, количество элементов и т.д.)
 */
public class CommandInfo extends Command implements CommandAuthorized {
	public CommandInfo() {
		super();
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
		session.append("Тип коллекции: ").append(context.collectionManager.getCollectionType()).append("\n");
		session.append("Тип элементов коллекции: ").append(context.collectionManager.getCollectionElementType()).append("\n");
		session.append("Дата инициализации коллекции: ").append(context.collectionManager.getCollectionInitializationDate()).append("\n");
		session.append("Количество элементов в коллекции: ").append(context.collectionManager.getCollectionSize()).append("\n");
	}
	
	@Override
	public String getName() {
		return "info";
	}
	
	@Override
	public String getDescription() {
		return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
	}
}
