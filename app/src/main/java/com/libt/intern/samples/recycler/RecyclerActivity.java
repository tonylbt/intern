package com.libt.intern.samples.recycler;

import java.util.ArrayList;
import java.util.List;

import org.litepal.LitePal;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.libt.intern.R;
import com.libt.intern.activity.BaseActivity;
import com.libt.intern.samples.recycler.Fragment.EditProfileFragment;
import com.libt.intern.samples.recycler.Fragment.RecyclerFragment;
import com.libt.intern.samples.recycler.adapter.UserAdapter;

import com.libt.intern.samples.recycler.bean.People;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.KeyEvent;

/**
 * TODO to liuyc， Modify Tips：
 * 1. Modify load data option to subThread;
 * 2. params name like: userList-> userList
 *
 * 3. People, User, DataBean, DataBean. Some bean java may merge or replace.
 */
public class RecyclerActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{
    private List<People> userList = new ArrayList<>();
    private UserAdapter adapter;
    private String jsonData;
    private BottomNavigationBar mBottomNavigationBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.getDatabase();
        init();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void init() {
        setContentView(R.layout.recycler_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.second_fl, new RecyclerFragment());
        transaction.commit();
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar_container);
        //setPullRefresher();
        mBottomNavigationBar.setAutoHideEnabled(true);//自动隐藏
        //BottomNavigationBar.MODE_SHIFTING;换挡模式，未选中的Item不会显示文字，选中的会显示文字。在切换的时候会有一个像换挡的动画
        //BottomNavigationBar.MODE_FIXED;填充模式，未选中的Item会显示文字，没有换挡动画。
        //BottomNavigationBar.MODE_DEFAULT; 如果Item的个数<=3就会使用MODE_FIXED模式，否则使用MODE_SHIFTING模式
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        // BottomNavigationBar.BACKGROUND_STYLE_DEFAULT; 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC 。如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
        // BottomNavigationBar.BACKGROUND_STYLE_RIPPLE  点击的时候有水波纹效果
        // BottomNavigationBar.BACKGROUND_STYLE_STATIC  点击的时候没有水波纹效果
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setBarBackgroundColor(R.color.grey);//背景颜色
        mBottomNavigationBar.setInActiveColor(R.color.dark);//未选中时的颜色
        mBottomNavigationBar.setActiveColor(R.color.colorPrimary);//选中时的颜色

       /* mTextBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setAnimationDuration(200)
                .setText("3")
                .setHideOnSelect(false);

        mShapeBadgeItem = new ShapeBadgeItem()
                .setShapeColorResource(R.color.colorPrimary)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(false);*///数字和蓝色五角星是分别通过TextBadgeItem和ShapeBadgeItem来实现的：
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.discover, "List"))
                .addItem(new BottomNavigationItem(R.drawable.yh,"Me"))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
        case 0:
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.second_fl, new RecyclerFragment());
        transaction.commit();

        break;
        case 1:
        getSupportFragmentManager().beginTransaction().replace(R.id.second_fl, new EditProfileFragment()).commit();
        break;

    }}

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
