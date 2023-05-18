package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.OrderInfoService;
import com.mobileclient.service.ProductService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class OrderItemSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public OrderItemSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.orderitem_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_itemId = (TextView)convertView.findViewById(R.id.tv_itemId);
	  holder.tv_orderObj = (TextView)convertView.findViewById(R.id.tv_orderObj);
	  holder.tv_productObj = (TextView)convertView.findViewById(R.id.tv_productObj);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_orderNumer = (TextView)convertView.findViewById(R.id.tv_orderNumer);
	  /*设置各个控件的展示内容*/
	  holder.tv_itemId.setText("条目id：" + mData.get(position).get("itemId").toString());
	  holder.tv_orderObj.setText("所属订单：" + (new OrderInfoService()).GetOrderInfo(mData.get(position).get("orderObj").toString()).getOrderNo());
	  holder.tv_productObj.setText("订单商品：" + (new ProductService()).GetProduct(Integer.parseInt(mData.get(position).get("productObj").toString())).getProductName());
	  holder.tv_price.setText("商品单价：" + mData.get(position).get("price").toString());
	  holder.tv_orderNumer.setText("购买数量：" + mData.get(position).get("orderNumer").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_itemId;
    	TextView tv_orderObj;
    	TextView tv_productObj;
    	TextView tv_price;
    	TextView tv_orderNumer;
    }
} 
