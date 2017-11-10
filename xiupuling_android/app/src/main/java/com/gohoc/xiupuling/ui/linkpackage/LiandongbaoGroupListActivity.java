package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.linkpackage.GroupListAdapter;
import com.gohoc.xiupuling.bean.GroupBean;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 我的联动群组
* */
public class LiandongbaoGroupListActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listView)
    ListView mListView;

    private String selectString;
    private GroupListAdapter mGroupListAdapter;
    private GroupBean mGroupBean;
    private String musicType,musicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_group_list_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        mToolbarTitle.setText("我的联动群组");
        initListener();
        initDatas();
    }

    private void initListener() {
        mGroupListAdapter=new GroupListAdapter(mContext);
        mListView.setAdapter(mGroupListAdapter);
        mGroupListAdapter.setCallback(new Callback2() {
            @Override
            public void callBack(String string1,String string2) {
                if(!TextUtils.isEmpty(string1)){
                    setSelectData(string1);
                    setResult(RESULT_OK,new Intent().putExtra("name",musicName).putExtra("id",string2));
                    finish();
                }
            }
        });
    }

    private void initDatas() {
        selectString=getIntent().getStringExtra("name");
        getMyGroupList("link","name");
    }

    private void getMyGroupList(String filterby, String orderby) {

        RxRetrofitClient.getInstance(mContext).getMyUnionList(filterby, orderby, new Observer<GroupBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GroupBean groupBean) {
                if(groupBean!=null&&groupBean.getData().size()>0){
                    mGroupBean=groupBean;
                    mGroupListAdapter.setmLists(mGroupBean.getData());
                    setSelectData(selectString);
                }
            }
        });
    }

    //通过
    private void setSelectData(String name)
    {
        for (int i=0;i<mGroupBean.getData().size();i++){
            if(mGroupBean.getData().get(i).getUnion_name().equals(name)){
                mGroupBean.getData().get(i).setFlag(1);
                musicName=mGroupBean.getData().get(i).getUnion_name();
            }else {
                mGroupBean.getData().get(i).setFlag(0);
            }
        }
        mGroupListAdapter.setmLists(mGroupBean.getData());
    }


    @OnClick(R.id.toolbar_title)
    public void onViewClicked() {
        finish();
    }
}
