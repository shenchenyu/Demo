﻿package com.leo.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.PromptManager;
import com.leo.demo.utils.SharedPreferencesUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WelcomeActivity extends Activity implements OnClickListener {
	private Context ct;
	@ViewInject(R.id.gv_home)
	private GridView mGridView;
	private WelcomeAdapter adapter;
	@ViewInject(R.id.tv_link_signin)
	private TextView mSignIn;
	@ViewInject(R.id.tv_link_signup)
	private TextView mSignUp;
	private static String[] mGvWelcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		// ZC
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		jumpOther();
		setContentView(R.layout.welcome_activity);
		ViewUtils.inject(this);
		init();
//		----------------leo------------------------
	}
	private void jumpOther(){
		boolean flag = SharedPreferencesUtils.getBoolean(this, ContentValue.HAD_LOGIN, true);
		if(!flag){
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	/**
	 * 检测用户登录是否过期
	 */
	private void checkLoginOutTime() {
		// TODO Auto-generated method stub
		
	}
	private void init() {
		
		ct = getApplicationContext();
		//4个导航内容
		mGvWelcome = new String[] {
				getResources().getString(R.string.gv_txt_1),
				getResources().getString(R.string.gv_txt_2),
				getResources().getString(R.string.gv_txt_3),
				getResources().getString(R.string.gv_txt_4), };
		adapter = new WelcomeAdapter();
		// 设置GridView适配器
		mGridView.setAdapter(adapter);
		mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					PromptManager.showToast(ct, R.string.gv_txt_1);
					break;
				case 1:
					PromptManager.showToast(ct, R.string.gv_txt_2);
					break;
				case 2:
					PromptManager.showToast(ct, R.string.gv_txt_3);
					break;
				case 3:
					PromptManager.showToast(ct, R.string.gv_txt_4);
					break;
				}
			}
		});
		mSignIn.setOnClickListener(this);
		//注册
		mSignUp.setOnClickListener(this);
	}
	class WelcomeAdapter extends BaseAdapter {
		private TextView tv;

		@Override
		public int getCount() {
			return mGvWelcome.length;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(WelcomeActivity.this,
					R.layout.item_welcome, null);
			ViewUtils.inject(view);
			tv = (TextView) view.findViewById(R.id.tv_item_wel);
			tv.setText(mGvWelcome[position]);
			return view;
		}

		@Override
		public Object getItem(int position) {
			return mGvWelcome[position];
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_link_signin://登陆
			Intent intent = new Intent(ct, LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_link_signup://注册
			
			break;	
		}	
	}
}