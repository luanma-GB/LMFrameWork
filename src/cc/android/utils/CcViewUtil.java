package cc.android.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cc.android.global.CcAppConfig;

public class CcViewUtil {
	/**
	 * 测量这个view，最后通过getMeasuredWidth()获取宽度和高度
	 * 
	 * @version 更新时间:2014年9月2日下午5:34:44
	 * @param v
	 *            要测量的view
	 */
	public static void measureView(View v) {
		ViewGroup.LayoutParams p = v.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		v.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scale(Context context, float value) {
		DisplayMetrics mDisplayMetrics = CcAppUtil.getDisplayMetrics(context);
		return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels,
				value);
	}

	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param displayWidth
	 *            the display width
	 * @param displayHeight
	 *            the display height
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scale(int displayWidth, int displayHeight, float pxValue) {
		float scale = 1;
		try {
			float scaleWidth = (float) displayWidth / CcAppConfig.uiWidth;
			float scaleHeight = (float) displayHeight / CcAppConfig.uiHeight;
			scale = Math.min(scaleWidth, scaleHeight);
		} catch (Exception e) {
		}
		return Math.round(pxValue * scale + 0.5f);
	}

	/**
	 * 描述：dip转换为px.
	 *
	 * @param context
	 *            the context
	 * @param dipValue
	 *            the dip value
	 * @return px值
	 */
	public static float dip2px(Context context, float dipValue) {
		DisplayMetrics mDisplayMetrics = CcAppUtil.getDisplayMetrics(context);
		return applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
				mDisplayMetrics);
	}

	/**
	 * 描述：px转换为dip.
	 *
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return dip值
	 */
	public static float px2dip(Context context, float pxValue) {
		DisplayMetrics mDisplayMetrics = CcAppUtil.getDisplayMetrics(context);
		return pxValue / mDisplayMetrics.density;
	}

	/**
	 * 描述：sp转换为px.
	 *
	 * @param context
	 *            the context
	 * @param spValue
	 *            the sp value
	 * @return sp值
	 */
	public static float sp2px(Context context, float spValue) {
		DisplayMetrics mDisplayMetrics = CcAppUtil.getDisplayMetrics(context);
		return applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				mDisplayMetrics);
	}

	/**
	 * 描述：px转换为sp.
	 *
	 * @param context
	 *            the context
	 * @param spValue
	 *            the sp value
	 * @return sp值
	 */
	public static float px2sp(Context context, float pxValue) {
		DisplayMetrics mDisplayMetrics = CcAppUtil.getDisplayMetrics(context);
		return pxValue / mDisplayMetrics.scaledDensity;
	}

	/**
	 * 缩放文字大小,这样设置的好处是文字的大小不和密度有关， 能够使文字大小在不同的屏幕上显示比例正确
	 * 
	 * @param textView
	 *            button
	 * @param sizePixels
	 *            px值
	 * @return
	 */
	public static void setTextSize(TextView textView, float sizePixels) {
		float scaledSize = scale(textView.getContext(), sizePixels);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize);
	}

	/**
	 * TypedValue官方源码中的算法，任意单位转换为PX单位
	 * 
	 * @param unit
	 *            TypedValue.COMPLEX_UNIT_DIP
	 * @param value
	 *            对应单位的值
	 * @param metrics
	 *            密度
	 * @return px值
	 */
	public static float applyDimension(int unit, float value,
			DisplayMetrics metrics) {
		switch (unit) {
		case TypedValue.COMPLEX_UNIT_PX:
			return value;
		case TypedValue.COMPLEX_UNIT_DIP:
			return value * metrics.density;
		case TypedValue.COMPLEX_UNIT_SP:
			return value * metrics.scaledDensity;
		case TypedValue.COMPLEX_UNIT_PT:
			return value * metrics.xdpi * (1.0f / 72);
		case TypedValue.COMPLEX_UNIT_IN:
			return value * metrics.xdpi;
		case TypedValue.COMPLEX_UNIT_MM:
			return value * metrics.xdpi * (1.0f / 25.4f);
		}
		return 0;
	}

	/**
	 * 设置PX padding.
	 *
	 * @param view the view
	 * @param left the left padding in pixels
     * @param top the top padding in pixels
     * @param right the right padding in pixels
     * @param bottom the bottom padding in pixels
	 */
	public static void setPadding(View view, int left,
			int top, int right, int bottom) {
		int scaledLeft = scale(view.getContext(), left);
		int scaledTop = scale(view.getContext(), top);
		int scaledRight = scale(view.getContext(), right);
		int scaledBottom = scale(view.getContext(), bottom);
		view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
	}
	/**
	 * @author luanma
	 * Todo  获取listview的高度
	 * @param Listview  listview
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
