package com.gohoc.xiupuling.ui.useraudit;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class AddauditUserActivity3 extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    /*@BindView(R.id.toolbar)
    Toolbar mToolbar;*/
    @BindView(R.id.register_head)
    LinearLayout mRegisterHead;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_telephone)
    TextView mTvTelephone;
    @BindView(R.id.register_phone_ll)
    LinearLayout mRegisterPhoneLl;
    @BindView(R.id.tv_tip_btn)
    TextView mTvTipBtn;
    @BindView(R.id.getvf_button_ll)
    LinearLayout mGetvfButtonLl;
    @BindView(R.id.activity_register)
    RelativeLayout mActivityRegister;
    private String mobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audituser_layout_3);
        ButterKnife.bind(this);
        mToolbarTitle.setText("添加被监管账户");
        setListener();
        setStatusColor(R.color.colorPrimary);

        mobile = getIntent().getStringExtra("mobile");
        getBeiAuditInfor();

        mTvTelephone.setText(Utils.doMobile(mobile));
    }

    private void setListener() {

    }

    private void getBeiAuditInfor() {
        RxRetrofitClient.getInstance(mContext).getBeiAuditInfor(mobile,new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(mContext, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("获取被监管者记载成功");
                if (emptyBean.code == 1) {
                } else {
                }
            }
        });
    }



    @OnClick({R.id.toolbar_left_title, R.id.getvf_button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.getvf_button_ll:
                EventBus.getDefault().post(new Event.RefreshAuditUserEvent_1());
                EventBus.getDefault().post(new Event.RefreshAuditUserListEvent());
                finish();
                break;
        }
    }
}
