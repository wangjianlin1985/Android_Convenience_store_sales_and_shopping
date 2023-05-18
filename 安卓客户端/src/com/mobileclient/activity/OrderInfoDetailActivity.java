package com.mobileclient.activity;

import java.util.Date;
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
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class OrderInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn,btnViewItem;
	// 声明订单编号控件
	private TextView TV_orderNo;
	// 声明下单用户控件
	private TextView TV_userObj;
	// 声明订单总金额控件
	private TextView TV_totalMoney;
	// 声明支付方式控件
	private TextView TV_payWay;
	// 声明订单状态控件
	private TextView TV_orderStateObj;
	// 声明下单时间控件
	private TextView TV_orderTime;
	// 声明收货人控件
	private TextView TV_receiveName;
	// 声明收货人电话控件
	private TextView TV_telephone;
	// 声明收货人地址控件
	private TextView TV_address;
	// 声明送货方式控件
	private TextView TV_sendWayObj;
	// 声明订单备注控件
	private TextView TV_orderMemo;
	// 声明订单卖家控件
	private TextView TV_sellerObj;
	/* 要保存的订单信息 */
	OrderInfo orderInfo = new OrderInfo(); 
	/* 订单管理业务逻辑层 */
	private OrderInfoService orderInfoService = new OrderInfoService();
	private UserInfoService userInfoService = new UserInfoService();
	private PayWayService payWayService = new PayWayService();
	private SendWayService sendWayService = new SendWayService();
	private SellerService sellerService = new SellerService();
	private String orderNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看订单详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnViewItem = (Button) findViewById(R.id.btnViewItem);
		TV_orderNo = (TextView) findViewById(R.id.TV_orderNo);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_totalMoney = (TextView) findViewById(R.id.TV_totalMoney);
		TV_payWay = (TextView) findViewById(R.id.TV_payWay);
		TV_orderStateObj = (TextView) findViewById(R.id.TV_orderStateObj);
		TV_orderTime = (TextView) findViewById(R.id.TV_orderTime);
		TV_receiveName = (TextView) findViewById(R.id.TV_receiveName);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_sendWayObj = (TextView) findViewById(R.id.TV_sendWayObj);
		TV_orderMemo = (TextView) findViewById(R.id.TV_orderMemo);
		TV_sellerObj = (TextView) findViewById(R.id.TV_sellerObj);
		Bundle extras = this.getIntent().getExtras();
		orderNo = extras.getString("orderNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderInfoDetailActivity.this.finish();
			}
		}); 
		btnViewItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//查看订单对应购买的商品列表
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("orderNo", orderNo);
				intent.putExtras(bundle);
				intent.setClass(OrderInfoDetailActivity.this.getApplicationContext(), OrderItemUserListActivity.class);
				startActivity(intent);
			}
		});
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderNo); 
		this.TV_orderNo.setText(orderInfo.getOrderNo());
		UserInfo userObj = userInfoService.GetUserInfo(orderInfo.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_totalMoney.setText(orderInfo.getTotalMoney() + "");
		PayWay payWay = payWayService.GetPayWay(orderInfo.getPayWay());
		this.TV_payWay.setText(payWay.getPayWayName());
		this.TV_orderStateObj.setText(orderInfo.getOrderStateObj());
		this.TV_orderTime.setText(orderInfo.getOrderTime());
		this.TV_receiveName.setText(orderInfo.getReceiveName());
		this.TV_telephone.setText(orderInfo.getTelephone());
		this.TV_address.setText(orderInfo.getAddress());
		SendWay sendWayObj = sendWayService.GetSendWay(orderInfo.getSendWayObj());
		this.TV_sendWayObj.setText(sendWayObj.getSendWayName());
		this.TV_orderMemo.setText(orderInfo.getOrderMemo());
		Seller sellerObj = sellerService.GetSeller(orderInfo.getSellerObj());
		this.TV_sellerObj.setText(sellerObj.getSellerName());
	} 
}
