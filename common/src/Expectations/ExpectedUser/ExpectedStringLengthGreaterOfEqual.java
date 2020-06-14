package Expectations.ExpectedUser;

import Expectations.Expectation;

public class ExpectedStringLengthGreaterOfEqual implements Expectation {
	private final long borderFrom;
	
	public ExpectedStringLengthGreaterOfEqual(long borderFrom) {
		this.borderFrom = borderFrom;
	}
	
	public ExpectedStringLengthGreaterOfEqual() {
		this(6);
	}
	
	@Override
	public void checkValueCorrectness(String valueRaw) {
		if (valueRaw.length() < borderFrom)
			throw createInputError();
	}
	
	@Override
	public String getErrorMessage() {
		return String.format("Длина строки должна быть не менее %d", borderFrom);
	}
}
