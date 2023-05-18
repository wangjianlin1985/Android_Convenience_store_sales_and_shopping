package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SendWay;
import com.mobileclient.util.HttpUtil;

/*送货方式管理业务逻辑层*/
public class SendWayService {
	/* 添加送货方式 */
	public String AddSendWay(SendWay sendWay) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sendWayId", sendWay.getSendWayId() + "");
		params.put("sendWayName", sendWay.getSendWayName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SendWayServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询送货方式 */
	public List<SendWay> QuerySendWay(SendWay queryConditionSendWay) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SendWayServlet?action=query";
		if(queryConditionSendWay != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SendWayListHandler sendWayListHander = new SendWayListHandler();
		xr.setContentHandler(sendWayListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SendWay> sendWayList = sendWayListHander.getSendWayList();
		return sendWayList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SendWay> sendWayList = new ArrayList<SendWay>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SendWay sendWay = new SendWay();
				sendWay.setSendWayId(object.getInt("sendWayId"));
				sendWay.setSendWayName(object.getString("sendWayName"));
				sendWayList.add(sendWay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendWayList;
	}

	/* 更新送货方式 */
	public String UpdateSendWay(SendWay sendWay) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sendWayId", sendWay.getSendWayId() + "");
		params.put("sendWayName", sendWay.getSendWayName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SendWayServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除送货方式 */
	public String DeleteSendWay(int sendWayId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sendWayId", sendWayId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SendWayServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "送货方式信息删除失败!";
		}
	}

	/* 根据送货方式id获取送货方式对象 */
	public SendWay GetSendWay(int sendWayId)  {
		List<SendWay> sendWayList = new ArrayList<SendWay>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sendWayId", sendWayId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SendWayServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SendWay sendWay = new SendWay();
				sendWay.setSendWayId(object.getInt("sendWayId"));
				sendWay.setSendWayName(object.getString("sendWayName"));
				sendWayList.add(sendWay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = sendWayList.size();
		if(size>0) return sendWayList.get(0); 
		else return null; 
	}
}
