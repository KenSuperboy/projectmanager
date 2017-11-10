package com.gohoc.xiupuling.ui.order;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.OrderBean;
import com.gohoc.xiupuling.bean.OrderDetailBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.terminal.SelectBaiduMapActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.MVidioView;
import com.gohoc.xiupuling.widget.RatingBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class OrderRushActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.v_banner)
    Banner vBanner;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.money_dt_tv)
    TextView moneyDtTv;
    @BindView(R.id.start_p_v_tv)
    TextView startPVTv;
    @BindView(R.id.end_p_v_tv)
    TextView endPVTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.req_time_dt_rl)
    LinearLayout reqTimeDtRl;
    @BindView(R.id.activity_t_tv)
    TextView activityTTv;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.link_type_tv)
    TextView linkTypeTv;
    private OrderBean.DataBean orderBeans;
    private OrderDetailBean orderDetailBeans;
    private int work_type;

    @BindView(R.id.content_frame)
    RelativeLayout mFrameContenLayout;
    @BindView(R.id.m_video_view)
    MVidioView mVidioView;
    @BindView(R.id.view_frame)
    FrameLayout mFrameLayout;
    @BindView(R.id.ll)
    LinearLayout linearLayout;
    @BindView(R.id.ll_1)
    LinearLayout linearLayout1;
    @BindView(R.id.ll_2)
    LinearLayout linearLayout2;
    @BindView(R.id.ll_3)
    LinearLayout linearLayout3;
    @BindView(R.id.ll_4)
    LinearLayout linearLayout4;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_bg_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_float)
    ImageView iv_float;
    private int mWidth, mHeight;
    private int w1;
    private float scale = 0;
    private int w, h;
    private int week = 0;
    private int cMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rush);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("待抢单");
        orderBeans = (OrderBean.DataBean) getIntent().getExtras().get("order");
        getOrderDetail(orderBeans.getRange_id(), orderBeans.getTerminal_id(), "1");
        work_type = orderBeans.getWork_type();
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        w1 = wm.getDefaultDisplay().getWidth();
        mVidioView.setMediaController(new MediaController(this));
        mVidioView.setOnCompletionListener(new MyPlayerOnCompletionListener());

    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.i("kekegdsz", "播放完成了");
            ivPlay.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.toolbar_left_title, R.id.button_ll, R.id.req_time_dt_rl, R.id.iv_play, R.id.time_tv,R.id.linear_item_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                playMediaFinish();
                finish();
                break;
            case R.id.button_ll:
                receiveOrder(orderDetailBeans.getData().getRange_id(), orderBeans.getTerminal_id());
