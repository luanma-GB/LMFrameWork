package cc.android.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 手机各种信息
 * 
 *
 */
public class CcPhoneUtil {
	/**
	 * 获取屏幕分辨率信息
	 * 
	 * @version 更新时间:2014年9月3日上午10:53:06
	 * @param context
	 *            窗口
	 * @return 分辨率信息
	 */
	public static DisplayMetrics getScreenInfo(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}
}
