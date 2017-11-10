package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.linkpackage.LiandongbaoAddActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.suke.widget.SwitchButton;
import com.wuxiaolong.androidutils.library.VersionUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class AccountSettingActivity extends BasicActivity implements SwitchButton.OnCheckedChangeListener {

    @BindView(R.id.setting_menu_info_lv)
    LinearLayout settingMenuInfoLv;
    @BindView(R.id.setting_menu_safe_tv)
    TextView settingMenuSafeTv;
    @BindView(R.id.setting_menu_mobile_tv)
    TextView settingMenuMobileTv;
    @BindView(R.id.setting_menu_mobile_lv)
    LinearLayout settingMenuMobileLv;
    @BindView(R.id.setting_menu_pwsafe_tv)
    TextView settingMenuPwsafeTv;
    @BindView(R.id.setting_menu_pwsafe_lv)
    LinearLayout settingMenuPwsafeLv;
    @BindView(R.id.setting_menu_safeq_tv)
    TextView settingMenuSafeqTv;
    @BindView(R.id.account_setting_safeq_lv)
    LinearLayout accountSettingSafeqLv;
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.setting_menu_legal_lv)
    LinearLayout settingMenuLegalLv;
    @BindView(R.id.setting_menu_about_lv)
    LinearLayout settingMenuAboutLv;
    @BindView(R.id.logout_button_ll)
    LinearLayout logoutButtonLl;
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.version_tv)
    TextView versionTv;
    @BindView(R.id.tv_net)
    TextView mTvNet;
    @BindView(R.id.linear_net_layout)
    LinearLayout mLinearNetLayout;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.linear_logo_layout)
    LinearLayout mLinearLogoLayout;

    private UserBaseBean userBaseBean;
    private ACache mCache;
    private SystemInfoBean sb;
    private int NETNAME_REQUEST=100,LOGO_REQUEST=101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("设置");
        mCache = ACache.get(this);
        userBaseBean = Credential.getInstance().getCurUser(this);
        sb = (SystemInfoBean) mCache.getAsObject("SystemInfoBean");
        initInfo(userBaseBean);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UserEvent(Event.UserEvent userEvent) {
        userBaseBean = Credential.getInstance().getCurUser(this);
        initInfo(userBaseBean);
    }

    @OnClick({R.id.setting_menu_info_lv, R.id.setting_menu_mobile_lv, R.id.setting_menu_pwsafe_lv, R.id.account_setting_safeq_lv, R.id.setting_menu_legal_lv, R.id.setting_menu_about_lv, R.id.logout_button_ll, R.id.toolbar_left_title,R.id.linear_net_layout,R.id.linear_logo_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_menu_info_lv:
                startActivity(new Intent(AccountSettingActivity.this, AccountActivity.class));
                break;
            case R.id.setting_menu_mobile_lv:
                if (null != userBaseBean)
                    startActivity(new Intent(AccountSettingActivity.this, ChangeMobileActivity.class).putExtra("mobile", userBaseBean.getData().getMobile() + ""));
                break;
            case R.id.setting_menu_pwsafe_lv:
                startActivity(new Intent(AccountSettingActivity.this, SettingPasswordActivity.class));
                break;
            case R.id.account_setting_safeq_lv:
                if (userBaseBean.getData().getIs_security_question() == 0)
                    startActivity(new Intent(AccountSettingActivity.this, SecurityQsActivity.class));
                else
                    startActivity(new Intent(AccountSettingActivity.this, ChangeSqActivity.class));
                break;
            case R.id.setting_menu_legal_lv:
                Logger.e(Utils.getSystemInfoBean(this).getData().getAboutLegaldocument());
                startActivity(new Intent(AccountSettingActivity.this, WebViewActivity.class).putExtra("url", Utils.getSystemInfoBean(this).getData().getAboutLegaldocument()).putExtra("title", "法律信息"));
                break;
            case R.id.setting_menu_about_lv:
                startActivity(new Intent(AccountSettingActivity.this, WebViewActivity.class).putExtra("url", sb.getData().getAboutxpl()).putExtra("title", "关于我们"));
                break;
            case R.id.logout_button_ll:
                Utils.logout(AccountSettingActivity.this, true);
                break;
            case R.id.toolbar_left_title:
                AccountSettingActivity.this.finish();
                break;
            case R.id.linear_net_layout:
                Intent intent = new Intent(AccountSettingActivity.this, LiandongbaoAddActivity.class);
                intent.putExtra("type", 2);//
                intent.putExtra("title", "网站域名");//
                intent.putExtra("name", mTvNet.getText().toString());//
                startActivityForResult(intent,NETNAME_REQUEST);
                break;
            case R.id.linear_logo_layout:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==NETNAME_REQUEST){
                //域名修改
                mTvNet.setText(data.getStringExtra("name"));
            }else if(requestCode==LOGO_REQUEST){
                //logo修改
                ---
            }
        }
    }

    @OnClick(R.id.logout_button_ll)
    public void onClick() {
    }

    private void initInfo(UserBaseBean userBaseBean) {
        settingMenuMobileTv.setText(Utils.doMobile(userBaseBean.getData().getMobile() + ""));
        if (userBaseBean.getData().getIs_security_question() == 1)
            settingMenuSafeqTv.setText("****** 修改");
        else
            settingMenuSafeqTv.setText("未设置");
        versionTv.setText("v" + VersionUtil.getVersionName(AccountSettingActivity.this) + "");


        if (userBaseBean.getData().getIs_promo() == 0) {
            switchButton.setChecked(userBaseBean.getData().getShared_platform() == 1 ? true : false);
            switchButton.setOnCheckedChangeListener(this);
        } else {
            switchButton.setEnabled(false);
        }
        // 账户安全等级
        //Logger.e(mCache.getAsString("securityLevel")+;
        settingMenuSafeTv.setText(Utils.getSafeLevelStr(userBaseBean, Integer.parseInt(mCache.getAsString("securityLevel"))));
        //密码安全等级
        settingMenuPwsafeTv.setText(Utils.analyzePasswordSecurityLevelStr(Integer.parseInt(mCache.getAsString("securityLevel"))));

        // securityLevel

    }


    private void switchsharedplatform(final String shared_platform) {
        RxRetrofitClient.getInstance(this).switchsharedplatform(shared_platform, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AccountSettingActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.ReLoginUpdate());
                    Credential.getInstance().updateUserInfo(AccountSettingActivity.this);
                    //Credential.getInstance().getCurUser(AccountSettingActivity.this).getData().setShared_platform(Integer.parseInt(shared_platform));
                } else {
                    switchButton.setOnCheckedChangeListener(null);
                    if (shared_platform.equals("0"))
                        switchButton.setChecked(false);
                    else
                        switchButton.setChecked(true);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            switchButton.setOnCheckedChangeListener(AccountSettingActivity.this);
                        }
                    }, 500);
                    //
                }
                Utils.toast(AccountSettingActivity.this, vCodeBenan.getMessage());
                // getUserInfo();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        if (isChecked)
            switchsharedplatform("1");
        else
            switchsharedplatform("0");
    }
}
