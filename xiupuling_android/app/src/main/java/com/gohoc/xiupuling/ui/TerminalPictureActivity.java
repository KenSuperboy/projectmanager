package com.gohoc.xiupuling.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.TerminalPictureBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.lzy.imagepicker.util.BitmapUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;


public class TerminalPictureActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_or)
    TextView mTvOr;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.relative_content_layout)
    RelativeLayout mRelativeContentLayout;
    @BindView(R.id.tv_full)
    TextView mTvFull;
    @BindView(R.id.relative_full_layout)
    RelativeLayout mRelativeFullLayout;
    @BindView(R.id.tv_load)
    TextView mTvLoad;
    @BindView(R.id.relative_hide_layout)
    RelativeLayout mRelativeHideLayout;
    @BindView(R.id.iv_bg_full)
    ImageView mIvBgFull;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.iv_tip)
    ImageView mIvTip;

    private int or;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_picture_layout);
        ButterKnife.bind(this);
        full(isFulllScreen, mToolbar);
        //setStatusColor(R.color.colorPrimary);
        initView();
        initData();
        fiveCount();
        getTerminalPicture();
    }

    private void initView() {
        mToolbarTitle.setText("播放截图");

        mRelativeHideLayout.setOnTouchListener(new View.OnTouchListener()

                                               {
                                                   @Override
                                                   public boolean onTouch(View v, MotionEvent event) {
                                                       if (v.getId() != R.id.iv_bg && v.getId() != R.id.tv_full && v.getId() != R.id.tv_load) {
                                                           isFulllScreen = !isFulllScreen;
                                                           full(isFulllScreen, mToolbar);
                                                       }
                                                       return false;
                                                   }
                                               }

        );
    }

    private void getTerminalPicture() {


        RxRetrofitClient.getInstance(TerminalPictureActivity.this).getTerminalPiture(id, new Observer<TerminalPictureBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(TerminalPictureActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TerminalPictureBean terminalPictureBean) {
                LogUtil.d("记载成功");//thumb-requirement15076224033396c6d7ee276e444064bac9585717a81d2.jpg
                //setShuping("http://7xrbkc.com1.z0.glb.clouddn.com/thumb-requirement1507634441795eaaa94459640a1f06a2830bf287041a2.jpg");
                //mIvBg.setImageBitmap(BitmapUtil.rotateBitmapByDegree("http://7xrbkc.com1.z0.glb.clouddn.com/thumb-requirement1507634441795eaaa94459640a1f06a2830bf287041a2.jpg",90));
                //Glide.with(mContext).load(NetConstant.BASE_USER_RESOURE + "thumb-requirement1507634441795eaaa94459640a1f06a2830bf287041a2.jpg").fitCenter().into(mIvBg);
                //Glide.with(mContext).load(NetConstant.BASE_USER_RESOURE + "thumb-requirement1507634441795eaaa94459640a1f06a2830bf287041a2.jpg").fitCenter().into(mIvBgFull);

                //if(true){
                if(terminalPictureBean.code==1&& !TextUtils.isEmpty(terminalPictureBean.data)){
                    mTvLoad.setVisibility(View.GONE);
                    if(terminalPictureBean.data.contains("http")){
                        if(or==1){
                            setShuping(terminalPictureBean.data);
                        }else {
                            Glide.with(mContext).load(terminalPictureBean.data).fitCenter().into(mIvBg);
                            Glide.with(mContext).load(terminalPictureBean.data).fitCenter().into(mIvBgFull);
                        }
                    }else {
                        if(or==1){
                            setShuping(NetConstant.BASE_USER_RESOURE + terminalPictureBean.data);
                        }else {
                            Glide.with(mContext).load(NetConstant.BASE_USER_RESOURE + terminalPictureBean.data).fitCenter().into(mIvBg);
                            Glide.with(mContext).load(NetConstant.BASE_USER_RESOURE + terminalPictureBean.data).fitCenter().into(mIvBgFull);
                        }
                        }
                }else {
                    mTvLoad.setVisibility(View.VISIBLE);
                }

                }
        });
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(mBitmap!=null){
                        Bitmap mybitmap=BitmapUtil.rotateBitmapByDegree(mBitmap,90);
                        mIvBg.setImageBitmap(mybitmap);
                        mIvBgFull.setImageBitmap(mybitmap);
                    }
                    break;
            }
        }
    };

    //如果是竖屏的话
    Bitmap mBitmap;
    private void setShuping(final String urlString)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap=getData(urlString);
                LogUtil.d("获取到的bitmap："+mBitmap);
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    public Bitmap getData(String urlString){
        Bitmap bitmap = null;
        ByteArrayOutputStream bos = null;
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            bos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while((len = is.read(data))!= -1){
                bos.write(data, 0, len);
            }
            byte[] data1 = bos.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, data1.length);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    Timer timer;
    private void fiveCount()
    {
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LogUtil.d("五秒计时");
                getTerminalPicture();
            }
        },0,5000);
    }

    @Override
    protected void onDestroy() {
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        super.onDestroy();
    }

    private void initData() {
        Intent data = getIntent();
        // TODO: 2017/6/8 0008 传入类型
        if (data != null) {
            or = data.getIntExtra("or", 0);
            id = data.getStringExtra("id");
        }
        //处理横竖屏
        //计算并设置宽度高度
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int w = wm.getDefaultDisplay().getWidth();
        if (or != 1) {//横屏
            float scale = 9f / 16f;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, (int) (w * scale));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mRelativeContentLayout.setLayoutParams(layoutParams);
            mRelativeContentLayout.requestLayout();
            mTvOr.setText("终端为横屏放置方式");
        } else {//竖屏  顺时针旋转90度
            float scale = 16f / 9f;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w / 2, (int) ((w / 2) * scale));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mRelativeContentLayout.setLayoutParams(layoutParams);
            mRelativeContentLayout.requestLayout();
            mTvOr.setText("终端为竖屏放置方式");
        }
    }

    private boolean isFulllScreen = true;

    @OnClick({R.id.toolbar_left_title, R.id.tv_full})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.tv_full:
                isFulllScreen = true;
                full(isFulllScreen, mToolbar);
                mIvBg.setVisibility(View.GONE);
                mTvLoad.setVisibility(View.GONE);
                mTvTip.setVisibility(View.GONE);
                mTvOr.setVisibility(View.GONE);
                mTvFull.setVisibility(View.GONE);
                mIvTip.setVisibility(View.GONE);
                mIvBgFull.setVisibility(View.VISIBLE);
                //

                /*mToolbar.setVisibility(View.GONE);
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                int w = wm.getDefaultDisplay().getWidth();
                int h = wm.getDefaultDisplay().getHeight();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mRelativeContentLayout.setLayoutParams(layoutParams);
                mRelativeContentLayout.requestLayout();*/
                break;
        }
    }
}
