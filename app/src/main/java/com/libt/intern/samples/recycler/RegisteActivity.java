package com.libt.intern.samples.recycler;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.libt.intern.R;
import com.libt.intern.activity.BaseActivity;
import com.libt.intern.samples.cake.bean.PeopleInfo;
import com.libt.intern.samples.cake.cake.SignInActivity;
import com.libt.intern.samples.cake.control.MainListPopupWindow;
import com.libt.intern.samples.cake.util.CircleHeadPhotoUtil;
import com.libt.intern.samples.cake.util.Constant;
import com.libt.intern.samples.cake.util.IsBarUtil;
import com.libt.intern.samples.cake.util.SharedPreferencesUtil;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegisteActivity extends BaseActivity implements View.OnClickListener, MainListPopupWindow.Listener {
	private ImageView headImage;
	private EditText name;
	private EditText password;
	private ImageButton cancel;
	private ImageButton unfold;
	private Button login;
	private TextView forgetpassword;
	private TextView newclient;
	private CheckBox rememberBox;
	private SharedPreferencesUtil sharedPreferencesUtil;
	private String userName;
	private String userPassword;
	private boolean isFirstRun;
	private List<String> userList;
	private Map<String, String> userMap;
	private MainListPopupWindow mainlistpopupwindow;
	private RegisteActivity instance = null;
	private LoadingDailog.Builder loadBuilder;
	private LoadingDailog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Connector.getDatabase();// creat database
		ActivityCompat.requestPermissions(RegisteActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQUEST_STORED_CODE);
		myFindViewById();
		intiVariable();
		IsBarUtil.isBar(instance);
		myOnClick();
	}

	private void myFindViewById() {
		headImage = (ImageView) findViewById(R.id.imageView3);
		name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.password);
		cancel = (ImageButton) findViewById(R.id.cancel);
		unfold = (ImageButton) findViewById(R.id.unfold);
		login = (Button) findViewById(R.id.login);
		forgetpassword = (TextView) findViewById(R.id.forgetpassword);
		newclient = (TextView) findViewById(R.id.newclient);
		rememberBox = (CheckBox) findViewById(R.id.remember_the_password);
		headImage.setImageResource(R.drawable.head);
	}
	private void myOnClick() {
		login.setOnClickListener(instance);
		unfold.setOnClickListener(instance);
		newclient.setOnClickListener(instance);
		name.addTextChangedListener(textWatcher);
	}
	private void intiVariable() {
		instance = this;
		sharedPreferencesUtil = SharedPreferencesUtil.getInstance(getApplicationContext());
		isFirstRun = sharedPreferencesUtil.getBoolean(SharedPreferencesUtil.IS_FRIST_RUN);
		userList = new ArrayList<String>();
		userMap = new HashMap<String, String>();
		userName = sharedPreferencesUtil.getString(SharedPreferencesUtil.USER_NAME);
		userPassword = sharedPreferencesUtil.getString(SharedPreferencesUtil.USER_PASSWORD);
		mainlistpopupwindow = new MainListPopupWindow(instance, userList, userName, instance);
		isFirstRun();
		insertToUserList();
		 loadBuilder=new LoadingDailog.Builder(this)
		.setMessage("Wait...")
		.setCancelable(true)
		.setCancelOutside(true);
		dialog=loadBuilder.create();
	}
	private void isFirstRun() {
		if (!isFirstRun) {
			name.setText("");
			password.setText("");
			unfold.setEnabled(false);

		} else {
			unfold.setEnabled(true);
			name.setText(userName);
			if (sharedPreferencesUtil.getBoolean(SharedPreferencesUtil.IS_AUTO)) {
				password.setText(userPassword);
				Intent intent = new Intent(RegisteActivity.this, RecyclerActivity.class);
				startActivityForResult(intent,Constant.REQUEST_PEOPLEACTIVITY_CODE);
			}
			if (sharedPreferencesUtil.getBoolean(SharedPreferencesUtil.IS_REMEBER_PASSWORD)) {
				password.setText(userPassword);
				rememberBox.setChecked(true);
			} else {
				password.setText("");
				rememberBox.setChecked(false);
			}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.login :
				userName = name.getText().toString();
				userPassword = password.getText().toString();
				List<PeopleInfo> list = LitePal.select("password").where("name like ?", userName)
						.find(PeopleInfo.class);
				boolean isName = list.size() > 0 ? true : false;
				if (userName.length() == 0 || userPassword.length() == 0) {
					Toast.makeText(RegisteActivity.this, "Please input username or password", Toast.LENGTH_SHORT).show();
				} else if (!isName) {
					Toast.makeText(RegisteActivity.this, "The user does not exist", Toast.LENGTH_SHORT).show();
				} else if (!userPassword.equals(list.get(0).getPassword())) {
					Toast.makeText(RegisteActivity.this, "Password is wrong", Toast.LENGTH_SHORT).show();
				} else {
					dialog.show();

					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							sharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_AUTO, true);
							Intent intent = new Intent(RegisteActivity.this, RecyclerActivity.class);
							//startActivityForResult(intent,Constant.REQUEST_PEOPLEACTIVITY_CODE);
							startActivity(intent);
							dialog.dismiss();
						}
					}, 500);
					sharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_FRIST_RUN, true);
					sharedPreferencesUtil.putString(SharedPreferencesUtil.USER_NAME, userName);
					sharedPreferencesUtil.putString(SharedPreferencesUtil.USER_PASSWORD, userPassword);
					if (rememberBox.isChecked()) {
						sharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_REMEBER_PASSWORD, true);
					} else {
						sharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_REMEBER_PASSWORD, false);
					}
				}
				break;
			case R.id.cancel :
				name.setText("");
				break;
			case R.id.unfold :
					mainlistpopupwindow = new MainListPopupWindow(RegisteActivity.this, userList,
							name.getText().toString(), RegisteActivity.this);
				if (mainlistpopupwindow.isShowing()) {
					isDisplayedOfControl(true);
				} else {
					mainlistpopupwindow.showDown(RegisteActivity.this.findViewById(R.id.v1));
					isDisplayedOfControl(false);
				}
				break;
			case R.id.newclient :
				Intent intent = new Intent(RegisteActivity.this, SignInActivity.class);
				startActivityForResult(intent, Constant.REQUEST_REGISTRATION_CODE );
				break;
		}
	}

	private void insertToUserList() {
		List<PeopleInfo> listOfPeopleInfo = LitePal.select("name", "password").find(PeopleInfo.class);
		for (int i = 0; i < listOfPeopleInfo.size(); i++) {
			userList.add(listOfPeopleInfo.get(i).getName());
			userMap.put(listOfPeopleInfo.get(i).getName(), listOfPeopleInfo.get(i).getPassword());
		}
	}

	@Override
	public void onPopupWindowDismissListener() {
		isDisplayedOfControl(true);

	}

	@Override
	public void onItemClickListener(int position) {
		userName = userList.get(position);
		name.setText(userName);
		password.setText("");
		isDisplayedOfControl(true);
		mainlistpopupwindow.dismiss();
		if(userName.length() != 0){
			name.setSelection(userName.length());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

			case Constant.REQUEST_REGISTRATION_CODE :
				if (Constant.REGISTRATION_RESULT_CODE == resultCode) {
					userName = data.getStringExtra("name");
					name.setText(userName);
					userPassword = data.getStringExtra("password");
					password.setText(userPassword);
					userList.add(data.getStringExtra("name"));
					userMap.put(data.getStringExtra("name"), data.getStringExtra("password"));
					unfold.setEnabled(true);
//					mainlistpopupwindow.mListPopupWindowAdapter.notifyDataSetChanged();
				}
				break;
			case Constant.REQUEST_PEOPLEACTIVITY_CODE:
				if(Constant.REGISTRATION__PEOPLEACTIVITY_CODE == resultCode)
					instance.finish();
				break;
			default :
				break;
		}
	}
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onResume() {
		super.onResume();
		File headFile = new File(getExternalCacheDir() + userName + ".jpg");//
		if (headFile.exists()) {
			CircleHeadPhotoUtil.setCirclePhoto(headFile.getPath(), headImage);
		} else {
			headImage.setImageResource(R.drawable.head);
		}
		if(userName.length() != 0){
			name.setSelection(userName.length());
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		// Toast.makeText(instance,"onPause()",Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// Toast.makeText(instance,"onRestart()",Toast.LENGTH_SHORT).show();

	}
	private void isDisplayedOfControl(boolean isDisplayed) {
		if (isDisplayed) {
			password.setVisibility(View.VISIBLE);
			RegisteActivity.this.findViewById(R.id.v1).setVisibility(View.VISIBLE);
			RegisteActivity.this.findViewById(R.id.v2).setVisibility(View.VISIBLE);
			login.setVisibility(View.VISIBLE);
			RegisteActivity.this.findViewById(R.id.forgetpassword).setVisibility(View.VISIBLE);
			RegisteActivity.this.findViewById(R.id.newclient).setVisibility(View.VISIBLE);
			unfold.setBackgroundResource(R.drawable.unfold);
		} else {
			password.setVisibility(View.INVISIBLE);
			RegisteActivity.this.findViewById(R.id.v1).setVisibility(View.INVISIBLE);
			RegisteActivity.this.findViewById(R.id.v2).setVisibility(View.INVISIBLE);
			login.setVisibility(View.INVISIBLE);
			RegisteActivity.this.findViewById(R.id.forgetpassword).setVisibility(View.INVISIBLE);
			RegisteActivity.this.findViewById(R.id.newclient).setVisibility(View.INVISIBLE);
			unfold.setBackgroundResource(R.drawable.fold);

		}

	}
	TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			password.setText("");
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (userMap.containsKey(s.toString()))
				password.setText(userMap.get(s.toString()));
			File headFile = new File(getExternalCacheDir() + s.toString() + ".jpg");
			if (headFile.exists()) {
				CircleHeadPhotoUtil.setCirclePhoto(headFile.getPath(), headImage);
			} else {
				headImage.setImageResource(R.drawable.head);
			}
		}
	};

}