package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/2.
 */

public class GroupTermianlListBean implements Serializable {


    /**
     * message : 查询成功
     * data : [{"ids":"72b0485a301f4423939431b7eafe2403","shop_id":"shop007","shop_name":"哈根达斯(cocopark店)","status":1,"term_orientation":0,"terminal_id":"2a644feedc394fe886271b133f771352","terminal_no":"02"},{"ids":"dfc6c4bf6ef74d498a53deaae6812555","shop_id":"shop008","shop_name":"哈根达斯(南山茂业店)","status":1,"term_orientation":0,"terminal_id":"terminal021","terminal_no":"01"}]
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

    public static class DataBean implements Serializable {
        /**
         * ids : 72b0485a301f4423939431b7eafe2403
         * shop_id : shop007
         * shop_name : 哈根达斯(cocopark店)
         * status : 1
         * term_orientation : 0
         * terminal_id : 2a644feedc394fe886271b133f771352
         * terminal_no : 02
         */

        private String ids;
        private String shop_id;
        private String shop_name;
        private int status;
        private int term_orientation;
        private String terminal_id;
        private String terminal_no;
        private boolean isCheck = false;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTerm_orientation() {
            return term_orientation;
        }

        public void setTerm_orientation(int term_orientation) {
            this.term_orientation = term_orientation;
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
