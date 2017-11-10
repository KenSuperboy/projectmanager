package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/2/15.
 * 我的店铺
 */

public class MyshopListBean implements Serializable{

    /**
     * message : 查询成功
     * data : [{"shop_id":"6c4f512db96448e9a94b27d1ced271a6","shop_name":"太平洋咖啡","shop_star_level":1,"termlist":[{"furtherorder":1,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":0,"terminal_id":"dbcc922f29714697ae0fb6665dde3a2a","terminal_no":"01","total_cycle_cnt":0,"totalamt":10,"totalplaytime":0},{"furtherorder":1,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":1,"terminal_id":"062894eac9b64ff8ae94ca92e7d804ec","terminal_no":"02","total_cycle_cnt":0,"totalamt":10,"totalplaytime":0}],"totalorderamt":20},{"shop_id":"87219e3de50e4b9e94acc38e6e544299","shop_name":"绿茶（虹口龙之梦店）","shop_star_level":1,"termlist":[{"furtherorder":0,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":0,"terminal_id":"a7a8d747551f4703b64c0c7fa4881e86","terminal_no":"01","total_cycle_cnt":0,"totalamt":0,"totalplaytime":0},{"furtherorder":0,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":0,"terminal_id":"2dc17c5b0f9d4c6a82be7df4c504d75b","terminal_no":"07","total_cycle_cnt":0,"totalamt":0,"totalplaytime":0}],"totalorderamt":0},{"shop_id":"shop001","shop_name":"绿茶餐厅（海岸城店）","shop_star_level":3,"termlist":[{"furtherorder":0,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":0,"terminal_id":"22eade838c654874a1ab7fea71dacea9","terminal_no":"03","total_cycle_cnt":0,"totalamt":0,"totalplaytime":0},{"furtherorder":0,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":1,"terminal_id":"terminal003","terminal_no":"04","total_cycle_cnt":0,"totalamt":0,"totalplaytime":0},{"furtherorder":0,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":1,"terminal_id":"terminal001","terminal_no":"07","total_cycle_cnt":0,"totalamt":0,"totalplaytime":0},{"furtherorder":0,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":1,"terminal_id":"9dd2ec3a09c847d9bf970f4845601d63","terminal_no":"11","total_cycle_cnt":0,"totalamt":0,"totalplaytime":0}],"totalorderamt":0},{"shop_id":"shop002","shop_name":"绿茶（海雅缤纷城店）","shop_star_level":2,"termlist":[{"furtherorder":0,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":1,"terminal_id":"terminal005","terminal_no":"02","total_cycle_cnt":0,"totalamt":0,"totalplaytime":0}],"totalorderamt":0}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * shop_id : 6c4f512db96448e9a94b27d1ced271a6
     * shop_name : 太平洋咖啡
     * shop_star_level : 1
     * termlist : [{"furtherorder":1,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":0,"terminal_id":"dbcc922f29714697ae0fb6665dde3a2a","terminal_no":"01","total_cycle_cnt":0,"totalamt":10,"totalplaytime":0},{"furtherorder":1,"online":0,"showtime":0,"term_internet_method":null,"term_orientation":1,"terminal_id":"062894eac9b64ff8ae94ca92e7d804ec","terminal_no":"02","total_cycle_cnt":0,"totalamt":10,"totalplaytime":0}]
     * totalorderamt : 20
     */

    public List<DataBean> data;

    public static class DataBean implements Serializable{
        public String shop_id;
        public String shop_name;
        public int shop_star_level;
        public int totalorderamt;
        /**
         * furtherorder : 1
         * online : 0
         * showtime : 0
         * term_internet_method : null
         * term_orientation : 0
         * terminal_id : dbcc922f29714697ae0fb6665dde3a2a
         * terminal_no : 01
         * total_cycle_cnt : 0
         * totalamt : 10
         * totalplaytime : 0
         */

        public List<TermlistBean> termlist;

        public static class TermlistBean implements Serializable{
            public int furtherorder;
            public int online;
            public int showtime;
            public Object term_internet_method;
            public int term_orientation;
            public String terminal_id;
            public String terminal_no;
            public int total_cycle_cnt;
            public int totalamt;
            public int totalplaytime;
            public int flag;
        }
    }
}
