package com.gohoc.xiupuling.adapter.linkpackage;

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

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.LinkPackageDetailBean;
import com.gohoc.xiupuling.callback.Callback3;
import com.gohoc.xiupuling.ui.linkpackage.LiandongbaoChooseTerminalActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/*
* 多屏联动包新建列表
* */
public class LiandongbaoXinjianAdapter extends BaseAdapter {
	private List<LinkPackageDetailBean.DataBean> testItems=new ArrayList<LinkPackageDetailBean.DataBean>();
	private Context context;
	private Callback3 mCallback3;
	private int type;
	private String union_id,union_name;

	public void setUnion_id(String union_name,String myUnion_id)
	{
		this.union_id=myUnion_id;
		this.union_name=union_name;
	}

	public void setType(int myType)
	{
		this.type=myType;
	}

	public void setCallback(Callback3 callback)
	{
		this.mCallback3=callback;
	}

	public LiandongbaoXinjianAdapter(Context context) {
		this.context = context;
	}

	public void setmLists(List<LinkPackageDetailBean.DataBean> testItems)
	{
		this.testItems.clear();
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addItems(List<LinkPackageDetailBean.DataBean> testItems){
		this.testItems.addAll(testItems);
		notifyDataSetChanged();
	}
	
	public void addOneItems(LinkPackageDetailBean.DataBean hashMap){
		//this.testItems.add(0, hashMap);
		this.testItems.add(hashMap);
		notifyDataSetChanged();
	}

	public ArrayList<String> getIschooseTerminal()
	{
		ArrayList<String> stringList=new ArrayList<>();
		for (int i=0;i<testItems.size();i++){
			LogUtil.d("多少："+testItems.get(i).terminal_id);
			if((testItems.get(i).terminal_id+"")!="null"&&testItems.get(i).terminal_id!=null&&testItems.get(i).terminal_id!="null"&&!TextUtils.isEmpty(testItems.get(i).terminal_id+"")){
				stringList.add(testItems.get(i).terminal_id+"");
				LogUtil.d("符合条件进来刷选");
			}
		}
		LogUtil.d(stringList.size()+"：有多少长度："+stringList);
		return stringList;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_liandong_list_xinjian_layout,parent,false);

			holder.mBehindViews=(LinearLayout) convertView.findViewById(R.id.mBehindViews);
			holder.swipeMenuLayout=(SwipeMenuLayout) convertView.findViewById(R.id.swipeMenuLayout);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_length=(TextView)convertView.findViewById(R.id.tv_length);
			holder.iv_flag=(ImageView) convertView.findViewById(R.id.iv_flag);
			holder.iv_type=(TextView) convertView.findViewById(R.id.iv_type);
			holder.iv_right=(ImageView) convertView.findViewById(R.id.iv_right);
			holder.iv_type_or=(ImageView) convertView.findViewById(R.id.iv_type_or);
			holder.view_line=(View)convertView.findViewById(R.id.view_line);
			holder.linear_item_layout=(LinearLayout) convertView.findViewById(R.id.linear_item_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(testItems.size()>0){
			final LinkPackageDetailBean.DataBean hashMap=testItems.get(position);
			holder.tv_name.setText(hashMap.work_name);
			if(hashMap.is_leader){
				holder.iv_flag.setVisibility(View.VISIBLE);
			}else {
				holder.iv_flag.setVisibility(View.INVISIBLE);
			}
			holder.tv_length.setText(CustomUtils.getWorkType(hashMap.work_type)+"   "+hashMap.playtime+"s");
			final ViewHolder finalHolder = holder;
			holder.mBehindViews.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtil.d("点击删除");//deleteworklinkdetail
					if(mCallback3!=null){
						finalHolder.swipeMenuLayout.smoothClose();
						if(type==0){
							mCallback3.callBack(hashMap.link_id,hashMap.detail_id,position+"");
						}else {
							mCallback3.callBack(hashMap.link_snap_id,hashMap.detail_id,position+"");
						}
					}
				}
			});
			if(hashMap.orientation==0){
				LogUtil.d("横屏");
				holder.iv_type_or.setImageResource(R.mipmap.icon_zdheng);
			}else {
				holder.iv_type_or.setImageResource(R.mipmap.icon_zdshu);
			}
			if(!TextUtils.isEmpty(hashMap.terminal_number)){
				holder.iv_type.setText(hashMap.terminal_number);
			}else {
				holder.iv_type.setText("");
			}
			if(type==0){
				holder.iv_right.setVisibility(View.VISIBLE);
			}else {
				holder.iv_right.setVisibility(View.GONE);
			}
			holder.linear_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context, LiandongbaoChooseTerminalActivity.class);
					intent.putExtra("union_id",union_id);
					intent.putExtra("position",position);
					intent.putExtra("union_name",union_name);
					LogUtil.d("屏幕类型："+hashMap.orientation);
					intent.putExtra("orientation",hashMap.orientation);
					intent.putExtra("terminal_id",getIschooseTerminal());
					context.startActivity(intent);
				}
			});

			if(position==testItems.size()-1){
				holder.view_line.setVisibility(View.INVISIBLE);
			}else {
				holder.view_line.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}

	class ViewHolder {
		private LinearLayout mBehindViews,linear_item_layout;
		private TextView tv_name,tv_length,iv_type;
		private ImageView iv_flag,iv_right,iv_type_or;
		private SwipeMenuLayout swipeMenuLayout;
		private View view_line;
	}
}