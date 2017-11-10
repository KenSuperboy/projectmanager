package com.gohoc.xiupuling.ui.linkpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.linkpackage.LiandongbaoDetailAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.LinkPackageDetailBean;
import com.gohoc.xiupuling.callback.Callback3;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.callback.LiandongbaoCallback;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.dialog.LiandongbaoDialog;
import com.gohoc.xiupuling.dialog.Ok_Cancel_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 联动包 详情
* */
public class LiandongbaoDetailActivity extends BasicActivity {


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
    @BindView(R.id.tv_shiduan)
    TextView mTvShiduan;
    @BindView(R.id.tv_banben)
    TextView mTvBanben;
    @BindView(R.id.tv_edit)
    TextView mTvEdit;
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.linear_data_layout)
    LinearLayout mLinearDataLayout;
    @BindView(R.id.listView)
    MyListView mListView;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.relative_empty_layout)
    RelativeLayout mRelativeEmptyLayout;
    @BindView(R.id.iv_dialog)
    ImageView iv_dialog;
    @BindView(R.id.middle_line)
    View middle_line;
    private LiandongbaoDialog liandongbaoDialog;
    private String linkid,linkname,url,temp_url;//其中url指的是头像路径
    private int ADD_LIANDONGBAO_REQUEST=1000;
    private int EDIT_LIANDONGBAO_REQUEST=1001;
    private int PAIXU_LIANDONGBAO_REQUEST=1002;
    private LiandongbaoDetailAdapter mLiandongbaoDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_detail_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("多屏联动包");
        setStatusColor(R.color.colorPrimary);

        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mLiandongbaoDetailAdapter=new LiandongbaoDetailAdapter(mContext);
        mLiandongbaoDetailAdapter.setCallback(new Callback3() {
            @Override
            public void callBack(String string1, String string2, String string3) {
                if(!TextUtils.isEmpty(string1)&&!TextUtils.isEmpty(string2)&&!TextUtils.isEmpty(string3)){
                    deleteLinkDetail(string1,string2,Integer.parseInt(string3));
                }
            }
        });
        mListView.setAdapter(mLiandongbaoDetailAdapter);
    }

    //删除多屏联动包里面的一个作品
    private void deleteLinkDetail(final String link_id, final String detail_id,final int postion)
    {
        Ok_Cancel_Dialog ok_cancel_dialog=new Ok_Cancel_Dialog(mContext,"确定删除此多屏联动包？");
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                deleteShop(link_id,detail_id,postion);
            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }

    //删除多屏联动包里面的作品
    private void deleteShop(final String link_id,String detail_id, final int postion) {
        RxRetrofitClient.getInstance(LiandongbaoDetailActivity.this).deleteLinkDetailPackage(link_id,detail_id, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoDetailActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("删除成功");
                if(emptyBean.code==1){
                    mLiandongbaoDetailAdapter.remove(postion);
                    if(mLiandongbaoDetailAdapter.getCount()>0){
                        isData();
                    }else {
                        emptyData();
                    }
                }
            }
        });
    }

    private void initData() {
        linkid=getIntent().getStringExtra("linkid");
        linkname=getIntent().getStringExtra("linkname");
        url=getIntent().getStringExtra("url");
        temp_url=url;
        mTvName.setText(linkname);

        Glide.with(mContext)
                .load(Constant.NetConstant.BASE_USER_RESOURE + url + Utils.getThumbnail(200,200))
                .placeholder(R.mipmap.img_liandong_fm)
                .error(R.mipmap.img_liandong_fm)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mIvBg);

        getLiandongbaoDetail();
    }


    private void getLiandongbaoDetail() {
        RxRetrofitClient.getInstance(mContext).getLiandongbaoDetail(linkid, new Observer<LinkPackageDetailBean>() {
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
            public void onNext(LinkPackageDetailBean linkPackageDetailBean) {
                LogUtil.d("记载成功");
                if(linkPackageDetailBean.code==1&&linkPackageDetailBean.data.size()>0){
                    middle_line.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.VISIBLE);
                    mLinearDataLayout.setVisibility(View.VISIBLE);
                    mRelativeEmptyLayout.setVisibility(View.GONE);
                    mLiandongbaoDetailAdapter.setmLists(linkPackageDetailBean.data);
                    mTvNumber.setText("(共"+linkPackageDetailBean.data.size()+"个)");
                }else {
                    emptyData();
                }
            }
        });
    }

    private void isData()
    {
        middle_line.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.VISIBLE);
        mLinearDataLayout.setVisibility(View.VISIBLE);
        mRelativeEmptyLayout.setVisibility(View.GONE);
        mTvNumber.setText("(共"+mLiandongbaoDetailAdapter.getCount()+"个)");
    }

    private void emptyData()
    {
        middle_line.setVisibility(View.GONE);
        mLinearDataLayout.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        mRelativeEmptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    LogUtil.d("回调重新加载");
                    getLiandongbaoDetail();
                    break;
                case 1001:

                    url=data.getStringExtra("url");
                    Glide.with(mContext)
                            .load(Constant.NetConstant.BASE_USER_RESOURE + url + Utils.getThumbnail(200,200))
                            .placeholder(R.mipmap.img_liandong_fm)
                            .error(R.mipmap.img_liandong_fm)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mIvBg);

                    linkname=data.getStringExtra("linkname");
                    mTvName.setText(linkname);
                    break;
                case 1002:
                    LogUtil.d("回调重新加载");
                    getLiandongbaoDetail();
                    break;
            }
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.tv_edit, R.id.tv_add,R.id.iv_dialog,R.id.tv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.tv_edit://编辑各种信息
                Intent intent=new Intent(LiandongbaoDetailActivity.this,LiandongbaoEditMessageActivity.class);
                intent.putExtra("linkid",linkid);
                intent.putExtra("linkname",linkname);
                intent.putExtra("url",url);
                startActivityForResult(intent,EDIT_LIANDONGBAO_REQUEST);
                break;
            case R.id.tv_add:
                addLiandongPackage();
                break;
            case R.id.iv_dialog://底部弹窗的各种操作
                liandongbaoDialog=new LiandongbaoDialog(mContext);
                liandongbaoDialog.setCallback(new LiandongbaoCallback() {
                    @Override
                    public void tianjia() {
                        liandongbaoDialog.dismiss();
                        addLiandongPackage();
                    }

                    @Override
                    public void xuanze() {//选择领播作品(暂时不做)
                        liandongbaoDialog.dismiss();
                    }

                    @Override
                    public void paixu() {
                        liandongbaoDialog.dismiss();
                        Intent intent_paixu=new Intent(LiandongbaoDetailActivity.this,LiandongbaoPaiXuActivity.class);
                        intent_paixu.putExtra("linkid",linkid);
                        startActivityForResult(intent_paixu,PAIXU_LIANDONGBAO_REQUEST);
                    }

                    @Override
                    public void qingkong() {//清空联动包里的所有作品
                        liandongbaoDialog.dismiss();
                        clearLinkDetail(linkid);
                    }
                });
                liandongbaoDialog.show();
                break;
            case R.id.tv_left:
                Intent intent_dingtou=new Intent(LiandongbaoDetailActivity.this,LiandongbaoDingtouActivity.class);
                intent_dingtou.putExtra("linkid",linkid);
                intent_dingtou.putExtra("linkname",linkname);
                intent_dingtou.putExtra("url",url);
                startActivity(intent_dingtou);
                break;
        }
    }

    private void clearLinkDetail(final String link_id)
    {
        Ok_Cancel_Dialog ok_cancel_dialog=new Ok_Cancel_Dialog(mContext,"确定清空联动包里面的所有作品？");
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                clearLinkPackage(link_id);
            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }

    //清空多屏联动包里的所有作品
    private void clearLinkPackage(final String link_id) {
        RxRetrofitClient.getInstance(LiandongbaoDetailActivity.this).clearLinkPackage(link_id, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoDetailActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("删除成功");
                if(emptyBean.code==1){
                    middle_line.setVisibility(View.GONE);
                    mLinearDataLayout.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                    mRelativeEmptyLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //添加作品到联动包
    private void addLiandongPackage()
    {
        Intent intent=new Intent(LiandongbaoDetailActivity.this,LiandongbaoChooseActivity.class);
        intent.putExtra("linkid",linkid);
        intent.putExtra("type","0");
        startActivityForResult(intent,ADD_LIANDONGBAO_REQUEST);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FinishLiandongDetailEvent(Event.FinishLiandongDetailEvent message) {
        LogUtil.d("联动包详情");
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
