package com.icer.cnbeta.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Coder {
	private static final char[] a;

	static {
		a = new char[16];
		for (int i = 0; i < a.length; i++) {
			if (i < 10) {
				a[i] = (char) (48 + i);
			} else {
				a[i] = (char) (48 + i + 7);
			}
		}
	}

	public static String coder(String paramString) {
		String str = "";
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			byte[] arrayOfByte = localMessageDigest.digest();
			StringBuilder localStringBuilder = new StringBuilder(
					2 * arrayOfByte.length);
			for (int i = 0;; i++) {
				if (i >= arrayOfByte.length) {
					str = localStringBuilder.toString().toLowerCase();
					break;
				}
				// 高四位
				localStringBuilder.append(a[((0xF0 & arrayOfByte[i]) >>> 4)]);
				// 低四位
				localStringBuilder.append(a[(0xF & arrayOfByte[i])]);
			}
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			localNoSuchAlgorithmException.printStackTrace();
			str = "";
		}
		return str;
	}
}
