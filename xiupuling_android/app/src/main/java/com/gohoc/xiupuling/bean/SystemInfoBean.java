package com.gohoc.xiupuling.bean;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by 28713 on 2017/4/5.
 */

public class SystemInfoBean implements Serializable {

    private String message;
    private int code;
    private Data data;


    public SystemInfoBean(String json) {
        try {

            JSONObject jsons = new JSONObject(json);
            message = jsons.getString("message");
            code = jsons.getInt("code");
            data = new Data(jsons.getString("data"));

        } catch (JSONException e) {
            Logger.e(e.toString());
            e.printStackTrace();
        }


    }

    public static class Data implements Serializable {
        private String valuationrules;
        private String bannarUrl1;
        private String powerJzapply;
        private String consumptionVideo;
        private String bannarUrl2;
        private String bannarUrl3;
        private String mp4Introduce;
        private String defaultAccount;
        private String prefixUrl;
        private String aboutTerminadocument;
        private String powerCvapply;
        private String aboutInformationwrong;
        private String powerZzapply;
        private String cycleSeats;
        private String aboutGainpoint;
        private String aboutOrderrules;
        private String iconPrivate;
        private String aboutLegaldocument;
        private String aboutWithdrawscash;
        private String serviceTelephone1;
        private String consumptionPoster;
        private String moduleMseats;
        private String powerDaylogin;
        private String aboutxpl;
        private String accountsecurity;
        private String consumptionVp;
        private String aboutCouponrules;
        private String seatsSecond;
        private String aboutFqa;
        private String launchspec;
        private String powerPvapply;
        private String cntMin;
        private String aboutUserguide;
        private String xpluseragreement;
        private String beatInterval;
        private String powerSqaset;
        private String iconShare;
        private String photoalbum;
        private String aboutPowerspec;
        private String aboutIosdownload;
        private String privacypolicy;
        private String aboutShowerpolicy;
        private String hostpolicy;
        private String systemXplSupportBank;
        private String jzapply;
        private String zzapply;
        //private String pt_dictionary;
;


