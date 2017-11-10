package com.gohoc.xiupuling.adapter;

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
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.ui.PreviewWActivity;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

/*
* 我的作品库
* */
public class MyWorkAdapter extends BaseAdapter {
	private List<MyWorksBean.DataBean> testItems=new ArrayList<MyWorksBean.DataBean>();
	private Context context;
	private Callback2 mCallback2;

	public void setCallback(Callback2 callback)
	{
		this.mCallback2=callback;
	}

	public MyWorkAdapter(Context context) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_my_works,parent,false);

			holder.og_tv=(TextView)convertView.findViewById(R.id.og_tv);
			holder.type_tv=(TextView)convertView.findViewById(R.id.type_tv);
			holder.designer_name_tv=(TextView)convertView.findViewById(R.id.designer_name_tv);
			holder.update_time_v_tv=(TextView)convertView.findViewById(R.id.update_time_v_tv);
			holder.select_tv=(TextView)convertView.findViewById(R.id.select_tv);
			holder.woks_cover_iv=(ImageView) convertView.findViewById(R.id.woks_cover_iv);
			holder.preLL=(RelativeLayout) convertView.findViewById(R.id.preLL);
			holder.contact_designer_ll=(LinearLayout) convertView.findViewById(R.id.contact_designer_ll);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final MyWorksBean.DataBean dataBean=testItems.get(position);

			Glide.with(context)
					.load(BASE_USER_RESOURE + dataBean.getMaterial_store_url() + Utils.getThumbnail(300, 300))
					.centerCrop()
					.placeholder(R.mipmap.df_logo)
					//.error(R.mipmap.icon_dengru_touxiang)
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.into(holder.woks_cover_iv);

			holder.og_tv.setText(dataBean.getOrientation() == 0 ? "横屏" : "竖屏");
			holder.type_tv.setText(dataBean.getRq_orientation_str() + "");
			holder.designer_name_tv.setText(dataBean.getWork_name());
			holder.update_time_v_tv.setText(dataBean.getCreate_time());

			holder.preLL.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(context, PreviewWActivity.class).putExtra("work_id", dataBean.getWork_id()));
				}
			});
			holder.select_tv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback2!=null){
						mCallback2.callBack("0",position+"");//选择操作
					}
				}
			});
			holder.contact_designer_ll.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mCallback2!=null){
						mCallback2.callBack("1",position+"");//相关修改
					}
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout contact_designer_ll;
		private RelativeLayout preLL;
		private TextView og_tv,type_tv,designer_name_tv,update_time_v_tv,select_tv;
		private ImageView woks_cover_iv;
	}
}