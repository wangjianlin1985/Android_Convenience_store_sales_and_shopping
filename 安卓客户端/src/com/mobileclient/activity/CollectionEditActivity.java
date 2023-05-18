package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Collection;
import com.mobileclient.service.CollectionService;
import com.mobileclient.domain.Product;
import com.mobileclient.service.ProductService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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

public class CollectionEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明收藏idTextView
	private TextView TV_collectionId;
	// 声明收藏宝贝下拉框
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<Product> productList = null;
	/*收藏宝贝管理业务逻辑层*/
	private ProductService productService = new ProductService();
	// 声明收藏用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*收藏用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明收藏时间输入框
	private EditText ET_collectionTime;
	protected String carmera_path;
	/*要保存的宝贝收藏信息*/
	Collection collection = new Collection();
	/*宝贝收藏管理业务逻辑层*/
	private CollectionService collectionService = new CollectionService();

	private int collectionId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.collection_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑宝贝收藏信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_collectionId = (TextView) findViewById(R.id.TV_collectionId);
		spinner_productObj = (Spinner) findViewById(R.id.Spinner_productObj);
		// 获取所有的收藏宝贝
		try {
			productList = productService.QueryProduct(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int productCount = productList.size();
		productObj_ShowText = new String[productCount];
		for(int i=0;i<productCount;i++) { 
			productObj_ShowText[i] = productList.get(i).getProductName();
		}
		// 将可选内容与ArrayAdapter连接起来
		productObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productObj_ShowText);
		// 设置图书类别下拉列表的风格
		productObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_productObj.setAdapter(productObj_adapter);
		// 添加事件Spinner事件监听
		spinner_productObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				collection.setProductObj(productList.get(arg2).getProductId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_productObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的收藏用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				collection.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_collectionTime = (EditText) findViewById(R.id.ET_collectionTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		collectionId = extras.getInt("collectionId");
		/*单击修改宝贝收藏按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取收藏时间*/ 
					if(ET_collectionTime.getText().toString().equals("")) {
						Toast.makeText(CollectionEditActivity.this, "收藏时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_collectionTime.setFocusable(true);
						ET_collectionTime.requestFocus();
						return;	
					}
					collection.setCollectionTime(ET_collectionTime.getText().toString());
					/*调用业务逻辑层上传宝贝收藏信息*/
					CollectionEditActivity.this.setTitle("正在更新宝贝收藏信息，稍等...");
					String result = collectionService.UpdateCollection(collection);
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
	    collection = collectionService.GetCollection(collectionId);
		this.TV_collectionId.setText(collectionId+"");
		for (int i = 0; i < productList.size(); i++) {
			if (collection.getProductObj() == productList.get(i).getProductId()) {
				this.spinner_productObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < userInfoList.size(); i++) {
			if (collection.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_collectionTime.setText(collection.getCollectionTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
