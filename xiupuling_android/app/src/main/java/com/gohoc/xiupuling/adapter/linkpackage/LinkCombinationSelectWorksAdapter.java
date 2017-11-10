package com.gohoc.xiupuling.adapter.linkpackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MyWorksBean;
import com.gohoc.xiupuling.callback.Callback1;
import com.gohoc.xiupuling.ui.PreviewWActivity;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

/*
* 联动包和组合包添加作品
* */
public class LinkCombinationSelectWorksAdapter extends BaseAdapter {
	private List<MyWorksBean.DataBean> testItems=new ArrayList<MyWorksBean.DataBean>();
	private Context context;
	private Callback1 mCallback1;

	public void setCallback(Callback1 callback)
	{
		this.mCallback1=callback;
	}

	public LinkCombinationSelectWorksAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<MyWorksBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<MyWorksBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(MyWorksBean.DataBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_works,parent,false);

			holder.preLL=(RelativeLayout) convertView.findViewById(R.id.preLL);
			holder.linear_item_layout=(LinearLayout) convertView.findViewById(R.id.linear_item_layout);

			holder.og_tv=(TextView)convertView.findViewById(R.id.og_tv);
			holder.type_tv=(TextView)convertView.findViewById(R.id.type_tv);
			holder.designer_name_tv=(TextView)convertView.findViewById(R.id.designer_name_tv);
			holder.update_time_v_tv=(TextView)convertView.findViewById(R.id.update_time_v_tv);

			holder.woks_cover_iv=(ImageView) convertView.findViewById(R.id.woks_cover_iv);
			holder.iv_select=(ImageView)convertView.findViewById(R.id.iv_select);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final MyWorksBean.DataBean hashMap=testItems.get(position);

			holder.og_tv.setText(hashMap.getOrientation() == 0 ? "横屏" : "竖屏");
			holder.type_tv.setText(hashMap.getRq_orientation_str() + "");
			holder.designer_name_tv.setText(hashMap.getActivity_title()+ "");
			holder.update_time_v_tv.setText(hashMap.getCreate_time() + "");

			Glide.with(context)
					.load(BASE_USER_RESOURE + hashMap.getMaterial_store_url() + Utils.getThumbnail(300, 300))
					.centerCrop()
					.placeholder(R.mipmap.df_logo)
					//.error(R.mipmap.icon_dengru_touxiang)
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.into(holder.woks_cover_iv);

			if(hashMap.getFlag()==0){
				holder.iv_select.setImageResource(R.mipmap.icon_zhuce_butongyi);
			}else {
				holder.iv_select.setImageResource(R.mipmap.icon__zhuce_tongyi);
			}
			holder.iv_select.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback1!=null){
						mCallback1.callBack(position+"");
					}
				}
			});

			holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback1!=null){
						mCallback1.callBack(position+"");
					}
				}
			});
			holder.preLL.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, PreviewWActivity.class).putExtra("work_id", hashMap.getWork_id()));
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout linear_item_layout;
		private TextView og_tv,type_tv,designer_name_tv,update_time_v_tv;
		private ImageView woks_cover_iv,iv_select;
		private RelativeLayout preLL;
	}
}