package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.terminal.AddCardActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

//这个界面调用的是手动输入二维码
public class AddCouponsActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_scan_add)
    TextView tv_scan_add;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupons);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("添加卡券");
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            if (!TextUtils.isEmpty(editText.getText()+""))
                                bindnewpwdcoupon(editText.getText()+"");
                            else
                                Utils.toast(AddCouponsActivity.this,"密码指令不能为空");
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.button_ll,R.id.tv_scan_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.button_ll:
                if (!TextUtils.isEmpty(editText.getText()+""))
                    bindnewpwdcoupon(editText.getText()+"");
                else
                    Utils.toast(this,"密码指令不能为空");
                break;
            case R.id.tv_scan_add:
                LogUtil.d("通过添加二维码添加");
                startActivityForResult(new Intent(AddCouponsActivity.this, AddCardActivity.class),100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    LogUtil.d("二维码扫描后的回调");
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }

    private void bindnewpwdcoupon(String add_pwd)
    {
        RxRetrofitClient.getInstance(this).bindnewpwdcoupon(add_pwd, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddCouponsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(AddCouponsActivity.this, vCodeBenan.getMessage());
                if(vCodeBenan.getCode()==1)
                {
                    setResult(RESULT_OK);
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(editText, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
