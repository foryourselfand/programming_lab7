package Utils;

import java.util.Random;

public class RandomSequenceGenerator {
	public static String getRandomSequence(int length, String... availableCharacters) {
		StringBuilder availableStringBuilder = new StringBuilder();
		for (String availableCharacter : availableCharacters) {
			availableStringBuilder.append(availableCharacter);
		}
		
		String available = availableStringBuilder.toString();
		
		StringBuilder resultStringBuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			resultStringBuilder.append(available.charAt(random.nextInt(available.length())));
		}
		return resultStringBuilder.toString();
	}
}
