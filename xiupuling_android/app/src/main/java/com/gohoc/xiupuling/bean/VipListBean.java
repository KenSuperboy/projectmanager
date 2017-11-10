package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by wjj on 2017/11/10.
 */
public class VipListBean {

    /**
     * message : 查询成功
     * data : [{"is_used":true,"not_used_cnt":1,"shop_name":"太平洋咖啡","terminal_no":"02","vip_code":"074a64c"},{"is_used":false,"not_used_cnt":2,"shop_name":null,"terminal_no":null,"vip_code":"08a065e0"},{"is_used":true,"not_used_cnt":2,"shop_name":"绿茶餐厅（海岸城店）","terminal_no":"03","vip_code":"09028fa5"},{"is_used":true,"not_used_cnt":2,"shop_name":"太平洋咖啡","terminal_no":"01","vip_code":"269d670"},{"is_used":true,"not_used_cnt":2,"shop_name":"太平洋咖啡","terminal_no":"03","vip_code":"8fb3f15a1"},{"is_used":false,"not_used_cnt":3,"shop_name":null,"terminal_no":null,"vip_code":"f300a996e"},{"is_used":false,"not_used_cnt":2,"shop_name":null,"terminal_no":null,"vip_code":"f399401"},{"is_used":true,"not_used_cnt":0,"shop_name":"绿茶餐厅（海岸城店）","terminal_no":"11","vip_code":"f72f9547f"}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * is_used : true
     * not_used_cnt : 1
     * shop_name : 太平洋咖啡
     * terminal_no : 02
     * vip_code : 074a64c
     */

    public List<DataBean> data;

    public static class DataBean {
        public boolean is_used;
        public int not_used_cnt;
        public String shop_name;
        public String terminal_no;
        public String vip_code;
        public int flag;
    }
}
