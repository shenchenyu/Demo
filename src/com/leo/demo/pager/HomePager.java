package com.leo.demo.pager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.leo.demo.AddBillActivity;
import com.leo.demo.InvoiceActivity;
import com.leo.demo.R;
import com.leo.demo.bean.Bill;
import com.leo.demo.http.HttpHelper.HttpResult;
import com.leo.demo.utils.CommonUtil;
import com.leo.demo.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HomePager extends BasePager implements OnClickListener, OnItemClickListener {
	@ViewInject(R.id.bt_add_bill)
	private Button bt_add_bill;
	@ViewInject(R.id.lv_home_bill)
	private ListView mLvBills;
	@ViewInject(R.id.ll_no_data)
	private LinearLayout mLLNoData;
	@ViewInject(R.id.tv_link_add_bill)
	private TextView tvQuickAdd;
	private BillAdapter adapter;
	private List<Bill> mListBills;
	private List<Bill> mCacheBills;
	public HomePager(Context ct) {
		super(ct);
	}
	/**
	 * 初始化页面布局
	 */
	@Override
	public View initView() {
		//ViewPager
		View view = View.inflate(ct, R.layout.pager_home, null);
		ViewUtils.inject(this, view);
		bt_add_bill.setOnClickListener(this);
		mLvBills.setOnItemClickListener(this);
		mLvBills.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//if(firstVisibleItem==)
				if(firstVisibleItem==0){
					
				}else if(mListBills!=null&&totalItemCount==mListBills.size()){
					
				}
				LogUtils.d("firstItem:"+firstVisibleItem);
				LogUtils.d("visibleItemCount:"+visibleItemCount);
				LogUtils.d("totalItemCount:"+totalItemCount);
			}
		});
		initTitleBar(view);
		return view;
	}
	/**
	 * 初始化数据
	 */
	@Override
	public void initData() {
		mListBills = new ArrayList<Bill>();
		Bill bill;
		for (int i = 0; i <10; i++) {
			bill = new Bill();
			if(i==0){
				bill.setCompanyName("德丰财富");
				bill.setCategory("项目费用");
			}else if(i%2==0){
				bill.setCompanyName("性质探索");
				bill.setCategory("挖掘费用");
			}else{
				bill.setCompanyName("小马跳河");
				bill.setCategory("救援费用");
			}
			bill.setTotal("120"+1+".00");
			bill.setId(i+1);
			bill.setDueDate(System.currentTimeMillis()+"");
			bill.setInvoiceData(System.currentTimeMillis()+"");
			mListBills.add(bill);
		}
		//mListBills = getBills();
		// 1 首先判断本地缓存里面是否有数据。
		// 2 如果有数据的话，首先展示缓存里面的数据。。如果没有缓存数据。就直接展示对话框。 
		/*if(getCacheData()!=null){
			
		}
		// 3然后在去链接服务器。如果服务器有数据，就必须从服务器获取数据，然后替换本地的数据。
		getLastesData();*/
		if(mListBills!=null){
			mLLNoData.setVisibility(View.GONE);
			mLvBills.setVisibility(View.VISIBLE);
			//设置listView适配器显示数据
			adapter = new BillAdapter(ct,mListBills);
			mLvBills.setAdapter(adapter);
		}else{
			mLLNoData.setVisibility(View.VISIBLE);
			mLvBills.setVisibility(View.INVISIBLE);
			tvQuickAdd.setOnClickListener(this);
		}
	}
	/**
	 * 获取服务器请求数据
	 */
	private void getLastesData() {
		
		new AsyncTask<String, Void, HttpResult>(){
			@Override
			protected HttpResult doInBackground(String... params) {
				return null;
			}

			@Override
			protected void onPostExecute(HttpResult result) {
				if(result==null)
					return;
				parseData(result.getString());
			}
		};
	}
	/**
	 * 封装数据
	 */
	protected void parseData(String result) {
		
	}
	private List<Bill> getCacheData() {
		
		return mCacheBills;
	}
	List<Bill> result;
	/**
	 * 获取页面显示数据
	 * @return
	 */
	protected void processData(String res) {
		LogUtils.d(res);
	}
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.bt_add_bill:
			intent = new Intent(ct,InvoiceActivity.class );
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
		LogUtils.d("第"+position+"个被点击了");
	}
	/***
	 *	ListView数据适配器
	 * @author Scleo
	 *
	 */
	private class BillAdapter extends BaseAdapter{
		private List<Bill> mBills;
		public BillAdapter(Context ct,List<Bill> mListBills) {
			this.mBills = mListBills;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyHolder holder;
			if(convertView!=null){
				holder = (MyHolder) convertView.getTag();
			}else{
				convertView = View.inflate(ct, R.layout.item_home_bill, null);
				holder = new MyHolder();
				holder.tvContent = (TextView) convertView.findViewById(R.id.tv_item_bill_info);
				holder.tvDate = (TextView) convertView.findViewById(R.id.tv_item_bill_date);
				holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_item_bill_money);
				holder.cb = (CheckBox) convertView.findViewById(R.id.cb_item_bill_check);
				holder.icon = (ImageView) convertView.findViewById(R.id.iv_item_bill_icon);
				convertView.setTag(holder);
			}
			Bill bill = mBills.get(position);
			holder.tvContent.setText(bill.getCompanyName()+","+bill.getCategory());
			holder.tvDate.setText(CommonUtil.formate2String(bill.getDueDate()));
			holder.tvMoney.setText(bill.getTotal());
			return convertView;
		}
		@Override
		public int getCount() {
			return mBills.size();
		}
		@Override
		public Object getItem(int position) {
			return mBills.get(position);
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
	class MyHolder{ 
		public TextView tvMoney;
		public TextView tvDate;
		public TextView tvContent;
		public TextView tv;
		public Button delete;
		public CheckBox cb;
		public ImageView icon;
	}
}
