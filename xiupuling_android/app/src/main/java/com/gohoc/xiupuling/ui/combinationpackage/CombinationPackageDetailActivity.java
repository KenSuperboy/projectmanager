package com.gohoc.xiupuling.ui.combinationpackage;

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
import com.gohoc.xiupuling.adapter.combinationadapter.CombinationDetailAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListBean;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListDetailBean;
import com.gohoc.xiupuling.callback.Callback3;
import com.gohoc.xiupuling.callback.Callback_Ok_Cancel;
import com.gohoc.xiupuling.callback.LiandongbaoCallback;
import com.gohoc.xiupuling.dialog.LiandongbaoDialog;
import com.gohoc.xiupuling.dialog.Ok_Cancel_Dialog;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.linkpackage.LiandongbaoChooseActivity;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 作品组合包 详情
* */
public class CombinationPackageDetailActivity extends BasicActivity {


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
    @BindView(R.id.tv_default)
    TextView tv_default;
    @BindView(R.id.middle_line)
    View mMiddleLine;

    private LiandongbaoDialog liandongbaoDialog;

    private String combinationid, combinationname, url;//其中url指的是头像路径
    private CombinationListBean.DataBean mDataBean;
    private int ADD_LIANDONGBAO_REQUEST = 1000;
    private int EDIT_LIANDONGBAO_REQUEST = 1001;
    private int PAIXU_LIANDONGBAO_REQUEST = 1002;
    private CombinationDetailAdapter mCombinationDetailAdapter;
    private boolean isData;//有作品的时候true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liandongbao_detail_layout);
        ButterKnife.bind(this);
        mToolbarTitle.setText("作品组合包");
        mTvLeft.setText("投放全部作品");
        setStatusColor(R.color.colorPrimary);

