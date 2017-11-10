package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/2/22.
 */

public class SecurityQsBean {


    /**
     * message : 查询成功
     * data : [{"dictcaption":"我爷爷的姓名是？","dicttype":201,"dicttypename":"密保问题1","dictval":"2011","ids":10001},{"dictcaption":"我外公的姓名是？","dicttype":201,"dicttypename":"密保问题1","dictval":"2012","ids":10002},{"dictcaption":"我奶奶的姓名是？","dicttype":201,"dicttypename":"密保问题1","dictval":"2013","ids":10003},{"dictcaption":"我外婆的姓名是？","dicttype":201,"dicttypename":"密保问题1","dictval":"2014","ids":10004},{"dictcaption":"我母亲的生日是？","dicttype":201,"dicttypename":"密保问题1","dictval":"2015","ids":10005},{"dictcaption":"我爱人的生日是？","dicttype":201,"dicttypename":"密保问题1","dictval":"2016","ids":10006},{"dictcaption":"我用一个人的姓名来提醒我，他/她是？","dicttype":201,"dicttypename":"密保问题1","dictval":"2017","ids":10007},{"dictcaption":"我比较喜欢的球类运动时？","dicttype":202,"dicttypename":"密保问题2","dictval":"2021","ids":10008},{"dictcaption":"我最新换的一首歌是？","dicttype":202,"dicttypename":"密保问题2","dictval":"2022","ids":10009},{"dictcaption":"我最喜欢车的汽车品牌是？","dicttype":202,"dicttypename":"密保问题2","dictval":"2023","ids":10010},{"dictcaption":"我最喜欢的一部电影名称是？","dicttype":202,"dicttypename":"密保问题2","dictval":"2024","ids":10011},{"dictcaption":"我最喜欢的一部电视剧名称是？","dicttype":202,"dicttypename":"密保问题2","dictval":"2025","ids":10012},{"dictcaption":"我向往的旅游地是？","dicttype":202,"dicttypename":"密保问题2","dictval":"2026","ids":10013},{"dictcaption":"我第一次购房的日期？","dicttype":203,"dicttypename":"密保问题3","dictval":"2031","ids":10014},{"dictcaption":"我第一次开的店铺名称是？","dicttype":203,"dicttypename":"密保问题3","dictval":"2032","ids":10015},{"dictcaption":"我的宠物名字是？","dicttype":203,"dicttypename":"密保问题3","dictval":"2033","ids":10016},{"dictcaption":"我第一次坐飞机去哪里？","dicttype":203,"dicttypename":"密保问题3","dictval":"2034","ids":10017},{"dictcaption":"对我影响最大的人叫什么名字？","dicttype":203,"dicttypename":"密保问题3","dictval":"2035","ids":10018},{"dictcaption":"我的结婚纪念日是？","dicttype":203,"dicttypename":"密保问题3","dictval":"2036","ids":10019},{"dictcaption":"我第一辆车的车牌是？","dicttype":203,"dicttypename":"密保问题3","dictval":"2037","ids":10020}]
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

    public static class DataBean {
        /**
         * dictcaption : 我爷爷的姓名是？
         * dicttype : 201
         * dicttypename : 密保问题1
         * dictval : 2011
         * ids : 10001
         */

        private String dictcaption;
        private int dicttype;
        private String dicttypename;
        private String dictval;
        private int ids;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getDictcaption() {
            return dictcaption;
        }

        public void setDictcaption(String dictcaption) {
            this.dictcaption = dictcaption;
        }

        public int getDicttype() {
            return dicttype;
        }

        public void setDicttype(int dicttype) {
            this.dicttype = dicttype;
        }

        public String getDicttypename() {
            return dicttypename;
        }

        public void setDicttypename(String dicttypename) {
            this.dicttypename = dicttypename;
        }

        public String getDictval() {
            return dictval;
        }

        public void setDictval(String dictval) {
            this.dictval = dictval;
        }

        public int getIds() {
            return ids;
        }

        public void setIds(int ids) {
            this.ids = ids;
        }
    }
}
