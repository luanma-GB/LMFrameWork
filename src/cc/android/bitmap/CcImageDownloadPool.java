package cc.android.bitmap;

import java.util.concurrent.Executor;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cc.android.task.CcThreadFactory;
import cc.android.utils.CcFileUtil;
import cc.android.utils.CcLog;
import cc.android.utils.CcStrUtil;

/**
 * 线程池图片下载,用andbase线程池
 * 
 *
 */
public class CcImageDownloadPool {

	/** 上下文 */
	private Context mContext = null;

	/** The image download. 单例对象 */
	private static CcImageDownloadPool imageDownload = null;

	/** 线程执行器. */
	public static Executor mExecutorService = null;

	/** 下载完成后的消息句柄. */
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			CcImageDownloadItem item = (CcImageDownloadItem) msg.obj;
			item.getListener().update(item.bitmap, item.imageUrl);
		}
	};

	/**
	 * 构造图片下载器.
	 * 
	 * @param context
	 */
	protected CcImageDownloadPool(Context context) {
		this.mContext = context;
		mExecutorService = CcThreadFactory.getExecutorService();
	}

	/**
	 * 单例构造图片下载器.
	 * 
	 * @param context
	 * @return single instance of AbImageDownloadPool
	 */
	public static CcImageDownloadPool getInstance(Context context) {
		if (imageDownload == null) {
			imageDownload = new CcImageDownloadPool(context);
		}
		return imageDownload;
	}

	/**
	 * 执行下载.
	 * 
	 * @param item
	 *            the item
	 */
	public void execute(final CcImageDownloadItem item) {

		String imageUrl = item.imageUrl;
		if (CcStrUtil.isEmpty(imageUrl)) {
			CcLog.d(mContext, "图片URL为空，请先判断");
		} else {
			imageUrl = imageUrl.trim();
		}

		// 从缓存中获取图片
		final String cacheKey = CcImageCache.getCacheKey(imageUrl, item.width,
				item.height, item.type);
		item.bitmap = CcImageCache.getBitmapFromCache(cacheKey);

		if (item.bitmap == null) {

			// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
			mExecutorService.execute(new Runnable() {
				public void run() {
					try {
						// 逻辑：判断这个任务是否有其他线程再执行，如果有等待，直到下载完成唤醒显示
						Runnable runnable = CcImageCache
								.getRunRunnableFromCache(cacheKey);
						if (runnable != null) {

							// 线程等待通知后显示
							// if(D) Log.d(TAG,
							// "线程等待:"+item.imageUrl+","+cacheKey);
							CcImageCache.addToWaitRunnableCache(cacheKey, this);
							synchronized (this) {
								this.wait();
							}
							// if(D) Log.d(TAG, "线程被唤醒:"+item.imageUrl);
							// 直接获取
							item.bitmap = CcImageCache
									.getBitmapFromCache(cacheKey);
						} else {
							// 增加下载中的线程记录
							// if(D) Log.d(TAG,
							// "从内存取或者下载:"+item.imageUrl+","+cacheKey);
							CcImageCache.addToRunRunnableCache(cacheKey, this);
							item.bitmap = CcFileUtil.getBitmapFromSDCache(
									item.imageUrl, item.type, item.width,
									item.height);
							// 增加到下载完成的缓存，删除下载中的记录和等待的记录，同时唤醒所有等待列表中key与其key相同的线程
							CcImageCache
									.addBitmapToCache(cacheKey, item.bitmap);

						}

					} catch (Exception e) {
						CcLog.d(mContext, "error:" + item.imageUrl);
						e.printStackTrace();
					} finally {
						if (item.getListener() != null) {
							Message msg = handler.obtainMessage();
							msg.obj = item;
							handler.sendMessage(msg);
						}
					}
				}
			});

		} else {
			// if(D) Log.d(TAG, "从内存缓存中得到图片:"+cacheKey+","+item.bitmap);
			if (item.getListener() != null) {
				Message msg = handler.obtainMessage();
				msg.obj = item;
				handler.sendMessage(msg);
			}
		}

	}

}
