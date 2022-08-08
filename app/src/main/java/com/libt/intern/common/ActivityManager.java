package com.libt.intern.common;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Managment all alive Activity.
 * Created by libingtao on 2015/10/16.
 */
public class ActivityManager {
    private ArrayList<Activity> mActivityList;
    private Activity mCurrentActivity;

    public ActivityManager() {
        mActivityList = new ArrayList<Activity>(8);
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public boolean addActivity(Activity activity) {
        if (mActivityList == null) {
            mActivityList = new ArrayList<Activity>(8);
        }
        mCurrentActivity = activity;
        return mActivityList.add(activity);
    }

    public boolean removeActivity(Activity activity) {
        if (mActivityList == null || mActivityList.size() == 0) {
            return false;
        }
        if (activity == null) {
            return false;
        }
        return mActivityList.remove(activity);
    }

    public void destoryAllActivity() {
        if (mActivityList != null && mActivityList.size() > 0) {
            for (Activity act : mActivityList) {
                if (!act.isDestroyed()) {
                    act.finish();
                }
            }
        }
    }

    public void onDestory() {
        destoryAllActivity();
        mActivityList.clear();
        mActivityList = null;
    }
}
