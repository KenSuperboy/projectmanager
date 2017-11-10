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
import com.gohoc.xiupuling.adapter.linkpackage.LiandongbaoAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.LiandongbaoBean;
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
* 联动包列表
* */
public class LiandongbaoListActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
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

    private LiandongbaoAdapter mLiandongbaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_list_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("多屏联动包");
        setStatusColor(R.color.colorPrimary);

        initView();
        EventBus.getDefault().register(this);
    }

    //默认情况进来
    /*SpannableString spannableString = new SpannableString("亲，您还未有任何作品\n快来“自己制作”吧");
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品\n" +"快来“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品\n" +"快来“").length(),("亲，您还未有任何作品\n" +"快来“自己制作").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品\n" +"快来“自己制作").length(),("亲，您还未有任何作品\n" +"快来“自己制作”吧").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    mTvEmpty.setText(spannableString);*/

    private void initView() {
        mLiandongbaoAdapter = new LiandongbaoAdapter(mContext);
        mListView.setAdapter(mLiandongbaoAdapter);
        mLiandongbaoAdapter.setCallback(new Callback2() {
            @Override
            public void callBack(String string1, String string2) {
                LogUtil.d(string1 + ":点击的位置：" + string2);
                if (!TextUtils.isEmpty(string1) && !TextUtils.isEmpty(string2)) {
                    deleteLink(string1, Integer.parseInt(string2));
                }
            }
        });
    }

    //删除多屏联动包
    private void deleteLink(final String link_id, final int postion) {
        Ok_Cancel_Dialog ok_cancel_dialog = new Ok_Cancel_Dialog(mContext, "确定删除此多屏联动包？");
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                deleteShop(link_id, postion);
            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }

    //删除多屏联动包
    private void deleteShop(final String link_id, final int postion) {
        RxRetrofitClient.getInstance(LiandongbaoListActivity.this).deleteLinkPackage(link_id, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("删除成功");
                if (emptyBean.code == 1) {

                    mLiandongbaoAdapter.remove(postion);

                    if(mLiandongbaoAdapter.getCount()==0){
                        setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_duojiliandong);
                    }
                }
            }
        });
    }

    private void initData() {
        getLiandongbaoList();
    }

    public void setMyvisible(int data, int empty, int src) {
        mScrollView.setVisibility(data);
        mRelativeEmptyLayout.setVisibility(empty);
        mIvEmpty.setImageResource(src);

        SpannableString spannableString = new SpannableString("亲，您还未有任何作品联动包\n赶紧点击右上角“+”一个");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品联动包\n" + "赶紧点击右上角“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品联动包\n" + "赶紧点击右上角“").length(), ("亲，您还未有任何作品联动包\n" + "赶紧点击右上角“+").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品联动包\n" + "赶紧点击右上角“+").length(), ("亲，您还未有任何作品联动包\n" + "赶紧点击右上角“+”一个").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvEmpty.setText(spannableString);
    }

    private void getLiandongbaoList() {
        RxRetrofitClient.getInstance(mContext).getLiandongbaolList(new Observer<LiandongbaoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_duojiliandong);
                Utils.toast(mContext, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(LiandongbaoBean liandongbaoBean) {
                LogUtil.d("记载成功");
                if (liandongbaoBean.code == 1 && liandongbaoBean.data.size() > 0) {
                    setMyvisible(View.VISIBLE, View.GONE, R.mipmap.icon_duojiliandong);
                    mLiandongbaoAdapter.setmLists(liandongbaoBean.data);
                } else {
                    setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_duojiliandong);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FinishLiandongListEvent(Event.FinishLiandongListEvent message) {
        LogUtil.d("联动包列表");
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.toolbar_left_title, R.id.iv_add,R.id.tv_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.iv_add:
                Intent intent = new Intent(LiandongbaoListActivity.this, LiandongbaoAddActivity.class);
                intent.putExtra("type", 0);//
                intent.putExtra("title", "新建联动包");//
                intent.putExtra("name", "");//
                startActivity(intent);
                break;
            case R.id.tv_empty:
                Intent intent_empty = new Intent(LiandongbaoListActivity.this, LiandongbaoAddActivity.class);
                intent_empty.putExtra("type", 0);//
                intent_empty.putExtra("title", "新建联动包");//
                intent_empty.putExtra("name", "");//
                startActivity(intent_empty);
                break;
        }
    }
}
