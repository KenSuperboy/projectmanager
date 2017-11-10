package com.gohoc.xiupuling.ui.group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class GroupInfoOtherActivity extends BasicActivity {


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
    private GroupDetailsBean groupDetailsBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruop_orther);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("群主");
        try {
            groupDetailsBeans = (GroupDetailsBean) getIntent().getExtras().get("groupDetailsBeans");

            Glide.with(this)
                    .load(BASE_USER_RESOURE+groupDetailsBeans.getData().getOwer().getPortrait_url())
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(shopPicIv);

            groupNameTv.setText(groupDetailsBeans.getData().getOwer().getNick_name()+"");

            groupSizeTv.setText(groupDetailsBeans.getData().getUnion_size()+"台终端群组");

        } catch (Exception e) {
            Logger.e(e.toString());
        }
    }

    @OnClick(R.id.toolbar_left_title)
    public void onClick() {
        finish();
    }
}
