package com.briup.run.common.util;

import java.util.Random;

public class RandomChar {
	private static final String CHAR_ALL = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

	private static final String CHAR_LOWERCASE = "qwertyuiopasdfghjklzxcvbnm";

	private static final String CHAR_UPPERCASE = "QWERTYUIOPLAKSJDHFGZXCVBNM";

	private static final String NUMBERS = "0123456789";

	private static final String ALL = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

	private static final String CHAR_SPECIAL_ALL = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM~!@#$%^&*";

	public static final int RANDOM_CHAR_UPPERCASE = 0;

	public static final int RANDOM_CHAR_LOWERCASE = 1;

	public static final int RANDOM_CHAR_ALL = 2;

	public static final int RANDOM_NUMBERS = 3;

	public static final int RANDOM_ALL = 4;

	public static final int RANDOM_SPECIAL_ALL = 5;

	public static String getChars(int MOD, int count) {

		Random r = new Random();

		int i = 0;
		String random_source = null;
		switch (MOD) {
		case RANDOM_CHAR_UPPERCASE:
			random_source = CHAR_UPPERCASE;
			break;
		case RANDOM_CHAR_LOWERCASE:
			random_source = CHAR_LOWERCASE;
			break;
		case RANDOM_CHAR_ALL:
			random_source = CHAR_ALL;
			break;
		case RANDOM_NUMBERS:
			random_source = NUMBERS;
			break;
		case RANDOM_ALL:
			random_source = ALL;
			break;
		case RANDOM_SPECIAL_ALL:
			random_source = CHAR_SPECIAL_ALL;
			break;
		default:
			random_source = CHAR_SPECIAL_ALL;
			break;
		}

		int c = random_source.length();
		String result = "";

		while (i < count) {
			int v = Math.abs(r.nextInt()) % c;
			result += random_source.substring(v, v + 1);
			i++;
		}
		return result;
	}

	
}