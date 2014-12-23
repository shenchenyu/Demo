package com.leo.demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.leo.demo.bean.User;
import com.leo.demo.http.HttpHelper;
import com.leo.demo.http.HttpHelper.HttpResult;
import com.leo.demo.ui.base.BaseTitleActivity;
import com.leo.demo.utils.CommonUtil;
import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.LogUtils;
import com.leo.demo.utils.PromptManager;
import com.leo.demo.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
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
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, super.layout);// 设置titleBar
																				// 布局文件
		initView();
	}

	@Override
	protected void initTitleBar() {
		// sTvTitle.setText(getResources().getString(R.string.tv_title_login));
	}

	protected void checkEdittext() {
		/*if (StringUtils.isEmpty(etUsername.getText().toString())) {
			PromptManager.showToast(ct, "用户名不能为空");
			return;
		}
		if (StringUtils.isEmpty(etPwd.getText().toString())) {
			PromptManager.showToast(ct, "密码不能为空");
			return;
		}*/
		checkLogin();
	}

	/**
	 * 解析结果，处理登录后续动作
	 * 
	 * @param result
	 *            服务器返回结果
	 */
	protected void checkLogin() {
		String url = ContentValue.SERVER_URL + "/" + ContentValue.LOGIN_URI;
		String json = CommonUtil.bean2Json(new User(etUsername.getText()
				.toString(), etPwd.getText().toString(),"password"));
		LogUtils.d("login-json:"+json);
		new AsyncTask<String, Void, HttpResult>() {
			@Override
			protected HttpResult doInBackground(String... params) {
				return HttpHelper.post(params[0], params[1],
						ContentValue.APPLICATION_JSON);
			}
			@Override
			protected void onPostExecute(HttpResult result) {
				LogUtils.d("LoginAct(code:"+result.getCode()+"login:"+result.getString());
				Intent it = new Intent(ct, MainActivity.class);
				if (result.getCode()==200) {
					User user = CommonUtil.json2Bean(result.getString(), User.class);
					if(user!=null)
						LogUtils.d("user:"+user.toString());
					it.putExtra("user", user);
					startActivity(it);
					return;
				}
				startActivity(it);
			};
		}.execute(url, json);
	}

	@Override
	protected void initView() {
		ViewUtils.inject(this);
		login = (Button) findViewById(R.id.btn_login);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkEdittext();
			}
		});
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}
