package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.WalletListBean;
import com.gohoc.xiupuling.bean.WalletListMultiItemBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class WalletListActivity extends BasicActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private MyAdapter adapter;
    private List<WalletListMultiItemBean> datalist = new ArrayList<>();
    private View emptyView;
    private HashMap<String, WalletListMultiItemBean> datalistMap = new HashMap<>();
    private int pageNumber = 1;
    private int pageSize = 10;
    private WalletListBean walletListBeanss;
    private boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_list);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        swipeLayout.setOnRefreshListener(this);
        toolbarTitle.setText("收支明细");
        emptyView = LayoutInflater.from(this).inflate(R.layout.no_content_txt, (ViewGroup) list.getParent(), false);
        TextView tv = (TextView) emptyView.findViewById(R.id.text);
        tv.setText("您还未有任何金钱的交易记录");
        tv.setTextColor(getResources().getColor(R.color.tips_font));
        getmyinoutlist(pageNumber + "", pageSize + "");
        initList();
        //getmyinoutstatistic();
    }


    private void getmyinoutlist(String pageNumbers, String pageSizes) {
        RxRetrofitClient.getInstance(this).getmyinoutlist(pageNumbers, pageSizes, new Observer<WalletListBean>() {
            @Override
            public void onCompleted() {
                if (isRefresh) {
                    if (swipeLayout != null)
                        swipeLayout.setRefreshing(false);
                } else {
                    adapter.loadMoreComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                adapter.loadMoreComplete();
                adapter.loadMoreEnd(false);
                Utils.toast(WalletListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(WalletListBean walletListBeans) {

                if (walletListBeans.getCode() == 1) {
                    if (isRefresh) {
                        walletListBeanss = walletListBeans;
                    } else {
                        walletListBeanss.getData().getList().addAll(walletListBeans.getData().getList());
                        if (walletListBeans.getData().isLastPage()) {
                            adapter.loadMoreEnd(true);
                            return;
                        } else {
                            pageNumber = pageNumber + 1;
                            adapter.loadMoreEnd(false);
                        }
                    }
                    datalist.clear();
                    datalistMap.clear();
                    getListDates();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }

                }

            }
        });
    }

    private void getmyinoutstatistic() {
        RxRetrofitClient.getInstance(this).getmyinoutstatistic("", new Observer<WalletListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WalletListBean walletListBean) {

            }
        });
    }

    private void initList() {
        getListDates();
        adapter = new MyAdapter(datalist);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter.setEmptyView(emptyView);
        adapter.setOnLoadMoreListener(this, list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivity(new Intent(WalletListActivity.this, WalletDetialsActivity.class)
                        .putExtra("src_type", datalist.get(i).getList().getSrc_type() + "")
                        .putExtra("trade_id", datalist.get(i).getList().getTrade_id()));
            }
        });
    }


    private void getListDates() {
        int lastHead = 0;//记录上一个头部的位置
        for (int a = 0; walletListBeanss != null && a < walletListBeanss.getData().getList().size(); a++) {
            String times = walletListBeanss.getData().getList().get(a).getCreate_time();
            String timesStr = Utils.formatDate(Utils.StrToDate(times, "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月");
            int month = Utils.getMonthOfDate(Utils.StrToDate(times, "yyyy-MM-dd HH:mm:ss"));
            if (!datalistMap.containsKey(timesStr)) {//此月份第一次遍历到
                datalist.add(new WalletListMultiItemBean(WalletListMultiItemBean.HEAD, 0, Utils.formatDate(Utils.StrToDate(times, "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月")));
                datalistMap.put(timesStr, new WalletListMultiItemBean(WalletListMultiItemBean.HEAD, 0, ""));//用于标记
                //处理第一行的值
                lastHead = datalist.size() - 1;
                datalist.get(lastHead).setCount(walletListBeanss.getData().getList().get(a).getTrade_amount());
            } else//此月份已有
            {
                //处理第一行的值
                datalist.get(lastHead).setCount(datalist.get(lastHead).getCount() + walletListBeanss.getData().getList().get(a).getTrade_amount());
            }
            datalist.add(new WalletListMultiItemBean(WalletListMultiItemBean.CONTENT, walletListBeanss.getData().getList().get(a)));
        }
    }

    @OnClick({R.id.toolbar_left_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        pageNumber = 1;
        getmyinoutlist(pageNumber + "", pageSize + "");
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        getmyinoutlist(pageNumber + "", pageSize + "");
    }

    class MyAdapter extends BaseMultiItemQuickAdapter<WalletListMultiItemBean, BaseViewHolder> {


        public MyAdapter(List<WalletListMultiItemBean> data) {
            super(data);
            addItemType(WalletListMultiItemBean.HEAD, R.layout.item_wallet_dt_head);
            addItemType(WalletListMultiItemBean.CONTENT, R.layout.item_wallet_dt);
        }

        @Override
        protected void convert(BaseViewHolder helper, WalletListMultiItemBean walletListMultiItemBean) {
            switch (helper.getItemViewType()) {
                case WalletListMultiItemBean.HEAD:
                    helper.setText(R.id.time, walletListMultiItemBean.getTimes())
                            .setText(R.id.money_tv, String.valueOf(walletListMultiItemBean.getCount()));
                    break;
                case WalletListMultiItemBean.CONTENT:
                    helper.setText(R.id.title, walletListMultiItemBean.getList().getTrade_name())
                            .setText(R.id.states_ps, walletListMultiItemBean.getList().getTrade_status())
                            .setText(R.id.time, Utils.formatDate(Utils.StrToDate(walletListMultiItemBean.getList().getCreate_time(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));
                    TextView tv = helper.getView(R.id.values);
                    tv.setText(walletListMultiItemBean.getList().getTrade_amount() > 0 ? "+" + (walletListMultiItemBean.getList().getTrade_amount()) : walletListMultiItemBean.getList().getTrade_amount() + "");
                    if (walletListMultiItemBean.getList().getTrade_amount() > 0)
                        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                    else
                        tv.setTextColor(getResources().getColor(R.color.gree));
                    break;
            }
        }
    }

}
