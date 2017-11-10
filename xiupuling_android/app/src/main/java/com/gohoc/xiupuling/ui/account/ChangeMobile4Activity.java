package com.gohoc.xiupuling.ui.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.RxCountDown;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;

public class ChangeMobile4Activity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.change_moblie_old_et)
    EditText changeMoblieOldEt;
    @BindView(R.id.change_moblie_sms_et)
    EditText changeMoblieSmsEt;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    @BindView(R.id.vcode_tips_tv)
    TextView vcodeTipsTv;
    @BindView(R.id.getvcode_bt_lv)
    LinearLayout getvcodeBtLv;
    private static boolean isStart = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile4);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("原手机验证");
        changeMoblieOldEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0 && changeMoblieSmsEt.getText().toString().length() > 0) {
                    sumbitButtonLl.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    sumbitButtonLl.setClickable(true);
                } else {
                    sumbitButtonLl.setBackgroundColor(getResources().getColor(R.color.sms_ds));
                    sumbitButtonLl.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        changeMoblieSmsEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0 && changeMoblieOldEt.getText().toString().length() > 0) {
                    sumbitButtonLl.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    sumbitButtonLl.setClickable(true);
                } else {
                    sumbitButtonLl.setBackgroundColor(getResources().getColor(R.color.sms_ds));
                    sumbitButtonLl.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        changeMoblieSmsEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        changeMoblieSmsEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            updateCellphone();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.sumbit_button_ll, R.id.getvcode_bt_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                ChangeMobile4Activity.this.finish();
                break;
            case R.id.sumbit_button_ll:
                updateCellphone();
                break;
            case R.id.getvcode_bt_lv:
                if (!isStart)
                    getVcode();
                break;
        }
    }


    public void setGetvcode() {
        if (!isStart) {
            RxCountDown.countdown(60)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            isStart = true;
                            Logger.d("开始计时");

                            vcodeTipsTv.setClickable(false);
                            getvcodeBtLv.setBackgroundResource(R.drawable.get_vcode_bt_d_sp);
                            //  getvcodeBtLv.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                            vcodeTipsTv.setText("60s");

                        }
                    })
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            Logger.d("计时完成");
                            vcodeTipsTv.setText("获取验证码");
                            getvcodeBtLv.setClickable(true);
                            getvcodeBtLv.setBackgroundResource(R.drawable.get_vcode_bt_selector);
                            //  getvcodeBtLv.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                            isStart = false;
                        }

                        @Override
                        public void onError(Throwable e) {
                            vcodeTipsTv.setText("获取验证码");
                            getvcodeBtLv.setClickable(true);
                            getvcodeBtLv.setBackgroundResource(R.drawable.get_vcode_bt_selector);
                            //   getvcodeBtLv.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                            isStart = false;
                        }

                        @Override
                        public void onNext(Integer integer) {
                            vcodeTipsTv.setText(integer + "秒后重获");
                            Logger.d("当前计时：" + integer);
                        }
                    });
        }


    }

    private void getVcode() {
        if (!Utils.isMobile(changeMoblieOldEt.getText() + "")) {
            Utils.toast(this, "请输入正确的手机号码");
            return;
        }
        RxRetrofitClient.getInstance(ChangeMobile4Activity.this).getphonemsgcode(changeMoblieOldEt.getText() + "", "1", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(ChangeMobile4Activity.this, vCodeBenan.getMessage());
                Logger.d(vCodeBenan.getCode());
                if (vCodeBenan.getCode() == 1) {
                    setGetvcode();
                }
            }
        });
    }

    public void updateCellphone() {
        RxRetrofitClient.getInstance(ChangeMobile4Activity.this).updateCellphone(changeMoblieOldEt.getText() + "", changeMoblieSmsEt.getText() + "", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ChangeMobile4Activity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(ChangeMobile4Activity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    Credential.getInstance().updateUserInfo(ChangeMobile4Activity.this);
                    finish();
                }
            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(changeMoblieOldEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }

}
