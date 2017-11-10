package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/4/25.
 */

public class PushResultBean implements Serializable {

    /**
     * message : 发布完成
     * data : {"create_time":null,"delete_time":null,"info_id":"825c9dee411c46928ae55e27ad7b87d6","inforange":{"addr_type":4,"bussiness_id_a":"10034","bussiness_id_b":"10035","bussiness_id_c":"10032","create_time":"2017-04-25","enddate":"20170603","fund_pool_amount":0,"group_id_a":"10586","group_id_b":"10588","group_id_c":"10589","group_type":1,"info_id":"825c9dee411c46928ae55e27ad7b87d6","orientation":1,"range_id":"e22237dc6d924e7ca3aff823ef91efad","range_type":0,"region_id":440305,"star_level":1,"startdate":"20170625","status":0,"total_amount":0,"unit_price":10,"user_id":"15dc1ad0e136471d82057368a737d67c","work_seats":1},"rq_id":"b18d8c14b45c4a68a5d982a569df597c","status":1,"user_id":"15dc1ad0e136471d82057368a737d67c","work_id":"81dbd7dbd71d4f6286ad0722431ea6bc"}
     * code : 1
     */

    private String message;
    private DataBean data;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean implements Serializable {
        /**
         * create_time : null
         * delete_time : null
         * info_id : 825c9dee411c46928ae55e27ad7b87d6
         * inforange : {"addr_type":4,"bussiness_id_a":"10034","bussiness_id_b":"10035","bussiness_id_c":"10032","create_time":"2017-04-25","enddate":"20170603","fund_pool_amount":0,"group_id_a":"10586","group_id_b":"10588","group_id_c":"10589","group_type":1,"info_id":"825c9dee411c46928ae55e27ad7b87d6","orientation":1,"range_id":"e22237dc6d924e7ca3aff823ef91efad","range_type":0,"region_id":440305,"star_level":1,"startdate":"20170625","status":0,"total_amount":0,"unit_price":10,"user_id":"15dc1ad0e136471d82057368a737d67c","work_seats":1}
         * rq_id : b18d8c14b45c4a68a5d982a569df597c
         * status : 1
         * user_id : 15dc1ad0e136471d82057368a737d67c
         * work_id : 81dbd7dbd71d4f6286ad0722431ea6bc
         */

        private Object create_time;
        private Object delete_time;
        private String info_id;
        private InforangeBean inforange;
        private String rq_id;
        private int status;
        private String user_id;
        private String work_id;

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }

        public InforangeBean getInforange() {
            return inforange;
        }

        public void setInforange(InforangeBean inforange) {
            this.inforange = inforange;
        }

        public String getRq_id() {
            return rq_id;
        }

        public void setRq_id(String rq_id) {
            this.rq_id = rq_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public static class InforangeBean implements Serializable {
            /**
             * addr_type : 4
             * bussiness_id_a : 10034
             * bussiness_id_b : 10035
             * bussiness_id_c : 10032
             * create_time : 2017-04-25
             * enddate : 20170603
             * fund_pool_amount : 0
             * group_id_a : 10586
             * group_id_b : 10588
             * group_id_c : 10589
             * group_type : 1
             * info_id : 825c9dee411c46928ae55e27ad7b87d6
             * orientation : 1
             * range_id : e22237dc6d924e7ca3aff823ef91efad
             * range_type : 0
             * region_id : 440305
             * star_level : 1
             * startdate : 20170625
             * status : 0
             * total_amount : 0
             * unit_price : 10
             * user_id : 15dc1ad0e136471d82057368a737d67c
             * work_seats : 1
             */

            private int addr_type;
            private String bussiness_id_a;
            private String bussiness_id_b;
            private String bussiness_id_c;
            private String create_time;
            private String enddate;
            private int fund_pool_amount;
            private String group_id_a;
            private String group_id_b;
            private String group_id_c;
            private int group_type;
            private String info_id;
            private int orientation;
            private String range_id;
            private int range_type;
            private int region_id;
            private int star_level;
            private String startdate;
            private int status;
            private int total_amount;
            private int unit_price;
            private String user_id;
            private int work_seats;

            public int getAddr_type() {
                return addr_type;
            }

            public void setAddr_type(int addr_type) {
                this.addr_type = addr_type;
            }

            public String getBussiness_id_a() {
                return bussiness_id_a;
            }

            public void setBussiness_id_a(String bussiness_id_a) {
                this.bussiness_id_a = bussiness_id_a;
            }

            public String getBussiness_id_b() {
                return bussiness_id_b;
            }

            public void setBussiness_id_b(String bussiness_id_b) {
                this.bussiness_id_b = bussiness_id_b;
            }

            public String getBussiness_id_c() {
                return bussiness_id_c;
            }

            public void setBussiness_id_c(String bussiness_id_c) {
                this.bussiness_id_c = bussiness_id_c;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getEnddate() {
                return enddate;
            }

            public void setEnddate(String enddate) {
                this.enddate = enddate;
            }

            public int getFund_pool_amount() {
                return fund_pool_amount;
            }

            public void setFund_pool_amount(int fund_pool_amount) {
                this.fund_pool_amount = fund_pool_amount;
            }

            public String getGroup_id_a() {
                return group_id_a;
            }

            public void setGroup_id_a(String group_id_a) {
                this.group_id_a = group_id_a;
            }

            public String getGroup_id_b() {
                return group_id_b;
            }

            public void setGroup_id_b(String group_id_b) {
                this.group_id_b = group_id_b;
            }

            public String getGroup_id_c() {
                return group_id_c;
            }

            public void setGroup_id_c(String group_id_c) {
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

            public int getRegion_id() {
                return region_id;
            }

            public void setRegion_id(int region_id) {
                this.region_id = region_id;
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

            public int getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(int total_amount) {
                this.total_amount = total_amount;
            }

            public int getUnit_price() {
                return unit_price;
            }

            public void setUnit_price(int unit_price) {
                this.unit_price = unit_price;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public int getWork_seats() {
                return work_seats;
            }

            public void setWork_seats(int work_seats) {
                this.work_seats = work_seats;
            }
        }
    }
}
