package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.VipBindAdapter;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.bean.VipListBean;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 去绑定vip码
* */
public class BindVipActivity extends BasicActivity {

    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    @BindView(R.id.listView)
    ListView mListView;

    private String terminal_id,vip_code;
    private VipBindAdapter mVipBindAdapter;
    private List<VipListBean.DataBean> beanList_unbind = new ArrayList<VipListBean.DataBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_list_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        initView();
        initListener();
        initDatas();
    }

    private void initView() {
        mVipBindAdapter=new VipBindAdapter(mContext);
        mVipBindAdapter.setCallback(new Callback2() {
            @Override
            public void callBack(String string1, String string2) {
                LogUtil.d("位置："+string1+"=====编码:"+string2);
                if(!TextUtils.isEmpty(string1)&&!TextUtils.isEmpty(string2)){
                    vip_code=string2;
                    for (int i=0;i<beanList_unbind.size();i++){
                        if(Integer.parseInt(string1)==i){
                            beanList_unbind.get(i).flag=1;
                        }else {
                            beanList_unbind.get(i).flag=0;
                        }
                    }
                    mVipBindAdapter.setmLists(beanList_unbind);
                }
            }
        });
        mListView.setAdapter(mVipBindAdapter);
    }

    private void initListener() {

    }

    private void initDatas() {
        terminal_id=getIntent().getStringExtra("terminal_id");
        getViplList();
    }

    private void getViplList() {
        RxRetrofitClient.getInstance(BindVipActivity.this).getViplList(new Observer<VipListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(BindVipActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VipListBean vipListBean) {
                LogUtil.d("vip列表记载成功");
                if (vipListBean.code == 1 && vipListBean.data != null && vipListBean.data.size() > 0) {
                    for (int i = 0; i < vipListBean.data.size(); i++) {
                        if (vipListBean.data.get(i).is_used) {
                        } else {
                            beanList_unbind.add(vipListBean.data.get(i));
                        }
                    }
                    mVipBindAdapter.setmLists(beanList_unbind);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.tv_cancel, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.toolbar_right:
                break;
        }
    }

    private void bindvip() {
        RxRetrofitClient.getInstance(BindVipActivity.this).bindVip(terminal_id,vip_code,new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(BindVipActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                LogUtil.d("绑定成功");
                if(vCodeBenan.getCode()==1){
                    setResult(RESULT_OK,new Intent().putExtra("bind",true));
                    finish();
                }else {
                    Utils.toast(BindVipActivity.this, vCodeBenan.getMessage());
                }
            }
        });
    }
}
