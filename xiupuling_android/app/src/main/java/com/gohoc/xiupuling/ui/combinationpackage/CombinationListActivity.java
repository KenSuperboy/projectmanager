package com.gohoc.xiupuling.ui.combinationpackage;

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
import com.gohoc.xiupuling.adapter.combinationadapter.CombinationAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListBean;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.dialog.Ok_Cancel_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.view.MyListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 作品组合包包列表
* */
public class CombinationListActivity extends BasicActivity {


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

    private CombinationAdapter mCombinationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_list_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("作品组合包");
        setStatusColor(R.color.colorPrimary);

        initView();

    }

    //默认情况进来
    /*SpannableString spannableString = new SpannableString("亲，您还未有任何作品\n快来“自己制作”吧");
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品\n" +"快来“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品\n" +"快来“").length(),("亲，您还未有任何作品\n" +"快来“自己制作").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品\n" +"快来“自己制作").length(),("亲，您还未有任何作品\n" +"快来“自己制作”吧").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    mTvEmpty.setText(spannableString);*/

    private void initView() {
        mCombinationAdapter = new CombinationAdapter(mContext);
        mListView.setAdapter(mCombinationAdapter);
        mCombinationAdapter.setCallback(new Callback2() {
            @Override
            public void callBack(String string1, String string2) {
                LogUtil.d(string1 + ":点击的位置：" + string2);
                if (!TextUtils.isEmpty(string1) && !TextUtils.isEmpty(string2)) {
                    deleteCombination(string1, Integer.parseInt(string2));
                }
            }
        });
    }

    //删除多屏组合包
    private void deleteCombination(final String package_id, final int postion) {
        Ok_Cancel_Dialog ok_cancel_dialog = new Ok_Cancel_Dialog(mContext, "确定删除此作品组合包？");
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                deleteCombinationShop(package_id, postion);
            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }

    //删除多屏组合包
    private void deleteCombinationShop(final String package_id, final int postion) {
        RxRetrofitClient.getInstance(CombinationListActivity.this).deleteCombinationPackage(package_id, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CombinationListActivity.this, "请检查网络是否正常");
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
                    mCombinationAdapter.remove(postion);
                }
                if(mCombinationAdapter.getCount()==0){
                    setMyvisible(View.GONE, View.VISIBLE, R.mipmap.img_zuopinzuhebao);
                }
            }
        });
    }

    private void initData() {
        getCombinationPackageList();
    }

    public void setMyvisible(int data, int empty, int src) {
        mScrollView.setVisibility(data);
        mRelativeEmptyLayout.setVisibility(empty);
        mIvEmpty.setImageResource(src);

        SpannableString spannableString = new SpannableString("亲，您还未有任何作品组合包\n赶紧点击右上角“+”一个");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品组合包\n" + "赶紧点击右上角“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品组合包\n" + "赶紧点击右上角“").length(), ("亲，您还未有任何作品组合包\n" + "赶紧点击右上角“+").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品组合包\n" + "赶紧点击右上角“+").length(), ("亲，您还未有任何作品组合包\n" + "赶紧点击右上角“+”一个").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvEmpty.setText(spannableString);
    }

    private void getCombinationPackageList() {
        RxRetrofitClient.getInstance(mContext).getCombinationPackageList(new Observer<CombinationListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                setMyvisible(View.GONE, View.VISIBLE, R.mipmap.img_zuopinzuhebao);
                Utils.toast(mContext, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(CombinationListBean combinationListBean) {
                LogUtil.d("记载成功");
                if (combinationListBean.code == 1 && combinationListBean.data.size() > 0) {
                    setMyvisible(View.VISIBLE, View.GONE, R.mipmap.img_zuopinzuhebao);
                    mCombinationAdapter.setmLists(combinationListBean.data);
                } else {
                    setMyvisible(View.GONE, View.VISIBLE, R.mipmap.img_zuopinzuhebao);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @OnClick({R.id.toolbar_left_title, R.id.iv_add,R.id.tv_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.iv_add:
                Intent intent = new Intent(CombinationListActivity.this, CombinationPackageAddActivity.class);
                intent.putExtra("type", 0);//
                intent.putExtra("title", "新建组合包");//
                intent.putExtra("name", "");//
                startActivity(intent);
                break;
            case R.id.tv_empty:
                Intent intent_empty = new Intent(CombinationListActivity.this, CombinationPackageAddActivity.class);
                intent_empty.putExtra("type", 0);//
                intent_empty.putExtra("title", "新建组合包");//
                intent_empty.putExtra("name", "");//
                startActivity(intent_empty);
                break;
        }
    }
}
