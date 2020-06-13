package Errors;

import Errors.InputErrors.InputError;

public class ConnectionError extends InputError {
	public ConnectionError() {
		super("Проблемы с подключением...");
	}
}
