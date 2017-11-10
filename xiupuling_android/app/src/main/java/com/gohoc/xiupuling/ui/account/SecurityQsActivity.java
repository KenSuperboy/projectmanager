package com.gohoc.xiupuling.ui.account;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.SimpleListAdater;
import com.gohoc.xiupuling.bean.SecurityQsBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.MyPopWindow;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.DisplayMetricsUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;
import rx.Observer;

public class SecurityQsActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.quetion1_tv)
    TextView quetion1Tv;
    @BindView(R.id.quetion1_iv)
    ImageView quetion1Iv;
    @BindView(R.id.quetion1_ll)
    LinearLayout quetion1Ll;
    @BindView(R.id.answerq1_et)
    EditText answerq1Et;
    @BindView(R.id.quetion2_tv)
    TextView quetion2Tv;
    @BindView(R.id.quetion2_iv)
    ImageView quetion2Iv;
    @BindView(R.id.quetion2_ll)
    LinearLayout quetion2Ll;
    @BindView(R.id.answerq2_et)
    EditText answerq2Et;
    @BindView(R.id.quetion3_tv)
    TextView quetion3Tv;
    @BindView(R.id.quetion3_iv)
    ImageView quetion3Iv;
    @BindView(R.id.quetion3_ll)
    LinearLayout quetion3Ll;
    @BindView(R.id.answerq3_et)
    EditText answerq3Et;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;


    private MyPopWindow popupWindowHelperQuestion1, popupWindowHelperQuestion2, popupWindowHelperQuestion3;
    private View questionView1, questionView2, questionView3;
    private ListView list1, list2, list3;
    private SecurityQsBean securityQsBean1, securityQsBean2, securityQsBean3;
    private SecurityQsBean.DataBean sqs1, sqs2, sqs3;
    private TextWatcher textWatcher;

    private Window window;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_qs);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("设置安保问题");
        if (getIntent().getIntExtra("type", 0) != 1)
            showDg();
        getQuestionList(1);
        getQuestionList(2);
        getQuestionList(3);
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

    @OnClick({R.id.toolbar_left_title, R.id.quetion1_ll, R.id.quetion2_ll, R.id.quetion3_ll, R.id.sumbit_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                SecurityQsActivity.this.finish();
                break;
            case R.id.quetion1_ll:
                popupWindowHelperQuestion1.showAsDropDown(quetion1Ll, DisplayMetricsUtil.dip2px(SecurityQsActivity.this, 300), 0);
                break;
            case R.id.quetion2_ll:
                popupWindowHelperQuestion2.showAsDropDown(quetion2Ll, DisplayMetricsUtil.dip2px(SecurityQsActivity.this, 300), 0);
                break;
            case R.id.quetion3_ll:
                popupWindowHelperQuestion3.showAsDropDown(quetion3Ll, DisplayMetricsUtil.dip2px(SecurityQsActivity.this, 300), 0);
                break;
            case R.id.sumbit_button_ll:
                sumbit();
                break;
        }
    }

    public void getQuestionList(final int type) {
        RxRetrofitClient.getInstance(SecurityQsActivity.this).securityqalist(type + "", new Observer<SecurityQsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SecurityQsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(SecurityQsBean securityQsBean) {
                if (securityQsBean.getCode() == 1) {
                    if (type == 1) {
                        securityQsBean1 = securityQsBean;
                        sqs1 = securityQsBean1.getData().get(0);
                        quetion1Tv.setText(securityQsBean.getData().get(0).getDictcaption() + "");
                        Logger.e(sqs1.getDictcaption());
                    } else if (type == 2) {
                        securityQsBean2 = securityQsBean;
                        sqs2 = securityQsBean2.getData().get(0);
                        quetion2Tv.setText(securityQsBean.getData().get(0).getDictcaption() + "");
                        Logger.e(sqs2.getDictcaption());
                    } else {
                        securityQsBean3 = securityQsBean;
                        sqs3 = securityQsBean3.getData().get(0);
                        quetion3Tv.setText(securityQsBean.getData().get(0).getDictcaption() + "");
                        Logger.e(sqs3.getDictcaption());
                    }

                    initQuestionList(securityQsBean.getData(), type);
                }
            }


        });
    }

    private void initQuestionList(List<SecurityQsBean.DataBean> questionList, int type) {

        if (type == 1) {
            questionView1 = LayoutInflater.from(this).inflate(R.layout.simple_list, null);
            list1 = (ListView) questionView1.findViewById(R.id.list);
            list1.setAdapter(new SimpleListAdater(SecurityQsActivity.this, questionList));
            list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    sqs1 = securityQsBean1.getData().get(position);
                    quetion1Tv.setText(sqs1.getDictcaption() + "");
                    popupWindowHelperQuestion1.dismiss();
                }
            });
            popupWindowHelperQuestion1 = new MyPopWindow(this, questionView1);
        } else if (type == 2) {
            questionView2 = LayoutInflater.from(this).inflate(R.layout.simple_list, null);
            list2 = (ListView) questionView2.findViewById(R.id.list);
            list2.setAdapter(new SimpleListAdater(SecurityQsActivity.this, questionList));
            list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    sqs2 = securityQsBean2.getData().get(position);
                    quetion2Tv.setText(sqs2.getDictcaption() + "");
                    popupWindowHelperQuestion2.dismiss();
                }
            });
            popupWindowHelperQuestion2 = new MyPopWindow(this, questionView2);
        } else {
            questionView3 = LayoutInflater.from(this).inflate(R.layout.simple_list, null);
            list3 = (ListView) questionView3.findViewById(R.id.list);
            list3.setAdapter(new SimpleListAdater(SecurityQsActivity.this, questionList));
            list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    sqs3 = securityQsBean3.getData().get(position);
                    quetion3Tv.setText(sqs3.getDictcaption() + "");
                    popupWindowHelperQuestion3.dismiss();
                }
            });
            popupWindowHelperQuestion3 = new MyPopWindow(this, questionView3);
        }


    }

    private void sumbit() {

        if (TextUtils.isEmpty(answerq1Et.getText()) || TextUtils.isEmpty(answerq2Et.getText()) || TextUtils.isEmpty(answerq3Et.getText()))
            return;

        Logger.e(sqs1.getDictcaption() + "" + sqs2.getDictcaption() + "" + sqs3.getDictcaption() + "" +
                answerq1Et.getText() + "" + answerq2Et.getText() + "" + answerq3Et.getText() + "");
        if (getIntent().getIntExtra("type", 0) != 1) {
            RxRetrofitClient.getInstance(SecurityQsActivity.this).setsecurityqa(sqs1.getDictcaption() + "", sqs2.getDictcaption() + "", sqs3.getDictcaption() + "",
                    answerq1Et.getText() + "", answerq2Et.getText() + "", answerq3Et.getText() + "", new Observer<VCodeBenan>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Utils.toast(SecurityQsActivity.this, "请检查网络是否正常");
                            try {
                                throw e;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(VCodeBenan vCodeBenan) {
                            Utils.toast(SecurityQsActivity.this, vCodeBenan.getMessage());
                            if (vCodeBenan.getCode() == 1) {
                                Credential.getInstance().getCurUser(SecurityQsActivity.this).getData().setIs_security_question(1);
                                EventBus.getDefault().post(new Event.UserEvent());
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    });
        } else {
            RxRetrofitClient.getInstance(SecurityQsActivity.this).updatesecurityqa(sqs1.getDictcaption() + "", sqs2.getDictcaption() + "", sqs3.getDictcaption() + "",
                    answerq1Et.getText() + "", answerq2Et.getText() + "", answerq3Et.getText() + "", new Observer<VCodeBenan>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Utils.toast(SecurityQsActivity.this, "请检查网络是否正常");
                            try {
                                throw e;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(VCodeBenan vCodeBenan) {
                            Utils.toast(SecurityQsActivity.this, vCodeBenan.getMessage());
                            if (vCodeBenan.getCode() == 1) {
                                EventBus.getDefault().post(new Event.UserEvent());
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    });
        }


    }


    private void showDg() {
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
        window.setContentView(R.layout.pop_security_qs);

        window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(answerq1Et, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
