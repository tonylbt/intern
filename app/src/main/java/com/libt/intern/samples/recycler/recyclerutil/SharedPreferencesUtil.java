package com.libt.intern.samples.recycler.recyclerutil;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qwe on 2018/8/16.
 */

/**
 * TODO to liuyc， Modify Tips： Make SharedPreferences keys to 'static final'
 * constant value.
 */
public class SharedPreferencesUtil {


	private static final String mTAG = "data";
	private static final String MODEL = "model";
	private static final String ADTIME = "adtime";
	private static final String TIME = "time";
	private static final String FIRSTRUN = "isFirstRun";
	private static SharedPreferences mPreferences;
	private static SharedPreferences.Editor mEditor;
	private static SharedPreferencesUtil mSharedPreferencesUtil;

	public SharedPreferencesUtil(Context context) {
		mPreferences = context.getSharedPreferences(mTAG, Context.MODE_PRIVATE);
		mEditor = mPreferences.edit();
	}
	// single model
	public static SharedPreferencesUtil getInstance(Context context) {
		if (mSharedPreferencesUtil == null) {
			mSharedPreferencesUtil = new SharedPreferencesUtil(context);
		}
		return mSharedPreferencesUtil;
	}
	public void putModel(String value) {
		mEditor.putString(MODEL, value);
		mEditor.apply();
	}
	public void putADTime(String value) {
		mEditor.putString(ADTIME, value);
		mEditor.apply();
	}
	public void putTime(String value) {
		mEditor.putString(TIME, value);
		mEditor.apply();
	}
	public void putFirstRun(Boolean value) {
		mEditor.putBoolean(FIRSTRUN, value);
		mEditor.apply();
	}
	public String getModel() {
		return mPreferences.getString(MODEL, "");
	}
	public String getAdtime() {
		return mPreferences.getString(ADTIME, "");
	}
	public String getTime() {
		return mPreferences.getString(TIME, "");
	}
	public Boolean getFirstRun() {
		return mPreferences.getBoolean(FIRSTRUN, true);
	}
	public void removeSP(String key) {
		mEditor.remove(key);
		mEditor.commit();
	}

}
