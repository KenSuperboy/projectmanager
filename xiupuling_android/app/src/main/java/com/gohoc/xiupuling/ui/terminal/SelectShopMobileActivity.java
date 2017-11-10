package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.wuxiaolong.androidutils.library.RegexUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectShopMobileActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.shop_mobile_et)
    ClearEditText shopMobileEt;
    private String mobile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop_mobile);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        shopMobileEt.setFocusable(true);
        mobile=this.getIntent().getStringExtra("mobile")+"";
        toolbarTitle.setText("电话(公开)");
        shopMobileEt.setText(mobile);
        Utils.showSoftInputFromWindow(this,shopMobileEt);
        shopMobileEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        shopMobileEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            goback();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        goback();
    }

    private void goback() {
     this.setResult(RESULT_OK, new Intent().putExtra("mobile", shopMobileEt.getText() + ""));
      SelectShopMobileActivity.this.finish();

    /*    if((shopMobileEt.getText()+"").isEmpty())
        {
            Toast.makeText(SelectShopMobileActivity.this,"请输入店铺名",Toast.LENGTH_SHORT).show();
        }else
        {
            this.setResult(Constant.BaseConstant.RESULT_OK, new Intent().putExtra("name",shopMobileEt.getText()+""));
            SelectShopMobileActivity.this.finish();
        }*/
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
        if (ViewUtils.isTouchedViewOutSideView(shopMobileEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
