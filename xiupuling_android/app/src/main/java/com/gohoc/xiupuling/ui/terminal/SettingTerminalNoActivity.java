package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.AddPicAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.adapter.TerminalNoAdater;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.ShopBean;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.bean.TerminalBean;
import com.gohoc.xiupuling.bean.TerminalListBean;
import com.gohoc.xiupuling.bean.TerminalNoBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class SettingTerminalNoActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TerminalNoAdater adater;
    private List<TerminalNoBean> list = new ArrayList<TerminalNoBean>();
    private ShopDetailsBean.DataBean shopDetailsBeans;
    private String no = "";
    private String shopId = "";
    private TerminalListBean dataBean;
    private View footer;
    int count = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_terminal_no);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("编号");
        no = getIntent().getStringExtra("terminalNo");
        shopId = getIntent().getStringExtra("shopId");
        getShopDetail(shopId);
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        SettingTerminalNoActivity.this.finish();
    }

    private void initRv() {

        if (TextUtils.isEmpty(no))
            no = "1";    //新建的终端还没有编号
        if (null != dataBean) {
            if (Integer.parseInt(no) > count) {
                count *= ((Integer.parseInt(no) / count) + 1);
            }
        }

        Logger.d(count);
        for (int a = 0; a < count; a++) {
            list.add(new TerminalNoBean(String.format("%02d", a + 1) + "", 0));
            if (null != dataBean) {
                for (int b = 0; b < dataBean.getData().size(); b++) {
                    Logger.e(dataBean.getData().get(b).getTerminal_no() + "    " + list.get(a).getNo());
                    if (dataBean.getData().get(b).getTerminal_no().equals(list.get(a).getNo())) {
                        list.get(a).setState(-1);
                    }

                    if (null != no && getIntent().getIntExtra("isMatchNo",0)==1) {
                        //if (no.equals(list.get(a).getNo()) && list.get(a).getState() != -1) {
                        if (no.equals(list.get(a).getNo())) {
                            list.get(a).setState(1);
                        }
                    }
                }

            }

        }


        recyclerView.setLayoutManager(new GridLayoutManager(SettingTerminalNoActivity.this, 4));
        //adater.setFooterView(footer);

        //设置adapter
        adater = new TerminalNoAdater(SettingTerminalNoActivity.this, R.layout.item_terminal_no, list);
        adater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if (list.get(position).getState() != 0)
                    return;
                for (int a = 0; a < list.size(); a++) {
                    if (list.get(a).getState() != -1) {
                        list.get(a).setState(0);
                    }
                }
                list.get(position).setState(1);
                adater.notifyDataSetChanged();
                setResult(RESULT_OK, new Intent().putExtra("terminalNo", list.get(position).getNo()).putExtra("shopId", shopId));
                SettingTerminalNoActivity.this.finish();
            }
        });

        footer = getLayoutInflater().inflate(R.layout.item_terminal_no_foot, (ViewGroup) recyclerView.getParent(), false);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adater.getList().size()> 96)
                    return;
                List<TerminalNoBean> listTemp = new ArrayList<TerminalNoBean>();
                for (int a = 0; a < count; a++) {
                    listTemp.add(new TerminalNoBean((adater.getList().size() + a) + "", 0));
                    if((adater.getList().size()+a)==99)
                        break;
                }
                adater.add(listTemp);
                recyclerView.smoothScrollToPosition(adater.getItemCount() - 1);
            }
        });
        adater.addFooterView(footer);
        recyclerView.setAdapter(adater);
        recyclerView.scrollToPosition(adater.getItemCount() - 1);
    }


    public void getShopDetail(String id) {
        RxRetrofitClient.getInstance(SettingTerminalNoActivity.this).getShopTermList(id,"2", new Observer<TerminalListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(SettingTerminalNoActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TerminalListBean terminalListBean) {
                if (terminalListBean.getCode() == 1) {
                    dataBean = terminalListBean;
                    initRv();
                }


            }
        });
    }
}
