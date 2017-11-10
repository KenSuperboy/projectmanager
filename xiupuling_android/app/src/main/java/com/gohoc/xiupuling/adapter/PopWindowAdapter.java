package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;

import java.util.List;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/6/24 0024
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class PopWindowAdapter extends BaseAdapter {

    private List<String> list;
    private LayoutInflater inflater;
    private Context mContext;

    public PopWindowAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        String s = list.get(position);
        convertView = inflater.inflate(R.layout.item_sort_item, null, false);
        TextView textView = (TextView) convertView.findViewById(R.id.sort_by_tv);
        textView.setText(s + "");
        return convertView;
    }
}
