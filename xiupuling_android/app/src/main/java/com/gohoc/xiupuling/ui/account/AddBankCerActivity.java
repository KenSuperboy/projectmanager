package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class AddBankCerActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.ivf1)
    ImageView ivf1;
    @BindView(R.id.xiaofeidaren_rv)
    RelativeLayout xiaofeidarenRv;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.ivf2)
    ImageView ivf2;
    @BindView(R.id.jizhu_rv)
    RelativeLayout jizhuRv;
    @BindView(R.id.textView18)
    TextView textView18;
    @BindView(R.id.personl_ll)
    LinearLayout personlLl;
    @BindView(R.id.enterprise_lv)
    LinearLayout enterpriseLv;
    @BindView(R.id.p_tv)
    TextView pTv;
    @BindView(R.id.e_tv)
    TextView eTv;
    @BindView(R.id.p_state)
    TextView pState;
    @BindView(R.id.e_state)
    TextView eState;
    private UserBaseBean userBaseBeans;
    private final static int REQUEST_RESULT_ADD_BANK = 10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_cer);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        toolbarTitle.setText("添加银行卡");
        setStatusColor(R.color.colorPrimary);
        userBaseBeans = Credential.getInstance().getCurUser(this);
        initDate();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UserEvent(Event.UserEvent userEvent) {
        Logger.e("zcsUserEvent","UserEventUserEventUserEventUserEvent");
        userBaseBeans = Credential.getInstance().getCurUser(this);
        initDate();
    }

    private void initDate() {
        if (userBaseBeans.getData().getPersonal_auth() == 2) {
            ivf1.setVisibility(View.VISIBLE);
            personlLl.setBackgroundResource(R.drawable.menu_selector);
        } else {
            ivf1.setVisibility(View.GONE);
        }

        if (userBaseBeans.getData().getCompany_auth() == 2) {
            ivf2.setVisibility(View.VISIBLE);
            enterpriseLv.setBackgroundResource(R.drawable.menu_selector);
        } else
            ivf2.setVisibility(View.GONE);


        if (userBaseBeans.getData().getPersonal_auth() == 0)
            pTv.setText("申请个人认证");
        else
            pTv.setText("个人认证\n" + "(" + userBaseBeans.getData().getPersonal_authStr() + ")");


        if (userBaseBeans.getData().getCompany_auth() == 0)
            eTv.setText("申请企业认证");
        else
            eTv.setText("企业认证\n" + "(" + userBaseBeans.getData().getCompany_authStr() + ")");

        if (userBaseBeans.getData().getPersonal_auth() != 0)
            pState.setText(userBaseBeans.getData().getPersonal_authStr());
        if (userBaseBeans.getData().getCompany_auth() != 0)
            eState.setText(userBaseBeans.getData().getCompany_authStr());
    }

    @OnClick({R.id.toolbar_left_title, R.id.xiaofeidaren_rv, R.id.jizhu_rv, R.id.personl_ll, R.id.enterprise_lv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.xiaofeidaren_rv:
                if (userBaseBeans.getData().getPersonal_auth() == 0) {
                    startActivity(new Intent(AddBankCerActivity.this, PersonlCertificationActivity.class));
                }
                break;
            case R.id.jizhu_rv:
                if (userBaseBeans.getData().getCompany_auth() == 0) {
                    startActivity(new Intent(AddBankCerActivity.this, EnterpriselCertificationActivity.class));
                }
                break;
            case R.id.personl_ll:
                if (userBaseBeans.getData().getPersonal_auth() == 2)
                    startActivityForResult(new Intent(AddBankCerActivity.this, AddbankActivity.class).putExtra("type", 0), REQUEST_RESULT_ADD_BANK);
                break;
            case R.id.enterprise_lv:
                if (userBaseBeans.getData().getCompany_auth() == 2)
                    startActivityForResult(new Intent(AddBankCerActivity.this, AddbankActivity.class).putExtra("type", 1), REQUEST_RESULT_ADD_BANK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            setResult(RESULT_OK);
            finish();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
