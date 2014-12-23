package com.leo.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RecurringInvoice extends Activity implements OnClickListener{
	
	private Spinner spinner;
	private static final String[] m={"Monthly","Weekly","Monthly","Annually"};
	private ArrayAdapter<String> adapter;
	private Button schedule;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recurring__invoice);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		spinner = (Spinner) findViewById(R.id.Spinner);
		schedule = (Button) findViewById(R.id.schedulebtn);
		schedule.setOnClickListener(this);
		
		//将可选内容与ArrayAdapter连接起来
				adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m); 
				//设置下拉列表的风格
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				//将adapter 添加到spinner中
				spinner.setAdapter(adapter);
				//添加事件Spinner事件监听  
				spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
				//设置默认值
				spinner.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.finish();
	}


	
}
