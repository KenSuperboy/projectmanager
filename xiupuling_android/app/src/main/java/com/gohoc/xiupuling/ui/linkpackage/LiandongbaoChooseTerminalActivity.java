package com.gohoc.xiupuling.ui.linkpackage;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.linkpackage.LiandongChooseTerminalAdapter;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 新建联动关系-----选择终端
* */
public class LiandongbaoChooseTerminalActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.gridview)
    GridView mGridview;
    @BindView(R.id.tv_name)
    TextView mTvName;
    private String union_id, union_name;
    private ArrayList<String> terminal_id;
    private int myposition, orientation;

    private LiandongChooseTerminalAdapter mLiandongChooseTerminalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_terminal_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);

        initView();
        initListener();
        initData();

    }

    private void initView() {
        tempGroupTermianlListBean.setData(dataBeanList);

        mToolbarTitle.setText("选择投放终端");
        mLiandongChooseTerminalAdapter = new LiandongChooseTerminalAdapter(mContext);
        mGridview.setAdapter(mLiandongChooseTerminalAdapter);
    }

    private void initListener() {
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.d("点击详情信息");
                GroupTermianlListBean.DataBean dataBean = (GroupTermianlListBean.DataBean) parent.getAdapter().getItem(position);

                EventBus.getDefault().post(new Event.SetLiandongChooseTerminalEvent(dataBean.getTerminal_id(), dataBean.getTerminal_no(), myposition));
                finish();
            }
        });
    }

    String myorientation = "";

    private void initData() {
        union_id = getIntent().getStringExtra("union_id");
        myposition = getIntent().getIntExtra("position", 0);
        terminal_id = getIntent().getStringArrayListExtra("terminal_id");
        union_name = getIntent().getStringExtra("union_name");


        orientation = getIntent().getIntExtra("orientation", 0);

        if (orientation == 0) {
            myorientation = "横屏";
        } else {
            myorientation = "竖屏";
        }
        mTvName.setText(union_name + "的" + "“" + myorientation + "”终端列表");
        getUnionTerminalList(union_id);
        LogUtil.d(":另一边传过来的："+terminal_id);
    }

    private GroupTermianlListBean tempGroupTermianlListBean=new GroupTermianlListBean();

    private void getUnionTerminalList(String union_id) {
        RxRetrofitClient.getInstance(LiandongbaoChooseTerminalActivity.this).getUnionTerminalList(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //Utils.toast(LiandongbaoChooseTerminalActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1 && groupTermianlListBean.getData().size() > 0) {

                    //tempGroupTermianlListBean=groupTermianlListBean;


                    LogUtil.d("加载成功:"+orientation+"====长度："+groupTermianlListBean.getData().size());
                    for (int i = 0; i < groupTermianlListBean.getData().size(); i++) {

                        LogUtil.d("加载成功多少:"+groupTermianlListBean.getData().get(i).getTerm_orientation());

                        if (orientation == groupTermianlListBean.getData().get(i).getTerm_orientation()) {
                            LogUtil.d("符合条件:"+groupTermianlListBean.getData().get(i).getTerm_orientation());
                            //tempGroupTermianlListBean.getData().remove(i);
                            setTempData(groupTermianlListBean.getData().get(i));//第一次刷选
                        }
                        LogUtil.d("加载成功:"+orientation+"====长度vvvv："+groupTermianlListBean.getData().size());
                    }

                    LogUtil.d(terminal_id+":前面传过来的数据："+terminal_id.size());


                    for (int j=0;j<terminal_id.size();j++){
                        for (int i=0;i<tempGroupTermianlListBean.getData().size();i++){
                            if(terminal_id.get(j).equals(tempGroupTermianlListBean.getData().get(i).getTerminal_id())){
                                LogUtil.d("第二次帅选:");
                                tempGroupTermianlListBean.getData().remove(i);
                            }
                        }
                    }

                    LogUtil.d("刷选后的东西："+tempGroupTermianlListBean.getData().size());

                    mLiandongChooseTerminalAdapter.setmLists(tempGroupTermianlListBean.getData());
                }
            }
        });
    }

    List<GroupTermianlListBean.DataBean> dataBeanList=new ArrayList<>();
    private void setTempData(GroupTermianlListBean.DataBean data)
    {
        GroupTermianlListBean.DataBean dataBean=new GroupTermianlListBean.DataBean();

        dataBean.setIds(data.getIds());
        dataBean.setShop_id(data.getShop_id());
        dataBean.setShop_name(data.getShop_name());
        dataBean.setStatus(data.getStatus());
        dataBean.setTerm_orientation(data.getTerm_orientation());
        dataBean.setTerminal_id(data.getTerminal_id());
        dataBean.setTerminal_no(data.getTerminal_no());

        tempGroupTermianlListBean.getData().add(dataBean);

        LogUtil.d("当前复制："+tempGroupTermianlListBean.getData().size());
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }
}
