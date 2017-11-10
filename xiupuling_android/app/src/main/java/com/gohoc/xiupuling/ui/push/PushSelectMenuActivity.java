package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushSelectMenuActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.p1_rv1)
    RelativeLayout p1Rv1;
    @BindView(R.id.p1_rv2)
    RelativeLayout p1Rv2;
    @BindView(R.id.p3_rv1)
    RelativeLayout p3Rv1;
    private PushNormlBean pushNormlBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_select_menu);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("投放作品");
        pushNormlBean= (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        if (Credential.getInstance().getCurUser(this).getData().getShared_platform() == 1) {
            p1Rv1.setVisibility(View.VISIBLE);
        } else {
            p1Rv1.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.toolbar_left_title, R.id.p1_rv1, R.id.p1_rv2, R.id.p3_rv1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.p1_rv1:
                startActivity(new Intent(PushSelectMenuActivity.this,PushReqByMoneyActivity.class).putExtra("pushNormlBean", pushNormlBean));
                break;
            case R.id.p1_rv2:
                startActivity(new Intent(PushSelectMenuActivity.this,PushByGroupActivity.class).putExtra("pushNormlBean", pushNormlBean));
                break;
            case R.id.p3_rv1:
                startActivity(new Intent(PushSelectMenuActivity.this,PushByScanActivity.class).putExtra("pushNormlBean", pushNormlBean));
                break;
        }
    }
}
