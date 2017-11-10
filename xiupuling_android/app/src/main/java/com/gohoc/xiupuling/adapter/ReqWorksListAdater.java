package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ReqListBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.ui.PreviewWActivity;
import com.gohoc.xiupuling.utils.Utils;

import java.util.List;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

/**
 * Created by 28713 on 2017/2/14.
 */
public class ReqWorksListAdater extends BaseQuickAdapter<ReqListBean.DataBean.ListBean.WorklistBean, BaseViewHolder> implements Constant {

    private Context context;
    private OnWorkerClicker onItemClick;

    public ReqWorksListAdater(int layoutResId, List<ReqListBean.DataBean.ListBean.WorklistBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ReqListBean.DataBean.ListBean.WorklistBean worklistBean) {
        if (worklistBean.getCopyright_price() > 0 && worklistBean.getIscopyrightown().equals("NO")) {
            baseViewHolder.setText(R.id.money_tv, "￥" + (int) (worklistBean.getCopyright_price()));
            baseViewHolder.setVisible(R.id.money_tv, true);
            baseViewHolder.setText(R.id.select_tv, "购买");
            baseViewHolder.setBackgroundRes(R.id.select_tv, R.drawable.bule_c_buy_sp);
            baseViewHolder.setVisible(R.id.state_rl, true);
        }
        if (worklistBean.getIs_viewed() == 0)
            baseViewHolder.setVisible(R.id.new_ds_tv, true);


        baseViewHolder.setText(R.id.tv_detail_name, "联系设计师");
        baseViewHolder.setText(R.id.og_tv, worklistBean.getOrientationStr());
        baseViewHolder.setText(R.id.type_tv, worklistBean.getWork_type_str());
        baseViewHolder.setText(R.id.designer_name_tv, worklistBean.getNick_name());
        baseViewHolder.setText(R.id.update_time_v_tv, worklistBean.getCreate_time());
        ImageView iv = baseViewHolder.getView(R.id.woks_cover_iv);

        Glide.with(context)
                .load(BASE_USER_RESOURE + worklistBean.getMaterial_store_url() + Utils.getThumbnail(316, 300))
                .centerCrop()
                .placeholder(R.mipmap.df_logo)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        baseViewHolder.getView(R.id.preLL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worklistBean.setIs_viewed(1);
                notifyItemChanged(baseViewHolder.getAdapterPosition());
                context.startActivity(new Intent(context, PreviewWActivity.class).putExtra("work_id", worklistBean.getWork_id()));
            }
        });

        baseViewHolder.getView(R.id.contact_designer_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClick)
                    onItemClick.onClickConactdsListernter(v, worklistBean);
            }
        });
        baseViewHolder.getView(R.id.select_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClick)
                    onItemClick.onClickReqListenter(v, worklistBean);
            }
        });


    }


    public OnWorkerClicker getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnWorkerClicker onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnWorkerClicker {
        void onClickReqListenter(View v,ReqListBean.DataBean.ListBean.WorklistBean worklistBean);

        void onClickConactdsListernter(View v,ReqListBean.DataBean.ListBean.WorklistBean worklistBean);
    }
}

