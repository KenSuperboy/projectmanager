package com.gohoc.xiupuling.ui.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.adapter.ShopAndTerminalAdater;
import com.gohoc.xiupuling.bean.ShopAndTerminal0Bean;
import com.gohoc.xiupuling.bean.ShopAndTerminal1Bean;
import com.gohoc.xiupuling.bean.ShopBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.callback.Callback;
import com.gohoc.xiupuling.callback.Callback1;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.terminal.TerminalDetailsActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.MyPopWindow;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;


public class ShopTerminalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.id_swipe_ly)
    SwipeRefreshLayout idSwipeLy;
    @BindView(R.id.terminal_list_rv)
    RecyclerView terminalListRv;
    @BindView(R.id.sort_tv)
    TextView sortTv;
    @BindView(R.id.sort_iv)
    ImageView sortIv;
    @BindView(R.id.sort_rv)
    RelativeLayout sortRv;
    @BindView(R.id.no_terminal_tips2_tv)
    TextView noTerminalTips2Tv;
    private View viewContainer;
    private ShopAndTerminalAdater shopAndTerminalAdater;
    private TextView sortByTv;
    private String sorttype = "star";
    private View sortView;
    private MyPopWindow myPopWindow;
    private ShopBean shopBeans;
    private List<MultiItemEntity> data = new ArrayList<>();
    private List<MultiItemEntity> tempData = new ArrayList<>();

    public ShopTerminalFragment() {

    }

    public static ShopTerminalFragment newInstance() {
        ShopTerminalFragment fragment = new ShopTerminalFragment();
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
        viewContainer = inflater.inflate(R.layout.fragment_shop_terminal, container, false);
        ButterKnife.bind(this, viewContainer);
        idSwipeLy.setOnRefreshListener(this);
        initTerminalList();
        getTerminalList();
        initSortMenu();
        return viewContainer;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoginUpdate(Event.ReLoginUpdate message) {
        getTerminalList();
    }

    private void getTerminalList() {
        if (idSwipeLy != null&&!isload)
            idSwipeLy.setRefreshing(true);
        RxRetrofitClient.getInstance(getContext()).getTerminalList(sorttype, new Observer<ShopBean>() {
            @Override
            public void onCompleted() {
                idSwipeLy.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                if(noDataLv!=null&&idSwipeLy!=null){
                    //noDataLv.setVisibility(View.VISIBLE);
                    idSwipeLy.setRefreshing(false);
                }
                Utils.toast(getActivity(), "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ShopBean shopBean) {
                idSwipeLy.setRefreshing(false);
                if (shopBean.getCode() == 1) {
                    if (null != shopBean.getData() && shopBean.getData().size() > 0) {
                        noDataLv.setVisibility(View.GONE);
                        terminalListRv.setVisibility(View.VISIBLE);
                        shopBeans = shopBean;
                        getListData();
                        return;
                    }

                }
                terminalListRv.setVisibility(View.GONE);
                noDataLv.setVisibility(View.VISIBLE);
                noTerminalTips2Tv.setText(Html.fromHtml("赶紧点击右上角<font color='#FF0000'>“+”</font>绑定一个"));

            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* initTerminalList(shopBeans,savedInstanceState);*/


    }

    private void initTerminalList() {
        //  getListData();
        shopAndTerminalAdater = new ShopAndTerminalAdater(getActivity(), data);
        terminalListRv.setAdapter(shopAndTerminalAdater);
        terminalListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        shopAndTerminalAdater.setOnSubClickLitener(new ShopAndTerminalAdater.OnSubClickLitener() {
            @Override
            public void onItemClick(ShopAndTerminal1Bean shopAndTerminal1Bean) {
                startActivity(new Intent(getActivity(), TerminalDetailsActivity.class).putExtra("terminaId", String.valueOf(shopAndTerminal1Bean.getTermlistBean().getTerminal_id())));
            }

            @Override
            public void onItemDel(final ShopAndTerminal1Bean shopAndTerminal1Bean, final int postion) {

            }

            @Override
            public void onShopDel(final ShopAndTerminal1Bean shopAndTerminal1Bean, final int postion) {
                LogUtil.d("准备删除====");
                BGAAlertController alertController = new BGAAlertController(getActivity(), "", "", BGAAlertController.AlertControllerStyle.ActionSheet);
                // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
                alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
                    @Override
                    public void onClick() {

                    }
                }));
                alertController.addAction(new BGAAlertAction("删除门店" + shopAndTerminal1Bean.getShop().getShop_name(), BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
                    @Override
                    public void onClick() {
                        LogUtil.d("删除门店====");
                        deleteShop(shopAndTerminal1Bean, postion);
                    }
                }));
                alertController.setCancelable(true);
                alertController.show();
            }
        });
        shopAndTerminalAdater.setCallback(new Callback1() {
            @Override
            public void callBack(String string) {
                LogUtil.d("终端详情回调：");
                CustomUtils.getShopDetail(getActivity(),string);
            }
        });

        shopAndTerminalAdater.setCallback(new Callback() {
            @Override
            public void callBack() {
                LogUtil.d("可局部刷新");
                getTerminalList();
            }
        });
    }

    private void deleteShop(final ShopAndTerminal1Bean shopAndTerminal1Bean, final int postion) {
        RxRetrofitClient.getInstance(getActivity()).deleteShop(shopAndTerminal1Bean.getShop().getShop_id(), new Observer<VCodeBenan>() {
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
            public void onNext(VCodeBenan vCodeBenan) {
                LogUtil.d("删除门店====");
                if (vCodeBenan.getCode() == 1) {
                    getTerminalList();
                } else {
                    Utils.toast(getActivity(), vCodeBenan.getMessage());
                }

            }
        });
    }

    boolean isload;
    private void getListData() {
        if (shopBeans == null)
            return;
        tempData.clear();
        for (int a = 0; a < shopBeans.getData().size(); a++) {
            ShopAndTerminal0Bean sat0 = new ShopAndTerminal0Bean(shopBeans.getData().get(a));
            for (int b = 0; b < shopBeans.getData().get(a).getTermlist().size(); b++)
                sat0.addSubItem(new ShopAndTerminal1Bean(shopBeans.getData().get(a).getTermlist().get(b), b == shopBeans.getData().get(a).getTermlist().size() - 1));
            if (shopBeans.getData().get(a).getTermlist().size() < 1)
                sat0.addSubItem(new ShopAndTerminal1Bean(shopBeans.getData().get(a)));
            tempData.add(sat0);
        }

        if(isload){
            shopAndTerminalAdater.setNewData(tempData);
            shopAndTerminalAdater.notifyDataSetChanged();
        }else {
            shopAndTerminalAdater.setNewData(tempData);
            terminalListRv.setAdapter(shopAndTerminalAdater);
        }
        isload=true;

        if(tempData.size()==0){
            terminalListRv.setVisibility(View.GONE);
            noDataLv.setVisibility(View.VISIBLE);
            noTerminalTips2Tv.setText(Html.fromHtml("赶紧点击右上角<font color='#FF0000'>“+”</font>绑定一个"));
        }
    }


    private void initSortMenu() {
        sortView = LayoutInflater.from(getActivity()).inflate(R.layout.item_sort, null);
        TextView textView = (TextView) sortView.findViewById(R.id.mask_tv);
        sortByTv = (TextView) sortView.findViewById(R.id.sort_by_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sortViewHelper.dismiss();
                myPopWindow.dismiss();
            }
        });
        sortByTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sorttype.equals("star")) {
                    sortTv.setText("按店名排序");
                    sorttype = "name";
                    sortByTv.setText("星级");
                } else {
                    sortTv.setText("按星级排序");
                    sortByTv.setText("店名");
                    sorttype = "star";
                }
                myPopWindow.dismiss();
