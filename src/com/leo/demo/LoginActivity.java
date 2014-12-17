package com.leo.demo;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.leo.demo.ui.base.BaseTitleActivity;
import com.leo.demo.utils.LogUtils;
import com.leo.demo.utils.PromptManager;
import com.leo.demo.utils.StringUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LoginActivity extends BaseTitleActivity {
	@ViewInject(R.id.et_pwd)
	private EditText etPwd;
	@ViewInject(R.id.et_username)
	private EditText etUsername;
	private Button login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				super.layout);// 设置titleBar 布局文件
		initView();
		
	}
	@Override
	protected void initTitleBar() {
		//sTvTitle.setText(getResources().getString(R.string.tv_title_login));
	}
	protected void checkLogin() {
		if(StringUtils.isEmpty(etUsername.getText().toString())){
			PromptManager.showToast(ct,"用户名不能为空");
			return ;
		}
		if(StringUtils.isEmpty(etPwd.getText().toString())){
			PromptManager.showToast(ct, "密码不能为空");
			return;
		}
		//访问网络校验用户是否登录成功
		RequestParams params =null;
		String url = "";
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException e, String msg) {
				LogUtils.d(msg);
				e.printStackTrace();
			}
			@Override
			public void onSuccess(ResponseInfo<String> info) {
				// TODO Auto-generated method stub
				String result = info.result;
				checkLogin(info.result);
			}
		});
		
	}
	/**
	 * 解析结果，处理登录后续动作
	 * @param result 服务器返回结果
	 */
	protected void checkLogin(String result) {
		
	}
	@Override
	protected void initView() {
		ViewUtils.inject(this);
		login = (Button) findViewById(R.id.btn_login);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkLogin();
			}
		});
	};
}
