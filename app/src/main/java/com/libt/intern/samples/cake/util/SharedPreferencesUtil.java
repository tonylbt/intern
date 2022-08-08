package com.libt.intern.samples.cake.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qwe on 2018/8/16.
 */

public class SharedPreferencesUtil {

	public static final String SHARED_NAME = "user";
	public static final String IS_FRIST_RUN = "isFirstRun";
	public static final String USER_NAME = "userName";
	public static final String USER_PASSWORD = "userPassword";
	public static final String IS_REMEBER_PASSWORD = "isRemeberPassword";
	public static final String IS_AUTO = "isAuto";

	private static SharedPreferences mPreferences;
	private static SharedPreferences.Editor mEditor;
	private static SharedPreferencesUtil mSharedPreferencesUtil;

	private SharedPreferencesUtil(Context context) {
		mPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
		mEditor = mPreferences.edit();
	}

	public static SharedPreferencesUtil getInstance(Context context) {
		if (mSharedPreferencesUtil == null) {
			mSharedPreferencesUtil = new SharedPreferencesUtil(context);
		}
		return mSharedPreferencesUtil;
	}

	public void putString(String key, String value) {
		mEditor.putString(key, value);
		mEditor.apply();
	}
	public void putBoolean(String key, Boolean value) {
		mEditor.putBoolean(key, value);
		mEditor.apply();
	}

	public String getString(String key) {
		return mPreferences.getString(key, "");
	}
	public Boolean getBoolean(String key) {
		return mPreferences.getBoolean(key, false);
	}

}
