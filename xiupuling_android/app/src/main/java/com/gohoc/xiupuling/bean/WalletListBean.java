package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/5/16.
 */

public class WalletListBean {


    /**
     * message : 查询成功
     * data : {"lastPage":false,"pageSize":10,"pageNumber":10,"list":[{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-27 16:26:12","pay_time":"2017-05-27 16:26:17","src_type":4,"trade_amount":-10,"trade_id":"45c6eb7aa3e744e08034611c73a1049b","trade_name":"截了图-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-26 18:10:21","pay_time":"2017-05-26 18:10:31","src_type":4,"trade_amount":-10,"trade_id":"f80068fe83244632a981e466ac2802ee","trade_name":"需求发布测试-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-24 10:50:35","pay_time":"2017-05-24 10:50:46","src_type":4,"trade_amount":-10,"trade_id":"a855fe8ae7674730bc62f7a197421d55","trade_name":"截了图-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-19 14:57:39","pay_time":"2017-05-19 14:58:16","src_type":4,"trade_amount":-20,"trade_id":"e3fa492d492641ba828490e7b1b8de03","trade_name":"主要是为了测试下标题的显示最多可以显示多少个汉字-视频1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 18:17:26","pay_time":"2017-05-18 18:17:32","src_type":4,"trade_amount":-10,"trade_id":"3c6dfd28144246159c4f7b17728d6d3a","trade_name":"需求发布测试-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 18:10:59","pay_time":"2017-05-18 18:11:31","src_type":4,"trade_amount":-10,"trade_id":"0ab06662adc747e6a91a360844611334","trade_name":"截了图-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 15:28:00","pay_time":"2017-05-18 15:28:50","src_type":4,"trade_amount":-10,"trade_id":"3597696ee45145fe8efb0aa9fe19065f","trade_name":"截了图-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 15:02:17","pay_time":"2017-05-18 15:02:17","src_type":7,"trade_amount":-2,"trade_id":"16a48d582eea46abba5f8bb947dd6f21","trade_name":"提现","trade_status":"银行操作中，请等待\u2026","trade_type":"体现","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 14:54:06","pay_time":"2017-05-18 14:54:06","src_type":7,"trade_amount":-10,"trade_id":"f28615148c544fde90d1139527c9a402","trade_name":"提现","trade_status":"银行操作中，请等待\u2026","trade_type":"体现","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 14:50:11","pay_time":"2017-05-18 14:50:11","src_type":7,"trade_amount":-10,"trade_id":"8fa981a2f698493d8dec418cb089cd10","trade_name":"提现","trade_status":"银行操作中，请等待\u2026","trade_type":"体现","yearmonth":"201705"}],"firstPage":false,"totalRow":221,"totalPage":23}
     * code : 1
     * yearmonths : [{"trade_amount":-533.5,"yearmonth":"201705"}]
     */

    private String message;
    private DataBean data;
    private int code;
    private List<YearmonthsBean> yearmonths;

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

    public List<YearmonthsBean> getYearmonths() {
        return yearmonths;
    }

    public void setYearmonths(List<YearmonthsBean> yearmonths) {
        this.yearmonths = yearmonths;
    }

