package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 联动包选择定投关系
* */
public class LiandongbaoDdingtouChooseActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.linear_add_layout)
    LinearLayout mLinearAddLayout;
    @BindView(R.id.linear_choose_layout)
    LinearLayout mLinearChooseLayout;

    private String linkid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_dingtou_choose_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);

        mToolbarTitle.setHint("联动定投");
        mToolbarTitle.setTextColor(mContext.getResources().getColor(R.color.white));

        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initView() {

    }

    private void initData() {
        linkid=getIntent().getStringExtra("linkid");
    }

    @OnClick({R.id.toolbar_left_title, R.id.linear_add_layout, R.id.linear_choose_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.linear_add_layout:
                Intent intent=new Intent(LiandongbaoDdingtouChooseActivity.this,LiandongbaoXinjianActivity.class);
                intent.putExtra("linkid",linkid);
                startActivity(intent);
                break;
            case R.id.linear_choose_layout:
                Intent intent_history=new Intent(LiandongbaoDdingtouChooseActivity.this,LiandongbaoHistoryToufangListActivity.class);
                intent_history.putExtra("linkid",linkid);
                startActivity(intent_history);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SetLiandongDingtouChooseEvent(Event.SetLiandongDingtouChooseEvent message) {
        LogUtil.d("选择列表：");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
