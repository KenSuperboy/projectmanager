package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.PushListAdater;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.ReqBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.push.PushReqDtActivity;
import com.gohoc.xiupuling.ui.push.PushSelectMenuActivity;
import com.gohoc.xiupuling.ui.requirement.MyWorksActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.MyPopWindow;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 当前投放广告
* */
public class PushAdvertActivity extends BasicActivity {

    @BindView(R.id.sort_tv)
    TextView sortTv;
    @BindView(R.id.sort_iv)
    ImageView sortIv;
    @BindView(R.id.sort_rv)
    RelativeLayout sortRv;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    @BindView(R.id.no_terminal_tips2_tv)
    TextView noTerminalTips2Tv;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.splite)
    TextView mSplite;
    private PushListAdater adater;
    private int page = 1;
    private int pageCount = 10;
    private String sortCondition = null;
    private boolean isRefresh = true;
    private ReqBean reqBeanTemp;

    private MyPopWindow myPopWindow;
    private View sortView;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_advert_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mToolbarTitle.setText("当前投放广告");
    }

    private void initListener() {
        EventBus.getDefault().register(this);
    }

    private void initData() {
        initSortMenu();
        initList();
        showrequirelist(sortCondition, page + "", pageCount + "", "true");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoginUpdate(Event.ReLoginUpdate message) {
        showrequirelist(sortCondition, page + "", pageCount + "", "true");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPushListEvent(Event.RefreshPushListEvent message) {
        showrequirelist(sortCondition, page + "", pageCount + "", "true");
    }


    private void initSortMenu() {
        sortView = LayoutInflater.from(mContext).inflate(R.layout.item_sort, null);
        textView = (TextView) sortView.findViewById(R.id.sort_by_tv);
        textView.setText("待投放作品");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// Toast.makeText(mContext,"xxxxx",Toast.LENGTH_SHORT).show();

                if (sortCondition == "unshow_first") {
                    sortTv.setText("全部投放作品");
                    sortCondition = null;
                    textView.setText("待投放作品");
                } else {
                    sortTv.setText("待投放作品");
                    textView.setText("全部投放作品");
                    sortCondition = "unshow_first";
                }

                page = 1;
                isRefresh = true;
                showrequirelist(sortCondition, page + "", pageCount + "", "true");
//                sortViewHelper.dismiss();
                myPopWindow.dismiss();
            }
        });
        myPopWindow = new MyPopWindow(mContext, sortView);

    }

    public void initList() {
        adater = new PushListAdater(mContext, reqBeanTemp);
        pullLoadMoreRecyclerView.setAdapter(adater);
        pullLoadMoreRecyclerView.setLinearLayout();
        pullLoadMoreRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        adater.setOnItemClickLitener(new PushListAdater.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.e("reqBeanTemp:" + reqBeanTemp.getData().getList().get(position).getWork_id());
                startActivity(new Intent(mContext, PushReqDtActivity.class)
                        .putExtra("reqBeanTemp", reqBeanTemp.getData().getList().get(position)));
            }

            @Override
            public void onItemPush(View view, int position) {
                PushNormlBean pushNormlBean = new PushNormlBean();
                reqBeanTemp.getData().getList().get(position).getOrientation();
                pushNormlBean.setIndexFlage(4);
                pushNormlBean.setOrientation(reqBeanTemp.getData().getList().get(position).getOrientation());
                pushNormlBean.setRq_id(reqBeanTemp.getData().getList().get(position).getRq_id());
                pushNormlBean.setName(reqBeanTemp.getData().getList().get(position).getActivity_title());
                pushNormlBean.setWork_id(reqBeanTemp.getData().getList().get(position).getWork_id());
                pushNormlBean.setPlaytime(reqBeanTemp.getData().getList().get(position).getPlaytime());
                pushNormlBean.setCover_url(reqBeanTemp.getData().getList().get(position).getMaterial_store_url());
                startActivity(new Intent(mContext, PushSelectMenuActivity.class).putExtra("pushNormlBean", pushNormlBean));

            }
        });


        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pullLoadMoreRecyclerView.setPushRefreshEnable(false);
                showrequirelist(sortCondition, "0", pageCount + "", "true");

            }

            @Override
            public void onLoadMore() {
                Logger.e("onLoadMore:page " + (page + 1));
                isRefresh = false;
                showrequirelist(sortCondition, (page + 1) + "", pageCount + "", "true");

            }
        });

        noDataTv.setText(Html.fromHtml("<font color='#32aefc'>亲，您当前无展示信息<br/>去</font><font color='#e62222'>\"我的作品库\"</font><font color='#32aefc'>挑一个来展示吧</font>"));


    }


    public void refreshOrLoadMore(ReqBean reqListBean) {
        if (isRefresh) {
            reqBeanTemp = reqListBean;
            adater.rf(reqBeanTemp);
        } else {
            reqBeanTemp = adater.getReqListBean();
            reqBeanTemp.getData().getList().addAll(reqListBean.getData().getList());
            adater.rf(reqBeanTemp);
        }

        if (reqBeanTemp.getData().getList().size() > 0)
            noDataLv.setVisibility(View.GONE);
        else {
            noTerminalTips2Tv.setText("");
            noDataLv.setVisibility(View.VISIBLE);
        }
    }

    public void showrequirelist(String orderby, String pageNumber,
                                String pageSize, String pagingquery) {

        if (pullLoadMoreRecyclerView != null) {
            pullLoadMoreRecyclerView.setRefreshing(true);
        }
        RxRetrofitClient.getInstance(mContext).showrequirelist(orderby, pageNumber, pageSize, pagingquery, new Observer<ReqBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (pullLoadMoreRecyclerView != null) {
                    pullLoadMoreRecyclerView.setRefreshing(false);
                    pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                }
                Utils.toast(mContext, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(ReqBean reqListBean) {
                if (pullLoadMoreRecyclerView != null) {
                    pullLoadMoreRecyclerView.setRefreshing(false);
                    pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                }
                if (reqListBean.getCode() == 1) {
                    if (reqListBean.getData().isLastPage())
                        pullLoadMoreRecyclerView.setPushRefreshEnable(false);
                    else
                        pullLoadMoreRecyclerView.setPushRefreshEnable(true);
                    if (isRefresh)
                        page = 1;
                    else
                        page++;
                    refreshOrLoadMore(reqListBean);
                }


            }
        });
    }

    @OnClick({R.id.sort_rv, R.id.no_data_lv,R.id.toolbar_left_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_rv:
//                sortViewHelper.showAsDropDown(sortRv);
                myPopWindow.showAsDropDown(view);
                break;
            case R.id.no_data_lv:
                startActivity(new Intent(mContext, MyWorksActivity.class));
                break;
            case R.id.toolbar_left_title:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
