package com.gohoc.xiupuling.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link FirstBloodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstBloodFragment extends BaseFragment {

    @BindView(R.id.iv)
    ImageView iv;
    Unbinder unbinder;
    @BindView(R.id.bt_iv)
    ImageView btIv;
    private View viewContainer;
    private OnNext onNext;
     private Integer rsId, btRsId;

    public FirstBloodFragment() {
        // Required empty public constructor
    }


    public static FirstBloodFragment newInstance() {
        FirstBloodFragment fragment = new FirstBloodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View getViewContainer() {
        return viewContainer;
    }

    public void setViewContainer(View viewContainer) {
        this.viewContainer = viewContainer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewContainer = inflater.inflate(R.layout.fragment_blood, container, false);
        unbinder = ButterKnife.bind(this, viewContainer);
        if(iv!=null&&rsId!=null){
            iv.setImageResource(rsId);
        }
        if (btRsId != null)
            btIv.setImageResource(btRsId);
        return viewContainer;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick(R.id.bt_iv)
    public void onViewClicked() {
        if (onNext != null) {
            onNext.onNext();
        }
    }

    public FirstBloodFragment setImages(Integer rsId, Integer btRsId) {
        this.rsId=rsId;
        this.btRsId=btRsId;
        return this;
    }

    public OnNext getOnNext() {
        return onNext;
    }

    public FirstBloodFragment setOnNext(OnNext onNext) {
        this.onNext = onNext;
        return this;
    }

    public interface OnNext {
        void onNext();
    }
}
