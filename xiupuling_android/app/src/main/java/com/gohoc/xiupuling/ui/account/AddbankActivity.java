package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BankBean;
import com.gohoc.xiupuling.bean.CompanyVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.PersonalVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.SimpleEditActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class AddbankActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.owner_tv)
    TextView ownerTv;
    @BindView(R.id.owner_ll)
    LinearLayout ownerLl;
    @BindView(R.id.bank_tv)
    TextView bankTv;
    @BindView(R.id.bank_ll)
    LinearLayout bankLl;
    @BindView(R.id.ps_ll)
    LinearLayout psLl;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.card_nunber_ll)
    LinearLayout cardNunberLl;
    @BindView(R.id.kaihuhang_ll)
    LinearLayout kaihuhangLl;
    @BindView(R.id.card_tv)
    TextView cardTv;
    @BindView(R.id.card_name_tv)
    TextView cardNameTv;
    private BankBean.DataBean bank;
    private SystemInfoBean sb;
    private ACache mCache;
    private final static int BANK_REQUEST_RESULT = 1000;
    private final static int CARD_REQUEST_RESULT = 1001;
    private final static int CARD_NAME_REQUEST_RESULT = 1002;
    private String cardNumber = "";
    private String cardName = "";
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbank);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("添加银行卡");
        mCache = ACache.get(this);
        sb = (SystemInfoBean) mCache.getAsObject("SystemInfoBean");
        if (getIntent().getIntExtra("type", 0) == 0)
            personalVerifyapplyInfo();
        else
            companyVerifyapplyInfo();
    }

    @OnClick({R.id.toolbar_left_title, R.id.owner_ll, R.id.bank_ll, R.id.ps_ll, R.id.button_ll, R.id.card_nunber_ll, R.id.kaihuhang_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.owner_ll:
                break;
            case R.id.bank_ll:
                startActivityForResult(new Intent(AddbankActivity.this, BanklistActivity.class), BANK_REQUEST_RESULT);
                break;
            case R.id.ps_ll:
                if (null != sb)
                    startActivity(new Intent(AddbankActivity.this, WebViewActivity.class).putExtra("url", sb.getData().getAccountsecurity() + "").putExtra("title", "账户安全"));
                break;
            case R.id.button_ll:
                if (check())
                    addbankcard(cardNumber, bank.getBank_id() + "", name, cardName);
                break;
            case R.id.card_nunber_ll:
                startActivityForResult(new Intent(AddbankActivity.this, SimpleEditActivity.class)
                        .putExtra("title", "银行卡号")
                        .putExtra("value", cardNumber)
                        .putExtra("tipes", "请输入您的银行卡号")
                        .putExtra("emptyTipes","银行卡号不能为空")
                        .putExtra("limteEmpty",true)
                        .putExtra("textType", InputType.TYPE_CLASS_NUMBER), CARD_REQUEST_RESULT);
                break;
            case R.id.kaihuhang_ll:
                startActivityForResult(new Intent(AddbankActivity.this, SimpleEditActivity.class)
                        .putExtra("title", "开户行")
                        .putExtra("value", cardName)
                        .putExtra("tipes", "请输入您的开户行名称")
                        .putExtra("emptyTipes","开户行名称不能为空")
                        .putExtra("limteEmpty",true)
                        .putExtra("textType", InputType.TYPE_CLASS_TEXT), CARD_NAME_REQUEST_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == BANK_REQUEST_RESULT) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        BankBean.DataBean banks = (BankBean.DataBean) d.get("bank");
                        if (banks != null) {
                            bank = banks;
                            bankTv.setText(banks.getBank_name() + "");
                            check();
                        }
                    }
                }
            } else if (requestCode == CARD_REQUEST_RESULT) {
            //    if (!TextUtils.isEmpty(data.getStringExtra("value"))) {
                    cardNumber = data.getStringExtra("value");
                    cardTv.setText(cardNumber);
                    check();
           //     }

            } else if (requestCode == CARD_NAME_REQUEST_RESULT) {
              //  if (!TextUtils.isEmpty(data.getStringExtra("value"))) {
                    cardName = data.getStringExtra("value");
                    cardNameTv.setText(cardName);
                    check();
              // }
            }
        }
    }

    private void companyVerifyapplyInfo() {
        RxRetrofitClient.companyVerifyapplyInfo(new Observer<CompanyVerifyapplyInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddbankActivity.this, "请检查网络是否正常");
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
                    name = companyVerifyapplyInfoBean.getData().getName() + "";
                    ownerTv.setText(name);
                }
            }
        });
    }

    private void personalVerifyapplyInfo() {
        RxRetrofitClient.personalVerifyapplyInfo(new Observer<PersonalVerifyapplyInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddbankActivity.this, "请检查网络是否正常");
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
                    name = personalVerifyapplyInfoBean.getData().getName() + "";
                    ownerTv.setText(name);
                }
            }
        });
    }

    private void addbankcard(String bankcardno, String bank_id, String name, String subbranch_addr) {
        RxRetrofitClient.getInstance(this).addbankcard(bankcardno, bank_id, name, subbranch_addr, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddbankActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(AddbankActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    setResult(RESULT_OK);
                    finish();
                }

            }
        });
    }

    private boolean check() {

        if (TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(cardName) || TextUtils.isEmpty(name)) {
            buttonLl.setBackgroundColor(Color.parseColor("#cbcbcb"));
            return false;
        } else {
            buttonLl.setBackgroundResource(R.drawable.login_bt_selector);
            return true;
        }


    }
}
