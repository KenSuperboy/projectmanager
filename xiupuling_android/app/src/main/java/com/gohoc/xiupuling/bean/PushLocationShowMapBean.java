package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/6/24.
 */

public class PushLocationShowMapBean {

    /**
     * message : 查询成功
     * data : {"lastPage":true,"pageSize":10,"pageNumber":1,"list":[{"range_id":"241aa1dfdc114e679cbc9cb157a64afb","shop_addr_latitude":113.951926,"shop_addr_longitude":22.550834,"shop_id":"shop009","shop_name":"许留山（深圳欢乐颂店）","shop_star_level":5,"terminal_cnt":1}],"firstPage":true,"totalRow":1,"totalPage":1}
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

    public static class DataBean {
        /**
         * lastPage : true
         * pageSize : 10
         * pageNumber : 1
         * list : [{"range_id":"241aa1dfdc114e679cbc9cb157a64afb","shop_addr_latitude":113.951926,"shop_addr_longitude":22.550834,"shop_id":"shop009","shop_name":"许留山（深圳欢乐颂店）","shop_star_level":5,"terminal_cnt":1}]
         * firstPage : true
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
             * range_id : 241aa1dfdc114e679cbc9cb157a64afb
             * shop_addr_latitude : 113.951926
             * shop_addr_longitude : 22.550834
             * shop_id : shop009
             * shop_name : 许留山（深圳欢乐颂店）
             * shop_star_level : 5
             * terminal_cnt : 1
             */

            private String range_id;
            private double shop_addr_latitude;
            private double shop_addr_longitude;
            private String shop_id;
            private String shop_name;
            private int shop_star_level;
            private int terminal_cnt;

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

            public int getTerminal_cnt() {
                return terminal_cnt;
            }

            public void setTerminal_cnt(int terminal_cnt) {
                this.terminal_cnt = terminal_cnt;
            }
        }
    }
}
