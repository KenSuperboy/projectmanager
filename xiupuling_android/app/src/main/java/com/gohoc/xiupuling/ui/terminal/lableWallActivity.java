package com.gohoc.xiupuling.ui.terminal;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ModuleBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class lableWallActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RelativeLayout list;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    private String terminal_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lable_wall);
        ButterKnife.bind(this);
        toolbarTitle.setText("标签墙管理");
        setStatusColor(R.color.colorPrimary);
        terminal_id=getIntent().getStringExtra("terminal_id");
        terminalTagWallList();
    }

    @OnClick({R.id.toolbar_left_title, R.id.no_data_lv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.no_data_lv:
                break;
        }
    }

    private void terminalTagWallList() {

        RxRetrofitClient.getInstance(lableWallActivity.this).terminalTagWallList(terminal_id, null, null, new Observer<ModuleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //  Toast.makeText(TerminalPlayerSettingActivity.this, "请检查网络是否正常", Toast.LENGTH_SHORT).show();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ModuleBean moduleBean) {
                if (moduleBean.getCode() == 1) {

                }

            }
        });
    }
}
