package com.gohoc.xiupuling.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.account.LoginActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observer;


/**
 * Created by zcs on 17/6/2.
 */

public class Credential {
    static private Credential instance;

    static public Credential getInstance() {
        if (instance == null) {
            synchronized (Credential.class) {
                if (instance == null) {
                    instance = new Credential();
                }
            }
        }
        return instance;
    }

    private Credential() {
    }


    public void setCurUser(UserBaseBean curUser) {
        this.curUser = curUser;
    }

    private UserBaseBean curUser;

    public UserBaseBean getCurUser(Context context) {
        if (curUser == null)
            curUser = (UserBaseBean) ACache.get(context).getAsObject("userBaseBean");
        return curUser;
    }

    public Credential setCurUser(UserBaseBean curUser, Context context) {
        this.curUser = curUser;
        ACache.get(context).put("userBaseBean", curUser);
        return this;
    }

    public void updateUserInfo(Context context) {
        RxRetrofitClient.getInstance(context).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    setCurUser(userBaseBean);
                    EventBus.getDefault().post(new Event.UserEvent());
                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                }
            }
        });
    }


    public void getBaseInfo(final Context context) {
        RxRetrofitClient.getInstance(context).systemdictionarylist(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Logger.e(e.toString());
                }

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    SystemInfoBean sb = new SystemInfoBean(responseBody.string());
                    ACache.get(context).put("SystemInfoBean", sb);
                    Logger.e("SystemInfoBean:" + sb.getCode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
