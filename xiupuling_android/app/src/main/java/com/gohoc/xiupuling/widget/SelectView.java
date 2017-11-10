package com.gohoc.xiupuling.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/7/4 0004
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class SelectView extends LinearLayout implements View.OnClickListener {

    private Context context;
    private TextView tv1, tv2, tv3, tv4;
    private TextView tvs1, tvs2, tvs3, tvs4;
    private ImageView iv1, iv2, iv3, iv4;
    private int mode = 0;

    private int index = 1;
    private int curr = 1;
    private SelectViewListener listener;

    public interface SelectViewListener {
        void selectItem(int postion);
    }

    public SelectView(Context context) {
        super(context);
        initView(context, null);
    }

    public SelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);

    }

    public void setMode(int mode) {
        this.mode = mode;
        cleanStatus();
        setStatus(tv1, iv1, tvs1);
    }

    public void setListener(SelectViewListener listener) {
        this.listener = listener;
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_select_view, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inflate.setLayoutParams(params);

        tv1 = (TextView) inflate.findViewById(R.id.tv1);
        tv2 = (TextView) inflate.findViewById(R.id.tv2);
        tv3 = (TextView) inflate.findViewById(R.id.tv3);
        tv4 = (TextView) inflate.findViewById(R.id.tv4);
        iv1 = (ImageView) inflate.findViewById(R.id.iv1);
        iv2 = (ImageView) inflate.findViewById(R.id.iv2);
        iv3 = (ImageView) inflate.findViewById(R.id.iv3);
        iv4 = (ImageView) inflate.findViewById(R.id.iv4);
        tvs1 = (TextView) inflate.findViewById(R.id.tvs1);
        tvs2 = (TextView) inflate.findViewById(R.id.tvs2);
        tvs3 = (TextView) inflate.findViewById(R.id.tvs3);
        tvs4 = (TextView) inflate.findViewById(R.id.tvs4);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        addView(inflate);
        cleanStatus();
        setStatus(tv1, iv1, tvs1);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv1:
                curr = 1;
                if (curr == index) return;
                index = 1;
                cleanStatus();
                setStatus(tv1, iv1, tvs1);
                if (listener != null) {
                    listener.selectItem(index);
                }
                break;
            case R.id.iv2:
                curr = 2;
                if (curr == index) return;
                index = 2;
                cleanStatus();
                setStatus(tv2, iv2, tvs2);
                if (listener != null) {
                    listener.selectItem(index);
                }
                break;
            case R.id.iv3:
                curr = 3;
                if (curr == index) return;
                index = 3;
                cleanStatus();
                setStatus(tv3, iv3, tvs3);
                if (listener != null) {
                    listener.selectItem(index);
                }
                break;
            case R.id.iv4:
                curr = 4;
                if (curr == index) return;
                index = 4;
                cleanStatus();
                setStatus(tv4, iv4, tvs4);
                if (listener != null) {
                    listener.selectItem(index);
                }
                break;
        }
    }

    private void setStatus(TextView tv, ImageView iv, TextView tvs) {
        tv.setTextColor(getResources().getColor(R.color.gallery_blue));
//        tv.setTextSize(14f);
        iv.setImageResource(R.drawable.blue12);
        if (mode == 1)
            tvs.setVisibility(VISIBLE);
    }

    private void cleanStatus() {
        tv1.setTextColor(getResources().getColor(R.color.black_text));
        tv2.setTextColor(getResources().getColor(R.color.black_text));
        tv3.setTextColor(getResources().getColor(R.color.black_text));
        tv4.setTextColor(getResources().getColor(R.color.black_text));
        iv1.setImageResource(R.drawable.radio_false_shape);
        iv2.setImageResource(R.drawable.radio_false_shape);
        iv3.setImageResource(R.drawable.radio_false_shape);
        iv4.setImageResource(R.drawable.radio_false_shape);
//        tv1.setTextSize(12f);
//        tv2.setTextSize(12f);
//        tv3.setTextSize(12f);
//        tv4.setTextSize(12f);
        tvs1.setVisibility(GONE);
        tvs2.setVisibility(GONE);
        tvs3.setVisibility(GONE);
        tvs4.setVisibility(GONE);
    }
}
