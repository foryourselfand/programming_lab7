package Expectations;

import Generators.IdGenerator;
import Utils.Context;

/**
 * Ожидается уникальный id
 */
public class ExpectedIdUnique implements ExpectationOnServer {
	@Override
	public void checkValueCorrectness(String valueRaw) {
		long valueLong = Long.parseLong(valueRaw);
		if (Context.idGenerator.containsId(valueLong))
			throw createInputError();
	}
	
	@Override
	public String getErrorMessage() {
		return "Должно быть уникальным";
	}
}
