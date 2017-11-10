package com.gohoc.xiupuling.ui.push;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.TermianlSelectAdater;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.bean.MoudlesLeve0Bean;
import com.gohoc.xiupuling.bean.TerminalListBean;
import com.gohoc.xiupuling.bean.TerminalSelectBean;
import com.gohoc.xiupuling.bean.TerminalSelectLeve0Bean;
import com.gohoc.xiupuling.bean.TerminalSelectLeve1Bean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushSelectTerminalActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.list)
    RecyclerView list;
    private String groupId;
    private String shopId;
    private int type = 0;// 0  共享群内终端   1 店铺终端
    private TerminalListBean terminalListBeans;
    private GroupTermianlListBean groupTermianlListBeans;
    private ArrayList<MultiItemEntity> menuList;
    private TermianlSelectAdater adater;
    private String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_select_terminal);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("终端选择");
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            groupId = getIntent().getStringExtra("groupId");
            getUnionTerminalListofMine(groupId);
        } else {
            shopId = getIntent().getStringExtra("shopId");
            shopName = getIntent().getStringExtra("shopName");
            getShopTermList(shopId);
        }

    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                break;
        }
    }

    private void getUnionTerminalListofMine(String union_id) {
        RxRetrofitClient.getInstance(PushSelectTerminalActivity.this).getUnionTerminalListofMine(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushSelectTerminalActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1) {
                    groupTermianlListBeans = groupTermianlListBean;
                    intitList();
                }

            }
        });

    }

    private void intitList() {
        menuList = new ArrayList<>();
        if (type == 0) {
            TerminalSelectLeve0Bean terminalSelectLeve0Bean=new TerminalSelectLeve0Bean();
            menuList.add(terminalSelectLeve0Bean);

            for (int a=0;a<groupTermianlListBeans.getData().size();a++)
            {
                terminalSelectLeve0Bean.addSubItem(new TerminalSelectLeve1Bean(groupTermianlListBeans.getData().get(a),String.format("%02d", a + 1)));
            }

            menuList.add(new MoudlesLeve0Bean("我的终端（" + groupTermianlListBeans.getData().size() + ")"));
            for (int a=0;a<groupTermianlListBeans.getData().size();a++) {
                menuList.add(new TerminalSelectBean(TerminalSelectBean.CHECK, groupTermianlListBeans.getData().get(a),String.format("%02d", a + 1)));
            }

        } else {
            TerminalSelectLeve0Bean terminalSelectLeve0Bean=new TerminalSelectLeve0Bean();
            menuList.add(new TerminalSelectLeve0Bean());
            for (int a=0;a<terminalListBeans.getData().size();a++ ) {
                terminalSelectLeve0Bean.addSubItem(new TerminalSelectLeve1Bean(terminalListBeans.getData().get(a),shopName,String.format("%02d", a + 1)));
            }
            menuList.add(new MoudlesLeve0Bean("我的终端（" + terminalListBeans.getData().size() + ")"));
            for (int a=0;a<terminalListBeans.getData().size();a++ ) {
                menuList.add(new TerminalSelectBean(TerminalSelectBean.CHECK, terminalListBeans.getData().get(a),shopName,String.format("%02d", a + 1)));
            }
        }
        adater=new TermianlSelectAdater(this,menuList);
        list.setAdapter(adater);
        list.setLayoutManager(new LinearLayoutManager(this));
        adater.setOnItemChange(new TermianlSelectAdater.OnItemChange() {
            @Override
            public void onAdd(int postion, Object obj) {
                TerminalSelectLeve0Bean terminalSelectLeve0Bean= (TerminalSelectLeve0Bean) menuList.get(0);
                TerminalSelectBean terminalSelectBean= (TerminalSelectBean) obj;
                if(!terminalSelectBean.isCheck())
                {
                    terminalSelectBean.setCheck(true);

                    TerminalSelectLeve1Bean terminalSelectLeve1Bean=new TerminalSelectLeve1Bean();
                    terminalSelectLeve1Bean.setGdata(terminalSelectBean.getGdataBean());
                    terminalSelectLeve1Bean.setSdata(terminalSelectBean.getTdataBean());
                    terminalSelectLeve1Bean.setSortNo(terminalSelectBean.getSortNo());
                    terminalSelectLeve1Bean.setShopName(terminalSelectBean.getShopName());

                    terminalSelectLeve0Bean.getSubItems().add(terminalSelectLeve1Bean);

                    for (int i = 0; i <menuList.size() ; i++) {
                        adater.collapse(i + adater.getHeaderLayoutCount(), false, false);
                    }
                    expandAll();
                    adater.notifyDataSetChanged();

                }
            }

            @Override
            public void onDel(int postion, Object obj) {
                 menuList.remove(postion);
                 TerminalSelectLeve0Bean t= (TerminalSelectLeve0Bean) menuList.get(0);
                 t.getSubItems().remove(postion-1);
                 adater.notifyDataSetChanged();

            }
        });
        expandAll();

    }

    public void getShopTermList(String id) {
        RxRetrofitClient.getInstance(PushSelectTerminalActivity.this).getShopTermList(id,null, new Observer<TerminalListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushSelectTerminalActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TerminalListBean terminalListBean) {
                if (terminalListBean.getCode() == 1) {
                    terminalListBeans = terminalListBean;
                    intitList();
                }


            }
        });
    }


    private void expandAll() {
        Logger.e(menuList.size()+"");
        for (int i = 0; i <menuList.size() ; i++) {
            adater.expand(i + adater.getHeaderLayoutCount(), false, false);
        }
    }

}
