package com.example.shang.changeskin;

/**
 * Created by Shang on 2018/1/17.
 */

public class SkinItem {

    private String attrName;
    private int refId;
    private String attrValue;
    private String attrType;

    public SkinItem(String attrName, int refId, String attrValue, String attrType) {
        this.attrName = attrName;
        this.refId = refId;
        this.attrValue = attrValue;
        this.attrType = attrType;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }
}
