package Errors.AuthorizationErrors;

import Errors.InputErrors.InputError;

public class WrongUserNameOfPasswordError extends InputError {
	public WrongUserNameOfPasswordError() {
		super("Неверный логин или пароль");
	}
}
