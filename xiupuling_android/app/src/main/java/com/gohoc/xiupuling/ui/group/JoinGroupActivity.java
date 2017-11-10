package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinGroupActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.jioin_group_scan_ll)
    LinearLayout jioinGroupScanLl;
    @BindView(R.id.jioin_group_search_ll)
    LinearLayout jioinGroupSearchLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("加入群组");
    }

    @OnClick({R.id.toolbar_left_title, R.id.jioin_group_scan_ll, R.id.jioin_group_search_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                JoinGroupActivity.this.finish();
                break;
            case R.id.jioin_group_scan_ll:
                startActivity(new Intent(JoinGroupActivity.this,JoinGroupScanActivity.class));
                break;
            case R.id.jioin_group_search_ll:
                startActivity(new Intent(JoinGroupActivity.this,JoinGroupSearchActivity.class));
                break;
        }
    }
}
