package cc.android.http;

import java.io.File;

import cc.android.path.CcFilePath;
import cc.android.utils.CcSDUtil;

/**
 * Http文件响应监听器
 * 
 *
 */
public abstract class CcFileHttpResponseListener extends CcHttpResponseListener {

	/** 当前缓存文件. */
	private File mFile;

	/**
	 * 下载文件的构造,用默认的缓存方式.
	 *
	 * @param url
	 *            the url
	 */
	public CcFileHttpResponseListener(String url) {
		super();
	}

	/**
	 * 默认的构造.
	 */
	public CcFileHttpResponseListener() {
		super();
	}

	/**
	 * 下载文件的构造,指定缓存文件名称.
	 * 
	 * @param file
	 *            缓存文件名称
	 */
	public CcFileHttpResponseListener(File file) {
		super();
		this.mFile = file;
	}

	/**
	 * 描述：下载文件成功会调用这里.
	 *
	 * @param statusCode
	 *            the status code
	 * @param file
	 *            the file
	 */
	public void onSuccess(int statusCode, File file) {
	};

	/**
	 * 描述：多文件上传成功调用.
	 *
	 * @param statusCode
	 *            the status code
	 */
	public void onSuccess(int statusCode) {
	};

	/**
	 * 描述：文件上传下载失败调用.
	 *
	 * @param statusCode
	 *            the status code
	 * @param content
	 *            the content
	 * @param error
	 *            the error
	 */
	public abstract void onFailure(int statusCode, String content,
			Throwable error);

	/**
	 * 成功消息.
	 *
	 * @param statusCode
	 *            the status code
	 */
	public void sendSuccessMessage(int statusCode) {
		sendMessage(obtainMessage(CcHttpClient.SUCCESS_MESSAGE,
				new Object[] { statusCode }));
	}

	/**
	 * 失败消息.
	 *
	 * @param statusCode
	 *            the status code
	 * @param error
	 *            the error
	 */
	public void sendFailureMessage(int statusCode, Throwable error) {
		sendMessage(obtainMessage(CcHttpClient.FAILURE_MESSAGE, new Object[] {
				statusCode, error }));
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return mFile;
	}

	/**
	 * Sets the file.
	 *
	 * @param file
	 *            the new file
	 */
	public void setFile(File file) {
		this.mFile = file;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the file.
	 *
	 * @param name
	 *            the new file
	 */
	public void setFile(String name) {
		// 生成缓存文件
		if (CcSDUtil.isCanUseSD()) {
			File file = new File(CcFilePath.getPathFile() + name);
			setFile(file);
		}
	}

}
