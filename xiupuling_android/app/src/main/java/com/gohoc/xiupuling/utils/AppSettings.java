package com.gohoc.xiupuling.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSettings {
	public static final String PREFER_NAME = "xiupuling_wjj";
	//public static int MODE = 3;
	public static int MODE = Context.MODE_PRIVATE;

	/**
	 * 获取配置文件
	 * 
	 * @param context
	 * @param key
	 *            根据key获取数据
	 * @param defaultValue
	 *            如果key对应为空，则输出此默认�?
	 * @return
	 */
	public static String getPrefString(Context context, String key,
			final String defaultValue) {
		final SharedPreferences settings = context.getSharedPreferences(
				PREFER_NAME, MODE);
		// .getDefaultSharedPreferences("",3);
		return settings.getString(key, defaultValue);
	}

	/**
	 * 添加�?��key-value 数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setPrefString(Context context, final String key,
			final String value) {
		// final SharedPreferences settings = PreferenceManager
		// .getDefaultSharedPreferences(context);
		final SharedPreferences settings = context.getSharedPreferences(PREFER_NAME, MODE);
		settings.edit().putString(key, value).commit();
	}
	
	/**
	 * 获取配置文件
	 * 
	 * @param context
	 * @param key
	 *            根据key获取数据
	 * @param defaultValue
	 *            如果key对应为空，则输出此默认�?
	 * @return
	 */
	public static boolean getPrefBoolean(Context context, String key,
			final Boolean defaultValue) {
		final SharedPreferences settings = context.getSharedPreferences(
				PREFER_NAME, MODE);
		// .getDefaultSharedPreferences("",3);
		return settings.getBoolean(key, defaultValue);
	}

	/**
	 * 添加�?��key-value 数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setPrefBoolean(Context context, final String key,
			final Boolean value) {
		// final SharedPreferences settings = PreferenceManager
		// .getDefaultSharedPreferences(context);
		final SharedPreferences settings = context.getSharedPreferences(
				PREFER_NAME, MODE);
		settings.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 获取配置文件
	 * 
	 * @param context
	 * @param key
	 *            根据key获取数据
	 * @param defaultValue
	 *            如果key对应为空，则输出此默认�?
	 * @return
	 */
	public static int getPrefInt(Context context, String key,
			final int defaultValue) {
		final SharedPreferences settings = context.getSharedPreferences(
				PREFER_NAME, MODE);
		// .getDefaultSharedPreferences("",3);
		return settings.getInt(key, defaultValue);
	}

	/**
	 * 添加�?��key-value 数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setPrefInt(Context context, final String key,
			final int value) {
		// final SharedPreferences settings = PreferenceManager
		// .getDefaultSharedPreferences(context);
		final SharedPreferences settings = context.getSharedPreferences(
				PREFER_NAME, MODE);
		settings.edit().putInt(key, value).commit();
	}

	/**
	 * 清空配置文件内所有数�?
	 * 
	 * @param context
	 * @param p
	 */
	public static void clearPreference(Context context,
			final SharedPreferences p) {
		final Editor editor = p.edit();

		editor.clear();
		editor.commit();
	}

	/**
	 * 移除相关key的数�?
	 * 
	 * @param context
	 * @param key
	 */
	public static void removePreference(Context context, final String key) {
		// final SharedPreferences settings = PreferenceManager
		// .getDefaultSharedPreferences(context);
		final SharedPreferences settings = context.getSharedPreferences(
				PREFER_NAME, MODE);
		settings.edit().remove(key).commit();
	}
}
