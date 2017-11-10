package com.gohoc.xiupuling.ui.requirement;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.CreatRqResultBean;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.account.BonusPointTokenActivity;
import com.gohoc.xiupuling.ui.account.MemberAuthorizationActivity;
import com.gohoc.xiupuling.ui.account.MemberShopActivity;
import com.gohoc.xiupuling.ui.order.RequirementCreteadSucessActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.SelectView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class CreateReqOtherActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.req_activity_tv)
    TextView reqTitleTv;
    @BindView(R.id.req_title_lv)
    LinearLayout reqTitleLv;
    @BindView(R.id.req_promotion_tv)
    TextView reqPromotionTv;
    @BindView(R.id.req_promotion_lv)
    LinearLayout reqPromotionLv;
    @BindView(R.id.req_og_tv)
    TextView reqOgTv;
    @BindView(R.id.req_og_lv)
    LinearLayout reqOgLv;
    @BindView(R.id.req_pd_count_tv)
    TextView reqPdCountTv;
    @BindView(R.id.req_pd_count_lv)
    LinearLayout reqPdCountLv;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.textView20)
    TextView textView20;
    @BindView(R.id.req_resourse_tv)
    TextView reqResourseTv;
    @BindView(R.id.req_pd_ds_lv)
    LinearLayout reqPdDsLv;
    @BindView(R.id.save_button_ll)
    LinearLayout saveButtonLl;
    @BindView(R.id.select_view)
    SelectView select_view;

    private String title = "";
    private PromotionInfoBean promotionInfoBean;
    private int og;
    private static int TITLE_REQUEST_RESULT = 1000;
    private static int PT_REQUEST_RESULT = 1001;
    private static int OG_REQUEST_RESULT = 1002;
    private static int DT_REQUEST_RESULT = 1003;
    private static int RQ_REQUEST_RESULT = 1004;
    private ArrayList<PicBean> picList;
    private int type = 1;
    private String req = "";
    private StringBuffer material_urls = new StringBuffer();
    private StringBuffer material_types = new StringBuffer();
    private ArrayList<String> souresUrl = new ArrayList<>();
    private Window window;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_other);
        ButterKnife.bind(this);
        toolbarTitle.setText("找人设计");
        toolbarRight.setVisibility(View.GONE);
        setStatusColor(R.color.colorPrimary);
        checkToken();//判断令牌数是否足够
        select_view.setMode(1);
        select_view.setListener(new SelectView.SelectViewListener() {
            @Override
            public void selectItem(int postion) {
                type = postion;
            }
        });
        promotionInfoBean = new PromotionInfoBean();
    }

    private void checkToken() {
        RxRetrofitClient.getInstance(this).getmypoin(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CreateReqOtherActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    int token = Integer.parseInt(vCodeBenan.getData());
                    if (token < 8) {
                        showShopPop(String.valueOf(token), String.valueOf(token), "令牌");
                    }
                }
            }
        });

    }

    private void showShopPop(String count, String ownCount, String title) {
        dialog = new AlertDialog.Builder(this,
                R.style.dialogTips).create();
        dialog.show();
        window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(false);
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容

        window.setContentView(R.layout.dialog_token_limt_alert);

        window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(CreateReqOtherActivity.this, MemberShopActivity.class).putExtra("backType", 1));

            }
        });
        window.findViewById(R.id.check_auth_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(CreateReqOtherActivity.this, MemberAuthorizationActivity.class)
                );
            }
        });
        window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                Intent intent = new Intent(CreateReqOtherActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                EventBus.getDefault().post(new Event.MainIndex(3));
                finish();
            }
        });
        SpanUtils su = new SpanUtils();
        su.append("您目前拥有" + count + "个" + title + "， 如果需获得更多" + title + ",请前往会员商城或参与")
                .append("官方活动").setForegroundColor(Color.parseColor("#38b2fd"))
                .append("扩充拥有数。");
        TextView tips_title_tv = (TextView) window.findViewById(R.id.tips_title_tv);
        tips_title_tv.setText(su.create());
        TextView shop_count_tv = (TextView) window.findViewById(R.id.shop_count_tv);
        su = new SpanUtils();
        su.append(title + " ").append(ownCount).setForegroundColor(Color.parseColor("#38b2fd"));
        shop_count_tv.setText(su.create());
        su = new SpanUtils();
        TextView check_auth_tv = (TextView) window.findViewById(R.id.check_auth_tv);
        check_auth_tv.setText(su.append(" 查询其它").setForegroundColor(Color.parseColor("#929292")).append("会员权限").setForegroundColor(Color.parseColor("#38b2fd")).create());
    }

    @OnClick({R.id.toolbar_left_title, R.id.req_title_lv, R.id.req_promotion_lv, R.id.req_og_lv, R.id.req_pd_count_lv, R.id.save_button_ll, R.id.req_pd_ds_lv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.req_title_lv:
                startActivityForResult(new Intent(CreateReqOtherActivity.this, CreateReqTitleActivity.class).putExtra("title", title), TITLE_REQUEST_RESULT);
                break;
            case R.id.req_promotion_lv:
                startActivityForResult(new Intent(CreateReqOtherActivity.this, CreateReqPromotionActivity.class).putExtra("promotionInfoBean", promotionInfoBean), PT_REQUEST_RESULT);
                break;
            case R.id.req_og_lv:
                startActivityForResult(new Intent(CreateReqOtherActivity.this, CreateReqOgActivity.class).putExtra("orientation", og), OG_REQUEST_RESULT);
                break;
            case R.id.req_pd_count_lv:
                startActivityForResult(new Intent(CreateReqOtherActivity.this, CreateReqOtherUploadDataActivity.class).putExtra("picList", picList), DT_REQUEST_RESULT);
                break;
            case R.id.req_pd_ds_lv:
                startActivityForResult(new Intent(CreateReqOtherActivity.this, CreateReqOtherDsActivity.class).putExtra("req", req), RQ_REQUEST_RESULT);
                break;
            case R.id.save_button_ll:
                sumbit();
                break;
        }
    }


    private void save() {
        showProgressDialog("正在提交", false);
        if (picList != null)
            for (int a = 0; a < picList.size(); a++) {
                if (picList.get(a).getType() != 0 && !TextUtils.isEmpty(picList.get(a).getUrl())) {
                    material_urls.append(picList.get(a).getUrl() + ",");
                    material_types.append("2" + ",");
                    souresUrl.add(picList.get(a).getDescribe());
                }

            }
        RxRetrofitClient.getInstance(this).requirementSave(type + "", title,
                promotionInfoBean.getOpen() == true ? "0" : "1",
                TextUtils.isEmpty(promotionInfoBean.getKeyWord()) ? null : promotionInfoBean.getKeyWord(),
                TextUtils.isEmpty(promotionInfoBean.getIntroduce()) ? null : promotionInfoBean.getIntroduce(),
                promotionInfoBean.getLinkType() + "",
                TextUtils.isEmpty(promotionInfoBean.getLinkUrl()) ? null : promotionInfoBean.getLinkUrl(),
                TextUtils.isEmpty(promotionInfoBean.getLongitude()) ? null : promotionInfoBean.getLongitude(),
                TextUtils.isEmpty(promotionInfoBean.getLatitude()) ? null : promotionInfoBean.getLatitude(),
                TextUtils.isEmpty(promotionInfoBean.getDetails()) ? null : promotionInfoBean.getDetails(),
                og + "",
                req,
                TextUtils.isEmpty(material_urls.toString()) ? null : material_urls.toString(),
                TextUtils.isEmpty(material_types.toString()) ? null : material_types.toString(),
                souresUrl,
                new Observer<CreatRqResultBean>() {
                    @Override

                    public void onCompleted() {
                        closeProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(CreateReqOtherActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(CreatRqResultBean vCodeBenan) {
                        if (vCodeBenan.getCode() == 1) {
                            startActivity(new Intent(CreateReqOtherActivity.this, RequirementCreteadSucessActivity.class)
                                    .putExtra("creatRqResultBean", vCodeBenan)
                                    .putExtra("title", title)
                            );
                        }
                    }
                });
    }

    private String getPicListString() {
        String str = "";
        for (int a = 0; a < picList.size(); a++) {
            if (picList.get(a).getType() == 1) {
                if (null != picList.get(a).getUrl() && picList.get(a).getUrl() != "null")
                    str += picList.get(a).getUrl() + ",";
            }

        }
        Logger.e(str);
        return str;
    }

    private void sumbit() {
        if (TextUtils.isEmpty(title)) {
            Utils.toast(this, "请输入作品标题");
            return;
        }
        if (TextUtils.isEmpty(req)) {
            Utils.toast(this, "请输入设计要求");
            return;
        }
        save();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TITLE_REQUEST_RESULT) {
                if (null != data) {
                    title = data.getStringExtra("title");
                    //      if (!title.isEmpty()) {
                    reqTitleTv.setText(title + "");
                    //        }
                }
            } else if (requestCode == PT_REQUEST_RESULT) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        PromotionInfoBean pti = (PromotionInfoBean) d.get("promotionInfoBean");
                        if (pti != null) {
                            promotionInfoBean = pti;
                            if (promotionInfoBean.getOpen())
                                reqPromotionTv.setText(promotionInfoBean.getIntroduce());
                            else
                                reqPromotionTv.setText("关");

                            Logger.e(promotionInfoBean.getLinkUrl() + "");
                        }
                    }

                }
            } else if (requestCode == OG_REQUEST_RESULT) {
                og = data.getIntExtra("orientation", 0);
                if (og == 0)
                    reqOgTv.setText("横(1920×1080)");
                else
                    reqOgTv.setText("竖(1080×1920)");
            } else if (requestCode == DT_REQUEST_RESULT) {

                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        ArrayList<PicBean> pic = (ArrayList<PicBean>) d.get("picList");
                        if (pic != null) {
                            picList = pic;
                            reqResourseTv.setText((picList.size() == 6 ? picList.size() + "" : (picList.size() - 1) + ""));
                        }
                    }

                }
            } else if (requestCode == RQ_REQUEST_RESULT) {
                if (null != data) {
                    req = data.getStringExtra("req");
                    reqPdCountTv.setText(req + "");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
