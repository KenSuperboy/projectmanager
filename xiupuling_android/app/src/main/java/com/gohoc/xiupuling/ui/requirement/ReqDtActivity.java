package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.adapter.ReqWorksListAdater;
import com.gohoc.xiupuling.adapter.WorksDetails;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushOrderConfimResultBean;
import com.gohoc.xiupuling.bean.ReqListBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.constant.MyApp;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.PreviewWActivity;
import com.gohoc.xiupuling.ui.order.OrderBuyCopyrightownActivity;
import com.gohoc.xiupuling.ui.push.PushSelectMenuActivity;
import com.gohoc.xiupuling.ui.terminal.PlayListActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;

public class ReqDtActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.list)
    RecyclerView list;
    private ReqListBean.DataBean.ListBean worksList;
    private ReqWorksListAdater reqWorksListAdater;
    private PushNormlBean pushNormlBean;
    private View headView;
    private TextView titleVTv;
    private TextView favourableVTv;
    private TextView adressTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_dt);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("作品一览");
        headView = LayoutInflater.from(this).inflate(R.layout.item_req_dt_head, (ViewGroup) list.getParent(), false);

        titleVTv = (TextView) headView.findViewById(R.id.v_title_v_tv);
        favourableVTv = (TextView) headView.findViewById(R.id.favourable_v_tv);
        adressTv = (TextView) headView.findViewById(R.id.adress_tv);


        try {
            worksList = (ReqListBean.DataBean.ListBean) getIntent().getExtras().get("reqListBean");
            initDatas();
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        MyApp.getActivityList().add(this);
    }

    private void initDatas() {
        titleVTv.setText(worksList.getActivity_title() + "");
        favourableVTv.setText(TextUtils.isEmpty(worksList.getActivity_brand()) ? "无关联活动" : worksList.getActivity_brand());
        adressTv.setText(TextUtils.isEmpty(worksList.getActivity_nav_content()) ? "无" : worksList.getActivity_nav_content());
        initList();
    }

    private void initList() {
        reqWorksListAdater = new ReqWorksListAdater(R.layout.item_works, worksList.getWorklist(), this);
        reqWorksListAdater.addHeaderView(headView);
        list.setAdapter(reqWorksListAdater);
        list.setLayoutManager(new LinearLayoutManager(this));
        reqWorksListAdater.setOnItemClick(new ReqWorksListAdater.OnWorkerClicker() {
            @Override
            public void onClickReqListenter(View v, ReqListBean.DataBean.ListBean.WorklistBean worklistBean) {
                if (worklistBean.getCopyright_price() > 0 && worklistBean.getIscopyrightown().equals("NO"))
                    showBuyDialogs(worklistBean);
                else
                    showDialogs(worklistBean);
            }

            @Override
            public void onClickConactdsListernter(View v, ReqListBean.DataBean.ListBean.WorklistBean worklistBean) {
                startActivity(new Intent(ReqDtActivity.this, ReqContactsdsActivity.class).putExtra("rq_id", worksList.getRq_id()).putExtra("work_id", worklistBean.getWork_id()));
            }
        });
    }

    public void showDialogs(final ReqListBean.DataBean.ListBean.WorklistBean worklistBean) {
        BGAAlertController alertController = new BGAAlertController(this, "", "", BGAAlertController.AlertControllerStyle.ActionSheet);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
        alertController.addAction(new BGAAlertAction("立即投放", BGAAlertAction.AlertActionStyle.Default, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                getPushBean(worklistBean);

                startActivity(new Intent(ReqDtActivity.this, PushSelectMenuActivity.class).putExtra("pushNormlBean", pushNormlBean));
            }
        }));
        alertController.addAction(new BGAAlertAction("加入投放列表", BGAAlertAction.AlertActionStyle.Default, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                createInfoDistribute(worksList.getRq_id(), worklistBean.getWork_id());
            }
        }));
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.setCancelable(true);
        alertController.show();
    }

    private void getPushBean(ReqListBean.DataBean.ListBean.WorklistBean worklistBean) {
        pushNormlBean = new PushNormlBean();
        pushNormlBean.setIndexFlage(3);
        pushNormlBean.setOrientation(worksList.getRq_orientation());
        pushNormlBean.setRq_id(worksList.getRq_id());
        pushNormlBean.setName(worksList.getActivity_title() + "");
        pushNormlBean.setWork_id(worklistBean.getWork_id());
        pushNormlBean.setPlaytime(worklistBean.getPlaytime());
        pushNormlBean.setCover_url(worklistBean.getMaterial_store_url());
    }

    private void createInfoDistribute(String rq_id, String work_id) {
        RxRetrofitClient.getInstance(this).createInfoDistribute(rq_id, work_id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ReqDtActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(ReqDtActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                    Intent intent = new Intent(ReqDtActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    EventBus.getDefault().post(new Event.MainIndex(4));
                    finish();
                }

            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        finish();
    }

    private void buyworkcopyrightinit(String work_id, String rq_id, final ReqListBean.DataBean.ListBean.WorklistBean worklistBean) {
        RxRetrofitClient.getInstance(this).buyworkcopyrightinit(work_id, rq_id, new Observer<PushOrderConfimResultBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ReqDtActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushOrderConfimResultBean pushOrderConfimResultBean) {
                if (pushOrderConfimResultBean.getCode() == 1) {
                    getPushBean(worklistBean);
                    startActivity(new Intent(ReqDtActivity.this, OrderBuyCopyrightownActivity.class)
                            .putExtra("resultBean", pushOrderConfimResultBean)
                            .putExtra("pushNormlBean", pushNormlBean)
                    );
                } else {
                    Utils.toast(ReqDtActivity.this, pushOrderConfimResultBean.getMessage());
                }
            }
        });

    }

    public void showBuyDialogs(final ReqListBean.DataBean.ListBean.WorklistBean worklistBean) {
        new AlertView(null, null, "取消", null,
                new String[]{"购买发布版权 ￥" + ((int) worklistBean.getCopyright_price()) + "元"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    buyworkcopyrightinit(worklistBean.getWork_id(), worksList.getRq_id(), worklistBean);
                }
            }
        }).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }
}
