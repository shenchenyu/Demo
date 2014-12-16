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

public abstract class BaseTitleActivity extends Activity {
	protected Context ct;
	protected int layout;
	protected Button sBtnLeft;
	protected ImageButton sImgBtnLeft;
	protected ImageButton sImgBtnRight;
	protected TextView sTvTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		ct = getApplicationContext();
		layout = R.layout.layout_title_bar;
		initTitleBar();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	protected void initTitleBar() {
		View view = View.inflate(ct, layout, null);
		sTvTitle =(TextView) view.findViewById(R.id.tv_txt_title);
		sBtnLeft = (Button) view.findViewById(R.id.btn_left);
		sImgBtnLeft = (ImageButton) view.findViewById(R.id.imgbtn_left);
		sImgBtnRight = (ImageButton) view.findViewById(R.id.imgbtn_right);
	}
	protected abstract void initView();
	protected void initActionBar() {
	}
}
