package com.leo.demo;

import com.leo.demo.utils.PromptManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SplashActivity extends Activity implements OnClickListener {
	
	@ViewInject(R.id.btn_left)
	private Button mBtnBack;
	@ViewInject(R.id.tv_txt_title)
	private TextView mTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 设置activity标题栏自定义
		setContentView(R.layout.activity_splash);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.layout_title_bar);// 设置自定义布局页面
		init();
	}

	private void init() {
		ViewUtils.inject(this);
		ViewUtils.inject(View.inflate(getApplicationContext(),
				R.layout.layout_title_bar, null));
		mBtnBack.setOnClickListener(this);
		mTitle.setText("测试Title");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			PromptManager.showToast(getApplicationContext(), "返回按钮");
			break;
		default:
			break;
		}
	}
}
