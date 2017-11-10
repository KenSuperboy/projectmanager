package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushHistory;
import com.gohoc.xiupuling.bean.PushHistoryDtBean;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.PreviewWActivity;
import com.gohoc.xiupuling.ui.push.PushLocationActivity;
import com.gohoc.xiupuling.ui.push.PushSelectMenuActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushHistoryDtActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.wk_covers_iv)
    ImageView wkCoversIv;
    @BindView(R.id.wk_title_tv)
    TextView wkTitleTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.terinal_c_tv)
    TextView terinalCTv;
    @BindView(R.id.w_c_tv)
    TextView wCTv;
    @BindView(R.id.r_c_tv)
    TextView rCTv;
    @BindView(R.id.push_lv)
    LinearLayout pushLv;
    @BindView(R.id.pr_lv)
    LinearLayout prLv;
    PushHistoryDtBean pushHistoryDtBeans;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.wk_activity_tv)
    TextView wkActivityTv;
    private PushNormlBean pushNormlBean = new PushNormlBean();
    private PushHistory.DataBean pushHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_history_dt);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("历史展示信息");
        historyrangemarketdetail(getIntent().getStringExtra("info_id"));
        pushHistory = (PushHistory.DataBean) getIntent().getExtras().get("pushHistory");
        pushNormlBean.setIndexFlage(4);
        pushNormlBean.setCover_url(pushHistory.getMaterial_store_url());
        pushNormlBean.setOrientation(pushHistory.getOrientation());
        pushNormlBean.setRq_id(pushHistory.getRq_id());
        pushNormlBean.setName(pushHistory.getActivity_title());
        pushNormlBean.setWork_id(pushHistory.getWork_id());
        pushNormlBean.setPlaytime(pushHistory.getPlaytime());
    }

    @OnClick({R.id.toolbar_left_title, R.id.push_lv, R.id.pr_lv, R.id.button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.push_lv:
                startActivity(new Intent(PushHistoryDtActivity.this, PushLocationActivity.class).putExtra("rq_id", pushHistoryDtBeans.getData().getRq_id()).putExtra("work_id", pushHistoryDtBeans.getData().getWork_id()));
                break;
            case R.id.pr_lv:
                startActivity(new Intent(PushHistoryDtActivity.this, PreviewWActivity.class).putExtra("work_id", pushHistoryDtBeans.getData().getWork_id()));
                break;
            case R.id.button_ll:
                startActivity(new Intent(PushHistoryDtActivity.this, PushSelectMenuActivity.class).putExtra("pushNormlBean", pushNormlBean));
                break;
        }
    }


    private void historyrangemarketdetail(String info_id) {
        RxRetrofitClient.getInstance(this).historyrangemarketdetail(info_id, new Observer<PushHistoryDtBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushHistoryDtActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushHistoryDtBean pushHistoryDtBean) {
                if (pushHistoryDtBean.getCode() == 1) {
                    pushHistoryDtBeans = pushHistoryDtBean;
                    initDates(pushHistoryDtBean);
                }


            }
        });
    }

    private void initDates(PushHistoryDtBean pushHistoryDtBean) {
        Glide.with(this)
                .load(NetConstant.BASE_USER_RESOURE + pushHistoryDtBean.getData().getMaterial_store_url())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(wkCoversIv);

        wkTitleTv.setText(pushHistoryDtBean.getData().getActivity_title());
        timeTv.setText(pushHistoryDtBean.getData().getPlaytime() + "");
        terinalCTv.setText(pushHistoryDtBean.getData().getFinish_terminal_cnt() + "个");

        wCTv.setText("每终端平均跨" + pushHistoryDtBean.getData().getFinish_week_per_term() + "周");
        rCTv.setText("总计轮播" + pushHistoryDtBean.getData().getFinish_cycle_cnt() + "次");
        wkActivityTv.setText(TextUtils.isEmpty(pushHistoryDtBean.getData().getActivity_content())?"":pushHistoryDtBean.getData().getActivity_content());
    }
}
