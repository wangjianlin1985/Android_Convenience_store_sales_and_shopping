package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.util.HttpUtil;

/*订单管理业务逻辑层*/
public class OrderInfoService {
	/* 添加订单 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderInfo.getOrderNo());
		params.put("userObj", orderInfo.getUserObj());
		params.put("totalMoney", orderInfo.getTotalMoney() + "");
		params.put("payWay", orderInfo.getPayWay() + "");
		params.put("orderStateObj", orderInfo.getOrderStateObj());
		params.put("orderTime", orderInfo.getOrderTime());
		params.put("receiveName", orderInfo.getReceiveName());
		params.put("telephone", orderInfo.getTelephone());
		params.put("address", orderInfo.getAddress());
		params.put("sendWayObj", orderInfo.getSendWayObj() + "");
		params.put("orderMemo", orderInfo.getOrderMemo());
		params.put("sellerObj", orderInfo.getSellerObj());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询订单 */
	public List<OrderInfo> QueryOrderInfo(OrderInfo queryConditionOrderInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=query";
		if(queryConditionOrderInfo != null) {
			urlString += "&orderNo=" + URLEncoder.encode(queryConditionOrderInfo.getOrderNo(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionOrderInfo.getUserObj(), "UTF-8") + "";
			urlString += "&payWay=" + queryConditionOrderInfo.getPayWay();
			urlString += "&orderStateObj=" + URLEncoder.encode(queryConditionOrderInfo.getOrderStateObj(), "UTF-8") + "";
			urlString += "&orderTime=" + URLEncoder.encode(queryConditionOrderInfo.getOrderTime(), "UTF-8") + "";
			urlString += "&receiveName=" + URLEncoder.encode(queryConditionOrderInfo.getReceiveName(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionOrderInfo.getTelephone(), "UTF-8") + "";
			urlString += "&sendWayObj=" + queryConditionOrderInfo.getSendWayObj();
			urlString += "&sellerObj=" + URLEncoder.encode(queryConditionOrderInfo.getSellerObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderInfoListHandler orderInfoListHander = new OrderInfoListHandler();
		xr.setContentHandler(orderInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderInfo> orderInfoList = orderInfoListHander.getOrderInfoList();
		return orderInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNo(object.getString("orderNo"));
				orderInfo.setUserObj(object.getString("userObj"));
				orderInfo.setTotalMoney((float) object.getDouble("totalMoney"));
				orderInfo.setPayWay(object.getInt("payWay"));
				orderInfo.setOrderStateObj(object.getString("orderStateObj"));
				orderInfo.setOrderTime(object.getString("orderTime"));
				orderInfo.setReceiveName(object.getString("receiveName"));
				orderInfo.setTelephone(object.getString("telephone"));
				orderInfo.setAddress(object.getString("address"));
				orderInfo.setSendWayObj(object.getInt("sendWayObj"));
				orderInfo.setOrderMemo(object.getString("orderMemo"));
				orderInfo.setSellerObj(object.getString("sellerObj"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderInfoList;
	}

	/* 更新订单 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderInfo.getOrderNo());
		params.put("userObj", orderInfo.getUserObj());
		params.put("totalMoney", orderInfo.getTotalMoney() + "");
		params.put("payWay", orderInfo.getPayWay() + "");
		params.put("orderStateObj", orderInfo.getOrderStateObj());
		params.put("orderTime", orderInfo.getOrderTime());
		params.put("receiveName", orderInfo.getReceiveName());
		params.put("telephone", orderInfo.getTelephone());
		params.put("address", orderInfo.getAddress());
		params.put("sendWayObj", orderInfo.getSendWayObj() + "");
		params.put("orderMemo", orderInfo.getOrderMemo());
		params.put("sellerObj", orderInfo.getSellerObj());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除订单 */
	public String DeleteOrderInfo(String orderNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "订单信息删除失败!";
		}
	}

	/* 根据订单编号获取订单对象 */
	public OrderInfo GetOrderInfo(String orderNo)  {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNo(object.getString("orderNo"));
				orderInfo.setUserObj(object.getString("userObj"));
				orderInfo.setTotalMoney((float) object.getDouble("totalMoney"));
				orderInfo.setPayWay(object.getInt("payWay"));
				orderInfo.setOrderStateObj(object.getString("orderStateObj"));
				orderInfo.setOrderTime(object.getString("orderTime"));
				orderInfo.setReceiveName(object.getString("receiveName"));
				orderInfo.setTelephone(object.getString("telephone"));
				orderInfo.setAddress(object.getString("address"));
				orderInfo.setSendWayObj(object.getInt("sendWayObj"));
				orderInfo.setOrderMemo(object.getString("orderMemo"));
				orderInfo.setSellerObj(object.getString("sellerObj"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderInfoList.size();
		if(size>0) return orderInfoList.get(0); 
		else return null; 
	}
}
