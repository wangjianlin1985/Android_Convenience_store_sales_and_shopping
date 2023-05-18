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
import com.chengxusheji.domain.SendWay;

@Service @Transactional
public class SendWayDAO {

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
    public void AddSendWay(SendWay sendWay) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(sendWay);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SendWay> QuerySendWayInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SendWay sendWay where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List sendWayList = q.list();
    	return (ArrayList<SendWay>) sendWayList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SendWay> QuerySendWayInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SendWay sendWay where 1=1";
    	Query q = s.createQuery(hql);
    	List sendWayList = q.list();
    	return (ArrayList<SendWay>) sendWayList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SendWay> QueryAllSendWayInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SendWay";
        Query q = s.createQuery(hql);
        List sendWayList = q.list();
        return (ArrayList<SendWay>) sendWayList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From SendWay sendWay where 1=1";
        Query q = s.createQuery(hql);
        List sendWayList = q.list();
        recordNumber = sendWayList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SendWay GetSendWayBySendWayId(int sendWayId) {
        Session s = factory.getCurrentSession();
        SendWay sendWay = (SendWay)s.get(SendWay.class, sendWayId);
        return sendWay;
    }

    /*更新SendWay信息*/
    public void UpdateSendWay(SendWay sendWay) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(sendWay);
    }

    /*删除SendWay信息*/
    public void DeleteSendWay (int sendWayId) throws Exception {
        Session s = factory.getCurrentSession();
        Object sendWay = s.load(SendWay.class, sendWayId);
        s.delete(sendWay);
    }

}
