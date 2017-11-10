package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushOrderConfimResultBean;
import com.gohoc.xiupuling.bean.PushResultBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushOrderConfirmActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.cover_iv)
    ImageView coverIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.tips_tv)
    TextView tipsTv;
    @BindView(R.id.money_et)
    EditText moneyEt;
    @BindView(R.id.terminal_c_tv)
    TextView terminalCTv;
    @BindView(R.id.play_c_tv)
    TextView playCTv;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    private PushResultBean pushResultBean;
    private PushNormlBean pushNormlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_order_confirm);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("提交订单");
        pushResultBean = (PushResultBean) getIntent().getExtras().get("pushResultBean");
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        initDates();

    }

    private void initDates() {
        if(!pushNormlBean.isGd())
        {
            moneyEt.setFocusable(false);
            moneyEt.setEnabled(false);
            if(pushNormlBean.getWeekCount()>1){
                moneyEt.setText(String.valueOf(pushResultBean.getData().getInforange().getUnit_price()*pushNormlBean.getWeekCount()));
            }else {
                moneyEt.setText(String.valueOf(pushResultBean.getData().getInforange().getUnit_price()));
            }

            calculator();
        }
        Glide.with(this)
                .load(Constant.NetConstant.BASE_USER_RESOURE + pushNormlBean.getCover_url() + "")
                // .placeholder(R.mipmap.icon_port_home)
                //.error(R.mipmap.icon_port_home)\
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(coverIv);
        titleTv.setText(pushNormlBean.getName() + "");
        tipsTv.setText("单屏单循环播放该作品，需支付" + pushResultBean.getData().getInforange().getUnit_price() + "元");
         moneyEt.setHint(pushResultBean.getData().getInforange().getUnit_price() + "");
        //terminalCTv.setText(pushResultBean.getData().getInforange().getFund_pool_amount() + "");
      //  playCTv.setText(pushResultBean.getData().getInforange().getTotal_amount() + "");
        moneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculator();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @OnClick({R.id.toolbar_left_title, R.id.button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.button_ll:
                sumit();
                break;
        }
    }

    private void sumit() {
        if (TextUtils.isEmpty(moneyEt.getText())) {
            Utils.toast(this, "请输入金额");
            return;
        }
        RxRetrofitClient.getInstance(this).payRangeMarket(pushResultBean.getData().getInforange().getRange_id(), moneyEt.getText() + "", new Observer<PushOrderConfimResultBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushOrderConfirmActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushOrderConfimResultBean pushOrderConfimResultBean) {
                if (pushOrderConfimResultBean.getCode() == 1) {
                    startActivity(new Intent(PushOrderConfirmActivity.this, PushOrderPayActivity.class)
                            .putExtra("resultBean", pushOrderConfimResultBean)
                            .putExtra("pushNormlBean", pushNormlBean));
                } else
                    Utils.toast(PushOrderConfirmActivity.this, pushOrderConfimResultBean.getMessage());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(moneyEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }


    /***
     *   计算涉及终端数
     *   单价：即单屏单周播放的费用；
     *   单价 = 作品所占的格子数 * 星级价格
     * 	 涉及终端数 = 投入金额 / 单价 / 投放时间（跨周数） * 不饱和补偿系数
     *   最少播放次数 = 投入金额 / 单价 * 每周最少播放次数
     */
    private void calculator() {
        Logger.e(String.valueOf(moneyEt.getText()));
        if (!TextUtils.isEmpty(moneyEt.getText())) {
            //涉及终端数
            double tMoney = Double.parseDouble(moneyEt.getText() + ""); //投入金额
            double uMoney = pushNormlBean.getsPrice();//单价
            int weekCount = pushNormlBean.getWeekCount();//投放时间（跨周数）
            double bubaohe = pushNormlBean.getBubaohe();//不饱和补偿系数
            double tCount = tMoney / uMoney / weekCount * bubaohe;
            if(tCount<1 && (tMoney>=uMoney))
                tCount=1;
            terminalCTv.setText(((int) tCount) + "个终端");
            //每周最少播放次数
            int palyCount = (int) (tMoney / uMoney * 300);
            playCTv.setText(palyCount + "次");
        } else {
            terminalCTv.setText("0个终端");
            playCTv.setText("0次");
        }


    }
}
