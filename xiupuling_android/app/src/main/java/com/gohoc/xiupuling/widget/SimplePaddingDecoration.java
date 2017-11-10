package com.gohoc.xiupuling.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gohoc.xiupuling.R;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/7/14 0014
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class SimplePaddingDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;


    public SimplePaddingDecoration(Context context) {
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;//类似加了一个bottom padding
    }


}
