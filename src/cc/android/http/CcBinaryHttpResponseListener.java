package cc.android.http;

/**
 * Http二进制响应监听器
 * 
 *
 */
public abstract class CcBinaryHttpResponseListener extends
		CcHttpResponseListener {

	/**
	 * 构造.
	 */
	public CcBinaryHttpResponseListener() {
		super();
	}

	/**
	 * 描述：获取数据成功会调用这里.
	 *
	 * @param statusCode
	 *            the status code
	 * @param content
	 *            the content
	 */
	public abstract void onSuccess(int statusCode, byte[] content);

	/**
	 * 成功消息.
	 *
	 * @param statusCode
	 *            the status code
	 * @param content
	 *            the content
	 */
	public void sendSuccessMessage(int statusCode, byte[] content) {
		sendMessage(obtainMessage(CcHttpClient.SUCCESS_MESSAGE, new Object[] {
				statusCode, content }));
	}

}
