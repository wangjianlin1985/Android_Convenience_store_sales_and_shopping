package com.chengxusheji.domain;

import java.sql.Timestamp;
public class PayWay {
    /*支付方式id*/
    private int payWayId;
    public int getPayWayId() {
        return payWayId;
    }
    public void setPayWayId(int payWayId) {
        this.payWayId = payWayId;
    }

    /*支付方式名称*/
    private String payWayName;
    public String getPayWayName() {
        return payWayName;
    }
    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

}