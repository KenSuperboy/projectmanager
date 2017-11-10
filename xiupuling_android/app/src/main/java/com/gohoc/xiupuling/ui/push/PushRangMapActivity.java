package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.LocationBean;
import com.gohoc.xiupuling.bean.MenuLocationBean;
import com.gohoc.xiupuling.bean.MyBaiduLocation;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.terminal.LocationDtNameActivity;
import com.gohoc.xiupuling.ui.terminal.SelectLocationSearchActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.BDresult;
import com.gohoc.xiupuling.utils.BaiduLocation;
import com.gohoc.xiupuling.utils.GeoCoderManager;
import com.gohoc.xiupuling.utils.ViewUtils;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushRangMapActivity extends BasicActivity {


    BaiduLocation baiduLocation;
    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.key_et)
    EditText keyEt;
    @BindView(R.id.location_iv)
    ImageView locationIv;
    // TODO: 2017/6/5 0005 定位按钮提示
    @BindView(R.id.location_message_tv)
    TextView locationMessageTv;
    @BindView(R.id.center_message_tv)
    TextView centerMessageTv;
    LinearLayout left;
    private BaiduMap baiduMap;
    private static int LOCATION_DETAIL_REQUEST_RESULT = 1000;
    private static int NAME_REQUEST_RESULT = 1001;
    private PoiInfo poiInfo;
    private BitmapDescriptor bitmap;
    private OverlayOptions option;
    private View popView;
    private LinearLayout popEditDtLl;
    private TextView locationTvTv, rang_tv;
    private InfoWindow mInfoWindow;
    private MenuLocationBean menuLocationBean;
    private double latDiff, longDiff;
    private int zoom = 15;
    private boolean isFirstStart = true;
    private String adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_rang_map);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("指定地点");
        baiduMap = bmapView.getMap();
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .zoom(16f)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);
        menuLocationBean = (MenuLocationBean) getIntent().getExtras().get("menuLocationBean");
        baiduLocation = new BaiduLocation(this);
        final MyBaiduLocation myBaiduLocation = (MyBaiduLocation) ACache.get(this).getAsObject("myBaiduLocation");
        if (menuLocationBean.getRangeInfo().getmLatitude() == null) {
            if (myBaiduLocation != null) {
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
                            startActivityForResult(new Intent(PushRangMapActivity.this, PushRangMapSearchActivity.class).putExtra("key", keyEt.getText() + ""), LOCATION_DETAIL_REQUEST_RESULT);
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


        if (menuLocationBean != null && menuLocationBean.getRangeInfo() != null) {
            MenuLocationBean.RangeInfo rangeInfo = menuLocationBean.getRangeInfo();
            PoiInfo poiInfos = new PoiInfo();
            poiInfos.address = rangeInfo.getAdress();
            if (menuLocationBean.getRangeInfo().getmLatitude() == null) return;
            if (menuLocationBean.getRangeInfo().getmLongitude() == null) return;
            poiInfos.location = new LatLng(Double.parseDouble(rangeInfo.getmLatitude()) ,Double.parseDouble(rangeInfo.getmLongitude()));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bmapView.onDestroy();
    }

    private void gotoLocation(Double location, Double longitude) {
        //设定中心点坐标
        LatLng cenpt = new LatLng(location, longitude);
        if (menuLocationBean.getRangeInfo().getDistance().equals("500")) {
            zoom = 16;
        } else if (menuLocationBean.getRangeInfo().getDistance().equals("1000")) {
            zoom = 16;
        } else {
            zoom = 16;
        }
        MapStatus mMapStatus = new MapStatus.Builder()
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
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.location_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                goBack();
                break;
            case R.id.location_iv:
                startLocation();

                break;
        }
    }

    private void startLocation() {
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
                            gotoLocation(location.getLatitude(), location.getLongitude());
                            poiInfo = new PoiInfo();
//                            poiInfo.address = location.getAddrStr();
                            poiInfo.address = location.getStreet();
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

        GeoCoderManager.getInstance().searchGeoCode(poiInfo.location.latitude, poiInfo.location.longitude, new GeoCoderManager.GeoCoderListener() {
            @Override
            public void onGeoCoderSucceed(double latitude, double longitude) {

            }

            @Override
            public void onGeoCoderAddressSucceed(String address) {
                // TODO: 2017/6/5 0005 重新设置地址
                poiInfo.address = address;
            }

            @Override
            public void onGeoCoderError() {
                Log.i("kekegdsz", "onGeoCoderError");

            }
        });

        menuLocationBean.getRangeInfo().setAdress(poiInfo.address);
        menuLocationBean.getRangeInfo().setmLatitude(poiInfo.location.latitude + "");
        menuLocationBean.getRangeInfo().setmLongitude(poiInfo.location.longitude + "");
        // menuLocationBean.setSim
        setResult(RESULT_OK, new Intent().putExtra("menuLocationBean", menuLocationBean));
        finish();
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
                        //gotoLocation(poiInfo.location.latitude,poiInfo.location.longitude);
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
        if (isFirstStart && menuLocationBean.getRangeInfo().getmLatitude() == null) {
            isFirstStart = false;
            return;
        }
        baiduMap.clear();
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lan);
        //构建Marker图标
        bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_shop_loc_dizhidngwei);
        //构建MarkerOption，用于在地图上添加Marker
        option = new MarkerOptions()
                .position(point)
                .animateType(MarkerOptions.MarkerAnimateType.grow)
                .draggable(true) // TODO: 2017/6/5 0005 设置可拖动
                .icon(bitmap);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
        //构建用户绘制多边形的Option对象
        CircleOptions polygonOption = new CircleOptions()
                .center(point)
                .radius(Integer.parseInt(menuLocationBean.getRangeInfo().getDistance()))
                .stroke(new Stroke(5, 0xAA27ABFD))
                .fillColor(0x3327ABFD);//圆的填充颜色

        baiduMap.addOverlay(polygonOption);
