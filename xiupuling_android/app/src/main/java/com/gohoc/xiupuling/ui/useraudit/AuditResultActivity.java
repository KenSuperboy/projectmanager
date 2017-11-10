package com.gohoc.xiupuling.ui.useraudit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.Event;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 各种结果
* */
public class AuditResultActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_logo)
    ImageView mTvLogo;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    @BindView(R.id.linear_shenhe_layout)
    LinearLayout mLinearShenheLayout;
    @BindView(R.id.tv_back)
    TextView mTvBack;

    private String type = "";//1:审核通过  2：驳回审核   3：解绑成功

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_result_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            //审核通过
            mTvLogo.setImageResource(R.mipmap.chenggong);
            mToolbarTitle.setText("审核结果");
            mTvStatus.setText("审核通过");
            mTvTips.setText("承播终端将自动下载并按规定时间播放");
            mLinearShenheLayout.setVisibility(View.VISIBLE);
            mTvBack.setVisibility(View.VISIBLE);
        } else if (type.equals("2")) {
            //驳回审核
            mTvLogo.setImageResource(R.mipmap.jieguo_bohui);
            mToolbarTitle.setText("审核结果");
            mTvStatus.setText("驳回申请");
            mTvTips.setText("承播终端将不会下载此作品，请知会被监管账户的操作人员。");
            mLinearShenheLayout.setVisibility(View.VISIBLE);
            mTvBack.setVisibility(View.VISIBLE);
        } else if (type.equals("3")) {
            //解绑成功
            mTvLogo.setImageResource(R.mipmap.chenggong);
            mToolbarTitle.setText("解绑结果");
            mTvStatus.setText("解绑成功");
            mTvTips.setText("解绑后，终端在接单后将立即开始下载播放。");
            mLinearShenheLayout.setVisibility(View.GONE);
            mTvBack.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.linear_shenhe_layout, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.linear_shenhe_layout:
                finish();
                break;
            case R.id.tv_back:
                Intent intent = new Intent(AuditResultActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                EventBus.getDefault().post(new Event.MainIndex(3));
                finish();
                break;
        }
    }
}
