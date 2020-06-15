package Errors;

import Errors.InputErrors.InputError;

public class ElementNotAddedError extends InputError {
	public ElementNotAddedError() {
		super("Элемент не добавлен");
	}
}
