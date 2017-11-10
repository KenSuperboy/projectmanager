package com.gohoc.xiupuling.ui.push;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.CreatRqResultBean;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.requirement.CreateReqPromotionActivity;
import com.gohoc.xiupuling.ui.requirement.CreateReqTitleActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class PushReqEditActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.req_title_tv)
    TextView reqTitleTv;
    @BindView(R.id.req_title_ll)
    LinearLayout reqTitleLl;
    @BindView(R.id.req_activity_tv)
    TextView reqActivityTv;
    @BindView(R.id.req_activity_ll)
    LinearLayout reqActivityLl;
    private String work_id;
    private String rq_id;
    private static int TITLE_REQUEST_RESULT = 1000;
    private static int PT_REQUEST_RESULT = 1001;
    private String title = "";
    private PromotionInfoBean promotionInfoBean;
    private  boolean isChang=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_req_edit);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("修改信息");
        promotionInfoBean = (PromotionInfoBean) getIntent().getExtras().get("promotionInfoBean");
        initDates();
    }

    private void initDates() {
/*        promotionInfoBean.setActivityTitle(reqBeanTemp.getActivity_title());
        promotionInfoBean.setOpen(reqBeanTemp.getActivity_onoff() == 0 ? true : false);
        promotionInfoBean.setLinkType(reqBeanTemp.getActivity_nav_type());
        promotionInfoBean.setLatitude(reqBeanTemp.getActivity_nav_latitude()==null?null: reqBeanTemp.getActivity_nav_latitude()+"");
        promotionInfoBean.setLongitude(reqBeanTemp.getActivity_nav_longitude()==null?null: reqBeanTemp.getActivity_nav_longitude()+"");
        //promotionInfoBean.setLcoationName(reqBeanTemp.getActivity_brand());
        promotionInfoBean.setDetails(reqBeanTemp.getActivity_detail());
        promotionInfoBean.setKeyWord(reqBeanTemp.getActivity_brand());
        promotionInfoBean.setIntroduce(reqBeanTemp.getActivity_content());
        if (promotionInfoBean.getLinkType() == 1)
            promotionInfoBean.setLinkUrl(reqBeanTemp.getActivity_nav_content());
        else
            promotionInfoBean.setLcoationName(reqBeanTemp.getActivity_nav_content());*/

        title = promotionInfoBean.getActivityTitle();
        reqTitleTv.setText(TextUtils.isEmpty(promotionInfoBean.getActivityTitle()) ? "" :promotionInfoBean.getActivityTitle());
        reqActivityTv.setText((promotionInfoBean.getOpen()) ? promotionInfoBean.getIntroduce() :"关");

    }

    @OnClick({R.id.toolbar_left_title, R.id.req_title_ll, R.id.req_activity_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.req_title_ll:
                startActivityForResult(new Intent(PushReqEditActivity.this, CreateReqTitleActivity.class).putExtra("title", title), TITLE_REQUEST_RESULT);
                break;
            case R.id.req_activity_ll:
                startActivityForResult(new Intent(PushReqEditActivity.this, CreateReqPromotionActivity.class).putExtra("promotionInfoBean", promotionInfoBean), PT_REQUEST_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TITLE_REQUEST_RESULT) {
                title = data.getStringExtra("title");
                reqTitleTv.setText(title);
                promotionInfoBean.setActivityTitle(title);
                save_title();
            } else {
                promotionInfoBean= (PromotionInfoBean) data.getExtras().get("promotionInfoBean");
                reqActivityTv.setText(TextUtils.isEmpty(promotionInfoBean.getIntroduce())|| !promotionInfoBean.getOpen() ? "关" : promotionInfoBean.getIntroduce());
                save();
            }
        }
    }

    private void save_title()
    {
        RxRetrofitClient.getInstance(this).updateWorkname(promotionInfoBean.getWork_id(), title,
                new Observer<VCodeBenan>() {
                    @Override

                    public void onCompleted() {
                        closeProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(PushReqEditActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(VCodeBenan vCodeBenan) {

                        if (vCodeBenan.getCode() == 1) {
                            EventBus.getDefault().post(new Event.RefreshPushListEvent(promotionInfoBean));
                            Utils.toast(PushReqEditActivity.this, "信息更改成功");
                            setResult(RESULT_OK,new Intent().putExtra("promotionInfoBean",promotionInfoBean));
                        }else
                            Utils.toast(PushReqEditActivity.this, vCodeBenan.getMessage());
                    }
                });
    }

    private void save() {
        RxRetrofitClient.getInstance(this).requirementUpdate(promotionInfoBean.getRq_id(),null, promotionInfoBean.getActivityTitle(),
                promotionInfoBean.getOpen() == true ? "0" : "1",
                TextUtils.isEmpty(promotionInfoBean.getKeyWord()) || promotionInfoBean.getOpen() == false ? null : promotionInfoBean.getKeyWord(),
                TextUtils.isEmpty(promotionInfoBean.getIntroduce())  || promotionInfoBean.getOpen() == false? null : promotionInfoBean.getIntroduce(),
                promotionInfoBean.getOpen() == false? null :promotionInfoBean.getLinkType() + "",
                promotionInfoBean.getOpen() == false?null: promotionInfoBean.getLinkType()==0? promotionInfoBean.getLcoationName() : promotionInfoBean.getLinkUrl(),
                TextUtils.isEmpty(promotionInfoBean.getLongitude()) || promotionInfoBean.getOpen() == false ? null : promotionInfoBean.getLongitude(),
                TextUtils.isEmpty(promotionInfoBean.getLatitude()) || promotionInfoBean.getOpen() == false ? null : promotionInfoBean.getLatitude(),
                TextUtils.isEmpty(promotionInfoBean.getDetails()) || promotionInfoBean.getOpen() == false ? null : promotionInfoBean.getDetails(),
                null,
                null,
                null,
                null,
                null,
                new Observer<CreatRqResultBean>() {
                    @Override

                    public void onCompleted() {
                        closeProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(PushReqEditActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(CreatRqResultBean vCodeBenan) {

                        if (vCodeBenan.getCode() == 1) {
                            EventBus.getDefault().post(new Event.RefreshPushListEvent(promotionInfoBean));
                            Utils.toast(PushReqEditActivity.this, "信息更改成功");
                            setResult(RESULT_OK,new Intent().putExtra("promotionInfoBean",promotionInfoBean));
                        }else
                            Utils.toast(PushReqEditActivity.this, vCodeBenan.getMessage());
                    }
                });
    }


}
