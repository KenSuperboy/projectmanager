package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BuyVipGoodsBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.account.MemberAuthorizationActivity;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayVipReslutActivity extends BasicActivity {
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.order_no_tv)
    TextView orderNoTv;
    @BindView(R.id.tips_tv)
    TextView tipsTv;
    @BindView(R.id.push_req_money_ps_ll)
    LinearLayout pushReqMoneyPsLl;
    private BuyVipGoodsBean buyVipGoodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_vip_reslut);
        ButterKnife.bind(this);
        toolbarTitle.setText("支付成功");
        toolbarLeftTitle.setText("商城首页");
        setStatusColor(R.color.colorPrimary);
        buyVipGoodsBean = (BuyVipGoodsBean) getIntent().getExtras().get("buyVipGoodsBean");
        Credential.getInstance().updateUserInfo(this);
        init();
    }

    private void init() {
        nameTv.setText(buyVipGoodsBean.getData().getTradename());
        timeTv.setText(buyVipGoodsBean.getData().getCreate_time() + "购买成功");
        orderNoTv.setText("订单号:" + buyVipGoodsBean.getData().getRefno());

        // 响应点击事件的话必须设置以下属性
        tipsTv.setMovementMethod(LinkMovementMethod.getInstance());


        SpanUtils su = new SpanUtils();
        su.append("Tips:您可进入个人中心，在会员特权里面查看");
        su.append("目前的权限数").setForegroundColor(Color.parseColor("#27ABFD")).setClickSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(PayVipReslutActivity.this, MemberAuthorizationActivity.class));
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        });
        su.append("，或进入");
        su.append("会员商城").setForegroundColor(Color.parseColor("#27ABFD")).setClickSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        });
        su.append("继续购买别的商品。");


        tipsTv.setText(su.create());

    }

    @OnClick({R.id.toolbar_left_title, R.id.push_req_money_ps_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.push_req_money_ps_ll:
                startActivity(new Intent(PayVipReslutActivity.this, WebViewActivity.class).putExtra("url", Utils.getSystemInfoBean(this).getData().getAboutPowerspec()).putExtra("title", "权限说明"));
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
        }
        return false;
    }
}
