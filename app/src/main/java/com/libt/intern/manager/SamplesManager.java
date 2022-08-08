package com.libt.intern.manager;

import java.util.ArrayList;

import android.app.Activity;

import com.libt.intern.offerwall.OfferWallActivity;
import com.libt.intern.samples.LoginActivity;
import com.libt.intern.samples.LoginFragment;

import com.libt.intern.samples.cake.cake.RegisteActivity;

import com.libt.intern.samples.recycler.SplashActivity;
import com.libt.intern.trim.TrimFragment;

/**
 * Created by lbingt on 2018/08/10.
 */
public class SamplesManager {
    private ArrayList<Actions> mItems;

    private static SamplesManager mInstance;

    public SamplesManager() {
    }

    public static SamplesManager getInstance() {
        if (mInstance == null) {
            mInstance = new SamplesManager();
        }
        return mInstance;
    }

    public ArrayList<Actions> getActions(Activity context) {
        mItems = new ArrayList<>();

        // Add Fragment
////        mItems.add(new Actions(context, BluetoothFragment.class, Actions.Category.FRAGMENT));
//        mItems.add(new Actions(context, LoginFragment.class, Actions.Category.FRAGMENT));
        mItems.add(new Actions(context, TrimFragment.class, Actions.Category.FRAGMENT));

        // Add Activity
////        mItems.add(new Actions(context, MergeActivity.class, Actions.Category.ACTIVITY));
//        mItems.add(new Actions(context, LoginActivity.class, Actions.Category.ACTIVITY));
//
//        mItems.add(new Actions(context, RegisteActivity.class, Actions.Category.ACTIVITY));
//
//        mItems.add(new Actions(context, SplashActivity.class, Actions.Category.ACTIVITY));
        mItems.add(new Actions(context, OfferWallActivity.class, Actions.Category.ACTIVITY));



        return mItems;
    }
}
