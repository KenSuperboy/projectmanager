package com.gohoc.xiupuling.ui.combinationpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.push.PushByGroupActivity;
import com.gohoc.xiupuling.ui.push.PushByScanActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 作品组合包投放选择
* */
public class CombinationPushSelectMenuActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.p1_rv2)
    LinearLayout mP1Rv2;
    @BindView(R.id.p3_rv1)
    LinearLayout mP3Rv1;
    private PushNormlBean pushNormlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination_push_select_menu);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        mToolbarTitle.setText("投放组合包");
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        /*if (Credential.getInstance().getCurUser(this).getData().getShared_platform() == 1) {
            p1Rv1.setVisibility(View.VISIBLE);
        } else {
            p1Rv1.setVisibility(View.GONE);
        }*/
    }


    @OnClick({R.id.toolbar_left_title,R.id.p1_rv2, R.id.p3_rv1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.p1_rv2:
                startActivity(new Intent(CombinationPushSelectMenuActivity.this, PushByGroupActivity.class).putExtra("pushNormlBean", pushNormlBean));
                break;
            case R.id.p3_rv1:
                startActivity(new Intent(CombinationPushSelectMenuActivity.this, PushByScanActivity.class).putExtra("pushNormlBean", pushNormlBean));
                break;
        }
    }
}
