package com.gohoc.xiupuling.ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.utils.ConfigUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class BasicActivity extends AppCompatActivity implements Constant {
    public InputMethodManager manager = null;
    public ProgressDialog dialog = null;
    private String mtitle;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //只对api19以上版本有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //为状态栏着色
        // setStatusColor(R.color.colorPrimary);
        mContext=this;
    }

/*
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
       if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(event);
    }
*/

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setStatusColor(int res) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        // tintManager.setStatusBarTintResource(R.color.colorPrimary);
        tintManager.setStatusBarTintResource(res);
    }

    protected void showProgressDialog(String msg) {
        dialog = ProgressDialog.show(this, null, msg);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();

            }
        });
    }

    protected void showProgressDialog(String msg,boolean canCancel) {
        dialog = ProgressDialog.show(this, null, msg);
        dialog.setCancelable(canCancel);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();

            }
        });
    }

    /**
     * 关闭等待对话框
     */
    protected void closeProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public String getMtitle() {
        return mtitle;
    }

    public BasicActivity setMtitle(String mtitle) {
        this.mtitle = mtitle;
        return this;
    }


    /**
     * 是否关闭键盘
     *
     * @param show
     */
    public void showInput(boolean show) {
        try {
            if (show) {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInputFromInputMethod(this.getCurrentFocus().getApplicationWindowToken(), 0);
            } else {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getApplicationWindowToken(), 0);
            }
        } catch (NullPointerException e1) {

        } catch (Exception e) {
        }
    }

    public void full(boolean isFulllScreen, Toolbar toolbar) {
        if (!isFulllScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //非全屏
            toolbar.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.top_translate_enter);
            toolbar.setAnimation(animation);
            ConfigUtils.setStatusBarColor(this, toolbar);
            toolbar.setVisibility(View.VISIBLE);
            animation.start();
        } else {
            toolbar.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.top_translate_exit);
            toolbar.setAnimation(animation);
            animation.start();
            toolbar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //清除非全屏的flag
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏的flag
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}