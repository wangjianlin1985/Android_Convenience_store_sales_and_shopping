package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Product {
    /*��Ʒid*/
    private int productId;
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /*��Ʒ���*/
    private ProductClass productClassObj;
    public ProductClass getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(ProductClass productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*��Ʒ����*/
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*��Ʒ��ͼ*/
    private String mainPhoto;
    public String getMainPhoto() {
        return mainPhoto;
    }
    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    /*��Ʒ�۸�*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*��Ʒ����*/
    private String productDesc;
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /*�����̼�*/
    private Seller sellerObj;
    public Seller getSellerObj() {
        return sellerObj;
    }
    public void setSellerObj(Seller sellerObj) {
        this.sellerObj = sellerObj;
    }

    /*����ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}