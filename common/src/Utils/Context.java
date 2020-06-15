package Utils;

import DataBase.CredentialsGetter.CredentialsGetterRaw;
import DataBase.DataBaseManager;
import DataBase.UrlGetter.UrlGetterSsh;
import Generators.CreationDateGenerator;
import Generators.IdGenerator;

import java.time.LocalDate;

public class Context {
	public static final LocalDate INITIALIZATION_DATE = CreationDateGenerator.generateCreationDate();
	public static final IdGenerator idGenerator = new IdGenerator();
	public static LineReader lineReader = new LineReader();
	public CommandsHolder commandsHolder;
	public CollectionManager collectionManager;
	public CSVSaver csvSaver;
	public DataBaseManager dataBaseManager;
	
	public Context() {
		this.dataBaseManager = new DataBaseManager(new UrlGetterSsh(), new CredentialsGetterRaw());
		this.collectionManager = new CollectionManager(dataBaseManager);
		this.commandsHolder = new CommandsHolder();
		this.csvSaver = new CSVSaver();
		
	}
}
