package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 编辑联动包信息
* */
public class LiandongbaoEditMessageActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_edit_layout)
    LinearLayout mLinearEditLayout;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.linear_name_layout)
    LinearLayout mLinearNameLayout;
    private String linkid,linkname,url;
    private int EDIT_NAME_REQUEST=100,EDIT_BG_REQUEST=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_edit_message_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("编辑联动包信息");
        setStatusColor(R.color.colorPrimary);

        initView();
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

    private void initData() {
        linkid = getIntent().getStringExtra("linkid");
        linkname = getIntent().getStringExtra("linkname");
        url = getIntent().getStringExtra("url");
        LogUtil.d("获取到的参数："+linkid+"====:"+linkname+"===:"+url);

        mTvName.setText(linkname);
    }


    private void setMyBg()
    {
        Glide.with(mContext)
                .load(Constant.NetConstant.BASE_USER_RESOURE + url + Utils.getThumbnail(200,200))
                .placeholder(R.mipmap.img_liandong_fm)
                .error(R.mipmap.img_liandong_fm)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_bg);
    }

    //编辑屏联动包里面的基本信息
    private void editLinkPackage(final String link_id, final String link_name, final String cover_url) {
        RxRetrofitClient.getInstance(LiandongbaoEditMessageActivity.this).editLinkPackage(link_id, link_name, cover_url, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoEditMessageActivity.this, "请检查网络是否正常");
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
                    LogUtil.d("编辑信息:修改成功");
                    url=cover_url;
                    linkname=link_name;
                    setMyBg();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    LogUtil.d("回调的名称："+data.getStringExtra("name"));
                    if(!data.getStringExtra("name").equals(linkname)){
                        //可以修改
                        mTvName.setText(data.getStringExtra("name"));
                        editLinkPackage(linkid,mTvName.getText().toString(),url);
                    }
                    break;
                case 101:
                    LogUtil.d("更换封面回调："+data.getStringExtra("url"));
                    editLinkPackage(linkid,mTvName.getText().toString(),data.getStringExtra("url"));
                    break;
            }
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.linear_edit_layout, R.id.linear_name_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                backAction();
                break;
            case R.id.linear_edit_layout:
                Intent intent_edit=new Intent(LiandongbaoEditMessageActivity.this,LiandongbaoEditBgActivity.class);
                intent_edit.putExtra("url",url);
                intent_edit.putExtra("type", "0");
                startActivityForResult(intent_edit,EDIT_BG_REQUEST);
                break;
            case R.id.linear_name_layout:
                Intent intent=new Intent(LiandongbaoEditMessageActivity.this,LiandongbaoAddActivity.class);
                intent.putExtra("type",1);//
                intent.putExtra("title","联动包信息");//
                intent.putExtra("name",linkname);//
                startActivityForResult(intent,EDIT_NAME_REQUEST);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backAction();
    }

    private void backAction()
    {
        Intent intent=new Intent();
        intent.putExtra("linkname",linkname);
        intent.putExtra("url",url);
        setResult(RESULT_OK,intent);
        finish();
    }
}
