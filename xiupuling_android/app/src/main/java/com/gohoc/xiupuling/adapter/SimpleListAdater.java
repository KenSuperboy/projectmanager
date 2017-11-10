package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.SecurityQsBean;

import java.util.List;

/**
 * Created by 28713 on 2017/2/22.
 */

public class SimpleListAdater extends BaseAdapter {
    private Context context;
    private LayoutInflater layout;
    List<SecurityQsBean.DataBean> list;
    private ViewHolder viewHolder;

    public SimpleListAdater(Context context, List<SecurityQsBean.DataBean> list) {
        this.context = context;
        this.list = list;
        this.layout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_simple_menu, null);
        }

        viewHolder.text1 = (TextView) convertView.findViewById(R.id.menu_title);
        viewHolder.text1.setText(list.get(position).getDictcaption());

        return convertView;
    }

    private class ViewHolder {
        private TextView text1;
    }
}
