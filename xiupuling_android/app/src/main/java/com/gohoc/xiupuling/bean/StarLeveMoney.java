package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/4/26.
 */

public class StarLeveMoney {

    /**
     * message : 查询成功
     * data : [{"dictcaption":"1","dicttype":301,"dicttypename":"星级价格","dictval":"10","ids":11001},{"dictcaption":"2","dicttype":301,"dicttypename":"星级价格","dictval":"20","ids":11002},{"dictcaption":"3","dicttype":301,"dicttypename":"星级价格","dictval":"50","ids":11003},{"dictcaption":"4","dicttype":301,"dicttypename":"星级价格","dictval":"80","ids":11004},{"dictcaption":"5","dicttype":301,"dicttypename":"星级价格","dictval":"100","ids":11005},{"dictcaption":"6","dicttype":301,"dicttypename":"星级价格","dictval":"150","ids":11006},{"dictcaption":"7","dicttype":301,"dicttypename":"星级价格","dictval":"200","ids":11007},{"dictcaption":"8","dicttype":301,"dicttypename":"星级价格","dictval":"300","ids":11008},{"dictcaption":"9","dicttype":301,"dicttypename":"星级价格","dictval":"500","ids":11009}]
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
         * dictcaption : 1
         * dicttype : 301
         * dicttypename : 星级价格
         * dictval : 10
         * ids : 11001
         */

        private String dictcaption;
        private int dicttype;
        private String dicttypename;
        private String dictval;
        private int ids;

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
