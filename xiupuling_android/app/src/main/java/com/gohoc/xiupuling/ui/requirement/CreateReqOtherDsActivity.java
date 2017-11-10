package com.gohoc.xiupuling.ui.requirement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.StarLeveMoney;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.ClearEditText;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class CreateReqOtherDsActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.req_et)
    ClearEditText reqEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_req_other_ds);
        setStatusColor(R.color.colorPrimary);
        ButterKnife.bind(this);
        toolbarTitle.setText("设计要求");
        try {
            reqEt.setText(getIntent().getStringExtra("req")+"");
            getdictrequirementhintlist();
        }catch (Exception e)
        {

        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.toolbar_right:
                setResult(RESULT_OK,new Intent().putExtra("req",reqEt.getText()+""));
                finish();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(reqEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }


    private void getdictrequirementhintlist()
    {
        RxRetrofitClient.getInstance(this).getdictrequirementhintlist(new Observer<StarLeveMoney>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(CreateReqOtherDsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(StarLeveMoney starLeveMoney) {
                   if(starLeveMoney.getCode()==1)
                   {
                       reqEt.setHint(starLeveMoney.getData().get( new Random().nextInt( starLeveMoney.getData().size())).getDictcaption());

                   }
            }
        });
    }
}
