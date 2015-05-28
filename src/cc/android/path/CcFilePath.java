package cc.android.path;

import java.io.File;

import cc.android.utils.CcSDUtil;

/**
 * 各种文件保存路径(存在SD卡则主目录为:/Android/data/包名/...;没有SD卡则主目录为:/data/data/包名/...;)
 * 
 *
 */
public class CcFilePath {
	/** 文件保存路径 */
	private static String mPathFile = "";
	/** 图片保存路径 */
	private static String mPathImg = "";
	/** 应用程序APK保存目录 */
	private static String mPathApk = "";
	/** 应用异常保存目录 */
	private static String mPathException = "";
	/** 包名 */
	private static String mPackageName = "defaultPackage";
	/** 默认根目录 */
	private static final String mPathRoot = CcSDUtil.isCanUseSD() ? CcSDUtil
			.getSDAbsolutePath()
			+ File.separator
			+ "Android"
			+ File.separator
			+ "data" + File.separator : File.separator + "data"
			+ File.separator + "data" + File.separator;

	/**
	 * 外部设置包名
	 * 
	 * @version 更新时间:2014年9月3日下午2:52:58
	 * @param packageName
	 *            包名
	 */
	public static void setPackageName(String packageName) {
		mPackageName = packageName;
	}

	/**
	 * 获取应用文件下载目录
	 * 
	 * @version 更新时间:2014年9月2日下午2:40:36
	 * @return 文件下载目录
	 */
	public static String getPathFile() {
		if (mPathFile.equals("")) {
			mPathFile = mPathRoot + mPackageName + File.separator + "files"
					+ File.separator;
		}
		return mPathFile;
	}

	/**
	 * 获取应用图片下载目录
	 * 
	 * @version 更新时间:2014年9月2日下午2:41:41
	 * @return 图片下载目录
	 */
	public static String getPathImg() {
		if (mPathImg.equals("")) {
			mPathImg = mPathRoot + mPackageName + File.separator + "images"
					+ File.separator;
		}
		return mPathImg;
	}

	/**
	 * 应用中APK下载目录
	 * 
	 * @version 更新时间:2014年9月2日下午2:43:44
	 * @return APK下载目录
	 */
	public static String getPathApk() {
		if (mPathApk.equals("")) {
			mPathApk = mPathRoot + mPackageName + File.separator + "apks"
					+ File.separator;
		}
		return mPathApk;
	}

	/**
	 * 应用异常文件保存目录
	 * 
	 * @version 更新时间:2014年9月2日下午3:03:36
	 * @return 异常信息保存目录
	 */
	public static String getPathException() {
		if (mPathException.equals("")) {
			mPathException = mPathRoot + mPackageName + File.separator
					+ "exceptions" + File.separator;
		}
		return mPathException;
	}
}
