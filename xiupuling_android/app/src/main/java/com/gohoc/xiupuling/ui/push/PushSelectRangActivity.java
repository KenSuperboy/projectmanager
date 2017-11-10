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
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.PushSelectRangAdater;
import com.gohoc.xiupuling.bean.LocationBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushSelectRangActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.province_list)
    RecyclerView provinceList;
    @BindView(R.id.right_iv)
    ImageView rightIv;
    @BindView(R.id.no_limt_ll)
    LinearLayout noLimtLl;
    @BindView(R.id.no_limt)
    LinearLayout noLimt;
    private PushSelectRangAdater adater;
    private MenuLocationBean menuLocationBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        ButterKnife.bind(this);

        setStatusColor(R.color.colorPrimary);

        try {
            menuLocationBean = (MenuLocationBean) getIntent().getExtras().get("menuLocationBean");
            if (menuLocationBean.getLeve() == 1) {
                toolbarTitle.setText("省");
                if (TextUtils.isEmpty(menuLocationBean.getProvince()))
                    rightIv.setVisibility(View.VISIBLE);
                else
                    rightIv.setVisibility(View.INVISIBLE);
                getCityList("0", "1");

            } else if (menuLocationBean.getLeve() == 2) {
                toolbarTitle.setText("市");
                if (TextUtils.isEmpty(menuLocationBean.getCity()))
                    rightIv.setVisibility(View.VISIBLE);
                else
                    rightIv.setVisibility(View.INVISIBLE);
                getCityList(menuLocationBean.getProvinceId(), "2");
            } else {
                toolbarTitle.setText("区");
                if (TextUtils.isEmpty(menuLocationBean.getDistrict()))
                    rightIv.setVisibility(View.VISIBLE);
                else
                    rightIv.setVisibility(View.INVISIBLE);
                getCityList(menuLocationBean.getCityId(), "3");
            }


        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }


    private void getCityList(String parenId, final String level) {
        RxRetrofitClient.getInstance(PushSelectRangActivity.this).getCitylist(parenId, level, new Observer<LocationBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushSelectRangActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(final LocationBean locationBean) {
                if (locationBean.getCode() == 1) {
                    for (int a = 0; a < locationBean.getData().size(); a++) {
                        if (menuLocationBean.getLeve() == 1) {
                            if ((locationBean.getData().get(a).getId() + "").equals(menuLocationBean.getProvinceId())) {
                                locationBean.getData().get(a).setCheck(true);
                            }
                        } else if (menuLocationBean.getLeve() == 2) {
                            if ((locationBean.getData().get(a).getId() + "").equals(menuLocationBean.getCityId())) {
                                locationBean.getData().get(a).setCheck(true);
                            }
                        } else {
                            if ((locationBean.getData().get(a).getId() + "").equals(menuLocationBean.getDistrictId())) {
                                locationBean.getData().get(a).setCheck(true);
                            }
                        }
                    }

                    adater = new PushSelectRangAdater(R.layout.item_simple_clickbox, locationBean.getData());
                    provinceList.setAdapter(adater);
                    provinceList.setLayoutManager(new LinearLayoutManager(PushSelectRangActivity.this));
                    adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                            if (menuLocationBean.getLeve() == 1) {
                                menuLocationBean.setProvince(locationBean.getData().get(position).getName() + "");
                                menuLocationBean.setProvinceId(locationBean.getData().get(position).getId() + "");
                            } else if (menuLocationBean.getLeve() == 2) {
                                menuLocationBean.setCity(locationBean.getData().get(position).getName() + "");
                                menuLocationBean.setCityId(locationBean.getData().get(position).getId() + "");
                            } else {
                                menuLocationBean.setDistrict(locationBean.getData().get(position).getName() + "");
                                menuLocationBean.setDistrictId(locationBean.getData().get(position).getId() + "");
                            }
                            setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBean));
                            finish();
                        }
                    });

                }
            }


        });
    }


    @OnClick({R.id.toolbar_left_title, R.id.no_limt_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.no_limt_ll:
                if (menuLocationBean.getLeve() == 1) {
                    menuLocationBean.setProvince(null);
                    menuLocationBean.setProvinceId(null);
                    menuLocationBean.setCity(null);
                    menuLocationBean.setCityId(null);
                    menuLocationBean.setDistrict(null);
                    menuLocationBean.setDistrictId(null);
                } else if (menuLocationBean.getLeve() == 2) {
                    menuLocationBean.setCity(null);
                    menuLocationBean.setCityId(null);
                    menuLocationBean.setDistrict(null);
                    menuLocationBean.setDistrictId(null);
                } else {
                    menuLocationBean.setDistrict(null);
                    menuLocationBean.setDistrictId(null);
                }
                setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBean));
                finish();
                break;
        }
    }
}
