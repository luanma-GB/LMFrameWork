package cc.android.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import cc.android.ccbase.R;
import cc.android.utils.CcPhoneUtil;
import cc.android.utils.CcViewUtil;

/**
 * 底部标题布局
 * 
 *
 */
public class CcBottomBar extends LinearLayout {
	/** 所属Activity */
	private Activity mActivity = null;
	/** 底部标题栏布局ID */
	public int mBottomBarID = 2;
	/** 全局的LayoutInflater对象 */
	public LayoutInflater mInflater = null;
	/** 下拉选择 */
	private PopupWindow mPopupWindow = null;
	/** 屏幕宽度 */
	public int mDiaplayWidth = 320;

	public CcBottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		ininBottomBar(context);
	}

	public CcBottomBar(Context context) {
		super(context);
		ininBottomBar(context);

	}

	/**
	 * 初始化
	 * 
	 * @version 更新时间:2014年9月3日上午10:23:04
	 * @param context
	 *            上下文
	 */
	public void ininBottomBar(Context context) {
		mActivity = (Activity) context;
		// 水平排列
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setId(mBottomBarID);
		this.setPadding(0, 0, 0, 0);
		this.setBackgroundColor(context.getResources().getColor(
				R.color.bottombar_color));
		mInflater = LayoutInflater.from(context);
		// 屏幕宽度
		mDiaplayWidth = CcPhoneUtil.getScreenInfo(mActivity).widthPixels;
	}

	/**
	 * 设置底部标题栏的背景图
	 * 
	 * @version 更新时间:2014年9月3日上午10:58:23
	 * @param res
	 *            背景图资源ID
	 */
	public void setBottomBarBackground(int res) {
		this.setBackgroundResource(res);
	}

	/**
	 * 设置底部标题栏的背景颜色
	 * 
	 * @version 更新时间:2014年9月3日上午10:58:44
	 * @param color
	 *            背景颜色
	 */
	public void setBottomBarBackgroundColor(int color) {
		this.setBackgroundColor(color);
	}

	/**
	 * 设置底部标题栏的背景图
	 * 
	 * @version 更新时间:2014年9月3日上午10:59:09
	 * @param d
	 *            需要设置的背景图
	 */
	@SuppressWarnings("deprecation")
	public void setBottomBarBackgroundDrawable(Drawable d) {
		this.setBackgroundDrawable(d);
	}

	/**
	 * 下拉菜单的的实现方法
	 * 
	 * @version 更新时间:2014年9月3日上午11:00:00
	 * @param parent
	 *            点击弹出popowindow的父容器
	 * @param view
	 *            弹出的view
	 * @param offsetMode
	 *            不填满的模式
	 */
	private void showWindow(View parent, View view, boolean offsetMode) {
		CcViewUtil.measureView(view);
		int popWidth = parent.getMeasuredWidth();
		if (view.getMeasuredWidth() > parent.getMeasuredWidth()) {
			popWidth = view.getMeasuredWidth();
		}
		int popMargin = this.getMeasuredHeight();

		if (offsetMode) {
			mPopupWindow = new PopupWindow(view, popWidth,
					LayoutParams.WRAP_CONTENT, true);
		} else {
			mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, true);
		}

		int[] location = new int[2];
		parent.getLocationInWindow(location);
		int startX = location[0] - parent.getLeft();
		if (startX + popWidth >= mDiaplayWidth) {
			startX = mDiaplayWidth - popWidth - 2;
		}
		// 使其聚集
		mPopupWindow.setFocusable(true);
		// 设置允许在外点击消失
		mPopupWindow.setOutsideTouchable(true);
		// 这个是为了点击"返回Back"也能使其消失，并且并不会影响你的背景
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(
				android.R.color.transparent));
		mPopupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT,
				startX, popMargin + 2);
	}

	/**
	 * 设置下拉的View
	 * 
	 * @version 更新时间:2014年9月3日上午11:02:02
	 * @param parent
	 *            点击的父容器
	 * @param view
	 *            弹出的view
	 */
	public void setDropDown(final View parent, final View view) {
		if (parent == null || view == null) {
			return;
		}
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showWindow(parent, view, true);
			}
		});

	}

	/**
	 * 设置底部标题栏界面显示
	 * 
	 * @version 更新时间:2014年9月3日上午11:02:43
	 * @param view
	 */
	public void setBottomView(View view) {
		removeAllViews();
		addView(view, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

	/**
	 * 用指定资源ID表示的View填充主界面
	 * 
	 * @version 更新时间:2014年9月3日上午11:03:31
	 * @param resId
	 *            指定的View的资源ID
	 */
	public void setBottomView(int resId) {
		setBottomView(mInflater.inflate(resId, null));
	}

}
