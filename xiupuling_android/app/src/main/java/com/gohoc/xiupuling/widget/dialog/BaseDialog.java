package com.gohoc.xiupuling.widget.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.gohoc.xiupuling.R;


/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/5/12 0012
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description 自定义dialog集类
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public abstract class BaseDialog extends DialogFragment {

    /**
     * tag
     */
    private static final String TAG = "BaseDialog";

    /**
     * FragmentManager
     */
    public FragmentManager mFragmentManager;

    /**
     * dialog操作监听回调
     */
    private DialogViewListener mDialogViewListener;

    /**
     * 是否点击其他区域关闭 默认关闭
     */
    private boolean mOutsideCancel = true;

    /**
     * 返回键是否能撤销dialog
     */
    private boolean mBackCancel = true;

    /**
     * tag标记
     */
    private String mTag = "BaseDialog";

    /**
     * 布局文件
     */
    @LayoutRes
    public int mLayoutRes;



    public interface DialogViewListener {

        void bindView(View v);

        void dismiss();
    }

    public BaseDialog() {

    }

    public BaseDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Theme_dialogLayout);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT <= 10) {
            return super.onCreateDialog(savedInstanceState);
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View body = inflater.inflate(getLayoutRes(), null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(body);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(mOutsideCancel);
        setCancelable(mBackCancel);
        bindView(body);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = win.getAttributes();
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);

    }


    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        if (Build.VERSION.SDK_INT > 10) {
            return super.onCreateView(inflater, container, bundle);
        }
        View body = inflater.inflate(getLayoutRes(), null, false);
        bindView(body);
        return body;
    }

    public void bindView(View v) {
        if (mDialogViewListener != null) {
            mDialogViewListener.bindView(v);
        }
    }


    /**
     * show显示dialog
     *
     * @param fragmentManager
     */
    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    public BaseDialog show() {
        show(mFragmentManager);
        return this;
    }

    @LayoutRes
    public int getLayoutRes() {
        return mLayoutRes;
    }

    public BaseDialog setDialogViewListener(DialogViewListener listener) {
        mDialogViewListener = listener;
        return this;
    }

    public BaseDialog setmOutsideCancel(boolean mOutsideCancel) {
        this.mOutsideCancel = mOutsideCancel;
        return this;
    }

    public BaseDialog setmBackCancel(boolean mBackCancel) {
        this.mBackCancel = mBackCancel;
        return this;
    }

    public BaseDialog setmTag(String mTag) {
        this.mTag = mTag;
        return this;
    }

    public BaseDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogViewListener != null) {
            mDialogViewListener.dismiss();
        }
    }

}
