package com.gohoc.xiupuling.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/4/17.
 */

public class PushLocationShowBean  implements Serializable{

    /**
     * message : 查询成功
     * data : {"lastPage":true,"pageSize":10,"pageNumber":1,"firstPage":true,"list":[{"range_id":"cf2488d174b44644b5fbf69b462f9ccf","shop_addr_latitude":22.5480783779,"shop_addr_longitude":113.9387172906,"shop_id":"shop006","shop_name":"满记甜品（深国投店）","shop_star_level":4,"terminal_id":"3bbece7cb3e74cb186ac86e4d3613fbc","terminal_no":"02"}],"totalRow":1,"totalPage":1}
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

    public static class DataBean implements Serializable{
        /**
         * lastPage : true
         * pageSize : 10
         * pageNumber : 1
         * firstPage : true
         * list : [{"range_id":"cf2488d174b44644b5fbf69b462f9ccf","shop_addr_latitude":22.5480783779,"shop_addr_longitude":113.9387172906,"shop_id":"shop006","shop_name":"满记甜品（深国投店）","shop_star_level":4,"terminal_id":"3bbece7cb3e74cb186ac86e4d3613fbc","terminal_no":"02"}]
         * totalRow : 1
         * totalPage : 1
         */

        private boolean lastPage;
        private int pageSize;
        private int pageNumber;
        private boolean firstPage;
        private int totalRow;
        private int totalPage;
        private List<ListBean> list;

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public int getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * range_id : cf2488d174b44644b5fbf69b462f9ccf
             * shop_addr_latitude : 22.5480783779
             * shop_addr_longitude : 113.9387172906
             * shop_id : shop006
             * shop_name : 满记甜品（深国投店）
             * shop_star_level : 4
             * terminal_id : 3bbece7cb3e74cb186ac86e4d3613fbc
             * terminal_no : 02
             */

            private String range_id;
            private double shop_addr_latitude;
            private double shop_addr_longitude;
            private String shop_id;
            private String shop_name;
            private int shop_star_level;
            private String terminal_id;
            private String terminal_no;

            public String getRange_id() {
                return range_id;
            }

            public void setRange_id(String range_id) {
                this.range_id = range_id;
            }

            public double getShop_addr_latitude() {
                return shop_addr_latitude;
            }

            public void setShop_addr_latitude(double shop_addr_latitude) {
                this.shop_addr_latitude = shop_addr_latitude;
            }

            public double getShop_addr_longitude() {
                return shop_addr_longitude;
            }

            public void setShop_addr_longitude(double shop_addr_longitude) {
                this.shop_addr_longitude = shop_addr_longitude;
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

            public int getShop_star_level() {
                return shop_star_level;
            }

            public void setShop_star_level(int shop_star_level) {
                this.shop_star_level = shop_star_level;
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
        }
    }
}
