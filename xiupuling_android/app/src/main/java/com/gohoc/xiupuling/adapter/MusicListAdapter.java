package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MusicListBean;
import com.gohoc.xiupuling.callback.Callback1;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/*
* 音乐列表
* */
public class MusicListAdapter extends BaseAdapter {
	private List<MusicListBean.DataBean> testItems=new ArrayList<MusicListBean.DataBean>();
	private Context context;
	private Callback1 mCallback1;

	public void setCallback(Callback1 callback)
	{
		this.mCallback1=callback;
	}

	public MusicListAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<MusicListBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<MusicListBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(MusicListBean.DataBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_music_list_layout,parent,false);
			
			holder.linear_item_layout=(LinearLayout) convertView.findViewById(R.id.linear_item_layout);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.iv_choose=(ImageView) convertView.findViewById(R.id.iv_choose);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final MusicListBean.DataBean hashMap=testItems.get(position);
			holder.tv_name.setText(hashMap.name);
			if(hashMap.flag==0){
				//没有选中
				holder.iv_choose.setVisibility(View.INVISIBLE);
			}else {
				//选中了
				holder.iv_choose.setVisibility(View.VISIBLE);
			}
			holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback1!=null){
						mCallback1.callBack(hashMap.name+"");
					}
				}
			});
		}

		return convertView;
	}
	
	public String getTime(long time) {
		Date date = new Date(time * 1000);
		SimpleDateFormat simFormat = new SimpleDateFormat("MM-dd HH:mm");
		String dateString = simFormat.format(date);
		return dateString;
	}

	class ViewHolder {
		private LinearLayout linear_item_layout;
		private TextView tv_name;
		private ImageView iv_choose;
	}
}