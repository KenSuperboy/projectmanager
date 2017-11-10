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
import com.gohoc.xiupuling.bean.VipListBean;
import com.gohoc.xiupuling.callback.Callback2;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/*
* 音乐列表
* */
public class VipBindAdapter extends BaseAdapter {
	private List<VipListBean.DataBean> testItems=new ArrayList<VipListBean.DataBean>();
	private Context context;
	private Callback2 mCallback1;

	public void setCallback(Callback2 callback)
	{
		this.mCallback1=callback;
	}

	public VipBindAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<VipListBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<VipListBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(VipListBean.DataBean hashMap){
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
			final VipListBean.DataBean hashMap=testItems.get(position);
			holder.tv_name.setText(hashMap.vip_code);
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
						mCallback1.callBack(position+"",hashMap.vip_code+"");
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