package Commands;

import Session.SessionServerClient;
import Utils.Context;

public class CommandLogin extends CommandAuthorization {
	@Override
	public void execute(Context context, SessionServerClient session) {
	}
	
	@Override
	public String getName() {
		return "login";
	}
	
	@Override
	public String getDescription() {
		return "авторизоваться";
	}
}
