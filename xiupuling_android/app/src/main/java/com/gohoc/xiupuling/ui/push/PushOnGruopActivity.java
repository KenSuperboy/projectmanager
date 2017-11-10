package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.bean.PushGroupMenuBean;
import com.gohoc.xiupuling.bean.ShopAndGroupItemBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushOnGruopActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.arr_right_iv)
    ImageView arrRightIv;
    @BindView(R.id.all_ll)
    LinearLayout allLl;
    @BindView(R.id.select_ll)
    LinearLayout selectLl;
    @BindView(R.id.group_type_tv)
    TextView groupTypeTv;
    private ShopAndGroupItemBean shopAndGroupItemBean;
    private int orientation;
    private static int TERMINAL_REQUEST_RESULT = 1000;
    private PushGroupMenuBean pushGroupMenuBean;
    private int count = 0;//终端数量
    private ArrayList<GroupTermianlListBean.DataBean> selectArrryList;
    private boolean combination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_on_gruop);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        shopAndGroupItemBean = (ShopAndGroupItemBean) getIntent().getExtras().get("shopAndGroupItemBean");
        orientation = getIntent().getIntExtra("orientation", 0);
        pushGroupMenuBean = (PushGroupMenuBean) getIntent().getExtras().get("pushGroupMenuBean");
        pushGroupMenuBean.setGroupTermianlType(shopAndGroupItemBean.getGroupDates().getUnion_type() == 1 ? 1 : 0);
        groupTypeTv.setText(shopAndGroupItemBean.getGroupDates().getUnion_type() == 1|| shopAndGroupItemBean.getGroupDates().getUnion_type() == 2? "群组内所有我的终端" : "群组内所有别人的终端");
        toolbarTitle.setText(shopAndGroupItemBean.getGroupDates().getUnion_typeStr());
        selectArrryList = (ArrayList<GroupTermianlListBean.DataBean>) getIntent().getExtras().get("selectArrryList");

        if (pushGroupMenuBean.getTyepe() == 0 && pushGroupMenuBean.getUnion() != null && shopAndGroupItemBean.getGroupDates().getUnion_id().equals(pushGroupMenuBean.getUnion().getUnion_id()))
            arrRightIv.setVisibility(View.VISIBLE);
        else
            arrRightIv.setVisibility(View.GONE);


        if (!(pushGroupMenuBean.getTyepe() == 2 && pushGroupMenuBean.getUnion() != null && shopAndGroupItemBean.getGroupDates().getUnion_id().equals(pushGroupMenuBean.getUnion().getUnion_id())))
            selectArrryList = null;

        if (pushGroupMenuBean.getGroupTermianlType() == 1||pushGroupMenuBean.getGroupTermianlType() == 2)
            getUnionTerminalList(shopAndGroupItemBean.getGroupDates().getUnion_id());
        else
            getUnionTerminalListofTheirs(shopAndGroupItemBean.getGroupDates().getUnion_id());
    }

    @OnClick({R.id.toolbar_left_title, R.id.all_ll, R.id.select_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.all_ll:
                pushGroupMenuBean.setUnion(shopAndGroupItemBean.getGroupDates());
                pushGroupMenuBean.setTyepe(0);
                setResult(RESULT_OK, new Intent().putExtra("pushGroupMenuBean", pushGroupMenuBean));
                finish();
                break;
            case R.id.select_ll:
                pushGroupMenuBean.setUnion(shopAndGroupItemBean.getGroupDates());
                startActivityForResult(new Intent(PushOnGruopActivity.this, PushSelectGroupTerminalActivity.class)
                                .putExtra("groupId", shopAndGroupItemBean.getGroupDates().getUnion_id())
                                .putExtra("orientation", orientation)
                                .putExtra("pushGroupMenuBean", pushGroupMenuBean)
                                .putExtra("selectArrryList", selectArrryList)
                        , TERMINAL_REQUEST_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }


    private void getUnionTerminalList(String union_id) {
        RxRetrofitClient.getInstance(PushOnGruopActivity.this).getUnionTerminalList(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushOnGruopActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1) {
                    for (int a = 0; a < groupTermianlListBean.getData().size(); a++) {
                        if (groupTermianlListBean.getData().get(a).getTerm_orientation() == orientation)
                            count++;
                    }
                    pushGroupMenuBean.setTerminalCount(count);
                }

            }
        });

    }


    private void getUnionTerminalListofTheirs(String union_id) {

        RxRetrofitClient.getInstance(this).getUnionTerminalListofTheirs(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PushOnGruopActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1) {
                    for (int a = 0; a < groupTermianlListBean.getData().size(); a++) {
                        if (groupTermianlListBean.getData().get(a).getTerm_orientation() == orientation)
                            count++;
                    }
                    pushGroupMenuBean.setTerminalCount(count);
                }
            }
        });

    }
}
