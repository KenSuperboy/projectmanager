package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.KvBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.DeviceUtils;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.greenrobot.eventbus.EventBus;
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

import static com.gohoc.xiupuling.constant.Constant.BaseConstant.IMAGE_PICKER;

public class AddGroupActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.group_type_tv)
    TextView groupTypeTv;
    @BindView(R.id.group_type_ll)
    LinearLayout groupTypeLl;
    @BindView(R.id.name_et)
    ClearEditText nameEt;
    @BindView(R.id.group_flage_et)
    ClearEditText groupFlageEt;
    @BindView(R.id.group_pic_iv)
    CircleImageView groupPicIv;
    @BindView(R.id.group_cover_ll)
    LinearLayout groupCoverLl;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    private static int TYPE_REQUEST_RESULT = 1000;
    private final static int REQUEST_RESULT = 1001;
    private KvBean groupType;
    private String coverUrls;
    private String coverUrlsNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("新建群组");
        toolbarLeftTitle.setText("取消");
        if (Credential.getInstance().getCurUser(this).getData().getShared_platform() == 1) {
            groupType = new KvBean("0", "共享群");
        } else {
            groupType = new KvBean("1", "私有群");
        }
        groupTypeTv.setText(groupType.getV() + "");

    }

    @OnClick({R.id.toolbar_left_title, R.id.group_type_ll, R.id.group_cover_ll, R.id.sumbit_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goback();
                break;
            case R.id.group_type_ll:
                startActivityForResult(new Intent(AddGroupActivity.this, AddGroupTypeActivity.class).putExtra("groupType", groupType), TYPE_REQUEST_RESULT);
                break;
            case R.id.group_cover_ll:
                showDialog();
                break;
            case R.id.sumbit_button_ll:
                if(canload){
                    canload=false;
                    showProgressDialog("正在提交....");
                    post();
                }
                break;
        }
    }

    private boolean canload=true;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode ==IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) return;
                for (ImageItem imageItem : images) {
                    coverUrls = imageItem.path;
                    Glide.with(AddGroupActivity.this)
                            .load(imageItem.path)
                            //  .placeholder(R.mipmap.icon_port_home)
                            // .error(R.mipmap.icon_port_home)
                            .into(groupPicIv);
                    Logger.d(imageItem.path);
                    uploadFile();
                }
            }
        } else if (resultCode == RESULT_OK) {
            if (requestCode == TYPE_REQUEST_RESULT) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        KvBean groupTypes = (KvBean) d.get("groupType");
                        if (groupTypes != null) {
                            groupType = groupTypes;
                            groupTypeTv.setText(groupType.getV() + "");
                        }
                    }

                }
            } else if (requestCode == REQUEST_RESULT) {
                finish();
            }
        }
    }


    private void post() {
        LogUtil.d("===:"+groupType.getK()+"====:"+groupType.getV());
        RxRetrofitClient.getInstance(this).saveUnion(nameEt.getText() + "", groupType.getK(),groupType.getV()+ ","+groupFlageEt.getText(), coverUrlsNet + "", new Observer<GroupDetailsBean>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
                canload=true;
            }

            @Override
            public void onError(Throwable e) {
                canload=true;
                closeProgressDialog();
                Utils.toast(AddGroupActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(GroupDetailsBean groupDetailsBean) {
                canload=true;
                closeProgressDialog();
                if (groupDetailsBean.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                    startActivityForResult(new Intent(AddGroupActivity.this, AddGroupResultActivity.class).putExtra("groupDetailsBeans", groupDetailsBean), REQUEST_RESULT);
                    finish();
                } else
                    Utils.toast(AddGroupActivity.this, groupDetailsBean.getMessage() + "");

            }
        });
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
            alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
                @Override
                public void onClick() {

                }
            }));
            alertController.setCancelable(true);
            alertController.show();

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
            startActivityForResult(intent,  IMAGE_PICKER);
        } else if (type == 1) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent,  IMAGE_PICKER);
        }
    }

    private void uploadFile() {
        RxRetrofitClient.getInstance(AddGroupActivity.this).getUploadToken(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddGroupActivity.this, "请检查网络是否正常");
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
                    Logger.e(coverUrls);
                    Logger.e(vCodeBenan.getMessage());
                    final String urls = Utils.getUploadKey(UpLoadConstant.UNION, coverUrls);
                    File file = null;
                    try {
                        file = Luban.with(AddGroupActivity.this).load(new File(coverUrls)).get();
                        coverUrls = file.getPath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    uploadManager.put(coverUrls, urls, vCodeBenan.getMessage(),
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    if (info.isOK()) {
                                        Logger.d("qiniu:Upload Success");
                                        coverUrlsNet = urls;

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

    private void goback() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        EventBus.getDefault().post(new Event.MainIndex(1));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(groupFlageEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goback();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
