package com.libt.intern.samples.cake.cake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.litepal.LitePal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.libt.intern.R;
import com.libt.intern.activity.BaseActivity;
import com.libt.intern.samples.cake.adapter.PeopleAdapter;
import com.libt.intern.samples.cake.bean.PeopleInfo;
import com.libt.intern.samples.cake.util.Constant;
import com.libt.intern.samples.cake.util.IsBarUtil;
import com.libt.intern.samples.cake.util.SharedPreferencesUtil;

/**
 * Created by Cake on 2018/8/6.
 */

/**
 * TODO to peiyun， Modify Tips：
 * 1. Simple param name.
 * 2. Modify load data option to subThread;
 */
public class PeopleActivity extends BaseActivity implements View.OnClickListener {
	private ListView listView;
	private TextView welcom;
	private TextView edit;
	private TextView exit;

	private SharedPreferencesUtil sharedPreferencesUtil;
	private String userName = "";

	private List<PeopleInfo> peopleList;
	private PeopleAdapter peopleAdapter;

	private Handler handler;
	private List<PeopleInfo> infoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.people_list);
		IsBarUtil.isBar(this);

		myFindViewById();

		intiVariable();

		edit.setOnClickListener(this);
		exit.setOnClickListener(this);

	}

	private void intiVariable() {
		peopleList = new ArrayList<>();
		peopleAdapter = new PeopleAdapter(peopleList, PeopleActivity.this);
		insertToPeopleList();
		listView.setAdapter(peopleAdapter);
		sharedPreferencesUtil = SharedPreferencesUtil.getInstance(getApplicationContext());
		userName = sharedPreferencesUtil.getString(SharedPreferencesUtil.USER_NAME);
		welcom.setText(userName);
		new Thread(new Runnable() {
			@Override
			public void run() {
				//.where("name like ?", userName)
				infoList = LitePal.findAll(PeopleInfo.class);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}).start();
	}

	private void myFindViewById() {
		listView = (ListView) findViewById(R.id.listview);
		welcom = (TextView) findViewById(R.id.welcom);
		edit = (TextView) findViewById(R.id.edit);
		exit = (TextView) findViewById(R.id.exit);
	}

	private void insertToPeopleList() {
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what){
					case 1:
						for (int i = 0; i < infoList.size(); i++) {
							peopleList.add(infoList.get(i));
						}
						Collections.sort(peopleList);
						peopleAdapter.notifyDataSetChanged();
						break;
				}

			}
		};

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.edit :
				Intent intent = new Intent(PeopleActivity.this, EditProfileActivity.class);
				intent.putExtra("userName",userName);
				startActivityForResult(intent, Constant.REQUEST_EDIT_CODE);
				break;
			case R.id.exit :
				sharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_AUTO, false);
				Intent intentExit = new Intent(PeopleActivity.this, RegisteActivity.class);
				setResult(Constant.REGISTRATION__EXIT_CODE,intentExit);
				finish();
				break;
			default :
				break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case Constant.REQUEST_EDIT_CODE :
				if (Constant.RESULT_EDIT_CODE == resultCode) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							infoList = LitePal.findAll(PeopleInfo.class);
							peopleList.clear();
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						}
					}).start();
				}
				break;
			default :

				break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			sharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_AUTO, true);
			Intent intent1 = new Intent(PeopleActivity.this, RegisteActivity.class);
			setResult(Constant.REGISTRATION__PEOPLEACTIVITY_CODE,intent1);
			finish();
			//System.exit(0);


		}
		return super.onKeyDown(keyCode, event);
	}
}
