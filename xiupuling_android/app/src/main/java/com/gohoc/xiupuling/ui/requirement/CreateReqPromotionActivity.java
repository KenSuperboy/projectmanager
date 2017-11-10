package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateReqPromotionActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.key_et)
    ClearEditText keyEt;
    @BindView(R.id.introduce_et)
    ClearEditText introduceEt;
    @BindView(R.id.details_et)
    ClearEditText detailsEt;
    @BindView(R.id.req_link_ll)
    LinearLayout reqLinkLl;
    @BindView(R.id.save_button_ll)
    LinearLayout saveButtonLl;
    @BindView(R.id.req_promotion_tips_tv)
    TextView reqPromotionTipsTv;
    @BindView(R.id.req_promotion_cs_ll)
    LinearLayout reqPromotionCsLl;
    @BindView(R.id.link_type_tv)
    TextView linkTypeTv;
    private PromotionInfoBean promotionInfoBean;
    private static int LINK_REQUEST_RESULT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_promotion);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("优惠活动");
        toolbarLeftTitle.setText("取消");
        try {
            promotionInfoBean = (PromotionInfoBean) getIntent().getExtras().get("promotionInfoBean");
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        if (null == promotionInfoBean)
            promotionInfoBean = new PromotionInfoBean();
        else
            initDates();

        switchButton.setChecked(promotionInfoBean.getOpen());
        checkIsShow(promotionInfoBean.getOpen());
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                promotionInfoBean.setOpen(isChecked);
                checkIsShow(isChecked);
            }
        });
    }

    private void initDates() {
        /*if (null != promotionInfoBean.getKeyWord())
            keyEt.setText(promotionInfoBean.getKeyWord() + "");*/
        LogUtil.d("多111少："+promotionInfoBean.getIntroduce());
        if (null != (promotionInfoBean.getIntroduce())&&(!promotionInfoBean.getIntroduce().equals("null"))&&null != (promotionInfoBean.getIntroduce()+"")&&"null"!=(promotionInfoBean.getIntroduce()+"")&&!TextUtils.isEmpty(promotionInfoBean.getIntroduce()))
            LogUtil.d("多222少："+promotionInfoBean.getIntroduce());
            introduceEt.setText(promotionInfoBean.getIntroduce() + "");
        if ((null != promotionInfoBean.getDetails()+"")&&null != promotionInfoBean.getDetails()&&"null"!=(promotionInfoBean.getDetails()+"")&&!TextUtils.isEmpty(promotionInfoBean.getDetails()))
            detailsEt.setText(promotionInfoBean.getDetails() + "");
        if (null != promotionInfoBean && promotionInfoBean.getOpen()&& promotionInfoBean.getLinkType() == 0 )
            linkTypeTv.setText("已链接到实体店");
        else if (null != promotionInfoBean&& promotionInfoBean.getOpen() && promotionInfoBean.getLinkType() == 1&&!TextUtils.isEmpty(promotionInfoBean.getLinkUrl()))
            linkTypeTv.setText("已链接到网址");
    }

    private void checkIsShow(boolean isCheck) {
        if (isCheck) {
            reqPromotionCsLl.setVisibility(View.VISIBLE);
            reqPromotionTipsTv.setVisibility(View.GONE);

        } else {
            reqPromotionCsLl.setVisibility(View.GONE);
            reqPromotionTipsTv.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.req_link_ll, R.id.save_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.req_link_ll:
                startActivityForResult(new Intent(CreateReqPromotionActivity.this, CreateReqLinkActivity.class).putExtra("promotionInfoBean", promotionInfoBean), LINK_REQUEST_RESULT);
                break;
            case R.id.save_button_ll:
                save();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (null != data) {
                Bundle d = data.getExtras();
                if (null != d) {
                    PromotionInfoBean pti = (PromotionInfoBean) d.get("promotionInfoBean");
                    if (pti != null) {
                        promotionInfoBean = pti;
                        if (promotionInfoBean.getLinkType() == 0)
                            linkTypeTv.setText("已链接到实体店");
                        else
                            linkTypeTv.setText("已链接到网址");
                        Logger.e(promotionInfoBean.getLcoationName() + "");
                    }
                }
            }
        }
    }

    private void save() {
        if (promotionInfoBean.getOpen()) {
            /*if (TextUtils.isEmpty(keyEt.getText())) {
                Utils.toast(this, "请输入品牌或关键字");
                return;
            }*/
            if (TextUtils.isEmpty(introduceEt.getText())) {
                Utils.toast(this, "请输入一句话总结");
                return;
            }
            if (TextUtils.isEmpty(detailsEt.getText())) {
                Utils.toast(this, "请输入具体规则说明");
                return;
            }
            if (TextUtils.isEmpty(linkTypeTv.getText()) || linkTypeTv.getText().length() < 1) {
                Utils.toast(this, "请选择二维码去向");
                return;
            }
        }


        //promotionInfoBean.setKeyWord(keyEt.getText() + "");
        promotionInfoBean.setDetails(detailsEt.getText() + "");
        promotionInfoBean.setIntroduce(introduceEt.getText() + "");

        setResult(RESULT_OK, new Intent().putExtra("promotionInfoBean", promotionInfoBean));
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*if (ViewUtils.isTouchedViewOutSideView(keyEt, event)) {
            showInput(false);
        }*/
        return super.onTouchEvent(event);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if((introduceEt.getText() + "").equals("null")||(introduceEt.getText() + "")=="null"||TextUtils.isEmpty(introduceEt.getText() + "")){
            introduceEt.setText("");
        }
    }
}
