package com.chengxusheji.domain;

import java.sql.Timestamp;
public class PayWay {
    /*֧����ʽid*/
    private int payWayId;
    public int getPayWayId() {
        return payWayId;
    }
    public void setPayWayId(int payWayId) {
        this.payWayId = payWayId;
    }

    /*֧����ʽ����*/
    private String payWayName;
    public String getPayWayName() {
        return payWayName;
    }
    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

}