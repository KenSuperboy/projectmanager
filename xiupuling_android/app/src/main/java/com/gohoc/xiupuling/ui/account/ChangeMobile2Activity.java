package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class ChangeMobile2Activity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.mobile_sms_lv)
    LinearLayout mobileSmsLv;
    @BindView(R.id.register2_tips)
    TextView register2Tips;
    @BindView(R.id.sq_ck_lv)
    LinearLayout sqCkLv;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile2);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("更换手机号码");
        getUserInfo();
        mobile = this.getIntent().getStringExtra("mobile");
        setStatusColor(R.color.colorPrimary);
        if (Utils.getSystemInfoBean(this) != null)
            register2Tips.setText("客服电话:" + Utils.getSystemInfoBean(this).getData().getServiceTelephone1() + "");
    }

    @OnClick({R.id.toolbar_left_title, R.id.mobile_sms_lv, R.id.sq_ck_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                ChangeMobile2Activity.this.finish();
                break;
            case R.id.mobile_sms_lv:
                startActivity(new Intent(ChangeMobile2Activity.this, ChangeMobile3Activity.class).putExtra("mobile", mobile + ""));
                finish();
                break;
            case R.id.sq_ck_lv:
                startActivity(new Intent(ChangeMobile2Activity.this, CheckSqActivity2.class).putExtra("mobile", mobile + "").putExtra("title","更换手机号"));
                finish();
        }
    }

    private void getUserInfo() {


        RxRetrofitClient.getInstance(this).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ChangeMobile2Activity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    if (userBaseBean.getData().getIs_security_question() == 1)
                        sqCkLv.setVisibility(View.VISIBLE);
                    else
                        sqCkLv.setVisibility(View.GONE);
                }
            }
        });
    }
}
