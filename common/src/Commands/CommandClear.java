package Commands;

public class CommandClear extends CommandWithNotEmptyCollection implements CommandAuthorized {
	public CommandClear() {
		super();
	}
	
	@Override
	public void execute() {
		this.context.collectionManager.getCollection().clear();
		stringBuilderResponse.append("Коллекция очищена");
	}
	
	@Override
	public String getName() {
		return "clear";
	}
	
	@Override
	public String getDescription() {
		return "очистить коллекцию";
	}
}
