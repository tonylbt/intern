package com.libt.intern.samples.recycler;

import com.libt.intern.R;
import com.libt.intern.samples.recycler.recyclerutil.SharedPreferencesUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import static java.lang.Thread.sleep;

/**
 * TODO to liuyc， Modify Tips： The handler msg may instead of loop thread.
 */

public class SplashActivity extends Activity {
    public String time, model;
    public TextView skip;
    public int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initTask();
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessage(MSG_WHAT_OPEN_REGISTER);
            }
        });
    }
    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // splash no title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        skip = (TextView) findViewById(R.id.skip);
        skip.setVisibility(View.GONE);
        model = SharedPreferencesUtil.getInstance(SplashActivity.this).getModel();
        time = SharedPreferencesUtil.getInstance(SplashActivity.this).getAdtime();
        if (time.equals("")) {
            time = "3";
        }
        count = Integer.parseInt(time);
        if (model.equals("ad")) {
            skip.setVisibility(View.VISIBLE);
        } else {
            skip.setVisibility(View.GONE);
        }
    }
    private Handler mHandler;
    private final int MSG_WHAT_COUNT_DOWN = 1;
    private final int MSG_WHAT_OPEN_REGISTER = 2;

    private void initTask() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_WHAT_COUNT_DOWN:
                        skip.setText("(" + String.valueOf(count) + "s)跳过");
                        if (count > 0) {
                            count--;
                            mHandler.sendEmptyMessageDelayed(MSG_WHAT_COUNT_DOWN, 1000);
                        } else {
                            mHandler.removeMessages(MSG_WHAT_COUNT_DOWN);
                            mHandler.sendEmptyMessage(MSG_WHAT_OPEN_REGISTER);
                        }
                        break;
                    case MSG_WHAT_OPEN_REGISTER:
                        startActivity(new Intent(SplashActivity.this, RegisteActivity.class));
                        SplashActivity.this.finish();
                        break;
                }
            }
        };
        mHandler.sendEmptyMessage(MSG_WHAT_COUNT_DOWN);
    }
}