    public static class DataBean {
        /**
         * lastPage : false
         * pageSize : 10
         * pageNumber : 10
         * list : [{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-27 16:26:12","pay_time":"2017-05-27 16:26:17","src_type":4,"trade_amount":-10,"trade_id":"45c6eb7aa3e744e08034611c73a1049b","trade_name":"截了图-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-26 18:10:21","pay_time":"2017-05-26 18:10:31","src_type":4,"trade_amount":-10,"trade_id":"f80068fe83244632a981e466ac2802ee","trade_name":"需求发布测试-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-24 10:50:35","pay_time":"2017-05-24 10:50:46","src_type":4,"trade_amount":-10,"trade_id":"a855fe8ae7674730bc62f7a197421d55","trade_name":"截了图-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-19 14:57:39","pay_time":"2017-05-19 14:58:16","src_type":4,"trade_amount":-20,"trade_id":"e3fa492d492641ba828490e7b1b8de03","trade_name":"主要是为了测试下标题的显示最多可以显示多少个汉字-视频1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 18:17:26","pay_time":"2017-05-18 18:17:32","src_type":4,"trade_amount":-10,"trade_id":"3c6dfd28144246159c4f7b17728d6d3a","trade_name":"需求发布测试-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 18:10:59","pay_time":"2017-05-18 18:11:31","src_type":4,"trade_amount":-10,"trade_id":"0ab06662adc747e6a91a360844611334","trade_name":"截了图-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 15:28:00","pay_time":"2017-05-18 15:28:50","src_type":4,"trade_amount":-10,"trade_id":"3597696ee45145fe8efb0aa9fe19065f","trade_name":"截了图-海报1(投放)","trade_status":"支付成功","trade_type":"投放展示","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 15:02:17","pay_time":"2017-05-18 15:02:17","src_type":7,"trade_amount":-2,"trade_id":"16a48d582eea46abba5f8bb947dd6f21","trade_name":"提现","trade_status":"银行操作中，请等待\u2026","trade_type":"体现","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 14:54:06","pay_time":"2017-05-18 14:54:06","src_type":7,"trade_amount":-10,"trade_id":"f28615148c544fde90d1139527c9a402","trade_name":"提现","trade_status":"银行操作中，请等待\u2026","trade_type":"体现","yearmonth":"201705"},{"account_id":"732685303ace4bdb98d85678007d6b75","create_time":"2017-05-18 14:50:11","pay_time":"2017-05-18 14:50:11","src_type":7,"trade_amount":-10,"trade_id":"8fa981a2f698493d8dec418cb089cd10","trade_name":"提现","trade_status":"银行操作中，请等待\u2026","trade_type":"体现","yearmonth":"201705"}]
         * firstPage : false
         * totalRow : 221
         * totalPage : 23
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
             * account_id : 732685303ace4bdb98d85678007d6b75
             * create_time : 2017-05-27 16:26:12
             * pay_time : 2017-05-27 16:26:17
             * src_type : 4
             * trade_amount : -10.0
             * trade_id : 45c6eb7aa3e744e08034611c73a1049b
             * trade_name : 截了图-海报1(投放)
             * trade_status : 支付成功
             * trade_type : 投放展示
             * yearmonth : 201705
             */

            private String account_id;
            private String create_time;
            private String pay_time;
            private int src_type;
            private double trade_amount;
            private String trade_id;
            private String trade_name;
            private String trade_status;
            private String trade_type;
            private String yearmonth;

            public String getAccount_id() {
                return account_id;
            }

            public void setAccount_id(String account_id) {
                this.account_id = account_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public int getSrc_type() {
                return src_type;
            }

            public void setSrc_type(int src_type) {
                this.src_type = src_type;
            }

            public double getTrade_amount() {
                return trade_amount;
            }

            public void setTrade_amount(double trade_amount) {
                this.trade_amount = trade_amount;
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

            public String getTrade_status() {
                return trade_status;
            }

            public void setTrade_status(String trade_status) {
                this.trade_status = trade_status;
            }

            public String getTrade_type() {
                return trade_type;
            }

            public void setTrade_type(String trade_type) {
                this.trade_type = trade_type;
            }

            public String getYearmonth() {
                return yearmonth;
            }

            public void setYearmonth(String yearmonth) {
                this.yearmonth = yearmonth;
            }
        }
    }

    public static class YearmonthsBean {
        /**
         * trade_amount : -533.5
         * yearmonth : 201705
         */

        private double trade_amount;
        private String yearmonth;

        public double getTrade_amount() {
            return trade_amount;
        }

        public void setTrade_amount(double trade_amount) {
            this.trade_amount = trade_amount;
        }

        public String getYearmonth() {
            return yearmonth;
        }

        public void setYearmonth(String yearmonth) {
            this.yearmonth = yearmonth;
        }
    }
}
