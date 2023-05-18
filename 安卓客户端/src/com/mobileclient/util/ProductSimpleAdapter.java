package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ProductClassService;
import com.mobileclient.service.SellerService;
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

public class ProductSimpleAdapter extends SimpleAdapter { 
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

    public ProductSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.product_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_productId = (TextView)convertView.findViewById(R.id.tv_productId);
	  holder.tv_productClassObj = (TextView)convertView.findViewById(R.id.tv_productClassObj);
	  holder.tv_productName = (TextView)convertView.findViewById(R.id.tv_productName);
	  holder.iv_mainPhoto = (ImageView)convertView.findViewById(R.id.iv_mainPhoto);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_sellerObj = (TextView)convertView.findViewById(R.id.tv_sellerObj);
	  holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_productId.setText("商品id：" + mData.get(position).get("productId").toString());
	  holder.tv_productClassObj.setText("商品类别：" + (new ProductClassService()).GetProductClass(Integer.parseInt(mData.get(position).get("productClassObj").toString())).getClassName());
	  holder.tv_productName.setText("商品名称：" + mData.get(position).get("productName").toString());
	  holder.iv_mainPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener mainPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_mainPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("mainPhoto"),mainPhotoLoadListener);  
	  holder.tv_price.setText("商品价格：" + mData.get(position).get("price").toString());
	  holder.tv_sellerObj.setText("发布商家：" + (new SellerService()).GetSeller(mData.get(position).get("sellerObj").toString()).getSellerName());
	  holder.tv_addTime.setText("发布时间：" + mData.get(position).get("addTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_productId;
    	TextView tv_productClassObj;
    	TextView tv_productName;
    	ImageView iv_mainPhoto;
    	TextView tv_price;
    	TextView tv_sellerObj;
    	TextView tv_addTime;
    }
} 
