package com.gohoc.xiupuling.ui.tabfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.adapter.GroupAdater;
import com.gohoc.xiupuling.adapter.SortMenuAdater;
import com.gohoc.xiupuling.bean.GroupBean;
import com.gohoc.xiupuling.bean.KvBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.account.LoginActivity;
import com.gohoc.xiupuling.ui.group.GroupCessionActivity;
import com.gohoc.xiupuling.ui.group.GroupDetailsActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.MyPopWindow;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;


public class GroupFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id_swipe_ly)
    SwipeRefreshLayout idSwipeLy;
    @BindView(R.id.sort_left_ll)
    LinearLayout sortLeftLl;
    @BindView(R.id.sort_right_ll)
    LinearLayout sortRightLl;
    @BindView(R.id.sort_left_tv)
    TextView sortLeftTv;
    @BindView(R.id.sort_right_tv)
    TextView sortRightTv;
    @BindView(R.id.sort_ll)
    PercentRelativeLayout sortLl;
    @BindView(R.id.sort_name_left_tv)
    TextView sortNameLeftTv;
    @BindView(R.id.sort_name_right_tv)
    TextView sortNameRightTv;
    @BindView(R.id.no_group_tips2_tv)
    TextView noGroupTips2Tv;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    private View viewContainer;
    private GroupAdater adater;
    private GroupBean groupBeans;

    private View sortViewLeft;
    private View sortViewRigh;

    private SortMenuAdater sortMenuAdaterL, sortMenuAdaterR;

    Unbinder unbinder;
    private ArrayList<KvBean> sortleftList = new ArrayList<>();
    private ArrayList<KvBean> sortRightList = new ArrayList<>();
    private String filterby = "all", orderby = "name";


    private MyPopWindow popMenuLeft, popMenuRight;

    public GroupFragment() {

    }

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_group, container, false);
        unbinder = ButterKnife.bind(this, viewContainer);
        idSwipeLy.setOnRefreshListener(this);
        initList();
        getMyUnionList(filterby, orderby);
        initSort();
        noDataLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event.ShowRightMeunEvent(true));
            }
        });
        EventBus.getDefault().register(this);
        return viewContainer;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoginUpdate(Event.ReLoginUpdate message) {
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshGroupeListEvent(Event.RefreshGroupeListEvent message) {
        initSort();
        this.onRefresh();
    }

    private void getMyUnionList(String filterby, String orderby) {

        RxRetrofitClient.getInstance(getContext()).getMyUnionList(filterby, orderby, new Observer<GroupBean>() {
            @Override
            public void onCompleted() {
                idSwipeLy.setRefreshing(false);

            }

            @Override
            public void onError(Throwable e) {
                idSwipeLy.setRefreshing(false);
                Utils.toast(getActivity(), "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupBean groupBean) {
                if (idSwipeLy != null)
                    idSwipeLy.setRefreshing(false);
                if (groupBean.getCode() == 1) {
                    groupBeans = groupBean;
                    adater.rf(groupBean);
                    if (groupBean.getData() != null && groupBean.getData().size() > 0)
                        noDataLv.setVisibility(View.GONE);
                    else {
                        noDataLv.setVisibility(View.VISIBLE);
                        noGroupTips2Tv.setText(Html.fromHtml("赶紧点击右上角<font color='#FF0000'>“+”</font>绑定一个"));
                    }

                }

            }
        });
    }

    private void initList() {

        adater = new GroupAdater(getContext(), groupBeans);
        recyclerView.setAdapter(adater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        adater.setOnItemClick(new GroupAdater.OnItemLitener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), GroupDetailsActivity.class).putExtra("unionId", groupBeans.getData().get(position).getUnion_id()));
            }

            @Override
            public void onItemDelClick(View view, final int positions) {

                final UserBean userBean = (UserBean) ACache.get(getActivity()).getAsObject("userBean");
                if (groupBeans.getData().get(positions).getUser_id().equals(userBean.getData().getUser_id()) && groupBeans.getData().get(positions).getUser_cnt() > 1) {
                    //自己的群,转移给别人，并退出此群
                    adater.notifyDataSetChanged();
                    startActivity(new Intent(getActivity(), GroupCessionActivity.class).putExtra("union_id", groupBeans.getData().get(positions).getUnion_id()).putExtra("isOut", 0).putExtra("type", 1));
                } else {

                    BGAAlertController alertController = new BGAAlertController(getActivity(), "","你确定退出群组 " + groupBeans.getData().get(positions).getUnion_name() , BGAAlertController.AlertControllerStyle.ActionSheet);
                    // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
                    alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
                        @Override
                        public void onClick() {

                        }
                    }));
                    alertController.addAction(new BGAAlertAction("退出该群组", BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
                        @Override
                        public void onClick() {
                            if (groupBeans.getData().get(positions).getUser_id().equals(userBean.getData().getUser_id()))
                                unionDelete(groupBeans.getData().get(positions).getUnion_id());
                            else
                                quitfromunion(groupBeans.getData().get(positions).getUnion_id());
                        }
                    }));
                    alertController.setCancelable(true);
                    alertController.show();

                }


            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();

    }

    @Override
    public void onRefresh() {
        getMyUnionList(filterby, orderby);
    }

    @Subscribe
    public void GroupEvent(Event.GroupEvent event) {
        this.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.no_data_lv, R.id.sort_left_ll, R.id.sort_right_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_data_lv:
                EventBus.getDefault().post(new Event.ShowRightMeunEvent(true));
                break;
            case R.id.sort_left_ll:
                popMenuRight.dismiss();
                popMenuLeft.setHeight(viewContainer.getHeight() + 35);
                popMenuLeft.showAsDropDown(sortLl, 0, 8);
                sortLeftTv.setBackgroundResource(R.color.colorPrimary);
                sortRightTv.setBackgroundResource(R.color.splite);
                break;
            case R.id.sort_right_ll:
                popMenuLeft.dismiss();
                popMenuRight.setHeight(viewContainer.getHeight() + 35);
                popMenuRight.showAsDropDown(sortLl, 0, 8);
                sortLeftTv.setBackgroundResource(R.color.splite);
                sortRightTv.setBackgroundResource(R.color.colorPrimary);
                break;
        }
    }


    private void initSort() {
/*        if (null != sortViewLeft)
            return;*/
        sortleftList.clear();
        sortRightList.clear();

        if(Credential.getInstance().getCurUser(getActivity())==null||Credential.getInstance().getCurUser(getActivity()).getData()==null){
            Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
            getActivity().finish();
            return;
        }

        if (Credential.getInstance().getCurUser(getActivity()).getData().getShared_platform() == 1) {
            filterby = "all";
            sortNameLeftTv.setText("全部群组");
            sortleftList.add(new KvBean("all", "全部群组").setCheck(true));
            sortleftList.add(new KvBean("private", "私有群组"));
            sortleftList.add(new KvBean("public", "共享群组"));
            sortleftList.add(new KvBean("chain", "连锁群组"));
            sortleftList.add(new KvBean("media", "媒体群组"));
            sortleftList.add(new KvBean("link", "联动群组"));
        } else {
            filterby = "all";
            sortleftList.add(new KvBean("all", "全部群组").setCheck(true));
            sortNameLeftTv.setText("全部群组");
            sortleftList.add(new KvBean("private", "私有群组"));
            sortleftList.add(new KvBean("chain", "连锁群组"));
        }

        sortRightList.add(new KvBean("name", "按名称排序").setCheck(true));
        sortRightList.add(new KvBean("termcnt", "按终端数量排序"));
        //
        sortViewLeft = LayoutInflater.from(getActivity()).inflate(R.layout.simple_list, null);
        ListView listL = (ListView) sortViewLeft.findViewById(R.id.list);

        sortMenuAdaterL = new SortMenuAdater(getActivity(), sortleftList);
        listL.setAdapter(sortMenuAdaterL);
        listL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMenuLeft.dismiss();
                for (int a = 0; a < sortleftList.size(); a++) {
                    sortleftList.get(a).setCheck(false);
                }
                sortleftList.get(position).setCheck(true);
                sortNameLeftTv.setText(sortleftList.get(position).getV());
                sortMenuAdaterL.notifyDataSetChanged();

                filterby = sortleftList.get(position).getK();
                onRefresh();


            }
        });

        popMenuLeft = new MyPopWindow(getActivity(), sortViewLeft);
        sortViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenuLeft.dismiss();

            }
        });
        sortViewRigh = LayoutInflater.from(getActivity()).inflate(R.layout.simple_list, null);
        ListView listR = (ListView) sortViewRigh.findViewById(R.id.list);
        sortMenuAdaterR = new SortMenuAdater(getActivity(), sortRightList);
        listR.setAdapter(sortMenuAdaterR);

        listR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMenuRight.dismiss();
                for (int a = 0; a < sortRightList.size(); a++) {
                    sortRightList.get(a).setCheck(false);
                }
                sortRightList.get(position).setCheck(true);
                sortNameRightTv.setText(sortRightList.get(position).getV());
                sortMenuAdaterR.notifyDataSetChanged();
                orderby = sortRightList.get(position).getK();
                onRefresh();
            }
        });
        popMenuRight = new MyPopWindow(getActivity(), sortViewRigh);
        sortViewRigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenuRight.dismiss();
            }
        });

    }

    private void quitfromunion(String union_id) {
        RxRetrofitClient.getInstance(getActivity()).quitfromunion(union_id, new Observer<VCodeBenan>() {
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
                if (vCodeBenan.getCode() == 1) {
                    onRefresh();
                }
            }
        });


    }

    private void unionDelete(String union_id) {
        RxRetrofitClient.getInstance(getActivity()).unionDelete(union_id, new Observer<VCodeBenan>() {
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
                if (vCodeBenan.getCode() == 1) {
                    onRefresh();
                }
            }
        });


    }



}
