package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/7/4 0004
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @descriptio
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class UploadBean implements Serializable {

    private List<PicBean> picBeanList;
    private VideoBean videoBean;

    public List<PicBean> getPicBeanList() {
        if (picBeanList == null) {
            return new ArrayList<>();
        }
        return picBeanList;
    }

    public void setPicBeanList(List<PicBean> picBeanList) {
        this.picBeanList = picBeanList;
    }

    public VideoBean getVideoBean() {
        return videoBean;
    }

    public void setVideoBean(VideoBean videoBean) {
        this.videoBean = videoBean;
    }

    public static class VideoBean {
        private int w;
        private int h;
        private int x;
        private int y;
        private String name;
        private long size;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }


    public static class PicBean {
        private String name;
        private String url;
        private String locUrl;
        private int progress;
        private int uploadStatus = 0;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLocUrl() {
            return locUrl;
        }

        public void setLocUrl(String locUrl) {
            this.locUrl = locUrl;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public void setUploadStatus(int uploadStatus) {
            this.uploadStatus = uploadStatus;
        }

        public int getUploadStatus() {
            return uploadStatus;
        }
    }
}
