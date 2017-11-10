package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 28713 on 2017/5/19.
 */

public class PreviewBean  implements Serializable{
    private ArrayList<String> picList;
    private String videoUrl;
    private int type; ///// 1：海报 2：数码相册 3：宣传片 4：图片嵌入宣传片
    private int x;
    private int y;
    private int w;
    private int h;

    public ArrayList<String> getPicList() {
        return picList;
    }

    public void setPicList(ArrayList<String> picList) {
        this.picList = picList;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
