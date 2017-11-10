package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushLocationShowBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushOnListActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.title_ll)
    LinearLayout titleLl;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;

    private View viewContainer;
    private String rq_id = "";
    private String work_id = "";
    private int pageNumber = 1;
    private int pageSize = 10;
    private Adater adater;
    private String shopId;
    private PushLocationShowBean pushLocationShowBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_on_list);
        ButterKnife.bind(this);
        toolbarTitle.setText("投放地点");
        setStatusColor(R.color.colorPrimary);
        String rq_id = getIntent().getStringExtra("rq_id");
        String work_id = getIntent().getStringExtra("work_id");
        shopId = getIntent().getStringExtra("shopId");
        distributeLocationList(rq_id, work_id, pageNumber + "", pageSize + "");
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    private void distributeLocationList(String rq_id, String work_id, String pageNumber, String pageSize) {
        RxRetrofitClient.getInstance(this).distributeLocationList(rq_id, work_id, pageNumber, pageSize, new Observer<PushLocationShowBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushOnListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushLocationShowBean pushLocationShowBean) {
                if (pushLocationShowBean.getCode() == 1) {
                    if (pushLocationShowBean.getData().getList() != null && pushLocationShowBean.getData().getList().size() > 0) {
                        pushLocationShowBeans = pushLocationShowBean;
                        initList(pushLocationShowBean);
                        noDateImg.setVisibility(View.GONE);
                    } else {
                        noDateImg.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
    }

    private void initList(final PushLocationShowBean pushLocationShowBean) {
        if (!TextUtils.isEmpty(shopId)) {

            List<PushLocationShowBean.DataBean.ListBean> dataBean = new ArrayList<>();
            dataBean.addAll(pushLocationShowBean.getData().getList());
            pushLocationShowBeans.getData().getList().clear();
            for (int a = 0; a < dataBean.size(); a++) {
                Logger.e(dataBean.get(a).getShop_id() + "  " + shopId);
                if (dataBean.get(a).getShop_id().trim().equals(shopId.trim()))
                    pushLocationShowBeans.getData().getList().add(dataBean.get(a));
            }
        }
        adater = new Adater(R.layout.item_push_order_list, pushLocationShowBeans.getData().getList());
        list.setAdapter(adater);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivity(new Intent(PushOnListActivity.this, PushTerminalDtActivity.class).putExtra("id", pushLocationShowBeans.getData().getList().get(i).getShop_id())
                        .putExtra("t_id", pushLocationShowBeans.getData().getList().get(i).getTerminal_id())
                        .putExtra("range_id", pushLocationShowBeans.getData().getList().get(i).getRange_id())
                );
            }
        });
    }

    class Adater extends BaseQuickAdapter<PushLocationShowBean.DataBean.ListBean, BaseViewHolder> {


        public Adater(int layoutResId, List<PushLocationShowBean.DataBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, PushLocationShowBean.DataBean.ListBean listBean) {
            baseViewHolder.setText(R.id.serial_tv, (baseViewHolder.getPosition() + 1) + "");
            baseViewHolder.setText(R.id.title_tv, listBean.getShop_name() + "");
            baseViewHolder.setText(R.id.termial_id_tv, listBean.getTerminal_no() + "");
        }
    }
}
