package cc.android.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import cc.android.bitmap.CcFileCache;
import cc.android.path.CcFilePath;

/**
 * 文件操作类
 * 
 *
 */
public class CcFileUtil {
	/** MB 单位B. */
	private static int MB = 1024 * 1024;
	/** 剩余空间大于200M才使用缓存. */
	private static int freeSdSpaceNeededToCache = 200 * MB;

	/**
	 * 获取文件名（.后缀），外链模式和通过网络获取.
	 *
	 * @param url
	 *            文件地址
	 * @param response
	 *            the response
	 * @return 文件名
	 */
	public static String getCacheFileNameFromUrl(String url,
			HttpResponse response) {
		if (CcStrUtil.isEmpty(url)) {
			return null;
		}
		String name = null;
		try {
			// 获取后缀
			String suffix = getMIMEFromUrl(url, response);
			if (CcStrUtil.isEmpty(suffix)) {
				suffix = ".ab";
			}
			name = CcMd5.MD5(url) + suffix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * 获取文件名（.后缀），外链模式和通过网络获取.
	 *
	 * @param url
	 *            文件地址
	 * @param connection
	 *            the connection
	 * @return 文件名
	 */
	public static String getCacheFileNameFromUrl(String url,
			HttpURLConnection connection) {
		if (CcStrUtil.isEmpty(url)) {
			return null;
		}
		String name = null;
		try {
			// 获取后缀
			String suffix = getMIMEFromUrl(url, connection);
			if (CcStrUtil.isEmpty(suffix)) {
				suffix = ".ab";
			}
			name = CcMd5.MD5(url) + suffix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * 获取文件后缀，本地.
	 *
	 * @param url
	 *            文件地址
	 * @param connection
	 *            the connection
	 * @return 文件后缀
	 */
	public static String getMIMEFromUrl(String url, HttpURLConnection connection) {

		if (CcStrUtil.isEmpty(url)) {
			return null;
		}
		String suffix = null;
		try {
			// 获取后缀
			if (url.lastIndexOf(".") != -1) {
				suffix = url.substring(url.lastIndexOf("."));
				if (suffix.indexOf("/") != -1 || suffix.indexOf("?") != -1
						|| suffix.indexOf("&") != -1) {
					suffix = null;
				}
			}
			if (CcStrUtil.isEmpty(suffix)) {
				// 获取文件名 这个效率不高
				String fileName = getRealFileName(connection);
				if (fileName != null && fileName.lastIndexOf(".") != -1) {
					suffix = fileName.substring(fileName.lastIndexOf("."));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return suffix;
	}

	/**
	 * 获取文件后缀，本地和网络.
	 *
	 * @param url
	 *            文件地址
	 * @param response
	 *            the response
	 * @return 文件后缀
	 */
	public static String getMIMEFromUrl(String url, HttpResponse response) {

		if (CcStrUtil.isEmpty(url)) {
			return null;
		}
		String mime = null;
		try {
			// 获取后缀
			if (url.lastIndexOf(".") != -1) {
				mime = url.substring(url.lastIndexOf("."));
				if (mime.indexOf("/") != -1 || mime.indexOf("?") != -1
						|| mime.indexOf("&") != -1) {
					mime = null;
				}
			}
			if (CcStrUtil.isEmpty(mime)) {
				// 获取文件名 这个效率不高
				String fileName = getRealFileName(response);
				if (fileName != null && fileName.lastIndexOf(".") != -1) {
					mime = fileName.substring(fileName.lastIndexOf("."));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mime;
	}

	/**
	 * 获取真实文件名（xx.后缀），通过网络获取.
	 *
	 * @param response
	 *            the response
	 * @return 文件名
	 */
	public static String getRealFileName(HttpResponse response) {
		String name = null;
		try {
			if (response == null) {
				return name;
			}
			// 获取文件名
			Header[] headers = response.getHeaders("content-disposition");
			for (int i = 0; i < headers.length; i++) {
				Matcher m = Pattern.compile(".*filename=(.*)").matcher(
						headers[i].getValue());
				if (m.find()) {
					name = m.group(1).replace("\"", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			CcLog.e(CcFileUtil.class, "网络上获取文件名失败");
		}
		return name;
	}

	/**
	 * 获取真实文件名（xx.后缀），通过网络获取.
	 * 
	 * @param connection
	 *            连接
	 * @return 文件名
	 */
	@SuppressLint("DefaultLocale")
	public static String getRealFileName(HttpURLConnection connection) {
		String name = null;
		try {
			if (connection == null) {
				return name;
			}
			if (connection.getResponseCode() == 200) {
				for (int i = 0;; i++) {
					String mime = connection.getHeaderField(i);
					if (mime == null) {
						break;
					}
					// "Content-Disposition","attachment; filename=1.txt"
					// Content-Length
					if ("content-disposition".equals(connection
							.getHeaderFieldKey(i).toLowerCase())) {
						Matcher m = Pattern.compile(".*filename=(.*)").matcher(
								mime.toLowerCase());
						if (m.find()) {
							return m.group(1).replace("\"", "");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			CcLog.e(CcFileUtil.class, "网络上获取文件名失败");
		}
		return name;
	}

	/**
	 * 获取文件名（不含后缀）.
	 *
	 * @param url
	 *            文件地址
	 * @return 文件名
	 */
	public static String getCacheFileNameFromUrl(String url) {
		if (CcStrUtil.isEmpty(url)) {
			return null;
		}
		String name = null;
		try {
			name = CcMd5.MD5(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * 初始化缓存.
	 *
	 * @return true, if successful
	 */
	public static boolean initFileCache() {

		try {

			CcFileCache.cacheSize = 0;

			if (!CcSDUtil.isCanUseSD()) {
				return false;
			}
			File fileDirectory = new File(CcFilePath.getPathImg());
			File[] files = fileDirectory.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				CcFileCache.cacheSize += files[i].length();
				CcFileCache.addFileToCache(files[i].getName(), files[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 释放部分文件， 当文件总大小大于规定的CcFileCache.
	 * maxCacheSize或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
	 * 那么删除40%最近没有被使用的文件
	 *
	 * @return true, if successful
	 */
	public static boolean freeCacheFiles() {

		try {
			if (!CcSDUtil.isCanUseSD()) {
				return false;
			}

			File fileDirectory = new File(CcFilePath.getPathImg());
			File[] files = fileDirectory.listFiles();
			if (files == null) {
				return true;
			}
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Arrays.sort(files, new FileLastModifSort());
			for (int i = 0; i < removeFactor; i++) {
				CcFileCache.cacheSize -= files[i].length();
				files[i].delete();
				CcFileCache.removeFileFromCache(files[i].getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 删除所有缓存文件.
	 *
	 * @return true, if successful
	 */
	public static boolean removeAllFileCache() {
		try {
			if (!CcSDUtil.isCanUseSD()) {
				return false;
			}
			File fileDirectory = new File(CcFilePath.getPathImg());
			File[] files = fileDirectory.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据文件的最后修改时间进行排序.
	 */
	public static class FileLastModifSort implements Comparator<File> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() == arg1.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * 描述：通过文件的网络地址从SD卡中读取图片，如果SD中没有则自动下载并保存.
	 * 
	 * @param url
	 *            文件的网络地址
	 * @param type
	 *            图片的处理类型（剪切或者缩放到指定大小，参考CcConstant类） 如果设置为原图，则后边参数无效，得到原图
	 * @param width
	 *            新图片的宽
	 * @param height
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getBitmapFromSDCache(String url, int type, int width,
			int height) {
		Bitmap bitmap = null;
		try {
			if (CcStrUtil.isEmpty(url)) {
				return null;
			}

			// SD卡不存在 或者剩余空间不足了就不缓存到SD卡了
			if (!CcSDUtil.isCanUseSD()
					|| freeSdSpaceNeededToCache < freeSpaceOnSD()) {
				bitmap = getBitmapFormURL(url, type, width, height);
				return bitmap;
			}

			if (type != CcImageUtil.ORIGINALIMG && (width <= 0 || height <= 0)) {
				throw new IllegalArgumentException("缩放和裁剪图片的宽高设置不能小于0");
			}
			// 下载文件，如果不存在就下载，存在直接返回地址
			String downFilePath = downFileToSD(url, CcFilePath.getPathImg());
			if (downFilePath != null) {
				// 获取图片
				return getBitmapFromSD(new File(downFilePath), type, width,
						height);
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 计算sdcard上的剩余空间.
	 *
	 * @return the int
	 */
	@SuppressWarnings("deprecation")
	public static int freeSpaceOnSD() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	/**
	 * 描述：根据URL从互连网获取图片.
	 * 
	 * @param url
	 *            要下载文件的网络地址
	 * @param type
	 *            图片的处理类型（剪切或者缩放到指定大小，参考CcConstant类）
	 * @param newWidth
	 *            新图片的宽
	 * @param newHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getBitmapFormURL(String url, int type, int newWidth,
			int newHeight) {
		Bitmap bit = null;
		try {
			bit = CcImageUtil.getBitmapFormURL(url, type, newWidth, newHeight);
		} catch (Exception e) {
			CcLog.d(CcFileUtil.class, "下载图片异常：" + e.getMessage());
		}
		return bit;
	}

	/**
	 * 下载网络文件到SD卡中.如果SD中存在同名文件将不再下载
	 *
	 * @param url
	 *            要下载文件的网络地址
	 * @param dirPath
	 *            the dir path
	 * @return 下载好的本地文件地址
	 */
	public static String downFileToSD(String url, String dirPath) {
		InputStream in = null;
		FileOutputStream fileOutputStream = null;
		HttpURLConnection connection = null;
		String downFilePath = null;
		File file = null;
		try {
			if (!CcSDUtil.isCanUseSD()) {
				return null;
			}
			// 先判断SD卡中有没有这个文件，不比较后缀部分比较
			String fileNameNoMIME = getCacheFileNameFromUrl(url);
			File parentFile = new File(dirPath);
			File[] files = parentFile.listFiles();
			for (int i = 0; i < files.length; ++i) {
				String fileName = files[i].getName();
				String name = fileName.substring(0, fileName.lastIndexOf("."));
				if (name.equals(fileNameNoMIME)) {
					// 文件已存在
					return files[i].getPath();
				}
			}
			URL mUrl = new URL(url);
			connection = (HttpURLConnection) mUrl.openConnection();
			connection.connect();
			// 获取文件名，下载文件
			String fileName = getCacheFileNameFromUrl(url, connection);
			file = new File(CcFilePath.getPathImg(), fileName);
			downFilePath = file.getPath();
			if (!file.exists()) {
				file.createNewFile();
			} else {
				// 文件已存在
				return file.getPath();
			}
			in = connection.getInputStream();
			fileOutputStream = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int temp = 0;
			while ((temp = in.read(b)) != -1) {
				fileOutputStream.write(b, 0, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			CcLog.e(CcFileUtil.class, "有文件下载出错了");
			// 检查文件大小,如果文件为0B说明网络不好没有下载成功，要将建立的空文件删除
			if (file != null) {
				file.delete();
			}
			file = null;
			downFilePath = null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (file != null) {
			// 加到缓存
			CcFileCache.addFileToCache(file.getName(), file);
		}

		return downFilePath;
	}

	/**
	 * 描述：通过文件的本地地址从SD卡读取图片.
	 *
	 * @param file
	 *            the file
	 * @param type
	 *            图片的处理类型（剪切或者缩放到指定大小，参考CcConstant类） 如果设置为原图，则后边参数无效，得到原图
	 * @param newWidth
	 *            新图片的宽
	 * @param newHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getBitmapFromSD(File file, int type, int newWidth,
			int newHeight) {
		Bitmap bit = null;
		try {
			// SD卡是否存在
			if (!CcSDUtil.isCanUseSD()) {
				return null;
			}

			if (type != CcImageUtil.ORIGINALIMG
					&& (newWidth <= 0 || newHeight <= 0)) {
				throw new IllegalArgumentException("缩放和裁剪图片的宽高设置不能小于0");
			}

			// 文件是否存在
			if (!file.exists()) {
				return null;
			}

			// 文件存在
			if (type == CcImageUtil.CUTIMG) {
				bit = CcImageUtil.cutImg(file, newWidth, newHeight);
			} else if (type == CcImageUtil.SCALEIMG) {
				bit = CcImageUtil.scaleImg(file, newWidth, newHeight);
			} else {
				bit = CcImageUtil.originalImg(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bit;
	}

	/**
	 * 描述：获取src中的图片资源.
	 *
	 * @param src
	 *            图片的src路径，如（“image/arrow.png”）
	 * @return Bitmap 图片
	 */
	public static Bitmap getBitmapFormSrc(String src) {
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeStream(CcFileUtil.class
					.getResourceAsStream(src));
		} catch (Exception e) {
			CcLog.d(CcFileUtil.class, "获取图片异常：" + e.getMessage());
		}
		return bit;
	}
}
