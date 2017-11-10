package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.utils.ViewUtils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import rx.Observer;

public class JoinGroupDtActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.shop_pic_iv)
    CircleImageView shopPicIv;
    @BindView(R.id.group_name_tv)
    TextView groupNameTv;
    @BindView(R.id.group_type_tv)
    TextView groupTypeTv;
    @BindView(R.id.group_flage_tv)
    TextView groupFlageTv;
    @BindView(R.id.group_ter_cover_iv)
    CircleImageView groupTerCoverIv;
    @BindView(R.id.terinal_name_tv)
    TextView terinalNameTv;
    @BindView(R.id.invite_et)
    EditText inviteEt;
    @BindView(R.id.sumbit_button_ll)
    LinearLayout sumbitButtonLl;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.gonxiang)
    LinearLayout gonxiang;
    @BindView(R.id.liansuo)
    LinearLayout liansuo;
    @BindView(R.id.meiti)
    LinearLayout meiti;
    private String code = "";
    private GroupDetailsBean groupDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group_dt);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("加入群组");
        getUnionDetailInfo(getIntent().getStringExtra("id"));
        code = getIntent().getStringExtra("code");
        if (!TextUtils.isEmpty(code)) {
            sumbitButtonLl.setBackgroundResource(R.drawable.login_bt_selector);
            inviteEt.setText(code);
        } else {
            sumbitButtonLl.setBackgroundResource(R.color.unable);
        }
        inviteEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                code = s.toString();
                if (!TextUtils.isEmpty(code)) {
                    sumbitButtonLl.setBackgroundResource(R.drawable.login_bt_selector);
                } else {
                    sumbitButtonLl.setBackgroundColor(Color.parseColor("#CBCBCB"));
                }

            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.sumbit_button_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                JoinGroupDtActivity.this.finish();
                break;
            case R.id.sumbit_button_ll:
                if (!TextUtils.isEmpty(code))
                    doJoinUnion();
                break;
        }
    }


    private void doJoinUnion() {
        RxRetrofitClient.getInstance(this).doJoinUnion(groupDetailsBeans.getData().getUnion_id() + "", inviteEt.getText() + "", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(JoinGroupDtActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    startActivity(new Intent(JoinGroupDtActivity.this, GroupDetailsActivity.class)
                            .putExtra("unionId", groupDetailsBeans.getData().getUnion_id())
                            .putExtra("type", 1)
                    );
                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                    EventBus.getDefault().post(new Event.MainIndex(2));
                    JoinGroupDtActivity.this.finish();
                } else
                    Utils.toast(JoinGroupDtActivity.this, vCodeBenan.getMessage());

            }
        });
    }

    private void getUnionDetailInfo(String union_id) {
        RxRetrofitClient.getInstance(JoinGroupDtActivity.this).unionDetailInfo(union_id, new Observer<GroupDetailsBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(JoinGroupDtActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupDetailsBean groupDetailsBean) {
                if (groupDetailsBean.getCode() == 1) {
                    groupDetailsBeans = groupDetailsBean;


                    Glide.with(JoinGroupDtActivity.this)
                            .load(NetConstant.BASE_USER_RESOURE + groupDetailsBeans.getData().getUnion_portrait())
                            //  .placeholder(R.mipmap.icon_port_home)
                            // .error(R.mipmap.icon_port_home)
                            .into(shopPicIv);
                    groupNameTv.setText(groupDetailsBeans.getData().getUnion_name() + "");
                    groupFlageTv.setText(groupDetailsBeans.getData().getUnion_brief_info() + "");
                    groupTypeTv.setText(groupDetailsBeans.getData().getUnion_typeStr());
                    terinalNameTv.setText(groupDetailsBeans.getData().getOwer().getNick_name() + "");
                    Glide.with(JoinGroupDtActivity.this)
                            .load(NetConstant.BASE_USER_RESOURE + groupDetailsBeans.getData().getOwer().getPortrait_url() + "")
                            //  .placeholder(R.mipmap.icon_port_home)
                            // .error(R.mipmap.icon_port_home)
                            .into(groupTerCoverIv);

                    if (groupDetailsBeans.getData().getUnion_type() == 0) {//共享群组
                        gonxiang.setVisibility(View.VISIBLE);
                        liansuo.setVisibility(View.GONE);
                        meiti.setVisibility(View.GONE);
                    } else if (groupDetailsBeans.getData().getUnion_type() == 2) {//连锁群组
                        gonxiang.setVisibility(View.GONE);
                        liansuo.setVisibility(View.VISIBLE);
                        meiti.setVisibility(View.GONE);
                    } else {                                     //媒体群组
                        gonxiang.setVisibility(View.GONE);
                        liansuo.setVisibility(View.GONE);
                        meiti.setVisibility(View.VISIBLE);
                    }

                }

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ViewUtils.isTouchedViewOutSideView(inviteEt, event)) {
            showInput(false);
        }
        return super.onTouchEvent(event);

    }

}
