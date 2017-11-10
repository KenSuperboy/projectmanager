package com.gohoc.xiupuling.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MyBankBean;
import com.gohoc.xiupuling.bean.TerminalNoBean;

import java.util.List;

/**
 * Created by 28713 on 2017/2/14.
 */

public class TerminalNoAdater extends BaseQuickAdapter<TerminalNoBean, BaseViewHolder> {

    private Context context;
    private List<TerminalNoBean> list;
    private OnItemClickLitener onItemClickLitener;


    public TerminalNoAdater(Context context,int layoutResId, List<TerminalNoBean> list) {
        super(layoutResId, list);
        this.context=context;
        this.list=list;

    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, TerminalNoBean terminalNoBean) {
        baseViewHolder.setText(R.id.terminal_no_tv, terminalNoBean.getNo());
        LinearLayout terminalNoLl = (LinearLayout) baseViewHolder.getView(R.id.terminal_no_ll);
        GradientDrawable myGrad = (GradientDrawable) terminalNoLl.getBackground();


        if(terminalNoBean.getState()==1)
        {
            myGrad.setColor( context.getResources().getColor(R.color.terminal_no_on));
            //holder.terminalNoLl.setBackgroundResource(R.drawable.terminal_no_on_sp);
        }else if(terminalNoBean.getState()==-1)
        {
            myGrad.setColor( context.getResources().getColor(R.color.terminal_no_d));
            //holder.terminalNoLl.setBackgroundResource(R.drawable.terminal_no_f_sp);
        }else
        {
            myGrad.setColor( context.getResources().getColor(R.color.withe));
        }

    }

    public List<TerminalNoBean> getList() {
        return list;
    }

    public void setList(List<TerminalNoBean> list) {
        this.list = list;
    }
    public  void add(List<TerminalNoBean> list)
    {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
