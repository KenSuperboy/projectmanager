package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/2/22.
 */

public class CompanyVerifyapplyInfoBean implements Serializable{


    /**
     * message : 查询成功
     * data : {"business_code":"4001234456789","code_photo_url":"深圳市大奖科技有限公司 01.jpg","create_time":null,"name":"ABC 网络英语有限公司","org_id":"12345678-0","org_id_photo_url":"深圳市大奖科技有限公司 03.jpg","status":2,"tax_no":"871919834919","tax_photo_url":"深圳市大奖科技有限公司 02.jpg","user_id":"7c7f36aed0ff4b8ca2ad736258a3c466"}
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
         * business_code : 4001234456789
         * code_photo_url : 深圳市大奖科技有限公司 01.jpg
         * create_time : null
         * name : ABC 网络英语有限公司
         * org_id : 12345678-0
         * org_id_photo_url : 深圳市大奖科技有限公司 03.jpg
         * status : 2
         * tax_no : 871919834919
         * tax_photo_url : 深圳市大奖科技有限公司 02.jpg
         * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
         */

        private String business_code;
        private String code_photo_url;
        private Object create_time;
        private String name;
        private String org_id;
        private String org_id_photo_url;
        private int status;
        private String tax_no;
        private String tax_photo_url;
        private String user_id;

        public String getBusiness_code() {
            return business_code;
        }

        public void setBusiness_code(String business_code) {
            this.business_code = business_code;
        }

        public String getCode_photo_url() {
            return code_photo_url;
        }

        public void setCode_photo_url(String code_photo_url) {
            this.code_photo_url = code_photo_url;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public String getOrg_id_photo_url() {
            return org_id_photo_url;
        }

        public void setOrg_id_photo_url(String org_id_photo_url) {
            this.org_id_photo_url = org_id_photo_url;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTax_no() {
            return tax_no;
        }

        public void setTax_no(String tax_no) {
            this.tax_no = tax_no;
        }

        public String getTax_photo_url() {
            return tax_photo_url;
        }

        public void setTax_photo_url(String tax_photo_url) {
            this.tax_photo_url = tax_photo_url;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
