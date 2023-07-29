package com.example.seleniumtest.test;

import java.text.SimpleDateFormat;

public class Util {

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static int getAsInt(String str) {
		if (str == null) {
			return 0;
		}

		str = str.trim();

		if (str.length() == 0) {
			return 0;
		}

		str = str.replaceAll(",", "");

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}

	}

	public static String getNowDateStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(System.currentTimeMillis());
	}
}
