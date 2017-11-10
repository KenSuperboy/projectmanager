package com.gohoc.xiupuling.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.account.LoginActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import rx.Observer;

public class SplashActivity extends Activity {
    private ACache mCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {

        mCache = ACache.get(SplashActivity.this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                UserBean userBean = (UserBean) mCache.getAsObject("userBean");
                if (userBean != null) {
                    getBaseInfo();
                    getUserInfo();
                } else {
                    if (TextUtils.isEmpty(mCache.getAsString("FirstBlood")))
                        startActivity(new Intent(SplashActivity.this, FirstBloodActivity.class));
                    else
                      startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                SplashActivity.this.finish();
            }
        }, Constant.BaseConstant.SPLASH_TIME);
    }

    private void getBaseInfo() {
        RxRetrofitClient.getInstance(SplashActivity.this).systemdictionarylist(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onError(Throwable e) {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
                Toast.makeText(SplashActivity.this, "请检查网络是否正常", Toast.LENGTH_SHORT).show();

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
                    mCache.put("SystemInfoBean", sb);
                    Logger.e("SystemInfoBean:" + sb.getCode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getUserInfo() {
        RxRetrofitClient.getInstance(SplashActivity.this).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SplashActivity.this, "请检查网络是否正常", Toast.LENGTH_SHORT).show();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    mCache.put("userBaseBean", userBaseBean);
                }
            }
        });
    }


}
