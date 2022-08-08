package com.libt.intern.offerwall;

import android.os.Bundle;
import android.view.View;

import com.libt.intern.R;
import com.libt.intern.activity.BaseActivity;

public class OfferWallActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offerwall);

		initView();
	}

	public void initView() {
		findViewById(R.id.button_open_ow).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				OfferWallHelper.showOfferWall(OfferWallActivity.this);
			}
		});
	}

}
