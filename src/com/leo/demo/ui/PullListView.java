package com.leo.demo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author Scleo
 */
public class PullListView extends ListView {

	public PullListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public PullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullListView(Context context) {
		super(context);
		init(context);
	}
	/*****初始数据****/
	private void init(Context context) {
		//初始化头部底部View
		
	}
}
