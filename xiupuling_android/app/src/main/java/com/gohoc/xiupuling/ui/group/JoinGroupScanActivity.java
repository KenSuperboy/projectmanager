package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

import static com.gohoc.xiupuling.constant.Constant.BaseConstant.IMAGE_PICKER;

public class JoinGroupScanActivity extends BasicActivity implements QRCodeView.Delegate {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.zxingview)
    QRCodeView zxingview;
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private String code = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group_scan);
        ButterKnife.bind(this);
        toolbarTitle.setText("加入群组");
        toolbarRight.setText("相册");
        zxingview.setDelegate(this);
        setStatusColor(R.color.colorPrimary);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                JoinGroupScanActivity.this.finish();
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
        code = result.split(",")[1];
        startActivity(new Intent(JoinGroupScanActivity.this, JoinGroupDtActivity.class).putExtra("id", result.split(",")[0]).putExtra("code", code));
        Logger.e("result:" + result);
        // Toast.makeText(JoinGroupScanActivity.this,result+"",Toast.LENGTH_SHORT).show();
        vibrate();
        zxingview.startSpot();

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
        zxingview.stopCamera();
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy();
        super.onDestroy();


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
                    // Toast.makeText(JoinGroupScanActivity.this, result, Toast.LENGTH_SHORT).show();
                    //
                    code = result.split(",")[1];
                    startActivity(new Intent(JoinGroupScanActivity.this, JoinGroupDtActivity.class).putExtra("id", result.split(",")[0]).putExtra("code", code));
                } else {
                    Utils.toast(JoinGroupScanActivity.this, "未发现二维码");
                }
            }
        }
    }

}
