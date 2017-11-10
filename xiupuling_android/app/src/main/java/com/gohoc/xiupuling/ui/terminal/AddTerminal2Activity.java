package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ShopBean;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.wuxiaolong.androidutils.library.VersionUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class AddTerminal2Activity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.terminal_serisa_no_tv)
    TextView terminalSerisaNoTv;
    @BindView(R.id.belong_shop_tv)
    TextView belongShopTv;
    @BindView(R.id.belong_shop_ll)
    LinearLayout belongShopLl;
    @BindView(R.id.terminal_no_tv)
    TextView terminalNoTv;
    @BindView(R.id.terminal_no_ll)
    LinearLayout terminalNoLl;
    @BindView(R.id.terminal_orientation_tv)
    TextView terminalOrientationTv;
    @BindView(R.id.terminal_orientation_lv)
    LinearLayout terminalOrientationLv;
    @BindView(R.id.logout_button_ll)
    LinearLayout logoutButtonLl;
    private static int SHOP_REQUEST_RESULT = 1000;
    private static int NO_REQUEST_RESULT = 1001;
    private static int ORIENTATUION_REQUEST_RESULT = 1002;
    private String token = "";
    private String shopId = "";
    private String shopName = "";
    private ShopBean.DataBean shop;
    private String no;
    private Integer og=10;
    private ShopDetailsBean shopDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terminal2);
        ButterKnife.bind(this);
        token = getIntent().getStringExtra("token");
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("新建终端");
        terminalSerisaNoTv.setText("终端序列号:" + token + "");
        try {
            shopDetailsBeans = (ShopDetailsBean) getIntent().getExtras().get("shopDetailsBeans");
            shopId = shopDetailsBeans.getData().getShop_id();
            shopName = shopDetailsBeans.getData().getShop_name();
            belongShopTv.setText(shopName + "");
        } catch (Exception e) {
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.belong_shop_ll, R.id.terminal_no_ll, R.id.terminal_orientation_lv, R.id.logout_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                AddTerminal2Activity.this.finish();
                break;
            case R.id.belong_shop_ll:
                startActivityForResult(new Intent(AddTerminal2Activity.this, SettingBelongShopActivity.class).putExtra("shopId", shopId), SHOP_REQUEST_RESULT);
                break;
            case R.id.terminal_no_ll:
                if (null != shop)
                    startActivityForResult(new Intent(AddTerminal2Activity.this, SettingTerminalNoActivity.class).putExtra("shopId", shop.getShop_id()).putExtra("terminalNo", no), NO_REQUEST_RESULT);
                else if (shopDetailsBeans != null)
                    startActivityForResult(new Intent(AddTerminal2Activity.this, SettingTerminalNoActivity.class).putExtra("shopId", shopDetailsBeans.getData().getShop_id()).putExtra("terminalNo", no), NO_REQUEST_RESULT);
                else

                    Utils.toast(AddTerminal2Activity.this, "请先选择门店");
                break;
            case R.id.terminal_orientation_lv:
                startActivityForResult(new Intent(AddTerminal2Activity.this, SettingTerminalOgActivity.class).putExtra("orientation", og), ORIENTATUION_REQUEST_RESULT);
                break;
            case R.id.logout_button_ll:
                post();
                break;
        }
    }

    private void post() {
        if (token.isEmpty()) {
            Utils.toast(AddTerminal2Activity.this, "数据异常，请重新扫描");
            return;
        }
        if (shopId.isEmpty()) {
            Utils.toast(AddTerminal2Activity.this, "请选择店铺");
            return;
        }
        if (no.isEmpty()) {
            Utils.toast(AddTerminal2Activity.this, "请选择终端编号");
            return;
        }
        if (null == og) {
            Utils.toast(AddTerminal2Activity.this, "请选择放置方式");
            return;
        }


        RxRetrofitClient.getInstance(AddTerminal2Activity.this).saveTerminal(token, "1000", shopId, no, VersionUtil.getVersionCode(AddTerminal2Activity.this) + "", og + "", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AddTerminal2Activity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(AddTerminal2Activity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshTerminalListEvent());
                    startActivity(new Intent(AddTerminal2Activity.this, MainActivity.class));
                    AddTerminal2Activity.this.finish();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SHOP_REQUEST_RESULT) {
                if (null != data) {

                    shopId = data.getStringExtra("shopId");
                    shopName = data.getStringExtra("shopName");
                    shop = (ShopBean.DataBean) data.getExtras().get("shop");


                    if (!shopName.isEmpty()) {
                        belongShopTv.setText(shopName + "");
                    }

                    //清空编号选择
                    no = "";
                    terminalNoTv.setText("未设置");


                }
            } else if (requestCode == NO_REQUEST_RESULT) {

                if (null != data) {

                    no = data.getStringExtra("terminalNo");
                    terminalNoTv.setText(no + "");

                    if (!shopName.isEmpty()) {
                        belongShopTv.setText(shopName + "");
                    }
                }
            } else if (requestCode == ORIENTATUION_REQUEST_RESULT) {
                if (null != data) {
                    og = data.getIntExtra("orientation", 0);
                    if (og == 0)
                        terminalOrientationTv.setText("横屏");
                    else
                        terminalOrientationTv.setText("竖屏");
                }
            }
        }
    }
}