//        showPop(lat - latDiff, lan + longDiff, text);
        showPop(lat, lan, text);
        gotoLocation(lat, lan);
        // TODO: 2017/6/5 0005  绑定监听
        baiduMap.setOnMarkerDragListener(onMarkerDragListener);
    }


    private void showPop(Double lat, Double lan, final String text) {
        adress = text;
        //构建自定义视图
        if (null == popView) {
            popView = LayoutInflater.from(this).inflate(R.layout.item_pop2, null);
            locationTvTv = (TextView) popView.findViewById(R.id.pop_location_tv);
            popEditDtLl = (LinearLayout) popView.findViewById(R.id.pop_edit_dt_ll);
            rang_tv = (TextView) popView.findViewById(R.id.rang_tv);
            left = (LinearLayout) popView.findViewById(R.id.left);

            String rang = "500m";
            if (menuLocationBean.getRangeInfo().getDistance().equals("1000"))
                rang = "1KM";
            else if (menuLocationBean.getRangeInfo().getDistance().equals("3000"))
                rang = "3KM";

            rang_tv.setText(rang + "范围内投放");
            popEditDtLl.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    startActivityForResult(new Intent(PushRangMapActivity.this, LocationDtNameActivity.class).putExtra("address", adress), NAME_REQUEST_RESULT);
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
    private BaiduMap.OnMarkerDragListener onMarkerDragListener = new BaiduMap.OnMarkerDragListener() {
        // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        // 这个位置可能与拖动的之前的marker位置不一样。
        // marker 被拖动的marker对象。
        @Override
        public void onMarkerDrag(Marker marker) {
            isDrag = true;
            Log.i("kekegdsz", "onMarkerDrag");
        }

        // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        // 这个位置可能与拖动的之前的marker位置不一样。
        // marker 被拖动的marker对象。
        @Override
        public void onMarkerDragEnd(final Marker marker) {
            Log.i("kekegdsz", "onMarkerDragEnd");
            isDrag = false;
            // TODO: 2017/6/5 0005 当移动结束时显示infowindow 并且居中显示marker和infowindow
            if (null != mInfoWindow) {
//                baiduMap.showInfoWindow(mInfoWindow);
            }
            final LatLng latLng = marker.getPosition();
            Log.i("kekegdsz", "onMarkerDragEnd" + "==" + latLng.latitude + "==" + latLng.longitude);
            if (null != latLng) {
                gotoLocation(latLng.latitude, latLng.longitude);
                GeoCoderManager.getInstance().searchGeoCode(latLng.latitude, latLng.longitude, new GeoCoderManager.GeoCoderListener() {
                    @Override
                    public void onGeoCoderSucceed(double latitude, double longitude) {

                    }

                    @Override
                    public void onGeoCoderAddressSucceed(String address) {
                        // TODO: 2017/6/5 0005 重新设置地址
                        poiInfo.address = address;
                        poiInfo.location = latLng;
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


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
