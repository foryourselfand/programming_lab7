package Commands;

import Errors.AuthorizationErrors.UserNameNotExistError;
import Errors.AuthorizationErrors.WrongUserNameOfPasswordError;
import Session.SessionServerClient;
import Utils.Context;

public class CommandLogin extends CommandAuthorization {
	@Override
	public void execute(Context context, SessionServerClient session) {
		if (! context.dataBaseManager.containsUserName(session.getUser()))
			throw new UserNameNotExistError(session.getUser());
		if (! context.dataBaseManager.containsUser(session.getUser()))
			throw new WrongUserNameOfPasswordError();
		session.append(String.format("Пользователь %s успешно авторизовался", session.getUser().getUsername()));
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
