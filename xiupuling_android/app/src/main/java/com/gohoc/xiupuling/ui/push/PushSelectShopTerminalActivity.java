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
import com.gohoc.xiupuling.bean.TerminalListBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.FlowLayoutManager;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushSelectShopTerminalActivity extends BasicActivity {


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
    private PushSelectShopTerminalActivity.TradAdater tradAdater;
    private ArrayList<TerminalListBean.DataBean> selectArrryList;
    private PushSelectShopTerminalActivity.TradSelectAdater tradSelectAdater;
    private TerminalListBean terminalListBeans;
    private int maxCount = 3;
    private String shopId;
    private String shopName;
    private int orientation;
    private PushGroupMenuBean pushGroupMenuBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_select_shop_terminal);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("选择终端");
        toolbarRight.setText("完成");
        locationTv.setText("我的终端");
        orientation = getIntent().getIntExtra("orientation", 0);
        selectArrryList = (ArrayList<TerminalListBean.DataBean>) getIntent().getExtras().get("selectArrryList");
        pushGroupMenuBean = (PushGroupMenuBean) getIntent().getExtras().get("pushGroupMenuBean");
        shopName = getIntent().getStringExtra("shopName");
        if (selectArrryList == null)
            selectArrryList = new ArrayList<>();

        shopId = getIntent().getStringExtra("shopId");
        getShopTermList(shopId);
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

    public void getShopTermList(String id) {
        RxRetrofitClient.getInstance(PushSelectShopTerminalActivity.this).getShopTermList(id, String.valueOf(orientation), new Observer<TerminalListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushSelectShopTerminalActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TerminalListBean terminalListBean) {
                if (terminalListBean.getCode() == 1) {
                    terminalListBeans = terminalListBean;
                    initList();
                }


            }
        });
    }

    private void initList() {
        ArrayList<TerminalListBean.DataBean> tempList = new ArrayList<>();
        tempList.addAll(terminalListBeans.getData());
        //去除不符合横竖屏的终端
        for (TerminalListBean.DataBean bsdb : terminalListBeans.getData())
            if (bsdb.getTerm_orientation() != orientation)
                tempList.remove(bsdb);
        //判断是否选中
        if (selectArrryList.size() > 0  && shopId.equals(pushGroupMenuBean.getShopId()))
            for (TerminalListBean.DataBean bsdb : tempList) {
                for (TerminalListBean.DataBean sedb : selectArrryList)
                    if (bsdb.getTerminal_id().equals(sedb.getTerminal_id()))
                        bsdb.setCheck(true);
            }
        terminalListBeans.setData(tempList);
        maxCount = terminalListBeans.getData().size();
        locationTv.setText("我的终端 " + (orientation == 0 ? "横屏" : "竖屏") + "(" + terminalListBeans.getData().size() + ")");

        countTv.setText(selectArrryList.size() + "/" + maxCount);
        tradAdater = new PushSelectShopTerminalActivity.TradAdater(R.layout.item_simple_clickbox2, terminalListBeans.getData());
        tradList.setAdapter(tradAdater);
        tradList.setLayoutManager(new LinearLayoutManager(PushSelectShopTerminalActivity.this));
        tradAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (terminalListBeans.getData().get(i).isCheck()) {
                    selectArrryList.remove(terminalListBeans.getData().get(i));
                    terminalListBeans.getData().get(i).setCheck(false);
                } else if (selectArrryList.size() < maxCount) {
                    selectArrryList.add(terminalListBeans.getData().get(i));
                    terminalListBeans.getData().get(i).setCheck(true);
                } else
                    Utils.toast(PushSelectShopTerminalActivity.this, "最多允许选择" + maxCount + "个");

                countTv.setText(selectArrryList.size() + "/" + maxCount);
                tradAdater.notifyDataSetChanged();
                tradSelectAdater.notifyDataSetChanged();
            }
        });

        tradSelectAdater = new PushSelectShopTerminalActivity.TradSelectAdater(R.layout.item_select_flage, selectArrryList);
        selectList.setAdapter(tradSelectAdater);
        selectList.setLayoutManager(new FlowLayoutManager());
        tradSelectAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                for (int a = 0; a < terminalListBeans.getData().size(); a++) {
                    if (terminalListBeans.getData().get(a).getTerminal_id().equals(selectArrryList.get(i).getTerminal_id())) {
                        terminalListBeans.getData().get(a).setCheck(false);
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

        pushGroupMenuBean.setShopTerminalList(selectArrryList);
        pushGroupMenuBean.setTyepe(3);
        pushGroupMenuBean.setShopName(shopName);
        pushGroupMenuBean.setShopId(shopId);
        pushGroupMenuBean.setShop(null);
        setResult(RESULT_OK, new Intent().putExtra("pushGroupMenuBean", pushGroupMenuBean));
        finish();
    }

    class TradAdater extends BaseQuickAdapter<TerminalListBean.DataBean, BaseViewHolder> {
        public TradAdater(int layoutResId, List<TerminalListBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TerminalListBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.menu_title, dataBean.getTerminal_no() + " " + shopName);
            if (dataBean.isCheck())
                baseViewHolder.setImageResource(R.id.right_iv, R.mipmap.icon__zhuce_tongyi);
            else
                baseViewHolder.setImageResource(R.id.right_iv, R.mipmap.icon_zhuce_butongyi);
        }
    }

    class TradSelectAdater extends BaseQuickAdapter<TerminalListBean.DataBean, BaseViewHolder> {

        public TradSelectAdater(int layoutResId, List<TerminalListBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TerminalListBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.selected_text_view, dataBean.getTerminal_no() + " " + shopName);
        }
    }


}
