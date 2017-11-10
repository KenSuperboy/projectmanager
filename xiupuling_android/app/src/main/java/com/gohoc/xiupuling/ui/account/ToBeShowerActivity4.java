package com.gohoc.xiupuling.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.Utils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class ToBeShowerActivity4 extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.save_button_ll)
    LinearLayout saveButtonLl;
    private Integer[] pics = {R.mipmap.img_zhanzhutequanjieshao, R.mipmap.img_zhuzhan_tips_icon_3, R.mipmap.img_zhuzhan_tips_icon_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_shower4);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("成为展主");
        initBanner(Arrays.asList(pics));
    }

    @OnClick({R.id.toolbar_left_title, R.id.save_button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.save_button_ll:
                applyforjz();
                //  startActivity(new Intent(ToBeTerminalerActivity5.this, ToBeTerminalerActivity2.class));
                break;
        }
    }

    private void initBanner(final List<Integer> picList) {

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load((Integer) path).centerCrop().into(imageView);
            }
        });
        banner.setImages(picList);
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
    }

    private void applyforjz() {
        RxRetrofitClient.getInstance(this).applyforzz(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ToBeShowerActivity4.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(ToBeShowerActivity4.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    Credential.getInstance().updateUserInfo(ToBeShowerActivity4.this);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
