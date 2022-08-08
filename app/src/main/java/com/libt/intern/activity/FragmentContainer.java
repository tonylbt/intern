package com.libt.intern.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;

import com.libt.intern.R;
import com.libt.intern.fragment.BaseFragment;

/**
 * 容器类型的Activity，为减少使用Activity，并可以减少activity在manifest中的定义。
 * Created by libingtao on 2018/08/10.
 */
public final class FragmentContainer extends BaseActivity {
    // fragment 的Container
    private static final int CONTAINER_ID = R.id.layout_container;
    // 需要跳转到的fragment
    public static final String EXTRA_KEY_SUB_FRAGMENT = "sub_fragment";
    // 外部传入的Bundle
    public static final String EXTRA_KEY_EXTRA_BUNDLE = "extra_bundle";
    public static final String ACTION_TRANSACTION_FRAGMENT = "com.ushareit.intern.ACTION_TRANSACTION_FRAGMENT";

    public static void launchContainerActivity(Context context, Bundle bundle, String fragmentName) {
        if (TextUtils.isEmpty(fragmentName)) {
            return;
        }
        try {
            Class<?> cls = Class.forName(fragmentName);
            Fragment fragment = (Fragment) cls.newInstance();
            if (!(fragment instanceof BaseFragment)) {
                return;
            }

            Intent intent = new Intent(context, FragmentContainer.class);
            if (bundle != null) {
                intent.putExtra(EXTRA_KEY_EXTRA_BUNDLE, bundle);
            }
            intent.putExtra(EXTRA_KEY_SUB_FRAGMENT, fragmentName);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void launchContainerActivity(Context context, Bundle bundle, Class<? extends BaseFragment> fragment) {
        if (fragment == null) {
            return;
        }

        Intent intent = new Intent(context, FragmentContainer.class);
        if (bundle != null) {
            intent.putExtra(EXTRA_KEY_EXTRA_BUNDLE, bundle);
        }
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(EXTRA_KEY_SUB_FRAGMENT, fragment.getName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        initView();
    }

    public void initView() {
        loadFragment();
    }

    private void loadFragment() {
        try {
            Class<?> cls = Class.forName(getIntent().getStringExtra(EXTRA_KEY_SUB_FRAGMENT));
            BaseFragment fragment = (BaseFragment) cls.newInstance();

            getSupportFragmentManager().beginTransaction().add(CONTAINER_ID, fragment).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
