package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.OrderInfo;
public class OrderInfoListHandler extends DefaultHandler {
	private List<OrderInfo> orderInfoList = null;
	private OrderInfo orderInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (orderInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("orderNo".equals(tempString)) 
            	orderInfo.setOrderNo(valueString); 
            else if ("userObj".equals(tempString)) 
            	orderInfo.setUserObj(valueString); 
            else if ("totalMoney".equals(tempString)) 
            	orderInfo.setTotalMoney(new Float(valueString).floatValue());
            else if ("payWay".equals(tempString)) 
            	orderInfo.setPayWay(new Integer(valueString).intValue());
            else if ("orderStateObj".equals(tempString)) 
            	orderInfo.setOrderStateObj(valueString); 
            else if ("orderTime".equals(tempString)) 
            	orderInfo.setOrderTime(valueString); 
            else if ("receiveName".equals(tempString)) 
            	orderInfo.setReceiveName(valueString); 
            else if ("telephone".equals(tempString)) 
            	orderInfo.setTelephone(valueString); 
            else if ("address".equals(tempString)) 
            	orderInfo.setAddress(valueString); 
            else if ("sendWayObj".equals(tempString)) 
            	orderInfo.setSendWayObj(new Integer(valueString).intValue());
            else if ("orderMemo".equals(tempString)) 
            	orderInfo.setOrderMemo(valueString); 
            else if ("sellerObj".equals(tempString)) 
            	orderInfo.setSellerObj(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("OrderInfo".equals(localName)&&orderInfo!=null){
			orderInfoList.add(orderInfo);
			orderInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		orderInfoList = new ArrayList<OrderInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("OrderInfo".equals(localName)) {
            orderInfo = new OrderInfo(); 
        }
        tempString = localName; 
	}

	public List<OrderInfo> getOrderInfoList() {
		return this.orderInfoList;
	}
}
