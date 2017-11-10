package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MyshopListBean;
import com.gohoc.xiupuling.callback.Callback1;

import java.util.ArrayList;
import java.util.List;

/*
* 我的店铺
* */
public class MyShopAdapter extends BaseAdapter {
	private List<MyshopListBean.DataBean> testItems=new ArrayList<MyshopListBean.DataBean>();
	private Context context;
	private Callback1 mCallback1;

	public void setCallback(Callback1 callback)
	{
		this.mCallback1=callback;
	}

	public MyShopAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<MyshopListBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<MyshopListBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(MyshopListBean.DataBean hashMap){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_list_layout,parent,false);
			
			holder.linear_item_layout=(LinearLayout) convertView.findViewById(R.id.linear_item_layout);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final MyshopListBean.DataBean hashMap=testItems.get(position);
			holder.tv_name.setText(hashMap.shop_name+"("+hashMap.termlist.size()+")");
			/*holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtil.d("");
				}
			});*/
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout linear_item_layout;
		private TextView tv_name;
	}
}