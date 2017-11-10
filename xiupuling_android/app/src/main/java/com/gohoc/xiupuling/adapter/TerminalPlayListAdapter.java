package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PlayerList;

/**
 * Created by 28713 on 2017/2/16.
 */

public class TerminalPlayListAdapter extends BaseExpandableListAdapter {


    private PlayerList playerList;
    private Context context;
    private LayoutInflater layoutInflater;
    private ExpandableListView expandedlistList;

    public TerminalPlayListAdapter(PlayerList playerList, Context context, ExpandableListView expandedlistList) {
        this.playerList = playerList;
        this.context = context;
        this.expandedlistList = expandedlistList;
        layoutInflater = LayoutInflater.from(context);


    }


    @Override
    public int getGroupCount() {
        return playerList.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return playerList.getData().get(groupPosition).getPlaylist().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return playerList.getData().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return playerList.getData().get(groupPosition).getPlaylist().get(childPosition);
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

        TextView title_tv;
        convertView = layoutInflater.inflate(R.layout.item_tplay_list, null);
        title_tv = (TextView) convertView.findViewById(R.id.title_tv);
        title_tv.setText(playerList.getData().get(groupPosition).getModule_name()+"");
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_tplay_list_sub, null);
        TextView name_tv, time_tv;
        name_tv=(TextView) convertView.findViewById(R.id.name_tv);
        time_tv=(TextView) convertView.findViewById(R.id.time_tv);
        name_tv.setText(playerList.getData().get(groupPosition).getPlaylist().get(childPosition).getName()+"");
        time_tv.setText(playerList.getData().get(groupPosition).getPlaylist().get(childPosition).getPlaytime()+"s");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
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




}
