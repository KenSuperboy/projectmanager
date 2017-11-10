package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushReqResultBean;
import com.gohoc.xiupuling.bean.PushResultMultiple;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.order.PushAddOrderConfirmActivity;
import com.gohoc.xiupuling.ui.order.PushOrderConfirmActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;

import com.gohoc.xiupuling.widget.RatingBar;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;

public class PushReqResultActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private String work_id;
    private String rq_id;
    private List<PushResultMultiple> dateList = new ArrayList<>();
    private MyAdater myAdater;
    private View notDataView;
    private PushNormlBean pushNormlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_result);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("投放详情");
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        work_id = getIntent().getStringExtra("work_id");
        rq_id = getIntent().getStringExtra("rq_id");
        workDistributeDetail(rq_id, work_id);
        initList(null);
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        finish();
    }

    private void workDistributeDetail(String rq_id, String work_id) {
        RxRetrofitClient.getInstance(this).workDistributeDetail(rq_id, work_id, new Observer<PushReqResultBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqResultActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushReqResultBean pushReqResultBean) {
                if (pushReqResultBean.getCode() == 1) {
                    //    if (null != pushReqResultBean.getDingtou())
                    getDates(pushReqResultBean);
                    myAdater.notifyDataSetChanged();
                }

            }
        });
    }

    private void initList(PushReqResultBean pushReqResultBean) {
        notDataView = getLayoutInflater().inflate(R.layout.no_content_img, (ViewGroup) list.getParent(), false);
        getDates(pushReqResultBean);
        myAdater = new MyAdater(dateList);
        list.setAdapter(myAdater);
        list.setLayoutManager(new LinearLayoutManager(this));
        myAdater.setEmptyView(notDataView);

    }

    private void getDates(PushReqResultBean pushReqResultBean) {
        if (pushReqResultBean == null)
            return;
        dateList.clear();
        if (pushReqResultBean.getDingtou() != null)
            dateList.add(new PushResultMultiple(pushReqResultBean.getDingtou(), PushResultMultiple.DING_TYPE));
        for (int a = 0; a < pushReqResultBean.getRange().size(); a++) {
            dateList.add(new PushResultMultiple(pushReqResultBean.getRange().get(a), PushResultMultiple.RANG_TYPE));
        }
        for (int a = 0; a < pushReqResultBean.getUnion().size(); a++) {
            dateList.add(new PushResultMultiple(pushReqResultBean.getUnion().get(a), PushResultMultiple.UNION_TYPE));
        }
    }


    class MyAdater extends BaseMultiItemQuickAdapter<PushResultMultiple, BaseViewHolder> {
        public MyAdater(List<PushResultMultiple> data) {
            super(data);
            addItemType(PushResultMultiple.DING_TYPE, R.layout.item_rang_r_dingtou);
            addItemType(PushResultMultiple.RANG_TYPE, R.layout.item_rang_r_fanwei);
            addItemType(PushResultMultiple.UNION_TYPE, R.layout.item_rang_r_group);
        }

        @Override
        protected void convert(final BaseViewHolder baseViewHolder, final PushResultMultiple pushResultMultiple) {
            final SwipeMenuLayout swipeMenuLayout = baseViewHolder.getView(R.id.swipeMenuLayout);
            String rand_id = "";
            switch (baseViewHolder.getItemViewType()) {
                case PushResultMultiple.DING_TYPE:
                    if (null == pushResultMultiple.getDingtouBean())
                        return;
                    baseViewHolder.setText(R.id.push_count_tv, "定投(" + pushResultMultiple.getDingtouBean().getRangecnt() + "次)￥" + pushResultMultiple.getDingtouBean().getTrade_amount())
                            .setText(R.id.push_od_count_tv, "目前" + pushResultMultiple.getDingtouBean().getTerminal_cnt() + "个终端接单")
                            .setText(R.id.push_od_time_tv, "平均接单跨" + pushResultMultiple.getDingtouBean().getWeek_per_term() + "周")
                            .setText(R.id.push_money, "￥" + pushResultMultiple.getDingtouBean().getFund_pool_amount());
                    // rand_id=pushResultMultiple.getDingtouBean().getRangecnt()
                    break;
                case PushResultMultiple.RANG_TYPE:
                    String startTime = Utils.formatDate(Utils.StrToDate(pushResultMultiple.getRangeBean().getStartdate(), "yyyyMMdd"), "yyyy.MM.dd");
                    String endTime = Utils.formatDate(Utils.StrToDate(pushResultMultiple.getRangeBean().getEnddate(), "yyyyMMdd"), "yyyy.MM.dd");

                    baseViewHolder.setText(R.id.push_count_tv, "范围悬赏￥" + pushResultMultiple.getRangeBean().getTotal_amount())
                            .setText(R.id.location_tv, pushResultMultiple.getRangeBean().getArea_name())
                            .setText(R.id.type_tv, pushResultMultiple.getRangeBean().getBusiness_name())
                            .setText(R.id.time_tv, startTime + "~" + endTime)
                            .setText(R.id.push_od_count_tv, "目前" + pushResultMultiple.getRangeBean().getTerminal_cnt() + "个终端接单")
                            .setText(R.id.push_od_time_tv, "平均接单跨" + pushResultMultiple.getRangeBean().getWeek_per_term() + "周")
                            .setText(R.id.push_money, "￥" + pushResultMultiple.getRangeBean().getFund_pool_amount());

                    RatingBar rb = baseViewHolder.getView(R.id.ratingbar);
                    rb.setStar(pushResultMultiple.getRangeBean().getStar_level());
                    rb.setEnabled(false);
                    rb.setmClickable(false);
                    rand_id = pushResultMultiple.getRangeBean().getRange_id();
                    break;
                case PushResultMultiple.UNION_TYPE:
                    String startTime1 = Utils.formatDate(Utils.StrToDate(pushResultMultiple.getUnionBean().getStartdate(), "yyyyMMdd"), "yyyy.MM.dd");
                    String endTime1 = Utils.formatDate(Utils.StrToDate(pushResultMultiple.getUnionBean().getEnddate(), "yyyyMMdd"), "yyyy.MM.dd");

                    baseViewHolder.setText(R.id.push_count_tv, "群内定投￥" + pushResultMultiple.getUnionBean().getTotal_amount())
                            .setText(R.id.type_tv, pushResultMultiple.getUnionBean().getUnion_name())
                            .setText(R.id.time_tv, startTime1 + "~" + endTime1)
                            .setText(R.id.push_od_count_tv, "目前" + pushResultMultiple.getUnionBean().getTerminal_cnt() + "个终端接单")
                            .setText(R.id.push_od_time_tv, "平均接单跨" + pushResultMultiple.getUnionBean().getWeek_per_term() + "周")
                            .setText(R.id.push_money, "￥" + pushResultMultiple.getUnionBean().getFund_pool_amount());

                    RatingBar rb1 = baseViewHolder.getView(R.id.ratingbar);
                    rb1.setStar(pushResultMultiple.getUnionBean().getStar_level());
                    rb1.setEnabled(false);
                    rb1.setmClickable(false);
                    rand_id = pushResultMultiple.getUnionBean().getRange_id();
                    break;
            }


            //作品截止投放
            final String finalRand_id = rand_id;
            baseViewHolder.getView(R.id.closedViews).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeMenuLayout.quickClose();


                    BGAAlertController alertController = new BGAAlertController(PushReqResultActivity.this, "","截止投放后，您剩余金额将返还您的钱包" , BGAAlertController.AlertControllerStyle.ActionSheet);
                    // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示

                    alertController.addAction(new BGAAlertAction("截止投放", BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
                        @Override
                        public void onClick() {
                            if (!TextUtils.isEmpty(finalRand_id))
                                finishRangeMarket(finalRand_id);
                            else
                                finishWorkRangeMarket(work_id);
                        }
                    }));
                    alertController.addAction(new BGAAlertAction("继续投放", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
                        @Override
                        public void onClick() {

                        }
                    }));
                    alertController.setCancelable(true);
                    alertController.show();

                }
            });
            //追加投放
            baseViewHolder.getView(R.id.addViews).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeMenuLayout.smoothClose();
                    startActivity(new Intent(PushReqResultActivity.this, PushAddOrderConfirmActivity.class)
                            .putExtra("pushNormlBean", pushNormlBean)
                            .putExtra("pushResultMultiple", pushResultMultiple)
                    );

                }
            });


        }
    }

    private void finishRangeMarket(final String range_id) {

        RxRetrofitClient.getInstance(PushReqResultActivity.this).finishRangeMarket(range_id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqResultActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(PushReqResultActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    workDistributeDetail(rq_id, work_id);
                }

            }
        });
    }

    private void finishWorkRangeMarket(final String work_id) {

        RxRetrofitClient.getInstance(PushReqResultActivity.this).finishWorkRangeMarket(work_id, "dingtou", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
                closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqResultActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(PushReqResultActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    workDistributeDetail(rq_id, work_id);
                }

            }
        });
    }
}
