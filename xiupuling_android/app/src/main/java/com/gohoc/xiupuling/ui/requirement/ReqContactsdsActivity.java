package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.DesignerBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.account.RegisterActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class ReqContactsdsActivity extends BasicActivity {
    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.ds_pic_iv)
    CircleImageView dsPicIv;
    @BindView(R.id.ds_name_tv)
    TextView dsNameTv;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.ds_indtrods_tv)
    TextView dsIndtrodsTv;
    @BindView(R.id.ds_moblie_tv)
    TextView dsMoblieTv;
    @BindView(R.id.ds_qq_tv)
    TextView dsQqTv;
    @BindView(R.id.ds_email_tv)
    TextView dsEmailTv;
    @BindView(R.id.ds_web_tv)
    TextView dsWebTv;
    private String rq_id;
    private String work_id;
    private DesignerBean designerBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_contactsds);
        ButterKnife.bind(this);

        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("联系设计师");

        try {
            rq_id = getIntent().getStringExtra("rq_id");
            work_id = getIntent().getStringExtra("work_id");
            getworkdesignerinfo(rq_id, work_id);
        } catch (Exception e) {
            Logger.e(toString().toString());
        }
    }

    private void initDates(DesignerBean designerBean) {
        Glide.with(this)
                .load(BASE_USER_RESOURE + designerBean.getData().getPortrait_url()+ Utils.getThumbnail(100,100))
                .centerCrop()
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                .error(R.mipmap.icon_usercenter_morentouxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(dsPicIv);
        dsNameTv.setText(designerBean.getData().getNick_name() + "");
        dsIndtrodsTv.setText(designerBean.getData().getIntroduce() + "");
        dsMoblieTv.setText(addUrl(designerBean.getData().getMobile()));
        dsQqTv.setText(addUrl(designerBean.getData().getQq()));
        dsEmailTv.setText(addUrl(designerBean.getData().getMail()));
        dsWebTv.setText(addUrl(designerBean.getData().getLink()));
        ratingbar.setStar(designerBean.getData().getStar_level());
    }


    private Spanned addUrl(String str) {
        if (TextUtils.isEmpty(str))
            return Html.fromHtml("暂未公开");
        return Html.fromHtml("<font color='#27ABFD'><u>" + str + "</font>");
    }

    private void getworkdesignerinfo(String rq_id, String work_id) {
        RxRetrofitClient.getInstance(this).getworkdesignerinfo(rq_id, work_id, new Observer<DesignerBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(ReqContactsdsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(DesignerBean designerBean) {
                if (designerBean.getCode() == 1) {
                    designerBeans = designerBean;
                    initDates(designerBean);
                }

            }
        });
    }


    @OnClick({R.id.ds_moblie_tv, R.id.ds_qq_tv, R.id.ds_email_tv, R.id.ds_web_tv, R.id.toolbar_left_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.ds_moblie_tv:
                if (!TextUtils.isEmpty(designerBeans.getData().getMobile()))
                    AppUtils.actionDial(ReqContactsdsActivity.this, designerBeans.getData().getMobile());
                    //Utils.callPhone(ReqContactsdsActivity.this, designerBeans.getData().getMobile());
                break;
            case R.id.ds_qq_tv:
                if (!TextUtils.isEmpty(designerBeans.getData().getQq()))
                    Utils.toQQ(ReqContactsdsActivity.this, designerBeans.getData().getQq());
                break;
            case R.id.ds_email_tv:
                if (!TextUtils.isEmpty(designerBeans.getData().getMail()))
                    Utils.toEmail(ReqContactsdsActivity.this, designerBeans.getData().getMail());
                break;
            case R.id.ds_web_tv:
                if (!TextUtils.isEmpty(designerBeans.getData().getLink()))
                    startActivity(new Intent(ReqContactsdsActivity.this, WebViewActivity.class).putExtra("url",Utils.delUrl(designerBeans.getData().getLink()) )
                            .putExtra("title", TextUtils.isEmpty(designerBeans.getData().getNick_name()) == true ? "" : designerBeans.getData().getNick_name()));
                break;
        }
    }
}
