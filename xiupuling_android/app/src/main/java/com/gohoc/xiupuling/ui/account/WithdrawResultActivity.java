package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawResultActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.time_tv)
    TextView timeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_result);
        ButterKnife.bind(this);
        toolbarTitle.setText("提现申请");
        setStatusColor(R.color.colorPrimary);
        timeTv.setText(timeTv.getText() + Utils.formatDate(Utils.addDate(Utils.getCurrDate(), 7), "MM月dd日") + "前到账");
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        goback();
    }

    private void goback() {
        startActivity(new Intent(WithdrawResultActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goback();
        }
        return false;
    }

}
