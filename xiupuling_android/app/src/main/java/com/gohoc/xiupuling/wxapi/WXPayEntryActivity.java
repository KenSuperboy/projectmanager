package com.gohoc.xiupuling.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, Constant.BaseConstant.WX_APP_ID);
        api.handleIntent(getIntent(), WXPayEntryActivity.this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
         //   Utils.toast(WXPayEntryActivity.this, "支付成功"+resp.errCode);

                Log.e("wxpay", "errstr=" + resp.errStr);
                if (resp.errCode == 0) {   //成功
                   // Utils.toast(WXPayEntryActivity.this, "支付成功");
                } else if (resp.errCode == -1) {   //错误
                    Utils.toast(WXPayEntryActivity.this, "支付失败");
                } else if (resp.errCode == -2) {   //取消
                    Utils.toast(WXPayEntryActivity.this, "取消支付");
                }
            EventBus.getDefault().post(new Event.WxPayEvent(resp.errCode));
            finish();
        }
    }


}
