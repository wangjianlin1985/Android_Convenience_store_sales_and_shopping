package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ShopCart;
import com.mobileclient.service.ShopCartService;
import com.mobileclient.domain.Product;
import com.mobileclient.service.ProductService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class ShopCartDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明购物车id控件
	private TextView TV_cartId;
	// 声明商品控件
	private TextView TV_productObj;
	// 声明用户控件
	private TextView TV_userObj;
	// 声明单价控件
	private TextView TV_price;
	// 声明购买数量控件
	private TextView TV_buyNum;
	/* 要保存的购物车信息 */
	ShopCart shopCart = new ShopCart(); 
	/* 购物车管理业务逻辑层 */
	private ShopCartService shopCartService = new ShopCartService();
	private ProductService productService = new ProductService();
	private UserInfoService userInfoService = new UserInfoService();
	private int cartId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.shopcart_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看购物车详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_cartId = (TextView) findViewById(R.id.TV_cartId);
		TV_productObj = (TextView) findViewById(R.id.TV_productObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_buyNum = (TextView) findViewById(R.id.TV_buyNum);
		Bundle extras = this.getIntent().getExtras();
		cartId = extras.getInt("cartId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ShopCartDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    shopCart = shopCartService.GetShopCart(cartId); 
		this.TV_cartId.setText(shopCart.getCartId() + "");
		Product productObj = productService.GetProduct(shopCart.getProductObj());
		this.TV_productObj.setText(productObj.getProductName());
		UserInfo userObj = userInfoService.GetUserInfo(shopCart.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_price.setText(shopCart.getPrice() + "");
		this.TV_buyNum.setText(shopCart.getBuyNum() + "");
	} 
}
