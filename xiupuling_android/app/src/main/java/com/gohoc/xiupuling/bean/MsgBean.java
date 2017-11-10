package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/5/12.
 */

public class MsgBean implements Serializable {

    /**
     * message : 查询成功
     * data : [{"content":"您交的企业认证资料通过审核了","icon":"test.icon","id":1,"linkurl":null,"msg_version":1476688357,"status":0,"title":"企业认证消息","msg_type":0}]
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
         * content : 您交的企业认证资料通过审核了
         * icon : test.icon
         * id : 1
         * linkurl : null
         * msg_version : 1476688357
         * status : 0
         * title : 企业认证消息
         * msg_type : 0
         */

        private String content;
        private String icon;
        private int id;
        private Object linkurl;
        private long msg_version;
        private int status;
        private String title;
        private int msg_type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(Object linkurl) {
            this.linkurl = linkurl;
        }

        public long getMsg_version() {
            return msg_version;
        }

        public void setMsg_version(long msg_version) {
            this.msg_version = msg_version;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMsg_type() {
            return msg_type;
        }

        public void setMsg_type(int msg_type) {
            this.msg_type = msg_type;
        }
    }
}
