package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/31.
 */

public class PushReqResultBean implements Serializable{


    /**
     * message : 查询成功
     * range : [{"addr_latitude":null,"addr_longitude":null,"addr_type":0,"address":null,"area_name":"全国","business_name":"青年旅社","bussiness_id_a":null,"bussiness_id_b":null,"bussiness_id_c":"10013","create_time":null,"cycle_cnt":0,"delete_time":null,"distance":null,"enddate":"20170528","fund_pool_amount":10,"group_id_a":null,"group_id_b":null,"group_id_c":null,"group_type":0,"info_id":"b27fd520b7674552bb082a56f21a6632","orientation":0,"playtime":30,"range_id":"395851473558426cb12dae15b47e8a06","range_type":0,"region_id":null,"rq_id":"d639302e914b425088099e686a63e88f","shop_id":null,"shop_type":null,"star_level":1,"startdate":"20170527","status":1,"terminal_cnt":0,"terminal_id":null,"total_amount":10,"union_id":null,"union_type":null,"unit_price":10,"user_id":"9c9bd30381ff4198addf8a8938996705","week_cnt":1,"week_per_term":0,"work_id":"1e86d60ee3aa4e2eb900385db8816dbd","work_seats":1,"xpl_status":null},{"addr_latitude":null,"addr_longitude":null,"addr_type":0,"address":null,"area_name":"全国","business_name":"配镜","bussiness_id_a":null,"bussiness_id_b":"10044","bussiness_id_c":null,"create_time":null,"cycle_cnt":0,"delete_time":null,"distance":null,"enddate":"20170528","fund_pool_amount":10,"group_id_a":null,"group_id_b":null,"group_id_c":null,"group_type":0,"info_id":"b27fd520b7674552bb082a56f21a6632","orientation":0,"playtime":30,"range_id":"82d308d517c04390aab2fad01086442e","range_type":0,"region_id":null,"rq_id":"d639302e914b425088099e686a63e88f","shop_id":null,"shop_type":null,"star_level":1,"startdate":"20170527","status":1,"terminal_cnt":0,"terminal_id":null,"total_amount":10,"union_id":null,"union_type":null,"unit_price":10,"user_id":"9c9bd30381ff4198addf8a8938996705","week_cnt":1,"week_per_term":0,"work_id":"1e86d60ee3aa4e2eb900385db8816dbd","work_seats":1,"xpl_status":null}]
     * union : [{"addr_latitude":null,"addr_longitude":null,"addr_type":null,"address":null,"bussiness_id_a":null,"bussiness_id_b":null,"bussiness_id_c":null,"create_time":null,"cycle_cnt":0,"delete_time":null,"distance":null,"enddate":"20170528","fund_pool_amount":10,"group_id_a":null,"group_id_b":null,"group_id_c":null,"group_type":0,"info_id":"b27fd520b7674552bb082a56f21a6632","orientation":0,"playtime":30,"range_id":"6f336d7a1985423e8bef68e3386547e5","range_type":1,"region_id":null,"rq_id":"d639302e914b425088099e686a63e88f","shop_id":null,"shop_type":null,"star_level":1,"startdate":"20170527","status":1,"terminal_cnt":0,"terminal_id":null,"total_amount":10,"union_id":"93645a09a235475f87e30be89690ecbb","union_name":"羽毛球（定向群）","union_type":2,"unit_price":10,"user_id":"9c9bd30381ff4198addf8a8938996705","week_cnt":1,"week_per_term":0,"work_id":"1e86d60ee3aa4e2eb900385db8816dbd","work_seats":1,"xpl_status":null},{"addr_latitude":null,"addr_longitude":null,"addr_type":null,"address":null,"bussiness_id_a":null,"bussiness_id_b":null,"bussiness_id_c":null,"create_time":null,"cycle_cnt":0,"delete_time":null,"distance":null,"enddate":"20170528","fund_pool_amount":10,"group_id_a":null,"group_id_b":null,"group_id_c":null,"group_type":0,"info_id":"b27fd520b7674552bb082a56f21a6632","orientation":0,"playtime":30,"range_id":"7413b5807c9c45cf86ce1c69b2c42840","range_type":1,"region_id":null,"rq_id":"d639302e914b425088099e686a63e88f","shop_id":null,"shop_type":null,"star_level":1,"startdate":"20170527","status":1,"terminal_cnt":0,"terminal_id":null,"total_amount":10,"union_id":"592c5d226ff64a06a3476712ba520188","union_name":"你好啊（定向群）","union_type":2,"unit_price":10,"user_id":"9c9bd30381ff4198addf8a8938996705","week_cnt":1,"week_per_term":0,"work_id":"1e86d60ee3aa4e2eb900385db8816dbd","work_seats":1,"xpl_status":null}]
     * code : 1
     */