        public Data(String json) {
            try {
                JSONObject jsons = new JSONObject(json);
                if (jsons.has("system.xpl.about.valuationrules"))
                    valuationrules = jsons.getString("system.xpl.about.valuationrules");
                if (jsons.has("system.xpl.about.valuationrules"))
                    powerJzapply = jsons.getString("system.xpl.about.valuationrules");
                if (jsons.has("system.xpl.about.valuationrules"))
                    consumptionVideo = jsons.getString("system.xpl.about.valuationrules");
                if (jsons.has("system.xpl.shop.bannar.url1"))
                    bannarUrl1 = jsons.getString("system.xpl.shop.bannar.url1");
                if (jsons.has("system.xpl.shop.bannar.url2"))
                    bannarUrl2 = jsons.getString("system.xpl.shop.bannar.url2");
                if (jsons.has("system.xpl.shop.bannar.url3"))
                    bannarUrl3 = jsons.getString("system.xpl.shop.bannar.url3");
                if (jsons.has("system.xpl.mp4.introduce"))
                    mp4Introduce = jsons.getString("system.xpl.mp4.introduce");
                if (jsons.has("system.xpl.default.account"))
                    defaultAccount = jsons.getString("system.xpl.default.account");
                if (jsons.has("system.qr.code.prefix.url"))
                    prefixUrl = jsons.getString("system.qr.code.prefix.url");
                if (jsons.has("system.xpl.about.terminadocument"))
                    aboutTerminadocument = jsons.getString("system.xpl.about.terminadocument");
                if (jsons.has("system.xpl.inset.power.cvapply"))
                    powerCvapply = jsons.getString("system.xpl.inset.power.cvapply");
                if (jsons.has("system.xpl.about.informationwrong"))
                    aboutInformationwrong = jsons.getString("system.xpl.about.informationwrong");
                if (jsons.has("system.xpl.inset.power.zzapply"))
                    powerZzapply = jsons.getString("system.xpl.inset.power.zzapply");
                if (jsons.has("system.cycle.seats"))
                    cycleSeats = jsons.getString("system.cycle.seats");
                if (jsons.has("system.xpl.about.gainpoint"))
                    aboutGainpoint = jsons.getString("system.xpl.about.gainpoint");
                if (jsons.has("system.xpl.about.orderrules"))
                    aboutOrderrules = jsons.getString("system.xpl.about.orderrules");
                if (jsons.has("system.xpl.module.icon.private"))
                    iconPrivate = jsons.getString("system.xpl.module.icon.private");
                if (jsons.has("system.xpl.about.legaldocument"))
                    aboutLegaldocument = jsons.getString("system.xpl.about.legaldocument");
                if (jsons.has("system.xpl.about.withdrawscash"))
                    aboutWithdrawscash = jsons.getString("system.xpl.about.withdrawscash");
                if (jsons.has("system.xpl.service.telephone.1"))
                    serviceTelephone1 = jsons.getString("system.xpl.service.telephone.1");
                if (jsons.has("system.rq.consumption.poster"))
                    consumptionPoster = jsons.getString("system.rq.consumption.poster");
                if (jsons.has("system.private.module.m.seats"))
                    moduleMseats = jsons.getString("system.private.module.m.seats");
                if (jsons.has("system.xpl.inset.power.daylogin"))
                    powerDaylogin = jsons.getString("system.xpl.inset.power.daylogin");
                if (jsons.has("system.xpl.about.aboutxpl"))
                    aboutxpl = jsons.getString("system.xpl.about.aboutxpl");
                if (jsons.has("system.xpl.about.accountsecurity"))
                    accountsecurity = jsons.getString("system.xpl.about.accountsecurity");
                if (jsons.has("system.rq.consumption.vp"))
                    consumptionVp = jsons.getString("system.rq.consumption.vp");
                if (jsons.has("system.xpl.about.couponrules"))
                    aboutCouponrules = jsons.getString("system.xpl.about.couponrules");
                if (jsons.has("system.seats.second"))
                    seatsSecond = jsons.getString("system.seats.second");
                if (jsons.has("system.xpl.about.fqa"))
                    aboutFqa = jsons.getString("system.xpl.about.fqa");
                if (jsons.has("system.xpl.about.launchspec"))
                    launchspec = jsons.getString("system.xpl.about.launchspec");
                if (jsons.has("system.xpl.support.bank"))
                    systemXplSupportBank = jsons.getString("system.xpl.support.bank");
                if (jsons.has("system.xpl.inset.power.pvapply"))
                    powerPvapply = jsons.getString("system.xpl.inset.power.pvapply");
                if (jsons.has("system.week.play.cycle.cnt.min"))
                    cntMin = jsons.getString("system.week.play.cycle.cnt.min");
                if (jsons.has("system.xpl.about.userguide"))
                    aboutUserguide = jsons.getString("system.xpl.about.userguide");
                if (jsons.has("system.xpl.about.xpluseragreement"))
                    xpluseragreement = jsons.getString("system.xpl.about.xpluseragreement");
                if (jsons.has("system.terminal.beat.interval"))
                    beatInterval = jsons.getString("system.terminal.beat.interval");
                if (jsons.has("system.xpl.inset.power.sqaset"))
                    powerSqaset = jsons.getString("system.xpl.inset.power.sqaset");
                if (jsons.has("system.xpl.module.icon.share"))
                    iconShare = jsons.getString("system.xpl.module.icon.share");
                if (jsons.has("system.rq.consumption.photoalbum"))
                    photoalbum = jsons.getString("system.rq.consumption.photoalbum");
                if (jsons.has("system.xpl.about.powerspec"))
                    aboutPowerspec = jsons.getString("system.xpl.about.powerspec");
                if (jsons.has("system.xpl.about.iosdownload"))
                    aboutIosdownload = jsons.getString("system.xpl.about.iosdownload");
                if (jsons.has("system.xpl.about.privacypolicy"))
                    privacypolicy = jsons.getString("system.xpl.about.privacypolicy");
                if (jsons.has("system.xpl.about.hostpolicy"))
                    hostpolicy = jsons.getString("system.xpl.about.hostpolicy");
                if (jsons.has("system.xpl.about.showerpolicy"))
                    aboutShowerpolicy = jsons.getString("system.xpl.about.showerpolicy");
                if(jsons.has("system.xpl.inset.power.jzapply"))
                    jzapply=jsons.getString("system.xpl.inset.power.jzapply");
                if(jsons.has("system.xpl.inset.power.jzapply"))
                    zzapply=jsons.getString("system.xpl.inset.power.zzapply");

            } catch (JSONException e) {
                Logger.e("Data::" + e.toString());
                e.printStackTrace();

            }

        }

