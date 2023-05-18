package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderItem {
    /*条目id*/
    private int itemId;
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /*所属订单*/
    private OrderInfo orderObj;
    public OrderInfo getOrderObj() {
        return orderObj;
    }
    public void setOrderObj(OrderInfo orderObj) {
        this.orderObj = orderObj;
    }

    /*订单商品*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
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