package com.gohoc.xiupuling.bean.audit;

import java.util.List;

/**
 * Created by wjj on 2017/11/9.
 */
public class AuditWorkListBean {

    /**
     * message : 查询成功
     * list : [{"copyright_price":0,"material_store_url":"offset-requirement15089889736108495076018b845289ee48438d528f575.mp4.jpg","orientation":1,"playtime":29,"work_id":"f55fc7d89d2342658e2237cea439365f","work_name":"测试切割-海报1","work_type":3,"audit_terminal_cnt":3}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * copyright_price : 0
     * material_store_url : offset-requirement15089889736108495076018b845289ee48438d528f575.mp4.jpg
     * orientation : 1
     * playtime : 29
     * work_id : f55fc7d89d2342658e2237cea439365f
     * work_name : 测试切割-海报1
     * work_type : 3
     * audit_terminal_cnt : 3
     */

    public List<ListBean> list;

    public static class ListBean {
        public int copyright_price;
        public String material_store_url;
        public int orientation;
        public int playtime;
        public String work_id;
        public String work_name;
        public int work_type;
        public int audit_terminal_cnt;
    }
}
