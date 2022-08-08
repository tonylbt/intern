package com.libt.intern.samples.recycler;

import com.libt.intern.R;
import com.libt.intern.activity.BaseActivity;
import com.libt.intern.samples.recycler.recyclerutil.SharedPreferencesUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * TODO to liuyc， Modify Tips： params name is too simple
 */
public class MenuActivity extends BaseActivity {
	private RadioButton normalButton, adButton;
	private Button save;
	private EditText timeText;
	private String time;
	private RadioGroup group;
	private static final String AD = "ad";
	private static final String NORMAL = "normal";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		init();
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				time = timeText.getText().toString();
				SharedPreferencesUtil.getInstance(getApplicationContext()).putADTime(time);
				MenuActivity.this.finish();
			}
		});
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				switch (checkedId) {
					case R.id.normal :
						SharedPreferencesUtil.getInstance(getApplicationContext()).putModel(NORMAL);
						break;
					case R.id.ad :
						SharedPreferencesUtil.getInstance(getApplicationContext()).putModel(AD);
						break;
					default :
						break;
				}
			}
		});
	}

	public void init() {
		normalButton = (RadioButton) findViewById(R.id.normal);
		adButton = (RadioButton) findViewById(R.id.ad);
		save = (Button) findViewById(R.id.button);
		timeText = (EditText) findViewById(R.id.time);
		group = (RadioGroup) findViewById(R.id.group);

	}
}
