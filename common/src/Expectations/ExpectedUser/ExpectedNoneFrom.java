package Expectations.ExpectedUser;

import Expectations.Expectation;

public class ExpectedNoneFrom implements Expectation {
	private final String characters;
	
	public ExpectedNoneFrom(String characters) {
		this.characters = characters;
	}
	
	@Override
	public void checkValueCorrectness(String valueRaw) {
		for (Character characterExpected : characters.toCharArray()) {
			if (valueRaw.contains(characterExpected.toString())) {
				throw createInputError();
			}
		}
	}
	
	@Override
	public String getErrorMessage() {
		return String.format("Строка не должна содержать символы из набора \"%s\"", characters);
	}
}
