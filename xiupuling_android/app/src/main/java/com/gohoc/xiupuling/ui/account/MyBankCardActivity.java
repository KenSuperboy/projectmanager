package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.MyBankListAdater;
import com.gohoc.xiupuling.bean.MyBankBean;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class MyBankCardActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    private View footerview;
    private TextView bankTipsTv;
    private ACache mCache;
    private SystemInfoBean sb;
    private MyBankListAdater adater;
    private MyBankBean.DataBean bank;
    private final  static int REQUEST_RESULT_ADD_BANK=10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_card);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        mCache = ACache.get(this);
        sb = (SystemInfoBean) mCache.getAsObject("SystemInfoBean");
        toolbarTitle.setText("我的银行卡");
        try {
            bank = (MyBankBean.DataBean) getIntent().getExtras().get("bank");
        }catch (Exception e){}

        mybankcardlist();
    }


    @OnClick({R.id.toolbar_left_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
        }
    }

    private void mybankcardlist() {
        RxRetrofitClient.getInstance(this).mybankcardlist(new Observer<MyBankBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MyBankCardActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onNext(MyBankBean bankBean) {
                if (bankBean.getCode() == 1) {
                    initList(bankBean.getData());
                }

            }
        });
    }


    private void initList(final List<MyBankBean.DataBean> data) {
        adater = new MyBankListAdater(this, R.layout.item_my_bank, data);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adater);
/*        //添加分割线
        list.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));*/
        adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                setResult(RESULT_OK, new Intent().putExtra("bank", data.get(i)));
                finish();
            }
        });
        footerview = getLayoutInflater().inflate(R.layout.item_my_bank_no, (ViewGroup) list.getParent(), false);
        footerview.findViewById(R.id.add_card_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MyBankCardActivity.this, AddBankCerActivity.class),REQUEST_RESULT_ADD_BANK);
            }
        });
        bankTipsTv= (TextView) footerview.findViewById(R.id.bank_tips_tv);
        bankTipsTv.setText(sb.getData().getSystemXplSupportBank());
        //
        adater.addFooterView(footerview);
        if (bank != null) {
            for (int a = 0; a < data.size(); a++) {
                if (bank.getBank_id() == data.get(a).getBank_id()) {
                    data.get(a).setSelect(true);
                    adater.notifyItemChanged(a);
                    break;
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            mybankcardlist();
        }
    }
}
