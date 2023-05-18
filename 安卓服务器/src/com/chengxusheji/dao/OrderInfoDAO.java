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
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.PayWay;
import com.chengxusheji.domain.SendWay;
import com.chengxusheji.domain.Seller;
import com.chengxusheji.domain.OrderInfo;

@Service @Transactional
public class OrderInfoDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddOrderInfo(OrderInfo orderInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(orderInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderInfo> QueryOrderInfoInfo(String orderNo,UserInfo userObj,PayWay payWay,String orderStateObj,String orderTime,String receiveName,String telephone,SendWay sendWayObj,Seller sellerObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderInfo orderInfo where 1=1";
    	if(!orderNo.equals("")) hql = hql + " and orderInfo.orderNo like '%" + orderNo + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and orderInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(null != payWay && payWay.getPayWayId()!=0) hql += " and orderInfo.payWay.payWayId=" + payWay.getPayWayId();
    	if(!orderStateObj.equals("")) hql = hql + " and orderInfo.orderStateObj like '%" + orderStateObj + "%'";
    	if(!orderTime.equals("")) hql = hql + " and orderInfo.orderTime like '%" + orderTime + "%'";
    	if(!receiveName.equals("")) hql = hql + " and orderInfo.receiveName like '%" + receiveName + "%'";
    	if(!telephone.equals("")) hql = hql + " and orderInfo.telephone like '%" + telephone + "%'";
    	if(null != sendWayObj && sendWayObj.getSendWayId()!=0) hql += " and orderInfo.sendWayObj.sendWayId=" + sendWayObj.getSendWayId();
    	if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and orderInfo.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List orderInfoList = q.list();
    	return (ArrayList<OrderInfo>) orderInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderInfo> QueryOrderInfoInfo(String orderNo,UserInfo userObj,PayWay payWay,String orderStateObj,String orderTime,String receiveName,String telephone,SendWay sendWayObj,Seller sellerObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderInfo orderInfo where 1=1";
    	if(!orderNo.equals("")) hql = hql + " and orderInfo.orderNo like '%" + orderNo + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and orderInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(null != payWay && payWay.getPayWayId()!=0) hql += " and orderInfo.payWay.payWayId=" + payWay.getPayWayId();
    	if(!orderStateObj.equals("")) hql = hql + " and orderInfo.orderStateObj like '%" + orderStateObj + "%'";
    	if(!orderTime.equals("")) hql = hql + " and orderInfo.orderTime like '%" + orderTime + "%'";
    	if(!receiveName.equals("")) hql = hql + " and orderInfo.receiveName like '%" + receiveName + "%'";
    	if(!telephone.equals("")) hql = hql + " and orderInfo.telephone like '%" + telephone + "%'";
    	if(null != sendWayObj && sendWayObj.getSendWayId()!=0) hql += " and orderInfo.sendWayObj.sendWayId=" + sendWayObj.getSendWayId();
    	if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and orderInfo.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
    	Query q = s.createQuery(hql);
    	List orderInfoList = q.list();
    	return (ArrayList<OrderInfo>) orderInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderInfo> QueryAllOrderInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From OrderInfo";
        Query q = s.createQuery(hql);
        List orderInfoList = q.list();
        return (ArrayList<OrderInfo>) orderInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String orderNo,UserInfo userObj,PayWay payWay,String orderStateObj,String orderTime,String receiveName,String telephone,SendWay sendWayObj,Seller sellerObj) {
        Session s = factory.getCurrentSession();
        String hql = "From OrderInfo orderInfo where 1=1";
        if(!orderNo.equals("")) hql = hql + " and orderInfo.orderNo like '%" + orderNo + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and orderInfo.userObj.user_name='" + userObj.getUser_name() + "'";
        if(null != payWay && payWay.getPayWayId()!=0) hql += " and orderInfo.payWay.payWayId=" + payWay.getPayWayId();
        if(!orderStateObj.equals("")) hql = hql + " and orderInfo.orderStateObj like '%" + orderStateObj + "%'";
        if(!orderTime.equals("")) hql = hql + " and orderInfo.orderTime like '%" + orderTime + "%'";
        if(!receiveName.equals("")) hql = hql + " and orderInfo.receiveName like '%" + receiveName + "%'";
        if(!telephone.equals("")) hql = hql + " and orderInfo.telephone like '%" + telephone + "%'";
        if(null != sendWayObj && sendWayObj.getSendWayId()!=0) hql += " and orderInfo.sendWayObj.sendWayId=" + sendWayObj.getSendWayId();
        if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and orderInfo.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
        Query q = s.createQuery(hql);
        List orderInfoList = q.list();
        recordNumber = orderInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public OrderInfo GetOrderInfoByOrderNo(String orderNo) {
        Session s = factory.getCurrentSession();
        OrderInfo orderInfo = (OrderInfo)s.get(OrderInfo.class, orderNo);
        return orderInfo;
    }

    /*����OrderInfo��Ϣ*/
    public void UpdateOrderInfo(OrderInfo orderInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(orderInfo);
    }

    /*ɾ��OrderInfo��Ϣ*/
    public void DeleteOrderInfo (String orderNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object orderInfo = s.load(OrderInfo.class, orderNo);
        s.delete(orderInfo);
    }

}
