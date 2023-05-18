package com.mobileclient.domain;

import java.io.Serializable;

public class OrderItem implements Serializable {
    /*条目id*/
    private int itemId;
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /*所属订单*/
    private String orderObj;
    public String getOrderObj() {
        return orderObj;
    }
    public void setOrderObj(String orderObj) {
        this.orderObj = orderObj;
    }

    /*订单商品*/
    private int productObj;
    public int getProductObj() {
        return productObj;
    }
    public void setProductObj(int productObj) {
        this.productObj = productObj;
    }

    /*商品单价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*购买数量*/
    private int orderNumer;
    public int getOrderNumer() {
        return orderNumer;
    }
    public void setOrderNumer(int orderNumer) {
        this.orderNumer = orderNumer;
    }

}