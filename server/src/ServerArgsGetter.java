import java.util.logging.Logger;

public class ServerArgsGetter {
	public static final Logger logger = Logger.getLogger(ServerArgsGetter.class.getName());
	
	private int port;
	private String CSVFilePath;
	
	public ServerArgsGetter(String[] args) {
		setUpFromArgs(args);
	}
	
	private void setUpFromArgs(String[] args) {
		if (args.length == 0 || args.length > 2) {
			showWrongArgsMessage();
			port = getPortDefault();
			CSVFilePath = getCSVFilePathDefault();
		} else if (args.length == 1) {
			showWrongArgsMessage();
			port = getPortFromArgs(args);
			CSVFilePath = getCSVFilePathDefault();
		} else {
			port = getPortFromArgs(args);
			CSVFilePath = args[1];
		}
	}
	
	private void showWrongArgsMessage() {
		logger.info("Args format: port collection.csv");
		logger.info(String.format("Args example: %d %s\n", getPortDefault(), getCSVFilePathDefault()));
	}
	
	private int getPortFromArgs(String[] args) {
		try {
			return Integer.parseInt(args[0]);
		} catch (Exception exception) {
			return getPortDefault();
		}
	}
	
	private int getPortDefault() {
		return 8000;
	}
	
	private String getCSVFilePathDefault() {
		return "res/collections/example.csv";
	}
	
	public int getPort() {
		return port;
	}
	
	public String getCSVFilePath() {
		return CSVFilePath;
	}
}
