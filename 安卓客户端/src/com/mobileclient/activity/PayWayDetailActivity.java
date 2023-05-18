package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.PayWay;
import com.mobileclient.service.PayWayService;
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
public class PayWayDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明支付方式id控件
	private TextView TV_payWayId;
	// 声明支付方式名称控件
	private TextView TV_payWayName;
	/* 要保存的支付方式信息 */
	PayWay payWay = new PayWay(); 
	/* 支付方式管理业务逻辑层 */
	private PayWayService payWayService = new PayWayService();
	private int payWayId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.payway_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看支付方式详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_payWayId = (TextView) findViewById(R.id.TV_payWayId);
		TV_payWayName = (TextView) findViewById(R.id.TV_payWayName);
		Bundle extras = this.getIntent().getExtras();
		payWayId = extras.getInt("payWayId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PayWayDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    payWay = payWayService.GetPayWay(payWayId); 
		this.TV_payWayId.setText(payWay.getPayWayId() + "");
		this.TV_payWayName.setText(payWay.getPayWayName());
	} 
}
