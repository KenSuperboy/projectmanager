package com.gohoc.xiupuling.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.GroupTerminalAdater;
import com.gohoc.xiupuling.adapter.GroupTerminalOrtherAdater;
import com.gohoc.xiupuling.adapter.OnItemClickLitener;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.Credential;
import com.gohoc.xiupuling.ui.GroupAddTerminal_Link_Activity;
import com.gohoc.xiupuling.ui.MainActivity;
import com.gohoc.xiupuling.ui.MyShopActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class GroupDetailsActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    ImageView toolbarRight;
    @BindView(R.id.shop_pic_iv)
    CircleImageView shopPicIv;
    @BindView(R.id.group_name_tv)
    TextView groupNameTv;
    @BindView(R.id.group_type_tv)
    TextView groupTypeTv;
    @BindView(R.id.group_info_ll)
    LinearLayout groupInfoLl;
    @BindView(R.id.group_flage_tv)
    TextView groupFlageTv;
    @BindView(R.id.group_dt_ll)
    LinearLayout groupDtLl;
    @BindView(R.id.mygroup_tv)
    TextView mygroupTv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.other_group_tv)
    TextView otherGroupTv;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.is_ower_tv)
    TextView isOwerTv;
    @BindView(R.id.tv_add)
    TextView tv_add;
    private GroupTerminalAdater adater;
    private GroupTerminalOrtherAdater adaterOther;
    private TopRightMenu mTopRightMenu;
    private GroupDetailsBean groupDetailsBeans;
    private static int ADD_REQUEST_RESULT = 1000;
    private ACache cache;
    private UserBean userBean;
    private GroupTermianlListBean groupTermianlListBeans, groupTermianlListOhterBeans;
    private boolean isOwner = false;//是否是群主
    private String unionId;
    private boolean isShop=false;//判断是否有自己的店铺
    private String shop_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        try {
            cache = ACache.get(this);
            userBean = (UserBean) cache.getAsObject("userBean");
            unionId = getIntent().getStringExtra("unionId");
            getUnionDetailInfo(unionId);
        } catch (Exception e) {
        }
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("群组终端");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GroupEvent(Event.GroupEvent message) {
        getUnionDetailInfo(unionId);
    }

    private void getUnionDetailInfo(final String union_id) {
        RxRetrofitClient.getInstance(GroupDetailsActivity.this).unionDetailInfo(union_id, new Observer<GroupDetailsBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupDetailsActivity.this, "请检查网络是否正常");
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

                    Logger.e("xxxxxxx: " + groupDetailsBean.getData().getOwer().getUser_id() + "    " + groupDetailsBean.getData().getUser_id());

                    if (null != userBean) {
                        initInfo(groupDetailsBean);
                        if (userBean.getData().getUser_id().equals(groupDetailsBean.getData().getUser_id())) {
                            //群主
                            isOwner = true;
                        } else {
                            //成员
                            isOwner = false;
                            isOwerTv.setText("我是成员");
                        }
                        initOtherList();
                        initMineList();
                        getUnionTerminalListofTheirs(union_id);
                        getUnionTerminalListofMine(union_id);

                    }

                }

            }
        });
    }


    private void getUnionTerminalListofMine(String union_id) {
        RxRetrofitClient.getInstance(GroupDetailsActivity.this).getUnionTerminalListofMine(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupDetailsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1 && groupTermianlListBean.getData().size() > 0) {
                    isShop=true;//有自己的店铺
                    shop_id=groupTermianlListBean.getData().get(0).getShop_id();
                    groupTermianlListBeans = groupTermianlListBean;
                    adater.rf(groupTermianlListBeans);
                    adater.notifyDataSetChanged();
                    mygroupTv.setVisibility(View.VISIBLE);
                    if (groupDetailsBeans.getData().getUnion_type() == 1 || groupDetailsBeans.getData().getUnion_type() == 2)
                        mygroupTv.setText(String.format(getResources().getString(R.string.group_dt_tips1_1).toString(), groupTermianlListBean.getData().size() + ""));
                    else
                        mygroupTv.setText(String.format(getResources().getString(R.string.group_dt_tips1).toString(), groupTermianlListBean.getData().size() + ""));
                } else {
                    isShop=false;//有自己的店铺
                    if (adater != null)
                        adater.rf(null);
                    mygroupTv.setVisibility(View.GONE);

                }


            }
        });

    }

    private void getUnionTerminalListofTheirs(String union_id) {
        RxRetrofitClient.getInstance(GroupDetailsActivity.this).getUnionTerminalListofTheirs(union_id, new Observer<GroupTermianlListBean>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupDetailsActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(GroupTermianlListBean groupTermianlListBean) {
                if (groupTermianlListBean.getCode() == 1 && groupTermianlListBean.getData().size() > 0) {
                    groupTermianlListOhterBeans = groupTermianlListBean;
                    adaterOther.rf(groupTermianlListOhterBeans);
                    adaterOther.notifyDataSetChanged();
                    otherGroupTv.setVisibility(View.VISIBLE);
                    otherGroupTv.setText(String.format(getResources().getString(R.string.group_dt_tips2).toString(), groupTermianlListBean.getData().size() + ""));
                } else {
                    if (adaterOther != null)
                        adaterOther.rf(null);
                    otherGroupTv.setVisibility(View.GONE);
                }


            }
        });

    }

    private void initMineList() {
        if (adater == null) {
            adater = new GroupTerminalAdater(GroupDetailsActivity.this, groupTermianlListBeans, groupDetailsBeans.getData().getUnion_type());
            recyclerView.setAdapter(adater);
            recyclerView.setLayoutManager(new LinearLayoutManager(GroupDetailsActivity.this));
            // provinceList.setItemAnimator(new DefaultItemAnimator());
            adater.setOnItemClickLitener(new OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (groupDetailsBeans.getData().getOwer().getUser_id().equals(groupDetailsBeans.getData().getUser_id())) {
                        Logger.e(groupDetailsBeans.getData().getUnion_portrait() + "");
                        startActivity(new Intent(GroupDetailsActivity.this, GroupTerminalDtActivity.class)
                                .putExtra("terminaId", groupTermianlListBeans.getData().get(position).getTerminal_id())
                                .putExtra("portrait", groupDetailsBeans.getData().getUnion_portrait() + ""));

                    } else {

                    }

                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            adater.setOnDel(new OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    // deleteTermFromUnion(groupTermianlListBean.getData().get(position).getTerminal_id());
                    deleteTermFromUnion(groupTermianlListBeans.getData().get(position).getIds());
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        } else {
            adater.rf(groupTermianlListBeans);
        }

    }

    private void initOtherList() {
        adaterOther = new GroupTerminalOrtherAdater(GroupDetailsActivity.this, groupTermianlListOhterBeans, isOwner);
        recyclerView2.setAdapter(adaterOther);
        recyclerView2.setLayoutManager(new LinearLayoutManager(GroupDetailsActivity.this));
        // provinceList.setItemAnimator(new DefaultItemAnimator());
        adaterOther.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (Credential.getInstance().getCurUser(GroupDetailsActivity.this).getData().getShared_platform() == 1) {
                    startActivity(new Intent(GroupDetailsActivity.this, GroupTerminalDtActivity.class)
                            .putExtra("terminaId", groupTermianlListOhterBeans.getData().get(position).getTerminal_id())
                            .putExtra("portrait", groupDetailsBeans.getData().getUnion_portrait() + ""));
                } else {
                    Utils.toast(GroupDetailsActivity.this, "未开启共享平台计划，无法投放到别人的终端");
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        adaterOther.setOnDel(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                // deleteTermFromUnion(groupTermianlListBean.getData().get(position).getTerminal_id());
                deleteTermFromUnion(groupTermianlListOhterBeans.getData().get(position).getIds());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @OnClick({R.id.toolbar_left_title, R.id.toolbar_right, R.id.group_info_ll, R.id.group_dt_ll,R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                goBack();
                break;
            case R.id.toolbar_right:
                initRightMenu();
                break;
            case R.id.group_info_ll:
                if (null != userBean) {
                    if (userBean.getData().getUser_id().equals(groupDetailsBeans.getData().getUser_id())) {
                        startActivity(new Intent(GroupDetailsActivity.this, GroupMoreInfoActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                    } else {
                        startActivity(new Intent(GroupDetailsActivity.this, GroupInfoOtherActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                    }
                }
                break;
            case R.id.group_dt_ll:
                break;
            case R.id.tv_add:
                if(groupTypeTv.getText().toString().equals("联动群组")){
                    //先去选择店铺
                    LogUtil.d("选择店铺");
                    if(isShop){
                        //不需要选择店铺了
                        startActivity(new Intent(GroupDetailsActivity.this, GroupAddTerminal_Link_Activity.class).putExtra("shop_id", shop_id).putExtra("id",groupDetailsBeans.getData().getUnion_id()));
                    }else {
                        startActivity(new Intent(GroupDetailsActivity.this, MyShopActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                    }
                }else {
                    //然后选择终端
                    startActivityForResult(new Intent(GroupDetailsActivity.this, GroupAddTerminalActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans), ADD_REQUEST_RESULT);
                }
                break;
        }
    }

    private void goBack() {
        if (getIntent().getIntExtra("type", 0) == 1) {
            EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
            Intent intent = new Intent(GroupDetailsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            EventBus.getDefault().post(new Event.MainIndex(1));
            finish();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goBack();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void initInfo(GroupDetailsBean groupDetailsBean) {
        if(!TextUtils.isEmpty(groupDetailsBean.getData().getUnion_name())){
            groupNameTv.setText(groupDetailsBean.getData().getUnion_name() + "");
        }else {
            groupNameTv.setText("");
        }

        if(!TextUtils.isEmpty(groupDetailsBean.getData().getUnion_brief_info())){
            groupFlageTv.setText(groupDetailsBean.getData().getUnion_brief_info() + "");
        }else {
            groupFlageTv.setText( "");
        }

        if(!TextUtils.isEmpty(groupDetailsBean.getData().getUnion_typeStr())){
            groupTypeTv.setText(groupDetailsBean.getData().getUnion_typeStr() + "");
        }else {
            groupTypeTv.setText("");
        }

        Glide.with(this)
                .load(BASE_USER_RESOURE + groupDetailsBeans.getData().getUnion_portrait() + Utils.getThumbnail(200, 200))
                // .placeholder(R.mipmap.icon_dengru_touxiang)
                //.error(R.mipmap.icon_dengru_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(shopPicIv);
    }

    private void initRightMenu() {
        mTopRightMenu = new TopRightMenu(GroupDetailsActivity.this);
        //添加菜单项
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem(R.mipmap.icon_group_join, "添加终端"));
        if (null != userBean&&(!groupTypeTv.getText().toString().equals("联动群组"))) {
            if (userBean.getData().getUser_id().equals(groupDetailsBeans.getData().getUser_id()) && groupDetailsBeans.getData().getUnion_type() != 1)
                menuItems.add(new MenuItem(R.mipmap.icon_join_qun, "邀请加入"));
        }
        mTopRightMenu
         /*       .setHeight(480)     //默认高度480
                .setWidth(320)      //默认宽度wrap_content*/
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)        //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        if (position == 0)//添加终端按钮(联动群组的话，要先选择店铺)
                        {
                            if(groupTypeTv.getText().toString().equals("联动群组")){
                                //先去选择店铺
                                LogUtil.d("选择店铺");
                                startActivity(new Intent(GroupDetailsActivity.this, MyShopActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                            }else {
                                //然后选择终端
                                startActivityForResult(new Intent(GroupDetailsActivity.this, GroupAddTerminalActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans), ADD_REQUEST_RESULT);
                            }

                        }else{
                            startActivity(new Intent(GroupDetailsActivity.this, GroupInviteActivity.class).putExtra("groupDetailsBeans", groupDetailsBeans));
                        }
                    }
                });
        mTopRightMenu.showAsDropDownM(toolbarRight, 50, 20);  //带偏移量
        //带偏移量


    }

    private void deleteTermFromUnion(String ids) {
        RxRetrofitClient.getInstance(GroupDetailsActivity.this).deleteTermFromUnion(ids + "", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupDetailsActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Logger.e(e.toString());
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {

                if (vCodeBenan.getCode() == 1) {
                    getUnionDetailInfo(unionId);
                    EventBus.getDefault().post(new Event.RefreshGroupeListEvent());
                } else
                    Utils.toast(GroupDetailsActivity.this, vCodeBenan.getMessage());

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_REQUEST_RESULT) {
                getUnionDetailInfo(unionId);
                //getUnionTerminalListofTheirs(groupBean.getUnion_id());
                // getUnionTerminalListofMine(groupBean.getUnion_id());
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
