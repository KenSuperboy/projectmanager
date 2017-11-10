package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.GroupTypeAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.KvBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGroupTypeActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private GroupTypeAdater adater;
    private List<KvBean> menus = new ArrayList<>();
    private KvBean groupType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_type);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        if (Credential.getInstance().getCurUser(this).getData().getShared_platform() == 1) {
            menus.add(new KvBean("0", "共享群组"));
            menus.add(new KvBean("1", "私有群组"));
            menus.add(new KvBean("2", "连锁群组"));
            menus.add(new KvBean("3", "媒体群组"));
            menus.add(new KvBean("4", "联动群组"));
        } else {
            menus.add(new KvBean("1", "私有群组"));
            menus.add(new KvBean("2", "连锁群组"));
        }


        toolbarTitle.setText("群组类型");

        try {
            groupType = (KvBean) getIntent().getExtras().get("groupType");
            Logger.e(groupType.getK() + "");
            if (null != groupType)
                for (int a = 0; a < menus.size(); a++) {
                    if (groupType.getK().equals(menus.get(a).getK()))
                        menus.get(a).setCheck(true);
                }
            else {
                groupType = menus.get(0);
                menus.get(0).setCheck(true);
            }
        } catch (Exception e) {
            Logger.e(e.toString() + "");
            groupType = menus.get(0);
            menus.get(0).setCheck(true);
        }
        initList();
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        AddGroupTypeActivity.this.finish();
    }


    private void initList() {
        adater = new GroupTypeAdater(AddGroupTypeActivity.this, menus);
        list.setAdapter(adater);
        list.setLayoutManager(new LinearLayoutManager(AddGroupTypeActivity.this));
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        adater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int a = 0; a < menus.size(); a++) {
                    menus.get(a).setCheck(false);
                }
                menus.get(position).setCheck(true);
                Logger.d(position);
                adater.notifyDataSetChanged();
                setResult(RESULT_OK, new Intent().putExtra("groupType", menus.get(position)));
                AddGroupTypeActivity.this.finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
