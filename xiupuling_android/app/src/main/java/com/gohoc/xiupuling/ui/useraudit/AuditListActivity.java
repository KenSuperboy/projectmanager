package com.gohoc.xiupuling.ui.useraudit;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.auditadapter.AuditAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.audit.AuditListBean;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;

/*
* 账户监管审查列表
* */
public class AuditListActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.relative_empty_layout)
    RelativeLayout mRelativeEmptyLayout;
    private AuditAdapter mAuditAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_list_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("账户监管");
        setStatusColor(R.color.colorPrimary);
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mAuditAdapter = new AuditAdapter(mContext);
        mListView.setAdapter(mAuditAdapter);
        mAuditAdapter.setCallback(new Callback2() {
            @Override
            public void callBack(String string1, String string2) {
                LogUtil.d("位置："+string1 + ":手机号：" + string2);
                if (!TextUtils.isEmpty(string1) && !TextUtils.isEmpty(string2)) {
                    unAdudit(Integer.parseInt(string1),string2);
                }
            }
        });
    }

    private void initData() {
        getAuditList();
    }

    public void setMyvisible(int data, int empty, int src) {
        mListView.setVisibility(data);
        mRelativeEmptyLayout.setVisibility(empty);
        mIvEmpty.setImageResource(src);

        SpannableString spannableString = new SpannableString("您还未添加任何被监管的账户\n试一试点击右上角“+”");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gallery_text_gray)), 0, ("您还未添加任何被监管的账户\n" + "试一试点击").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("您还未添加任何被监管的账户\n" + "试一试点击").length(), ("您还未添加任何被监管的账户\n试一试点击右上角“+”").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvEmpty.setText(spannableString);
    }

    private void getAuditList() {
        RxRetrofitClient.getInstance(mContext).getAuditList(new Observer<AuditListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_usercenter_morentouxiang);
                Utils.toast(mContext, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(AuditListBean auditListBean) {
                LogUtil.d("记载成功");
                if (auditListBean.code == 1&&auditListBean.data!=null&&auditListBean.data.size()>0) {
                    setMyvisible(View.VISIBLE, View.GONE, R.mipmap.icon_usercenter_morentouxiang);
                    mAuditAdapter.setmLists(auditListBean.data);
                } else {
                    setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_usercenter_morentouxiang);
                }
            }
        });
    }


    //
    private void unAdudit(final int position, final String mobile)
    {
        BGAAlertController alertController = new BGAAlertController(AuditListActivity.this, "","解绑后，你将失去对此账号绑定终端的\n内容审核权。 " , BGAAlertController.AlertControllerStyle.ActionSheet);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.addAction(new BGAAlertAction("解绑账户"+"15817234445", BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                LogUtil.d("点击事件");
                deleteAuditUser(position,mobile);
            }
        }));
        alertController.setCancelable(true);
        alertController.show();
    }

    //删除用户
    private void deleteAuditUser(final int position, String mobile) {
        RxRetrofitClient.getInstance(mContext).deleteAuditUser(mobile,new Observer<EmptyBean>() {
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
                LogUtil.d("记载成功");
                if (emptyBean.code == 1) {
                    mAuditAdapter.remove(position);
                    if(mAuditAdapter.getCount()==0){
                        setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_usercenter_morentouxiang);
                    }else {
                        setMyvisible(View.VISIBLE, View.GONE, R.mipmap.icon_usercenter_morentouxiang);
                    }
                    //mLiandongbaoAdapter.setmLists(liandongbaoBean.data);
                } else {
                    setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_usercenter_morentouxiang);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshAuditUserListEvent(Event.RefreshAuditUserListEvent message) {
        LogUtil.d("监管账户列表");
        getAuditList();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.toolbar_left_title, R.id.iv_add, R.id.tv_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.iv_add:
                addAuditUser();
                break;
            case R.id.tv_empty:
                addAuditUser();
                break;
        }
    }

    private void addAuditUser()
    {
        startActivity(new Intent(AuditListActivity.this, AddauditUserActivity1.class));
    }
}
