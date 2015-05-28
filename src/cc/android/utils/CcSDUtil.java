package cc.android.utils;

import android.os.Environment;

/**
 * SD卡信息
 * 
 *
 */
public class CcSDUtil {
	/**
	 * 检测SD卡是否可用
	 * 
	 * @version 更新时间:2014年9月2日上午11:49:33
	 * @return true 可用,false不可用
	 */
	public static boolean isCanUseSD() {
		try {
			return Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取SD卡绝对目录
	 * 
	 * @version 更新时间:2014年9月2日下午2:32:58
	 * @return
	 */
	public static String getSDAbsolutePath() {
		if (isCanUseSD()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			return "";
		}
	}
}
