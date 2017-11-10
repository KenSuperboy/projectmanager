package com.gohoc.xiupuling.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gohoc.xiupuling.constant.Constant;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by 28713 on 2017/4/8.
 */

public class WxRegister extends BroadcastReceiver implements Constant {
    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        msgApi.registerApp(BaseConstant.WX_APP_ID);
    }
}
