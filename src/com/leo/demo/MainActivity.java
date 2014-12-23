package com.leo.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.leo.demo.fragment.HomeFragment;

public class MainActivity extends FragmentActivity {
	private boolean TOKEN_FLAG = false;
	private HomeFragment mHomeFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
		
	}
	private void init() {
		//请求服务器校验Token信息是否过期。
		TOKEN_FLAG  = checkToken();
		mHomeFragment = new HomeFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.content,mHomeFragment ,"HomeFragment").commit();
		//getSupportFragmentManager().beginTransaction().replace(R.id.content,mTransFragment ,"TransFragment").commit();
	}
	/**
	 * 用户每次进到这个页面都会检查Token是否过期。
	 */
	private boolean checkToken() {
		return false;
	}
	/**
	 * 替换content显示内容
	 * @param fragment
	 */
	public void switchFragment(Fragment fragment){
		getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
	}
	/**
	 * 返回HomeFragment对象
	 * @return
	 */
	public HomeFragment getHomeFragment(){
		mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
		return mHomeFragment;
	}
}
