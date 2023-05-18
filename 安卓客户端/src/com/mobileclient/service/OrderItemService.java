package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.OrderItem;
import com.mobileclient.util.HttpUtil;

/*订单条目管理业务逻辑层*/
public class OrderItemService {
	/* 添加订单条目 */
	public String AddOrderItem(OrderItem orderItem) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", orderItem.getItemId() + "");
		params.put("orderObj", orderItem.getOrderObj());
		params.put("productObj", orderItem.getProductObj() + "");
		params.put("price", orderItem.getPrice() + "");
		params.put("orderNumer", orderItem.getOrderNumer() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询订单条目 */
	public List<OrderItem> QueryOrderItem(OrderItem queryConditionOrderItem) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderItemServlet?action=query";
		if(queryConditionOrderItem != null) {
			urlString += "&orderObj=" + URLEncoder.encode(queryConditionOrderItem.getOrderObj(), "UTF-8") + "";
			urlString += "&productObj=" + queryConditionOrderItem.getProductObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderItemListHandler orderItemListHander = new OrderItemListHandler();
		xr.setContentHandler(orderItemListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderItem> orderItemList = orderItemListHander.getOrderItemList();
		return orderItemList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderItem orderItem = new OrderItem();
				orderItem.setItemId(object.getInt("itemId"));
				orderItem.setOrderObj(object.getString("orderObj"));
				orderItem.setProductObj(object.getInt("productObj"));
				orderItem.setPrice((float) object.getDouble("price"));
				orderItem.setOrderNumer(object.getInt("orderNumer"));
				orderItemList.add(orderItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderItemList;
	}

	/* 更新订单条目 */
	public String UpdateOrderItem(OrderItem orderItem) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", orderItem.getItemId() + "");
		params.put("orderObj", orderItem.getOrderObj());
		params.put("productObj", orderItem.getProductObj() + "");
		params.put("price", orderItem.getPrice() + "");
		params.put("orderNumer", orderItem.getOrderNumer() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除订单条目 */
	public String DeleteOrderItem(int itemId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "订单条目信息删除失败!";
		}
	}

	/* 根据条目id获取订单条目对象 */
	public OrderItem GetOrderItem(int itemId)  {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", itemId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderItemServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderItem orderItem = new OrderItem();
				orderItem.setItemId(object.getInt("itemId"));
				orderItem.setOrderObj(object.getString("orderObj"));
				orderItem.setProductObj(object.getInt("productObj"));
				orderItem.setPrice((float) object.getDouble("price"));
				orderItem.setOrderNumer(object.getInt("orderNumer"));
				orderItemList.add(orderItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderItemList.size();
		if(size>0) return orderItemList.get(0); 
		else return null; 
	}
}
