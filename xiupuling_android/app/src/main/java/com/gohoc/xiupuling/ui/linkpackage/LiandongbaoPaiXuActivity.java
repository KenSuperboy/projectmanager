package com.gohoc.xiupuling.ui.linkpackage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.linkpackage.LiandongbaoPaixuAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.LinkPackageDetailBean;
import com.gohoc.xiupuling.callback.OnItemCallbackHelper;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 联动包排序
* */
public class LiandongbaoPaiXuActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_finish)
    TextView mIvFinish;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String linkid;
    private LiandongbaoPaixuAdapter mLiandongbaoPaixuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_paixu_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("手动排序");
        setStatusColor(R.color.colorPrimary);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        linkid = getIntent().getStringExtra("linkid");
        getLiandongbaoDetail();
    }

    //linkPackageSort
    private void getLiandongbaoDetail() {
        RxRetrofitClient.getInstance(mContext).getLiandongbaoDetail(linkid, new Observer<LinkPackageDetailBean>() {
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
            public void onNext(LinkPackageDetailBean linkPackageDetailBean) {
                LogUtil.d("记载成功");
                if (linkPackageDetailBean.code == 1 && linkPackageDetailBean.data.size() > 0) {

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(LiandongbaoPaiXuActivity.this, LinearLayoutManager.VERTICAL, false));
                    mLiandongbaoPaixuAdapter = new LiandongbaoPaixuAdapter(LiandongbaoPaiXuActivity.this,linkPackageDetailBean.data);
                    mRecyclerView.setAdapter(mLiandongbaoPaixuAdapter);
                    ItemTouchHelper.Callback callback =new OnItemCallbackHelper(mLiandongbaoPaixuAdapter,null);
                    /**
                     * 实例化ItemTouchHelper对象,然后添加到RecyclerView
                     */
                    ItemTouchHelper helper = new ItemTouchHelper(callback);
                    helper.attachToRecyclerView(mRecyclerView);
                } else {
                }
            }
        });
    }

    //联动包排序
    private void liandongbaoSort(String link_id, String work_ids, String rq_ids) {
        RxRetrofitClient.getInstance(mContext).linkPackageSort_(link_id, work_ids, rq_ids, new Observer<EmptyBean>() {
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
                LogUtil.d("排序成功");
                Toast.makeText(mContext,"排序成功",Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.iv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.iv_finish:
                LogUtil.d("保存排序状况");
                mDataBeanList=mLiandongbaoPaixuAdapter.getDataList();
                liandongbaoSort(linkid,getWorkId(),getRqId());
                break;
        }
    }

    private List<LinkPackageDetailBean.DataBean> mDataBeanList;

    private String getWorkId()
    {
        String workId="";
        for (int i=0;i<mLiandongbaoPaixuAdapter.getDataList().size();i++){
            workId+=mDataBeanList.get(i).work_id+",";
        }
        LogUtil.d("workId===:"+workId);
        return workId;
    }

    private String getRqId()
    {
        String rQid="";
        for (int i=0;i<mLiandongbaoPaixuAdapter.getDataList().size();i++){
            rQid+=mDataBeanList.get(i).rq_id+",";
        }
        LogUtil.d("rQid===:"+rQid);
        return rQid;
    }

}
