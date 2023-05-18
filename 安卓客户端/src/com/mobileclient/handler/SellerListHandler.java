package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Seller;
public class SellerListHandler extends DefaultHandler {
	private List<Seller> sellerList = null;
	private Seller seller;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (seller != null) { 
            String valueString = new String(ch, start, length); 
            if ("sellUserName".equals(tempString)) 
            	seller.setSellUserName(valueString); 
            else if ("password".equals(tempString)) 
            	seller.setPassword(valueString); 
            else if ("sellerName".equals(tempString)) 
            	seller.setSellerName(valueString); 
            else if ("telephone".equals(tempString)) 
            	seller.setTelephone(valueString); 
            else if ("address".equals(tempString)) 
            	seller.setAddress(valueString); 
            else if ("regTime".equals(tempString)) 
            	seller.setRegTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Seller".equals(localName)&&seller!=null){
			sellerList.add(seller);
			seller = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		sellerList = new ArrayList<Seller>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Seller".equals(localName)) {
            seller = new Seller(); 
        }
        tempString = localName; 
	}

	public List<Seller> getSellerList() {
		return this.sellerList;
	}
}
