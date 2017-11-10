package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ShopAndGroupItemBean;
import com.gohoc.xiupuling.constant.Constant;

import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class PushGroupAdater extends BaseMultiItemQuickAdapter<ShopAndGroupItemBean, BaseViewHolder> implements Constant {
    private Context context;

    public PushGroupAdater(Context context, List<ShopAndGroupItemBean> data) {
        super(data);
        this.context=context;
        addItemType(ShopAndGroupItemBean.TEXT, R.layout.item_sg_head);
        addItemType(ShopAndGroupItemBean.GROUP, R.layout.item_group_select);
        addItemType(ShopAndGroupItemBean.SHOP, R.layout.item_simple_menu_arr2);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ShopAndGroupItemBean shopAndGroupItemBean) {

        switch (shopAndGroupItemBean.getType())
        {
            case ShopAndGroupItemBean.TEXT:
                baseViewHolder.setText(R.id.title,shopAndGroupItemBean.getTitle());
                break;
            case ShopAndGroupItemBean.GROUP:
                ImageView  groupIv = (ImageView) baseViewHolder.getView(R.id.group_pic_iv);
                Glide.with(context)
                        .load(NetConstant.BASE_USER_RESOURE + shopAndGroupItemBean.getGroupDates().getUnion_portrait() + "")
                        // .placeholder(R.mipmap.icon_port_home)
                        //.error(R.mipmap.icon_port_home)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(groupIv);
                baseViewHolder.setText(R.id.group_name_tv,shopAndGroupItemBean.getGroupDates().getUnion_name() + "("+shopAndGroupItemBean.getGroupDates().getMember_cnt()+")");

                if(TextUtils.isEmpty(shopAndGroupItemBean.getGroupDates().getUnion_brief_info())||(shopAndGroupItemBean.getGroupDates().getUnion_brief_info() + "")=="null"||(shopAndGroupItemBean.getGroupDates().getUnion_brief_info() + "")==null){
                    baseViewHolder.setText(R.id.group_info_tv,"");
                }else {
                    baseViewHolder.setText(R.id.group_info_tv,shopAndGroupItemBean.getGroupDates().getUnion_brief_info() + "");
                }
                break;
            case ShopAndGroupItemBean.SHOP:
                baseViewHolder.setText(R.id.menu_title,shopAndGroupItemBean.getShopBean().getShop_name()+"("+shopAndGroupItemBean.getShopBean().getTermlist().size()+")");
                break;
        }


    }
}
