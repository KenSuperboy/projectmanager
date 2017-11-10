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
import com.gohoc.xiupuling.ui.account.MemberAuthorizationActivity;
import com.gohoc.xiupuling.ui.account.MemberShopActivity;
import com.gohoc.xiupuling.ui.combinationpackage.CombinationListActivity;
import com.gohoc.xiupuling.ui.linkpackage.LiandongbaoListActivity;
import com.gohoc.xiupuling.ui.requirement.CreateReqActivity;
import com.gohoc.xiupuling.ui.requirement.CreateReqOtherActivity;
import com.gohoc.xiupuling.ui.requirement.MyWorksActivity;
import com.gohoc.xiupuling.ui.requirement.ReqListActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;


public class WorksFragment extends BaseFragment {

    @BindView(R.id.rq_ct_ll)
    LinearLayout rqCtLl;
    @BindView(R.id.rq_ot_ll)
    LinearLayout rqOtLl;
    @BindView(R.id.rq_ot_list_ll)
    LinearLayout rqOtListLl;
    @BindView(R.id.rq_ct_list_ll)
    LinearLayout rqCtListLl;
    private View viewContainer;
    private Window window;
    private AlertDialog dialog;

    public WorksFragment() {

    }

    private UserBaseBean.DataBean userBaseBeans;

    public static WorksFragment newInstance() {
        WorksFragment fragment = new WorksFragment();
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
        viewContainer = inflater.inflate(R.layout.fragment_works, container, false);
        ButterKnife.bind(this, viewContainer);
        return viewContainer;

    }

    private boolean canMade=true;
    @OnClick({R.id.rq_ct_ll, R.id.rq_ot_ll, R.id.rq_ot_list_ll, R.id.rq_ct_list_ll,R.id.linear_zuopingzuhe_layout,R.id.linear_liandong_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rq_ct_ll:
                if(canMade){
                    canMade=false;
                    updateUserInfo(getActivity());
                }
                break;
            case R.id.rq_ot_ll:
                startActivity(new Intent(getActivity(), CreateReqOtherActivity.class));
                break;
            case R.id.rq_ot_list_ll:
                startActivity(new Intent(getActivity(), ReqListActivity.class));
                break;
            case R.id.rq_ct_list_ll:
                startActivity(new Intent(getActivity(), MyWorksActivity.class));
                break;
            case R.id.linear_zuopingzuhe_layout://作品组合包
                startActivity(new Intent(getActivity(), CombinationListActivity.class));
                break;
            case R.id.linear_liandong_layout://多屏联动包
                startActivity(new Intent(getActivity(), LiandongbaoListActivity.class));
                break;
        }
    }

    public void updateUserInfo(Context context) {
        RxRetrofitClient.getInstance(context).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {
                canMade=true;
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(getActivity(), "请检查网络是否正常");
                canMade=true;
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                canMade=true;
                if (userBaseBean.getCode() == 1) {
                    Credential.getInstance().setCurUser(userBaseBean);
                    userBaseBeans = Credential.getInstance().getCurUser(getActivity()).getData();
                    EventBus.getDefault().post(new Event.UserEvent());
                    if (userBaseBean.getData().getWork_left_cnt() > 0)
                        startActivity(new Intent(getActivity(), CreateReqActivity.class));
                    else
                        showShopPop();
                }else {
                    Utils.toast(getActivity(), userBaseBean.getMessage());
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

}
