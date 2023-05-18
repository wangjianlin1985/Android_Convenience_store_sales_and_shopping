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
import com.chengxusheji.domain.ShopCart;

@Service @Transactional
public class ShopCartDAO {

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
    public void AddShopCart(ShopCart shopCart) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(shopCart);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ShopCart> QueryShopCartInfo(Product productObj,UserInfo userObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ShopCart shopCart where 1=1";
    	if(null != productObj && productObj.getProductId()!=0) hql += " and shopCart.productObj.productId=" + productObj.getProductId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and shopCart.userObj.user_name='" + userObj.getUser_name() + "'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List shopCartList = q.list();
    	return (ArrayList<ShopCart>) shopCartList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ShopCart> QueryShopCartInfo(Product productObj,UserInfo userObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ShopCart shopCart where 1=1";
    	if(null != productObj && productObj.getProductId()!=0) hql += " and shopCart.productObj.productId=" + productObj.getProductId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and shopCart.userObj.user_name='" + userObj.getUser_name() + "'";
    	Query q = s.createQuery(hql);
    	List shopCartList = q.list();
    	return (ArrayList<ShopCart>) shopCartList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ShopCart> QueryAllShopCartInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ShopCart";
        Query q = s.createQuery(hql);
        List shopCartList = q.list();
        return (ArrayList<ShopCart>) shopCartList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Product productObj,UserInfo userObj) {
        Session s = factory.getCurrentSession();
        String hql = "From ShopCart shopCart where 1=1";
        if(null != productObj && productObj.getProductId()!=0) hql += " and shopCart.productObj.productId=" + productObj.getProductId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and shopCart.userObj.user_name='" + userObj.getUser_name() + "'";
        Query q = s.createQuery(hql);
        List shopCartList = q.list();
        recordNumber = shopCartList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ShopCart GetShopCartByCartId(int cartId) {
        Session s = factory.getCurrentSession();
        ShopCart shopCart = (ShopCart)s.get(ShopCart.class, cartId);
        return shopCart;
    }

    /*更新ShopCart信息*/
    public void UpdateShopCart(ShopCart shopCart) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(shopCart);
    }

    /*删除ShopCart信息*/
    public void DeleteShopCart (int cartId) throws Exception {
        Session s = factory.getCurrentSession();
        Object shopCart = s.load(ShopCart.class, cartId);
        s.delete(shopCart);
    }

}
