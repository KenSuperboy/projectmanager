package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeMobileActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.phone_tx_tips)
    TextView phoneTxTips;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;

    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("手机号");
        mobile = this.getIntent().getStringExtra("mobile");
        phoneTxTips.setText(phoneTxTips.getText()+""+ Utils.doMobile(mobile));
    }

    @OnClick({R.id.toolbar_left_title, R.id.sumbit_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                ChangeMobileActivity.this.finish();
                break;
            case R.id.sumbit_button_ll:
                startActivity(new Intent(ChangeMobileActivity.this, ChangeMobile2Activity.class).putExtra("mobile", mobile + ""));
                finish();
                break;
        }
    }

}
