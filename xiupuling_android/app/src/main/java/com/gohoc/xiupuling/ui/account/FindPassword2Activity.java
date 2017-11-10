package com.gohoc.xiupuling.ui.account;

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
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;


public class FindPassword2Activity extends BasicActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.register2_tips)
    TextView register2Tips;
    @BindView(R.id.register2_tips_ll)
    LinearLayout register2TipsLl;
    @BindView(R.id.register_phone_et)
    EditText registerPhoneEt;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    private  String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password2);
        ButterKnife.bind(this);
        toolbarTitle.setText("找回密码");
        setLisener();
        mobile=this.getIntent().getStringExtra("mobile");
        register2Tips.setText("短信验证码已经发送到手机"+ Utils.doMobile(mobile)+"，请查收！");
        setStatusColor(R.color.colorPrimary);
        setStatusColor(R.color.colorPrimary);
    }

    private void setLisener() {
        toolbarLeftTitle.setOnClickListener(this);
        sumbitButtonLl.setOnClickListener(this);
        registerPhoneEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        registerPhoneEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            verifysmscode();
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
        switch (v.getId())
        {
            case R.id.toolbar_left_title:
                this.finish();
                break;
            case R.id.sumbit_button_ll:
                verifysmscode();
        }
    }
    private void verifysmscode()
    {
        String smsCode=registerPhoneEt.getText().toString();
        if(TextUtils.isEmpty(smsCode))
        {
            Utils.toast(FindPassword2Activity.this,"请输入短信中的验证码！");
            return;
        }
        RxRetrofitClient.getInstance(FindPassword2Activity.this).verifysmscode(mobile, smsCode, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if(vCodeBenan.getCode()==1)
                {
                    startActivity(new Intent(FindPassword2Activity.this, FindPassword3Activity.class).putExtra("mobile",mobile));
                }else
                {
                    Utils.toast(FindPassword2Activity.this,vCodeBenan.getMessage());
                }
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(registerPhoneEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