        public String getJzapply() {
            return jzapply;
        }

        public void setJzapply(String jzapply) {
            this.jzapply = jzapply;
        }

        public String getZzapply() {
            return zzapply;
        }

        public void setZzapply(String zzapply) {
            this.zzapply = zzapply;
        }

        public String getSystemXplSupportBank() {
            return systemXplSupportBank;
        }

        public void setSystemXplSupportBank(String systemXplSupportBank) {
            this.systemXplSupportBank = systemXplSupportBank;
        }

        public String getAboutShowerpolicy() {
            return aboutShowerpolicy;
        }

        public void setAboutShowerpolicy(String aboutShowerpolicy) {
            this.aboutShowerpolicy = aboutShowerpolicy;
        }

        public String getHostpolicy() {
            return hostpolicy;
        }

        public void setHostpolicy(String hostpolicy) {
            this.hostpolicy = hostpolicy;
        }

        public String getValuationrules() {
            return valuationrules;
        }

        public void setValuationrules(String valuationrules) {
            this.valuationrules = valuationrules;
        }

        public String getBannarUrl1() {
            return bannarUrl1;
        }

        public void setBannarUrl1(String bannarUrl1) {
            this.bannarUrl1 = bannarUrl1;
        }

        public String getPowerJzapply() {
            return powerJzapply;
        }

        public void setPowerJzapply(String powerJzapply) {
            this.powerJzapply = powerJzapply;
        }

        public String getConsumptionVideo() {
            return consumptionVideo;
        }

        public void setConsumptionVideo(String consumptionVideo) {
            this.consumptionVideo = consumptionVideo;
        }

        public String getBannarUrl2() {
            return bannarUrl2;
        }

        public void setBannarUrl2(String bannarUrl2) {
            this.bannarUrl2 = bannarUrl2;
        }

        public String getBannarUrl3() {
            return bannarUrl3;
        }

        public void setBannarUrl3(String bannarUrl3) {
            this.bannarUrl3 = bannarUrl3;
        }

        public String getMp4Introduce() {
            return mp4Introduce;
        }

        public void setMp4Introduce(String mp4Introduce) {
            this.mp4Introduce = mp4Introduce;
        }

        public String getDefaultAccount() {
            return defaultAccount;
        }

        public void setDefaultAccount(String defaultAccount) {
            this.defaultAccount = defaultAccount;
        }

        public String getPrefixUrl() {
            return prefixUrl;
        }

        public void setPrefixUrl(String prefixUrl) {
            this.prefixUrl = prefixUrl;
        }

        public String getAboutTerminadocument() {
            return aboutTerminadocument;
        }

        public void setAboutTerminadocument(String aboutTerminadocument) {
            this.aboutTerminadocument = aboutTerminadocument;
        }

        public String getPowerCvapply() {
            return powerCvapply;
        }

        public void setPowerCvapply(String powerCvapply) {
            this.powerCvapply = powerCvapply;
        }

        public String getAboutInformationwrong() {
            return aboutInformationwrong;
        }

        public void setAboutInformationwrong(String aboutInformationwrong) {
            this.aboutInformationwrong = aboutInformationwrong;
        }

        public String getPowerZzapply() {
            return powerZzapply;
        }

        public void setPowerZzapply(String powerZzapply) {
            this.powerZzapply = powerZzapply;
        }

        public String getCycleSeats() {
            return cycleSeats;
        }

        public void setCycleSeats(String cycleSeats) {
            this.cycleSeats = cycleSeats;
        }

        public String getAboutGainpoint() {
            return aboutGainpoint;
        }

        public void setAboutGainpoint(String aboutGainpoint) {
            this.aboutGainpoint = aboutGainpoint;
        }

        public String getAboutOrderrules() {
            return aboutOrderrules;
        }

        public void setAboutOrderrules(String aboutOrderrules) {
            this.aboutOrderrules = aboutOrderrules;
        }

        public String getIconPrivate() {
            return iconPrivate;
        }

        public void setIconPrivate(String iconPrivate) {
            this.iconPrivate = iconPrivate;
        }

        public String getAboutLegaldocument() {
            return aboutLegaldocument;
        }

