package com.gohoc.xiupuling.ui.terminal;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.MyShopListAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.ShopBean;
import com.gohoc.xiupuling.bean.ShopLimtBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.account.MemberAuthorizationActivity;
import com.gohoc.xiupuling.ui.account.MemberShopActivity;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SettingBelongShopActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    ImageView toolbarRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.no_date_tv)
    TextView noDateTv;
    @BindView(R.id.no_date_rv)
    RelativeLayout noDateRv;
    private MyShopListAdater adater;
    private String shopId = "";
    private ShopBean shopBeans;
    private int edit;
    private static int TERMINAL_NO_REQUEST_RESULT = 1000;
    private static int ADD_REQUEST_RESULT = 1001;
    private String terminalNo;
    private Window window;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_belong_shop);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("门店选择");
        shopId = getIntent().getStringExtra("shopId");
        terminalNo = getIntent().getStringExtra("terminalNo");
        edit = getIntent().getIntExtra("edit", 0);

        SpanUtils spanUtils = new SpanUtils();
        spanUtils.append("亲,您还没有门店").appendLine().append("赶紧点击右上角").append("\"+\"").setForegroundColor(Color.parseColor("#ff0000")).append("新建一个");
        noDateTv.setText(spanUtils.create());
        noDateRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShopCntRight();
            }
        });
        getTerminalList();
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                checkShopCntRight();
                break;
        }
    }

    private void checkShopCntRight() {
        RxRetrofitClient.getInstance(this).checkShopCntRight(new Observer<ShopLimtBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SettingBelongShopActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ShopLimtBean shopLimtBean) {
                if (shopLimtBean.getCode() == 1) {
                    startActivityForResult(new Intent(SettingBelongShopActivity.this, AddShopActivity.class).putExtra("type", 1), ADD_REQUEST_RESULT);
                } else {
                    showShopPop(shopLimtBean.getShopcnt() + "", shopLimtBean.getShopowncnt() + "", "门店");
                }
            }
        });
    }

    private void showShopPop(String count, String ownCount, String title) {
        dialog = new AlertDialog.Builder(this,
                R.style.dialogTips).create();
        dialog.show();
        window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(false);
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容

        window.setContentView(R.layout.dialog_shop_limt_alert);

        window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(SettingBelongShopActivity.this, MemberShopActivity.class));

            }
        });
        window.findViewById(R.id.check_auth_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(SettingBelongShopActivity.this, MemberAuthorizationActivity.class));
            }
        });
        window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        SpanUtils su = new SpanUtils();
        su.append("您目前只能管理" + count + "个" + title + "， 如果需管理更多门店，请前往会员商城或参与")
                .append("官方活动").setForegroundColor(Color.parseColor("#38b2fd"))
                .append("扩充拥有数。");
        TextView tips_title_tv = (TextView) window.findViewById(R.id.tips_title_tv);
        tips_title_tv.setText(su.create());
        TextView shop_count_tv = (TextView) window.findViewById(R.id.shop_count_tv);
        su = new SpanUtils();
        su.append(title + " ").append(ownCount).setForegroundColor(Color.parseColor("#38b2fd"));
        shop_count_tv.setText(su.create());
        su = new SpanUtils();
        TextView check_auth_tv = (TextView) window.findViewById(R.id.check_auth_tv);
        check_auth_tv.setText(su.append(" 查询其它").setForegroundColor(Color.parseColor("#929292")).append("会员权限").setForegroundColor(Color.parseColor("#38b2fd")).create());
    }

    private void getTerminalList() {

        RxRetrofitClient.getInstance(SettingBelongShopActivity.this).getTerminalList("star", new Observer<ShopBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ShopBean shopBean) {
                if (shopBean.getCode() == 1) {
                    shopBeans = shopBean;
                    if (shopBeans.getData().size() > 0) {
                        initList(shopBean);
                        recyclerView.setVisibility(View.VISIBLE);
                        noDateRv.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noDateRv.setVisibility(View.VISIBLE);
                    }


                }

            }
        });
    }

    private void initList(final ShopBean shopBean) {
        adater = new MyShopListAdater(SettingBelongShopActivity.this, shopBean);
        recyclerView.setAdapter(adater);
        recyclerView.setLayoutManager(new LinearLayoutManager(SettingBelongShopActivity.this));
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        adater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (shopBean.getData().get(position).isSelect())
                    return;

                for (int a = 0; a < shopBean.getData().size(); a++) {
                    if (shopBean.getData().get(a).isSelect()) {
                        shopBean.getData().get(a).setSelect(false);
                        adater.notifyItemChanged(a);
                    }
                }
                shopBean.getData().get(position).setSelect(true);
                adater.notifyItemChanged(position);
                if (edit == 0) {
                    setResult(RESULT_OK, new Intent().putExtra("shopName", shopBean.getData().get(position).getShop_name() + "")
                            .putExtra("shopId", shopBean.getData().get(position).getShop_id() + "").putExtra("shop", shopBean.getData().get(position)));
                    SettingBelongShopActivity.this.finish();
                } else {
                    startActivityForResult(new Intent(SettingBelongShopActivity.this, SettingTerminalNoActivity.class).putExtra("shopId", shopBean.getData().get(position).getShop_id()
                    ).putExtra("terminalNo", terminalNo), TERMINAL_NO_REQUEST_RESULT);

                }

                Logger.d(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        setChecked(shopId, shopBean.getData());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TERMINAL_NO_REQUEST_RESULT) {
                if (null != data) {

                    String terminalNo = data.getStringExtra("terminalNo");
                    String shopId = data.getStringExtra("shopId");

                    setResult(RESULT_OK, new Intent().putExtra("terminalNo", terminalNo).putExtra("shopId", shopId));
                    SettingBelongShopActivity.this.finish();
                }
            } else if (requestCode == ADD_REQUEST_RESULT) {
                getTerminalList();
            }
        }
    }

    private void setChecked(String id, List<ShopBean.DataBean> data) {
        Logger.e(id);
        for (int a = 0; a < data.size(); a++) {
            if ((data.get(a).getShop_id() + "".trim()).equals(id)) {
                data.get(a).setSelect(true);
                adater.notifyItemChanged(a);
            }
        }

    }
}
