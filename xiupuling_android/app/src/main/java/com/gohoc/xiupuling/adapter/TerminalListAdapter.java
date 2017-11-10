package com.gohoc.xiupuling.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ShopBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.greenrobot.eventbus.EventBus;

import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;

/**
 * Created by 28713 on 2017/2/16.
 */

public class TerminalListAdapter extends BaseExpandableListAdapter {


    private ShopBean shopBean;
    private Activity activity;
    private LayoutInflater layoutInflater;
    private ExpandableListView expandedlistList;
    private OnExpandableListViewItemClickLitener onExpandableListViewItemClickLitener;

    public TerminalListAdapter(ShopBean shopBean, Activity activity, ExpandableListView expandedlistList) {
        this.shopBean = shopBean;
        this.activity = activity;
        this.expandedlistList = expandedlistList;
        layoutInflater = LayoutInflater.from(activity);


    }


    @Override
    public int getGroupCount() {
        return shopBean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (shopBean.getData().get(groupPosition).getTermlist().size() == 0)
            return 1;
        return shopBean.getData().get(groupPosition).getTermlist().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return shopBean.getData().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return shopBean.getData().get(groupPosition).getTermlist().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        RatingBar ratingBar;
        TextView terminal_name_tv, terminal_price;
        ImageView item_terminal_arr;

        convertView = layoutInflater.inflate(R.layout.item_terminal, null);


        terminal_name_tv = (TextView) convertView.findViewById(R.id.terminal_name_tv);
        terminal_price = (TextView) convertView.findViewById(R.id.terminal_price);
        item_terminal_arr = (ImageView) convertView.findViewById(R.id.item_terminal_arr);
        ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
        ratingBar.setEnabled(false);
        ratingBar.setmClickable(false);
        terminal_name_tv.setText(shopBean.getData().get(groupPosition).getShop_name() + "");
        terminal_price.setText("￥"+shopBean.getData().get(groupPosition).getTotalorderamt());
        if (isExpanded)
            item_terminal_arr.setBackgroundResource(R.mipmap.icon_explain_open);
        else
            item_terminal_arr.setBackgroundResource(R.mipmap.icon_explain_close);

        ratingBar.setStar(shopBean.getData().get(groupPosition).getShop_star_level());


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_terminal_sub, null);
        TextView terminal_number_id_tv, terminal_price_tv;
        ImageView terminal_battery_iv;
        LinearLayout terminal_sub_closed_lv;
        SeekBar seekBar = (SeekBar) convertView.findViewById(R.id.seekBar);
        seekBar.setEnabled(false);
        LinearLayout content_ll, mBehindViews;
        SwipeMenuLayout swipeMenuLayout;
        LinearLayout terminal_sub_del_lv;
        swipeMenuLayout = (SwipeMenuLayout) convertView.findViewById(R.id.swipeMenuLayout);
        content_ll = (LinearLayout) convertView.findViewById(R.id.content_ll);
        terminal_battery_iv = (ImageView) convertView.findViewById(R.id.terminal_battery_iv);
        terminal_number_id_tv = (TextView) convertView.findViewById(R.id.terminal_number_id_tv);
        terminal_price_tv = (TextView) convertView.findViewById(R.id.terminal_price_tv);
        terminal_sub_closed_lv = (LinearLayout) convertView.findViewById(R.id.terminal_sub_closed_lv);
       // terminal_number_id_tv.setText(String.format("%02d", childPosition + 1) + "");

        mBehindViews = (LinearLayout) convertView.findViewById(R.id.mBehindViews);
        terminal_sub_del_lv = (LinearLayout) convertView.findViewById(R.id.terminal_sub_del_lv);

        if (shopBean.getData().get(groupPosition).getTermlist().size() == 0) {
            swipeMenuLayout.setVisibility(View.GONE);
            terminal_sub_closed_lv.setVisibility(View.GONE);
            terminal_sub_del_lv.setVisibility(View.VISIBLE);
            terminal_sub_del_lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(shopBean.getData().get(groupPosition).getShop_name(), shopBean.getData().get(groupPosition).getShop_id());

                }
            });
            return convertView;
        }
        terminal_number_id_tv.setText(shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getTerminal_no());
        terminal_price_tv.setText(String.valueOf("￥"+shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getTotalamt()));

        if (shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getTerm_orientation() == 0) {
            //横屏
            if (shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getOnline() == 1)//是否在线
                terminal_number_id_tv.setBackgroundResource(R.mipmap.port_orientation_h2_off);
            else
                terminal_number_id_tv.setBackgroundResource(R.mipmap.port_orientation_h_off);
        } else {
            if (shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getOnline() == 1)
                terminal_number_id_tv.setBackgroundResource(R.mipmap.port_orientation_s2_off);
            else
                terminal_number_id_tv.setBackgroundResource(R.mipmap.port_orientation_s_off);
        }


        if (shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getFurtherorder() == 1)
            terminal_battery_iv.setBackgroundResource(R.mipmap.port_order_on);
        else
            terminal_battery_iv.setBackgroundResource(R.mipmap.port_order_off);
        if (isLastChild) {
            terminal_sub_closed_lv.setVisibility(View.VISIBLE);
            terminal_sub_closed_lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandedlistList.collapseGroup(groupPosition);
                    notifyDataSetInvalidated();
                    notifyDataSetChanged();
                }
            });
        } else {
            terminal_sub_closed_lv.setVisibility(View.GONE);
        }
        if (null != onExpandableListViewItemClickLitener) {
            content_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onExpandableListViewItemClickLitener.onItemClick(groupPosition, childPosition, isLastChild);
                }
            });
            content_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onExpandableListViewItemClickLitener.onItemLongClick(groupPosition, childPosition, isLastChild);
                    return true;
                }
            });
            mBehindViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onExpandableListViewItemClickLitener.onItemDel(groupPosition, childPosition, isLastChild);
                }
            });
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

/*    static class GroupViewHolder {
        RatingBar ratingBar;
        TextView terminal_name_tv, terminal_price;
        ImageView item_terminal_arr;
    }*/

  /*  static class ChildViewHolder {
        TextView terminal_number_id_tv, terminal_price_tv, terminal_battery_tv;
        LinearLayout terminal_sub_closed_lv;
    }*/

    public interface OnExpandableListViewItemClickLitener {
        void onItemClick(int groupPosition, int childPosition, boolean isLastChild);

        void onItemLongClick(int groupPosition, int childPosition, boolean isLastChild);

        void onItemDel(int groupPosition, int childPosition, boolean isLastChild);
    }

    public OnExpandableListViewItemClickLitener getOnExpandableListViewItemClickLitener() {
        return onExpandableListViewItemClickLitener;
    }

    public void setOnExpandableListViewItemClickLitener(OnExpandableListViewItemClickLitener onExpandableListViewItemClickLitener) {
        this.onExpandableListViewItemClickLitener = onExpandableListViewItemClickLitener;
    }


    private void deleteShop(String shop_id) {
        RxRetrofitClient.getInstance(activity).deleteShop(shop_id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(activity, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshTerminalListEvent());
                } else {
                    Utils.toast(activity, vCodeBenan.getMessage());
                }

            }
        });
    }

    public void showDialog(String name, final String id) {

        BGAAlertController alertController = new BGAAlertController(activity, "","" , BGAAlertController.AlertControllerStyle.ActionSheet);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.addAction(new BGAAlertAction("删除门店"+name, BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                deleteShop(id);
            }
        }));
        alertController.setCancelable(true);
        alertController.show();

    }
}
