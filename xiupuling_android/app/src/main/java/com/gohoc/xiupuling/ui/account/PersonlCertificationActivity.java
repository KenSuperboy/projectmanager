package com.gohoc.xiupuling.ui.account;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PersonalVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.VCodeBenan;

import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.DeviceUtils;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
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
import rx.Observer;
import top.zibin.luban.Luban;

import static com.gohoc.xiupuling.constant.Constant.BaseConstant.IMAGE_PICKER;

public class PersonlCertificationActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.real_name_et)
    EditText realNameEt;
    @BindView(R.id.idcertfi_et)
    EditText idcertfiEt;
    @BindView(R.id.idcard_pic1_iv)
    ImageView idcardPic1Iv;
    @BindView(R.id.add_pic_lv)
    LinearLayout addPicLv;
    @BindView(R.id.add_pic2)
    LinearLayout addPic2;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    @BindView(R.id.idcard_pic2_iv)
    ImageView idcardPic2Iv;
    private int type = 1;
    private String idcardPic1Url1 = "";
    private String idcardPic1Url2 = "";
    private PersonalVerifyapplyInfoBean personalVerifyapplyInfo;
    private Window window;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personl_certification);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        showPop();
        toolbarTitle.setText("个人认证");

        realNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        idcertfiEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void uploadFile(final String fileUrl) {
        showProgressDialog("正在上传", false);
        RxRetrofitClient.getInstance(PersonlCertificationActivity.this).getUploadToken(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
                Utils.toast(PersonlCertificationActivity.this, "请检查网络是否正常");
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
                        file = Luban.with(PersonlCertificationActivity.this).load(new File(fileUrl)).get();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    uploadManager.put(file.getPath(), urls, vCodeBenan.getMessage(),
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    closeProgressDialog();
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    if (info.isOK()) {
                                        Logger.d("qiniu:Upload Success");

                                        if (type == 1)
                                            idcardPic1Url1 = urls;
                                        else
                                            idcardPic1Url2 = urls;
                                        check();
                                        Logger.d(urls);
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

    @OnClick({R.id.toolbar_left_title, R.id.add_pic_lv, R.id.add_pic2, R.id.sumbit_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                PersonlCertificationActivity.this.finish();
                break;
            case R.id.add_pic_lv:
                showDialogMy(1);
                break;
            case R.id.add_pic2:
                showDialogMy(2);
                break;
            case R.id.sumbit_button_ll:
                personalVerifyapply();
                break;
        }
    }

    public void showDialogMy(int type) {
        this.type = type;
        new AlertView(null, null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {
                showMenu(position);
            }
        }).show();
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
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        if (type == 0) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent,  IMAGE_PICKER);
        } else if (type == 1) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        }
    }

    public void personalVerifyapply() {
        RxRetrofitClient.getInstance(PersonlCertificationActivity.this).personalVerifyapply(idcertfiEt.getText() + "", realNameEt.getText() + "", idcardPic1Url1 + "", idcardPic1Url2 + "", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PersonlCertificationActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(PersonlCertificationActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    Credential.getInstance().updateUserInfo(PersonlCertificationActivity.this);
                    EventBus.getDefault().post(new Event.UserEvent());
                    startActivity(new Intent(PersonlCertificationActivity.this, PersonlCertificationInfoActivity.class));
                    finish();
                }
            }
        });
    }

    private void check() {
        if (TextUtils.isEmpty(realNameEt.getText()) || TextUtils.isEmpty(idcertfiEt.getText()) || TextUtils.isEmpty(idcardPic1Url1)
                || TextUtils.isEmpty(idcardPic1Url2)) {
            sumbitButtonLl.setBackgroundColor(getResources().getColor(R.color.sms_ds));
            sumbitButtonLl.setClickable(false);
        } else {
            sumbitButtonLl.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            sumbitButtonLl.setClickable(true);
        }
    }


    private void showPop() {
        if (Build.VERSION.SDK_INT >= 14) {
            dialog = new AlertDialog.Builder(this,
                    R.style.dialogTips).create();
        } else {
            dialog = new AlertDialog.Builder(this).create();
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        window = dialog.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.pop_person_cer);

        window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) return;
                for (ImageItem imageItem : images) {
                    uploadFile(imageItem.path);
                    ImageView iv = idcardPic1Iv;
                    if (type == 2)
                        iv = idcardPic2Iv;
                    Glide.with(PersonlCertificationActivity.this)
                            .load(imageItem.path)
                            .centerCrop()
                            .into(iv);
                    Logger.d(imageItem.path);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(idcertfiEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
