package cc.android.view;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.android.ccbase.R;
import cc.android.utils.CcStrUtil;
import cc.android.utils.CcViewUtil;

/**
 * 顶部标题栏
 * 
 *
 */
public class CcTitleBar extends LinearLayout {
	/** 坐边log距离屏幕左边距离 */
	private int mLogLeftMargin = 25;
	/** 右边布局距离屏幕右边距离 */
	private int mRightViewMargin = 25;
	/** 整体布局 */
	private RelativeLayout mTitleBarLayout = null;
	/** 上下文 */
	private Activity mActivity = null;
	/** 标题布局 */
	protected LinearLayout mTitleLayout = null;
	/** 显示标题文字的View */
	protected TextView mTitleTextBtn = null;
	/** 显示标题文字的小View */
	protected TextView mTitleSmallTextBtn = null;
	/** 左侧的Logo图标View */
	protected TextView mLogoView = null;
	/** 标题文本的对齐参数 */
	private RelativeLayout.LayoutParams mTitleLayoutParams = null;
	/** 右边布局的的对齐参数 */
	private RelativeLayout.LayoutParams mRightLayoutParams = null;
	/** 整体布局参数 */
	private RelativeLayout.LayoutParams mTitleBarParams = null;
	/** LinearLayout.LayoutParams */
	public LinearLayout.LayoutParams mLayoutParamsMM = null;
	/** 右边的View，可以自定义显示什么 */
	protected LinearLayout mRightLayout = null;
	/** 标题栏布局ID */
	public int mCcTitleBarID = 1;
	/** 全局的LayoutInflater对象 */
	public LayoutInflater mInflater;
	/** 下拉选择 */
	private PopupWindow mPopupWindow = null;

