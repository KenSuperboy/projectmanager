package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by wjj on 2017/9/7.
 * 多屏联动包详情
 */
public class LinkPackageDetailBean {

    /**
     * message : 查询成功
     * data : [{"detail_id":"e4939a2b81a14489bf4a561071fab8b1","is_leader":false,"link_id":"b7637edb66004ca4bdacc2159b6fd6c5","order_num":1,"orientation":0,"playtime":15,"rq_id":"xq011","terminal_id":null,"work_id":"pt010","work_name":"绿茶清新广告对应的作品-作品2","work_type":3},{"detail_id":"c5e688b658c04e8080a6c3170f35aa6f","is_leader":false,"link_id":"b7637edb66004ca4bdacc2159b6fd6c5","order_num":2,"orientation":1,"playtime":12,"rq_id":"4882673dd4ac4c3780254f2e214146f4","terminal_id":null,"work_id":"c9f8417fe72d4b01ab751f3281e68651","work_name":"测试竖版视频-图嵌视频1","work_type":4},{"detail_id":"c11159e330f94884b48252753e7d9fb6","is_leader":false,"link_id":"b7637edb66004ca4bdacc2159b6fd6c5","order_num":3,"orientation":0,"playtime":30,"rq_id":"xq011","terminal_id":null,"work_id":"pt009","work_name":"绿茶清新广告对应的作品-作品1","work_type":3},{"detail_id":"5189f462d3f14630957da8f8864177a4","is_leader":false,"link_id":"b7637edb66004ca4bdacc2159b6fd6c5","order_num":4,"orientation":0,"playtime":30,"rq_id":"xq011","terminal_id":null,"work_id":"pt012","work_name":"绿茶清新广告对应的作品-作品4","work_type":3}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * detail_id : e4939a2b81a14489bf4a561071fab8b1
     * is_leader : false
     * link_id : b7637edb66004ca4bdacc2159b6fd6c5
     * order_num : 1
     * orientation : 0
     * playtime : 15
     * rq_id : xq011
     * terminal_id : null
     * work_id : pt010
     * work_name : 绿茶清新广告对应的作品-作品2
     * work_type : 3
     */

    public List<DataBean> data;

    public static class DataBean {
        public String detail_id;
        public boolean is_leader;
        public String link_id;
        public int order_num;
        public int orientation;
        public int playtime;
        public String rq_id;
        public Object terminal_id;
        public String work_id;
        public String work_name;
        public int work_type;
        public String link_snap_id;
        public String terminal_number;
        public String terminal_no;
    }
}
