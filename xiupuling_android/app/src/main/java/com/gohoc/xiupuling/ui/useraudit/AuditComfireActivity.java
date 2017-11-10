package com.gohoc.xiupuling.ui.useraudit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.VerificationCodeActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 监管验证
* */
public class AuditComfireActivity extends BasicActivity{


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    /*@BindView(R.id.toolbar)
    Toolbar mToolbar;*/
    @BindView(R.id.register_head)
    LinearLayout mRegisterHead;
    @BindView(R.id.tv_telephone)
    TextView mTvTelephone;
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

    private String number="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_comfire_layout);
        ButterKnife.bind(this);
        initView();
        setListener();
        setStatusColor(R.color.colorPrimary);
        //EventBus.getDefault().register(this);
    }

    private void initView()
    {
        number=getIntent().getStringExtra("number");
        mToolbarTitle.setText("监管人验证");
        mToolbarRight.setText("发送验证码");
        mToolbarRight.setTextColor(getResources().getColor(R.color.yellow));
        mTvTelephone.setText(Utils.doMobile(number)+"");
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshAuditUserListEvent(Event.RefreshAuditUserEvent_1 message) {
        LogUtil.d("监管账户列表111111111");
        finish();
    }*/

    private void setListener() {
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
                    mIvDelete.setVisibility(View.INVISIBLE);
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

    private void getVcode() {
        if (number.isEmpty()) {
            Utils.toast(AuditComfireActivity.this, "手机号码不能为空！");
            return;
        }
        if (!Utils.isMobile(number)) {
            Utils.toast(AuditComfireActivity.this, "手机号码有误！");
            return;
        }

        startActivity(new Intent(AuditComfireActivity.this, VerificationCodeActivity.class).putExtra("mobile", number).putExtra("type", "4"));
    }


    //验证验证码
    private void verifysmscode() {
        String smsCode = mRegisterPhoneEt.getText().toString();
        if (TextUtils.isEmpty(smsCode)) {
            Utils.toast(AuditComfireActivity.this, "请输入短信中的验证码");
            return;
        }
        RxRetrofitClient.getInstance(AuditComfireActivity.this).verifysmscode(number, smsCode, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AuditComfireActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    unbindAuditUser();
                } else {
                    Utils.toast(AuditComfireActivity.this, vCodeBenan.getMessage());
                }
            }
        });
    }


    //解绑用户
    private void unbindAuditUser() {
        RxRetrofitClient.getInstance(AuditComfireActivity.this).unbindAuditUser( new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AuditComfireActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshAuditUserStatusEvent());
                    Intent intent=new Intent(AuditComfireActivity.this,AuditResultActivity.class);
                    intent.putExtra("type","3");
                    startActivity(intent);
                    finish();
                } else {
                    Utils.toast(AuditComfireActivity.this, vCodeBenan.getMessage());
                }
            }
        });
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
        //EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.iv_delete, R.id.getvf_button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                getVcode();
                break;
            case R.id.iv_delete:
                mRegisterPhoneEt.setText("");
                break;
            case R.id.getvf_button_ll:
                verifysmscode();
                break;
        }
    }
}
