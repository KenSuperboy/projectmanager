package com.gohoc.xiupuling.ui.terminal;

import android.os.Bundle;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.TerminalFlagBean;
import com.gohoc.xiupuling.ui.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlagUpdateActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.count_tv)
    TextView countTv;
    private TerminalFlagBean terminalFlagBeanCurr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_update);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("标记说明");
        terminalFlagBeanCurr= (TerminalFlagBean) getIntent().getExtras().get("terminalFlagBean");
        if(terminalFlagBeanCurr!=null && terminalFlagBeanCurr.getData()!=null)
          countTv.setText(terminalFlagBeanCurr.getData().getCycle_cnt());
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }
}
