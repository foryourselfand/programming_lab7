package Commands;

import Expectations.Argument;
import Expectations.ExpectedUser.ExpectedAtLeastOneFrom;
import Expectations.ExpectedUser.ExpectedNoneFrom;
import Expectations.ExpectedUser.ExpectedStringLengthGreaterOfEqual;
import Session.User;
import Utils.Characters;

import java.util.List;

public abstract class CommandAuthorization extends Command {
	private User user;
	
	@Override
	protected void addArgumentValidators(List<Argument> arguments) {
		arguments.add(new Argument(
						"username",
						new ExpectedStringLengthGreaterOfEqual(6),
						new ExpectedNoneFrom(Characters.FORBIDDEN)
				)
		);
		arguments.add(new Argument(
						"password",
						new ExpectedStringLengthGreaterOfEqual(6),
						new ExpectedAtLeastOneFrom(Characters.LOWERCASE),
						new ExpectedAtLeastOneFrom(Characters.UPPERCASE),
						new ExpectedAtLeastOneFrom(Characters.DIGITS),
						new ExpectedAtLeastOneFrom(Characters.SPECIAL),
						new ExpectedNoneFrom(Characters.FORBIDDEN)
				)
		);
	}
	
	@Override
	public void preExecute() {
		user = new User(commandArguments[0], commandArguments[1]);
	}
	
	
	@Override
	public User getUser() {
		return user;
	}
}
