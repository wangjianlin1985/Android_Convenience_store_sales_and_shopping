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
import com.chengxusheji.domain.ProductClass;

@Service @Transactional
public class ProductClassDAO {

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
    public void AddProductClass(ProductClass productClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(productClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductClass> QueryProductClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ProductClass productClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List productClassList = q.list();
    	return (ArrayList<ProductClass>) productClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductClass> QueryProductClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ProductClass productClass where 1=1";
    	Query q = s.createQuery(hql);
    	List productClassList = q.list();
    	return (ArrayList<ProductClass>) productClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ProductClass> QueryAllProductClassInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ProductClass";
        Query q = s.createQuery(hql);
        List productClassList = q.list();
        return (ArrayList<ProductClass>) productClassList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From ProductClass productClass where 1=1";
        Query q = s.createQuery(hql);
        List productClassList = q.list();
        recordNumber = productClassList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ProductClass GetProductClassByClassId(int classId) {
        Session s = factory.getCurrentSession();
        ProductClass productClass = (ProductClass)s.get(ProductClass.class, classId);
        return productClass;
    }

    /*更新ProductClass信息*/
    public void UpdateProductClass(ProductClass productClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(productClass);
    }

    /*删除ProductClass信息*/
    public void DeleteProductClass (int classId) throws Exception {
        Session s = factory.getCurrentSession();
        Object productClass = s.load(ProductClass.class, classId);
        s.delete(productClass);
    }

}
