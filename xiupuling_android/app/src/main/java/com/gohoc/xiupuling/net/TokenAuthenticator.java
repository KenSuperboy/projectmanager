package com.gohoc.xiupuling.net;

import android.content.Context;
import android.content.Intent;

import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.ui.account.AccountSettingActivity;
import com.gohoc.xiupuling.ui.account.LoginActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by 28713 on 2017/3/25.
 */
public class TokenAuthenticator implements Authenticator {
    private Context context;

    public TokenAuthenticator(Context context) {
        this.context = context;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        ACache aCache = ACache.get(context);
        LinkedHashMap<String, UserBean> userList = (LinkedHashMap<String, UserBean>) aCache.getAsObject("userList");
        aCache.clear();
        aCache.put("userList", userList);
        aCache.put("FirstBlood", "FirstBlood");

        Intent intent = new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Logger.e("登录信息无效.......");
        return null;
    }
}