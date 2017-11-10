package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 编辑字幕
* */
public class EditZimuActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_zimu)
    EditText mEtZimu;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editzimu_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        mToolbarTitle.setText("字幕内容");
        initListener();
        initDatas();
    }

    private void initListener() {

    }

    private void initDatas() {
        mEtZimu.setText(getIntent().getStringExtra("edit"));
        mEtZimu.setSelection(getIntent().getStringExtra("edit").length());
    }


    @OnClick({R.id.toolbar_left_title, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goback();
                break;
            case R.id.iv_delete:
                mEtZimu.setText("");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goback();
        }
        return false;
    }

    private void goback() {
        setResult(RESULT_OK,new Intent().putExtra("data",mEtZimu.getText().toString().trim()));
        finish();
    }
}
