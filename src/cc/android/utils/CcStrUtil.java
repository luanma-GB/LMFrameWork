package cc.android.utils;

import android.util.Base64;

/**
 * 字符串工具类
 * 
 *
 */
public class CcStrUtil {
	/**
	 * 判断一个字符串是否为null或空值
	 * 
	 * @version 更新时间:2014年9月2日下午5:58:52
	 * @param str
	 *            需要判断的字符串
	 * @return true:为空或者null，false:不为空或者null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 描述：是否只是数字.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 是否只是数字:是为true，否则false
	 */
	public static Boolean isNumber(String str) {
		Boolean isNumber = false;
		String expr = "^[0-9]+$";
		if (str.matches(expr)) {
			isNumber = true;
		}
		return isNumber;
	}
	
	/**
	 * ToBase64
	 * @param data
	 * @return
	 */
	public static String stringToBase64(String data) {
		return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
	}

	/**
	 * base64Tostring
	 * @param data
	 * @return
	 */
	public static String base64Tostring(String data) {
		return new String(Base64.decode(data, Base64.DEFAULT));
	}
}
