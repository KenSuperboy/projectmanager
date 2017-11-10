package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BankBean;
import com.gohoc.xiupuling.bean.MyBankBean;
import com.gohoc.xiupuling.constant.Constant;

import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class MyBankListAdater extends BaseQuickAdapter<MyBankBean.DataBean, BaseViewHolder> {

    private Context context;


    public MyBankListAdater(Context context, int layoutResId, List<MyBankBean.DataBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MyBankBean.DataBean dataBean) {
        //baseViewHolder.setAdapter(R.id.)
        ImageView ic = baseViewHolder.getView(R.id.cover_iv);
        Glide.with(context)
                .load(Constant.NetConstant.BASE_USER_RESOURE + dataBean.getBank_icon())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ic);

        baseViewHolder.setText(R.id.name_tv, dataBean.getBankname());
        String str = dataBean.getBankcardno();
        baseViewHolder.setText(R.id.ps_tv, "尾数是" + str.substring(str.length() - 4, str.length()));
        baseViewHolder.setVisible(R.id.right_iv, dataBean.isSelect());

        //cover_iv
    }


}
