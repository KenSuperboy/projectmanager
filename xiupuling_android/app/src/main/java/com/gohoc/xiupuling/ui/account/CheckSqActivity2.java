package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MySecurityQsBean;
import com.gohoc.xiupuling.bean.SecurityQsBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;
import rx.Observer;

public class CheckSqActivity2 extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.quetion1_tv)
    TextView quetion1Tv;
    @BindView(R.id.answerq1_et)
    EditText answerq1Et;
    @BindView(R.id.quetion2_tv)
    TextView quetion2Tv;
    @BindView(R.id.answerq2_et)
    EditText answerq2Et;
    @BindView(R.id.quetion3_tv)
    TextView quetion3Tv;
    @BindView(R.id.answerq3_et)
    EditText answerq3Et;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;


    private PopupWindowHelper popupWindowHelperQuestion1, popupWindowHelperQuestion2, popupWindowHelperQuestion3;
    private View questionView1, questionView2, questionView3;
    private ListView list1, list2, list3;
    private SecurityQsBean securityQsBean1, securityQsBean2, securityQsBean3;
    private SecurityQsBean.DataBean sqs1, sqs2, sqs3;
    private TextWatcher textWatcher;
    private final static int CHNGE_REQUEST_RESULT = 1000;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sq2);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        try {
            toolbarTitle.setText(getIntent().getStringExtra("title"));
        } catch (Exception e) {
            toolbarTitle.setText("安保问题验证");
        }
        mobile = getIntent().getStringExtra("mobile");
        getmysecurityqa();
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(answerq1Et.getText()) || TextUtils.isEmpty(answerq2Et.getText()) || TextUtils.isEmpty(answerq3Et.getText()))
                    sumbitButtonLl.setBackgroundResource(R.color.sms_ds);
                else
                    sumbitButtonLl.setBackgroundResource(R.color.colorPrimary);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        answerq1Et.addTextChangedListener(textWatcher);
        answerq2Et.addTextChangedListener(textWatcher);
        answerq3Et.addTextChangedListener(textWatcher);
        answerq3Et.setImeOptions(EditorInfo.IME_ACTION_GO);
        answerq3Et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            sumbit();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.sumbit_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.sumbit_button_ll:
                sumbit();
                break;
        }
    }


    private void getmysecurityqa() {
        RxRetrofitClient.getInstance(this).getmysecurityqa(new Observer<MySecurityQsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CheckSqActivity2.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(MySecurityQsBean mySecurityQsBean) {
                if (mySecurityQsBean.getCode() == 1) {
                    quetion1Tv.setText(mySecurityQsBean.getData().getQuestion_a());
                    quetion2Tv.setText(mySecurityQsBean.getData().getQuestion_b());
                    quetion3Tv.setText(mySecurityQsBean.getData().getQuestion_c());
                }

            }
        });
    }


    private void sumbit() {

        if (TextUtils.isEmpty(answerq1Et.getText()) || TextUtils.isEmpty(answerq2Et.getText()) || TextUtils.isEmpty(answerq3Et.getText()))
            return;


        RxRetrofitClient.getInstance(CheckSqActivity2.this).checksecurityqa(quetion1Tv.getText() + "", quetion2Tv.getText() + "", quetion3Tv.getText() + "",
                answerq1Et.getText() + "", answerq2Et.getText() + "", answerq3Et.getText() + "", new Observer<VCodeBenan>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(CheckSqActivity2.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(VCodeBenan vCodeBenan) {
                        if (vCodeBenan.getCode() == 1) {
                            startActivity(new Intent(CheckSqActivity2.this, ChangeMobile4Activity.class).putExtra("mobile", mobile));
                            finish();
                        } else {
                            Utils.toast(CheckSqActivity2.this, vCodeBenan.getMessage());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(answerq1Et, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
