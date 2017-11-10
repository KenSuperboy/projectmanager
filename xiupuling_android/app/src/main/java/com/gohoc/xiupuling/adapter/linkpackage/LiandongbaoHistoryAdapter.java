package com.gohoc.xiupuling.adapter.linkpackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.linkpackage.LinkPackageHistoryListBean;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.ui.linkpackage.LiandongbaoHistoryToufangListDetailActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/*
* 多屏联动包历史投放记录
* */
public class LiandongbaoHistoryAdapter extends BaseAdapter {
	private List<LinkPackageHistoryListBean.DataBean> testItems=new ArrayList<LinkPackageHistoryListBean.DataBean>();
	private Context context;
	private Callback2 mCallback2;

	public void setCallback(Callback2 callback)
	{
		this.mCallback2=callback;
	}

	public LiandongbaoHistoryAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<LinkPackageHistoryListBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<LinkPackageHistoryListBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(LinkPackageHistoryListBean.DataBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_liandong_history_list_layout,parent,false);

			holder.swipeMenuLayout=(SwipeMenuLayout) convertView.findViewById(R.id.swipeMenuLayout);
			holder.linear_item_layout=(LinearLayout) convertView.findViewById(R.id.linear_item_layout);
			holder.mBehindViews=(LinearLayout) convertView.findViewById(R.id.mBehindViews);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final LinkPackageHistoryListBean.DataBean hashMap=testItems.get(position);
			holder.tv_name.setText(hashMap.link_snap_name);
			/*holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtil.d("");
				}
			});*/
			final ViewHolder finalHolder = holder;
			holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtil.d("点击删除");
					if(mCallback2!=null){
						finalHolder.swipeMenuLayout.smoothClose();
						mCallback2.callBack(hashMap.link_snap_id,position+"");
					}
				}
			});

			holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context, LiandongbaoHistoryToufangListDetailActivity.class);
					intent.putExtra("link_snap_id",hashMap.link_snap_id);
					intent.putExtra("link_snap_name",hashMap.link_snap_name);
					intent.putExtra("union_name",hashMap.union_name+"");
					intent.putExtra("link_id",hashMap.link_id);
					context.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout linear_item_layout,mBehindViews;
		private TextView tv_name;
		private SwipeMenuLayout swipeMenuLayout;
	}
}