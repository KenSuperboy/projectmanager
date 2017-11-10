package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.KvBean;
import com.gohoc.xiupuling.bean.TerminalBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.EditZimuActivity;
import com.gohoc.xiupuling.ui.MusicListActivity;
import com.gohoc.xiupuling.ui.TerminalPictureActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.suke.widget.SwitchButton;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.wuxiaolong.androidutils.library.VersionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class TerminalSettingActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.terminal_og_tv)
    TextView terminalOgTv;
    @BindView(R.id.terminal_og_ll)
    LinearLayout terminalOgLl;
    @BindView(R.id.terinal_run_sb)
    SwitchButton terinalRunSb;
    @BindView(R.id.terinal_sutdown_sb)
    SwitchButton terinalSutdownSb;
    @BindView(R.id.terinal_ad_sb)
    SwitchButton terinalAdSb;
    @BindView(R.id.sutdown_ll)
    LinearLayout sutdownLl;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.start_time_ll)
    LinearLayout startTimeLl;

    @BindView(R.id.end_time_tv)
    TextView endTimeTv;
    @BindView(R.id.end_time_ll)
    LinearLayout endTimeLl;
    @BindView(R.id.rp_time_ll)
    LinearLayout rpTimeLl;
    @BindView(R.id.rp_tv)
    TextView rpTv;
    @BindView(R.id.start_time_loop_h)
    LoopView startTimeLoopH;
    @BindView(R.id.start_time_pl)
    PercentRelativeLayout startTimePl;
    @BindView(R.id.start_time_loop_m)
    LoopView startTimeLoopM;
    @BindView(R.id.end_time_loop_h)
    LoopView endTimeLoopH;
    @BindView(R.id.end_time_loop_m)
    LoopView endTimeLoopM;
    @BindView(R.id.end_time_pl)
    PercentRelativeLayout endTimePl;
    @BindView(R.id.tv_yinyue)
    TextView mTvYinyue;
    @BindView(R.id.linear_yinyue_layout)
    LinearLayout mLinearYinyueLayout;
    @BindView(R.id.sb_zimu)
    SwitchButton mSbZimu;
    @BindView(R.id.tv_zimu)
    TextView mTvZimu;
    @BindView(R.id.linear_zimu_layout)
    LinearLayout mLinearZimuLayout;
    @BindView(R.id.linear_bofangjietu_layout)
    LinearLayout mLinearBofangjietuLayout;
    @BindView(R.id.sb_zhuangtai)
    SwitchButton mSbZhuangtai;
    @BindView(R.id.sb_erweima)
    SwitchButton mSbErweima;

    private TerminalBean.DataBean terminal;
    private int force_xpl = 0; //是否强制运行
    private int take_order = 0; //是否接订单
    private int schedule_up_down = 0; //定时开关机 1 开 0 关
    private int bg_audio_flag;//0:么有  1：有
    private String bg_audio_type,bg_audio_type_name;//音乐类型id和名字
    private int roll_title_flag;//滚动字幕
    private String roll_titles;
    private int weather_flag;
    private int scancode_flag;

    private static int ORIENTATUION_REQUEST_RESULT = 1000;
    private static int TIME_REQUEST_RESULT = 1001;
    private static int ZIMU_REQUEST_RESULT = 1002;
    private static int MUSIC_REQUEST_RESULT = 1003;
    private ArrayList<KvBean> rpdata;//重复运行时间

    private ArrayList<String> hourTime = new ArrayList<>();
    private ArrayList<String> minuteTime = new ArrayList<>();
    private String startTimeHour = "08";
    private String startTimeMinute = "00";

    private String endTimeHour = "18";
    private String endTimeMinute = "00";
    private String weekV = "";
    private String[] weekN = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private String[] weekVs = {"0", "0", "0", "0", "0", "0", "0"};
    private int orientation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_setting);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("功能/参数设定");
        initListener();
        initTimer();
        initDatas();
    }

    private void initListener()
    {
        //滚动字幕
        mSbZimu.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                LogUtil.d("字幕监听："+isChecked);
                if(isChecked){
                    roll_title_flag=1;
                    mLinearZimuLayout.setVisibility(View.VISIBLE);
                }else {
                    roll_title_flag=0;
                    mLinearZimuLayout.setVisibility(View.GONE);
                }
            }
        });

        //天气显示
        mSbZhuangtai.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    weather_flag=1;
                }else {
                    weather_flag=0;
                }
            }
        });

        //二维码显示
        mSbErweima.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    scancode_flag=1;
                }else {
                    scancode_flag=0;
                }
            }
        });
    }

    private void initDatas() {
        try {
            terminal = (TerminalBean.DataBean) getIntent().getExtras().get("terminal");
            orientation = terminal.getTerm_orientation();
            if (terminal.getTerm_orientation() == 0)
                terminalOgTv.setText("横屏");
            else
                terminalOgTv.setText("竖屏");

            //背景音乐设定
            bg_audio_type=terminal.getBg_audio_type();
            bg_audio_type_name=terminal.getBg_audio_type_name();
            if(terminal.isBg_audio_flag()){
                mTvYinyue.setText(terminal.getBg_audio_type_name());
                bg_audio_flag=1;
            }else {
                bg_audio_flag=0;
                mTvYinyue.setText("无");
            }

            //滚动字幕显示
            mSbZimu.setChecked(terminal.isRoll_title_flag());
            if(terminal.isRoll_title_flag()){
                LogUtil.d("进来显示-----字幕");
                mLinearZimuLayout.setVisibility(View.VISIBLE);
                mTvZimu.setText(terminal.getRoll_titles());
                roll_titles=terminal.getRoll_titles();
                roll_title_flag=1;
            }else {
                mLinearZimuLayout.setVisibility(View.GONE);
                LogUtil.d("进来不显示-----字幕");
                roll_title_flag=0;
                roll_titles="";
                mTvZimu.setText("");
            }

            //天气显示
            mSbZhuangtai.setChecked(terminal.isWeather_flag());
            weather_flag = terminal.isWeather_flag() == true ? 1 : 0;

            //二维码显示
            mSbErweima.setChecked(terminal.isScancode_flag());
            scancode_flag = terminal.isScancode_flag() == true ? 1 : 0;

            terinalRunSb.setChecked(terminal.isForce_xpl());
            terinalSutdownSb.setChecked(terminal.isSchedule_up_down());
            terinalAdSb.setChecked(terminal.isTake_order());
            force_xpl = terminal.isForce_xpl() == true ? 1 : 0;
            take_order = terminal.isTake_order() == true ? 1 : 0;
            schedule_up_down = terminal.isSchedule_up_down() == true ? 1 : 0;

            if (terminal.isSchedule_up_down()) {
                sutdownLl.setVisibility(View.VISIBLE);
                startTimeTv.setText(terminal.getUp_start_time() + "");
                endTimeTv.setText(terminal.getUp_end_time() + "");

                Logger.e(terminal.getUp_start_time() + "");
                startTimeHour = (terminal.getUp_start_time() + "").split(":")[0];
                startTimeMinute = (terminal.getUp_start_time() + "").split(":")[1];

                endTimeHour = (terminal.getUp_end_time() + "").split(":")[0];
                endTimeMinute = (terminal.getUp_end_time() + "").split(":")[1];


                for (int a = 0; a < hourTime.size(); a++) {
                    if (startTimeHour.equals(hourTime.get(a)))
                        startTimeLoopH.setCurrentPosition(a);

                    if (endTimeHour.equals(hourTime.get(a)))
                        endTimeLoopH.setCurrentPosition(a);
                }

                for (int a = 0; a < minuteTime.size(); a++) {
                    if (startTimeMinute.equals(minuteTime.get(a)))
                        startTimeLoopM.setCurrentPosition(a);

                    if (endTimeMinute.equals(minuteTime.get(a)))
                        endTimeLoopM.setCurrentPosition(a);
                }
            }


        } catch (Exception e) {
            Logger.e(e.toString());
        }


        //处理自动开关机天数重复  (1111111)
        rpdata = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        for (int a = 0; a < weekN.length; a++) {
            if (terminal.getRepeat_weekday() == null || terminal.getRepeat_weekday().toString().length() < 6) {   //新终端为null 还有各种 奇葩数据
                rpdata.add(new KvBean(weekN[a], "1").setCheck(true));
            } else {
                String x = terminal.getRepeat_weekday().toString().toCharArray()[a] + "";   //取字符判断是否为1
                if (x.equals("1")) {
                    rpdata.add(new KvBean(weekN[a], terminal.getRepeat_weekday().toString().toCharArray()[a] + "").setCheck(true));
                    sb.append(weekN[a] + ",");
                } else
                    rpdata.add(new KvBean(weekN[a], "0"));
            }
        }
        if (sb.length() > 1) {
            weekV = String.valueOf(terminal.getRepeat_weekday());
            //判断全部选择
            if (weekV.startsWith("1111111"))
                rpTv.setText("每天");
            else if (weekV.startsWith("1111100")) //判断下工作日
                rpTv.setText("工作日");
            else if (weekV.endsWith("0000011"))
                rpTv.setText("周末");
            else
                rpTv.setText(sb.toString().substring(0, sb.toString().length() - 1));

        }else {
            rpTv.setText("每天");
        }
        terinalSutdownSb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                    sutdownLl.setVisibility(View.VISIBLE);
                else
                    sutdownLl.setVisibility(View.GONE);
                schedule_up_down = isChecked == true ? 1 : 0;
            }
        });
        terinalRunSb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                force_xpl = isChecked == true ? 1 : 0;
            }
        });
        terinalAdSb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                take_order = isChecked == true ? 1 : 0;
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.terminal_og_ll, R.id.start_time_ll, R.id.end_time_ll, R.id.rp_time_ll,R.id.linear_yinyue_layout,R.id.linear_zimu_layout,R.id.linear_bofangjietu_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                TerminalSettingActivity.this.finish();
                break;
            case R.id.toolbar_right:
                save();
                break;
            case R.id.terminal_og_ll:
                startActivityForResult(new Intent(TerminalSettingActivity.this, SettingTerminalOgActivity.class).putExtra("orientation", terminal.getTerm_orientation()), ORIENTATUION_REQUEST_RESULT);
                break;
            case R.id.start_time_ll:
                if (startTimePl.getVisibility() == View.VISIBLE)
                    startTimePl.setVisibility(View.GONE);
                else
                    startTimePl.setVisibility(View.VISIBLE);
                break;
            case R.id.end_time_ll:
                if (endTimePl.getVisibility() == View.VISIBLE)
                    endTimePl.setVisibility(View.GONE);
                else
                    endTimePl.setVisibility(View.VISIBLE);
                break;
            case R.id.rp_time_ll:
                startActivityForResult(new Intent(TerminalSettingActivity.this, SelectWeekActivity.class).putExtra("data", rpdata), TIME_REQUEST_RESULT);
                break;
            case R.id.linear_yinyue_layout:
                Intent intent_music=new Intent(TerminalSettingActivity.this, MusicListActivity.class);
                intent_music.putExtra("music",mTvYinyue.getText().toString());
                startActivityForResult(intent_music,MUSIC_REQUEST_RESULT);
                break;
            case R.id.linear_zimu_layout:
                Intent intent_zimu=new Intent(TerminalSettingActivity.this, EditZimuActivity.class);
                intent_zimu.putExtra("edit",mTvZimu.getText().toString());
                startActivityForResult(intent_zimu,ZIMU_REQUEST_RESULT);
                break;
            case R.id.linear_bofangjietu_layout:
                //terminal.getTerm_orientation() == 0
                Intent intent=new Intent(TerminalSettingActivity.this, TerminalPictureActivity.class);
                intent.putExtra("or",terminal.getTerm_orientation());
                intent.putExtra("id",terminal.getTerminal_id());
                startActivity(intent);
                break;
        }
    }

    private void save() {
        if (schedule_up_down == 0)
            setterminalruntimestatus(String.valueOf(orientation), terminal.getTerminal_id(), force_xpl + "", schedule_up_down + "", null, null, take_order + "", null,bg_audio_flag+"",bg_audio_type+"",roll_title_flag+"",roll_titles,weather_flag+"",scancode_flag+"");
        else {

            if (Integer.parseInt(startTimeHour) > Integer.parseInt(endTimeHour)) {
                Utils.toast(this, "结束时间不能早于开始时间");
                return;
            }

            if(mSbZimu.isChecked()&& TextUtils.isEmpty(roll_titles)){
                Utils.toast(this, "请填写滚动字幕");
                return;
            }

            if (Integer.parseInt(startTimeHour) == Integer.parseInt(endTimeHour)) {
                if (Integer.parseInt(startTimeMinute) >= Integer.parseInt(endTimeMinute)) {
                    Utils.toast(this, "结束时间不能早于开始时间");
                    return;
                }
            }
            setterminalruntimestatus(String.valueOf(orientation), terminal.getTerminal_id(), force_xpl + "", schedule_up_down + "", startTimeHour + ":" + startTimeMinute, endTimeHour + ":" + endTimeMinute, take_order + "", weekV,bg_audio_flag+"",bg_audio_type+"",roll_title_flag+"",roll_titles,weather_flag+"",scancode_flag+"");
        }
    }

    public void updateTerminal(final int og) {


        RxRetrofitClient.getInstance(TerminalSettingActivity.this).updateTerminal(
                terminal.getTerminal_id(), terminal.getShop().getShop_id(), terminal.getTerminal_no(), VersionUtil.getVersionCode(TerminalSettingActivity.this) + "", og + "", new Observer<VCodeBenan>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(TerminalSettingActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(VCodeBenan vCodeBenan) {
                        Utils.toast(TerminalSettingActivity.this, vCodeBenan.getMessage());
                        if (vCodeBenan.getCode() == 1) {
                            terminal.setTerm_orientation(og);
                            if (terminal.getTerm_orientation() == 0)
                                terminalOgTv.setText("横屏");
                            else
                                terminalOgTv.setText("竖屏");
                            EventBus.getDefault().post(new Event.RefreshTerminalListEvent());
                        }


                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ORIENTATUION_REQUEST_RESULT) {
                if (null != data) {
                    orientation = data.getIntExtra("orientation", 0);
                    if (orientation == 0)
                        terminalOgTv.setText("横屏");
                    else
                        terminalOgTv.setText("竖屏");
                }

            } else if (requestCode == TIME_REQUEST_RESULT) {
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
                                    rpTv.setText("每天");
                                else if (weekV.startsWith("1111100")) //判断下工作日
                                    rpTv.setText("工作日");
                                else if (weekV.endsWith("0000011"))
                                    rpTv.setText("周末");
                                else
                                    rpTv.setText(sb.toString().substring(0, sb.toString().length() - 1));
                            } else
                                rpTv.setText("");
                        }
                    }

                }
            }else if(requestCode == ZIMU_REQUEST_RESULT){
                mTvZimu.setText(data.getStringExtra("data")+"");
                roll_titles=data.getStringExtra("data")+"";
            }else if(requestCode == MUSIC_REQUEST_RESULT){//音乐相关
                mTvYinyue.setText(data.getStringExtra("name")+"");
                bg_audio_type_name=data.getStringExtra("name")+"";
                bg_audio_type=data.getStringExtra("type")+"";
                if(bg_audio_type_name.equals("无")){
                    bg_audio_flag=0;
                }else {
                    bg_audio_flag=1;
                }
            }
        }
    }


    private void setterminalruntimestatus(String term_orientation, final String terminal_id, String force_xpl, String schedule_up_down,
                                          String up_start_time, String up_end_time, String take_order, String repeat_weekday,
                                          String bg_audio_flag,String bg_audio_type,String roll_title_flag,String roll_titles,String weather_flag,String scancode_flag) {
        RxRetrofitClient.getInstance(this).setterminalruntimestatus(term_orientation, terminal_id, force_xpl, schedule_up_down, up_start_time, up_end_time, take_order, repeat_weekday,bg_audio_flag,bg_audio_type,roll_title_flag,roll_titles,weather_flag,scancode_flag, new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(TerminalSettingActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(TerminalSettingActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    setResult(RESULT_OK, new Intent().putExtra("terminalNo", terminal.getTerminal_id()));
                    finish();
                }

            }
        });

    }

    private void initTimer() {
        for (int a = 0; a < 24; a++) {
            hourTime.add(String.format("%02d", a) + "");
        }
        for (int a = 0; a < 60; a++) {
            minuteTime.add(String.format("%02d", a) + "");
        }

        startTimeLoopH.setItems(hourTime);
        startTimeLoopM.setItems(minuteTime);
        endTimeLoopH.setItems(hourTime);
        endTimeLoopM.setItems(minuteTime);


        startTimeLoopH.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                startTimeHour = hourTime.get(i);
                startTimeTv.setText(startTimeHour + ":" + startTimeMinute);

            }
        });
        startTimeLoopM.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                startTimeMinute = minuteTime.get(i);
                startTimeTv.setText(startTimeHour + ":" + startTimeMinute);
                Logger.e(startTimeHour);
                // Logger.e(startTimeHour + ":" + startTimeMinute );
            }
        });
        endTimeLoopH.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                endTimeHour = minuteTime.get(i);
                endTimeTv.setText(endTimeHour + ":" + endTimeMinute);
            }
        });
        endTimeLoopM.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                endTimeMinute = minuteTime.get(i);
                endTimeTv.setText(endTimeHour + ":" + endTimeMinute);
            }
        });


        startTimeLoopH.setInitPosition(8);
        startTimeLoopM.setInitPosition(0);
        endTimeLoopH.setInitPosition(18);
        endTimeLoopM.setInitPosition(0);

    }


}
