package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MoudlesBean;
import com.gohoc.xiupuling.bean.MoudlesBean;
import com.gohoc.xiupuling.bean.MoudlesLeve0Bean;
import com.gohoc.xiupuling.bean.MoudlesLeve1Bean;
import com.gohoc.xiupuling.bean.MoudlesLeve2Bean;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.suke.widget.SwitchButton;

import java.util.List;

/**
 * Created by 28713 on 2017/4/19.
 */

public class ModuleListAdater extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    private Context context;
    private OnCheckChangeListenter onCheckChangeListenter;

    public ModuleListAdater(List<MultiItemEntity> data) {
        super(data);
        addItemType(MoudlesBean.TITLE_TEXT, R.layout.item_simpe_title);
        addItemType(MoudlesBean.MODULE, R.layout.item_terminal_module);
        addItemType(MoudlesBean.MODULE_SUB, R.layout.item_terminal_module_sub);
    }

    public OnCheckChangeListenter getOnCheckChangeListenter() {
        return onCheckChangeListenter;
    }

    public void setOnCheckChangeListenter(OnCheckChangeListenter onCheckChangeListenter) {
        this.onCheckChangeListenter = onCheckChangeListenter;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
        // Logger.e("update....");

        switch (baseViewHolder.getItemViewType()) {
            case MoudlesBean.TITLE_TEXT:
                MoudlesLeve0Bean lv0 = (MoudlesLeve0Bean) multiItemEntity;
                if (lv0.getTitle() == null)
                    baseViewHolder.setVisible(R.id.title_tv, false);
                else
                    baseViewHolder.setText(R.id.title_tv, lv0.getTitle());
                break;
            case MoudlesBean.MODULE:

                final MoudlesLeve1Bean lv1 = (MoudlesLeve1Bean) multiItemEntity;
                baseViewHolder.setText(R.id.name_tv, lv1.getLeve2datas().getName())
                        .setImageResource(R.id.arr_iv, lv1.isExpanded() ? R.mipmap.icon_explain_open : R.mipmap.icon_explain_close)
                        .setText(R.id.count_tv, lv1.getOpenCount() + "/" + (lv1 == null || lv1.getSubItems() == null ? "0" : lv1.getSubItems().size() + ""));
                ;
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = baseViewHolder.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos,true,true);
                        } else {
                            expand(pos,true,true);
                        }
                    }
                });

                break;
            case MoudlesBean.MODULE_SUB:
                Logger.e("MoudlesBean.MODULE_SUB");
                final MoudlesLeve2Bean lv2 = (MoudlesLeve2Bean) multiItemEntity;
                baseViewHolder.setText(R.id.sub_name_tv, lv2.getModuleListBean().getModule_name())
                        .setText(R.id.sub_size_tv, Utils.getPrintSize(lv2.getModuleListBean().getModule_size()));


                final ToggleButton sb = baseViewHolder.getView(R.id.switch_button);
                sb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCheckChangeListenter != null)
                            onCheckChangeListenter.onChange(sb, lv2.getModuleListBean(), getParentPosition(lv2));
                       //
                    }
                });

                sb.setChecked(lv2.isCheck());
                break;

        }

    }

    public interface OnCheckChangeListenter {
        void onChange(ToggleButton sb, MoudlesBean.DataBean.ModuleListBean moduleListBean, int parentPostion);
    }

}
