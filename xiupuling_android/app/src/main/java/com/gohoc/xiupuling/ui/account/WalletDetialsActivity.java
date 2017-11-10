package com.gohoc.xiupuling.ui.account;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.WalletDetailsBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class WalletDetialsActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.type1_money_tv)
    TextView type1MoneyTv;
    @BindView(R.id.type1_type_tv)
    TextView type1TypeTv;
    @BindView(R.id.type1_state_tv)
    TextView type1StateTv;
    @BindView(R.id.type1_time1_tv)
    TextView type1Time1Tv;
    @BindView(R.id.type1_time2_tv)
    TextView type1Time2Tv;
    @BindView(R.id.type1_bank_tv)
    TextView type1BankTv;
    @BindView(R.id.type1_order_no_tv)
    TextView type1OrderNoTv;
    @BindView(R.id.type1)
    LinearLayout type1;
    @BindView(R.id.type2_money_tv)
    TextView type2MoneyTv;
    @BindView(R.id.type2_type_tv)
    TextView type2TypeTv;
    @BindView(R.id.type2_shop_tv)
    TextView type2ShopTv;
    @BindView(R.id.type2_state_tv)
    TextView type2StateTv;
    @BindView(R.id.type2_time_tv)
    TextView type2TimeTv;
    @BindView(R.id.type2_order_no_tv)
    TextView type2OrderNoTv;
    @BindView(R.id.type2_intro_user_tv)
    TextView type2IntroUserTv;
    @BindView(R.id.type2_percentage_no_tv)
    TextView type2PercentageNoTv;
    @BindView(R.id.type2)
    LinearLayout type2;
    @BindView(R.id.type3_money_tv)
    TextView type3MoneyTv;
    @BindView(R.id.type3_type_tv)
    TextView type3TypeTv;
    @BindView(R.id.type3_shop_tv)
    TextView type3ShopTv;
    @BindView(R.id.type3_target_tv)
    TextView type3TargetTv;
    @BindView(R.id.type3_state_tv)
    TextView type3StateTv;
    @BindView(R.id.type3_time_tv)
    TextView type3TimeTv;
    @BindView(R.id.type3_termianl_no_tv)
    TextView type3TermianlNoTv;
    @BindView(R.id.type3)
    LinearLayout type3;
    @BindView(R.id.type4_money_tv)
    TextView type4MoneyTv;
    @BindView(R.id.type4_type_tv)
    TextView type4TypeTv;
    @BindView(R.id.type4_shop_tv)
    TextView type4ShopTv;
    @BindView(R.id.type4_target_tv)
    TextView type4TargetTv;
    @BindView(R.id.type4_state_tv)
    TextView type4StateTv;
    @BindView(R.id.type4_time_tv)
    TextView type4TimeTv;
    @BindView(R.id.type4_order_no_tv)
    TextView type4OrderNoTv;
    @BindView(R.id.type4)
    LinearLayout type4;
    @BindView(R.id.type5_money_tv)
    TextView type5MoneyTv;
    @BindView(R.id.type5_type_tv)
    TextView type5TypeTv;
    @BindView(R.id.type5_shop_name_tv)
    TextView type5ShopNameTv;
    @BindView(R.id.type5_push_type_tv)
    TextView type5PushTypeTv;
    @BindView(R.id.type5_state_tv)
    TextView type5StateTv;
    @BindView(R.id.type5_time_tv)
    TextView type5TimeTv;
    @BindView(R.id.type5_order_id_tv)
    TextView type5OrderIdTv;
    @BindView(R.id.type5)
    LinearLayout type5;
    @BindView(R.id.type6_money_tv)
    TextView type6MoneyTv;
    @BindView(R.id.type6_type_tv)
    TextView type6TypeTv;
    @BindView(R.id.type6_shop_name_tv)
    TextView type6ShopNameTv;
    @BindView(R.id.type6_push_type_tv)
    TextView type6PushTypeTv;
    @BindView(R.id.type6_state_tv)
    TextView type6StateTv;
    @BindView(R.id.type6_time_tv)
    TextView type6TimeTv;
    @BindView(R.id.type6_order_id_tv)
    TextView type6OrderIdTv;
    @BindView(R.id.type6_pay_type_tv)
    TextView type6PayTypeTv;
    @BindView(R.id.type6)
    LinearLayout type6;
    @BindView(R.id.type7_money_tv)
    TextView type7MoneyTv;
    @BindView(R.id.type7_type_tv)
    TextView type7TypeTv;
    @BindView(R.id.type7_shop_name_tv)
    TextView type7ShopNameTv;
    @BindView(R.id.type7_work_type_tv)
    TextView type7WorkTypeTv;
    @BindView(R.id.type7_state_tv)
    TextView type7StateTv;
    @BindView(R.id.type7_time_tv)
    TextView type7TimeTv;
    @BindView(R.id.type7_order_id_tv)
    TextView type7OrderIdTv;
    @BindView(R.id.type7_pay_type_tv)
    TextView type7PayTypeTv;
    @BindView(R.id.type7)
    LinearLayout type7;
    @BindView(R.id.type8_money_tv)
    TextView type8MoneyTv;
    @BindView(R.id.type8_type_tv)
    TextView type8TypeTv;
    @BindView(R.id.type8_shop_name_tv)
    TextView type8ShopNameTv;
    @BindView(R.id.type8_work_type_tv)
    TextView type8WorkTypeTv;
    @BindView(R.id.type8_state_tv)
    TextView type8StateTv;
    @BindView(R.id.type8_time_tv)
    TextView type8TimeTv;
    @BindView(R.id.type8_order_id_tv)
    TextView type8OrderIdTv;
    @BindView(R.id.type8_pay_type_tv)
    TextView type8PayTypeTv;
    @BindView(R.id.type8)
    LinearLayout type8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detials);
        ButterKnife.bind(this);
        toolbarTitle.setText("交易详情");
        setStatusColor(R.color.colorPrimary);
        getmyinoutdetail(getIntent().getStringExtra("src_type"), getIntent().getStringExtra("trade_id"));
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    private void getmyinoutdetail(String src_type, String trade_id) {
        RxRetrofitClient.getInstance(this).getmyinoutdetail(src_type, trade_id, new Observer<WalletDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(WalletDetialsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(WalletDetailsBean walletDetailsBean) {
                if (walletDetailsBean.getCode() == 1) {
                    switch (walletDetailsBean.getData().getSrc_type()) {
                        case 1:  //1:终端展示收入
                            type3TypeTv.setText(walletDetailsBean.getData().getTrade_type());
                            type3MoneyTv.setText("￥" + Math.abs(walletDetailsBean.getData().getTrade_amount()) + "");
                            type3ShopTv.setText(walletDetailsBean.getData().getTrade_name());
                            type3StateTv.setText(walletDetailsBean.getData().getTrade_status());
                            type3TimeTv.setText(walletDetailsBean.getData().getCreate_time() + "");
                            if (walletDetailsBean.getData().getTrade_amount() > 0)
                                type3MoneyTv.setTextColor(Color.parseColor("#27ABFD"));
                            else
                                type3MoneyTv.setTextColor(Color.parseColor("#000000"));
                            type3.setVisibility(View.VISIBLE);
                            break;
                        case 2: // 2：作品稿酬收入
                            type4StateTv.setText(walletDetailsBean.getData().getTrade_status());
                            type4TypeTv.setText(walletDetailsBean.getData().getTrade_type());
                            type4MoneyTv.setText("￥" + Math.abs(walletDetailsBean.getData().getTrade_amount()) + "");
                            type4TimeTv.setText(walletDetailsBean.getData().getCreate_time() + "");
                            type4ShopTv.setText(walletDetailsBean.getData().getTrade_name());
                            type4OrderNoTv.setText(walletDetailsBean.getData().getTrade_id() + "");
                            type4TargetTv.setText(walletDetailsBean.getData().getTrade_target_name() + "");
                            if (walletDetailsBean.getData().getTrade_amount() > 0)
                                type4MoneyTv.setTextColor(Color.parseColor("#27ABFD"));
                            else
                                type4MoneyTv.setTextColor(Color.parseColor("#000000"));
                            type4.setVisibility(View.VISIBLE);
                            break;
                        case 3:// 3：投放资金回收
                            type5StateTv.setText(walletDetailsBean.getData().getTrade_status());
                            type5TypeTv.setText(walletDetailsBean.getData().getTrade_type());
                            type5MoneyTv.setText("￥" + Math.abs(walletDetailsBean.getData().getTrade_amount()) + "");
                            type5TimeTv.setText(walletDetailsBean.getData().getCreate_time() + "");
                            type5ShopNameTv.setText(walletDetailsBean.getData().getTrade_name() + "");
                            type5PushTypeTv.setText(walletDetailsBean.getData().getRange_type_name() + "");
                            type5OrderIdTv.setText(walletDetailsBean.getData().getTrade_id() + "");
                            if (walletDetailsBean.getData().getTrade_amount() > 0)
                                type5MoneyTv.setTextColor(Color.parseColor("#27ABFD"));
                            else
                                type5MoneyTv.setTextColor(Color.parseColor("#000000"));
                            type5.setVisibility(View.VISIBLE);
                            break;
                        case 4: // 4：投放展示
                            type6StateTv.setText(walletDetailsBean.getData().getTrade_status());
                            type6TypeTv.setText(walletDetailsBean.getData().getTrade_type());
                            type6MoneyTv.setText("￥" + Math.abs(walletDetailsBean.getData().getTrade_amount()) + "");
                            type6TimeTv.setText(walletDetailsBean.getData().getCreate_time() + "");
                            type6ShopNameTv.setText(walletDetailsBean.getData().getTrade_name());
                            type6OrderIdTv.setText(walletDetailsBean.getData().getTrade_id() + "");
                            type6PushTypeTv.setText(walletDetailsBean.getData().getRange_type_name() + "");
                            type6PayTypeTv.setText(walletDetailsBean.getData().getFundroute() + "");
                            if (walletDetailsBean.getData().getTrade_amount() > 0)
                                type6MoneyTv.setTextColor(Color.parseColor("#27ABFD"));
                            else
                                type6MoneyTv.setTextColor(Color.parseColor("#000000"));
                            type6.setVisibility(View.VISIBLE);
                            break;
                        case 5: // 5：购买作品
                            type7StateTv.setText(walletDetailsBean.getData().getTrade_status());
                            type7TypeTv.setText(walletDetailsBean.getData().getTrade_type());
                            type7MoneyTv.setText("￥" + Math.abs(walletDetailsBean.getData().getTrade_amount()) + "");
                            type7TimeTv.setText(walletDetailsBean.getData().getCreate_time() + "");
                            type7ShopNameTv.setText(walletDetailsBean.getData().getTrade_name() + "");
                            type7OrderIdTv.setText(walletDetailsBean.getData().getTrade_id() + "");
                            type7WorkTypeTv.setText(walletDetailsBean.getData().getWork_type_name() + "");
                            type7PayTypeTv.setText(walletDetailsBean.getData().getFundroute());
                            if (walletDetailsBean.getData().getTrade_amount() > 0)
                                type7MoneyTv.setTextColor(Color.parseColor("#27ABFD"));
                            else
                                type7MoneyTv.setTextColor(Color.parseColor("#000000"));
                            type7.setVisibility(View.VISIBLE);
                            break;
                        case 6: // 6：购买商品
                            type8StateTv.setText(walletDetailsBean.getData().getTrade_status());
                            type8TypeTv.setText(walletDetailsBean.getData().getTrade_type());
                            type8MoneyTv.setText("￥" + Math.abs(walletDetailsBean.getData().getTrade_amount()) + "");
                            type8TimeTv.setText(walletDetailsBean.getData().getCreate_time() + "");
                            type8ShopNameTv.setText(walletDetailsBean.getData().getTrade_name() + "");
                            type8PayTypeTv.setText(walletDetailsBean.getData().getFundroute() + "");
                            type8OrderIdTv.setText(walletDetailsBean.getData().getTrade_id() + "");
                            //  type8WorkTypeTv.setText(walletDetailsBean.getData().getTrade_id() + "");
                            if (walletDetailsBean.getData().getTrade_amount() > 0)
                                type8MoneyTv.setTextColor(Color.parseColor("#27ABFD"));
                            else
                                type3MoneyTv.setTextColor(Color.parseColor("#000000"));
                            type8.setVisibility(View.VISIBLE);
                            break;
                        case 7: //7：ᨀ现详情
                            type1TypeTv.setText(walletDetailsBean.getData().getTrade_type());
                            type1MoneyTv.setText("￥" + Math.abs(walletDetailsBean.getData().getTrade_amount()) + "");
                            type1Time1Tv.setText(walletDetailsBean.getData().getCreate_time() + "");
                            type1Time2Tv.setText("");
                            type1StateTv.setText(walletDetailsBean.getData().getTrade_status());
                            //       type1BankTv.setText(walletDetailsBean.getData().getCreate_time() + "");
                            type1OrderNoTv.setText(walletDetailsBean.getData().getTrade_id() + "");
                            type1BankTv.setText(walletDetailsBean.getData().getBank_name());
                            if (walletDetailsBean.getData().getTrade_amount() > 0)
                                type1MoneyTv.setTextColor(Color.parseColor("#27ABFD"));
                            else
                                type1MoneyTv.setTextColor(Color.parseColor("#000000"));
                            type1.setVisibility(View.VISIBLE);
                            break;
                    }
                }

            }
        });
    }


}
