package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.OrderItem;
import com.mobileclient.service.OrderItemService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.Product;
import com.mobileclient.service.ProductService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
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
public class OrderItemDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明条目id控件
	private TextView TV_itemId;
	// 声明所属订单控件
	private TextView TV_orderObj;
	// 声明订单商品控件
	private TextView TV_productObj;
	// 声明商品单价控件
	private TextView TV_price;
	// 声明购买数量控件
	private TextView TV_orderNumer;
	/* 要保存的订单条目信息 */
	OrderItem orderItem = new OrderItem(); 
	/* 订单条目管理业务逻辑层 */
	private OrderItemService orderItemService = new OrderItemService();
	private OrderInfoService orderInfoService = new OrderInfoService();
	private ProductService productService = new ProductService();
	private int itemId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderitem_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看订单条目详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_itemId = (TextView) findViewById(R.id.TV_itemId);
		TV_orderObj = (TextView) findViewById(R.id.TV_orderObj);
		TV_productObj = (TextView) findViewById(R.id.TV_productObj);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_orderNumer = (TextView) findViewById(R.id.TV_orderNumer);
		Bundle extras = this.getIntent().getExtras();
		itemId = extras.getInt("itemId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderItemDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderItem = orderItemService.GetOrderItem(itemId); 
		this.TV_itemId.setText(orderItem.getItemId() + "");
		OrderInfo orderObj = orderInfoService.GetOrderInfo(orderItem.getOrderObj());
		this.TV_orderObj.setText(orderObj.getOrderNo());
		Product productObj = productService.GetProduct(orderItem.getProductObj());
		this.TV_productObj.setText(productObj.getProductName());
		this.TV_price.setText(orderItem.getPrice() + "");
		this.TV_orderNumer.setText(orderItem.getOrderNumer() + "");
	} 
}
