package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ShopCart;
import com.mobileclient.util.HttpUtil;

/*购物车管理业务逻辑层*/
public class ShopCartService {
	/* 添加购物车 */
	public String AddShopCart(ShopCart shopCart) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", shopCart.getCartId() + "");
		params.put("productObj", shopCart.getProductObj() + "");
		params.put("userObj", shopCart.getUserObj());
		params.put("price", shopCart.getPrice() + "");
		params.put("buyNum", shopCart.getBuyNum() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShopCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询购物车 */
	public List<ShopCart> QueryShopCart(ShopCart queryConditionShopCart) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ShopCartServlet?action=query";
		if(queryConditionShopCart != null) {
			urlString += "&productObj=" + queryConditionShopCart.getProductObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionShopCart.getUserObj(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ShopCartListHandler shopCartListHander = new ShopCartListHandler();
		xr.setContentHandler(shopCartListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ShopCart> shopCartList = shopCartListHander.getShopCartList();
		return shopCartList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ShopCart> shopCartList = new ArrayList<ShopCart>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ShopCart shopCart = new ShopCart();
				shopCart.setCartId(object.getInt("cartId"));
				shopCart.setProductObj(object.getInt("productObj"));
				shopCart.setUserObj(object.getString("userObj"));
				shopCart.setPrice((float) object.getDouble("price"));
				shopCart.setBuyNum(object.getInt("buyNum"));
				shopCartList.add(shopCart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shopCartList;
	}

	/* 更新购物车 */
	public String UpdateShopCart(ShopCart shopCart) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", shopCart.getCartId() + "");
		params.put("productObj", shopCart.getProductObj() + "");
		params.put("userObj", shopCart.getUserObj());
		params.put("price", shopCart.getPrice() + "");
		params.put("buyNum", shopCart.getBuyNum() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShopCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除购物车 */
	public String DeleteShopCart(int cartId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", cartId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShopCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "购物车信息删除失败!";
		}
	}

	/* 根据购物车id获取购物车对象 */
	public ShopCart GetShopCart(int cartId)  {
		List<ShopCart> shopCartList = new ArrayList<ShopCart>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cartId", cartId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ShopCartServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ShopCart shopCart = new ShopCart();
				shopCart.setCartId(object.getInt("cartId"));
				shopCart.setProductObj(object.getInt("productObj"));
				shopCart.setUserObj(object.getString("userObj"));
				shopCart.setPrice((float) object.getDouble("price"));
				shopCart.setBuyNum(object.getInt("buyNum"));
				shopCartList.add(shopCart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = shopCartList.size();
		if(size>0) return shopCartList.get(0); 
		else return null; 
	}
}
