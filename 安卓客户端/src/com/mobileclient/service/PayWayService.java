package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.PayWay;
import com.mobileclient.util.HttpUtil;

/*支付方式管理业务逻辑层*/
public class PayWayService {
	/* 添加支付方式 */
	public String AddPayWay(PayWay payWay) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("payWayId", payWay.getPayWayId() + "");
		params.put("payWayName", payWay.getPayWayName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PayWayServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询支付方式 */
	public List<PayWay> QueryPayWay(PayWay queryConditionPayWay) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PayWayServlet?action=query";
		if(queryConditionPayWay != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PayWayListHandler payWayListHander = new PayWayListHandler();
		xr.setContentHandler(payWayListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<PayWay> payWayList = payWayListHander.getPayWayList();
		return payWayList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<PayWay> payWayList = new ArrayList<PayWay>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PayWay payWay = new PayWay();
				payWay.setPayWayId(object.getInt("payWayId"));
				payWay.setPayWayName(object.getString("payWayName"));
				payWayList.add(payWay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payWayList;
	}

	/* 更新支付方式 */
	public String UpdatePayWay(PayWay payWay) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("payWayId", payWay.getPayWayId() + "");
		params.put("payWayName", payWay.getPayWayName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PayWayServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除支付方式 */
	public String DeletePayWay(int payWayId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("payWayId", payWayId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PayWayServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "支付方式信息删除失败!";
		}
	}

	/* 根据支付方式id获取支付方式对象 */
	public PayWay GetPayWay(int payWayId)  {
		List<PayWay> payWayList = new ArrayList<PayWay>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("payWayId", payWayId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PayWayServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				PayWay payWay = new PayWay();
				payWay.setPayWayId(object.getInt("payWayId"));
				payWay.setPayWayName(object.getString("payWayName"));
				payWayList.add(payWay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = payWayList.size();
		if(size>0) return payWayList.get(0); 
		else return null; 
	}
}
