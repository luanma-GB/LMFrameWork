package cc.android.application;

import java.io.File;

import android.app.Application;
import cc.android.path.CcFilePath;
import cc.android.utils.CcExceptionHandler;
import cc.android.utils.CcLog;

/**
 * 当前应用信息 ------需要在项目中添加 <uses-permission
 * android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * 
 *
 */
public class CcAplication extends Application {
	/** SharePreferences文件名 */
	public static final String SHAREPATH = "app_share";
	/** 当前应用 */
	private static CcAplication ccAplication = null;

	/**
	 * 私有构造方法
	 * 
	 * @version 更新时间:2014年9月2日下午2:24:32
	 */
	protected CcAplication() {

	}

	/**
	 * 获取当前应用
	 * 
	 * @version 更新时间:2014年9月2日下午2:23:38
	 * @return 当前应用
	 */
	public static CcAplication getInstance() {
		if (ccAplication == null) {
			synchronized (CcAplication.class) {
				if (ccAplication == null) {
					ccAplication = new CcAplication();
				}
			}
		}
		return ccAplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 先设置包名，再创建目录
		CcFilePath.setPackageName(getPackageName());
		// 建立缓存文件目录
		initCache();
		// 先建立缓存目录,在开启异常监听线程
		Thread.setDefaultUncaughtExceptionHandler(new CcExceptionHandler(
				CcFilePath.getPathException(), null));
	}

	/**
	 * 初始化缓存目录
	 * 
	 * @version 更新时间:2014年9月2日下午3:05:51
	 */
	private void initCache() {
		// 创建文件缓存目录
		File pathFile = new File(CcFilePath.getPathFile());
		if (!pathFile.exists()) {
			CcLog.e(getInstance(), "创建缓存文件夹:" + CcFilePath.getPathFile());
			pathFile.mkdirs();
		}
		// 创建图片缓存目录
		File pathImg = new File(CcFilePath.getPathImg());
		if (!pathImg.exists()) {
			pathImg.mkdirs();
		}
		// 创建APK缓存目录
		File pathApk = new File(CcFilePath.getPathApk());
		if (!pathApk.exists()) {
			pathApk.mkdirs();
		}
		// 创建异常缓存目录
		File pathException = new File(CcFilePath.getPathException());
		if (!pathException.exists()) {
			pathException.mkdirs();
		}
	}
}
