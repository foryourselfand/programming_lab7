package Expectations;

import Errors.InputErrors.InputError;

import java.io.Serializable;

/**
 * Ожидание от значения
 */
public interface Expectation extends Serializable {
	void checkValueCorrectness(String valueRaw);
	
	String getErrorMessage();
	
	default InputError createInputError() {
		return new InputError(getErrorMessage());
	}
}
