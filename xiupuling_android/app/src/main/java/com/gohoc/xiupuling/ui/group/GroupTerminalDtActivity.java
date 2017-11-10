package com.gohoc.xiupuling.ui.group;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.TerminalBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.requirement.MyWorksActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.AppUtils;
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


public class GroupTerminalDtActivity extends BasicActivity {

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
    @BindView(R.id.termianl_no_tv)
    TextView termianlNoTv;
    @BindView(R.id.group_pic_iv)
    CircleImageView groupPicIv;
    @BindView(R.id.group_name_tv)
    TextView groupNameTv;
    @BindView(R.id.group_cover_ll)
    LinearLayout groupCoverLl;
    @BindView(R.id.shop_industry_tv)
    TextView shopIndustryTv;
    @BindView(R.id.terminal_push_lv)
    LinearLayout terminalPushLv;
    TerminalBean terminal;
    @BindView(R.id.check_auth_ll)
    LinearLayout checkAuthLl;
    @BindView(R.id.no_pic_rr)
    RelativeLayout noPicRr;
    private String portrait;
    private TerminalBean terminalBeans;
    private UserBaseBean userBaseBean;
    private SystemInfoBean sb;
    private ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_terminal_dt);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("终端信息");
        EventBus.getDefault().register(this);
        try {
            getTerminalDetail(getIntent().getStringExtra("terminaId"));
            portrait = getIntent().getStringExtra("portrait");
            userBaseBean = Credential.getInstance().getCurUser(this);
            mCache = ACache.get(this);
            sb = (SystemInfoBean) mCache.getAsObject("SystemInfoBean");
        } catch (Exception e) {

        }


    }

    @OnClick({R.id.toolbar_left_title, R.id.terminal_push_lv, R.id.call_phone, R.id.check_auth_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                GroupTerminalDtActivity.this.finish();
                break;
            case R.id.terminal_push_lv:
                PushNormlBean pushNormlBean = new PushNormlBean();
                pushNormlBean.setIndexFlage(1);
                pushNormlBean.setShopName(terminal.getData().getShop().getShop_name());
                pushNormlBean.setTermianlNo(terminal.getData().getTerminal_no());
                pushNormlBean.setShopStar(terminal.getData().getShop().getShop_star_level() );
                pushNormlBean.setTerminanlId(terminal.getData().getTerminal_id());
                pushNormlBean.setFree(terminalBeans.getData().getUser_id().equals(userBaseBean.getData().getUser_id()));
                pushNormlBean.setOrientation(terminalBeans.getData().getTerm_orientation());

                Logger.e(terminal.getData().getTerm_orientation() + "");
                //    pushNormlBean.setOrientation();
                startActivity(new Intent(GroupTerminalDtActivity.this, MyWorksActivity.class).putExtra("pushNormlBean", pushNormlBean).putExtra("type", 1));
                break;
            case R.id.call_phone:
                if (!TextUtils.isEmpty(terminalBeans.getData().getShop().getShop_telephone())) {
                    AppUtils.actionDial(GroupTerminalDtActivity.this, terminalBeans.getData().getShop().getShop_telephone());
                    callPhone.setImageResource(R.mipmap.icon_mdxxi_phone);
                } else
                    callPhone.setImageResource(R.mipmap.icon_mdxxi_phone1);
                break;
            case R.id.check_auth_ll:
                startActivity(new Intent(GroupTerminalDtActivity.this, WebViewActivity.class).putExtra("url", sb.getData().getAboutInformationwrong()).putExtra("title", "信息不符举报"));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GroupEvent(Event.GroupEvent message) {
        getTerminalDetail(getIntent().getStringExtra("terminaId"));
    }

    private void getTerminalDetail(String terminaId) {
        RxRetrofitClient.getInstance(GroupTerminalDtActivity.this).getTerminalDetail(terminaId, new Observer<TerminalBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupTerminalDtActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TerminalBean terminalBean) {
                if (terminalBean.getCode() == 1) {
                    terminalBeans = terminalBean;
                    terminal = terminalBean;
                    shopNameTv.setText(terminal.getData().getShop().getShop_name() + "");
                    locationTv.setText(terminal.getData().getShop().getShop_address() + "");
                    belowLocationTv.setText(terminal.getData().getShop().getProvince() +
                            terminal.getData().getShop().getCity() +
                            terminal.getData().getShop().getDistrict());

                    groupTv.setText(terminal.getData().getShop().getGroup_name() + "");
                    businessTv.setText(terminal.getData().getShop().getBusiness_name() + "");
                    initBanner(terminal.getData().getShop().getShop_photos());

                    termianlNoTv.setText(terminal.getData().getTerminal_no() + "号终端");
                    groupNameTv.setText(terminal.getData().getNick_name());
                    Glide.with(GroupTerminalDtActivity.this)
                            .load(NetConstant.BASE_USER_RESOURE + terminal.getData().getPortrait_url()+ Utils.getThumbnail(100,100))
                            // .placeholder(R.mipmap.icon_dengru_touxiang)
                            .error(R.mipmap.icon_port_home)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter()
                            .into(groupPicIv);


                    // portrait
                }
            }
        });
    }

    private void initBanner(final List<TerminalBean.DataBean.ShopBean.ShopPhotosBean> terminalBean) {
        if(terminalBean.size()<1)
            noPicRr.setVisibility(View.VISIBLE);
        else
            noPicRr.setVisibility(View.GONE);

        shopBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        shopBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {

                TerminalBean.DataBean.ShopBean.ShopPhotosBean shopphotosBean = (TerminalBean.DataBean.ShopBean.ShopPhotosBean) path;
                Glide.with(context).load(NetConstant.BASE_USER_RESOURE + shopphotosBean.getPhoto_url()).fitCenter().into(imageView);

                Logger.e(NetConstant.BASE_USER_RESOURE + shopphotosBean.getPhoto_url());
            }
        });
        shopBanner.setImages(terminalBean);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
