package cc.android.http;

/**
 * Http字符串响应监听器
 * 
 *
 */
public abstract class CcStringHttpResponseListener extends
		CcHttpResponseListener {

	/**
	 * 构造.
	 */
	public CcStringHttpResponseListener() {
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
	public abstract void onSuccess(int statusCode, String content);

	/**
	 * 成功消息.
	 *
	 * @param statusCode
	 *            the status code
	 * @param content
	 *            the content
	 */
	public void sendSuccessMessage(int statusCode, String content) {
		sendMessage(obtainMessage(CcHttpClient.SUCCESS_MESSAGE, new Object[] {
				statusCode, content }));
	}

}
