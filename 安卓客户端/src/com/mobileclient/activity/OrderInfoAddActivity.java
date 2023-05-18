package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.PayWay;
import com.mobileclient.service.PayWayService;
import com.mobileclient.domain.SendWay;
import com.mobileclient.service.SendWayService;
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
public class OrderInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明订单编号输入框
	private EditText ET_orderNo;
	// 声明下单用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*下单用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明订单总金额输入框
	private EditText ET_totalMoney;
	// 声明支付方式下拉框
	private Spinner spinner_payWay;
	private ArrayAdapter<String> payWay_adapter;
	private static  String[] payWay_ShowText  = null;
	private List<PayWay> payWayList = null;
	/*支付方式管理业务逻辑层*/
	private PayWayService payWayService = new PayWayService();
	// 声明订单状态输入框
	private EditText ET_orderStateObj;
	// 声明下单时间输入框
	private EditText ET_orderTime;
	// 声明收货人输入框
	private EditText ET_receiveName;
	// 声明收货人电话输入框
	private EditText ET_telephone;
	// 声明收货人地址输入框
	private EditText ET_address;
	// 声明送货方式下拉框
	private Spinner spinner_sendWayObj;
	private ArrayAdapter<String> sendWayObj_adapter;
	private static  String[] sendWayObj_ShowText  = null;
	private List<SendWay> sendWayList = null;
	/*送货方式管理业务逻辑层*/
	private SendWayService sendWayService = new SendWayService();
	// 声明订单备注输入框
	private EditText ET_orderMemo;
	// 声明订单卖家下拉框
	private Spinner spinner_sellerObj;
	private ArrayAdapter<String> sellerObj_adapter;
	private static  String[] sellerObj_ShowText  = null;
	private List<Seller> sellerList = null;
	/*订单卖家管理业务逻辑层*/
	private SellerService sellerService = new SellerService();
	protected String carmera_path;
	/*要保存的订单信息*/
	OrderInfo orderInfo = new OrderInfo();
	/*订单管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加订单");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_orderNo = (EditText) findViewById(R.id.ET_orderNo);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的下单用户
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
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_totalMoney = (EditText) findViewById(R.id.ET_totalMoney);
		spinner_payWay = (Spinner) findViewById(R.id.Spinner_payWay);
		// 获取所有的支付方式
		try {
			payWayList = payWayService.QueryPayWay(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int payWayCount = payWayList.size();
		payWay_ShowText = new String[payWayCount];
		for(int i=0;i<payWayCount;i++) { 
			payWay_ShowText[i] = payWayList.get(i).getPayWayName();
		}
		// 将可选内容与ArrayAdapter连接起来
		payWay_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, payWay_ShowText);
		// 设置下拉列表的风格
		payWay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_payWay.setAdapter(payWay_adapter);
		// 添加事件Spinner事件监听
		spinner_payWay.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setPayWay(payWayList.get(arg2).getPayWayId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_payWay.setVisibility(View.VISIBLE);
		ET_orderStateObj = (EditText) findViewById(R.id.ET_orderStateObj);
		ET_orderTime = (EditText) findViewById(R.id.ET_orderTime);
		ET_receiveName = (EditText) findViewById(R.id.ET_receiveName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_address = (EditText) findViewById(R.id.ET_address);
		spinner_sendWayObj = (Spinner) findViewById(R.id.Spinner_sendWayObj);
		// 获取所有的送货方式
		try {
			sendWayList = sendWayService.QuerySendWay(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int sendWayCount = sendWayList.size();
		sendWayObj_ShowText = new String[sendWayCount];
		for(int i=0;i<sendWayCount;i++) { 
			sendWayObj_ShowText[i] = sendWayList.get(i).getSendWayName();
		}
		// 将可选内容与ArrayAdapter连接起来
		sendWayObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sendWayObj_ShowText);
		// 设置下拉列表的风格
		sendWayObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_sendWayObj.setAdapter(sendWayObj_adapter);
		// 添加事件Spinner事件监听
		spinner_sendWayObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setSendWayObj(sendWayList.get(arg2).getSendWayId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_sendWayObj.setVisibility(View.VISIBLE);
		ET_orderMemo = (EditText) findViewById(R.id.ET_orderMemo);
		spinner_sellerObj = (Spinner) findViewById(R.id.Spinner_sellerObj);
		// 获取所有的订单卖家
		try {
			sellerList = sellerService.QuerySeller(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int sellerCount = sellerList.size();
		sellerObj_ShowText = new String[sellerCount];
		for(int i=0;i<sellerCount;i++) { 
			sellerObj_ShowText[i] = sellerList.get(i).getSellerName();
		}
		// 将可选内容与ArrayAdapter连接起来
		sellerObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sellerObj_ShowText);
		// 设置下拉列表的风格
		sellerObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_sellerObj.setAdapter(sellerObj_adapter);
		// 添加事件Spinner事件监听
		spinner_sellerObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setSellerObj(sellerList.get(arg2).getSellUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_sellerObj.setVisibility(View.VISIBLE);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加订单按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取订单编号*/
					if(ET_orderNo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderNo.setFocusable(true);
						ET_orderNo.requestFocus();
						return;
					}
					orderInfo.setOrderNo(ET_orderNo.getText().toString());
					/*验证获取订单总金额*/ 
					if(ET_totalMoney.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单总金额输入不能为空!", Toast.LENGTH_LONG).show();
						ET_totalMoney.setFocusable(true);
						ET_totalMoney.requestFocus();
						return;	
					}
					orderInfo.setTotalMoney(Float.parseFloat(ET_totalMoney.getText().toString()));
					/*验证获取订单状态*/ 
					if(ET_orderStateObj.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderStateObj.setFocusable(true);
						ET_orderStateObj.requestFocus();
						return;	
					}
					orderInfo.setOrderStateObj(ET_orderStateObj.getText().toString());
					/*验证获取下单时间*/ 
					if(ET_orderTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "下单时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderTime.setFocusable(true);
						ET_orderTime.requestFocus();
						return;	
					}
					orderInfo.setOrderTime(ET_orderTime.getText().toString());
					/*验证获取收货人*/ 
					if(ET_receiveName.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_receiveName.setFocusable(true);
						ET_receiveName.requestFocus();
						return;	
					}
					orderInfo.setReceiveName(ET_receiveName.getText().toString());
					/*验证获取收货人电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货人电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					orderInfo.setTelephone(ET_telephone.getText().toString());
					/*验证获取收货人地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货人地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					orderInfo.setAddress(ET_address.getText().toString());
					/*验证获取订单备注*/ 
					if(ET_orderMemo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderMemo.setFocusable(true);
						ET_orderMemo.requestFocus();
						return;	
					}
					orderInfo.setOrderMemo(ET_orderMemo.getText().toString());
					/*调用业务逻辑层上传订单信息*/
					OrderInfoAddActivity.this.setTitle("正在上传订单信息，稍等...");
					String result = orderInfoService.AddOrderInfo(orderInfo);
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