    private String message;
    private int code;
    private List<RangeBean> range;
    private List<UnionBean> union;
    private  DingtouBean dingtou;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RangeBean> getRange() {
        return range;
    }

    public void setRange(List<RangeBean> range) {
        this.range = range;
    }

    public List<UnionBean> getUnion() {
        return union;
    }

    public void setUnion(List<UnionBean> union) {
        this.union = union;
    }

    public DingtouBean getDingtou() {
        return dingtou;
    }

    public void setDingtou(DingtouBean dingtou) {
        this.dingtou = dingtou;
    }

    public static class RangeBean implements Serializable{
        /**
         * addr_latitude : null
         * addr_longitude : null
         * addr_type : 0
         * address : null
         * area_name : 全国
         * business_name : 青年旅社
         * bussiness_id_a : null
         * bussiness_id_b : null
         * bussiness_id_c : 10013
         * create_time : null
         * cycle_cnt : 0
         * delete_time : null
         * distance : null
         * enddate : 20170528
         * fund_pool_amount : 10.0
         * group_id_a : null
         * group_id_b : null
         * group_id_c : null
         * group_type : 0
         * info_id : b27fd520b7674552bb082a56f21a6632
         * orientation : 0
         * playtime : 30
         * range_id : 395851473558426cb12dae15b47e8a06
         * range_type : 0
         * region_id : null
         * rq_id : d639302e914b425088099e686a63e88f
         * shop_id : null
         * shop_type : null
         * star_level : 1
         * startdate : 20170527
         * status : 1
         * terminal_cnt : 0
         * terminal_id : null
         * total_amount : 10.0
         * union_id : null
         * union_type : null
         * unit_price : 10.0
         * user_id : 9c9bd30381ff4198addf8a8938996705
         * week_cnt : 1
         * week_per_term : 0.0
         * work_id : 1e86d60ee3aa4e2eb900385db8816dbd
         * work_seats : 1
         * xpl_status : null
         */

        private Object addr_latitude;
        private Object addr_longitude;
        private int addr_type;
        private String address;
        private String area_name;
        private String business_name;
        private Object bussiness_id_a;
        private Object bussiness_id_b;
        private String bussiness_id_c;
        private Object create_time;
        private int cycle_cnt;
        private Object delete_time;
        private Object distance;
        private String enddate;
        private double fund_pool_amount;
        private Object group_id_a;
        private Object group_id_b;
        private Object group_id_c;
        private int group_type;
        private String info_id;
        private int orientation;
        private int playtime;
        private String range_id;
        private int range_type;
        private Object region_id;
        private String rq_id;
        private Object shop_id;
        private Object shop_type;
        private int star_level;
        private String startdate;
        private int status;
        private int terminal_cnt;
        private Object terminal_id;
        private double total_amount;
        private Object union_id;
        private Object union_type;
        private double unit_price;
        private String user_id;
        private int week_cnt;
        private double week_per_term;
        private String work_id;
        private int work_seats;
        private Object xpl_status;

        public Object getAddr_latitude() {
            return addr_latitude;
        }

        public void setAddr_latitude(Object addr_latitude) {
            this.addr_latitude = addr_latitude;
        }

        public Object getAddr_longitude() {
            return addr_longitude;
        }

        public void setAddr_longitude(Object addr_longitude) {
            this.addr_longitude = addr_longitude;
        }

        public int getAddr_type() {
            return addr_type;
        }

