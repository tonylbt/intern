package com.libt.intern.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.libt.intern.activity.FragmentContainer;
import com.libt.intern.fragment.BaseFragment;

/**
 * Created by lbingt on 2018/08/10.
 */
public class Actions {
    public interface Category {
        int ACTIVITY = 1;
        int FRAGMENT = 2;
        int BROADCAST = 3;
        int SERVICE = 4;
    }

    private Activity mContext;
    private Class<? extends BaseFragment> fragment;
    private Class<Activity> activity;
    private Intent mIntent;
    private String mName;

    public int mCategory;

    public Actions(Activity context, Class clazz, int category) {
        mContext = context;
        mCategory = category;

        buildAction(clazz, null);
    }

    public Actions(Activity context, Class clazz, Bundle bundle, int category) {
        mContext = context;
        mCategory = category;

        buildAction(clazz, bundle);
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return mName;
    }

    private void buildAction(Class clazz, Bundle bundle) {
        mName = clazz.getSimpleName();
        switch (mCategory) {
            case Category.ACTIVITY:
                mIntent = new Intent(mContext, clazz);
                break;
            case Category.FRAGMENT:
                mIntent = new Intent(FragmentContainer.ACTION_TRANSACTION_FRAGMENT);
                mIntent.putExtra(FragmentContainer.EXTRA_KEY_SUB_FRAGMENT, clazz.getName());
                break;
            case Category.BROADCAST:
                break;
            case Category.SERVICE:
                break;
        }
        if(bundle != null) {
            mIntent.putExtras(bundle);
        }
    }

    public void doAction() {
        switch (mCategory) {
            case Category.ACTIVITY:
            case Category.FRAGMENT:
                mContext.startActivity(mIntent);
                break;
            case Category.BROADCAST:
                mContext.sendBroadcast(mIntent);
                break;
            case Category.SERVICE:
                mContext.startService(mIntent);
                break;
        }
    }
}
