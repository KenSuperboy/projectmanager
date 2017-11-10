package com.gohoc.xiupuling.ui.linkpackage;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.linkpackage.LinkCombinationSelectWorksAdapter;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.MyWorksBean;
import com.gohoc.xiupuling.callback.Callback1;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* 选择联动包
* */
public class LiandongbaoChooseActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    /*@BindView(R.id.toolbar)
    Toolbar mToolbar;*/
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.relative_empty_layout)
    RelativeLayout mRelativeEmptyLayout;

    private LinkCombinationSelectWorksAdapter mLinkCombinationSelectWorksAdapter;
    private String linkid="",combinationid="",type="";
    private List<MyWorksBean.DataBean> dataBeenlist = new ArrayList<>();
    private int or;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_combination_list_layout);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);

        mToolbarRight.setText("完成");
        initView();
        initData();
    }

    private void initView()
    {
        mLinkCombinationSelectWorksAdapter=new LinkCombinationSelectWorksAdapter(mContext);
        listView.setAdapter(mLinkCombinationSelectWorksAdapter);
        mLinkCombinationSelectWorksAdapter.setCallback(new Callback1() {
            @Override
            public void callBack(String string) {
                if(!TextUtils.isEmpty(string)){
                    reloadData(Integer.parseInt(string));
                }
            }
        });
    }

    private void initData() {

        type=getIntent().getStringExtra("type");
        if(type.equals("0")){
            linkid=getIntent().getStringExtra("linkid");//联动包
            mToolbarTitle.setText("我的作品");
        }else {
            //需要区分横屏竖屏
            or=getIntent().getBooleanExtra("or",false)?1:0;
            combinationid=getIntent().getStringExtra("combinationid");//组合包
            if(or==0){
                mToolbarTitle.setText("我的横屏作品");
            }else {
                mToolbarTitle.setText("我的竖屏作品");
            }
        }
        getmyavailableworklist();
    }

    //获取我的作品作品
    private void getmyavailableworklist() {
        RxRetrofitClient.getInstance(this).getmyavailableworklist(null, new Observer<MyWorksBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                setEmptyData();
                Utils.toast(LiandongbaoChooseActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(MyWorksBean myWorksBean) {
                if (myWorksBean.getCode() == 1 && myWorksBean.getData().size() > 0) {

                    for (int a = 0; a < myWorksBean.getData().size(); a++) {
                        if(type.equals("0")){
                            //联动包相关
                            if (myWorksBean.getData().get(a).getWork_type() == 1 ||myWorksBean.getData().get(a).getWork_type() == 2 ||myWorksBean.getData().get(a).getWork_type() == 3 || myWorksBean.getData().get(a).getWork_type() == 4)
                                dataBeenlist.add(myWorksBean.getData().get(a));
                        }else if (type.equals("1")){
                            //作品组合包相关
                            if (myWorksBean.getData().get(a).getOrientation()==or){
                                dataBeenlist.add(myWorksBean.getData().get(a));
                            }
                        }
                    }

                    if (dataBeenlist.size() > 0) {
                        setMyvisible(View.VISIBLE, View.GONE, R.mipmap.icon_zuopinku);
                        mLinkCombinationSelectWorksAdapter.setmLists(dataBeenlist);
                    } else {
                        setEmptyData();
                    }
                } else {
                    setEmptyData();
                }
            }
        });
    }

    //添加作品到联动包
    private void addLinkPackage(final String link_id,String work_ids, String rq_ids) {
        RxRetrofitClient.getInstance(LiandongbaoChooseActivity.this).addLinkPackage(link_id,work_ids,rq_ids, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoChooseActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("添加成功");
                if(emptyBean.code==1){
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    //添加作品到作品组合包
    private void addCombinationPackage(final String package_id,String work_ids, String rq_ids) {
        RxRetrofitClient.getInstance(LiandongbaoChooseActivity.this).addCombinationPackage(package_id,work_ids,rq_ids, new Observer<EmptyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(LiandongbaoChooseActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(EmptyBean emptyBean) {
                LogUtil.d("添加组合包成功");
                if(emptyBean.code==1){
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    public void setEmptyData() {
        //默认情况进来
        setMyvisible(View.GONE, View.VISIBLE, R.mipmap.icon_zuopinku);
        SpannableString spannableString = new SpannableString("亲，您还未有任何作品\n快来“自己制作”吧");
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, ("亲，您还未有任何作品\n" + "快来“").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), ("亲，您还未有任何作品\n" + "快来“").length(), ("亲，您还未有任何作品\n" + "快来“自己制作").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), ("亲，您还未有任何作品\n" + "快来“自己制作").length(), ("亲，您还未有任何作品\n" + "快来“自己制作”吧").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvEmpty.setText(spannableString);

    }

    public void setMyvisible(int data, int empty, int src) {
        listView.setVisibility(data);
        mRelativeEmptyLayout.setVisibility(empty);
        mIvEmpty.setImageResource(src);
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                getIdString();
                if(TextUtils.isEmpty(work_ids_list)){
                    Toast.makeText(mContext,"请选择作品",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(type.equals("0")){
                    //联动包相关
                    addLinkPackage(linkid,work_ids_list,rq_ids_list);
                }else if(type.equals("1")){
                    //组合包相关
                    addCombinationPackage(combinationid,work_ids_list,rq_ids_list);
                }
                break;
        }
    }

    /*class MyAdapter extends BaseQuickAdapter<MyWorksBean.DataBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, List<MyWorksBean.DataBean> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(final BaseViewHolder baseViewHolder, final MyWorksBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.og_tv, dataBean.getOrientation() == 0 ? "横屏" : "竖屏")
                    .setText(R.id.type_tv, dataBean.getRq_orientation_str() + "")
                    .setText(R.id.designer_name_tv, dataBean.getWork_name() + "")
                    .setText(R.id.update_time_v_tv, dataBean.getCreate_time() + "")
            ;
            LogUtil.d("当前位置："+baseViewHolder.getAdapterPosition());
            ImageView iv = baseViewHolder.getView(R.id.woks_cover_iv);
            Glide.with(LiandongbaoChooseActivity.this)
                    .load(BASE_USER_RESOURE + dataBean.getMaterial_store_url() + Utils.getThumbnail(300, 300))
                    .centerCrop()
                    .placeholder(R.mipmap.df_logo)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);
            ImageView iv_select = baseViewHolder.getView(R.id.iv_select);
            iv_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reloadData(baseViewHolder.getAdapterPosition());
                }
            });
            if(dataBean.getFlag()==0){
                iv_select.setImageResource(R.mipmap.icon_zhuce_butongyi);
            }else {
                iv_select.setImageResource(R.mipmap.icon__zhuce_tongyi);
            }
            LinearLayout linear_item_layout = baseViewHolder.getView(R.id.linear_item_layout);
            linear_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reloadData(baseViewHolder.getAdapterPosition());
                }
            });
            baseViewHolder.getView(R.id.preLL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LiandongbaoChooseActivity.this, PreviewWActivity.class).putExtra("work_id", dataBean.getWork_id()));
                }
            });
        }
    }*/

    //重新加载标示
    private void reloadData(int position)
    {
        if(dataBeenlist.get(position).getFlag()==0){
            dataBeenlist.get(position).setFlag(1);
        }else {
            dataBeenlist.get(position).setFlag(0);
        }
        if(mLinkCombinationSelectWorksAdapter!=null){
            mLinkCombinationSelectWorksAdapter.setmLists(dataBeenlist);
        }
    }

    private String work_ids_list="",rq_ids_list="";
    private void getIdString()
    {
        for (int i=0;i<dataBeenlist.size();i++){
            if(dataBeenlist.get(i).getFlag()==1){
                work_ids_list+=dataBeenlist.get(i).getWork_id()+",";
                rq_ids_list+=dataBeenlist.get(i).getRq_id()+",";
            }
        }

        LogUtil.d(work_ids_list+":选中的东西："+rq_ids_list);
    }
}
