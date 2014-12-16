package com.leo.demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.leo.demo.ui.base.BaseTitleActivity;
import com.leo.demo.utils.LogUtils;
import com.leo.demo.utils.MD5Utils;
import com.leo.demo.utils.PromptManager;
import com.leo.demo.utils.SharedPreferencesUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class GuideActivity extends BaseTitleActivity implements OnClickListener {
	@ViewInject(R.id.et_pin_1)
	private EditText etPin1;
	@ViewInject(R.id.et_pin_2)
	private EditText etPin2;
	@ViewInject(R.id.et_pin_3)
	private EditText etPin3;
	@ViewInject(R.id.et_pin_4)
	private EditText etPin4;
	@ViewInject(R.id.et_pin_5)
	private EditText etPin5;
	@ViewInject(R.id.et_pin_6)
	private EditText etPin6;
	@ViewInject(R.id.et_pin_guide)
	private EditText mEtPin;
	@ViewInject(R.id.btn_next_guide)
	private Button mBtnNext;
	@ViewInject(R.id.tv_skip_guide)
	private TextView mTvSkip;
	private String mPin;
	private final String PIN_CACHE = "Pin";
	/*protected Button mBtnLeft;
	protected ImageButton mImgBtnLeft;
	protected ImageButton mImgBtnRight;
	protected TextView mTvTitle;*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_pin);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, layout);
		initView();
	};
	@Override
	protected void initActionBar() {
		/*View view = View.inflate(ct, layout, null);
		mTvTitle =(TextView) view.findViewById(R.id.tv_txt_title);
		mBtnLeft = (Button) view.findViewById(R.id.btn_left);
		mImgBtnLeft = (ImageButton) view.findViewById(R.id.imgbtn_left);
		mImgBtnRight = (ImageButton) view.findViewById(R.id.imgbtn_right);
		mBtnLeft.setVisibility(View.INVISIBLE);
		mTvTitle.setText(getResources().getString(R.string.tv_title_pin));*/
	}
	@Override
	protected void initView() {

		ViewUtils.inject(this);
		sTvTitle.setText(getResources().getString(R.string.tv_title_pin));
		mBtnNext.setOnClickListener(this);
		mTvSkip.setOnClickListener(this);
		mEtPin.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//LogUtils.d("onTextChanged(s:"+s+"...start:"+start+"...before"+before+"...count:"+count+")");
				if(s.length()>0)
				{
					mBtnNext.setVisibility(View.VISIBLE);
				}else{
					mBtnNext.setVisibility(View.INVISIBLE);
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
		//super.onk
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
		mPin = MD5Utils.digest(mPin);
		SharedPreferencesUtils.saveString(ct, PIN_CACHE, mPin);
		goNext();
	}
	/**
	 * 忽略此步跳转下一步骤
	 */
	private void goNext(){
		//开启意图跳转首页
	}
}
