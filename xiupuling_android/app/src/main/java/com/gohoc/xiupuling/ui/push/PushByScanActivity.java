package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;

import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.TerminalBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.group.JoinGroupDtActivity;
import com.gohoc.xiupuling.ui.group.JoinGroupScanActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.BaseConstant.IMAGE_PICKER;

public class PushByScanActivity extends BasicActivity implements QRCodeView.Delegate {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.zxingview)
    QRCodeView zxingview;
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private String terminalId;
    private PushNormlBean pushNormlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_by_scan);
        ButterKnife.bind(this);
        toolbarTitle.setText("扫描投放");
        toolbarRight.setText("相册");
        zxingview.setDelegate(this);
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        setStatusColor(R.color.colorPrimary);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                PushByScanActivity.this.finish();
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
        // Utils.toast(this, result);
        getTerminalDetail(result);
    }

    private void getTerminalDetail(String terminalId) {
        RxRetrofitClient.getInstance(PushByScanActivity.this).getTerminalDetail(terminalId, new Observer<TerminalBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushByScanActivity.this, "请检查网络是否正常");
                vibrate();
                zxingview.startSpot();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(TerminalBean terminalBean) {

                if (terminalBean.getCode() == 1) {
                    if (terminalBean.getData() == null) {
                        Utils.toast(PushByScanActivity.this, "未查询到终端信息");
                        return;
                    }

                    if (Credential.getInstance().getCurUser(PushByScanActivity.this).getData().getUser_id().equals(terminalBean.getData().getUser_id()))
                        pushNormlBean.setFree(true);
                    else {
                        if (Credential.getInstance().getCurUser(PushByScanActivity.this).getData().getShared_platform() == 0) {
                            Utils.toast(PushByScanActivity.this, "只能投放到自己的终端");
                            return;
                        }
                        pushNormlBean.setFree(false);
                    }

                    pushNormlBean.setShopName(terminalBean.getData().getShop().getShop_name());
                    pushNormlBean.setTerminanlId(terminalBean.getData().getTerminal_id());
                    pushNormlBean.setTermianlNo(terminalBean.getData().getTerminal_no());
                    pushNormlBean.setShopStar(terminalBean.getData().getShop().getShop_star_level() );
                    startActivity(new Intent(PushByScanActivity.this, PushByDinActivity.class).putExtra("pushNormlBean", pushNormlBean));
                } else
                    Utils.toast(PushByScanActivity.this, terminalBean.getMessage());
                vibrate();
                zxingview.startSpot();
            }
        });

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
            startActivityForResult(intent,IMAGE_PICKER);
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
                    // Utils.toast(PushByScanActivity.this, result);
                    getTerminalDetail(result);
                } else {
                    Utils.toast(PushByScanActivity.this, "未发现二维码");
                }
            }
        }
    }
}
