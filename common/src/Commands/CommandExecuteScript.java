package Commands;

import Expectations.Argument;
import Expectations.ExpectedFile.ExpectedFileExist;
import Expectations.ExpectedFile.ExpectedFileReadable;
import Expectations.ExpectedFile.ExpectedFileRegular;
import Expectations.ExpectedFile.ExpectedFileWritable;
import Session.SessionServerClient;
import SourseReaders.SourceReaderFile;
import Utils.Context;

import java.util.List;

/**
 * Команда исполнения скрипта
 */
public class CommandExecuteScript extends Command {
	public CommandExecuteScript() {
		super();
	}
	
	@Override
	protected void addArgumentValidators(List<Argument> arguments) {
		arguments.add(new Argument(
				"file_name",
				new ExpectedFileExist(),
				new ExpectedFileRegular(),
				new ExpectedFileReadable(),
				new ExpectedFileWritable()));
	}
	
	@Override
	public void preExecute() {
		Context.lineReader.addSourceReader(new SourceReaderFile(String.valueOf(commandArguments[0])));
	}
	
	@Override
	public void execute(Context context, SessionServerClient session) {
	}
	
	@Override
	public String getName() {
		return "execute_script";
	}
	
	@Override
	public String getDescription() {
		return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
	}
}
