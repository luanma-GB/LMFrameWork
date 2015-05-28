package cc.android.activity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cc.android.application.CcAplication;
import cc.android.bitmap.CcImageDownloader;
import cc.android.ccbase.R;
import cc.android.http.CcHttpUtil;
import cc.android.ioc.CcIocEventListener;
import cc.android.ioc.CcIocMethod;
import cc.android.ioc.CcIocSelect;
import cc.android.ioc.CcIocView;
import cc.android.manager.CcActivityManager;
import cc.android.utils.CcAppUtil;
import cc.android.utils.CcImageUtil;
import cc.android.utils.CcPhoneUtil;
import cc.android.view.CcBottomBar;
import cc.android.view.CcTitleBar;

/**
 * 基类Activity
 *
 */
public abstract class CcActivity extends FragmentActivity {
	/** title高度 */
	public int mTitleHeight = 150;
	/** bottom高度 */
	public int mBottomHeight = 150;
	/** 全局的LayoutInflater对象 */
	public LayoutInflater mInflater = null;
	/** 总布局 */
	public RelativeLayout mCCLayout = null;
	/** 顶部标题栏布局 */
	private CcTitleBar mCcTitleBar = null;
	/** 底部标题栏布局 */
	private CcBottomBar mCcBottomBar = null;
	/** 全局的SharedPreferences对象，已经完成初始化 */
	private SharedPreferences mCcSharedPreferences = null;
	/** 主内容布局 */
	protected RelativeLayout mContentLayout = null;
	/** LinearLayout.LayoutParams，已经初始化为MATCH_PARENT, MATCH_PARENT */
	public LinearLayout.LayoutParams layoutParamsMM = null;
	/** LinearLayout.LayoutParams，已经初始化为MATCH_PARENT, WRAP_CONTENT */
	public LinearLayout.LayoutParams layoutParamsMW = null;
	/** LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, MATCH_PARENT */
	public LinearLayout.LayoutParams layoutParamsWM = null;
	/** LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, WRAP_CONTENT */
	public LinearLayout.LayoutParams layoutParamsWW = null;
	/** 全局的网络访问类 */
	public CcHttpUtil mHttpUtil = null;
	/** 全局的图片下载类 */
	public CcImageDownloader mImageDownloader = null;
	/** 消息显示 */
	private static Toast mToast;

	/**
	 * 描述：用指定的View填充主界面.
	 * 
	 * @param contentView
	 *            指定的View
	 */
	public void setCcContentView(View contentView) {
		mContentLayout.removeAllViews();
		mContentLayout.addView(contentView, layoutParamsMM);
		initIocView();
	}

	/**
	 * 用指定资源ID表示的View填充主界面
	 * 
	 * @version 更新时间:2014年9月3日上午11:23:48
	 * @param resId
	 *            指定的View的资源ID
	 */
	public void setCcContentView(int resId) {
		setCcContentView(mInflater.inflate(resId, null));
	}

