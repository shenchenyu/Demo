package com.leo.demo.pager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.leo.demo.AccountPayable;
import com.leo.demo.AddBillActivity;
import com.leo.demo.R;
import com.leo.demo.bean.Bill;
import com.leo.demo.ui.base.BasePager;
import com.leo.demo.utils.CommonUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HomePager extends BasePager implements OnClickListener, OnItemClickListener {
	@ViewInject(R.id.bt_add_bill)
	private Button bt_add_bill;
	@ViewInject(R.id.lv_home_bill)
	private ListView lvBills;
	
	@ViewInject(R.id.ll_no_data)
	private LinearLayout mLLNoData;
	@ViewInject(R.id.tv_link_add_bill)
	private TextView tvQuickAdd;
	private BillAdapter adapter;
	private List<Bill> mListBills;
	public HomePager(Context ct) {
		super(ct);
	}
	/**
	 * 初始化页面布局
	 */
	@Override
	public View initView() {
		View view = View.inflate(ct, R.layout.pager_home, null);
		ViewUtils.inject(this, view);
		bt_add_bill.setOnClickListener(this);
		lvBills.setOnItemClickListener(this);
		initTitleBar(view);
		return view;
	}
	/**
	 * 初始化数据
	 */
	@Override
	public void initData() {
		mListBills = getBills();
		// 1 首先判断本地缓存里面是否有数据。
		getCacheData();
		// 2 如果有数据的话，首先展示缓存里面的数据。。如果没有缓存数据。就直接展示对话框。 
		// 3然后在去链接服务器。如果服务器有数据，就必须从服务器获取数据，然后替换本地的数据。
		if(mListBills!=null){
			mLLNoData.setVisibility(View.INVISIBLE);
			lvBills.setVisibility(View.VISIBLE);
			//设置listView适配器显示数据
			adapter = new BillAdapter(mListBills);
			lvBills.setAdapter(adapter);
		}else{
			mLLNoData.setVisibility(View.VISIBLE);
			lvBills.setVisibility(View.INVISIBLE);
			tvQuickAdd.setOnClickListener(this);
		}
	}
	private List<Bill> getCacheData() {
		
		return null;
	}
	/**
	 * 获取页面显示数据
	 * @return
	 */
	private List<Bill> getBills() {
		List<Bill> result;
		if(CommonUtil.isNetworkAvailable(ct)==0){//未找到网络，抓取本地缓存数据
			//getCacheData();//获取本地数据业务逻辑
			return null;
		}else{
			//连s接网络，请求服务器获取最新的数据。
			result = new ArrayList<Bill>();
		}
		return result;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.bt_add_bill:
			intent = new Intent(ct,AccountPayable.class );
			break;
		case R.id.tv_link_add_bill:
			intent = new Intent(ct,AddBillActivity.class);
			break;
		}
		if(intent!=null){
			ct.startActivity(intent);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
	/***
	 *	首页ListView数据适配器
	 * @author Scleo
	 *
	 */
	private class BillAdapter extends BaseAdapter{
		private List<Bill> mBills;
		public BillAdapter(List<Bill> mListBills) {
			//TODO
			this.mBills = mListBills;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}
		@Override
		public int getCount() {
			return mBills.size();
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
}
