package com.leo.demo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class TitleBar extends View {
	private LinearLayout title_bar;
	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public TitleBar(Context context) {
		super(context);
	}
	
	private void initView() {
		
	}
}
