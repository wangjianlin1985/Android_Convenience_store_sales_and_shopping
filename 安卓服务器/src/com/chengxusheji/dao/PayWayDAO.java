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
import com.chengxusheji.domain.PayWay;

@Service @Transactional
public class PayWayDAO {

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
    public void AddPayWay(PayWay payWay) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(payWay);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PayWay> QueryPayWayInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PayWay payWay where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List payWayList = q.list();
    	return (ArrayList<PayWay>) payWayList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PayWay> QueryPayWayInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From PayWay payWay where 1=1";
    	Query q = s.createQuery(hql);
    	List payWayList = q.list();
    	return (ArrayList<PayWay>) payWayList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<PayWay> QueryAllPayWayInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From PayWay";
        Query q = s.createQuery(hql);
        List payWayList = q.list();
        return (ArrayList<PayWay>) payWayList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From PayWay payWay where 1=1";
        Query q = s.createQuery(hql);
        List payWayList = q.list();
        recordNumber = payWayList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public PayWay GetPayWayByPayWayId(int payWayId) {
        Session s = factory.getCurrentSession();
        PayWay payWay = (PayWay)s.get(PayWay.class, payWayId);
        return payWay;
    }

    /*����PayWay��Ϣ*/
    public void UpdatePayWay(PayWay payWay) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(payWay);
    }

    /*ɾ��PayWay��Ϣ*/
    public void DeletePayWay (int payWayId) throws Exception {
        Session s = factory.getCurrentSession();
        Object payWay = s.load(PayWay.class, payWayId);
        s.delete(payWay);
    }

}
