package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/2/13.
 */

public class MusicListBean implements Serializable{


    /**
     * message : 查询成功
     * data : [{"bgaudio_type":"1000","name":"安静"},{"bgaudio_type":"1001","name":"怀旧"},{"bgaudio_type":"1002","name":"浪漫"},{"bgaudio_type":"1003","name":"清新"},{"bgaudio_type":"1004","name":"伤感"},{"bgaudio_type":"1005","name":"兴奋"},{"bgaudio_type":"1006","name":"性感"},{"bgaudio_type":"1007","name":"治愈"}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * bgaudio_type : 1000
     * name : 安静
     */

    public List<DataBean> data;

    public static class DataBean {
        public String bgaudio_type;
        public String name;
        public int flag;//0 :没有选中   1：选中了
    }
}
