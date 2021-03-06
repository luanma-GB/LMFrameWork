package cc.android.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import cc.android.utils.CcImageUtil;
import cc.android.utils.CcLog;
import cc.android.utils.CcStrUtil;
import cc.android.utils.CcViewUtil;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn 名称：AbImageDownloader.java 描述：下载图片并显示的工具类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2011-12-10 上午10:10:53
 */
public class CcImageDownloader {

	/** Context. */
	private static Context mContext = null;

	/** 显示的图片的宽. */
	private int width;

	/** 显示的图片的高. */
	private int height;

	/** 图片的处理类型（剪切或者缩放到指定大小）. */
	private int type = CcImageUtil.ORIGINALIMG;

	/** 显示为下载中的图片. */
	private Drawable loadingImage;

	/** 显示为下载中的View. */
	private View loadingView;

	/** 显示下载失败的图片. */
	private Drawable errorImage;

	/** 图片未找到的图片. */
	private Drawable noImage;

	/** 下载用的线程池. 可以根据实际需求改变下载策略 */
	private CcImageDownloadPool mAbImageDownloadPool = null;

	/**
	 * 构造图片下载器.
	 *
	 * @param context
	 *            the context
	 */
	@SuppressWarnings("static-access")
	public CcImageDownloader(Context context) {
		this.mContext = context;
		this.mAbImageDownloadPool = CcImageDownloadPool.getInstance(mContext);
	}

	/**
	 * 显示这个图片,解决了列表问题. 列表问题：滑动过程中，getView的imageView会重复利用，导致图片会串位
	 * 
	 * @param imageView
	 *            显得的View
	 * @param url
	 *            the url
	 * @return
	 */
	public void display(final ImageView imageView, String url) {
		if (CcStrUtil.isEmpty(url)) {
			if (noImage != null) {
				if (loadingView != null) {
					loadingView.setVisibility(View.INVISIBLE);
					imageView.setVisibility(View.VISIBLE);
				}
				imageView.setImageDrawable(noImage);
			}
			return;
		}

		// 设置下载项
		final CcImageDownloadItem item = new CcImageDownloadItem();
		// 设置显示的大小
		item.width = CcViewUtil.scale(mContext, width);
		item.height = CcViewUtil.scale(mContext, height);
		// 设置为缩放
		item.type = type;
		item.imageUrl = url;
		final String cacheKey = CcImageCache.getCacheKey(item.imageUrl,
				item.width, item.height, item.type);
		item.bitmap = CcImageCache.getBitmapFromCache(cacheKey);
		// if(D) Log.d(TAG, "缓存中获取的"+cacheKey+":"+item.bitmap);

		// 设置标记
		imageView.setTag(url);

		if (item.bitmap == null) {

			// 先显示加载中
			if (loadingView != null) {
				loadingView.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.INVISIBLE);
			} else if (loadingImage != null) {
				imageView.setImageDrawable(loadingImage);
			}

			// 下载完成后更新界面
			item.setListener(new CcImageDownloadListener() {

				@Override
				public void update(Bitmap bitmap, String imageUrl) {

					// 未设置加载中的图片，并且设置了隐藏的View
					if (loadingView != null
							&& imageUrl.equals(imageView.getTag())) {
						loadingView.setVisibility(View.INVISIBLE);
						imageView.setVisibility(View.VISIBLE);
					}
					// 要判断这个imageView的url有变化，如果没有变化才set，
					// 有变化就取消，解决列表的重复利用View的问题
					if (bitmap != null && imageUrl.equals(imageView.getTag())) {
						CcLog.d(mContext, "图片下载，设置:" + imageUrl);
						imageView.setImageBitmap(bitmap);
					} else {
						if (errorImage != null
								&& imageUrl.equals(imageView.getTag())) {
							imageView.setImageDrawable(errorImage);
						}
					}
				}
			});
			CcLog.d(mContext, "图片下载，执行:" + url);
			mAbImageDownloadPool.execute(item);
		} else {
			if (loadingView != null) {
				loadingView.setVisibility(View.INVISIBLE);
				imageView.setVisibility(View.VISIBLE);
			}
			imageView.setImageBitmap(item.bitmap);
		}

	}

	/**
	 * 描述：设置下载中的图片.
	 *
	 * @param resID
	 *            the new loading image
	 */
	public void setLoadingImage(int resID) {
		this.loadingImage = mContext.getResources().getDrawable(resID);
	}

	/**
	 * 描述：设置下载中的View，优先级高于setLoadingImage.
	 *
	 * @param view
	 *            放在ImageView的上边或者下边的View
	 */
	public void setLoadingView(View view) {
		this.loadingView = view;
	}

	/**
	 * 描述：设置下载失败的图片.
	 *
	 * @param resID
	 *            the new error image
	 */
	public void setErrorImage(int resID) {
		this.errorImage = mContext.getResources().getDrawable(resID);
	}

	/**
	 * 描述：设置未找到的图片.
	 *
	 * @param resID
	 *            the new no image
	 */
	public void setNoImage(int resID) {
		this.noImage = mContext.getResources().getDrawable(resID);
	}

	/**
	 * 获取图片宽度.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 描述：设置图片的宽度.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(int width) {
		this.width = CcViewUtil.scale(mContext, width);
	}

	/**
	 * 获取图片的高度.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 描述：设置图片的高度.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(int height) {
		this.height = CcViewUtil.scale(mContext, height);
	}

	/**
	 * 获取图片处理类型.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * 描述：图片的处理类型（剪切或者缩放到指定大小，参考AbConstant类）.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(int type) {
		this.type = type;
	}

}
