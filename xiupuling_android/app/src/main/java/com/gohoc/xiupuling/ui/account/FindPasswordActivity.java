package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.VerificationCodeActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.wuxiaolong.androidutils.library.RegexUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindPasswordActivity extends BasicActivity implements Constant,View.OnClickListener {

    @BindView(R.id.register_phone_et)
    EditText registerPhoneEt;
    @BindView(R.id.register_phone_ll)
    LinearLayout registerPhoneLl;
    @BindView(R.id.getvf_button_ll)
    LinearLayout getvfButtonLl;

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.activity_register)
    RelativeLayout activityRegister;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        toolbarTitle.setText("找回密码");
        setListener();
        setStatusColor(R.color.colorPrimary);
    }

    private void setListener() {
        getvfButtonLl.setOnClickListener(this);
        toolbarLeftTitle.setOnClickListener(this);
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
            case R.id.getvf_button_ll:
                getVcode();
                break;
            case R.id.toolbar_left_title:
                FindPasswordActivity.this.finish();
                break;
            case R.id.agreement_con_tv:
                startActivity(new Intent(FindPasswordActivity.this,WebViewActivity.class).putExtra("url",NetConstant.BASE_USER_AGREEMENT));
                break;

        }
    }

    private void getVcode() {
        String mobile = registerPhoneEt.getText().toString();
        if (TextUtils.isEmpty(mobile) ) {
            Utils.toast(FindPasswordActivity.this,"手机号码不能为空！");
            return;
        }
        if (!Utils.isMobile(mobile)) {
            Utils.toast(FindPasswordActivity.this,"手机号码有误！");
            return;
        }
        startActivity(new Intent(FindPasswordActivity.this, VerificationCodeActivity.class).putExtra("mobile", mobile).putExtra("type", "2"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(registerPhoneEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
