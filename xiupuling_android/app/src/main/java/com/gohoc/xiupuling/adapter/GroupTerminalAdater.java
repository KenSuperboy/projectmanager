package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.Utils;
import com.suke.widget.SwitchButton;

import rx.Observer;

/**
 * Created by 28713 on 2017/2/14.
 */

public class GroupTerminalAdater extends RecyclerView.Adapter<GroupTerminalAdater.MyViewHolder> {

    private Context context;
    private GroupTermianlListBean groupTermianlListBean;
    private OnItemClickLitener onItemClickLitener, OnDel;
    private int type = 0;

    public GroupTerminalAdater(Context context, GroupTermianlListBean groupTermianlListBean, int type) {
        this.context = context;
        this.groupTermianlListBean = groupTermianlListBean;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_group_setting, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑  禁用掉侧滑菜单
        //((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(position % 2 == 0 ? true : false).setSwipeEnable(false);

        if (type == 1) {
            // ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(false).setSwipeEnable(false);
            holder.switchButton.setVisibility(View.GONE);
        }

        holder.termianlInfoTv.setText(groupTermianlListBean.getData().get(position).getTerminal_no() + "号机  " + groupTermianlListBean.getData().get(position).getShop_name());
        if (groupTermianlListBean.getData().get(position).getTerm_orientation() == 0)
            holder.og.setImageResource(R.mipmap.icon_term_orientation_h);
        else
            holder.og.setImageResource(R.mipmap.icon_group_add_port);

        if (groupTermianlListBean.getData().get(position).getStatus() == 1)
            holder.switchButton.setChecked(true);
        else
            holder.switchButton.setChecked(false);

        if (null != onItemClickLitener) {
            holder.content_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(v, position);
                }
            });
            holder.content_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickLitener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
        if (type == 2||type==4){
            holder.switchButton.setVisibility(View.GONE);
            holder.iv_right.setVisibility(View.VISIBLE);
        }else{
            holder.switchButton.setVisibility(View.VISIBLE);
            holder.iv_right.setVisibility(View.GONE);
        }

        holder.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //  if(isChecked)
            }
        });

        if (null != OnDel) {
            holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnDel.onItemClick(v, position);
                }
            });
        }

        holder.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //Toast.makeText(context, position+"::"+isChecked, Toast.LENGTH_SHORT).show();
                if (isChecked)
                    autoReceivingOrderSwitch(groupTermianlListBean.getData().get(position).getIds(), "on");
                else
                    autoReceivingOrderSwitch(groupTermianlListBean.getData().get(position).getIds(), "off");
            }
        });

    }

    @Override
    public int getItemCount() {
        if (groupTermianlListBean == null)
            return 0;
        return groupTermianlListBean.getData().size();
    }


    public void rf(GroupTermianlListBean groupTermianlListBean) {
        this.groupTermianlListBean = groupTermianlListBean;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout content_ll;
        ImageView og,iv_right;
        TextView termianlInfoTv;
        SwitchButton switchButton;
        LinearLayout mBehindViews;


        public MyViewHolder(View view) {
            super(view);
            termianlInfoTv = (TextView) view.findViewById(R.id.termianl_info_tv);
            og = (ImageView) view.findViewById(R.id.termianl_og_iv);
            iv_right = (ImageView) view.findViewById(R.id.iv_right);
            switchButton = (SwitchButton) view.findViewById(R.id.switch_button);
            content_ll = (LinearLayout) view.findViewById(R.id.content_ll);
            mBehindViews = (LinearLayout) view.findViewById(R.id.mBehindViews);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }


    public OnItemClickLitener getOnDel() {
        return OnDel;
    }

    public void setOnDel(OnItemClickLitener onDel) {
        OnDel = onDel;
    }


    private void autoReceivingOrderSwitch(String ids, String switcher) {
        RxRetrofitClient.getInstance(context).autoReceivingOrderSwitch(ids, switcher, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(context, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {

                if (vCodeBenan.getCode() == 1) {

                } else {
                    Toast.makeText(context, vCodeBenan.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
