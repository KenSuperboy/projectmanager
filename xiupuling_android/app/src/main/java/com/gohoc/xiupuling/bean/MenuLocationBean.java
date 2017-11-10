package com.gohoc.xiupuling.bean;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/2/14.
 */

public class MenuLocationBean implements Serializable {

    private String province;
    private String provinceId;

    private String city;
    private String cityId;

    private String district;
    private String districtId;
    private int leve = 1;
    private RangeInfo rangeInfo;
    private  String  simpleName="";

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public RangeInfo getRangeInfo() {
        return rangeInfo;
    }

    public void setRangeInfo(RangeInfo rangeInfo) {
        this.rangeInfo = rangeInfo;
    }

    public int getLeve() {
        return leve;
    }

    public void setLeve(int leve) {
        this.leve = leve;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }


    public static class RangeInfo implements Serializable{
        private String mLatitude;
        private String mLongitude;
        private String distance;  //范围  500m  1000m   3000m
        private String adress;

        public String getmLatitude() {
            return mLatitude;
        }

        public void setmLatitude(String mLatitude) {
            this.mLatitude = mLatitude;
        }

        public String getmLongitude() {
            return mLongitude;
        }

        public void setmLongitude(String mLongitude) {
            this.mLongitude = mLongitude;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }
    }
}
