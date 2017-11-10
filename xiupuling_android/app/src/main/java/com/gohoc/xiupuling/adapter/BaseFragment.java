package com.gohoc.xiupuling.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;


/**
 * Created by sky on 2015/11/14.
 * 封装了 类似onActivityResult 的方法 ，用于fragment之间传值
 */
public class BaseFragment extends Fragment {
    private OnFragmentResult onFragmentResult;
    private int requestCode;
    private int resultCode;
    private Intent data;
    private String title ="";


    public interface OnFragmentResult {
        public void onFragmentResult(int requestCode, int resultCode, Intent data);
    }


    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        this.onFragmentResult = new OnFragmentResult() {
            @Override
            public void onFragmentResult(int requestCode, int resultCode, Intent data) {

            }
        };
    }


    public void setFragmentResult(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setFragmentResult(int resultCode, Intent data) {

        setFragmentResult(resultCode);
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
