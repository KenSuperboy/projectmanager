package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.TerminalFlagBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class FlagInstructionActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.start_flag_itr)
    LinearLayout startFlagItr;
    @BindView(R.id.palyer_flage)
    LinearLayout palyerFlage;
    @BindView(R.id.update_flage)
    LinearLayout updateFlage;
    private String terminalId;
    private TerminalFlagBean terminalFlagBeanCurr;
    private int leve=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_instruction);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("标记说明");
        terminalId = getIntent().getStringExtra("terminalId");
        leve=getIntent().getIntExtra("leve",1);
        terminalFlagStatus(terminalId);
    }

    @OnClick({R.id.toolbar_left_title, R.id.start_flag_itr, R.id.palyer_flage, R.id.update_flage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.start_flag_itr:
                startActivity(new Intent(FlagInstructionActivity.this,FlagStarActivity.class).putExtra("leve",leve));
                break;
            case R.id.palyer_flage:
                startActivity(new Intent(FlagInstructionActivity.this,FlagePlayActivity.class).putExtra("terminalFlagBean",terminalFlagBeanCurr));
                break;
            case R.id.update_flage:
                startActivity(new Intent(FlagInstructionActivity.this,FlagUpdateActivity.class).putExtra("terminalFlagBean",terminalFlagBeanCurr));
                break;
        }
    }

    private void terminalFlagStatus(String terminal_id)
    {
        RxRetrofitClient.getInstance(this).terminalFlagStatus(terminal_id, new Observer<TerminalFlagBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(FlagInstructionActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TerminalFlagBean terminalFlagBean) {
               if(terminalFlagBean.getCode()==1)
               {
                   terminalFlagBeanCurr=terminalFlagBean;
               }
            }
        });
    }
}
