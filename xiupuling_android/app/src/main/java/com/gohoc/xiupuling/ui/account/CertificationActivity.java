package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.CompanyVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.PersonalVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CertificationActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.personl_certification_tv)
    TextView personlCertificationTv;
    @BindView(R.id.personl_certification_lv)
    LinearLayout personlCertificationLv;
    @BindView(R.id.enterprise_certification_tv)
    TextView enterpriseCertificationTv;
    @BindView(R.id.enterprise_certification_lv)
    LinearLayout enterpriseCertificationLv;

    private CompanyVerifyapplyInfoBean companyVerifyapplyInfo;
    private PersonalVerifyapplyInfoBean personalVerifyapplyInfo;

    private UserBaseBean userBaseBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        EventBus.getDefault().register(this);
        toolbarTitle.setText("实名认证");
        userBaseBeans = Credential.getInstance().getCurUser(this);
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UserEventBus(Event.UserEvent message) {
        userBaseBeans = Credential.getInstance().getCurUser(this);
        init();
    }

    @OnClick({R.id.toolbar_left_title, R.id.personl_certification_lv, R.id.enterprise_certification_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                CertificationActivity.this.finish();
                break;
            case R.id.personl_certification_lv:
                if (userBaseBeans.getData().getPersonal_auth() == 0)
                    startActivity(new Intent(CertificationActivity.this, PersonlCertificationActivity.class));
                else
                    startActivity(new Intent(CertificationActivity.this, PersonlCertificationInfoActivity.class));
                 finish();
                break;
            case R.id.enterprise_certification_lv:
                if (userBaseBeans.getData().getCompany_auth() == 0)
                    startActivity(new Intent(CertificationActivity.this, EnterpriselCertificationActivity.class));
                else
                    startActivity(new Intent(CertificationActivity.this, EnterpriselCertificationInfoActivity.class));
                finish();
                break;
        }
    }

    private void init() {
        if (userBaseBeans.getData().getCompany_auth() != 0)
            enterpriseCertificationTv.setText("(" + userBaseBeans.getData().getCompany_authStr() + ")");
        if (userBaseBeans.getData().getPersonal_auth() != 0)
            personlCertificationTv.setText("(" + userBaseBeans.getData().getPersonal_authStr() + ")");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
