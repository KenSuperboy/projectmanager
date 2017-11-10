package com.gohoc.xiupuling.ui.useraudit;

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

/*
* 选择类目
* */
public class AuditTypeChooseActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.linear_zhongdaun_layout)
    LinearLayout mLinearZhongdaunLayout;
    @BindView(R.id.linear_zuoping_layout)
    LinearLayout mLinearZuopingLayout;

    private String user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audittype_choose_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);

        mToolbarTitle.setText("选择类目");
        mToolbarTitle.setTextColor(mContext.getResources().getColor(R.color.white));

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        user_id=getIntent().getStringExtra("user_id");
    }

    @OnClick({R.id.toolbar_left_title, R.id.linear_zhongdaun_layout, R.id.linear_zuoping_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.linear_zhongdaun_layout:
                startActivity(new Intent(AuditTypeChooseActivity.this,AuditTerminalListActivity.class).putExtra("user_id",user_id));
                break;
            case R.id.linear_zuoping_layout:
                startActivity(new Intent(AuditTypeChooseActivity.this,AuditWorksListActivity.class).putExtra("user_id",user_id));
                break;
        }
    }
}
