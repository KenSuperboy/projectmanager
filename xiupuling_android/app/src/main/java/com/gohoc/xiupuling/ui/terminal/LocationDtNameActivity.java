package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationDtNameActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.location_dt_et)
    ClearEditText locationDtEt;
    private String address,type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_dt_name);
        ButterKnife.bind(this);
        toolbarTitle.setText("详细地址");
        toolbarRight.setText("完成");
        setStatusColor(R.color.colorPrimary);
        address = this.getIntent().getStringExtra("address");
        locationDtEt.setText(address + "");
        type=getIntent().getStringExtra("type");
        if(type.equals("1")){
            toolbarRight.setVisibility(View.GONE);
            locationDtEt.setClearIconVisible(false);
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_return_left);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            toolbarLeftTitle.setCompoundDrawables(drawable, null, null, null);
            toolbarLeftTitle.setText("返回");
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                goback();
                break;
        }
    }

    private void goback() {
        this.setResult(RESULT_OK, new Intent().putExtra("name", locationDtEt.getText() + ""));
        LocationDtNameActivity.this.finish();
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
        if (ViewUtils.isTouchedViewOutSideView(locationDtEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
