package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/4/6.
 */

public class TokenBean {


    /**
     * message : 查询成功
     * data : {"lastPage":true,"pageSize":10,"pageNumber":1,"firstPage":true,"list":[{"create_time":"2016-10-118:11:16","remark":"用户注册成功,赠送 50 令牌","status":2,"trade_id":"8c666e1b6f4e487a95ec770c27e83f80","trade_name":"令牌+50","trade_point":50}],"totalRow":1,"totalPage":1}
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
         * firstPage : true
         * list : [{"create_time":"2016-10-118:11:16","remark":"用户注册成功,赠送 50 令牌","status":2,"trade_id":"8c666e1b6f4e487a95ec770c27e83f80","trade_name":"令牌+50","trade_point":50}]
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
             * create_time : 2016-10-118:11:16
             * remark : 用户注册成功,赠送 50 令牌
             * status : 2
             * trade_id : 8c666e1b6f4e487a95ec770c27e83f80
             * trade_name : 令牌+50
             * trade_point : 50
             */

            private String create_time;
            private String remark;
            private int status;
            private String trade_id;
            private String trade_name;
            private int trade_point;

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTrade_id() {
                return trade_id;
            }

            public void setTrade_id(String trade_id) {
                this.trade_id = trade_id;
            }

            public String getTrade_name() {
                return trade_name;
            }

            public void setTrade_name(String trade_name) {
                this.trade_name = trade_name;
            }

            public int getTrade_point() {
                return trade_point;
            }

            public void setTrade_point(int trade_point) {
                this.trade_point = trade_point;
            }
        }
    }
}
