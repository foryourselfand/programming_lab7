package Commands;

public class CommandLogin extends CommandAuthorization {
	@Override
	public void execute() {
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
