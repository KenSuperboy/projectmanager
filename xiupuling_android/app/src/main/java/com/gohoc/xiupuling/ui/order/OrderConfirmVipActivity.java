package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BuyVipGoodsBean;
import com.gohoc.xiupuling.bean.GoodsBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.account.BonusPointTokenActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class OrderConfirmVipActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.cover_img)
    ImageView coverImg;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.shop_ds_tv)
    TextView shopDsTv;
    @BindView(R.id.splite)
    TextView splite;
    @BindView(R.id.tips_tv)
    TextView tipsTv;
    @BindView(R.id.paytips)
    TextView paytips;
    @BindView(R.id.pay_money_tv)
    TextView payMoneyTv;
    @BindView(R.id.buy_rs_iv)
    ImageView buyRsIv;
    @BindView(R.id.buy_rs)
    TextView buyRs;
    @BindView(R.id.buy_rs_v)
    TextView buyRsV;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    private GoodsBean.DataBean shop;
    private int[] icoRes = {0, R.mipmap.icon_sc_mendian, R.mipmap.icon_sc_zhongduan, R.mipmap.icon_sc_sirenpindao, R.mipmap.icon_sc_zuopin, R.mipmap.icon_sc_lingpai};
    private String[] typeNames = {"", "购买后门店拥有数为：", "购买后终端拥有数为：", "购买后私人电台拥有数为：", "购买后作品剩余数为：", "购买后令牌拥有数为："};
    private String[] tips = {"","拥有门店的最大数量增加", "拥有终端的最大数量增加", "拥有私人电台的最大数量增加", "消耗品，拥有作品的最大数量增加", "拥有令牌的最大数量增加"};
    private int[] typeCount = {0, 0, 0, 0, 0, 0};
    private static final int REQUEST_RESULT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("提交订单");
        getUserInfo();

        try {
            shop = (GoodsBean.DataBean) getIntent().getExtras().get("shop");
            Glide.with(this)
                    .load(NetConstant.BASE_USER_RESOURE + shop.getGoods_icon())
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(coverImg);
            shopNameTv.setText(shop.getGoods_name() + "");

            shopDsTv.setText(tips[shop.getGoods_type()]+shop.getGoods_num()+ (shop.getGoods_type()==4?"通常由设计师购买":""));

            payMoneyTv.setText("￥" + shop.getGoods_price() + "元");
            tipsTv.setText("注："+shop.getGoods_detail() + "");
            buyRsIv.setImageResource(icoRes[shop.getGoods_type()]);
            Logger.e(shop.getGoods_type() + "");
            if (shop.getGoods_type() == 5)
                getmypoin();

        } catch (Exception e) {
        }

    }


    @OnClick({R.id.toolbar_left_title, R.id.button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.button_ll:
                buyVipGoods(shop.getGoods_id() + "");
                break;
        }
    }

    private void buyVipGoods(String goods_id) {
        RxRetrofitClient.getInstance(this).buyVipGoods(goods_id, new Observer<BuyVipGoodsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderConfirmVipActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(BuyVipGoodsBean buyVipGoodsBean) {

                if (buyVipGoodsBean.getCode() == 1) {
                    startActivityForResult(new Intent(OrderConfirmVipActivity.this, OrderBuyVipActivity.class).putExtra("buyVipGoodsBean", buyVipGoodsBean), REQUEST_RESULT);
                } else
                    Utils.toast(OrderConfirmVipActivity.this, buyVipGoodsBean.getMessage());

            }
        });
    }

    private void getUserInfo() {


        RxRetrofitClient.getInstance(OrderConfirmVipActivity.this).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderConfirmVipActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    typeCount[1] = userBaseBean.getData().getShop_own_cnt();
                    typeCount[2] = userBaseBean.getData().getTerminal_own_cnt();
                    typeCount[3] = userBaseBean.getData().getModule_own_cnt();
                    typeCount[4] = userBaseBean.getData().getWork_left_cnt();
                    //    typeCount[5]= userBaseBean.getData().get();
                    shop.getGoods_num();
                    buyRs.setText(typeNames[shop.getGoods_type()] + (typeCount[shop.getGoods_type()] + shop.getGoods_num())+"个");
                }
            }


        });
    }

    private void getmypoin() {
        RxRetrofitClient.getInstance(this).getmypoin(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderConfirmVipActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    typeCount[5] = Integer.parseInt(vCodeBenan.getData());
                    shop.getGoods_num();
                    buyRs.setText(typeNames[shop.getGoods_type()] + (typeCount[shop.getGoods_type()] + shop.getGoods_num()));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_RESULT) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
