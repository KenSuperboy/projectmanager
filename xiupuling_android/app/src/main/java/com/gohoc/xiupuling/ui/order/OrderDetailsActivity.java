package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.OrderDetailBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.PreviewWActivity;
import com.gohoc.xiupuling.ui.useraudit.AuditResultActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;
import com.wuxiaolong.androidutils.library.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class OrderDetailsActivity extends BasicActivity {
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.flage_no_tv)
    TextView flageNoTv;
    @BindView(R.id.cover_iv)
    ImageView coverIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.state_iv)
    ImageView stateIv;
    @BindView(R.id.arr_right_iv)
    ImageView arrRightIv;
    @BindView(R.id.money_dt_tv)
    TextView moneyDtTv;
    @BindView(R.id.activity_v_tv)
    TextView activityVTv;
    @BindView(R.id.how_t_tv)
    TextView howTTv;
    @BindView(R.id.how_v_tv)
    TextView howVTv;
    @BindView(R.id.start_p_v_tv)
    TextView startPVTv;
    @BindView(R.id.end_p_v_tv)
    TextView endPVTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.req_time_dt_rl)
    RelativeLayout reqTimeDtRl;
    @BindView(R.id.prew_ll)
    LinearLayout prewLl;
    @BindView(R.id.left_ll)
    LinearLayout leftLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.opt)
    PercentRelativeLayout opt;
    @BindView(R.id.opt_shenghe)
    PercentRelativeLayout opt_shenghe;
    @BindView(R.id.splite)
    TextView splite;
    @BindView(R.id.tv_type)
    TextView tv_type;//
    @BindView(R.id.left_ll_bohui)
    LinearLayout left_ll_bohui;
    @BindView(R.id.right_ll_tongguo)
    LinearLayout right_ll_tongguo;
    //  private OrderHistoryBean.DataBean orderHistoryBean;
    private OrderDetailBean orderDetailBeans;
    private String range_id;
    private String terminal_id,playTypeString="";
    private String url="",repeat="",order="",orderString="";
    private int type = 0,playType=0;
    private boolean ignore,limit;
    private boolean shenghe;
    private static int UPDAE_REQUEST_RESULT = 1000;
    private String start="",end="",user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        toolbarTitle.setText("订单详情");
        setStatusColor(R.color.colorPrimary);
        range_id = getIntent().getStringExtra("range_id");
        terminal_id = getIntent().getStringExtra("terminal_id");
        url = getIntent().getStringExtra("url");
        user_id=getIntent().getStringExtra("user_id");
        type = getIntent().getIntExtra("type", 0);
        ignore = getIntent().getBooleanExtra("ignore",false);
        limit = getIntent().getBooleanExtra("limit",false);
        shenghe=getIntent().getBooleanExtra("shenghe",false);
        repeat=getIntent().getStringExtra("repeat");
        start=getIntent().getStringExtra("start");
        end=getIntent().getStringExtra("end");
        getOrderDetail(range_id, terminal_id, "2");
        if (type == 1) {
            splite.setVisibility(View.VISIBLE);
            opt.setVisibility(View.VISIBLE);
            leftLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogs();
                }
            });
            rightLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(OrderDetailsActivity.this, OrderChargebackActivity.class)
                            .putExtra("range_id", range_id)
                            .putExtra("playType", playType)
                            .putExtra("title", titleTv.getText().toString())
                            .putExtra("terminal_id", terminal_id), UPDAE_REQUEST_RESULT);
                }
            });
            stateIv.setImageResource(R.mipmap.icon_ddxq_zhixingzhong);
        }
        if(shenghe){
            opt_shenghe.setVisibility(View.VISIBLE);
        }
        orderString=getIntent().getStringExtra("order");
        if(!TextUtils.isEmpty(orderString)&&orderString.equals("package")){
            playType=0;
            order="组合包投放";
            playTypeString="播放";
        }else if(!TextUtils.isEmpty(orderString)&&orderString.equals("link")){
            playType=1;
            order="联动包投放";
            playTypeString="播放";
        }else{
            playType=2;
            order="单播投放";
            playTypeString="轮播";
        }


        /*if(playType==0){
            tv_type.setText("单播投放：非独占时段轮播");
        }else {
            tv_type.setText("单播投放：独占时段轮播");
        }*/

        if(!limit&&ignore){
            tv_type.setText(order+"：全天候独占轮"+playTypeString);
        }else {
            if(!limit&&!ignore){
                tv_type.setText(order+"：非独占时段"+playTypeString);
            }else {
                if(!TextUtils.isEmpty(repeat)){
                    tv_type.setText(order+"："+ CustomUtils.getPlayTime(repeat,ignore)+" "+CustomUtils.start_end(start+"",end+""));
                }else {
                    tv_type.setText(order+"：非独占时段"+playTypeString);
                }
            }
        }
    }

    private void showDialogs() {
        new AlertView("工作日:9:00-12:00 14:00-18:00", null, "取消", null,
                new String[]{Utils.getSystemInfoBean(this).getData().getServiceTelephone1() + ""},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {
                if (position != -1)
                    AppUtils.actionDial(OrderDetailsActivity.this, Utils.getSystemInfoBean(OrderDetailsActivity.this).getData().getServiceTelephone1() + "");
            }
        }).show();
    }


    private void getOrderDetail(String range_id, String terminal_id, String type) {

        RxRetrofitClient.getInstance(this).orderDetail(range_id, terminal_id, type, new Observer<OrderDetailBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {

                Utils.toast(OrderDetailsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(OrderDetailBean orderDetailBean) {
                if (orderDetailBean.getCode() == 1) {
                    orderDetailBeans = orderDetailBean;
                    initDates(orderDetailBean);
                }

            }
        });
    }

    private void initDates(OrderDetailBean orderDetailBean) {
        flageNoTv.setText("投放标识：" + orderDetailBean.getData().getRange_id());
        LogUtil.d(url+"：头像路径："+NetConstant.BASE_USER_RESOURE + url+ Utils.getThumbnail(202,202));

        Glide.with(OrderDetailsActivity.this)
                .load(Constant.NetConstant.BASE_USER_RESOURE+url+ Utils.getThumbnail(202,202))
                .placeholder(R.mipmap.df_logo)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(coverIv);

        /*Glide.with(this)
                .load(NetConstant.BASE_USER_RESOURE + url+ Utils.getThumbnail(300,300))
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(coverIv);*/
        //   Logger.e(Constant.NetConstant.BASE_USER_RESOURE + orderDetailBean.getData().getWorkdetail().getWork_cover_url());
        titleTv.setText(orderDetailBean.getData().getActivity_title()+ "");
        ratingbar.setEnabled(false);
        ratingbar.setmClickable(false);
        ratingbar.setStar(orderDetailBean.getData().getStar_level());


        if (orderDetailBean.getData().getActivity_onoff() == 0) {
         activityVTv.setText(TextUtils.isEmpty(orderDetailBean.getData().getActivity_title()) ? "无关联活动" : orderDetailBean.getData().getActivity_content());
            howVTv.setText(TextUtils.isEmpty(orderDetailBean.getData().getActivity_nav_content()) ? "无二维码链接去处" : orderDetailBean.getData().getActivity_nav_content());
        }

        try {
            startPVTv.setText(Utils.delTime(orderDetailBean.getData().getStartdate()) + ""+Utils.getWeekOfDate(orderDetailBean.getData().getStartdate(),"yyyyMMdd"));
            endPVTv.setText(Utils.delTime(orderDetailBean.getData().getEnddate()) + ""+Utils.getWeekOfDate(orderDetailBean.getData().getEnddate(),"yyyyMMdd"));
            timeTv.setText("之间的" + orderDetailBean.getData().getWeek_cnt() + "周");
        }catch (Exception e){}

        /*if(!TextUtils.isEmpty(orderDetailBean.getData().getIs_ignore_other_work()+"")&&(orderDetailBean.getData().getIs_ignore_other_work()+"").equals("null"))
        {
            if(orderDetailBean.getData().getIs_ignore_other_work()==0){
                tv_type.setText("单播投放：非独占时段轮播");
            }else {
                tv_type.setText("单播投放：独占时段轮播");
            }
        }else {
            tv_type.setText("单播投放：非独占时段轮播");
        }*/
    }

    @OnClick({R.id.arr_right_iv, R.id.money_dt_tv, R.id.prew_ll, R.id.left_ll, R.id.right_ll, R.id.toolbar_left_title,R.id.left_ll_bohui,R.id.right_ll_tongguo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.arr_right_iv:
                startActivity(new Intent(OrderDetailsActivity.this, OrderDetailsMoneyDt.class).putExtra("orderDetailBeans", orderDetailBeans).putExtra("id", terminal_id));
                break;
            case R.id.money_dt_tv:
                startActivity(new Intent(OrderDetailsActivity.this, OrderDetailsMoneyDt.class).putExtra("orderDetailBeans", orderDetailBeans).putExtra("id", terminal_id));
                break;
            case R.id.prew_ll:
                startActivity(new Intent(OrderDetailsActivity.this, PreviewWActivity.class).putExtra("work_id", orderDetailBeans.getData().getWork_id()));
                break;
            case R.id.left_ll:
                break;
            case R.id.right_ll:
                break;
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.left_ll_bohui:
                shenheWorkAudit(user_id,terminal_id,range_id,"reject");
                break;
            case R.id.right_ll_tongguo:
                shenheWorkAudit(user_id,terminal_id,range_id,"pass");
                break;
        }
    }

    private void shenheWorkAudit(String user_id, String terminal_id, String range_id, final String dicision) {
        RxRetrofitClient.getInstance(this).shenheTerminalAuditUser(user_id,terminal_id,range_id,dicision, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderDetailsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    if(dicision.equals("pass")){
                        Intent intent=new Intent(OrderDetailsActivity.this,AuditResultActivity.class);
                        intent.putExtra("type","1");
                        startActivity(intent);
                        finish();
                    }else if(dicision.equals("reject")){
                        Intent intent=new Intent(OrderDetailsActivity.this,AuditResultActivity.class);
                        intent.putExtra("type","2");
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Utils.toast(OrderDetailsActivity.this, vCodeBenan.getMessage());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
