package com.gohoc.xiupuling.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.GroupAddLinkAdapter;
import com.gohoc.xiupuling.bean.CombinationAddTerminalBean;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 联动群组情况下进来添加
* */
public class GroupAddTerminal_Link_Activity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    private String union_id, shop_id;
    private GroupAddLinkAdapter mGroupAddLinkAdapter;
    public CombinationAddTerminalBean mCombinationAddTerminalBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add_link_terminal_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        mToolbarTitle.setText("添加终端");

        initView();
        initListener();
        initData();
    }

    private void initView() {
        mGroupAddLinkAdapter = new GroupAddLinkAdapter(mContext);
        mListView.setAdapter(mGroupAddLinkAdapter);
    }

    //根据店铺获取对应终端
    public void getShopTermList(String shop_id, String orientation, String is_link) {
        RxRetrofitClient.getInstance(this).getCombinationShopTermList(shop_id, orientation, is_link, new Observer<CombinationAddTerminalBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupAddTerminal_Link_Activity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(CombinationAddTerminalBean mycombinationAddTerminalBean) {
                mCombinationAddTerminalBean = mycombinationAddTerminalBean;
                if (mycombinationAddTerminalBean.code == 1 && mycombinationAddTerminalBean.data != null && mycombinationAddTerminalBean.data.size() > 0) {
                    mListView.setVisibility(View.VISIBLE);
                    tv_empty.setVisibility(View.GONE);
                    LogUtil.d("获取可加入的终端信息");
                    mGroupAddLinkAdapter.setmLists(mycombinationAddTerminalBean.data);
                } else {
                    mListView.setVisibility(View.GONE);
                    mTvTip.setVisibility(View.GONE);
                    tv_empty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mCombinationAddTerminalBean.data.get(position).flag == 0) {
                    //执行选中
                    mCombinationAddTerminalBean.data.get(position).flag = 1;
                } else {
                    //取消选中
                    mCombinationAddTerminalBean.data.get(position).flag = 0;
                }
                mGroupAddLinkAdapter.setmLists(mCombinationAddTerminalBean.data);
            }
        });
    }

    private void initData() {
        union_id = getIntent().getStringExtra("id");
        shop_id = getIntent().getStringExtra("shop_id");
        getShopTermList(shop_id, 2 + "", 1 + "");
    }

    private void addMultiTermsToUnion() {
        String sb = "";

        for (int a = 0; a < mCombinationAddTerminalBean.data.size(); a++) {
            if (mCombinationAddTerminalBean.data.get(a).flag == 1)
                sb += mCombinationAddTerminalBean.data.get(a).terminal_id + ",";
        }
        if (sb.length() < 1) {
            Utils.toast(this, "请选择需要加入的终端");
            return;
        }

        LogUtil.d("各种终端：" + union_id + "======:" + sb.toString());
        RxRetrofitClient.getInstance(GroupAddTerminal_Link_Activity.this).addCombinationMultiTermsToUnion(union_id + "", sb.toString(), new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupAddTerminal_Link_Activity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {

                if (emptyBean.code == 1) {
                    EventBus.getDefault().post(new Event.FinishMyShopActivityEvent());
                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                    EventBus.getDefault().post(new Event.GroupEvent(null));
                    setResult(RESULT_OK);
                    GroupAddTerminal_Link_Activity.this.finish();
                } else
                    Utils.toast(GroupAddTerminal_Link_Activity.this, emptyBean.message);
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                addMultiTermsToUnion();
                break;
        }
    }
}
