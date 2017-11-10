package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BusinessTypeSubAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.BusinessTypeBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SelectShopBusinessTypeSubActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private String parent_id;
    private BusinessTypeSubAdater adater;
    private String businessType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop_business_type_sub);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("行业分类");
        parent_id = this.getIntent().getStringExtra("parent_id");
        businessType = getIntent().getStringExtra("businessType");
        getBusinessTypeList();
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        SelectShopBusinessTypeSubActivity.this.finish();
        ;
    }

    private void getBusinessTypeList() {
        RxRetrofitClient.getInstance(SelectShopBusinessTypeSubActivity.this).businessCategories("2", parent_id, new Observer<BusinessTypeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SelectShopBusinessTypeSubActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(BusinessTypeBean businessTypeBean) {
                if (businessTypeBean.getCode() == 1) {
                    initList(businessTypeBean);
                }
            }


        });

    }

    private void initList(final BusinessTypeBean businessTypeBean) {
        adater = new BusinessTypeSubAdater(SelectShopBusinessTypeSubActivity.this, businessTypeBean);
        list.setAdapter(adater);
        list.setLayoutManager(new LinearLayoutManager(SelectShopBusinessTypeSubActivity.this));
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        adater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                setResult(RESULT_OK, new Intent().putExtra("business_id", businessTypeBean.getData().get(position).getBusiness_id() + "")
                        .putExtra("business_name", businessTypeBean.getData().get(position).getBusiness_name() + "")
                        .putExtra("businessType", businessType));

                SelectShopBusinessTypeSubActivity.this.finish();
                Logger.d(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
