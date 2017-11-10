package com.gohoc.xiupuling.adapter.combinationadapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListBean;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.ui.combinationpackage.CombinationPackageDetailActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/*
* 作品组合包
* */
public class CombinationAdapter extends BaseAdapter {
	private List<CombinationListBean.DataBean> testItems=new ArrayList<CombinationListBean.DataBean>();
	private Context context;
	private Callback2 mCallback2;

	private String start_end(String start,String end)
	{
		String time="";
		if(!TextUtils.isEmpty(start)&&!TextUtils.isEmpty(end)){
			return start+"-"+end;
		}else {
			return "";
		}
	}

	public void setCallback(Callback2 callback)
	{
		this.mCallback2=callback;
	}

	public CombinationAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<CombinationListBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<CombinationListBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(CombinationListBean.DataBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_combination_list_layout,parent,false);

			holder.swipeMenuLayout=(SwipeMenuLayout) convertView.findViewById(R.id.swipeMenuLayout);
			holder.linear_item_layout=(LinearLayout) convertView.findViewById(R.id.linear_item_layout);
			holder.mBehindViews=(LinearLayout) convertView.findViewById(R.id.mBehindViews);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
			holder.iv_bg=(ImageView) convertView.findViewById(R.id.iv_bg);
			holder.iv_pingbi=(ImageView) convertView.findViewById(R.id.iv_pingbi);
			holder.iv_type=(ImageView) convertView.findViewById(R.id.iv_type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final CombinationListBean.DataBean hashMap=testItems.get(position);
			if(!TextUtils.isEmpty(hashMap.work_cnt+"")&&(hashMap.work_cnt+"")!="null"&&!(hashMap.work_cnt+"").contains("null")){
				holder.tv_name.setText(hashMap.package_name+"("+hashMap.work_cnt+")");
			}else {
				holder.tv_name.setText(hashMap.package_name+"(0)");
			}

			if(hashMap.orientation){//false 默认横屏
				holder.iv_type.setImageResource(R.mipmap.icon_zdshu);
			}else {
				holder.iv_type.setImageResource(R.mipmap.icon_zdheng);
			}

			if(hashMap.is_ignore_other_work){
				holder.iv_pingbi.setVisibility(View.VISIBLE);
				holder.iv_pingbi.setImageResource(R.mipmap.icon_pingbi);
			}else {
				holder.iv_pingbi.setVisibility(View.GONE);
			}

			if(!hashMap.is_time_limit&&hashMap.is_ignore_other_work){
				holder.tv_time.setText("全天候独占轮播");
			}else {
				if(!hashMap.is_time_limit&&!hashMap.is_ignore_other_work){
					holder.tv_time.setText("非独占时段轮播");
				}else {
					if(!TextUtils.isEmpty(hashMap.repeat_weekday+"")){
						holder.tv_time.setText(CustomUtils.getPlayTime(hashMap.repeat_weekday+"",hashMap.is_ignore_other_work)+" "+start_end(hashMap.start_time+"",hashMap.end_time+""));
					}else {
						holder.tv_time.setText("非独占时段轮播");
					}
				}
			}

			/*if(!hashMap.is_ignore_other_work){
				holder.tv_time.setText("非独占时段轮播");
			}else if(hashMap.is_ignore_other_work){

			}*/

			Glide.with(context)
					.load(Constant.NetConstant.BASE_USER_RESOURE + hashMap.cover_url + Utils.getThumbnail(200,200))
					.placeholder(R.mipmap.icon_zuhebao)
					.error(R.mipmap.icon_zuhebao)
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.into(holder.iv_bg);
			final ViewHolder finalHolder = holder;
			holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtil.d("点击删除");
					if(mCallback2!=null){
						finalHolder.swipeMenuLayout.smoothClose();
						mCallback2.callBack(hashMap.package_id,position+"");
					}
				}
			});

			final ViewHolder finalHolder1 = holder;
			holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context, CombinationPackageDetailActivity.class);

					/*intent.putExtra("combinationid",hashMap.package_id);
					intent.putExtra("combinationname",hashMap.package_name);
					intent.putExtra("url",hashMap.cover_url+"");
					intent.putExtra("or",hashMap.orientation);
					intent.putExtra("time", finalHolder1.tv_time.getText().toString());*/
					//intent.putExtra("time", finalHolder1.tv_time.getText().toString());
					intent.putExtra("data",hashMap);
					context.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout linear_item_layout,mBehindViews;
		private TextView tv_name,tv_time;
		private ImageView iv_bg,iv_type,iv_pingbi;
		private SwipeMenuLayout swipeMenuLayout;
	}
}