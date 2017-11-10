package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.dialog.Ok_Cancel_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.push.PushReqSelectTimeActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 联动定投
* */
public class LiandongbaoDingtouActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.start_p_tv)
    TextView mStartPTv;
    @BindView(R.id.start_p_v_tv)
    TextView mStartPVTv;
    @BindView(R.id.end_p_tv)
    TextView mEndPTv;
    @BindView(R.id.end_p_v_tv)
    TextView mEndPVTv;
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.arr_right_iv)
    ImageView mArrRightIv;
    @BindView(R.id.req_time_dt_rl)
    RelativeLayout mReqTimeDtRl;
    @BindView(R.id.linear_toufang_layout)
    LinearLayout mLinearToufangLayout;
    @BindView(R.id.tv_toufang)
    TextView mTvToufang;
    @BindView(R.id.tv_guanxi)
    TextView mTvGuanxi;
    @BindView(R.id.tv_notice)
    TextView mTvNotice;
    @BindView(R.id.linear_notice_layout)
    LinearLayout mLinearNoticeLayout;

    private String linkid, linkname, url, link_snap_id,startdate,enddate;//其中url指的是头像路径
    private static int TIME_REQUEST_RESULT = 1000;
    private SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays;//选择的时间范围

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_dingtou_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        mToolbarTitle.setText("联动定投");
        initDatas();
        EventBus.getDefault().register(this);
    }//

    private void initDatas() {

        linkid = getIntent().getStringExtra("linkid");
        linkname = getIntent().getStringExtra("linkname");
        url = getIntent().getStringExtra("url");
        mTvName.setText(linkname);

        Glide.with(mContext)
                .load(NetConstant.BASE_USER_RESOURE + url + Utils.getThumbnail(200, 200))
                .placeholder(R.mipmap.img_liandong_fm)
                .error(R.mipmap.img_liandong_fm)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mIvBg);

        initTime();
    }

    private void initTime() {
        mStartPVTv.setText(TimeUtil.getCurrentTime("yyyy-MM-dd") + " " + Utils.getWeekOfDate(TimeUtil.getCurrentTime("yyyy-MM-dd"), "yyyy-MM-dd"));
        String endTime = TimeUtil.timeAddSubtract(TimeUtil.getCurrentTime("yyyy-MM-dd"), 7 - TimeUtil.getDayOfWeek());
        mEndPVTv.setText(endTime + " " + Utils.getWeekOfDate(endTime, "yyyy-MM-dd"));
        selectedDays = new SimpleMonthAdapter.SelectedDays<>();
        selectedDays.setFirst(new SimpleMonthAdapter.CalendarDay(System.currentTimeMillis()));
        Logger.e(Utils.StrToDay(endTime) + "");
        selectedDays.setLast(new SimpleMonthAdapter.CalendarDay(TimeUtil.getYear(), Utils.StrToMonth(endTime), Utils.StrToDay(endTime)));

        startdate=TimeUtil.getCurrentTime("yyyyMMdd");
        enddate=endTime.replace("-","");
        LogUtil.d("时间："+startdate+"=====:"+enddate);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SetLiandongDingtouEvent(Event.SetLiandongDingtouEvent message) {
        LogUtil.d("联动定投事件收听:" + message.mlink_snap_id);
        link_snap_id = message.mlink_snap_id;
        mTvGuanxi.setText(message.mlink_snap_name);
        mTvNotice.setText("按“"+message.mlink_snap_name+"”关系将作品投放到对应的终端联动显示");
        mLinearNoticeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TIME_REQUEST_RESULT) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDay = (SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay>) d.get("selectedDays");
                        if (selectedDay != null) {
                            selectedDays = selectedDay;
                            mStartPVTv.setText(TimeUtil.unixTimestamp2BeijingTime(selectedDay.getFirst().getDate().getTime(), "yyyy-MM-dd") + " " + Utils.getWeekOfDate(selectedDay.getFirst().getDate()));
                            mEndPVTv.setText(TimeUtil.unixTimestamp2BeijingTime(selectedDay.getLast().getDate().getTime(), "yyyy-MM-dd") + " " + Utils.getWeekOfDate(selectedDay.getLast().getDate()));
                            int w = (int) Utils.countTwoDayWeek(selectedDay.getFirst().getDate(), selectedDay.getLast().getDate());
                            mTimeTv.setText("跨" + w + "周");
                            startdate=TimeUtil.unixTimestamp2BeijingTime(selectedDay.getFirst().getDate().getTime(), "yyyyMMdd");
                            enddate=TimeUtil.unixTimestamp2BeijingTime(selectedDay.getLast().getDate().getTime(), "yyyyMMdd");
                            LogUtil.d("时间："+startdate+"=====:"+enddate);
                        }
                    }
                }
            }
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.req_time_dt_rl, R.id.linear_toufang_layout, R.id.tv_toufang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.req_time_dt_rl:
                startActivityForResult(new Intent(LiandongbaoDingtouActivity.this, PushReqSelectTimeActivity.class).putExtra("selectedDays", selectedDays), TIME_REQUEST_RESULT);
                break;
            case R.id.linear_toufang_layout:
                Intent intent = new Intent(LiandongbaoDingtouActivity.this, LiandongbaoDdingtouChooseActivity.class);
                intent.putExtra("linkid", linkid);
                intent.putExtra("linkname", linkname);
                intent.putExtra("url", url);
                startActivity(intent);
                break;
            case R.id.tv_toufang:
                if(mTvGuanxi.getText().toString().equals("投放关系")){
                    Toast.makeText(mContext,"请设置投放关系",Toast.LENGTH_SHORT).show();
                    return;
                }
                setLiandongbaoGuanxi(link_snap_id,"3",startdate,enddate);
                break;
        }
    }

    //设置联动投放关系
    private void setLiandongbaoGuanxi(String link_snap_id,String range_type,String startdate,String enddate) {
        RxRetrofitClient.getInstance(mContext).setLiandongbaoGuanxi(link_snap_id,range_type,startdate,enddate,1+"", new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(mContext, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("设定成功");
                if(emptyBean.code==1){
                    Intent intent=new Intent(LiandongbaoDingtouActivity.this,LiandongbaoSucceedActivity.class);
                    intent.putExtra("type","1");
                    intent.putExtra("name",linkname);
                    startActivity(intent);
                    finish();
                }else {
                    showError();
                }
            }
        });
    }

    private void showError()
    {
        Ok_Cancel_Dialog ok_cancel_dialog=new Ok_Cancel_Dialog(mContext,"该历史联动投放的某台终端已被删除。\n投放失败！请新建联动关系后重新投放。");
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {

            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
