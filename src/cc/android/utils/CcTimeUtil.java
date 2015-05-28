package cc.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 各种时间信息
 * 
 *
 */
public class CcTimeUtil {
	/**
	 * 获取手机当前时间
	 * 
	 * @version 更新时间:2014年9月2日下午2:58:49
	 * @return 手机当前时间
	 */
	public static String getTimeAll() {
		return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault())
				.format(new Date());
	}
}
