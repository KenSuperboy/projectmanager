package com.gohoc.xiupuling.ui.useraudit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.auditadapter.AuditWorkAdapter;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.bean.audit.AuditWorkListBean;
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
* 未审核订单作品列表
* */
public class AuditWorksListActivity extends BasicActivity {


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
    private String user_id;

    private AuditWorkAdapter mAuditWorkAdapter;
    private AuditWorkListBean mAuditWorkListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_work_list_layout);
        ButterKnife.bind(this);
        initView();
        mToolbarTitle.setText("未审作品");
        user_id = getIntent().getStringExtra("user_id");
        initData();
        EventBus.getDefault().register(this);
    }

    public void setMyvisible(int data, int empty, int src, String string) {
        mListView.setVisibility(data);
        mRelativeEmptyLayout.setVisibility(empty);
        mIvEmpty.setImageResource(R.mipmap.icon_no_order_bg);
        mTvEmpty.setText(string);//当前无待审核作品
    }

    private void getAuditWorksList(String user_id) {
        RxRetrofitClient.getInstance(this).getAuditWorksList(user_id, new Observer<AuditWorkListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AuditWorksListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(AuditWorkListBean auditWorkListBean) {
                if (auditWorkListBean!=null&&auditWorkListBean.code == 1&& auditWorkListBean.list!=null && auditWorkListBean.list.size() > 0) {
                    mAuditWorkListBean = auditWorkListBean;
                    setMyvisible(View.VISIBLE,View.GONE,R.mipmap.icon_usercenter_morentouxiang,"当前无待审核作品");
                } else {
                    setMyvisible(View.GONE,View.VISIBLE,R.mipmap.icon_usercenter_morentouxiang,"当前无待审核作品");
                }
            }
        });
    }

    private void initView() {
        mToolbarTitle.setText("未审作品");
        setStatusColor(R.color.colorPrimary);

        mAuditWorkAdapter = new AuditWorkAdapter(mContext);
        mListView.setAdapter(mAuditWorkAdapter);
        mAuditWorkAdapter.setCallback(new Callback2() {
            @Override
            public void callBack(String string1, String string2) {
                LogUtil.d("驳回："+string1 + "===通过：" + string2);
                if(!TextUtils.isEmpty(string1)){
                    //驳回
                    shenheWorkAudit(user_id,string1,"reject");
                }else if(!TextUtils.isEmpty(string2)){
                    //通过
                    shenheWorkAudit(user_id,string1,"pass");
                }
            }
        });
    }

    private void shenheWorkAudit(String user_id, String work_id, final String dicision) {
        RxRetrofitClient.getInstance(AuditWorksListActivity.this).shenheWorkAuditUser(user_id,work_id,dicision, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(AuditWorksListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    if(dicision.equals("pass")){
                        Intent intent=new Intent(AuditWorksListActivity.this,AuditResultActivity.class);
                        intent.putExtra("type","1");
                        startActivity(intent);
                        finish();
                    }else if(dicision.equals("reject")){
                        Intent intent=new Intent(AuditWorksListActivity.this,AuditResultActivity.class);
                        intent.putExtra("type","2");
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Utils.toast(AuditWorksListActivity.this, vCodeBenan.getMessage());
                }
            }
        });
    }

    private void initData() {
        getAuditWorksList(user_id);
    }

    private void unAdudit() {
        BGAAlertController alertController = new BGAAlertController(AuditWorksListActivity.this, "", "解绑后，你将失去对此账号绑定终端的内容审核权。 ", BGAAlertController.AlertControllerStyle.ActionSheet);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.addAction(new BGAAlertAction("解绑账户" + "15817234445", BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                LogUtil.d("点击事件");
            }
        }));
        alertController.setCancelable(true);
        alertController.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshAuditUserListEvent(Event.RefreshAuditUserListEvent message) {
        LogUtil.d("监管账户列表");
        initData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.toolbar_left_title, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.iv_add:
                break;
        }
    }
}
