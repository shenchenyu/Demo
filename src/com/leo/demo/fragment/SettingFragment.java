package com.leo.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.leo.demo.R;
import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.UIUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SettingFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView view = new TextView(getActivity());
		view.setText("Setting设置中心");
		return view;
	}
	
	
}
