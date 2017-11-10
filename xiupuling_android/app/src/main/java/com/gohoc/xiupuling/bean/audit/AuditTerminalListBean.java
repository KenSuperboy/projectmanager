package com.gohoc.xiupuling.bean.audit;

import java.util.List;

/**
 * Created by wjj on 2017/11/9.
 */
public class AuditTerminalListBean {

    /**
     * message : 查询成功
     * list : [{"activity_title":"测试切割","amount":0,"audit_status":1,"create_time":"2017-11-08 17:03:16","download_status":0,"end_time":null,"enddate":"20171109","is_ignore_other_work":null,"is_leader":0,"is_time_limit":null,"link_id":null,"material_store_url":"thumb-7c7f36aed0ff4b8ca2ad736258a3c466/requirement/078761508810010.jpg","order_source":null,"package_id":null,"range_id":"1f52ac66ffcc433f97891c77511522ff","repeat_weekday":null,"shop_name":"绿茶餐厅（海岸城店）","star_level":1,"start_time":null,"terminal_id":"22eade838c654874a1ab7fea71dacea9","terminal_no":"03","totalamount":0,"work_id":"f55fc7d89d2342658e2237cea439365f","work_name":"测试切割-海报1"}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * activity_title : 测试切割
     * amount : 0
     * audit_status : 1
     * create_time : 2017-11-08 17:03:16
     * download_status : 0
     * end_time : null
     * enddate : 20171109
     * is_ignore_other_work : null
     * is_leader : 0
     * is_time_limit : null
     * link_id : null
     * material_store_url : thumb-7c7f36aed0ff4b8ca2ad736258a3c466/requirement/078761508810010.jpg
     * order_source : null
     * package_id : null
     * range_id : 1f52ac66ffcc433f97891c77511522ff
     * repeat_weekday : null
     * shop_name : 绿茶餐厅（海岸城店）
     * star_level : 1
     * start_time : null
     * terminal_id : 22eade838c654874a1ab7fea71dacea9
     * terminal_no : 03
     * totalamount : 0
     * work_id : f55fc7d89d2342658e2237cea439365f
     * work_name : 测试切割-海报1
     */

    public List<ListBean> list;

    public static class ListBean {
        public String activity_title;
        public int amount;
        public int audit_status;
        public String create_time;
        public int download_status;
        public Object end_time;
        public String enddate;
        public boolean is_ignore_other_work;
        public int is_leader;
        public boolean is_time_limit;
        public Object link_id;
        public String material_store_url;
        public Object order_source;
        public Object package_id;
        public String range_id;
        public Object repeat_weekday;
        public String shop_name;
        public int star_level;
        public Object start_time;
        public String terminal_id;
        public String terminal_no;
        public int totalamount;
        public String work_id;
        public String work_name;
    }
}
