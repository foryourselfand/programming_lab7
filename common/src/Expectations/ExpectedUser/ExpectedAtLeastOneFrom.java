package Expectations.ExpectedUser;

import Expectations.Expectation;

public class ExpectedAtLeastOneFrom implements Expectation {
	private final String characters;
	
	public ExpectedAtLeastOneFrom(String characters) {
		this.characters = characters;
	}
	
	@Override
	public void checkValueCorrectness(String valueRaw) {
		boolean found = false;
		for (Character characterExpected : characters.toCharArray()) {
			if (valueRaw.contains(characterExpected.toString())) {
				found = true;
				break;
			}
		}
		if (found) return;
		throw createInputError();
		
	}
	
	@Override
	public String getErrorMessage() {
		return String.format("Строка должна содержать как минимум один символ из набора: \"%s\"", characters);
	}
}
