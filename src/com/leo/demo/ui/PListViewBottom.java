package com.leo.demo.ui;

import com.leo.demo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
/***
 * 
 * ListView下拉头部view
 * @author scleo
 *
 */
public class PListViewBottom extends LinearLayout {

	
	private  int mState = STATE_NORMAL;//默认状态 
	private static final int STATE_NORMAL = 0;
	private static final int STATE_REFRESHING = 1;
	private static final int STATE_READY = 2;
	public PListViewBottom(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PListViewBottom(Context context) {
		super(context);
		init(context);
	}

	/** 初始化 ***/
	private void init(Context context) {
	}
	//设置高度
	public void setVisiableHeight(int height){
		/*if(height<0)
			height=0;
		LinearLayout.LayoutParams lp = (LayoutParams) mLLayout.getLayoutParams();
		lp.height = height;
		mLLayout.setLayoutParams(lp);*/
	}
	public int getVisiableHeight(){
		//return mLLayout.getHeight();
		return 1;
	}
}
