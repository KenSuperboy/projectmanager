package com.gohoc.xiupuling.widget.dialog;


import android.app.FragmentManager;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/5/12 0012
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description  dialog工厂
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class FactoryDialog extends BaseDialog {

    public static FactoryDialog create(FragmentManager mFragmentManager) {
        FactoryDialog factoryDialog = new FactoryDialog();
        factoryDialog.setFragmentManager(mFragmentManager);
        return factoryDialog;
    }

}