        initView();
        initData();
    }

    private void initView() {
        mCombinationDetailAdapter = new CombinationDetailAdapter(mContext);
        mCombinationDetailAdapter.setCallback(new Callback3() {
            @Override
            public void callBack(String string1, String string2, String string3) {
                if (!TextUtils.isEmpty(string1) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                    deleteLinkDetail(string1, string2, Integer.parseInt(string3));
                }
            }
        });
        mListView.setAdapter(mCombinationDetailAdapter);
    }

    //删除多屏联动包里面的一个作品
    private void deleteLinkDetail(final String package_id, final String detail_id, final int postion) {
        Ok_Cancel_Dialog ok_cancel_dialog = new Ok_Cancel_Dialog(mContext, "确定删除此多屏联动包？");
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                deleteShop(package_id, detail_id, postion);
            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }

    //删除多屏联动包里面的作品
    private void deleteShop(final String package_id, String detail_id, final int postion) {
        RxRetrofitClient.getInstance(CombinationPackageDetailActivity.this).deleteCombinationDetailPackage(package_id, detail_id, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CombinationPackageDetailActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("删除成功");
                if (emptyBean.code == 1) {
                    mCombinationDetailAdapter.remove(postion);
                    if(mCombinationDetailAdapter.getCount()>0){
                        isData();
                    }else {
                        emptyData();
                    }
                }
            }
        });
    }

    private void initData() {
        mDataBean = (CombinationListBean.DataBean) getIntent().getExtras().get("data");
        setMydata(mDataBean);
        getCombinationDetail();
        tv_default.setText("亲，您的作品组合包还未有任何作品\n你可以添加作品到作品组合包中");
    }

    private String start_end(String start,String end)
    {
        String time="";
        if(!TextUtils.isEmpty(start)&&!TextUtils.isEmpty(end)){
            return start+"-"+end;
        }else {
            return "";
        }
    }

    private void setMydata(CombinationListBean.DataBean mydata)
    {
        combinationid = mydata.package_id;
        combinationname = mydata.package_name;
        url = mydata.cover_url + "";

        mTvShiduan.setVisibility(View.VISIBLE);
        if(!mydata.is_time_limit&&mydata.is_ignore_other_work){
            mTvShiduan.setText("全天候独占播放");
        }else {
            if(!mydata.is_time_limit&&!mydata.is_ignore_other_work){
                mTvShiduan.setText("非独占时段播放");
            }else {
                if(!TextUtils.isEmpty(mydata.repeat_weekday+"")){
                    mTvShiduan.setText(CustomUtils.getPlayTime(mydata.repeat_weekday+"",mydata.is_ignore_other_work)+" "+start_end(mydata.start_time+"",mydata.end_time+""));
                }else {
                    mTvShiduan.setText("非独占时段播放");
                }
            }
        }

        mTvName.setText(combinationname);
        mTvBanben.setVisibility(View.VISIBLE);
        if (mydata.orientation) {
            mTvBanben.setText("竖版");
        } else {
            mTvBanben.setText("横版");
        }
        mTvShiduan.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(NetConstant.BASE_USER_RESOURE + url + Utils.getThumbnail(200, 200))
                .placeholder(R.mipmap.icon_zuhebao)
                .error(R.mipmap.icon_zuhebao)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mIvBg);
    }


    private void getCombinationDetail() {
        RxRetrofitClient.getInstance(mContext).getCombinationDetail(combinationid, new Observer<CombinationListDetailBean>() {
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
            public void onNext(CombinationListDetailBean combinationListDetailBean) {
                LogUtil.d("记载成功");

                if (combinationListDetailBean.code == 1 && combinationListDetailBean.data.size() > 0) {
                    isData = true;
                    mMiddleLine.setVisibility(View.VISIBLE);
                    timeLength = timeLength(combinationListDetailBean.data);
                    mListView.setVisibility(View.VISIBLE);
                    mLinearDataLayout.setVisibility(View.VISIBLE);
                    mRelativeEmptyLayout.setVisibility(View.GONE);
                    mCombinationDetailAdapter.setmLists(combinationListDetailBean.data);
                    mTvNumber.setText("(共" + combinationListDetailBean.data.size() + "个)");
                } else {
                    emptyData();
                }
            }
        });
    }

    //有数据
    private void isData()
    {
        isData = true;
        mMiddleLine.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.VISIBLE);
        mLinearDataLayout.setVisibility(View.VISIBLE);
        mRelativeEmptyLayout.setVisibility(View.GONE);
        mTvNumber.setText("(共" + mCombinationDetailAdapter.getCount() + "个)");
        timeLength = timeLength(mCombinationDetailAdapter.getTestItems());
    }

    //没有数据
    private void emptyData()
    {
        isData = false;
        mMiddleLine.setVisibility(View.GONE);
        mLinearDataLayout.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        mRelativeEmptyLayout.setVisibility(View.VISIBLE);
    }

    private int timeLength;

    //获取总共的时间
    private int timeLength(List<CombinationListDetailBean.DataBean> testItems) {
        int time = 0;
        for (int i = 0; i < testItems.size(); i++) {
            time += testItems.get(i).playtime;
        }
        return time;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    LogUtil.d("回调重新加载");

                    getCombinationDetail();
                    break;
                case 1001:
                    //组合包作品修改
                    url = data.getStringExtra("url");
                    mDataBean = (CombinationListBean.DataBean) data.getExtras().get("data");
                    LogUtil.d("回调："+mDataBean.orientation);
                    setMydata(mDataBean);
                    break;
                case 1002:
                    getCombinationDetail();
                    break;
            }
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.tv_edit, R.id.tv_add, R.id.iv_dialog, R.id.tv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.tv_edit://编辑各种信息
                Intent intent = new Intent(CombinationPackageDetailActivity.this, CombinationEditMessageActivity.class);
                intent.putExtra("data", mDataBean);
                intent.putExtra("isData", isData);
                startActivityForResult(intent, EDIT_LIANDONGBAO_REQUEST);
                break;
            case R.id.tv_add:
                addCombinationPackage();
                break;
            case R.id.iv_dialog://底部弹窗的各种操作
                liandongbaoDialog = new LiandongbaoDialog(mContext);
                liandongbaoDialog.setAddText("添加作品到组合包");
                liandongbaoDialog.setCallback(new LiandongbaoCallback() {
                    @Override
                    public void tianjia() {
                        liandongbaoDialog.dismiss();
                        addCombinationPackage();
                    }

                    @Override
                    public void xuanze() {//选择领播作品(暂时不做)
                        liandongbaoDialog.dismiss();
                    }

                    @Override
                    public void paixu() {
                        liandongbaoDialog.dismiss();
                        Intent intent_paixu = new Intent(CombinationPackageDetailActivity.this, CombinationPackagePaiXuActivity.class);
                        intent_paixu.putExtra("combinationid", combinationid);
                        startActivityForResult(intent_paixu, PAIXU_LIANDONGBAO_REQUEST);
                    }

                    @Override
                    public void qingkong() {//清空联动包里的所有作品
                        liandongbaoDialog.dismiss();
                        clearCombinationDetail(combinationid);
                    }
                });
                liandongbaoDialog.show();
                break;
            case R.id.tv_left:
                PushNormlBean pushNormlBean = new PushNormlBean();
                pushNormlBean.setName(mDataBean.package_name);
                pushNormlBean.setPlaytime(timeLength);
                pushNormlBean.setCover_url(url);
                pushNormlBean.setOrientation(mDataBean.orientation ? 1 : 0);
                pushNormlBean.setRq_id(mDataBean.package_id);
                pushNormlBean.setWork_id(mDataBean.user_id);
                pushNormlBean.setFlag(1);//表明是组合包进来
                pushNormlBean.setFree(true);
                startActivity(new Intent(CombinationPackageDetailActivity.this, CombinationPushSelectMenuActivity.class).putExtra("pushNormlBean", pushNormlBean));

                /*Intent intent_push=new Intent(CombinationPackageDetailActivity.this,CombinationPushSelectMenuActivity.class);
                startActivity(intent_push);*/

                /*Intent intent_dingtou=new Intent(CombinationPackageDetailActivity.this,LiandongbaoDingtouActivity.class);
                intent_dingtou.putExtra("combinationid",combinationid);
                intent_dingtou.putExtra("combinationname",combinationname);
                intent_dingtou.putExtra("url",url);
                startActivity(intent_dingtou);*/
                break;
        }
    }

    private void clearCombinationDetail(final String package_id) {
        Ok_Cancel_Dialog ok_cancel_dialog = new Ok_Cancel_Dialog(mContext, "确定清空组合包里面的所有作品？");
        ok_cancel_dialog.setCallback(new Callback_Ok_Cancel() {
            @Override
            public void okBack() {
                clearCombinationPackage(package_id);
            }

            @Override
            public void cancelBack() {

            }
        });
        ok_cancel_dialog.show();
    }

    //清空多屏联动包里的所有作品
    private void clearCombinationPackage(final String package_id) {
        RxRetrofitClient.getInstance(CombinationPackageDetailActivity.this).clearCombinationPackage(package_id, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CombinationPackageDetailActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("删除成功");
                if (emptyBean.code == 1) {
                    mMiddleLine.setVisibility(View.GONE);
                    mLinearDataLayout.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                    mRelativeEmptyLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //添加作品到组合包
    private void addCombinationPackage() {
        Intent intent = new Intent(CombinationPackageDetailActivity.this, LiandongbaoChooseActivity.class);
        intent.putExtra("combinationid", combinationid);
        intent.putExtra("type", "1");
        intent.putExtra("or",mDataBean.orientation);
        startActivityForResult(intent, ADD_LIANDONGBAO_REQUEST);
    }
}
