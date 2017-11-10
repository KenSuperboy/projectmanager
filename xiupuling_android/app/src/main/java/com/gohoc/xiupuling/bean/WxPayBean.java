package com.gohoc.xiupuling.bean;

/**
 * Created by 28713 on 2017/4/8.
 */

public class WxPayBean {

    /**
     * code : 1
     * message : 统一订单成功
     * sign : D38C52C981E62F3C247B97FAA0A9AF98
     * timestamp : 1491637298
     * noncestr : 1049215584049938
     * partnerid : 1356191402
     * prepayid : wx20170408154138e4220896e90164062232
     * package : Sign=WXPay
     * appid : wx32e071c492b4ebf4
     */

    private int code;
    private String message;
    private String sign;
    private String timestamp;
    private String noncestr;
    private String partnerid;
    private String prepayid;
    private String packageX;
    private String appid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
