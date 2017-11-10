package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.LocalMusicBean;
import com.gohoc.xiupuling.callback.Callback1;
import com.gohoc.xiupuling.utils.CustomUtils;

import java.util.ArrayList;
import java.util.List;

/*
* 本地音乐列表
* */
public class LocalMusicListAdapter extends BaseAdapter {
	private List<LocalMusicBean> testItems=new ArrayList<LocalMusicBean>();
	private Context context;
	private Callback1 mCallback1;

	public void setCallback(Callback1 callback)
	{
		this.mCallback1=callback;
	}

	public LocalMusicListAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<LocalMusicBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<LocalMusicBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(LocalMusicBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_local_music_list_layout,parent,false);

			holder.linear_item_layout=(LinearLayout) convertView.findViewById(R.id.linear_item_layout);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final LocalMusicBean hashMap=testItems.get(position);
			holder.tv_name.setText(hashMap.getName());
			holder.tv_time.setText(oneNumber(hashMap.getSize())+"    "+ CustomUtils.getMytime(Long.parseLong(hashMap.getDuration()+""))+"");
			holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback1!=null){
						mCallback1.callBack(position+"");
					}
				}
			});
		}
		return convertView;
	}

	private String oneNumber(long mysize)
	{
		if(TextUtils.isEmpty(mysize+"")){
			return "";
		}
		long number;
		number=mysize/1024/1024;

		java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.0");
		String str = myformat.format(number);
		return str+"M";
	}

	class ViewHolder {
		private LinearLayout linear_item_layout;
		private TextView tv_name,tv_time;
	}
}