package Errors;

import Errors.InputErrors.InputError;

public class TimeoutError extends InputError {
	public TimeoutError() {
		super("Сервер не доступен.");
	}
}
