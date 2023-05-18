package com.mobileclient.domain;

import java.io.Serializable;

public class Product implements Serializable {
    /*商品id*/
    private int productId;
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /*商品类别*/
    private int productClassObj;
    public int getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(int productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*商品名称*/
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*商品主图*/
    private String mainPhoto;
    public String getMainPhoto() {
        return mainPhoto;
    }
    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    /*商品价格*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*商品描述*/
    private String productDesc;
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /*发布商家*/
    private String sellerObj;
    public String getSellerObj() {
        return sellerObj;
    }
    public void setSellerObj(String sellerObj) {
        this.sellerObj = sellerObj;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}