package com.leo.demo.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;


public class SettingPager extends BasePager {
	
	public SettingPager(Context ct) {
		super(ct);
	}

	@Override
	public View initView() {
		TextView textView = new TextView(ct);
		textView.setTextSize(16);
		textView.setTextColor(Color.RED);
		textView.setText("设置中心");
		return textView;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
