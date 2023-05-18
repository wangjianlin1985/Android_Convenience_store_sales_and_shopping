package com.mobileclient.activity;

import java.util.Date;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Collection;
import com.mobileclient.domain.Product;
import com.mobileclient.domain.ShopCart;
import com.mobileclient.service.CollectionService;
import com.mobileclient.service.ProductService;
import com.mobileclient.service.ShopCartService;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
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
public class ProductDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn,btnAddCart,btnCollect;
	// 声明商品id控件
	private TextView TV_productId;
	// 声明商品类别控件
	private TextView TV_productClassObj;
	// 声明商品名称控件
	private TextView TV_productName;
	// 声明商品主图图片框
	private ImageView iv_mainPhoto;
	// 声明商品价格控件
	private TextView TV_price;
	// 声明商品描述控件
	private TextView TV_productDesc;
	// 声明发布商家控件
	private TextView TV_sellerObj;
	// 声明发布时间控件
	private TextView TV_addTime;
	/* 要保存的商品信息 */
	Product product = new Product(); 
	/* 商品管理业务逻辑层 */
	private ProductService productService = new ProductService();
	private ProductClassService productClassService = new ProductClassService();
	private SellerService sellerService = new SellerService();
	private ShopCartService shopCartService = new ShopCartService();
	private int productId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.product_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看商品详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnAddCart = (Button) findViewById(R.id.btnAddCart);
		btnCollect = (Button) findViewById(R.id.btnCollect);
		TV_productId = (TextView) findViewById(R.id.TV_productId);
		TV_productClassObj = (TextView) findViewById(R.id.TV_productClassObj);
		TV_productName = (TextView) findViewById(R.id.TV_productName);
		iv_mainPhoto = (ImageView) findViewById(R.id.iv_mainPhoto); 
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_productDesc = (TextView) findViewById(R.id.TV_productDesc);
		TV_sellerObj = (TextView) findViewById(R.id.TV_sellerObj);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		productId = extras.getInt("productId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProductDetailActivity.this.finish();
			}
		}); 
		
		btnAddCart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//加入商品到购物车
				ShopCart shopCart = new ShopCart();
				shopCart.setBuyNum(1);
				Declare declare = (Declare) ProductDetailActivity.this.getApplication();
				shopCart.setUserObj(declare.getUserName());
				shopCart.setPrice(product.getPrice());
				shopCart.setProductObj(product.getProductId());
				ProductDetailActivity.this.setTitle("正在上传购物车信息，稍等...");
				String result = shopCartService.AddShopCart(shopCart);
				Toast.makeText(getApplicationContext(), result, 1).show(); 
				//然后跳转到购物车
				Intent intent = new Intent();
				intent.setClass(ProductDetailActivity.this, ShopCartUserListActivity.class);
				startActivity(intent);
				
			}
		}); 
		
		
		btnCollect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*调用业务逻辑层上传宝贝收藏信息*/
				Collection collection = new Collection();
				collection.setProductObj(productId);
				Declare declare = (Declare) ProductDetailActivity.this.getApplication();
				collection.setUserObj(declare.getUserName());
				collection.setCollectionTime("");
				CollectionService collectionService = new CollectionService(); 
				String result = collectionService.AddCollection(collection);
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show(); 
				
			}
		});
		
		
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    product = productService.GetProduct(productId); 
		this.TV_productId.setText(product.getProductId() + "");
		ProductClass productClassObj = productClassService.GetProductClass(product.getProductClassObj());
		this.TV_productClassObj.setText(productClassObj.getClassName());
		this.TV_productName.setText(product.getProductName());
		byte[] mainPhoto_data = null;
		try {
			// 获取图片数据
			mainPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + product.getMainPhoto());
			Bitmap mainPhoto = BitmapFactory.decodeByteArray(mainPhoto_data, 0,mainPhoto_data.length);
			this.iv_mainPhoto.setImageBitmap(mainPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_price.setText(product.getPrice() + "");
		this.TV_productDesc.setText(product.getProductDesc());
		Seller sellerObj = sellerService.GetSeller(product.getSellerObj());
		this.TV_sellerObj.setText(sellerObj.getSellerName());
		this.TV_addTime.setText(product.getAddTime());
	} 
}
