package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.OrderItem;
import com.mobileclient.service.OrderItemService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.Product;
import com.mobileclient.service.ProductService;
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

public class OrderItemEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明条目idTextView
	private TextView TV_itemId;
	// 声明所属订单下拉框
	private Spinner spinner_orderObj;
	private ArrayAdapter<String> orderObj_adapter;
	private static  String[] orderObj_ShowText  = null;
	private List<OrderInfo> orderInfoList = null;
	/*所属订单管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();
	// 声明订单商品下拉框
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<Product> productList = null;
	/*订单商品管理业务逻辑层*/
	private ProductService productService = new ProductService();
	// 声明商品单价输入框
	private EditText ET_price;
	// 声明购买数量输入框
	private EditText ET_orderNumer;
	protected String carmera_path;
	/*要保存的订单条目信息*/
	OrderItem orderItem = new OrderItem();
	/*订单条目管理业务逻辑层*/
	private OrderItemService orderItemService = new OrderItemService();

	private int itemId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderitem_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑订单条目信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_itemId = (TextView) findViewById(R.id.TV_itemId);
		spinner_orderObj = (Spinner) findViewById(R.id.Spinner_orderObj);
		// 获取所有的所属订单
		try {
			orderInfoList = orderInfoService.QueryOrderInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int orderInfoCount = orderInfoList.size();
		orderObj_ShowText = new String[orderInfoCount];
		for(int i=0;i<orderInfoCount;i++) { 
			orderObj_ShowText[i] = orderInfoList.get(i).getOrderNo();
		}
		// 将可选内容与ArrayAdapter连接起来
		orderObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderObj_ShowText);
		// 设置图书类别下拉列表的风格
		orderObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderObj.setAdapter(orderObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderItem.setOrderObj(orderInfoList.get(arg2).getOrderNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderObj.setVisibility(View.VISIBLE);
		spinner_productObj = (Spinner) findViewById(R.id.Spinner_productObj);
		// 获取所有的订单商品
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
				orderItem.setProductObj(productList.get(arg2).getProductId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_productObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_orderNumer = (EditText) findViewById(R.id.ET_orderNumer);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		itemId = extras.getInt("itemId");
		/*单击修改订单条目按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取商品单价*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(OrderItemEditActivity.this, "商品单价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					orderItem.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取购买数量*/ 
					if(ET_orderNumer.getText().toString().equals("")) {
						Toast.makeText(OrderItemEditActivity.this, "购买数量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderNumer.setFocusable(true);
						ET_orderNumer.requestFocus();
						return;	
					}
					orderItem.setOrderNumer(Integer.parseInt(ET_orderNumer.getText().toString()));
					/*调用业务逻辑层上传订单条目信息*/
					OrderItemEditActivity.this.setTitle("正在更新订单条目信息，稍等...");
					String result = orderItemService.UpdateOrderItem(orderItem);
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
	    orderItem = orderItemService.GetOrderItem(itemId);
		this.TV_itemId.setText(itemId+"");
		for (int i = 0; i < orderInfoList.size(); i++) {
			if (orderItem.getOrderObj().equals(orderInfoList.get(i).getOrderNo())) {
				this.spinner_orderObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < productList.size(); i++) {
			if (orderItem.getProductObj() == productList.get(i).getProductId()) {
				this.spinner_productObj.setSelection(i);
				break;
			}
		}
		this.ET_price.setText(orderItem.getPrice() + "");
		this.ET_orderNumer.setText(orderItem.getOrderNumer() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
