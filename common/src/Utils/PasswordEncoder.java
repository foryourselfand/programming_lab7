package Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
	public static final String pepper = "1@#$&^%$)3";
	
	public static String getHash(String password, String salt) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-224");
			byte[] data = messageDigest.digest((pepper + password + salt).getBytes(StandardCharsets.UTF_8));
			StringBuilder hexStringBuilder = new StringBuilder();
			for (byte b : data)
				hexStringBuilder.append(String.format("%02x", b));
			return hexStringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
