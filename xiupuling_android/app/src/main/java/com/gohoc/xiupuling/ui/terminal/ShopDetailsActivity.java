package com.gohoc.xiupuling.ui.terminal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BannerBean;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.bean.ShopLimtBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.account.MemberAuthorizationActivity;
import com.gohoc.xiupuling.ui.account.MemberShopActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.AppUtils;
import com.wuxiaolong.androidutils.library.VersionUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class ShopDetailsActivity extends BasicActivity {


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
    @BindView(R.id.edit_shop_ll)
    LinearLayout editShopLl;
    @BindView(R.id.move_shop_ll)
    LinearLayout moveShopLl;
    @BindView(R.id.menu_title)
    TextView menuTitle;
    @BindView(R.id.news_ll)
    LinearLayout newsLl;
    @BindView(R.id.no_pic_rr)
    RelativeLayout noPicRr;
    private String shopId, terminal_id, terminalNo;
    private static int SHOP_REQUEST_RESULT = 1000;
    private ShopDetailsBean shopDetailsBeans;
    private int type = 0;
    private Window window;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("所属门店");
        EventBus.getDefault().register(this);

        try {
            type = getIntent().getIntExtra("type", 0);
            shopId = getIntent().getStringExtra("shopId");
            getShopDetail(shopId);
            //getShopPhotos(shopId);
            terminal_id = getIntent().getStringExtra("terminal_id");
            terminalNo = getIntent().getStringExtra("terminalNo");
            if (type == 1) {
                editShopLl.setVisibility(View.GONE);
                newsLl.setVisibility(View.VISIBLE);
                menuTitle.setText("添加终端");
                menuTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

        } catch (Exception e) {
            Logger.e(e.toString());
            Utils.toast(ShopDetailsActivity.this, "数据异常");
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.call_phone, R.id.edit_shop_ll, R.id.move_shop_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                ShopDetailsActivity.this.finish();
                break;
            case R.id.call_phone:
                if (!TextUtils.isEmpty(shopDetailsBeans.getData().getShop_telephone()))
                    AppUtils.actionDial(ShopDetailsActivity.this, shopDetailsBeans.getData().getShop_telephone() + "");
                break;
            case R.id.edit_shop_ll:
                startActivity(new Intent(ShopDetailsActivity.this, AddShopActivity.class).putExtra("edit", shopDetailsBeans.getData()));
                break;
            case R.id.move_shop_ll:
                if (type == 0)
                    startActivityForResult(new Intent(ShopDetailsActivity.this, SettingBelongShopActivity.class).putExtra("shopId", shopDetailsBeans.getData().getShop_id()).putExtra("edit", 1).putExtra("terminalNo", terminalNo),
                            SHOP_REQUEST_RESULT);
                else
                    checkTerminalOwnCntRight();

                break;

        }
    }

    private void checkTerminalOwnCntRight() {
        RxRetrofitClient.getInstance(this).checkTerminalOwnCntRight(new Observer<ShopLimtBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ShopDetailsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ShopLimtBean shopLimtBean) {
                if (shopLimtBean.getCode() == 1) {
                    startActivity(new Intent(ShopDetailsActivity.this, AddTerminalActivity.class).putExtra("shopDetailsBeans", shopDetailsBeans));
                } else {
                    showShopPop(shopLimtBean.getTerminalcnt() + "", shopLimtBean.getTerminalowncnt() + "", "终端");
                }
            }
        });
    }

    private void showShopPop(String count, String ownCount, String title) {
        dialog = new AlertDialog.Builder(this,
                R.style.dialogTips).create();
        dialog.show();
        window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(false);
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容

        window.setContentView(R.layout.dialog_shop_limt_alert);

        window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(ShopDetailsActivity.this, MemberShopActivity.class));

            }
        });
        window.findViewById(R.id.check_auth_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(ShopDetailsActivity.this, MemberAuthorizationActivity.class));
            }
        });
        window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        SpanUtils su = new SpanUtils();
        su.append("您目前只能管理" + count + "个" + title + "， 如果需管理更多门店，请前往会员商城或参与")
                .append("官方活动").setForegroundColor(Color.parseColor("#38b2fd"))
                .append("扩充拥有数。");
        TextView tips_title_tv = (TextView) window.findViewById(R.id.tips_title_tv);
        tips_title_tv.setText(su.create());
        TextView shop_count_tv = (TextView) window.findViewById(R.id.shop_count_tv);
        su = new SpanUtils();
        su.append(title + " ").append(ownCount).setForegroundColor(Color.parseColor("#38b2fd"));
        shop_count_tv.setText(su.create());
        su = new SpanUtils();
        TextView check_auth_tv = (TextView) window.findViewById(R.id.check_auth_tv);
        check_auth_tv.setText(su.append(" 查询其它").setForegroundColor(Color.parseColor("#929292")).append("会员权限").setForegroundColor(Color.parseColor("#38b2fd")).create());
    }

    public void getShopPhotos(String id) {
        RxRetrofitClient.getInstance(ShopDetailsActivity.this).getShopPhotos(id, new Observer<BannerBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ShopDetailsActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(BannerBean bannerBean) {
                if (bannerBean.getCode() == 1) {

                }

            }
        });
    }

    public void getShopDetail(String id) {
        RxRetrofitClient.getInstance(ShopDetailsActivity.this).getShopDetail(id, new Observer<ShopDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ShopDetailsActivity.this, "修改失败，请检查网络是否正常");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshTerminalListEvent(Event.RefreshTerminalListEvent message) {
        getShopDetail(shopId);
    }

    private void initBanner(final List<ShopDetailsBean.DataBean.ShopphotosBean> shopphotosBean) {
        if (shopphotosBean.size() < 1) {
            noPicRr.setVisibility(View.VISIBLE);
            return;
        } else
            noPicRr.setVisibility(View.GONE);


        shopBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        shopBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                // Logger.d(path);
                ShopDetailsBean.DataBean.ShopphotosBean shopphotosBean1 = (ShopDetailsBean.DataBean.ShopphotosBean) path;
                Glide.with(context).load(NetConstant.BASE_USER_RESOURE + shopphotosBean1.getPhoto_url()).fitCenter().into(imageView);
                Logger.e(NetConstant.BASE_USER_RESOURE + shopphotosBean1.getPhoto_url() + "");
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

    public void updateTerminal(final String tNo, final String sId) {


        RxRetrofitClient.getInstance(ShopDetailsActivity.this).updateTerminal(terminal_id, sId, tNo, VersionUtil.getVersionCode(ShopDetailsActivity.this) + "", null, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ShopDetailsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(ShopDetailsActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshTerminalListEvent());
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SHOP_REQUEST_RESULT) {
                String terminalNo = data.getStringExtra("terminalNo");
                String shopId = data.getStringExtra("shopId");
                updateTerminal(terminalNo, shopId);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
