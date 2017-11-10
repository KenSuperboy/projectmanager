package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.CouponsAdater;
import com.gohoc.xiupuling.bean.CouponBean;
import com.gohoc.xiupuling.bean.CouponSectionBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class CardCouponsActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private static int ADDREQUEST_RESULT = 1000;
    List<CouponSectionBean> datas = new ArrayList<CouponSectionBean>();
    private CouponsAdater multipleItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_coupons);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("卡券包");
        toolbarRight.setText("添加");
        initList();
        mynewpwdcouponlist();
    }


    private void initList() {
        multipleItemAdapter = new CouponsAdater(this, R.layout.item_coupons, R.layout.item_coupons_head, datas);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(multipleItemAdapter);
        multipleItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (datas.get(i).getCouponBean().getStatus() == 1)
                    usenewpwdcoupon(datas.get(i).getCouponBean().getIds());
            }
        });
    }


    private void mynewpwdcouponlist() {
        RxRetrofitClient.getInstance(this).mynewpwdcouponlist(new Observer<CouponBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CardCouponsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(CouponBean couponBean) {
                if (couponBean.getCode() == 1) {
                    if (null != couponBean.getData() && couponBean.getData().size() > 0) {
                        noDataLv.setVisibility(View.GONE);
                        list.setVisibility(View.VISIBLE);

                        datas.clear();
                        multipleItemAdapter.getData().clear();
                        for (int a = 0; a < couponBean.getData().size(); a++) {
                            if (couponBean.getData().get(a).getStatus() == 1)
                                datas.add(new CouponSectionBean(couponBean.getData().get(a)));
                        }
                        datas.add(new CouponSectionBean(true, "已失效优惠券", true));
                        for (int a = 0; a < couponBean.getData().size(); a++) {
                            if (couponBean.getData().get(a).getStatus() != 1)
                                datas.add(new CouponSectionBean(couponBean.getData().get(a)));
                        }
                        multipleItemAdapter.notifyDataSetChanged();

                    } else {
                        noDataLv.setVisibility(View.VISIBLE);
                        list.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void usenewpwdcoupon(String id) {
        RxRetrofitClient.getInstance(this).usenewpwdcoupon(id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CardCouponsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(CardCouponsActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    Credential.getInstance().updateUserInfo(CardCouponsActivity.this);
                    mynewpwdcouponlist();
                }


            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                startActivityForResult(new Intent(CardCouponsActivity.this, AddCouponsActivity.class), ADDREQUEST_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mynewpwdcouponlist();
        }
    }
}
