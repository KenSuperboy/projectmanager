package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.MusicListAdapter;
import com.gohoc.xiupuling.bean.MusicListBean;
import com.gohoc.xiupuling.callback.Callback1;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 音乐列表
* */
public class MusicListActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listView)
    ListView mListView;

    private String selectString;
    private MusicListAdapter mMusicListAdapter;
    private MusicListBean mMusicListBean;
    private String musicType,musicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        mToolbarTitle.setText("背景音乐");
        initListener();
        initDatas();
    }

    private void initListener() {
        mMusicListAdapter=new MusicListAdapter(mContext);
        mListView.setAdapter(mMusicListAdapter);
        mMusicListAdapter.setCallback(new Callback1() {
            @Override
            public void callBack(String string) {
                if(!TextUtils.isEmpty(string)){
                    setSelectData(string);
                    setResult(RESULT_OK,new Intent().putExtra("type",musicType).putExtra("name",musicName));
                    finish();
                }
            }
        });
    }

    private void initDatas() {
        selectString=getIntent().getStringExtra("music");
        getMusicList();
    }

    private void getMusicList() {
        RxRetrofitClient.getInstance(MusicListActivity.this).getMusiclList(new Observer<MusicListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MusicListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(MusicListBean musicListBean) {
                LogUtil.d("记载成功");
                if(musicListBean.data.size()>0){
                    mMusicListBean=musicListBean;
                    MusicListBean.DataBean dataBean=new MusicListBean.DataBean();
                    dataBean.name="无";
                    mMusicListBean.data.add(0,dataBean);
                    mMusicListAdapter.setmLists(mMusicListBean.data);
                    setSelectData(selectString);
                }else {

                }
            }
        });
    }

    //通过
    private void setSelectData(String name)
    {
        for (int i=0;i<mMusicListBean.data.size();i++){
            if(mMusicListBean.data.get(i).name.equals(name)){
                mMusicListBean.data.get(i).flag=1;
                musicType=mMusicListBean.data.get(i).bgaudio_type;
                musicName=mMusicListBean.data.get(i).name;
            }else {
                mMusicListBean.data.get(i).flag=0;
            }
        }
        mMusicListAdapter.setmLists(mMusicListBean.data);
    }


    @OnClick(R.id.toolbar_title)
    public void onViewClicked() {
        finish();
    }
}
