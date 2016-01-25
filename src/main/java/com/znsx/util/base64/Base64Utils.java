package com.znsx.util.base64;

import sun.misc.BASE64Decoder;

/**
 * Base64Utils
 * @author wangbinyu <p />
 * Create at 2013 下午3:32:00
 */
public class Base64Utils {
	public static String getBASE64(byte[] b) {
		String s = null;
		if (b != null) {
			s = new sun.misc.BASE64Encoder().encode(b);
		}
		return s;
	}

	public static byte[] getFromBASE64(String s) {
		byte[] b = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				return b;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}
}
