package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushHistory;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.account.PushHistoryDtActivity;
import com.gohoc.xiupuling.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
/*
* 历史投放广告
* */
public class PushHistoryActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Adater adater;
    private boolean isRefresh = true;
    private int page = 1;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_history);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText(getIntent().getStringExtra("title"));
        emptyView= getLayoutInflater().inflate(R.layout.push_histroy_emty_view, (ViewGroup) recyclerView.getParent(), false);
        historyrangemarket();

    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    public void historyrangemarket() {
        RxRetrofitClient.getInstance(this).historyrangemarket(new Observer<PushHistory>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                Utils.toast(PushHistoryActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(PushHistory pushHistory) {
                if (pushHistory.getCode() == 1) {
                    initList(pushHistory);
                }

            }
        });
    }

    private void initList(final PushHistory pushHistory) {
        adater = new Adater(R.layout.item_push_history, pushHistory.getData());
        recyclerView.setAdapter(adater);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adater.setEmptyView(emptyView);
        adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                startActivity(new Intent(PushHistoryActivity.this, PushHistoryDtActivity.class)
                        .putExtra("info_id",pushHistory.getData().get(position).getInfo_id())
                        .putExtra("pushHistory",pushHistory.getData().get(position))
                        );
            }
        });
    }

    class Adater extends BaseQuickAdapter<PushHistory.DataBean, BaseViewHolder> {

        public Adater(int layoutResId, List<PushHistory.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, PushHistory.DataBean dataBean) {
            ImageView iv = baseViewHolder.getView(R.id.works_iv);

            Glide.with(PushHistoryActivity.this)
                    .load(Constant.NetConstant.BASE_USER_RESOURE + dataBean.getMaterial_store_url()+ Utils.getThumbnail(202,202))
                     .placeholder(R.mipmap.df_logo)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);
            baseViewHolder.setText(R.id.v_title_tv, dataBean.getWork_name() + "")
                    .setText(R.id.termianl_count_tv, dataBean.getTerminal_cnt() + "")
                    .setText(R.id.rp_count_tv, dataBean.getCycle_cnt() + "");

        }
    }
}
