package com.gohoc.xiupuling.net;

import android.content.Context;
import android.util.Base64;

import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.utils.ACache;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sherlock-sky on 2017/1/27.
 */

public class HttpHeaderInterceptor implements Interceptor {
    private Context context;

    public HttpHeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder=original.newBuilder()
                .header("User-Agent", "Android")
                .header("Accept", "application/json")
                //.header("Content-type", "application/json");
               .header("Content-type", "application/x-www-form-urlencoded");

        UserBean userBean= (UserBean) ACache.get(context).getAsObject("userBean");

        if(null!=userBean && userBean.getData()!=null)
        {
            builder.header("Authorization",getAuthorization(userBean));
            //builder.header("Authorization",getAuthorization("10140569","3ae3010b-16f5-3686-aef0-661fe4e51eda"));
            //builder.header("Authorization","MTAxNDA1ODE6MTg4MjY5OTgtMWI5NS0zMzgwLTg1YWUtNWE1NThhZmFhZTU3");
        }

        Request request = builder.method(original.method(), original.body()).build();
        Logger.d("requestHead:"+request.headers());
        return chain.proceed(request);
    }

    public String getAuthorization(UserBean userBean) {
        String authorization="";
        String  temp=userBean.getData().getGid()+":"+userBean.getData().getToken();
        Logger.e(temp);
        authorization= Base64.encodeToString(temp.getBytes(), Base64.DEFAULT);
        return authorization.trim();
    }
    public String getAuthorization(String gid,String token) {
        String authorization="";
        String  temp=gid+":"+token;
        Logger.e(temp);
        authorization= Base64.encodeToString(temp.getBytes(), Base64.DEFAULT);
        return authorization.trim();
    }
}