package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.constant.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.OnTextChanged;

/**
 * Created by 28713 on 2017/2/14.
 */

public class ReqSouresDatesAdater extends RecyclerView.Adapter<ReqSouresDatesAdater.MyViewHolder> implements Constant {

    private Context context;
    private List<PicBean> picBeanList;
    private OnItemClickLitener onItemClickLitener;
    private ReqSouresDatesAdater.OnPicChangeListion onPicChangeListion;
    private int maxCount = 6;


    public ReqSouresDatesAdater(Context context, List<PicBean> picBeanList) {
        this.context = context;
        this.picBeanList = picBeanList;
    }

    @Override
    public ReqSouresDatesAdater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ct_req_datas, parent, false);
        // pass MyCustomEditTextListener to viewholder in onCreateViewHolder
        // so that we don't have to do this expensive allocation in onBindViewHolder
        MyViewHolder vh = new MyViewHolder(v, new MyCustomEditTextListener());

        return vh;
    }

    @Override
    public void onBindViewHolder(final ReqSouresDatesAdater.MyViewHolder holder, final int position) {
        //holder.setIsRecyclable(false);
        if (null != onPicChangeListion) {
            holder.del_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPicChangeListion.OnDel(v, position);
                }
            });
            holder.img_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPicChangeListion.OnAdd(v, position);
                }
            });

            if (picBeanList.get(position).getType() == 1) {
                String url = picBeanList.get(position).getLocUrl();
                if (null == url) {
                    url = Constant.NetConstant.BASE_USER_RESOURE + picBeanList.get(position).getUrl();
                }
                Logger.e(url);
                Glide.with(context)
                        .load(url)
                        // .placeholder(R.mipmap.icon_dengru_touxiang)
                        //.error(R.mipmap.icon_dengru_touxiang)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.img_iv);
            } else {
                Glide.with(context)
                        .load(R.mipmap.shop_logo_add_nor)
                        // .placeholder(R.mipmap.icon_dengru_touxiang)
                        //.error(R.mipmap.icon_dengru_touxiang)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.img_iv);
            }


            if (picBeanList.get(position).getType() == 1) {
                holder.del_iv.setVisibility(View.VISIBLE);
                holder.no_pic_tips_tv.setVisibility(View.GONE);
                holder.ds_et.setVisibility(View.VISIBLE);

                holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
                holder.ds_et.setText(picBeanList.get(holder.getAdapterPosition()).getDescribe());


            } else {
                holder.del_iv.setVisibility(View.GONE);
                holder.no_pic_tips_tv.setVisibility(View.VISIBLE);
                holder.ds_et.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return picBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextWatcher mWatcher;
        TextView no_pic_tips_tv;
        ImageView img_iv, del_iv;
        EditText ds_et;
        public MyCustomEditTextListener myCustomEditTextListener;

        public MyViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            no_pic_tips_tv = (TextView) view.findViewById(R.id.no_pic_tips_tv);
            ds_et = (EditText) view.findViewById(R.id.ds_et);
            img_iv = (ImageView) view.findViewById(R.id.img_iv);
            del_iv = (ImageView) view.findViewById(R.id.del_iv);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.ds_et.addTextChangedListener(myCustomEditTextListener);

        }
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
    }

    public ReqSouresDatesAdater.OnPicChangeListion getOnPicChangeListion() {
        return onPicChangeListion;
    }

    public void setOnPicChangeListion(ReqSouresDatesAdater.OnPicChangeListion onPicChangeListion) {
        this.onPicChangeListion = onPicChangeListion;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            picBeanList.get(position).setDescribe(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
