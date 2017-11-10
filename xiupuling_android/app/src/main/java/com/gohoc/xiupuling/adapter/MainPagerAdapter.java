package com.gohoc.xiupuling.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by sky on 2016/2/2.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;
    public FragmentManager fm;
    public MainPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.fragmentList = fragmentList;
    }



    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getTitle();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        //Log.i("sssssssssssss", "getItem");
        fragment = fragmentList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id", "" + position);
        fragment.setArguments(bundle);
        return fragment;

        //return fragmentList.get(position);
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        Fragment fragment = fragmentList.get(position);
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

}
