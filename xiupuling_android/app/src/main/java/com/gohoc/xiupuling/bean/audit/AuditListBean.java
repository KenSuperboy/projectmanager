package com.gohoc.xiupuling.bean.audit;

import java.util.List;

/**
 * Created by wjj on 2017/11/9.
 */
public class AuditListBean {

    /**
     * message : 查询成功
     * data : [{"audit_cnt":0,"mobile":"15817234445","nick_name":"158****4445","portrait_url":null,"user_id":"fe97438eebfe4a97bc80e16f89e78040"}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * audit_cnt : 0
     * mobile : 15817234445
     * nick_name : 158****4445
     * portrait_url : null
     * user_id : fe97438eebfe4a97bc80e16f89e78040
     */

    public List<DataBean> data;

    public static class DataBean {
        public int audit_cnt;
        public String mobile;
        public String nick_name;
        public Object portrait_url;
        public String user_id;
    }
}
