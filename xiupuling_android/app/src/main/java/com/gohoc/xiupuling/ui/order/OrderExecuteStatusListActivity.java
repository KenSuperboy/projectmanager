package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.OrderSBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class OrderExecuteStatusListActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.date_cont_ll)
    LinearLayout dateContLl;
    @BindView(R.id.list)
    RecyclerView list;
    private String terminal_id;
    private MyAdapter adapter;
    private OrderSBean orderSBeans;
    private static int UPDAE_REQUEST_RESULT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_execute_status_list);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);

        terminal_id = getIntent().getStringExtra("terminal_id");
        toolbarTitle.setText("订单执行情况");
        orderExecuteStatusList(terminal_id);

    }

    private void orderExecuteStatusList(String terminal_id) {
        RxRetrofitClient.getInstance(this).orderExecuteStatusList(terminal_id, new Observer<OrderSBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderExecuteStatusListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(OrderSBean orderSBean) {
                if (orderSBean.getCode() == 1 && orderSBean.getData().size() > 0) {
                    orderSBeans = orderSBean;
                    dateContLl.setVisibility(View.VISIBLE);
                    noDataLv.setVisibility(View.GONE);
                    initList(orderSBean);
                } else {
                    dateContLl.setVisibility(View.GONE);
                    noDataLv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initList(final OrderSBean orderSBean) {
        adapter = new MyAdapter(R.layout.item_order2, orderSBean.getData());
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                startActivityForResult(new Intent(OrderExecuteStatusListActivity.this, OrderDetailsActivity.class)
                        .putExtra("range_id", orderSBean.getData().get(position).getRange_id())
                        .putExtra("terminal_id", orderSBean.getData().get(position).getTerminal_id())
                        .putExtra("url", orderSBean.getData().get(position).getMaterial_store_url())
                        .putExtra("type",1)
                        .putExtra("start",orderSBean.getData().get(position).getStart_time())
                        .putExtra("end",orderSBean.getData().get(position).getEnd_time())
                        .putExtra("limit",orderSBean.getData().get(position).getIs_time_limit())
                        .putExtra("ignore",orderSBean.getData().get(position).getIs_ignore_other_work())
                        .putExtra("repeat",orderSBean.getData().get(position).getRepeat_weekday())
                        .putExtra("order",orderSBean.getData().get(position).getOrder_source())
                        ,UPDAE_REQUEST_RESULT
                );
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.no_data_lv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.no_data_lv:
                break;
        }
    }

    class MyAdapter extends BaseQuickAdapter<OrderSBean.DataBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, List<OrderSBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, OrderSBean.DataBean dataBean) {

            baseViewHolder.setText(R.id.termianl_title_tv, dataBean.getTerminal_no() + " " + dataBean.getShop_name())
                    .setText(R.id.v_title_tv, dataBean.getWork_name() + "")
                    .setText(R.id.v_time_tv, "接单于 " + Utils.formatDate(Utils.StrToDate(dataBean.getCreate_time(), "yyyy-MM-ddHH:mm:ss"), "yyyy-MM-dd"))
                    .setText(R.id.v_money_tv, "￥" + dataBean.getTotalamount()+"元");

            RatingBar ratingbar = baseViewHolder.getView(R.id.ratingbar);
            ratingbar.setmClickable(false);
            ratingbar.setEnabled(false);
            ratingbar.setStar(dataBean.getStar_level());

            ImageView iv = baseViewHolder.getView(R.id.works_iv);
            Glide.with(OrderExecuteStatusListActivity.this)
                    .load(NetConstant.BASE_USER_RESOURE + dataBean.getMaterial_store_url())
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);

            //修改增加
            ImageView iv_type = baseViewHolder.getView(R.id.iv_type);
            TextView tv_type = baseViewHolder.getView(R.id.tv_type);
            ImageView iv_down_status = baseViewHolder.getView(R.id.iv_down_status);
            LogUtil.d("标示："+dataBean.getOrder_source());
            TextView tv_leader = baseViewHolder.getView(R.id.tv_leader);


            if(dataBean.getOrder_source()!=null&&dataBean.getOrder_source().equals("link")){
                if(dataBean.getIs_leader()==0){
                    tv_leader.setText("跟播");
                }else if(dataBean.getIs_leader()==1){
                    tv_leader.setText("领播");
                }else {
                    tv_leader.setText("");
                }
            }else {
                tv_leader.setText("");
            }

            String playType="";
            if(dataBean.getOrder_source()!=null){
                if(dataBean.getOrder_source().equals("package")){
                    iv_type.setImageResource(R.mipmap.icon_dingdan_zuhebao);
                    playType="播放";
                }else if(dataBean.getOrder_source().equals("link")){
                    iv_type.setImageResource(R.mipmap.icon_dingdan_liandong);
                    playType="播放";
                }else{
                    iv_type.setImageResource(R.mipmap.icon_dan);
                    playType="轮播";
                }
            }else {
                playType="轮播";
            }

            if(dataBean.getDownload_status()==1){
                iv_down_status.setImageResource(R.mipmap.icon_wancheng);
            }else {
                iv_down_status.setImageResource(R.mipmap.icon_xiazaizhong);
            }
            if(!dataBean.getIs_time_limit()&&dataBean.getIs_ignore_other_work()){
                tv_type.setText("全天候独占"+playType);
            }else {
                if(!dataBean.getIs_time_limit()&&!dataBean.getIs_ignore_other_work()){
                    tv_type.setText("非独占时段"+playType);
                }else {
                    if(!TextUtils.isEmpty(dataBean.getRepeat_weekday())){
                        tv_type.setText(CustomUtils.getPlayTime(dataBean.getRepeat_weekday(),dataBean.getIs_ignore_other_work())+" "+CustomUtils.start_end(dataBean.getStart_time(),dataBean.getEnd_time()));
                    }else {
                        tv_type.setText("非独占时段"+playType);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            orderExecuteStatusList(terminal_id);
        }
    }
}
