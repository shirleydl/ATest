package com.shirley.aTestActuator.util;

import java.security.MessageDigest;

/**
 * md5加密处理(组合函数)
 */
public class Md5 {
	public String encryption32(String str) {
		String re_md5 = new String();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update((str).getBytes("UTF-8"));
			byte b[] = md5.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return re_md5;
		}
	}
}
