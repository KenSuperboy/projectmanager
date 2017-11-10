package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.bean.PushGroupMenuBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.FlowLayoutManager;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushSelectGroupTerminalActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.count_tv)
    TextView countTv;
    @BindView(R.id.state_tv)
    TextView stateTv;
    @BindView(R.id.arr_iv)
    ImageView arrIv;
    @BindView(R.id.menu_ll)
    LinearLayout menuLl;
    @BindView(R.id.select_list)
    RecyclerView selectList;
    @BindView(R.id.trad_list)
    RecyclerView tradList;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private PushSelectGroupTerminalActivity.TradAdater tradAdater;
    private ArrayList<GroupTermianlListBean.DataBean> selectArrryList;
    private PushSelectGroupTerminalActivity.TradSelectAdater tradSelectAdater;
    private GroupTermianlListBean groupTermianlListBeans;
    private PushGroupMenuBean pushGroupMenuBean;
    private int maxCount = 3;
    private String groupId;
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_select_terminal2);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("选择终端");
        toolbarRight.setText("完成");
        // locationTv.setText("我的终端");
        selectArrryList = (ArrayList<GroupTermianlListBean.DataBean>) getIntent().getExtras().get("selectArrryList");
        pushGroupMenuBean = (PushGroupMenuBean) getIntent().getExtras().get("pushGroupMenuBean");

        orientation = getIntent().getIntExtra("orientation", 0);
        if (selectArrryList == null)
            selectArrryList = new ArrayList<>();

        groupId = getIntent().getStringExtra("groupId");
        LogUtil.d("多少groupId："+groupId);
        if (pushGroupMenuBean.getGroupTermianlType() == 1||pushGroupMenuBean.getGroupTermianlType() == 2)
            getUnionTerminalList(groupId);
        else
            getUnionTerminalListofTheirs(groupId);
    }

    @OnClick({R.id.toolbar_left_title, R.id.menu_ll, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                goBack();
                break;
            case R.id.menu_ll:
                if (selectList.getVisibility() == View.VISIBLE) {
                    stateTv.setText("展开");
                    arrIv.setImageResource(R.mipmap.icon_explain_close2);
                    selectList.setVisibility(View.GONE);
                } else {
                    stateTv.setText("收起");
                    arrIv.setImageResource(R.mipmap.icon_hangyeleixingl_shouqi);
                    selectList.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void getUnionTerminalList(String union_id) {
        RxRetrofitClient.getInstance(PushSelectGroupTerminalActivity.this).getUnionTerminalListofMine(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushSelectGroupTerminalActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1) {
                    groupTermianlListBeans = groupTermianlListBean;
                    initList();
                }

            }
        });

    }

    private void getUnionTerminalListofTheirs(String union_id) {


        RxRetrofitClient.getInstance(this).getUnionTerminalListofTheirs(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushSelectGroupTerminalActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1) {
                    groupTermianlListBeans = groupTermianlListBean;
                    initList();
                }

            }
        });

    }

    private void initList() {
        ArrayList<GroupTermianlListBean.DataBean> tempList = new ArrayList<>();
        tempList.addAll(groupTermianlListBeans.getData());

        //去除不符合横竖屏的终端
        for (GroupTermianlListBean.DataBean bsdb : groupTermianlListBeans.getData())
            if (bsdb.getTerm_orientation() != orientation)
                tempList.remove(bsdb);
        //判断是否选中
        if (selectArrryList.size() > 0 )
            for (GroupTermianlListBean.DataBean bsdb : tempList) {
                for (GroupTermianlListBean.DataBean sedb : selectArrryList)
                    if (bsdb.getTerminal_id().equals(sedb.getTerminal_id()))
                        bsdb.setCheck(true);
            }
        groupTermianlListBeans.setData(tempList);
        maxCount = groupTermianlListBeans.getData().size();
        locationTv.setText((pushGroupMenuBean.getGroupTermianlType() == 1 ? "我的终端 " : "别人的终端 ") + (orientation == 0 ? "横屏" : "竖屏") + "(" + groupTermianlListBeans.getData().size() + ")");

        countTv.setText(selectArrryList.size() + "/" + maxCount);
        tradAdater = new PushSelectGroupTerminalActivity.TradAdater(R.layout.item_simple_clickbox2, groupTermianlListBeans.getData());
        tradList.setAdapter(tradAdater);
        tradList.setLayoutManager(new LinearLayoutManager(PushSelectGroupTerminalActivity.this));
        tradAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (groupTermianlListBeans.getData().get(i).isCheck()) {
                    selectArrryList.remove(groupTermianlListBeans.getData().get(i));
                    groupTermianlListBeans.getData().get(i).setCheck(false);
                } else if (selectArrryList.size() < maxCount) {
                    selectArrryList.add(groupTermianlListBeans.getData().get(i));
                    groupTermianlListBeans.getData().get(i).setCheck(true);
                } else
                    Utils.toast(PushSelectGroupTerminalActivity.this, "最多允许选择" + maxCount + "个");

                countTv.setText(selectArrryList.size() + "/" + maxCount);
                tradAdater.notifyDataSetChanged();
                tradSelectAdater.notifyDataSetChanged();
            }
        });

        tradSelectAdater = new PushSelectGroupTerminalActivity.TradSelectAdater(R.layout.item_select_flage, selectArrryList);
        selectList.setAdapter(tradSelectAdater);
        selectList.setLayoutManager(new FlowLayoutManager());
        tradSelectAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                for (int a = 0; a < groupTermianlListBeans.getData().size(); a++) {
                    if (groupTermianlListBeans.getData().get(a).getTerminal_id().equals(selectArrryList.get(i).getTerminal_id())) {
                        groupTermianlListBeans.getData().get(a).setCheck(false);
                    }
                }
                selectArrryList.remove(i);
                tradAdater.notifyDataSetChanged();
                tradSelectAdater.notifyDataSetChanged();
                countTv.setText(selectArrryList.size() + "/" + maxCount);
            }
        });
    }

    private void goBack() {
        if (selectArrryList == null || selectArrryList.size() < 1) {
            Utils.toast(this, "至少选择一个终端");
            return;
        }
        pushGroupMenuBean.setGroupTerminalList(selectArrryList);
        pushGroupMenuBean.setTyepe(2);
        // pushGroupMenuBean.setUnion(null);
        setResult(RESULT_OK, new Intent().putExtra("pushGroupMenuBean", pushGroupMenuBean));
        finish();
    }

    class TradAdater extends BaseQuickAdapter<GroupTermianlListBean.DataBean, BaseViewHolder> {
        public TradAdater(int layoutResId, List<GroupTermianlListBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, GroupTermianlListBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.menu_title, dataBean.getTerminal_no() + " " + dataBean.getShop_name());
            if (dataBean.isCheck())
                baseViewHolder.setImageResource(R.id.right_iv, R.mipmap.icon__zhuce_tongyi);
            else
                baseViewHolder.setImageResource(R.id.right_iv, R.mipmap.icon_zhuce_butongyi);
        }
    }

    class TradSelectAdater extends BaseQuickAdapter<GroupTermianlListBean.DataBean, BaseViewHolder> {

        public TradSelectAdater(int layoutResId, List<GroupTermianlListBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, GroupTermianlListBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.selected_text_view, dataBean.getTerminal_no() + " " + dataBean.getShop_name());
        }
    }
}
