package com.gohoc.xiupuling.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.PushOrderConfimResultBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.constant.MyApp;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.group.GroupDetailsActivity;
import com.gohoc.xiupuling.ui.push.PushSelectMenuActivity;
import com.gohoc.xiupuling.ui.requirement.MyWorksActivity;
import com.gohoc.xiupuling.ui.requirement.ReqDtActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class OrderCopyrightownResultActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.order_no_tv)
    TextView orderNoTv;
    @BindView(R.id.push_lv)
    LinearLayout pushLv;
    @BindView(R.id.pr_lv)
    LinearLayout prLv;
    private PushNormlBean pushNormlBean;
    private PushOrderConfimResultBean resultBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_copyrightown_result);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("支付成功");
        pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        resultBean = (PushOrderConfimResultBean) getIntent().getExtras().get("resultBean");
        initDates();
        MyApp.getActivityList().add(this);
    }

    private void initDates() {
        nameTv.setText(pushNormlBean.getName());
        timeTv.setText(resultBean.getData().getCreate_time() + "购买成功");
        orderNoTv.setText("订单号：" + resultBean.getData().getRefno());
    }

    @OnClick({R.id.toolbar_left_title, R.id.push_lv, R.id.pr_lv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                closedAll();
                break;
            case R.id.push_lv:
                startActivity(new Intent(OrderCopyrightownResultActivity.this, PushSelectMenuActivity.class).putExtra("pushNormlBean", pushNormlBean));
                break;
            case R.id.pr_lv:
                createInfoDistribute(pushNormlBean.getRq_id(), pushNormlBean.getWork_id());
                break;
        }
    }

    private void closedAll() {
        for (int a = 0; a < MyApp.getActivityList().size(); a++)
            MyApp.getActivityList().get(a).finish();

    }

    private void createInfoDistribute(String rq_id, String work_id) {
        RxRetrofitClient.getInstance(this).createInfoDistribute(rq_id, work_id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderCopyrightownResultActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(OrderCopyrightownResultActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshPushListEvent(null));
                    Intent intent = new Intent(OrderCopyrightownResultActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    EventBus.getDefault().post(new Event.MainIndex(4));
                    finish();
                }

            }
        });
    }
}
