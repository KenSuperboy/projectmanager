package com.gohoc.xiupuling.adapter.linkpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.callback.Callback2;

import java.util.ArrayList;
import java.util.List;

/*
* 联动包----新建联动关系---选择终端
* */
public class LiandongChooseTerminalAdapter extends BaseAdapter {
	private List<GroupTermianlListBean.DataBean> testItems=new ArrayList<GroupTermianlListBean.DataBean>();
	private Context context;
	private Callback2 mCallback2;

	public void setCallback(Callback2 callback)
	{
		this.mCallback2=callback;
	}

	public LiandongChooseTerminalAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<GroupTermianlListBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<GroupTermianlListBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(GroupTermianlListBean.DataBean hashMap){
		//this.testItems.add(0, hashMap);
		this.testItems.add(hashMap);
		notifyDataSetChanged();
	}
	
	public void remove(int position)
	{
		testItems.remove(position);
		notifyDataSetChanged();
	}
	
	public void clear() {
		testItems.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (testItems == null || testItems.size() <= 0)
			return 0;
		return testItems.size();
	}

	@Override
	public Object getItem(int position) {
		return testItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_layout,parent,false);

			holder.tv_number=(TextView)convertView.findViewById(R.id.tv_number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final GroupTermianlListBean.DataBean hashMap=testItems.get(position);
			holder.tv_number.setText(hashMap.getTerminal_no());
			if(hashMap.isCheck()){
				holder.tv_number.setBackgroundResource(R.drawable.item_gridview_blue_round);
				holder.tv_number.setTextColor(context.getResources().getColor(R.color.white));
			}else {
				holder.tv_number.setBackgroundResource(R.drawable.item_gridview_white_round);
				holder.tv_number.setTextColor(context.getResources().getColor(R.color.tv_3));
			}

		}


		return convertView;
	}

	class ViewHolder {
		private TextView tv_number;
	}
}