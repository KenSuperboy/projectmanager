package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class InputTerminalNumberActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.shop_mobile_et)
    ClearEditText shopMobileEt;
    @BindView(R.id.toolbar_right)
    TextView toolbar_right;

    private String terminalId;
    private ShopDetailsBean shopDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terminal_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);

        shopMobileEt.setFocusable(true);
        toolbarTitle.setText("终端编号");
        Utils.showSoftInputFromWindow(this, shopMobileEt);
        shopMobileEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        shopMobileEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            if(TextUtils.isEmpty(shopMobileEt.getText().toString())){
                                Toast.makeText(InputTerminalNumberActivity.this,"请输入终端编号",Toast.LENGTH_SHORT).show();
                            }else {
                                if (null == terminalId)
                                    accesstokencheck(shopMobileEt.getText().toString());//添加终端
                                else
                                    fixtermianl(shopMobileEt.getText().toString(), terminalId);//恢复终端
                                //accesstokencheck(shopMobileEt.getText().toString());
                            }
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });

        initData();
    }

    private void initData()
    {
        terminalId = getIntent().getStringExtra("terminalId");
        if (null == terminalId)
            toolbarTitle.setText("添加终端");
        else
            toolbarTitle.setText("恢复终端");
        try {
            shopDetailsBeans = (ShopDetailsBean) getIntent().getExtras().get("shopDetailsBeans");
        } catch (Exception e) {
        }
    }

    private void goback() {
        InputTerminalNumberActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goback();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(shopMobileEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goback();
                break;
            case R.id.toolbar_right:
                if(TextUtils.isEmpty(shopMobileEt.getText().toString())){
                    Toast.makeText(InputTerminalNumberActivity.this,"请输入终端编号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null == terminalId)
                    accesstokencheck(shopMobileEt.getText().toString());//添加终端
                else
                    fixtermianl(shopMobileEt.getText().toString(), terminalId);//恢复终端

                break;
        }
    }

    //添加終端
    private void accesstokencheck(final String result) {
        RxRetrofitClient.getInstance(InputTerminalNumberActivity.this).accesstokencheck(result, "1000", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(InputTerminalNumberActivity.this, "请检查网络是否正常");
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
                        startActivity(new Intent(InputTerminalNumberActivity.this, AddTerminal2Activity.class).putExtra("token", result).putExtra("shopDetailsBeans", shopDetailsBeans));
                    else
                        Utils.toast(InputTerminalNumberActivity.this, vCodeBenan.getMessage());
                    finish();
                } else
                    Utils.toast(InputTerminalNumberActivity.this, vCodeBenan.getMessage());
            }
        });
    }

    //恢復終端
    private void fixtermianl(String token, String terminalId) {
        //Utils.toast(this,"fixtermianl");
        RxRetrofitClient.getInstance(InputTerminalNumberActivity.this).recovery(token, "1000", terminalId, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(InputTerminalNumberActivity.this, "请检查网络是否正常");
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
                    Utils.toast(InputTerminalNumberActivity.this, "终端恢复成功");
                    finish();
                } else
                    Utils.toast(InputTerminalNumberActivity.this, vCodeBenan.getMessage());
            }
        });

    }
}
