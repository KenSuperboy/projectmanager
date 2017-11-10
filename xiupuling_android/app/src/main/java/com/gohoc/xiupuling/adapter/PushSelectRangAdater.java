package com.gohoc.xiupuling.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.LocationBean;

import java.util.List;

/**
 * Created by 28713 on 2017/4/22.
 */

public class PushSelectRangAdater extends BaseQuickAdapter<LocationBean.DataBean, BaseViewHolder> {

    public PushSelectRangAdater(int layoutResId, List<LocationBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LocationBean.DataBean dataBean) {
        baseViewHolder.setText(R.id.menu_title,dataBean.getName());
        if(dataBean.isCheck())
            baseViewHolder.setVisible(R.id.right_iv,true);
        else
            baseViewHolder.setVisible(R.id.right_iv,false);
    }
}
