package com.gohoc.xiupuling.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.ui.account.AccountEditPortraitActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link PicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PicFragment extends BaseFragment {
    @BindView(R.id.photoview)
    PhotoView photoview;
    Unbinder unbinder;
    private PicBean picBean;
    private View viewContainer;
    private PhotoViewAttacher mAttacher;
    public PicFragment() {
        // Required empty public constructor
    }


    public static PicFragment newInstance() {
        PicFragment fragment = new PicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewContainer = inflater.inflate(R.layout.fragment_pic, container, false);
        unbinder = ButterKnife.bind(this, viewContainer);

        String url=picBean.getLocUrl();
        if(url==null)
            url=BASE_USER_RESOURE + picBean.getUrl();

        Glide.with(this)
                .load(url)
                //.placeholder(R.mipmap.icon_usercenter_morentouxiang)
               // .error(R.mipmap.icon_usercenter_morentouxiang)
                .listener(new RequestListener() {

                    @Override
                    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                        Logger.e(e.toString());
                        Utils.toast(getActivity(), "加载失败，请检查网络后重试");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //在这里添加一些图片加载完成的操作
                        mAttacher = new PhotoViewAttacher(photoview);
                        return false;
                    }
                })
                .into(photoview);


        return viewContainer;
    }


    public PicBean getPicBean() {
        return picBean;
    }

    public PicFragment setPicBean(PicBean picBean) {
        this.picBean = picBean;
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
