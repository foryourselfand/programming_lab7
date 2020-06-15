package Commands;

import Errors.AuthorizationErrors.UserNameAlreadyExistError;
import Session.SessionServerClient;
import Utils.Context;

public class CommandRegister extends CommandAuthorization {
	@Override
	public void execute(Context context, SessionServerClient session) {
		if (context.dataBaseManager.containsUserName(session.getUser()))
			throw new UserNameAlreadyExistError(session.getUser());
		context.dataBaseManager.addUser(session.getUser());
		session.append(String.format("Пользователь %s успешно зарегистрирован", session.getUser().getUsername()));
	}
	
	@Override
	public String getName() {
		return "register";
	}
	
	@Override
	public String getDescription() {
		return "зарегистрироваться";
	}
}
