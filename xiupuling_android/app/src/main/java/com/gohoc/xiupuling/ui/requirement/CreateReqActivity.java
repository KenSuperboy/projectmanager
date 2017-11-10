package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PicBean;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.constant.EventFactory;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.UploadManage;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.SelectView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class CreateReqActivity extends BasicActivity {

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
    @BindView(R.id.save_button_ll)
    LinearLayout saveButtonLl;
    @BindView(R.id.select_view)
    SelectView select_view;

    private String title = "";
    private PromotionInfoBean promotionInfoBean;
    private int og = 0;
    private static int TITLE_REQUEST_RESULT = 1000;
    private static int PT_REQUEST_RESULT = 1001;
    private static int OG_REQUEST_RESULT = 1002;
    private static int DT_REQUEST_RESULT = 1003;
    private int type = 1;//1：海报  2：相册  3：视频   4：图嵌视频
    private ArrayList<PicBean> picList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        toolbarTitle.setText("自己制作");
        toolbarRight.setText("完成");
        toolbarRight.setVisibility(View.GONE);
        setStatusColor(R.color.colorPrimary);
        select_view.setListener(new SelectView.SelectViewListener() {
            @Override
            public void selectItem(int postion) {
                if (postion == type) return;
                type = postion;
                i = null;
                UploadManage.INSTANS.uploadImageCount = 0;
                picList.clear();//每点击一次，就会清零  重新选择
                reqPdCountTv.setText("");
            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.req_title_lv, R.id.req_promotion_lv, R.id.req_og_lv, R.id.req_pd_count_lv, R.id.save_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.req_title_lv:
                startActivityForResult(new Intent(CreateReqActivity.this, CreateReqTitleActivity.class).putExtra("title", title), TITLE_REQUEST_RESULT);
                break;
            case R.id.req_promotion_lv:
                startActivityForResult(new Intent(CreateReqActivity.this, CreateReqPromotionActivity.class).putExtra("promotionInfoBean", promotionInfoBean), PT_REQUEST_RESULT);
                break;
            case R.id.req_og_lv:
                startActivityForResult(new Intent(CreateReqActivity.this, CreateReqOgActivity.class).putExtra("orientation", og), OG_REQUEST_RESULT);
                break;
            case R.id.req_pd_count_lv://1 海报   2 相册   3 视频  4 图嵌视频
                if(type==1){
                    startActivityForResult(new Intent(CreateReqActivity.this, CreateReqUploadData_1_Activity.class).putExtra("type", type).putExtra("og", og).putExtra("picList", picList), DT_REQUEST_RESULT);
                }else if(type==2){
                    startActivityForResult(new Intent(CreateReqActivity.this, CreateReqUploadData_2_Activity.class).putExtra("type", type).putExtra("og", og).putExtra("picList", picList), DT_REQUEST_RESULT);
                }else if(type==3){
                    startActivityForResult(new Intent(CreateReqActivity.this, CreateReqUploadData_3_New_Activity.class).putExtra("type", type).putExtra("og", og).putExtra("picList", picList), DT_REQUEST_RESULT);
                }else if(type==4){
                    startActivityForResult(new Intent(CreateReqActivity.this, CreateReqUploadData_4_Activity.class).putExtra("type", type).putExtra("og", og).putExtra("picList", picList), DT_REQUEST_RESULT);
                }
                break;
            case R.id.save_button_ll:
                if(canload){
                    save();
                }
                break;
        }
    }

    private boolean canload=true;

    private void save() {

        if (TextUtils.isEmpty(reqTitleTv.getText())) {
            Utils.toast(this, "请输入作品标题");
            return;
        }
        if (UploadManage.INSTANS.uploadImageCount > 0) {
            Utils.toast(this, "正在上传文件，请稍后..");
            return;
        }
        if (picList == null || picList.size() < 1) {
            if(type==1){
                Utils.toast(this, "请选择需要上传的海报");
            }else if(type==2){
                Utils.toast(this, "请选择需要上传的相册");
            }else if (type == 3){
                Utils.toast(this, "请选择需要上传的视频");
            }else if (type == 4){
                Utils.toast(this, "请选择需要上传的图嵌视频");
            }
            return;
        } else if (type == 4) {
            if (!hasSoure(1)) {
                Utils.toast(this, "请选择需要上传的视频");
                return;
            }
        }
        canload=false;
        showProgressDialog("正在提交....");
        if (promotionInfoBean == null) {
            promotionInfoBean = new PromotionInfoBean();
        }


        RxRetrofitClient.getInstance(this).selfdesignwork(
                promotionInfoBean.getOpen() ? "0" : "1",
                promotionInfoBean.getOpen() ? promotionInfoBean.getKeyWord() + "" : null,
                promotionInfoBean.getOpen() ? promotionInfoBean.getIntroduce() + "" : null,
                promotionInfoBean.getOpen() ? promotionInfoBean.getLinkType() + "" : null,
                promotionInfoBean.getOpen() ? (promotionInfoBean.getLinkType() == 0 ? promotionInfoBean.getLcoationName() : promotionInfoBean.getLinkUrl()) : null,
                promotionInfoBean.getOpen() ? promotionInfoBean.getLongitude() : null,
                promotionInfoBean.getOpen() ? promotionInfoBean.getLatitude() : null,
                promotionInfoBean.getOpen() ? promotionInfoBean.getDetails() + "" : null,
                type + "",
                reqTitleTv.getText() + "",
                og + "",
                getMaterial(0),//水印和背景音乐需要单独区分
                getMaterial(1) + "",
                getMaterial(2),     //文件原始名称  水印和背景音乐需要单独区分
                getAudio_File(),   //音乐文件的key
                getAudio_File_Name() , //音乐文件的原始名称
                getWaterMark(), //水印图片的各种格式
                getWaterMarkName(),  //水印图片的原始名称
                (type == 4 && picList.size() > 0 ? getVideoW() : 0) + "",
                (type == 4 && picList.size() > 0 ? getVideoH() : 0) + "",
                (type == 4 && picList.size() > 0 ? getVideoX() : 0) + "",
                (type == 4 && picList.size() > 0 ? getVideoY() : 0) + "",

                new Observer<VCodeBenan>() {
                    @Override

                    public void onCompleted() {
                        canload=true;
                        closeProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        canload=true;
                        Utils.toast(CreateReqActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(VCodeBenan vCodeBenan) {
                        canload=true;
                        if (vCodeBenan.getMessage().equals("发布成功")) {
                            Credential.getInstance().updateUserInfo(CreateReqActivity.this);
                            Utils.toast(CreateReqActivity.this, "作品上传成功");
                        } else {
                            Utils.toast(CreateReqActivity.this, vCodeBenan.getMessage());
                        }
                        if (vCodeBenan.getCode() == 1) {
                            finish();
                        }
                    }
                });
    }

    //获取视频的宽度
    private int getVideoW()
    {
        int w = 0;
        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isVideo()){
                w=picList.get(i).getW();
            }
        }
        LogUtil.d("获取到的宽度："+w);
        return w;
    }

    //获取视频的高度
    private int getVideoH()
    {
        int h = 0;
        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isVideo()){
                h=picList.get(i).getH();
            }
        }

        LogUtil.d("获取到的高度："+ h);
        return h;
    }

    private int getVideoX()
    {
        int x = 0;
        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isVideo()){
                x=picList.get(i).getX();
            }
        }
        LogUtil.d("获取到的横坐标："+ x);
        return x;
    }

    private int getVideoY()
    {
        int y = 0;
        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isVideo()){
                y=picList.get(i).getY();
            }
        }
        LogUtil.d("获取到的纵坐标："+ y);
        return y;
    }

    private boolean hasSoure(int type) {
        for (int a = 0; a < picList.size(); a++) {
            if (picList.get(a).getType() != 0) {
                    if (type == 0 && picList.get(a).isPic())
                        return true;
                    else if (type == 1 && picList.get(a).isVideo())
                        return true;
            }
        }
        return false;
    }

    private String getMaterial(int i) {
        StringBuffer sb = new StringBuffer();
        for (int a = 0; a < picList.size(); a++) {
            if (picList.get(a).getType() != 0) {
                if (i == 0) {//材料url
                    if (type != 3 && !picList.get(a).isMusic()) {//不需要水印和音乐文件
                        sb.append(picList.get(a).getUpdatefileName() + ",");//本地key和上传成功后的key是一样的
                    }else if(type==3&&!picList.get(a).isPic()){
                        sb.append(picList.get(a).getUpdatefileName() + ",");//本地key和上传成功后的key是一样的
                    }
                }else if(i==1){//材料类型
                    if (picList.get(a).isVideo()) {
                        sb.append("0,");
                    }else if(picList.get(a).isPic()&&type!=3){
                        sb.append("2,");
                    }

                    /*if (picList.get(a).isVideo()) {
                        sb.append("0,");
                    }else if(picList.get(a).isMusic()){
                        sb.append("1,");
                    }else if(picList.get(a).isPic()){
                        sb.append("2,");
                    }else if(picList.get(a).isPic()&&type==3){
                        sb.append("3,");
                    }*/
                }else if(i==2){//材料原始名字
                    if (type != 3 && !picList.get(a).isMusic()) {//不需要水印和音乐文件
                        if(!TextUtils.isEmpty(picList.get(a).getLocUrl())){
                            sb.append(new File(picList.get(a).getLocUrl()).getName()+ ",");
                            LogUtil.d("原始材不等于三的时候料名称："+new File(picList.get(a).getLocUrl()).getName());
                        }
                    }else if(type==3&&!picList.get(a).isPic()){
                        if(!TextUtils.isEmpty(picList.get(a).getLocUrl())){
                            sb.append(new File(picList.get(a).getLocUrl()).getName()+ ",");
                            LogUtil.d("原始材料等于三的时候名称："+new File(picList.get(a).getLocUrl()).getName());
                        }
                    }
                }
            }
        }
        if(sb.length()>1){
            return sb.toString().substring(0, sb.length() - 1);
        }else {
            return "";
        }
        //Logger.e("素材url：：：" + sb.toString().substring(0, sb.length() - 1));
    }

    //获取音乐文件的key
    private String getAudio_File()
    {
        String audio_file="";
        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isMusic()){
                audio_file=picList.get(i).getUpdatefileName();
            }
        }
        return audio_file;
    }

    private String getAudio_File_Name()
    {
        String audio_file_name="";
        for (int i=0;i<picList.size();i++){
            if(picList.get(i).isMusic()){
                if(!TextUtils.isEmpty(picList.get(i).getLocUrl())){
                    audio_file_name=new File(picList.get(i).getLocUrl()).getName();
                }
            }
        }
        return audio_file_name;
    }

    private String getWaterMarkName()
    {
        String audio_file_name="";
        for (int i=0;i<picList.size();i++){
            if(type==3&&picList.get(i).isPic()){
                if(!TextUtils.isEmpty(picList.get(i).getLocUrl())){
                    audio_file_name=new File(picList.get(i).getLocUrl()).getName();
                }
            }
        }
        return audio_file_name;
    }

    private String getWaterMark()
    {
        String audio_file_name="";
        for (int i=0;i<picList.size();i++){
            if(type==3&&picList.get(i).isPic()){
                audio_file_name=picList.get(i).getUpdatefileName()+"|"+picList.get(i).getW()+"|"+picList.get(i).getH()+"|"+picList.get(i).getX()+"|"+picList.get(i).getY();
            }
        }
        return audio_file_name;
    }

    private Intent i;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TITLE_REQUEST_RESULT) {
                if (null != data) {
                    title = data.getStringExtra("title");
                    // if (!title.isEmpty()) {
                    reqTitleTv.setText(title + "");
                    // }
                }
            } else if (requestCode == PT_REQUEST_RESULT) {
                if (null != data) {
                    Bundle d = data.getExtras();
                    if (null != d) {
                        PromotionInfoBean pti = (PromotionInfoBean) d.get("promotionInfoBean");
                        if (pti != null) {
                            promotionInfoBean = pti;
                            reqPromotionTv.setText(promotionInfoBean.getOpen() == true ? promotionInfoBean.getIntroduce() : "关");
                            Logger.e(promotionInfoBean.getLinkUrl() + "");
                        }
                    }

                }
            } else if (requestCode == OG_REQUEST_RESULT) {
                int orientation = data.getIntExtra("orientation", 0);
                if (orientation == og) return;
                i = null;
                UploadManage.INSTANS.uploadImageCount = 0;
                picList.clear();
                reqPdCountTv.setText("");
                og = orientation;

                if (og == 0)
                    reqOgTv.setText("横(1920×1080)");
                else
                    reqOgTv.setText("竖(1080×1920)");
            } else if (requestCode == DT_REQUEST_RESULT) {
                if (null != data) {
                    this.i = data;
                    ArrayList<PicBean> pic = (ArrayList<PicBean>) data.getSerializableExtra("picList");
                    picList = pic;
                    if (UploadManage.INSTANS.uploadImageCount > 0) {
                        reqPdCountTv.setText("上传中,请等待..");
                    } else {
                        setDataHint(data);
                    }
                }

            }
        }
    }

    private void setDataHint(Intent d) {
        if (null != d) {
            int type = d.getIntExtra("type", -1);
            switch (type) {
                case 1://图片和音频
                    ArrayList<PicBean> pic = (ArrayList<PicBean>) d.getSerializableExtra("picList");
                    if (pic != null) {
                        picList = pic;
                        reqPdCountTv.setText("");
                        for (int i=0;i<pic.size();i++){
                            if(pic.get(i).isPic()){
                                reqPdCountTv.setText(reqPdCountTv.getText().toString()+" 1张图片 ");
                            }

                            if(pic.get(i).isMusic()){
                                reqPdCountTv.setText(reqPdCountTv.getText().toString()+" 1个音频 ");
                            }
                        }
                    }
                    break;
                case 2://相册
                    ArrayList<PicBean> pic1 = (ArrayList<PicBean>) d.getSerializableExtra("picList");
                    if (pic1 != null) {
                        picList = pic1;
                        int picNumber=0;
                        reqPdCountTv.setText("");
                        for (int i=0;i<pic1.size();i++){
                            if(pic1.get(i).isPic()){
                                picNumber++;
                                reqPdCountTv.setText(picNumber+"张图片 ");
                            }

                            if(pic1.get(i).isMusic()){
                                reqPdCountTv.setText(reqPdCountTv.getText().toString()+" 1个音频 ");
                            }
                        }
                    }
                    break;
                case 3:
                    ArrayList<PicBean> pic2 = (ArrayList<PicBean>) d.getSerializableExtra("picList");
                    if (pic2 != null) {
                        picList = pic2;
                        if (isEventBus) {
                            if (picList != null && picList.size() >= 1) {
                                picList.get(0).setProgress(100);
                            }
                        }
                        reqPdCountTv.setText("");
                        for (int i=0;i<pic2.size();i++){
                            if(pic2.get(i).isPic()){
                                reqPdCountTv.setText(reqPdCountTv.getText().toString()+"1张图片 ");
                            }

                            if(pic2.get(i).isVideo()){
                                reqPdCountTv.setText(reqPdCountTv.getText().toString()+" 1个视频 ");
                            }
                        }
                    }
                    break;
                case 4:
                    ArrayList<PicBean> pic3 = (ArrayList<PicBean>) d.getSerializableExtra("picList");
                    if (pic3 != null) {
                        picList = pic3;
                        LogUtil.d("回调得到的数据："+pic3);
                        reqPdCountTv.setText("");
                        for (int i=0;i<pic3.size();i++){
                            if(pic3.get(i).isPic()){
                                reqPdCountTv.setText(reqPdCountTv.getText().toString()+"1张图片 ");
                            }

                            if(pic3.get(i).isVideo()){
                                reqPdCountTv.setText(reqPdCountTv.getText().toString()+" 1个视频 ");
                            }
                        }


                        /*if (pic3.size() > 0) {
                            String s = "";
                            boolean isfp = true;
                            boolean isfv = true;
                            for (PicBean p : pic3) {
                                if (p != null) {
                                    if (p.isPic() && isfp) {
                                        isfp = false;
                                        s += "1张图片";
                                    }
                                    if (p.isVideo() && isfv) {
                                        isfv = false;
                                        s += "1个视频";
                                    }
                                }
                            }
                            reqPdCountTv.setText(s);
                        } else {
                            reqPdCountTv.setText("");
                        }*/
                    }
                    break;
            }
            isEventBus = false;
        }
    }

    private boolean isEventBus = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pleaseUploadData(EventFactory.pleaseUploadData message) {
        isEventBus = true;
        setDataHint(i);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
