package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.SendWay;
import com.mobileclient.service.SendWayService;
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
public class SendWayDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明送货方式id控件
	private TextView TV_sendWayId;
	// 声明送货方式名称控件
	private TextView TV_sendWayName;
	/* 要保存的送货方式信息 */
	SendWay sendWay = new SendWay(); 
	/* 送货方式管理业务逻辑层 */
	private SendWayService sendWayService = new SendWayService();
	private int sendWayId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.sendway_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看送货方式详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_sendWayId = (TextView) findViewById(R.id.TV_sendWayId);
		TV_sendWayName = (TextView) findViewById(R.id.TV_sendWayName);
		Bundle extras = this.getIntent().getExtras();
		sendWayId = extras.getInt("sendWayId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SendWayDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    sendWay = sendWayService.GetSendWay(sendWayId); 
		this.TV_sendWayId.setText(sendWay.getSendWayId() + "");
		this.TV_sendWayName.setText(sendWay.getSendWayName());
	} 
}
