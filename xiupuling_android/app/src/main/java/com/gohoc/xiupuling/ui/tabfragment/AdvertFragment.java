package com.gohoc.xiupuling.ui.tabfragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.adapter.OrderListAdater;
import com.gohoc.xiupuling.adapter.PopWindowAdapter;
import com.gohoc.xiupuling.bean.OrderBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.order.OrderRushActivity;
import com.gohoc.xiupuling.ui.requirement.MyWorksActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.MyPopWindow;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;


public class AdvertFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.sort_tv)
    TextView sortTv;
    @BindView(R.id.sort_rv)
    RelativeLayout sortRv;
    @BindView(R.id.list_rv)
    RecyclerView listRv;
    @BindView(R.id.id_swipe_ly)
    SwipeRefreshLayout idSwipeLy;
    @BindView(R.id.no_date)
    TextView noDate;
    private View viewContainer;
    private String type = "price";  //price,terminal,workstar
    private OrderListAdater orderListAdater;
    private OrderBean orderBeans;
    private View empty;
    private MyPopWindow myPopWindow;
    private View sortView;
    private PopWindowAdapter adapter;

    public AdvertFragment() {

    }

    public static AdvertFragment newInstance() {
        AdvertFragment fragment = new AdvertFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_advert, container, false);
        ButterKnife.bind(this, viewContainer);
        idSwipeLy.setOnRefreshListener(this);
        initList(null);
        orderlist(type);
        initSortMenu();
        return viewContainer;

    }

    private ListView listview;
    private List<String> list = new ArrayList<>();

    private void initSortMenu() {
        sortView = LayoutInflater.from(getActivity()).inflate(R.layout.item_sort_lsitview, null);
        listview = (ListView) sortView.findViewById(R.id.listview);
        TextView tv1 = (TextView) sortView.findViewById(R.id.tv1);
        adapter = new PopWindowAdapter(getActivity());
        listview.setAdapter(adapter);
        list.clear();
        list.add("作品星级");
        list.add("订单金额");
        adapter.setList(list);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPopWindow.dismiss();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getAdapter().getItem(position);
                if (s.equals("作品星级")) {
                    type = "workstar";
                    sortTv.setText("按作品星级排序");
                } else if (s.equals("订单金额")) {
                    type = "price";
                    sortTv.setText("按订单金额排序");
                } else if (s.equals("店铺终端")) {
                    type = "terminal";
                    sortTv.setText("按店铺终端排序");
                }
                orderlist(type);
                myPopWindow.dismiss();
                if (type.equals("workstar")) {
                    list.clear();
                    list.add("店铺终端");
                    list.add("订单金额");
                } else if (type.equals("terminal")) {
                    list.clear();
                    list.add("作品星级");
                    list.add("订单金额");
                } else if (type.equals("price")) {
                    list.clear();
                    list.add("店铺终端");
                    list.add("作品星级");
                }
                adapter.setList(list);
                ViewUtils.setListViewHeight(listview, list.size());
            }
        });
        myPopWindow = new MyPopWindow(getActivity(), sortView);

    }

    @OnClick({R.id.sort_rv,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_rv:
                myPopWindow.showAsDropDown(view);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReLoginUpdate(Event.ReLoginUpdate message) {
        orderlist(type);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OrderUpdateEvent(Event.OrderUpdateEvent message) {
        orderlist(type);
    }

    public void orderlist(String orderby) {
        RxRetrofitClient.getInstance(getActivity()).orderlist(orderby, new Observer<OrderBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {

                Utils.toast(getActivity(), "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(OrderBean orderBean) {

                if (orderBean.getCode() == 1) {
                    orderBeans = orderBean;
                    if (idSwipeLy != null)
                        idSwipeLy.setRefreshing(false);
                    ///orderListAdater.rf(orderBeans);
                    if (orderBean.getData().size() > 0) {
                        noDate.setVisibility(View.GONE);
                        orderListAdater.getData().clear();
                        orderListAdater.addData(orderBeans.getData());
                        orderListAdater.notifyDataSetChanged();
                    } else
                        {
                            orderListAdater.getData().clear();
                            orderListAdater.notifyDataSetChanged();
                            noDate.setVisibility(View.VISIBLE);
                        }


                }


            }
        });
    }

    private void initList(List<OrderBean.DataBean> data) {
        orderListAdater = new OrderListAdater(getActivity(), R.layout.item_order, data);
        listRv.setAdapter(orderListAdater);
        listRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //添加分割线
        listRv.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        orderListAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivity(new Intent(getActivity(), OrderRushActivity.class).putExtra("order", orderBeans.getData().get(i)));

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

    }


    @Override
    public void onRefresh() {
        orderlist(type);
    }
}
