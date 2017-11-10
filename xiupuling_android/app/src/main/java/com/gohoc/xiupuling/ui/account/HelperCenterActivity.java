package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.AppUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelperCenterActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.jizhurumen_lv)
    LinearLayout jizhurumenLv;
    @BindView(R.id.zhanzhurumen_lv)
    LinearLayout zhanzhurumenLv;
    @BindView(R.id.yonghuzinan_lv)
    LinearLayout yonghuzinanLv;
    @BindView(R.id.chagnjianwenti_lv)
    LinearLayout chagnjianwentiLv;
    @BindView(R.id.lianxikefu_lv)
    LinearLayout lianxikefuLv;
    @BindView(R.id.dianzhan_lv)
    LinearLayout dianzhanLv;
    private ACache mCache;
    private SystemInfoBean systemInfoBean;
    private Integer[] zihu={R.mipmap.img_mendianhezhongdua,R.mipmap.img_bofangmokuai,R.mipmap.img_qun,R.mipmap.img_jieguanggao,R.mipmap.img_zhanzhu_toufang};
    private Integer[] zhagnzhu={R.mipmap.img_zhanzhu_zuopin,R.mipmap.img_zhanzhu_wodezuopinku,R.mipmap.img_zhanzhu_toufang,R.mipmap.img_zhanzhu_ditu,R.mipmap.img_zhanzhu_toufangzuopin};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_center);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("帮助与反馈");
        mCache = ACache.get(this);
        systemInfoBean = (SystemInfoBean) mCache.getAsObject("SystemInfoBean");
    }

    @OnClick({R.id.toolbar_left_title, R.id.jizhurumen_lv, R.id.zhanzhurumen_lv, R.id.yonghuzinan_lv, R.id.chagnjianwenti_lv, R.id.lianxikefu_lv, R.id.dianzhan_lv})
    public void onViewClicked(View view) {
        if (view.getId() != R.id.toolbar_left_title && null == systemInfoBean) {
            Utils.toast(HelperCenterActivity.this,"获取系统参数失败！");
            return;
        }

        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.jizhurumen_lv:
                startActivity(new Intent(HelperCenterActivity.this, LoophellpActivity.class).putExtra("title", "机主入门").putExtra("imglist",zihu));
                break;
            case R.id.zhanzhurumen_lv:
                startActivity(new Intent(HelperCenterActivity.this, LoophellpActivity.class).putExtra("title", "展主入门").putExtra("imglist",zhagnzhu));
                break;
            case R.id.yonghuzinan_lv:
                startActivity(new Intent(HelperCenterActivity.this, WebViewActivity.class).putExtra("url", systemInfoBean.getData().getAboutUserguide()).putExtra("title", "用户指南"));
                break;
            case R.id.chagnjianwenti_lv:
                startActivity(new Intent(HelperCenterActivity.this, WebViewActivity.class).putExtra("url", systemInfoBean.getData().getAboutFqa()).putExtra("title", "常见问题"));
                break;
            case R.id.lianxikefu_lv:
                showDialog();
                break;
            case R.id.dianzhan_lv:
                Utils.toShop(this);
                break;
        }
    }

    public void showDialog() {
        new AlertView("工作日:9:00-12:00 14:00-18:00", null, "取消", null,
                new String[]{systemInfoBean.getData().getServiceTelephone1() + ""},
                this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            public void onItemClick(Object o, int position) {
                if(position!=-1)
                  AppUtils.actionDial(HelperCenterActivity.this, systemInfoBean.getData().getServiceTelephone1()+"");
            }
        }).show();
    }
}
