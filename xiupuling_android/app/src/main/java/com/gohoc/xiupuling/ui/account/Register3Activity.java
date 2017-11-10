package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.DeviceUtils;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class Register3Activity extends BasicActivity implements View.OnClickListener {

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
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.fw_et)
    EditText fwEt;
    @BindView(R.id.fuwushagnll)
    LinearLayout fuwushagnll;
    @BindView(R.id.fuwushagnll2)
    LinearLayout fuwushagnll2;
    private String isShare = "1";
    private String mobile;
    private ACache mCache;
    private HashMap<String, UserBean> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        ButterKnife.bind(this);
        setListener();
        mobile = this.getIntent().getStringExtra("mobile");
        toolbarTitle.setText("注册");
        setStatusColor(R.color.colorPrimary);
    }

    private void setListener() {
        toolbarLeftTitle.setOnClickListener(this);
        sumbitButtonLl.setOnClickListener(this);
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isShare = isChecked == true ? "1" : "0";
                if (isChecked) {
                    fuwushagnll.setVisibility(View.VISIBLE);
                    fuwushagnll2.setVisibility(View.VISIBLE);
                } else {
                    fuwushagnll.setVisibility(View.GONE);
                    fuwushagnll2.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_title:
                Register3Activity.this.finish();
                break;
            case R.id.sumbit_button_ll:
                regist();
                break;

        }
    }

    private void regist() {
        if (TextUtils.isEmpty(register3PasswordEt.getText())) {
            Utils.toast(this, "请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(register3Password2Et.getText())) {
            Utils.toast(this, "确认密码不能为空");
            return;
        }
        if (!register3PasswordEt.getText().toString().equals(register3Password2Et.getText().toString())) {
            Utils.toast(this, "新密码与确认密码不一致");
            return;
        }

        if (register3PasswordEt.getText().toString().length() < 6 || register3PasswordEt.getText().toString().length() > 16) {
            Utils.toast(this, "密码由6～16位数字、大小写字母组成");
            return;
        }


        showProgressDialog("正在注册...");
        RxRetrofitClient.getInstance(Register3Activity.this).regist(DeviceUtils.getUid(Register3Activity.this), mobile, register3PasswordEt.getText() + "",
                register3Password2Et.getText() + "", "android", Build.MODEL, isShare, fwEt == null ? null : fwEt.getText() + "", new Observer<UserBean>() {
                    @Override
                    public void onCompleted() {
                        closeProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // loginButtonLl.setClickable(true);
                        closeProgressDialog();
                        Utils.toast(Register3Activity.this, "注册失败，请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }

                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        Utils.toast(Register3Activity.this, userBean.getMessage());
                        if (userBean.getCode() == 1) {
                            saveUser(userBean);
                        }

                    }
                }

        );


    }

    private void saveUser(UserBean userBean) {
        mCache = ACache.get(Register3Activity.this);
        userList = (LinkedHashMap<String, UserBean>) mCache.getAsObject("userList");
        if (userList == null)
            userList = new LinkedHashMap<String, UserBean>();

        if (userList.containsKey(userBean.getData().getUser_id()) || userList.containsValue(userBean.getData().getMobile()))
            userList.remove(userBean.getData().getUser_id());
        userList.put(userBean.getData().getUser_id(), userBean);
        mCache.remove("userList");
        mCache.put("userList", userList);
        mCache.put("userBean", userBean);
        mCache.put("analyzePasswordSecurityLevel", Utils.analyzePasswordSecurityLevelStr(Utils.analyzePasswordSecurityLevel(register3PasswordEt.getText() + "")));
        mCache.put("securityLevel", Utils.analyzePasswordSecurityLevel(register3PasswordEt.getText() + "") + "");

        Logger.d(userBean.getData().getMobile());
        Logger.d(userBean.getPassword() + "");
        Credential.getInstance().updateUserInfo(this);
        Credential.getInstance().getBaseInfo(this);
        EventBus.getDefault().post(new Event.ReLoginUpdate());
        startActivity(new Intent(Register3Activity.this, MainActivity.class));
        closeProgressDialog();
        this.finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(register3Password2Et, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
