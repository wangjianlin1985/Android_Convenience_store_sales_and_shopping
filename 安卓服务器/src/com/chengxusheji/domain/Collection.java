package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Collection {
    /*�ղ�id*/
    private int collectionId;
    public int getCollectionId() {
        return collectionId;
    }
    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    /*�ղر���*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*�ղ��û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*�ղ�ʱ��*/
    private String collectionTime;
    public String getCollectionTime() {
        return collectionTime;
    }
    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

}