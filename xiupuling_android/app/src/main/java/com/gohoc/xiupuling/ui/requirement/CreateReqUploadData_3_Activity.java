package com.gohoc.xiupuling.ui.requirement;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.AddPicAdater_1;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.callback.SourceSubmitCallback;
import com.gohoc.xiupuling.constant.EventFactory;
import com.gohoc.xiupuling.constant.OnUploadListener;
import com.gohoc.xiupuling.dialog.SourceSubmitDialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.DeviceUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.ThreadTask;
import com.gohoc.xiupuling.utils.UploadManage;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.MVidioView;
import com.gohoc.xiupuling.widget.SimplePaddingDecoration;
import com.gohoc.xiupuling.widget.dialog.BaseDialog;
import com.gohoc.xiupuling.widget.dialog.NormalDialog;
import com.gohoc.xiupuling.widget.itemtouchhelper.divider.DividerGridItemDecoration;
import com.gohoc.xiupuling.widget.itemtouchhelper.helper.ItemTouchHelperCallBack;
import com.gohoc.xiupuling.widget.itemtouchhelper.helper.OnRecyclerViewItemClickListener;
import com.gohoc.xiupuling.widget.itemtouchhelper.utils.VibratorUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.VideoPicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.bean.VideoItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.video.VideoGridActivity;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UploadManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

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
* 上传视频 : 一个视频   水印图片
* */
public class CreateReqUploadData_3_Activity extends BasicActivity implements OnUploadListener {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.tips_tv)
    TextView tipsTv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.update_ps_tv)
    TextView updatePsTv;
    //@BindView(R.id.progressBar)
    //ProgressBar progressBar;
    //@BindView(R.id.del_pic_iv)
    //ImageView delPicIv;
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
    @BindView(R.id.h_banner)
    Banner hBanner;
    @BindView(R.id.h_samplevideo)
    MVidioView mVideoView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.h_banner_rl)
    RelativeLayout hBannerRl;
    @BindView(R.id.h_samplevideo_rl)
    RelativeLayout hSamplevideoRl;
    @BindView(R.id.action_bar_title)
    RelativeLayout mActionBar;
    @BindView(R.id.title_message_tv)
    TextView mTvTitleMessage;
    @BindView(R.id.iv_float)
    ImageView iv_float;

    private ArrayList<PicBean> picList;
    private AddPicAdater_1 addPicAdater;
    private String fileUrl;
    private int maxCount = 5;
    private String[] tips = {"", "海报需要上传1张图片(最大5M)", "数码相册需要上传1~5张图片(最大5M)", "视频需要上传1个视频短片(最大100M)", "需要上传背景图(5M以内)和视频(100M以内)"};
    private int og;
    private ArrayList<PicBean> showPicList = new ArrayList<>();
    private int upW = 0;// 0 图片  1视频
    private boolean isCancelled = false;
    private int mType = -1,dialogType;
    ItemTouchHelper itemTouchHelper = null;
    @BindView(R.id.content_frame)
    RelativeLayout mFrameContenLayout;
    private ImagePicker imagePicker;
    private VideoPicker videoPicker;
    private boolean hasData = false;
    private MediaController mc;
    private boolean isUploadSueed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_upload_data_3);
        UploadManage.INSTANS.setOnUploadListener(this);
        imagePicker = ImagePicker.getInstance();
        videoPicker = VideoPicker.getInstance();
        ButterKnife.bind(this);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        og = getIntent().getIntExtra("og", 0);
        toolbarLeftTitle.setText("完成");
        toolbarRight.setVisibility(View.GONE);
        toolbarTitle.setText("自己制作");

        initDates();
        initType();
        initRv();
        // TODO: 2017/7/14 0014   视频控制器
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                             @Override
                                             public void onPrepared(MediaPlayer mp) {
                                                 //FixMe 获取视频资源的宽度
                                                 int mVideoWidth = mp.getVideoWidth();
                                                 //FixMe 获取视频资源的高度
                                                 int mVideoHeight = mp.getVideoHeight();
                                                 //FixMe 获取屏幕的宽度
                                                 DisplayMetrics display = CreateReqUploadData_3_Activity.this.getResources().getDisplayMetrics();
                                                 //FixMe 在资源尺寸可以播放观看时处理
                                                 ViewGroup parent = (ViewGroup) hSamplevideoRl.getParent();
                                                 int height = parent.getHeight();
                                                 int width = parent.getWidth();
                                                 float scale = (float) mVideoHeight / (float) mVideoWidth;

                                                 mVideoWidth = width;
                                                 mVideoHeight = (int) (mVideoWidth * scale);
                                                 if (mVideoHeight > height) {
                                                     mVideoHeight = height;
                                                 }
                                                 mVideoWidth = (int) (mVideoHeight / scale);


                                                 mVideoView.getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                                                 //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
                                                 mVideoView.setMeasure(mVideoWidth, mVideoHeight);
                                                 //FixMe 请求调整
                                                 mVideoView.requestLayout();

                                             }
                                         }

        );
    }

    private void initDates() {
        ///获取记录
        Intent intent = getIntent();
        Bundle data = null;
        if (null != intent) {
            data = intent.getExtras();
            mType = data.getInt("type", -1);
            Log.d("TAG","从那边传过来的："+mType);
            if (mType != -1) {
                tipsTv.setText(tips[mType]);
            }
            if (null != data) {
                picList = (ArrayList<PicBean>) data.get("picList");
            }
            if (null == picList || picList.size() == 0) {
                UploadManage.INSTANS.init();
                picList = new ArrayList<PicBean>();
                //progressBar.setProgress(0);
            } else {
                hasData = true;
                if (mType != 3 && mType != 4) {
                    initBanner(picList);
                } else if (mType == 3) {
                    if (picList.size() > 0) {
                        PicBean picBean = picList.get(0);
                        initPlayer(mVideoView, picBean);
                        int progress = picBean.getProgress();
                        if (progress == 100) {
                            //progressBar.setProgress(100);
                            //delPicIv.setImageResource(R.mipmap.button_canshujindutiao_gou);
                            isUploadSueed = true;
                            //delPicIv.setEnabled(false);
                        }
                    }
                }

            }
        }

        //处理横竖屏
        //计算并设置宽度高度
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        int w = wm.getDefaultDisplay().getWidth() - dip2px(this, 32);
        if (og != 1) {
            float scale = 9f / 16f;
            iv_float.setImageResource(R.mipmap.icon_hengbanui);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, (int) (w * scale));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
        } else {
            float scale = 16f / 9f;
            iv_float.setImageResource(R.mipmap.icon_shubanui);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w / 2, (int) ((w / 2) * scale));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
        }

        //处理作品类型
        if (mType == 4 || mType == 3) {
            //progressBar.setVisibility(View.VISIBLE);
            //delPicIv.setVisibility(View.VISIBLE);
            if (mType == 4)
                editVido.setVisibility(View.VISIBLE);
        } else {
            //progressBar.setVisibility(View.GONE);
            editVido.setVisibility(View.GONE);
            //delPicIv.setVisibility(View.GONE);
            mVideoView.setVisibility(View.GONE);
        }
    }

    private void initType()
    {
        if(mType==1||mType==2){
            dialogType=6;
        }else if(mType==3){
            dialogType=4;
        }
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void initBanner(final List<PicBean> picList) {

        List<PicBean> tempPicList=new ArrayList<>();

        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isMusic()){
                //picList.remove(i);
            }else if(picList.get(i).isPic()){
                tempPicList.add(picList.get(i));
            }
        }

        if (tempPicList == null || tempPicList.size() == 0) {
            return;
        }


        hBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        hBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                // Logger.d(path);
                PicBean picBean = (PicBean) path;
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Glide.with(context).load(picBean.getLocUrl()).into(imageView);
            }
        });
        hBanner.setImages(tempPicList);
        //设置banner动画效果
        hBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        hBanner.isAutoPlay(true);
        //设置轮播时间
        hBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        hBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        hBanner.start();
    }

    private void initRv() {

        if (picList.size() == 0) {
            PicBean picBean = new PicBean();
            picBean.setStatus(100);
            picList.add(picBean);
        } else if (picList.size() < 5 && mType == 2) {
            PicBean picBean = new PicBean();
            picBean.setStatus(100);
            picList.add(picBean);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(CreateReqUploadData_3_Activity.this, 2));
        recyclerView.addItemDecoration(new SimplePaddingDecoration(this));
        //设置adapter
        addPicAdater = new AddPicAdater_1(CreateReqUploadData_3_Activity.this, picList);
        addPicAdater.setMaxCount(2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addPicAdater);

        if (mType == 2) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
            itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallBack(addPicAdater));
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
        recyclerView.addOnItemTouchListener(new OnRecyclerViewItemClickListener(recyclerView) {
            @Override
            public void onLoncClick(RecyclerView.ViewHolder viewHolder) {
                if (itemTouchHelper == null) return;
                if (viewHolder.getLayoutPosition() != picList.size() - 1) {
                    itemTouchHelper.startDrag(viewHolder);
                    VibratorUtil.Vibrate(CreateReqUploadData_3_Activity.this, 60);//ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
            }
        });

        addPicAdater.setOnPicChangeListion(
                new AddPicAdater_1.OnPicChangeListion() {
                    @Override
                    public void OnDel(View v, int postion) {
                        if (UploadManage.INSTANS.uploadImageCount > 0)
                            UploadManage.INSTANS.uploadImageCount--;
                        if (picList.get(picList.size() - 1).getType() == 1) {
                            picList.add(picList.size(), new PicBean().setStatus(100));
                        }
                        PicBean remove = picList.remove(postion);
                        UploadManage.INSTANS.setCannelUpload(remove.isVideo() ? remove.getUpdatefileName() : remove.getUrl());
                        addPicAdater.notifyDataSetChanged();
                        updatePicshow();
                        if (mType == 4) {
                            if (showPicList.get(postion).isVideo())
                                upW = 1;
                            else
                                upW = 0;
                        }
                        if (mType == 3) {
                            isCancelled = true;
                            canelUpdate();
                        }
                    }

                    @Override
                    public void OnAdd(View v, int postion) {
                        PicBean picBean = picList.get(postion);
                        if (picBean.getStatus() == 100) {
                            if (mType == 3) {
                                boolean hasVidio = false;
                                boolean hasshuiyin = false;

                                for (PicBean picBean1 : picList) {
                                    if (picBean1.isVideo()) {
                                        hasVidio = true;
                                        break;
                                    }
                                    if (picBean1.isPic()) {
                                        hasshuiyin = true;
                                        break;
                                    }
                                }
                                if(hasVidio){
                                    dialogType=5;
                                }
                                if(hasshuiyin){
                                    dialogType=2;
                                }
                                showDialog();
                            } else if (mType == 4) {
                                if (upW == 0) {
                                    showDialog();
                                } else {
                                    chooseVideo();
                                }
                            } else if(mType==1||mType==2){
                                //图片、视频、水印
                                showDialog();
                            }
                        }
                    }

                    @Override
                    public void OnMove(List<PicBean> p) {
                        List<PicBean> o = new ArrayList<PicBean>();
                        List<PicBean> temps = new ArrayList<PicBean>();
                        o.addAll(p);
                        temps.addAll(p);
                        for (int a = 0; a < temps.size(); a++) {
                            if (temps.get(a).getType() == 0)
                                o.remove(a);
                        }
                        initBanner(o);
                    }
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                // TODO: 2017/7/12 0012 操作数据
                if (images == null || images.size() == 0) return;
                isCancelled = false;

                ThreadTask.getInstance().executorDBThread(new Runnable() {
                    @Override
                    public void run() {
                        for (ImageItem imageItem : images) {
                            // TODO: 2017/8/9 0009 压缩图片尺寸
                            imageItem.path = com.gohoc.xiupuling.utils.ImageUtils.compress(CreateReqUploadData_3_Activity.this, imageItem.path);

                            UploadManage.INSTANS.uploadImageCount++;
                            if (addPicAdater.getItemCount() == addPicAdater.getMaxCount()) {
                                //最后一张
                                String uploadKey = Utils.getUploadKey(UpLoadConstant.REQUIREMENT, imageItem.path);
                                picList.get(picList.size() - 1).setType(1).setStatus(0).setUpdatefileName(uploadKey).setLocUrl(imageItem.path);
                                uploadFile(picList.get(picList.size() - 1), picList.size() - 1);
                            } else {
                                PicBean picBean = new PicBean();
                                picBean.setLocUrl(imageItem.path);
                                picBean.setType(1);
                                picBean.setPic(true);
                                String uploadKey = Utils.getUploadKey(UpLoadConstant.REQUIREMENT, imageItem.path);
                                picBean.setUpdatefileName(uploadKey);
                                picList.add(picList.size() - 1, picBean);
                                uploadFile(picBean, picList.size() - 2);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updatePicshow();
                                    addPicAdater.notifyDataSetChanged();
                                }
                            });
                            Logger.d(imageItem.path);

                        }
                    }
                }, ThreadTask.ThreadPeriod.PERIOD_HIGHT);

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (resultCode == VideoPicker.RESULT_VIDEO_ITEMS) {
                if (data != null && requestCode == 300) {
                    ArrayList<VideoItem> videos = (ArrayList<VideoItem>) data.getSerializableExtra(VideoPicker.EXTRA_RESULT_VIDEO_ITEMS);
                    if (videos == null) return;
                    final VideoItem videoItem = videos.get(0);
                    NormalDialog.create(getFragmentManager())
                            .setNormalDialogListenner(new NormalDialog.NormalDialogListenner() {
                                @Override
                                public void onClick(NormalDialog.NormalType normalType) {
                                    if (normalType == NormalDialog.NormalType.OK) {
                                        isCancelled = false;
                                        UploadManage.INSTANS.uploadImageCount++;
                                        if (addPicAdater.getItemCount() == addPicAdater.getMaxCount()) {
                                            //最后一张
                                            String uploadKey = Utils.getUploadKey(UpLoadConstant.REQUIREMENT, videoItem.path);
                                            picList.get(picList.size() - 1).setType(1).setStatus(0).setUpdatefileName(uploadKey).setLocUrl(videoItem.path).setVideo(true);
                                            uploadFile(picList.get(picList.size() - 1), picList.size() - 1);

                                            initPlayer(mVideoView, picList.get(picList.size() - 1));
                                        } else {
                                            PicBean picBean = new PicBean();
                                            picBean.setLocUrl(videoItem.path);
                                            picBean.setType(1);
                                            picBean.setVideo(true);
                                            String uploadKey = Utils.getUploadKey(UpLoadConstant.REQUIREMENT, videoItem.path);
                                            picBean.setUpdatefileName(uploadKey);
                                            picList.add(picList.size() - 1, picBean);
                                            uploadFile(picBean, picList.size() - 2);
                                            initPlayer(mVideoView, picBean);
                                        }

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

                } else {
                    Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void chooseVideo() {
        videoPicker.setMultiMode(false);
        videoPicker.setShowCamera(false);
        Intent i1 = new Intent(this, VideoGridActivity.class);
        startActivityForResult(i1, 300);
    }

    private void updatePicshow() {
        showPicList.clear();
        showPicList.addAll(picList);

        if (picList.size() > 1 && picList.get(picList.size() - 1).getType() == 0)
            showPicList.remove(picList.size() - 1);


        if (og == 1) {
            initBanner(showPicList);
        } else {
            hBanner = hBanner;
            initBanner(showPicList);
        }
    }

    private void uploadFile(final PicBean picBean, final int potions) {
        RxRetrofitClient.getInstance(CreateReqUploadData_3_Activity.this).getUploadToken(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CreateReqUploadData_3_Activity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    UploadManager uploadManager = new UploadManager();
                    Logger.e(picBean.getLocUrl());
                    Logger.e(vCodeBenan.getMessage());

                    if (picBean.isVideo()) {
                        upW = 0;
                    } else {
                        upW = 1;
                    }
                    UploadManage.INSTANS.upload(picBean, potions, vCodeBenan, CreateReqUploadData_3_Activity.this);
                }
            }
        });
    }

    private SourceSubmitDialog mSourceSubmitDialog;//图片、拍摄、音频
    public void showDialog() {
        if(mSourceSubmitDialog==null){
            mSourceSubmitDialog =new SourceSubmitDialog(this, new SourceSubmitCallback() {
                @Override
                public void tupian() {
                    showMenu(1);
                    mSourceSubmitDialog.dismiss();
                }

                @Override
                public void shiping() {
                    LogUtil.d("shiping");
                    chooseVideo();
                    mSourceSubmitDialog.dismiss();
                }

                @Override
                public void yinping() {

                }

                @Override
                public void shuiying() {
                    showMenu(1);
                    mSourceSubmitDialog.dismiss();
                }

                @Override
                public void paishe() {
                    showMenu(0);
                    mSourceSubmitDialog.dismiss();
                }
            },dialogType);
        }

        if((picList!=null&&picList.size()==0)||picList==null){
            LogUtil.d("首次进来：1111111111");
            mSourceSubmitDialog.setMyClick(false,true,false,true,false);
        }else {
            LogUtil.d("不是首次进来：22222222222");
            mSourceSubmitDialog.setMyClick(false,!CustomUtils.getVideo(picList),false,!CustomUtils.getPic(picList),false);
        }

        mSourceSubmitDialog.show();

        /*AlertView alertView = new AlertView(null, null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {
                showMenu(position);
            }
        });
        alertView.setCancelable(true);
        alertView.show();*/
    }

    private void showMenu(int type) {
        imagePicker.setCrop(true);
        if (og == 0) {
            imagePicker.setFocusWidth(DeviceUtils.getScreenSize(this)[0]);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(DeviceUtils.getScreenSize(this)[0]*9/16);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(1920);//保存文件的宽度。单位像素
            imagePicker.setOutPutY(1080);//保存文件的高度。单位像素
        } else {
            imagePicker.setFocusWidth(DeviceUtils.getScreenSize(this)[0]*3/5);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(DeviceUtils.getScreenSize(this)[0]*16/15);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(1080);//保存文件的宽度。单位像素
            imagePicker.setOutPutY(1920);//保存文件的高度。单位像素
        }


        int limit = 0;
        if (mType == 2) {
            imagePicker.setMultiMode(true);
            if (picList != null) {
                int count = 0;
                for (PicBean b : picList) {
                    if (b.getLocUrl() != null && b.getLocUrl().length() > 0) {
                        count++;
                    }
                }
                limit = 5 - count;
            }

        } else {
            imagePicker.setMultiMode(false);
        }

        imagePicker.setSelectLimit(limit);//选中数量限制
        if (type == 0) {

            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, IMAGE_PICKER);
        } else if (type == 1) {
            Intent intent = new Intent(CreateReqUploadData_3_Activity.this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        }
    }

    @OnClick({R.id.action_bar_left_title, R.id.action_bar_title, R.id.title_message_tv, R.id.toolbar_left_title, R.id.toolbar_right, R.id.del_pic_iv, R.id.minus_iv, R.id.add_iv, R.id.play_iv, R.id.paus_iv, R.id.stop_iv, R.id.width_tv, R.id.height_tv, R.id.x_tv, R.id.y_tv, R.id.logout_button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_bar_title:
                if (mActionBar.isShown()) {
                    mActionBar.setVisibility(View.GONE);
                    setStatusColor(R.color.black);
                }
                break;
            case R.id.title_message_tv:
                if (!mActionBar.isShown()) {
                    ShowCell();
                    mActionBar.setVisibility(View.VISIBLE);
                    setStatusColor(R.color.colorPrimary);
                }
                break;
            case R.id.action_bar_left_title:
                if (picList != null && picList.size() > 0) {
                    ArrayList<PicBean> objects = new ArrayList<>();
                    for (PicBean p : picList) {
                        objects.add(p);
                    }
                    setResult(RESULT_OK, new Intent().putExtra("picList", objects).putExtra("type", mType));
                }
                onBackPressed();
                break;

            case R.id.toolbar_left_title:
                if (picList != null && picList.size() > 0) {
                    ArrayList<PicBean> objects = new ArrayList<>();
                    for (PicBean p : picList) {
                        objects.add(p);
                    }
                    setResult(RESULT_OK, new Intent().putExtra("picList", objects).putExtra("type", mType));
                }
                onBackPressed();
                break;
            case R.id.del_pic_iv:
                if (picList != null && picList.size() > 0) {
                    PicBean picBean = picList.get(0);
                    if (picBean.isVideo()) {
                        String url = picBean.getUpdatefileName();
                        UploadManage.INSTANS.setCannelUpload(url);
                        canelUpdate();
                    }
                }

                break;
            case R.id.minus_iv:
                break;
            case R.id.add_iv:
                break;
            case R.id.play_iv:
                mVideoView.start();
                break;
            case R.id.paus_iv:
                mVideoView.pause();
                break;
            case R.id.stop_iv:
                //设置视频路径
                mVideoView.setVideoURI(Uri.parse(picList.get(0).getLocUrl()));
                //开始播放视频
                mVideoView.start();
                break;
            case R.id.width_tv:
                break;
            case R.id.height_tv:
                break;
            case R.id.x_tv:
                break;
            case R.id.y_tv:
                break;
            case R.id.logout_button_ll:
                if (picList != null && picList.size() > 0) {
                    ArrayList<PicBean> objects = new ArrayList<>();
                    for (PicBean p : picList) {
                        if (p.getLocUrl() != null || p.getUrl() != null)
                            objects.add(p);
                    }
                    setResult(RESULT_OK, new Intent().putExtra("picList", objects).putExtra("type", mType));
                }
                onBackPressed();
                break;
        }
    }


    private void initPlayer(final MVidioView samplevideo, PicBean picBean) {
        //设置视频路径
        mVideoView.setVideoURI(Uri.parse(picBean.getLocUrl()));
        //开始播放视频
        mVideoView.start();
        mVideoView.setVisibility(View.VISIBLE);
    }


    private void canelUpdate() {
        //progressBar.setProgress(0);
        mVideoView.stopPlayback();
        //delPicIv.setEnabled(true);
        isUploadSueed = false;
        //delPicIv.setImageResource(R.mipmap.btn_guanbi);
        mVideoView.setVisibility(View.GONE);
        if (picList.size() == 1) {
            picList.get(picList.size() - 1).setType(0);
        } else {
            picList.remove(picList.get(picList.size() - 1));
            picList.add(picList.size(), new PicBean());
        }
        addPicAdater.notifyDataSetChanged();
        upW = 1;
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


    @Override
    public void complete(String key, ResponseInfo info, JSONObject res, PicBean picBean) {
        if (UploadManage.INSTANS.uploadImageCount > 0)
            UploadManage.INSTANS.uploadImageCount--;
        if (UploadManage.INSTANS.uploadImageCount == 0) {
            EventBus.getDefault().post(new EventFactory.pleaseUploadData());
            if (picList != null && picList.size() > 0) {
                ArrayList<PicBean> objects = new ArrayList<>();
                for (PicBean p : picList) {
                    if (p.getLocUrl() != null || p.getUrl() != null)
                        p.setProgress(100);
                    objects.add(p);
                }
                addPicAdater.notifyDataSetChanged();
            }
        }
        //res包含hash、key等信息，具体字段取决于上传策略的设置
        if (info.isOK()) {
            Logger.d("qiniu:Upload Success");
            if (picBean.isVideo()) {
                if (picList != null && picList.size() > 0) {
                    PicBean picBean1 = picList.get(0);
                    picBean1.setProgress(100);
                }
                //delPicIv.setEnabled(false);
                isUploadSueed = true;
                //delPicIv.setImageResource(R.mipmap.button_canshujindutiao_gou);
            }
        } else {
            //  Logger.d(");
            Logger.e("qiniu Upload Fail" + res);
            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
        }
        Logger.d("qiniu: " + key + ",\r\n " + info + ",\r\n " + res);

    }

    @Override
    public void progress(String key, double percent, PicBean picBean, int potions) {
        Logger.e(percent + "");
        LogUtil.d("进度11111111："+percent);
        if (picBean.isVideo()||picBean.isPic()) {
            LogUtil.d("进度22222222："+percent);
            if (potions > picList.size() - 1) return;
            picList.get(potions).setProgress((int) (percent * 100));
            addPicAdater.notifyItemChanged(potions);
        } else {
            //progressBar.setProgress((int) (percent * 100));
        }
    }

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
            if (mType == 3) {
                //progressBar.setProgress(0);
            }
        }

        super.onBackPressed();

    }


}
