package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Seller;
import com.mobileclient.service.SellerService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class SellerAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明商家账号输入框
	private EditText ET_sellUserName;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明商家名称输入框
	private EditText ET_sellerName;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明商家地址输入框
	private EditText ET_address;
	// 声明入住时间输入框
	private EditText ET_regTime;
	protected String carmera_path;
	/*要保存的商家信息*/
	Seller seller = new Seller();
	/*商家管理业务逻辑层*/
	private SellerService sellerService = new SellerService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.seller_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加商家");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_sellUserName = (EditText) findViewById(R.id.ET_sellUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_sellerName = (EditText) findViewById(R.id.ET_sellerName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_regTime = (EditText) findViewById(R.id.ET_regTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加商家按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取商家账号*/
					if(ET_sellUserName.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "商家账号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sellUserName.setFocusable(true);
						ET_sellUserName.requestFocus();
						return;
					}
					seller.setSellUserName(ET_sellUserName.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					seller.setPassword(ET_password.getText().toString());
					/*验证获取商家名称*/ 
					if(ET_sellerName.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "商家名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sellerName.setFocusable(true);
						ET_sellerName.requestFocus();
						return;	
					}
					seller.setSellerName(ET_sellerName.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					seller.setTelephone(ET_telephone.getText().toString());
					/*验证获取商家地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "商家地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					seller.setAddress(ET_address.getText().toString());
					/*验证获取入住时间*/ 
					if(ET_regTime.getText().toString().equals("")) {
						Toast.makeText(SellerAddActivity.this, "入住时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_regTime.setFocusable(true);
						ET_regTime.requestFocus();
						return;	
					}
					seller.setRegTime(ET_regTime.getText().toString());
					/*调用业务逻辑层上传商家信息*/
					SellerAddActivity.this.setTitle("正在上传商家信息，稍等...");
					String result = sellerService.AddSeller(seller);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
