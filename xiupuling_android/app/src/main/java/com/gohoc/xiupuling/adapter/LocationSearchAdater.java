package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.BusinessTypeBean;

import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class LocationSearchAdater extends RecyclerView.Adapter<LocationSearchAdater.MyViewHolder> {

    private Context context;
    private  List<PoiInfo> poiInfoList;
    private OnItemClickLitener onItemClickLitener;
    private PoiResult datas;

    public LocationSearchAdater(Context context,List<PoiInfo> poiInfoList) {
        this.context = context;
        this.poiInfoList = poiInfoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_location, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //  holder.tv.setText(businessBean.getData().get(position).getBusiness_name());

        holder.name.setText(poiInfoList.get(position).name + "");
        holder.location.setText(poiInfoList.get(position).address + "");
        if (null != onItemClickLitener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(v, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickLitener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return poiInfoList.size();
    }

    public void setdatas(List<PoiInfo> poiInfos) {
        this.poiInfoList.clear();
        this.poiInfoList.addAll(poiInfos);
        notifyDataSetChanged();
    }

    public void addDatas(List<PoiInfo> poiInfos) {
        this.poiInfoList.addAll(poiInfos);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, location;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.lcoation_name);
            location = (TextView) view.findViewById(R.id.lcoation_detail);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

}
