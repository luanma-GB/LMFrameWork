package cc.android.manager;

import java.util.ArrayList;

import cc.android.activity.CcActivity;

/**
 * CcActivity的管理
 * 
 *
 */
public class CcActivityManager {
	/** 所有CcActivity */
	private static ArrayList<CcActivity> ccactivityListAll = new ArrayList<CcActivity>();
	/** 可见CcActivity */
	private static ArrayList<CcActivity> ccactivityVisible = new ArrayList<CcActivity>();
	/** 不可见CcActivity */
	private static ArrayList<CcActivity> ccactivityForeground = new ArrayList<CcActivity>();
	/** CcActivity管理实例 */
	private static CcActivityManager ccActivityManagerModel;

	/**
	 * 构造方法
	 * 
	 * @version 更新时间:2014年9月1日下午5:12:42
	 */
	private CcActivityManager() {

	}

	/**
	 * 外部获取当前实例
	 * 
	 * @version 更新时间:2014年9月1日下午5:12:53
	 * @return CcActivityManagerModel实例
	 */
	public static CcActivityManager getInstance() {
		if (ccActivityManagerModel == null) {
			synchronized (CcActivityManager.class) {
				if (ccActivityManagerModel == null) {
					ccActivityManagerModel = new CcActivityManager();
				}
			}
		}
		return ccActivityManagerModel;
	}

	/**
	 * 添加CcActivity到所有CcActivity列表
	 * 
	 * @version 更新时间:2014年9月1日下午5:16:00
	 * @param activity
	 *            需要添加的CcActivity
	 */
	public static void addCcactivityAll(CcActivity activity) {
		if (activity != null && !ccactivityListAll.contains(activity)) {
			ccactivityListAll.add(activity);
		}
	}

	/**
	 * 添加CcActivity到可见CcActivity列表
	 * 
	 * @version 更新时间:2014年9月1日下午5:17:50
	 * @param activity
	 *            需要添加的CcActivity
	 */
	public static void addCcactivityVisible(CcActivity activity) {
		if (activity != null && !ccactivityVisible.contains(activity)) {
			ccactivityVisible.add(activity);
		}
	}

	/**
	 * 添加CcActivity到不可见CcActivity列表
	 * 
	 * @version 更新时间:2014年9月1日下午5:18:53
	 * @param activity
	 *            需要添加的CcActivity
	 */
	public static void addCcactivityInVisible(CcActivity activity) {
		if (activity != null && !ccactivityForeground.contains(activity)) {
			ccactivityForeground.add(activity);
		}
	}

	/**
	 * 从所有CcActivity中移除一个CcActivity
	 * 
	 * @version 更新时间:2014年9月1日下午5:20:44
	 * @param activity
	 *            需要移除的CcActivity
	 */
	public static void removeCcactivityInAll(CcActivity activity) {
		// 如果需要移除的CcActivity为空，则不执行
		if (activity == null) {
			return;
		}
		// ALL中移除
		if (ccactivityListAll.contains(activity)) {
			ccactivityListAll.remove(activity);
		}
		// 可见中移除
		if (ccactivityVisible.contains(activity)) {
			ccactivityVisible.remove(activity);
		}
		// 不可见中移除
		if (ccactivityForeground.contains(activity)) {
			ccactivityForeground.remove(activity);
		}
	}

       
	/**
	 * 按名字 删除指定 activity
	 * @param activity
	 */
	public static void removeCcactivityOneByName(String activityName) {
		if (activityName == null || activityName.equals("")) {
			return;
		}
		for (int j = 0; j < ccactivityListAll.size(); j++) {
			if(ccactivityListAll.get(j).getClass().getSimpleName().equals(activityName)){
				ccactivityListAll.get(j).finish();
//				ccactivityListAll.remove(ccactivityListAll.get(j));
				return;
			}
		}
	}

	
	
	
	
	
	
	
	
	/**
	 * 从可见CcActivity中移除一个CcActivity
	 * 
	 * @version 更新时间:2014年9月1日下午5:25:58
	 * @param activity
	 *            需要移除的CcActivity
	 */
	public static void removeCcactivityVisible(CcActivity activity) {
		if (activity != null && ccactivityVisible.contains(activity)) {
			ccactivityVisible.remove(activity);
		}
	}

	/**
	 * 从不可见CcActivity中移除一个CcActivity
	 * 
	 * @version 更新时间:2014年9月1日下午5:25:58
	 * @param activity
	 *            需要移除的CcActivity
	 */
	public static void removeCcactivityInVisible(CcActivity activity) {
		if (activity != null && ccactivityForeground.contains(activity)) {
			ccactivityForeground.remove(activity);
		}
	}

	/**
	 * 获取所有Activity
	 * 
	 * @version 更新时间:2014年9月22日下午3:59:27
	 * @return
	 */
	public static ArrayList<CcActivity> getActivitAll() {
		return ccactivityListAll;
	}

	/**
	 * 获取前台Activity
	 * 
	 * @version 更新时间:2014年9月22日下午3:59:43
	 * @return
	 */
	public static ArrayList<CcActivity> getActivitForeground() {
		return ccactivityForeground;
	}

	/**
	 * 获取可见Activity
	 * 
	 * @version 更新时间:2014年9月22日下午3:59:46
	 * @return
	 */
	public static ArrayList<CcActivity> getActivitVisible() {
		return ccactivityVisible;
	}
}
