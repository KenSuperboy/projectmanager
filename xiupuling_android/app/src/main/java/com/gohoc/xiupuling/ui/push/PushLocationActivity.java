package com.gohoc.xiupuling.ui.push;

import android.os.Bundle;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.adapter.MainPagerAdapter;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.CustomViewPager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushLocationActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;
    @BindView(R.id.main_viewpager)
    CustomViewPager mainViewpager;
    private MainPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    private String[] mTitles = {"列表显示", "地图显示"};
    private String rq_id = "";
    private String work_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_location);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("投放地点");
        work_id = getIntent().getStringExtra("work_id");
        rq_id = getIntent().getStringExtra("rq_id");

        Logger.e("PushLocationActivity::" + work_id);
        initViewPage();
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    private void initViewPage() {
        fragmentList.add(PushLcListFragment.newInstance().setRq_id(rq_id).setWork_id(work_id));
        fragmentList.add(PushLcMapFragment.newInstance().setRq_id(rq_id).setWork_id(work_id));
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainViewpager.setAdapter(adapter);
        tl1.setTabData(mTitles);
        tl1.setCurrentTab(1);
        mainViewpager.setCurrentItem(1);
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mainViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
}
