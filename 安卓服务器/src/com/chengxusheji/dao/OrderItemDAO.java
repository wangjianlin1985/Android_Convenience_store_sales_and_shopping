package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.domain.Product;
import com.chengxusheji.domain.OrderItem;

@Service @Transactional
public class OrderItemDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddOrderItem(OrderItem orderItem) throws Exception {
    	Session s = factory.getCurrentSession();
    	s.merge(orderItem);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderItem> QueryOrderItemInfo(OrderInfo orderObj,Product productObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderItem orderItem where 1=1";
    	if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderItem.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
    	if(null != productObj && productObj.getProductId()!=0) hql += " and orderItem.productObj.productId=" + productObj.getProductId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List orderItemList = q.list();
    	return (ArrayList<OrderItem>) orderItemList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderItem> QueryOrderItemInfo(OrderInfo orderObj,Product productObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderItem orderItem where 1=1";
    	if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderItem.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
    	if(null != productObj && productObj.getProductId()!=0) hql += " and orderItem.productObj.productId=" + productObj.getProductId();
    	Query q = s.createQuery(hql);
    	List orderItemList = q.list();
    	return (ArrayList<OrderItem>) orderItemList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderItem> QueryAllOrderItemInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From OrderItem";
        Query q = s.createQuery(hql);
        List orderItemList = q.list();
        return (ArrayList<OrderItem>) orderItemList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(OrderInfo orderObj,Product productObj) {
        Session s = factory.getCurrentSession();
        String hql = "From OrderItem orderItem where 1=1";
        if(null != orderObj && !orderObj.getOrderNo().equals("")) hql += " and orderItem.orderObj.orderNo='" + orderObj.getOrderNo() + "'";
        if(null != productObj && productObj.getProductId()!=0) hql += " and orderItem.productObj.productId=" + productObj.getProductId();
        Query q = s.createQuery(hql);
        List orderItemList = q.list();
        recordNumber = orderItemList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public OrderItem GetOrderItemByItemId(int itemId) {
        Session s = factory.getCurrentSession();
        OrderItem orderItem = (OrderItem)s.get(OrderItem.class, itemId);
        return orderItem;
    }

    /*更新OrderItem信息*/
    public void UpdateOrderItem(OrderItem orderItem) throws Exception {
        Session s = factory.getCurrentSession();
        s.merge(orderItem);
    }

    /*删除OrderItem信息*/
    public void DeleteOrderItem (int itemId) throws Exception {
        Session s = factory.getCurrentSession();
        Object orderItem = s.load(OrderItem.class, itemId);
        s.delete(orderItem);
    }

}
