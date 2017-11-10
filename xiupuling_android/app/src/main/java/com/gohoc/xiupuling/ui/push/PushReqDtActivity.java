package com.gohoc.xiupuling.ui.push;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.WorksDetails;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.ReqBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.MVidioView;
import com.gohoc.xiupuling.widget.dialog.BaseDialog;
import com.gohoc.xiupuling.widget.dialog.BlueDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
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

public class PushReqDtActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.v_title_v_tv)
    TextView vTitleVTv;
    @BindView(R.id.v_favourable_v_tv)
    TextView vFavourableVTv;
    @BindView(R.id.v_link_tv)
    TextView vLinkTv;
    @BindView(R.id.v_adress_tv)
    TextView vAdressTv;
    @BindView(R.id.v_edit_ll)
    LinearLayout vEditLl;
    @BindView(R.id.v_dt_ll)
    LinearLayout vDtLl;
    @BindView(R.id.h_title_v_tv)
    TextView hTitleVTv;
    @BindView(R.id.h_favourable_v_tv)
    TextView hFavourableVTv;
    @BindView(R.id.h_adress_tv)
    TextView hAdressTv;
    @BindView(R.id.h_edit_ll)
    LinearLayout hEditLl;
    @BindView(R.id.h_dt_ll)
    LinearLayout hDtLl;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    @BindView(R.id.push_dt_ct_ll)
    LinearLayout pushDtCtLl;
    @BindView(R.id.push_show_ll)
    LinearLayout pushShowLl;
    @BindView(R.id.push_local_ll)
    LinearLayout pushLocalLl;
    @BindView(R.id.v_banner)
    Banner vBanner;
    @BindView(R.id.h_banner)
    Banner hBanner;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.contentLL)
    LinearLayout contentLL;
    @BindView(R.id.ll_1)
    LinearLayout ll_1;
    @BindView(R.id.ll_2)
    LinearLayout ll_2;
    @BindView(R.id.rl_vd1)
    RelativeLayout rl_vd1;
    @BindView(R.id.rl_vd2)
    RelativeLayout rl_vd2;
    @BindView(R.id.video_view)
    MVidioView mVidioView;
    @BindView(R.id.video_view1)
    MVidioView mVidioView1;
    @BindView(R.id.playv)
    ImageView ivPlayv;
    @BindView(R.id.playh)
    ImageView ivPlayh;
    @BindView(R.id.view_frame1)
    FrameLayout view_frame1;
    @BindView(R.id.view_frame)
    FrameLayout view_frame;
    @BindView(R.id.iv_bg)
    ImageView iv_video_bg;
    @BindView(R.id.iv_bg1)
    ImageView iv_video_bg1;
    @BindView(R.id.iv_float)
    ImageView iv_float;
    @BindView(R.id.iv_shuiying)
    ImageView iv_shuiying;
    @BindView(R.id.iv_shuiying_)
    ImageView iv_shuiying_;
    private int width, height;
    private ImageView imageView;
    private WorksDetails worksDetail;
    private String rq_id = "";
    private String work_id = "";
    private PushNormlBean pushNormlBean;
    private ReqBean.DataBean.ListBean reqBeanTemp;
    private static int EDIT_REQUEST_RESULT = 1000;
    private int work_type = 1;
    private int measuredWidth;
    private int measuredHeight;
    private float sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_dt);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("展示信息");
        toolbarRight.setText("删除");
        reqBeanTemp = (ReqBean.DataBean.ListBean) getIntent().getExtras().get("reqBeanTemp");
        int orientation = reqBeanTemp.getOrientation();
        sc = 16f / 9f;
        if (orientation != 0) {
            vDtLl.setVisibility(View.GONE);
            hDtLl.setVisibility(View.VISIBLE);
            int height = PushReqDtActivity.this.getWindowManager().getDefaultDisplay().getWidth();
            int width = Math.round(height / sc);
            LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(width, height);
            layoutParams.width = width;
            layoutParams.height = height;
            ll_2.setLayoutParams(layoutParams);
            mWidth=width;
            mHeight=height;
        } else {
            vDtLl.setVisibility(View.VISIBLE);
            hDtLl.setVisibility(View.GONE);
            int width = PushReqDtActivity.this.getWindowManager().getDefaultDisplay().getWidth();
            int height = Math.round(width / sc);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            ll_1.setLayoutParams(layoutParams);
            mWidth=width;
            mHeight=height;
        }


        work_id = reqBeanTemp.getWork_id();
        rq_id = reqBeanTemp.getRq_id();
        workdetail(work_id);
        mVidioView.setMediaController(new MediaController(this));
        mVidioView.setOnCompletionListener(new MyPlayerOnCompletionListener());

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
                                                         mVideoWidth = width;
                                                         //FixMe 拉伸VideoView高度
                                                         mVideoHeight = height;//FixMe 设置surfaceview画布大小
                                                         mVidioView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                                                         //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                                                         mVidioView.setMeasure(mVideoWidth, mVideoHeight);
                                                         //FixMe 请求调整
                                                         mVidioView.requestLayout();
                                                     }
                                                 } else {
                                                     float scale = (float) mVideoWidth / (float) mVideoHeight;
                                                     int width = 0;
                                                     int height = 0;
                                                     if (worksDetail.getData().getOrientation() == 1) {
                                                         ViewGroup viewGroup = (ViewGroup) rl_vd2.getParent();
                                                         width = viewGroup.getWidth();
                                                         height = viewGroup.getHeight();
                                                     } else {
                                                         ViewGroup viewGroup = (ViewGroup) rl_vd1.getParent();
                                                         width = viewGroup.getWidth();
                                                         height = viewGroup.getHeight();
                                                     }
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

        mVidioView1.setMediaController(new MediaController(this));
        mVidioView1.setOnCompletionListener(new MyPlayerOnCompletionListener());
        mVidioView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                              @Override
                                              public void onPrepared(MediaPlayer mp) {
                                                  //FixMe 获取视频资源的宽度
                                                  int mVideoWidth = mp.getVideoWidth();
                                                  //FixMe 获取视频资源的高度
                                                  int mVideoHeight = mp.getVideoHeight();
                                                  if (work_type == 4) {
                                                      if (mVideoHeight > 0 && mVideoWidth > 0) {
                                                          mVideoWidth = width;
                                                          //FixMe 拉伸VideoView高度
                                                          mVideoHeight = height;//FixMe 设置surfaceview画布大小
                                                          mVidioView1.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                                                          //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                                                          mVidioView1.setMeasure(mVideoWidth, mVideoHeight);
                                                          //FixMe 请求调整
                                                          mVidioView1.requestLayout();
                                                      }

                                                  } else {
                                                      float scale = (float) mVideoWidth / (float) mVideoHeight;
                                                      int width = 0;
                                                      int height = 0;
                                                      if (worksDetail.getData().getOrientation() == 1) {
                                                          ViewGroup viewGroup = (ViewGroup) rl_vd2.getParent();
                                                          width = viewGroup.getWidth();
                                                          height = viewGroup.getHeight();
                                                      } else {
                                                          ViewGroup viewGroup = (ViewGroup) rl_vd1.getParent();
                                                          width = viewGroup.getWidth();
                                                          height = viewGroup.getHeight();
                                                      }
                                                      mVideoWidth = width;
                                                      mVideoHeight = (int) (mVideoWidth / scale);
                                                      if (mVideoHeight > height) {
                                                          mVideoHeight = height;
                                                          mVideoWidth = (int) (mVideoHeight * scale);
                                                      }
                                                      mVidioView1.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                                                      //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                                                      mVidioView1.setMeasure(mVideoWidth, mVideoHeight);
                                                      //FixMe 请求调整
                                                      mVidioView1.requestLayout();
                                                  }
                                              }


                                          }

        );


    }


    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.i("kekegdsz", "播放完成了");
            if (work_type == 4) {
                ivPlayv.setVisibility(View.VISIBLE);
                ivPlayh.setVisibility(View.VISIBLE);
            }

        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.v_edit_ll, R.id.v_dt_ll, R.id.h_edit_ll, R.id.h_dt_ll, R.id.sumbit_button_ll, R.id.push_dt_ct_ll, R.id.push_show_ll, R.id.push_local_ll, R.id.toolbar_right, R.id.playh, R.id.playv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.v_edit_ll:
                //   case R.id.v_dt_ll:
            case R.id.h_edit_ll:
                // case R.id.h_dt_ll:
                PromotionInfoBean promotionInfoBean = new PromotionInfoBean();
                promotionInfoBean.setRq_id(reqBeanTemp.getRq_id());
                promotionInfoBean.setActivityTitle(reqBeanTemp.getActivity_title());
                promotionInfoBean.setOpen(reqBeanTemp.getActivity_onoff() == 0 ? true : false);
                promotionInfoBean.setLinkType(reqBeanTemp.getActivity_nav_type());
                promotionInfoBean.setLatitude(reqBeanTemp.getActivity_nav_latitude() == null ? null : reqBeanTemp.getActivity_nav_latitude() + "");
                promotionInfoBean.setLongitude(reqBeanTemp.getActivity_nav_longitude() == null ? null : reqBeanTemp.getActivity_nav_longitude() + "");
                //promotionInfoBean.setLcoationName(reqBeanTemp.getActivity_brand());
                promotionInfoBean.setDetails(reqBeanTemp.getActivity_detail());
                promotionInfoBean.setKeyWord(reqBeanTemp.getActivity_brand());
                promotionInfoBean.setIntroduce(reqBeanTemp.getActivity_content());
                if (promotionInfoBean.getLinkType() == 1)
                    promotionInfoBean.setLinkUrl(reqBeanTemp.getActivity_nav_content());
                else
                    promotionInfoBean.setLcoationName(reqBeanTemp.getActivity_nav_content());

                startActivityForResult(new Intent(PushReqDtActivity.this, PushReqEditActivity.class).putExtra("promotionInfoBean", promotionInfoBean), EDIT_REQUEST_RESULT);
                break;
            case R.id.sumbit_button_ll:
                startActivity(new Intent(PushReqDtActivity.this, PushSelectMenuActivity.class).putExtra("pushNormlBean", pushNormlBean));
                break;
            case R.id.push_dt_ct_ll:
                if (Credential.getInstance().getCurUser(PushReqDtActivity.this).getData().getShared_platform() == 1)
                    startActivity(new Intent(PushReqDtActivity.this, PushReqResultActivity.class).putExtra("rq_id", rq_id).putExtra("work_id", work_id).putExtra("pushNormlBean", pushNormlBean));
                else
                    Utils.toast(PushReqDtActivity.this, "未开启共享平台计划，无法查看");
                break;
            case R.id.push_show_ll:
                startActivity(new Intent(PushReqDtActivity.this, PushReqShowCountActivity.class).putExtra("rq_id", rq_id).putExtra("work_id", work_id).putExtra("reqBeanTemp", reqBeanTemp).putExtra("pushNormlBean", pushNormlBean));
                break;
            case R.id.push_local_ll:
                startActivity(new Intent(PushReqDtActivity.this, PushLocationActivity.class).putExtra("rq_id", rq_id).putExtra("work_id", work_id));
                break;
            case R.id.toolbar_right:
                finishWorkRangeMarket(work_id);
                break;
            case R.id.playh:
                mVidioView1.setVisibility(View.VISIBLE);
                iv_video_bg1.setVisibility(View.GONE);
                mVidioView1.start();
                ivPlayh.setVisibility(View.GONE);
                break;
            case R.id.playv:
                iv_video_bg.setVisibility(View.GONE);
                mVidioView.setVisibility(View.VISIBLE);
                mVidioView.start();
                ivPlayv.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            PromotionInfoBean promotionInfoBean = (PromotionInfoBean) data.getExtras().get("promotionInfoBean");
            reqBeanTemp.setActivity_onoff(promotionInfoBean.getOpen() == true ? 0 : 1);
            reqBeanTemp.setActivity_nav_type(promotionInfoBean.getLinkType());
            reqBeanTemp.setActivity_nav_latitude(TextUtils.isEmpty(promotionInfoBean.getLatitude()) ? null : Double.parseDouble(promotionInfoBean.getLatitude()));
            reqBeanTemp.setActivity_nav_longitude(TextUtils.isEmpty(promotionInfoBean.getLongitude()) ? null : Double.parseDouble(promotionInfoBean.getLongitude()));
            reqBeanTemp.setActivity_detail(promotionInfoBean.getDetails());
            reqBeanTemp.setActivity_brand(promotionInfoBean.getKeyWord());
            reqBeanTemp.setActivity_content(promotionInfoBean.getIntroduce());
            if (promotionInfoBean.getLinkType() == 1)
                reqBeanTemp.setActivity_nav_content(promotionInfoBean.getLinkUrl());
            else
                reqBeanTemp.setActivity_nav_content(promotionInfoBean.getLcoationName());
            reqBeanTemp.setActivity_title(promotionInfoBean.getActivityTitle());
            initDatas(worksDetail);
        }
    }

    public void workdetail(String id) {
        RxRetrofitClient.getInstance(this).workdetail(id, new Observer<WorksDetails>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqDtActivity.this, "请检查网络是否正常");
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
                    initDatas(worksDetails);
                    if(worksDetail!=null){
                        playMyMusic();
                    }
                }

            }
        });

    }

    private void playMyMusic()
    {
        for (int i=0;i<worksDetail.getData().getMateriallist().size();i++){
            if(worksDetail.getData().getMateriallist().get(i).getFiletype().equals("1")){
                playMediaStart(BASE_USER_RESOURE+worksDetail.getData().getMateriallist().get(i).getMaterial_store_url());
            }
        }
    }

    private void initDatas(WorksDetails worksDetails) {
        if (Credential.getInstance().getCurUser(this).getData().getShared_platform() == 0) {
            pushDtCtLl.setVisibility(View.GONE);
        } else
            pushDtCtLl.setVisibility(View.VISIBLE);

        pushNormlBean = new PushNormlBean();
        pushNormlBean.setCover_url(worksDetails.getData().getMateriallist().get(0).getMaterial_store_url());
        pushNormlBean.setName(reqBeanTemp.getActivity_title());
        pushNormlBean.setRq_id(rq_id);
        pushNormlBean.setIndexFlage(4);
        pushNormlBean.setWork_id(worksDetails.getData().getWork_id());
        pushNormlBean.setOrientation(worksDetails.getData().getOrientation());
        pushNormlBean.setPlaytime(worksDetails.getData().getPlaytime());
        pushNormlBean.setActivity_content(reqBeanTemp.getActivity_content());

        List<WorksDetails.DataBean.MateriallistBean> materiallistTemp = new ArrayList<>();
        materiallistTemp.addAll(worksDetails.getData().getMateriallist());
        if (worksDetails.getData().getWork_type() == 4)
            materiallistTemp.remove(1);

        if (worksDetails.getData().getOrientation() == 1) {  //竖屏
            iv_float.setImageResource(R.mipmap.icon_shubanui);
            hTitleVTv.setText(reqBeanTemp.getActivity_title() + "");
            hFavourableVTv.setText(TextUtils.isEmpty(reqBeanTemp.getActivity_content()) || reqBeanTemp.getActivity_onoff() == 1 ? "无活动举办" : reqBeanTemp.getActivity_content());
            hAdressTv.setText(TextUtils.isEmpty(reqBeanTemp.getActivity_nav_content()) || reqBeanTemp.getActivity_onoff() == 1 ? "无" : reqBeanTemp.getActivity_nav_content());

            if (worksDetails.getData().getWork_type() == 3 || worksDetails.getData().getWork_type() == 4) {
                mVidioView.setVisibility(View.VISIBLE);
                initPlayer(worksDetails, mVidioView);
            } else {
                mVidioView.setVisibility(View.GONE);
            }

            if (worksDetails.getData().getWork_type() != 3) {
                initBanner(hBanner, materiallistTemp);
                hBanner.setVisibility(View.VISIBLE);
            } else
                hBanner.setVisibility(View.GONE);

        } else {    //横屏
            iv_float.setImageResource(R.mipmap.icon_hengbanui);
            vTitleVTv.setText(reqBeanTemp.getActivity_title() + "");
            vFavourableVTv.setText(TextUtils.isEmpty(reqBeanTemp.getActivity_content()) || reqBeanTemp.getActivity_onoff() == 1 ? "无活动举办" : reqBeanTemp.getActivity_content());
            vAdressTv.setText(TextUtils.isEmpty(reqBeanTemp.getActivity_nav_content()) || reqBeanTemp.getActivity_onoff() == 1 ? "无" : reqBeanTemp.getActivity_nav_content());
            if (worksDetails.getData().getWork_type() == 3 || worksDetails.getData().getWork_type() == 4) {
                mVidioView.setVisibility(View.VISIBLE);
                initPlayer(worksDetails, mVidioView1);
            } else {
                mVidioView.setVisibility(View.GONE);
            }

            if (worksDetails.getData().getWork_type() != 3) {
                initBanner(vBanner, materiallistTemp);
                vBanner.setVisibility(View.VISIBLE);
            } else
                vBanner.setVisibility(View.GONE);


        }
        contentLL.setVisibility(View.VISIBLE);
        work_type = worksDetails.getData().getWork_type();

        switch (work_type) {
            case 1:
                rl_vd1.setVisibility(View.GONE);
                break;
            case 2:
                rl_vd1.setVisibility(View.GONE);
                break;
            case 3:
                getShuiyinPosition();
                rl_vd1.setVisibility(View.VISIBLE);
                rl_vd2.setVisibility(View.VISIBLE);
                //String s = BASE_USER_RESOURE + worksDetails.getData().getMateriallist().get(0).getMaterial_store_url();
                if (worksDetails.getData().getOrientation() == 1) {
                    Glide.with(this).load(BASE_USER_RESOURE +tupianString).into(iv_video_bg);
                } else {
                    Glide.with(this).load(BASE_USER_RESOURE +tupianString).into(iv_video_bg1);
                }

                Glide.with(mContext).load(BASE_USER_RESOURE + shuiyingString).fitCenter().into(iv_shuiying);
                Glide.with(mContext).load(BASE_USER_RESOURE + shuiyingString).fitCenter().into(iv_shuiying_);
                LogUtil.d("水印图片："+BASE_USER_RESOURE + shuiyingString);
                if(shuiying){
                    setIv_shuiying();
                }
                break;
            case 4:
                final int material_start_x = worksDetails.getData().getMateriallist().get(1).getMaterial_start_x();
                final int material_start_y = worksDetails.getData().getMateriallist().get(1).getMaterial_start_y();
                final int material_width = worksDetails.getData().getMateriallist().get(1).getMaterial_width();
                final int material_height = worksDetails.getData().getMateriallist().get(1).getMaterial_height();
                if (worksDetails.getData().getOrientation() == 1) {
                    rl_vd2.post(new Runnable() {
                        @Override
                        public void run() {
                            //竖屏
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            view_frame.setLayoutParams(layoutParams);
                            vBanner.setVisibility(View.VISIBLE);
                            int x = 1080;
                            int y = 1920;
                            measuredWidth = ll_2.getMeasuredWidth();
                            measuredHeight = ll_2.getMeasuredHeight();
                            int marginsleft = (int) (((float) material_start_x / (float) x) * measuredWidth);
                            int marginstop = (int) (((float) material_start_y / (float) y) * measuredHeight);
                            width = (int) (((float) material_width / (float) x) * measuredWidth);
                            height = (int) (((float) material_height / (float) y) * measuredHeight);
                            Log.i("kekegdsz", marginsleft + "," + marginstop + "," + width + "," + height);
                            FrameLayout.LayoutParams rlp = new FrameLayout.LayoutParams(width, height);
                            rlp.setMargins(marginsleft, marginstop, 0, 0);//addRule参数对应RelativeLayout XML布局的属性
                            mVidioView.setLayoutParams(rlp);
                        }
                    });


                } else {
                    ll_1.post(new Runnable() {
                        @Override
                        public void run() {

                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            view_frame1.setLayoutParams(layoutParams);
                            //横屏
                            hBanner.setVisibility(View.VISIBLE);
                            int x = 1920;
                            int y = 1080;
                            measuredWidth = ll_1.getMeasuredWidth();
                            measuredHeight = ll_1.getMeasuredHeight();
                            int marginsleft = (int) (((float) material_start_x / (float) x) * measuredWidth);
                            int marginstop = (int) (((float) material_start_y / (float) y) * measuredHeight);
                            width = (int) (((float) material_width / (float) x) * measuredWidth);
                            height = (int) (((float) material_height / (float) y) * measuredHeight);
                            Log.i("kekegdsz", marginsleft + "," + marginstop + "," + width + "," + height);
                            FrameLayout.LayoutParams rlp = new FrameLayout.LayoutParams(width, height);
                            rlp.setMargins(marginsleft, marginstop, 0, 0);//addRule参数对应RelativeLayout XML布局的属性
                            mVidioView1.setLayoutParams(rlp);
                        }
                    });

                }

                break;

        }

    }

    int mX=0,mY=0,CurrW=0,CurrH=0,mWidth=0,mHeight=0;
    private void setIv_shuiying()
    {
        LogUtil.d("进来设置水印图片的大小");
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

                LogUtil.d("vvv宽度："+mWidth+"===vvv高度："+mHeight+"：视频获取："+CurrW+"====:"+CurrW);

                FrameLayout.LayoutParams mFrameLayout_ivLayoutParams = (FrameLayout.LayoutParams) iv_shuiying.getLayoutParams();
                mFrameLayout_ivLayoutParams.width=CurrW;
                mFrameLayout_ivLayoutParams.height=CurrH;
                iv_shuiying.setLayoutParams(mFrameLayout_ivLayoutParams);
                iv_shuiying.invalidate();

                FrameLayout.LayoutParams mFrameLayout_ivLayoutParams_ = (FrameLayout.LayoutParams) iv_shuiying_.getLayoutParams();
                mFrameLayout_ivLayoutParams_.width=CurrW;
                mFrameLayout_ivLayoutParams_.height=CurrH;
                iv_shuiying_.setLayoutParams(mFrameLayout_ivLayoutParams_);
                iv_shuiying_.invalidate();

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) iv_shuiying.getLayoutParams();
                layoutParams.setMargins(left, top,0, 0);
                iv_shuiying.setLayoutParams(layoutParams);

                FrameLayout.LayoutParams layoutParams_ = (FrameLayout.LayoutParams) iv_shuiying_.getLayoutParams();
                layoutParams_.setMargins(left, top,0, 0);
                iv_shuiying_.setLayoutParams(layoutParams_);

            }
        });
    }

    boolean shuiying;
    private String shuiyingString="",tupianString="";
    private int getShuiyinPosition()
    {
        int shuiyinPosition=0;
        for (int i=0;i<worksDetail.getData().getMateriallist().size();i++){
            if(worksDetail.getData().getMateriallist().get(i).getFiletype().equals("3")){
                shuiyinPosition=i;
                shuiying=true;
                shuiyingString=worksDetail.getData().getMateriallist().get(i).getMaterial_store_url();
            }else if(worksDetail.getData().getMateriallist().get(i).getFiletype().equals("2")){
                tupianString=worksDetail.getData().getMateriallist().get(i).getMaterial_store_url();
            }
        }
        return shuiyinPosition;
    }

    private void initPlayer(WorksDetails worksDetails, MVidioView mVidioView) {
        String url = null;
        List<WorksDetails.DataBean.MateriallistBean> materiallist = worksDetails.getData().getMateriallist();
        if (materiallist == null) return;
        for (WorksDetails.DataBean.MateriallistBean bean : materiallist) {
            if (bean.getFiletype().equals("0")) {
                url = BASE_USER_RESOURE + bean.getMaterial_store_url();
                break;
            }
        }
        if (url == null) return;
        ivPlayv.setVisibility(View.VISIBLE);
        ivPlayh.setVisibility(View.VISIBLE);
        mVidioView.setVisibility(View.GONE);
        mVidioView.setVideoURI(Uri.parse(url));
    }

    private void initBanner(Banner banner, List<WorksDetails.DataBean.MateriallistBean> materiallist) {

        List<WorksDetails.DataBean.MateriallistBean> tempMateriallist=new ArrayList<>();
        for (int i=0;i<materiallist.size();i++){
            if(materiallist.get(i).getFiletype().equals("2")){
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
                WorksDetails.DataBean.MateriallistBean materiallistBean = (WorksDetails.DataBean.MateriallistBean) path;
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

    private void finishWorkRangeMarket(final String work_id) {
        BlueDialog.create(getFragmentManager())
                .setNormalDialogListenner(new BlueDialog.NormalDialogListenner() {
                    @Override
                    public void onClick(BlueDialog.NormalType normalType) {
                        if (normalType == BlueDialog.NormalType.NO) {
                            showProgressDialog("");
                            RxRetrofitClient.getInstance(PushReqDtActivity.this).finishWorkRangeMarket(work_id, "all", new Observer<VCodeBenan>() {
                                @Override
                                public void onCompleted() {
                                    closeProgressDialog();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Utils.toast(PushReqDtActivity.this, "请检查网络是否正常");
                                    try {
                                        throw e;
                                    } catch (Throwable throwable) {
                                        throwable.printStackTrace();
                                    }

                                }

                                @Override
                                public void onNext(VCodeBenan vCodeBenan) {
                                    if (vCodeBenan.getCode() == 1) {
                                        EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                                        EventBus.getDefault().post(new Event.RefreshPushListEvent(null));
                                        finish();
                                    } else
                                        Utils.toast(PushReqDtActivity.this, vCodeBenan.getMessage());
                                }
                            });
                        }

                    }
                })
                .setmTileText("")
                .setmContent(new SpannableString("删除此作品的投放并回收剩余预算"))
                .setTvContentGravity(Gravity.CENTER_HORIZONTAL)
                .setmCannelText("确定")
                .setmConfirmText("取消")
                .setmOutsideCancel(false)
                .setmBackCancel(true)
                .setmTag("提示")
                .setDialogViewListener(new BaseDialog.DialogViewListener() {
                    @Override
                    public void bindView(View v) {

                    }

                    @Override
                    public void dismiss() {

                    }
                })
                .show();


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

    @Override
    protected void onDestroy() {
        playMediaFinish();
        super.onDestroy();
    }

}
