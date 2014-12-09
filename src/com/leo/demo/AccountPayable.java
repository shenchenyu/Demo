package com.leo.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class AccountPayable extends Activity implements OnClickListener{
	private ImageButton ibtn_vendor;
	private ImageButton ibtn_terms;
	private final int VENDOR_FLAG = 0;
	private final int TERMS_FLAG = 1;
	private PopupWindow pop;
	private List<String> popList;
	private ListView listView;
	private EditText et_vendor;
	private EditText et_terms;
	private boolean isShow = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_payable);
		et_vendor = (EditText) findViewById(R.id.et_vendor);
		et_terms = (EditText) findViewById(R.id.et_terms);
		
		ibtn_vendor = (ImageButton) findViewById(R.id.ibtn_vendor_dropDown);
		ibtn_vendor.setOnClickListener(this);
		
		ibtn_terms = (ImageButton) findViewById(R.id.ibtn__terms_dropDown);
		ibtn_terms.setOnClickListener(this);
		
		listView = new ListView(this);
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_vendor_dropDown://点选供应商事件
			showInfo(VENDOR_FLAG);
			break;
		case R.id.ibtn__terms_dropDown://点选付款条件
			showInfo(TERMS_FLAG);
			break;
		}
		
	}
	
	private void showInfo(int flag) {
		popList = new ArrayList<String>();
		popList.clear();
		if(pop!=null&&isShow ){
			pop.dismiss();
			isShow = false;
		}
		switch (flag) {
		case VENDOR_FLAG:
			pop = new PopupWindow(listView, et_vendor.getWidth(), LayoutParams.WRAP_CONTENT);
			popList.add("添加新供应商");
			popList.add("供应商1");
			popList.add("供应商2");
			popList.add("供应商3");
			listView.setAdapter(new MyAdapter());
			Drawable image = new ColorDrawable(Color.CYAN);
			pop.setBackgroundDrawable(image);
			pop.showAsDropDown(et_vendor);
			isShow = true;
			break;
		case TERMS_FLAG:
			pop = new PopupWindow(listView, et_terms.getWidth(), LayoutParams.WRAP_CONTENT);
			popList.add("添加新发票");
			popList.add("发票1");
			popList.add("发票2");
			popList.add("发票3");
			listView.setAdapter(new MyAdapter());
			Drawable image1 = new ColorDrawable(Color.CYAN);
			pop.setBackgroundDrawable(image1);
			pop.showAsDropDown(et_terms);
			isShow = true;
			break;
		}
	}
	private class MyAdapter extends BaseAdapter{

		public int getCount() {
			return popList.size();
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			TextView tView = null;
			if(convertView!=null){
				tView = (TextView) convertView;
			}else{
				tView = new TextView(AccountPayable.this);
			}
			final String result = popList.get(position);
			tView.setText(result);
			tView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(pop!=null){
						if(position==0){
							showAddNewItem();
							Toast.makeText(AccountPayable.this, result, 0).show();
							pop.dismiss();
						}else{
							et_vendor.setText(result);
							pop.dismiss();
						}
					}
				}
			});
			return tView;
		}
		
		public Object getItem(int position) {
			return popList.get(position);
		}

		public long getItemId(int position) {
			return popList.indexOf(position);
		}
	}
	protected void showAddNewItem() {
	}
}
