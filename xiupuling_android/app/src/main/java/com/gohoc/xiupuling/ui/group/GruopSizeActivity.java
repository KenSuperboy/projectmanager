package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class GruopSizeActivity extends BasicActivity {

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
    @BindView(R.id.group_size_tv)
    TextView groupSizeTv;
    @BindView(R.id.inviteTv)
    TextView inviteTv;
    private GroupDetailsBean groupDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruop_size);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("群组大小");
        try {
            groupDetailsBeans = (GroupDetailsBean) getIntent().getExtras().get("groupDetailsBeans");

            Glide.with(this)
                    .load(BASE_USER_RESOURE + groupDetailsBeans.getData().getOwer().getPortrait_url()+ Utils.getThumbnail(100,100))
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(shopPicIv);

            groupNameTv.setText(groupDetailsBeans.getData().getOwer().getNick_name() + "");
           // groupTypeTv.setText(groupDetailsBeans.getData().getOwer().get + "");
            groupSizeTv.setText(groupDetailsBeans.getData().getUnion_size() + "台终端群组");
            inviteTv.setVisibility(groupDetailsBeans.getData().getUnion_type() == 1 ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }


    @OnClick({R.id.toolbar_left_title, R.id.inviteTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                finish();
                break;
            case R.id.inviteTv:
                    startActivity(new Intent(GruopSizeActivity.this, GroupInviteActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                break;
        }
    }
}
