package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SendWay {
    /*�ͻ���ʽid*/
    private int sendWayId;
    public int getSendWayId() {
        return sendWayId;
    }
    public void setSendWayId(int sendWayId) {
        this.sendWayId = sendWayId;
    }

    /*�ͻ���ʽ����*/
    private String sendWayName;
    public String getSendWayName() {
        return sendWayName;
    }
    public void setSendWayName(String sendWayName) {
        this.sendWayName = sendWayName;
    }

}