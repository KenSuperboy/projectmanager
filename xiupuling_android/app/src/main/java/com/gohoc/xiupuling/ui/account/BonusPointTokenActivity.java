package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.adapter.TokenAdater;
import com.gohoc.xiupuling.bean.SystemInfoBean;
import com.gohoc.xiupuling.bean.TokenBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.DividerItemDecoration;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.TimeUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class BonusPointTokenActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.ps_ll)
    LinearLayout psLl;
    @BindView(R.id.button_ll)
    LinearLayout buttonLl;
    @BindView(R.id.token_count_tv)
    TextView tokenCountTv;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private ACache mCache;
    private SystemInfoBean sb;
    private TokenAdater tokenAdater;
    private int page = 1;
    private int pageCount = 10;
    private TokenBean tokenBeanTemp;
    private boolean isRefresh = true;
    private UserBaseBean user;
    private boolean isdailycheckin = false;
    private Window window;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_point_token);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("令牌");
        mCache = ACache.get(this);
        sb = (SystemInfoBean) mCache.getAsObject("SystemInfoBean");
        getpointtradedtl(page + "", pageCount + "");
        user = Credential.getInstance().getCurUser(this);
        checkIsdailycheckin();
        getmypoin();

    }

    private void checkIsdailycheckin() {
        String dailycheckin = mCache.getAsString("dailycheckin");
        Logger.e(dailycheckin+"");
        if (TextUtils.isEmpty(dailycheckin))
            isdailycheckin = false;
        else
            isdailycheckin = TimeUtil.unixTimestamp2BeijingTime(Long.parseLong(dailycheckin), "yyyy-MM-dd").equals(TimeUtil.unixTimestamp2BeijingTime(System.currentTimeMillis(), "yyyy-MM-dd"));

        if (isdailycheckin)
            buttonLl.setBackgroundResource(R.color.gray);
        else
            buttonLl.setBackgroundResource(R.color.org);
    }

    private void showDialog() {
            if (Build.VERSION.SDK_INT >= 14) {
                dialog = new AlertDialog.Builder(this,
                        R.style.dialogTips).create();
            } else {
                dialog = new AlertDialog.Builder(this).create();
            }

            dialog.show();
            window = dialog.getWindow();
            // *** 主要就是在这里实现这种效果的.
            // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容

            window.setContentView(R.layout.pop_add_token);

            window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
    }

    @OnClick({R.id.toolbar_left_title, R.id.ps_ll, R.id.button_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.ps_ll:
                if (null != sb && null != sb.getData())
                    startActivity(new Intent(BonusPointTokenActivity.this, WebViewActivity.class).putExtra("url", sb.getData().getAboutGainpoint()).putExtra("title", "如何获取令牌"));
                break;
            case R.id.button_ll:
                if (!isdailycheckin)
                    dailycheckin();
                break;
        }
    }

    public void initList(final TokenBean tokenBean) {

        if (null == tokenAdater) {
            tokenBeanTemp = tokenBean;
            tokenAdater = new TokenAdater(this, tokenBean);
            pullLoadMoreRecyclerView.setAdapter(tokenAdater);
            pullLoadMoreRecyclerView.setLinearLayout();
            pullLoadMoreRecyclerView.addItemDecoration(new DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL_LIST));
            // provinceList.setItemAnimator(new DefaultItemAnimator());
            tokenAdater.setOnItemClickLitener(new OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    //  startActivity(new Intent(ReqMineListActivity.this, ReqContactsdsActivity.class).putExtra("rq_id", reqListBean.getData().getList().get(position).getRq_id()).putExtra("work_id", reqListBean.getData().getList().get(position).getWork_id()));
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });


            pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                @Override
                public void onRefresh() {
                    isRefresh = true;
                    pullLoadMoreRecyclerView.setPushRefreshEnable(false);
                    page = 1;
                    getpointtradedtl(page + "", pageCount + "");

                }

                @Override
                public void onLoadMore() {
                    Logger.e("onLoadMore:page " + (page + 1));
                    isRefresh = false;
                    getpointtradedtl((page + 1) + "", pageCount + "");

                }
            });
        } else {
            if (isRefresh) {
                tokenBeanTemp = tokenBean;
                tokenAdater.rf(tokenBeanTemp);
            } else {
                tokenBeanTemp = tokenAdater.getTokenBean();
                tokenBeanTemp.getData().getList().addAll(tokenBean.getData().getList());
                tokenAdater.rf(tokenBeanTemp);
            }

        }


    }


    private void getpointtradedtl(String pageNumber,
                                  String pageSize) {
        RxRetrofitClient.getInstance(this).getpointtradedtl(pageNumber, pageSize, new Observer<TokenBean>() {
            @Override
            public void onCompleted() {
                pullLoadMoreRecyclerView.setRefreshing(false);
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }

            @Override
            public void onError(Throwable e) {
                pullLoadMoreRecyclerView.setRefreshing(false);
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                Utils.toast(BonusPointTokenActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TokenBean tokenBean) {
                pullLoadMoreRecyclerView.setRefreshing(false);
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                if (tokenBean.getData().isLastPage())
                    pullLoadMoreRecyclerView.setPushRefreshEnable(false);
                else
                    pullLoadMoreRecyclerView.setPushRefreshEnable(true);

                if (tokenBean.getCode() == 1) {
                    if (isRefresh)
                        page = 1;
                    else
                        page++;
                    initList(tokenBean);
                }
            }
        });
    }


    private void dailycheckin() {
        RxRetrofitClient.getInstance(this).dailycheckin(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(BonusPointTokenActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(BonusPointTokenActivity.this, vCodeBenan.getMessage());
                getmypoin();
                if(vCodeBenan.getCode()==1 )
                {
                    mCache.put("dailycheckin", System.currentTimeMillis()+"");
                    checkIsdailycheckin();
                    showDialog();
                    getpointtradedtl(page + "", pageCount + "");
                }else if(vCodeBenan.getMessage().equals("已经领取") )
                {
                    mCache.put("dailycheckin", System.currentTimeMillis()+"");
                    checkIsdailycheckin();
                    page = 1;

                }

            }
        });
    }

    private void getmypoin() {
        RxRetrofitClient.getInstance(this).getmypoin(new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(BonusPointTokenActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    tokenCountTv.setText(vCodeBenan.getData());
                }
            }
        });
    }


}
