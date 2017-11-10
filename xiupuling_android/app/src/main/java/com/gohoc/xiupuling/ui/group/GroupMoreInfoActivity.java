package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class GroupMoreInfoActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.group_info_tv)
    TextView groupInfoTv;
    @BindView(R.id.group_pic_iv)
    CircleImageView groupPicIv;
    @BindView(R.id.group_cover_ll)
    LinearLayout groupCoverLl;
    @BindView(R.id.group_id_tv)
    TextView groupIdTv;
    @BindView(R.id.group_name_tv)
    TextView groupNameTv;
    @BindView(R.id.group_name_ll)
    LinearLayout groupNameLl;
    @BindView(R.id.group_flage_tv)
    TextView groupFlageTv;
    @BindView(R.id.group_flage_ll)
    LinearLayout groupFlageLl;
    @BindView(R.id.group_user_list_ll)
    LinearLayout groupUserListLl;
    @BindView(R.id.group_belong_ll)
    LinearLayout groupBelongLl;
    @BindView(R.id.group_invite_ll)
    LinearLayout groupInviteLl;
    @BindView(R.id.group_size_ll)
    LinearLayout groupSizeLl;
    @BindView(R.id.moreot)
    LinearLayout moreot;
    private GroupDetailsBean groupDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_more_info);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("群组信息");
        EventBus.getDefault().register(this);
        try {
            groupDetailsBeans = (GroupDetailsBean) getIntent().getExtras().get("groupDetailsBeans");
            init();
        } catch (Exception e) {

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GroupEvent(Event.GroupEvent message) {
        groupDetailsBeans = message.groupDetailsBeans;
        init();
    }

    private void init() {
        if(!TextUtils.isEmpty(groupDetailsBeans.getData().getUnion_num())){
            groupIdTv.setText(groupDetailsBeans.getData().getUnion_num() + "");
        }else {
            groupIdTv.setText("");
        }

        if(!TextUtils.isEmpty(groupDetailsBeans.getData().getUnion_name() )){
            groupNameTv.setText(groupDetailsBeans.getData().getUnion_name() + "");
        }else {
            groupNameTv.setText("");
        }

        if(!TextUtils.isEmpty(groupDetailsBeans.getData().getUnion_brief_info() )){
            groupFlageTv.setText(groupDetailsBeans.getData().getUnion_brief_info() + "");
        }else {
            groupFlageTv.setText("");
        }

        Glide.with(this)
                .load(BASE_USER_RESOURE + groupDetailsBeans.getData().getUnion_portrait()+ Utils.getThumbnail(100,100))
                //.placeholder(R.mipmap.icon_port_home)
                .error(R.mipmap.icon_port_home)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(groupPicIv);

        moreot.setVisibility(groupDetailsBeans.getData().getUnion_type() == 1 ||groupDetailsBeans.getData().getUnion_type() == 4? View.GONE : View.VISIBLE);

    }

    @OnClick({R.id.toolbar_left_title, R.id.group_cover_ll, R.id.group_name_ll, R.id.group_flage_ll, R.id.group_user_list_ll, R.id.group_belong_ll, R.id.group_invite_ll, R.id.group_size_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                GroupMoreInfoActivity.this.finish();
                break;
            case R.id.group_cover_ll:
                startActivity(new Intent(GroupMoreInfoActivity.this, GroupCoverActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                break;
            case R.id.group_name_ll:
                startActivity(new Intent(GroupMoreInfoActivity.this, GroupNameActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                break;
            case R.id.group_flage_ll:
                startActivity(new Intent(GroupMoreInfoActivity.this, GroupFlageActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                break;
            case R.id.group_user_list_ll:
                startActivity(new Intent(GroupMoreInfoActivity.this, GroupUserListActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                break;
            case R.id.group_belong_ll:
                startActivity(new Intent(GroupMoreInfoActivity.this, GroupCessionActivity.class).putExtra("union_id", groupDetailsBeans.getData().getUnion_id()));
                break;
            case R.id.group_invite_ll:
                startActivity(new Intent(GroupMoreInfoActivity.this, GroupInviteActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                break;
            case R.id.group_size_ll:
                startActivity(new Intent(GroupMoreInfoActivity.this, GruopSizeActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
