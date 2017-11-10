package com.gohoc.xiupuling.bean.linkpackage;

/**
 * Created by wjj on 2017/9/7.
 * 新建联动包
 */
public class AddlinkPackageBean {

    /**
     * message : 添加成功
     * data : {"create_time":"2017-09-07","link_id":"d7ccdd9ec24241a7886a76032ac6c331","link_name":"都好都好都好好的","user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":0,"work_size":0}
     * code : 1
     */

    public String message;
    /**
     * create_time : 2017-09-07
     * link_id : d7ccdd9ec24241a7886a76032ac6c331
     * link_name : 都好都好都好好的
     * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
     * work_cnt : 0
     * work_size : 0
     */

    public DataBean data;
    public int code;

    public static class DataBean {
        public String create_time;
        public String link_id;
        public String link_name;
        public String user_id;
        public int work_cnt;
        public int work_size;
    }
}
