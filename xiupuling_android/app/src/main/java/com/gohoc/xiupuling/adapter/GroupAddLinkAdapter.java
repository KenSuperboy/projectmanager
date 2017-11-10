package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.CombinationAddTerminalBean;
import com.gohoc.xiupuling.callback.Callback1;

import java.util.ArrayList;
import java.util.List;

/*
* 联动群组---添加终端
* */
public class GroupAddLinkAdapter extends BaseAdapter {
	private List<CombinationAddTerminalBean.DataBean> testItems=new ArrayList<CombinationAddTerminalBean.DataBean>();
	private Context context;
	private Callback1 mCallback1;

	public void setCallback(Callback1 callback)
	{
		this.mCallback1=callback;
	}

	public GroupAddLinkAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<CombinationAddTerminalBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<CombinationAddTerminalBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(CombinationAddTerminalBean.DataBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_group_termianl_s,parent,false);
			
			holder.termianl_info_tv=(TextView)convertView.findViewById(R.id.termianl_info_tv);
			holder.termianl_og_iv=(ImageView) convertView.findViewById(R.id.termianl_og_iv);
			holder.right_iv=(ImageView) convertView.findViewById(R.id.right_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final CombinationAddTerminalBean.DataBean hashMap=testItems.get(position);
			holder.termianl_info_tv.setText(hashMap.terminal_no+"号机");
			if(hashMap.term_orientation==0){
				//横屏
				holder.termianl_og_iv.setImageResource(R.mipmap.icon_term_orientation_h);
			}else {
				//竖屏
				holder.termianl_og_iv.setImageResource(R.mipmap.icon_group_add_port);
			}

			if(hashMap.flag==0){
				//没有选中
				holder.right_iv.setVisibility(View.INVISIBLE);
			}else {
				//选中了
				holder.right_iv.setVisibility(View.VISIBLE);
			}

			/*holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback1!=null){
						mCallback1.callBack(hashMap.name+"");
					}
				}
			});*/
		}

		return convertView;
	}

	class ViewHolder {
		private TextView termianl_info_tv;
		private ImageView termianl_og_iv,right_iv;
	}
}