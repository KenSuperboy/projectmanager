package com.gohoc.xiupuling.bean.combinationbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wjj on 2017/9/18.
 * 作品组合包列表
 */
public class CombinationListBean implements Serializable{

    /**
     * message : 查询成功
     * data : [{"cover_url":null,"create_time":"2017-09-05 23:04:09","end_time":null,"is_ignore_other_work":false,"is_time_limit":false,"orientation":false,"package_id":"5fefa1702707452e881064e89e2695c7","package_name":"你愿意自己","repeat_weekday":null,"start_time":null,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":null,"work_seat":null,"work_size":null},{"cover_url":null,"create_time":"2017-09-05 23:03:33","end_time":null,"is_ignore_other_work":false,"is_time_limit":false,"orientation":false,"package_id":"e5d2011a0da24994a061d63687e1a66f","package_name":"名模","repeat_weekday":null,"start_time":null,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":null,"work_seat":null,"work_size":null},{"cover_url":null,"create_time":"2017-07-06 21:04:22","end_time":"","is_ignore_other_work":true,"is_time_limit":false,"orientation":false,"package_id":"97b1d54a43844b87bfafb01b05febc48","package_name":"早餐时段作品组合","repeat_weekday":"1111111","start_time":"","user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":5,"work_seat":null,"work_size":null}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * cover_url : null
     * create_time : 2017-09-05 23:04:09
     * end_time : null
     * is_ignore_other_work : false
     * is_time_limit : false
     * orientation : false
     * package_id : 5fefa1702707452e881064e89e2695c7
     * package_name : 你愿意自己
     * repeat_weekday : null
     * start_time : null
     * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
     * work_cnt : null
     * work_seat : null
     * work_size : null
     */

    public List<DataBean> data;

    public static class DataBean implements Serializable{
        public Object cover_url;
        public String create_time;
        public Object end_time;
        public boolean is_ignore_other_work;
        public boolean is_time_limit;
        public boolean orientation;
        public String package_id;
        public String package_name;
        public Object repeat_weekday;
        public Object start_time;
        public String user_id;
        public Object work_cnt;
        public Object work_seat;
        public Object work_size;
    }
}
