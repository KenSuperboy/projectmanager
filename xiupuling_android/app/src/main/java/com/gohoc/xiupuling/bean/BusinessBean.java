package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/2/15.
 */

public class BusinessBean implements Serializable{


    /**
     * message : 查询成功
     * data : [{"group_name":"阎村","group_type":1,"region_id":110111,"group_id":10141},{"group_name":"窦店","group_type":1,"region_id":110111,"group_id":10142},{"group_name":"房山城关","group_type":1,"region_id":110111,"group_id":10143},{"group_name":"长阳镇","group_type":1,"region_id":110111,"group_id":10144},{"group_name":"良乡","group_type":1,"region_id":110111,"group_id":10145},{"group_name":"十渡","group_type":1,"region_id":110111,"group_id":10146},{"group_name":"孤山寨","group_type":1,"region_id":110111,"group_id":10147}]
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
         * group_name : 阎村
         * group_type : 1
         * region_id : 110111
         * group_id : 10141
         */

        private String group_name;
        private int group_type;
        private int region_id;
        private int group_id;
        private boolean isSelect = false;

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public int getGroup_type() {
            return group_type;
        }

        public void setGroup_type(int group_type) {
            this.group_type = group_type;
        }

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        @Override
        public boolean equals(Object obj) {
            DataBean db= (DataBean) obj;
            if (getGroup_id()==db.getGroup_id())
              return true;
            return false;
        }
    }
}