/*                startActivity(new Intent(OrderRushActivity.this, OrderRushDtActivity.class)
                        .putExtra("orderDetailBeans", orderDetailBeans)
                        .putExtra("id", orderBeans.getTerminal_id())
                        .putExtra("order", orderBeans)
                );*/
                break;
            case R.id.req_time_dt_rl:

                break;
            case R.id.iv_play:
                mFrameLayout.setVisibility(View.VISIBLE);
                ivPlay.setVisibility(View.GONE);
                mVidioView.start();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.time_tv:
                startActivity(new Intent(OrderRushActivity.this, OrderRushDtActivity.class)
                        .putExtra("orderDetailBeans", orderDetailBeans)
                        .putExtra("id", orderBeans.getTerminal_id())
                        .putExtra("order", orderBeans));
                break;
            /*case R.id.iv_float:
                LogUtil.d("播放按钮点击");
                if(ivPlay.getVisibility()==View.VISIBLE){
                    LogUtil.d("播放按钮点击11111");
                    if(mVidioView.isPlaying()){
                        LogUtil.d("播放按钮点击22222");
                        mVidioView.pause();
                    }else {
                        LogUtil.d("播放按钮点击33333");
                        mVidioView.start();
                    }
                }
                break;*/
            case R.id.linear_item_layout:
                if (orderDetailBeans.getData().getActivity_onoff() == 0) {
                    if (orderDetailBeans.getData().getActivity_nav_type() == 0){
                        //linkTypeTv.setText("扫描链接:导航到实体地址");
                        PoiInfo poiInfo = new PoiInfo();
                        poiInfo.address = orderDetailBeans.getData().getActivity_nav_content() + "";
                        poiInfo.location = new LatLng(Double.parseDouble(orderDetailBeans.getData().getActivity_nav_latitude()+""), Double.parseDouble(orderDetailBeans.getData().getActivity_nav_longitude()+""));

                        startActivity(new Intent(OrderRushActivity.this, SelectBaiduMapActivity.class).putExtra("poiInfo", poiInfo).putExtra("type","1"));
                    }else {
                        startActivity(new Intent(OrderRushActivity.this, WebViewActivity.class).putExtra("url", Utils.delUrl(orderDetailBeans.getData().getActivity_nav_content() + "")).putExtra("title", "链接到网址"));
                    }
                } else {
                    //无响应  可以不用理会
                }
                break;
        }
    }

    private void receiveOrder(String range_id, String terminal_id) {
        StringBuffer sb = new StringBuffer();

        for (OrderDetailBean.DataBean.DatalistBean datalistBean : orderDetailBeans.getData().getDatalist()) {
            if (datalistBean.isCheck())
                sb.append(datalistBean.getRange_detail_id() + ",");
        }
        if (sb.length() < 1) {
            Utils.toast(this, "至少选择一项");
            return;
        }
        RxRetrofitClient.getInstance(this).receiveOrder(range_id, sb.toString() + "", terminal_id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderRushActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.OrderUpdateEvent());
                    startActivity(new Intent(OrderRushActivity.this, OrderRushSuccessActivity.class)
                            .putExtra("orderDetailBeans", orderDetailBeans)
                            .putExtra("order", orderBeans));
                } else
                    Utils.toast(OrderRushActivity.this, vCodeBenan.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVidioView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVidioView.pause();
    }

    private void getOrderDetail(String range_id, String terminal_id, String type) {

        RxRetrofitClient.getInstance(this).orderDetail(range_id, terminal_id, type, new Observer<OrderDetailBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderRushActivity.this, "请检查网络是否正常");
                EventBus.getDefault().post(new Event.OrderUpdateEvent());
                finish();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(OrderDetailBean orderDetailBean) {
                if (orderDetailBean.getCode() == 1) {
                    orderDetailBeans = orderDetailBean;
                    try {
                        initDates(orderDetailBean);
                    } catch (Exception e) {
                        Utils.toast(OrderRushActivity.this, "订单不存在,或已被抢.");
                        EventBus.getDefault().post(new Event.OrderUpdateEvent());
                        finish();
                    }
                }
            }
        });
    }

    /*private void playMyMusic()
    {
        for (int i=0;i<orderDetailBeans.getData().getDatalist().size();i++){
            if(orderDetailBeans.getData().getDatalist().get(i).get.equals("1")){
                playMediaStart(BASE_USER_RESOURE+orderDetailBeans.getData().getDatalist().get(i).getMaterial_store_url());
            }
        }
    }*/

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PrderRushEvent(Event.PrderRushEvent message) {
        cMoney = message.m;
        week = (int) (cMoney / orderDetailBeans.getData().getUnit_price());
        timeTv.setText("之间的" + week + "周");
        moneyDtTv.setText("￥" + cMoney + "元");
        orderDetailBeans.getData().setDatalist(message.datalist);
    }

    private void initDates(OrderDetailBean orderDetailBean) {
        if (orderDetailBean.getData().getActivity_onoff() == 0) {
            if (orderDetailBean.getData().getActivity_nav_type() == 0)
                linkTypeTv.setText("扫描链接:导航到实体地址");
            else
                linkTypeTv.setText("扫描链接:导航到网址");
            activityTTv.setText(orderDetailBean.getData().getActivity_nav_content());
            linkTypeTv.setVisibility(View.VISIBLE);
        } else {
            activityTTv.setText("无活动举办");
            linkTypeTv.setVisibility(View.GONE);
        }
        for (int a = 0; a < orderDetailBean.getData().getDatalist().size(); a++)
            if (cMoney < orderDetailBean.getData().getTotalamt()) {
                orderDetailBean.getData().getDatalist().get(a).setCheck(true);
                cMoney += orderDetailBean.getData().getDatalist().get(a).getUnit_price();
                week++;
            }
        cMoney = (int) (week * orderDetailBeans.getData().getUnit_price());
        int orientation = orderDetailBean.getData().getWorkdetail().getOrientation();
        Log.i("kekegdsz", orientation + "");

        if (orientation != 1) {
            //横屏
            iv_float.setImageResource(R.mipmap.icon_hengbanui);
            float scale = 9f / 16f;
            mWidth = w1;
            mHeight = (int) (w1 * scale);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout4.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            iv_float.setImageResource(R.mipmap.icon_shubanui);
            float scale = 16f / 9f;
            mWidth = w1 / 2;
            mHeight = (int) ((w1 / 2) * scale);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            linearLayout3.setOrientation(LinearLayout.VERTICAL);
            linearLayout4.setOrientation(LinearLayout.VERTICAL);
        }

        mVidioView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                             @Override
                                             public void onPrepared(MediaPlayer mp) {
                                                 //FixMe 获取视频资源的宽度
                                                 int mVideoWidth = mp.getVideoWidth();
                                                 //FixMe 获取视频资源的高度
                                                 int mVideoHeight = mp.getVideoHeight();

                                                 if (work_type == 4) {
                                                     if (mVideoHeight > 0 && mVideoWidth > 0) {
                                                         //FixMe 拉伸比例
                                                         float scale = (float) mVideoWidth / (float) mVideoHeight;
                                                         //FixMe 视频资源拉伸至屏幕宽度，横屏竖屏需结合传感器等特殊处理
                                                         mVideoWidth = w;
                                                         //FixMe 拉伸VideoView高度
                                                         mVideoHeight = h;//FixMe 设置surfaceview画布大小
                                                         mVidioView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                                                         //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                                                         mVidioView.setMeasure(mVideoWidth, mVideoHeight);
                                                         //FixMe 请求调整
                                                         mVidioView.requestLayout();
                                                     }
                                                 } else {
                                                     float scale = (float) mVideoWidth / (float) mVideoHeight;
                                                     int width = mWidth;
                                                     int height = mHeight;
                                                     mVideoWidth = width;
                                                     mVideoHeight = Math.round(mVideoWidth / scale);
                                                     if (mVideoHeight > height) {
                                                         mVideoHeight = height;
                                                         mVideoWidth = Math.round(mVideoHeight * scale);
                                                     }
                                                     mVidioView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                                                     //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                                                     mVidioView.setMeasure(mVideoWidth, mVideoHeight);
                                                     //FixMe 请求调整
                                                     mVidioView.requestLayout();
                                                 }

                                             }
                                         }

        );


        moneyDtTv.setText("￥" + cMoney + "元");
        titleTv.setText(orderDetailBean.getData().getWorkdetail().getWork_name());
        ratingbar.setmClickable(false);
        ratingbar.setEnabled(false);
        ratingbar.setStar(orderDetailBean.getData().getStar_level());
        startPVTv.setText(Utils.delTime(orderDetailBean.getData().getDatalist().get(0).getStart_date()) + "  " + Utils.getWeekOfDate(Utils.StrToDate(orderDetailBean.getData().getDatalist().get(0).getStart_date(), "yyyyMMdd")));
        endPVTv.setText(Utils.delTime(orderDetailBean.getData().getDatalist().get(orderDetailBean.getData().getDatalist().size() - 1).getEnd_date()) + "  " + Utils.getWeekOfDate(Utils.StrToDate(orderDetailBean.getData().getDatalist().get(orderDetailBean.getData().getDatalist().size() - 1).getEnd_date(), "yyyyMMdd")));

        if (orderDetailBean.getData().getActivity_onoff() == 0 && !TextUtils.isEmpty(orderDetailBean.getData().getActivity_nav_content())) {
            activityTTv.setText(orderDetailBean.getData().getActivity_nav_content() + "");
        }


        timeTv.setText("之间的" + week + "周");
        if (orderDetailBean.getData().getWorkdetail().getWork_type() == 3) {//视频水印
            ivPlay.setVisibility(View.VISIBLE);
            vBanner.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.GONE);
            initPlayer(mVidioView, orderDetailBean);
            String url = BASE_USER_RESOURE + orderDetailBean.getData().getWorkmaterials().get(0).getMaterial_store_url();
            iv_bg.setVisibility(View.VISIBLE);
            Glide.with(this).load(url).into(iv_bg);
        } else if (orderDetailBean.getData().getWorkdetail().getWork_type() == 4) {//视频图片背景
            mFrameLayout.setVisibility(View.VISIBLE);
            int x = 0;
            int y = 0;
            if (orientation == 1) {
                //竖屏
                x = 1080;
                y = 1920;
            } else {
                //横屏
                x = 1920;
                y = 1080;
            }

            w = (int) (((float) orderDetailBean.getData().getWorkmaterials().get(1).getMaterial_width() / (float) x) * (float) mWidth);
            h = (int) (((float) orderDetailBean.getData().getWorkmaterials().get(1).getMaterial_height() / (float) y) * (float) mHeight);
            int left = (int) (((float) orderDetailBean.getData().getWorkmaterials().get(1).getMaterial_start_x() / (float) x) * (float) mWidth);
            int top = (int) (((float) orderDetailBean.getData().getWorkmaterials().get(1).getMaterial_start_y() / (float) y) * (float) mHeight);
            mVidioView.setMeasure(w, h);
            //FixMe 请求调整
            mVidioView.requestLayout();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
            layoutParams.setMargins(left, top, mWidth - w - left, mHeight - h - top);
            mFrameLayout.setLayoutParams(layoutParams);
            String url = BASE_USER_RESOURE + orderDetailBean.getData().getWorkmaterials().get(1).getMaterial_store_url();
            mVidioView.setVideoURI(Uri.parse(url));
            ivPlay.setVisibility(View.VISIBLE);
            vBanner.setVisibility(View.VISIBLE);
            initBanner1(vBanner, getBackUrl(orderDetailBean.getData().getWorkmaterials()));
        } else {
            vBanner.setVisibility(View.VISIBLE);
            mFrameLayout.setVisibility(View.GONE);
            initBanner(vBanner, orderDetailBean.getData().getWorkmaterials());
        }
    }

    private String getBackUrl(List<OrderDetailBean.DataBean.WorkmaterialsBean> mymateriallist)
    {
        String url="";
        if(mymateriallist==null||mymateriallist.size()==0){
            url="";
        }else {
            for (int i=0;i<mymateriallist.size();i++){
                if(mymateriallist.get(i).getFiletype().equals("2")){
                    url=mymateriallist.get(i).getMaterial_store_url();
                }
            }
        }
        return url;
    }


    private void initBanner1(Banner banner, String s) {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add(s);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(NetConstant.BASE_USER_RESOURE + path).centerCrop().into(imageView);
            }
        });
        banner.setImages(strings);
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

    private MediaPlayer mMediaPlayer;
    private void playMediaStart(String mediaPath)
    {
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
        if(mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }

    private void initBanner(Banner banner, List<OrderDetailBean.DataBean.WorkmaterialsBean> materiallist) {

        List<OrderDetailBean.DataBean.WorkmaterialsBean> tempMateriallist=new ArrayList<>();

        for (int i=0;i<materiallist.size();i++){
            if(materiallist.get(i).getFiletype().equals("1")){
                //音乐文件
                playMediaStart(BASE_USER_RESOURE+materiallist.get(i).getMaterial_store_url());
            }else if(materiallist.get(i).getFiletype().equals("2")){//图片
                tempMateriallist.add(materiallist.get(i));
            }
        }

        if(tempMateriallist.size()==0)return;

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                // Logger.d(path);
                OrderDetailBean.DataBean.WorkmaterialsBean materiallistBean = (OrderDetailBean.DataBean.WorkmaterialsBean) path;
                Glide.with(context).load(NetConstant.BASE_USER_RESOURE + materiallistBean.getMaterial_store_url()).fitCenter().into(imageView);
            }
        });
        banner.setImages(tempMateriallist);
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

    private void initPlayer(MVidioView samplevideo, OrderDetailBean orderDetailBean) {
        String url = null;
        List<OrderDetailBean.DataBean.WorkmaterialsBean> workmaterials = orderDetailBean.getData().getWorkmaterials();
        if (workmaterials == null) return;
        for (OrderDetailBean.DataBean.WorkmaterialsBean bean : workmaterials) {
            if (bean.getFiletype().equals("0")) {
                url = BASE_USER_RESOURE + bean.getMaterial_store_url();
                break;
            }
        }
        if (url == null) return;
        samplevideo.setVideoURI(Uri.parse(url));
    }

    @Override
    protected void onDestroy() {
        playMediaFinish();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mVidioView.destroyDrawingCache();
    }

}
