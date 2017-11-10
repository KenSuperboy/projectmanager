package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.adapter.ProvinceListAdater;
import com.gohoc.xiupuling.bean.LocationBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
import com.gohoc.xiupuling.bean.MyBaiduLocation;
import com.gohoc.xiupuling.bean.RegionLocationBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.BDresult;
import com.gohoc.xiupuling.utils.BaiduLocation;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SelectLocationActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.auto_city_tv)
    TextView autoCityTv;
    @BindView(R.id.auto_city_lv)
    LinearLayout autoCityLv;
    @BindView(R.id.province_list)
    RecyclerView provinceList;
    private ProvinceListAdater adater;
    private static int CITY_REQUEST_RESULT = 1000;
    private MenuLocationBean menuLocationBean;
    private MenuLocationBean menuLocationBeanAuto;
    private BaiduLocation baiduLocation;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("地区信息");
        final MyBaiduLocation myBaiduLocation = (MyBaiduLocation) ACache.get(this).getAsObject("myBaiduLocation");
        if (myBaiduLocation != null && myBaiduLocation.getCityId()!=null) {
            autoCityTv.setText(myBaiduLocation.getProvince() + myBaiduLocation.getCity() + myBaiduLocation.getDistrict());
            autoCityLv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getRegion(myBaiduLocation.getDistrict(),myBaiduLocation.getCity());
                }
            });
        } else {
            baiduLocation = new BaiduLocation(SelectLocationActivity.this);
            baiduLocation.setbDresult(new BDresult() {
                @Override
                public void onCallBack(final BDLocation location) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != location && location.getDistrict() != null) {
                                autoCityTv.setText(location.getProvince() + location.getCity() + location.getDistrict());
                                autoCityLv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getRegion(location.getDistrict(),location.getCity());
                                    }
                                });
                            } else {
                                autoCityTv.setText("定位失败，请确认手机权限已开放");
                                autoCityLv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        baiduLocation.initBaiduLocation();
                                    }
                                });
                            }
                        }
                    });


                }
            }).initBaiduLocation();
        }


        menuLocationBean = new MenuLocationBean();
        getCityList("0", "1");

    }


    private void initProvinceList() {
        //province_list
    }

    @OnClick({R.id.toolbar_left_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                SelectLocationActivity.this.finish();
                break;
        }
    }

    private void getCityList(String parenId, String level) {
        RxRetrofitClient.getInstance(SelectLocationActivity.this).getCitylist(parenId, level, new Observer<LocationBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SelectLocationActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(final LocationBean locationBean) {
                if (locationBean.getCode() == 1) {
                    adater = new ProvinceListAdater(SelectLocationActivity.this, locationBean);
                    provinceList.setAdapter(adater);
                    provinceList.setLayoutManager(new LinearLayoutManager(SelectLocationActivity.this));
                    // provinceList.setItemAnimator(new DefaultItemAnimator());
                    adater.setOnItemClickLitener(new OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Logger.d(position);
                            menuLocationBean.setProvince(locationBean.getData().get(position).getName() + "");
                            menuLocationBean.setProvinceId(locationBean.getData().get(position).getId() + "");
                            startActivityForResult(new Intent(SelectLocationActivity.this, CityListActivity.class).putExtra("menuLocationBean", menuLocationBean), CITY_REQUEST_RESULT);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });

                }
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CITY_REQUEST_RESULT) {
            if (resultCode == RESULT_OK) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        MenuLocationBean mlb = (MenuLocationBean) d.get("menuLocationBean");
                        setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", mlb));
                        SelectLocationActivity.this.finish();
                    }

                }
            }
        }
    }


    private void getRegion(String district,String city_name) {
        RxRetrofitClient.getInstance(SelectLocationActivity.this).region(district, "3",city_name, new Observer<RegionLocationBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SelectLocationActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(RegionLocationBean regionLocationBean) {
                if (regionLocationBean.getCode() == 1) {
                    menuLocationBeanAuto = new MenuLocationBean();
                    menuLocationBeanAuto.setProvince(regionLocationBean.getData().getProvince().getName() + "");
                    menuLocationBeanAuto.setProvinceId(regionLocationBean.getData().getProvince().getId() + "");
                    menuLocationBeanAuto.setCity(regionLocationBean.getData().getCity().getName() + "");
                    menuLocationBeanAuto.setCityId(regionLocationBean.getData().getCity().getId() + "");
                    menuLocationBeanAuto.setDistrict(regionLocationBean.getData().getName() + "");
                    menuLocationBeanAuto.setDistrictId(regionLocationBean.getData().getId() + "");

                    setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBeanAuto));
                    SelectLocationActivity.this.finish();
                } else {
                    Utils.toast(SelectLocationActivity.this, regionLocationBean.getMessage());
                }
            }
        });
    }
}
