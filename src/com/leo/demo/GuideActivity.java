package com.leo.demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.leo.demo.bean.User;
import com.leo.demo.ui.base.BaseTitleActivity;
import com.leo.demo.utils.LogUtils;
import com.leo.demo.utils.MD5Utils;
import com.leo.demo.utils.PromptManager;
import com.leo.demo.utils.SharedPreferencesUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class GuideActivity extends BaseTitleActivity implements OnClickListener {
	@ViewInject(R.id.et_pin_guide)
	private EditText mEtPin;
	@ViewInject(R.id.btn_next_guide)
	private Button mBtnNext;
	@ViewInject(R.id.tv_skip_guide)
	private TextView mTvSkip;
	private String mPin;
	private final String PIN_CACHE = "Pin";
	private Intent intent;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_pin);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, layout);
		initView();
	};
	@Override
	protected void initActionBar() {
	}
	@Override
	protected void initView() {
		intent = getIntent();
		ViewUtils.inject(this);
		mBtnNext.setOnClickListener(this);
		mTvSkip.setOnClickListener(this);
		//隐藏显示按钮操作。
		mEtPin.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()>0)
				{
					mBtnNext.setVisibility(View.VISIBLE);
					mTvSkip.setVisibility(View.INVISIBLE);
				}else{
					mBtnNext.setVisibility(View.INVISIBLE);
					mTvSkip.setVisibility(View.VISIBLE);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				LogUtils.d("beforeTextChanged(s:"+s+"...start:"+start+"...after"+after+"...count:"+count+")");
			}
			@Override
			public void afterTextChanged(Editable s) {
				LogUtils.d("afterTextChanged(s:"+s+"....count)");
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		LogUtils.d("KeyCode:"+keyCode);
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next_guide:
			savePin2Cache();
			break;
		case R.id.tv_skip_guide:
			goNext();
			break;
		}
	}
	/**
	 * pin码设置成功，跳转到首页
	 */
	private void savePin2Cache(){
		if(mEtPin!=null)
			mPin = mEtPin.getText().toString();
		if(mPin.length()<6){
			PromptManager.showToast(ct, getResources().getString(R.string.str_pin_empty));
			return;
		}
		mPin = MD5Utils.digest(mPin);//加密Pin码
		LogUtils.d("Pin:"+mPin);
		SharedPreferencesUtils.saveString(ct, PIN_CACHE, mPin);
		goNext();
	}
	/**
	 * 跳转下一步骤
	 */
	private void goNext(){
		Bundle bundle = intent.getExtras();
		User user = (User) bundle.get("user");
		//开启意图跳转首页
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
}
