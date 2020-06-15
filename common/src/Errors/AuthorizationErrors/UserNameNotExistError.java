package Errors.AuthorizationErrors;

import Errors.InputErrors.InputError;
import Session.User;

public class UserNameNotExistError extends InputError {
	public UserNameNotExistError(User user) {
		super(String.format("Пользователь с именем %s не существует", user.getUsername()));
	}
}
