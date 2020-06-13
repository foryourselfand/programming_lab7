package Commands;

import Expectations.Argument;
import Expectations.ExpectedFile.ExpectedFileExist;
import Expectations.ExpectedFile.ExpectedFileExtensionCsv;
import Expectations.ExpectedFile.ExpectedFileReadable;
import Expectations.ExpectedFile.ExpectedFileRegular;
import Utils.CSVLoader;
import Utils.Context;

import java.util.List;

/**
 * Команда загрузки коллекции из файла
 */
public class CommandLoad extends Command {
	public static StringBuilder stringBuilderLoad;
	private final CSVLoader csvLoader;
	
	public CommandLoad() {
		super();
		csvLoader = new CSVLoader();
	}
	
	@Override
	protected void addArgumentValidators(List<Argument> arguments) {
		arguments.add(new Argument(
				"file_name",
				new ExpectedFileExtensionCsv(),
				new ExpectedFileExist(),
				new ExpectedFileRegular(),
				new ExpectedFileReadable()
		));
	}
	
	@Override
	public void execute() {
		stringBuilderLoad = new StringBuilder();
		
		this.context.collectionManager.clearCollection();
		Context.idGenerator.clear();
		this.csvLoader.createCollectionFromFile(commandArguments[0], Context.lineReader, context.collectionManager);
		this.context.collectionManager.changeInitializationDate();
		
		stringBuilderResponse = new StringBuilder(stringBuilderLoad);
		stringBuilderResponse.append("Элементы коллекции в строковом представлении:\n");
		
		stringBuilderResponse.append(context.collectionManager.getCollectionSortedString());
	}
	
	@Override
	public String getName() {
		return "load";
	}
	
	@Override
	public String getDescription() {
		return "загрузить коллекцию из файла";
	}
}
