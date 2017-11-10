package com.gohoc.xiupuling.ui.useraudit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.VerificationCodeActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddauditUserActivity1 extends BasicActivity implements View.OnClickListener {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    /*@BindView(R.id.toolbar)
    Toolbar mToolbar;*/
    @BindView(R.id.register_head)
    LinearLayout mRegisterHead;
    @BindView(R.id.register_phone_et)
    EditText mRegisterPhoneEt;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.register_phone_ll)
    LinearLayout mRegisterPhoneLl;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.getvf_button_ll)
    LinearLayout mGetvfButtonLl;
    @BindView(R.id.activity_register)
    RelativeLayout mActivityRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audituser_layout_1);
        ButterKnife.bind(this);
        mToolbarTitle.setText("添加被监管账户");
        setListener();
        setStatusColor(R.color.colorPrimary);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshAuditUserListEvent(Event.RefreshAuditUserEvent_1 message) {
        LogUtil.d("监管账户列表111111111");
        finish();
    }

    private void setListener() {
        mGetvfButtonLl.setOnClickListener(this);
        mToolbarLeftTitle.setOnClickListener(this);
        mIvDelete.setOnClickListener(this);
        mRegisterPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mIvDelete.setVisibility(View.VISIBLE);
                } else {
                    mIvDelete.setVisibility(View.GONE);
                }
            }
        });
        mRegisterPhoneEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        mRegisterPhoneEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                AddauditUserActivity1.this.finish();
                break;
            case R.id.iv_delete:
                mRegisterPhoneEt.setText("");
                break;
        }
    }

    private void getVcode() {
        String mobile = mRegisterPhoneEt.getText().toString();

        if (mobile.isEmpty()) {
            Utils.toast(AddauditUserActivity1.this, "手机号码不能为空！");
            return;
        }
        if (!Utils.isMobile(mobile)) {
            Utils.toast(AddauditUserActivity1.this, "手机号码有误！");
            return;
        }

        startActivity(new Intent(AddauditUserActivity1.this, VerificationCodeActivity.class).putExtra("mobile", mobile).putExtra("type", "3"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(mRegisterPhoneEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
