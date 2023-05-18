package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.SendWay;
import com.mobileclient.service.SendWayService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class SendWayEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明送货方式idTextView
	private TextView TV_sendWayId;
	// 声明送货方式名称输入框
	private EditText ET_sendWayName;
	protected String carmera_path;
	/*要保存的送货方式信息*/
	SendWay sendWay = new SendWay();
	/*送货方式管理业务逻辑层*/
	private SendWayService sendWayService = new SendWayService();

	private int sendWayId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.sendway_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑送货方式信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_sendWayId = (TextView) findViewById(R.id.TV_sendWayId);
		ET_sendWayName = (EditText) findViewById(R.id.ET_sendWayName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		sendWayId = extras.getInt("sendWayId");
		/*单击修改送货方式按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取送货方式名称*/ 
					if(ET_sendWayName.getText().toString().equals("")) {
						Toast.makeText(SendWayEditActivity.this, "送货方式名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sendWayName.setFocusable(true);
						ET_sendWayName.requestFocus();
						return;	
					}
					sendWay.setSendWayName(ET_sendWayName.getText().toString());
					/*调用业务逻辑层上传送货方式信息*/
					SendWayEditActivity.this.setTitle("正在更新送货方式信息，稍等...");
					String result = sendWayService.UpdateSendWay(sendWay);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    sendWay = sendWayService.GetSendWay(sendWayId);
		this.TV_sendWayId.setText(sendWayId+"");
		this.ET_sendWayName.setText(sendWay.getSendWayName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
