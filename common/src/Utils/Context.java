package Utils;

import Commands.Command;
import Errors.InputErrors.InputError;
import Generators.CreationDateGenerator;
import Generators.IdGenerator;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Context {
	public static final Logger logger = Logger.getLogger(Context.class.getName());
	
	public static final LocalDate INITIALIZATION_DATE = CreationDateGenerator.generateCreationDate();
	public static final int HISTORY_SIZE = 12;
	public static final IdGenerator idGenerator = new IdGenerator();
	public static LineReader lineReader = new LineReader();
	public CommandsHolder commandsHolder;
	public CommandsHistoryManager commandsHistoryManager;
	public CollectionManager collectionManager;
	public CSVSaver csvSaver;
	
	public Context() {
		this.collectionManager = new CollectionManager();
		this.commandsHolder = new CommandsHolder();
		this.commandsHistoryManager = new CommandsHistoryManager();
		this.csvSaver = new CSVSaver();
	}
	
	public void loadCollection(String csvFilePath) {
		logger.info("Trying to load collection from csv file");
		
		Command commandLoad = commandsHolder.getCommandByName("load");
		try {
			commandLoad.validateArguments(new String[]{csvFilePath});
			commandLoad.showDescriptionAndExecute(this);
			
			Command commandShow = commandsHolder.getCommandByName("show");
			commandShow.validateArguments(new String[]{});
			commandShow.showDescriptionAndExecute(this);
			
			logger.info("Successfully load collection from csv file");
		} catch (InputError inputError) {
			logger.log(Level.SEVERE, "Error while trying to load deque from csv file", inputError);
		}
	}
}
