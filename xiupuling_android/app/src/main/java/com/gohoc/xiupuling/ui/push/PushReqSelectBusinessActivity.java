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
import com.gohoc.xiupuling.bean.BusinessBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
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

public class PushReqSelectBusinessActivity extends BasicActivity {

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
    private MenuLocationBean menuLocationBean;
    private TradAdater tradAdater;
    private ArrayList<BusinessBean.DataBean> selectBussList;
    private TradSelectAdater tradSelectAdater;
    private BusinessBean businessBeans;
    private int maxCount = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_select_business);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("商圈");
        toolbarRight.setText("完成");
        menuLocationBean = (MenuLocationBean) getIntent().getExtras().get("menuLocationBean");
        locationTv.setText(menuLocationBean.getProvince() + menuLocationBean.getCity() + menuLocationBean.getDistrict());
        selectBussList = (ArrayList<BusinessBean.DataBean>) getIntent().getExtras().get("selectBussList");
        if (selectBussList == null)
            selectBussList = new ArrayList<>();
        getGroup();
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

    private void getGroup() {
        RxRetrofitClient.getInstance(this).getGroup(menuLocationBean.getDistrictId(), "1", new Observer<BusinessBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqSelectBusinessActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(BusinessBean businessBean) {
                if (businessBean.getCode() == 1) {
                    businessBeans = businessBean;
                    initList();
                }
            }
        });
    }

    private void initList() {
        if (selectBussList.size() > 0)
            for (BusinessBean.DataBean bsdb : businessBeans.getData())
                for (BusinessBean.DataBean sedb : selectBussList)
                    if (bsdb.equals(sedb))
                        bsdb.setSelect(true);

        countTv.setText(selectBussList.size() + "/" + maxCount);
        tradAdater = new TradAdater(R.layout.item_simple_clickbox2, businessBeans.getData());
        tradList.setAdapter(tradAdater);
        tradList.setLayoutManager(new LinearLayoutManager(PushReqSelectBusinessActivity.this));
        tradAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (businessBeans.getData().get(i).isSelect()) {
                    selectBussList.remove(businessBeans.getData().get(i));
                    businessBeans.getData().get(i).setSelect(false);
                } else if (selectBussList.size() < maxCount) {
                    selectBussList.add(businessBeans.getData().get(i));
                    businessBeans.getData().get(i).setSelect(true);
                } else
                    Utils.toast(PushReqSelectBusinessActivity.this, "最多允许选择" + maxCount + "个");

                countTv.setText(selectBussList.size() + "/" + maxCount);
                tradAdater.notifyDataSetChanged();
                tradSelectAdater.notifyDataSetChanged();
            }
        });

        tradSelectAdater = new TradSelectAdater(R.layout.item_select_flage, selectBussList);
        selectList.setAdapter(tradSelectAdater);
        selectList.setLayoutManager(new FlowLayoutManager());
        tradSelectAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                for (int a = 0; a < businessBeans.getData().size(); a++) {
                    if (businessBeans.getData().get(a).equals(selectBussList.get(i))) {
                        businessBeans.getData().get(a).setSelect(false);
                    }
                }
                selectBussList.remove(i);
                tradAdater.notifyDataSetChanged();
                tradSelectAdater.notifyDataSetChanged();
            }
        });



    }

    private void goBack() {
        setResult(RESULT_OK, new Intent().putExtra("selectBussList", selectBussList));
        finish();
    }

    class TradAdater extends BaseQuickAdapter<BusinessBean.DataBean, BaseViewHolder> {
        public TradAdater(int layoutResId, List<BusinessBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, BusinessBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.menu_title, dataBean.getGroup_name());
            if (dataBean.isSelect())
                baseViewHolder.setImageResource(R.id.right_iv, R.mipmap.icon__zhuce_tongyi);
            else
                baseViewHolder.setImageResource(R.id.right_iv, R.mipmap.icon_zhuce_butongyi);
        }
    }

    class TradSelectAdater extends BaseQuickAdapter<BusinessBean.DataBean, BaseViewHolder> {

        public TradSelectAdater(int layoutResId, List<BusinessBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, BusinessBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.selected_text_view, dataBean.getGroup_name());
        }
    }
}
