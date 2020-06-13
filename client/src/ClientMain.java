import Errors.InputErrors.InputError;
import Utils.CommandsHolder;
import Utils.Context;

public class ClientMain {
	public static void main(String[] args) {
		ClientArgsGetter argsGetter = new ClientArgsGetter(args);
		String host = argsGetter.getHost();
		int port = argsGetter.getPort();
		System.out.format("host: %s\t\tport: %d\n", host, port);
		
		try {
			Client.connect(host, port);
		} catch (InputError inputError) {
			System.out.println(inputError.getMessage());
		}
		
		CommandsHolder commandsHolder = new CommandsHolder();
		CommandsSender commandsSender = new CommandsSender(commandsHolder);
		
		while (Context.lineReader.hasSomethingToRead()) {
			String lineRead = Context.lineReader.readLine(">>> ");
			commandsSender.tryToExecuteCommand(lineRead);
		}
	}
}
