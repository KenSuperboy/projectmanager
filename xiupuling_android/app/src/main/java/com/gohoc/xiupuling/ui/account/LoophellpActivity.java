package com.gohoc.xiupuling.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoophellpActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Integer[] imglist;
    private boolean isFulllScreen = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loophellp);
        ButterKnife.bind(this);

        imglist = (Integer[]) getIntent().getExtras().get("imglist");
        setMtitle(getIntent().getStringExtra("title"));
        toolbarTitle.setText(getMtitle());
        initBanner();
        full(isFulllScreen,toolbar);
    }

    @OnClick({R.id.toolbar_left_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
        }
    }


    private void initBanner() {
/*    for (int a=0;a<imglist.length;a++)
        Logger.e(imglist[a]+"");*/
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Logger.e(path + "");
                imageView.setImageResource((Integer) path);
                // Glide.with(context).load(R.mipmap.img_mendianhezhongdua).centerCrop().into(imageView);
            }
        });
        banner.setImages(Arrays.asList(imglist));
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                isFulllScreen=!isFulllScreen;
                Logger.e(isFulllScreen+"");
                full(isFulllScreen,toolbar);
            }
        });
    }




}
