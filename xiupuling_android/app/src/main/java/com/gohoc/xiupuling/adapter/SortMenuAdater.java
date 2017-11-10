package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.KvBean;

import java.util.ArrayList;

/**
 * Created by 28713 on 2017/3/20.
 */

public class SortMenuAdater extends BaseAdapter {
    private Context context;
    private ArrayList<KvBean> menusList;
    private LayoutInflater layout;

    public SortMenuAdater(Context context, ArrayList<KvBean> menusList) {
        this.context = context;
        this.menusList = menusList;
        this.layout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
       return menusList.size();
    }

    @Override
    public Object getItem(int position) {
        return menusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layout.inflate(R.layout.item_sort_menu, null);
        TextView t1 = (TextView) convertView.findViewById(R.id.sort_by_tv);
        t1.setText(menusList.get(position).getV());
        if(menusList.get(position).isCheck())
            t1.setBackgroundResource(R.color.bgc);
        else
            t1.setBackgroundResource(R.color.withe);
        return convertView;
    }
}
