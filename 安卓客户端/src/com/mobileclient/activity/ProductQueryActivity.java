package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Product;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
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
public class ProductQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明商品类别下拉框
	private Spinner spinner_productClassObj;
	private ArrayAdapter<String> productClassObj_adapter;
	private static  String[] productClassObj_ShowText  = null;
	private List<ProductClass> productClassList = null; 
	/*商品类别管理业务逻辑层*/
	private ProductClassService productClassService = new ProductClassService();
	// 声明商品名称输入框
	private EditText ET_productName;
	// 声明发布商家下拉框
	private Spinner spinner_sellerObj;
	private ArrayAdapter<String> sellerObj_adapter;
	private static  String[] sellerObj_ShowText  = null;
	private List<Seller> sellerList = null; 
	/*商家管理业务逻辑层*/
	private SellerService sellerService = new SellerService();
	// 声明发布时间输入框
	private EditText ET_addTime;
	/*查询过滤条件保存到这个对象中*/
	private Product queryConditionProduct = new Product();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.product_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置商品查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_productClassObj = (Spinner) findViewById(R.id.Spinner_productClassObj);
		// 获取所有的商品类别
		try {
			productClassList = productClassService.QueryProductClass(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int productClassCount = productClassList.size();
		productClassObj_ShowText = new String[productClassCount+1];
		productClassObj_ShowText[0] = "不限制";
		for(int i=1;i<=productClassCount;i++) { 
			productClassObj_ShowText[i] = productClassList.get(i-1).getClassName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		productClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productClassObj_ShowText);
		// 设置商品类别下拉列表的风格
		productClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_productClassObj.setAdapter(productClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_productClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionProduct.setProductClassObj(productClassList.get(arg2-1).getClassId()); 
				else
					queryConditionProduct.setProductClassObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_productClassObj.setVisibility(View.VISIBLE);
		ET_productName = (EditText) findViewById(R.id.ET_productName);
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
		// 设置发布商家下拉列表的风格
		sellerObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_sellerObj.setAdapter(sellerObj_adapter);
		// 添加事件Spinner事件监听
		spinner_sellerObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionProduct.setSellerObj(sellerList.get(arg2-1).getSellUserName()); 
				else
					queryConditionProduct.setSellerObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_sellerObj.setVisibility(View.VISIBLE);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionProduct.setProductName(ET_productName.getText().toString());
					queryConditionProduct.setAddTime(ET_addTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionProduct", queryConditionProduct);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
