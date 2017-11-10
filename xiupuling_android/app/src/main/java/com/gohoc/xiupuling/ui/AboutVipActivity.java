package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.VipAdapter;
import com.gohoc.xiupuling.bean.VipListBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.useraudit.VipDetailActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 权限介绍
* */
public class AboutVipActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_video_number)
    TextView mTvVideoNumber;
    @BindView(R.id.linear_video_layout)
    LinearLayout mLinearVideoLayout;
    @BindView(R.id.tv_video_tips)
    TextView mTvVideoTips;
    @BindView(R.id.tv_vip_tips)
    TextView mTvVipTips;
    @BindView(R.id.tv_add_vip)
    TextView mTvAddVip;
    @BindView(R.id.listView_bind)
    MyListView mListViewBind;
    @BindView(R.id.listView_unbind)
    MyListView mListViewUnbind;
    @BindView(R.id.linear_vip_layout)
    LinearLayout mLinearVipLayout;

    private String type = "";//0:私人视频库    1：终端vip码
    private VipAdapter mVipAdapter_unbind;
    private VipAdapter mVipAdapter_bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vip_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        initView();
        initListener();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        if (type.equals("0")) {
            //私人视频库
            mToolbarTitle.setText("私人视频库");
            mLinearVideoLayout.setVisibility(View.VISIBLE);
            mTvVideoTips.setVisibility(View.VISIBLE);
            mTvVipTips.setVisibility(View.GONE);
            mLinearVipLayout.setVisibility(View.GONE);
            mIvLogo.setImageResource(R.mipmap.icon_sc_sirenpindao);
            mTvVideoNumber.setText(getIntent().getStringExtra("count"));
        } else if (type.equals("1")) {
            //终端vip码
            mToolbarTitle.setText("终端VIP码");
            mLinearVideoLayout.setVisibility(View.GONE);
            mTvVideoTips.setVisibility(View.GONE);
            mTvVipTips.setVisibility(View.VISIBLE);
            mLinearVipLayout.setVisibility(View.VISIBLE);
            mIvLogo.setImageResource(R.mipmap.zhongduan);

            mVipAdapter_bind=new VipAdapter(mContext);
            mListViewBind.setAdapter(mVipAdapter_bind);

            mVipAdapter_unbind=new VipAdapter(mContext);
            mListViewUnbind.setAdapter(mVipAdapter_unbind);
        }
    }


    private void initListener() {
        //不用绑定
        mListViewBind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VipListBean.DataBean dataBean= (VipListBean.DataBean) parent.getAdapter().getItem(position);
                Intent intent_bind=new Intent(AboutVipActivity.this, VipDetailActivity.class);
                intent_bind.putExtra("type","1");
                intent_bind.putExtra("count",dataBean.not_used_cnt);
                intent_bind.putExtra("code",dataBean.vip_code);
                intent_bind.putExtra("name",dataBean.shop_name);
                intent_bind.putExtra("number",dataBean.terminal_no);
                startActivity(intent_bind);
            }
        });

        //去绑定
        mListViewUnbind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VipListBean.DataBean dataBean= (VipListBean.DataBean) parent.getAdapter().getItem(position);
                Intent intent_bind=new Intent(AboutVipActivity.this, VipDetailActivity.class);
                intent_bind.putExtra("type","0");
                intent_bind.putExtra("count",dataBean.not_used_cnt);
                intent_bind.putExtra("code",dataBean.vip_code);
                intent_bind.putExtra("name","");
                intent_bind.putExtra("number","");
                startActivity(intent_bind);
            }
        });
    }

    private void initDatas() {
        getViplList();
    }

    private void getViplList() {
        RxRetrofitClient.getInstance(AboutVipActivity.this).getViplList(new Observer<VipListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AboutVipActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VipListBean vipListBean) {
                LogUtil.d("vip列表记载成功");
                if(vipListBean.code==1&&vipListBean.data!=null&&vipListBean.data.size()>0){
                    List<VipListBean.DataBean> beanList_bind=new ArrayList<VipListBean.DataBean>();
                    List<VipListBean.DataBean> beanList_unbind=new ArrayList<VipListBean.DataBean>();

                    for (int i=0;i<vipListBean.data.size();i++){
                        if(vipListBean.data.get(i).is_used){
                            beanList_bind.add(vipListBean.data.get(i));
                        }else {
                            beanList_unbind.add(vipListBean.data.get(i));
                        }
                    }

                    mVipAdapter_unbind.setmLists(beanList_unbind);
                    mVipAdapter_bind.setmLists(beanList_bind);
                }
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.tv_add_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.tv_add_vip:
                startActivity(new Intent(AboutVipActivity.this,AddVipCodeActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(type.equals("1")){
            initDatas();
        }
    }
}
