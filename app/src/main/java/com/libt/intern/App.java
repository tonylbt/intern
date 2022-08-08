package com.libt.intern;

import android.app.Activity;
import android.app.Application;

import com.libt.intern.common.ActivityManager;
import com.libt.intern.offerwall.OfferWallHelper;

import org.litepal.LitePal;

public class App extends Application {
    private ActivityManager mActMgr;

    private static App mInstance;

    public static App getInstance() {
        return mInstance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mActMgr = new ActivityManager();
        mInstance = this;
        LitePal.initialize(getApplicationContext());

        OfferWallHelper.init(this);
    }

    public boolean addActivity(Activity activity) {
        return mActMgr.addActivity(activity);
    }

    public boolean removeActivity(Activity activity) {
        return mActMgr.removeActivity(activity);
    }

    public Activity getCurrentActivity() {
        return mActMgr.getCurrentActivity();
    }

    public void quit() {
        mActMgr.onDestory();
    }
}
