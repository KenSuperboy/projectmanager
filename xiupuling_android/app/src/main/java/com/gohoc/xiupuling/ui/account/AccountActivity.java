package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.KvBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class AccountActivity extends BasicActivity implements View.OnClickListener {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.account_head_bk)
    ImageView accountHeadBk;
    @BindView(R.id.account_user_head_iv)
    ImageView accountUserHeadIv;
    @BindView(R.id.account_name_tv)
    TextView accountNameTv;
    @BindView(R.id.account_name_lv)
    LinearLayout accountNameLv;
    @BindView(R.id.account_sex_tv)
    TextView accountSexTv;
    @BindView(R.id.account_sex_lv)
    LinearLayout accountSexLv;
    @BindView(R.id.account_age_tv)
    TextView accountAgeTv;
    @BindView(R.id.account_age_lv)
    LinearLayout accountAgeLv;
    @BindView(R.id.account_mobile_tv)
    TextView accountMobileTv;
    @BindView(R.id.account_mobile_lv)
    LinearLayout accountMobileLv;
    @BindView(R.id.account_iscertification_tv)
    TextView accountIscertificationTv;
    @BindView(R.id.account_iscertification_lv)
    LinearLayout accountIscertificationLv;
    private ACache mCache;
    OptionsPickerView sexOptions, ageOptions;
    private ArrayList<KvBean> optionsSexItems = new ArrayList<>();
    private ArrayList<KvBean> optionsAgeItems = new ArrayList<>();
    private UserBaseBean user;
    RequestListener<String, GlideDrawable> listener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            Logger.d("onException: " + e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            Logger.d("isFromMemoryCache:" + isFromMemoryCache + "  model:" + model + " isFirstResource: " + isFirstResource);
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        toolbarTitle.setText("个人信息");
        setStatusColor(R.color.colorPrimary);
        EventBus.getDefault().register(this);
        mCache = ACache.get(this);
        user = Credential.getInstance().getCurUser(this);
        initInfo(user);
        Credential.getInstance().updateUserInfo(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UserEventBus(Event.UserEvent message) {
        user = Credential.getInstance().getCurUser(this);
        initInfo(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initInfo(UserBaseBean userBaseBean) {
        if (userBaseBean != null) {
            userBaseBean.getData().setPortrait_url(userBaseBean.getData().getPortrait_url() + "");
            userBaseBean.getData().setNick_name(userBaseBean.getData().getNick_name() + "");

            if(!TextUtils.isEmpty(userBaseBean.getData().getAge()))
            {
                userBaseBean.getData().setAge(String.valueOf(userBaseBean.getData().getAge()));
            }


            userBaseBean.getData().setUsername(userBaseBean.getData().getUsername() + "");
            if (null != userBaseBean.getData()) {
                Logger.d(BASE_USER_RESOURE + userBaseBean.getData().getPortrait_url() + Utils.getThumbnail(100,100));
                Glide.with(getApplication())
                        .load(BASE_USER_RESOURE + userBaseBean.getData().getPortrait_url())
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(listener)
                        .into(accountUserHeadIv);
                Logger.e(BASE_USER_RESOURE + userBaseBean.getData().getPortrait_url() + "");
            }
            if (!TextUtils.isEmpty(userBaseBean.getData().getNick_name())) {
                accountNameTv.setText(userBaseBean.getData().getNick_name());
            }
            if (!TextUtils.isEmpty(userBaseBean.getData().getSex())) {
                if (userBaseBean.getData().getSex().toString().equals("0"))
                    accountSexTv.setText("保密");
                else if (userBaseBean.getData().getSex().toString().equals("1"))
                    accountSexTv.setText("男");
                else
                    accountSexTv.setText("女");
            }
            if (!TextUtils.isEmpty(userBaseBean.getData().getAge())) {
                Logger.e("zcs",userBaseBean.getData().getAge()+"");
                accountAgeTv.setText(userBaseBean.getData().getAge() + "后");
            }
            if (!TextUtils.isEmpty(userBaseBean.getData().getMobile())) {
                accountMobileTv.setText(Utils.doMobile(userBaseBean.getData().getMobile() + ""));
            }
            if (userBaseBean.getData().getPersonal_auth() == 0 && userBaseBean.getData().getCompany_auth() == 0) {
                accountIscertificationTv.setText("还未认证");
            } else if (userBaseBean.getData().getPersonal_auth() == 2 && userBaseBean.getData().getCompany_auth() == 2)
                accountIscertificationTv.setText("全部通过认证");
            else if (userBaseBean.getData().getPersonal_auth() == 2) {
                accountIscertificationTv.setText("通过个人认证");
            } else if (userBaseBean.getData().getCompany_auth() == 2) {
                accountIscertificationTv.setText("通过企业认证");
            }

        }


    }


    @OnClick({R.id.toolbar_left_title, R.id.account_user_head_iv, R.id.account_name_lv, R.id.account_sex_lv, R.id.account_age_lv, R.id.account_mobile_lv, R.id.account_iscertification_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                AccountActivity.this.finish();
                break;
            case R.id.account_user_head_iv:
                startActivity(new Intent(AccountActivity.this, AccountEditPortraitActivity.class));
                break;
            case R.id.account_name_lv:
                if (null != user)
                    startActivity(new Intent(AccountActivity.this, AccountEditNameActivity.class).putExtra("name", user.getData().getNick_name() + ""));
                break;
            case R.id.account_sex_lv:
                showSexSelect();
                break;
            case R.id.account_age_lv:
                showAgeSelect();
                break;
            case R.id.account_mobile_lv:
                if (null != user)
                    startActivity(new Intent(AccountActivity.this, ChangeMobileActivity.class).putExtra("mobile", user.getData().getMobile() + ""));
                break;
            case R.id.account_iscertification_lv:
                startActivity(new Intent(AccountActivity.this, CertificationActivity.class));
                break;
        }
    }


    private void showAgeSelect() {
        if (null == ageOptions) {
            //条件选择器
            optionsAgeItems.add(new KvBean("00", "00后"));
            optionsAgeItems.add(new KvBean("10", "10后"));
            optionsAgeItems.add(new KvBean("90", "90后"));
            optionsAgeItems.add(new KvBean("80", "80后"));
            optionsAgeItems.add(new KvBean("70", "70后"));
            optionsAgeItems.add(new KvBean("60", "60后"));


            ageOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    Logger.d(optionsAgeItems.get(options1));
                    KvBean kvBean = optionsAgeItems.get(options1);
                    updateUser(null, kvBean.getK());
                    accountAgeTv.setText(kvBean.getV());
                }
            }).setTitleBgColor(Color.parseColor("#27ABFD"))
                    .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                    .setCancelColor(Color.WHITE)//取消按钮文字颜色
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("保存")//确认按钮文字
                    .build();
            ageOptions.setPicker(optionsAgeItems);
        }
        if (!ageOptions.isShowing())
            ageOptions.show();
    }

    private void showSexSelect() {
        if (null == sexOptions) {
            //条件选择器
            optionsSexItems.add(new KvBean("0", "保密"));
            optionsSexItems.add(new KvBean("1", "男"));
            optionsSexItems.add(new KvBean("2", "女"));
            sexOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    KvBean kvBean = optionsSexItems.get(options1);
                    updateUser(kvBean.getK(), null);
                    accountSexTv.setText(kvBean.getV());
                    Logger.d(optionsSexItems.get(options1));
                }
            }).setTitleBgColor(Color.parseColor("#27ABFD"))
                    .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                    .setCancelColor(Color.WHITE)//取消按钮文字颜色
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("保存")//确认按钮文字
                    .build();
            sexOptions.setPicker(optionsSexItems);
        }
        if (!sexOptions.isShowing())
            sexOptions.show();
    }


    private void updateUser(final String sex, final String age) {
        RxRetrofitClient.getInstance(AccountActivity.this).updatePortrait(null, null, sex, age, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
                Utils.toast(AccountActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(AccountActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    if (null != sex)
                        Credential.getInstance().getCurUser(AccountActivity.this).getData().setSex(sex);
                    if (null != age)
                        Credential.getInstance().getCurUser(AccountActivity.this).getData().setAge(age);
                    Credential.getInstance().updateUserInfo(AccountActivity.this);
                }

            }
        });
    }


}
