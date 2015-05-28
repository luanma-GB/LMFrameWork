package cc.android.ioc;

import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 注解View的监听
 * 
 *
 */
public class CcIocEventListener implements OnClickListener,
		OnLongClickListener, OnItemClickListener, OnItemSelectedListener,
		OnItemLongClickListener {

	private Object handler;

	private String clickMethod;
	private String longClickMethod;
	private String itemClickMethod;
	private String itemSelectMethod;
	private String nothingSelectedMethod;
	private String itemLongClickMehtod;

	public CcIocEventListener(Object handler) {
		this.handler = handler;
	}

	public CcIocEventListener click(String method) {
		this.clickMethod = method;
		return this;
	}

	public CcIocEventListener longClick(String method) {
		this.longClickMethod = method;
		return this;
	}

	public CcIocEventListener itemLongClick(String method) {
		this.itemLongClickMehtod = method;
		return this;
	}

	public CcIocEventListener itemClick(String method) {
		this.itemClickMethod = method;
		return this;
	}

	public CcIocEventListener select(String method) {
		this.itemSelectMethod = method;
		return this;
	}

	public CcIocEventListener noSelect(String method) {
		this.nothingSelectedMethod = method;
		return this;
	}

	public boolean onLongClick(View v) {
		return invokeLongClickMethod(handler, longClickMethod, v);
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return invokeItemLongClickMethod(handler, itemLongClickMehtod, arg0,
				arg1, arg2, arg3);
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		invokeItemSelectMethod(handler, itemSelectMethod, arg0, arg1, arg2,
				arg3);
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		invokeNoSelectMethod(handler, nothingSelectedMethod, arg0);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		invokeItemClickMethod(handler, itemClickMethod, arg0, arg1, arg2, arg3);
	}

	public void onClick(View v) {

		invokeClickMethod(handler, clickMethod, v);
	}

	private static Object invokeClickMethod(Object handler, String methodName,
			Object... params) {
		if (handler == null) {
			return null;
		}
		Method method = null;
		try {
			method = handler.getClass().getDeclaredMethod(methodName,
					View.class);
			if (method != null) {
				return method.invoke(handler, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean invokeLongClickMethod(Object handler,
			String methodName, Object... params) {
		if (handler == null) {
			return false;
		}
		Method method = null;
		try {
			// public boolean onLongClick(View v)
			method = handler.getClass().getDeclaredMethod(methodName,
					View.class);
			if (method != null) {
				Object obj = method.invoke(handler, params);
				return obj == null ? false : Boolean.valueOf(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	private static Object invokeItemClickMethod(Object handler,
			String methodName, Object... params) {
		if (handler == null) {
			return null;
		}
		Method method = null;
		try {
			// /onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			method = handler.getClass().getDeclaredMethod(methodName,
					AdapterView.class, View.class, int.class, long.class);
			if (method != null) {
				return method.invoke(handler, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static boolean invokeItemLongClickMethod(Object handler,
			String methodName, Object... params) {

		Method method = null;
		try {
			// /onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long
			// arg3)
			method = handler.getClass().getDeclaredMethod(methodName,
					AdapterView.class, View.class, int.class, long.class);
			if (method != null) {
				Object obj = method.invoke(handler, params);
				return Boolean.valueOf(obj == null ? false : Boolean
						.valueOf(obj.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private static Object invokeItemSelectMethod(Object handler,
			String methodName, Object... params) {
		if (handler == null) {
			return null;
		}
		Method method = null;
		try {
			// /onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long
			// arg3)
			method = handler.getClass().getDeclaredMethod(methodName,
					AdapterView.class, View.class, int.class, long.class);
			if (method != null) {
				return method.invoke(handler, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Object invokeNoSelectMethod(Object handler,
			String methodName, Object... params) {
		if (handler == null) {
			return null;
		}
		Method method = null;
		try {
			// onNothingSelected(AdapterView<?> arg0)
			method = handler.getClass().getDeclaredMethod(methodName,
					AdapterView.class);
			if (method != null) {
				return method.invoke(handler, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
