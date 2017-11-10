package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.adapter.ProvinceListAdater;
import com.gohoc.xiupuling.bean.LocationBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class CityListActivity extends BasicActivity {
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.city_list)
    RecyclerView cityList;
    @BindView(R.id.right_iv)
    ImageView rightIv;
    @BindView(R.id.no_limt_ll)
    LinearLayout noLimtLl;
    private ProvinceListAdater adater;
    private static int DISTRICT_REQUEST_RESULT = 1000;
    private MenuLocationBean menuLocationBean;
    private int type = 0;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("地区信息");
        Bundle db = getIntent().getExtras();

        try {
            if (null != db) {
                menuLocationBean = (MenuLocationBean) db.get("menuLocationBean");
                getCityList();
                title = getIntent().getStringExtra("title");
                type = getIntent().getIntExtra("type", 0);
                if (!TextUtils.isEmpty(title))
                {
                    toolbarTitle.setText(title);
                    noLimtLl.setVisibility(View.VISIBLE);
                }

            }
        } catch (Exception e) {
            Logger.e(e.toString());
        }

    }

    private void getCityList() {
        RxRetrofitClient.getInstance(CityListActivity.this).getCitylist(menuLocationBean.getProvinceId(), "2", new Observer<LocationBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CityListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(final LocationBean locationBean) {
                if (locationBean.getCode() == 1) {
                    adater = new ProvinceListAdater(CityListActivity.this, locationBean);
                    cityList.setAdapter(adater);
                    cityList.setLayoutManager(new LinearLayoutManager(CityListActivity.this));
                    // provinceList.setItemAnimator(new DefaultItemAnimator());
                    adater.setOnItemClickLitener(new OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Logger.d(position);
                            menuLocationBean.setCity(locationBean.getData().get(position).getName() + "");
                            menuLocationBean.setCityId(locationBean.getData().get(position).getId() + "");
                            if (type == 0)
                                startActivityForResult(new Intent(CityListActivity.this, DistrictListActivity.class).putExtra("menuLocationBean", menuLocationBean), DISTRICT_REQUEST_RESULT);
                            else {
                                setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBean));
                                finish();
                            }
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });

                }
            }
        });
    }

    @OnClick({R.id.toolbar_left_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                CityListActivity.this.finish();
                break;
            case R.id.no_limt_ll:
                setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBean));
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DISTRICT_REQUEST_RESULT) {
            if (resultCode == RESULT_OK) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        MenuLocationBean mlb = (MenuLocationBean) d.get("menuLocationBean");
                        setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", mlb));
                        CityListActivity.this.finish();
                    }

                }
            }
        }
    }


}
