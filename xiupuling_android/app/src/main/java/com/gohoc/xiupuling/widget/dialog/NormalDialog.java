package com.gohoc.xiupuling.widget.dialog;

import android.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gohoc.xiupuling.R;


/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/5/15 0015
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description 公用的dialog
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class NormalDialog extends BaseDialog implements View.OnClickListener {
    private final static String TAG ="NormalDialog";

    private Button btnCannel;
    private Button btnConfirm;
    private TextView tvTitle;
    private TextView tvContent;

    private String mTileText = "温馨提示";
    private String mContent = "默认内容";
    private String mCannelText = "取消";
    private String mConfirmText = "确定";

    private NormalDialogListenner listenner;

    public enum NormalType {
        OK, NO
    }

    public interface NormalDialogListenner {
        void onClick(NormalType normalType);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_normal_dialog;
    }

    public static NormalDialog create(FragmentManager mFragmentManager) {
        NormalDialog normalDialog = new NormalDialog();
        normalDialog.setFragmentManager(mFragmentManager);
        return normalDialog;
    }

    @Override
    public void bindView(View v) {
        btnCannel = (Button) v.findViewById(R.id.button1);
        btnConfirm = (Button) v.findViewById(R.id.button2);
        tvTitle = (TextView) v.findViewById(R.id.title);
        tvContent = (TextView) v.findViewById(R.id.content);
        btnCannel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        setData();
    }

    private void setData() {
        tvTitle.setText(mTileText);
        tvContent.setText(mContent);
        btnConfirm.setText(mConfirmText);
        btnCannel.setText(mCannelText);
    }

    public NormalDialog setNormalDialogListenner(NormalDialogListenner listener) {
        this.listenner = listener;
        return this;
    }


    @Override
    public void onClick(View v) {
        dismiss();
        if (v == btnCannel) {
            if (listenner != null) {
                listenner.onClick(NormalType.NO);
            }
        } else if (v == btnConfirm) {
            if (listenner != null) {
                listenner.onClick(NormalType.OK);
            }
        }
    }

    public NormalDialog setmTileText(String mTileText) {
        this.mTileText = mTileText;
        return this;
    }

    public NormalDialog setmContent(String mContent) {
        this.mContent = mContent;
        return this;
    }

    public NormalDialog setmConfirmText(String mConfirmText) {
        this.mConfirmText = mConfirmText;
        return this;
    }

    public NormalDialog setmCannelText(String mCannelText) {
        this.mCannelText = mCannelText;
        return this;
    }

    @Override
    public BaseDialog show() {
        return super.show();
    }
}
