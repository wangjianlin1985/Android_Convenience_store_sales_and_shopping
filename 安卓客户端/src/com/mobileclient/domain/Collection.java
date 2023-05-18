package com.mobileclient.domain;

import java.io.Serializable;

public class Collection implements Serializable {
    /*收藏id*/
    private int collectionId;
    public int getCollectionId() {
        return collectionId;
    }
    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    /*收藏宝贝*/
    private int productObj;
    public int getProductObj() {
        return productObj;
    }
    public void setProductObj(int productObj) {
        this.productObj = productObj;
    }

    /*收藏用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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