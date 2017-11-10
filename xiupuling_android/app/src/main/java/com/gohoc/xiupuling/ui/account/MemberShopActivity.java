package com.gohoc.xiupuling.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.MemberShopAdater;
import com.gohoc.xiupuling.bean.GoodsBean;
import com.gohoc.xiupuling.bean.ShopViewBean;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class MemberShopActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.banner)
    Banner shopBanner;
    @BindView(R.id.list)
    RecyclerView list;
    private ArrayList<ShopViewBean> shops;
    private MemberShopAdater adater;
    private int[] icoRes = { R.mipmap.icon_sc_sirenpindao, R.mipmap.icon_sc_lingpai ,R.mipmap.vipxx,};
    private String[] typeNames = { "增加私人电台拥有数",  "增加令牌拥有数","终端VIP码"};//type:3   5   6
    private ArrayList<String> imglist = new ArrayList<>();
    private ACache mACache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_shop);
        ButterKnife.bind(this);
        toolbarTitle.setText("会员商城");
        setStatusColor(R.color.colorPrimary);
        mACache = ACache.get(this);
        initBanner();
        getVipGoodsList();


    }

    private void getVipGoodsList() {
        RxRetrofitClient.getInstance(this).getVipGoodsList(new Observer<GoodsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MemberShopActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Logger.e(e.toString());
                }
            }

            @Override
            public void onNext(GoodsBean goodsBean) {
                if (goodsBean.getCode() == 1) {
                    doResult(goodsBean);
                }

            }
        });
    }

    private void doResult(GoodsBean goodsBean) {
        shops = new ArrayList<>();

        for (int a = 0; a < 3; a++) {
            shops.add(new ShopViewBean().setIco(icoRes[a]).setTitle(typeNames[a]));
        }

        for (int b = 0; b < goodsBean.getData().size(); b++) {
            if (goodsBean.getData().get(b).getGoods_type() == 3)
                shops.get(0).getShoplist().add(goodsBean.getData().get(b));
            if (goodsBean.getData().get(b).getGoods_type() == 5)
                shops.get(1).getShoplist().add(goodsBean.getData().get(b));
            if (goodsBean.getData().get(b).getGoods_type() == 6)
                shops.get(2).getShoplist().add(goodsBean.getData().get(b));
        }
        initList();
    }

    private void initList() {
        adater = new MemberShopAdater(this, shops);
        list.setAdapter(adater);
        list.setLayoutManager(new LinearLayoutManager(this));

    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        if (getIntent().getIntExtra("backType", 0) == 1)
            goback();
        else
            finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getIntent().getIntExtra("backType", 0) == 1)
                goback();
            else
                finish();
        }
        return false;
    }

    private void goback() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        EventBus.getDefault().post(new Event.MainIndex(3));
        finish();
    }

    private void initBanner() {
        try {
            SystemInfoBean systemInfoBean = (SystemInfoBean) mACache.getAsObject("SystemInfoBean");
            imglist.add(systemInfoBean.getData().getBannarUrl1());
            imglist.add(systemInfoBean.getData().getBannarUrl2());
            imglist.add(systemInfoBean.getData().getBannarUrl3());

        } catch (Exception e) {
            imglist.add("xpl/ios/goods/goodsshopbanner1.jpg");
            imglist.add("xpl/ios/goods/goodsshopbanner2.jpg");
            imglist.add("xpl/ios/goods/goodsshopbanner3.jpg");
        }


        shopBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        shopBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(NetConstant.BASE_USER_RESOURE + path).centerCrop().into(imageView);
            }
        });
        shopBanner.setImages(imglist);
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
