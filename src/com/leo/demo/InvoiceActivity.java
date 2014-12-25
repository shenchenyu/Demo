package com.leo.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.leo.demo.adapter.Itemadapter;
import com.leo.demo.bean.Bill1;
import com.leo.demo.bean.Category;
import com.leo.demo.bean.Itemlist;
import com.leo.demo.bean.Vendor;
import com.leo.demo.http.HttpHelper;
import com.leo.demo.http.HttpHelper.HttpResult;
import com.leo.demo.utils.CommonUtil;
import com.leo.demo.utils.ContentValue;
import com.leo.demo.utils.PromptManager;
import com.lidroid.xutils.util.LogUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class InvoiceActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	private static final String[] m={"Vendor 0","Vendor 1","Vendor 2","Vendor 3","Vendor 4"};
	private static final String[] n ={"c9f1cc92-f260-467d-9e5b-de4a9d03aa4f","1c7acdb6-ca7e-4d9b-8e20-5fafe469eabb","c38fc53c-240e-42b0-bb67-34f6279ac5c6","c17e31cc-4bbc-4fe4-bf5b-0e0ecc7c3973","2fe09e7d-b17d-44eb-978b-8c81630fe642"};
	private RelativeLayout titleinvoice;
	private Spinner spinner;
	private ArrayAdapter<String> adapter; 
	private ImageView imageview;
	private Context context;
	private ListView listview;
	public final static int REQUEST_CODE_TAKE_PICTURE = 12;// 设置拍照操作的标志
	private ImageView mImg;
	protected String mUri;
	private ArrayList<String> arrayList;
	private Button btnItem,addmore,company;
	private Itemadapter	itemadapter;
	private LinearLayout notes;
	private ScrollView scr;
	private CheckBox checkbox;
	private Bill1 bill;
	private Itemlist item;
	private EditText billDate,dueDate,amount;	
	private EditText createOn,description,lastUpdatedOn;
	private boolean isRecurring;
	private Vendor vendor;
	private Category category;
	private String vendorname,vendorid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_invoice);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.layout_invoice_title_bar);// 设置titleBar 布局文件
		init();	


	}



	private void init() {
		context = this;
		billDate = (EditText) findViewById(R.id.billdata);
		dueDate = (EditText) findViewById(R.id.dueDate);
		amount = (EditText) findViewById(R.id.amount);
		mImg = (ImageView) findViewById(R.id.ivShow);
		btnItem = (Button) findViewById(R.id.btnitem);
		spinner = (Spinner) findViewById(R.id.companySpinner);
		imageview = (ImageView) findViewById(R.id.camera);
		addmore = (Button) findViewById(R.id.addmore);
		notes = (LinearLayout) findViewById(R.id.linearlayout);
		scr = (ScrollView) findViewById(R.id.scrollview);
		checkbox = (CheckBox) findViewById(R.id.checkbox);
		company = (Button) findViewById(R.id.company);
		
		category = new Category("9607ea59-ea15-45cf-b7ca-bb4a39117d64", "Cat 3", "");
		company.setOnClickListener(this);
		checkbox.setOnCheckedChangeListener(this);
		addmore.setOnClickListener(this);
		addmore.setOnClickListener(this);
		imageview.setOnClickListener(this);
		btnItem.setOnClickListener(this);
		//将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m); 
		//设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		//添加事件Spinner事件监听  
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {


			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
					vendorid = n[position];
					vendorname  = m[position];
					vendor = new Vendor(vendorid, vendorname);
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		//设置默认值
		spinner.setVisibility(View.VISIBLE);
		arrayList  = new ArrayList<String>();
		arrayList.add("名称：");
		listview = (ListView) findViewById(R.id.listview_company);
		itemadapter = new Itemadapter(this,arrayList,new com.leo.demo.adapter.Itemadapter.Delete() {

			@Override
			public void delete(ArrayList<String> arr, int position) {
				// TODO Auto-generated method stub
				//arr.remove(position);
				itemadapter.notifyDataSetChanged();
				setListViewHeightBasedOnChildren(listview);

			}
		}); 

		listview.setAdapter(itemadapter);
		setListViewHeightBasedOnChildren(listview);
		scr.smoothScrollTo(0,0);  


	}

	/**
	 * 
	 */
	public void bill_value() { 
		String bd = billDate.getText().toString();
		String dd = dueDate.getText().toString();
		String at = amount.getText().toString();
		bill = new Bill1(bd,at,dd);
		bill.setVendor(vendor);
		bill.setCategory(category);
		item = new Itemlist("水电费",10, "8888");
		List<Itemlist> items = new ArrayList<Itemlist>();
		items.add(item);
		bill.setItems(items);

	};

	public void setListViewHeightBasedOnChildren(ListView listView) {

		// 获取listview的适配器
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(MeasureSpec.makeMeasureSpec(getResources()
					.getDisplayMetrics().widthPixels, MeasureSpec.EXACTLY), 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}




	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.camera:
			mImg.setVisibility(View.VISIBLE);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
			break;
		case R.id.btnitem:
			itemadapter.arr.add(""); 
			itemadapter.notifyDataSetChanged(); 
			setListViewHeightBasedOnChildren(listview);
			break;
		case R.id.addmore:
			addmore.setVisibility(View.GONE);
			notes.setVisibility(View.VISIBLE);
			break;
		case R.id.company:
			checkVerifyCode();
			break;

		default:
			break;
		}

	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);



		if (resultCode == Activity.RESULT_OK) {  
			String sdStatus = Environment.getExternalStorageState();  
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
				Log.i("TestFile",  
						"SD card is not avaiable/writeable right now.");  
				return;  
			}  
			String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";     
			Toast.makeText(this, name, Toast.LENGTH_LONG).show();  
			Bundle bundle = data.getExtras();  
			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式  

			FileOutputStream b = null;  
			File file = new File("/sdcard/myImage/");  
			file.mkdirs();// 创建文件夹  
			String fileName = "/sdcard/myImage/"+name;  

			try {  
				b = new FileOutputStream(fileName);  
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件  
			} catch (FileNotFoundException e) {  
				e.printStackTrace();  
			} finally {  
				try {  
					b.flush();  
					b.close();  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
			mImg.setImageBitmap(bitmap);// 将图片显示在ImageView里  
		}  


	}

	private int maxH = 200;//

	/**
	 * 获取图片并压缩图片
	 * @param imagePath
	 */
	private Bitmap getImage(String imagePath) {
		return BitmapFactory.decodeFile(imagePath);
	}

	/*** 校验验证码 */
	private void checkVerifyCode() {
		bill_value();
		String json = CommonUtil.bean2Json(bill);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+json);
		String url = ContentValue.NEWSERVER_URL + "/"
				+ ContentValue.BILL_URL;


		new AsyncTask<String, Void, HttpResult>() {
			@Override
			protected void onPreExecute() {




			};

			@Override
			protected HttpResult doInBackground(String... params) {
				return HttpHelper.post(params[0], params[1],ContentValue.APPLICATION_JSON);
			}

			@Override
			protected void onPostExecute(HttpResult result) {

				LogUtils.d("服务器返回校验结果：" + result);

				if (result != null && result.getCode() == 201) {
										PromptManager.showToast(
												InvoiceActivity.this,
												getResources().getString(R.string.str_send_success));

					


				} else {
					// 请求异常，关闭加载框 ，关闭网络连接
					int code = result.getCode();
					String code1 =result.getString();
					
					LogUtils.d("服务器返回校验结果：" + code);
					LogUtils.d("服务器返回校验结果：" + code1);

					PromptManager.showToast(
							InvoiceActivity.this,
							getResources().getString(
									R.string.str_send_failure));


				}
			}
		}.execute(url, json);
	}




	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(isChecked){  
			Intent intent = new Intent(context, RecurringInvoice.class);
			startActivity(intent);
		}
	}
}

