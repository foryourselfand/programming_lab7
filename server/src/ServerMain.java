import DataBase.CredentialsGetter.CredentialsGetterRaw;
import DataBase.DataBaseManager;
import DataBase.UrlGetter.UrlGetterSsh;
import Errors.InputErrors.InputError;
import Utils.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
	public static final Logger logger = Logger.getLogger(ServerMain.class.getName());
	
	public static void main(String[] args) {
		ServerArgsGetter argsGetter = new ServerArgsGetter(args);
		int port = argsGetter.getPort();
		logger.info(String.format("port: %d\n", port));
		
		Context context = new Context();
		
		try {
			Server.connect(port);
		} catch (InputError inputError) {
			logger.log(Level.SEVERE, "Error while trying initialize server", inputError);
		}
		Server.run(context);
	}
}
