package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.push.PushReqShowCountActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateReqLinkToWebActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.url_et)
    ClearEditText urlEt;
    @BindView(R.id.show_button_ll)
    LinearLayout showButtonLl;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private PromotionInfoBean promotionInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_link_to);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("链接到网址");
        try {
            promotionInfoBean = (PromotionInfoBean) getIntent().getExtras().get("promotionInfoBean");
            if (!TextUtils.isEmpty(promotionInfoBean.getLinkUrl())) ;
              urlEt.setText(promotionInfoBean.getLinkUrl());
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }


    @OnClick({R.id.toolbar_left_title, R.id.show_button_ll, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.show_button_ll:
                startActivity(new Intent(CreateReqLinkToWebActivity.this, WebViewActivity.class).putExtra("url", Utils.delUrl(urlEt.getText() + "")).putExtra("title", "链接到网址"));
                break;
            case R.id.toolbar_right:
                promotionInfoBean.setLinkType(1);
                if (!TextUtils.isEmpty(urlEt.getText()))
                    promotionInfoBean.setLinkUrl(urlEt.getText().toString());
                else {
                    Utils.toast(this, "请输入链接网址");
                    return;
                }
                setResult(RESULT_OK, new Intent().putExtra("promotionInfoBean", promotionInfoBean));
                finish();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(urlEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }
}
