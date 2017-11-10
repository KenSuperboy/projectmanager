package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by 28713 on 2017/3/3.
 */

public class PlayerList {


    /**
     * message : 查询成功
     * data : [{"module_name":"努力","playlist":[{"name":"26.mp4","playtime":25},{"name":"ROBERTO.CAVALLI.罗伯特·卡沃利.香水广告.1080p.mp4","playtime":45}]}]
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
         * module_name : 努力
         * playlist : [{"name":"26.mp4","playtime":25},{"name":"ROBERTO.CAVALLI.罗伯特·卡沃利.香水广告.1080p.mp4","playtime":45}]
         */

        private String module_name;
        private List<PlaylistBean> playlist;

        public String getModule_name() {
            return module_name;
        }

        public void setModule_name(String module_name) {
            this.module_name = module_name;
        }

        public List<PlaylistBean> getPlaylist() {
            return playlist;
        }

        public void setPlaylist(List<PlaylistBean> playlist) {
            this.playlist = playlist;
        }

        public static class PlaylistBean {
            /**
             * name : 26.mp4
             * playtime : 25
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
