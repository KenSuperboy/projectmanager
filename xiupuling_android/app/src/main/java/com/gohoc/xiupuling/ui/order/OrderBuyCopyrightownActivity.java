package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushOrderConfimResultBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.bean.WxPayBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.constant.MyApp;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.pay.PayResult;
import com.gohoc.xiupuling.pay.PayWebViewActivity;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class OrderBuyCopyrightownActivity extends BasicActivity {
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.order_name_v)
    TextView orderNameV;
    @BindView(R.id.order_money_v)
    TextView orderMoneyV;
    @BindView(R.id.coupons_lv)
    LinearLayout couponsLv;
    @BindView(R.id.pay_crad_ll)
    LinearLayout payCradLl;
    @BindView(R.id.pay_wechat_ll)
    LinearLayout payWechatLl;
    @BindView(R.id.pay_aliy_ll)
    LinearLayout payAliyLl;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.pay_crad_iv)
    ImageView payCradIv;
    @BindView(R.id.pay_wechat_iv)
    ImageView payWechatIv;
    @BindView(R.id.pay_aliy_iv)
    ImageView payAliyIv;
    @BindView(R.id.less_tv)
    TextView lessTv;
    private int payType = 3;
    private PushOrderConfimResultBean resultBean;
    private String payInfoStr = "";
    private Handler mHandler;
    private static final int SDK_PAY_FLAG = 1001;
    private IWXAPI api;
    private PushNormlBean pushNormlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_buy_copyrightown);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("支付订单");
        EventBus.getDefault().register(this);
        initPay();
        api = WXAPIFactory.createWXAPI(this, Constant.BaseConstant.WX_APP_ID);
        api.registerApp(Constant.BaseConstant.WX_APP_ID);
        MyApp.getActivityList().add(this);

    }

    private void initPay() {
        try {
            resultBean = (PushOrderConfimResultBean) getIntent().getExtras().get("resultBean");
            pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
            orderNameV.setText(pushNormlBean.getName() + " 版权费");
            orderMoneyV.setText("￥" + resultBean.getData().getAmount() + "");
            lessTv.setText("￥" + resultBean.getData().getAmount() + "");

        } catch (Exception e) {

        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult(msg.obj.toString());
                        //("{9000}", "支付成功");
                        //("{4000}", "系统异常");
                        //("{4001}", "订单参数错误");
                        //("{6001}", "您已取消了本次订单的支付");
                        //("{6002}", "网络连接异常");

                        Logger.e("支付结果:" + msg.obj);
                        Utils.toast(OrderBuyCopyrightownActivity.this, payResult.getMemo());
                        if (payResult.getResultStatus().equals("9000")) {
                            startActivity(new Intent(OrderBuyCopyrightownActivity.this, OrderCopyrightownResultActivity.class).putExtra("resultBean", resultBean)
                                    .putExtra("pushNormlBean", pushNormlBean));
                        }
                        break;
                    }
                    default:
                        break;
                }

            }
        };

    }

    @OnClick({R.id.toolbar_left_title, R.id.pay_crad_ll, R.id.pay_wechat_ll, R.id.pay_aliy_ll, R.id.button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.pay_crad_ll:
                payType = 1;
                payCradIv.setImageResource(R.mipmap.icon__zhuce_tongyi);
                payWechatIv.setImageResource(R.mipmap.icon_zhuce_butongyi);
                payAliyIv.setImageResource(R.mipmap.icon_zhuce_butongyi);
                break;
            case R.id.pay_wechat_ll:
                payType = 2;
                payCradIv.setImageResource(R.mipmap.icon_zhuce_butongyi);
                payWechatIv.setImageResource(R.mipmap.icon__zhuce_tongyi);
                payAliyIv.setImageResource(R.mipmap.icon_zhuce_butongyi);
                break;
            case R.id.pay_aliy_ll:
                payType = 3;
                payCradIv.setImageResource(R.mipmap.icon_zhuce_butongyi);
                payWechatIv.setImageResource(R.mipmap.icon_zhuce_butongyi);
                payAliyIv.setImageResource(R.mipmap.icon__zhuce_tongyi);
                break;
            case R.id.button_ll:
                sumbitPay(payType);
                break;
        }

    }

    //支付宝支付
    private void initorder(String refno) {
        RxRetrofitClient.getInstance(this).initorder(refno, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderBuyCopyrightownActivity.this, "订单初始化失败");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    payInfoStr = vCodeBenan.getData();
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            // 构造PayTask 对象
                            PayTask alipay = new PayTask(OrderBuyCopyrightownActivity.this);
                            // 调用支付接口，获取支付结果
                            String result = alipay.pay(payInfoStr, true);

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };

                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else
                    Utils.toast(OrderBuyCopyrightownActivity.this, vCodeBenan.getMessage() + "");
            }
        });
    }

    //快钱支付
    private void kqinitorder(String refno) {
        RxRetrofitClient.getInstance(this).kqinitorder(refno, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderBuyCopyrightownActivity.this, "订单初始化失败");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    startActivity(new Intent(OrderBuyCopyrightownActivity.this, PayWebViewActivity.class).putExtra("url", vCodeBenan.getData()).putExtra("title", "快钱支付"));
                } else
                    Utils.toast(OrderBuyCopyrightownActivity.this, vCodeBenan.getMessage() + "");
            }
        });
    }

    //微信支付
    private void unifiedorder(String refno) {
        RxRetrofitClient.getInstance(this).unifiedorder(refno, new Observer<WxPayBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderBuyCopyrightownActivity.this, "订单初始化失败");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(WxPayBean wxPayBean) {
                if (wxPayBean.getCode() == 1) {
                    Logger.e("调起微信支付...." + (api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT));
                    PayReq request = new PayReq();
                    request.appId = Constant.BaseConstant.WX_APP_ID;
                    request.partnerId = wxPayBean.getPartnerid();
                    request.prepayId = wxPayBean.getPrepayid();
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr = wxPayBean.getNoncestr();
                    request.timeStamp = wxPayBean.getTimestamp();
                    request.sign = wxPayBean.getSign();
                    api.sendReq(request);
                } else
                    Utils.toast(OrderBuyCopyrightownActivity.this, wxPayBean.getMessage());
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WxPayEvent(Event.WxPayEvent wxPayEvent) {
        if (wxPayEvent.i == 0) {
            startActivity(new Intent(OrderBuyCopyrightownActivity.this, OrderCopyrightownResultActivity.class).putExtra("resultBean", resultBean)
                    .putExtra("pushNormlBean", pushNormlBean));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void sumbitPay(int type) {
        if (type == 1)
            kqinitorder(resultBean.getData().getRefno() + "");
        else if (type == 2)
            unifiedorder(resultBean.getData().getRefno() + "");
        else if (type == 3)
            initorder(resultBean.getData().getRefno() + "");
    }

}

