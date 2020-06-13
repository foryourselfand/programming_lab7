package Commands;

import Expectations.Argument;
import Expectations.ExpectedFile.*;

import java.util.List;

public class CommandSave extends CommandWithNotEmptyCollection {
	public CommandSave() {
		super();
	}
	
	@Override
	protected void addArgumentValidators(List<Argument> arguments) {
		arguments.add(new Argument(
				"file_name",
				new ExpectedFileExtensionCsv(),
				new ExpectedCreateFileIfNotExist(),
				new ExpectedFileExist(),
				new ExpectedFileRegular(),
				new ExpectedFileWritable()
		));
	}
	
	@Override
	public void execute() {
		String filePath = commandArguments[0];
		this.context.csvSaver.saveCollectionToCSVFile(this.context.collectionManager.getCollection(), filePath);
		stringBuilderResponse.append("Коллекция сохранена в файл\n");
	}
	
	
	@Override
	public String getName() {
		return "save";
	}
	
	@Override
	public String getDescription() {
		return "сохранить коллекцию в файл";
	}
}
