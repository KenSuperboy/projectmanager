package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.baidu.location.BDLocation;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BusinessBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
import com.gohoc.xiupuling.bean.MyBaiduLocation;
import com.gohoc.xiupuling.bean.RegionLocationBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.BDresult;
import com.gohoc.xiupuling.utils.BaiduLocation;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushRangeActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.auto_city_tv)
    TextView autoCityTv;
    @BindView(R.id.auto_city_lv)
    LinearLayout autoCityLv;
    @BindView(R.id.ru1_tb)
    ToggleButton ru1Tb;
    @BindView(R.id.ru1_tv)
    TextView ru1Tv;
    @BindView(R.id.ru2_tb)
    ToggleButton ru2Tb;
    @BindView(R.id.ru2_tv)
    TextView ru2Tv;
    @BindView(R.id.ru2_a_ll)
    LinearLayout ru2ALl;
    @BindView(R.id.ru2_b_ll)
    LinearLayout ru2BLl;
    @BindView(R.id.ru2_c_ll)
    LinearLayout ru2CLl;
    @BindView(R.id.province_tv)
    TextView provinceTv;
    @BindView(R.id.province_ll)
    LinearLayout provinceLl;
    @BindView(R.id.city_tv)
    TextView cityTv;
    @BindView(R.id.city_ll)
    LinearLayout cityLl;
    @BindView(R.id.district_tv)
    TextView districtTv;
    @BindView(R.id.district_ll)
    LinearLayout districtLl;
    @BindView(R.id.bussin_ll)
    LinearLayout bussinLl;
    @BindView(R.id.business_tv)
    TextView businessTv;
    @BindView(R.id.type1)
    LinearLayout type1;
    @BindView(R.id.type2)
    LinearLayout type2;

    @BindView(R.id.textView19)
    TextView textView_500;
    @BindView(R.id.textView15)
    TextView textView_1000;
    @BindView(R.id.tv_3000)
    TextView textView_3000;
    @BindView(R.id.province_t_tv)
    TextView provinceTTv;
    @BindView(R.id.city_t_tv)
    TextView cityTTv;
    @BindView(R.id.district_t_tv)
    TextView districtTTv;
    @BindView(R.id.business_t_tv)
    TextView businessTTv;
    @BindView(R.id.fangyuang1_tv)
    TextView fangyuang1Tv;
    @BindView(R.id.fangyuang2_tv)
    TextView fangyuang2Tv;
    @BindView(R.id.fangyuang3_tv)
    TextView fangyuang3Tv;
    private BaiduLocation baiduLocation;
    private static int PROVINCE_REQUEST_RESULT = 1000;
    private static int CITY_REQUEST_RESULT = 1001;
    private static int DISTRICT_REQUEST_RESULT = 1002;
    private static int INDUSTRY_REQUEST_RESULT = 1003;
    private static int RANG_REQUEST_RESULT = 1004;//范围选择
    private int type = 0;
    private ArrayList<BusinessBean.DataBean> selectBussList;
    private MenuLocationBean menuLocationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_range);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("地域范围");
        toolbarRight.setText("完成");
        initDates();
        baiduLocation = new BaiduLocation(this);
        final MyBaiduLocation myBaiduLocation = (MyBaiduLocation) ACache.get(this).getAsObject("myBaiduLocation");
        if (myBaiduLocation != null && myBaiduLocation.getCityId() != null) {
            autoCityTv.setText(myBaiduLocation.getProvince() + myBaiduLocation.getCity() + myBaiduLocation.getDistrict());
            autoCityLv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getRegion(myBaiduLocation.getDistrict(), myBaiduLocation.getCity());
                }
            });
        } else {
            baiduLocation = new BaiduLocation(PushRangeActivity.this);
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
                                        getRegion(location.getDistrict(), location.getCity());
                                    }
                                });
                            } else {
                                autoCityTv.setText("定位失败，点击重新定位");
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

    }

    private void initDates() {
        type = getIntent().getIntExtra("type", 0);
        selectBussList = (ArrayList<BusinessBean.DataBean>) getIntent().getExtras().get("selectBussList");
        menuLocationBean = (MenuLocationBean) getIntent().getExtras().get("menuLocationBean");
        if (selectBussList == null)
            selectBussList = new ArrayList<>();
        if (menuLocationBean == null) {
            menuLocationBean = new MenuLocationBean();
        }
        if (menuLocationBean.getRangeInfo() == null) {
            menuLocationBean.setRangeInfo(new MenuLocationBean.RangeInfo());
        }

        setType(type); //方法
        if (type == 0) {
            if (!TextUtils.isEmpty(menuLocationBean.getProvince()))
                provinceTv.setText(menuLocationBean.getProvince());
            if (!TextUtils.isEmpty(menuLocationBean.getCity()))
                cityTv.setText(menuLocationBean.getCity());
            if (!TextUtils.isEmpty(menuLocationBean.getDistrict()))
                districtTv.setText(menuLocationBean.getDistrict());

            if (selectBussList != null && selectBussList.size() > 0) {
                StringBuffer tips = new StringBuffer();
                for (BusinessBean.DataBean d : selectBussList) {
                    tips.append(d.getGroup_name() + " ");
                }
                businessTv.setText(tips.toString().substring(0, tips.length() - 1));
            }


        } else {
            setType(1);
            String distance = menuLocationBean.getRangeInfo().getDistance();
            if (distance == null) return;
            if (distance.equals("500")) {
                textView_500.setText(menuLocationBean.getRangeInfo().getAdress());
            } else if (distance.equals("1000")) {
                textView_1000.setText(menuLocationBean.getRangeInfo().getAdress());
            } else if (distance.equals("3000")) {
                textView_3000.setText(menuLocationBean.getRangeInfo().getAdress());
            }
        }
    }


    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.ru2_a_ll, R.id.ru2_b_ll, R.id.ru2_c_ll, R.id.province_ll, R.id.city_ll, R.id.district_ll, R.id.bussin_ll, R.id.type1, R.id.type2, R.id.ru1_tb, R.id.ru2_tb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                if (type == 1 && (menuLocationBean.getRangeInfo() == null || TextUtils.isEmpty(menuLocationBean.getRangeInfo().getDistance()))) {
                    Utils.toast(PushRangeActivity.this, "请指定地点和范围");
                    return;
                }
                setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBean).putExtra("selectBussList", selectBussList).putExtra("type", type));
                finish();
                break;
            case R.id.ru2_a_ll:
                if (type == 0)
                    return;
                if (menuLocationBean.getRangeInfo() == null)
                    menuLocationBean.setRangeInfo(new MenuLocationBean.RangeInfo());
                menuLocationBean.getRangeInfo().setDistance("500");
                startActivityForResult(new Intent(PushRangeActivity.this, PushRangMapActivity.class).putExtra("menuLocationBean", menuLocationBean), RANG_REQUEST_RESULT);
                break;
            case R.id.ru2_b_ll:
                if (type == 0)
                    return;
                if (menuLocationBean.getRangeInfo() == null)
                    menuLocationBean.setRangeInfo(new MenuLocationBean.RangeInfo());
                menuLocationBean.getRangeInfo().setDistance("1000");
                startActivityForResult(new Intent(PushRangeActivity.this, PushRangMapActivity.class).putExtra("menuLocationBean", menuLocationBean), RANG_REQUEST_RESULT);
                break;
            case R.id.ru2_c_ll:
                if (type == 0)
                    return;
                if (menuLocationBean.getRangeInfo() == null)
                    menuLocationBean.setRangeInfo(new MenuLocationBean.RangeInfo());
                menuLocationBean.getRangeInfo().setDistance("3000");
                startActivityForResult(new Intent(PushRangeActivity.this, PushRangMapActivity.class).putExtra("menuLocationBean", menuLocationBean), RANG_REQUEST_RESULT);
                break;
            case R.id.province_ll:
                if (type == 1)
                    return;
                menuLocationBean.setLeve(1);
                selectBussList.clear();
                startActivityForResult(new Intent(PushRangeActivity.this, PushSelectRangActivity.class).putExtra("menuLocationBean", menuLocationBean), PROVINCE_REQUEST_RESULT);
                break;
            case R.id.city_ll:
                if (type == 1)
                    return;
                menuLocationBean.setLeve(2);
                if (!TextUtils.isEmpty(menuLocationBean.getProvinceId()))
                    startActivityForResult(new Intent(PushRangeActivity.this, PushSelectRangActivity.class).putExtra("menuLocationBean", menuLocationBean).putExtra("type", 1), CITY_REQUEST_RESULT);
                else
                    Utils.toast(PushRangeActivity.this, "请先选择省份");
                break;
            case R.id.district_ll:
                if (type == 1)
                    return;
                menuLocationBean.setLeve(3);
                if (!TextUtils.isEmpty(menuLocationBean.getCityId()))
                    startActivityForResult(new Intent(PushRangeActivity.this, PushSelectRangActivity.class).putExtra("menuLocationBean", menuLocationBean), DISTRICT_REQUEST_RESULT);
                else
                    Utils.toast(PushRangeActivity.this, "请先选择城市");
                break;
            case R.id.bussin_ll:
                if (type == 1)
                    return;
                if (!TextUtils.isEmpty(menuLocationBean.getDistrictId()))
                    startActivityForResult(new Intent(PushRangeActivity.this, PushReqSelectBusinessActivity.class).putExtra("menuLocationBean", menuLocationBean).putExtra("selectBussList", selectBussList), INDUSTRY_REQUEST_RESULT);
                else
                    Utils.toast(PushRangeActivity.this, "请先选择地区信息");
                break;
            case R.id.type1:
                setType(0);
                break;
            case R.id.type2:
                setType(1);
                break;
            case R.id.ru1_tb:
                setType(0);
                break;
            case R.id.ru2_tb:
                setType(1);
                break;
        }
    }

    private void setType(int i) {
        type = i;
        if (type == 0) {
            ru1Tb.setChecked(true);
            ru2Tb.setChecked(false);
            ru1Tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            ru2Tv.setTextColor(getResources().getColor(R.color.tips_font));
            provinceLl.setBackgroundResource(R.drawable.menu_selector);
            cityLl.setBackgroundResource(R.drawable.menu_selector);
            districtLl.setBackgroundResource(R.drawable.menu_selector);

            bussinLl.setBackgroundResource(R.drawable.menu_selector);

            ru2ALl.setBackgroundResource(R.color.unable);
            ru2BLl.setBackgroundResource(R.color.unable);
            ru2CLl.setBackgroundResource(R.color.unable);


            provinceTTv.setTextColor(getResources().getColor(R.color.df_font));
            cityTTv.setTextColor(getResources().getColor(R.color.df_font));
            districtTTv.setTextColor(getResources().getColor(R.color.df_font));
            businessTTv.setTextColor(getResources().getColor(R.color.df_font));


            fangyuang1Tv.setTextColor(getResources().getColor(R.color.tips_font));
            fangyuang2Tv.setTextColor(getResources().getColor(R.color.tips_font));
            fangyuang3Tv.setTextColor(getResources().getColor(R.color.tips_font));


        } else {
            ru1Tb.setChecked(false);
            ru2Tb.setChecked(true);
            ru1Tv.setTextColor(getResources().getColor(R.color.tips_font));
            ru2Tv.setTextColor(getResources().getColor(R.color.colorPrimary));

            ru2ALl.setBackgroundResource(R.drawable.menu_selector);
            ru2BLl.setBackgroundResource(R.drawable.menu_selector);
            ru2CLl.setBackgroundResource(R.drawable.menu_selector);

            bussinLl.setBackgroundResource(R.color.unable);

            provinceLl.setBackgroundResource(R.color.unable);
            cityLl.setBackgroundResource(R.color.unable);
            districtLl.setBackgroundResource(R.color.unable);


            provinceTTv.setTextColor(getResources().getColor(R.color.tips_font));
            cityTTv.setTextColor(getResources().getColor(R.color.tips_font));
            districtTTv.setTextColor(getResources().getColor(R.color.tips_font));
            businessTTv.setTextColor(getResources().getColor(R.color.tips_font));

            fangyuang1Tv.setTextColor(getResources().getColor(R.color.df_font));
            fangyuang2Tv.setTextColor(getResources().getColor(R.color.df_font));
            fangyuang3Tv.setTextColor(getResources().getColor(R.color.df_font));

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == INDUSTRY_REQUEST_RESULT) {//商圈

                if (null != data) {
                    selectBussList = (ArrayList<BusinessBean.DataBean>) data.getExtras().get("selectBussList");
                    //menuLocationBean.setRangeInfo(null);
                    //selectBussList.clear();
                    setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBean).putExtra("selectBussList", selectBussList).putExtra("type", type));
                    finish();
                }
            } else if (requestCode == PROVINCE_REQUEST_RESULT) {
                menuLocationBean = (MenuLocationBean) data.getExtras().get("menuLocationBean");
                if (!TextUtils.isEmpty(menuLocationBean.getProvince()))
                    provinceTv.setText(menuLocationBean.getProvince() + "");
                else
                    provinceTv.setText("不限");

                menuLocationBean.setCity(null);
                menuLocationBean.setCityId(null);
                menuLocationBean.setDistrict(null);
                menuLocationBean.setDistrictId(null);
                selectBussList.clear();
                businessTv.setText("");
                cityTv.setText("不限");
                districtTv.setText("不限");
            } else if (requestCode == CITY_REQUEST_RESULT) {
                selectBussList.clear();
                businessTv.setText("");
                menuLocationBean = (MenuLocationBean) data.getExtras().get("menuLocationBean");
                if (!TextUtils.isEmpty(menuLocationBean.getCity()))
                    cityTv.setText(menuLocationBean.getCity() + "");
                else
                    cityTv.setText("不限");
                menuLocationBean.setDistrict(null);
                menuLocationBean.setDistrictId(null);
                districtTv.setText("不限");
            } else if (requestCode == DISTRICT_REQUEST_RESULT) {
                selectBussList.clear();
                businessTv.setText("");
                menuLocationBean = (MenuLocationBean) data.getExtras().get("menuLocationBean");
                if (!TextUtils.isEmpty(menuLocationBean.getDistrict()))
                    districtTv.setText(menuLocationBean.getDistrict() + "");
                else
                    districtTv.setText("不限");
            } else if (requestCode == RANG_REQUEST_RESULT) {
                menuLocationBean = (MenuLocationBean) data.getExtras().get("menuLocationBean");
                selectBussList.clear();
                businessTv.setText("");
                menuLocationBean.setProvince(null);
                menuLocationBean.setProvinceId(null);
                menuLocationBean.setCity(null);
                menuLocationBean.setCityId(null);
                menuLocationBean.setDistrict(null);
                menuLocationBean.setDistrictId(null);
                setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBean).putExtra("selectBussList", selectBussList).putExtra("type", type));
                finish();
            }
        }
    }

    private void getRegion(String district, String city_name) {
        RxRetrofitClient.getInstance(PushRangeActivity.this).region(district, "3", city_name, new Observer<RegionLocationBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushRangeActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(RegionLocationBean regionLocationBean) {
                if (regionLocationBean.getCode() == 1) {
                    menuLocationBean = new MenuLocationBean();
                    menuLocationBean.setProvince(regionLocationBean.getData().getProvince().getName() + "");
                    menuLocationBean.setProvinceId(regionLocationBean.getData().getProvince().getId() + "");
                    menuLocationBean.setCity(regionLocationBean.getData().getCity().getName() + "");
                    menuLocationBean.setCityId(regionLocationBean.getData().getCity().getId() + "");
                    menuLocationBean.setDistrict(regionLocationBean.getData().getName() + "");
                    menuLocationBean.setDistrictId(regionLocationBean.getData().getId() + "");

                    provinceTv.setText(regionLocationBean.getData().getProvince().getName());
                    cityTv.setText(regionLocationBean.getData().getCity().getName());
                    districtTv.setText(regionLocationBean.getData().getName());
                } else {
                    Utils.toast(PushRangeActivity.this, regionLocationBean.getMessage());
                }
            }
        });
    }


}
