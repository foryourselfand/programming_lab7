package Errors.AuthorizationErrors;

import Errors.InputErrors.InputError;

public class NotAuthorizedError extends InputError {
	public NotAuthorizedError() {
		super("Пользователь не авторизован");
	}
}
