package com.gohoc.xiupuling.ui.account;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PersonalVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import rx.Observer;

public class PersonlCertificationInfoActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.idcard_tv)
    TextView idcardTv;
    @BindView(R.id.user_pic_iv)
    CircleImageView userPicIv;
    @BindView(R.id.ctf_inf_tv)
    TextView ctfInfTv;
    private PersonalVerifyapplyInfoBean personalVerifyapplyInfo;
    private UserBaseBean userBaseBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personl_certification_info);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        userBaseBean = Credential.getInstance().getCurUser(this);
        toolbarTitle.setText("个人认证");
        personalVerifyapplyInfo();

    }


    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        PersonlCertificationInfoActivity.this.finish();
    }

    private void personalVerifyapplyInfo() {
        RxRetrofitClient.personalVerifyapplyInfo(new Observer<PersonalVerifyapplyInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PersonlCertificationInfoActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PersonalVerifyapplyInfoBean personalVerifyapplyInfoBean) {
                //Toast.makeText(CertificationActivity.this, companyVerifyapplyInfoBean.getMessage(), Toast.LENGTH_SHORT).show();
                if (personalVerifyapplyInfoBean.getCode() == 1) {
                    Glide.with(PersonlCertificationInfoActivity.this)
                            .load(NetConstant.BASE_USER_RESOURE + userBaseBean.getData().getPortrait_url())
                            // .placeholder(R.mipmap.icon_dengru_touxiang)
                            //.error(R.mipmap.icon_dengru_touxiang)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(userPicIv);
                    boolean isDel = userBaseBean.getData().getPersonal_auth() == 2;
                    nameTv.setText(personalVerifyapplyInfoBean.getData().getName() + "");
                    idcardTv.setText(delNumber(personalVerifyapplyInfoBean.getData().getIdno(), isDel));
                    if (isDel)
                        ctfInfTv.setText("恭喜您,个人实名认证成功!");
                }
            }
        });
    }

    private void getUserInfo() {
        RxRetrofitClient.getInstance(this).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PersonlCertificationInfoActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    Credential.getInstance().setCurUser(userBaseBean);


                }
            }
        });
    }

    private String delNumber(String str) {
        String newStr = "";
        char[] chars = str.toCharArray();
        if (chars.length > 6) {
            for (int a = 0; a < chars.length; a++) {
                if (a > 3 && a < chars.length - 3)
                    newStr += "*";
                else
                    newStr += chars[a];
            }
        }
        return newStr;
    }

    private String delNumber(String str, boolean isDel) {
        if (!isDel)
            return str;
        return delNumber(str);
    }
}
