package Errors.AuthorizationErrors;

import Errors.InputErrors.InputError;
import Session.User;

public class UserNameAlreadyExistError extends InputError {
	public UserNameAlreadyExistError(User user) {
		super(String.format("Пользователь с именем %s уже существует", user.getUsername()));
	}
}
