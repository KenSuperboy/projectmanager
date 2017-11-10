package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BusinessTypeAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.BusinessTypeBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.push.PushIndustryActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SelectShopBusinessTypeActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private BusinessTypeAdater adater;
    private String business_id, business_name,businessType;
    private static int SUB_REQUEST_RESULT = 1000;
    private ArrayList<BusinessTypeBean.DataBean> selectBussList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop_business_type);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("行业分类");

        getBusinessTypeList();
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        SelectShopBusinessTypeActivity.this.finish();
    }

    private void getBusinessTypeList() {
        RxRetrofitClient.getInstance(SelectShopBusinessTypeActivity.this).businessCategories("1", "0", new Observer<BusinessTypeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SelectShopBusinessTypeActivity.this, "请检查网络是否正常");
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
        adater = new BusinessTypeAdater(SelectShopBusinessTypeActivity.this, businessTypeBean);
        list.setAdapter(adater);
        list.setLayoutManager(new LinearLayoutManager(SelectShopBusinessTypeActivity.this));
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        adater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (TextUtils.isEmpty(getIntent().getStringExtra("push"))) {
                    startActivityForResult(new Intent(SelectShopBusinessTypeActivity.this, SelectShopBusinessTypeSubActivity.class)
                            .putExtra("parent_id", businessTypeBean.getData().get(position).getBusiness_id() + "").putExtra("businessType", businessTypeBean.getData().get(position).getBusiness_name()), SUB_REQUEST_RESULT);
                } else {
                    startActivityForResult(new Intent(SelectShopBusinessTypeActivity.this, PushIndustryActivity.class)
                                    .putExtra("parent_id", businessTypeBean.getData().get(position).getBusiness_id() + "")
                                    .putExtra("businessType", businessTypeBean.getData().get(position).getBusiness_name())
                            , SUB_REQUEST_RESULT);
                }

                Logger.d(businessTypeBean.getData().get(position).getBusiness_id() + "");

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUB_REQUEST_RESULT) {
            if (resultCode == RESULT_OK) {
                if (null != data) {
                    if (TextUtils.isEmpty(getIntent().getStringExtra("push"))) {
                        business_id = data.getStringExtra("business_id");
                        business_name = data.getStringExtra("business_name");
                        businessType= data.getStringExtra("businessType");
                        if (!business_id.isEmpty() && !business_name.isEmpty()) {
                            setResult(RESULT_OK, new Intent().putExtra("business_id", business_id).putExtra("business_name", business_name).putExtra("businessType",businessType));
                            SelectShopBusinessTypeActivity.this.finish();
                        }
                    } else {
                        selectBussList = (ArrayList<BusinessTypeBean.DataBean>) data.getExtras().get("selectBussTypeList");
                        setResult(RESULT_OK, new Intent().putExtra("selectBussTypeList", selectBussList));
                        SelectShopBusinessTypeActivity.this.finish();
                    }

                }
            }
        }
    }
}
