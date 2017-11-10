package com.gohoc.xiupuling.ui.push;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PlayLogBean;
import com.gohoc.xiupuling.bean.PlayLogMultiple;
import com.gohoc.xiupuling.bean.PushResultMultiple;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PlayLogActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    private MyAdater myAdater;
    private List<PlayLogMultiple> playLoglist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_log);
        ButterKnife.bind(this);
        toolbarTitle.setText("播放日志");
        setStatusColor(R.color.colorPrimary);
        getTerminalPlayList(getIntent().getStringExtra("tId"), getIntent().getStringExtra("range_id"), null);
    }

    private void getTerminalPlayList(String terminal_ids, String range_id, String info_id) {
        RxRetrofitClient.getInstance(this).getTerminalPlayList(terminal_ids, range_id, info_id, new Observer<PlayLogBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PlayLogActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PlayLogBean playLogBean) {
                if (playLogBean.getCode() == 1) {
                    initList(playLogBean);
                }

            }
        });
    }

    private void initList(PlayLogBean playLogBean) {
        for (int a = 0; a < playLogBean.getData().size(); a++) {
            playLoglist.add(new PlayLogMultiple(playLogBean.getData().get(a), PlayLogMultiple.HEAD_TYPE));
            for (int b = 0; b < playLogBean.getData().get(a).getDaydtllist().size(); b++) {
                if (b == 0)
                    playLoglist.add(new PlayLogMultiple(playLogBean.getData().get(a).getCycle_cnt() + "", playLogBean.getData().get(a).getDaydtllist().get(b), PlayLogMultiple.CONTENT_H_TYPE));
                else
                    playLoglist.add(new PlayLogMultiple(playLogBean.getData().get(a).getDaydtllist().get(b), PlayLogMultiple.CONTENT_TYPE));
            }
        }

        myAdater = new MyAdater(playLoglist);
        list.setAdapter(myAdater);
        list.setLayoutManager(new LinearLayoutManager(this));


    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    class MyAdater extends BaseMultiItemQuickAdapter<PlayLogMultiple, BaseViewHolder> {
        public MyAdater(List<PlayLogMultiple> data) {
            super(data);
            addItemType(PlayLogMultiple.HEAD_TYPE, R.layout.item_play_log_h);
            addItemType(PlayLogMultiple.CONTENT_H_TYPE, R.layout.item_play_log_c_h);
            addItemType(PlayLogMultiple.CONTENT_TYPE, R.layout.item_play_log_c);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, PlayLogMultiple playLogMultiple) {
            switch (baseViewHolder.getItemViewType()) {
                case PlayLogMultiple.HEAD_TYPE:
                    baseViewHolder.setText(R.id.start_time_tv, Utils.delTime(playLogMultiple.getDataBean().getStart_date()))
                            .setText(R.id.end_time_tv, Utils.delTime(playLogMultiple.getDataBean().getEnd_date()))
                            .setText(R.id.time_money, "￥" + playLogMultiple.getDataBean().getTrans_amount() + "元");

                    break;
                case PlayLogMultiple.CONTENT_H_TYPE:
                    baseViewHolder.setText(R.id.total, "合计播放:   " + playLogMultiple.getTotal() + "次")
                            .setText(R.id.times, Utils.delTime(playLogMultiple.getDaydtllistBean().getTermdate()))
                            .setText(R.id.pcount, "播放" + playLogMultiple.getDaydtllistBean().getCyclecnt() + "次");
                    break;
                case PlayLogMultiple.CONTENT_TYPE:
                    baseViewHolder.setText(R.id.times, Utils.delTime(playLogMultiple.getDaydtllistBean().getTermdate()))
                            .setText(R.id.pcount, "播放" + playLogMultiple.getDaydtllistBean().getCyclecnt() + "次");
                    if(playLoglist.size()-1==baseViewHolder.getAdapterPosition())
                        baseViewHolder.setVisible(R.id.splite,true);
                    break;
            }
        }
    }
}
