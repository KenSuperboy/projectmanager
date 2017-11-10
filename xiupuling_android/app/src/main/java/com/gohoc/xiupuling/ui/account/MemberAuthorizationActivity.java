package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.AboutVipActivity;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class MemberAuthorizationActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_head_iv)
    CircleImageView mUserHeadIv;
    @BindView(R.id.user_name_tv)
    TextView mUserNameTv;
    @BindView(R.id.add_count_bt_tv)
    TextView mAddCountBtTv;
    @BindView(R.id.tv_video_number)
    TextView mTvVideoNumber;
    @BindView(R.id.linear_video_layout)
    LinearLayout mLinearVideoLayout;
    @BindView(R.id.tv_vip_number)
    TextView mTvVipNumber;
    @BindView(R.id.linear_vip_layout)
    LinearLayout mLinearVipLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_authorization);
        ButterKnife.bind(this);
        mToolbarTitle.setText("会员权限");
        setStatusColor(R.color.colorPrimary);
        getUserInfo();
    }


    private void getUserInfo() {
        RxRetrofitClient.getInstance(MemberAuthorizationActivity.this).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MemberAuthorizationActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    initInfo(userBaseBean);
                }
            }
        });
    }

    private void initInfo(UserBaseBean userBaseBean) {
        Glide.with(this)
                .load(NetConstant.BASE_USER_RESOURE + userBaseBean.getData().getPortrait_url())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                .error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserHeadIv);

        mUserNameTv.setText(userBaseBean.getData().getNick_name() + "");
        mTvVideoNumber.setText(userBaseBean.getData().getModule_own_cnt() + "");
        mTvVipNumber.setText(userBaseBean.getData().getVip_code_cnt() + "");
    }

    @OnClick({R.id.toolbar_left_title, R.id.add_count_bt_tv, R.id.linear_video_layout,R.id.linear_vip_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.add_count_bt_tv:
                startActivity(new Intent(MemberAuthorizationActivity.this, MemberShopActivity.class));
                break;
            case R.id.tv_video_number:
                startActivity(new Intent(MemberAuthorizationActivity.this, AboutVipActivity.class).putExtra("type","0").putExtra("count",mTvVideoNumber.getText().toString()));
                break;
            case R.id.linear_vip_layout:
                startActivity(new Intent(MemberAuthorizationActivity.this, AboutVipActivity.class).putExtra("type","1"));
                break;
        }
    }
}
