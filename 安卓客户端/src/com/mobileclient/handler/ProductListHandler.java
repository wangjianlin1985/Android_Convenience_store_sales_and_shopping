package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Product;
public class ProductListHandler extends DefaultHandler {
	private List<Product> productList = null;
	private Product product;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (product != null) { 
            String valueString = new String(ch, start, length); 
            if ("productId".equals(tempString)) 
            	product.setProductId(new Integer(valueString).intValue());
            else if ("productClassObj".equals(tempString)) 
            	product.setProductClassObj(new Integer(valueString).intValue());
            else if ("productName".equals(tempString)) 
            	product.setProductName(valueString); 
            else if ("mainPhoto".equals(tempString)) 
            	product.setMainPhoto(valueString); 
            else if ("price".equals(tempString)) 
            	product.setPrice(new Float(valueString).floatValue());
            else if ("productDesc".equals(tempString)) 
            	product.setProductDesc(valueString); 
            else if ("sellerObj".equals(tempString)) 
            	product.setSellerObj(valueString); 
            else if ("addTime".equals(tempString)) 
            	product.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Product".equals(localName)&&product!=null){
			productList.add(product);
			product = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		productList = new ArrayList<Product>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Product".equals(localName)) {
            product = new Product(); 
        }
        tempString = localName; 
	}

	public List<Product> getProductList() {
		return this.productList;
	}
}
