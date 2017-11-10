package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.MyShopAdapter;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.MyshopListBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 我的店铺
* */
public class MyShopActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listView)
    ListView mListView;

    private MyShopAdapter mMyShopAdapter;
    private GroupDetailsBean groupDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("我的店铺");
        setStatusColor(R.color.colorPrimary);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FinishMyShopActivityEvent(Event.FinishMyShopActivityEvent message) {
        LogUtil.d("我的店铺");
        finish();
    }

    private void initView()
    {
        mMyShopAdapter=new MyShopAdapter(mContext);
        mListView.setAdapter(mMyShopAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyshopListBean.DataBean dataBean= (MyshopListBean.DataBean) parent.getAdapter().getItem(position);
                LogUtil.d("多少："+dataBean.shop_name);//跳转需要新建一个东西
                startActivity(new Intent(MyShopActivity.this, GroupAddTerminal_Link_Activity.class).putExtra("shop_id", dataBean.shop_id).putExtra("id",groupDetailsBeans.getData().getUnion_id()));
                //这个地方不一定要销毁
            }
        });
    }

    private void initData()
    {
        try {
            groupDetailsBeans = (GroupDetailsBean) getIntent().getExtras().get("groupDetailsBeans");
        } catch (Exception e) {
        }
        getMyshopList();
    }

    private void getMyshopList() {
        RxRetrofitClient.getInstance(mContext).getMyShoplList(new Observer<MyshopListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(mContext, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(MyshopListBean myshopListBean) {
                LogUtil.d("记载成功");
                if(myshopListBean.data.size()>0){
                    mMyShopAdapter.setmLists(myshopListBean.data);
                }
            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
