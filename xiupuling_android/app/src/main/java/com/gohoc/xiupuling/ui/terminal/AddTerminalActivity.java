package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
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

public class AddTerminalActivity extends BasicActivity implements QRCodeView.Delegate {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.zxingview)
    QRCodeView zxingview;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private String terminalId;
    private ShopDetailsBean shopDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terminal);
        ButterKnife.bind(this);
        toolbarRight.setText("相册");
        zxingview.setDelegate(this);
        terminalId = getIntent().getStringExtra("terminalId");
        if (null == terminalId)
            toolbarTitle.setText("添加终端");
        else
            toolbarTitle.setText("恢复终端");
        try {
            shopDetailsBeans = (ShopDetailsBean) getIntent().getExtras().get("shopDetailsBeans");
        } catch (Exception e) {
        }
        setStatusColor(R.color.colorPrimary);
        ImagePicker.getInstance().setMultiMode(false);
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right,R.id.tv_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                AddTerminalActivity.this.finish();
                break;
            case R.id.toolbar_right:
                showMenu(1);
                break;
            case R.id.tv_edit:
                Intent intent=new Intent(AddTerminalActivity.this,InputTerminalNumberActivity.class);
                intent.putExtra("terminalId",terminalId);
                intent.putExtra("shopDetailsBeans",shopDetailsBeans);
                startActivity(intent);
                finish();
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
        if (null == terminalId)
            accesstokencheck(result);//添加终端
        else
            fixtermianl(result, terminalId);//恢复终端
    }

    private void fixtermianl(String token, String terminalId) {
        //Utils.toast(this,"fixtermianl");
        RxRetrofitClient.getInstance(AddTerminalActivity.this).recovery(token, "1000", terminalId, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddTerminalActivity.this, "请检查网络是否正常");
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
                //  Utils.toast(AddTerminalActivity.this,vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    Utils.toast(AddTerminalActivity.this, "终端恢复成功");
                    finish();
                } else
                    Utils.toast(AddTerminalActivity.this, vCodeBenan.getMessage());
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
            startActivityForResult(intent, IMAGE_PICKER);
        } else if (type == 1) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        }
    }

    private void accesstokencheck(final String result) {
        RxRetrofitClient.getInstance(AddTerminalActivity.this).accesstokencheck(result, "1000", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddTerminalActivity.this, "请检查网络是否正常");
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
                //Utils.toast(AddTerminalActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    if (TextUtils.isEmpty(terminalId))
                        startActivity(new Intent(AddTerminalActivity.this, AddTerminal2Activity.class).putExtra("token", result).putExtra("shopDetailsBeans", shopDetailsBeans));
                    else
                        Utils.toast(AddTerminalActivity.this, vCodeBenan.getMessage());
                    finish();
                } else
                    Utils.toast(AddTerminalActivity.this, vCodeBenan.getMessage());
                vibrate();
                zxingview.startSpot();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() == 0) return;

                String result = QRCodeDecoder.syncDecodeQRCode(images.get(0).path);
                if (result != null && !result.isEmpty()) {
                    //  Utils.toast(AddTerminalActivity.this, result);
                    if (null == terminalId)
                        accesstokencheck(result);
                    else
                        fixtermianl(result, terminalId);

                } else {
                    Utils.toast(AddTerminalActivity.this, "未发现二维码");
                }
            }
        }
    }
}
