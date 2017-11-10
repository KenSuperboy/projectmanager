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
import com.gohoc.xiupuling.adapter.WorksDetails;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushOrderConfimResultBean;
import com.gohoc.xiupuling.bean.PushResultBean;
import com.gohoc.xiupuling.bean.PushResultMultiple;
import com.gohoc.xiupuling.bean.StarLeveMoney;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.push.PushByDinActivity;
import com.gohoc.xiupuling.ui.push.PushReqDtActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushAddOrderConfirmActivity extends BasicActivity {

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
    private PushResultMultiple pushResultMultiple;
    private WorksDetails worksDetail;
    private double uinMoney = 0, fund_pool_amount = 0, total_amount = 0;
    private String range_id, works_id;
    private StarLeveMoney buboahe;
    private double bubaohexishu = 0;
    private int weeks = 0;
    private PushNormlBean pushNormlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_order_confirm);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("提交订单");
        pushResultMultiple = (PushResultMultiple) getIntent().getExtras().get("pushResultMultiple");
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        initDates();
    }

    private void initDates() {


        if (pushResultMultiple.getType() == 1) {


        } else if (pushResultMultiple.getType() == 2) {
            range_id = pushResultMultiple.getRangeBean().getRange_id();
            uinMoney = pushResultMultiple.getRangeBean().getUnit_price();
            fund_pool_amount = pushResultMultiple.getRangeBean().getFund_pool_amount();
            total_amount = pushResultMultiple.getRangeBean().getTotal_amount();
            works_id = pushResultMultiple.getRangeBean().getWork_id();
            //    weeks = pushResultMultiple.getRangeBean().getWeek_cnt();
        } else {
            range_id = pushResultMultiple.getUnionBean().getRange_id();
            uinMoney = pushResultMultiple.getUnionBean().getUnit_price();
            fund_pool_amount = pushResultMultiple.getUnionBean().getFund_pool_amount();
            total_amount = pushResultMultiple.getUnionBean().getTotal_amount();
            works_id = pushResultMultiple.getUnionBean().getWork_id();
            //  weeks = pushResultMultiple.getUnionBean().getWeek_cnt();
        }
        //  if(weeks<1)
        weeks = 1;
        workdetail(works_id);

        tipsTv.setText("单屏单循环播放该作品，需支付" + uinMoney + "元");
        moneyEt.setHint(uinMoney + "");
        terminalCTv.setText(fund_pool_amount + "");
        playCTv.setText(total_amount + "");
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
        getBuBaoHe();
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
        RxRetrofitClient.getInstance(this).payRangeMarket(range_id, moneyEt.getText() + "", new Observer<PushOrderConfimResultBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushAddOrderConfirmActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushOrderConfimResultBean pushOrderConfimResultBean) {
                if (pushOrderConfimResultBean.getCode() == 1) {

                    startActivity(new Intent(PushAddOrderConfirmActivity.this, PushOrderPayActivity.class)
                            .putExtra("resultBean", pushOrderConfimResultBean)
                            .putExtra("pushNormlBean", pushNormlBean));
                } else
                    Utils.toast(PushAddOrderConfirmActivity.this, pushOrderConfimResultBean.getMessage());
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
        if (!TextUtils.isEmpty(moneyEt.getText())) {
            double touru = Double.parseDouble(String.valueOf(moneyEt.getText()));
            //每周最少播放次数
            int palyCount = (int) (touru / uinMoney * 300);
            //涉及终端数
            int tCount = (int) (touru / uinMoney / weeks * bubaohexishu);
            if (tCount < 1 && (touru >= uinMoney))
                tCount = 1;
            playCTv.setText(palyCount + "次");
            terminalCTv.setText(tCount + "个终端");
        } else {
            terminalCTv.setText("0个终端");
            playCTv.setText("0次");
        }
    }


    public void workdetail(String id) {
        RxRetrofitClient.getInstance(this).workdetail(id, new Observer<WorksDetails>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushAddOrderConfirmActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(WorksDetails worksDetails) {
                if (worksDetails.getCode() == 1) {
                    worksDetail = worksDetails;
                    Glide.with(PushAddOrderConfirmActivity.this)
                            .load(NetConstant.BASE_USER_RESOURE + worksDetails.getData().getMateriallist().get(0).getMaterial_store_url() + Utils.getThumbnail(202, 202)
                                    + "")
                            // .placeholder(R.mipmap.icon_port_home)
                            //.error(R.mipmap.icon_port_home)\
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(coverIv);
                    titleTv.setText(pushNormlBean.getName());
                }

            }
        });

    }

    private void getBuBaoHe() {
        RxRetrofitClient.getInstance(this).getfeedictlist("401", new Observer<StarLeveMoney>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushAddOrderConfirmActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(StarLeveMoney starLeveMoney) {
                if (starLeveMoney.getCode() == 1) {
                    buboahe = starLeveMoney;
                    //选择不饱和系数
                    if (weeks == 1)
                        bubaohexishu = Double.parseDouble(buboahe.getData().get(0).getDictval());
                    else if (weeks < 4)
                        bubaohexishu = Double.parseDouble(buboahe.getData().get(1).getDictval());
                    else if (weeks < 7)
                        bubaohexishu = Double.parseDouble(buboahe.getData().get(2).getDictval());
                    else if (weeks < 10)
                        bubaohexishu = Double.parseDouble(buboahe.getData().get(3).getDictval());
                    else
                        bubaohexishu = Double.parseDouble(buboahe.getData().get(4).getDictval());

                    calculator();
                }

            }
        });
    }
}
