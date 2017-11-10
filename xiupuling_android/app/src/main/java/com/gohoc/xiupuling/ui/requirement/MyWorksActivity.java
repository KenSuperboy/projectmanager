package com.gohoc.xiupuling.ui.requirement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.MyWorkAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.MyWorksBean;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.callback.Callback2;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.dialog.Delete_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.account.MemberAuthorizationActivity;
import com.gohoc.xiupuling.ui.account.MemberShopActivity;
import com.gohoc.xiupuling.ui.push.PushByDinActivity;
import com.gohoc.xiupuling.ui.push.PushReqEditActivity;
import com.gohoc.xiupuling.ui.push.PushSelectMenuActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;

public class MyWorksActivity extends BasicActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.relative_empty_layout)
    RelativeLayout mRelativeEmptyLayout;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    private PushNormlBean pushNormlBean;
    private UserBaseBean.DataBean userBaseBeans;
    private Window window;
    private AlertDialog dialog;
    private int type;
    private List<MyWorksBean.DataBean> dataBeenlist = new ArrayList<>();
    private int EDIT_REQUEST_RESULT = 100;
    private MyWorkAdapter mMyWorkAdapter;
    private boolean isload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        initView();
    }

    private void initView()
    {
        mToolbarRight.setVisibility(View.INVISIBLE);

        type = getIntent().getIntExtra("type", 0);
        if(type==1){
            toolbarTitle.setText("可投放作品");
        }else {
            toolbarTitle.setText("我的作品库");
        }

        try {
            pushNormlBean = (PushNormlBean) getIntent().getExtras().get("pushNormlBean");
        } catch (Exception e) {
        }

        mMyWorkAdapter=new MyWorkAdapter(MyWorksActivity.this);
        listView.setAdapter(mMyWorkAdapter);
        mMyWorkAdapter.setCallback(new Callback2() {
            @Override
            public void callBack(String string1, String string2) {
                if(string1.equals("0")&&!TextUtils.isEmpty(string2)){
                    //选择框
                    if (getIntent().getIntExtra("type", 0) == 0) {
                        pushNormlBean = new PushNormlBean();
                        showDialogs(Integer.parseInt(string2));
                    } else {
                        int position=Integer.parseInt(string2);
                        pushNormlBean.setOrientation(dataBeenlist.get(position).getOrientation());
                        pushNormlBean.setRq_id(dataBeenlist.get(position).getRq_id());
                        pushNormlBean.setName(dataBeenlist.get(position).getActivity_title());
                        pushNormlBean.setWork_id(dataBeenlist.get(position).getWork_id());
                        pushNormlBean.setPlaytime(dataBeenlist.get(position).getPlaytime());
                        pushNormlBean.setCover_url(dataBeenlist.get(position).getMaterial_store_url());
                        startActivity(new Intent(MyWorksActivity.this, PushByDinActivity.class).putExtra("pushNormlBean", pushNormlBean));
                    }
                }else {
                    //修改x相关
                    editWorkMessage(dataBeenlist.get(Integer.parseInt(string2)));
                }
            }
        });
    }

    private void getmyavailableworklist() {
        RxRetrofitClient.getInstance(this).getmyavailableworklist(null, new Observer<MyWorksBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                setEmptyData();
                Utils.toast(MyWorksActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(MyWorksBean myWorksBean) {
                if (myWorksBean.getCode() == 1) {
                    isload=true;
                    if (myWorksBean.getData().size() > 0) {
                        if(dataBeenlist!=null){
                            dataBeenlist.clear();
                        }
                        if (getIntent().getIntExtra("type", 0) == 1) {//需要帅选
                            for (int a = 0; a < myWorksBean.getData().size(); a++) {
                                if (myWorksBean.getData().get(a).getOrientation() == pushNormlBean.getOrientation())
                                    dataBeenlist.add(myWorksBean.getData().get(a));
                            }
                            if(dataBeenlist.size()==0){
                                setEmptyData();
                            }
                        } else{
                            setMyvisible(View.VISIBLE, View.GONE, R.mipmap.icon_zuopinku);
                            dataBeenlist.addAll(myWorksBean.getData());
                        }
                        initList();
                    } else {
                        setEmptyData();
                    }
                }
            }
        });
    }

    public void setEmptyData() {
        if (type == 0) {//默认情况进来
            setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_zuopinku);
            SpannableString spannableString = new SpannableString("亲，您还未有任何作品\n快来“自己制作”吧");
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品\n" + "快来“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品\n" + "快来“").length(), ("亲，您还未有任何作品\n" + "快来“自己制作").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品\n" + "快来“自己制作").length(), ("亲，您还未有任何作品\n" + "快来“自己制作”吧").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvEmpty.setText(spannableString);
        } else if (type == 1) {
            if (pushNormlBean.getOrientation() == 0) {
                setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_hengping);
                mTvEmpty.setText("没有可投放到该终端的横屏作品");
            } else if (pushNormlBean.getOrientation() == 1) {
                setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_shuping);
                mTvEmpty.setText("没有可投放到该终端的竖屏作品");
            }
        }
    }

    public void setMyvisible(int data, int empty, int src) {
        listView.setVisibility(data);
        mRelativeEmptyLayout.setVisibility(empty);
        mIvEmpty.setImageResource(src);
    }

    private void initList() {
        LogUtil.d("数据设置");
        mMyWorkAdapter.setmLists(dataBeenlist);
    }


    public void showDialogs(final int postion) {


        BGAAlertController alertController = new BGAAlertController(this, "", "", BGAAlertController.AlertControllerStyle.ActionSheet);
        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
        alertController.addAction(new BGAAlertAction("立即投放", BGAAlertAction.AlertActionStyle.Default, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                pushNormlBean.setIndexFlage(2);
                pushNormlBean.setOrientation(dataBeenlist.get(postion).getOrientation());
                pushNormlBean.setRq_id(dataBeenlist.get(postion).getRq_id());
                pushNormlBean.setName(dataBeenlist.get(postion).getActivity_title());
                pushNormlBean.setWork_id(dataBeenlist.get(postion).getWork_id());
                pushNormlBean.setPlaytime(dataBeenlist.get(postion).getPlaytime());
                pushNormlBean.setCover_url(dataBeenlist.get(postion).getMaterial_store_url());

                startActivity(new Intent(MyWorksActivity.this, PushSelectMenuActivity.class).putExtra("pushNormlBean", pushNormlBean));
            }
        }));
        alertController.addAction(new BGAAlertAction("删除作品", BGAAlertAction.AlertActionStyle.Default, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {
                deleteShow(dataBeenlist.get(postion).getActivity_title(),dataBeenlist.get(postion).getWork_id(),dataBeenlist.get(postion).getRq_id(),postion);
                //createInfoDistribute(dataBeenlist.get(postion).getRq_id(), dataBeenlist.get(postion).getWork_id());
            }
        }));
        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
            @Override
            public void onClick() {

            }
        }));
        alertController.setCancelable(true);
        alertController.show();

    }

    private void deleteShow(String name, final String work_id, final String rq_id, final int position) {
        //默认情况进来  deleteAvailableWork
        SpannableString spannableString = new SpannableString("删除作品" + name + "，将无法恢复");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gallery_text_gray)), 0, ("删除作品").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("删除作品").length(), ("删除作品" + name).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gallery_text_gray)), ("删除作品" + name).length(), ("删除作品" + name + "，将无法恢复").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //mTvEmpty.setText(spannableString);

        Delete_Dialog ok_cancel_dialog = new Delete_Dialog(MyWorksActivity.this, spannableString);
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                LogUtil.d("删除作品：");
                deleteAvailableWork(work_id,rq_id,position);
            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }

    private void deleteAvailableWork(String work_id, String rq_id, final int position) {
        RxRetrofitClient.getInstance(this).deleteAvailableWork(work_id,rq_id, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                setEmptyData();
                Utils.toast(MyWorksActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                if (emptyBean.code == 1) {
                    mMyWorkAdapter.remove(position);
                    dataBeenlist.remove(position);
                    if(mMyWorkAdapter.getCount()==0){
                        setEmptyData();
                    }
                }
            }
        });
    }

    private void createInfoDistribute(String rq_id, String work_id) {
        RxRetrofitClient.getInstance(this).createInfoDistribute(rq_id, work_id, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MyWorksActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(MyWorksActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                    EventBus.getDefault().post(new Event.RefreshPushListEvent(null));
                    EventBus.getDefault().post(new Event.MainIndex(4));
                    Intent intent = new Intent(MyWorksActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.tv_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.tv_empty:
                updateUserInfo(MyWorksActivity.this);
                break;
        }
    }


    public void updateUserInfo(Context context) {
        RxRetrofitClient.getInstance(context).userbasicinfo(new Observer<UserBaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(UserBaseBean userBaseBean) {
                if (userBaseBean.getCode() == 1) {
                    Credential.getInstance().setCurUser(userBaseBean);
                    userBaseBeans = Credential.getInstance().getCurUser(MyWorksActivity.this).getData();
                    EventBus.getDefault().post(new Event.UserEvent());
                    if (userBaseBean.getData().getWork_left_cnt() > 0)
                        startActivity(new Intent(MyWorksActivity.this, CreateReqActivity.class));
                    else
                        showShopPop();
                }
            }
        });
    }

    private void showShopPop() {
        dialog = new AlertDialog.Builder(MyWorksActivity.this,
                R.style.dialogTips).create();
        dialog.show();
        window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(false);
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容

        window.setContentView(R.layout.dialog_works_limt_alert);

        window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MyWorksActivity.this, MemberShopActivity.class));

            }
        });
        window.findViewById(R.id.check_auth_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MyWorksActivity.this, MemberAuthorizationActivity.class));
            }
        });
        window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        SpanUtils su = new SpanUtils();
        su.append("您当前作品剩余数为" + userBaseBeans.getWork_left_cnt() + ",如需获得更多的作品拥有数,请前往会员商城或参与")
                .append("官方活动").setForegroundColor(Color.parseColor("#38b2fd"))
                .append("扩充。");
        TextView tips_title_tv = (TextView) window.findViewById(R.id.tips_title_tv);
        tips_title_tv.setText(su.create());
        TextView shop_count_tv = (TextView) window.findViewById(R.id.shop_count_tv);
        su = new SpanUtils();
        su.append("作品剩余数 ").append(userBaseBeans.getWork_left_cnt() + "").setForegroundColor(Color.parseColor("#38b2fd"));
        shop_count_tv.setText(su.create());
        su = new SpanUtils();
        TextView check_auth_tv = (TextView) window.findViewById(R.id.check_auth_tv);
        check_auth_tv.setText(su.append(" 查询其它").setForegroundColor(Color.parseColor("#929292")).append("会员权限").setForegroundColor(Color.parseColor("#38b2fd")).create());
    }

    private void editWorkMessage(MyWorksBean.DataBean dataBean) {
        PromotionInfoBean promotionInfoBean = new PromotionInfoBean();
        promotionInfoBean.setRq_id(dataBean.getRq_id());
        promotionInfoBean.setWork_id(dataBean.getWork_id());
        promotionInfoBean.setActivityTitle(dataBean.getWork_name());
        promotionInfoBean.setOpen(dataBean.getActivity_onoff() == 0 ? true : false);
        if (dataBean.getActivity_onoff()==0) {//开启  网址  或实体店
            promotionInfoBean.setLinkType(Integer.parseInt(dataBean.getActivity_nav_type()));
        }
        promotionInfoBean.setLatitude(dataBean.getActivity_nav_latitude() == null ? null : dataBean.getActivity_nav_latitude() + "");
        promotionInfoBean.setLongitude(dataBean.getActivity_nav_longitude() == null ? null : dataBean.getActivity_nav_longitude() + "");
        //promotionInfoBean.setLcoationName(reqBeanTemp.getActivity_brand());
        promotionInfoBean.setDetails(dataBean.getActivity_detail());
        promotionInfoBean.setKeyWord(dataBean.getActivity_brand() + "");
        promotionInfoBean.setIntroduce(dataBean.getActivity_content() + "");
        if(dataBean.getActivity_nav_type()!=null&&(dataBean.getActivity_nav_type()+"")!="null"){
            if (dataBean.getActivity_nav_type().equals("0"))//实体店
                promotionInfoBean.setLcoationName(dataBean.getActivity_nav_content());
            else
                promotionInfoBean.setLinkUrl(dataBean.getActivity_nav_content() + "");//网址
        }
        startActivityForResult(new Intent(MyWorksActivity.this, PushReqEditActivity.class).putExtra("promotionInfoBean", promotionInfoBean), EDIT_REQUEST_RESULT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getmyavailableworklist();
    }
}
