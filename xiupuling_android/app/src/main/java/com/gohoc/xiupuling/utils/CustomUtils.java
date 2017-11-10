package com.gohoc.xiupuling.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.account.ToBeShowerActivity;
import com.gohoc.xiupuling.ui.account.ToBeTerminalerActivity;
import com.gohoc.xiupuling.ui.terminal.AddShopActivity;

import java.util.ArrayList;

import rx.Observer;

/**
 * Created by wjj on 2017/8/30.
 */
public class CustomUtils {

    public static String getPlayTime(String days,boolean is_ignore_other_work)
    {
        String typeString="",duzhan="";
        if(is_ignore_other_work){
            duzhan="独占";
        }else {
            duzhan="非独占";
        }
        LogUtil.d(days.substring(days.length()-2,days.length())+":进来的东西："+days);
        if(!TextUtils.isEmpty(days)&&days.length()==7){
            if(!days.contains("0")){
                //全部为1  每天独占
                typeString="每天"+duzhan;
                LogUtil.d("每天");
            }else if(!days.substring(0,5).contains("0")&&!days.substring(days.length()-2,days.length()).contains("1")){
                //全部为1  工作日独占
                typeString="工作日"+duzhan;
                LogUtil.d("工作日");
            }else if(!days.substring(days.length()-2,days.length()).contains("0")&&!days.substring(0,5).contains("1")){
                //全部为1  周末独占
                typeString="周末"+duzhan;
                LogUtil.d("周末");
            }else {
                typeString="周";
                LogUtil.d("具体情况");
                if(days.charAt(0)=='1'){

                    typeString+="1";
                }
                if(days.charAt(1)=='1'){
                    typeString+="2";
                }
                if(days.charAt(2)=='1'){
                    typeString+="3";
                }
                if(days.charAt(3)=='1'){
                    typeString+="4";
                }
                if(days.charAt(4)=='1'){
                    typeString+="5";
                }
                if(days.charAt(5)=='1'){
                    typeString+="6";
                }
                if(days.charAt(6)=='1'){
                    typeString+="7";
                }
                typeString+=duzhan;
            }
        }
        return typeString;
    }

    public static String start_end(String start,String end)
    {
        String time="";
        if(!TextUtils.isEmpty(start)&&!TextUtils.isEmpty(end)){
            return start+"-"+end;
        }else {
            return "";
        }
    }

