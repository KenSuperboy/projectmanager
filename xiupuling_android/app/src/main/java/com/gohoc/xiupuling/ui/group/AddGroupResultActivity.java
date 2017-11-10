package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.MyShopActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class AddGroupResultActivity extends BasicActivity {

    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.gruop_type)
    TextView gruopType;
    @BindView(R.id.cover_pic_iv)
    CircleImageView coverPicIv;
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.gruop_no)
    TextView gruopNo;
    @BindView(R.id.group_flage)
    TextView groupFlage;
    @BindView(R.id.termianl_og_iv)
    ImageView termianlOgIv;
    @BindView(R.id.add_termianl)
    LinearLayout addTermianl;
    private GroupDetailsBean groupDetailsBeans;
    private final  static int REQUEST_RESULT=10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_result);
        ButterKnife.bind(this);
        toolbarTitle.setText("成功建群组");
        setStatusColor(R.color.colorPrimary);

        groupDetailsBeans = (GroupDetailsBean) getIntent().getExtras().get("groupDetailsBeans");
        initdate();
    }

    private void initdate() {
        gruopType.setText(groupDetailsBeans.getData().getUnion_typeStr());
        Glide.with(this)
                .load(BASE_USER_RESOURE + groupDetailsBeans.getData().getUnion_portrait())
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(coverPicIv);
        groupFlage.setText(groupDetailsBeans.getData().getUnion_brief_info());
        gruopNo.setText(groupDetailsBeans.getData().getUnion_num());
        place.setText(groupDetailsBeans.getData().getUnion_name());
    }

    @OnClick({R.id.toolbar_left_title, R.id.add_termianl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.add_termianl:
                if(groupDetailsBeans.getData().getUnion_type()==4){
                    //联动群组
                    startActivity(new Intent(AddGroupResultActivity.this, MyShopActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                }else {
                    startActivityForResult(new Intent(AddGroupResultActivity.this, GroupAddTerminalActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans).putExtra("type", 1),REQUEST_RESULT);
                }
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
            setResult(RESULT_OK);
            finish();
        }
    }
}
