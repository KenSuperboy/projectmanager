package com.gohoc.xiupuling.widget.dialog;

import android.view.View;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/6/8 0008
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public interface NormalDialogListener {

    void onClick(NormalDialog.NormalType normalType);

    void dismiss();

    void bindView(View view);
}
