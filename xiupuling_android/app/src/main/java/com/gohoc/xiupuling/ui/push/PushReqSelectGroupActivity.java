package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.PushGroupAdater;
import com.gohoc.xiupuling.bean.GroupBean;
import com.gohoc.xiupuling.bean.PushGroupMenuBean;
import com.gohoc.xiupuling.bean.ShopAndGroupItemBean;
import com.gohoc.xiupuling.bean.ShopBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushReqSelectGroupActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private GroupBean groupBeans;
    private ShopBean shopBeans;
    private PushGroupAdater adater;
    private ArrayList<ShopAndGroupItemBean> shopAndGroupItemBean;
    private int orientation;
    private PushGroupMenuBean pushGroupMenuBean;
    private static int TERMINAL_REQUEST_RESULT = 1000;
    private String userId;
    private boolean combination;//如果是true表示是作品组合包

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_select_group);
        ButterKnife.bind(this);
        userId = Credential.getInstance().getCurUser(PushReqSelectGroupActivity.this).getData().getUser_id();
        combination=getIntent().getBooleanExtra("combination",false);
        getMyUnionList();
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("群组");
        orientation = getIntent().getIntExtra("orientation", 0);
        Log.d("TAG","1111群组内类型："+orientation);
        pushGroupMenuBean = (PushGroupMenuBean) getIntent().getExtras().get("pushGroupMenuBean");
    }

    private void getMyUnionList() {

        RxRetrofitClient.getInstance(this).getMyUnionList("all", "name", new Observer<GroupBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqSelectGroupActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupBean groupBean) {

                if (groupBean.getCode() == 1) {
                    groupBeans = groupBean;
                    getTerminalList();
                }

            }
        });
    }

    private void initList() {
        //0共享群 1私有群 2连锁群  3媒体群 4联动群组
        shopAndGroupItemBean = new ArrayList<>();

        if(combination){
            //作品组合包进来，只有私有群和连锁群

            // 1私有群
            int has0 = 0;
            shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.TEXT, "私有群组"));
            for (GroupBean.DataBean db : groupBeans.getData()) {
                if (db.getUnion_type() == 1) {
                    shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.GROUP, db));
                    has0 = 1;
                }
            }
            if (has0 == 0)
                shopAndGroupItemBean.remove(shopAndGroupItemBean.size() - 1);

            //连锁群
            int has2 = 0;
            shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.TEXT, "连锁群组"));
            for (GroupBean.DataBean db : groupBeans.getData()) {
                if (db.getUnion_type() == 2 && db.getUser_id().equals(userId)) {
                    has2 = 1;
                    shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.GROUP, db));
                }
            }
            if (has2 == 0)
                shopAndGroupItemBean.remove(shopAndGroupItemBean.size() - 1);

        }else {
            // 1私有群
            int has0 = 0;
            shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.TEXT, "私有群组"));
            for (GroupBean.DataBean db : groupBeans.getData()) {
                if (db.getUnion_type() == 1) {
                    shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.GROUP, db));
                    has0 = 1;
                }
            }
            if (has0 == 0)
                shopAndGroupItemBean.remove(shopAndGroupItemBean.size() - 1);


            //0共享群
            if (Credential.getInstance().getCurUser(this).getData().getShared_platform() == 1) {
                int has1 = 0;
                shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.TEXT, "共享群组"));
                for (GroupBean.DataBean db : groupBeans.getData()) {
                    if (db.getUnion_type() == 0) {
                        has1 = 1;
                        shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.GROUP, db));
                    }
                }
                if (has1 == 0)
                    shopAndGroupItemBean.remove(shopAndGroupItemBean.size() - 1);
            }

            //连锁群
            int has2 = 0;
            shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.TEXT, "连锁群组"));
            for (GroupBean.DataBean db : groupBeans.getData()) {
                if (db.getUnion_type() == 2 && db.getUser_id().equals(userId)) {
                    has2 = 1;
                    shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.GROUP, db));
                }
            }
            if (has2 == 0)
                shopAndGroupItemBean.remove(shopAndGroupItemBean.size() - 1);



            if (Credential.getInstance().getCurUser(this).getData().getShared_platform() == 1) {
                //3媒体群
                int has3 = 0;
                shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.TEXT, "媒体群组"));
                for (GroupBean.DataBean db : groupBeans.getData()) {
                    if (db.getUnion_type() == 3 && db.getUser_id().equals(userId)) {
                        has3 = 1;
                        shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.GROUP, db));
                    }
                }
                if (has3 == 0)
                    shopAndGroupItemBean.remove(shopAndGroupItemBean.size() - 1);
            }

            //联动群组
            /*int has4 = 0;
            shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.TEXT, "联动群组"));
            for (GroupBean.DataBean db : groupBeans.getData()) {
                if (db.getUnion_type() == 4) {
                    has4 = 1;
                    shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.GROUP, db));
                }
            }
            if (has4 == 0)
                shopAndGroupItemBean.remove(shopAndGroupItemBean.size() - 1);*/
        }



        shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.TEXT, "店铺"));
        for (ShopBean.DataBean db : shopBeans.getData()) {
            shopAndGroupItemBean.add(new ShopAndGroupItemBean(ShopAndGroupItemBean.SHOP, db));
        }
        adater = new PushGroupAdater(this, shopAndGroupItemBean);
        list.setAdapter(adater);
        list.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        list.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (baseQuickAdapter.getItemViewType(i) == ShopAndGroupItemBean.GROUP) {//选择了群
                    startActivityForResult(new Intent(PushReqSelectGroupActivity.this, PushOnGruopActivity.class)
                                    .putExtra("shopAndGroupItemBean", shopAndGroupItemBean.get(i))
                                    .putExtra("orientation", orientation)
                                    .putExtra("pushGroupMenuBean", pushGroupMenuBean)
                                    .putExtra("combination", combination)
                                    .putExtra("selectArrryList", pushGroupMenuBean.getGroupTerminalList())

                            , TERMINAL_REQUEST_RESULT);
                } else if (baseQuickAdapter.getItemViewType(i) == ShopAndGroupItemBean.SHOP) {//选择了店铺
                    startActivityForResult(new Intent(PushReqSelectGroupActivity.this, PushOnShopActivity.class)
                                    .putExtra("shopAndGroupItemBean", shopAndGroupItemBean.get(i))
                                    .putExtra("orientation", orientation)
                                    .putExtra("pushGroupMenuBean", pushGroupMenuBean)
                                    .putExtra("selectArrryList", pushGroupMenuBean.getShopTerminalList())

                            , TERMINAL_REQUEST_RESULT);
                }
            }
        });

    }

    private void getTerminalList() {

        RxRetrofitClient.getInstance(this).getTerminalList("name", new Observer<ShopBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushReqSelectGroupActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ShopBean shopBean) {
                if (shopBean.getCode() == 1) {
                    shopBeans = shopBean;
                    initList();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }
}
