package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ProductClass;
public class ProductClassListHandler extends DefaultHandler {
	private List<ProductClass> productClassList = null;
	private ProductClass productClass;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (productClass != null) { 
            String valueString = new String(ch, start, length); 
            if ("classId".equals(tempString)) 
            	productClass.setClassId(new Integer(valueString).intValue());
            else if ("className".equals(tempString)) 
            	productClass.setClassName(valueString); 
            else if ("classDesc".equals(tempString)) 
            	productClass.setClassDesc(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ProductClass".equals(localName)&&productClass!=null){
			productClassList.add(productClass);
			productClass = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		productClassList = new ArrayList<ProductClass>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ProductClass".equals(localName)) {
            productClass = new ProductClass(); 
        }
        tempString = localName; 
	}

	public List<ProductClass> getProductClassList() {
		return this.productClassList;
	}
}