        public void setAboutLegaldocument(String aboutLegaldocument) {
            this.aboutLegaldocument = aboutLegaldocument;
        }

        public String getAboutWithdrawscash() {
            return aboutWithdrawscash;
        }

        public void setAboutWithdrawscash(String aboutWithdrawscash) {
            this.aboutWithdrawscash = aboutWithdrawscash;
        }

        public String getServiceTelephone1() {
            return serviceTelephone1;
        }

        public void setServiceTelephone1(String serviceTelephone1) {
            this.serviceTelephone1 = serviceTelephone1;
        }

        public String getConsumptionPoster() {
            return consumptionPoster;
        }

        public void setConsumptionPoster(String consumptionPoster) {
            this.consumptionPoster = consumptionPoster;
        }

        public String getModuleMseats() {
            return moduleMseats;
        }

        public void setModuleMseats(String moduleMseats) {
            this.moduleMseats = moduleMseats;
        }

        public String getPowerDaylogin() {
            return powerDaylogin;
        }

        public void setPowerDaylogin(String powerDaylogin) {
            this.powerDaylogin = powerDaylogin;
        }

        public String getAboutxpl() {
            return aboutxpl;
        }

        public void setAboutxpl(String aboutxpl) {
            this.aboutxpl = aboutxpl;
        }

        public String getAccountsecurity() {
            return accountsecurity;
        }

        public void setAccountsecurity(String accountsecurity) {
            this.accountsecurity = accountsecurity;
        }

        public String getConsumptionVp() {
            return consumptionVp;
        }

        public void setConsumptionVp(String consumptionVp) {
            this.consumptionVp = consumptionVp;
        }

        public String getAboutCouponrules() {
            return aboutCouponrules;
        }

        public void setAboutCouponrules(String aboutCouponrules) {
            this.aboutCouponrules = aboutCouponrules;
        }

        public String getSeatsSecond() {
            return seatsSecond;
        }

        public void setSeatsSecond(String seatsSecond) {
            this.seatsSecond = seatsSecond;
        }

        public String getAboutFqa() {
            return aboutFqa;
        }

        public void setAboutFqa(String aboutFqa) {
            this.aboutFqa = aboutFqa;
        }

        public String getLaunchspec() {
            return launchspec;
        }

        public void setLaunchspec(String launchspec) {
            this.launchspec = launchspec;
        }

        public String getPowerPvapply() {
            return powerPvapply;
        }

        public void setPowerPvapply(String powerPvapply) {
            this.powerPvapply = powerPvapply;
        }


        public String getCntMin() {
            return cntMin;
        }

        public void setCntMin(String cntMin) {
            this.cntMin = cntMin;
        }

        public String getAboutUserguide() {
            return aboutUserguide;
        }

        public void setAboutUserguide(String aboutUserguide) {
            this.aboutUserguide = aboutUserguide;
        }

        public String getXpluseragreement() {
            return xpluseragreement;
        }

        public void setXpluseragreement(String xpluseragreement) {
            this.xpluseragreement = xpluseragreement;
        }

        public String getBeatInterval() {
            return beatInterval;
        }

        public void setBeatInterval(String beatInterval) {
            this.beatInterval = beatInterval;
        }

        public String getPowerSqaset() {
            return powerSqaset;
        }

        public void setPowerSqaset(String powerSqaset) {
            this.powerSqaset = powerSqaset;
        }

        public String getIconShare() {
            return iconShare;
        }

        public void setIconShare(String iconShare) {
            this.iconShare = iconShare;
        }

        public String getPhotoalbum() {
            return photoalbum;
        }

        public void setPhotoalbum(String photoalbum) {
            this.photoalbum = photoalbum;
        }

        public String getAboutPowerspec() {
            return aboutPowerspec;
        }

        public void setAboutPowerspec(String aboutPowerspec) {
            this.aboutPowerspec = aboutPowerspec;
        }

        public String getAboutIosdownload() {
            return aboutIosdownload;
        }

        public void setAboutIosdownload(String aboutIosdownload) {
            this.aboutIosdownload = aboutIosdownload;
        }

        public String getPrivacypolicy() {
            return privacypolicy;
        }

        public void setPrivacypolicy(String privacypolicy) {
            this.privacypolicy = privacypolicy;
        }
    }

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


}
