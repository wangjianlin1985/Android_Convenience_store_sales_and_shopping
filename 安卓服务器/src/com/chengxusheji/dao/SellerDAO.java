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
import com.chengxusheji.domain.Seller;

@Service @Transactional
public class SellerDAO {

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
    public void AddSeller(Seller seller) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(seller);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Seller> QuerySellerInfo(String sellUserName,String sellerName,String telephone,String address,String regTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Seller seller where 1=1";
    	if(!sellUserName.equals("")) hql = hql + " and seller.sellUserName like '%" + sellUserName + "%'";
    	if(!sellerName.equals("")) hql = hql + " and seller.sellerName like '%" + sellerName + "%'";
    	if(!telephone.equals("")) hql = hql + " and seller.telephone like '%" + telephone + "%'";
    	if(!address.equals("")) hql = hql + " and seller.address like '%" + address + "%'";
    	if(!regTime.equals("")) hql = hql + " and seller.regTime like '%" + regTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List sellerList = q.list();
    	return (ArrayList<Seller>) sellerList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Seller> QuerySellerInfo(String sellUserName,String sellerName,String telephone,String address,String regTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Seller seller where 1=1";
    	if(!sellUserName.equals("")) hql = hql + " and seller.sellUserName like '%" + sellUserName + "%'";
    	if(!sellerName.equals("")) hql = hql + " and seller.sellerName like '%" + sellerName + "%'";
    	if(!telephone.equals("")) hql = hql + " and seller.telephone like '%" + telephone + "%'";
    	if(!address.equals("")) hql = hql + " and seller.address like '%" + address + "%'";
    	if(!regTime.equals("")) hql = hql + " and seller.regTime like '%" + regTime + "%'";
    	Query q = s.createQuery(hql);
    	List sellerList = q.list();
    	return (ArrayList<Seller>) sellerList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Seller> QueryAllSellerInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Seller";
        Query q = s.createQuery(hql);
        List sellerList = q.list();
        return (ArrayList<Seller>) sellerList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String sellUserName,String sellerName,String telephone,String address,String regTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Seller seller where 1=1";
        if(!sellUserName.equals("")) hql = hql + " and seller.sellUserName like '%" + sellUserName + "%'";
        if(!sellerName.equals("")) hql = hql + " and seller.sellerName like '%" + sellerName + "%'";
        if(!telephone.equals("")) hql = hql + " and seller.telephone like '%" + telephone + "%'";
        if(!address.equals("")) hql = hql + " and seller.address like '%" + address + "%'";
        if(!regTime.equals("")) hql = hql + " and seller.regTime like '%" + regTime + "%'";
        Query q = s.createQuery(hql);
        List sellerList = q.list();
        recordNumber = sellerList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Seller GetSellerBySellUserName(String sellUserName) {
        Session s = factory.getCurrentSession();
        Seller seller = (Seller)s.get(Seller.class, sellUserName);
        return seller;
    }

    /*更新Seller信息*/
    public void UpdateSeller(Seller seller) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(seller);
    }

    /*删除Seller信息*/
    public void DeleteSeller (String sellUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object seller = s.load(Seller.class, sellUserName);
        s.delete(seller);
    }

}
