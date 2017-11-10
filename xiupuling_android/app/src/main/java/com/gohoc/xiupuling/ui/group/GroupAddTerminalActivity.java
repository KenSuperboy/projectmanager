package com.gohoc.xiupuling.ui.group;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.GroupTerminalCheckAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class GroupAddTerminalActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    private GroupDetailsBean groupDetailsBeans;
    private GroupTerminalCheckAdater adater;
    private GroupTermianlListBean groupTermianlListBeans;
    private int type;//type:如果等于10的话，标示联动群组进来的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add_terminal);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("添加终端");
        if (getIntent().getIntExtra("type", 0) == 10) {
            //联动群组
            getUnUnionTermList(getIntent().getStringExtra("id"));
        } else {
            //其他类型的群组
            try {
                groupDetailsBeans = (GroupDetailsBean) getIntent().getExtras().get("groupDetailsBeans");
                getUnUnionTermList(groupDetailsBeans.getData().getUnion_id());
                if (getIntent().getIntExtra("type", 0) == 1) {
                    toolbarLeftTitle.setText("以后再说");

                    Drawable drawable = getResources().getDrawable(R.mipmap.icon_return_left);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    toolbarLeftTitle.setCompoundDrawables(drawable, null, null, null);
                }
            } catch (Exception e) {
            }
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                if (getIntent().getIntExtra("type", 0) == 1) {
                    setResult(RESULT_OK);
                }
                finish();
                break;
            case R.id.toolbar_right:
                addMultiTermsToUnion();
                break;
        }
    }


    private void getUnUnionTermList(String union_id) {


        RxRetrofitClient.getInstance(GroupAddTerminalActivity.this).getUnUnionTermList(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupAddTerminalActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1&&groupTermianlListBean.getData()!=null&&groupTermianlListBean.getData().size()>0) {
                    mScrollView.setVisibility(View.VISIBLE);
                    mTvEmpty.setVisibility(View.GONE);
                    groupTermianlListBeans = groupTermianlListBean;
                    initMineList();
                }else {
                    mScrollView.setVisibility(View.GONE);
                    mTvEmpty.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void initMineList() {
        adater = new GroupTerminalCheckAdater(GroupAddTerminalActivity.this, groupTermianlListBeans);
        recyclerView.setAdapter(adater);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupAddTerminalActivity.this));
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        adater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                groupTermianlListBeans.getData().get(position).setCheck(!groupTermianlListBeans.getData().get(position).isCheck());
                adater.notifyItemChanged(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void addMultiTermsToUnion() {
        StringBuffer sb = new StringBuffer();

        for (int a = 0; a < groupTermianlListBeans.getData().size(); a++) {
            if (groupTermianlListBeans.getData().get(a).isCheck())
                sb.append(groupTermianlListBeans.getData().get(a).getTerminal_id() + ",");
        }
        if (sb.length() < 1) {
            Utils.toast(this, "请选择需要加入的终端");
            return;
        }
        LogUtil.d("各种终端：" + groupDetailsBeans.getData().getUnion_id() + "======:" + sb.toString());

        RxRetrofitClient.getInstance(GroupAddTerminalActivity.this).addMultiTermsToUnion(groupDetailsBeans.getData().getUnion_id() + "", sb.toString(), new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupAddTerminalActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {

                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                    setResult(RESULT_OK);
                    GroupAddTerminalActivity.this.finish();
                } else
                    Utils.toast(GroupAddTerminalActivity.this, vCodeBenan.getMessage());

            }
        });
    }
}
