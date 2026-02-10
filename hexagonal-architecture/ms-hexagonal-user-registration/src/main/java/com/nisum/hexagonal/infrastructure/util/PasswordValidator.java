package com.nisum.hexagonal.infrastructure.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

	public static boolean isValid(final String password, final String patternPassword) {
		Pattern pattern = Pattern.compile(patternPassword);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}