package cc.android.utils;

import com.alibaba.fastjson.JSON;

/**
 * fastjson解析
 * 
 *
 */
public class CcJsonUtil {
	
	/**
	 * JSON转String
	 * 
	 * @version 更新时间:2014-4-17下午3:19:33
	 * @param obj
	 *            JSON对象
	 * @return
	 */
	public static String bean2Json(Object obj) {
		return JSON.toJSONString(obj);
	}

	/***
	 * String转Bean
	 * 
	 * @version 更新时间:2014-4-17下午3:19:55
	 * @param jsonStr
	 *            需要转的String
	 * @param objClass
	 *            生成的Bean
	 * @return
	 */
	public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
		return JSON.parseObject(jsonStr, objClass);
	}
}
