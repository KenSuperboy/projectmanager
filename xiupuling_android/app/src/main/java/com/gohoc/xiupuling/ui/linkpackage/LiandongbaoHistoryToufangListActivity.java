package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.linkpackage.LiandongbaoHistoryAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.linkpackage.LinkPackageHistoryListBean;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.dialog.Ok_Cancel_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 联动包历史投放记录
* */
public class LiandongbaoHistoryToufangListActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.listView)
    MyListView mListView;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.relative_empty_layout)
    RelativeLayout mRelativeEmptyLayout;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    private LiandongbaoHistoryAdapter mHistoryOrderListAdater;
    private String linkid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_history_list_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("历史投放记录");
        setStatusColor(R.color.colorPrimary);

        initView();
        initData();
        EventBus.getDefault().register(this);

    }

    private void initEmptyData() {
        SpannableString spannableString = new SpannableString("亲，你还未有任何联动包投放对应关系\n赶紧“" + "+" + "”一个");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，你还未有任何联动包投放对应关系\n赶紧“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，你还未有任何联动包投放对应关系\n赶紧“").length(), ("亲，你还未有任何联动包投放对应关系\n赶紧“" + "+").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，你还未有任何联动包投放对应关系\n赶紧“" + "+").length(), ("亲，你还未有任何联动包投放对应关系\n赶紧“" + "+" + "”一个").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mIvEmpty.setVisibility(View.GONE);
        mTvEmpty.setText(spannableString);
    }

    private void initView() {
        mHistoryOrderListAdater = new LiandongbaoHistoryAdapter(mContext);
        mListView.setAdapter(mHistoryOrderListAdater);
        mHistoryOrderListAdater.setCallback(new Callback2() {
            @Override
            public void callBack(String string1, String string2) {
                LogUtil.d(string1 + ":点击的位置：" + string2);
                if (!TextUtils.isEmpty(string1) && !TextUtils.isEmpty(string2)) {
                    deleteLink(string1, Integer.parseInt(string2));
                }
            }
        });
    }

    //确定删除此历史投放记录
    private void deleteLink(final String link_snap_id, final int postion) {
        Ok_Cancel_Dialog ok_cancel_dialog = new Ok_Cancel_Dialog(mContext, "确定删除此历史投放记录？");
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                deleteLiandongbaoToufangGuanxi(link_snap_id, postion);
            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }

    private void deleteLiandongbaoToufangGuanxi(String link_snap_id, final int postion) {
        RxRetrofitClient.getInstance(LiandongbaoHistoryToufangListActivity.this).deleteLinkToufangGuanxi(link_snap_id, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoHistoryToufangListActivity.this, "请检查网络是否正常");
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
                    mHistoryOrderListAdater.remove(postion);
                    if(mHistoryOrderListAdater.getCount()==0){
                        mScrollView.setVisibility(View.GONE);
                        mRelativeEmptyLayout.setVisibility(View.VISIBLE);
                        initEmptyData();
                    }
                }
            }
        });
    }

    private void getLiandongbaoHistory(String link_id) {
        RxRetrofitClient.getInstance(LiandongbaoHistoryToufangListActivity.this).getLiandongbaoHistory(link_id, new Observer<LinkPackageHistoryListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoHistoryToufangListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(LinkPackageHistoryListBean linkPackageHistoryListBean) {
                LogUtil.d("获取成功");
                if (linkPackageHistoryListBean.code == 1 && linkPackageHistoryListBean.data.size() > 0) {
                    mScrollView.setVisibility(View.VISIBLE);
                    mRelativeEmptyLayout.setVisibility(View.GONE);
                    mHistoryOrderListAdater.setmLists(linkPackageHistoryListBean.data);
                } else {
                    mScrollView.setVisibility(View.GONE);
                    mRelativeEmptyLayout.setVisibility(View.VISIBLE);
                    initEmptyData();
                }
            }
        });
    }

    private void initData() {
        linkid = getIntent().getStringExtra("linkid");
        getLiandongbaoHistory(linkid);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SetLiandongDingtouHistoryEvent(Event.SetLiandongDingtouHistoryEvent message) {
        LogUtil.d("历史记录");
        finish();
    }

    @OnClick({R.id.toolbar_left_title, R.id.tv_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                break;
            case R.id.tv_empty:
                Intent intent=new Intent(LiandongbaoHistoryToufangListActivity.this,LiandongbaoXinjianActivity.class);
                intent.putExtra("linkid",linkid);
                startActivity(intent);
                break;
        }
    }
}
