package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.LoginListAdapter;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.SplashActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.DeviceUtils;
import com.gohoc.xiupuling.utils.DropEditText;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.ResponseBody;
import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class LoginActivity extends BasicActivity implements View.OnClickListener {

    @BindView(R.id.password_tx)
    EditText passwordTx;
    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;
    @BindView(R.id.remober_pssword_tv)
    TextView remoberPsswordTv;
    @BindView(R.id.login_button_ll)
    LinearLayout loginButtonLl;
    @BindView(R.id.find_password_tx)
    TextView findPasswordTx;
    @BindView(R.id.reg_tx)
    TextView regTx;
    @BindView(R.id.remober_pssword_ll)
    LinearLayout remoberPsswordLl;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    @BindView(R.id.lgoin_phone_ll)
    LinearLayout lgoinPhoneLl;
    @BindView(R.id.phone_tx)
    DropEditText phoneTx;
    @BindView(R.id.account_user_head_iv)
    CircleImageView accountUserHeadIv;
    private PopupWindow mListPop;
    private ArrayList<UserBean> userListArr = new ArrayList<UserBean>();
    private ACache mCache;
    private LinkedHashMap<String, UserBean> userList;
    private UserBean currUserBean;//最后一次登录用户


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mCache = ACache.get(LoginActivity.this);
        initListPopupWindow();
        setListener();
    }


    private void setListener() {
        remoberPsswordTv.setOnClickListener(this);
        loginButtonLl.setOnClickListener(this);
        regTx.setOnClickListener(this);

        findPasswordTx.setOnClickListener(this);
        phoneTx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lgoinPhoneLl.setBackground(getResources().getDrawable(R.drawable.login_input_sp_on));
                    passwordTx.setBackground(getResources().getDrawable(R.drawable.login_input_sp));

                    setDrawabout(phoneTx, R.mipmap.icon_phone_xuanzhong, -1);
                    setDrawabout(passwordTx, R.mipmap.icon_password_unchecked, -1);
                    // setDrawabout(phoneTx,R.drawable.blue_input_sp);
                } else {
                    lgoinPhoneLl.setBackground(getResources().getDrawable(R.drawable.login_input_sp));
                    setDrawabout(phoneTx, R.mipmap.icon_phone_noxuanzhong, -1);
                }
            }
        });
        passwordTx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    passwordTx.setBackground(getResources().getDrawable(R.drawable.login_input_sp_on));
                    lgoinPhoneLl.setBackground(getResources().getDrawable(R.drawable.login_input_sp));

                    setDrawabout(passwordTx, R.mipmap.icon_password_selected, -1);
                    setDrawabout(phoneTx, R.mipmap.icon_phone_noxuanzhong, -1);
                    // setDrawabout(phoneTx,R.drawable.blue_input_sp);
                } else {
                    passwordTx.setBackground(getResources().getDrawable(R.drawable.login_input_sp));
                    setDrawabout(passwordTx, R.mipmap.icon_password_unchecked, -1);
                }
            }
        });
        passwordTx.setImeOptions(EditorInfo.IME_ACTION_GO);
        passwordTx.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            login(phoneTx.getText().toString(), passwordTx.getText().toString());
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
            case R.id.remober_pssword_tv:
                toggleButton.setChecked(!toggleButton.isChecked());
                break;
            case R.id.login_button_ll:
                login(phoneTx.getText().toString(), passwordTx.getText().toString());
                break;
            case R.id.reg_tx:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.find_password_tx:
                startActivity(new Intent(LoginActivity.this, FindPasswordActivity.class));
                break;