	public CcTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		ininTitleBar(context);
	}

	public CcTitleBar(Context context) {
		super(context);
		ininTitleBar(context);
	}

	/**
	 * 初始化
	 * 
	 * @version 更新时间:2014年9月3日下午6:15:10
	 * @param context
	 *            上下文
	 */
	@SuppressLint("InflateParams")
	public void ininTitleBar(Context context) {
		mActivity = (Activity) context;
		mLogLeftMargin = CcViewUtil.scale(context, 25);
		mRightViewMargin = CcViewUtil.scale(context, 25);
		// 水平排列
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setId(mCcTitleBarID);
		mInflater = LayoutInflater.from(context);
		// 右边布局参数
		mLayoutParamsMM = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// 左边Logo参数
		RelativeLayout.LayoutParams layoutParamsLogo = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsLogo.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
				RelativeLayout.TRUE);
		layoutParamsLogo.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);
		layoutParamsLogo.leftMargin = mLogLeftMargin;
		// 标题参数
		mTitleLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mTitleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);
		// 右边的对齐参数
		mRightLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		mRightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);
		mRightLayoutParams.rightMargin = mRightViewMargin;
		// 整体布局参数
		mTitleBarParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// 整体布局
		mTitleBarLayout = new RelativeLayout(context);
		// 标题布局
		mTitleLayout = new LinearLayout(context);
		mTitleLayout.setOrientation(LinearLayout.VERTICAL);
		mTitleLayout.setGravity(Gravity.CENTER);
		mTitleLayout.setPadding(0, 10, 0, 10);

		// 标题文字(大)
		mTitleTextBtn = new TextView(context);
		mTitleTextBtn.setTextColor(Color.rgb(255, 255, 255));
		mTitleTextBtn.setTextSize(20);
		mTitleTextBtn.setGravity(Gravity.CENTER);
		mTitleTextBtn.setIncludeFontPadding(false);
		mTitleTextBtn.setPadding(0, 0, 0, 0);
		mTitleTextBtn.setSingleLine();
		// 标题文字(小)
		mTitleSmallTextBtn = new TextView(context);
		mTitleSmallTextBtn.setTextColor(Color.rgb(255, 255, 255));
		mTitleSmallTextBtn.setTextSize(10);
		mTitleSmallTextBtn.setPadding(5, 0, 5, 0);
		mTitleSmallTextBtn.setGravity(Gravity.CENTER);
		mTitleSmallTextBtn.setSingleLine();
		mTitleSmallTextBtn.setVisibility(View.GONE);

		// 添加文字(大)到标题布局
		mTitleLayout.addView(mTitleTextBtn, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		// 添加文字(小)到标题布局
		mTitleLayout.addView(mTitleSmallTextBtn, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		LinearLayout view = (LinearLayout) LayoutInflater.from(context)
				.inflate(R.layout.logview, null);
		// 左侧图标
		mLogoView = (TextView) view.findViewById(R.id.logview);
		// 加右边的布局
		mRightLayout = new LinearLayout(context);
		mRightLayout.setOrientation(LinearLayout.HORIZONTAL);
		mRightLayout.setGravity(Gravity.RIGHT);
		mRightLayout.setPadding(0, 0, 0, 0);
		mRightLayout.setHorizontalGravity(Gravity.RIGHT);
		mRightLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		mRightLayout.setVisibility(View.GONE);
		// 设置上下居中
		mTitleBarLayout.setGravity(Gravity.CENTER_VERTICAL);
		// 添加标题
		mTitleBarLayout.addView(mTitleLayout, mTitleLayoutParams);
		view.removeView(mLogoView);
		view = null;
		// 添加左边logo
		mTitleBarLayout.addView(mLogoView, layoutParamsLogo);
		// 添加右边布局
		mTitleBarLayout.addView(mRightLayout, mRightLayoutParams);
		// 添加所有布局
		this.addView(mTitleBarLayout, mTitleBarParams);
		this.setGravity(Gravity.CENTER);
		this.setBackgroundResource(R.drawable.titlebar_bg);
		// 默认点击左边图标结束当前activity
		mLogoView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.finish();
			}
		});
	}

	/**
	 * 设置标题背景图
	 * 
	 * @version 更新时间:2014年9月2日下午5:20:01
	 * @param res
	 *            背景图资源
	 */
	public void setTitleBarBackground(int res) {
		this.setBackgroundResource(res);
	}

	/**
	 * 设置标题背景图
	 * 
	 * @version 更新时间:2014年9月2日下午5:20:49
	 * @param d
	 *            背景图资源
	 */
	@SuppressWarnings("deprecation")
	public void setTitleBarBackgroundDrawable(Drawable d) {
		this.setBackgroundDrawable(d);
	}

	/**
	 * 设置背景颜色
	 * 
	 * @version 更新时间:2014年9月2日下午5:25:43
	 * @param color
	 *            需要设置的颜色
	 */
	public void setTitleBarBackgroundColor(int color) {
		this.setBackgroundColor(color);
	}

	/**
	 * 描述：标题文字的对齐,需要在setTitleBarGravity之后设置才生效.
	 * 
	 * @version 更新时间:2014年9月2日下午5:26:12
	 * @param left
	 *            左边距
	 * @param top
	 *            上边距
	 * @param right
	 *            右边距
	 * @param bottom
	 *            下边距
	 */
	public void setTitleTextMargin(int left, int top, int right, int bottom) {
		mTitleLayoutParams.setMargins(left, top, right, bottom);
	}

	/**
	 * 设置文字大小
	 * 
	 * @version 更新时间:2014年9月2日下午5:28:35
	 * @param titleTextSize
	 *            需要设置的大小
	 */
	public void setTitleTextSize(int titleTextSize) {
		this.mTitleTextBtn.setTextSize(titleTextSize);
	}

	/**
	 * 获取大标题文本的Button
	 * 
	 * @version 更新时间:2014年9月2日下午5:47:49
	 * @return 标题显示控件
	 */
	public TextView getTitleTextButton() {
		return mTitleTextBtn;
	}

	/**
	 * 获取小标题文本的Button
	 * 
	 * @version 更新时间:2014年9月2日下午5:48:41
	 * @return 标题显示控件
	 */
	public TextView getTitleSmallTextButton() {
		return mTitleSmallTextBtn;
	}

	/**
	 * 获取最左边的logview
	 * 
	 * @version 更新时间:2014年9月2日下午5:49:06
	 * @return logview控件
	 */
	public TextView getLogoView() {
		return mLogoView;
	}

	/**
	 * 设置标题是否为粗体
	 * 
	 * @version 更新时间:2014年9月2日下午5:50:29
	 * @param bold
	 *            true:粗体,false:非粗体
	 */
	public void setTitleTextBold(boolean bold) {
		TextPaint paint = mTitleTextBtn.getPaint();
		if (bold) {
			// 粗体
			paint.setFakeBoldText(true);
		} else {
			paint.setFakeBoldText(false);
		}

	}

	/**
	 * 设置标题背景
	 * 
	 * @version 更新时间:2014年9月2日下午5:52:26
	 * @param resId
	 *            图片资源
	 */
	public void setTitleTextBackgroundResource(int resId) {
		mTitleTextBtn.setBackgroundResource(resId);
	}

	/**
	 * 设置标题背景
	 * 
	 * @version 更新时间:2014年9月2日下午5:52:48
	 * @param drawable
	 *            背景图片
	 */
	@SuppressWarnings("deprecation")
	public void setTitleTextBackgroundDrawable(Drawable drawable) {
		mTitleTextBtn.setBackgroundDrawable(drawable);
	}

	/**
	 * 设置标题文本
	 * 
	 * @version 更新时间:2014年9月2日下午5:53:41
	 * @param text
	 *            需要显示的标题
	 */
	public void setTitleText(String text) {
		mTitleTextBtn.setText(text);
	}

	/**
	 * 设置标题文本
	 * 
	 * @version 更新时间:2014年9月2日下午5:56:35
	 * @param resId
	 *            显示的标题资源
	 */
	public void setTitleText(int resId) {
		mTitleTextBtn.setText(resId);
	}

	/**
	 * 设置小标题文本
	 * 
	 * @version 更新时间:2014年9月2日下午5:57:18
	 * @param text
	 *            显示的标题
	 */
	public void setTitleSmallText(String text) {
		if (CcStrUtil.isEmpty(text)) {
			mTitleSmallTextBtn.setVisibility(View.GONE);
		} else {
			mTitleSmallTextBtn.setVisibility(View.VISIBLE);
			LinearLayout.LayoutParams titleSmallTextViewLayoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mTitleSmallTextBtn.setLayoutParams(titleSmallTextViewLayoutParams);
			mTitleSmallTextBtn.setText(text);
		}
	}

	/**
	 * 设置小标题文本
	 * 
	 * @version 更新时间:2014年9月2日下午6:01:30
	 * @param resId
	 *            需要显示的资源
	 */
	public void setTitleSmallText(int resId) {
		LinearLayout.LayoutParams titleSmallTextViewLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mTitleSmallTextBtn.setLayoutParams(titleSmallTextViewLayoutParams);
		mTitleSmallTextBtn.setText(resId);
	}

	/**
	 * 设置Logo的背景图
	 * 
	 * @version 更新时间:2014年9月2日下午6:02:15
	 * @param drawable
	 *            需要设置的图片
	 */
	public void setLogo(Context context, Drawable drawable) {
		mLogoView.setVisibility(View.VISIBLE);
		drawable.setBounds(0, 0, CcViewUtil.scale(context, 30),
				CcViewUtil.scale(context, 50));
		mLogoView.setCompoundDrawables(drawable, null, null, null);
	}

	/**
	 * 设置Logo的背景资源
	 * 
	 * @version 更新时间:2014年9月2日下午6:03:00
	 * @param resId
	 *            图片资源
	 */
	public void setLogo(Context context, int resId) {
		mLogoView.setVisibility(View.VISIBLE);
		Drawable drawable = context.getResources().getDrawable(resId);
		drawable.setBounds(0, 0, CcViewUtil.scale(context, 30),
				CcViewUtil.scale(context, 50));
		// drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
		mLogoView.setCompoundDrawables(drawable, null, null, null);
	}

	/**
	 * 设置显示文字
	 * 
	 * @version 更新时间:2014年9月3日下午5:45:08
	 * @param s需要显示的文字
	 */
	public void setLogoText(String s) {
		if (s != null) {
			mLogoView.setVisibility(View.VISIBLE);
			mLogoView.setText(s);
		}
	}

	/**
	 * 把指定的View填加到标题栏右边
	 * 
	 * @version 更新时间:2014年9月2日下午6:06:45
	 * @param rightView
	 *            需要添加的VIEW
	 */
	public void addRightView(View rightView) {
		mRightLayout.setVisibility(View.VISIBLE);
		mRightLayout.addView(rightView, mLayoutParamsMM);
	}

	/**
	 * 把指定资源ID表示的View填加到标题栏右边
	 * 
	 * @version 更新时间:2014年9月2日下午6:07:50
	 * @param resId
	 *            VIEW资源
	 */
	public void addRightView(int resId) {
		mRightLayout.setVisibility(View.VISIBLE);
		mRightLayout.addView(mInflater.inflate(resId, null), mLayoutParamsMM);
	}

	/**
	 * 清除标题栏右边的View
	 * 
	 * @version 更新时间:2014年9月2日下午6:08:44
	 */
	public void clearRightView() {
		mRightLayout.removeAllViews();
	}

	/**
	 * 获取这个右边的布局,可用来设置位置
	 * 
	 * @version 更新时间:2014年9月2日下午6:09:01
	 * @return 右边布局
	 */
	public LinearLayout getRightLayout() {
		return mRightLayout;
	}

	/**
	 * 设置Logo按钮的点击事件
	 * 
	 * @version 更新时间:2014年9月2日下午6:09:44
	 * @param mOnClickListener
	 *            点击事件
	 */
	public void setLogoOnClickListener(View.OnClickListener mOnClickListener) {
		mLogoView.setOnClickListener(mOnClickListener);
	}

	/**
	 * 设置标题的点击事件
	 * 
	 * @version 更新时间:2014年9月2日下午6:11:19
	 * @param mOnClickListener
	 *            点击事件
	 */
	public void setTitleTextOnClickListener(
			View.OnClickListener mOnClickListener) {
		mTitleTextBtn.setOnClickListener(mOnClickListener);
	}

	/**
	 * 下拉菜单的的实现方法
	 * 
	 * @version 更新时间:2014年9月2日下午6:11:44
	 * @param parent
	 *            显示菜单的父容器
	 * @param view
	 *            要显示的View
	 * @param offsetMode
	 *            不填满的模式
	 */
	public void showWindow(View parent, View view, boolean offsetMode) {
		CcViewUtil.measureView(view);
		int popWidth = parent.getMeasuredWidth();
		int popMargin = (this.getMeasuredHeight() - parent.getMeasuredHeight()) / 2;
		if (view.getMeasuredWidth() > parent.getMeasuredWidth()) {
			popWidth = view.getMeasuredWidth();
		}
		if (offsetMode) {
			mPopupWindow = new PopupWindow(view, popWidth + 10,
					LayoutParams.WRAP_CONTENT, true);
		} else {
			mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, true);
		}
		// 使其聚集
		mPopupWindow.setFocusable(true);
		// 设置允许在外点击消失
		mPopupWindow.setOutsideTouchable(true);
		// 这个是为了点击"返回Back"也能使其消失，并且并不会影响你的背景
		mPopupWindow
				.setBackgroundDrawable(new ColorDrawable(color.transparent));
		mPopupWindow.showAsDropDown(parent, 0, popMargin + 2);
	}

	/**
	 * 隐藏mPopupWindow
	 * 
	 * @version 更新时间:2014年9月2日下午6:15:09
	 */
	public void hideWindow() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}

	/**
	 * 设置标题下拉的View
	 * 
	 * @version 更新时间:2014年9月2日下午6:15:41
	 * @param view
	 *            下拉mPopupWindow的view
	 */
	public void setTitleTextDropDown(final View view) {
		if (view == null) {
			return;
		}
		setTitleTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showWindow(mTitleTextBtn, view, true);
			}
		});
	}

	/**
	 * 获取标题的全体布局
	 * 
	 * @version 更新时间:2014年9月2日下午6:17:19
	 * @return
	 */
	public LinearLayout getTitleTextLayout() {
		return mTitleLayout;
	}
}