//                sortViewHelper.dismiss();
                getTerminalList();
            }
        });
        myPopWindow = new MyPopWindow(getActivity(), sortView);

    }

    @Override
    public void onRefresh() {
        LogUtil.d("刷新门店终端111111111111111111111111");
        getTerminalList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshTerminalListEvent(Event.RefreshTerminalListEvent message) {
        this.onRefresh();
    }

    @OnClick({R.id.sort_rv, R.id.no_data_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_rv:
//                sortViewHelper.showAsDropDown(sortRv);
                myPopWindow.showAsDropDown(view);
                break;
            case R.id.no_data_lv:
                EventBus.getDefault().post(new Event.ShowRightMeunEvent(true));
                break;
        }
    }

    private void deleteTerminal(final ShopAndTerminal1Bean shopAndTerminal1Bean, final int postion) {
        RxRetrofitClient.getInstance(getActivity()).deleteTerminal(String.valueOf(shopAndTerminal1Bean.getTermlistBean().getTerminal_id()), new Observer<VCodeBenan>() {
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
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    shopAndTerminalAdater.notifyItemRemoved(postion);
                }
            }
        });
    }

    private void getTerminalStatusList() {
        RxRetrofitClient.getInstance(getActivity()).getTerminalStatusList("25e0637d4e914193a85741a58c0622fb", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(getActivity(), "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
