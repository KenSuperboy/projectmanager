package com.gohoc.xiupuling.ui.requirement;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.AddPicAdater_4;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.callback.SourceSubmitCallback;
import com.gohoc.xiupuling.constant.EventFactory;
import com.gohoc.xiupuling.constant.OnUploadListener;
import com.gohoc.xiupuling.dialog.SourceSubmitDialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.AnimationTools;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.DeviceUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.ThreadTask;
import com.gohoc.xiupuling.utils.UploadManage;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.MVidioView;
import com.gohoc.xiupuling.widget.dialog.BaseDialog;
import com.gohoc.xiupuling.widget.dialog.NormalDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.VideoPicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.bean.VideoItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.video.VideoGridActivity;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.BaseConstant.IMAGE_PICKER;
/*
* 上传背景图片   视频
* */
public class CreateReqUploadData_4_Activity extends BasicActivity implements OnUploadListener {

    @BindView(R.id.tips_tv)
    TextView tipsTv;
    @BindView(R.id.add_pic)
    ImageView addPic;
    @BindView(R.id.pic_del)
    ImageView picDel;
    @BindView(R.id.progressBarPic)
    ProgressBar progressBarPic;
    @BindView(R.id.picRl)
    RelativeLayout picRl;
    @BindView(R.id.add_video)
    ImageView addVideo;
    @BindView(R.id.vide_del)
    ImageView videDel;
    @BindView(R.id.videRl)
    RelativeLayout videRl;
    @BindView(R.id.update_ps_tv)
    TextView updatePsTv;
    //@BindView(R.id.progressBar)
    //ProgressBar progressBar;
    //@BindView(R.id.del_pic_iv)
    //ImageView delPicIv;
    @BindView(R.id.bgIv)
    ImageView bgIv;
    @BindView(R.id.minus_iv)
    ImageView minusIv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.play_iv)
    ImageView playIv;
    @BindView(R.id.paus_iv)
    ImageView pausIv;
    @BindView(R.id.stop_iv)
    ImageView stopIv;
    @BindView(R.id.width_tv)
    TextView widthTv;
    @BindView(R.id.height_tv)
    TextView heightTv;
    @BindView(R.id.x_tv)
    TextView xTv;
    @BindView(R.id.y_tv)
    TextView yTv;
    @BindView(R.id.edit_vido)
    LinearLayout editVido;
    @BindView(R.id.logout_button_ll)
    LinearLayout logoutButtonLl;
    @BindView(R.id.m_video_view)
    MVidioView mVideoView;
    @BindView(R.id.view_frame)
    FrameLayout mFrameLayout;//只是视频容器
    @BindView(R.id.content_frame)
    RelativeLayout mFrameContenLayout;
    @BindView(R.id.action_bar_title)
    RelativeLayout mActionBar;
    @BindView(R.id.title_message_tv)
    TextView mTvTitleMessage;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_float)
    ImageView iv_float;
    private ArrayList<PicBean> picList;
    private int og;
    private AddPicAdater_4 addPicAdater;

    private PicBean bgPic;
    private PicBean video;

    private int mW, mH, mX, mY;
    private int mType = -1,dialogType;
    private ImagePicker imagePicker;

    private int mWidth, mHeight;//展现容器的高度和宽度
    private VideoPicker videoPicker;
    private float sc;
    private boolean hasData = false;
    private int videoWidth = 0, viewHeight = 0;
    private boolean isRest = false;
    private boolean isUploadSueed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_upload_data_4);
        ButterKnife.bind(this);

        //获取各种初始化
        UploadManage.INSTANS.setOnUploadListener(this);
        imagePicker = ImagePicker.getInstance();
        videoPicker = VideoPicker.getInstance();

        og = getIntent().getIntExtra("og", 0);
        initDates();
        initType();

        //取得窗口属性和宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //无效设置
        int width = dm.widthPixels;
        int iconHeight = (width - dip2px(this, 52)) / 2;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iconHeight, iconHeight);
        addPic.setLayoutParams(layoutParams);
        addVideo.setLayoutParams(layoutParams);

        //无效设置
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) progressBarPic.getLayoutParams();
        layoutParams1.width = iconHeight - dip2px(this, 10);
        layoutParams1.height = dip2px(this, 7);
        progressBarPic.setLayoutParams(layoutParams1);
    }

    private PicBean getVideoBean()
    {
        PicBean picBean = null;
        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isVideo()){
                picBean=picList.get(i);
            }
        }
        return picBean;
    }

    private PicBean getPicBean()
    {
        PicBean picBean = null;
        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isPic()){
                picBean=picList.get(i);
            }
        }
        return picBean;
    }

    private void initDates() {
        imagePicker.setMultiMode(false);

        ///获取记录
        Intent intent = getIntent();
        Bundle data = null;
        data = intent.getExtras();
        // TODO: 2017/6/8 0008 传入类型
        if (data != null) {
            mType = getIntent().getExtras().getInt("type", -1);
        }
        Log.d("TAG","类型："+mType);
        //处理横竖屏
        //计算并设置宽度高度
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        int w = wm.getDefaultDisplay().getWidth() - dip2px(this, 32);//整个容器的宽度
        if (og != 1) {//0 的话是横板
            iv_float.setImageResource(R.mipmap.icon_hengbanui);
            float scale = 9f / 16f;
            mWidth = w;
            mHeight = (int) (w * scale);//高度会更小
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();

        } else {//   1的话是竖版
            iv_float.setImageResource(R.mipmap.icon_shubanui);
            float scale = 16f / 9f;
            mWidth = w / 2;
            mHeight = (int) ((w / 2) * scale);//高度会更高
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w / 2, (int) ((w / 2) * scale));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
        }

        //这里的比例是  高度：宽度
        mFrameLayout.setOnTouchListener(onTouchListener);

        if (null != intent) {//再次进来各种数据的设定
            data = intent.getExtras();
            if (null != data)
                picList = (ArrayList<PicBean>) data.get("picList");
            if (null == picList || picList.size() == 0) {
                UploadManage.INSTANS.init();
                picList = new ArrayList<PicBean>();
            } else {
                hasData = true;
                //if (picList.size() == 1) {
                    /*
                    PicBean picBean = showSizeone();
                    bgPic = picBean;
                    PicBean picBean1 = new PicBean();
                    picBean1.setStatus(100);
                    picList.add(picBean1);
                */
                //}
                //} else if (picList.size() == 2) {
                    //PicBean picBean1 = showSizeone();
                    //bgPic = picBean1;
                    //PicBean picBean = picList.get(1);

                bgPic=getPicBean();

                if (bgPic != null && bgPic.getLocUrl() != null) {
                    LogUtil.d("图片路径："+bgPic.getLocUrl());
                    Glide.with(CreateReqUploadData_4_Activity.this)
                            .load(bgPic.getLocUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(bgIv);
                }

                    video = getVideoBean();
                    if(video!=null){//获取到的视频不等于空才去设定
                    if (video.getProgress() == 100) {
                        //progressBar.setProgress(100);
                        //delPicIv.setImageResource(R.mipmap.button_canshujindutiao_gou);
                        isUploadSueed = true;
                        //delPicIv.setEnabled(false);
                    }
                    addVideo.setImageBitmap(getVideoBitmap(video.getLocUrl()));
                    mW = video.getW();
                    mH = video.getH();
                    mX = video.getX();
                    mY = video.getY();

                        //单纯的视频容器
                    mFrameLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            int x = 0;
                            int y = 0;
                            if (og == 1) {
                                //竖屏
                                x = 1080;
                                y = 1920;
                            } else {
                                //横屏
                                x = 1920;
                                y = 1080;
                            }
                            videoWidth = video.getCurrW();
                            viewHeight = video.getCurrH();
                            tempW=videoWidth;
                            tempH=viewHeight;
                            mFrameLayout.setVisibility(View.VISIBLE);
                            int left = Math.round((((float) mX / (float) x) * (float) mWidth));//屏幕比例
                            int top = Math.round((((float) mY / (float) y) * (float) mHeight));//屏幕比例
                            LogUtil.d("left:"+left+"====right:"+top);
                            LogUtil.d(video.getCurrW()+"：设定视频的高度和宽度："+video.getCurrH());
                            mVideoView.setMeasure(video.getCurrW(), video.getCurrH());//设定大小
                            //FixMe 请求调整
                            mVideoView.requestLayout();
                            //设定位置
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
                            layoutParams.setMargins(left, top, mWidth - video.getCurrW() - left, mHeight - video.getCurrH() - top);
                            mFrameLayout.setLayoutParams(layoutParams);
                            //设置视频路径
                            LogUtil.d("进来播放视频罗："+video.getLocUrl());
                            mVideoView.setVideoURI(Uri.parse(video.getLocUrl()));
                            //开始播放视频
                            mVideoView.start();
                            xTv.setText(String.format("X:%s", video.getX()));
                            yTv.setText(String.format("Y:%s", video.getY()));
                            heightTv.setText(String.format("H:%s", video.getH()));
                            widthTv.setText(String.format("W:%s", video.getW()));
                        }
                    });
                    }
                //}
            }
        }


        if (picList.size() == 0) {
            PicBean picBean = new PicBean();
            picBean.setStatus(100);
            picList.add(picBean);
        }
        addPicAdater = new AddPicAdater_4(CreateReqUploadData_4_Activity.this, picList);
        addPicAdater.setMaxCount(2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(addPicAdater);
        addPicAdater.setOnPicChangeListion(
                new AddPicAdater_4.OnPicChangeListion() {
                    @Override
                    public void OnDel(View v, int postion) {
                        if (UploadManage.INSTANS.uploadImageCount > 0)
                            UploadManage.INSTANS.uploadImageCount--;
                        if (picList.get(picList.size() - 1).getType() == 1) {
                            picList.add(picList.size(), new PicBean().setStatus(100));
                        }
                        if (picList.get(postion).isVideo()) {
                            mVideoView.pause();
                            video = null;
                            mFrameLayout.setVisibility(View.GONE);
                            canelUpdate();
                        } else {
                            bgPic = null;
                            bgIv.setVisibility(View.GONE);
                        }
                        PicBean remove = picList.remove(postion);
                        UploadManage.INSTANS.setCannelUpload(remove.isVideo() ? remove.getUpdatefileName() : remove.getUrl());
                        addPicAdater.notifyDataSetChanged();
                    }


                    @Override
                    public void OnAdd(View v, int postion) {
                        PicBean picBean = picList.get(postion);
                        if (picBean.getStatus() == 100) {
                            if (picList != null) {
                                boolean hasVidio = false;
                                boolean hasPic = false;
                                for (PicBean picBean1 : picList) {
                                    if (picBean1.isVideo()) {
                                        hasVidio = true;
                                        break;
                                    }
                                    if (picBean1.isPic()) {
                                        hasPic = true;
                                        break;
                                    }
                                }
                                if (hasVidio) {
                                    dialogType=3;
                                    showDialog();
                                } else if (hasPic) {
                                    dialogType=2;
                                    showDialog();
                                } else {
                                    dialogType=1;
                                    showDialog();
                                }
                            }
                        }
                    }

                    @Override
                    public void OnMove(List<PicBean> picBeanList) {

                    }
                }
        );

        mVideoView.setOnCompletionListener(new CreateReqUploadData_4_Activity.MyPlayerOnCompletionListener());
        mVideoView.setOnPreparedListener(onPreparedListener);
    }

    private void initType()
    {
        if(mType==1||mType==2){
            dialogType=6;
        }else if(mType==3){
            dialogType=4;
        }else if(mType==4){
            dialogType=1;
        }
    }

    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            LogUtil.d("视频监听==============");
            //FixMe 获取视频资源的宽度
            int mVideoWidth = mp.getVideoWidth();
            //FixMe 获取视频资源的高度
            int mVideoHeight = mp.getVideoHeight();
            if (videoWidth != 0 && viewHeight != 0) {
                LogUtil.d("视频判断进来");
                mVideoView.getHolder().setFixedSize(videoWidth, viewHeight);
                //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                mVideoView.setMeasure(videoWidth, viewHeight);
                //FixMe 请求调整
                mVideoView.requestLayout();
                mVideoView.post(new Runnable() {
                    @Override
                    public void run() {
                        countXY(mFrameLayout);
                        countVideoWH(mFrameLayout);
                    }
                });
                videoWidth = 0;
                viewHeight = 0;
            } else {
                LogUtil.d("播放的时候重新绘制视频大小");
                if (isRest) {
                    isRest = false;
                    return;
                }
                //FixMe 在资源尺寸可以播放观看时处理

                int height = mHeight;
                float scale = (float) mVideoWidth / (float) mVideoHeight;
                mVideoWidth = mWidth;
                mVideoHeight = Math.round(mVideoWidth / scale);
                if (mVideoHeight > height) {
                    mVideoHeight = height;
                    mVideoWidth = Math.round(mVideoHeight * scale);
                }

                mVideoView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                mVideoView.setMeasure(mVideoWidth, mVideoHeight);
                //FixMe 请求调整
                mVideoView.requestLayout();
                mVideoView.post(new Runnable() {
                    @Override
                    public void run() {
                        countXY(mFrameLayout);
                        countVideoWH(mFrameLayout);
                    }
                });
            }

            sc = (float) mVideoWidth / (float) mVideoHeight;
        }
    };


    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private PicBean showSizeone() {
        PicBean picBean = picList.get(0);
        if (picBean != null && picBean.getLocUrl() != null) {
            Glide.with(CreateReqUploadData_4_Activity.this)
                    .load(picBean.getLocUrl())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(addPic);
            picRl.setVisibility(View.VISIBLE);
            picDel.setVisibility(View.VISIBLE);
            videRl.setVisibility(View.VISIBLE);
        }
        if (picBean != null && picBean.getLocUrl() != null) {
            Glide.with(CreateReqUploadData_4_Activity.this)
                    .load(picBean.getLocUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bgIv);
        }

        return picBean;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                LogUtil.d("图片上传");
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                // TODO: 2017/7/12 0012 操作数据
                if (images == null || images.size() == 0) return;

                RelativeLayout.LayoutParams mFrameLayout_ivLayoutParams = (RelativeLayout.LayoutParams) bgIv.getLayoutParams();
                mFrameLayout_ivLayoutParams.width=mWidth;
                mFrameLayout_ivLayoutParams.height=mHeight;
                bgIv.setLayoutParams(mFrameLayout_ivLayoutParams);
                bgIv.invalidate();

                ThreadTask.getInstance().executorDBThread(new Runnable() {
                    @Override
                    public void run() {
                        for (ImageItem a : images) {
                            a.path = com.gohoc.xiupuling.utils.ImageUtils.compress(CreateReqUploadData_4_Activity.this, a.path);
                            UploadManage.INSTANS.uploadImageCount++;

                            if (addPicAdater.getItemCount() == addPicAdater.getMaxCount()) {
                                //最后一张
                                String uploadKey = Utils.getUploadKey(UpLoadConstant.REQUIREMENT, a.path);
                                picList.get(picList.size() - 1).setType(1).setStatus(0).setUpdatefileName(uploadKey).setLocUrl(a.path).setPic(true);
                                uploadFile(picList.get(picList.size() - 1), picList.size() - 1);
                            }else {
                                bgPic = new PicBean();
                                LogUtil.d("是本地路径吗："+a.path);
                                bgPic.setLocUrl(a.path);
                                bgPic.setType(1);
                                bgPic.setPic(true);
                                bgPic.setUpdatefileName(Utils.getUploadKey(UpLoadConstant.REQUIREMENT, a.path));
                                picList.add(picList.size() - 1, bgPic);
                                uploadFile(bgPic,picList.size() - 2);
                            }

                            //公共信息显示
                            final ImageItem imageItem = a;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(CreateReqUploadData_4_Activity.this)
                                            .load(imageItem.path)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(bgIv);
                                    bgIv.setVisibility(View.VISIBLE);
                                    //picList.add(0, bgPic);
                                    addPicAdater.notifyDataSetChanged();
                                    /*if (video != null) {
                                        mVideoView.start();
                                    }*/
                                }
                            });

                        }
                    }
                }, ThreadTask.ThreadPeriod.PERIOD_HIGHT);
            }
        } else {
            if (resultCode == VideoPicker.RESULT_VIDEO_ITEMS) {
                LogUtil.d("视频上传111111111");
                if (data != null && requestCode == 300) {
                    LogUtil.d("视频上传222222222");
                    ArrayList<VideoItem> videos = (ArrayList<VideoItem>) data.getSerializableExtra(VideoPicker.EXTRA_RESULT_VIDEO_ITEMS);
                    if (videos == null) return;
                    final VideoItem videoItem = videos.get(0);
                    NormalDialog.create(getFragmentManager())
                            .setNormalDialogListenner(new NormalDialog.NormalDialogListenner() {
                                @Override
                                public void onClick(NormalDialog.NormalType normalType) {
                                    if (normalType == NormalDialog.NormalType.OK) {
                                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
                                        layoutParams.setMargins(0, 0, 0, 0);
                                        mFrameLayout.setLayoutParams(layoutParams);
                                        UploadManage.INSTANS.uploadImageCount++;

                                        /**************************************************************/
                                        if (addPicAdater.getItemCount() == addPicAdater.getMaxCount()) {
                                            //最后一张
                                            String uploadKey = Utils.getUploadKey(UpLoadConstant.REQUIREMENT, videoItem.path);
                                            picList.get(picList.size() - 1).setType(1).setStatus(0).setUpdatefileName(uploadKey).setLocUrl(videoItem.path).setVideo(true);
                                            uploadFile(picList.get(picList.size() - 1), picList.size() - 1);
                                            isMyVideo=true;
                                        } else {
                                            video = new PicBean();
                                            video.setLocUrl(videoItem.path);
                                            video.setType(1);
                                            video.setVideo(true);
                                            String uploadKey = Utils.getUploadKey(UpLoadConstant.REQUIREMENT, videoItem.path);
                                            video.setUpdatefileName(uploadKey);
                                            picList.add(picList.size() - 1, video);
                                            uploadFile(video, picList.size() - 2);
                                        }
                                        /**************************************************************/

                                        /*video = new PicBean();
                                        video.setType(1)
                                                .setUpdatefileName(Utils.getUploadKey(UpLoadConstant.REQUIREMENT, videoItem.path))
                                                .setLocUrl(videoItem.path)
                                                .setVideo(true);
                                        uploadFile(video);*/

                                        //设置视频路径
                                        mVideoView.setVideoURI(Uri.parse(videoItem.path));
                                        //开始播放视频
                                        mVideoView.start();
                                        mFrameLayout.setVisibility(View.VISIBLE);
                                        videDel.setVisibility(View.VISIBLE);
                                        addVideo.setImageBitmap(getVideoBitmap(videoItem.path));
                                        xTv.setText(String.format("X:%s", 0));
                                        yTv.setText(String.format("Y:%s", 0));
                                        //picList.remove(picList.size() - 1);
                                        //picList.add(picList.size() - 1, video);
                                        addPicAdater.notifyDataSetChanged();
                                    }
                                }
                            })
                            .setmTileText("提示")
                            .setmContent("视频文件大小为" + Utils.getPrintSize(videoItem.size) + ",确定上传吗？\n 注：上传流量大，请在WIFI下操作。")
                            .setmCannelText("取消")
                            .setmConfirmText("上传")
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
            }
        }
    }

    boolean isMyVideo;

    /**
     * @param path 媒体文件绝对路径
     * @return 返回媒体文件缩略图
     */
    public static Bitmap getVideoBitmap(String path) {
        Log.e("Icon", "path:" + path);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            Bitmap frameAtTime = retriever.getFrameAtTime();
            return frameAtTime;
        } catch (Exception e) {
            return null;
        } finally {
            retriever.release();
        }

    }

    private void chooseVideo() {
        videoPicker.setMultiMode(false);
        videoPicker.setShowCamera(false);
        Intent i1 = new Intent(this, VideoGridActivity.class);
        startActivityForResult(i1, 300);
    }

    private void uploadFile(final PicBean picBean, final int potions) {
        RxRetrofitClient.getInstance(CreateReqUploadData_4_Activity.this).getUploadToken(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CreateReqUploadData_4_Activity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    Logger.e(picBean.getLocUrl());
                    Logger.e(vCodeBenan.getMessage());
                    if (picBean.isVideo()) {
                    }
                    UploadManage.INSTANS.upload(picBean, potions, vCodeBenan, CreateReqUploadData_4_Activity.this);

                }
            }
        });
    }

    private SourceSubmitDialog mSourceSubmitDialog;//(图片、视频、拍照)
    public void showDialog() {
            mSourceSubmitDialog=new SourceSubmitDialog(this, new SourceSubmitCallback() {
                @Override
                public void tupian() {
                    showMenu(1);
                    mSourceSubmitDialog.dismiss();
                }

                @Override
                public void shiping() {
                    chooseVideo();
                    mSourceSubmitDialog.dismiss();
                }

                @Override
                public void yinping() {

                }

                @Override
                public void shuiying() {

                }

                @Override
                public void paishe() {
                    showMenu(0);
                    mSourceSubmitDialog.dismiss();
                }
            },dialogType);
        if((picList!=null&&picList.size()==0)||picList==null){
            LogUtil.d("首次进来：1111111111");
            mSourceSubmitDialog.setMyClick(true,true,false,false,true);
        }else {
            LogUtil.d("不是首次进来：22222222222");
            mSourceSubmitDialog.setMyClick(!CustomUtils.getPic(picList),!CustomUtils.getVideo(picList),false,false,!CustomUtils.getPic(picList));
        }
        mSourceSubmitDialog.show();
        /*
        new AlertView(null, null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {
                showMenu(position);
            }
        }).show();
    */}

    private void showMenu(int type) {
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);
        if (og == 0) {
            imagePicker.setFocusWidth(DeviceUtils.getScreenSize(this)[0]);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(DeviceUtils.getScreenSize(this)[0] * 9 / 16);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(1920);//保存文件的宽度。单位像素
            imagePicker.setOutPutY(1080);//保存文件的高度。单位像素
        } else {
            imagePicker.setFocusWidth(DeviceUtils.getScreenSize(this)[0] * 3 / 5);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(DeviceUtils.getScreenSize(this)[0] * 16 / 15);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(1080);//保存文件的宽度。单位像素
            imagePicker.setOutPutY(1920);//保存文件的高度。单位像素
        }
        if (type == 0) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, IMAGE_PICKER);
        } else if (type == 1) {
            Intent intent = new Intent(CreateReqUploadData_4_Activity.this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        }
    }


    private void canelUpdate() {
        //progressBar.setProgress(0);
        isUploadSueed = false;
        //delPicIv.setEnabled(true);
        //delPicIv.setImageResource(R.mipmap.btn_guanbi);
        if (UploadManage.INSTANS.uploadImageCount > 0) {
            UploadManage.INSTANS.uploadImageCount--;
        }
    }

    //隐藏标题栏
    private void hideTitle()
    {
        TranslateAnimation translateAnimation= AnimationTools.getTranslateAnimation(0,0,0,-mActionBar.getMeasuredHeight(), BaseConstant.TITLETIME);


        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setStatusColor(R.color.black);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mActionBar.setAnimation(translateAnimation);
        translateAnimation.start();
        mActionBar.setVisibility(View.GONE);
    }

    //展现标题栏
    private void showTitlte()
    {
        setStatusColor(R.color.colorPrimary);
        TranslateAnimation translateAnimation=AnimationTools.getTranslateAnimation(0,0,-mActionBar.getMeasuredHeight(),0, BaseConstant.TITLETIME);
        mActionBar.setAnimation(translateAnimation);
        translateAnimation.start();
        mActionBar.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.action_bar_left_title, R.id.action_bar_title, R.id.title_message_tv, R.id.view_frame, R.id.minus_iv,R.id.add_pic, R.id.pic_del, R.id.add_video, R.id.vide_del, R.id.del_pic_iv, R.id.add_iv, R.id.play_iv, R.id.paus_iv, R.id.stop_iv, R.id.width_tv, R.id.height_tv, R.id.x_tv, R.id.y_tv, R.id.logout_button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_bar_title:
                if (mActionBar.isShown()) {
                    hideTitle();
                }
                break;
            case R.id.title_message_tv:
                if (!mActionBar.isShown()) {
                    showTitlte();
                }
                break;
            case R.id.action_bar_left_title:
                saveMessage();
                break;
            case R.id.add_iv:
                if (video != null||isMyVideo){
                    zoomIn();
                }
                break;
            case R.id.minus_iv:
                if (video != null||isMyVideo){
                    zoomOut();
                }
                break;
            case R.id.play_iv:
                if (video != null||isMyVideo)
                    //开始播放视频
                    mVideoView.start();
                break;
            case R.id.paus_iv:
                if (video != null||isMyVideo)
                    mVideoView.pause();
                break;
            case R.id.stop_iv:
                if (video != null||isMyVideo) {
                    isRest = true;
                    mVideoView.stopPlayback();
                    //设置视频路径
                    mVideoView.setVideoURI(Uri.parse(video.getLocUrl()));
                }
                break;
            case R.id.logout_button_ll:
                /*picList.clear();
                if (bgPic != null) {
                    picList.add(bgPic);
                }
                if (video != null) {
                    countXY(mFrameLayout);
                    countVideoWH(mFrameLayout);
                    video.setX(mX);
                    video.setY(mY);
                    video.setH(mH);
                    video.setW(mW);
                    video.setCurrW(mVideoView.getMeasuredWidth());
                    video.setCurrH(mVideoView.getMeasuredHeight());
                    picList.add(video);
                }*/
                LogUtil.d("自身宽度："+mFrameLayout.getMeasuredWidth()+"====自身高度："+mFrameLayout.getMeasuredHeight());
                LogUtil.d("容器宽度："+mWidth+"====容器高度："+mHeight+"=====left:"+mFrameLayout.getLeft()+"===right:"+mFrameLayout.getRight()+"=====top:"+mFrameLayout.getTop()+"====btn:"+mFrameLayout.getBottom());
                saveMessage();
                break;
            case R.id.del_pic_iv:
                if (video != null) {
                    if (video.isVideo()) {
                        String url = video.getUpdatefileName();
                        UploadManage.INSTANS.setCannelUpload(url);
                        mVideoView.pause();
                        video = null;
                        mFrameLayout.setVisibility(View.GONE);
                        canelUpdate();
                        picList.clear();
                        if (bgPic != null) {
                            picList.add(bgPic);
                            picList.add(picList.size(), new PicBean().setStatus(100));
                            addPicAdater.notifyDataSetChanged();
                        }
                    }
                }
        }
    }

    //保存编辑信息
    private void saveMessage()
    {
        LogUtil.d("图嵌视频横坐标："+mX+"=====纵坐标："+mY+"=====高度:"+mH+"=====宽度："+mW+"========:"+mVideoView.getMeasuredWidth()+"=====:"+mVideoView.getMeasuredHeight());
        if (video != null) {
            LogUtil.d("视频不等于空");
            video.setX(mX);
            video.setY(mY);
            video.setH(mH);
            video.setW(mW);
            video.setCurrW(mVideoView.getMeasuredWidth());
            video.setCurrH(mVideoView.getMeasuredHeight());
        }

        if(containVideo()){
            picList.get(videoPosition).setX(mX);
            picList.get(videoPosition).setY(mY);
            picList.get(videoPosition).setW(mW);
            picList.get(videoPosition).setH(mH);
            picList.get(videoPosition).setCurrW(mVideoView.getMeasuredWidth());
            picList.get(videoPosition).setCurrH(mVideoView.getMeasuredHeight());
        }

        LogUtil.d("准备好："+picList.size());
        if (picList != null && picList.size() > 0) {
            setResult(RESULT_OK, new Intent().putExtra("picList", picList).putExtra("type", mType));
        }
        onBackPressed();
    }

    private int videoPosition;
    private boolean containVideo()
    {
        boolean isVideo=false;

        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isVideo()){
                isVideo=true;
                videoPosition=i;
            }
        }
        return isVideo;
    }

    @Override
    public void complete(String key, ResponseInfo info, JSONObject res, PicBean picBean) {
        if (UploadManage.INSTANS.uploadImageCount > 0) {
            UploadManage.INSTANS.uploadImageCount--;
        }
        if (UploadManage.INSTANS.uploadImageCount == 0) {
            EventBus.getDefault().post(new EventFactory.pleaseUploadData());
        }
        //res包含hash、key等信息，具体字段取决于上传策略的设置
        if (info.isOK()) {
            Logger.d("qiniu:Upload Success");
            if (picBean.isVideo()) {
                if (video != null) {
                    video.setProgress(100);
                }
                isUploadSueed = true;
                //delPicIv.setEnabled(false);
                //delPicIv.setImageResource(R.mipmap.button_canshujindutiao_gou);
            } else {
                //progressBarPic.setVisibility(View.GONE);
            }

        } else {
            //  Logger.d(");
            Logger.e("qiniu Upload Fail" + res);
            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
        }
        Logger.d("qiniu: " + key + ",\r\n " + info + ",\r\n " + res);
    }

    @Override
    public void progress(String key, final double percent, final PicBean picBean, int potions) {
        Log.i("kekegdsz", Thread.currentThread().getName() + "");
        Logger.e(percent + "");
        LogUtil.d(potions+":上传进度："+percent);
        /*if (!picBean.isVideo()) {
            picList.get(0).setProgress((int) (percent * 100));
            addPicAdater.notifyItemChanged(0);
        }
        if (picBean.isVideo()) {
            //progressBar.setProgress((int) (percent * 100));
        }*/

        if (potions > picList.size() - 1) return;
        picList.get(potions).setProgress((int) (percent * 100));
        addPicAdater.notifyItemChanged(potions);

        /*if (picBean.isVideo()||picBean.isPic()) {
            if (potions > picList.size() - 1) return;
            picList.get(potions).setProgress((int) (percent * 100));
            addPicAdater.notifyItemChanged(potions);
        }*/
    }


    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.i("kekegdsz", "播放完成了");
        }
    }

    /**
     * vidioview 操作进行处理
     */
    int lastX, lastY;
    double nLenStart = 0;

    RelativeLayout.LayoutParams layoutParams;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int ea = event.getAction();
            Log.i("TAG", "Touch:" + ea);

            //Toast.makeText(DraftTest.this, "位置："+x+","+y, Toast.LENGTH_SHORT).show();

            switch (ea) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    mode = DRAG;
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    //多指缩放
                    Log.e("TAG", "多指移动");
                    mode = SCALE;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    int left = v.getLeft() + dx;
                    int top = v.getTop() + dy;
                    int right = v.getRight() + dx;
                    int bottom = v.getBottom() + dy;
                    LogUtil.d("各种dx："+dx+"====dy:"+dy+"===left:"+left+"====top:"+top+"====right:"+right+"====bottom:"+bottom);
                    if (mode == DRAG) {
                        Log.i("kekegds+pa", mHeight + "," + mHeight);
                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }

                        if (right > mWidth) {
                            right = mWidth;
                            left = right - v.getWidth();
                        }

                        if (top < 0) {
                            top = 0;
                            bottom = top + v.getHeight();
                        }

                        if (bottom > mHeight) {
                            bottom = mHeight;
                            top = bottom - v.getHeight();
                        }
                        v.layout(left, top, right, bottom);
                        layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        layoutParams.setMargins(left, top, 0, 0);

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        countXY(v);
                    } else if (mode == SCALE) {
                        Log.i("kekegdsz", "多次触控");
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    // TODO: 2017/6/7 0007 抬起手指计算xy
                    // TODO: 2017/7/20 0020 设置view位置属性
                    if (mode == DRAG) {
                        if (layoutParams != null){
                            v.setLayoutParams(layoutParams);
                        }
                    }
                    break;
            }
            // TODO: 2017/6/6 0006 消费掉事件

            return true;
        }
    };


    private void countXY(View v) {
        int top = v.getTop();
        int left = v.getLeft();
        int x = 0;
        int y = 0;

        float hs = (float) top / (float) mHeight;
        float ws = (float) left / (float) mWidth;

        if (og != 1) {
            y = Math.round(hs * 1080);
            x = Math.round(ws * 1920);
        } else {
            y = Math.round(hs * 1920);
            x = Math.round(ws * 1080);
        }
        Log.i("kekegdszXY", top + "," + left + "," + mWidth + "," + mHeight + "====" + x + "xy" + y);
        xTv.setText(String.format("X:%s", x));
        yTv.setText(String.format("Y:%s", y));
        mX = x;
        mY = y;

    }

    //宽和高    自身的宽和高    这两者不一样
    public void countVideoWH(View view) {
        int measuredHeight = view.getMeasuredHeight();
        int measuredWidth = view.getMeasuredWidth();
        float sh = (float) measuredHeight / (float) mHeight;//所占比例
        float sw = (float) measuredWidth / (float) mWidth;//所占比例
        int x = 0;
        int y = 0;
        if (og == 1) {
            x = Math.round(sw * 1080);
            y = Math.round(sh * 1920);
        } else {
            x = Math.round(sw * 1920);
            y = Math.round(sh * 1080);
        }
        mW = x;
        mH = y;
        heightTv.setText(String.format("H:%s", y));
        widthTv.setText(String.format("W:%s", x));
    }


    /*@Override
    protected void onPause() {
        if (video != null)
            mVideoView.pause();
        super.onPause();
    }*/

    @Override
    protected void onDestroy() {
        if (video != null)
            mVideoView.stopPlayback();
        Log.i("onDestroy", mX + "," + mY + "," + mW + "," + mH);
        super.onDestroy();
    }

    /**
     * 放大view
     */
    public void zoomIn() {
        int left = mFrameLayout.getLeft();
        int top = mFrameLayout.getTop();
        int measuredWidth = mVideoView.getMeasuredWidth();
        // TODO: 2017/7/19 0019 首先判断是横屏还是竖屏
        int w = measuredWidth + 5;
        if (w > mWidth) {
            w = mWidth;
        }
        int h = (int) (w / sc);
        if (h > mHeight) {
            h = mHeight;
            w = (int) (h * sc);
        }
        int right = mWidth - w - left;
        if (right <= 0) {
            left = left + right;
        }

        int buttom = mHeight - h - top;
        if (buttom <= 0) {
            top = top + buttom;
        }
        mVideoView.setMeasure(w, h);
        //FixMe 请求调整
        mVideoView.requestLayout();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
        layoutParams.setMargins(left, top, 0, 0);
        mFrameLayout.setLayoutParams(layoutParams);
        mFrameLayout.requestLayout();
        mVideoView.post(new Runnable() {
            @Override
            public void run() {
                countXY(mFrameLayout);
                countVideoWH(mFrameLayout);
            }
        });


    }

    /**
     * 缩小view
     */
    public void zoomOut() {
        int left = mFrameLayout.getLeft();
        int top = mFrameLayout.getTop();
        int measuredWidth = mVideoView.getMeasuredWidth();
        // TODO: 2017/7/19 0019 测量200像素是多少宽度
        int min = 0;
        if (og != 0) {
            //竖屏
            min = (int) ((200f / 1080f) * mWidth);
        } else {
            min = (int) ((200f / 1920f) * mWidth);
        }
        int w = measuredWidth - 5;
        if (w < min) {
            w = min;
        }
        int h = (int) (w / sc);
        if (h < min) {
            h = min;
            w = (int) (h * sc);
        }
        LogUtil.d("====宽度:"+w+"====高度:"+h+"======"+left+":缩小："+top);
        mVideoView.setMeasure(w, h);
        //FixMe 请求调整
        mVideoView.requestLayout();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();

        layoutParams.setMargins(left, top, 0, 0);//如果是零的话，保持原状
        mFrameLayout.setLayoutParams(layoutParams);
        mFrameLayout.requestLayout();
        mVideoView.post(new Runnable() {
            @Override
            public void run() {
                countXY(mFrameLayout);
                countVideoWH(mFrameLayout);
            }
        });
    }


    @SuppressLint("NewApi")
    private void ShowCell() {
        int TitleHeight = mActionBar.getMeasuredHeight();
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(mActionBar, "translationY", -TitleHeight, 0)
        );
        set.setDuration(300).start();
    }

    public static final int DRAG = 1;
    public static final int SCALE = 2;
    int mode = 1;

    @Override
    public void onBackPressed() {
        if (hasData) {
            ArrayList<PicBean> picList = (ArrayList<PicBean>) getIntent().getExtras().get("picList");
            if (picList != null && picList.size() > 0) {
                Set<String> stringHashMap = new HashSet<>();
                for (PicBean picBean : this.picList) {
                    stringHashMap.add(picBean.isVideo() ? picBean.getUpdatefileName() : picBean.getUrl());
                }
                for (PicBean picBean : picList) {
                    if (!stringHashMap.contains(picBean.isVideo() ? picBean.getUpdatefileName() : picBean.getUrl())) {
                        UploadManage.INSTANS.setCannelUpload(picBean.isVideo() ? picBean.getUpdatefileName() : picBean.getUrl());
                    }
                }
            }
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        if(video!=null&&mVideoView!=null){
            mVideoView.setMeasure(tempW, tempH);
            mVideoView.requestLayout();
            mVideoView.seekTo(tempPosition);
            mVideoView.start();
        }
        super.onResume();
    }

    /*mVideoView.setMeasure(w, h);
    mVideoView.requestLayout();*/

    private int tempPosition;
    private int tempW,tempH;
    @Override
    protected void onPause() {
        if (video != null&&mVideoView!=null) {
            tempW=mVideoView.getMeasuredWidth();
            tempH=mVideoView.getMeasuredHeight();
            isRest = true;
            tempPosition=mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
        super.onPause();
    }
}
