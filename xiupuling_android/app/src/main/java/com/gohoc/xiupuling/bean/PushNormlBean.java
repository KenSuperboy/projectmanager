package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/4/27.
 * 存储投放作品的相关信息
 * 单价：即单屏单周播放的费用；
 * 单价 = 作品所占的格子数 * 星级价格
 * 涉及终端数 = 投入金额 / 单价 / 投放时间（跨周数） * 不饱和补偿系数
 * 最少播放次数 = 投入金额 / 单价 * 每周最少播放次数
 */

public class PushNormlBean implements Serializable {
    private int playtime;  //播放时长
    private String rq_id;
    private String name;
    private String cover_url;
    private String work_id;
    private int orientation = 0;
    private String termianlNo;
    private String shopName;
    private String terminanlId;
    private int shopStar=1;//店铺星级
    private int indexFlage;//最后跳转到首页那个页面
    private boolean isGd=true;//价格是否可以修改
    private String activity_content;  //活动内容
    private int ptc;//时长系数
    private double sPrice;//单价
    private int weekCount; //跨周数
    private double bubaohe;//不饱和补偿系数
    private boolean isFree=false;//是否免费
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getActivity_content() {
        return activity_content;
    }

    public void setActivity_content(String activity_content) {
        this.activity_content = activity_content;
    }

    public boolean isGd() {
        return isGd;
    }

    public void setGd(boolean gd) {
        isGd = gd;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public double getsPrice() {
        return sPrice;
    }

    public void setsPrice(double sPrice) {
        this.sPrice = sPrice;
    }

    public int getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount = weekCount;
    }

    public double getBubaohe() {
        return bubaohe;
    }

    public void setBubaohe(double bubaohe) {
        this.bubaohe = bubaohe;
    }

    public int getPtc() {
        return ptc;
    }

    public void setPtc(int ptc) {
        this.ptc = ptc;
    }

    public int getShopStar() {
        return shopStar;
    }

    public void setShopStar(int shopStar) {
        this.shopStar = shopStar;
    }

    public String getTerminanlId() {
        return terminanlId;
    }

    public void setTerminanlId(String terminanlId) {
        this.terminanlId = terminanlId;
    }

    public String getTermianlNo() {
        return termianlNo;
    }

    public void setTermianlNo(String termianlNo) {
        this.termianlNo = termianlNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public String getRq_id() {
        return rq_id;
    }

    public void setRq_id(String rq_id) {
        this.rq_id = rq_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public int getIndexFlage() {
        return indexFlage;
    }

    public void setIndexFlage(int indexFlage) {
        this.indexFlage = indexFlage;
    }
}
