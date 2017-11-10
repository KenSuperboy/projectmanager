package com.gohoc.xiupuling.adapter.auditadapter;

import android.content.Context;
import android.content.Intent;
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
import com.gohoc.xiupuling.bean.audit.AuditWorkListBean;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.ui.PreviewWActivity;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/*
* 审核机制
* */
public class AuditWorkAdapter extends BaseAdapter {
	private List<AuditWorkListBean.ListBean> testItems=new ArrayList<AuditWorkListBean.ListBean>();
	private Context context;
	private Callback2 mCallback2;

	public void setCallback(Callback2 callback)
	{
		this.mCallback2=callback;
	}

	public AuditWorkAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<AuditWorkListBean.ListBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<AuditWorkListBean.ListBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(AuditWorkListBean.ListBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_audit_work_layout,parent,false);

			holder.linear_item_layout=(LinearLayout) convertView.findViewById(R.id.linear_item_layout);
			holder.linear_bohui_layout=(LinearLayout) convertView.findViewById(R.id.linear_bohui_layout);
			holder.linear_tongguo_layout=(LinearLayout) convertView.findViewById(R.id.linear_tongguo_layout);
			holder.termianl_title_tv=(TextView)convertView.findViewById(R.id.termianl_title_tv);
			holder.tv_title_tv=(TextView)convertView.findViewById(R.id.tv_title_tv);
			holder.works_iv=(ImageView) convertView.findViewById(R.id.works_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final AuditWorkListBean.ListBean hashMap=testItems.get(position);
			holder.termianl_title_tv.setText("共有"+hashMap.audit_terminal_cnt+"台终端待审");
			Glide.with(context)
					.load(Constant.NetConstant.BASE_USER_RESOURE + hashMap.material_store_url + Utils.getThumbnail(200,200))
					.placeholder(R.mipmap.default_image)
					.error(R.mipmap.default_image)
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.into(holder.works_iv);
			final ViewHolder finalHolder = holder;
			holder.tv_title_tv.setText(hashMap.work_name);
			holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					/*Intent intent=new Intent(context, LiandongbaoDetailActivity.class);
					intent.putExtra("linkid",hashMap.link_id);
					intent.putExtra("linkname",hashMap.link_name);
					intent.putExtra("url",hashMap.cover_url+"");
					context.startActivity(intent);*/
					context.startActivity(new Intent(context, PreviewWActivity.class).putExtra("work_id", hashMap.work_id));
				}
			});
			//驳回
			holder.linear_bohui_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback2!=null){
						mCallback2.callBack(hashMap.work_id+"","");
					}
				}
			});
			//通过
			holder.linear_tongguo_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback2!=null){
						mCallback2.callBack("",""+hashMap.work_id);
					}
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout linear_item_layout,linear_bohui_layout,linear_tongguo_layout;
		private TextView termianl_title_tv,tv_title_tv;
		private ImageView works_iv;
	}
}