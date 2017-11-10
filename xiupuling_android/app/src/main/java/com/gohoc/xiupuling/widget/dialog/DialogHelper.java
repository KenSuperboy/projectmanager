package com.gohoc.xiupuling.widget.dialog;

import android.app.FragmentManager;
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
public class DialogHelper {


    /**
     * 快速创建NormalDialog
     *
     * @param fragmentManager
     * @param title           标题
     * @param message         信息
     * @param left            左边信息
     * @param right           右边信息
     * @param isOutsideCancel
     * @param isBackCancel
     * @param tag
     * @param listenner
     * @return
     */
    public static BaseDialog showNormalDialog(FragmentManager fragmentManager,
                                              String title,
                                              String message,
                                              String left,
                                              String right,
                                              boolean isOutsideCancel,
                                              boolean isBackCancel,
                                              String tag,
                                              final NormalDialogListener listenner) {

        BaseDialog baseDialog = NormalDialog.create(fragmentManager)
                .setNormalDialogListenner(new NormalDialog.NormalDialogListenner() {
                    @Override
                    public void onClick(NormalDialog.NormalType normalType) {
                        listenner.onClick(normalType);
                    }
                })
                .setmTileText(title)
                .setmContent(message)
                .setmCannelText(left)
                .setmConfirmText(right)
                .setmOutsideCancel(isOutsideCancel)
                .setmBackCancel(isBackCancel)
                .setmTag(tag)
                .setDialogViewListener(new BaseDialog.DialogViewListener() {
                    @Override
                    public void bindView(View v) {
                        listenner.bindView(v);
                    }

                    @Override
                    public void dismiss() {
                        listenner.dismiss();
                    }
                })
                .show();
        return baseDialog;
    }


}
