package com.gohoc.xiupuling.ui.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.OrderDetailBean;
import com.gohoc.xiupuling.bean.OrderMoneyListBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class OrderDetailsMoneyDt extends BasicActivity {

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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.money_v_tv)
    TextView moneyVTv;
    private OrderDetailBean orderDetailBean;
    private MyAdapter adapter;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_money_dt);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("费用详情");
        orderDetailBean = (OrderDetailBean) getIntent().getExtras().get("orderDetailBeans");
        id = getIntent().getStringExtra("id");
        initDates();
    }

    private void initDates() {
        LogUtil.d("详情名称："+orderDetailBean.getData().getActivity_title());
        titleTv.setText(orderDetailBean.getData().getActivity_title() + "");
        ratingbar.setStar(orderDetailBean.getData().getStar_level());
        ratingbar.setmClickable(false);
        ratingbar.setEnabled(false);
        getorderFeeDetail(orderDetailBean.getData().getRange_id(), id);
    }

    private void initList(OrderMoneyListBean orderMoneyListBean) {
        int totalMoney = 0;
        int totaGet = 0;
        for (int a = 0; a < orderMoneyListBean.getData().size(); a++) {
            totalMoney += orderMoneyListBean.getData().get(a).getTrans_amount();
            if (orderMoneyListBean.getData().get(a).getOrder_status()==2)
                totaGet += orderMoneyListBean.getData().get(a).getTrans_amount();

        }

        moneyVTv.setText("￥" + totaGet + "元");
        moneyDtTv.setText("￥" + totalMoney + "元");
        adapter = new MyAdapter(R.layout.item_or_dt_unio, orderMoneyListBean.getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getorderFeeDetail(String range_id, String terminal_id) {
        RxRetrofitClient.getInstance(this).orderFeeDetail(range_id, terminal_id, new Observer<OrderMoneyListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderDetailsMoneyDt.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(OrderMoneyListBean orderMoneyListBean) {
                if (orderMoneyListBean.getCode() == 1 && orderMoneyListBean.getData() != null)
                    initList(orderMoneyListBean);
            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    class MyAdapter extends BaseQuickAdapter<OrderMoneyListBean.DataBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, List<OrderMoneyListBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, OrderMoneyListBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.start_time_tv, Utils.delTime(dataBean.getStart_date()))
                    .setText(R.id.end_time_tv, Utils.delTime(dataBean.getEnd_date()))
                    .setText(R.id.time_money, "￥" + dataBean.getTrans_amount() + "元");
            ImageView iv = baseViewHolder.getView(R.id.state_iv);
            iv.setImageResource(dataBean.getOrder_status() == 2 ? R.mipmap.icon_port_pen_1 : R.mipmap.icon_port_pen_2);

            ImageView midleIv = baseViewHolder.getView(R.id.midle_iv);
            midleIv.setImageResource(dataBean.getOrder_status() == 2 ? R.mipmap.icon_jiantou_blue_2 : R.mipmap.icon_daiqiangdan2);
            baseViewHolder.setTextColor(R.id.time_money,dataBean.getOrder_status() == 2 ? Color.parseColor("#27ABFD") : Color.parseColor("#929292"));
        }
    }
}
