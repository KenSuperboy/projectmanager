package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/4/1.
 */

public class OrderBean implements Serializable{


    /**
     * message : 查询成功
     * data : [{"activity_title":"测试画质1","info_id":"9e1884d6ca054fbeb6c553e5376e5e3f","is_cover":1,"material_store_id":"d84df36138de4e059b916f88543111c8","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/095831488105820.jpg","playtime":30,"range_id":"efb2f6f1e3b8443ea552e61e7c7d977d","rq_id":"d7f5834af4e94ee9bbd1b75e3e744a2f","shop_id":"08d4761d78a9406d81284e8dd60ab260","shop_name":"超级赛亚人","star_level":1,"terminal_id":"77c1dd532de14a6cb69f6de79053f6d5","terminal_no":"01","totalamt":10,"unit_price":10,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_type":2}]
     * code : 1
     */

    private String message;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * activity_title : 测试画质1
         * info_id : 9e1884d6ca054fbeb6c553e5376e5e3f
         * is_cover : 1
         * material_store_id : d84df36138de4e059b916f88543111c8
         * material_store_url : thumb-15dc1ad0e136471d82057368a737d67c/requirement/095831488105820.jpg
         * playtime : 30
         * range_id : efb2f6f1e3b8443ea552e61e7c7d977d
         * rq_id : d7f5834af4e94ee9bbd1b75e3e744a2f
         * shop_id : 08d4761d78a9406d81284e8dd60ab260
         * shop_name : 超级赛亚人
         * star_level : 1
         * terminal_id : 77c1dd532de14a6cb69f6de79053f6d5
         * terminal_no : 01
         * totalamt : 10.0
         * unit_price : 10.0
         * work_id : 54b405e6e8aa41f38112e109df1e702c
         * work_type : 2
         */

        private String activity_title;
        private String info_id;
        private int is_cover;
        private String material_store_id;
        private String material_store_url;
        private int playtime;
        private String range_id;
        private String rq_id;
        private String shop_id;
        private String shop_name;
        private int star_level;
        private String terminal_id;
        private String terminal_no;
        private double totalamt;
        private double unit_price;
        private String work_id;
        private int work_type;
        private String work_name;

        public String getWork_name() {
            return work_name;
        }

        public void setWork_name(String work_name) {
            this.work_name = work_name;
        }

        public String getActivity_title() {
            return activity_title;
        }

        public void setActivity_title(String activity_title) {
            this.activity_title = activity_title;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }

        public int getIs_cover() {
            return is_cover;
        }

        public void setIs_cover(int is_cover) {
            this.is_cover = is_cover;
        }

        public String getMaterial_store_id() {
            return material_store_id;
        }

        public void setMaterial_store_id(String material_store_id) {
            this.material_store_id = material_store_id;
        }

        public String getMaterial_store_url() {
            return material_store_url;
        }

        public void setMaterial_store_url(String material_store_url) {
            this.material_store_url = material_store_url;
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

        public String getRq_id() {
            return rq_id;
        }

        public void setRq_id(String rq_id) {
            this.rq_id = rq_id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getStar_level() {
            return star_level;
        }

        public void setStar_level(int star_level) {
            this.star_level = star_level;
        }

        public String getTerminal_id() {
            return terminal_id;
        }

        public void setTerminal_id(String terminal_id) {
            this.terminal_id = terminal_id;
        }

        public String getTerminal_no() {
            return terminal_no;
        }

        public void setTerminal_no(String terminal_no) {
            this.terminal_no = terminal_no;
        }

        public double getTotalamt() {
            return totalamt;
        }

        public void setTotalamt(double totalamt) {
            this.totalamt = totalamt;
        }

        public double getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(double unit_price) {
            this.unit_price = unit_price;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public int getWork_type() {
            return work_type;
        }

        public void setWork_type(int work_type) {
            this.work_type = work_type;
        }
    }
}
