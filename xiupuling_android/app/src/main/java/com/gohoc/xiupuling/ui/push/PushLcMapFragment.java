package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
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
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.bean.MyBaiduLocation;
import com.gohoc.xiupuling.bean.PushLocationShowBean;
import com.gohoc.xiupuling.bean.PushLocationShowMapBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.BDresult;
import com.gohoc.xiupuling.utils.BaiduLocation;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.qiniu.android.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;


public class PushLcMapFragment extends BaseFragment {
    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.no_contont_tv)
    TextView noContontTv;
    @BindView(R.id.location_iv)
    ImageView location_iv;
    private String rq_id = "";
    private String work_id = "";
    BaiduLocation baiduLocation;
    private View viewContainer;
    private BaiduMap baiduMap;
    private MyBaiduLocation locations;
    private int pageNumber = 1;
    private int pageSize = 10;
    private InfoWindow mInfoWindow;
    private List<Marker> markerList = new ArrayList<>();
    private BitmapDescriptor bitmap = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_shop_loc_full);
    ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
    private PushLocationShowMapBean pushLocationShowMapBean;
    private View popViwe;
    private TextView popTv;

    private static final String MARKER_NUMBER = "marker_number";        // Key —— Marker的序号

    public PushLcMapFragment() {

    }

    public static PushLcMapFragment newInstance() {
        PushLcMapFragment fragment = new PushLcMapFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        giflist.add(bitmap);
        popViwe = LayoutInflater.from(getActivity()).inflate(R.layout.item_lt_pop, null);
        popTv = (TextView) popViwe.findViewById(R.id.title);
        popTv.setText("");
        distributeLocationList(rq_id, work_id, pageNumber + "", pageSize + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_push_lc_map, container, false);
        ButterKnife.bind(this, viewContainer);
        baiduMap = bmapView.getMap();
        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                if (mInfoWindow != null)
                    baiduMap.hideInfoWindow();
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

            }
        });
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final int post = getMarkerPostion(marker);
                popTv.setText(pushLocationShowMapBean.getData().getList().get(post).getShop_name());
                LatLng lt = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                mInfoWindow = new InfoWindow(popViwe, lt, -120);
                popTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), PushOnListActivity.class).putExtra("rq_id", rq_id).putExtra("work_id", work_id).putExtra("shopId",pushLocationShowMapBean.getData().getList().get(post).getShop_id()));
                    }
                });
                baiduMap.showInfoWindow(mInfoWindow);
                return false;
            }
        });
        locations = (MyBaiduLocation) ACache.get(getActivity()).getAsObject("myBaiduLocation");
        if (locations == null) {
            startLocation();
        } else {
            MyLocationData locData = new MyLocationData.Builder()
                    //.accuracy(locations.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(locations.getLatitude())
                    .longitude(locations.getLongitude()).build();
            // 设置定位数据
            baiduMap.setMyLocationData(locData);
            gotoLocation(locations.getLatitude(), locations.getLongitude());
        }
        return viewContainer;
    }

    private void startLocation() {
        if (baiduLocation == null)
            baiduLocation = new BaiduLocation(getActivity());
        baiduLocation.setbDresult(new BDresult() {
            @Override
            public void onCallBack(final BDLocation location) {
                getActivity().runOnUiThread(new Runnable() {
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
                            //   poiInfo = new PoiInfo();
                            //   poiInfo.address = location.getAddrStr();
                            //   poiInfo.location = new LatLng(location.getLongitude(), location.getLatitude());
                            //   drawPop(location.getLatitude(), location.getLongitude(), location.getAddrStr());
                        } else {
                            //定位失败
                        }
                    }
                });
            }
        }).initBaiduLocation();
    }

    private int getMarkerPostion(Marker marker) {
        for (int a = 0; a < pushLocationShowMapBean.getData().getList().size(); a++) {
            if (pushLocationShowMapBean.getData().getList().get(a).getShop_addr_latitude() == marker.getPosition().latitude
                    && pushLocationShowMapBean.getData().getList().get(a).getShop_addr_longitude() == marker.getPosition().longitude) {
                return a;
            }
        }
        return 0;
    }

    @OnClick(R.id.location_iv)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_iv:
                startLocation();
                break;
        }
    }

    private void gotoLocation(Double location, Double longitude) {
        //设定中心点坐标
        LatLng cenpt = new LatLng(location, longitude);

        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                .zoom(14)
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

    public String getRq_id() {
        return rq_id;
    }

    public PushLcMapFragment setRq_id(String rq_id) {
        this.rq_id = rq_id;
        return this;
    }

    public String getWork_id() {
        return work_id;
    }

    public PushLcMapFragment setWork_id(String work_id) {
        this.work_id = work_id;
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void distributeLocationList(String rq_id, String work_id, String pageNumber, String pageSize) {
        RxRetrofitClient.getInstance(getActivity()).distributeLocationMapList(rq_id, work_id, pageNumber, pageSize, new Observer<PushLocationShowMapBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(getActivity(), "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PushLocationShowMapBean pushLocationShowBean) {
                if (pushLocationShowBean.getCode() == 1) {
                    noContontTv.setVisibility(View.GONE);
                    pushLocationShowMapBean = pushLocationShowBean;
                    if (pushLocationShowBean.getData().getList() != null && pushLocationShowBean.getData().getList().size() > 0) {
                        baiduMap.clear();
                        for (int a = 0; a < pushLocationShowBean.getData().getList().size(); a++) {
                            addMaker(pushLocationShowBean.getData().getList().get(a).getShop_addr_longitude()
                                    , pushLocationShowBean.getData().getList().get(a).getShop_addr_latitude()
                                    , pushLocationShowBean.getData().getList().get(a).getTerminal_cnt());
                            if (a == 0) {
                                gotoLocation(pushLocationShowBean.getData().getList().get(a).getShop_addr_latitude(), pushLocationShowBean.getData().getList().get(a).getShop_addr_longitude());
                            }
                        }
                        baiduMap.addOverlays(markerOptionses);
                    } else {
                        baiduMap.clear();
                        noContontTv.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
    }

    List<OverlayOptions> markerOptionses = new ArrayList<>();

    private void addMaker(double longitude, double location, int postion) {
        //定义Maker坐标点
//        LatLng point = new LatLng(location, longitude);
//
//        MarkerOptions ooD = new MarkerOptions().position(point).icons(giflist)
//                .zIndex(0).period(10);
//        // if (animationBox.isChecked()) {
//        // 生长动画
//        ooD.animateType(MarkerOptions.MarkerAnimateType.drop);
//        //  }
//        Marker mMarkerD = (Marker) (baiduMap.addOverlay(ooD));
//
//
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap);
//        //在地图上添加Marker，并显示
//        baiduMap.addOverlay(option);
//        markerList.add(mMarkerD);

        //增加标记

        MarkerOptions option = generateSendTaskMarker(location, longitude, postion);
        markerOptionses.add(option);


    }

    public MarkerOptions generateSendTaskMarker(double latitude, double longitude, int number) {
        View view = generateSendTaskTextView(number + "");
        MarkerOptions markerOptions = generateMarker(latitude, longitude, BitmapDescriptorFactory.fromView(view));
        if (null == markerOptions.getExtraInfo()) {
            markerOptions.extraInfo(new Bundle());
        }
        markerOptions.getExtraInfo().putInt(MARKER_NUMBER, number);
        markerOptions.zIndex(9);
        return markerOptions;
    }

    /**
     * 创建一个取派任务Marker的TextView对象
     *
     * @return
     */
    private View generateSendTaskTextView(String number) {
        LinearLayout parent = (LinearLayout) View.inflate(getActivity(), R.layout.item_marker, null);
        TextView tv = (TextView) parent.findViewById(R.id.tv_task);
        tv.setTextColor(getActivity().getResources().getColor(R.color.withe));
        tv.setText(number);
        if (Build.VERSION.SDK_INT > 15) {
            tv.setBackground(getActivity().getResources().getDrawable(R.mipmap.icon_shop_loc_full));
        } else {
            tv.setBackgroundDrawable(getActivity().getResources().getDrawable(R.mipmap.icon_shop_loc_full));
        }
        return parent;
    }


    /**
     * 创建一个标记对象
     *
     * @param latitude
     * @param longitude
     * @param driverDesc
     * @return
     */
    @NonNull
    private MarkerOptions generateMarker(double latitude, double longitude, BitmapDescriptor driverDesc) {
        MarkerOptions option = new MarkerOptions();
        option.zIndex(9);
        option.icon(driverDesc);
        option.animateType(MarkerOptions.MarkerAnimateType.none);
        option.draggable(false);
        option.position(new LatLng(latitude, longitude));
        return option;
    }


}
