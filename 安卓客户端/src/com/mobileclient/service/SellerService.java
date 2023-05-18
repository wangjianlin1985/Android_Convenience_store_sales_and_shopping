package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Seller;
import com.mobileclient.util.HttpUtil;

/*商家管理业务逻辑层*/
public class SellerService {
	/* 添加商家 */
	public String AddSeller(Seller seller) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sellUserName", seller.getSellUserName());
		params.put("password", seller.getPassword());
		params.put("sellerName", seller.getSellerName());
		params.put("telephone", seller.getTelephone());
		params.put("address", seller.getAddress());
		params.put("regTime", seller.getRegTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SellerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询商家 */
	public List<Seller> QuerySeller(Seller queryConditionSeller) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SellerServlet?action=query";
		if(queryConditionSeller != null) {
			urlString += "&sellUserName=" + URLEncoder.encode(queryConditionSeller.getSellUserName(), "UTF-8") + "";
			urlString += "&sellerName=" + URLEncoder.encode(queryConditionSeller.getSellerName(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionSeller.getTelephone(), "UTF-8") + "";
			urlString += "&address=" + URLEncoder.encode(queryConditionSeller.getAddress(), "UTF-8") + "";
			urlString += "&regTime=" + URLEncoder.encode(queryConditionSeller.getRegTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SellerListHandler sellerListHander = new SellerListHandler();
		xr.setContentHandler(sellerListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Seller> sellerList = sellerListHander.getSellerList();
		return sellerList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Seller> sellerList = new ArrayList<Seller>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Seller seller = new Seller();
				seller.setSellUserName(object.getString("sellUserName"));
				seller.setPassword(object.getString("password"));
				seller.setSellerName(object.getString("sellerName"));
				seller.setTelephone(object.getString("telephone"));
				seller.setAddress(object.getString("address"));
				seller.setRegTime(object.getString("regTime"));
				sellerList.add(seller);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sellerList;
	}

	/* 更新商家 */
	public String UpdateSeller(Seller seller) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sellUserName", seller.getSellUserName());
		params.put("password", seller.getPassword());
		params.put("sellerName", seller.getSellerName());
		params.put("telephone", seller.getTelephone());
		params.put("address", seller.getAddress());
		params.put("regTime", seller.getRegTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SellerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除商家 */
	public String DeleteSeller(String sellUserName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sellUserName", sellUserName);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SellerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "商家信息删除失败!";
		}
	}

	/* 根据商家账号获取商家对象 */
	public Seller GetSeller(String sellUserName)  {
		List<Seller> sellerList = new ArrayList<Seller>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sellUserName", sellUserName);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SellerServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Seller seller = new Seller();
				seller.setSellUserName(object.getString("sellUserName"));
				seller.setPassword(object.getString("password"));
				seller.setSellerName(object.getString("sellerName"));
				seller.setTelephone(object.getString("telephone"));
				seller.setAddress(object.getString("address"));
				seller.setRegTime(object.getString("regTime"));
				sellerList.add(seller);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = sellerList.size();
		if(size>0) return sellerList.get(0); 
		else return null; 
	}
}
