package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class DistrictListActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.district_list)
    RecyclerView districtList;
    private ProvinceListAdater adater;
    private MenuLocationBean menuLocationBean;
    private int type = 0;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("地区信息");

        try {
            Bundle db=getIntent().getExtras();
            if (null != db) {
                menuLocationBean = (MenuLocationBean) db.get("menuLocationBean");
                getCityList();
                title = getIntent().getStringExtra("title");
                type = getIntent().getIntExtra("type", 0);
                if (!TextUtils.isEmpty(title))
                    toolbarTitle.setText(title);
            }
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        DistrictListActivity.this.finish();
    }

    private void getCityList() {
        RxRetrofitClient.getInstance(DistrictListActivity.this).getCitylist(menuLocationBean.getCityId(), "3", new Observer<LocationBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(DistrictListActivity.this,"请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(final LocationBean locationBean) {
                if (locationBean.getCode() == 1) {
                    adater = new ProvinceListAdater(DistrictListActivity.this, locationBean);
                    districtList.setAdapter(adater);
                    districtList.setLayoutManager(new LinearLayoutManager(DistrictListActivity.this));
                    // provinceList.setItemAnimator(new DefaultItemAnimator());
                    adater.setOnItemClickLitener(new OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Logger.d(position);
                            menuLocationBean.setDistrict(locationBean.getData().get(position).getName() + "");
                            menuLocationBean.setDistrictId(locationBean.getData().get(position).getId() + "");
                            setResult(RESULT_OK,new Intent().putExtra("menuLocationBean",menuLocationBean));
                            DistrictListActivity.this.finish();
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });

                }
            }
        });
    }
}
