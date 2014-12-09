package com.leo.demo;


import com.leo.demo.ui.base.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

public class RegistActivity extends BaseActivity {
	
	private ActionBar mActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
	
		
	}
	@Override
	protected void initActionBar() {
		//mActionBar = get
	};
}
