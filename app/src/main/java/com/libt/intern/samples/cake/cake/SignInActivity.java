package com.libt.intern.samples.cake.cake;

import java.util.List;

import org.litepal.LitePal;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.libt.intern.R;
import com.libt.intern.activity.BaseActivity;
import com.libt.intern.samples.cake.bean.PeopleInfo;
import com.libt.intern.samples.cake.util.Constant;
import com.libt.intern.samples.cake.util.IsBarUtil;

public class SignInActivity extends BaseActivity {
	private EditText name;
	private EditText password;
	private EditText repassword;
	private Button sign;

	private SignInActivity instance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		IsBarUtil.isBar(this);

		instance = this;
		MyfindviewById();

		sign.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (name.getText().toString().length() != 0 && password.getText().toString().length() != 0
						&& password.getText().toString().equals(repassword.getText().toString())) {
					List<PeopleInfo> infoList = LitePal.where("name like ?", name.getText().toString())
							.find(PeopleInfo.class);
					boolean isName = infoList.size() > 0 ? true : false;
					if (isName) {
						Toast.makeText(instance, "The user already exists", Toast.LENGTH_SHORT).show();
					} else {
						PeopleInfo info = new PeopleInfo(null, null, null, name.getText().toString(), null,
								password.getText().toString(), null);
						info.save();
						Intent intent = new Intent(instance, RegisteActivity.class);
						intent.putExtra("name", name.getText().toString());
						intent.putExtra("password", password.getText().toString());
						setResult(Constant.REGISTRATION_RESULT_CODE, intent);
						finish();

					}

				} else if (name.getText().toString().length() == 0) {
					Toast.makeText(instance, "Please input user name", Toast.LENGTH_SHORT).show();
				} else if (password.getText().toString().length() == 0) {
					Toast.makeText(instance, "Please input password", Toast.LENGTH_SHORT).show();
				} else if (!password.getText().toString().equals(repassword.getText().toString())) {
					Toast.makeText(instance, "The two passwords do not match", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private void MyfindviewById() {
		name = (EditText) findViewById(R.id.sgin_name_edit);
		password = (EditText) findViewById(R.id.passwordNew);
		repassword = (EditText) findViewById(R.id.RePassword);
		sign = (Button) findViewById(R.id.sign_in);

	}
}
