package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MyBankBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import rx.Observer;

public class WalletWithdrawActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.valus_et)
    ClearEditText valusEt;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.cover_iv)
    CircleImageView coverIv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.ps_tv)
    TextView psTv;
    @BindView(R.id.card_ll)
    LinearLayout cardLl;
    private MyBankBean.DataBean bank;
    private String money;
    private Double moneyDouble;
    private final static int BANK_REQUEST_RESLUT = 10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_withdraw);
        ButterKnife.bind(this);
        toolbarTitle.setText("提现设定");
        toolbarRight.setText("明细");
        setStatusColor(R.color.colorPrimary);
        bank = (MyBankBean.DataBean) getIntent().getExtras().get("bank");
        money = getIntent().getStringExtra("money");
        moneyDouble = getIntent().getDoubleExtra("moneyDouble", 0);

        if (bank != null) {
            nameTv.setText(bank.getBankname());
            psTv.setVisibility(View.VISIBLE);
            psTv.setText("尾数是" + bank.getBankcardno().substring(bank.getBankcardno().length() - 4, bank.getBankcardno().length()));
            Glide.with(this)
                    .load(Constant.NetConstant.BASE_USER_RESOURE + bank.getBank_icon())
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(coverIv);
        }
        if (!TextUtils.isEmpty(money))
            valusEt.setHint("本次最多转出" + money);
        check();
        valusEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {
                check();
            }
        });
    }


    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.button_ll, R.id.card_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                startActivity(new Intent(WalletWithdrawActivity.this, WalletListActivity.class));
                break;
            case R.id.button_ll:
                if (check())
                    withdrawapply(bank.getIds(), valusEt.getText() + "");
                break;
            case R.id.card_ll:
                startActivityForResult(new Intent(WalletWithdrawActivity.this, MyBankCardActivity.class).putExtra("bank", bank), BANK_REQUEST_RESLUT);
                break;
        }
    }

    private void withdrawapply(String bank_ids, String amount) {
        RxRetrofitClient.getInstance(this).withdrawapply(bank_ids, amount, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(WalletWithdrawActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(WalletWithdrawActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.WalletUpdate());
                    startActivity(new Intent(WalletWithdrawActivity.this, WithdrawResultActivity.class));
                }
            }
        });
    }

    private boolean check() {
        if (bank != null && !TextUtils.isEmpty(valusEt.getText())&& Integer.parseInt(valusEt.getText().toString())>0) {
            if(Integer.parseInt(valusEt.getText().toString()) <=moneyDouble)
            {
                buttonLl.setBackgroundResource(R.drawable.login_bt_selector);
                return true;
            }
        }
        buttonLl.setBackgroundColor(Color.parseColor("#CBCBCB"));
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BANK_REQUEST_RESLUT) {
                bank = (MyBankBean.DataBean) data.getExtras().get("bank");
                nameTv.setText(bank.getBankname());
                psTv.setText(" 尾数是" + bank.getBankcardno().substring(bank.getBankcardno().length() - 4, bank.getBankcardno().length()));
                Glide.with(this)
                        .load(Constant.NetConstant.BASE_USER_RESOURE + bank.getBank_icon())
                        // .placeholder(R.mipmap.icon_dengru_touxiang)
                        //.error(R.mipmap.icon_dengru_touxiang)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(coverIv);
                check();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(valusEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
