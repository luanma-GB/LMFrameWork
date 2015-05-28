package cc.android.utils;

import android.content.Context;
import android.util.Log;

/**
 * 自定义信息打印
 * 
 *
 */
public class CcLog {
	/** debug开关 */
	private static boolean D = true;

	/** info开关 */
	private static boolean I = true;

	/** error开关 */
	private static boolean E = true;

	public static void setDebug(boolean d) {
		D = d;
	}

	public static void setInfo(boolean i) {
		I = i;
	}

	public static void setError(boolean e) {
		E = e;
	}

	/**
	 * 设置每一个的状态
	 * 
	 * @version 更新时间:2014年9月1日下午4:50:39
	 * @param debug
	 * @param info
	 * @param error
	 */
	public static void setItemState(boolean debug, boolean info, boolean error) {
		setDebug(debug);
		setInfo(info);
		setError(error);
	}

	/**
	 * 设置所有打印信息状态
	 * 
	 * @version 更新时间:2014年9月1日下午4:28:42
	 * @param open
	 */
	public static void setAllState(boolean open) {
		setDebug(open);
		setInfo(open);
		setError(open);
	}

	// ***************************************debug***************************************//
	/**
	 * debug日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:31:31
	 * @param tag
	 *            打印的TAG
	 * @param message
	 *            打印的信息
	 */
	public static void d(String tag, String message) {
		if (D) {
			Log.d(tag, message);
		}
	}

	/**
	 * debug日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:35:24
	 * @param context
	 *            上下文
	 * @param message
	 *            打印的信息
	 */
	public static void d(Context context, String message) {
		String tag = context.getClass().getSimpleName();
		d(tag, message);
	}

	/**
	 * debug日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:36:39
	 * @param clazz
	 *            类
	 * @param message
	 *            打印的信息
	 */
	public static void d(Class<?> clazz, String message) {
		String tag = clazz.getSimpleName();
		d(tag, message);
	}

	// ***************************************info***************************************//
	/**
	 * info日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:37:29
	 * @param tag
	 *            打印的TAG
	 * @param message
	 *            打印的信息
	 */
	public static void i(String tag, String message) {
		if (I) {
			Log.i(tag, message);
		}
	}

	/**
	 * info日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:37:58
	 * @param context
	 *            上下文
	 * @param message
	 *            打印的信息
	 */
	public static void i(Context context, String message) {
		String tag = context.getClass().getSimpleName();
		i(tag, message);
	}

	/**
	 * info日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:39:14
	 * @param clazz
	 *            类
	 * @param message
	 *            打印的信息
	 */
	public static void i(Class<?> clazz, String message) {
		String tag = clazz.getSimpleName();
		i(tag, message);
	}

	// ***************************************error***************************************//
	/**
	 * error日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:39:40
	 * @param tag
	 *            打印的TAG
	 * @param message
	 *            打印的信息
	 */
	public static void e(String tag, String message) {
		if (E) {
			Log.e(tag, message);
		}
	}

	/**
	 * error日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:40:26
	 * @param context
	 *            上下文
	 * @param message
	 *            打印的信息
	 */
	public static void e(Context context, String message) {
		String tag = context.getClass().getSimpleName();
		e(tag, message);
	}

	/**
	 * error日志
	 * 
	 * @version 更新时间:2014年9月1日下午4:40:43
	 * @param clazz
	 *            类
	 * @param message
	 *            打印的信息
	 */
	public static void e(Class<?> clazz, String message) {
		String tag = clazz.getSimpleName();
		e(tag, message);
	}
	// ***************************************----***************************************//
}