        public void setAddr_type(int addr_type) {
            this.addr_type = addr_type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public Object getBussiness_id_a() {
            return bussiness_id_a;
        }

        public void setBussiness_id_a(Object bussiness_id_a) {
            this.bussiness_id_a = bussiness_id_a;
        }

        public Object getBussiness_id_b() {
            return bussiness_id_b;
        }

        public void setBussiness_id_b(Object bussiness_id_b) {
            this.bussiness_id_b = bussiness_id_b;
        }

        public String getBussiness_id_c() {
            return bussiness_id_c;
        }

        public void setBussiness_id_c(String bussiness_id_c) {
            this.bussiness_id_c = bussiness_id_c;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public int getCycle_cnt() {
            return cycle_cnt;
        }

        public void setCycle_cnt(int cycle_cnt) {
            this.cycle_cnt = cycle_cnt;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public double getFund_pool_amount() {
            return fund_pool_amount;
        }

        public void setFund_pool_amount(double fund_pool_amount) {
            this.fund_pool_amount = fund_pool_amount;
        }

        public Object getGroup_id_a() {
            return group_id_a;
        }

        public void setGroup_id_a(Object group_id_a) {
            this.group_id_a = group_id_a;
        }

        public Object getGroup_id_b() {
            return group_id_b;
        }

        public void setGroup_id_b(Object group_id_b) {
            this.group_id_b = group_id_b;
        }

        public Object getGroup_id_c() {
            return group_id_c;
        }

        public void setGroup_id_c(Object group_id_c) {
            this.group_id_c = group_id_c;
        }

        public int getGroup_type() {
            return group_type;
        }

        public void setGroup_type(int group_type) {
            this.group_type = group_type;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public int getPlaytime() {
            return playtime;
        }

        public void setPlaytime(int playtime) {
            this.playtime = playtime;
        }

        public String getRange_id() {
            return range_id;
        }

        public void setRange_id(String range_id) {
            this.range_id = range_id;
        }

        public int getRange_type() {
            return range_type;
        }

        public void setRange_type(int range_type) {
            this.range_type = range_type;
        }

        public Object getRegion_id() {
            return region_id;
        }

        public void setRegion_id(Object region_id) {
            this.region_id = region_id;
        }

        public String getRq_id() {
            return rq_id;
        }

        public void setRq_id(String rq_id) {
            this.rq_id = rq_id;
        }

        public Object getShop_id() {
            return shop_id;
        }

        public void setShop_id(Object shop_id) {
            this.shop_id = shop_id;
        }

        public Object getShop_type() {
            return shop_type;
        }

        public void setShop_type(Object shop_type) {
            this.shop_type = shop_type;
        }

        public int getStar_level() {
            return star_level;
        }

        public void setStar_level(int star_level) {
            this.star_level = star_level;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTerminal_cnt() {
            return terminal_cnt;
        }

        public void setTerminal_cnt(int terminal_cnt) {
            this.terminal_cnt = terminal_cnt;
        }

        public Object getTerminal_id() {
            return terminal_id;
        }

        public void setTerminal_id(Object terminal_id) {
            this.terminal_id = terminal_id;
        }

        public double getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(double total_amount) {
            this.total_amount = total_amount;
        }

        public Object getUnion_id() {
            return union_id;
        }

        public void setUnion_id(Object union_id) {
            this.union_id = union_id;
        }

        public Object getUnion_type() {
            return union_type;
        }

        public void setUnion_type(Object union_type) {
            this.union_type = union_type;
        }

        public double getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(double unit_price) {
            this.unit_price = unit_price;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getWeek_cnt() {
            return week_cnt;
        }

        public void setWeek_cnt(int week_cnt) {
            this.week_cnt = week_cnt;
        }

        public double getWeek_per_term() {
            return week_per_term;
        }

        public void setWeek_per_term(double week_per_term) {
            this.week_per_term = week_per_term;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public int getWork_seats() {
            return work_seats;
        }

        public void setWork_seats(int work_seats) {
            this.work_seats = work_seats;
        }

        public Object getXpl_status() {
            return xpl_status;
        }

        public void setXpl_status(Object xpl_status) {
            this.xpl_status = xpl_status;
        }
    }

    public static class UnionBean implements Serializable{
        /**
         * addr_latitude : null
         * addr_longitude : null
         * addr_type : null
         * address : null
         * bussiness_id_a : null
         * bussiness_id_b : null
         * bussiness_id_c : null
         * create_time : null
         * cycle_cnt : 0
         * delete_time : null
         * distance : null
         * enddate : 20170528
         * fund_pool_amount : 10.0
         * group_id_a : null
         * group_id_b : null
         * group_id_c : null
         * group_type : 0
         * info_id : b27fd520b7674552bb082a56f21a6632
         * orientation : 0
         * playtime : 30
         * range_id : 6f336d7a1985423e8bef68e3386547e5
         * range_type : 1
         * region_id : null
         * rq_id : d639302e914b425088099e686a63e88f
         * shop_id : null
         * shop_type : null
         * star_level : 1
         * startdate : 20170527
         * status : 1
         * terminal_cnt : 0
         * terminal_id : null
         * total_amount : 10.0
         * union_id : 93645a09a235475f87e30be89690ecbb
         * union_name : 羽毛球（定向群）
         * union_type : 2
         * unit_price : 10.0
         * user_id : 9c9bd30381ff4198addf8a8938996705
         * week_cnt : 1
         * week_per_term : 0.0
         * work_id : 1e86d60ee3aa4e2eb900385db8816dbd
         * work_seats : 1
         * xpl_status : null
         */

        private Object addr_latitude;
        private Object addr_longitude;
        private Object addr_type;
        private Object address;
        private Object bussiness_id_a;
        private Object bussiness_id_b;
        private Object bussiness_id_c;
        private Object create_time;
        private int cycle_cnt;
        private Object delete_time;
        private Object distance;
        private String enddate;
        private double fund_pool_amount;
        private Object group_id_a;
        private Object group_id_b;
        private Object group_id_c;
        private int group_type;
        private String info_id;
        private int orientation;
        private int playtime;
        private String range_id;
        private int range_type;
        private Object region_id;
        private String rq_id;
        private Object shop_id;
        private Object shop_type;
        private int star_level;
        private String startdate;
        private int status;
        private int terminal_cnt;
        private Object terminal_id;
        private double total_amount;
        private String union_id;
        private String union_name;
        private int union_type;
        private double unit_price;
        private String user_id;
        private int week_cnt;
        private double week_per_term;
        private String work_id;
        private int work_seats;
        private Object xpl_status;
        private String union_typeStr;

        public String getUnion_typeStr() {
            switch (getUnion_type()) {
                case 0:
                    return "共享群组";
                case 1:
                    return "私有群组";
                case 2:
                    return "连锁群组";
                default:
                    return "媒体群组";
            }
        }

        public void setUnion_typeStr(String union_typeStr) {
            this.union_typeStr = union_typeStr;
        }

        public Object getAddr_latitude() {
            return addr_latitude;
        }

        public void setAddr_latitude(Object addr_latitude) {
            this.addr_latitude = addr_latitude;
        }

        public Object getAddr_longitude() {
            return addr_longitude;
        }

        public void setAddr_longitude(Object addr_longitude) {
            this.addr_longitude = addr_longitude;
        }

        public Object getAddr_type() {
            return addr_type;
        }

        public void setAddr_type(Object addr_type) {
            this.addr_type = addr_type;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getBussiness_id_a() {
            return bussiness_id_a;
        }

        public void setBussiness_id_a(Object bussiness_id_a) {
            this.bussiness_id_a = bussiness_id_a;
        }

        public Object getBussiness_id_b() {
            return bussiness_id_b;
        }

        public void setBussiness_id_b(Object bussiness_id_b) {
            this.bussiness_id_b = bussiness_id_b;
        }

        public Object getBussiness_id_c() {
            return bussiness_id_c;
        }

        public void setBussiness_id_c(Object bussiness_id_c) {
            this.bussiness_id_c = bussiness_id_c;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public int getCycle_cnt() {
            return cycle_cnt;
        }

        public void setCycle_cnt(int cycle_cnt) {
            this.cycle_cnt = cycle_cnt;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public double getFund_pool_amount() {
            return fund_pool_amount;
        }

        public void setFund_pool_amount(double fund_pool_amount) {
            this.fund_pool_amount = fund_pool_amount;
        }

        public Object getGroup_id_a() {
            return group_id_a;
        }

        public void setGroup_id_a(Object group_id_a) {
            this.group_id_a = group_id_a;
        }

        public Object getGroup_id_b() {
            return group_id_b;
        }

        public void setGroup_id_b(Object group_id_b) {
            this.group_id_b = group_id_b;
        }

        public Object getGroup_id_c() {
            return group_id_c;
        }

        public void setGroup_id_c(Object group_id_c) {
            this.group_id_c = group_id_c;
        }

        public int getGroup_type() {
            return group_type;
        }

        public void setGroup_type(int group_type) {
            this.group_type = group_type;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public int getPlaytime() {
            return playtime;
        }

        public void setPlaytime(int playtime) {
            this.playtime = playtime;
        }

        public String getRange_id() {
            return range_id;
        }

        public void setRange_id(String range_id) {
            this.range_id = range_id;
        }

        public int getRange_type() {
            return range_type;
        }

        public void setRange_type(int range_type) {
            this.range_type = range_type;
        }

        public Object getRegion_id() {
            return region_id;
        }

        public void setRegion_id(Object region_id) {
            this.region_id = region_id;
        }

        public String getRq_id() {
            return rq_id;
        }

        public void setRq_id(String rq_id) {
            this.rq_id = rq_id;
        }

        public Object getShop_id() {
            return shop_id;
        }

        public void setShop_id(Object shop_id) {
            this.shop_id = shop_id;
        }

        public Object getShop_type() {
            return shop_type;
        }

        public void setShop_type(Object shop_type) {
            this.shop_type = shop_type;
        }

        public int getStar_level() {
            return star_level;
        }

        public void setStar_level(int star_level) {
            this.star_level = star_level;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTerminal_cnt() {
            return terminal_cnt;
        }

        public void setTerminal_cnt(int terminal_cnt) {
            this.terminal_cnt = terminal_cnt;
        }

        public Object getTerminal_id() {
            return terminal_id;
        }

        public void setTerminal_id(Object terminal_id) {
            this.terminal_id = terminal_id;
        }

        public double getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(double total_amount) {
            this.total_amount = total_amount;
        }

        public String getUnion_id() {
            return union_id;
        }

        public void setUnion_id(String union_id) {
            this.union_id = union_id;
        }

        public String getUnion_name() {
            return union_name;
        }

        public void setUnion_name(String union_name) {
            this.union_name = union_name;
        }

        public int getUnion_type() {
            return union_type;
        }

        public void setUnion_type(int union_type) {
            this.union_type = union_type;
        }

        public double getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(double unit_price) {
            this.unit_price = unit_price;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getWeek_cnt() {
            return week_cnt;
        }

        public void setWeek_cnt(int week_cnt) {
            this.week_cnt = week_cnt;
        }

        public double getWeek_per_term() {
            return week_per_term;
        }

        public void setWeek_per_term(double week_per_term) {
            this.week_per_term = week_per_term;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public int getWork_seats() {
            return work_seats;
        }

        public void setWork_seats(int work_seats) {
            this.work_seats = work_seats;
        }

        public Object getXpl_status() {
            return xpl_status;
        }

        public void setXpl_status(Object xpl_status) {
            this.xpl_status = xpl_status;
        }
    }
    public static class DingtouBean implements Serializable{
        /**
         * cycle_cnt : 0
         * fund_pool_amount : 0.0
         * range_type : 2
         * rangecnt : 10
         * terminal_cnt : 1
         * trade_amount : 0.0
         * week_per_term : 2.0
         */

        private int cycle_cnt;
        private double fund_pool_amount;
        private int range_type;
        private int rangecnt;
        private int terminal_cnt;
        private double trade_amount;
        private double week_per_term;

        public int getCycle_cnt() {
            return cycle_cnt;
        }

        public void setCycle_cnt(int cycle_cnt) {
            this.cycle_cnt = cycle_cnt;
        }

        public double getFund_pool_amount() {
            return fund_pool_amount;
        }

        public void setFund_pool_amount(double fund_pool_amount) {
            this.fund_pool_amount = fund_pool_amount;
        }

        public int getRange_type() {
            return range_type;
        }

        public void setRange_type(int range_type) {
            this.range_type = range_type;
        }

        public int getRangecnt() {
            return rangecnt;
        }

        public void setRangecnt(int rangecnt) {
            this.rangecnt = rangecnt;
        }

        public int getTerminal_cnt() {
            return terminal_cnt;
        }

        public void setTerminal_cnt(int terminal_cnt) {
            this.terminal_cnt = terminal_cnt;
        }

        public double getTrade_amount() {
            return trade_amount;
        }

        public void setTrade_amount(double trade_amount) {
            this.trade_amount = trade_amount;
        }

        public double getWeek_per_term() {
            return week_per_term;
        }

        public void setWeek_per_term(double week_per_term) {
            this.week_per_term = week_per_term;
        }
    }
}
