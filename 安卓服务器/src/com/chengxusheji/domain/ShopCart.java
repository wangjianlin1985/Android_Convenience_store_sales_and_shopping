package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ShopCart {
    /*���ﳵid*/
    private int cartId;
    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    /*��Ʒ*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*�û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*����*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*��������*/
    private int buyNum;
    public int getBuyNum() {
        return buyNum;
    }
    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

}