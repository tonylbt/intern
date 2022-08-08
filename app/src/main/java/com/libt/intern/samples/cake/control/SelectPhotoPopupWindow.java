package com.libt.intern.samples.cake.control;



import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.libt.intern.R;

/**
 * Created by Cake on 2018/8/8.
 */

public class SelectPhotoPopupWindow extends PopupWindow {
	private Button takePhoto;
	private Button pickPhoto;
	private Button btnCancel;
	private View mMenuView;

	public SelectPhotoPopupWindow(Context context, View.OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.bottompop, null);
		takePhoto = (Button) mMenuView.findViewById(R.id.btn_take_photo);
		pickPhoto = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
		btnCancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(itemsOnClick);
		pickPhoto.setOnClickListener(itemsOnClick);
		takePhoto.setOnClickListener(itemsOnClick);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		// this.setAnimationStyle();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}
}
