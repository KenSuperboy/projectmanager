package com.gohoc.xiupuling.ui.tabfragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.PushAdvertActivity;
import com.gohoc.xiupuling.ui.ReceiveAdvertActivity;
import com.gohoc.xiupuling.ui.account.MemberAuthorizationActivity;
import com.gohoc.xiupuling.ui.account.MemberShopActivity;
import com.gohoc.xiupuling.ui.order.OrderHistoryActivity;
import com.gohoc.xiupuling.ui.push.PushHistoryActivity;
import com.gohoc.xiupuling.ui.requirement.CreateReqActivity;
import com.gohoc.xiupuling.ui.useraudit.AuditListActivity;
import com.gohoc.xiupuling.ui.useraudit.AuditStatusActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.SpanUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 投放管理
* */
public class PushManagerFragment extends BaseFragment {


    @BindView(R.id.linear_chaxun_layout)
    LinearLayout mLinearChaxunLayout;
    @BindView(R.id.linear_dangqiantoufang_layout)
    LinearLayout mLinearDangqiantoufangLayout;
    @BindView(R.id.linear_lishitoufang_layout)
    LinearLayout mLinearLishitoufangLayout;
    @BindView(R.id.linear_dangqianchengjie_layout)
    LinearLayout mLinearDangqianchengjieLayout;
    @BindView(R.id.linear_lishichengjie_layout)
    LinearLayout mLinearLishichengjieLayout;
    @BindView(R.id.linear_bofangshenhe_layout)
    LinearLayout linear_bofangshenhe_layout;

    private View viewContainer;
    private Window window;
    private AlertDialog dialog;

    public PushManagerFragment() {

    }

    private UserBaseBean.DataBean userBaseBeans;

    public static PushManagerFragment newInstance() {
        PushManagerFragment fragment = new PushManagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBaseBeans = Credential.getInstance().getCurUser(getActivity()).getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_push_manager, container, false);
        ButterKnife.bind(this, viewContainer);
        return viewContainer;
    }


    public void updateUserInfo(Context context) {
        RxRetrofitClient.getInstance(context).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    Credential.getInstance().setCurUser(userBaseBean);
                    userBaseBeans = Credential.getInstance().getCurUser(getActivity()).getData();
                    EventBus.getDefault().post(new Event.UserEvent());
                    if (userBaseBean.getData().getWork_left_cnt() > 0)
                        startActivity(new Intent(getActivity(), CreateReqActivity.class));
                    else
                        showShopPop();
                }
            }
        });
    }

    private void showShopPop() {
        dialog = new AlertDialog.Builder(getActivity(),
                R.style.dialogTips).create();
        dialog.show();
        window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(false);
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容

        window.setContentView(R.layout.dialog_works_limt_alert);

        window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), MemberShopActivity.class));

            }
        });
        window.findViewById(R.id.check_auth_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), MemberAuthorizationActivity.class));
            }
        });
        window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        SpanUtils su = new SpanUtils();
        su.append("您当前作品剩余数为" + userBaseBeans.getWork_left_cnt() + ",如需获得更多的作品拥有数,请前往会员商城或参与")
                .append("官方活动").setForegroundColor(Color.parseColor("#38b2fd"))
                .append("扩充。");
        TextView tips_title_tv = (TextView) window.findViewById(R.id.tips_title_tv);
        tips_title_tv.setText(su.create());
        TextView shop_count_tv = (TextView) window.findViewById(R.id.shop_count_tv);
        su = new SpanUtils();
        su.append("作品剩余数 ").append(userBaseBeans.getWork_left_cnt() + "").setForegroundColor(Color.parseColor("#38b2fd"));
        shop_count_tv.setText(su.create());
        su = new SpanUtils();
        TextView check_auth_tv = (TextView) window.findViewById(R.id.check_auth_tv);
        check_auth_tv.setText(su.append(" 查询其它").setForegroundColor(Color.parseColor("#929292")).append("会员权限").setForegroundColor(Color.parseColor("#38b2fd")).create());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.linear_chaxun_layout, R.id.linear_dangqiantoufang_layout, R.id.linear_lishitoufang_layout, R.id.linear_dangqianchengjie_layout, R.id.linear_lishichengjie_layout,R.id.linear_bofangshenhe_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_chaxun_layout:
                startActivity(new Intent(getActivity(), ReceiveAdvertActivity.class));
                break;
            case R.id.linear_dangqiantoufang_layout:
                startActivity(new Intent(getActivity(), PushAdvertActivity.class));
                break;
            case R.id.linear_lishitoufang_layout://历史投放广告
                Intent intent=new Intent(getActivity(), PushHistoryActivity.class);
                intent.putExtra("title","历史投放广告");
                startActivity(intent);
                break;
            case R.id.linear_dangqianchengjie_layout:
                Intent intent_current=new Intent(getActivity(), OrderHistoryActivity.class);
                intent_current.putExtra("type",1);//当前接单
                startActivity(intent_current);
                break;
            case R.id.linear_lishichengjie_layout:
                Intent intent_history=new Intent(getActivity(), OrderHistoryActivity.class);
                intent_history.putExtra("type",0);//历史接单
                startActivity(intent_history);
                break;
            case R.id.linear_bofangshenhe_layout:
                ACache aCache=ACache.get(getActivity());
                UserBaseBean userBean = (UserBaseBean) aCache.getAsObject("userBaseBean");
                if(userBean.getData().getIs_audit()==0){
                    //列表页面
                    Intent intent_list=new Intent(getActivity(), AuditListActivity.class);
                    startActivity(intent_list);
                }else {
                    //认证页面
                    Intent intent_shenhe=new Intent(getActivity(), AuditStatusActivity.class);
                    startActivity(intent_shenhe);
                }

                break;
        }
    }

}
