package Errors;

import Errors.InputErrors.InputError;

public class NotEnoughRightsToDeleteError extends InputError {
	public NotEnoughRightsToDeleteError() {
		super("Недостаточно прав для удаления элемента");
	}
}
