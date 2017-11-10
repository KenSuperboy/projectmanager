package com.gohoc.xiupuling.ui.requirement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.UploadImageAdapter;
import com.gohoc.xiupuling.bean.UploadBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.widget.NonScrollGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

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
public class CreateReqUploadDataNewActivity extends BasicActivity {

    @BindView(R.id.tips_tv)
    TextView tipsTv;
    @BindView(R.id.content_frame)
    RelativeLayout mFrameContenLayout;
    @BindView(R.id.gridview)
    NonScrollGridView gridView;
    @BindView(R.id.iv_float)
    ImageView iv_float;

    private UploadBean bean;
    private UploadImageAdapter mAdapter;
    private int og;  //是否横屏还是竖屏
    private int mType = -1;
    private String[] tips = {"", "海报需要上传1张图片(最大5M)", "数码相册需要上传1~5张图片(最大5M)", "视频需要上传1个视频短片(最大100M)", "需要上传背景图(5M以内)和视频(100M以内)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_upload_data_new);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    private void initData() {
        Intent data = getIntent();
        // TODO: 2017/6/8 0008 传入类型
        if (data != null) {
            og = data.getIntExtra("og", 0);
            mType = data.getIntExtra("type", -1);
            if (mType != -1)
                tipsTv.setText(tips[mType]);
        }
        //处理横竖屏
        //计算并设置宽度高度
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        int w = wm.getDefaultDisplay().getWidth();
        if (og != 1) {//横屏
            iv_float.setImageResource(R.mipmap.icon_hengbanui);
            float scale = 9f / 16f;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, (int) (w * scale));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
        } else {//竖屏
            iv_float.setImageResource(R.mipmap.icon_shubanui);
            float scale = 16f / 9f;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w / 2, (int) ((w / 2) * scale));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mFrameContenLayout.setLayoutParams(layoutParams);
            mFrameContenLayout.requestLayout();
        }

    }

    private void initView() {
        mAdapter = new UploadImageAdapter(this);
        switch (mType) {
            case 1:
                mAdapter.setMax(1);
                break;
            case 2:
                mAdapter.setMax(5);
                break;
            case 3:
                mAdapter.setMax(1);
                break;
            case 4:
                mAdapter.setMax(2);
                break;
        }
        gridView.setAdapter(mAdapter);
        if (bean == null) {
            bean = new UploadBean();
        }
        mAdapter.setDatas(bean.getPicBeanList());
    }


}
