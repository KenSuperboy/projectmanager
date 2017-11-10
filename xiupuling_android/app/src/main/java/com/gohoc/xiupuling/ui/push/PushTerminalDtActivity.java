package com.gohoc.xiupuling.ui.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.wuxiaolong.androidutils.library.AppUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushTerminalDtActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.shop_banner)
    Banner shopBanner;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.call_phone)
    ImageView callPhone;
    @BindView(R.id.below_location_tv)
    TextView belowLocationTv;
    @BindView(R.id.group_tv)
    TextView groupTv;
    @BindView(R.id.business_tv)
    TextView businessTv;
    @BindView(R.id.news_ll)
    LinearLayout newsLl;
    @BindView(R.id.move_shop_ll)
    LinearLayout moveShopLl;
    @BindView(R.id.no_pic_rr)
    RelativeLayout noPicRr;
    private ShopDetailsBean shopDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_terminal_dt);
        ButterKnife.bind(this);
        toolbarTitle.setText("终端信息");
        setStatusColor(R.color.colorPrimary);
        getShopDetail(getIntent().getStringExtra("id"));
    }

    @OnClick({R.id.toolbar_left_title, R.id.move_shop_ll, R.id.call_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.move_shop_ll:
                startActivity(new Intent(PushTerminalDtActivity.this, PlayLogActivity.class).putExtra("tId", getIntent().getStringExtra("t_id"))
                        .putExtra("range_id", getIntent().getStringExtra("range_id")));
                break;
            case R.id.call_phone:
                if (!TextUtils.isEmpty(shopDetailsBeans.getData().getShop_telephone())) {
                    AppUtils.actionDial(PushTerminalDtActivity.this, shopDetailsBeans.getData().getShop_telephone());
                    callPhone.setImageResource(R.mipmap.icon_mdxxi_phone);
                } else
                    callPhone.setImageResource(R.mipmap.icon_mdxxi_phone1);
                break;
        }
    }

    public void getShopDetail(String id) {
        RxRetrofitClient.getInstance(this).getShopDetail(id, new Observer<ShopDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushTerminalDtActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ShopDetailsBean shopDetailsBean) {
                if (shopDetailsBean.getCode() == 1) {
                    shopDetailsBeans = shopDetailsBean;
                    initBanner(shopDetailsBean.getData().getShopphotos());
                    initData(shopDetailsBean);
                }


            }
        });
    }

    private void initData(ShopDetailsBean shopDetailsBean) {

        shopNameTv.setText(shopDetailsBean.getData().getShop_name() + "");
        locationTv.setText(shopDetailsBean.getData().getShop_address() + "");
        belowLocationTv.setText(shopDetailsBean.getData().getProvince().getName() + shopDetailsBean.getData().getCity().getName() + shopDetailsBean.getData().getRegion().getName() + "");
        groupTv.setText(shopDetailsBean.getData().getGroup().getGroup_name() + "");
        businessTv.setText(shopDetailsBean.getData().getBusiness().getBusiness_name() + "");
        if (!TextUtils.isEmpty(shopDetailsBeans.getData().getShop_telephone()))
            callPhone.setImageResource(R.mipmap.icon_mdxxi_phone);
        else
            callPhone.setImageResource(R.mipmap.icon_mdxxi_phone1);

    }

    private void initBanner(final List<ShopDetailsBean.DataBean.ShopphotosBean> shopphotosBean) {
        if (shopphotosBean.size() < 1)
            noPicRr.setVisibility(View.VISIBLE);
        else
            noPicRr.setVisibility(View.GONE);

        shopBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        shopBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                // Logger.d(path);
                ShopDetailsBean.DataBean.ShopphotosBean shopphotosBean1 = (ShopDetailsBean.DataBean.ShopphotosBean) path;
                Glide.with(context).load(NetConstant.BASE_USER_RESOURE + shopphotosBean1.getPhoto_url()).fitCenter().into(imageView);
            }
        });
        shopBanner.setImages(shopphotosBean);
        //设置banner动画效果
        shopBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        shopBanner.isAutoPlay(true);
        //设置轮播时间
        shopBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        shopBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        shopBanner.start();
    }
}
