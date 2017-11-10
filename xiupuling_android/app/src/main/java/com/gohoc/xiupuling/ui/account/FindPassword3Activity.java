package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;


public class FindPassword3Activity extends BasicActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.register2_tips)
    TextView register2Tips;
    @BindView(R.id.register3_password_et)
    EditText register3PasswordEt;
    @BindView(R.id.register3_password2_et)
    EditText register3Password2Et;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password3);
        ButterKnife.bind(this);
        setListener();
        toolbarTitle.setText("找回密码");
        mobile = this.getIntent().getStringExtra("mobile");
        setStatusColor(R.color.colorPrimary);
    }

    private void setListener() {
        toolbarLeftTitle.setOnClickListener(this);
        sumbitButtonLl.setOnClickListener(this);
        register3Password2Et.setImeOptions(EditorInfo.IME_ACTION_GO);
        register3Password2Et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            resetpwd();
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
            case R.id.toolbar_left_title:
                FindPassword3Activity.this.finish();
                break;
            case R.id.sumbit_button_ll:
                resetpwd();
                break;
        }
    }

    private void resetpwd() {
        if (TextUtils.isEmpty(register3PasswordEt.getText())) {
            Utils.toast(this, "新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(register3Password2Et.getText())) {
            Utils.toast(this, "确认密码不能为空");
            return;
        }
        if (!Utils.checkPW(register3PasswordEt.getText().toString())) {
            Utils.toast(this, "需为6~16位数字或字母、符号");
            return;
        }
        if (!register3PasswordEt.getText().toString().equals(register3Password2Et.getText().toString())) {
            Utils.toast(this, "新密码和确认密码不一致");
            return;
        }

        RxRetrofitClient.getInstance(FindPassword3Activity.this).resetpwd(mobile, register3PasswordEt.getText() + "", register3Password2Et.getText() + "", "android", Build.MODEL, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(FindPassword3Activity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {

                    ACache mCache = ACache.get(FindPassword3Activity.this);
                    mCache.remove("userBean");
                    startActivity(new Intent(FindPassword3Activity.this, LoginActivity.class).putExtra("mobile", mobile));
                    FindPassword3Activity.this.finish();
                }

            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(register3Password2Et, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
