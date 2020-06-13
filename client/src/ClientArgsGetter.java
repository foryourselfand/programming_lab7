public class ClientArgsGetter {
	private String host;
	private int port;
	
	public ClientArgsGetter(String[] args) {
		setUpFromArgs(args);
	}
	
	public void setUpFromArgs(String[] args) {
		if (args.length == 0 || args.length > 2) {
			showWrongArgsMessage();
			host = getHostDefault();
			port = getPortDefault();
		} else if (args.length == 1) {
			showWrongArgsMessage();
			host = args[0];
			port = getPortDefault();
		} else {
			host = args[0];
			port = getPortFromArgs(args);
		}
	}
	
	private void showWrongArgsMessage() {
		System.out.println("Args format: host port");
		System.out.println("Args example: localhost 8000");
	}
	
	private int getPortFromArgs(String[] args) {
		try {
			return Integer.parseInt(args[1]);
		} catch (Exception exception) {
			return getPortDefault();
		}
	}
	
	private String getHostDefault() {
		return "localhost";
	}
	
	private int getPortDefault() {
		return 8000;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
}
