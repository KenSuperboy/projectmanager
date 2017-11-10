package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushReqChargesDtActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.push_req_money_tv)
    TextView pushReqMoneyTv;
    @BindView(R.id.push_req_money1_tv)
    TextView pushReqMoney1Tv;
    @BindView(R.id.push_req_money2_tv)
    TextView pushReqMoney2Tv;
    @BindView(R.id.push_req_time_tv)
    TextView pushReqTimeTv;
    @BindView(R.id.push_req_count_tv)
    TextView pushReqCountTv;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.push_req_money_r_tv)
    TextView pushReqMoneyRTv;
    @BindView(R.id.push_req_money_ps_ll)
    LinearLayout pushReqMoneyPsLl;
    @BindView(R.id.leve)
    TextView leve;
    private int leveMoney, times, ptc,leves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_charges_dt);
        ButterKnife.bind(this);
        toolbarTitle.setText("单屏单周计费");
        setStatusColor(R.color.colorPrimary);
        leveMoney = getIntent().getIntExtra("leveMoney", 0);
        times = getIntent().getIntExtra("times", 0);
        ptc = getIntent().getIntExtra("ptc", 0);
        leves= getIntent().getIntExtra("leve", 1);
        pushReqMoneyTv.setText((leveMoney * ptc) + "元");
        pushReqMoney1Tv.setText(leveMoney + "元");
        pushReqTimeTv.setText(times + "S");
        pushReqCountTv.setText(ptc + "");
        leve.setText("星级("+leves+"级)");
        //  (10-0)*1=10元
        pushReqMoneyRTv.setText("("+leveMoney+"-0)*"+ptc+"="+(leveMoney * ptc) + "元");
    }

    @OnClick({R.id.toolbar_left_title, R.id.push_req_money_ps_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.push_req_money_ps_ll:
                startActivity(new Intent(PushReqChargesDtActivity.this, WebViewActivity.class).putExtra("url", Utils.getSystemInfoBean(this).getData().getValuationrules())
                                                                                                .putExtra("title","计费规则"));
                break;
        }
    }
}
