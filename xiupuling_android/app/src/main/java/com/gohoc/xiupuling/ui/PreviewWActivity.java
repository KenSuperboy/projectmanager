package com.gohoc.xiupuling.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.WorksDetails;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.MVidioView;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class PreviewWActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.banner1)
    Banner banner1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tipsTitle)
    TextView tipsTitle;
    @BindView(R.id.tipsBottom)
    TextView tipsBottom;
    @BindView(R.id.banner_ll)
    FrameLayout bannerLl;
    @BindView(R.id.videoRl)
    RelativeLayout videoRl;
    @BindView(R.id.content_frame)
    RelativeLayout mFrameContenLayout;
    @BindView(R.id.m_video_view)
    MVidioView mVidioView;
    @BindView(R.id.view_frame)
    FrameLayout mFrameLayout;
    @BindView(R.id.crv)
    RelativeLayout crv;
    @BindView(R.id.iv_video)
    ImageView iv_video;
    @BindView(R.id.iv_shuiying)
    ImageView iv_shuiying;
    private WorksDetails worksDetail;
    private String work_id;
    private boolean isFulllScreen = true;
    List<WorksDetails.DataBean.MateriallistBean> temp;
    private int mWidth, mHeight;
    int work_type;
    private int w;
    private int h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_w);
        ButterKnife.bind(this);
        work_id = getIntent().getStringExtra("work_id");
        workdetail(work_id);
        toolbarTitle.setText("预览");
        //toolbar.setVisibility(View.GONE);
        full(isFulllScreen, toolbar);
        mVidioView.setMediaController(new MediaController(this));
        mVidioView.setOnCompletionListener(new MyPlayerOnCompletionListener());
        mVidioView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                             @Override
                                             public void onPrepared(MediaPlayer mp) {


                                                 //FixMe 获取视频资源的宽度
                                                 int mVideoWidth = mp.getVideoWidth();
                                                 //FixMe 获取视频资源的高度
                                                 int mVideoHeight = mp.getVideoHeight();
                                                 if (work_type != 3) {
                                                     if (mVideoHeight > 0 && mVideoWidth > 0) {
                                                         mVidioView.getHolder().setFixedSize(w, h);
                                                         //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                                                         mVidioView.setMeasure(w, h);
                                                         //FixMe 请求调整
                                                         mVidioView.requestLayout();
                                                     }
                                                 } else {
                                                     //FixMe 在资源尺寸可以播放观看时处理
                                                     ViewGroup parent = (ViewGroup) mFrameLayout.getParent();
                                                     int height = parent.getHeight();
                                                     int width = parent.getWidth();
                                                     float scale = (float) mVideoHeight / (float) mVideoWidth;


                                                     mVideoWidth = width;
                                                     mVideoHeight = (int) (mVideoWidth * scale);
                                                     if (mVideoHeight > height) {
                                                         mVideoHeight = height;
                                                     }
                                                     mVideoWidth = (int) (mVideoHeight / scale);
                                                     mVidioView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                                                     //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                                                     mVidioView.setMeasure(mVideoWidth, mVideoHeight);
                                                     //FixMe 请求调整
                                                     mVidioView.requestLayout();
                                                 }

                                                 mVidioView.start();

                                             }


                                         }

        );

        crv.setOnTouchListener(new View.OnTouchListener()

                               {
                                   @Override
                                   public boolean onTouch(View v, MotionEvent event) {
                                       if (v.getId() != R.id.banner && v.getId() != R.id.banner1 && v.getId() != R.id.tipsBottom) {
                                           isFulllScreen = !isFulllScreen;
                                           Logger.e(isFulllScreen + "");
                                           full(isFulllScreen, toolbar);
                                       }
                                       return false;
                                   }
                               }

        );

    }


    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.i("kekegdsz", "播放完成了");
        }

    }


    public void workdetail(String id) {
        RxRetrofitClient.getInstance(this).workdetail(id, new Observer<WorksDetails>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PreviewWActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(WorksDetails worksDetails) {
                if (worksDetails.getCode() == 1) {
                    worksDetail = worksDetails;
                    temp = new ArrayList<WorksDetails.DataBean.MateriallistBean>();
                    temp.addAll(worksDetail.getData().getMateriallist());
                    EventBus.getDefault().post(new Event.RefreshPushListEvent(null));
                    initDatas();
                }

            }
        });

    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void initDatas() {
        EventBus.getDefault().post(new Event.RefreshPushListEvent(null));
        //处理横竖屏
        //计算并设置宽度高度
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        int w1 = wm.getDefaultDisplay().getWidth() - dip2px(this, 32);
        if (worksDetail.getData().getOrientation() != 1) {
            iv_video.setImageResource(R.mipmap.icon_hengbanui);
            tipsTitle.setText("横屏预览区域");
            float scale = 9f / 16f;
            mWidth = w1;
            mHeight = (int) (w1 * scale);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
        } else {
            iv_video.setImageResource(R.mipmap.icon_shubanui);
            tipsTitle.setText("竖屏预览区域");
            float scale = 16f / 9f;
            mWidth = w1 / 2;
            mHeight = (int) ((w1 / 2) * scale);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
        }

        work_type = worksDetail.getData().getWork_type();
        switch (worksDetail.getData().getWork_type()) {
            case 1://海报
                mVidioView.setVisibility(View.GONE);
                banner.setVisibility(View.VISIBLE);
                initBanner(worksDetail.getData().getMateriallist());
                initBanner1(worksDetail.getData().getMateriallist());//悬浮的
            case 2:
                mVidioView.setVisibility(View.GONE);
                banner.setVisibility(View.VISIBLE);
                initBanner(worksDetail.getData().getMateriallist());
                initBanner1(worksDetail.getData().getMateriallist());
                break;
            case 3://视频  水印图片

                mVidioView.setVisibility(View.VISIBLE);
                banner.setVisibility(View.VISIBLE);
                initPlayer();

                //initBanner(worksDetail.getData().getMateriallist());
                //initBanner1(worksDetail.getData().getMateriallist());

                getShuiyinPosition();

                Glide.with(mContext).load(BASE_USER_RESOURE + shuiyingString).fitCenter().into(iv_shuiying);

                if(shuiying){
                    setIv_shuiying();
                }
                break;
            case 4:
                banner.setVisibility(View.VISIBLE);
                if (worksDetail.getData().getMateriallist().size() > 1) {
                    mVidioView.setVisibility(View.VISIBLE);
                    int x = 0;
                    int y = 0;
                    if (worksDetail.getData().getOrientation() == 1) {
                        //竖屏
                        x = 1080;
                        y = 1920;
                    } else {
                        //横屏
                        x = 1920;
                        y = 1080;
                    }

                    w = (int) (((float) temp.get(1).getMaterial_width() / (float) x) * (float) mWidth);
                    h = (int) (((float) temp.get(1).getMaterial_height() / (float) y) * (float) mHeight);
                    int left = (int) (((float) temp.get(1).getMaterial_start_x() / (float) x) * (float) mWidth);
                    int top = (int) (((float) temp.get(1).getMaterial_start_y() / (float) y) * (float) mHeight);
                    mVidioView.setMeasure(w, h);
                    //FixMe 请求调整
                    mVidioView.requestLayout();
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
                    layoutParams.setMargins(left, top, mWidth - w - left, mHeight - h - top);
                    mFrameLayout.setLayoutParams(layoutParams);

                    String url1 = getPlayerUrl();
                    if(!TextUtils.isEmpty(url1)){
                        mVidioView.setVideoURI(Uri.parse(url1));
                        mVidioView.requestFocus();
                    }
                }
                initBanner(temp);
                initBanner1(temp);
                break;
        }
    }

    boolean shuiying;
    private String shuiyingString="";
    private int getShuiyinPosition()
    {
        int shuiyinPosition=0;
        for (int i=0;i<worksDetail.getData().getMateriallist().size();i++){
            if(worksDetail.getData().getMateriallist().get(i).getFiletype().equals("3")){
                shuiyinPosition=i;
                shuiying=true;
                shuiyingString=worksDetail.getData().getMateriallist().get(i).getMaterial_store_url();
            }
        }
        return shuiyinPosition;
    }

    //设置水印图片位置大小
    int mX=0,mY=0,CurrW=0,CurrH=0;
    private void setIv_shuiying()
    {
        mX=worksDetail.getData().getMateriallist().get(getShuiyinPosition()).getMaterial_start_x();
        mY=worksDetail.getData().getMateriallist().get(getShuiyinPosition()).getMaterial_start_y();
        CurrW=worksDetail.getData().getMateriallist().get(getShuiyinPosition()).getMaterial_width();
        CurrH=worksDetail.getData().getMateriallist().get(getShuiyinPosition()).getMaterial_height();

        iv_shuiying.post(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                int y = 0;
                if (worksDetail.getData().getOrientation() == 1) {
                    //竖屏
                    x = 1080;
                    y = 1920;
                } else {
                    //横屏
                    x = 1920;
                    y = 1080;
                }
                //videoWidth = video.getCurrW();
                //viewHeight = video.getCurrH();
                //mFrameLayout.setVisibility(View.VISIBLE);
                int left = Math.round((((float) mX / (float) x) * (float) mWidth));
                int top = Math.round((((float) mY / (float) y) * (float) mHeight));
                LogUtil.d("宽度："+CurrW+"===高度："+CurrH+"：视频获取："+left+"====:"+top);

                CurrW = Math.round((((float) CurrW / (float) x) * (float) mWidth));
                CurrH = Math.round((((float) CurrH / (float) y) * (float) mHeight));

                RelativeLayout.LayoutParams mFrameLayout_ivLayoutParams = (RelativeLayout.LayoutParams) iv_shuiying.getLayoutParams();
                mFrameLayout_ivLayoutParams.width=CurrW;
                mFrameLayout_ivLayoutParams.height=CurrH;
                iv_shuiying.setLayoutParams(mFrameLayout_ivLayoutParams);
                iv_shuiying.invalidate();

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_shuiying.getLayoutParams();
                layoutParams.setMargins(left, top,0, 0);
                iv_shuiying.setLayoutParams(layoutParams);

            }
        });
    }

    private void initBanner(List<WorksDetails.DataBean.MateriallistBean> list) {
/*    for (int a=0;a<imglist.length;a++)
        Logger.e(imglist[a]+"");*/

        List<WorksDetails.DataBean.MateriallistBean> tempList=new ArrayList<>();

        for (int i=0;i<list.size();i++){
            if(work_type==2||work_type==1||work_type==4){
                if(list.get(i).getFiletype().equals("2")){
                    //图片文件
                    tempList.add(list.get(i));
                }
            }else if(work_type==3){//水印
                if(list.get(i).getFiletype().equals("3")){
                    //图片文件
                    tempList.add(list.get(i));
                }
            }

        }

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Logger.e(path + "=====");

                WorksDetails.DataBean.MateriallistBean materiallistBean = (WorksDetails.DataBean.MateriallistBean) path;
                //LogUtil.d("图片路径0000："+Constant.NetConstant.BASE_USER_RESOURE+materiallistBean.getMaterial_store_url()+ Utils.getThumbnail(202,202));
                /*Glide.with(context)
                        .load(Constant.NetConstant.BASE_USER_RESOURE+materiallistBean.getMaterial_store_url()+ Utils.getThumbnail(300, 300))
                        .placeholder(R.mipmap.df_logo)
                        //.error(R.mipmap.icon_dengru_touxiang)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);*/

                Glide.with(context).load(BASE_USER_RESOURE + materiallistBean.getMaterial_store_url()).fitCenter().into(imageView);
            }
        });
        banner.setImages(tempList);
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
        //banner.setOnTouchListener(this);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                isFulllScreen = !isFulllScreen;
