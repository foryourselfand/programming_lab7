package Expectations;

import Generators.IdGenerator;
import Utils.Context;

/**
 * Ожидается существующий id
 */
public class ExpectedIdExist implements ExpectationOnServer {
	@Override
	public void checkValueCorrectness(String valueRaw) {
		long id = Long.parseLong(valueRaw);
		if (! Context.idGenerator.containsId(id))
			throw createInputError();
	}
	
	@Override
	public String getErrorMessage() {
		return "Элемент с id должен находится коллекции";
	}
}
