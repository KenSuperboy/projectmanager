package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushResultBean;
import com.gohoc.xiupuling.bean.StarLeveMoney;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.order.PushOrderConfirmActivity;
import com.gohoc.xiupuling.ui.order.PushOrderPayReslutActivity;
import com.gohoc.xiupuling.utils.Utils;

import com.gohoc.xiupuling.widget.RatingBar;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.TimeUtil;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushByDinActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.req_cover_iv)
    ImageView reqCoverIv;
    @BindView(R.id.req_activity_tv)
    TextView reqActivityTv;
    @BindView(R.id.req_time_tv)
    TextView reqTimeTv;
    @BindView(R.id.req_time_v_tv)
    TextView reqTimeVTv;
    @BindView(R.id.start_p_tv)
    TextView startPTv;
    @BindView(R.id.start_p_v_tv)
    TextView startPVTv;
    @BindView(R.id.end_p_tv)
    TextView endPTv;
    @BindView(R.id.end_p_v_tv)
    TextView endPVTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.arr_right_iv)
    ImageView arrRightIv;
    @BindView(R.id.req_time_dt_rl)
    RelativeLayout reqTimeDtRl;
    @BindView(R.id.termianl_tv)
    TextView termianlTv;
    @BindView(R.id.shop_tv)
    TextView shopTv;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.free_ll)
    LinearLayout freeLl;
    @BindView(R.id.money_rr)
    RelativeLayout moneyRr;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.req_money_v_tv)
    TextView reqMoneyVTv;
    @BindView(R.id.dt_tv)
    TextView dtTv;
    private SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays;//选择的时间范围
    private static int TIME_REQUEST_RESULT = 1000;
    private PushNormlBean pushNormlBean;
    private StarLeveMoney starLeveMoneys;
    private int ptc;      //时长系数
    private int starLeve = 1;//星级
    private StarLeveMoney buboahe;//不饱和系数表
    private String endTime;
    private DecimalFormat df = new DecimalFormat("#.00");
    private int unitPrice = 0;//单价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_by_din);
        ButterKnife.bind(this);
        toolbarTitle.setText("定投发布");
        setStatusColor(R.color.colorPrimary);
        getBuBaoHe();
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        initDatas();
    }

    private void initDatas() {

        if (null != pushNormlBean) {

            if (pushNormlBean.isFree()) {
                freeLl.setVisibility(View.VISIBLE);
                moneyRr.setVisibility(View.GONE);
            } else {
                freeLl.setVisibility(View.GONE);
                moneyRr.setVisibility(View.VISIBLE);
            }
            ptc = pushNormlBean.getPlaytime() / 30;//时长系数。。
            if (ptc == 0 || pushNormlBean.getPlaytime() % 30 != 0)
                ptc++;
            pushNormlBean.setPtc(ptc);
            pushNormlBean.setWeekCount(1);
            getStarLeveMoney();
            starLeve=pushNormlBean.getShopStar();
            ratingbar.setStar(starLeve);
            ratingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChange(float RatingCount) {

                    if (RatingCount <= pushNormlBean.getShopStar()) {
                        ratingbar.setStar(pushNormlBean.getShopStar());
                        starLeve = pushNormlBean.getShopStar();
                    } else
                        starLeve = (int) RatingCount;
                    unitPrice = ptc * Integer.parseInt(starLeveMoneys.getData().get(starLeve - 1).getDictval());
                    reqMoneyVTv.setText("￥" + String.valueOf(df.format(unitPrice)));
                    pushNormlBean.setsPrice(unitPrice);
                    // pushNormlBean.setBubaohe(buboahe.getData().get());
                    //  pushNormlBean.get
                }
            });

            Glide.with(this)
                    .load(NetConstant.BASE_USER_RESOURE + pushNormlBean.getCover_url() + "")
                    .placeholder(R.mipmap.df_logo)
                    .error(R.mipmap.df_logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(reqCoverIv);
            reqActivityTv.setText(pushNormlBean.getName() + "");
            reqTimeVTv.setText(pushNormlBean.getPlaytime() + "s");


            startPVTv.setText(TimeUtil.getCurrentTime("yyyy-MM-dd") + " " + Utils.getWeekOfDate(TimeUtil.getCurrentTime("yyyy-MM-dd"), "yyyy-MM-dd"));
            endTime = TimeUtil.timeAddSubtract(TimeUtil.getCurrentTime("yyyy-MM-dd"), 7 - TimeUtil.getDayOfWeek());
            endPVTv.setText(endTime + " " + Utils.getWeekOfDate(endTime, "yyyy-MM-dd"));
            selectedDays = new SimpleMonthAdapter.SelectedDays<>();
            selectedDays.setFirst(new SimpleMonthAdapter.CalendarDay(System.currentTimeMillis()));
            Logger.e(Utils.StrToDay(endTime) + "");
            selectedDays.setLast(new SimpleMonthAdapter.CalendarDay(TimeUtil.getYear(), Utils.StrToMonth(endTime), Utils.StrToDay(endTime)));
            termianlTv.setText(pushNormlBean.getTermianlNo() + "号机");
            shopTv.setText("安装于：" + pushNormlBean.getShopName());
            pushNormlBean.setGd(false);

        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.req_time_dt_rl, R.id.button_ll, R.id.arr_right_iv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.req_time_dt_rl:
                startActivityForResult(new Intent(PushByDinActivity.this, PushReqSelectTimeActivity.class).putExtra("selectedDays", selectedDays), TIME_REQUEST_RESULT);
                break;
            case R.id.button_ll:
                rangeMarket(pushNormlBean.getRq_id(), pushNormlBean.getWork_id(), "2", Utils.formatDate(selectedDays.getFirst().getDate()
                        , "yyyyMMdd"), Utils.formatDate(selectedDays.getLast().getDate(), "yyyyMMdd")
                        , null, null, null, null, null, null, null, null, null, null, null, null, null, pushNormlBean.getTerminanlId(),String.valueOf(pushNormlBean.getShopStar()) , null, null, null, null);
                break;
            case R.id.dt_tv:
            case R.id.arr_right_iv2:
                startActivity(new Intent(PushByDinActivity.this, PushReqChargesDtActivity.class)
                        .putExtra("leveMoney", Integer.parseInt(starLeveMoneys.getData().get(starLeve - 1).getDictval()))
                        .putExtra("times", pushNormlBean.getPlaytime())
                        .putExtra("ptc", ptc)
                        .putExtra("leve", starLeve)
                );
                break;
        }
    }


    private void rangeMarket(String rq_id, String work_id, String range_type, String startdate, String enddate, String addr_type
            , String region_id, String group_type, String group_id_a, String group_id_b, String group_id_c, String bussiness_id_a, String bussiness_id_b, String bussiness_id_c
            , String union_type, String union_id, String shop_type, String shop_id, String terminal_id, String star_level, String address, String addr_longitude, String addr_latitude
            , String distance) {

     /*   if (selectBussTypeList == null || selectBussTypeList.size() < 1) {
            Utils.toast(this, "请选择行业");
            return;
        }*/

        showProgressDialog("正在提交.....");
        if(pushNormlBean.getFlag()==0){
            RxRetrofitClient.getInstance(this).rangeMarket(rq_id, work_id, range_type, startdate, enddate, addr_type
                    , region_id, group_type, group_id_a, group_id_b, group_id_c, bussiness_id_a, bussiness_id_b, bussiness_id_c
                    , union_type, union_id, shop_type, shop_id, terminal_id, star_level, address, addr_longitude, addr_latitude
                    , distance, new Observer<PushResultBean>() {
                        @Override
                        public void onCompleted() {
                            closeProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            closeProgressDialog();
                            Utils.toast(PushByDinActivity.this, "请检查网络是否正常");
                            try {
                                throw e;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(PushResultBean pushResultBean) {
                            // Utils.toast(PushByDinActivity.this, pushResultBean.getMessage());
                            if (pushResultBean.getCode() == 1) {
                                if (pushNormlBean.isFree())
                                    startActivity(new Intent(PushByDinActivity.this, PushOrderPayReslutActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                                else
                                    startActivity(new Intent(PushByDinActivity.this, PushOrderConfirmActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                                finish();
                            } else {
                                Utils.toast(PushByDinActivity.this, pushResultBean.getMessage());
                            }

                        }
                    });
        }else {
            RxRetrofitClient.getInstance(this).packageRangeMarket(rq_id,  range_type, startdate, enddate, addr_type
                    , region_id, group_type, group_id_a, group_id_b, group_id_c, bussiness_id_a, bussiness_id_b, bussiness_id_c
                    , union_type, union_id, shop_type, shop_id, terminal_id, star_level, address, addr_longitude, addr_latitude
                    , distance, new Observer<PushResultBean>() {
                        @Override
                        public void onCompleted() {
                            closeProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            closeProgressDialog();
                            Utils.toast(PushByDinActivity.this, "请检查网络是否正常");
                            try {
                                throw e;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(PushResultBean pushResultBean) {
                            // Utils.toast(PushByDinActivity.this, pushResultBean.getMessage());
                            if (pushResultBean.getCode() == 1) {
                                if (pushNormlBean.isFree())
                                    startActivity(new Intent(PushByDinActivity.this, PushOrderPayReslutActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                                else
                                    startActivity(new Intent(PushByDinActivity.this, PushOrderConfirmActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                                finish();
                            } else {
                                Utils.toast(PushByDinActivity.this, pushResultBean.getMessage());
                            }

                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TIME_REQUEST_RESULT) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDay = (SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay>) d.get("selectedDays");
                        if (selectedDay != null) {
                            selectedDays = selectedDay;
                            startPVTv.setText(TimeUtil.unixTimestamp2BeijingTime(selectedDay.getFirst().getDate().getTime(), "yyyy-MM-dd") + " " + Utils.getWeekOfDate(selectedDay.getFirst().getDate()));
                            endPVTv.setText(TimeUtil.unixTimestamp2BeijingTime(selectedDay.getLast().getDate().getTime(), "yyyy-MM-dd") + " " + Utils.getWeekOfDate(selectedDay.getLast().getDate()));
                            int w = (int) Utils.countTwoDayWeek(selectedDay.getFirst().getDate(), selectedDay.getLast().getDate());
                            timeTv.setText("跨" + w + "周");
                            pushNormlBean.setWeekCount(w);
                            //选择不饱和系数
                            if (w == 1)
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(0).getDictval()));
                            else if (w < 4)
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(1).getDictval()));
                            else if (w < 7)
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(2).getDictval()));
                            else if (w < 10)
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(3).getDictval()));
                            else
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(4).getDictval()));
                        }
                    }

                }
            }
        }
    }


    private void getBuBaoHe() {
        RxRetrofitClient.getInstance(this).getfeedictlist("401", new Observer<StarLeveMoney>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushByDinActivity.this, "请检查网络是否正常");
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
                    pushNormlBean.setBubaohe(Integer.parseInt(buboahe.getData().get(0).getDictval()));
                }

            }
        });
    }

    private void getStarLeveMoney() {
        RxRetrofitClient.getInstance(this).getfeedictlist("301", new Observer<StarLeveMoney>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushByDinActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(StarLeveMoney starLeveMoney) {
                if (starLeveMoney.getCode() == 1) {
                    starLeveMoneys = starLeveMoney;
                    unitPrice = ptc * Integer.parseInt(starLeveMoneys.getData().get(starLeve - 1).getDictval());
                    reqMoneyVTv.setText("￥" + String.valueOf(df.format(unitPrice)));
                    pushNormlBean.setsPrice(unitPrice);
                }

            }
        });
    }

}
