package com.leo.demo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.leo.demo.http.NetUtils;
import com.leo.demo.utils.CommonUtil;
import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.PromptManager;
import com.leo.demo.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RegisterActivity extends Activity implements OnClickListener {
	@ViewInject(R.id.btn_done_login)
	private Button login;
	@ViewInject(R.id.btn_request_code)
	private Button reqPhoneCode;
	@ViewInject(R.id.et_phone_number)
	private EditText etUsername;
	@ViewInject(R.id.et_verical_code)
	private EditText etVerivalCode;
	@ViewInject(R.id.btn_left)
	private Button mBtnBack;
	@ViewInject(R.id.tv_txt_title)
	private TextView mTitle;
	private int mSeconds = 60;
	private boolean mTimerRunning = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_regist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.layout_title_bar);// 设置titleBar 布局文件
		init();
	}
	private void init() {
		ViewUtils.inject(this);
		String localPhone = getPhone();
		login.setOnClickListener(this);
		reqPhoneCode.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		mTitle.setText(getString(R.string.tv_title_regist));
		if (!StringUtils.isEmpty(localPhone))
			etUsername.setText(localPhone);
	}
	private String getPhone() {
		mTeleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		LogUtils.d(mTeleManager.getLine1Number());
		return mTeleManager.getLine1Number();
	}
	/**
	 * 点击事件
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_done_login:// 登陆
			checkLogin();
			break;
		case R.id.btn_request_code:// 获取
			requestVercicalCode();
			// gotoNext();
			break;
		case R.id.btn_left:// 标题栏返回按钮事件
			finish();
			break;
		}
	}

	private void checkLogin() {
		// 1.获取验证码输入的内容，并校验是否与服务器一致
		String etCode = etVerivalCode.getText().toString();
		if (StringUtils.isEmpty(etCode)) {
			PromptManager.showToast(getApplicationContext(),
					R.string.str_vercode_empty);
		} else {
			// 请求服务器等待服务器返回结果
			new AsyncTask<String, Void, String>(){
				@Override
				protected void onPreExecute() {
					int networkAvailable = CommonUtil.isNetworkAvailable(getApplicationContext());
					if (networkAvailable == ContentValue.NO_NETWORK) {
						PromptManager.showToast(getApplicationContext(), "无服务，请检测您的网络状态！");
						cancel(true);
					} 
				}
				@Override
				protected void onPostExecute(String result) {
					JSONObject json;
					try {
						json = new JSONObject(result);
						if (!json.getBoolean("VerifyResult")) {
							PromptManager.showToast(getApplicationContext(),
									R.string.str_vercode_error);
						} else {
							// 创建意图跳转界面s
							Intent intent = new Intent(getApplicationContext(),
									MainActivity.class);
							startActivity(intent);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				@Override
				protected String doInBackground(String... params) {
					String url = ContentValue.SERVER_URI+"/"+ContentValue.VERICAL_REQUEST;
					url = url+"?"+ContentValue.VERICAL_PHONE+"="+params[0]+"&"+ContentValue.VERICAL_REQUEST+"="+params[1];
					LogUtils.d("url:"+url);
					return NetUtils.doGetOfHttpClient(url);
				}}.execute(new String[]{etUsername.getText().toString(),
						etVerivalCode.getText().toString()});
		}
	}

	/**
	 * 请求验证码
	 */
	private void requestVercicalCode() {
		mTimerRunning = true;
		if (CommonUtil.isNetworkAvailable(getApplicationContext()) == ContentValue.NO_NETWORK) {
			PromptManager.showToast(getApplicationContext(), "无服务，请检测您的网络状态！");
		} else {
			new AsyncTask<String, Void, String>() {
				@Override
				protected String doInBackground(String... params) {
					// 通知服务器发送短信验证码，更新button按钮动作。
					CommonUtil.noticeSMSCode(etUsername.getText().toString());
					return null;
				}
			}.execute("");
			changeBotton();
		}
	}

	/**
	 * 改变button样式。
	 */
	private void changeBotton() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg;
				while (mTimerRunning) {
					msg = Message.obtain();
					msg.arg1 = mSeconds;
					msg.what = 0;
					handle.sendMessage(msg);
					mSeconds--;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	private Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 设置登陆按钮显示状态
			if (mTimerRunning && msg.arg1 >= 0) {
				reqPhoneCode.setClickable(false);
				reqPhoneCode
						.setBackgroundResource(R.drawable.verical_btn_unclickable);
				reqPhoneCode.setText("("
						+ msg.arg1
						+ ")"
						+ getApplicationContext().getResources().getString(
								R.string.btn_request_recode));
				login.setVisibility(View.VISIBLE);
			} else {
				reqPhoneCode.setClickable(true);
				reqPhoneCode
						.setBackgroundResource(R.drawable.verical_btn_clickable);
				reqPhoneCode.setText(getApplicationContext().getResources()
						.getString(R.string.btn_request_code));
				// login.setVisibility(View.INVISIBLE);
				mTimerRunning = false;
			}
		}
	};
	private TelephonyManager mTeleManager;
}
