package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ShopCart;
public class ShopCartListHandler extends DefaultHandler {
	private List<ShopCart> shopCartList = null;
	private ShopCart shopCart;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (shopCart != null) { 
            String valueString = new String(ch, start, length); 
            if ("cartId".equals(tempString)) 
            	shopCart.setCartId(new Integer(valueString).intValue());
            else if ("productObj".equals(tempString)) 
            	shopCart.setProductObj(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	shopCart.setUserObj(valueString); 
            else if ("price".equals(tempString)) 
            	shopCart.setPrice(new Float(valueString).floatValue());
            else if ("buyNum".equals(tempString)) 
            	shopCart.setBuyNum(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ShopCart".equals(localName)&&shopCart!=null){
			shopCartList.add(shopCart);
			shopCart = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		shopCartList = new ArrayList<ShopCart>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ShopCart".equals(localName)) {
            shopCart = new ShopCart(); 
        }
        tempString = localName; 
	}

	public List<ShopCart> getShopCartList() {
		return this.shopCartList;
	}
}
