package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.VerificationCodeActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BasicActivity implements View.OnClickListener {

    @BindView(R.id.register_phone_et)
    EditText registerPhoneEt;
    @BindView(R.id.register_phone_ll)
    LinearLayout registerPhoneLl;
    @BindView(R.id.getvf_button_ll)
    LinearLayout getvfButtonLl;
    @BindView(R.id.register_agreement_tb)
    ToggleButton registerAgreementTb;
    @BindView(R.id.register_agreement_tv)
    TextView registerAgreementTv;
    @BindView(R.id.register_agreement_ll)
    LinearLayout registerAgreementLl;
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.activity_register)
    RelativeLayout activityRegister;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.agreement_con_tv)
    TextView agreementConTv;
    private SystemInfoBean sb;
    private ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        toolbarTitle.setText("注册");
        setListener();
        setStatusColor(R.color.colorPrimary);
        mCache = ACache.get(this);
        sb = (SystemInfoBean) mCache.getAsObject("SystemInfoBean");
    }

    private void setListener() {
        getvfButtonLl.setOnClickListener(this);
        registerAgreementTv.setOnClickListener(this);
        toolbarLeftTitle.setOnClickListener(this);
        agreementConTv.setOnClickListener(this);
        registerAgreementTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getvfButtonLl.setBackgroundColor(getResources().getColor(R.color.colorLoginbkOn));
                    getvfButtonLl.setClickable(true);
                } else {
                    getvfButtonLl.setBackgroundColor(getResources().getColor(R.color.colorLoginbkDf));
                    getvfButtonLl.setClickable(false);
                }
            }
        });

        registerPhoneEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        registerPhoneEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            getVcode();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_agreement_tv:
                registerAgreementTb.setChecked(!registerAgreementTb.isChecked());
                break;
            case R.id.getvf_button_ll:
                getVcode();
                break;
            case R.id.toolbar_left_title:
                RegisterActivity.this.finish();
                break;
            case R.id.agreement_con_tv:
                startActivity(new Intent(RegisterActivity.this, WebViewActivity.class).putExtra("url", BaseConstant.USER_DOUCMENT).putExtra("title", "秀铺令用户协议"));
                break;
        }
    }

    private void getVcode() {
        String mobile = registerPhoneEt.getText().toString();

        if (mobile.isEmpty()) {
            Utils.toast(RegisterActivity.this, "手机号码不能为空！");
            return;
        }
        if (!Utils.isMobile(mobile)) {
            Utils.toast(RegisterActivity.this, "手机号码有误！");
            return;
        }

        startActivity(new Intent(RegisterActivity.this, VerificationCodeActivity.class).putExtra("mobile", mobile));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(registerPhoneEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
