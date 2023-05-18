package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class OrderItemAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderitem_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加订单条目");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
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
		// 设置下拉列表的风格
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
		// 设置下拉列表的风格
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
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加订单条目按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取商品单价*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(OrderItemAddActivity.this, "商品单价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					orderItem.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取购买数量*/ 
					if(ET_orderNumer.getText().toString().equals("")) {
						Toast.makeText(OrderItemAddActivity.this, "购买数量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderNumer.setFocusable(true);
						ET_orderNumer.requestFocus();
						return;	
					}
					orderItem.setOrderNumer(Integer.parseInt(ET_orderNumer.getText().toString()));
					/*调用业务逻辑层上传订单条目信息*/
					OrderItemAddActivity.this.setTitle("正在上传订单条目信息，稍等...");
					String result = orderItemService.AddOrderItem(orderItem);
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
