package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Seller;

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
public class SellerQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明商家账号输入框
	private EditText ET_sellUserName;
	// 声明商家名称输入框
	private EditText ET_sellerName;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明商家地址输入框
	private EditText ET_address;
	// 声明入住时间输入框
	private EditText ET_regTime;
	/*查询过滤条件保存到这个对象中*/
	private Seller queryConditionSeller = new Seller();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.seller_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置商家查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_sellUserName = (EditText) findViewById(R.id.ET_sellUserName);
		ET_sellerName = (EditText) findViewById(R.id.ET_sellerName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_regTime = (EditText) findViewById(R.id.ET_regTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionSeller.setSellUserName(ET_sellUserName.getText().toString());
					queryConditionSeller.setSellerName(ET_sellerName.getText().toString());
					queryConditionSeller.setTelephone(ET_telephone.getText().toString());
					queryConditionSeller.setAddress(ET_address.getText().toString());
					queryConditionSeller.setRegTime(ET_regTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionSeller", queryConditionSeller);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
