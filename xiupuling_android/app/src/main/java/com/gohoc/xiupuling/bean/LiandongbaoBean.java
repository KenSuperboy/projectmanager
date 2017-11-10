package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wjj on 2017/9/7.
 * 联动包
 */
public class LiandongbaoBean implements Serializable{

    /**
     * message : 查询成功
     * data : [{"cover_url":null,"create_time":"2017-07-06 20:53:32","link_id":"1f1bd0095a7943acb08ac5191db7ec41","link_name":"恩爱夫妻","shop_id":null,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":1,"work_seat":null,"work_size":0},{"cover_url":"liandongbao/b7637edb66004ca4bdacc2159b6fd6c5/liandongbaologo1498716977.jpg","create_time":"2017-06-26 15:05:04","link_id":"b7637edb66004ca4bdacc2159b6fd6c5","link_name":"活得很好","shop_id":null,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":4,"work_seat":null,"work_size":0},{"cover_url":null,"create_time":"2017-06-26 15:01:35","link_id":"6a3c3bda54a444dd85e1460c054a945e","link_name":"很多的家","shop_id":null,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":2,"work_seat":null,"work_size":0},{"cover_url":null,"create_time":"2017-06-22 18:58:09","link_id":"e1d4f198f96e4767b8d8ff1e1308192d","link_name":"测试哈哈","shop_id":null,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466","work_cnt":4,"work_seat":null,"work_size":null}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * cover_url : null
     * create_time : 2017-07-06 20:53:32
     * link_id : 1f1bd0095a7943acb08ac5191db7ec41
     * link_name : 恩爱夫妻
     * shop_id : null
     * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
     * work_cnt : 1
     * work_seat : null
     * work_size : 0
     */

    public List<DataBean> data;

    public static class DataBean {
        public Object cover_url;
        public String create_time;
        public String link_id;
        public String link_name;
        public Object shop_id;
        public String user_id;
        public int work_cnt;
        public Object work_seat;
        public int work_size;
    }
}
