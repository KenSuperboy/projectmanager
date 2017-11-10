package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushGroupMenuBean;
import com.gohoc.xiupuling.bean.ShopAndGroupItemBean;
import com.gohoc.xiupuling.bean.TerminalListBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushOnShopActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.arr_right_iv)
    ImageView arrRightIv;
    @BindView(R.id.all_ll)
    LinearLayout allLl;
    @BindView(R.id.select_ll)
    LinearLayout selectLl;
    private ShopAndGroupItemBean shopAndGroupItemBean;
    private int orientation;
    private PushGroupMenuBean pushGroupMenuBean;   //选中的终端
    private static int TERMINAL_REQUEST_RESULT = 1000;
    private int count = 0;
    private ArrayList<TerminalListBean.DataBean> selectArrryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_on_shop);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("店铺内发布");
        shopAndGroupItemBean = (ShopAndGroupItemBean) getIntent().getExtras().get("shopAndGroupItemBean");
        orientation = getIntent().getIntExtra("orientation", 0);
        Log.d("TAG","屏幕类型："+orientation);
        pushGroupMenuBean = (PushGroupMenuBean) getIntent().getExtras().get("pushGroupMenuBean");
        selectArrryList = (ArrayList<TerminalListBean.DataBean>) getIntent().getExtras().get("selectArrryList");
        if (pushGroupMenuBean.getTyepe() == 1)//判断之前是否选择的是按店铺投递
        {
            //判断是否是之前选择的店铺
            if (pushGroupMenuBean.getShop().getShop_id().equals(shopAndGroupItemBean.getShopBean().getShop_id())) {
                arrRightIv.setVisibility(View.VISIBLE);
            } else {
                arrRightIv.setVisibility(View.GONE);
                selectArrryList = null;
            }
        } else if (pushGroupMenuBean.getTyepe() == 3) {
            //判断是否是之前选择的店铺
            if (!shopAndGroupItemBean.getShopBean().getShop_id().equals(pushGroupMenuBean.getShopId()))
                selectArrryList = null;
        }
        getShopTermList(shopAndGroupItemBean.getShopBean().getShop_id());
    }

    @OnClick({R.id.toolbar_left_title, R.id.all_ll, R.id.select_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.all_ll:
                pushGroupMenuBean.setShop(shopAndGroupItemBean.getShopBean());
                pushGroupMenuBean.setTyepe(1);
                setResult(RESULT_OK, new Intent().putExtra("pushGroupMenuBean", pushGroupMenuBean));
                finish();
                break;
            case R.id.select_ll:
                startActivityForResult(new Intent(PushOnShopActivity.this, PushSelectShopTerminalActivity.class)
                                .putExtra("shopId", shopAndGroupItemBean.getShopBean().getShop_id()).putExtra("type", 1)
                                .putExtra("shopName", shopAndGroupItemBean.getShopBean().getShop_name())
                                .putExtra("orientation", orientation)
                                .putExtra("pushGroupMenuBean", pushGroupMenuBean)
                                .putExtra("selectArrryList", selectArrryList)
                        , TERMINAL_REQUEST_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }


    public void getShopTermList(String id) {
        RxRetrofitClient.getInstance(this).getShopTermList(id, String.valueOf(orientation), new Observer<TerminalListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushOnShopActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TerminalListBean terminalListBean) {
                if (terminalListBean.getCode() == 1) {
                    for (int a = 0; a < terminalListBean.getData().size(); a++) {
                        if (terminalListBean.getData().get(a).getTerm_orientation() == orientation)
                            count++;
                    }
                    pushGroupMenuBean.setTerminalCount(count);
                }


            }
        });
    }


}