    //通过shopId来获取详情
    public static void getShopDetail(final Context context, String id) {
        RxRetrofitClient.getInstance(context).getShopDetail(id, new Observer<ShopDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(context, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ShopDetailsBean shopDetailsBean) {
                if (shopDetailsBean.getCode() == 1) {
                    ShopDetailsBean shopDetailsBeans = shopDetailsBean;
                    context.startActivity(new Intent(context, AddShopActivity.class).putExtra("edit", shopDetailsBeans.getData()));
                }
            }
        });
    }

    //公用的弹窗显示
    public static void showPop(final Context context, final int types) {
        final Window window;
        final AlertDialog dialog;

            if (Build.VERSION.SDK_INT >= 14) {
                dialog = new AlertDialog.Builder(context,R.style.dialogTips).create();
            } else {
                dialog = new AlertDialog.Builder(context).create();
            }
            dialog.show();
            window = dialog.getWindow();
            dialog.setCanceledOnTouchOutside(false);
            // *** 主要就是在这里实现这种效果的.
            // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
            if (types == 0){
                SystemInfoBean systemInfoBean=(SystemInfoBean) ACache.get(context).getAsObject("SystemInfoBean");
                String url="";
                if(systemInfoBean.getData()!=null){
                    url=systemInfoBean.getData().getJzapply();
                }
                window.setContentView(R.layout.pop_to_be_jz);
                ImageView imageView= (ImageView) window.findViewById(R.id.tips_iv);
                LogUtil.d("图片控件："+imageView);
                if(!TextUtils.isEmpty(url)){
                    LogUtil.d("图片控件1111："+(Constant.NetConstant.BASE_USER_RESOURE + url));
                    Glide.with(context)
                            .load(Constant.NetConstant.BASE_USER_RESOURE + url )
                            .placeholder(R.mipmap.tips_default)
                            .error(R.mipmap.tips_default)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                }
            }else if(types==1){
                SystemInfoBean systemInfoBean=(SystemInfoBean) ACache.get(context).getAsObject("SystemInfoBean");
                String url="";

                if(systemInfoBean.getData()!=null){
                    url=systemInfoBean.getData().getZzapply();
                }

                window.setContentView(R.layout.pop_to_be_zz);
                ImageView imageView= (ImageView) window.findViewById(R.id.tips_iv);
                if(!TextUtils.isEmpty(url)){
                    Glide.with(context)
                            .load(Constant.NetConstant.BASE_USER_RESOURE + url)
                            .placeholder(R.mipmap.df_logo)
                            .error(R.mipmap.df_logo)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                }
            }

            window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (types == 0)
                        context.startActivity(new Intent(context, ToBeTerminalerActivity.class));
                    else
                        context.startActivity(new Intent(context, ToBeShowerActivity.class));
                }
            });
            window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
    }

    //判断门店终端是否展示
    public static void showMendian(Context context)
    {
        SystemInfoBean systemInfoBean=(SystemInfoBean) ACache.get(context).getAsObject("SystemInfoBean");
        if(systemInfoBean==null||systemInfoBean.getData()==null){
            return;
        }
        if(TextUtils.isEmpty(AppSettings.getPrefString(context, Constant.FlagConstant.MENDIAN,""))){
            showPop(context,0);
            LogUtil.d("当钱日期："+TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
            AppSettings.setPrefString(context, Constant.FlagConstant.MENDIAN, TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
        }else if(!TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd").equals(AppSettings.getPrefString(context, Constant.FlagConstant.MENDIAN,""))){
            showPop(context,0);
            AppSettings.setPrefString(context, Constant.FlagConstant.MENDIAN, TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
        }
    }

    //判断群组是否展示
    public static void showQunzu(Context context)
    {
        SystemInfoBean systemInfoBean=(SystemInfoBean) ACache.get(context).getAsObject("SystemInfoBean");
        if(systemInfoBean==null||systemInfoBean.getData()==null){
            return;
        }
        if(TextUtils.isEmpty(AppSettings.getPrefString(context, Constant.FlagConstant.QUNZU,""))){
            showPop(context,0);
            LogUtil.d("当钱日期："+TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
            AppSettings.setPrefString(context, Constant.FlagConstant.QUNZU, TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
        }else if(!TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd").equals(AppSettings.getPrefString(context, Constant.FlagConstant.QUNZU,""))){
            showPop(context,0);
            AppSettings.setPrefString(context, Constant.FlagConstant.QUNZU, TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
        }
    }

    public static void showJieguanggao(Context context)
    {
        if(TextUtils.isEmpty(AppSettings.getPrefString(context, Constant.FlagConstant.JIEGUANGGAO,""))){
            showPop(context,1);
            LogUtil.d("当钱日期："+ TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
            AppSettings.setPrefString(context, Constant.FlagConstant.JIEGUANGGAO, TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
        }else if(!TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd").equals(AppSettings.getPrefString(context, Constant.FlagConstant.JIEGUANGGAO,""))){
            showPop(context,1);
            AppSettings.setPrefString(context, Constant.FlagConstant.JIEGUANGGAO, TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
        }
    }

    //判断作品是否展示
    public static void showZuoping(Context context)
    {
        if(TextUtils.isEmpty(AppSettings.getPrefString(context, Constant.FlagConstant.ZUOPING,""))){
            showPop(context,1);
            LogUtil.d("当钱日期："+TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
            AppSettings.setPrefString(context, Constant.FlagConstant.ZUOPING, TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
        }else if(!TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd").equals(AppSettings.getPrefString(context, Constant.FlagConstant.ZUOPING,""))){
            showPop(context,1);
            AppSettings.setPrefString(context, Constant.FlagConstant.ZUOPING, TimeUtil.getTimeFormMillis(System.currentTimeMillis(),"yyyy-MM-dd"));
        }
    }

    //默认情况进来
    /*SpannableString spannableString = new SpannableString("亲，您还未有任何作品\n快来“自己制作”吧");
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品\n" +"快来“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品\n" +"快来“").length(),("亲，您还未有任何作品\n" +"快来“自己制作").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品\n" +"快来“自己制作").length(),("亲，您还未有任何作品\n" +"快来“自己制作”吧").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    mTvEmpty.setText(spannableString);*/


    /*Glide.with(context)
            .load(NetConstant.BASE_USER_RESOURE + groupBean.getData().get(position).getUnion_portrait() + Utils.getThumbnail(200,200))
            // .placeholder(R.mipmap.icon_port_home)
            //.error(R.mipmap.icon_port_home)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    .into(holder.groupIv);*/

    public static String getWorkType(int type)
    {
        String typeString="";
        switch (type){
            case 1:
                typeString="海报";
                break;
            case 2:
                typeString="相册";
                break;
            case 3:
                typeString="视频";
                break;
            case 4:
                typeString="图嵌视频";
                break;
        }
        return typeString;
    }

    public static boolean getPic(ArrayList<PicBean> showPicList){
        boolean pic=false;
        if(showPicList!=null&&showPicList.size()>0){
            for (int i=0;i<showPicList.size();i++){
                if(showPicList.get(i).isPic()){
                    pic=true;
                    break;
                }
            }
        }
        return pic;
    }

    //判断是不是有五张图片
    public static boolean getPic_Five(ArrayList<PicBean> showPicList){
        boolean pic=false;
        int count=0;
        if(showPicList!=null&&showPicList.size()>0){
            for (int i=0;i<showPicList.size();i++){
                if(showPicList.get(i).isPic()){
                    count++;
                }
            }
        }
        if(count==5){
            pic=true;
        }
        return pic;
    }

    public static boolean getMusic(ArrayList<PicBean> showPicList){
        boolean pic=false;
        if(showPicList!=null&&showPicList.size()>0){
            for (int i=0;i<showPicList.size();i++){
                if(showPicList.get(i).isMusic()){
                    pic=true;
                    break;
                }
            }
        }
        return pic;
    }

    public static boolean getVideo(ArrayList<PicBean> showPicList){
        boolean pic=false;
        if(showPicList!=null&&showPicList.size()>0){
            for (int i=0;i<showPicList.size();i++){
                if(showPicList.get(i).isVideo()){
                    pic=true;
                    break;
                }
            }
        }
        return pic;
    }

    public static String getMytime(long millions)
    {
        int processHour,processMunites,processSeconds;
        processHour=(int) (millions/1000/60/60);
        processMunites=(int) ((millions%(1000*60*60))/60/1000);
        processSeconds=(int) (((millions%(1000*60*60))%(60*1000))/1000);
        LogUtil.d("得到的时间："+processHour+"===:"+processMunites+"===="+processSeconds);
        String stringHour="",stringMunites="",stringSeconds="";
        if(processHour==0){
            stringHour="";
        }else if(0<processHour&&processHour<10){
            stringHour="0"+processHour+":";
        }else if(processHour>=10){
            stringHour=processHour+":";
        }

        if(processMunites==0){
            stringMunites="00:";
        }else if(0<processMunites&&processMunites<10){
            stringMunites="0"+processMunites+":";
        }else if(processMunites>=10){
            stringMunites=processMunites+":";
        }

        if(processSeconds==0){
            stringSeconds="00";
        }else if(0<processSeconds&&processSeconds<10){
            stringSeconds="0"+processSeconds;
        }else if(processSeconds>=10){
            stringSeconds=processSeconds+"";
        }
        return stringHour+stringMunites+stringSeconds;
    }
}
