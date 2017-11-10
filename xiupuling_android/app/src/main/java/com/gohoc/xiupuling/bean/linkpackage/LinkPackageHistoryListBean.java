package com.gohoc.xiupuling.bean.linkpackage;

import java.util.List;

/**
 * Created by wjj on 2017/9/11.
 * 投放关系历史记录
 */
public class LinkPackageHistoryListBean {

    /**
     * message : 查询成功
     * data : [{"cover_url":"personal15048662646221ade4520d77a90d5a5237951246aac4a.jpg","create_time":"2017-09-11 17:53:16","is_deleted":false,"is_distributed":false,"link_id":"a7af1c4f725444a98c6afe5553fa882f","link_snap_id":"db2f2c5babcc49089c4a3f49dbc97f9d","link_snap_name":"秘密哦","union_id":"dccc211fd6e24325b7b3737e8881057e","union_name":"联动测试群组","user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":2,"work_seat":null,"work_size":0}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * cover_url : personal15048662646221ade4520d77a90d5a5237951246aac4a.jpg
     * create_time : 2017-09-11 17:53:16
     * is_deleted : false
     * is_distributed : false
     * link_id : a7af1c4f725444a98c6afe5553fa882f
     * link_snap_id : db2f2c5babcc49089c4a3f49dbc97f9d
     * link_snap_name : 秘密哦
     * union_id : dccc211fd6e24325b7b3737e8881057e
     * union_name : 联动测试群组
     * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
     * work_cnt : 2
     * work_seat : null
     * work_size : 0
     */

    public List<DataBean> data;

    public static class DataBean {
        public String cover_url;
        public String create_time;
        public boolean is_deleted;
        public boolean is_distributed;
        public String link_id;
        public String link_snap_id;
        public String link_snap_name;
        public String union_id;
        public String union_name;
        public String user_id;
        public int work_cnt;
        public Object work_seat;
        public int work_size;
    }
}
