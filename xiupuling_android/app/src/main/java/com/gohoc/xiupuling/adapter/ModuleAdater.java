package com.gohoc.xiupuling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ModuleBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.terminal.TerminalPlayerSettingActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.suke.widget.SwitchButton;

import rx.Observer;

/**
 * Created by 28713 on 2017/2/14.
 */

public class ModuleAdater extends RecyclerView.Adapter<ModuleAdater.MyViewHolder> implements Constant {

    private Context context;
    private ModuleBean module;
    private OnItemClickLitener onItemClickLitener;
    private  String terminalId;

    public ModuleAdater(Context context, ModuleBean module,String terminalId) {
        this.context = context;
        this.module = module;
        this.terminalId=terminalId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_terminal_module_sub, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.sub_name_tv.setText(module.getData().getModule_list().get(position).getModule_name() + "");
        holder.sub_size_tv.setText(Utils.getPrintSize(module.getData().getModule_list().get(position).getModule_size()) + "");
        holder.switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                updateSelectedModules(terminalId,module.getData().getModule_list().get(position).getModule_id());
            }
        });
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
        return module.getData().getModule_list().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sub_name_tv, sub_size_tv;
        SwitchButton switch_button;

        public MyViewHolder(View view) {
            super(view);
            sub_name_tv = (TextView) view.findViewById(R.id.sub_name_tv);
            sub_size_tv = (TextView) view.findViewById(R.id.sub_size_tv);
            switch_button = (SwitchButton) view.findViewById(R.id.switch_button);
        }
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    private void updateSelectedModules(String tid, String mId) {

        RxRetrofitClient.getInstance(context).updateSelectedModules(tid, mId, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //  Toast.makeText(TerminalPlayerSettingActivity.this, "请检查网络是否正常", Toast.LENGTH_SHORT).show();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {

                }

            }
        });
    }
}
