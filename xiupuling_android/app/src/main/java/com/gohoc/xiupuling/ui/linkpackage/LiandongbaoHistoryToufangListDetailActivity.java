package com.gohoc.xiupuling.ui.linkpackage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.linkpackage.LiandongbaoHistoryDetailAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.LinkPackageDetailBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.view.MyListView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 联动包历史投放记录---下面的包作品
* */
public class LiandongbaoHistoryToufangListDetailActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_detail_name)
    TextView mTvDetailName;
    @BindView(R.id.listView)
    MyListView mListView;
    @BindView(R.id.tv_ensure)
    TextView mTvEnsure;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.relative_empty_layout)
    RelativeLayout mRelativeEmptyLayout;
    private LiandongbaoHistoryDetailAdapter mLiandongbaoXinjianAdapter;
    private String link_id,link_snap_id,link_snap_name,union_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_history_list_detail_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("历史联动投放");
        setStatusColor(R.color.colorPrimary);

        initView();
        initData();
    }

    public class Mythread extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }

    private void initView() {
        mLiandongbaoXinjianAdapter = new LiandongbaoHistoryDetailAdapter(mContext);
        mLiandongbaoXinjianAdapter.setType(1);
        mListView.setAdapter(mLiandongbaoXinjianAdapter);
    }

    private void postLeader(final String link_id,String detail_id) {
        RxRetrofitClient.getInstance(mContext).leaderLinkPackage(link_id,detail_id, new Observer<EmptyBean>() {
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
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("删除成功");
                if(emptyBean.code==1){
                    getLiandongbaoHistoryDetail(link_snap_id);
                }
            }
        });
    }

    private void getLiandongbaoHistoryDetail(String link_snap_id) {
        RxRetrofitClient.getInstance(LiandongbaoHistoryToufangListDetailActivity.this).getLiandongbaoHistory_List(link_snap_id, new Observer<LinkPackageDetailBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoHistoryToufangListDetailActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(LinkPackageDetailBean linkPackageDetailBean) {
                LogUtil.d("获取成功");
                if(linkPackageDetailBean.code==1&&linkPackageDetailBean.data.size()>0){
                    mLiandongbaoXinjianAdapter.setmLists(linkPackageDetailBean.data);
                }
            }
        });
    }

    private void initData() {
        link_snap_id = getIntent().getStringExtra("link_snap_id");
        link_snap_name=getIntent().getStringExtra("link_snap_name");
        union_name=getIntent().getStringExtra("union_name");
        link_id=getIntent().getStringExtra("link_id");
        mTvName.setText(link_snap_name);
        mTvDetailName.setText(union_name);
        getLiandongbaoHistoryDetail(link_snap_id);
    }

    /*public void setMyvisible(int data,int empty,int src)
    {
        mListView.setVisibility(data);
        mRelativeEmptyLayout.setVisibility(empty);
        mIvEmpty.setImageResource(src);

        SpannableString spannableString = new SpannableString("亲，您还未有任何作品联动包\n赶紧点击右上角“+”一个");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品联动包\n" +"赶紧点击右上角“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品联动包\n" +"赶紧点击右上角“").length(),("亲，您还未有任何作品联动包\n" +"赶紧点击右上角“+").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品联动包\n" +"赶紧点击右上角“+").length(),("亲，您还未有任何作品联动包\n" +"赶紧点击右上角“+”一个").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvEmpty.setText(spannableString);
    }*/


    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    @OnClick({R.id.toolbar_left_title, R.id.tv_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.tv_ensure:
                EventBus.getDefault().post(new Event.SetLiandongDingtouHistoryEvent());
                EventBus.getDefault().post(new Event.SetLiandongDingtouChooseEvent());
                EventBus.getDefault().post(new Event.SetLiandongDingtouEvent(link_snap_id,link_snap_name));
                finish();
                break;
        }
    }
}
