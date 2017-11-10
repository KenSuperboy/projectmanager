package com.gohoc.xiupuling.bean.audit;

/**
 * Created by wjj on 2017/11/9.
 */
public class AuditUserInfor {

    /**
     * message : 查询成功
     * data : {"mobile":"18038009771","nick_name":"张无忌","portrait_url":"personal1504859327754449da71de2803affeb1e5e02b31d0919.jpg","user_id":"7c7f36aed0ff4b8ca2ad736258a3c466"}
     * code : 1
     */

    public String message;
    /**
     * mobile : 18038009771
     * nick_name : 张无忌
     * portrait_url : personal1504859327754449da71de2803affeb1e5e02b31d0919.jpg
     * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
     */

    public DataBean data;
    public int code;

    public static class DataBean {
        public String mobile;
        public String nick_name;
        public String portrait_url;
        public String user_id;
    }
}
