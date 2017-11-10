package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/3/22.
 */

public class PromotionInfoBean implements Serializable {
    private String keyWord;
    private String introduce;
    private String details;
    private Boolean isOpen = false;
    private int linkType;  // 0实体店 1网络地址
    private String linkUrl;
    private String longitude;
    private String latitude;
    private String lcoationName;
    private String activityTitle;//活动标题
    private String rq_id;
    private String work_id;

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getRq_id() {
        return rq_id;
    }

    public void setRq_id(String rq_id) {
        this.rq_id = rq_id;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getLcoationName() {
        return lcoationName;
    }

    public void setLcoationName(String lcoationName) {
        this.lcoationName = lcoationName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

}
