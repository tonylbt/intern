package com.libt.intern.activity;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.libt.intern.App;

/**
 * Created by libingtao on 2018/08/10.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getInstance().addActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        App.getInstance().removeActivity(this);
    }
}
