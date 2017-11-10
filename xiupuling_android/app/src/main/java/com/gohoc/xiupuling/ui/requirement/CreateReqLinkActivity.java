package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.terminal.SelectBaiduMapActivity;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateReqLinkActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.link_to_adress_ll)
    LinearLayout linkToAdressLl;
    @BindView(R.id.link_to_web_ll)
    LinearLayout linkToWebLl;
    @BindView(R.id.adress_tv)
    TextView adressTv;
    @BindView(R.id.web_text_tv)
    TextView webTextTv;

    private PromotionInfoBean promotionInfoBean;
    private static int ADRESS_REQUEST_RESULT = 1000;
    private static int WEB_REQUEST_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_link);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("扫描链接");
        try {
            promotionInfoBean = (PromotionInfoBean) getIntent().getExtras().get("promotionInfoBean");
            if (promotionInfoBean.getLinkType() == 0) {
                adressTv.setText(promotionInfoBean.getLcoationName());
                webTextTv.setText("");
            } else {
                adressTv.setText("");
                webTextTv.setText(promotionInfoBean.getLinkUrl());
            }
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.link_to_adress_ll, R.id.link_to_web_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.link_to_adress_ll:
                startActivityForResult(new Intent(CreateReqLinkActivity.this, SelectBaiduMapActivity.class).putExtra("type","0").putExtra("promotionInfoBean", promotionInfoBean)
                        .putExtra("CreateReqLinkActivity", "CreateReqLinkActivity"), ADRESS_REQUEST_RESULT);
                break;
            case R.id.link_to_web_ll:
                startActivityForResult(new Intent(CreateReqLinkActivity.this, CreateReqLinkToWebActivity.class).putExtra("promotionInfoBean", promotionInfoBean), WEB_REQUEST_RESULT);
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == WEB_REQUEST_RESULT) {
                if (null != data) {
                    setResult(RESULT_OK, data);
                    finish();
                }
            } else if (requestCode == ADRESS_REQUEST_RESULT) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        PoiInfo poiInfos = (PoiInfo) d.get("poiInfo");
                        if (poiInfos != null) {
                            promotionInfoBean.setLatitude(poiInfos.location.latitude + "");
                            promotionInfoBean.setLongitude(poiInfos.location.longitude + "");
                            promotionInfoBean.setLcoationName(poiInfos.address + "");
                            promotionInfoBean.setLinkType(0);
                            setResult(RESULT_OK, new Intent().putExtra("promotionInfoBean", promotionInfoBean));
                            finish();
                        }
                    }

                }

            }

        }
    }


}
