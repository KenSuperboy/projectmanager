package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/2/23.
 */

public class UserBaseBean implements Serializable {


    /**
     * message : 查询成功
     * data : {"age":"00","attention_count":1,"company_auth":2,"company_name":"深圳市大奖科技有限公司","email":"rhome@163.com","follow_count":1,"is_id_jz":1,"is_id_sjs":1,"is_id_zz":1,"is_promo":0,"is_security_question":1,"mail_verification":null,"mobile":"13808856815","module_own_cnt":10,"nick_name":"朝天椒","personal_auth":0,"portrait_url":"o_1b2l0t5nj1lt5ibgm8p18hj3eq9.jpg","real_name":"","sex":"2","shared_platform":1,"shop_own_cnt":30,"signature":null,"status":1,"terminal_own_cnt":100,"user_id":"15dc1ad0e136471d82057368a737d67c","username":"13808856815","work_count":116,"work_left_cnt":83,"work_own_cnt":66}
     * code : 1
     */

    private String message;
    private DataBean data;
    private int code;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
         * age : 00
         * attention_count : 1
         * company_auth : 2
         * company_name : 深圳市大奖科技有限公司
         * email : rhome@163.com
         * follow_count : 1
         * is_id_jz : 1
         * is_id_sjs : 1
         * is_id_zz : 1
         * is_promo : 0
         * is_security_question : 1
         * mail_verification : null
         * mobile : 13808856815
         * module_own_cnt : 10
         * nick_name : 朝天椒
         * personal_auth : 0
         * portrait_url : o_1b2l0t5nj1lt5ibgm8p18hj3eq9.jpg
         * real_name :
         * sex : 2
         * shared_platform : 1
         * shop_own_cnt : 30
         * signature : null
         * status : 1
         * terminal_own_cnt : 100
         * user_id : 15dc1ad0e136471d82057368a737d67c
         * username : 13808856815
         * work_count : 116
         * work_left_cnt : 83
         * work_own_cnt : 66
         */

        private String age;
        private int attention_count;
        private int company_auth;
        private String company_name;
        private String email;
        private int follow_count;
        private int is_id_jz;
        private int is_id_sjs;
        private int is_id_zz;
        private int is_promo;
        private int is_security_question;
        private Object mail_verification;
        private String mobile;
        private int module_own_cnt;
        private String nick_name;
        private int personal_auth;
        private String portrait_url;
        private String real_name;
        private String sex;
        private int shared_platform;
        private int shop_own_cnt;
        private Object signature;
        private int status;
        private int terminal_own_cnt;
        private String user_id;
        private String username;
        private int work_count;
        private int work_left_cnt;
        private int work_own_cnt;
        private int is_audit;
        private int vip_code_cnt;
        private String web_domain;
        private String logo;

        public int getVip_code_cnt() {
            return vip_code_cnt;
        }

        public void setVip_code_cnt(int vip_code_cnt) {
            this.vip_code_cnt = vip_code_cnt;
        }

        public String getWeb_domain() {
            return web_domain;
        }

        public void setWeb_domain(String web_domain) {
            this.web_domain = web_domain;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getIs_audit() {
            return is_audit;
        }

        public void setIs_audit(int is_audit) {
            this.is_audit = is_audit;
        }

        public String getPersonal_authStr() {
            if (personal_auth == 0)
                return "申请个人认证";
            else if (personal_auth == 1)
                return "审核中";
            else
                return "已通过";
        }

        public String getCompany_authStr() {
            if (company_auth == 0)
                return "申请企业认证";
            else if (company_auth == 1)
                return "审核中";
            else
                return "已通过";
        }


        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public int getAttention_count() {
            return attention_count;
        }

        public void setAttention_count(int attention_count) {
            this.attention_count = attention_count;
        }

        public int getCompany_auth() {
            return company_auth;
        }

        public void setCompany_auth(int company_auth) {
            this.company_auth = company_auth;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(int follow_count) {
            this.follow_count = follow_count;
        }

        public int getIs_id_jz() {
            return is_id_jz;
        }

        public void setIs_id_jz(int is_id_jz) {
            this.is_id_jz = is_id_jz;
        }

        public int getIs_id_sjs() {
            return is_id_sjs;
        }

        public void setIs_id_sjs(int is_id_sjs) {
            this.is_id_sjs = is_id_sjs;
        }

        public int getIs_id_zz() {
            return is_id_zz;
        }

        public void setIs_id_zz(int is_id_zz) {
            this.is_id_zz = is_id_zz;
        }

        public int getIs_promo() {
            return is_promo;
        }

        public void setIs_promo(int is_promo) {
            this.is_promo = is_promo;
        }

        public int getIs_security_question() {
            return is_security_question;
        }

        public void setIs_security_question(int is_security_question) {
            this.is_security_question = is_security_question;
        }

        public Object getMail_verification() {
            return mail_verification;
        }

        public void setMail_verification(Object mail_verification) {
            this.mail_verification = mail_verification;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getModule_own_cnt() {
            return module_own_cnt;
        }

        public void setModule_own_cnt(int module_own_cnt) {
            this.module_own_cnt = module_own_cnt;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getPersonal_auth() {
            return personal_auth;
        }

        public void setPersonal_auth(int personal_auth) {
            this.personal_auth = personal_auth;
        }

        public String getPortrait_url() {
            return portrait_url;
        }

        public void setPortrait_url(String portrait_url) {
            this.portrait_url = portrait_url;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getShared_platform() {
            return shared_platform;
        }

        public void setShared_platform(int shared_platform) {
            this.shared_platform = shared_platform;
        }

        public int getShop_own_cnt() {
            return shop_own_cnt;
        }

        public void setShop_own_cnt(int shop_own_cnt) {
            this.shop_own_cnt = shop_own_cnt;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTerminal_own_cnt() {
            return terminal_own_cnt;
        }

        public void setTerminal_own_cnt(int terminal_own_cnt) {
            this.terminal_own_cnt = terminal_own_cnt;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getWork_count() {
            return work_count;
        }

        public void setWork_count(int work_count) {
            this.work_count = work_count;
        }

        public int getWork_left_cnt() {
            return work_left_cnt;
        }

        public void setWork_left_cnt(int work_left_cnt) {
            this.work_left_cnt = work_left_cnt;
        }

        public int getWork_own_cnt() {
            return work_own_cnt;
        }

        public void setWork_own_cnt(int work_own_cnt) {
            this.work_own_cnt = work_own_cnt;
        }
    }
}
