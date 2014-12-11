package com.leo.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.SharedPreferencesUtils;
import com.leo.demo.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;

public class SplashActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		init();
	}
	private void init() {
		ViewUtils.inject(this);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		jumpOther();
	}
	private void jumpOther(){
		String auth_token = SharedPreferencesUtils.getString(this, ContentValue.SPFILE_TOKEN, "");
		Intent intent;
		if(!StringUtils.isEmpty(auth_token)){
			intent = new Intent(this,MainActivity.class);
			startActivity(intent);
		}else{
			intent = new Intent(this,WelcomeActivity.class);
			startActivity(intent);
		}
	}
	@Override
	public void onClick(View v) {
	}
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
}
