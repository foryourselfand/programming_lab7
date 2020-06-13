package Errors.WrongHeaderErrors;

import java.util.Set;

public class WrongHeaderFieldsBothError extends WrongHeaderError {
	public WrongHeaderFieldsBothError(Set<String> missingFields, Set<String> extraFields) {
		super(WrongHeaderError.MESSAGE_MISSING + getWithoutBrackets(missingFields.toString()) + "\n"
				+ WrongHeaderError.MESSAGE_EXTRA + getWithoutBrackets(extraFields.toString()));
	}
	
	public static String getWithoutBrackets(String input) {
		return input.substring(1, input.length() - 1);
	}
}
