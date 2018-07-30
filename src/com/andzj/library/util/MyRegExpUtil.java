package com.andzj.library.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegExpUtil {
	private static String regOnlyDigit = "^[0-9]*$";
	private static Pattern patternOnlyDigit = Pattern.compile(regOnlyDigit);
	
	public static boolean matchOnlyDigit(String str)
	{
		Matcher matcherPhoneNumber = patternOnlyDigit.matcher(str);
		return matcherPhoneNumber.matches();
	}
	
	private static String regPhoneNumber = "^1[3|4|5|7|8][0-9]\\d{8}$";
	private static Pattern patternPhoneNumber = Pattern.compile(regPhoneNumber);
	
	
	public static boolean matchPhoneNumber(String str)
	{
		Matcher matcherPhoneNumber = patternPhoneNumber.matcher(str);
		return matcherPhoneNumber.matches();
	}

}
