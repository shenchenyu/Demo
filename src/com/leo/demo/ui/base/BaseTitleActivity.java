package com.leo.demo.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leo.demo.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public abstract class BaseTitleActivity extends Activity {
	public Context ct;
	public int layout;
	@ViewInject(R.id.btn_left)
	protected Button sBtnLeft;
	@ViewInject(R.id.imgbtn_left)
	protected ImageButton sImgBtnLeft;
	@ViewInject(R.id.btn_right)
	protected ImageButton sImgBtnRight;
	@ViewInject(R.id.tv_txt_title)
	protected TextView sTvTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		ct = getApplicationContext();
		layout = R.layout.layout_title_bar;
		init();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	protected void init() {
		View view = View.inflate(ct, layout, null);
		ViewUtils.inject(view);
	}
	protected abstract void initView();
	protected void initActionBar() {
	}
}
