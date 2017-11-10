package com.gohoc.xiupuling.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class MemberRoleActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.xiaofeidaren_rv)
    RelativeLayout xiaofeidarenRv;
    @BindView(R.id.jizhu_rv)
    RelativeLayout jizhuRv;
    @BindView(R.id.zhngzhu_rl)
    RelativeLayout zhngzhuRl;
    @BindView(R.id.shejishi_rv)
    RelativeLayout shejishiRv;
    @BindView(R.id.ivf1)
    ImageView ivf1;
    @BindView(R.id.ivf2)
    ImageView ivf2;
    @BindView(R.id.ivf3)
    ImageView ivf3;
    @BindView(R.id.ivf4)
    ImageView ivf4;
    @BindView(R.id.tips1)
    TextView tips1;
    @BindView(R.id.tips2)
    TextView tips2;
    @BindView(R.id.tips3)
    TextView tips3;
    @BindView(R.id.tips4)
    TextView tips4;
    private static int TERMINALER_REQUEST_RESULT = 1000;
    private UserBaseBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_role);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("我的身份");
        setStatusColor(R.color.colorPrimary);
        Credential.getInstance().updateUserInfo(this);
        user = Credential.getInstance().getCurUser(this);
        initInfo(user);
    }

    private void initInfo(UserBaseBean user) {
        ///ivf1.setVisibility(user.getData().getIs_id()==1?View.VISIBLE:View.GONE);
        ivf2.setVisibility(user.getData().getIs_id_jz() == 1 ? View.VISIBLE : View.GONE);
        ivf3.setVisibility(user.getData().getIs_id_zz() == 1 ? View.VISIBLE : View.GONE);
        ivf4.setVisibility(user.getData().getIs_id_sjs() == 1 ? View.VISIBLE : View.GONE);
        tips2.setText(user.getData().getIs_id_jz() == 1 ? "机主" : "申请成为机主");
        tips3.setText(user.getData().getIs_id_zz() == 1 ? "展主" : "申请成为展主");
        tips4.setText(user.getData().getIs_id_sjs() == 1 ? "设计师" : "申请成为设计师");

    }


    @OnClick({R.id.xiaofeidaren_rv, R.id.jizhu_rv, R.id.zhngzhu_rl, R.id.shejishi_rv, R.id.toolbar_left_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xiaofeidaren_rv:
                //startActivity(new Intent(MemberRoleActivity.this,ToBeDesignerActivity.class));
                break;
            case R.id.jizhu_rv:
                if (user.getData().getIs_id_jz() != 1)
                    startActivityForResult(new Intent(MemberRoleActivity.this, ToBeTerminalerActivity.class), TERMINALER_REQUEST_RESULT);
                break;
            case R.id.zhngzhu_rl:
                if (user.getData().getIs_id_zz() != 1)
                    startActivityForResult(new Intent(MemberRoleActivity.this, ToBeShowerActivity.class), TERMINALER_REQUEST_RESULT);
                break;
            case R.id.shejishi_rv:
                if (user.getData().getIs_id_sjs() != 1)
                    startActivityForResult(new Intent(MemberRoleActivity.this, ToBeDesignerActivity.class), TERMINALER_REQUEST_RESULT);
                break;
            case R.id.toolbar_left_title:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            user = Credential.getInstance().getCurUser(this);
            initInfo(user);
        }
    }
}
