package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushOrderConfimResultBean;
import com.gohoc.xiupuling.bean.PushResultBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.push.PushLocationActivity;
import com.gohoc.xiupuling.ui.push.PushReqShowCountActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;
import com.wuxiaolong.androidutils.library.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushOrderPayReslutActivity extends BasicActivity {

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
    private PushOrderConfimResultBean resultBean;
    private PushResultBean pushResultBean;
    private PushNormlBean pushNormlBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_order_pay_reslut);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("支付成功");
        toolbarLeftTitle.setText("新信息");
        //tipsTv.setText(Html.fromHtml("<font color='#27ABFD'>展示效果</font>和<font color='#27ABFD'>投放地点</font>"));
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        nameTv.setText(pushNormlBean.getName());
        try {
            resultBean = (PushOrderConfimResultBean) getIntent().getExtras().get("resultBean");
            timeTv.setText(resultBean.getData().getCreate_time() + "发布成功");
            orderNoTv.setText("订单号：" + resultBean.getData().getRefno());

        } catch (Exception e) {
            try {
                pushResultBean = (PushResultBean) getIntent().getExtras().get("pushResultBean");
                timeTv.setText(pushResultBean.getData().getInforange().getCreate_time() + "发布成功");
                orderNoTv.setText("订单号：" + pushResultBean.getData().getInforange().getRange_id());
            } catch (Exception e2) {
            }

        }

        SpanUtils su = new SpanUtils();
        tipsTv.setMovementMethod(LinkMovementMethod.getInstance());
        su.append("Tips:耐心等待机主接单后，信息即可在对应终端上展示，可随时在发信息中查看");
        su.append("展示效果").setForegroundColor(Color.parseColor("#27ABFD")).setClickSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (pushResultBean != null)
                    startActivity(new Intent(PushOrderPayReslutActivity.this, PushReqShowCountActivity.class)
                            .putExtra("rq_id", pushResultBean.getData().getRq_id())
                            .putExtra("work_id", pushResultBean.getData().getWork_id())
                            .putExtra("pushNormlBean", pushNormlBean)
                    );
                else
                    startActivity(new Intent(PushOrderPayReslutActivity.this, PushReqShowCountActivity.class)
                            .putExtra("rq_id", pushNormlBean.getRq_id())
                            .putExtra("work_id", pushNormlBean.getWork_id())
                            .putExtra("pushNormlBean", pushNormlBean)
                    );

                setResult(RESULT_OK);

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        });
        su.append("和");
        su.append("投放地点").setForegroundColor(Color.parseColor("#27ABFD")).setClickSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                if (pushResultBean != null)
                    startActivity(new Intent(PushOrderPayReslutActivity.this, PushLocationActivity.class)
                            .putExtra("rq_id", pushResultBean.getData().getRq_id())
                            .putExtra("work_id", pushResultBean.getData().getWork_id())

                    );
                else
                    startActivity(new Intent(PushOrderPayReslutActivity.this, PushLocationActivity.class)
                            .putExtra("rq_id", pushNormlBean.getRq_id())
                            .putExtra("work_id", pushNormlBean.getWork_id())
                    );
                setResult(RESULT_OK);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        });
        tipsTv.setText(su.create());

        timeTv.setText(TimeUtil.getCurrentTime("yyyy-MM-dd")+"发布成功");
    }

    @OnClick({R.id.toolbar_left_title, R.id.push_req_money_ps_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goBack(pushNormlBean.getIndexFlage());
                break;
            case R.id.push_req_money_ps_ll:
                startActivity(new Intent(PushOrderPayReslutActivity.this, WebViewActivity.class).putExtra("url", Utils.getSystemInfoBean(this).getData().getLaunchspec()).putExtra("title", "投放说明"));
                break;
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack(pushNormlBean.getIndexFlage());
        }
        return false;
    }

    private void goBack(int i) {
        EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
        EventBus.getDefault().post(new Event.RefreshTerminalListEvent());//刷新首页   门店终端
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        EventBus.getDefault().post(new Event.MainIndex(i));
        finish();
    }

}