//                Logger.e(isFulllScreen + "");
//                full(isFulllScreen, toolbar);
            }
        });
    }

    private MediaPlayer mMediaPlayer;
    private void playMediaStart(String mediaPath)
    {
        LogUtil.d("音乐播放33333333333333333");
        mMediaPlayer=new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("TAG","音乐已经播放完毕");
                playMediaFinish();
            }
        });
        try {
            mMediaPlayer.setDataSource(mediaPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    private void playMediaFinish()
    {
        LogUtil.d("停止播放音乐");
        if(mMediaPlayer!=null){
            LogUtil.d("停止播放音乐1111111111");
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }


    private void initBanner1(final List<WorksDetails.DataBean.MateriallistBean> list) {
        List<WorksDetails.DataBean.MateriallistBean> tempList=new ArrayList<>();

        for (int i=0;i<list.size();i++){
            if(work_type==2||work_type==1||work_type==4){
                if(list.get(i).getFiletype().equals("2")){
                    //图片文件
                    tempList.add(list.get(i));
                }

                if(list.get(i).getFiletype().equals("1")){
                    //音乐文件
                    if(mMediaPlayer==null){
                        playMediaStart(BASE_USER_RESOURE+list.get(i).getMaterial_store_url());
                    }
                }

            }else if(work_type==3){
                if(list.get(i).getFiletype().equals("3")){
                    //图片文件
                    tempList.add(list.get(i));
                }
            }
        }

        banner1.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner1.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Logger.e(path + "");
                WorksDetails.DataBean.MateriallistBean materiallistBean = (WorksDetails.DataBean.MateriallistBean) path;
                //LogUtil.d("图片路径1111："+Constant.NetConstant.BASE_USER_RESOURE+materiallistBean.getMaterial_store_url()+ Utils.getThumbnail(202,202));
                /*Glide.with(context)
                        .load(Constant.NetConstant.BASE_USER_RESOURE+materiallistBean.getMaterial_store_url()+ Utils.getThumbnail(300, 300))
                        .placeholder(R.mipmap.df_logo)
                        //.error(R.mipmap.icon_dengru_touxiang)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);*/

                Glide.with(context).load(BASE_USER_RESOURE + materiallistBean.getMaterial_store_url()).fitCenter().into(imageView);
            }
        });
        banner1.setImages(tempList);
        //设置banner动画效果
        banner1.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner1.isAutoPlay(true);
        //设置轮播时间
        banner1.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner1.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner1.start();
        //banner.setOnTouchListener(this);
        banner1.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                isFulllScreen = !isFulllScreen;
//                Logger.e(isFulllScreen + "");
//                full(isFulllScreen, toolbar);
            }
        });


    }


    private void initPlayer() {
        String url = null;
        List<WorksDetails.DataBean.MateriallistBean> materiallist = worksDetail.getData().getMateriallist();
        if (materiallist == null) return;
        for (WorksDetails.DataBean.MateriallistBean bean : materiallist) {
            if (bean.getFiletype().equals("0")) {
                url = BASE_USER_RESOURE + bean.getMaterial_store_url();
                break;
            }
        }
        if (url == null) return;
        mVidioView.setVideoURI(Uri.parse(url));
        //开始播放视频
        mVidioView.start();

    }

    private String getPlayerUrl() {
        String url = "";
        List<WorksDetails.DataBean.MateriallistBean> materiallist = worksDetail.getData().getMateriallist();
        if (materiallist == null) return url;
        for (WorksDetails.DataBean.MateriallistBean bean : materiallist) {
            if (bean.getFiletype().equals("0")) {
                url = BASE_USER_RESOURE + bean.getMaterial_store_url();
                break;
            }
        }
        return url;
    }


    @OnClick({R.id.toolbar_left_title, R.id.tipsBottom})//,R.id.iv_video
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                LogUtil.d("点击返回按钮");

                /*RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_shuiying.getLayoutParams();
                layoutParams.setMargins(0, 0,0,0);
                iv_shuiying.setLayoutParams(layoutParams);*/

                playMediaFinish();
                finish();
                break;
            case R.id.tipsBottom:
                iv_video.setVisibility(View.GONE);
                bannerLl.setBackgroundResource(R.color.touming);
                switch (worksDetail.getData().getWork_type()) {
                    case 1:
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }

                isFulllScreen = true;
                full(isFulllScreen, toolbar);
                tipsBottom.setVisibility(View.GONE);
                tipsTitle.setVisibility(View.GONE);
                banner.setVisibility(View.GONE);
                banner1.setVisibility(View.VISIBLE);

                break;
            /*case R.id.iv_video:
                if(mVidioView!=null&&mVidioView.isPlaying()){
                    mVidioView.pause();
                }else if(mVidioView!=null){
                    mVidioView.start();
                }
                break;*/
        }
    }

    @Override
    protected void onDestroy() {
        playMediaFinish();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        playMediaFinish();
        super.onBackPressed();
    }
}

