package cc.android.bitmap;

import android.graphics.Bitmap;

/**
 * 图片下载的回调接口
 * 
 *
 */
public interface CcImageDownloadListener {

	/**
	 * 描述：更新视图.
	 *
	 * @param bitmap
	 *            下载返回的Bitmap
	 * @param imageUrl
	 *            原互联网下载地址
	 */
	public void update(Bitmap bitmap, String imageUrl);

}
