package com.gohoc.xiupuling.ui.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SettingPasswordActivity extends BasicActivity {

    Button delBt3;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.setting_password_psd_old_et)
    ClearEditText settingPasswordPsdOldEt;
    @BindView(R.id.setting_password_psd_new_et)
    ClearEditText settingPasswordPsdNewEt;
    @BindView(R.id.setting_password_psd_new2_et)
    ClearEditText settingPasswordPsdNew2Et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("修改密码");

        settingPasswordPsdNew2Et.setImeOptions(EditorInfo.IME_ACTION_GO);
        settingPasswordPsdNew2Et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            settingPassword();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.sumbit_button_ll, R.id.toolbar_left_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sumbit_button_ll:
                settingPassword();
                break;
            case R.id.toolbar_left_title:
                SettingPasswordActivity.this.finish();
                break;
        }
    }

    private void settingPassword() {
        sumbitButtonLl.setClickable(false);
        if (TextUtils.isEmpty(settingPasswordPsdOldEt.getText())) {
            sumbitButtonLl.setClickable(true);
            Utils.toast(this, "请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(settingPasswordPsdNewEt.getText())) {
            sumbitButtonLl.setClickable(true);
            Utils.toast(this, "请输入新密码");
            return;
        }

        if (!Utils.checkPW(settingPasswordPsdNewEt.getText().toString())) {
            sumbitButtonLl.setClickable(true);
            Utils.toast(this, "需为6~16位数字或字母、符号");
            return;
        }
        if (TextUtils.isEmpty(settingPasswordPsdNew2Et.getText())) {
            sumbitButtonLl.setClickable(true);
            Utils.toast(this, "请输入确认密码");
            return;
        }


        if (!Utils.checkPW(settingPasswordPsdNewEt.getText() + "")) {
            sumbitButtonLl.setClickable(true);
            Utils.toast(this, "请输入正确的新密码");
            return;
        }

        if (!settingPasswordPsdNewEt.getText().toString().equals(settingPasswordPsdNew2Et.getText().toString())) {
            sumbitButtonLl.setClickable(true);
            Utils.toast(this, "新密码和确认密码不一致");
            return;
        }

        RxRetrofitClient.getInstance(SettingPasswordActivity.this).updatepwd(settingPasswordPsdOldEt.getText() + "", settingPasswordPsdNewEt.getText() + "", settingPasswordPsdNew2Et.getText() + "", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                sumbitButtonLl.setClickable(true);
                Utils.toast(SettingPasswordActivity.this, "修改失败，请检查网络是否正常");

                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                sumbitButtonLl.setClickable(true);
                Utils.toast(SettingPasswordActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    Utils.logout(SettingPasswordActivity.this, false);
                }

            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(settingPasswordPsdNew2Et, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }

}
