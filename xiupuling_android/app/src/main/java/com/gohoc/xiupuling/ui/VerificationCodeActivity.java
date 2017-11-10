package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.account.FindPassword2Activity;
import com.gohoc.xiupuling.ui.account.Register2Activity;
import com.gohoc.xiupuling.ui.useraudit.AddauditUserActivity2;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class VerificationCodeActivity extends BasicActivity implements View.OnClickListener {

    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.verification_tips)
    TextView verificationTips;
    @BindView(R.id.verification_confim_lv)
    LinearLayout verificationConfimLv;
    @BindView(R.id.verification_cancel_lv)
    LinearLayout verificationCancelLv;
    @BindView(R.id.verification_tv)
    TextView verificationTv;
    private String vcCode;
    private String mobile;
    private String type;//3:账户监管  4:销毁本身自己

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        ButterKnife.bind(this);
        mobile = this.getIntent().getStringExtra("mobile");
        type = this.getIntent().getStringExtra("type");//3:账户监管  4:销毁本身自己
        if (TextUtils.isEmpty(type))
            type = "1";
        setRandom();
        Logger.d(mobile);
        setLisener();
        setStatusColor(R.color.transparent);
    }

    private void setLisener() {
        verificationTips.setOnClickListener(this);
        verificationConfimLv.setOnClickListener(this);
        verificationCancelLv.setOnClickListener(this);
        verificationTv.setOnClickListener(this);
        verificationEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        verificationEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
            case R.id.verification_confim_lv:
                getVcode();
                break;
            case R.id.verification_cancel_lv:
                VerificationCodeActivity.this.finish();
                break;
            case R.id.verification_tv:
            case R.id.verification_tips:
                setRandom();
                break;
        }
    }


    private void getVcode() {
        String vcCodeInput = verificationEt.getText().toString();
        if (!vcCode.equalsIgnoreCase(vcCodeInput)) {
            Utils.toast(this, "您输入的图形验证码有误！");
            //setRandom();
            return;
        }

        if (type.equals("100")) {
            RxRetrofitClient.getInstance(this).getphonemsgcode(mobile + "", "0", new Observer<VCodeBenan>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Logger.e(e.toString());
                }

                @Override
                public void onNext(VCodeBenan vCodeBenan) {

                    Logger.d(vCodeBenan.getCode());
                    if (vCodeBenan.getCode() == 1) {
                        EventBus.getDefault().post(new Event.VcodeEvent(true));
                    } else
                        Utils.toast(VerificationCodeActivity.this, vCodeBenan.getMessage());
                    finish();
                }
            });

        } else {
            RxRetrofitClient.getInstance(VerificationCodeActivity.this).getMsgCode(mobile, vcCodeInput, type, new Observer<VCodeBenan>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    setRandom();
                    Logger.e(e.toString());
                }

                @Override
                public void onNext(VCodeBenan vCodeBenan) {
                    Logger.d(vCodeBenan.getCode());
                    if (vCodeBenan.getCode() == 1) {
                        Utils.toast(VerificationCodeActivity.this, "发送短信验证码成功");
                        if (type.equals("1")) {
                            startActivity(new Intent(VerificationCodeActivity.this, Register2Activity.class).putExtra("mobile", mobile));
                        } else if (type.equals("2")){
                            startActivity(new Intent(VerificationCodeActivity.this, FindPassword2Activity.class).putExtra("mobile", mobile));
                        }else if (type.equals("3")){
                            startActivity(new Intent(VerificationCodeActivity.this, AddauditUserActivity2.class).putExtra("mobile", mobile));
                        }else if (type.equals("4")){

                        }
                    } else {
                        Utils.toast(VerificationCodeActivity.this, vCodeBenan.getMessage());
                        setRandom();
                    }
                    finish();
                }
            });
        }


    }

    private void setRandom() {
        RxRetrofitClient.getInstance(VerificationCodeActivity.this).getauthcode(mobile, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    vcCode = vCodeBenan.getMessage() + "";
                    verificationTv.setText(vcCode);
                }

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(verificationEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
