package com.libt.intern.samples.cake.control;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.libt.intern.R;
import com.libt.intern.samples.cake.adapter.ListpopupWindowAdapter;

/**
 * Created by Cake on 2018/8/9.
 */

public class MainListPopupWindow extends PopupWindow {
    private ListpopupWindowAdapter mListPopupWindowAdapter;
    private Listener mListener;
    View contentView;

    public MainListPopupWindow(Activity context, List<String> list, String selectName, Listener listener ) {
        super(context);
        mListener = listener;
        //拿到基本的item高度，这里给定每个Item的高度和宽度
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        float titleItemHeight = density*50;  //50dp，高
        float paddingWight = density*12;  //12dp
        WindowManager wm = context.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        //拿到要显示的总高度，超过5个条目时只显示5个，其余滑动显示
        int height;
        if(list.size() > 5){
            height = (int)titleItemHeight * 5;
        }else {
            height = (int)titleItemHeight * list.size();
        }
        contentView = LayoutInflater.from(context).inflate(R.layout.list_popu, null);
        this.setContentView(contentView);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView listView = (ListView) contentView.findViewById(R.id.lv_ranking_classify);
        LinearLayout.LayoutParams lp;
        lp = (LinearLayout.LayoutParams) listView.getLayoutParams();
        lp.height = height;
        listView.setLayoutParams(lp);

        if(mListPopupWindowAdapter != null ){
            mListPopupWindowAdapter = null;
        }
        mListPopupWindowAdapter = new ListpopupWindowAdapter(context,list,selectName,titleItemHeight);
        listView.setAdapter(mListPopupWindowAdapter);

        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                mListener.onItemClickListener(position);
            }
        });
        listView.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                dismiss();
                return true;
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                mListener.onPopupWindowDismissListener();
            }
        });

        //点击外围
        this.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    public interface Listener{
        void onPopupWindowDismissListener();  //弹框消失监听
        void onItemClickListener(int position);  //条目点击监听
    }
    public void showDown(View v) {
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int  popupHeight = contentView.getMeasuredHeight();
        int  popupWidth = contentView.getMeasuredWidth();
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        showAtLocation(v, Gravity.NO_GRAVITY, location[0] , location[1]);
    }
}
