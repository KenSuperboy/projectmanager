package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.adapter.BigPicAdapter;
import com.gohoc.xiupuling.bean.PicBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PicActivity extends BasicActivity implements View.OnTouchListener {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    ImageView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private BigPicAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    private ArrayList<PicBean> picList = new ArrayList<PicBean>();
    private int curr = 0;
    private boolean isFulllScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        picList.addAll((ArrayList<PicBean>) getIntent().getExtras().get("picList"));
        curr = getIntent().getIntExtra("curr", 0);
        initViewpager();
    }


    private void initViewpager() {
        for (int a = 0; a < picList.size(); a++) {
            if (picList.get(a).getType() != 0)
                fragmentList.add(PicFragment.newInstance().setPicBean(picList.get(a)));
        }
        toolbarTitle.setText((curr + 1) + "/" + fragmentList.size());
        adapter = new BigPicAdapter(getSupportFragmentManager(), fragmentList);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curr = position;
                toolbarTitle.setText((position + 1) + "/" + fragmentList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setCurrentItem(curr);

    }


    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goback();
                break;
            case R.id.toolbar_right:
                // Utils.toast(PicActivity.this,curr+"");
                del(curr);
                break;
        }
    }

    private void del(int postion) {
        picList.remove(postion);
        fragmentList.remove(postion);
        adapter.notifyDataSetChanged();
        if (picList.size() != 5 && picList.get(picList.size() - 1).getType() != 0)
            picList.add(new PicBean());
        if (fragmentList.size() == 0)
            goback();
        toolbarTitle.setText((viewpager.getCurrentItem() + 1) + "/" + fragmentList.size());
    }

    private void goback() {
        setResult(RESULT_OK, new Intent().putExtra("picList", picList));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goback();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //当按下时处理
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            full(isFulllScreen, toolbar);
            isFulllScreen = !isFulllScreen;
        }
        return true;
    }
}
