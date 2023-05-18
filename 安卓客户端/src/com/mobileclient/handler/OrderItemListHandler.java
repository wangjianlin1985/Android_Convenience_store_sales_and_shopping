package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.OrderItem;
public class OrderItemListHandler extends DefaultHandler {
	private List<OrderItem> orderItemList = null;
	private OrderItem orderItem;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (orderItem != null) { 
            String valueString = new String(ch, start, length); 
            if ("itemId".equals(tempString)) 
            	orderItem.setItemId(new Integer(valueString).intValue());
            else if ("orderObj".equals(tempString)) 
            	orderItem.setOrderObj(valueString); 
            else if ("productObj".equals(tempString)) 
            	orderItem.setProductObj(new Integer(valueString).intValue());
            else if ("price".equals(tempString)) 
            	orderItem.setPrice(new Float(valueString).floatValue());
            else if ("orderNumer".equals(tempString)) 
            	orderItem.setOrderNumer(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("OrderItem".equals(localName)&&orderItem!=null){
			orderItemList.add(orderItem);
			orderItem = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		orderItemList = new ArrayList<OrderItem>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("OrderItem".equals(localName)) {
            orderItem = new OrderItem(); 
        }
        tempString = localName; 
	}

	public List<OrderItem> getOrderItemList() {
		return this.orderItemList;
	}
}
