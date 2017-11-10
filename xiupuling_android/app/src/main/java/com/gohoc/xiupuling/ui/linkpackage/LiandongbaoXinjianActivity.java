package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.linkpackage.LiandongbaoXinjianAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.LinkPackageDetailBean;
import com.gohoc.xiupuling.bean.linkpackage.SetLiandongbaoGuanxi;
import com.gohoc.xiupuling.callback.Callback3;
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
* 新建联动关系
* */
public class LiandongbaoXinjianActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.tv_guanxi)
    TextView mTvGuanxi;
    @BindView(R.id.linear_guanxi_layout)
    LinearLayout mLinearGuanxiLayout;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @BindView(R.id.listView)
    MyListView mListView;
    @BindView(R.id.linear_btnline_layout)
    View mLinearBtnlineLayout;
    private String linkid, union_id;

    private int REQUEST = 1000;
    private LinkPackageDetailBean mLinkPackageDetailBean;
    private LiandongbaoXinjianAdapter mLiandongbaoXinjianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duopingliandong_xinjian_layout);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        mToolbarTitle.setText("新建联动关系");
        initView();
        initListener();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SetLiandongChooseTerminalEvent(Event.SetLiandongChooseTerminalEvent message) {
        LogUtil.d("联动定投事件收听===选择终端:" + message.terminal_id + message.position + "====:" + message.terminal_no);
        if (mLinkPackageDetailBean != null && mLinkPackageDetailBean.data.size() > 0) {
            mLinkPackageDetailBean.data.get(message.position).terminal_id = message.terminal_id;
            mLinkPackageDetailBean.data.get(message.position).terminal_number = message.terminal_no;
            mLiandongbaoXinjianAdapter.setmLists(mLinkPackageDetailBean.data);

            if(!TextUtils.isEmpty(mEtName.getText().toString())&&mLiandongbaoXinjianAdapter.getIschooseTerminal().size()==mLiandongbaoXinjianAdapter.getCount()){
                mTvSave.setBackgroundColor(getResources().getColor(R.color.blue));
            }else {
                mTvSave.setBackgroundColor(getResources().getColor(R.color.gray_unselect));
            }
        }
    }

    private void initView() {
        mToolbarLeftTitle.setText("取消");

        mLiandongbaoXinjianAdapter = new LiandongbaoXinjianAdapter(mContext);
        mLiandongbaoXinjianAdapter.setType(0);
        mListView.setAdapter(mLiandongbaoXinjianAdapter);
        mLiandongbaoXinjianAdapter.setCallback(new Callback3() {
            @Override
            public void callBack(String string1, String string2, String string3) {
                LogUtil.d("string1:" + string1 + "===string2:" + string2 + "====string3:" + string3);
                postLeader(string1, string2,string3);
            }
        });
    }

    private void initListener() {
        EventBus.getDefault().register(this);
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.d("点击item条目");
            }
        });*/

        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0&&mLinearBtnlineLayout.getVisibility()==View.VISIBLE&&mLiandongbaoXinjianAdapter!=null&&mLiandongbaoXinjianAdapter.getIschooseTerminal().size()==mLiandongbaoXinjianAdapter.getCount()){
                    mTvSave.setBackgroundColor(getResources().getColor(R.color.blue));
                }else {
                    mTvSave.setBackgroundColor(getResources().getColor(R.color.gray_unselect));
                }

            }
        });
    }

    private void postLeader(final String link_id, String detail_id, final String position) {
        RxRetrofitClient.getInstance(mContext).leaderLinkPackage(link_id, detail_id, new Observer<EmptyBean>() {
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
                if (emptyBean.code == 1) {
                    if(!TextUtils.isEmpty(position)){

                        for (int i=0;i<mLinkPackageDetailBean.data.size();i++){
                            if(i==Integer.parseInt(position)){
                                mLinkPackageDetailBean.data.get(i).is_leader=true;
                            }else {
                                mLinkPackageDetailBean.data.get(i).is_leader=false;
                            }
                        }
                        mLiandongbaoXinjianAdapter.setmLists(mLinkPackageDetailBean.data);
                    }
                    //getLiandongbaoDetail();
                }
            }
        });
    }

    //保存联动投放关系
    private void saveLiandongbaoGuanxi(String link_id, String link_snap_name, String union_id, String link_detail_ids, String terminal_ids, String leader_link_detail_id) {
        RxRetrofitClient.getInstance(mContext).saveLiandongbaoGuanxi(link_id, link_snap_name, union_id, link_detail_ids, terminal_ids, leader_link_detail_id, new Observer<SetLiandongbaoGuanxi>() {
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
            public void onNext(SetLiandongbaoGuanxi setLiandongbaoGuanxi) {
                LogUtil.d("保存成功成功");
                if (setLiandongbaoGuanxi.code == 1) {
                    EventBus.getDefault().post(new Event.SetLiandongDingtouChooseEvent());
                    EventBus.getDefault().post(new Event.SetLiandongDingtouEvent(setLiandongbaoGuanxi.data.link_snap_id, setLiandongbaoGuanxi.data.link_snap_name));
                    finish();
                }else {
                    Toast.makeText(mContext,setLiandongbaoGuanxi.message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private String getDetailId() {
        String detailId = "";
        for (int i = 0; i < mLinkPackageDetailBean.data.size(); i++) {
            detailId += mLinkPackageDetailBean.data.get(i).detail_id + ",";
        }
        return detailId;
    }

    private String getTerminalId() {
        String string = "";
        for (int i = 0; i < mLinkPackageDetailBean.data.size(); i++) {
            string += mLinkPackageDetailBean.data.get(i).terminal_id + ",";
        }
        return string;
    }

    private String getLeaderLinkDetailId() {
        String string = "";
        for (int i = 0; i < mLinkPackageDetailBean.data.size(); i++) {
            if (mLinkPackageDetailBean.data.get(i).is_leader) {
                string = mLinkPackageDetailBean.data.get(i).detail_id;
            }
        }
        return string;
    }

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
                    mLinkPackageDetailBean = linkPackageDetailBean;
                    mLiandongbaoXinjianAdapter.setmLists(linkPackageDetailBean.data);
                    mLinearBtnlineLayout.setVisibility(View.VISIBLE);
                    if(!TextUtils.isEmpty(mEtName.getText().toString())&&mLiandongbaoXinjianAdapter.getIschooseTerminal().size()==mLiandongbaoXinjianAdapter.getCount()){
                        mTvSave.setBackgroundColor(getResources().getColor(R.color.blue));
                    }else {
                        mTvSave.setBackgroundColor(getResources().getColor(R.color.gray_unselect));
                    }
                } else {
                    mTvSave.setBackgroundColor(getResources().getColor(R.color.gray_unselect));
                }
            }
        });
    }

    private void initData() {
        linkid = getIntent().getStringExtra("linkid");
    }

    @OnClick({R.id.toolbar_left_title, R.id.linear_guanxi_layout, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.linear_guanxi_layout:
                Intent intent = new Intent(LiandongbaoXinjianActivity.this, LiandongbaoGroupListActivity.class);
                if (!mTvGuanxi.getText().toString().equals("请选择联动的群组")) {
                    intent.putExtra("name", mTvGuanxi.getText().toString());
                } else {
                    intent.putExtra("name", "");
                }
                startActivityForResult(intent, REQUEST);
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(mEtName.getText().toString())) {
                    Toast.makeText(mContext, "请输入名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getDetailId()) || TextUtils.isEmpty(getTerminalId()) || TextUtils.isEmpty(getLeaderLinkDetailId())) {
                    Toast.makeText(mContext, "暂无可用的投放关系", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveLiandongbaoGuanxi(linkid, mEtName.getText().toString(), union_id, getDetailId(), getTerminalId(), getLeaderLinkDetailId());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST) {
                LogUtil.d("TAG:" + data.getStringExtra("name"));
                mTvGuanxi.setText(data.getStringExtra("name"));
                mTvGuanxi.setTextColor(getResources().getColor(R.color.gallery_text_gray));
                union_id = data.getStringExtra("id");
                if (mLiandongbaoXinjianAdapter != null) {
                    mLiandongbaoXinjianAdapter.setUnion_id(data.getStringExtra("name"), union_id);
                }
                getLiandongbaoDetail();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
