package com.leo.demo.pager;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leo.demo.AddBillActivity;
import com.leo.demo.InvoiceActivity;
import com.leo.demo.R;
import com.leo.demo.bean.Bill;
import com.leo.demo.bean.Bill1;
import com.leo.demo.http.HttpHelper;
import com.leo.demo.http.HttpHelper.HttpResult;
import com.leo.demo.ui.PullListView;
import com.leo.demo.ui.PullListView.IPListViewListener;
import com.leo.demo.utils.CommonUtil;
import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.LogUtils;
import com.leo.demo.utils.SharedPreferencesUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HomePager extends BasePager implements OnClickListener, OnItemClickListener, IPListViewListener {
	@ViewInject(R.id.bt_add_bill)
	private Button bt_add_bill;
	@ViewInject(R.id.lv_home_bill)
	private PullListView mLvBills;
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
		mLvBills.setPullLoadEnable(true);
		mLvBills.setPullRefreshEnable(true);
		mLvBills.setListViewListener(this);
		initTitleBar(view);
		return view;
	}
	protected void refresh() {
		//mLvBills.stopRefresh();
	}
	protected void loadMore() {
		mLvBills.stopLoadMore();
	}
	/**
	 * 初始化数据
	 */
	@Override
	public void initData() {
		mListBills = new ArrayList<Bill>();
		//mListBills = getBills();
		// 1 首先判断本地缓存里面是否有数据。
		// 2 如果有数据的话，首先展示缓存里面的数据。。如果没有缓存数据。就直接展示对话框。 
		/*if(getCacheData()!=null){
		}*/
		// 3然后在去链接服务器。如果服务器有数据，就必须从服务器获取数据，然后替换本地的数据。
		getLastesData();
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
		final String url = ContentValue.SERVER_URL+"/"+ContentValue.BILL_GETALL;
		new AsyncTask<String, Void, HttpResult>(){
			@Override
			protected HttpResult doInBackground(String... params) {
				HttpResult result = HttpHelper.get(url, ContentValue.APPLICATION_JSON);
				parseData(result.getString());
				return result;
			}
			@Override
			protected void onPostExecute(HttpResult result) {
				mListBills = null;
				/*if(result==null)
					return;*/
			}
		}.execute();
	}
	/**
	 * 封装数据
	 */
	protected void parseData(String result) {
		SharedPreferencesUtils.saveString(ct, "json", result);
		LogUtils.d("数据："+result);
		String bill = result;
		LogUtils.d("bill:"+bill);
		String str = bill.substring(1, bill.length()-1);
		LogUtils.d("str:"+str);
		Gson gson = new Gson();
		List<Bill1> listBill  = gson.fromJson(bill, new TypeToken<ArrayList<Bill1>>(){}.getType());
		if(listBill!=null){
			
		}
	}
	private List<Bill> getCacheData() {
		return mCacheBills;
	}
	List<Bill1> result;
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
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		
	}
}
