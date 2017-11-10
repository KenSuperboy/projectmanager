package com.gohoc.xiupuling.adapter.linkpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.LinkPackageDetailBean;
import com.gohoc.xiupuling.callback.Callback3;
import com.gohoc.xiupuling.callback.OnItemCallbackListener;
import com.gohoc.xiupuling.utils.CustomUtils;

import java.util.Collections;
import java.util.List;

/*
* 多屏联动包排序
* */
public class LiandongbaoPaixuAdapter extends RecyclerView.Adapter<LiandongbaoPaixuAdapter.Holder> implements OnItemCallbackListener {
	private List<LinkPackageDetailBean.DataBean> testItems;
	private Context context;
	private Callback3 mCallback3;

	public void setCallback(Callback3 callback)
	{
		this.mCallback3=callback;
	}

	public LiandongbaoPaixuAdapter(Context context,List<LinkPackageDetailBean.DataBean> myTestItems) {
		this.context = context;
		this.testItems=myTestItems;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_liandong_list_paixu_layout, parent, false);
		return new Holder(view);
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position) {
		//holder.tv.setText(mData.get(position));

		if(testItems.size()>0){
			final LinkPackageDetailBean.DataBean hashMap=testItems.get(position);
			holder.tv_name.setText(hashMap.work_name);
			if(hashMap.is_leader){
				holder.iv_flag.setVisibility(View.INVISIBLE);
			}else {
				holder.iv_flag.setVisibility(View.INVISIBLE);
			}
			holder.tv_length.setText(CustomUtils.getWorkType(hashMap.work_type)+"   "+hashMap.playtime+"s");
		}
	}

	@Override
	public int getItemCount() {
		return testItems.size();
	}

	@Override
	public void onMove(int fromPosition, int toPosition) {
		/**
		 * 在这里进行给原数组数据的移动
		 */
		Collections.swap(testItems, fromPosition, toPosition);
		/**
		 * 通知数据移动
		 */
		notifyItemMoved(fromPosition, toPosition);
	}

	public List<LinkPackageDetailBean.DataBean> getDataList()
	{
		return testItems;
	}

	@Override
	public void onSwipe(int position) {
		/**
		 * 原数据移除数据
		 */
		//mData.remove(position);
		/**
		 * 通知移除
		 */
		//notifyItemRemoved(position);
	}

	class Holder extends RecyclerView.ViewHolder {
		TextView tv_name,tv_length;
		ImageView iv_flag,iv_paixu;
		public Holder(View itemView) {
			super(itemView);
			tv_name=(TextView)itemView.findViewById(R.id.tv_name);
			tv_length=(TextView)itemView.findViewById(R.id.tv_length);
			iv_flag=(ImageView) itemView.findViewById(R.id.iv_flag);
			iv_paixu=(ImageView) itemView.findViewById(R.id.iv_paixu);
		}
	}
}