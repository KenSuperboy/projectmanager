package com.gohoc.xiupuling.ui.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class AccountEditNameActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.setting_nike_name_et)
    ClearEditText settingNikeNameEt;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit_name);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        settingNikeNameEt.requestFocus();
        toolbarTitle.setText("昵称");
        name = this.getIntent().getStringExtra("name");
        if (!TextUtils.isEmpty(name)) {
            settingNikeNameEt.setText(name + "");
        }
        settingNikeNameEt.setImeOptions(EditorInfo.IME_ACTION_GO);
        settingNikeNameEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            submit();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                AccountEditNameActivity.this.finish();
                break;
            case R.id.toolbar_right:
                submit();
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(settingNikeNameEt.getText())) {
            Utils.toast(this, "请输入昵称");
            return;
        }
        showProgressDialog("正在加载...");
        RxRetrofitClient.getInstance(AccountEditNameActivity.this).updatePortrait(null, settingNikeNameEt.getText().toString(), null, null, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
                Utils.toast(AccountEditNameActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(AccountEditNameActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    Credential.getInstance().getCurUser(AccountEditNameActivity.this).getData().setNick_name(settingNikeNameEt.getText() + "");
                    EventBus.getDefault().post(new Event.UserEvent());
                    AccountEditNameActivity.this.finish();
                }

            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(settingNikeNameEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
