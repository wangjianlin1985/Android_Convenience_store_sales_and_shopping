package com.mobileclient.domain;

import java.io.Serializable;

public class ShopCart implements Serializable {
    /*购物车id*/
    private int cartId;
    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    /*商品*/
    private int productObj;
    public int getProductObj() {
        return productObj;
    }
    public void setProductObj(int productObj) {
        this.productObj = productObj;
    }

    /*用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*单价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*购买数量*/
    private int buyNum;
    public int getBuyNum() {
        return buyNum;
    }
    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

}