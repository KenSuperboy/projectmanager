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
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.bean.PushGroupMenuBean;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushResultBean;
import com.gohoc.xiupuling.bean.StarLeveMoney;
import com.gohoc.xiupuling.bean.TerminalListBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.linkpackage.LiandongbaoSucceedActivity;
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

public class PushByGroupActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.req_cover_iv)
    ImageView reqCoverIv;
    @BindView(R.id.req_activity_tv)
    TextView reqActivityTv;
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
    @BindView(R.id.push_group_ll)
    LinearLayout pushGroupLl;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.req_money_v_tv)
    TextView reqMoneyVTv;
    @BindView(R.id.dt_tv)
    TextView dtTv;
    @BindView(R.id.terminal_count_tv)
    TextView terminalCountTv;
    private static int TIME_REQUEST_RESULT = 1000;
    private static int GROUP_REQUEST_RESULT = 1001;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.no_free_ll)
    RelativeLayout noFreeLl;
    @BindView(R.id.free_ll)
    RelativeLayout freeLl;
    @BindView(R.id.tips_tv)
    TextView tipsTv;
    private StarLeveMoney starLeveMoneys;
    private int ptc;      //时长系数
    private int starLeve = 1;//星级
    private PushNormlBean pushNormlBean;   //作品详情
    private SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays;//选择的时间范围
    private PushGroupMenuBean pushGroupMenuBean;   //选中的终端
    private DecimalFormat df = new DecimalFormat("#.00");
    //单价：即单屏单周播放的费用；
    //单价 = 作品所占的格子数 * 星级价格
    private int unitPrice = 0;//单价name_tv
    private StarLeveMoney buboahe;//不饱和系数表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_by_group);
        ButterKnife.bind(this);
        toolbarTitle.setText("群组内定投");
        setStatusColor(R.color.colorPrimary);
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        initDatas();
    }

    private void initDatas() {
        pushGroupMenuBean = new PushGroupMenuBean();
        ptc = pushNormlBean.getPlaytime() / 30;//时长系数。。
        if (ptc == 0 || pushNormlBean.getPlaytime() % 30 != 0)
            ptc++;
        pushNormlBean.setPtc(ptc);
        getStarLeveMoney();
        getBuBaoHe();
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
                pushNormlBean.setsPrice(unitPrice);
                reqMoneyVTv.setText("￥" + String.valueOf(df.format(unitPrice)));
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
            reqActivityTv.setText(pushNormlBean.getName() + "");
            reqTimeVTv.setText(pushNormlBean.getPlaytime() + "s");
            startPVTv.setText(TimeUtil.getCurrentTime("yyyy-MM-dd") + " " + Utils.getWeekOfDate(TimeUtil.getCurrentTime("yyyy-MM-dd"), "yyyy-MM-dd"));

            String endTime = TimeUtil.timeAddSubtract(TimeUtil.getCurrentTime("yyyy-MM-dd"), 7 - TimeUtil.getDayOfWeek());
            endPVTv.setText(endTime + " " + Utils.getWeekOfDate(endTime, "yyyy-MM-dd"));
            selectedDays = new SimpleMonthAdapter.SelectedDays<>();
            selectedDays.setFirst(new SimpleMonthAdapter.CalendarDay(System.currentTimeMillis()));
            Logger.e(Utils.StrToDay(endTime) + "");
            selectedDays.setLast(new SimpleMonthAdapter.CalendarDay(TimeUtil.getYear(), Utils.StrToMonth(endTime), Utils.StrToDay(endTime)));
            pushNormlBean.setWeekCount(1);

            if(pushNormlBean.isFree()){
                //免费
                tipsTv.setText("所有作品投放到自己的终端上显示");
                freeLl.setVisibility(View.VISIBLE);
                noFreeLl.setVisibility(View.GONE);
            }else {
                //不免费
            }
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.req_time_dt_rl, R.id.push_group_ll, R.id.dt_tv, R.id.arr_right_iv2, R.id.button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.req_time_dt_rl:
                startActivityForResult(new Intent(PushByGroupActivity.this, PushReqSelectTimeActivity.class).putExtra("selectedDays", selectedDays), TIME_REQUEST_RESULT);
                break;
            case R.id.push_group_ll:
                if(pushNormlBean.isFree()){//说明是组合包进来
                    startActivityForResult(new Intent(PushByGroupActivity.this, PushReqSelectGroupActivity.class).putExtra("orientation", pushNormlBean.getOrientation()).putExtra("combination",true).putExtra("pushGroupMenuBean", pushGroupMenuBean), GROUP_REQUEST_RESULT);
                }else {
                    startActivityForResult(new Intent(PushByGroupActivity.this, PushReqSelectGroupActivity.class).putExtra("orientation", pushNormlBean.getOrientation()).putExtra("combination",false).putExtra("pushGroupMenuBean", pushGroupMenuBean), GROUP_REQUEST_RESULT);
                }
                break;
            case R.id.dt_tv:
            case R.id.arr_right_iv2:
                startActivity(new Intent(PushByGroupActivity.this, PushReqChargesDtActivity.class)
                        .putExtra("leveMoney", Integer.parseInt(starLeveMoneys.getData().get(starLeve - 1).getDictval()))
                        .putExtra("times", pushNormlBean.getPlaytime())
                        .putExtra("ptc", ptc)
                        .putExtra("leve", starLeve)
                );
                break;
            case R.id.button_ll:
                switch (pushGroupMenuBean.getTyepe()) {// 0群id (全部（横竖屏要对应）) 1店铺id   2群组内的终端  3店铺内终端    15    16
                    case 0:
                        if (pushGroupMenuBean.getUnion() == null) {
                            Utils.toast(PushByGroupActivity.this, "请选择需要投放的群组");
                            return;
                        }
                        rangeMarket(pushNormlBean.getRq_id(), pushNormlBean.getWork_id(), "1", Utils.formatDate(selectedDays.getFirst().getDate()
                                , "yyyyMMdd"), Utils.formatDate(selectedDays.getLast().getDate(), "yyyyMMdd"), null, null, null, null, null, null, null, null, null
                                , pushGroupMenuBean.getTyepe() == 0 ? "1" : "2", pushGroupMenuBean.getUnion().getUnion_id(), null, null, null, starLeve + "", null, null, null, null);
                        break;
                    case 1:
                        if (pushGroupMenuBean.getShop() == null) {
                            Utils.toast(PushByGroupActivity.this, "请选择需要投放的门店");
                            return;
                        }
                        rangeMarket(pushNormlBean.getRq_id(), pushNormlBean.getWork_id(), "3", Utils.formatDate(selectedDays.getFirst().getDate()
                                , "yyyyMMdd"), Utils.formatDate(selectedDays.getLast().getDate(), "yyyyMMdd"), null, null, null, null, null, null, null, null, null
                                , null, null, "1", pushGroupMenuBean.getShop().getShop_id(), null, starLeve + "", null, null, null, null);
                        break;
                    case 2:
                        StringBuffer sb = new StringBuffer();
                        for (GroupTermianlListBean.DataBean dataBean : pushGroupMenuBean.getGroupTerminalList())
                            sb.append(dataBean.getTerminal_id() + ",");

                        rangeMarket(pushNormlBean.getRq_id(), pushNormlBean.getWork_id(), "1", Utils.formatDate(selectedDays.getFirst().getDate()
                                , "yyyyMMdd"), Utils.formatDate(selectedDays.getLast().getDate(), "yyyyMMdd"), null, null, null, null, null, null, null, null, null
                                , "3", pushGroupMenuBean.getUnion().getUnion_id(), null, null, sb.toString(), starLeve + "", null, null, null, null);
                        break;
                    case 3:
                        StringBuffer sb2 = new StringBuffer();
                        for (TerminalListBean.DataBean dataBean : pushGroupMenuBean.getShopTerminalList())
                            sb2.append(dataBean.getTerminal_id() + ",");

                        rangeMarket(pushNormlBean.getRq_id(), pushNormlBean.getWork_id(), "3", Utils.formatDate(selectedDays.getFirst().getDate()
                                , "yyyyMMdd"), Utils.formatDate(selectedDays.getLast().getDate(), "yyyyMMdd"), null, null, null, null, null, null, null, null, null
                                , null, null, "2", pushGroupMenuBean.getShopId(), sb2.toString(), starLeve + "", null, null, null, null);
                        break;
                }
                break;
        }
    }

    private void getStarLeveMoney() {
        RxRetrofitClient.getInstance(this).getfeedictlist("301", new Observer<StarLeveMoney>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushByGroupActivity.this, "请检查网络是否正常");
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
                    pushNormlBean.setsPrice(unitPrice);
                    reqMoneyVTv.setText("￥" + String.valueOf(df.format(unitPrice)));
                }
            }
        });
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
                            Utils.toast(PushByGroupActivity.this, "请检查网络是否正常");
                            try {
                                throw e;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(PushResultBean pushResultBean) {

                            if (pushResultBean.getCode() == 1) {
                                if (pushGroupMenuBean.isFree()){
                                    if(pushNormlBean.getFlag()==1){
                                        Intent intent=new Intent(PushByGroupActivity.this,LiandongbaoSucceedActivity.class);
                                        intent.putExtra("type","0");
                                        intent.putExtra("name",reqActivityTv.getText().toString());
                                        startActivity(intent);
                                    }else {
                                        startActivity(new Intent(PushByGroupActivity.this, PushOrderPayReslutActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                                    }
                                }else{
                                    startActivity(new Intent(PushByGroupActivity.this, PushOrderConfirmActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                                }
                                } else
                                Utils.toast(PushByGroupActivity.this, pushResultBean.getMessage());

                        }
                    });
        }else {
            //组合包投放  union_type:1自己的终端
            RxRetrofitClient.getInstance(this).packageRangeMarket(rq_id,  range_type, startdate, enddate, addr_type
                    , region_id, group_type, group_id_a, group_id_b, group_id_c, bussiness_id_a, bussiness_id_b, bussiness_id_c
                    , "1", union_id, shop_type, shop_id, terminal_id, star_level, address, addr_longitude, addr_latitude
                    , distance, new Observer<PushResultBean>() {
                        @Override
                        public void onCompleted() {
                            closeProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            closeProgressDialog();
                            Utils.toast(PushByGroupActivity.this, "请检查网络是否正常");
                            try {
                                throw e;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(PushResultBean pushResultBean) {

                            if (pushResultBean.getCode() == 1) {
                                if (pushGroupMenuBean.isFree())
                                    startActivity(new Intent(PushByGroupActivity.this, PushOrderPayReslutActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                                else
                                    startActivity(new Intent(PushByGroupActivity.this, PushOrderConfirmActivity.class).putExtra("pushResultBean", pushResultBean).putExtra("pushNormlBean", pushNormlBean));
                            } else
                                Utils.toast(PushByGroupActivity.this, pushResultBean.getMessage());

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
            } else if (requestCode == GROUP_REQUEST_RESULT) {
                Bundle d = data.getExtras();
                if (null != d) {
                    PushGroupMenuBean pushGroupMenuBeans = (PushGroupMenuBean) d.get("pushGroupMenuBean");
                    if (pushGroupMenuBeans != null) {
                        pushGroupMenuBean = pushGroupMenuBeans;
                        switch (pushGroupMenuBeans.getTyepe()) {// 0群id  1店铺id   2群组内的终端  3店铺内终端
                            case 0:
                                //判断是否收费
                                if (pushGroupMenuBean.getUnion().getUnion_type() == 1) {
                                    pushGroupMenuBean.setFree(true);
                                    tipsTv.setText("将作品投放到私有群组内的终端上显示");
                                    freeLl.setVisibility(View.VISIBLE);
                                    noFreeLl.setVisibility(View.GONE);
                                    pushGroupMenuBean.setFree(true);
                                } else if (pushGroupMenuBean.getUnion().getUnion_type() == 2) {
                                    pushGroupMenuBean.setFree(true);
                                    tipsTv.setText("将作品投放到连锁群组内的终端上显示");
                                    freeLl.setVisibility(View.VISIBLE);
                                    noFreeLl.setVisibility(View.GONE);
                                    pushGroupMenuBean.setFree(true);
                                } else {
                                    freeLl.setVisibility(View.GONE);
                                    noFreeLl.setVisibility(View.VISIBLE);
                                    pushGroupMenuBean.setFree(false);
                                }
                                //  pushGroupMenuBean.getUnion().get
                                nameTv.setText(pushGroupMenuBean.getUnion().getUnion_name() + " " + (pushGroupMenuBeans.getGroupTermianlType() == 0 ? "所有别人的终端" : "所有我的终端"));
                                //涉及到的终端
                                terminalCountTv.setText(String.valueOf(pushGroupMenuBean.getTerminalCount()));
                                break;
                            case 1:
                                //是否收费
                                pushGroupMenuBean.setFree(true);
                                tipsTv.setText("将作品投放到自己的店铺上显示");
                                freeLl.setVisibility(View.VISIBLE);
                                noFreeLl.setVisibility(View.GONE);
                                nameTv.setText(pushGroupMenuBean.getShop().getShop_name());
                                //涉及到的终端
                                terminalCountTv.setText(String.valueOf(pushGroupMenuBean.getTerminalCount()));
                                break;
                            case 2:
                                if (pushGroupMenuBean.getGroupTerminalList().size() < 1)
                                    return;
                                if (pushGroupMenuBean.getUnion().getUnion_type() == 1) {
                                    pushGroupMenuBean.setFree(true);
                                    tipsTv.setText("将作品投放到私有群组内的终端上显示");
                                    freeLl.setVisibility(View.VISIBLE);
                                    noFreeLl.setVisibility(View.GONE);
                                    pushGroupMenuBean.setFree(true);
                                } else if (pushGroupMenuBean.getUnion().getUnion_type() == 2) {
                                    pushGroupMenuBean.setFree(true);
                                    tipsTv.setText("将作品投放到连锁群组内的终端上显示");
                                    freeLl.setVisibility(View.VISIBLE);
                                    noFreeLl.setVisibility(View.GONE);
                                    pushGroupMenuBean.setFree(true);
                                } else {
                                    freeLl.setVisibility(View.GONE);
                                    noFreeLl.setVisibility(View.VISIBLE);
                                    pushGroupMenuBean.setFree(false);
                                }

                                StringBuffer sb = new StringBuffer();
                                for (GroupTermianlListBean.DataBean dataBean : pushGroupMenuBean.getGroupTerminalList()) {
                                    sb.append(dataBean.getTerminal_no() + " " +dataBean.getShop_name() + " ");
                                }
                                nameTv.setText(sb.toString());
                                //涉及到的终端
                                terminalCountTv.setText(String.valueOf(pushGroupMenuBean.getGroupTerminalList().size()));
                                break;
                            case 3:
                                if (pushGroupMenuBean.getShopTerminalList().size() < 1)
                                    return;
                                StringBuffer sbs = new StringBuffer();
                                for (TerminalListBean.DataBean dataBean : pushGroupMenuBean.getShopTerminalList()) {
                                    sbs.append(dataBean.getTerminal_no() + " " + pushGroupMenuBean.getShopName());
                                }
                                pushGroupMenuBean.setFree(true);
                                tipsTv.setText("将作品投放到自己的店铺上显示");
                                freeLl.setVisibility(View.VISIBLE);
                                noFreeLl.setVisibility(View.GONE);
                                nameTv.setText(sbs.toString());
                                //涉及到的终端
                                terminalCountTv.setText(String.valueOf(pushGroupMenuBean.getShopTerminalList().size()));
                                break;
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
                Utils.toast(PushByGroupActivity.this, "请检查网络是否正常");
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

}
