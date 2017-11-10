package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.WorksDetails;
import com.gohoc.xiupuling.bean.BusinessBean;
import com.gohoc.xiupuling.bean.BusinessTypeBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushResultBean;
import com.gohoc.xiupuling.bean.StarLeveMoney;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.order.PushOrderConfirmActivity;
import com.gohoc.xiupuling.ui.terminal.SelectShopBusinessTypeActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.TimeUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushReqByMoneyActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.req_cover_iv)
    ImageView reqCoverIv;
    @BindView(R.id.req_activity_tv)
    TextView reqTitleTv;
    @BindView(R.id.req_time_v_tv)
    TextView reqTimeVTv;
    @BindView(R.id.start_p_v_tv)
    TextView startPVTv;
    @BindView(R.id.end_p_v_tv)
    TextView endPVTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.req_time_dt_rl)
    RelativeLayout reqTimeDtRl;
    @BindView(R.id.push_location_ll)
    LinearLayout pushLocationLl;
    @BindView(R.id.push_business_ll)
    LinearLayout pushBusinessLl;
    @BindView(R.id.req_leve_tv)
    TextView reqLeveTv;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.req_money_v_tv)
    TextView reqMoneyVTv;
    @BindView(R.id.terminal_count_tv)
    TextView terminalCountTv;
    private static int TIME_REQUEST_RESULT = 1000;
    private static int LOCATION_REQUEST_RESULT = 1001;
    private static int CHARGE_REQUEST_RESULT = 1002;
    private static int INDUSTRY_REQUEST_RESULT = 1003;
    @BindView(R.id.adress_tv)
    TextView adressTv;
    @BindView(R.id.industry_tv)
    TextView industryTv;
    //  private WorksDetails worksDetail;   //作品详情
    private SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays;//选择的时间范围
    private int addrType = 0;//0 全国 1 省 2 市 3区 4 商圈 5 范围
    private int typeOfLocation = 0;//地域范围 类型
    private int starLeve = 1;//星级
    private StarLeveMoney starLeveMoneys;//星级价格表
    private StarLeveMoney buboahe;//不饱和系数表
    //地域范围
    private MenuLocationBean menuLocationBean = new MenuLocationBean();
    private ArrayList<BusinessBean.DataBean> selectBussList; //选中的商圈
    private ArrayList<BusinessTypeBean.DataBean> selectBussTypeList; //选中的行业范围
    private int ptc; //时长系数
    private PushNormlBean pushNormlBean;
    private DecimalFormat df = new DecimalFormat("#.00");
    private int unitPrice = 0;//单价
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_by_money);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("悬赏发布");
        getBuBaoHe();
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        initDatas();
    }

    private void initDatas() {
        ptc = pushNormlBean.getPlaytime() / 30;//时长系数。。
        if (ptc == 0 || pushNormlBean.getPlaytime() % 30 != 0)
            ptc++;
        pushNormlBean.setPtc(ptc);
        getStarLeveMoney();

        ratingbar.setStar(starLeve);
        ratingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                if (RatingCount < 1) {
                    ratingbar.setStar(1);
                    starLeve = 1;
                } else
                    starLeve = (int) RatingCount;
                unitPrice = ptc * Integer.parseInt(starLeveMoneys.getData().get(starLeve - 1).getDictval());
                reqMoneyVTv.setText("￥" + String.valueOf(df.format(unitPrice)));
                pushNormlBean.setPtc(ptc);
                pushNormlBean.setsPrice(unitPrice);
                // pushNormlBean.setBubaohe(buboahe.getData().get());
                //  pushNormlBean.get
            }
        });
        if (null != pushNormlBean) {
            Glide.with(this)
                    .load(NetConstant.BASE_USER_RESOURE + pushNormlBean.getCover_url() + "")
                     .placeholder(R.mipmap.df_logo)
                     .error(R.mipmap.df_logo)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(reqCoverIv);
            reqTitleTv.setText(pushNormlBean.getName() + "");
            reqTimeVTv.setText(pushNormlBean.getPlaytime() + "s");
            startPVTv.setText(TimeUtil.getCurrentTime("yyyy-MM-dd") + " " + Utils.getWeekOfDate(TimeUtil.getCurrentTime("yyyy-MM-dd"), "yyyy-MM-dd"));

            String endTime = TimeUtil.timeAddSubtract(TimeUtil.getCurrentTime("yyyy-MM-dd"), 7 - TimeUtil.getDayOfWeek());
            endPVTv.setText(endTime + " " + Utils.getWeekOfDate(endTime, "yyyy-MM-dd"));
            selectedDays = new SimpleMonthAdapter.SelectedDays<>();
            selectedDays.setFirst(new SimpleMonthAdapter.CalendarDay(System.currentTimeMillis()));
            Logger.e(Utils.StrToDay(endTime) + "");
            selectedDays.setLast(new SimpleMonthAdapter.CalendarDay(TimeUtil.getYear(),Utils.StrToMonth(endTime), Utils.StrToDay(endTime)));
            pushNormlBean.setWeekCount(1);

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
                            int w= (int) Utils.countTwoDayWeek(selectedDay.getFirst().getDate(), selectedDay.getLast().getDate());
                            timeTv.setText("跨" +w + "周");
                            pushNormlBean.setWeekCount(w);
                            //选择不饱和系数
                            if(w==1)
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(0).getDictval()));
                            else if(w<4)
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(1).getDictval()));
                            else if(w<7)
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(2).getDictval()));
                            else if(w<10)
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(3).getDictval()));
                            else
                                pushNormlBean.setBubaohe(Double.parseDouble(buboahe.getData().get(4).getDictval()));

                        }
                    }

                }


            } else if (requestCode == LOCATION_REQUEST_RESULT) {
                //地域范围
                menuLocationBean = (MenuLocationBean) data.getExtras().get("menuLocationBean");
                selectBussList = (ArrayList<BusinessBean.DataBean>) data.getExtras().get("selectBussList");
                typeOfLocation = data.getIntExtra("type", -1);
                if (typeOfLocation == 0) {  //按地址或商圈
                    if (selectBussList == null || selectBussList.size() < 1)//未选择商圈的情况
                    {
                        if (menuLocationBean.getProvince() == null) {  //全国
                            addrType = 0;
                            adressTv.setText("全国");
                        } else if (menuLocationBean.getCity() == null) {//省
                            addrType = 1;
                            adressTv.setText(menuLocationBean.getProvince());
                        } else if (menuLocationBean.getDistrict() == null) //市
                        {
                            addrType = 2;
                            adressTv.setText(menuLocationBean.getProvince() + menuLocationBean.getCity());
                        } else {
                            addrType = 3;
                            adressTv.setText(menuLocationBean.getProvince() + menuLocationBean.getCity() + menuLocationBean.getDistrict());
                        }

                    } else {
                        //选择了商圈的情况
                        addrType = 4;
                        StringBuffer tips = new StringBuffer();
                        for (BusinessBean.DataBean d : selectBussList) {
                            tips.append(d.getGroup_name() + " ");
                        }
                        adressTv.setText(tips.toString().substring(0, tips.length() - 1));
                    }
                } else {
                    //选择范围
                    addrType = 5;
                    String distance = menuLocationBean.getRangeInfo().getDistance();
                    adressTv.setText("(" + Integer.parseInt(distance) / 1000f + "KM) " + menuLocationBean.getRangeInfo().getAdress());
                }

            } else if (requestCode == INDUSTRY_REQUEST_RESULT) {
                selectBussTypeList = (ArrayList<BusinessTypeBean.DataBean>) data.getExtras().get("selectBussTypeList");
                StringBuffer tips = new StringBuffer();
                for (BusinessTypeBean.DataBean d : selectBussTypeList) {
                    tips.append(d.getBusiness_name() + " ");
                }
                if (tips.length() > 0)
                    industryTv.setText(tips.toString().substring(0, tips.length() - 1));
                else {
                    selectBussTypeList = null;
                    industryTv.setText("");
                }

            } else if (requestCode == CHARGE_REQUEST_RESULT) {

            }
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.req_time_dt_rl, R.id.push_location_ll, R.id.push_business_ll, R.id.button_ll, R.id.dt_tv, R.id.arr_right_iv2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.req_time_dt_rl:
                startActivityForResult(new Intent(PushReqByMoneyActivity.this, PushReqSelectTimeActivity.class).putExtra("selectedDays", selectedDays), TIME_REQUEST_RESULT);
                break;
            case R.id.push_location_ll:
                startActivityForResult(new Intent(PushReqByMoneyActivity.this, PushRangeActivity.class).putExtra("type", typeOfLocation)
                                .putExtra("selectBussList", selectBussList)
                                .putExtra("menuLocationBean", menuLocationBean)
                        , LOCATION_REQUEST_RESULT);
                break;
            case R.id.push_business_ll:
                startActivityForResult(new Intent(PushReqByMoneyActivity.this, SelectShopBusinessTypeActivity.class).putExtra("push", "push"), INDUSTRY_REQUEST_RESULT);
                break;
            case R.id.button_ll:
                Logger.e(Utils.formatDate(selectedDays.getFirst().getDate(), "yyyyMMdd") + "  ");
                Logger.e(Utils.formatDate(selectedDays.getLast().getDate(), "yyyyMMdd") + "  ");

                if (typeOfLocation == 0)
                    rangeMarket(pushNormlBean.getRq_id(), pushNormlBean.getWork_id(), "0", Utils.formatDate(selectedDays.getFirst().getDate()
                            , "yyyyMMdd"), Utils.formatDate(selectedDays.getLast().getDate(), "yyyyMMdd"), addrType + "",
                            getPlaceId(), addrType == 4 ? "1" : "0",
                            (selectBussList != null && selectBussList.size() > 0) ? (selectBussList.get(0).getGroup_id() + "") : null,  //商圈1
                            (selectBussList != null && selectBussList.size() > 1) ? (selectBussList.get(1).getGroup_id() + "") : null, //商圈2
                            (selectBussList != null && selectBussList.size() > 2) ? (selectBussList.get(2).getGroup_id() + "") : null,//商圈3
                            (selectBussTypeList != null && selectBussTypeList.size() > 0) ? (selectBussTypeList.get(0).getBusiness_id() + "") : null,//行业1
                            (selectBussTypeList != null && selectBussTypeList.size() > 1) ? (selectBussTypeList.get(1).getBusiness_id() + "") : null,//行业2
                            (selectBussTypeList != null && selectBussTypeList.size() > 2) ? (selectBussTypeList.get(2).getBusiness_id() + "") : null,//行业3
                            null, null, null, null, null, starLeve + "", null, null, null, null);
                else //按地域范围
                    rangeMarket(pushNormlBean.getRq_id(), pushNormlBean.getWork_id(), "0", Utils.formatDate(selectedDays.getFirst().getDate()
                            , "yyyyMMdd"), Utils.formatDate(selectedDays.getLast().getDate(), "yyyyMMdd"), addrType + "",
                            getPlaceId(), addrType == 4 ? "1" : "0",
                            null, null, null,
                            (selectBussTypeList != null && selectBussTypeList.size() > 0) ? (selectBussTypeList.get(0).getBusiness_id() + "") : null,//行业1
                            (selectBussTypeList != null && selectBussTypeList.size() > 1) ? (selectBussTypeList.get(1).getBusiness_id() + "") : null,//行业2
                            (selectBussTypeList != null && selectBussTypeList.size() > 2) ? (selectBussTypeList.get(2).getBusiness_id() + "") : null,//行业3
                            null, null, null, null, null, starLeve + "",
                            menuLocationBean.getRangeInfo().getAdress(), //地址
                            menuLocationBean.getRangeInfo().getmLongitude(),//经度
                            menuLocationBean.getRangeInfo().getmLatitude(),//纬度
                            menuLocationBean.getRangeInfo().getDistance());//范围

                break;
            case R.id.dt_tv:
            case R.id.arr_right_iv2:
                startActivity(new Intent(PushReqByMoneyActivity.this, PushReqChargesDtActivity.class)
                        .putExtra("leveMoney", Integer.parseInt(starLeveMoneys.getData().get(starLeve - 1).getDictval()))
                        .putExtra("times", pushNormlBean.getPlaytime())
                        .putExtra("ptc", ptc)
                        .putExtra("leve", starLeve)
                );
                break;
        }
    }

    private String getPlaceId() {
        switch (addrType) {
            case 0:
                return null;
            case 1:
                return menuLocationBean.getProvinceId();
            case 2:
                return menuLocationBean.getCityId();
            case 3:
            case 4:
                return menuLocationBean.getDistrictId();
            case 5:
                return null;
        }
        return null;
    }


    private void getStarLeveMoney() {
        RxRetrofitClient.getInstance(this).getfeedictlist("301", new Observer<StarLeveMoney>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqByMoneyActivity.this, "请检查网络是否正常");
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
                    pushNormlBean.setPtc(ptc);
                    pushNormlBean.setsPrice(unitPrice);
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
                Utils.toast(PushReqByMoneyActivity.this, "请检查网络是否正常");
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
                    pushNormlBean.setBubaohe(1);
                }

            }
        });
    }


    private void rangeMarket(String rq_id, String work_id, String range_type, String startdate, String enddate, String addr_type
            , String region_id, String group_type, String group_id_a, String group_id_b, String group_id_c, String bussiness_id_a, String bussiness_id_b, String bussiness_id_c
            , String union_type, String union_id, String shop_type, String shop_id, String terminal_id, String star_level, String address, String addr_longitude, String addr_latitude
            , String distance) {

        if (selectBussTypeList == null || selectBussTypeList.size() < 1) {
            Utils.toast(this, "请选择行业");
            return;
        }

        showProgressDialog("正在提交.....");
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
                        Utils.toast(PushReqByMoneyActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(PushResultBean pushResultBean) {

                        if (pushResultBean.getCode() == 1) {
                            startActivity(new Intent(PushReqByMoneyActivity.this, PushOrderConfirmActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                        } else {
                            Utils.toast(PushReqByMoneyActivity.this, pushResultBean.getMessage());
                        }

                    }
                });
    }


}