	/**
	 * 描述：设置界面显示（忽略标题栏）
	 * 
	 * @see android.app.Activity#setContentView(int)
	 */
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initIocView();
	}

	/**
	 * 描述：设置界面显示（忽略标题栏）
	 * 
	 * @see android.app.Activity#setContentView(android.view.View,
	 *      android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void setContentView(View view,
			android.view.ViewGroup.LayoutParams params) {
		super.setContentView(view, params);
		initIocView();
	}

	/**
	 * 描述：设置界面显示（忽略标题栏）
	 * 
	 * @see android.app.Activity#setContentView(android.view.View)
	 */
	public void setContentView(View view) {
		super.setContentView(view);
		initIocView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加当前Activity到所有CcActivity管理列表
		CcActivityManager.addCcactivityAll(this);
		init();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 添加当前Activity到可见CcActivity管理列表
		CcActivityManager.addCcactivityVisible(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// 从可见CcActivity管理列表移除当前Activity
		CcActivityManager.removeCcactivityVisible(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 添加当前Activity到不可见CcActivity管理列表
		CcActivityManager.addCcactivityInVisible(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 从不可见CcActivity管理列表移除当前Activity
		CcActivityManager.removeCcactivityInVisible(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 从所有CcActivity管理列表移除当前Activity
		CcActivityManager.removeCcactivityInAll(this);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		// 从右到左打开页面
		// overridePendingTransition(R.anim.push_right_out,
		// R.anim.push_right_in);
	}

	@Override
	public void finish() {
		super.finish();
		CcAppUtil.closeSoftInput(this);
		// 从左到右关闭页面
		// overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
	}

	/**
	 * 初始化
	 * 
	 * @version 更新时间:2014年9月2日下午3:36:57
	 */
	private void init() {
		// 无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mInflater = LayoutInflater.from(this);
		// 获取Http工具类
		mHttpUtil = CcHttpUtil.getInstance(this);
		mImageDownloader = new CcImageDownloader(this);
		DisplayMetrics dm = CcPhoneUtil.getScreenInfo(this);
		mImageDownloader.setWidth(dm.widthPixels);
		mImageDownloader.setHeight(dm.heightPixels);
		mImageDownloader.setType(CcImageUtil.ORIGINALIMG);
		mImageDownloader.setLoadingImage(R.drawable.down_fail);
		mImageDownloader.setNoImage(R.drawable.down_fail);
		mImageDownloader.setErrorImage(R.drawable.down_fail);
		// 初始化各种布局大小
		layoutParamsMM = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParamsMW = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWM = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		layoutParamsWW = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// 总布局
		mCCLayout = new RelativeLayout(this);
		mCCLayout.setBackgroundColor(Color.rgb(255, 255, 255));
		// 顶部标题栏
		mCcTitleBar = new CcTitleBar(this);
		mCcTitleBar.setVisibility(View.GONE);
		// 内容布局
		mContentLayout = new RelativeLayout(this);
		mContentLayout.setPadding(0, 0, 0, 0);
		// 底部标题栏
		mCcBottomBar = new CcBottomBar(this);
		mCcBottomBar.setVisibility(View.GONE);
		// 添加顶部布局
		mCCLayout.addView(mCcTitleBar, new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, mTitleHeight));
		// 添加底部布局
		RelativeLayout.LayoutParams layoutParamsFW2 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, mBottomHeight);
		layoutParamsFW2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);
		mCCLayout.addView(mCcBottomBar, layoutParamsFW2);
		// 添加中间内容
		RelativeLayout.LayoutParams layoutParamsFW1 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsFW1.addRule(RelativeLayout.BELOW, mCcTitleBar.getId());
		layoutParamsFW1.addRule(RelativeLayout.ABOVE, mCcBottomBar.getId());
		mCCLayout.addView(mContentLayout, layoutParamsFW1);
		// SharedPreferences初始化
		mCcSharedPreferences = getSharedPreferences(CcAplication.SHAREPATH,
				Context.MODE_PRIVATE);
		// 设置ContentView
		setContentView(mCCLayout, layoutParamsMM);
	}

	/**
	 * 初始化为IOC控制的View.
	 */
	private void initIocView() {
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					field.setAccessible(true);

					if (field.get(this) != null)
						continue;

					CcIocView viewInject = field.getAnnotation(CcIocView.class);
					if (viewInject != null) {

						int viewId = viewInject.id();
						field.set(this, findViewById(viewId));

						setListener(field, viewInject.click(),
								CcIocMethod.Click);
						setListener(field, viewInject.longClick(),
								CcIocMethod.LongClick);
						setListener(field, viewInject.itemClick(),
								CcIocMethod.ItemClick);
						setListener(field, viewInject.itemLongClick(),
								CcIocMethod.itemLongClick);

						CcIocSelect select = viewInject.select();
						if (!TextUtils.isEmpty(select.selected())) {
							setViewSelectListener(field, select.selected(),
									select.noSelected());
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 注解View的监听
	 * 
	 * @version 更新时间:2014年9月2日下午3:21:33
	 * @param field
	 *            反射的对象
	 * @param methodName
	 *            操作方式
	 * @param method
	 *            监听的种类
	 * @throws Exception
	 *             抛出的异常
	 */
	private void setListener(Field field, String methodName, CcIocMethod method)
			throws Exception {
		if (methodName == null || methodName.trim().length() == 0)
			return;

		Object obj = field.get(this);

		switch (method) {
		case Click:
			if (obj instanceof View) {
				((View) obj).setOnClickListener(new CcIocEventListener(this)
						.click(methodName));
			}
			break;
		case ItemClick:
			if (obj instanceof AbsListView) {
				((AbsListView) obj)
						.setOnItemClickListener(new CcIocEventListener(this)
								.itemClick(methodName));
			}
			break;
		case LongClick:
			if (obj instanceof View) {
				((View) obj)
						.setOnLongClickListener(new CcIocEventListener(this)
								.longClick(methodName));
			}
			break;
		case itemLongClick:
			if (obj instanceof AbsListView) {
				((AbsListView) obj)
						.setOnItemLongClickListener(new CcIocEventListener(this)
								.itemLongClick(methodName));
			}
			break;
		default:
			break;
		}
	}

	/**
	 * IOCView选中的监听
	 * 
	 * @version 更新时间:2014年9月2日下午3:31:18
	 * @param field
	 *            反射对象
	 * @param select
	 *            选中信息
	 * @param noSelect
	 *            未选中信息
	 * @throws Exception
	 *             抛出的异常
	 */
	private void setViewSelectListener(Field field, String select,
			String noSelect) throws Exception {
		Object obj = field.get(this);
		if (obj instanceof View) {
			((AbsListView) obj)
					.setOnItemSelectedListener(new CcIocEventListener(this)
							.select(select).noSelect(noSelect));
		}
	}

	/**
	 * 获取主标题栏布局
	 * 
	 * @return 顶部布局
	 */
	public CcTitleBar getTitleBar() {
		mCcTitleBar.setVisibility(View.VISIBLE);
		return mCcTitleBar;
	}

	/**
	 * 获取底部标题栏布局
	 * 
	 * @return 底部布局
	 */
	public CcBottomBar getBottomBar() {
		mCcBottomBar.setVisibility(View.VISIBLE);
		return mCcBottomBar;
	}

	/**
	 * 页面跳转
	 * 
	 * @version 更新时间:2014年9月5日下午4:01:59
	 * @param context
	 *            当前页面
	 * @param cls
	 *            需要跳转的页面class
	 * @param isFinish
	 *            是否结束当前页面
	 */
	public void intentActivity(Activity context, Class<?> cls, boolean isFinish) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
		if (isFinish) {
			context.finish();
		}
	}

	/**
	 * 页面跳转
	 * 
	 * @version 更新时间:2014年9月9日上午10:38:46
	 * @param context
	 *            当前页面
	 * @param cls
	 *            需要跳转的页面class
	 */
	public void intentActivity(Activity context, Class<?> cls) {
		intentActivity(context, cls, false);
	}

	/**
	 * 页面跳转
	 * 
	 * @version 更新时间:2014年9月5日下午5:30:33
	 * @param context
	 *            当前页面
	 * @param cls
	 *            需要跳转的页面class
	 * @param isFinish
	 *            是否结束当前页面
	 * @param key
	 *            传递消息的key
	 * @param value
	 *            传递的消息
	 */
	public void intentActivity(Activity context, Class<?> cls,
			boolean isFinish, String key, String value) {
		Intent intent = new Intent(context, cls);
		intent.putExtra(key, value);
		startActivity(intent);
		if (isFinish) {
			context.finish();
		}
	}

	/**
	 * 消息显示
	 * 
	 * @version 更新时间:2014年9月9日上午10:48:21
	 * @param context
	 *            上下文
	 * @param content
	 *            显示内容
	 * @param durtion
	 *            显示时间
	 */
	public void showToast(Context context, CharSequence content) {
		showToast(context, content, 2000);
	}

	/**
	 * 消息显示
	 * 
	 * @version 更新时间:2014年9月9日上午10:48:21
	 * @param context
	 *            上下文
	 * @param content
	 *            显示内容
	 * @param durtion
	 *            显示时间
	 */
	public void showToast(Context context, CharSequence content, int durtion) {
		if (mToast == null) {
			mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(content);
		mToast.setDuration(durtion);
		mToast.setGravity(Gravity.BOTTOM, 0, 0);
		mToast.show();
	}

	/**
	 * 保存SharedPreferences的值
	 */
	public void saveSharedPreferences(String key, String value) {
		Editor editor = mCcSharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 获取SharedPreferences的值
	 */
	public String getSharedPreferences(String key) {
		return mCcSharedPreferences.getString(key, "");
	}
}
