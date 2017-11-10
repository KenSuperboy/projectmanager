package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.AddShopResultBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class AddShopActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.shop_name_lv)
    LinearLayout shopNameLv;
    @BindView(R.id.shop_location_tv)
    TextView shopLocationTv;
    @BindView(R.id.shop_location_lv)
    LinearLayout shopLocationLv;
    @BindView(R.id.trade_area_tv)
    TextView tradeAreaTv;
    @BindView(R.id.trade_area_lv)
    LinearLayout tradeAreaLv;
    @BindView(R.id.shop_lc_detail_tv)
    TextView shopLcDetailTv;
    @BindView(R.id.shop_lc_detail_lv)
    LinearLayout shopLcDetailLv;
    @BindView(R.id.shop_industry_tv)
    TextView shopIndustryTv;
    @BindView(R.id.shop_industry_lv)
    LinearLayout shopIndustryLv;
    @BindView(R.id.shop_phone_tv)
    TextView shopPhoneTv;
    @BindView(R.id.shop_phone_lv)
    LinearLayout shopPhoneLv;
    @BindView(R.id.shop_photo_tv)
    TextView shopPhotoTv;
    @BindView(R.id.shop_photo_lv)
    LinearLayout shopPhotoLv;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    private static int NAME_REQUEST_RESULT = 1000;
    private static int LOCATION_REQUEST_RESULT = 1001;
    private static int TRADE_REQUEST_RESULT = 1002;
    private static int LOCATION_DETAIL_REQUEST_RESULT = 1003;
    private static int INDUSTRY_REQUEST_RESULT = 1004;
    private static int PHONE_REQUEST_RESULT = 1005;
    private static int PHOTO_REQUEST_RESULT = 1006;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private String name = "";
    private String groupId = "";
    private String groupName = "";
    private String business_id = "";
    private String business_name = "";
    private String businessType="";
    private String mobile = "";
    private MenuLocationBean menuLocationBean;
    private ArrayList<PicBean> picList = new ArrayList<PicBean>();
    private PoiInfo poiInfo;
    private ShopDetailsBean.DataBean ShopDetailsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        ButterKnife.bind(this);
        toolbarRight.setVisibility(View.GONE);
        toolbarTitle.setText("门店信息");
        setStatusColor(R.color.colorPrimary);
        try {
            ShopDetailsBean = (ShopDetailsBean.DataBean) getIntent().getExtras().get("edit");
            initEdit();

        } catch (Exception e) {

        }
    }

    /**
     * 初始化编辑数据
     */
    private void initEdit() {
        name = ShopDetailsBean.getShop_name() + "";
        groupId = ShopDetailsBean.getGroup().getGroup_id() + "";
        groupName = ShopDetailsBean.getGroup().getGroup_name() + "";
        business_id = ShopDetailsBean.getBusiness().getBusiness_id() + "";
        business_name = ShopDetailsBean.getBusiness().getBusiness_name() + "";
        mobile = ShopDetailsBean.getShop_telephone();

        menuLocationBean = new MenuLocationBean();
        menuLocationBean.setProvince(ShopDetailsBean.getProvince().getName());
        menuLocationBean.setProvinceId(ShopDetailsBean.getProvince().getId() + "");
        menuLocationBean.setCity(ShopDetailsBean.getCity().getName());
        menuLocationBean.setCityId(ShopDetailsBean.getCity().getId() + "");
        menuLocationBean.setDistrict(ShopDetailsBean.getRegion().getName());
        menuLocationBean.setDistrictId(ShopDetailsBean.getRegion().getId() + "");
        poiInfo = new PoiInfo();
        poiInfo.address = ShopDetailsBean.getShop_address() + "";
        poiInfo.location = new LatLng(ShopDetailsBean.getShop_addr_latitude(), ShopDetailsBean.getShop_addr_longitude());


        shopNameTv.setText(name + "");
        shopLocationTv.setText(menuLocationBean.getProvince() + menuLocationBean.getCity() + menuLocationBean.getDistrict());
        tradeAreaTv.setText(groupName + "");
        shopLcDetailTv.setText(poiInfo.address + "");
        shopIndustryTv.setText(business_name + "");
        if (null == mobile || mobile.isEmpty())
            mobile = "";
        shopPhoneTv.setText(mobile + "");

        shopPhotoTv.setText(ShopDetailsBean.getShopphotos().size() + "");

        for (int a = 0; a < ShopDetailsBean.getShopphotos().size(); a++) {
            PicBean picBean = new PicBean();
            picBean.setType(1);
            picBean.setUrl(ShopDetailsBean.getShopphotos().get(a).getPhoto_url());
            picList.add(picBean);
        }
        //未到达5张
        if (ShopDetailsBean.getShopphotos().size() < 5) {
            PicBean picBean = new PicBean();
            picBean.setType(0);
            picList.add(picBean);
        }


    }

    @OnClick({R.id.shop_name_lv, R.id.shop_location_lv, R.id.trade_area_lv, R.id.shop_lc_detail_lv, R.id.shop_industry_lv, R.id.shop_phone_lv, R.id.shop_photo_lv, R.id.button_ll, R.id.toolbar_left_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shop_name_lv:
                startActivityForResult(new Intent(AddShopActivity.this, SelectShopNameActivity.class).putExtra("name", name), NAME_REQUEST_RESULT);
                break;
            case R.id.shop_location_lv:
                startActivityForResult(new Intent(AddShopActivity.this, SelectLocationActivity.class), LOCATION_REQUEST_RESULT);
                break;
            case R.id.trade_area_lv:
                if (null != menuLocationBean && menuLocationBean.getDistrictId() != null) {
                    Logger.e(groupId);
                    startActivityForResult(new Intent(AddShopActivity.this, SelectShopTradeActivity.class).putExtra("menuLocationBean", menuLocationBean).putExtra("groudId", groupId), TRADE_REQUEST_RESULT);
                } else {
                    Utils.toast(AddShopActivity.this, "请选择地区信息");
                }
                break;
            case R.id.shop_lc_detail_lv:

                startActivityForResult(new Intent(AddShopActivity.this, SelectBaiduMapActivity.class).putExtra("poiInfo", poiInfo).putExtra("type","0"), LOCATION_DETAIL_REQUEST_RESULT);
                break;
            case R.id.shop_industry_lv:
                startActivityForResult(new Intent(AddShopActivity.this, SelectShopBusinessTypeActivity.class), INDUSTRY_REQUEST_RESULT);
                break;
            case R.id.shop_phone_lv:
                startActivityForResult(new Intent(AddShopActivity.this, SelectShopMobileActivity.class).putExtra("mobile", mobile), PHONE_REQUEST_RESULT);
                break;
            case R.id.shop_photo_lv:
                startActivityForResult(new Intent(AddShopActivity.this, SelectShopPicActivity.class).putExtra("picList", picList), PHOTO_REQUEST_RESULT);
                break;
            case R.id.button_ll:
                saveShop();
                break;
            case R.id.toolbar_left_title:
                AddShopActivity.this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NAME_REQUEST_RESULT) {
            if (null != data) {
                name = data.getStringExtra("name");
                shopNameTv.setText(name + "");

            }

        } else if (requestCode == LOCATION_REQUEST_RESULT) {

            if (null != data) {
                Bundle d = data.getExtras();
                if (null != d) {
                    MenuLocationBean mlb = (MenuLocationBean) d.get("menuLocationBean");
                    if (mlb != null && (!mlb.getDistrict().isEmpty())) {
                        menuLocationBean = mlb;
                        shopLocationTv.setText(mlb.getProvince() + mlb.getCity() + mlb.getDistrict());
                        //清除商圈信息
                        groupId = "";
                        groupName = "";
                        tradeAreaTv.setText("");
                        //清除详细地址
                        poiInfo = null;
                        shopLcDetailTv.setText("");

                    }
                }

            }
        } else if (requestCode == TRADE_REQUEST_RESULT) {
            if (null != data) {
                groupId = data.getStringExtra("groupId");
                Logger.e(groupId);
                groupName = data.getStringExtra("groupName");
                if (!groupName.isEmpty()) {
                    tradeAreaTv.setText(groupName + "");
                }
            }

        } else if (requestCode == LOCATION_DETAIL_REQUEST_RESULT) {
            if (null != data) {
                Bundle d = data.getExtras();
                if (null != d) {
                    PoiInfo poiInfos = (PoiInfo) d.get("poiInfo");
                    if (poiInfos != null) {
                        poiInfo = poiInfos;
                        shopLcDetailTv.setText(poiInfo.address + "");
                    }
                }

            }

        } else if (requestCode == INDUSTRY_REQUEST_RESULT) {
            if (null != data) {
                business_id = data.getStringExtra("business_id");
                business_name = data.getStringExtra("business_name");
                businessType= data.getStringExtra("businessType");
                Logger.e(business_id + " " + business_name);
                if (!business_name.isEmpty()) {
                    shopIndustryTv.setText(businessType+"-"+business_name + "");
                }
            }

        } else if (requestCode == PHONE_REQUEST_RESULT) {
            if (null != data) {
                mobile = data.getStringExtra("mobile") + "";
                //  if (!mobile.isEmpty()) {
                shopPhoneTv.setText(mobile + "");
                //      }
            }

        } else if (requestCode == PHOTO_REQUEST_RESULT) {
            if (null != data) {
                Bundle d = data.getExtras();
                if (null != d) {
                    ArrayList<PicBean> picl = (ArrayList<PicBean>) d.get("picList");
                    if (picl != null) {
                        picList = picl;
                        int count = 0;
                        if (picl.size() > 1) {
                            count = picl.size() - 1;
                            if (picl.get(picl.size() - 1).getType() == 1)
                                count = 5;
                        }
                        shopPhotoTv.setText(count + "");

                    }
                }

            }
        }


    }

    private void saveShop() {

        if (name.isEmpty()) {
            Utils.toast(AddShopActivity.this, "请输入店铺名");
            return;
        }
        if (null == menuLocationBean || menuLocationBean.getCityId().isEmpty()) {
            Utils.toast(AddShopActivity.this, "请选择所属地区");
            return;
        }
        if (groupId.isEmpty()) {
            Utils.toast(AddShopActivity.this, "请选择所属商圈");
            return;
        }
        if (null == poiInfo) {
            Utils.toast(AddShopActivity.this, "请选择详细地址");
            return;
        }
        if (business_id.isEmpty()) {
            Utils.toast(AddShopActivity.this, "请选择所属行业");
            return;
        }
/*
        if (null == mobile || mobile.isEmpty()) {
            Utils.toast(AddShopActivity.this, "请输入店铺电话");
            return;
        }
*/

/*        if (picList.size() < 2) {
            Utils.toast(AddShopActivity.this, "请添加店铺图片");
            return;
        }*/
        post();
    }


    private void post() {

        if (null != ShopDetailsBean) {
            //修改门店信息
            RxRetrofitClient.getInstance(AddShopActivity.this).updateShop(ShopDetailsBean.getShop_id(), name, poiInfo.address, menuLocationBean.getProvinceId(), menuLocationBean.getCityId(), menuLocationBean.getDistrictId(),
                    groupId, poiInfo.location.longitude + "", poiInfo.location.latitude + "", mobile, business_id, getPicListString(), new Observer<AddShopResultBean>() {
                        @Override
                        public void onCompleted() {
                            closeProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Utils.toast(AddShopActivity.this, "请检查网络是否正常");
                            try {
                                throw e;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void onNext(AddShopResultBean addShopResultBean) {
                            Utils.toast(AddShopActivity.this, addShopResultBean.getMessage());
                            if (addShopResultBean.getCode() == 1) {

                                EventBus.getDefault().post(new Event.RefreshTerminalListEvent());
                                // startActivity(new Intent(AddShopActivity.this, ShopDetailsActivity.class).putExtra("shopId", addShopResultBean.getData().getShop_id()));
                                AddShopActivity.this.finish();
                            }

                        }
                    });
            return;
        }


        RxRetrofitClient.getInstance(AddShopActivity.this).saveShop(name, poiInfo.address, menuLocationBean.getProvinceId(), menuLocationBean.getCityId(), menuLocationBean.getDistrictId(),
                groupId, poiInfo.location.longitude + "", poiInfo.location.latitude + "", mobile, business_id, getPicListString(), new Observer<AddShopResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(AddShopActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(AddShopResultBean addShopResultBean) {
                        Utils.toast(AddShopActivity.this, addShopResultBean.getMessage() + "");
                        if (addShopResultBean.getCode() == 1) {
                            EventBus.getDefault().post(new Event.RefreshTerminalListEvent());
                            if (getIntent().getIntExtra("type", 0) == 0)
                                startActivity(new Intent(AddShopActivity.this, ShopDetailsActivity.class).putExtra("shopId", addShopResultBean.getData().getShop_id())
                                        .putExtra("type", 1));
                            setResult(RESULT_OK);
                            finish();

                        }

                    }
                });
    }


    private String getPicListString() {
        String str = "";
        for (int a = 0; a < picList.size(); a++) {
            if (picList.get(a).getType() == 1) {
                if (null != picList.get(a).getUrl() && picList.get(a).getUrl() != "null")
                    str += picList.get(a).getUrl() + ",";
            }

        }
        Logger.e(str);
        return str;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeProgressDialog();
    }
}
