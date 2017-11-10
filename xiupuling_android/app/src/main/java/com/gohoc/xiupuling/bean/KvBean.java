package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by sherlock-sky on 2017/2/12.
 */

public class KvBean implements Serializable{
    private String k;
    private String v;
    private boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public KvBean setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    public KvBean(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public KvBean setV(String v) {
        this.v = v;
        return this;
    }

    @Override
    public String toString() {
        return v;
    }
}
