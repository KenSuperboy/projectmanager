package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ModuleAllListBean;
import com.gohoc.xiupuling.bean.ModuleBean;

/**
 * Created by 28713 on 2017/2/16.
 */

public class SystemMouduleListAdapter extends BaseExpandableListAdapter {


    private ModuleAllListBean moduleAllListBean;
    private Context context;
    private LayoutInflater layoutInflater;
    private ExpandableListView expandedlistList;

    public SystemMouduleListAdapter(ModuleBean moduleBean, Context context, ModuleAllListBean moduleAllListBean) {
        this.moduleAllListBean = moduleAllListBean;
        this.context = context;
        this.expandedlistList = expandedlistList;
        layoutInflater = LayoutInflater.from(context);


    }


    @Override
    public int getGroupCount() {
        return moduleAllListBean.getData().getModule_list().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return moduleAllListBean.getData().getModule_list().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return moduleAllListBean.getData().getModule_list().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return moduleAllListBean.getData().getModule_list().get(groupPosition);
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

/*        RatingBar ratingBar;
        TextView terminal_name_tv, terminal_price;
        ImageView item_terminal_arr;*/

        convertView = layoutInflater.inflate(R.layout.item_terminal_module, null);


/*        terminal_name_tv = (TextView) convertView.findViewById(R.id.terminal_name_tv);
        terminal_price = (TextView) convertView.findViewById(R.id.terminal_price);
        item_terminal_arr = (ImageView) convertView.findViewById(R.id.item_terminal_arr);
        ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);

        terminal_name_tv.setText(shopBean.getData().get(groupPosition).getShop_name() + "");
        if (isExpanded)
            item_terminal_arr.setBackgroundResource(R.mipmap.icon_arrow_up);
        else
            item_terminal_arr.setBackgroundResource(R.mipmap.icon_arrow_down);

        ratingBar.setStar(shopBean.getData().get(groupPosition).getShop_star_level());*/


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.item_terminal_module_sub, null);
  /*      TextView terminal_number_id_tv, terminal_price_tv, terminal_battery_tv;
        LinearLayout terminal_sub_closed_lv;
        SeekBar seekBar = (SeekBar) convertView.findViewById(R.id.seekBar);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        terminal_battery_tv = (TextView) convertView.findViewById(R.id.terminal_battery_tv);
        terminal_number_id_tv = (TextView) convertView.findViewById(R.id.terminal_number_id_tv);
        terminal_price_tv = (TextView) convertView.findViewById(R.id.terminal_price_tv);
        terminal_sub_closed_lv = (LinearLayout) convertView.findViewById(R.id.terminal_sub_closed_lv);
        terminal_number_id_tv.setText(String.format("%02d", childPosition + 1) + "");

        if (shopBean.getData().get(groupPosition).getTermlist().get(childPosition).getOnline() == 1)
            terminal_battery_tv.setBackgroundResource(R.mipmap.port_order_on);
        else
            terminal_battery_tv.setBackgroundResource(R.mipmap.port_order_off);
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
        }*/

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


}
