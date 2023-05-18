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
import com.chengxusheji.domain.Product;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.Collection;

@Service @Transactional
public class CollectionDAO {

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
    public void AddCollection(Collection collection) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(collection);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Collection> QueryCollectionInfo(Product productObj,UserInfo userObj,String collectionTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Collection collection where 1=1";
    	if(null != productObj && productObj.getProductId()!=0) hql += " and collection.productObj.productId=" + productObj.getProductId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and collection.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!collectionTime.equals("")) hql = hql + " and collection.collectionTime like '%" + collectionTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List collectionList = q.list();
    	return (ArrayList<Collection>) collectionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Collection> QueryCollectionInfo(Product productObj,UserInfo userObj,String collectionTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Collection collection where 1=1";
    	if(null != productObj && productObj.getProductId()!=0) hql += " and collection.productObj.productId=" + productObj.getProductId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and collection.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!collectionTime.equals("")) hql = hql + " and collection.collectionTime like '%" + collectionTime + "%'";
    	Query q = s.createQuery(hql);
    	List collectionList = q.list();
    	return (ArrayList<Collection>) collectionList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Collection> QueryAllCollectionInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Collection";
        Query q = s.createQuery(hql);
        List collectionList = q.list();
        return (ArrayList<Collection>) collectionList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Product productObj,UserInfo userObj,String collectionTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Collection collection where 1=1";
        if(null != productObj && productObj.getProductId()!=0) hql += " and collection.productObj.productId=" + productObj.getProductId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and collection.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!collectionTime.equals("")) hql = hql + " and collection.collectionTime like '%" + collectionTime + "%'";
        Query q = s.createQuery(hql);
        List collectionList = q.list();
        recordNumber = collectionList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Collection GetCollectionByCollectionId(int collectionId) {
        Session s = factory.getCurrentSession();
        Collection collection = (Collection)s.get(Collection.class, collectionId);
        return collection;
    }

    /*����Collection��Ϣ*/
    public void UpdateCollection(Collection collection) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(collection);
    }

    /*ɾ��Collection��Ϣ*/
    public void DeleteCollection (int collectionId) throws Exception {
        Session s = factory.getCurrentSession();
        Object collection = s.load(Collection.class, collectionId);
        s.delete(collection);
    }

}
