package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.CreatRqResultBean;
import com.gohoc.xiupuling.bean.OrderDetailBean;
import com.gohoc.xiupuling.bean.StarLeveMoney;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.SimpleEditActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.suke.widget.SwitchButton;
import com.wuxiaolong.androidutils.library.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class RequirementCreteadSucessActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.conect_sb)
    SwitchButton conectSb;
    @BindView(R.id.tel_tv)
    TextView telTv;
    @BindView(R.id.edit_tel_lv)
    LinearLayout editTelLv;
    @BindView(R.id.pice_sb)
    SwitchButton piceSb;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.edit_money_lv)
    LinearLayout editMoneyLv;
    @BindView(R.id.ps_ll)
    LinearLayout psLl;
    private OrderDetailBean orderDetailBeans;

    private ACache mCache;
    private static int TEL_REQUEST_RESULT = 1000;
    private static int MONEY_REQUEST_RESULT = 1001;
    private String tel;
    private double money = 0;
    private CreatRqResultBean creatRqResultBean;
    private UserBaseBean userBaseBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rush_sucess);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        getFree();
        toolbarTitle.setText("发布成功");
        toolbarLeftTitle.setText("选作品");
        creatRqResultBean = (CreatRqResultBean) getIntent().getExtras().get("creatRqResultBean");
        mCache = ACache.get(this);
        userBaseBean = Credential.getInstance().getCurUser(this);
        tel = userBaseBean.getData().getMobile();
        initInfo(userBaseBean);
    }

    @OnClick({R.id.edit_tel_lv, R.id.edit_money_lv, R.id.toolbar_left_title, R.id.ps_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_tel_lv:
                startActivityForResult(new Intent(RequirementCreteadSucessActivity.this, SimpleEditActivity.class)
                                .putExtra("title", "联系电话")
                                .putExtra("value", tel)
                                .putExtra("tipes", "请输入联系电话")
                                .putExtra("textType", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL)
                        , TEL_REQUEST_RESULT);
                break;
            case R.id.edit_money_lv:
                Logger.e("money", String.valueOf(money));
                startActivityForResult(new Intent(RequirementCreteadSucessActivity.this, SimpleEditActivity.class)
                                .putExtra("title", "意向度")
                                .putExtra("value", String.valueOf(money))
                                .putExtra("tipes", "请输入金额")
                                .putExtra("textType", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        , MONEY_REQUEST_RESULT);
                break;
            case R.id.toolbar_left_title:
                goBack();
                break;
            case R.id.ps_ll:
                startActivity(new Intent(RequirementCreteadSucessActivity.this, WebViewActivity.class).putExtra("url", Utils.getSystemInfoBean(RequirementCreteadSucessActivity.this).getData().getAboutGainpoint()).putExtra("title", "如何获取令牌"));
                break;

        }
    }

    private void goBack() {
        EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        EventBus.getDefault().post(new Event.MainIndex(3));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == TEL_REQUEST_RESULT) {
                String value = data.getStringExtra("value");
                if (value != null) {
                    tel = value;
                    telTv.setText(value + "");
                    updateintent(creatRqResultBean.getData().getRq_id(), conectSb.isChecked() == true ? "1" : "0", tel, piceSb.isChecked() == true ? "1" : "0", Utils.insertComma(String.valueOf(money), 2));
                }
            } else if (requestCode == MONEY_REQUEST_RESULT) {
                String value = data.getStringExtra("value");
                if (!TextUtils.isEmpty(value)) {
                    money = Double.parseDouble(value);
                    moneyTv.setText("￥" + new DecimalFormat("0.0").format(Double.parseDouble(value)));
                } else {
                    money = Double.parseDouble("0");
                    moneyTv.setText("￥0.0");
                }
                updateintent(creatRqResultBean.getData().getRq_id(), conectSb.isChecked() == true ? "1" : "0", tel, piceSb.isChecked() == true ? "1" : "0", Utils.insertComma(String.valueOf(money), 2));
            }
        }
    }

    private void initInfo(UserBaseBean userBaseBean) {
        this.userBaseBean = userBaseBean;
        tel = userBaseBean.getData().getMobile();
//        money = orderDetailBeans.getData().getTotalamt();
        userNameTv.setText(getIntent().getStringExtra("title") + "");
        telTv.setText(tel + "");
        timeTv.setText(TimeUtil.getCurrentTime("yyyy-MM-dd") + " 发布成功");
        moneyTv.setText("￥" + money);
        conectSb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                updateintent(creatRqResultBean.getData().getRq_id(), isChecked == true ? "1" : "0", tel, piceSb.isChecked() == true ? "1" : "0", Utils.insertComma(String.valueOf(money), 2));
            }
        });
        piceSb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                updateintent(creatRqResultBean.getData().getRq_id(), conectSb.isChecked() == true ? "1" : "0", tel, isChecked == true ? "1" : "0", Utils.insertComma(String.valueOf(money), 2));
            }
        });
    }

    private void updateintent(String rq_id, String is_show_phone, String rq_telephone, String is_pay_money, String rq_intention_money) {
        RxRetrofitClient.getInstance(this).updateintent(rq_id, is_show_phone, rq_telephone, is_pay_money, rq_intention_money, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(RequirementCreteadSucessActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(RequirementCreteadSucessActivity.this, vCodeBenan.getMessage());
            }
        });
    }

    private void getFree() {
        RxRetrofitClient.getInstance(this).getfeedictlist("503", new Observer<StarLeveMoney>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(RequirementCreteadSucessActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(StarLeveMoney starLeveMoney) {
                if (starLeveMoney.getCode() == 1) {
                    money = Double.parseDouble(starLeveMoney.getData().get((creatRqResultBean.getData().getRq_type() - 1)).getDictval());
                    Logger.e("money", money+"");
                    moneyTv.setText("￥" + new DecimalFormat("0.0").format(money));
                }

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
        }
        return false;
    }
}
