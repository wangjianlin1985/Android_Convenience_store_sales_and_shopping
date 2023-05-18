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
    public void AddSendWay(SendWay sendWay) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(sendWay);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SendWay> QuerySendWayInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SendWay sendWay where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SendWay GetSendWayBySendWayId(int sendWayId) {
        Session s = factory.getCurrentSession();
        SendWay sendWay = (SendWay)s.get(SendWay.class, sendWayId);
        return sendWay;
    }

    /*����SendWay��Ϣ*/
    public void UpdateSendWay(SendWay sendWay) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(sendWay);
    }

    /*ɾ��SendWay��Ϣ*/
    public void DeleteSendWay (int sendWayId) throws Exception {
        Session s = factory.getCurrentSession();
        Object sendWay = s.load(SendWay.class, sendWayId);
        s.delete(sendWay);
    }

}
