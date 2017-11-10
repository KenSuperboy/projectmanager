package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MyBankBean;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.WalletBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.NumberUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class WalletActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.wallet_money_tv)
    TextView walletMoneyTv;
    @BindView(R.id.card_tv)
    TextView cardTv;
    @BindView(R.id.card_ll)
    LinearLayout cardLl;
    @BindView(R.id.ps_ll)
    LinearLayout psLl;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private SystemInfoBean sb;
    private ACache mCache;
    private final static int BANK_REQUEST_RESLUT = 10086;
    private MyBankBean.DataBean bank;
    private WalletBean walletBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        mCache = ACache.get(this);
        sb = (SystemInfoBean) mCache.getAsObject("SystemInfoBean");
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("我的钱包");
        toolbarRight.setText("明细");
        pocketmoney();

    }

    @OnClick({R.id.toolbar_left_title, R.id.card_ll, R.id.ps_ll, R.id.button_ll, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.card_ll:
                startActivityForResult(new Intent(WalletActivity.this, MyBankCardActivity.class).putExtra("bank",bank), BANK_REQUEST_RESLUT);
                break;
            case R.id.ps_ll:
                if (null!=sb)
                    startActivity(new Intent(WalletActivity.this, WebViewActivity.class).putExtra("url", sb.getData().getAboutWithdrawscash()).putExtra("title", "提现规则"));
                break;
            case R.id.button_ll:
                if (check())
                    startActivity(new Intent(WalletActivity.this, WalletWithdrawActivity.class).putExtra("bank",bank)
                            .putExtra("money",String.valueOf(walletMoneyTv.getText()) )
                            .putExtra("moneyDouble",walletBeans.getBalance()));
                break;

            case R.id.toolbar_right:
                startActivity(new Intent(WalletActivity.this, WalletListActivity.class));
                break;
        }
    }

    private void pocketmoney() {
        RxRetrofitClient.getInstance(this).pocketmoney(new Observer<WalletBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(WalletActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Logger.e(e.toString());
                }
            }

            @Override
            public void onNext(WalletBean walletBean) {
                if (walletBean.getCode() == 1) {
                    walletBeans = walletBean;
                    walletMoneyTv.setText( NumberUtil.getDecimal(walletBean.getBalance()));
                    check();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BANK_REQUEST_RESLUT) {
                bank = (MyBankBean.DataBean) data.getExtras().get("bank");
                cardTv.setText(bank.getBankname() + " 尾数是"+bank.getBankcardno().substring(bank.getBankcardno().length()-4,bank.getBankcardno().length()));
                check();
            }
        }
    }

    private boolean check() {
        if (walletBeans.getBalance() > 0) {
            buttonLl.setBackgroundResource(R.drawable.login_bt_selector);
            return true;
        }
        buttonLl.setBackgroundColor(Color.parseColor("#CBCBCB"));
        return false;
    }
}
