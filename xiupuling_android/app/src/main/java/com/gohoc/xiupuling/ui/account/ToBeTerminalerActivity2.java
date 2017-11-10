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
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class ToBeTerminalerActivity2 extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.id_iv)
    ImageView idIv;
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
    @BindView(R.id.save_button_ll)
    LinearLayout saveButtonLl;
    private static int TERMINALER_REQUEST_RESULT = 1000;
    @BindView(R.id.p_tv)
    TextView pTv;
    @BindView(R.id.e_tv)
    TextView eTv;
    private UserBaseBean userBaseBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_terminaler2);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("实名认证");
        userBaseBeans = Credential.getInstance().getCurUser(this);
        init();
    }

    @OnClick({R.id.toolbar_left_title, R.id.xiaofeidaren_rv, R.id.jizhu_rv, R.id.save_button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.xiaofeidaren_rv:
                if (userBaseBeans.getData().getPersonal_auth() == 0)
                    startActivity(new Intent(ToBeTerminalerActivity2.this, PersonlCertificationActivity.class));
                break;
            case R.id.jizhu_rv:
                if (userBaseBeans.getData().getCompany_auth() == 0)
                    startActivity(new Intent(ToBeTerminalerActivity2.this, EnterpriselCertificationActivity.class));
                break;
            case R.id.save_button_ll:
                if (userBaseBeans.getData().getCompany_auth() == 2 || userBaseBeans.getData().getPersonal_auth() == 2)
                    startActivityForResult(new Intent(ToBeTerminalerActivity2.this, ToBeTerminalerActivity3.class), TERMINALER_REQUEST_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode);
        finish();
    }

    private void init() {
        if (userBaseBeans.getData().getPersonal_auth() == 2) {
            ivf1.setVisibility(View.VISIBLE);
        } else {
            ivf1.setVisibility(View.GONE);
        }

        if (userBaseBeans.getData().getCompany_auth() == 2)
            ivf2.setVisibility(View.VISIBLE);
        else
            ivf2.setVisibility(View.GONE);

        if (userBaseBeans.getData().getPersonal_auth() == 0)
            pTv.setText(userBaseBeans.getData().getPersonal_authStr());
        else
            pTv.setText("个人认证\n" + "(" + userBaseBeans.getData().getPersonal_authStr() + ")");

        if (userBaseBeans.getData().getCompany_auth() == 0)
            eTv.setText(userBaseBeans.getData().getCompany_authStr());
        else
            eTv.setText("企业认证\n" + "(" + userBaseBeans.getData().getCompany_authStr() + ")");
        if (userBaseBeans.getData().getCompany_auth() == 2 || userBaseBeans.getData().getPersonal_auth() == 2) {
            saveButtonLl.setBackgroundResource(R.drawable.login_bt_selector);
        }
    }
}
