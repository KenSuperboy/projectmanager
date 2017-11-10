package com.gohoc.xiupuling.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.KvBean;
import com.gohoc.xiupuling.bean.TerminalNoBean;
import com.gohoc.xiupuling.bean.VCodeBenan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 28713 on 2017/4/17.
 */

public class WeekAdater extends BaseQuickAdapter<KvBean, BaseViewHolder> {
    public WeekAdater(int layoutResId, ArrayList<KvBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, KvBean kvBean) {
        baseViewHolder.setText(R.id.menu_title,kvBean.getK());
        baseViewHolder.setVisible(R.id.right_iv,kvBean.isCheck());
    }
}
