package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SendWay;
public class SendWayListHandler extends DefaultHandler {
	private List<SendWay> sendWayList = null;
	private SendWay sendWay;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (sendWay != null) { 
            String valueString = new String(ch, start, length); 
            if ("sendWayId".equals(tempString)) 
            	sendWay.setSendWayId(new Integer(valueString).intValue());
            else if ("sendWayName".equals(tempString)) 
            	sendWay.setSendWayName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SendWay".equals(localName)&&sendWay!=null){
			sendWayList.add(sendWay);
			sendWay = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		sendWayList = new ArrayList<SendWay>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SendWay".equals(localName)) {
            sendWay = new SendWay(); 
        }
        tempString = localName; 
	}

	public List<SendWay> getSendWayList() {
		return this.sendWayList;
	}
}
