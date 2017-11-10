package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/2/22.
 */

public class MySecurityQsBean {


    /**
     * message : 查询成功
     * data : {"question_a":"我外公的姓名是？ ","question_b":"我最喜欢的一部电影名称是？ ","question_c":"我的宠物名字是？ "}
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
         * question_a : 我外公的姓名是？
         * question_b : 我最喜欢的一部电影名称是？
         * question_c : 我的宠物名字是？
         */

        private String question_a;
        private String question_b;
        private String question_c;

        public String getQuestion_a() {
            return question_a;
        }

        public void setQuestion_a(String question_a) {
            this.question_a = question_a;
        }

        public String getQuestion_b() {
            return question_b;
        }

        public void setQuestion_b(String question_b) {
            this.question_b = question_b;
        }

        public String getQuestion_c() {
            return question_c;
        }

        public void setQuestion_c(String question_c) {
            this.question_c = question_c;
        }
    }
}
