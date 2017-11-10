package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.terminal.AddTerminal2Activity;
import com.gohoc.xiupuling.ui.terminal.AddTerminalActivity;
import com.gohoc.xiupuling.utils.Utils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import rx.Observer;

import static android.content.Context.VIBRATOR_SERVICE;


public class ScanFragment extends BaseFragment implements QRCodeView.Delegate {


    @BindView(R.id.zxingview)
    ZXingView zxingview;
    @BindView(R.id.scan_tips_tv)
    TextView scanTipsTv;
    @BindView(R.id.terminal_scanl_tips_tv)
    TextView terminalScanlTipsTv;
    private View viewContainer;


    public ScanFragment() {

    }

    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_scan, container, false);
        ButterKnife.bind(this, viewContainer);

        zxingview.setDelegate(this);

        return viewContainer;

    }


    @Override
    public void onStart() {
        super.onStart();
        zxingview.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        zxingview.showScanRect();
        zxingview.startSpot();

    }

    @Override
    public void onStop() {
        super.onStop();
        zxingview.stopCamera();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        zxingview.onDestroy();

    }


    @Override
    public void onScanQRCodeSuccess(String result) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    private void accesstokencheck(final String result) {
        RxRetrofitClient.getInstance(getActivity()).accesstokencheck(result, "1000", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(getActivity(), "请检查网络是否正常");
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

                if (vCodeBenan.getCode() == 1) {
                    startActivity(new Intent(getActivity(), AddTerminal2Activity.class).putExtra("token", result));
                } else
                    Utils.toast(getActivity(), vCodeBenan.getMessage());
                vibrate();
                zxingview.startSpot();
            }
        });
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
