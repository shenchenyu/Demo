package com.leo.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 
 * @author Scleo
 *
 */
public class SharedPreferencesUtils {

	public static final String SP_NAME = "config";
	public static SharedPreferences sp;
	/**
	 * 新建保存数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
	/**
	 * 获取布尔数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		return sp.getBoolean(key, defValue);
	}
	/**
	 * 获取字符串数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, 0);
		return sp.getString(key, defValue);
	}
}
