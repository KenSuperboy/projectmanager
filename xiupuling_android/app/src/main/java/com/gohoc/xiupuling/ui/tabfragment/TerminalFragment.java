package com.gohoc.xiupuling.ui.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.adapter.TerminalListAdapter;
import com.gohoc.xiupuling.bean.ShopBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.terminal.DistrictListActivity;
import com.gohoc.xiupuling.ui.terminal.TerminalDetailsActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.MyPopWindow;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;
import rx.Observer;


public class TerminalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.id_swipe_ly)
    SwipeRefreshLayout idSwipeLy;
    @BindView(R.id.expandedlist_list)
    ExpandableListView expandedlistList;
    @BindView(R.id.sort_tv)
    TextView sortTv;
    @BindView(R.id.sort_iv)
    ImageView sortIv;
    @BindView(R.id.sort_rv)
    RelativeLayout sortRv;
    @BindView(R.id.no_terminal_tips2_tv)
    TextView noTerminalTips2Tv;
    private View viewContainer;
    private TerminalListAdapter terminalListAdapter;
    private TextView sortByTv;
    private String sorttype = "star";
    //    private PopupWindowHelper sortViewHelper;
    private View sortView;
    private MyPopWindow myPopWindow;
    private ArrayList<Boolean> isOpenList = new ArrayList<>();//记录是否打开
    private ShopBean shopBeans;

    public TerminalFragment() {

    }

    public static TerminalFragment newInstance() {
        TerminalFragment fragment = new TerminalFragment();
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
        viewContainer = inflater.inflate(R.layout.fragment_terminal, container, false);
        ButterKnife.bind(this, viewContainer);
        idSwipeLy.setOnRefreshListener(this);
        getTerminalList();
        initSortMenu();
        return viewContainer;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoginUpdate(Event.ReLoginUpdate message) {
        getTerminalList();
    }

    private void getTerminalList() {
        if (idSwipeLy != null)
            idSwipeLy.setRefreshing(true);
        RxRetrofitClient.getInstance(getContext()).getTerminalList(sorttype, new Observer<ShopBean>() {
            @Override
            public void onCompleted() {
                idSwipeLy.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                noDataLv.setVisibility(View.VISIBLE);
                idSwipeLy.setRefreshing(false);
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
                        expandedlistList.setVisibility(View.VISIBLE);
                        shopBeans = shopBean;
                        initTerminalList();
                        setIsOpenList(shopBean);
                        // getTerminalStatusList();
                        return;
                    }

                }
                expandedlistList.setVisibility(View.GONE);
                noDataLv.setVisibility(View.VISIBLE);
                noTerminalTips2Tv.setText(Html.fromHtml("赶紧点击右上角<font color='#FF0000'>“+”</font>绑定一个"));

            }
        });
    }

    private void updateTerminalList(int groupPosition) {

        RxRetrofitClient.getInstance(getContext()).getTerminalList(sorttype, new Observer<ShopBean>() {
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
            public void onNext(ShopBean shopBean) {
                idSwipeLy.setRefreshing(false);
                if (shopBean.getCode() == 1) {
                    shopBeans = shopBean;
                    terminalListAdapter.notifyDataSetChanged();
                    for (int a = 0; a < isOpenList.size(); a++) {
                        if (isOpenList.get(a))
                            expandedlistList.expandGroup(a);
                        else
                            expandedlistList.collapseGroup(a);
                    }

                }


            }
        });
    }

    private void setIsOpenList(ShopBean shopBean) {
        isOpenList.clear();
        for (int a = 0; a < shopBean.getData().size(); a++) {
            isOpenList.add(false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* initTerminalList(shopBeans,savedInstanceState);*/


    }

    private void initTerminalList() {
        terminalListAdapter = new TerminalListAdapter(shopBeans, getActivity(), expandedlistList);
        expandedlistList.setAdapter(terminalListAdapter);
        expandedlistList.setGroupIndicator(null);

/*        expandedlistList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                startActivity(new Intent(getActivity(), TerminalDetailsActivity.class).putExtra("terminaId", shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getTerminal_id() + ""));
                Logger.d(shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getTerminal_id());
                return true;
            }
        });*/


        expandedlistList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                isOpenList.set(groupPosition, !expandedlistList.isGroupExpanded(groupPosition));
                updateTerminalList(groupPosition);
                return false;
            }
        });

        terminalListAdapter.setOnExpandableListViewItemClickLitener(new TerminalListAdapter.OnExpandableListViewItemClickLitener() {
            @Override
            public void onItemClick(int groupPosition, int childPosition, boolean isLastChild) {
                startActivity(new Intent(getActivity(), TerminalDetailsActivity.class).putExtra("terminaId", shopBeans.getData().get(groupPosition).getTermlist().get(childPosition).getTerminal_id() + ""));
                Logger.d(shopBeans.getData().get(groupPosition).getTermlist().get(childPosition).getTerminal_id());
            }

            @Override
            public void onItemLongClick(int groupPosition, int childPosition, boolean isLastChild) {

            }

            @Override
            public void onItemDel(final int groupPosition, final int childPosition, boolean isLastChild) {
                showAlert(groupPosition,childPosition,"删除 " + shopBeans.getData().get(groupPosition).getTermlist().get(childPosition).getTerminal_no() + "号机",BGAAlertController.AlertControllerStyle.ActionSheet);
            }
        });


    }
    private void showAlert(final int groupPosition, final int childPosition, String content, BGAAlertController.AlertControllerStyle preferredStyle) {
        BGAAlertController alertController = new BGAAlertController(getActivity(), "","一旦删除，数据不可恢复，目前该终端订单收益全部归零。" , preferredStyle);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.addAction(new BGAAlertAction(content, BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                deleteTerminal(shopBeans.getData().get(groupPosition).getTermlist().get(childPosition).getTerminal_id() + "");
            }
        }));
        alertController.setCancelable(true);
        alertController.show();
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

    private void deleteTerminal(String id) {
        RxRetrofitClient.getInstance(getActivity()).deleteTerminal(id, new Observer<VCodeBenan>() {
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
                    onRefresh();
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
