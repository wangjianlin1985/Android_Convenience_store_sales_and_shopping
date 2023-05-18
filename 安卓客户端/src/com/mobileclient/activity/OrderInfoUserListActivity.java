package com.mobileclient.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.OrderInfoSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class OrderInfoUserListActivity extends Activity {
	OrderInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String orderNo;
	/* 订单操作业务逻辑层对象 */
	OrderInfoService orderInfoService = new OrderInfoService();
	/*保存查询参数条件的订单对象*/
	private OrderInfo queryConditionOrderInfo;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.orderinfo_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Intent intent = new Intent();
				//intent.setClass(OrderInfoUserListActivity.this, OrderInfoQueryActivity.class);
				//startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
				setViews();
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("订单查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OrderInfoUserListActivity.this, OrderInfoAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		add_btn.setVisibility(View.GONE);
		
		queryConditionOrderInfo = new OrderInfo();
		queryConditionOrderInfo.setOrderNo("");
		queryConditionOrderInfo.setUserObj(username);
		queryConditionOrderInfo.setPayWay(0);
		queryConditionOrderInfo.setOrderStateObj("");
		queryConditionOrderInfo.setOrderTime("");
		queryConditionOrderInfo.setReceiveName("");
		queryConditionOrderInfo.setTelephone("");
		queryConditionOrderInfo.setSendWayObj(0);
		queryConditionOrderInfo.setSellerObj("");
		 
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionOrderInfo = (OrderInfo)extras.getSerializable("queryConditionOrderInfo");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionOrderInfo = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new OrderInfoSimpleAdapter(OrderInfoUserListActivity.this, list,
	        					R.layout.orderinfo_list_item,
	        					new String[] { "orderNo","userObj","totalMoney","payWay","orderStateObj","orderTime","sendWayObj","sellerObj" },
	        					new int[] { R.id.tv_orderNo,R.id.tv_userObj,R.id.tv_totalMoney,R.id.tv_payWay,R.id.tv_orderStateObj,R.id.tv_orderTime,R.id.tv_sendWayObj,R.id.tv_sellerObj,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(orderInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String orderNo = list.get(arg2).get("orderNo").toString();
            	Intent intent = new Intent();
            	intent.setClass(OrderInfoUserListActivity.this, OrderInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("orderNo", orderNo);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener orderInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			//menu.add(0, 0, 0, "编辑订单信息"); 
			//menu.add(0, 1, 0, "删除订单信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑订单信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取订单编号
			orderNo = list.get(position).get("orderNo").toString();
			Intent intent = new Intent();
			intent.setClass(OrderInfoUserListActivity.this, OrderInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("orderNo", orderNo);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除订单信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取订单编号
			orderNo = list.get(position).get("orderNo").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(OrderInfoUserListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = orderInfoService.DeleteOrderInfo(orderNo);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询订单信息 */
			List<OrderInfo> orderInfoList = orderInfoService.QueryOrderInfo(queryConditionOrderInfo);
			for (int i = 0; i < orderInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNo", orderInfoList.get(i).getOrderNo());
				map.put("userObj", orderInfoList.get(i).getUserObj());
				map.put("totalMoney", orderInfoList.get(i).getTotalMoney());
				map.put("payWay", orderInfoList.get(i).getPayWay());
				map.put("orderStateObj", orderInfoList.get(i).getOrderStateObj());
				map.put("orderTime", orderInfoList.get(i).getOrderTime());
				map.put("sendWayObj", orderInfoList.get(i).getSendWayObj());
				map.put("sellerObj", orderInfoList.get(i).getSellerObj());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
