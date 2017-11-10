package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BankListAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.BankBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class BanklistActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    private BankListAdater bankListAdater;
    private BankBean bankBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banklist);
        ButterKnife.bind(this);
        toolbarTitle.setText("银行列表");
        setStatusColor(R.color.colorPrimary);
        banklist();
        initList();

    }

    private void initList() {
        bankListAdater = new BankListAdater(this, bankBeans);
        list.setAdapter(bankListAdater);
        list.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        list.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        bankListAdater.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                // startActivity(new Intent(BanklistActivity.this, GroupDetailsActivity.class).putExtra("groupBean", groupBeans.getData().get(position)));
                setResult(RESULT_OK, new Intent().putExtra("bank", bankBeans.getData().get(position)));
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void banklist() {
        RxRetrofitClient.getInstance(this).banklist(new Observer<BankBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(BanklistActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(BankBean bankBean) {
                if (bankBean.getCode() == 1) {
                    if (bankBean.getData() != null || bankBean.getData().size() > 0) {
                        bankBeans=bankBean;
                        bankListAdater.rf(bankBean);
                    }
                }

            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.no_data_lv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.no_data_lv:
                break;
        }
    }
}
