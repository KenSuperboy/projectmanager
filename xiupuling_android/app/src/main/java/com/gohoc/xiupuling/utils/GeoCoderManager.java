package com.gohoc.xiupuling.utils;


import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.qiniu.android.utils.StringUtils;

/**
 * Created by keke on 2017/3/27 0027.
 * <p>
 * 反地理编码查询,通过经纬度查地址or通过地址查经纬度
 */

public class GeoCoderManager {

    private static final String TAG = "GeoCoderManager";

    private static GeoCoderManager mInstance;

    private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用

    private OnGetGeoCoderResultListener onGetGeoCoderResultListener = null;

    private GeoCoderListener geoCoderListener;
    private GeoCoderCityListener geoCoderCityListener;


    public interface GeoCoderListener {

        void onGeoCoderSucceed(double latitude, double longitude);

        void onGeoCoderAddressSucceed(String address);

        void onGeoCoderError();
    }

    public interface GeoCoderCityListener {

        void onGeoCoderCitySucceed(String city);

        void onGeoCoderCityError();
    }


    public void searchGeoCode(String address, GeoCoderListener geoCoderListener) {
        if (StringUtils.isNullOrEmpty(address)) {
            Log.i(TAG, "address is null");
            return;
        }
        initGeoCoder();
        this.geoCoderListener = geoCoderListener;
        // Geo搜索
        mSearch.geocode(new GeoCodeOption().city("").address(address));
    }

    public void searchGeoCode(double latitude, double longitude, GeoCoderListener geoCoderListener) {
        if (StringUtils.isNullOrEmpty(String.valueOf(latitude)) || StringUtils.isNullOrEmpty(String.valueOf(longitude))) {
            Log.i(TAG, "latitude or longitude is null");
            return;
        }
        initGeoCoder();
        this.geoCoderListener = geoCoderListener;

        LatLng ptCenter = new LatLng(latitude, longitude);
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(ptCenter));
    }

    public void searchGeoCodeCity(double latitude, double longitude, GeoCoderCityListener geoCoderCityListener) {
        if (StringUtils.isNullOrEmpty(String.valueOf(latitude)) || StringUtils.isNullOrEmpty(String.valueOf(longitude))) {
            Log.i(TAG, "latitude or longitude is null");
            return;
        }
        initGeoCoder();
        this.geoCoderCityListener = geoCoderCityListener;
        LatLng ptCenter = new LatLng(latitude, longitude);
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(ptCenter));
    }


    public static synchronized GeoCoderManager getInstance() {

        synchronized (GeoCoderManager.class) {
            if (mInstance == null) {
                mInstance = new GeoCoderManager();
            }
        }
        return mInstance;
    }

    public GeoCoderManager() {
        initGeoCoder();
    }

    private void initGeoCoder() {
        // 初始化搜索模块，注册事件监听
        if (mSearch == null) {
            mSearch = GeoCoder.newInstance();
        }
        if (onGetGeoCoderResultListener == null) {
            initOnGetGeoCoderResultListener();
        }
        if (null != mSearch && null != onGetGeoCoderResultListener) {
            mSearch.setOnGetGeoCodeResultListener(onGetGeoCoderResultListener);
        }
    }

    private void initOnGetGeoCoderResultListener() {

        onGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.i(TAG, "抱歉，未能找到结果");
                    if (null != geoCoderListener) {
                        geoCoderListener.onGeoCoderError();
                    }
                    return;
                }
                double latitude = result.getLocation().latitude;
                double longitude = result.getLocation().longitude;
                if (null != geoCoderListener) {
                    geoCoderListener.onGeoCoderSucceed(latitude, longitude);
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Log.i(TAG, "抱歉，未能找到结果");
                    if (null != geoCoderListener) {
                        geoCoderListener.onGeoCoderError();
                    }
                    if (null != geoCoderCityListener) {
                        geoCoderCityListener.onGeoCoderCityError();
                    }
                    return;
                }

                String address = result.getAddress();
                ReverseGeoCodeResult.AddressComponent addressDetail = result.getAddressDetail();
                if (null != addressDetail) {
                    String city = addressDetail.city;
                    if (null != geoCoderCityListener) {
                        geoCoderCityListener.onGeoCoderCitySucceed(city);
                    }
                }
                if (null != geoCoderListener) {
                    geoCoderListener.onGeoCoderAddressSucceed(address);
                }
            }

        };
    }
}
