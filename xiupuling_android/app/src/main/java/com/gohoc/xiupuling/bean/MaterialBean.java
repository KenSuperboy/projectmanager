package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/6/1.
 */

public class MaterialBean {


    /**
     * message : 查询成功
     * data : {"material_list":[{"name":"超高 3761080.mp4","playtime":30},{"name":"超长 1920470.mp4","playtime":35},{"name":"竖屏 394720.mp4","playtime":30}],"module_name":"新增私人频道"}
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
         * material_list : [{"name":"超高 3761080.mp4","playtime":30},{"name":"超长 1920470.mp4","playtime":35},{"name":"竖屏 394720.mp4","playtime":30}]
         * module_name : 新增私人频道
         */

        private String module_name;
        private List<MaterialListBean> material_list;

        public String getModule_name() {
            return module_name;
        }

        public void setModule_name(String module_name) {
            this.module_name = module_name;
        }

        public List<MaterialListBean> getMaterial_list() {
            return material_list;
        }

        public void setMaterial_list(List<MaterialListBean> material_list) {
            this.material_list = material_list;
        }

        public static class MaterialListBean {
            /**
             * name : 超高 3761080.mp4
             * playtime : 30
             */

            private String name;
            private int playtime;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPlaytime() {
                return playtime;
            }

            public void setPlaytime(int playtime) {
                this.playtime = playtime;
            }
        }
    }
}
