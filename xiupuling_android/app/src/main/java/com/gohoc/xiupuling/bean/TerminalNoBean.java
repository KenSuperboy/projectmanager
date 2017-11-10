package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/2/28.
 */

public class TerminalNoBean {
    private String no;
    private int state;

    public TerminalNoBean() {

    }

    public TerminalNoBean(String no, int state) {
        this.no = no;
        this.state = state;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
