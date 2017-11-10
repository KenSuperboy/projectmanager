package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MoudlesBean;
import com.gohoc.xiupuling.bean.MoudlesLeve0Bean;
import com.gohoc.xiupuling.bean.TerminalSelectBean;
import com.gohoc.xiupuling.bean.TerminalSelectLeve0Bean;
import com.gohoc.xiupuling.bean.TerminalSelectLeve1Bean;
import com.gohoc.xiupuling.utils.Utils;

import java.util.List;

import rx.Observable;

/**
 * Created by 28713 on 2017/4/21.
 */

public class TermianlSelectAdater extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private Context context;
    private OnItemChange onItemChange;



    public TermianlSelectAdater(Context context, List<MultiItemEntity> data) {
        super(data);
        this.context = context;

        addItemType(TerminalSelectBean.TITLE_TEXT, R.layout.item_sg_head);
        addItemType(TerminalSelectBean.GROUP, R.layout.item_terminal_module1);
        addItemType(TerminalSelectBean.CHECK, R.layout.item_simple_clickbox2);
        addItemType(TerminalSelectBean.GROUP_MENU, R.layout.item_select_flage);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
        switch (baseViewHolder.getItemViewType()) {
            case TerminalSelectBean.TITLE_TEXT:
                MoudlesLeve0Bean m = (MoudlesLeve0Bean) multiItemEntity;
                baseViewHolder.setText(R.id.title, m.getTitle());
                break;
            case TerminalSelectBean.GROUP:
                final TerminalSelectLeve0Bean terminalSelectLeve0Bean= (TerminalSelectLeve0Bean) multiItemEntity;
                if(terminalSelectLeve0Bean==null || terminalSelectLeve0Bean.getSubItems()==null)
                {
                    baseViewHolder.getConvertView().setVisibility(View.GONE);
                    break;
                }
                baseViewHolder.setText(R.id.name_tv, "已选")
                .setText(R.id.count_tv, "0/"+(terminalSelectLeve0Bean==null||terminalSelectLeve0Bean.getSubItems()==null?0:terminalSelectLeve0Bean.getSubItems().size()))
                .setText(R.id.state_tv, terminalSelectLeve0Bean.isExpanded()?"收起":"展开")
                .setImageResource(R.id.arr_iv, terminalSelectLeve0Bean.isExpanded() ? R.mipmap.icon_arrow_up : R.mipmap.icon_arrow_down);
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = baseViewHolder.getAdapterPosition();
                        if (terminalSelectLeve0Bean.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });

                break;
            case TerminalSelectBean.GROUP_MENU:
                final TerminalSelectLeve1Bean terminalSelectBean1 = (TerminalSelectLeve1Bean) multiItemEntity;
                if (terminalSelectBean1.getGdata() != null) {
                    baseViewHolder.setText(R.id.title_tv, terminalSelectBean1.getSortNo() + " " + terminalSelectBean1.getGdata().getShop_name());
                } else {
                    baseViewHolder.setText(R.id.title_tv, terminalSelectBean1.getSortNo() + " " + terminalSelectBean1.getShopName());
                }
                baseViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Utils.toast(context,""+baseViewHolder.getAdapterPosition());
                        if(null!=onItemChange){
                            onItemChange.onDel( baseViewHolder.getAdapterPosition(),terminalSelectBean1);
                        }
                    }

                });
                break;
            case TerminalSelectBean.CHECK:
                final TerminalSelectBean terminalSelectBean = (TerminalSelectBean) multiItemEntity;
                if (terminalSelectBean.getGdataBean() != null) {
                    baseViewHolder.setText(R.id.menu_title, terminalSelectBean.getSortNo() + " " + terminalSelectBean.getGdataBean().getShop_name());
                } else {
                    baseViewHolder.setText(R.id.menu_title, terminalSelectBean.getSortNo() + " " + terminalSelectBean.getShopName());
                }
                baseViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Utils.toast(context,""+baseViewHolder.getAdapterPosition());
                        if(null!=onItemChange){
                            onItemChange.onAdd( baseViewHolder.getAdapterPosition(),terminalSelectBean);
                        }
                    }

                });
                break;
        }

    }

    public OnItemChange getOnItemChange() {
        return onItemChange;
    }

    public void setOnItemChange(OnItemChange onItemChange) {
        this.onItemChange = onItemChange;
    }

    public interface OnItemChange
    {
        void onAdd(int postion , Object obj);
        void onDel(int postion , Object obj);
    }
}
