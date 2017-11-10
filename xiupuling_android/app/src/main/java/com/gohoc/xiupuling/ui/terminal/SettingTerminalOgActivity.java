package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingTerminalOgActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_iv1)
    ImageView rightIv1;
    @BindView(R.id.hengping_ll)
    LinearLayout hengpingLl;
    @BindView(R.id.right_iv2)
    ImageView rightIv2;
    @BindView(R.id.shuping_ll)
    LinearLayout shupingLl;
    @BindView(R.id.hp_ll)
    LinearLayout hpLl;
    @BindView(R.id.sp_ll)
    LinearLayout spLl;
    private int a = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_terminal_og);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("放置方式");
        try {
            a = getIntent().getIntExtra("orientation", 100);
            if (a == 0) {//横屏
                rightIv1.setVisibility(View.VISIBLE);
                rightIv2.setVisibility(View.INVISIBLE);
            } else if(a == 1){//竖屏
                rightIv1.setVisibility(View.INVISIBLE);
                rightIv2.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.hengping_ll, R.id.shuping_ll,R.id.hp_ll, R.id.sp_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                SettingTerminalOgActivity.this.finish();
                break;
            case R.id.hengping_ll:
                rightIv1.setVisibility(View.VISIBLE);
                rightIv2.setVisibility(View.INVISIBLE);
                a = 0;
                setResult(RESULT_OK, new Intent().putExtra("orientation", a));
                SettingTerminalOgActivity.this.finish();
                break;
            case R.id.shuping_ll:
                rightIv1.setVisibility(View.INVISIBLE);
                rightIv2.setVisibility(View.VISIBLE);
                a = 1;
                setResult(RESULT_OK, new Intent().putExtra("orientation", a));
                SettingTerminalOgActivity.this.finish();
                break;
            case R.id.hp_ll:
                rightIv1.setVisibility(View.VISIBLE);
                rightIv2.setVisibility(View.INVISIBLE);
                a = 0;
                setResult(RESULT_OK, new Intent().putExtra("orientation", a));
                SettingTerminalOgActivity.this.finish();
                break;
            case R.id.sp_ll:
                rightIv1.setVisibility(View.INVISIBLE);
                rightIv2.setVisibility(View.VISIBLE);
                a = 1;
                setResult(RESULT_OK, new Intent().putExtra("orientation", a));
                SettingTerminalOgActivity.this.finish();
                break;

        }
    }


}
