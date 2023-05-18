package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
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
public class ProductClassDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明类别id控件
	private TextView TV_classId;
	// 声明类别名称控件
	private TextView TV_className;
	// 声明类别描述控件
	private TextView TV_classDesc;
	/* 要保存的商品类别信息 */
	ProductClass productClass = new ProductClass(); 
	/* 商品类别管理业务逻辑层 */
	private ProductClassService productClassService = new ProductClassService();
	private int classId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.productclass_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看商品类别详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_classId = (TextView) findViewById(R.id.TV_classId);
		TV_className = (TextView) findViewById(R.id.TV_className);
		TV_classDesc = (TextView) findViewById(R.id.TV_classDesc);
		Bundle extras = this.getIntent().getExtras();
		classId = extras.getInt("classId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProductClassDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    productClass = productClassService.GetProductClass(classId); 
		this.TV_classId.setText(productClass.getClassId() + "");
		this.TV_className.setText(productClass.getClassName());
		this.TV_classDesc.setText(productClass.getClassDesc());
	} 
}
