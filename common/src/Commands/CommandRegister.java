package Commands;

import Session.SessionServerClient;
import Utils.Context;

public class CommandRegister extends CommandAuthorization {
	@Override
	public void execute(Context context, SessionServerClient sessionServerClient) {
		context.dataBaseManager.addUser(user);
		System.out.println(user);
		System.out.println("Успешно добавлен");
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
