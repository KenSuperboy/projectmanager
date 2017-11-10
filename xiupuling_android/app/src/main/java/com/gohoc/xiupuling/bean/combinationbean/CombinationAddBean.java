package com.gohoc.xiupuling.bean.combinationbean;

/**
 * Created by wjj on 2017/9/19.
 * 新建作品组合包
 */
public class CombinationAddBean {

    /**
     * message : 添加成功
     * data : {"create_time":"2017-09-19","is_ignore_other_work":0,"is_time_limit":0,"orientation":0,"package_id":"908be79c836a4e71ada3decc0bc915e5","package_name":"安卓新建作品组合包","user_id":"7c7f36aed0ff4b8ca2ad736258a3c466"}
     * code : 1
     */

    public String message;
    /**
     * create_time : 2017-09-19
     * is_ignore_other_work : 0
     * is_time_limit : 0
     * orientation : 0
     * package_id : 908be79c836a4e71ada3decc0bc915e5
     * package_name : 安卓新建作品组合包
     * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
     */

    public DataBean data;
    public int code;

    public static class DataBean {
        public String create_time;
        public int is_ignore_other_work;
        public int is_time_limit;
        public int orientation;
        public String package_id;
        public String package_name;
        public String user_id;
    }
}
