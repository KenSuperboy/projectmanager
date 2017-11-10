package com.gohoc.xiupuling.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

import android.view.Window;

import com.gohoc.xiupuling.R;

/**
 * 此类实现自定义的AlertDialog对话框
 *
 * @author zcs
 */
public class CustomerDialog {



    private Context context;
    private String msg;
    private MyDialogClickListener mMyDialogClickListener;
    private Window window;
    private AlertDialog dialog;




    public CustomerDialog(Context context, String msg) {
        super();
        this.context = context;
        this.msg = msg;

        showDialog();

    }



    public AlertDialog getDialog() {
        return dialog;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void closedDialog() {
        dialog.dismiss();
    }

    private void showDialog() {
        if (Build.VERSION.SDK_INT >= 14) {
            dialog = new AlertDialog.Builder(context,
                   R.style.dialogTips).create();
        } else {
            dialog = new AlertDialog.Builder(context).create();
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        window = dialog.getWindow();

        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容

        window.setContentView(R.layout.pop_to_be_jz);

        findViews();
        setListeners();

    }

    public interface MyDialogClickListener {
        void onConfirm();

        void onCancel();

        void onCosed();
    }

    public MyDialogClickListener getmMyDialogClickListener() {
        return mMyDialogClickListener;
    }

    public CustomerDialog setmMyDialogClickListener(
            MyDialogClickListener mMyDialogClickListener) {
        this.mMyDialogClickListener = mMyDialogClickListener;

        return this;
    }

    private void findViews() {


    }

    private void setListeners() {



    }

}
