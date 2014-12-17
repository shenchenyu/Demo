package com.leo.demo.ui.base;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.leo.demo.R;

public abstract class BaseTitleActivity extends Activity {
	protected Context ct;
	protected int layout;
	private static final List<BaseTitleActivity> mActivities = new LinkedList<BaseTitleActivity>();
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
	}
	protected abstract void initView();
	protected void initActionBar() {
	}
}
