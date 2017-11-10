package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MyBaiduLocation;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.BDresult;
import com.gohoc.xiupuling.utils.BaiduLocation;
import com.gohoc.xiupuling.utils.GeoCoderManager;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectBaiduMapActivity extends BasicActivity {
    BaiduLocation baiduLocation;
    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    // TODO: 2017/6/5 0005 定位按钮提示
    @BindView(R.id.location_message_tv)
    TextView locationMessageTv;
    @BindView(R.id.center_message_tv)
    TextView centerMessageTv;
    @BindView(R.id.key_et)
    EditText keyEt;
    @BindView(R.id.location_iv)
    ImageView locationIv;
    @BindView(R.id.linear_mine_layout)
    LinearLayout mLinearMineLayout;
    @BindView(R.id.linearLayout3)
    LinearLayout mLinearLayout3;
    private BaiduMap baiduMap;
    private static int LOCATION_DETAIL_REQUEST_RESULT = 1000;
    private static int NAME_REQUEST_RESULT = 1001;
    private PoiInfo poiInfo;
    private BitmapDescriptor bitmap;
    private OverlayOptions option;
    private View popView;
    private LinearLayout popEditDtLl;
    private TextView locationTvTv;
    private LinearLayout left;
    private InfoWindow mInfoWindow;
    private boolean isFirstStart = true;
    private boolean isSave = false;
    private boolean isLoc = false;
    private String title = "";
    private PromotionInfoBean promotionInfoBean;
    private String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_badidu_map);


        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        type= getIntent().getStringExtra("type");
        toolbarTitle.setText("详细地址");
        title = "详细地址";
        baiduMap = bmapView.getMap();
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .zoom(14f)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);
        baiduLocation = new BaiduLocation(getApplication());

        try {
            promotionInfoBean = (PromotionInfoBean) getIntent().getExtras().get("promotionInfoBean");
            if (!TextUtils.isEmpty(getIntent().getStringExtra("CreateReqLinkActivity"))) {
                title = "链接到实体地址";
                toolbarTitle.setText("链接到实体地址");
            }
        } catch (Exception e) {

        }

        final MyBaiduLocation myBaiduLocation = (MyBaiduLocation) ACache.get(this).getAsObject("myBaiduLocation");
        // TODO: 2017/6/5 0005 定位

        if (myBaiduLocation != null && myBaiduLocation.getLatitude() != null) {
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(myBaiduLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(myBaiduLocation.getLatitude())
                    .longitude(myBaiduLocation.getLongitude()).build();
            // 设置定位数据
            baiduMap.setMyLocationData(locData);
            gotoLocation(myBaiduLocation.getLatitude(), myBaiduLocation.getLongitude());
            poiInfo = new PoiInfo();
            poiInfo.address = myBaiduLocation.getAddrStr();
            poiInfo.location = new LatLng(myBaiduLocation.getLatitude(), myBaiduLocation.getLongitude());
            drawPop(myBaiduLocation.getLatitude(), myBaiduLocation.getLongitude(), myBaiduLocation.getAddrStr());
        } else {
            startLocation();
        }


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            PoiInfo poiInfos = (PoiInfo) extras.get("poiInfo");
            if (poiInfos != null) {
                poiInfo = poiInfos;
                if (null != poiInfos) {
                    drawPop(poiInfo.location.latitude, poiInfo.location.longitude, poiInfo.address);
                }
            }

            type = extras.getString("type");//type  0    type 1隐藏三个按钮  1
            if (type!=null&&type.equals("1")) {
                mLinearMineLayout.setVisibility(View.GONE);
                mLinearLayout3.setVisibility(View.GONE);
                toolbarRight.setVisibility(View.GONE);

                Drawable drawable = getResources().getDrawable(R.mipmap.icon_return_left);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                toolbarLeftTitle.setCompoundDrawables(drawable, null, null, null);
                toolbarLeftTitle.setText("返回");
            }
        }


        keyEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        keyEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            //发送请求
                            Logger.e(" keyEt.setOnEditorActionListener");
                            startActivityForResult(new Intent(SelectBaiduMapActivity.this, SelectLocationSearchActivity.class).putExtra("key", keyEt.getText() + ""), LOCATION_DETAIL_REQUEST_RESULT);
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });

        locationMessageTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                locationMessageTv.setVisibility(View.GONE);
            }
        }, 3000);


        if (promotionInfoBean != null && promotionInfoBean.getLinkType() == 0) {

            PoiInfo poiInfos = new PoiInfo();
            poiInfos.address = promotionInfoBean.getLcoationName();
            if (promotionInfoBean.getLatitude() == null) return;
            if (promotionInfoBean.getLongitude() == null) return;
            poiInfos.location = new LatLng(Double.parseDouble(promotionInfoBean.getLatitude()), Double.parseDouble(promotionInfoBean.getLongitude()));
            if (poiInfos != null) {
                poiInfo = poiInfos;
                if (null != poiInfos) {
                    centerMessageTv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            centerMessageTv.setVisibility(View.GONE);
                        }
                    }, 3000);
                    drawPop(poiInfo.location.latitude, poiInfo.location.longitude, poiInfo.address);
                }
            }
        }

    }

    private void gotoLocation(Double location, Double longitude) {
        //设定中心点坐标
        LatLng cenpt = new LatLng(location, longitude);

        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        try {
            if (null == baiduMap)
                baiduMap = bmapView.getMap();
            if (null != baiduMap && null != mMapStatusUpdate)
                baiduMap.setMapStatus(mMapStatusUpdate);
        } catch (Exception e) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bmapView.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.location_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                //goBack();
                finish();
                break;
            case R.id.toolbar_right:
                if (!isLoc) {
                    Utils.toast(this, "请搜索标注活动举办地点");
                    return;
                }
                isSave = true;
                goBack();
                break;
            case R.id.location_iv:
                startLocation();
                break;
        }
    }

    private void startLocation() {
        // TODO: 2017/6/5 0005 定位
        if (baiduLocation == null) return;
        baiduLocation.setbDresult(new BDresult() {
            @Override
            public void onCallBack(final BDLocation location) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != location && location.getDistrict() != null) {
                            MyLocationData locData = new MyLocationData.Builder()
                                    .accuracy(location.getRadius())
                                    // 此处设置开发者获取到的方向信息，顺时针0-360
                                    .direction(100).latitude(location.getLatitude())
                                    .longitude(location.getLongitude()).build();
                            // 设置定位数据
                            baiduMap.setMyLocationData(locData);
                            gotoLocation(location.getLongitude(), location.getLatitude());
                            poiInfo = new PoiInfo();
                            poiInfo.address = location.getAddrStr();
                            poiInfo.location = new LatLng(location.getLatitude(), location.getLongitude());
                            drawPop(location.getLatitude(), location.getLongitude(), location.getAddrStr());
                            centerMessageTv.setVisibility(View.VISIBLE);
                            centerMessageTv.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    centerMessageTv.setVisibility(View.GONE);
                                }
                            }, 3000);
                        } else {
                            //定位失败
                        }
                    }
                });
            }
        }).initBaiduLocation();
    }

    private void goBack() {
        if (isSave) {
            setResult(RESULT_OK, new Intent().putExtra("poiInfo", poiInfo));
        }
        SelectBaiduMapActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == LOCATION_DETAIL_REQUEST_RESULT && null != data) {
                String key = data.getStringExtra("serachKey") + "";
                if (!key.isEmpty())
                    keyEt.setText(key + "");
                Bundle d = data.getExtras();
                if (null != d) {
                    PoiInfo poi = (PoiInfo) d.get("PoiResult");
                    if (poi != null && (!poi.name.isEmpty())) {
                        poiInfo = poi;
                        drawPop(poiInfo.location.latitude, poiInfo.location.longitude, poiInfo.address);
                    }
                }
            } else if (requestCode == NAME_REQUEST_RESULT && null != data) {
                poiInfo.address = data.getStringExtra("name");
                drawPop(poiInfo.location.latitude, poiInfo.location.longitude, poiInfo.address);

            }
        }
    }

    private void drawPop(Double lat, Double lan, String text) {
        // TODO: 2017/6/5 0005 如果是第一次的话不添加
        if (isFirstStart) {
            isFirstStart = false;
            return;
        }
        isLoc = true;
        baiduMap.clear();
        if (mInfoWindow != null) {
            baiduMap.hideInfoWindow();
            mInfoWindow = null;
        }
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lan);
        //构建Marker图标
        bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_shop_loc_dizhidngwei);
        //构建MarkerOption，用于在地图上添加Marker
        option = new MarkerOptions()
                .position(point)
                .draggable(true) // TODO: 2017/6/5 0005 设置可拖动
                .icon(bitmap)
                .zIndex(1000);

        //在地图上添加Marker，并显示
        // TODO: 2017/6/5 0005 获取marker方便后续操作
        baiduMap.addOverlay(option);
        showPop(lat, lan, text);
        gotoLocation(lat, lan);
        // TODO: 2017/6/5 0005  绑定监听
        baiduMap.setOnMarkerDragListener(onMarkerDragListener);
    }

    private void showPop(Double lat, Double lan, String text) {
        //构建自定义视图
        if (null == popView) {
            popView = LayoutInflater.from(SelectBaiduMapActivity.this).inflate(R.layout.item_pop, null);
            locationTvTv = (TextView) popView.findViewById(R.id.pop_location_tv);
            TextView mTvTitle = (TextView) popView.findViewById(R.id.title);
            TextView tv_action = (TextView) popView.findViewById(R.id.tv_action);
            popEditDtLl = (LinearLayout) popView.findViewById(R.id.pop_edit_dt_ll);
            left = (LinearLayout) popView.findViewById(R.id.left);
            if (title.equals("链接到实体地址")) {
                mTvTitle.setText("在这儿举办活动？");
            }
            if(type.equals("1")){
                mTvTitle.setText("在这儿举办活动？");
                tv_action.setText("查看详情");
            }
            popEditDtLl.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    startActivityForResult(new Intent(SelectBaiduMapActivity.this, LocationDtNameActivity.class).putExtra("address", poiInfo.address + "").putExtra("type",type), NAME_REQUEST_RESULT);
                    return false;
                }
            });
            left.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.i("kekegdsz", "ACTION_DOWN");
                            if (null != mInfoWindow) {
                                baiduMap.hideInfoWindow();
                            }
                    }
                    return false;
                }
            });
        }
        locationTvTv.setText(text + "");
        LatLng pt = new LatLng(lat, lan);
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        mInfoWindow = new InfoWindow(popView, pt, 60);
        //显示InfoWindow
        baiduMap.showInfoWindow(mInfoWindow);
    }

    private boolean isDrag = false;
    // TODO: 2017/6/5 0005 maker拖拽监听
    private OnMarkerDragListener onMarkerDragListener = new OnMarkerDragListener() {
        // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        // 这个位置可能与拖动的之前的marker位置不一样。
        // marker 被拖动的marker对象。
        @Override
        public void onMarkerDrag(Marker marker) {
            Log.i("kekegdsz", "onMarkerDrag");
            isDrag = true;

        }

        // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        // 这个位置可能与拖动的之前的marker位置不一样。
        // marker 被拖动的marker对象。
        @Override
        public void onMarkerDragEnd(final Marker marker) {
            isDrag = false;
            Log.i("kekegdsz", "onMarkerDragEnd");
            // TODO: 2017/6/5 0005 当移动结束时显示infowindow 并且居中显示marker和infowindow
            if (null != mInfoWindow) {
//                baiduMap.showInfoWindow(mInfoWindow);
            }
            final LatLng latLng = marker.getPosition();
            Log.i("kekegdsz", "onMarkerDragEnd" + "==" + latLng.latitude + "==" + latLng.longitude);
            if (null != latLng) {
                gotoLocation(latLng.latitude, latLng.longitude);
                poiInfo.location = latLng;
                GeoCoderManager.getInstance().searchGeoCode(latLng.latitude, latLng.longitude, new GeoCoderManager.GeoCoderListener() {
                    @Override
                    public void onGeoCoderSucceed(double latitude, double longitude) {

                    }

                    @Override
                    public void onGeoCoderAddressSucceed(String address) {
                        // TODO: 2017/6/5 0005 重新设置地址
                        poiInfo.address = address;
                        drawPop(latLng.latitude, latLng.longitude, address);
                    }

                    @Override
                    public void onGeoCoderError() {
                        Log.i("kekegdsz", "onGeoCoderError");
                        drawPop(latLng.latitude, latLng.longitude, "该地区无地址");
                    }
                });

            }


        }

        // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        // 这个位置可能与拖动的之前的marker位置不一样。
        // marker 被拖动的marker对象。
        @Override
        public void onMarkerDragStart(Marker marker) {
            // TODO: 2017/6/5 0005
            Log.i("kekegdsz", "onMarkerDragStart");

            // TODO: 2017/6/5 0005 移动定位图标隐藏info
            if (null != mInfoWindow) {
                baiduMap.hideInfoWindow();
            }

        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(keyEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.i("kekegdsz", "ACTION_UP");
                if (null != mInfoWindow && isDrag == false) {
                    baiduMap.showInfoWindow(mInfoWindow);
                }
        }
        return super.dispatchTouchEvent(ev);
    }
}
