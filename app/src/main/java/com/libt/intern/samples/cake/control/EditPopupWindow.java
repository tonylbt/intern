package com.libt.intern.samples.cake.control;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.libt.intern.R;

/**
 * Created by Cake on 2018/8/8.
 */

public class EditPopupWindow extends PopupWindow {
	private EditText editText;
	private TextView realText;
	private TextView maxText;
	private Button yes;
	private Button no;
	private TextView addText;
	private int maxLength;
	private Context context;
	private View mMenuView;

	public EditPopupWindow(final TextView addText, final Context context, final int maxLength) {
		super(context);
		this.addText = addText;
		this.context = context;
		this.maxLength = maxLength;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.editpopu, null);
		MyfindviewById();
		maxText.setText("/" + maxLength);
		realText.setText("" + editText.getText().toString().length());
		editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
		no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		editText.setText(addText.getText().toString());
		editText.setSelection(addText.getText().toString().length());
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				realText.setText("" + s.toString().length());
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() <= maxLength)
					realText.setText("" + s.toString().length());
				else {
					Toast.makeText(context, "out of range", Toast.LENGTH_SHORT);
					realText.setText("" + maxLength);
				}

			}
		});
		yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				addText.setText(editText.getText().toString());
				dismiss();
			}
		});
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
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
	private void MyfindviewById() {
		editText = (EditText) mMenuView.findViewById(R.id.editpopu_edit);
		realText = (TextView) mMenuView.findViewById(R.id.realtext);
		maxText = (TextView) mMenuView.findViewById(R.id.maxtext);
		yes = (Button) mMenuView.findViewById(R.id.yes);
		no = (Button) mMenuView.findViewById(R.id.no);
	}
	public void showUp2(View v) {
		mMenuView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int popupHeight = mMenuView.getMeasuredHeight();
		int popupWidth = mMenuView.getMeasuredWidth();
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - popupHeight);

	}
	public void showSoft() {
		Handler handle = new Handler();
		handle.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(editText, 0);
			}
		}, 0);
	}
}
