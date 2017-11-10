package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.VerificationCodeActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.RxCountDown;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;

public class ChangSqSmsActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.sms_tips)
    TextView smsTips;
    @BindView(R.id.sms_phone_et)
    ClearEditText smsPhoneEt;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    private String mobile;
    private static boolean isStart = false;
    private final static int CHNGE_REQUEST_RESULT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_sq_sms);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("原手机验证");
        toolbarRight.setText("发送验证码");
        toolbarRight.setTextColor(getResources().getColor(R.color.sms_c));
        mobile = Credential.getInstance().getCurUser(this).getData().getMobile();
        if (mobile == null) {
            Utils.toast(this, "数据异常");
            finish();
        }
        smsTips.setText("原手机号码：" + Utils.doMobile(mobile));
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.sumbit_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                ChangSqSmsActivity.this.finish();
                break;
            case R.id.toolbar_right:
                if (!isStart)
                    startActivity(new Intent(ChangSqSmsActivity.this, VerificationCodeActivity.class).putExtra("mobile", mobile).putExtra("type", "100"));
                break;
            case R.id.sumbit_button_ll:
                verifysmscode();
                break;
        }
    }

    private void verifysmscode() {

        //  startActivity(new Intent(ChangSqSmsActivity.this, ChangeMobile4Activity.class).putExtra("mobile", mobile));


        String smsCode = smsPhoneEt.getText() + "";
        if (TextUtils.isEmpty(smsCode)) {
            Utils.toast(ChangSqSmsActivity.this, "请输入短信中的验证码！");
            return;
        }
        RxRetrofitClient.getInstance(ChangSqSmsActivity.this).verifysmscode2(mobile, smsCode, new Observer<VCodeBenan>() {
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
                    startActivityForResult(new Intent(ChangSqSmsActivity.this, SecurityQsActivity.class).putExtra("mobile", mobile).putExtra("type", 1), CHNGE_REQUEST_RESULT);
                } else {
                    Utils.toast(ChangSqSmsActivity.this, vCodeBenan.getMessage());
                }
            }
        });


    }

    public void setGetvcode() {
        if (!isStart) {
            RxCountDown.countdown(60)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            isStart = true;
                            Logger.d("开始计时");
                            toolbarRight.setText("60秒后重获");


                        }
                    })
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            Logger.d("计时完成");
                            toolbarRight.setText("发送验证码");
                            isStart = false;
                        }

                        @Override
                        public void onError(Throwable e) {

                            isStart = false;
                        }

                        @Override
                        public void onNext(Integer integer) {
                            toolbarRight.setText(integer + "秒后重获");
                            Logger.d("当前计时：" + integer);
                        }
                    });
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void VcodeEvent(Event.VcodeEvent message) {
        setGetvcode();
    }

    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(smsPhoneEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
