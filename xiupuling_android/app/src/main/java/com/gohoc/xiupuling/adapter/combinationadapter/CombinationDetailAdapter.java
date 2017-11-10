package com.gohoc.xiupuling.adapter.combinationadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListDetailBean;
import com.gohoc.xiupuling.callback.Callback3;
import com.gohoc.xiupuling.ui.PreviewWActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/*
* 作品组合包详情列表
* */
public class CombinationDetailAdapter extends BaseAdapter {
	private List<CombinationListDetailBean.DataBean> testItems=new ArrayList<CombinationListDetailBean.DataBean>();
	private Context context;
	private Callback3 mCallback3;

	public void setCallback(Callback3 callback)
	{
		this.mCallback3=callback;
	}

	public CombinationDetailAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<CombinationListDetailBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}

	public List<CombinationListDetailBean.DataBean> getTestItems()
	{
		return testItems;
	}
	
	public void addItems(List<CombinationListDetailBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(CombinationListDetailBean.DataBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_liandong_list_detail_layout,parent,false);

			holder.mBehindViews=(LinearLayout) convertView.findViewById(R.id.mBehindViews);
			holder.swipeMenuLayout=(SwipeMenuLayout) convertView.findViewById(R.id.swipeMenuLayout);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_length=(TextView)convertView.findViewById(R.id.tv_length);
			holder.tv_number=(TextView)convertView.findViewById(R.id.tv_number);
			holder.iv_flag=(ImageView) convertView.findViewById(R.id.iv_flag);
			holder.iv_play=(ImageView) convertView.findViewById(R.id.iv_play);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final CombinationListDetailBean.DataBean hashMap=testItems.get(position);
			holder.tv_name.setText(hashMap.work_name);
			holder.iv_flag.setVisibility(View.GONE);
			holder.tv_number.setVisibility(View.VISIBLE);
			holder.tv_number.setText((position+1)+"");
			holder.tv_length.setText(CustomUtils.getWorkType(hashMap.work_type)+"     "+hashMap.playtime+"s");
			final ViewHolder finalHolder = holder;
			holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtil.d("点击删除");//deleteworklinkdetail
					if(mCallback3!=null){
						finalHolder.swipeMenuLayout.smoothClose();
						mCallback3.callBack(hashMap.package_id,hashMap.detail_id,position+"");
					}
				}
			});

			holder.iv_play.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context, PreviewWActivity.class);
					intent.putExtra("work_id",hashMap.work_id);
					context.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout mBehindViews;
		private TextView tv_name,tv_length,tv_number;
		private ImageView iv_flag,iv_play;
		private SwipeMenuLayout swipeMenuLayout;
	}
}