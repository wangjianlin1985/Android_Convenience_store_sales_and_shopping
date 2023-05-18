package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ProductClass;
import com.mobileclient.util.HttpUtil;

/*商品类别管理业务逻辑层*/
public class ProductClassService {
	/* 添加商品类别 */
	public String AddProductClass(ProductClass productClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", productClass.getClassId() + "");
		params.put("className", productClass.getClassName());
		params.put("classDesc", productClass.getClassDesc());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询商品类别 */
	public List<ProductClass> QueryProductClass(ProductClass queryConditionProductClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ProductClassServlet?action=query";
		if(queryConditionProductClass != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ProductClassListHandler productClassListHander = new ProductClassListHandler();
		xr.setContentHandler(productClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ProductClass> productClassList = productClassListHander.getProductClassList();
		return productClassList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ProductClass> productClassList = new ArrayList<ProductClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductClass productClass = new ProductClass();
				productClass.setClassId(object.getInt("classId"));
				productClass.setClassName(object.getString("className"));
				productClass.setClassDesc(object.getString("classDesc"));
				productClassList.add(productClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productClassList;
	}

	/* 更新商品类别 */
	public String UpdateProductClass(ProductClass productClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", productClass.getClassId() + "");
		params.put("className", productClass.getClassName());
		params.put("classDesc", productClass.getClassDesc());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除商品类别 */
	public String DeleteProductClass(int classId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", classId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "商品类别信息删除失败!";
		}
	}

	/* 根据类别id获取商品类别对象 */
	public ProductClass GetProductClass(int classId)  {
		List<ProductClass> productClassList = new ArrayList<ProductClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", classId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ProductClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ProductClass productClass = new ProductClass();
				productClass.setClassId(object.getInt("classId"));
				productClass.setClassName(object.getString("className"));
				productClass.setClassDesc(object.getString("classDesc"));
				productClassList.add(productClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = productClassList.size();
		if(size>0) return productClassList.get(0); 
		else return null; 
	}
}
