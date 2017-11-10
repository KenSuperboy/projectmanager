package com.gohoc.xiupuling.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.MsgBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.account.BonusPointTokenActivity;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;
import com.wuxiaolong.androidutils.library.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class MsgActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("我的消息");
        getmymessage();
    }

    private void getmymessage() {
        RxRetrofitClient.getInstance(this).getmymessage("0", new Observer<MsgBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MsgActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(final MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    adapter = new MyAdapter(R.layout.item_msg, msgBean.getData());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MsgActivity.this));
                    //添加分割线
                    recyclerView.addItemDecoration(new DividerItemDecoration(
                            MsgActivity.this, DividerItemDecoration.VERTICAL_LIST));
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                            if (TextUtils.isEmpty(msgBean.getData().get(i).getLinkurl() + "") || (msgBean.getData().get(i).getLinkurl() + "").equals("null") ) {
                                startActivity(new Intent(MsgActivity.this, MsgDtActivity.class).putExtra("msg",msgBean.getData().get(i)));
                            } else {
                                startActivity(new Intent(MsgActivity.this, WebViewActivity.class).putExtra("url", msgBean.getData().get(i).getLinkurl()+"").putExtra("title", "我的消息"));
                            }
                        }
                    });
                }


            }
        });
    }

    @OnClick(R.id.toolbar_left_title)
    public void onViewClicked() {
        finish();
    }

    class MyAdapter extends BaseQuickAdapter<MsgBean.DataBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, List<MsgBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, MsgBean.DataBean dataBean) {
            ImageView iv = baseViewHolder.getView(R.id.pic_iv);
            Glide.with(MsgActivity.this)
                    .load(Constant.NetConstant.BASE_USER_RESOURE + dataBean.getIcon())
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);

            baseViewHolder.setText(R.id.content_tv, dataBean.getContent() + "")
                    .setText(R.id.time_tv, Utils.format(Utils.timestampToDate(dataBean.getMsg_version())))
                    .setText(R.id.title_tv, dataBean.getTitle() + "");
        }
    }
}
