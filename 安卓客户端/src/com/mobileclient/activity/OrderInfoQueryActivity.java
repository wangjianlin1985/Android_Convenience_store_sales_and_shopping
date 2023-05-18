package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.OrderInfo;
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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class OrderInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明订单编号输入框
	private EditText ET_orderNo;
	// 声明下单用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
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
	// 声明送货方式下拉框
	private Spinner spinner_sendWayObj;
	private ArrayAdapter<String> sendWayObj_adapter;
	private static  String[] sendWayObj_ShowText  = null;
	private List<SendWay> sendWayList = null; 
	/*送货方式管理业务逻辑层*/
	private SendWayService sendWayService = new SendWayService();
	// 声明订单卖家下拉框
	private Spinner spinner_sellerObj;
	private ArrayAdapter<String> sellerObj_adapter;
	private static  String[] sellerObj_ShowText  = null;
	private List<Seller> sellerList = null; 
	/*商家管理业务逻辑层*/
	private SellerService sellerService = new SellerService();
	/*查询过滤条件保存到这个对象中*/
	private OrderInfo queryConditionOrderInfo = new OrderInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置订单查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_orderNo = (EditText) findViewById(R.id.ET_orderNo);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下单用户下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionOrderInfo.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		spinner_payWay = (Spinner) findViewById(R.id.Spinner_payWay);
		// 获取所有的支付方式
		try {
			payWayList = payWayService.QueryPayWay(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int payWayCount = payWayList.size();
		payWay_ShowText = new String[payWayCount+1];
		payWay_ShowText[0] = "不限制";
		for(int i=1;i<=payWayCount;i++) { 
			payWay_ShowText[i] = payWayList.get(i-1).getPayWayName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		payWay_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, payWay_ShowText);
		// 设置支付方式下拉列表的风格
		payWay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_payWay.setAdapter(payWay_adapter);
		// 添加事件Spinner事件监听
		spinner_payWay.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setPayWay(payWayList.get(arg2-1).getPayWayId()); 
				else
					queryConditionOrderInfo.setPayWay(0);
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
		spinner_sendWayObj = (Spinner) findViewById(R.id.Spinner_sendWayObj);
		// 获取所有的送货方式
		try {
			sendWayList = sendWayService.QuerySendWay(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int sendWayCount = sendWayList.size();
		sendWayObj_ShowText = new String[sendWayCount+1];
		sendWayObj_ShowText[0] = "不限制";
		for(int i=1;i<=sendWayCount;i++) { 
			sendWayObj_ShowText[i] = sendWayList.get(i-1).getSendWayName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		sendWayObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sendWayObj_ShowText);
		// 设置送货方式下拉列表的风格
		sendWayObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_sendWayObj.setAdapter(sendWayObj_adapter);
		// 添加事件Spinner事件监听
		spinner_sendWayObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setSendWayObj(sendWayList.get(arg2-1).getSendWayId()); 
				else
					queryConditionOrderInfo.setSendWayObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_sendWayObj.setVisibility(View.VISIBLE);
		spinner_sellerObj = (Spinner) findViewById(R.id.Spinner_sellerObj);
		// 获取所有的商家
		try {
			sellerList = sellerService.QuerySeller(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int sellerCount = sellerList.size();
		sellerObj_ShowText = new String[sellerCount+1];
		sellerObj_ShowText[0] = "不限制";
		for(int i=1;i<=sellerCount;i++) { 
			sellerObj_ShowText[i] = sellerList.get(i-1).getSellerName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		sellerObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sellerObj_ShowText);
		// 设置订单卖家下拉列表的风格
		sellerObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_sellerObj.setAdapter(sellerObj_adapter);
		// 添加事件Spinner事件监听
		spinner_sellerObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setSellerObj(sellerList.get(arg2-1).getSellUserName()); 
				else
					queryConditionOrderInfo.setSellerObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_sellerObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionOrderInfo.setOrderNo(ET_orderNo.getText().toString());
					queryConditionOrderInfo.setOrderStateObj(ET_orderStateObj.getText().toString());
					queryConditionOrderInfo.setOrderTime(ET_orderTime.getText().toString());
					queryConditionOrderInfo.setReceiveName(ET_receiveName.getText().toString());
					queryConditionOrderInfo.setTelephone(ET_telephone.getText().toString());
					Declare declare = (Declare) OrderInfoQueryActivity.this.getApplication();
					queryConditionOrderInfo.setSellerObj(declare.getUserName());
					
					
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionOrderInfo", queryConditionOrderInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
