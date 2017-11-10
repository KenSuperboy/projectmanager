package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.CopyrightAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.CopyrrightBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class CopyrightActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.orderby1_tv)
    TextView orderby1Tv;
    @BindView(R.id.orderby2_tv)
    TextView orderby2Tv;
    @BindView(R.id.orderby3_tv)
    TextView orderby3Tv;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_date)
    TextView noDate;
    private String[] timeperiod = {"one_month","three_month","one_year"}; //one_month   three_month  one_year_
    private CopyrightAdater adater;
    private String[] no_date={"近一个月无任何版权交易记录","近三个月无任何版权交易记录","近一年无任何版权交易记录"};
    int type=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyright);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("版权交易记录");
        initList(null);
        copyrightdtl(timeperiod[0]);
    }

    @OnClick({R.id.toolbar_left_title, R.id.orderby1_tv, R.id.orderby2_tv, R.id.orderby3_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.orderby1_tv:
                type=0;
                orderby1Tv.setBackgroundResource(R.drawable.bt_history_p_sp);
                orderby2Tv.setBackgroundResource(R.drawable.bt_history_n_sp);
                orderby3Tv.setBackgroundResource(R.drawable.bt_history_n_sp);
                orderby1Tv.setTextColor(Color.WHITE);
                orderby2Tv.setTextColor(getResources().getColor(R.color.df_font));
                orderby3Tv.setTextColor(getResources().getColor(R.color.df_font));
                copyrightdtl(timeperiod[type]);
                break;
            case R.id.orderby2_tv:
                type=1;
                orderby1Tv.setBackgroundResource(R.drawable.bt_history_n_sp);
                orderby2Tv.setBackgroundResource(R.drawable.bt_history_p_sp);
                orderby3Tv.setBackgroundResource(R.drawable.bt_history_n_sp);
                orderby1Tv.setTextColor(getResources().getColor(R.color.df_font));
                orderby2Tv.setTextColor(Color.WHITE);
                orderby3Tv.setTextColor(getResources().getColor(R.color.df_font));
                copyrightdtl(timeperiod[type]);
                break;
            case R.id.orderby3_tv:
                type=2;
                orderby1Tv.setBackgroundResource(R.drawable.bt_history_n_sp);
                orderby2Tv.setBackgroundResource(R.drawable.bt_history_n_sp);
                orderby3Tv.setBackgroundResource(R.drawable.bt_history_p_sp);
                orderby1Tv.setTextColor(getResources().getColor(R.color.df_font));
                orderby2Tv.setTextColor(getResources().getColor(R.color.df_font));
                orderby3Tv.setTextColor(Color.WHITE);
                copyrightdtl(timeperiod[type]);
                break;
        }
    }


    private void copyrightdtl(String timeperiod) {
        RxRetrofitClient.getInstance(this).copyrightdtl(timeperiod, new Observer<CopyrrightBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CopyrightActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(CopyrrightBean copyrrightBean) {
                if (copyrrightBean.getCode() == 1) {
                    initList(copyrrightBean);
                }
            }
        });
    }

    private void initList(final CopyrrightBean copyrrightBean) {
        if (adater == null) {
            adater = new CopyrightAdater(this, copyrrightBean);
            list.setAdapter(adater);
            list.setLayoutManager(new LinearLayoutManager(this));
            //添加分割线
            list.addItemDecoration(new DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL_LIST));
            adater.setOnItemClickLitener(new OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {

                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

        } else {
            adater.rf(copyrrightBean);
        }
        if(copyrrightBean!=null && copyrrightBean.getData().size()>0)
            noDate.setVisibility(View.GONE);
        else
        {
            noDate.setText(no_date[type]);
            noDate.setVisibility(View.VISIBLE);
        }


    }
}
