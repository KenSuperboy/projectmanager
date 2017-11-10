package com.gohoc.xiupuling.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MsgBean;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MsgDtActivity extends BasicActivity {
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
     private MsgBean.DataBean dataBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_dt);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("我的消息");
        dataBean= (MsgBean.DataBean) getIntent().getExtras().get("msg");
        titleTv.setText(dataBean.getTitle()+"");
        timeTv.setText(Utils.timestampToDateStr(dataBean.getMsg_version(), "MM-dd HH:mm"));
        contentTv.setText(dataBean.getContent()+"");

    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }
}
