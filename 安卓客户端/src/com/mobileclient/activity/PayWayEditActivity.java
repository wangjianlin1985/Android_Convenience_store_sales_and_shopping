package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.PayWay;
import com.mobileclient.service.PayWayService;
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

public class PayWayEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明支付方式idTextView
	private TextView TV_payWayId;
	// 声明支付方式名称输入框
	private EditText ET_payWayName;
	protected String carmera_path;
	/*要保存的支付方式信息*/
	PayWay payWay = new PayWay();
	/*支付方式管理业务逻辑层*/
	private PayWayService payWayService = new PayWayService();

	private int payWayId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.payway_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑支付方式信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_payWayId = (TextView) findViewById(R.id.TV_payWayId);
		ET_payWayName = (EditText) findViewById(R.id.ET_payWayName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		payWayId = extras.getInt("payWayId");
		/*单击修改支付方式按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取支付方式名称*/ 
					if(ET_payWayName.getText().toString().equals("")) {
						Toast.makeText(PayWayEditActivity.this, "支付方式名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_payWayName.setFocusable(true);
						ET_payWayName.requestFocus();
						return;	
					}
					payWay.setPayWayName(ET_payWayName.getText().toString());
					/*调用业务逻辑层上传支付方式信息*/
					PayWayEditActivity.this.setTitle("正在更新支付方式信息，稍等...");
					String result = payWayService.UpdatePayWay(payWay);
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
	    payWay = payWayService.GetPayWay(payWayId);
		this.TV_payWayId.setText(payWayId+"");
		this.ET_payWayName.setText(payWay.getPayWayName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
