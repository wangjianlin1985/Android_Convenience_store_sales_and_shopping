package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ProductClass {
    /*���id*/
    private int classId;
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /*�������*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*�������*/
    private String classDesc;
    public String getClassDesc() {
        return classDesc;
    }
    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

}