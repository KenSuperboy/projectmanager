package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.ReqListAdater;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.bean.ReqListBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.constant.MyApp;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.push.PushReqEditActivity;
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
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;
import rx.Observer;

public class ReqListActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.sort_tv)
    TextView sortTv;
    @BindView(R.id.sort_iv)
    ImageView sortIv;
    @BindView(R.id.sort_rv)
    RelativeLayout sortRv;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    private ReqListAdater adater;
    private int page = 1;
    private int pageCount = 10;
    private boolean isRefresh = true;
    private ReqListBean reqListBeanTemp;

    private MyPopWindow sortViewHelper;
    private View sortView;
    private TextView sortByTv;
    private String sorttype = "new_work";  //unshow_first,showed_first,new_work
    private int pos = 0;
    private  final static  int REQUEST_RESLUT_RF=10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_list);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        EventBus.getDefault().register(this);
        toolbarTitle.setText("征集情况一览");
        noDataTv.setText(Html.fromHtml("<font color='#32aefc'>亲，您还未发布需求<br/></font>点击<font color='#e62222'>\"作品\"</font><font color='#32aefc'>发布任务</font>"));
        noDataTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                Intent intent = new Intent(ReqListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                EventBus.getDefault().post(new Event.MainIndex(3));
                finish();
            }
        });
        selectworklist(sorttype, page + "", pageCount + "", "true");
        initSortMenu();
        MyApp.getActivityList().add(this);

    }

    @OnClick({R.id.toolbar_left_title, R.id.sort_rv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                this.finish();
                break;
            case R.id.sort_rv:
                sortViewHelper.showAsDropDown(sortRv);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshPushListEvent(Event.RefreshPushListEvent message) {
        isRefresh = true;
        page = 1;
        pullLoadMoreRecyclerView.setPushRefreshEnable(false);
        selectworklist(sorttype, page + "", pageCount + "", "true");
    }
    public void selectworklist(String orderby, String pageNumber,
                               String pageSize, String pagingquery) {
        RxRetrofitClient.getInstance(this).selectworklist(orderby, pageNumber, pageSize, pagingquery, new Observer<ReqListBean>() {
            @Override
            public void onCompleted() {
                pullLoadMoreRecyclerView.setRefreshing(false);
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }

            @Override
            public void onError(Throwable e) {
                pullLoadMoreRecyclerView.setRefreshing(false);
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                Utils.toast(ReqListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(ReqListBean reqListBean) {
                pullLoadMoreRecyclerView.setRefreshing(false);
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                if (reqListBean.getData().isLastPage())
                    pullLoadMoreRecyclerView.setPushRefreshEnable(false);
                else
                    pullLoadMoreRecyclerView.setPushRefreshEnable(true);

                if (reqListBean.getCode() == 1) {
                    if (isRefresh)
                        page = 1;
                    else
                        page++;
                    initList(reqListBean);
                }

            }
        });
    }

    public void initList(ReqListBean reqListBean) {
        pullLoadMoreRecyclerView.setRefreshing(true);
        if (null == adater) {
            reqListBeanTemp = reqListBean;
            if (reqListBean.getData().getList().size() < 1) {
                noDataLv.setVisibility(View.VISIBLE);

            } else
                noDataLv.setVisibility(View.GONE);

            adater = new ReqListAdater(this, reqListBean);
            pullLoadMoreRecyclerView.setAdapter(adater);
            pullLoadMoreRecyclerView.setLinearLayout();
            // provinceList.setItemAnimator(new DefaultItemAnimator());
            adater.setOnItemClickLitener(new ReqListAdater.OnItemClickLitener() {
                @Override
                public void onClick(View v, int position) {
                    if(reqListBeanTemp.getData().getList().get(position).getWorklist().size()>0)
                      startActivityForResult(new Intent(ReqListActivity.this, ReqDtActivity.class).putExtra("reqListBean", reqListBeanTemp.getData().getList().get(position)),REQUEST_RESLUT_RF);
                }

                @Override
                public void onClosed(View v, int position) {
                    pos = position;
                    showDialog();
                }

                @Override
                public void onEdit(View v, int position) {
                    ReqListBean.DataBean.ListBean date = reqListBeanTemp.getData().getList().get(position);

                    PromotionInfoBean promotionInfoBean = new PromotionInfoBean();
                    promotionInfoBean.setRq_id(date.getRq_id());
                    promotionInfoBean.setActivityTitle(date.getActivity_title());
                    promotionInfoBean.setOpen(date.getActivity_onoff() == 0 ? true : false);
                    promotionInfoBean.setLinkType(date.getActivity_nav_type());
                    promotionInfoBean.setLatitude(date.getActivity_nav_latitude() == null ? null : date.getActivity_nav_latitude() + "");
                    promotionInfoBean.setLongitude(date.getActivity_nav_longitude() == null ? null : date.getActivity_nav_longitude() + "");
                    //promotionInfoBean.setLcoationName(reqBeanTemp.getActivity_brand());
                    promotionInfoBean.setDetails(date.getActivity_detail());
                    promotionInfoBean.setKeyWord(date.getActivity_brand());
                    promotionInfoBean.setIntroduce(date.getActivity_content());
                    if (promotionInfoBean.getLinkType() == 1)
                        promotionInfoBean.setLinkUrl(date.getActivity_nav_content());
                    else
                        promotionInfoBean.setLcoationName(date.getActivity_nav_content());
                    startActivity(new Intent(ReqListActivity.this, PushReqEditActivity.class).putExtra("promotionInfoBean", promotionInfoBean));

                }
            });


            pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                @Override
                public void onRefresh() {
                    isRefresh = true;
                    page = 1;
                    pullLoadMoreRecyclerView.setPushRefreshEnable(false);
                    selectworklist(sorttype, page + "", pageCount + "", "true");

                }

                @Override
                public void onLoadMore() {
                    Logger.e("onLoadMore:page " + (page + 1));
                    isRefresh = false;
                    selectworklist(sorttype, (page + 1) + "", pageCount + "", "true");

                }
            });
        } else {
            if (isRefresh) {
                reqListBeanTemp = reqListBean;
                adater.rf(reqListBeanTemp);
                if (reqListBean.getData().getList().size() < 1) {
                    noDataLv.setVisibility(View.VISIBLE);

                } else
                    noDataLv.setVisibility(View.GONE);

            } else {
                reqListBeanTemp = adater.getReqListBean();
                reqListBeanTemp.getData().getList().addAll(reqListBean.getData().getList());

                Logger.e("reqListBeanSize: " + reqListBeanTemp.getData().getList().size());
                adater.rf(reqListBeanTemp);
                //adater.rf(reqListBean);
            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK==resultCode)
        {
            isRefresh = true;
            page = 1;
            pullLoadMoreRecyclerView.setPushRefreshEnable(false);
            selectworklist(sorttype, page + "", pageCount + "", "true");
        }

    }

    private void initSortMenu() {
        sortView = LayoutInflater.from(this).inflate(R.layout.item_sort, null);
        TextView textView = (TextView) sortView.findViewById(R.id.mask_tv);
        sortByTv = (TextView) sortView.findViewById(R.id.sort_by_tv);
        sortByTv.setText("需求发布时间");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortViewHelper.dismiss();
            }
        });
        sortByTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //unshow_first,showed_first,new_work
                if (sorttype.equals("unshow_first")) {
                    sortTv.setText("按新增作品排序");
                    sorttype = "new_work";
                    sortByTv.setText("需求发布时间");
                } else {
                    sortTv.setText("需求发布时间");
                    sortByTv.setText("按新增作品排序");
                    sorttype = "unshow_first";
                }
                page = 1;
                isRefresh = true;
                pullLoadMoreRecyclerView.setRefreshing(true);
                selectworklist(sorttype, page + "", pageCount + "", "true");
                sortViewHelper.dismiss();
            }
        });
        sortViewHelper = new MyPopWindow(this,sortView);

    }

    public void showDialog() {

        BGAAlertController alertController = new BGAAlertController(this, "","该需求已有满意作品，或者该需求已过期，请停止作品征集" , BGAAlertController.AlertControllerStyle.ActionSheet);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.addAction(new BGAAlertAction("停止 作品征集", BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                RxRetrofitClient.getInstance(ReqListActivity.this).closeRequirement(reqListBeanTemp.getData().getList().get(pos).getRq_id(), new Observer<VCodeBenan>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(ReqListActivity.this, "修改失败，请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(VCodeBenan vCodeBenan) {
                        Utils.toast(ReqListActivity.this, vCodeBenan.getMessage());
                        if (vCodeBenan.getCode() == 1) {
                            isRefresh = true;
                            page = 1;
                            pullLoadMoreRecyclerView.setPushRefreshEnable(false);
                            selectworklist(sorttype, page + "", pageCount + "", "true");
                        }
                    }
                });
            }
        }));
        alertController.setCancelable(true);
        alertController.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
