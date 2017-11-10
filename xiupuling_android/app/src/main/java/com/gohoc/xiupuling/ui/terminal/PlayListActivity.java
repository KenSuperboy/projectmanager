package com.gohoc.xiupuling.ui.terminal;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.TerminalPlayListAdapter;
import com.gohoc.xiupuling.bean.PlayerList;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;

public class PlayListActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list)
    ExpandableListView list;
    @BindView(R.id.no_data_lv)
    LinearLayout noDataLv;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private String terminal_id;
    private TerminalPlayListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("播放清单");
        toolbarRight.setText("一键清空");
        terminal_id = getIntent().getStringExtra("terminal_id");
        getCurrentPlayList(terminal_id);
    }

    @OnClick({R.id.toolbar_left_title, R.id.no_data_lv,R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.no_data_lv:
                break;
            case  R.id.toolbar_right:
                delList();
                break;
        }
    }

    private void getCurrentPlayList(String terminal_id) {
        RxRetrofitClient.getInstance(PlayListActivity.this).getCurrentPlayList(terminal_id, new Observer<PlayerList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(PlayListActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(PlayerList playerList) {
                if (playerList.getCode() == 1) {
                    initList(playerList);
                }

            }
        });
    }

    private void initList(PlayerList playerList) {
        adapter = new TerminalPlayListAdapter(playerList, this, list);
        list.setAdapter(adapter);
        list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        for (int i = 0; i < playerList.getData().size(); i++) {
            list.expandGroup(i);
        }
    }

    private void delList() {

        BGAAlertController alertController = new BGAAlertController(this, "","一旦清空，数据不可恢复，目前该终端未完成的订单收益全部归零。" , BGAAlertController.AlertControllerStyle.ActionSheet);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示

        alertController.addAction(new BGAAlertAction("清空内容", BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                RxRetrofitClient.getInstance(PlayListActivity.this).takebackterminalallorder(terminal_id, new Observer<VCodeBenan>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(PlayListActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(VCodeBenan vCodeBenan) {
                        if(vCodeBenan.getCode()==1)
                        {
                            getCurrentPlayList(terminal_id);
                        }

                    }
                });
            }
        }));
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.setCancelable(true);
        alertController.show();
    }

}
