package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.DownLoadImageService;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.DeviceUtils;
import com.gohoc.xiupuling.utils.ImageDownLoadCallBack;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;
import top.zibin.luban.Luban;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.gohoc.xiupuling.constant.Constant.BaseConstant.IMAGE_PICKER;
import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;
/*
* 修改联动包的封面路径
* */
public class LiandongbaoEditBgActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    ImageView toolbarRight;
    @BindView(R.id.portrait_iv)
    PhotoView portraitIv;
    private PhotoViewAttacher mAttacher;
    private String fileUrl;
    //private UserBaseBean userBaseBean;
    private boolean isSave = true;
    private String url,type;//type:0联动包   1：组合包
    private int src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit_portrait);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(LiandongbaoEditBgActivity.this);

        toolbarTitle.setText("更换封面");
        url=getIntent().getStringExtra("url");
        type=getIntent().getStringExtra("type");
        if(type.equals("0")){
            src=R.mipmap.img_liandong_fm;
        }else if(type.equals("1")){
            src=R.mipmap.icon_zuhebao;
        }
        initView();
    }

    private void initView()
    {
        Glide.with(this)
                .load(BASE_USER_RESOURE + url)
                .placeholder(src)
                .error(src)
                .listener(new RequestListener() {

                    @Override
                    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                        isSave = false;
                        showDialog();
                        // Utils.toast(AccountEditPortraitActivity.this, "加载头像失败，请检查网络后重试");
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //在这里添加一些图片加载完成的操作
                        mAttacher = new PhotoViewAttacher(portraitIv);
                        return false;
                    }
                }).into(portraitIv);
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                LiandongbaoEditBgActivity.this.finish();
                break;
            case R.id.toolbar_right:
                showDialog();
                break;
        }
    }


    public void showDialog() {
        BGAAlertController alertController = new BGAAlertController(this, "", "", BGAAlertController.AlertControllerStyle.ActionSheet);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
        alertController.addAction(new BGAAlertAction("拍照", BGAAlertAction.AlertActionStyle.Default, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                showMenu(0);
            }
        }));
        alertController.addAction(new BGAAlertAction("从相册中选择", BGAAlertAction.AlertActionStyle.Default, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                showMenu(1);
            }
        }));
        if (isSave) {
            alertController.addAction(new BGAAlertAction("保存图片", BGAAlertAction.AlertActionStyle.Default, new BGAAlertAction.Delegate() {
                @Override
                public void onClick() {
                    showMenu(2);
                }
            }));
        }
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.setCancelable(true);
        alertController.show();

    }

    //七牛相关获取token
    private void uploadFile() {
        RxRetrofitClient.getInstance(LiandongbaoEditBgActivity.this).getUploadToken(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoEditBgActivity.this, "请检查网络是否正常");
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
                    Logger.e(fileUrl);
                    Logger.e(vCodeBenan.getMessage());
                    final String urls = Utils.getUploadKey(UpLoadConstant.PERSONAL, fileUrl);
                    File file = null;
                    try {
                        file = Luban.with(LiandongbaoEditBgActivity.this).load(new File(fileUrl)).get();
                        fileUrl = file.getPath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    uploadManager.put(fileUrl, urls, vCodeBenan.getMessage(),
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    if (info.isOK()) {
                                        Logger.d("qiniu:Upload Success");
                                        //updateUser(urls);
                                        LogUtil.d("上传成功："+urls);
                                        Intent intent=new Intent();
                                        intent.putExtra("url",urls);
                                        setResult(RESULT_OK,intent);
                                        finish();
                                    } else {
                                        Logger.d("qiniu Upload Fail");
                                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                    }
                                    Logger.d("qiniu: " + key + ",\r\n " + info + ",\r\n " + res);
                                }
                            }, null);
                }
            }
        });
    }

    private void showMenu(int type) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setImageLoader(new com.gohoc.xiupuling.utils.ImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        //  imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(DeviceUtils.getScreenSize(this)[0]);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(DeviceUtils.getScreenSize(this)[0]);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(300);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(300);//保存文件的高度。单位像素
        if (type == 0) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, IMAGE_PICKER);
        } else if (type == 1) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        } else if (type == 2) {
            showProgressDialog("正在保存");
            DownLoadImageService service = new DownLoadImageService(LiandongbaoEditBgActivity.this, BASE_USER_RESOURE + url, new ImageDownLoadCallBack() {
                @Override
                public void onDownLoadSuccess(File file) {
                    closeProgressDialog();
                }

                @Override
                public void onDownLoadSuccess(Bitmap bitmap) {
                    closeProgressDialog();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.toast(LiandongbaoEditBgActivity.this, "保存成功");
                        }
                    });
                }

                @Override
                public void onDownLoadFailed() {
                    closeProgressDialog();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.toast(LiandongbaoEditBgActivity.this, "保存失败");
                        }
                    });
                }
            });
            new Thread(service).start();
        } else {
            // Utils.toast(AccountEditPortraitActivity.this, "未设置头像");
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeProgressDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode ==IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) return;
                for (ImageItem imageItem : images) {
                    fileUrl = imageItem.path;
                    uploadFile();
                    Glide.with(LiandongbaoEditBgActivity.this)
                            .load(fileUrl)
                            .placeholder(src)
                            .error(src)
                            .listener(new RequestListener() {

                                @Override
                                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                                    Utils.toast(LiandongbaoEditBgActivity.this, "加载头像失败，请检查网络后重试");
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    //在这里添加一些图片加载完成的操作
                                    mAttacher = new PhotoViewAttacher(portraitIv);
                                    return false;
                                }
                            })
                            .into(portraitIv);
                    Logger.d(fileUrl);
                }
            }
        }
    }
}
