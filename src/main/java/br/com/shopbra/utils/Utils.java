package br.com.shopbra.utils;

import java.security.SecureRandom;

public class Utils {

	protected static SecureRandom random = new SecureRandom();

	public static String generateToken() {
		long longToken = Math.abs(random.nextLong());
		String random = Long.toString(longToken, 16);
		return (random);
	}

}
