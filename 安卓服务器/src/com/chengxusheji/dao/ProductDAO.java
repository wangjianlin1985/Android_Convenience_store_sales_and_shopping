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
import com.chengxusheji.domain.Seller;
import com.chengxusheji.domain.Product;

@Service @Transactional
public class ProductDAO {

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
    public void AddProduct(Product product) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(product);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Product> QueryProductInfo(ProductClass productClassObj,String productName,Seller sellerObj,String addTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Product product where 1=1";
    	if(null != productClassObj && productClassObj.getClassId()!=0) hql += " and product.productClassObj.classId=" + productClassObj.getClassId();
    	if(!productName.equals("")) hql = hql + " and product.productName like '%" + productName + "%'";
    	if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and product.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
    	if(!addTime.equals("")) hql = hql + " and product.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List productList = q.list();
    	return (ArrayList<Product>) productList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Product> QueryProductInfo(ProductClass productClassObj,String productName,Seller sellerObj,String addTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Product product where 1=1";
    	if(null != productClassObj && productClassObj.getClassId()!=0) hql += " and product.productClassObj.classId=" + productClassObj.getClassId();
    	if(!productName.equals("")) hql = hql + " and product.productName like '%" + productName + "%'";
    	if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and product.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
    	if(!addTime.equals("")) hql = hql + " and product.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	List productList = q.list();
    	return (ArrayList<Product>) productList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Product> QueryAllProductInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Product";
        Query q = s.createQuery(hql);
        List productList = q.list();
        return (ArrayList<Product>) productList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(ProductClass productClassObj,String productName,Seller sellerObj,String addTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Product product where 1=1";
        if(null != productClassObj && productClassObj.getClassId()!=0) hql += " and product.productClassObj.classId=" + productClassObj.getClassId();
        if(!productName.equals("")) hql = hql + " and product.productName like '%" + productName + "%'";
        if(null != sellerObj && !sellerObj.getSellUserName().equals("")) hql += " and product.sellerObj.sellUserName='" + sellerObj.getSellUserName() + "'";
        if(!addTime.equals("")) hql = hql + " and product.addTime like '%" + addTime + "%'";
        Query q = s.createQuery(hql);
        List productList = q.list();
        recordNumber = productList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Product GetProductByProductId(int productId) {
        Session s = factory.getCurrentSession();
        Product product = (Product)s.get(Product.class, productId);
        return product;
    }

    /*����Product��Ϣ*/
    public void UpdateProduct(Product product) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(product);
    }

    /*ɾ��Product��Ϣ*/
    public void DeleteProduct (int productId) throws Exception {
        Session s = factory.getCurrentSession();
        Object product = s.load(Product.class, productId);
        s.delete(product);
    }

}
