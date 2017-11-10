package com.gohoc.xiupuling.ui.terminal;

import android.os.Bundle;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.TerminalFlagBean;
import com.gohoc.xiupuling.ui.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlagePlayActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.count_tv)
    TextView countTv;
    @BindView(R.id.less_count)
    TextView lessCount;
    private  TerminalFlagBean terminalFlagBeanCurr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flage_play);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("标记说明");
        terminalFlagBeanCurr= (TerminalFlagBean) getIntent().getExtras().get("terminalFlagBean");
        if(terminalFlagBeanCurr!=null && terminalFlagBeanCurr.getData()!=null)
          lessCount.setText(terminalFlagBeanCurr.getData().getCycle_cnt()-terminalFlagBeanCurr.getData().getCycle_cnt_min());
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }
}
