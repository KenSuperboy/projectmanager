package com.gohoc.xiupuling.ui.push;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushReqShowBean;
import com.gohoc.xiupuling.bean.ReqBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushReqShowCountActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.wk_covers_iv)
    ImageView wkCoversIv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.line2_1_tv)
    TextView line21Tv;
    @BindView(R.id.line2_2_tv)
    TextView line22Tv;
    @BindView(R.id.line2_3_tv)
    TextView line23Tv;
    @BindView(R.id.line3_1_tv)
    TextView line31Tv;
    @BindView(R.id.line3_2_tv)
    TextView line32Tv;
    @BindView(R.id.line3_3_tv)
    TextView line33Tv;
    @BindView(R.id.line4_1_tv)
    TextView line41Tv;
    @BindView(R.id.line4_2_tv)
    TextView line42Tv;
    @BindView(R.id.line4_3_tv)
    TextView line43Tv;
    @BindView(R.id.wk_title_tv)
    TextView wkTitleTv;
    @BindView(R.id.wk_activity_tv)
    TextView wkActivityTv;
    @BindView(R.id.content_rl)
    RelativeLayout contentRl;
    @BindView(R.id.no_push_iv)
    ImageView noPushIv;

    private String work_id;
    private String rq_id;
    private ReqBean.DataBean.ListBean reqBeanTemp;
    private PushNormlBean pushNormlBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_show_count);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("展示效果");
        work_id = getIntent().getStringExtra("work_id");
        rq_id = getIntent().getStringExtra("rq_id");
        reqBeanTemp = (ReqBean.DataBean.ListBean) getIntent().getExtras().get("reqBeanTemp");
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        workShowDetail(rq_id, work_id);
    }


    private void workShowDetail(String rq_id, String work_id) {
        RxRetrofitClient.getInstance(this).workShowDetail(rq_id, work_id, new Observer<PushReqShowBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqShowCountActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushReqShowBean pushReqShowBean) {
                if (pushReqShowBean.getCode() == 1) {

                    Glide.with(PushReqShowCountActivity.this)
                            .load(NetConstant.BASE_USER_RESOURE + pushNormlBean.getCover_url())
                            // .placeholder(R.mipmap.icon_dengru_touxiang)
                            //.error(R.mipmap.icon_dengru_touxiang)

                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(wkCoversIv);
                    wkTitleTv.setText(pushNormlBean.getName());
                    if (!TextUtils.isEmpty(pushNormlBean.getActivity_content()))
                        wkActivityTv.setText(pushNormlBean.getActivity_content());


                    if (pushReqShowBean.getData() != null) {//有投放内容情况
                        if (reqBeanTemp != null)
                            timeTv.setText(reqBeanTemp.getPlaytime() + "");
                        else
                            timeTv.setText(pushNormlBean.getPlaytime() + "");


                        int vFinsh1 = 0, vFinsh2 = 0, vFinsh3 = 0, vUnFinsh1 = 0, vUnFinsh2 = 0, vUnFinsh3 = 0;

                        if (pushReqShowBean.getData() == null)
                            return;

             /*       finish_terminal_cnt： 已完成周 接单终端数
                    finish_week_per_term： 已完成周 平均跨周数
                    finish_cycle_cnt： 已完成周 播出次数

                    unfinish_terminal_cnt： 未完成周 接单终端数
                    unfinish_week_per_term： 未完成周 平均跨周数
                    unfinish_cycle_cnt： 未完成周 播出次数

                    达成协议的 接单终端数 =（已完成周 接单终端数+未完成周 接单终端数）／2 取整
                    达成协议的平均跨周数=（已完成周平均跨周数+未完成周 平均跨周数）／2
                    达成协议的播出次数=已完成周 播出次数+未完成周播出次数*/


                        vFinsh1 = pushReqShowBean.getData().getFinish_terminal_cnt();       // 已完成周 接单终端数
                        vFinsh2 = (int) pushReqShowBean.getData().getFinish_week_per_term();  //已完成周 平均跨周数
                        vFinsh3 = pushReqShowBean.getData().getFinish_cycle_cnt();          //已完成周 播出次数

                        vUnFinsh1 = pushReqShowBean.getData().getUnfinish_terminal_cnt();      //未完成周 接单终端数
                        vUnFinsh2 = (int) pushReqShowBean.getData().getUnfinish_week_per_term();   //未完成周 平均跨周数
                        vUnFinsh3 = pushReqShowBean.getData().getUnfinish_cycle_cnt();         //未完成周 播出次数


                        //第一列
                        if ((vFinsh1 + vUnFinsh1) / 2.0 > 0)
                            // line21Tv.setText((double) (Math.round((vFinsh1 + vUnFinsh1) / 2.0)) + "");
                            line21Tv.setText(pushReqShowBean.getData().getTotal_terminal_cnt() + "");
                        if ((vFinsh2 + vUnFinsh2) / 2.0 > 0)
                            // line31Tv.setText((double) (Math.round((vFinsh2 + vUnFinsh2) / 2.0)) + "");  //
                            line31Tv.setText(pushReqShowBean.getData().getTotal_week_per_term() + "");  //
                        if (vFinsh3 + vUnFinsh3 > 0)
                            // line41Tv.setText(Math.floor(vFinsh3 + vUnFinsh3) + "");
                            line41Tv.setText(pushReqShowBean.getData().getTotal_cycle_cnt() + "");
                        //第二列
                        if (vFinsh1 > 0)
                            line22Tv.setText(Math.floor(vFinsh1) + "");
                        if (vFinsh2 > 0)
                            line32Tv.setText(Math.floor(vFinsh2) + "");
                        if (vFinsh3 > 0)
                            line42Tv.setText(Math.floor(vFinsh3) + "");
                        //第三列
                        if (vUnFinsh1 > 0)
                            line23Tv.setText(Math.floor(vUnFinsh1) + "");
                        if (vUnFinsh2 > 0)
                            line33Tv.setText(Math.floor(vUnFinsh2) + "");
                        if (vUnFinsh3 > 0)
                            line43Tv.setText(Math.floor(vUnFinsh3) + "");
                        contentRl.setVisibility(View.VISIBLE);
                        noPushIv.setVisibility(View.GONE);
                    } else {
                        contentRl.setVisibility(View.GONE);
                        noPushIv.setVisibility(View.VISIBLE);
                    }


                }

            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }
}
