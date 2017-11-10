package com.gohoc.xiupuling.bean.combinationbean;

import java.util.List;

/**
 * Created by wjj on 2017/9/18.
 * 作品组合包里面的详情
 */
public class CombinationListDetailBean {

    /**
     * message : 查询成功
     * data : [{"detail_id":"94c583ee24c943078077d0fbf15e0270","order_num":1,"orientation":0,"package_id":"97b1d54a43844b87bfafb01b05febc48","playtime":30,"rq_id":"xq011","work_id":"pt012","work_name":"绿茶清新广告对应的作品-作品4","work_type":3},{"detail_id":"511d308ecf364d44b1e14fb031e34959","order_num":2,"orientation":0,"package_id":"97b1d54a43844b87bfafb01b05febc48","playtime":30,"rq_id":"xq005","work_id":"pt014","work_name":"绿茶特色菜-作品2","work_type":2},{"detail_id":"92f30f6ee5644333962868371882f9e2","order_num":2,"orientation":0,"package_id":"97b1d54a43844b87bfafb01b05febc48","playtime":30,"rq_id":"714477cd126d480788885615180636e0","work_id":"9ff9f112f101461581de6c536797236a","work_name":"我的测试作品二-海报1","work_type":1},{"detail_id":"3de993bc11b3485b9bcf21605a3e15f3","order_num":3,"orientation":0,"package_id":"97b1d54a43844b87bfafb01b05febc48","playtime":30,"rq_id":"1f34adf1af9143c99ec7ec341c9249e9","work_id":"edabfba34bec46c0941a2a3afa15f564","work_name":"测试-海报1","work_type":1},{"detail_id":"712ad74484e64308b05f5760149ccff6","order_num":3,"orientation":0,"package_id":"97b1d54a43844b87bfafb01b05febc48","playtime":15,"rq_id":"xq011","work_id":"pt010","work_name":"绿茶清新广告对应的作品-作品2","work_type":3}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * detail_id : 94c583ee24c943078077d0fbf15e0270
     * order_num : 1
     * orientation : 0
     * package_id : 97b1d54a43844b87bfafb01b05febc48
     * playtime : 30
     * rq_id : xq011
     * work_id : pt012
     * work_name : 绿茶清新广告对应的作品-作品4
     * work_type : 3
     */

    public List<DataBean> data;

    public static class DataBean {
        public String detail_id;
        public int order_num;
        public int orientation;
        public String package_id;
        public int playtime;
        public String rq_id;
        public String work_id;
        public String work_name;
        public int work_type;
    }
}
