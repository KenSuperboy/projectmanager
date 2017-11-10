package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeSqActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.head)
    LinearLayout head;
    @BindView(R.id.sms_lv)
    LinearLayout smsLv;
    @BindView(R.id.sq_lv)
    LinearLayout sqLv;
    @BindView(R.id.tips)
    TextView tips;
    private final static int CHNGE_REQUEST_RESULT=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sq);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("更换安保问题");
    }

    @OnClick({R.id.toolbar_left_title, R.id.sms_lv, R.id.sq_lv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.sms_lv:
                startActivityForResult(new Intent(ChangeSqActivity.this,ChangSqSmsActivity.class),CHNGE_REQUEST_RESULT);
                break;
            case R.id.sq_lv:
                startActivityForResult(new Intent(ChangeSqActivity.this,CheckSqActivity.class),CHNGE_REQUEST_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode);
        finish();
    }
}
