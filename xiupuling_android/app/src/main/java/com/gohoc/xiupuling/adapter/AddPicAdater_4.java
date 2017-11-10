package com.gohoc.xiupuling.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.itemtouchhelper.adapater.DragAdapater;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class AddPicAdater_4 extends DragAdapater<AddPicAdater_4.MyViewHolder> implements Constant {

    private Context context;
    private List<PicBean> picBeanList;
    private OnItemClickLitener onItemClickLitener;
    private OnPicChangeListion onPicChangeListion;
    private int maxCount = 5;

    public AddPicAdater_4(Context context, List<PicBean> picBeanList) {
        this.context = context;
        this.picBeanList = picBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_add_pic2, parent,
                false));


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (picBeanList.get(position) != null) {
            if (picBeanList.get(position).getProgress() == 100 || picBeanList.get(position).getType() == 0 ) {
                holder.progressBar2.setVisibility(View.GONE);
            } else {
                holder.progressBar2.setVisibility(View.VISIBLE);
                holder.progressBar2.setProgress(picBeanList.get(position).getProgress());
            }

            if (null != onPicChangeListion) {
                holder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.d("1111适配器里面删除点击："+position);
                        onPicChangeListion.OnDel(v, position);
                    }
                });
                holder.pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.d("2222适配器里面添加点击："+position);
                        onPicChangeListion.OnAdd(v, position);
                    }
                });

                if (picBeanList.get(position).getType() == 1) {
                    if (picBeanList.get(position).isVideo()) {
                        try {
                            holder.pic.setImageBitmap(Utils.getVideoThumbnail(picBeanList.get(position).getLocUrl(), 100, 100, MediaStore.Images.Thumbnails.MICRO_KIND));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        String url = picBeanList.get(position).getLocUrl();
                        if (null == url) {
                            url = NetConstant.BASE_USER_RESOURE + picBeanList.get(position).getUrl();
                        }
                        Logger.e(url);
                        Glide.with(context)
                                .load(url)
                                // .placeholder(R.mipmap.icon_dengru_touxiang)
                                .placeholder(R.mipmap.btn_shangchuan)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.pic);
                    }

                } else {
                    Glide.with(context)
                            .load(R.mipmap.btn_shangchuan)
                            .placeholder(R.mipmap.btn_shangchuan)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.pic);
                }


                if (picBeanList.get(position).getType() == 1)
                    holder.del.setVisibility(View.VISIBLE);
                else
                    holder.del.setVisibility(View.GONE);
            }
            holder.mView.setBackgroundColor(context.getResources().getColor(R.color.black));
        }

    }


    /**
     * @param path 媒体文件绝对路径
     * @return 返回媒体文件缩略图
     */
    public static Bitmap getVideoBitmap(String path) {
        Log.e("Icon", "path:" + path);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            Bitmap frameAtTime = retriever.getFrameAtTime();
            return frameAtTime;
        } catch (Exception e) {
            return null;
        } finally {
            retriever.release();
        }

    }

    @Override
    public int getItemCount() {
        return picBeanList == null ? 0 : picBeanList.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(picBeanList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(picBeanList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        if(onPicChangeListion!=null)
            onPicChangeListion.OnMove(picBeanList);
    }

    @Override
    public void onSwiped(int position) {
        picBeanList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView pic, del;
        ProgressBar progressBar2;
        View mView;


        public MyViewHolder(View view) {
            super(view);
            this.mView = view;
            FrameLayout frameLayout = (FrameLayout) view;
            DisplayMetrics dm = new DisplayMetrics();
            //取得窗口属性
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            //窗口的宽度
            int width = dm.widthPixels;
            int iconHeight = (width - dip2px(context, 42)) / 2;
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(iconHeight, iconHeight);
            frameLayout.setLayoutParams(layoutParams);

            pic = (ImageView) view.findViewById(R.id.imageView1);
            del = (ImageView) view.findViewById(R.id.imageViewDel);
            progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);

            FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(iconHeight - dip2px(context, 10), dip2px(context, 5));
            layoutParams1.gravity = Gravity.CENTER;
            progressBar2.setLayoutParams(layoutParams1);

        }
    }


    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public interface OnPicChangeListion {
        void OnDel(View v, int postion);
        void OnAdd(View v, int postion);
        void OnMove(List<PicBean> picBeanList);
    }

    public OnPicChangeListion getOnPicChangeListion() {
        return onPicChangeListion;
    }

    public void setOnPicChangeListion(OnPicChangeListion onPicChangeListion) {
        this.onPicChangeListion = onPicChangeListion;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

}
