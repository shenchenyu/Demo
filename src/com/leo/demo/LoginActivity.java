package com.leo.demo;


import android.os.Bundle;
import android.view.Window;

import com.leo.demo.ui.base.BaseTitleActivity;

public class LoginActivity extends BaseTitleActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				super.layout);// 设置titleBar 布局文件
	}
	@Override
	protected void initActionBar() {
	}
	@Override
	protected void initView() {
		sTvTitle.setText(getResources().getString(R.string.tv_title_login));
	};
}
