package com.gohoc.xiupuling.bean;


import java.io.Serializable;

/**
 * Created by 28713 on 2017/2/15.
 */

public class PicBean implements Serializable {
    private String url;
    private String locUrl;
    private int type = 0;
    private String updatefileName;
    private String describe;
    private int progress;
    private int status = 0;
    private boolean isPic = false;
    private boolean isMusic = false;
    private boolean isVideo = false;
    private Long fileSize;
    private int x = 0;
    private int y = 0;
    private int w = 0;
    private int h = 0;
    private int currW = 0;
    private int currH = 0;

    public boolean isMusic() {
        return isMusic;
    }

    public void setMusic(boolean music) {
        isMusic = music;
    }

    public int getCurrW() {
        return currW;
    }

    public void setCurrW(int currW) {
        this.currW = currW;
    }

    public int getCurrH() {
        return currH;
    }

    public void setCurrH(int currH) {
        this.currH = currH;
    }

    public int getStatus() {
        return status;
    }

    public PicBean setStatus(int status) {
        this.status = status;
        return this;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setPic(boolean pic) {
        isPic = pic;
    }

    public boolean isPic() {
        return isPic;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public PicBean setFileSize(Long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public PicBean setVideo(boolean video) {
        isVideo = video;
        return this;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public PicBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getLocUrl() {
        return locUrl;
    }

    public PicBean setLocUrl(String locUrl) {
        this.locUrl = locUrl;
        return this;
    }

    public int getType() {
        return type;
    }

    public PicBean setType(int type) {
        this.type = type;
        return this;
    }

    public String getUpdatefileName() {
        return updatefileName;
    }

    public PicBean setUpdatefileName(String updatefileName) {
        this.updatefileName = updatefileName;
        return this;
    }
}
