package com.gohoc.xiupuling.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 增加vip码
* */
public class AddVipCodeActivity extends BasicActivity{


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_vip)
    ClearEditText mEtVip;
    @BindView(R.id.activity_register)
    LinearLayout mActivityRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vip_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        mToolbarTitle.setText("添加VIP码");
    }

    private void addvip() {
        String vip_code = mEtVip.getText().toString();
        if (TextUtils.isEmpty(vip_code)) {
            Utils.toast(AddVipCodeActivity.this, "请输入VIP码");
            return;
        }
        RxRetrofitClient.getInstance(AddVipCodeActivity.this).addVip(vip_code, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddVipCodeActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    finish();
                } else {
                    Utils.toast(AddVipCodeActivity.this, vCodeBenan.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(mEtVip, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                addvip();
                break;
        }
    }
}
