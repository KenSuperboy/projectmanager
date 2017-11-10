package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.GroupUserAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.GroupUserBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.push.PushReqDtActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.dialog.BaseDialog;
import com.gohoc.xiupuling.widget.dialog.BlueDialog;
import com.gohoc.xiupuling.widget.dialog.NormalDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class GroupCessionActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private String union_id;
    private GroupUserAdater groupUserAdater;
    private AlertView mAlertView;
    private int post;
    private UserBean userBean;
    private GroupUserBean groupUserBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_user_list);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("选择新群主");
        userBean = (UserBean) ACache.get(this).getAsObject("userBean");
        try {
            union_id = getIntent().getStringExtra("union_id");
            getMyUnionUserList();
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        GroupCessionActivity.this.finish();
    }


    private void getMyUnionUserList() {
        RxRetrofitClient.getInstance(this).getMyUnionUserList(union_id, new Observer<GroupUserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupCessionActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupUserBean groupUserBean) {
                if (groupUserBean.getCode() == 1) {
                    groupUserBeans = groupUserBean;
                    initList(groupUserBeans);
                }

            }
        });
    }

    private void initList(final GroupUserBean groupUserBean) {
        if (groupUserAdater == null) {
            groupUserAdater = new GroupUserAdater(GroupCessionActivity.this, groupUserBean, false);
            list.setAdapter(groupUserAdater);
            list.setLayoutManager(new LinearLayoutManager(GroupCessionActivity.this));
            list.addItemDecoration(new DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL_LIST));
            groupUserAdater.setOnItemClick(new GroupUserAdater.OnItemLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    post = position;
                    if (groupUserBean.getData().get(post).getUser_id().equals(userBean.getData().getUser_id()))
                        return;


                    String tipsStr = "确定选择<font color='#27ABFD'>" + groupUserBean.getData().get(post).getNick_name() + "</font>为新群主，您将自动放弃群主身份。";
                    if (getIntent().getIntExtra("type", 0) == 1) {
                        tipsStr = "确定选择<font color='#27ABFD'>" + groupUserBean.getData().get(post).getNick_name() + "</font>为新群主，您确定要退出该群组？";
                    }

                    BlueDialog.create(getFragmentManager())
                            .setNormalDialogListenner(new BlueDialog.NormalDialogListenner() {
                                @Override
                                public void onClick(BlueDialog.NormalType normalType) {
                                    if (normalType == BlueDialog.NormalType.OK) {
                                        shiftUnionOwn(groupUserBean.getData().get(post).getUser_id() + "", getIntent().getIntExtra("isOut", 1));
                                    }
                                }
                            })
                            .setmTileText("")
                            .setmContent(Html.fromHtml(tipsStr))
                            .setmCannelText("取消")
                            .setmConfirmText("确定")
                            .setTvContentGravity(Gravity.CENTER)
                            .setmOutsideCancel(false)
                            .setmBackCancel(true)
                            .setmTag("提示")
                            .setDialogViewListener(new BaseDialog.DialogViewListener() {
                                @Override
                                public void bindView(View v) {

                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .show();
                }

                @Override
                public void onItemDelClick(View view, int position) {

                }
            });
        } else {
            groupUserAdater.rf(groupUserBeans);
        }

    }


    private void shiftUnionOwn(String id, int isOut) {
        RxRetrofitClient.getInstance(this).shiftUnionOwn(union_id, id + "", isOut + "", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupCessionActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    if(getIntent().getIntExtra("type", 0) == 1)
                        Utils.toast(GroupCessionActivity.this,"已成功退出群组");
                    else
                        Utils.toast(GroupCessionActivity.this, "新群主已指定");

                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                    Intent intent = new Intent(GroupCessionActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    EventBus.getDefault().post(new Event.MainIndex(1));
                    finish();
                }else
                    Utils.toast(GroupCessionActivity.this, vCodeBenan.getMessage());

            }
        });
    }
}
