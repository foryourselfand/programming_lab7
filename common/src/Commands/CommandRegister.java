package Commands;

public class CommandRegister extends CommandAuthorization {
	@Override
	public void execute() {
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
