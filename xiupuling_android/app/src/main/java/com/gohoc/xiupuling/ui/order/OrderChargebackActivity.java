package com.gohoc.xiupuling.ui.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.SecurityQsBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.dialog.Delete_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class OrderChargebackActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MyAdapter myAdater;
    private SecurityQsBean securityQsBeans;
    private String range_id;
    private String terminal_id;
    private String title;
    private View header;
    private View footer;
    private LinearLayout button_ll;
    private SecurityQsBean.DataBean curr;
    private int playType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_chargeback);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        playType=getIntent().getIntExtra("playType",0);
        title=getIntent().getStringExtra("title");
        toolbarTitle.setText("申请退单");
        header = getLayoutInflater().inflate(R.layout.item_takeback_head, (ViewGroup) recyclerView.getParent(), false);
        footer = getLayoutInflater().inflate(R.layout.item_takeback_foot, (ViewGroup) recyclerView.getParent(), false);
        range_id = getIntent().getStringExtra("range_id");
        terminal_id = getIntent().getStringExtra("terminal_id");
        button_ll = (LinearLayout) footer.findViewById(R.id.button_ll);
        button_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curr != null) {
                    if(playType==2){
                        takebackorder(terminal_id, range_id, curr.getIds() + "");
                    }else {
                        tuiKuanDialog();
                    }
                }
            }
        });
        gettakebackorderreasonlist();
        LogUtil.d("传输进来的："+playType);
    }

    //申请退款弹窗提示
    private void tuiKuanDialog()
    {
        String name="";
        if(playType==0){
            name="组合包";
        }else if(playType==1){
            name="联动包";
        }
        SpannableString spannableString = new SpannableString("此订单是" + name + "订单，是否退掉整个"+name);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gallery_dark_gray)), 0, ("此订单是").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("此订单是").length(), ("此订单是" + name).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gallery_dark_gray)), ("此订单是" + name).length(), ("此订单是" + name + "订单，是否退掉整个").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("此订单是" + name + "订单，是否退掉整个").length(), ("此订单是" + name + "订单，是否退掉整个"+name).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Delete_Dialog delete_dialog=new Delete_Dialog(mContext,spannableString);
        delete_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                takebackorder(terminal_id, range_id, curr.getIds() + "");
            }

            @Override
            public void cancelBack() {

            }
        });
        delete_dialog.show();
    }

    private void check() {
        for (int a = 0; a < securityQsBeans.getData().size(); a++) {
            if (securityQsBeans.getData().get(a).isCheck())
                curr = securityQsBeans.getData().get(a);
        }
        if (curr != null)
            button_ll.setBackgroundResource(R.color.colorPrimary);
        else
            button_ll.setBackgroundResource(R.color.unable);
    }

    private void gettakebackorderreasonlist() {
        RxRetrofitClient.getInstance(this).gettakebackorderreasonlist(new Observer<SecurityQsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderChargebackActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(SecurityQsBean securityQsBean) {
                if (securityQsBean.getCode() == 1) {
                    securityQsBeans = securityQsBean;
                    initList();
                }

            }
        });
    }

    private void takebackorder(String terminal_id, String range_id, String remark) {
        RxRetrofitClient.getInstance(this).takebackorder(terminal_id, range_id, remark, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(OrderChargebackActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(OrderChargebackActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void initList() {
        myAdater = new MyAdapter(R.layout.item_simple_clickbox3, securityQsBeans.getData());
        recyclerView.setAdapter(myAdater);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Logger.e(i + "");
                for (int a = 0; a < securityQsBeans.getData().size(); a++) {
                    securityQsBeans.getData().get(a).setCheck(false);
                }
                securityQsBeans.getData().get(i).setCheck(true);

                myAdater.notifyDataSetChanged();
                check();
            }
        });
        myAdater.addHeaderView(header);
        myAdater.addFooterView(footer);
    }

    class MyAdapter extends BaseQuickAdapter<SecurityQsBean.DataBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, List<SecurityQsBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, SecurityQsBean.DataBean dataBean) {
            ImageView iv = baseViewHolder.getView(R.id.right_iv);
            if (dataBean.isCheck())
                iv.setImageResource(R.mipmap.icon__zhuce_tongyi);
            else
                iv.setImageResource(R.mipmap.icon_zhuce_butongyi);

            baseViewHolder.setText(R.id.menu_title, dataBean.getDictcaption() + "");
        }
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }
}
