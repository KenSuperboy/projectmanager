package com.gohoc.xiupuling.ui.useraudit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/*
* vip码的详情
* */
public class VipDetailActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView mToolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.linear_item_layout)
    LinearLayout mLinearItemLayout;
    @BindView(R.id.tv_tips)
    TextView mTvTips;

    private String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_detail_layout);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        initView();
        initListener();
        initDatas();
    }

    private void initView() {
        type = getIntent().getStringExtra("type");
        mToolbarTitle.setText("终端VIP码");
        if (type.equals("0")) {
            //空闲的  可以去绑定的
            mIvRight.setVisibility(View.VISIBLE);
            mTvShopName.setText("");
        } else if (type.equals("1")) {
            //非空闲的
            mIvRight.setVisibility(View.GONE);
            mTvShopName.setText(getIntent().getStringExtra("name")+"    "+getIntent().getStringExtra("number")+"号机");
        }
    }

    private void initListener() {

    }

    private void initDatas() {

        mTvNumber.setText(getIntent().getStringExtra("code"));
        mTvCount.setText(getIntent().getStringExtra("count"));
    }

    @OnClick({R.id.toolbar_left_title, R.id.linear_item_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.linear_item_layout:
                if (type.equals("0")) {
                    //空闲的  可以去绑定的
                } else if (type.equals("1")) {
                    //非空闲的  可以去绑定的
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==100){
                bind(data.getStringExtra("terminal_id"));
            }
        }
    }

    private void bind(String terminal_id)
    {
        RxRetrofitClient.getInstance(VipDetailActivity.this).bindVip(terminal_id,mTvNumber.getText().toString(), new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(VipDetailActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    Utils.toast(VipDetailActivity.this, "绑定成功");
                } else {
                    Utils.toast(VipDetailActivity.this, vCodeBenan.getMessage());
                }
            }
        });
    }
}
