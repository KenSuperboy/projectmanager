package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.UserBean;

import java.util.ArrayList;

/**
 * Created by sherlock-sky on 2017/2/10.
 */

public class LoginListAdapter extends BaseAdapter {
    private LayoutInflater layout;
    private Context context;
    private ArrayList<UserBean> userBeenList;

    public LoginListAdapter(ArrayList<UserBean> userBeenList, Context context) {
        this.layout = LayoutInflater.from(context);
        this.userBeenList = userBeenList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return userBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    convertView = layout.inflate(R.layout.item_user_list, null);
        TextView t1 = (TextView) convertView.findViewById(R.id.item_text);
        t1.setText(userBeenList.get(position).getData().getMobile());
        return convertView;
    }
}
