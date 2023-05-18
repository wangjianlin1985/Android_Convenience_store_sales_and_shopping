package com.mobileclient.activity;

import java.util.Date;
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
public class SellerDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn,btnViewProduct;
	// 声明商家账号控件
	private TextView TV_sellUserName;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明商家名称控件
	private TextView TV_sellerName;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明商家地址控件
	private TextView TV_address;
	// 声明入住时间控件
	private TextView TV_regTime;
	/* 要保存的商家信息 */
	Seller seller = new Seller(); 
	/* 商家管理业务逻辑层 */
	private SellerService sellerService = new SellerService();
	private String sellUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.seller_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看商家详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnViewProduct = (Button) findViewById(R.id.BtnViewProduct);
		TV_sellUserName = (TextView) findViewById(R.id.TV_sellUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_sellerName = (TextView) findViewById(R.id.TV_sellerName);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_regTime = (TextView) findViewById(R.id.TV_regTime);
		Bundle extras = this.getIntent().getExtras();
		sellUserName = extras.getString("sellUserName");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SellerDetailActivity.this.finish();
			}
		}); 
		
		btnViewProduct.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 查看这店铺里面的商品信息
				Intent intent = new Intent(SellerDetailActivity.this, ProductListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("sellUserName", sellUserName);
				intent.putExtras(bundle);
				startActivity(intent); 
			}
		}); 
		
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    seller = sellerService.GetSeller(sellUserName); 
		this.TV_sellUserName.setText(seller.getSellUserName());
		this.TV_password.setText(seller.getPassword());
		this.TV_sellerName.setText(seller.getSellerName());
		this.TV_telephone.setText(seller.getTelephone());
		this.TV_address.setText(seller.getAddress());
		this.TV_regTime.setText(seller.getRegTime());
	} 
}
