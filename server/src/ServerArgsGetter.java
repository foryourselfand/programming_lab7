import java.util.logging.Logger;

public class ServerArgsGetter {
	public static final Logger logger = Logger.getLogger(ServerArgsGetter.class.getName());
	
	private int port;
	
	public ServerArgsGetter(String[] args) {
		setUpFromArgs(args);
	}
	
	private void setUpFromArgs(String[] args) {
		if (args.length != 1) {
			showWrongArgsMessage();
			port = getPortDefault();
		} else {
			port = getPortFromArgs(args);
		}
	}
	
	private void showWrongArgsMessage() {
		logger.info("Args format: port ");
		logger.info(String.format("Args example: %d\n", getPortDefault()));
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
	
	public int getPort() {
		return port;
	}
}
