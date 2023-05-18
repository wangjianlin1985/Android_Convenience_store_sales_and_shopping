package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Collection {
    /*收藏id*/
    private int collectionId;
    public int getCollectionId() {
        return collectionId;
    }
    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    /*收藏宝贝*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*收藏用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*收藏时间*/
    private String collectionTime;
    public String getCollectionTime() {
        return collectionTime;
    }
    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

}