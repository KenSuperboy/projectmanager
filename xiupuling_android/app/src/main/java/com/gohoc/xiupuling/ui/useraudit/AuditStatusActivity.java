package com.gohoc.xiupuling.ui.useraudit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.audit.AuditUserInfor;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 账户监管审查状态
* */
public class AuditStatusActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_telephone)
    TextView mTvTelephone;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout mSumbitButtonLl;
    @BindView(R.id.linear_yes_layout)
    LinearLayout mLinearYesLayout;
    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;

    private String number="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_status_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("播放审核机制");
        setStatusColor(R.color.colorPrimary);

        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mIvAdd.setVisibility(View.GONE);
    }


    private void initData() {
        getAuditInfor();
    }

    private void getAuditInfor() {
        RxRetrofitClient.getInstance(mContext).getAuditInfor(new Observer<AuditUserInfor>() {
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
            public void onNext(AuditUserInfor auditUserInfor) {
                LogUtil.d("获取监管者记载成功");
                if (auditUserInfor.code == 1 && auditUserInfor.data != null) {
                    mTvName.setText(auditUserInfor.data.nick_name);
                    number=auditUserInfor.data.mobile;
                    Glide.with(AuditStatusActivity.this)
                            .load(NetConstant.BASE_USER_RESOURE + auditUserInfor.data.portrait_url + Utils.getThumbnail(200, 200))
                            .placeholder(R.mipmap.icon_usercenter_morentouxiang)
                            .error(R.mipmap.icon_usercenter_morentouxiang)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mIvLogo);

                    mTvTelephone.setText(Utils.doMobile(auditUserInfor.data.mobile) + "");
                } else {//没有播放机制的话会返回0

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshAuditUserListEvent(Event.RefreshAuditUserStatusEvent message) {
        LogUtil.d("监管账户状态");
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /*private void addAuditUser() {
        startActivity(new Intent(AuditStatusActivity.this, AddauditUserActivity3.class).putExtra("mobile", "15817234445"));
        //startActivity(new Intent(AuditStatusActivity.this, AddauditUserActivity1.class));
    }*/

    @OnClick({R.id.toolbar_left_title, R.id.sumbit_button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.sumbit_button_ll:
                Intent intent = new Intent(AuditStatusActivity.this, AuditComfireActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);
                break;
        }
    }
}
