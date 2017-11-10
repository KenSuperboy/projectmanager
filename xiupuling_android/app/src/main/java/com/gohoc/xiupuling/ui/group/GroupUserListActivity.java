package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BusinessTypeAdater;
import com.gohoc.xiupuling.adapter.GroupAdater;
import com.gohoc.xiupuling.adapter.GroupUserAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.BusinessTypeBean;
import com.gohoc.xiupuling.bean.GroupBean;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.GroupUserBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.terminal.SelectShopBusinessTypeActivity;
import com.gohoc.xiupuling.ui.terminal.SelectShopBusinessTypeSubActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.dialog.BaseDialog;
import com.gohoc.xiupuling.widget.dialog.BlueDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class GroupUserListActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private GroupDetailsBean groupDetailsBeans;
    private GroupUserAdater groupUserAdater;
    private GroupUserBean groupUserBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_user_list);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("群用户");

        try {
            groupDetailsBeans = (GroupDetailsBean) getIntent().getExtras().get("groupDetailsBeans");
            getMyUnionUserList();
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        GroupUserListActivity.this.finish();
    }


    private void getMyUnionUserList() {
        RxRetrofitClient.getInstance(this).getMyUnionUserList(groupDetailsBeans.getData().getUnion_id() + "", new Observer<GroupUserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupUserListActivity.this, "请检查网络是否正常");
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
            groupUserAdater = new GroupUserAdater(GroupUserListActivity.this, groupUserBeans, true);
            list.setAdapter(groupUserAdater);
            list.setLayoutManager(new LinearLayoutManager(GroupUserListActivity.this));
            list.addItemDecoration(new DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL_LIST));
            groupUserAdater.setOnItemClick(new GroupUserAdater.OnItemLitener() {
                @Override
                public void onItemClick(View view, int position) {

                }

                @Override
                public void onItemDelClick(View view, final int position) {
                    BlueDialog.create(getFragmentManager())
                            .setNormalDialogListenner(new BlueDialog.NormalDialogListenner() {
                                @Override
                                public void onClick(BlueDialog.NormalType normalType) {
                                    if (normalType == BlueDialog.NormalType.OK) {
                                        deleteUserFromUnion(position, groupUserBean.getData().get(position).getUnion_id() + "", groupUserBean.getData().get(position).getUser_id());
                                    }
                                }
                            })
                            .setmTileText("")
                            .setTvContentGravity(Gravity.CENTER)
                            .setmContent(Html.fromHtml("确定将<font color='#27ABFD'>" + groupUserBean.getData().get(position).getNick_name() + "</font>踢出该群组"))
                            .setmCannelText("取消")
                            .setmConfirmText("确定")
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
            });
        } else {
            groupUserAdater.rf(groupUserBeans);
        }

    }

    private void deleteUserFromUnion(final int p, String union_id, String delete_user_id) {
        RxRetrofitClient.getInstance(this).deleteUserFromUnion(union_id, delete_user_id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupUserListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    Utils.toast(GroupUserListActivity.this, "踢出成功");
                    // getMyUnionUserList();
                    EventBus.getDefault().post(new Event.GroupEvent(null));
                    groupUserBeans.getData().remove(p);
                    groupUserAdater.rf(groupUserBeans);

                }

            }
        });
    }
}
