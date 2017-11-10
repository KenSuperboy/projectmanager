package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.dialog.Ok_Cancel_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.BaseConstant.IMAGE_PICKER;

/*
* 通过扫描二维码添加卡券
* */
public class AddCardActivity extends BasicActivity implements QRCodeView.Delegate {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.zxingview)
    QRCodeView zxingview;
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private Ok_Cancel_Dialog mOk_cancel_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_layout);
        ButterKnife.bind(this);

        toolbarRight.setText("相册");
        toolbarTitle.setText("添加卡券");
        zxingview.setDelegate(this);
        setStatusColor(R.color.colorPrimary);
        ImagePicker.getInstance().setMultiMode(false);
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                AddCardActivity.this.finish();
                break;
            case R.id.toolbar_right:
                showMenu(1);
                break;
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Logger.d("result:" + result);
        // Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        LogUtil.d("扫描成功："+result);
        querycoupon(result);//添加卡券
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Logger.e("打开相机出错");
    }

    @Override
    protected void onStart() {
        super.onStart();
        zxingview.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        zxingview.showScanRect();
        zxingview.startSpot();

    }

    @Override
    protected void onStop() {
        super.onStop();
        zxingview.stopCamera();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zxingview.onDestroy();

    }

    private void showMenu(int type) {
        if (type == 0) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, IMAGE_PICKER);
        } else if (type == 1) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) return;

                String result = QRCodeDecoder.syncDecodeQRCode(images.get(0).path);
                if (result != null && !result.isEmpty()) {
                    querycoupon(result);
                } else {
                    Utils.toast(AddCardActivity.this, "未发现二维码");
                }
            }
        }
    }

    //先扫描
    private void querycoupon(final String invite_code)
    {
        RxRetrofitClient.getInstance(this).querycoupon(invite_code, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddCardActivity.this, "请检查网络是否正常");
                vibrate();
                zxingview.startSpot();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                //Utils.toast(AddCardActivity.this, vCodeBenan.getMessage());
                if(vCodeBenan.getCode()==1)
                {
                    vibrate();
                    bindnewpwdcoupon(invite_code,vCodeBenan.getData());
                }else {
                    Utils.toast(AddCardActivity.this, vCodeBenan.getMessage());
                    vibrate();
                    zxingview.startSpot();
                }
            }
        });

    }

    private void bindnewpwdcoupon(final String invite_code,final String token)
    {
        LogUtil.d("扫描add_pwd");
        mOk_cancel_dialog=new Ok_Cancel_Dialog(mContext,"是否添加此二维码包含的卡券，添加后该二维码将失效！");
        mOk_cancel_dialog.setTopTitlt("扫描成功");
        mOk_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                addAction(invite_code,token);
            }

            @Override
            public void cancelBack() {
                //vibrate();
                zxingview.startSpot();
            }
        });
        mOk_cancel_dialog.show();

    }

    private void addAction(String invite_code,String token){
        RxRetrofitClient.getInstance(this).querybindcoupon(invite_code,token, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddCardActivity.this, "请检查网络是否正常");
                vibrate();
                zxingview.startSpot();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(AddCardActivity.this, vCodeBenan.getMessage());
                if(vCodeBenan.getCode()==1)
                {
                    vibrate();
                    zxingview.startSpot();
                    setResult(RESULT_OK);
                    finish();
                }else {
                    vibrate();
                    zxingview.startSpot();
                }
            }
        });
    }
}
