package com.gohoc.xiupuling.ui.useraudit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.audit.AuditTerminalListBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.order.OrderDetailsActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 未审核订单终端列表
* */
public class AuditTerminalListActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.relative_empty_layout)
    RelativeLayout mRelativeEmptyLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String user_id = "";
    private MyAdapter adapter;
    private AuditTerminalListBean mAuditTerminalListBean;
    private static int UPDAE_REQUEST_RESULT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_work_list_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        initView();
        getAuditTerminalList(user_id);
    }

    private void initView()
    {
        mRecyclerView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        user_id = getIntent().getStringExtra("user_id");
        mToolbarTitle.setText("未审订单");
    }

    public void setMyvisible(int data, int empty, int src, String string) {
        mListView.setVisibility(data);
        mRelativeEmptyLayout.setVisibility(empty);
        mIvEmpty.setImageResource(src);
        mTvEmpty.setText(string);//当前无待审核订单
    }

    private void getAuditTerminalList(String user_id) {
        RxRetrofitClient.getInstance(this).getAuditTerminalList(user_id, new Observer<AuditTerminalListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AuditTerminalListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(AuditTerminalListBean auditTerminalListBean) {
                if (auditTerminalListBean!=null&&auditTerminalListBean.code == 1&&auditTerminalListBean.list!=null && auditTerminalListBean.list.size() > 0) {
                    mAuditTerminalListBean = auditTerminalListBean;
                    setMyvisible(View.VISIBLE,View.GONE,R.mipmap.icon_no_order_bg,"当前无待审核订单");
                    initList(auditTerminalListBean);
                } else {
                    setMyvisible(View.GONE,View.VISIBLE,R.mipmap.icon_no_order_bg,"当前无待审核订单");
                }
            }
        });
    }

    private void initList(final AuditTerminalListBean orderSBean) {
        adapter = new MyAdapter(R.layout.item_audit_terminal_layout, orderSBean.list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                startActivityForResult(new Intent(AuditTerminalListActivity.this, OrderDetailsActivity.class)
                                .putExtra("range_id", orderSBean.list.get(position).range_id)
                                .putExtra("terminal_id", orderSBean.list.get(position).terminal_id)
                                .putExtra("url", orderSBean.list.get(position).material_store_url)
                                .putExtra("type", 0)
                                .putExtra("user_id", user_id)
                                .putExtra("shenghe", true)
                                .putExtra("start", orderSBean.list.get(position).start_time+"")
                                .putExtra("end", orderSBean.list.get(position).end_time+"")
                                .putExtra("limit", orderSBean.list.get(position).is_time_limit)
                                .putExtra("ignore", orderSBean.list.get(position).is_ignore_other_work)
                                .putExtra("repeat", orderSBean.list.get(position).repeat_weekday+"")
                                .putExtra("order", orderSBean.list.get(position).order_source+"")
                        , UPDAE_REQUEST_RESULT
                );
            }
        });
    }

    @OnClick({R.id.toolbar_left_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
        }
    }

    class MyAdapter extends BaseQuickAdapter<AuditTerminalListBean.ListBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, List<AuditTerminalListBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, AuditTerminalListBean.ListBean dataBean) {

            baseViewHolder.setText(R.id.termianl_title_tv, dataBean.terminal_no + " " + dataBean.shop_name)
                    .setText(R.id.v_title_tv, dataBean.work_name + "")
                    .setText(R.id.v_time_tv, "接单于 " + Utils.formatDate(Utils.StrToDate(dataBean.create_time, "yyyy-MM-ddHH:mm:ss"), "yyyy-MM-dd"))
                    .setText(R.id.v_money_tv, "￥" + dataBean.totalamount + "元");

            RatingBar ratingbar = baseViewHolder.getView(R.id.ratingbar);
            ratingbar.setmClickable(false);
            ratingbar.setEnabled(false);
            ratingbar.setStar(dataBean.star_level);

            ImageView iv = baseViewHolder.getView(R.id.works_iv);
            Glide.with(AuditTerminalListActivity.this)
                    .load(NetConstant.BASE_USER_RESOURE + dataBean.material_store_url)
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);

            //修改增加
            ImageView iv_type = baseViewHolder.getView(R.id.iv_type);
            TextView tv_type = baseViewHolder.getView(R.id.tv_type);
            ImageView iv_down_status = baseViewHolder.getView(R.id.iv_down_status);
            LogUtil.d("标示：" + dataBean.order_source);
            //TextView tv_leader = baseViewHolder.getView(R.id.tv_leader);

            /*if (dataBean.getOrder_source() != null && dataBean.getOrder_source().equals("link")) {
                if (dataBean.getIs_leader() == 0) {
                    tv_leader.setText("跟播");
                } else if (dataBean.getIs_leader() == 1) {
                    tv_leader.setText("领播");
                } else {
                    tv_leader.setText("");
                }
            } else {
                tv_leader.setText("");
            }*/

            String playType = "";
            if (dataBean.order_source != null) {
                if (dataBean.order_source.equals("package")) {
                    iv_type.setImageResource(R.mipmap.icon_dingdan_zuhebao);
                    playType = "播放";
                } else if (dataBean.order_source.equals("link")) {
                    iv_type.setImageResource(R.mipmap.icon_dingdan_liandong);
                    playType = "播放";
                } else {
                    iv_type.setImageResource(R.mipmap.icon_dan);
                    playType = "轮播";
                }
            } else {
                playType = "轮播";
            }

            /*if (dataBean.getDownload_status() == 1) {
                iv_down_status.setImageResource(R.mipmap.icon_wancheng);
            } else {
                iv_down_status.setImageResource(R.mipmap.icon_xiazaizhong);
            }*/
            if (!dataBean.is_time_limit && dataBean.is_ignore_other_work) {
                tv_type.setText("全天候独占" + playType);
            } else {
                if (!dataBean.is_time_limit && !dataBean.is_ignore_other_work) {
                    tv_type.setText("非独占时段" + playType);
                } else {
                    if (!TextUtils.isEmpty(dataBean.repeat_weekday+"")) {
                        tv_type.setText(CustomUtils.getPlayTime(dataBean.repeat_weekday+"", dataBean.is_ignore_other_work) + " " + CustomUtils.start_end(dataBean.start_time+"", dataBean.end_time+""));
                    } else {
                        tv_type.setText("非独占时段" + playType);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getAuditTerminalList(user_id);
        }
    }
}
