package cc.android.http;

import android.content.Context;

/**
 * 描述：Http执行工具类，可处理get，post，以及异步处理文件的上传下载
 * 
 *
 */
public class CcHttpUtil {

	/** 上下文. */
	private Context mContext;

	/** 实例话对象. */
	private CcHttpClient client = null;

	/** 工具类. */
	private static CcHttpUtil mAbHttpUtil = null;

	/**
	 * 描述：获取实例.
	 *
	 * @param context
	 *            the context
	 * @return single instance of AbHttpUtil
	 */
	public static CcHttpUtil getInstance(Context context) {
		if (null == mAbHttpUtil) {
			mAbHttpUtil = new CcHttpUtil(context);
		}

		return mAbHttpUtil;
	}

	/**
	 * 初始化AbHttpUtil.
	 *
	 * @param context
	 *            the context
	 */
	private CcHttpUtil(Context context) {
		super();
		this.mContext = context;
		this.client = new CcHttpClient(this.mContext);
	}

	/**
	 * 描述：无参数的get请求.
	 *
	 * @param url
	 *            the url
	 * @param responseListener
	 *            the response listener
	 */
	public void get(String url, CcHttpResponseListener responseListener) {
		client.get(url, responseListener);
	}

	/**
	 * 描述：带参数的get请求.
	 *
	 * @param url
	 *            the url
	 * @param params
	 *            the params
	 * @param responseListener
	 *            the response listener
	 */
	public void get(String url, CcRequestParams params,
			CcHttpResponseListener responseListener) {
		client.get(url, params, responseListener);
	}

	/**
	 * 
	 * 描述：下载数据使用，会返回byte数据(下载文件或图片).
	 *
	 * @param url
	 *            the url
	 * @param responseListener
	 *            the response listener
	 */
	public void get(String url, CcBinaryHttpResponseListener responseListener) {
		client.get(url, responseListener);
	}

	/**
	 * 描述：文件下载的get.
	 *
	 * @param url
	 *            the url
	 * @param params
	 *            the params
	 * @param responseListener
	 *            the response listener
	 */
	public void get(String url, CcRequestParams params,
			CcFileHttpResponseListener responseListener) {
		client.get(url, params, responseListener);
	}

	/**
	 * 描述：无参数的post请求.
	 *
	 * @param url
	 *            the url
	 * @param responseListener
	 *            the response listener
	 */
	public void post(String url, CcHttpResponseListener responseListener) {
		client.post(url, responseListener);
	}

	/**
	 * 描述：带参数的post请求.
	 *
	 * @param url
	 *            the url
	 * @param params
	 *            the params
	 * @param responseListener
	 *            the response listener
	 */
	public void post(String url, CcRequestParams params,
			CcHttpResponseListener responseListener) {
		client.post(url, params, responseListener);
	}

	/**
	 * 描述：文件下载的post.
	 *
	 * @param url
	 *            the url
	 * @param params
	 *            the params
	 * @param responseListener
	 *            the response listener
	 */
	public void post(String url, CcRequestParams params,
			CcFileHttpResponseListener responseListener) {
		client.post(url, params, responseListener);
	}

	/**
	 * 描述：设置连接超时时间(第一次请求前设置).
	 *
	 * @param timeout
	 *            毫秒
	 */
	public void setTimeout(int timeout) {
		client.setTimeout(timeout);
	}

	/**
	 * 打开ssl 自签名(第一次请求前设置).
	 * 
	 * @param enabled
	 */
	public void setEasySSLEnabled(boolean enabled) {
		client.setOpenEasySSL(enabled);
	}

	/**
	 * 设置编码(第一次请求前设置).
	 * 
	 * @param encode
	 */
	public void setEncode(String encode) {
		client.setEncode(encode);
	}

	/**
	 * 设置用户代理(第一次请求前设置).
	 * 
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent) {
		client.setUserAgent(userAgent);
	}

	/**
	 * 关闭HttpClient 当HttpClient实例不再需要是，确保关闭connection manager，以释放其系统资源
	 */
	@SuppressWarnings("unused")
	private void shutdownHttpClient() {
		if (client != null) {
			client.shutdown();
		}
	}

}
