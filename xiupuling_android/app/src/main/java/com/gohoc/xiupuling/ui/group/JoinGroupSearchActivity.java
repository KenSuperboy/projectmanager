package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.GroupSearchAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.GroupSearchBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class JoinGroupSearchActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.serach_ct)
    EditText serachCt;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_recod_tv)
    TextView noRecodTv;
    private GroupSearchAdater groupSearchAdater;
    private GroupSearchBean groupSearchBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group_search);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("加入群组");
        serachCt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        serachCt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            if (!(serachCt.getText() + "").isEmpty())
                                getCanJoinUnionListByCond(serachCt.getText() + "");
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
        init();
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        JoinGroupSearchActivity.this.finish();
    }

    private void getCanJoinUnionListByCond(String q) {
        RxRetrofitClient.getInstance(this).getCanJoinUnionListByCond(serachCt.getText() + "", new Observer<GroupSearchBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(JoinGroupSearchActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupSearchBean groupSearchBean) {
                if (groupSearchBean.getData() != null && groupSearchBean.getData().size() > 0) {
                    groupSearchBeans=groupSearchBean;
                    Logger.e(groupSearchBean.getData().size() + "");
                    noRecodTv.setVisibility(View.GONE);
                } else {
                    groupSearchBeans=null;
                    noRecodTv.setVisibility(View.VISIBLE);
                }
                groupSearchAdater.rf(groupSearchBeans);
            }
        });

    }

    private void init() {

        groupSearchAdater = new GroupSearchAdater(this, groupSearchBeans);
        list.setAdapter(groupSearchAdater);
        list.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        list.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        groupSearchAdater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(JoinGroupSearchActivity.this, JoinGroupDtActivity.class).putExtra("id", groupSearchBeans.getData().get(position).getUnion_id()));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(serachCt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
