package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.HistoryOrderListAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.OrderHistoryBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 历史承接广告
* */
public class OrderHistoryActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private HistoryOrderListAdater adater;
    private int type;//0历史承接广告   1：当前承接广告  3：
    private OrderHistoryBean orderHistoryBean=new OrderHistoryBean();
    private int pageNumber=1,pageSize=10;
    private boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        initList();
        initData();
    }

    private void initData() {

        if (mPullLoadMoreRecyclerView != null) {
            mPullLoadMoreRecyclerView.setRefreshing(true);
        }

        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            toolbarTitle.setText("历史承接广告");
            historyOrderList();
        } else {
            toolbarTitle.setText("当前承接广告");
            currentOrderList();
        }
    }

    private void historyOrderList() {
        RxRetrofitClient.getInstance(this).historyOrderList(pageNumber+"",pageSize+"",new Observer<OrderHistoryBean>() {
            @Override
            public void onCompleted() {
                loadComplete();
            }

            @Override
            public void onError(Throwable e) {
                loadComplete();
                Utils.toast(OrderHistoryActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(OrderHistoryBean orderHistoryBean) {
                loadComplete();
                if (orderHistoryBean.getCode() == 1) {
                    if (orderHistoryBean.getData().size() > 0) {
                        noDataLv.setVisibility(View.GONE);
                        setData(orderHistoryBean);
                        if (mPullLoadMoreRecyclerView != null) {
                            mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
                        }
                    } else{
                        if(pageNumber==1){
                            noDataLv.setVisibility(View.VISIBLE);
                        }else {
                            if (mPullLoadMoreRecyclerView != null) {
                                mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
                            }
                        }
                    }
                }else {
                    if (mPullLoadMoreRecyclerView != null) {
                        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
                    }
                }
            }
        });
    }

    private void loadComplete()
    {
        if (mPullLoadMoreRecyclerView != null) {
            mPullLoadMoreRecyclerView.setRefreshing(false);
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        }
    }

    private void setData(OrderHistoryBean myorderHistoryBean)
    {
        if(pageNumber==1){
            //刷新
            orderHistoryBean=myorderHistoryBean;
            adater.rf(orderHistoryBean);
        }else if(pageNumber>1){
            //加载更多
            if(orderHistoryBean!=null&&orderHistoryBean.getData()!=null&&orderHistoryBean.getData().size()>0){
                orderHistoryBean.getData().addAll(myorderHistoryBean.getData());
                adater.rf(orderHistoryBean);
            }else {
                Toast.makeText(mContext,"没有更多了",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void currentOrderList() {
        RxRetrofitClient.getInstance(this).currentOrderList(pageNumber+"",pageSize+"",new Observer<OrderHistoryBean>() {
            @Override
            public void onCompleted() {
                loadComplete();
            }

            @Override
            public void onError(Throwable e) {
                loadComplete();
                Utils.toast(OrderHistoryActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(OrderHistoryBean orderHistoryBean) {
                loadComplete();
                if (orderHistoryBean.getCode() == 1) {
                    if (orderHistoryBean.getData().size() > 0) {
                        noDataLv.setVisibility(View.GONE);
                        setData(orderHistoryBean);
                        if (mPullLoadMoreRecyclerView != null) {
                            mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
                        }
                    } else{
                        if(pageNumber==1){
                            noDataLv.setVisibility(View.VISIBLE);
                        }else {
                            if (mPullLoadMoreRecyclerView != null) {
                                mPullLoadMoreRecyclerView.setRefreshing(false);
                            }
                        }
                    }
                }else {
                    if (mPullLoadMoreRecyclerView != null) {
                        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
                    }
                }
            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    private void initList() {
        adater = new HistoryOrderListAdater(this, orderHistoryBean);
        mPullLoadMoreRecyclerView.setAdapter(adater);
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        //添加分割线
        //   list.addItemDecoration(new DividerItemDecoration(
        //          this, DividerItemDecoration.VERTICAL_LIST));
        adater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(OrderHistoryActivity.this, OrderDetailsActivity.class)
                        .putExtra("range_id", orderHistoryBean.getData().get(position).getRange_id())
                        .putExtra("terminal_id", orderHistoryBean.getData().get(position).getTerminal_id())
                        .putExtra("url", orderHistoryBean.getData().get(position).getMaterial_store_url())
                        .putExtra("start", "")
                        .putExtra("end", "")

                );
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                LogUtil.d("数据刷新=======");
                isRefresh = true;
                //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
                pageNumber=1;
                initData();
            }

            @Override
            public void onLoadMore() {
                LogUtil.d("加载更多=======");
                isRefresh = false;
                pageNumber++;
                initData();
            }
        });
    }
}
