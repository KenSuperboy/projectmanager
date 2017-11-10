package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.Event;
import com.wuxiaolong.androidutils.library.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 新建联动包
* */
public class LiandongbaoSucceedActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_shuoming)
    TextView mTvShuoming;
    @BindView(R.id.tv_name)
    TextView tv_name;

    private String startdate="",type="",name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duopingliandong_toufangchenggong_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);

        initData();
    }

    private void initData() {
        startdate = TimeUtil.getCurrentTime("yyyy-MM-dd");
        mTvTime.setText(startdate+"发布成功");
        mToolbarTitle.setText("投放成功");
        type=getIntent().getStringExtra("type");
        name=getIntent().getStringExtra("name");
        if(type.equals("1")){
            mToolbarLeftTitle.setText("新信息");
        }
        if(!TextUtils.isEmpty(name)){
            tv_name.setText(name);
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.tv_shuoming})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                if(type.equals("1")){
                    EventBus.getDefault().post(new Event.FinishLiandongDetailEvent());
                    EventBus.getDefault().post(new Event.FinishLiandongListEvent());
                }
                finish();
                break;
            case R.id.tv_shuoming:
                startActivity(new Intent(LiandongbaoSucceedActivity.this, WebViewActivity.class).putExtra("url", "").putExtra("title", "投放说明"));
                break;
        }
    }
}
