package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.WeekAdater;
import com.gohoc.xiupuling.bean.KvBean;
import com.gohoc.xiupuling.ui.BasicActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectWeekActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<KvBean> data;

    private WeekAdater adater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_week);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("重复");

        data= (ArrayList<KvBean>) getIntent().getExtras().get("data");

        adater=new WeekAdater(R.layout.item_simple_clickbox,data);
        adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int postion) {
                data.get(postion).setCheck(!data.get(postion).isCheck());
                adater.notifyItemChanged(postion);
            }
        });
        recyclerView.setAdapter(adater);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        goback();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goback();
        }
        return false;
    }

    private void goback() {
        if(isMycheck()){
            setResult(RESULT_OK,new Intent().putExtra("data",data));
            finish();
        }else {
            Toast.makeText(mContext,"至少选择一项",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMycheck()
    {
        boolean ischeck = false;
        for (int i=0;i<data.size();i++){
            if(data.get(i).isCheck()){
                ischeck=true;
                break;
            }
        }
        return ischeck;
    }
}
