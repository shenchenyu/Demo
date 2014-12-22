package com.leo.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.leo.demo.bean.User;
import com.leo.demo.bean.VerifyCode;
import com.leo.demo.http.HttpHelper;
import com.leo.demo.http.HttpHelper.HttpResult;
import com.leo.demo.receiver.SmsReceiver;
import com.leo.demo.receiver.SmsReceiver.SmsResultListener;
import com.leo.demo.ui.base.BaseTitleActivity;
import com.leo.demo.utils.CommonUtil;
import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.PromptManager;
import com.leo.demo.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RegisterActivity extends BaseTitleActivity implements
		OnClickListener {
	@ViewInject(R.id.btn_done_regist)
	private Button regist;
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
	private SmsReceiver mReceiver;
	private static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	private IntentFilter mItFilter;
	private int mSeconds = 60;
	private boolean mTimerRunning = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, layout);// 设置titleBar
																		// 布局文件
		initView();
	}

	@Override
	protected void initView() {
		ViewUtils.inject(this);
		String localPhone = getPhone();
		regist.setOnClickListener(this);
		reqPhoneCode.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		mTitle.setText(getString(R.string.tv_title_regist));
		if (!StringUtils.isEmpty(localPhone))
			etUsername.setText(localPhone);
	}

	/**
	 * 获取本机号码
	 * 
	 * @return
	 */
	private String getPhone() {
		mTeleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		LogUtils.d(mTeleManager.getLine1Number());
		String phone = mTeleManager.getLine1Number();
		phone = phone.replace("+86", "");
		LogUtils.d("截取后的phone：" + phone);
		return phone;
	}

	/*** 点击事件 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_done_regist:// 检验注册
			checkVerifyCode();
			break;
		case R.id.btn_request_code:// 获取手机验证码
			requestVercicalCode();
			// gotoNext();
			break;
		case R.id.btn_left:// 标题栏返回按钮事件
			finish();
			break;
		}
	}

	/*** 校验验证码 */
	private void checkVerifyCode() {
		// PromptManager.showToast(this, "被点击了");
		String etCode = etVerivalCode.getText().toString();
		if (StringUtils.isEmpty(etCode)) {
			PromptManager.showToast(getApplicationContext(),
					R.string.str_vercode_empty);
		} else {
			if (etCode.length() < 6) {
				return;
			}
			// 请求服务器等待服务器返回结果
			String url = ContentValue.SERVER_URI + "/"
					+ ContentValue.VERICAL_REQUEST;
			url = url + "?" + ContentValue.VERICAL_PHONE + "="
					+ etUsername.getText().toString() + "&"
					+ ContentValue.VERICAL_REQUEST + "="
					+ etVerivalCode.getText().toString();
			new AsyncTask<String, Void, HttpResult>() {
				@Override
				protected void onPreExecute() {
					changeBotton(regist);
					showLoadDialog();
				};

				@Override
				protected HttpResult doInBackground(String... params) {
					return HttpHelper.get(params[0], params[1]);
				}

				@Override
				protected void onPostExecute(HttpResult result) {
					LogUtils.d("服务器返回校验结果：" + result);
					if (result != null && result.getCode() == 200) {
						parseJson2Result(result.getString());
					} else {
						// 请求异常，关闭加载框 ，关闭网络连接
						changeBotton(regist);
						if (dialog != null)
							dialog.dismiss();
						return;
					}
				}
			}.execute(url, ContentValue.APPLICATION_JSON);
		}
	}

	/**
	 * 将服务器返回的接送结果 解析json
	 * 
	 * @param result
	 */
	protected void parseJson2Result(String result) {
		VerifyCode vc = CommonUtil.json2Bean(result, VerifyCode.class);
		LogUtils.d("verifycode:" + vc.isVerifyResult());
		if (vc != null && vc.isVerifyResult()) {// 手机验证校验成功
			// 调用注册Api创建用户
			regist();
		} else {
			PromptManager.showToast(getApplicationContext(), getResources()
					.getString(R.string.str_vercode_error));
			return;
		}
		return;
	}

	/*** 注册用户 */
	private void regist() {
		String url = ContentValue.SERVER_URL + "/" + ContentValue.REGIST_URI;
		// 创建User Bean对象封装数据
		User user = new User();
		user.setPhone(etUsername.getText().toString());
		user.setPassword(etVerivalCode.getText().toString());
		user.setConfirmPassword(etVerivalCode.getText().toString());
		// 将对象转换成json字符串提交服务器
		String json = CommonUtil.bean2Json(user);
		new AsyncTask<String, Void, HttpResult>() {
			@Override
			protected HttpResult doInBackground(String... params) {
				return HttpHelper.post(params[0], params[1], params[2]);
			}

			@Override
			protected void onPostExecute(HttpResult result) {
				if (result != null) {
					if (result.getCode() == 200) {
						LogUtils.d("helper:" + result.getString());
						PromptManager.showToast(ct, ct.getResources()
								.getString(R.string.str_regist_success));
						User u = CommonUtil.json2Bean(result.getString(),
								User.class);
						goNext(u);
					} else {
						changeBotton(regist);
						if (dialog != null)
							dialog.dismiss();
						PromptManager.showToast(ct, result.getString());
						return;
					}
				} else {
					changeBotton(regist);
					if (dialog != null)
						dialog.dismiss();
					PromptManager.showToast(
							ct,
							ct.getResources().getString(
									R.string.str_server_error));
					return;
				}
			}
		}.execute(new String[] { url, json, ContentValue.APPLICATION_JSON });
	}

	/**
	 * 跳转下一步骤
	 * 
	 * @param user
	 */
	private void goNext(User user) {
		Intent intent = new Intent(this, GuideActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("user", user);
		intent.putExtras(bundle);
		// 方式1：调用webservice登录Api 登录并跳转
		// 方式2：直接跳转，省略登录步骤。
		startActivity(intent);
	}

	/**
	 * 请求验证码
	 */
	private void requestVercicalCode() {
		mTimerRunning = true;
		if (CommonUtil.isNetworkAvailable(getApplicationContext()) == ContentValue.NO_NETWORK) {
			PromptManager.showToast(getApplicationContext(), "无服务，请检测您的网络状态！");
		} else {
			String url = ContentValue.SERVER_URL + "/"
					+ ContentValue.VERICAL_REQUEST;
			new AsyncTask<String, Void, HttpResult>() {
				@Override
				protected void onPreExecute() {
					changeBotton(reqPhoneCode);
					// 注册广播监听短信验证码
					initReceiver();
				};

				@Override
				protected HttpResult doInBackground(String... params) {
					// 通知服务器发送短信验证码，更新button按钮动作。
					// CommonUtil.noticeSMSCode(etUsername.getText().toString())
					return HttpHelper.post(params[0], "=" + params[1],
							ContentValue.APPLICATION_FORM);
				}

				@Override
				protected void onPostExecute(HttpResult result) {
					if (result != null && result.getCode() == 200) {
						// com.leo.demo.utils.LogUtils.d(result.getString());
						PromptManager.showToast(
								RegisterActivity.this,
								getResources().getString(
										R.string.str_send_success));
					} else {
						PromptManager.showToast(
								RegisterActivity.this,
								getResources().getString(
										R.string.str_send_failed));
					}
				}
			}.execute(url, etUsername.getText().toString());
		}
	}

	protected void showLoadDialog() {
		dialog = new ProgressDialog(this);
		dialog.setMessage("请求中...请稍候...");
		dialog.setCanceledOnTouchOutside(false);
		// dialog.set
		dialog.show();
	}

	/**** 初始化广播接收者 ******/
	protected void initReceiver() {
		// TODO Auto-generated method stub
		if (mReceiver == null)
			mReceiver = new SmsReceiver();
		// 设置过滤器对象
		mItFilter = new IntentFilter(SMS_ACTION);
		mItFilter.setPriority(Integer.MAX_VALUE);// 设置广播优先级
		// 设置广播回调接口
		mReceiver.setOnReceiveSmsListener(new SmsResultListener() {
			@Override
			public void onReceive(String msg) {
				if (!StringUtils.isEmpty(msg)) {
					etVerivalCode.setText(msg);
				}
			}
		});
		// 注册广播
		registerReceiver(mReceiver, mItFilter);
	}

	/** 改变button样式 */
	private void changeBotton(View v) {
		if (v == reqPhoneCode) {
			countDown();
		} else {
			if (v.isEnabled()) {
				v.setEnabled(false);
				reqPhoneCode.setVisibility(View.INVISIBLE);
			} else {
				v.setEnabled(true);
				reqPhoneCode.setVisibility(View.VISIBLE);
			}
		}
	}
	/*** 倒计时 **/
	private void countDown() {
		if (mTaskThread == null)
			mTaskThread = new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg;
					while (mTimerRunning) {
						msg = Message.obtain();// 获取一个消息对象
						msg.arg1 = mSeconds;// 设置参数
						msg.what = 0;// 参数类型
						handle.sendMessage(msg);// 发送消息
						mSeconds--;
						try {
							Thread.sleep(1000);// 休眠1秒
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		mTaskThread.start();
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
				regist.setVisibility(View.VISIBLE);
			} else {
				reqPhoneCode.setClickable(true);
				reqPhoneCode
						.setBackgroundResource(R.drawable.verical_btn_clickable);
				reqPhoneCode.setText(getApplicationContext().getResources()
						.getString(R.string.btn_request_code));
				// login.setVisibility(View.INVISIBLE);
				mTimerRunning = false;
				// 销毁线程
				mSeconds = 60;
				mTaskThread = null;
			}
		}
	};
	private TelephonyManager mTeleManager;
	private Thread mTaskThread;
	private ProgressDialog dialog;

	@Override
	protected void onStop() {
		super.onStop();
		if (dialog != null)
			dialog.dismiss();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 注销广播
		if (mReceiver != null)
			unregisterReceiver(mReceiver);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (dialog != null)
			dialog.dismiss();
		changeBotton(regist);
	}
}
