package com.gohoc.xiupuling.ui.terminal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.wuxiaolong.androidutils.library.AppUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectShopNameActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.shop_name_et)
    ClearEditText shopNameEt;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop_name);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("门店信息");
        name = this.getIntent().getStringExtra("name") + "";
        shopNameEt.setText(name);
        shopNameEt.setFocusable(true);
        shopNameEt.setFocusableInTouchMode(true);
        shopNameEt.requestFocus();
        Utils.showSoftInputFromWindow(this,shopNameEt);
        shopNameEt.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    goback();

                    return true;
                }
                return false;
            }

        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) shopNameEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(shopNameEt, 0);
                           }

                       },
                998);
    }

    @OnClick({R.id.toolbar_left_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goback();
                break;
        }
    }

    private void goback() {
        if (Utils.getLength(shopNameEt.getText() + "") > 12) {
            Utils.toast(this, "最多只能允许24个英文字符或12个汉字");
        } else {
            this.setResult(RESULT_OK, new Intent().putExtra("name", shopNameEt.getText() + ""));
            SelectShopNameActivity.this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goback();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(shopNameEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
