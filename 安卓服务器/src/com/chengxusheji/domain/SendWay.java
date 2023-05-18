package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SendWay {
    /*送货方式id*/
    private int sendWayId;
    public int getSendWayId() {
        return sendWayId;
    }
    public void setSendWayId(int sendWayId) {
        this.sendWayId = sendWayId;
    }

    /*送货方式名称*/
    private String sendWayName;
    public String getSendWayName() {
        return sendWayName;
    }
    public void setSendWayName(String sendWayName) {
        this.sendWayName = sendWayName;
    }

}