package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.adapter.MainPagerAdapter;
import com.gohoc.xiupuling.ui.account.LoginActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstBloodActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    private MainPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    private Integer[] zihu = {R.mipmap.img_mendianhezhongdua, R.mipmap.img_bofangmokuai, R.mipmap.img_zhanzhu_zuopin, R.mipmap.img_toufang1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //清除非全屏的flag
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏的flag
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_blood);
        ButterKnife.bind(this);
        initViewpager();
    }

    private void initViewpager() {

        for (int a = 0; a < zihu.length; a++) {
            final int finalA = a;
            fragmentList.add(FirstBloodFragment.newInstance().setImages(zihu[a], a == zihu.length - 1 ? R.mipmap.button_xindangye_into : null).setOnNext(new FirstBloodFragment.OnNext() {
                @Override
                public void onNext() {
                    if (finalA == zihu.length - 1)
                    {
                        startActivity(new Intent(FirstBloodActivity.this, LoginActivity.class));
                        ACache.get(FirstBloodActivity.this).put("FirstBlood","FirstBlood");
                    }
                    else
                        viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                }
            }));
        }

        // fragmentList.add(ScanFragment.newInstance());
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewpager.setAdapter(adapter);
    }
}
