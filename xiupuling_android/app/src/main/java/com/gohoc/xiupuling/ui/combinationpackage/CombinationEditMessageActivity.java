package com.gohoc.xiupuling.ui.combinationpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.KvBean;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListBean;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.dialog.Ok_Cancel_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.linkpackage.LiandongbaoAddActivity;
import com.gohoc.xiupuling.ui.linkpackage.LiandongbaoEditBgActivity;
import com.gohoc.xiupuling.ui.terminal.SelectWeekActivity;
import com.gohoc.xiupuling.ui.terminal.SettingTerminalOgActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.suke.widget.SwitchButton;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 作品组合包信息
* */
public class CombinationEditMessageActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_logo_layout)
    LinearLayout mLinearLogoLayout;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.linear_name_layout)
    LinearLayout mLinearNameLayout;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.linear_type_layout)
    LinearLayout mLinearTypeLayout;
    @BindView(R.id.sb_shiduan)
    SwitchButton mSbShiduan;
    @BindView(R.id.start_time_tv)
    TextView mStartTimeTv;
    @BindView(R.id.start_time_ll)
    LinearLayout mStartTimeLl;
    @BindView(R.id.start_time_loop_h)
    LoopView mStartTimeLoopH;
    @BindView(R.id.start_time_loop_m)
    LoopView mStartTimeLoopM;
    @BindView(R.id.start_time_pl)
    PercentRelativeLayout mStartTimePl;
    @BindView(R.id.end_time_tv)
    TextView mEndTimeTv;
    @BindView(R.id.end_time_ll)
    LinearLayout mEndTimeLl;
    @BindView(R.id.end_time_loop_h)
    LoopView mEndTimeLoopH;
    @BindView(R.id.end_time_loop_m)
    LoopView mEndTimeLoopM;
    @BindView(R.id.end_time_pl)
    PercentRelativeLayout mEndTimePl;
    @BindView(R.id.rp_tv)
    TextView mRpTv;
    @BindView(R.id.rp_time_ll)
    LinearLayout mRpTimeLl;
    @BindView(R.id.sutdown_ll)
    LinearLayout mSutdownLl;
    @BindView(R.id.sb_pingbi)
    SwitchButton mSbPingbi;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    private String package_id;
    private String package_name;
    private String cover_url,temp_url;
    private String orientation;
    private String is_time_limit;
    private String start_time;
    private String end_time;
    private String repeat_weekday;
    private String is_ignore_other_work;
    private int EDIT_NAME_REQUEST = 100, EDIT_BG_REQUEST = 101,ORIENTATUION_REQUEST_RESULT=102,TIME_REQUEST_RESULT=103;

    private CombinationListBean.DataBean mDataBean;

    private ArrayList<String> hourTime = new ArrayList<>();
    private ArrayList<String> minuteTime = new ArrayList<>();
    private String startTimeHour = "08";
    private String startTimeMinute = "00";

    private String endTimeHour = "18";
    private String endTimeMinute = "00";

    private ArrayList<KvBean> rpdata;//重复运行时间
    private String weekV = "";
    private String[] weekN = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private String[] weekVs = {"0", "0", "0", "0", "0", "0", "0"};
    private boolean isData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination_edit_message_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("编辑组合包信息");
        setStatusColor(R.color.colorPrimary);

        initView();
        initListener();
        initTimer();
        initData();
        setMyBg();
    }

    //默认情况进来
    /*SpannableString spannableString = new SpannableString("亲，您还未有任何作品\n快来“自己制作”吧");
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品\n" +"快来“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品\n" +"快来“").length(),("亲，您还未有任何作品\n" +"快来“自己制作").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品\n" +"快来“自己制作").length(),("亲，您还未有任何作品\n" +"快来“自己制作”吧").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    mTvEmpty.setText(spannableString);*/

    private void initView() {

    }

    private void initListener()
    {
        mSbPingbi.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    is_ignore_other_work="1";
                }else {
                    is_ignore_other_work="0";
                }
            }
        });

        mSbShiduan.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    LogUtil.d("时段监听11111");
                    is_time_limit="1";
                    mSutdownLl.setVisibility(View.VISIBLE);
                }else {
                    LogUtil.d("时段监听22222");
                    is_time_limit="0";
                    mSutdownLl.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData() {
        isData=getIntent().getBooleanExtra("isData",false);
        mDataBean = (CombinationListBean.DataBean) getIntent().getExtras().get("data");

        package_id = mDataBean.package_id;
        package_name = mDataBean.package_name;
        cover_url = mDataBean.cover_url + "";
        temp_url=cover_url;
        orientation = mDataBean.orientation == false ? 0 + "" : 1 + "";
        is_time_limit = mDataBean.is_time_limit == false ? 0 + "" : 1 + "";
        start_time = mDataBean.start_time + "";
        end_time = mDataBean.end_time + "";
        repeat_weekday = mDataBean.repeat_weekday + "";
        is_ignore_other_work = mDataBean.is_ignore_other_work == false ? 0 + "" : 1 + "";

        if(orientation.equals("0")){
            mTvType.setText("横(1920*1080)");
        }else if(orientation.equals("1")){
            mTvType.setText("竖(1080*1920)");
        }
        if(is_ignore_other_work.equals("0")){
            mSbPingbi.setChecked(false);
        }else if(is_ignore_other_work.equals("1")){
            mSbPingbi.setChecked(true);
        }
        if(is_time_limit.equals("0")){
            mSbShiduan.setChecked(false);
            mSutdownLl.setVisibility(View.GONE);
        }else if(is_time_limit.equals("1")){
            mSbShiduan.setChecked(true);
            mSutdownLl.setVisibility(View.VISIBLE);
        }
        mTvName.setText(package_name);

        /*时间相关*/
        if(!TextUtils.isEmpty(start_time)&&!TextUtils.isEmpty(end_time)&&!start_time.equals("null")&&!end_time.equals("null")){
            mStartTimeTv.setText(start_time);
            mEndTimeTv.setText(end_time);
            startTimeHour = start_time.split(":")[0];
            startTimeMinute = start_time.split(":")[1];
            endTimeHour = end_time.split(":")[0];
            endTimeMinute = end_time.split(":")[1];
        }else {
            mStartTimeTv.setText(startTimeHour+":"+startTimeMinute);
            mEndTimeTv.setText(endTimeHour+":"+endTimeMinute);
        }


        LogUtil.d(minuteTime+"====="+hourTime+":多少："+startTimeHour+"=====:"+startTimeMinute+"====:"+endTimeHour+"====:"+endTimeMinute);

        for (int a = 0; a < hourTime.size(); a++) {
            if (startTimeHour.equals(hourTime.get(a))){
                LogUtil.d("条件满足11111");
                mStartTimeLoopH.setCurrentPosition(a);
            }

            if (endTimeHour.equals(hourTime.get(a))){
                LogUtil.d("条件满足22222");
                mEndTimeLoopH.setCurrentPosition(a);
            }
        }

        for (int a = 0; a < minuteTime.size(); a++) {
            if (startTimeMinute.equals(minuteTime.get(a)))
                mStartTimeLoopM.setCurrentPosition(a);

            if (endTimeMinute.equals(minuteTime.get(a)))
                mEndTimeLoopM.setCurrentPosition(a);
        }


        //处理自动开关机天数重复  (1111111)
        rpdata = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        for (int a = 0; a < weekN.length; a++) {
            if (repeat_weekday == null || repeat_weekday.toString().length() < 6) {   //新终端为null 还有各种 奇葩数据
                LogUtil.d("进来添加======");
                rpdata.add(new KvBean(weekN[a], "1").setCheck(true));
            } else {
                String x = repeat_weekday.toString().toCharArray()[a] + "";   //取字符判断是否为1
                if (x.equals("1")) {
                    rpdata.add(new KvBean(weekN[a], repeat_weekday.toString().toCharArray()[a] + "").setCheck(true));
                    sb.append(weekN[a] + ",");
                } else
                    rpdata.add(new KvBean(weekN[a], "0"));
            }
        }
        if (sb.length() > 1) {
            weekV = String.valueOf(repeat_weekday);
            //判断全部选择
            if (weekV.startsWith("1111111"))
                mRpTv.setText("每天");
            else if (weekV.startsWith("1111100")) //判断下工作日
                mRpTv.setText("工作日");
            else if (weekV.endsWith("0000011"))
                mRpTv.setText("周末");
            else
                mRpTv.setText(sb.toString().substring(0, sb.toString().length() - 1));
        }else {
            //weekV="1111111";
            mRpTv.setText("每天");

            /*for (int a = 0; a < weekN.length; a++) {
                rpdata.add(new KvBean(weekN[a],  "1").setCheck(true));
            }*/
        }
    }

    private void setMyBg() {
        Glide.with(mContext)
                .load(NetConstant.BASE_USER_RESOURE + cover_url + Utils.getThumbnail(200, 200))
                .placeholder(R.mipmap.icon_zuhebao)
                .error(R.mipmap.icon_zuhebao)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_bg);
    }

    //编辑屏联动包里面的基本信息
    private void editLinkPackage(final String package_id,final String package_name,final String mCover_url,final String orientation,final String is_time_limit,final String start_time,final String end_time,final String repeat_weekday,final String is_ignore_other_work) {
        RxRetrofitClient.getInstance(CombinationEditMessageActivity.this).editCombinationPackage(package_id, package_name, cover_url,orientation,is_time_limit,start_time,end_time,repeat_weekday,is_ignore_other_work, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CombinationEditMessageActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("编辑信息:");
                if (emptyBean.code == 1) {
                    LogUtil.d("编辑信息:修改成功:"+orientation);
                    Toast.makeText(mContext,"修改成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();

                    /*intent.putExtra("url",cover_url);
                    intent.putExtra("name",mTvName.getText().toString());
                    intent.putExtra("or",mTvType.getText().toString());
                    intent.putExtra("time",mSbPingbi.isChecked()?"独占时段播放":"非独占时段轮播");*/

                     mDataBean.package_id=package_id;
                     mDataBean.package_name=package_name;
                     mDataBean.cover_url =cover_url;

                    if(orientation.equals("0")||orientation=="0"){
                        mDataBean.orientation =false;
                    }else {
                        mDataBean.orientation =true;
                    }
                    LogUtil.d("多少："+mDataBean.orientation);

                    if(is_time_limit.equals("0")||is_time_limit=="0"){
                        mDataBean.is_time_limit =false;
                    }else {
                        mDataBean.is_time_limit =true;
                    }

                     mDataBean.start_time=start_time;
                     mDataBean.end_time=end_time;
                     mDataBean.repeat_weekday=repeat_weekday;

                    if(is_ignore_other_work.equals("0")||is_ignore_other_work=="0"){
                        mDataBean.is_ignore_other_work =false;
                    }else {
                        mDataBean.is_ignore_other_work =true;
                    }

                    intent.putExtra("data",mDataBean);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    LogUtil.d("回调的名称：" + data.getStringExtra("name"));
                    if (!data.getStringExtra("name").equals(package_name)) {
                        //可以修改
                        mTvName.setText(data.getStringExtra("name"));
                        package_name=data.getStringExtra("name");
                        //editLinkPackage(linkid, mTvName.getText().toString(), url);
                    }
                    break;
                case 101:
                    LogUtil.d("更换封面回调：" + data.getStringExtra("url"));
                    cover_url=data.getStringExtra("url");
                    setMyBg();
                    //editLinkPackage(linkid, mTvName.getText().toString(), data.getStringExtra("url"));
                    break;
                case 102:
                    orientation = data.getIntExtra("orientation", 0)+"";
                    LogUtil.d("得到的数据："+orientation);
                    if (orientation.equals("0")){
                        mTvType.setText("横(1920*1080)");
                    }else{
                        mTvType.setText("竖(1080*1920)");
                    }
                    break;
                case 103:

                    if (null != data) {
                        Bundle d = data.getExtras();
                        if (null != d) {
                            ArrayList<KvBean> datalsit = (ArrayList<KvBean>) d.get("data");
                            if (datalsit != null) {
                                rpdata = datalsit;
                                StringBuffer sb = new StringBuffer();
                                StringBuffer sbV = new StringBuffer();
                                for (int a = 0; a < datalsit.size(); a++) {
                                    if (datalsit.get(a).isCheck()) {
                                        sb.append(datalsit.get(a).getK() + ",");
                                        sbV.append("1");
                                    } else {
                                        sbV.append("0");
                                    }

                                }
                                weekV = sbV.toString();
                                if (sb.length() > 1) {
                                    //判断全部选择
                                    if (weekV.startsWith("1111111"))
                                        mRpTv.setText("每天");
                                    else if (weekV.startsWith("1111100")) //判断下工作日
                                        mRpTv.setText("工作日");
                                    else if (weekV.endsWith("0000011"))
                                        mRpTv.setText("周末");
                                    else
                                        mRpTv.setText(sb.toString().substring(0, sb.toString().length() - 1));
                                } else
                                    mRpTv.setText("");
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void initTimer() {
        for (int a = 0; a < 24; a++) {
            hourTime.add(String.format("%02d", a) + "");
        }
        for (int a = 0; a < 60; a++) {
            minuteTime.add(String.format("%02d", a) + "");
        }

        mStartTimeLoopH.setItems(hourTime);
        mStartTimeLoopM.setItems(minuteTime);
        mEndTimeLoopH.setItems(hourTime);
        mEndTimeLoopM.setItems(minuteTime);


        mStartTimeLoopH.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                startTimeHour = hourTime.get(i);
                mStartTimeTv.setText(startTimeHour + ":" + startTimeMinute);
            }
        });
        mStartTimeLoopM.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                startTimeMinute = minuteTime.get(i);
                mStartTimeTv.setText(startTimeHour + ":" + startTimeMinute);
                Logger.e(startTimeHour);
                // Logger.e(startTimeHour + ":" + startTimeMinute );
            }
        });
        mEndTimeLoopH.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                endTimeHour = minuteTime.get(i);
                mEndTimeTv.setText(endTimeHour + ":" + endTimeMinute);
            }
        });
        mEndTimeLoopM.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                endTimeMinute = minuteTime.get(i);
                mEndTimeTv.setText(endTimeHour + ":" + endTimeMinute);
            }
        });


        mStartTimeLoopH.setInitPosition(8);
        mStartTimeLoopM.setInitPosition(0);
        mEndTimeLoopH.setInitPosition(18);
        mEndTimeLoopM.setInitPosition(0);
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.linear_logo_layout, R.id.linear_name_layout, R.id.linear_type_layout,R.id.rp_time_ll,R.id.start_time_ll,R.id.end_time_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                if(!cover_url.equals(temp_url)){
                    backAction();
                }else {
                    finish();
                }
                break;
            case R.id.toolbar_right:
                saveMessage();
                LogUtil.d("保存信息");
                break;
            case R.id.linear_logo_layout:
                Intent intent_edit = new Intent(CombinationEditMessageActivity.this, LiandongbaoEditBgActivity.class);
                intent_edit.putExtra("url", cover_url);
                intent_edit.putExtra("type", "1");
                startActivityForResult(intent_edit, EDIT_BG_REQUEST);
                break;
            case R.id.linear_name_layout:
                Intent intent = new Intent(CombinationEditMessageActivity.this, LiandongbaoAddActivity.class);
                intent.putExtra("type", 1);//
                intent.putExtra("title", "组合包信息");//
                intent.putExtra("name", package_name);//
                startActivityForResult(intent, EDIT_NAME_REQUEST);
                break;
            case R.id.linear_type_layout:
                if(!isData){
                    if(!TextUtils.isEmpty(orientation)){
                        startActivityForResult(new Intent(CombinationEditMessageActivity.this, SettingTerminalOgActivity.class).putExtra("orientation", Integer.parseInt(orientation)), ORIENTATUION_REQUEST_RESULT);
                    }else {
                        startActivityForResult(new Intent(CombinationEditMessageActivity.this, SettingTerminalOgActivity.class).putExtra("orientation", 0), ORIENTATUION_REQUEST_RESULT);
                    }
                }else {
                    Toast.makeText(mContext,"组合包有作品时不支持更改版面",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.rp_time_ll:
                LogUtil.d("设置重复时间:"+rpdata);
                startActivityForResult(new Intent(CombinationEditMessageActivity.this, SelectWeekActivity.class).putExtra("data", rpdata), TIME_REQUEST_RESULT);
                break;
            case R.id.start_time_ll:
                if (mStartTimePl.getVisibility() == View.VISIBLE)
                    mStartTimePl.setVisibility(View.GONE);
                else
                    mStartTimePl.setVisibility(View.VISIBLE);
                break;
            case R.id.end_time_ll:
                if (mEndTimePl.getVisibility() == View.VISIBLE)
                    mEndTimePl.setVisibility(View.GONE);
                else
                    mEndTimePl.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void saveMessage()
    {
        if (Integer.parseInt(startTimeHour) > Integer.parseInt(endTimeHour)) {
            Utils.toast(this, "结束时间不能早于开始时间");
            return;
        }

        if (Integer.parseInt(startTimeHour) == Integer.parseInt(endTimeHour)) {
            if (Integer.parseInt(startTimeMinute) >= Integer.parseInt(endTimeMinute)) {
                Utils.toast(this, "结束时间不能早于开始时间");
                return;
            }
        }
        start_time=mStartTimeTv.getText().toString();
        end_time=mEndTimeTv.getText().toString();
        repeat_weekday=weekV;
        LogUtil.d("相关信息："+package_id+"====:"+package_name+"====:"+cover_url+"====:"+orientation+"====:"+is_time_limit+"====:"+start_time+"====:"+end_time+"====:"+weekV+"====:"+is_ignore_other_work);
        editLinkPackage(package_id,package_name,cover_url,orientation,is_time_limit,start_time,end_time,repeat_weekday,is_ignore_other_work);
    }

    Ok_Cancel_Dialog mOk_cancel_dialog;
    private void backAction()
    {
        if(mOk_cancel_dialog==null){
            mOk_cancel_dialog=new Ok_Cancel_Dialog(mContext,"还未保存，确定退出？");
            mOk_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
                @Override
                public void okBack() {
                    finish();
                }

                @Override
                public void cancelBack() {

                }
            });
        }
        mOk_cancel_dialog.show();
    }

    @Override
    public void onBackPressed() {
        if(!cover_url.equals(temp_url)){
            backAction();
        }else {
            finish();
        }
    }
}
