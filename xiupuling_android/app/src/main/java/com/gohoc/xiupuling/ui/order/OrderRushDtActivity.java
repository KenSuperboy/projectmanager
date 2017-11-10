package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.OrderBean;
import com.gohoc.xiupuling.bean.OrderDetailBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class OrderRushDtActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.money_dt_tv)
    TextView moneyDtTv;
    @BindView(R.id.total_money_tv)
    TextView totalMoneyTv;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    OrderDetailBean orderDetailBeans;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MyAdapter adapter;
    private String id;
    private int total = 0;
    private OrderBean.DataBean orderBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rush_dt);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("待抢单");
        orderDetailBeans = (OrderDetailBean) getIntent().getExtras().get("orderDetailBeans");
        orderBeans = (OrderBean.DataBean) getIntent().getExtras().get("order");
        id = getIntent().getStringExtra("id");
        initDates();
    }

    private void initDates() {
        titleTv.setText(orderDetailBeans.getData().getWorkdetail().getWork_name() + "");
        ratingbar.setmClickable(false);
        ratingbar.setEnabled(false);
        ratingbar.setStar(orderDetailBeans.getData().getStar_level());
        moneyDtTv.setText("￥" + orderDetailBeans.getData().getTotalamt() + "元");
        initList(orderDetailBeans);

    }

    @OnClick({R.id.toolbar_left_title, R.id.button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.button_ll:
                receiveOrder(orderDetailBeans.getData().getRange_id(), id);
                break;
        }
    }

    private void receiveOrder(String range_id, String terminal_id) {
        StringBuffer sb = new StringBuffer();

        for (OrderDetailBean.DataBean.DatalistBean datalistBean : orderDetailBeans.getData().getDatalist()) {
            if (datalistBean.isCheck())
                sb.append(datalistBean.getRange_detail_id() + ",");
        }
        if (sb.length() < 1) {
            Utils.toast(this, "至少选择一项");
            return;
        }
        RxRetrofitClient.getInstance(this).receiveOrder(range_id, sb.toString() + "", terminal_id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderRushDtActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.OrderUpdateEvent());
                    startActivity(new Intent(OrderRushDtActivity.this, OrderRushSuccessActivity.class)
                            .putExtra("orderDetailBeans", orderDetailBeans)
                            .putExtra("order", orderBeans));
                } else
                    Utils.toast(OrderRushDtActivity.this, vCodeBenan.getMessage());
            }
        });
    }

    private void initList(OrderDetailBean orderDetailBean) {
        for (int a = 0; a < orderDetailBean.getData().getDatalist().size(); a++)
            if ( orderDetailBean.getData().getDatalist().get(a).isCheck())
                total += orderDetailBean.getData().getDatalist().get(a).getUnit_price();
        totalMoneyTv.setText("￥" + total);
        adapter = new MyAdapter(R.layout.item_or_dt_cf, orderDetailBean.getData().getDatalist());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (!orderDetailBeans.getData().getDatalist().get(i).isCheck()) {
                    if (total >= orderDetailBeans.getData().getTotalamt()) {
                        Utils.toast(OrderRushDtActivity.this, "您的订单金额不能超过" + orderDetailBeans.getData().getTotalamt() + "元");
                        return;
                    }
                    total += orderDetailBeans.getData().getDatalist().get(i).getUnit_price();
                } else {
                    total -= orderDetailBeans.getData().getDatalist().get(i).getUnit_price();
                }
                totalMoneyTv.setText("￥" + total);
                orderDetailBeans.getData().getDatalist().get(i).setCheck(!orderDetailBeans.getData().getDatalist().get(i).isCheck());
                adapter.notifyItemChanged(i);
                EventBus.getDefault().post(new  Event.PrderRushEvent(total,orderDetailBeans.getData().getDatalist()));
            }
        });
    }
    class MyAdapter extends BaseQuickAdapter<OrderDetailBean.DataBean.DatalistBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, List<OrderDetailBean.DataBean.DatalistBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, OrderDetailBean.DataBean.DatalistBean dataBean) {
            baseViewHolder.setText(R.id.start_time_tv, Utils.delTime(dataBean.getStart_date()))
                    .setText(R.id.end_time_tv, Utils.delTime(dataBean.getEnd_date()))
                    .setText(R.id.time_money, "￥" + dataBean.getUnit_price() + "元");
            ImageView iv = baseViewHolder.getView(R.id.state_iv);
            iv.setImageResource(dataBean.isCheck() ? R.mipmap.icon__zhuce_tongyi : R.mipmap.icon_zhuce_butongyi);
        }
    }

}
