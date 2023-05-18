package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.PayWay;
public class PayWayListHandler extends DefaultHandler {
	private List<PayWay> payWayList = null;
	private PayWay payWay;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (payWay != null) { 
            String valueString = new String(ch, start, length); 
            if ("payWayId".equals(tempString)) 
            	payWay.setPayWayId(new Integer(valueString).intValue());
            else if ("payWayName".equals(tempString)) 
            	payWay.setPayWayName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("PayWay".equals(localName)&&payWay!=null){
			payWayList.add(payWay);
			payWay = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		payWayList = new ArrayList<PayWay>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("PayWay".equals(localName)) {
            payWay = new PayWay(); 
        }
        tempString = localName; 
	}

	public List<PayWay> getPayWayList() {
		return this.payWayList;
	}
}
