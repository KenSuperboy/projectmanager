package com.gohoc.xiupuling.bean.linkpackage;

/**
 * Created by wjj on 2017/9/12.
 * 设定联动包关系
 */
public class SetLiandongbaoGuanxi {

    /**
     * message : 设置成功
     * data : {" link_snap_id":"11111","link_snap_name":"1234231"}
     * code : 1
     */

    public String message;
    /**
     *  link_snap_id : 11111
     * link_snap_name : 1234231
     */

    public DataBean data;
    public int code;

    public static class DataBean {
        public String link_snap_id;
        public String link_snap_name;
    }
}
