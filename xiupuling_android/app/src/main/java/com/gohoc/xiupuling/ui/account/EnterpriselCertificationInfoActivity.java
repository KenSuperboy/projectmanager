package com.gohoc.xiupuling.ui.account;

import android.os.Bundle;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.CompanyVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class EnterpriselCertificationInfoActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.commpany_name_tv)
    TextView commpanyNameTv;
    @BindView(R.id.yingyezhizhao_tv)
    TextView yingyezhizhaoTv;
    @BindView(R.id.shuiwudengji_tv)
    TextView shuiwudengjiTv;
    @BindView(R.id.zhuzijiegou_tv)
    TextView zhuzijiegouTv;
    @BindView(R.id.ctf_inf_tv)
    TextView ctfInfTv;
    private CompanyVerifyapplyInfoBean companyVerifyapplyInfoBean;
    private UserBaseBean userBaseBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprisel_certification_info);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("企业认证");
        companyVerifyapplyInfo();
        userBaseBean = Credential.getInstance().getCurUser(this);
    }

    private void companyVerifyapplyInfo() {
        RxRetrofitClient.companyVerifyapplyInfo(new Observer<CompanyVerifyapplyInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(EnterpriselCertificationInfoActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(CompanyVerifyapplyInfoBean companyVerifyapplyInfoBean) {
                //Toast.makeText(CertificationActivity.this, companyVerifyapplyInfoBean.getMessage(), Toast.LENGTH_SHORT).show();
                if (companyVerifyapplyInfoBean.getCode() == 1) {
                    commpanyNameTv.setText(companyVerifyapplyInfoBean.getData().getName() + "");
                    boolean isDel=userBaseBean.getData().getCompany_auth() == 2;

                    yingyezhizhaoTv.setText(yingyezhizhaoTv.getText() + delNumber(companyVerifyapplyInfoBean.getData().getBusiness_code() + "",isDel));
                    shuiwudengjiTv.setText(shuiwudengjiTv.getText() + delNumber(companyVerifyapplyInfoBean.getData().getTax_no() + "",isDel));
                    zhuzijiegouTv.setText(zhuzijiegouTv.getText() + delNumber(companyVerifyapplyInfoBean.getData().getOrg_id() + "",isDel));


                    if (isDel)
                        ctfInfTv.setText("恭喜您,企业实名认证成功!");

                }
            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        EnterpriselCertificationInfoActivity.this.finish();
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
