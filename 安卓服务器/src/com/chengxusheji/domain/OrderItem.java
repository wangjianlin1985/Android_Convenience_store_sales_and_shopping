package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderItem {
    /*��Ŀid*/
    private int itemId;
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /*��������*/
    private OrderInfo orderObj;
    public OrderInfo getOrderObj() {
        return orderObj;
    }
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }

    /*������Ʒ*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*��Ʒ����*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*��������*/
    private int orderNumer;
    public int getOrderNumer() {
        return orderNumer;
    }
    public void setOrderNumer(int orderNumer) {
        this.orderNumer = orderNumer;
    }

}