package com.leo.demo.pager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.leo.demo.CompanyInf;
import com.leo.demo.R;
import com.lidroid.xutils.ViewUtils;


public class SettingPager extends BasePager{
	
	private Button btn;
	
	public SettingPager(Context ct) {
		super(ct);
	}

	@Override
	public View initView() {
		View view = View.inflate(ct,R.layout.setting, null);
		ViewUtils.inject(this, view);
		btn = (Button) view.findViewById(R.id.setting);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ct,CompanyInf.class );
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				ct.startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