/*            case R.id.arr_list_tv:
                if (mListPop != null) {
                    if (mListPop.isShowing()) {
                        mListPop.dismiss();
                    } else {
                        mListPop.show();
                    }


                }
                break;*/
        }
    }

    private void login(final String user, String pass) {
        loginButtonLl.setClickable(false);
        showProgressDialog("正在登陆...");
        RxRetrofitClient.getInstance(LoginActivity.this).login(null, DeviceUtils.getUid(LoginActivity.this), user, pass, "android", Build.MODEL, new Observer<UserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                loginButtonLl.setClickable(true);
                closeProgressDialog();
                Utils.toast(LoginActivity.this, "登录失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBean userBean) {

                if (userBean.getCode() == 1) {
                    mCache.put("userBean", userBean);
                    getUserInfo(userBean);
                } else if (userBean.getMessage().equals("java.lang.Exception: 密码错误次数超限，暂时不能登录")) {
                    loginButtonLl.setClickable(true);
                    closeProgressDialog();
                    Utils.toast(LoginActivity.this, "密码错误次数超限,几小时内不能登录");
                } else if (userBean.getMessage().equals("密码验证失败")) {
                    loginButtonLl.setClickable(true);
                    closeProgressDialog();
                    passwordTx.setText("");
                    Utils.toast(LoginActivity.this, userBean.getMessage());
                } else {
                    loginButtonLl.setClickable(true);
                    closeProgressDialog();
                    Utils.toast(LoginActivity.this, userBean.getMessage());
                }


            }
        });
    }

    private void saveUser(UserBean userBean, UserBaseBean userBaseBean) {

        mCache.put("securityLevel", Utils.analyzePasswordSecurityLevel(passwordTx.getText() + "") + "");
        if (toggleButton.isChecked())
            userBean.setPassword(passwordTx.getText().toString() + "".trim());
        else
            userBean.setPassword("");
        if (userList.containsKey(userBean.getData().getUser_id()) || userList.containsValue(phoneTx.getText().toString().trim())) {
            userList.remove(userBean.getData().getUser_id());
        }
        userList.put(userBean.getData().getUser_id(), userBean);
        mCache.put("userList", userList);
        Logger.d(userList);
        Credential.getInstance().setCurUser(userBaseBean);
        EventBus.getDefault().post(new Event.ReLoginUpdate());
        getBaseInfo();
        closeProgressDialog();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        this.finish();
    }

    private void initListPopupWindow() {
        //Logger.d(mCache.getAsObject("userList"));
        userList = (LinkedHashMap<String, UserBean>) mCache.getAsObject("userList");
        if (userList == null)
            userList = new LinkedHashMap<String, UserBean>();
        if (null == userList || userList.size() < 1)
            return;
        Iterator iter = userList.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            UserBean userBean = (UserBean) entry.getValue();
            userListArr.add(userBean);
        }
        //取最后一次登录的用户
        currUserBean = userListArr.get(userListArr.size() - 1);
        if (null != currUserBean) {
            phoneTx.setText(currUserBean.getData().getMobile());
            if (!TextUtils.isEmpty(currUserBean.getPassword())) {
                passwordTx.setText(currUserBean.getPassword());
                toggleButton.setChecked(true);
            } else {
                passwordTx.setText("");
                toggleButton.setChecked(false);
            }
            Logger.d(BASE_USER_RESOURE + currUserBean.getData().getPortrait_url() + "");
            Glide.with(this)
                    .load(BASE_USER_RESOURE + currUserBean.getData().getPortrait_url()+Utils.getThumbnail(100,100))
                    //.load("http://www.baidu.com")
                    //    .placeholder(R.mipmap.icon_usercenter_morentouxiang)
                    //     .error(R.mipmap.icon_usercenter_morentouxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(accountUserHeadIv);
        }


        phoneTx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordTx.setText("");
                check(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mListPop = phoneTx.getmPopupWindow();
        phoneTx.setAdapter(new LoginListAdapter(userListArr, this));
        phoneTx.getmPopListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserBean userBean = userListArr.get(position);
                phoneTx.setText(userBean.getData().getMobile());
                phoneTx.myDismiss();
            }
        });
    }

    private void check(String s) {
        if (null != userListArr) {
            for (int a = 0; a < userListArr.size(); a++) {
                Logger.e(userListArr.get(a) + "");

                UserBean userBean = userListArr.get(a);
                if (userBean.getData().getMobile().equals(s)) {
                    passwordTx.setText("");
                    Glide.with(LoginActivity.this)
                            .load(BASE_USER_RESOURE + userBean.getData().getPortrait_url() +Utils.getThumbnail(100,100))
                            //.load("http://www.baidu.com")
                            // .placeholder(R.mipmap.icon_usercenter_morentouxiang)
                            .error(R.mipmap.icon_usercenter_morentouxiang)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(accountUserHeadIv);
                    if (null != userBean && !TextUtils.isEmpty(userBean.getPassword())) {
                        passwordTx.setText(userBean.getPassword());
                        toggleButton.setChecked(true);
                    } else {
                        passwordTx.setText("");
                        toggleButton.setChecked(false);
                    }
                    return;
                }

            }
            accountUserHeadIv.setImageResource(R.mipmap.icon_usercenter_morentouxiang);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeProgressDialog();
    }


    public void setDrawabout(EditText view, int left, int right) {
        Drawable drawableLeft = view.getCompoundDrawables()[0], drawableRight = view.getCompoundDrawables()[2];
        /// 这一步必须要做,否则不会显示.
        if (left != -1) {
            drawableLeft = getResources().getDrawable(left);
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        }
        if (right != -1) {
            drawableRight = getResources().getDrawable(right);
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        }
        view.setCompoundDrawables(drawableLeft, null, drawableRight, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                phoneTx.clearFocus(); //清除焦点
                passwordTx.clearFocus(); //清除焦点
                showInput(false);
            }
        }
        return super.onTouchEvent(event);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Utils.toast(LoginActivity.this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
                Logger.e("再按一次退出程序:::" + exitTime);
            } else {
                finish();
                System.exit(1);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void getBaseInfo() {
        RxRetrofitClient.getInstance(this).systemdictionarylist(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LoginActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Logger.e(e.toString());
                }

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    // Logger.e("responseBody::"+responseBody.string());
                    SystemInfoBean sb = new SystemInfoBean(responseBody.string());
                    mCache.put("SystemInfoBean", sb);
                    Logger.e("SystemInfoBean:" + sb.getCode());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getUserInfo(final UserBean userBean) {
        RxRetrofitClient.getInstance(LoginActivity.this).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LoginActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    saveUser(userBean, userBaseBean);
                }
            }
        });
    }
}
