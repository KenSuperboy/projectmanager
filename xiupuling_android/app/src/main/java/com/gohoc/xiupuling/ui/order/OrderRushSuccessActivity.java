package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.OrderBean;
import com.gohoc.xiupuling.bean.OrderDetailBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.requirement.ReqContactsdsActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderRushSuccessActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.terinal_tv)
    TextView terinalTv;
    @BindView(R.id.contet_tv)
    TextView contetTv;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.tips_tv)
    TextView tipsTv;
    @BindView(R.id.order_rule_ll)
    LinearLayout orderRuleLl;
    private OrderDetailBean orderDetailBeans;
    private OrderBean.DataBean orderBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rush_success);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("抢单成功");
        orderDetailBeans = (OrderDetailBean) getIntent().getExtras().get("orderDetailBeans");
        orderBeans = (OrderBean.DataBean) getIntent().getExtras().get("order");
        initDates();
    }

    private void initDates() {
        terinalTv.setText(orderBeans.getTerminal_no() + "号机 " + orderBeans.getShop_name());
        contetTv.setText("播放:" + orderDetailBeans.getData().getWorkdetail().getWork_name());
        SpanUtils su = new SpanUtils();
        tipsTv.setMovementMethod(LinkMovementMethod.getInstance());
        su.append("提示:您可以在终端-").append("订单执行情况").setClickSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(OrderRushSuccessActivity.this, OrderExecuteStatusListActivity.class).putExtra("terminal_id", orderBeans.getTerminal_id()));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }).setFontSize(14, true).setForegroundColor(Color.parseColor("#0099fd")).append("中查看具体情况");
        moneyTv.setText("￥" + orderBeans.getTotalamt());
        tipsTv.setText(su.create());
    }

    @OnClick({R.id.toolbar_left_title, R.id.order_rule_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goBack();
                break;
            case R.id.order_rule_ll:
                startActivity(new Intent(OrderRushSuccessActivity.this, WebViewActivity.class)
                        .putExtra("url", Utils.getSystemInfoBean(this).getData().getAboutOrderrules())
                        .putExtra("title", "订单收益规则"));
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
        }
        return false;
    }

    private void goBack() {
        EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        EventBus.getDefault().post(new Event.MainIndex(2));
        finish();
    }
}
