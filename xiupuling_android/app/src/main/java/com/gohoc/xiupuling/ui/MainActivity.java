package com.gohoc.xiupuling.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.adapter.BaseFragment;
import com.gohoc.xiupuling.adapter.MainPagerAdapter;
import com.gohoc.xiupuling.bean.MsgBean;
import com.gohoc.xiupuling.bean.ShopLimtBean;
import com.gohoc.xiupuling.bean.TabEntity;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.WalletBean;
import com.gohoc.xiupuling.constant.Constant;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.account.AccountActivity;
import com.gohoc.xiupuling.ui.account.AccountSettingActivity;
import com.gohoc.xiupuling.ui.account.BonusPointTokenActivity;
import com.gohoc.xiupuling.ui.account.CardCouponsActivity;
import com.gohoc.xiupuling.ui.account.CopyrightActivity;
import com.gohoc.xiupuling.ui.account.HelperCenterActivity;
import com.gohoc.xiupuling.ui.account.MemberAuthorizationActivity;
import com.gohoc.xiupuling.ui.account.MemberRoleActivity;
import com.gohoc.xiupuling.ui.account.MemberShopActivity;
import com.gohoc.xiupuling.ui.account.ToBeShowerActivity;
import com.gohoc.xiupuling.ui.account.ToBeTerminalerActivity;
import com.gohoc.xiupuling.ui.account.WalletActivity;
import com.gohoc.xiupuling.ui.group.AddGroupActivity;
import com.gohoc.xiupuling.ui.group.JoinGroupActivity;
import com.gohoc.xiupuling.ui.order.OrderHistoryActivity;
import com.gohoc.xiupuling.ui.push.PushHistoryActivity;
import com.gohoc.xiupuling.ui.tabfragment.GroupFragment;
import com.gohoc.xiupuling.ui.tabfragment.PushManagerFragment;
import com.gohoc.xiupuling.ui.tabfragment.ShopTerminalFragment;
import com.gohoc.xiupuling.ui.tabfragment.WorksFragment;
import com.gohoc.xiupuling.ui.terminal.AddShopActivity;
import com.gohoc.xiupuling.ui.terminal.AddTerminalActivity;
import com.gohoc.xiupuling.utils.ACache;
import com.gohoc.xiupuling.utils.BDresult;
import com.gohoc.xiupuling.utils.BaiduLocation;
import com.gohoc.xiupuling.utils.CustomUtils;
import com.gohoc.xiupuling.utils.CustomViewPager;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.LogUtil;
import com.gohoc.xiupuling.utils.NumberUtil;
import com.gohoc.xiupuling.utils.SpanUtils;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wuxiaolong.androidutils.library.VersionUtil;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;


public class MainActivity extends AppCompatActivity implements Constant, View.OnClickListener {

    private RelativeLayout slideMenuAccount;
    private LinearLayout slideMenuWallet;
    private LinearLayout slideMenuCoupons;
    private LinearLayout slideMenuBonus;
    private LinearLayout slideMenuRole;
    private LinearLayout slideMenuPush;
    private LinearLayout slideMenuShop;
    private LinearLayout slideMenuAuthorization;
    private LinearLayout slideMnuZizhuHistory;
    private LinearLayout slideMenuOrder;
    private LinearLayout slideMenuHelp;
    private TextView walletTv,slide_menu_version_tv;


    private LinearLayout slideMenuSetting;
    //状态栏
    private SystemBarTintManager mTintManager;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private CustomViewPager viewpager;
    private MainPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    private CommonTabLayout tabLayout;
    private TopRightMenu mTopRightMenu;
    private boolean isShowTopRightMenu = false;
    private CircleImageView silde_user_pic_iv;
    private boolean hasmsg;

    private String[] mTitles = {
            TableConstant.Table_TERMINAL, TableConstant.Table_GROUP,
            TableConstant.Table_WORKS,TableConstant.Table_PUSH};
    private int[] mIconUnselectIds = {
            R.mipmap.icon_tab_port_nor, R.mipmap.icon_tab_group_nor,
            R.mipmap.icon_tab_design_nor, R.mipmap.icon_tab_show_nor};
    private int[] mIconSelectIds = {
            R.mipmap.icon_tab_port_sel, R.mipmap.icon_tab_group_sel,
            R.mipmap.icon_tab_design_sel, R.mipmap.icon_tab_show_se};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    private ImageView  imageView8, imageView7;
    private TextView silde_user_name_tv, silde_user_mobile_tv,tv_title;
    private Toolbar toolbar;
    private int positionFlag = 0;
    private ACache mCache;

    private View contentView;
    private BaiduLocation baiduLocation;
    private Window window;
    private AlertDialog dialog;
    private UserBaseBean userBaseBean;
    private boolean isZZ = true;//true :机主 false : 展主
    private int tablePostion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCache = ACache.get(this);
        EventBus.getDefault().register(this);
        getmymessage();
        //sampleText.setText("Hello ButterKnife"+" And  "+stringFromJNI());
        initSlide();
        initViewpager();
        initToolbar();
        userBaseBean = Credential.getInstance().getCurUser(this);
        initUserInfo(userBaseBean);
        if (baiduLocation == null) {
            baiduLocation = new BaiduLocation(this).setbDresult(new BDresult() {
                @Override
                public void onCallBack(final BDLocation location) {

                }
            }).initBaiduLocation();
        }
    }

    private void showPop(final int types) {
        if (userBaseBean == null || userBaseBean.getData() == null)
            return;
        if ((types == 0 && userBaseBean.getData().getIs_id_jz() == 0) || (types == 1 && userBaseBean.getData().getIs_id_zz() == 0)) {
            mCache.put("tipsTimes", System.currentTimeMillis() + "");

            if (Build.VERSION.SDK_INT >= 14) {
                dialog = new AlertDialog.Builder(this,
                        R.style.dialogTips).create();
            } else {
                dialog = new AlertDialog.Builder(this).create();
            }
            dialog.show();
            window = dialog.getWindow();
            dialog.setCanceledOnTouchOutside(false);
            // *** 主要就是在这里实现这种效果的.
            // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
            if (types == 0)
                window.setContentView(R.layout.pop_to_be_jz);
            else
                window.setContentView(R.layout.pop_to_be_zz);

            window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (types == 0)
                        startActivity(new Intent(MainActivity.this, ToBeTerminalerActivity.class));
                    else
                        startActivity(new Intent(MainActivity.this, ToBeShowerActivity.class));
                }
            });
            window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

    }

    private void showShopPop(String count, String ownCount, String title) {
        dialog = new AlertDialog.Builder(this,
                R.style.dialogTips).create();
        dialog.show();
        window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(false);
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容

        window.setContentView(R.layout.dialog_shop_limt_alert);

        window.findViewById(R.id.to_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, MemberShopActivity.class));

            }
        });
        window.findViewById(R.id.check_auth_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, MemberAuthorizationActivity.class));
            }
        });
        window.findViewById(R.id.bt_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        SpanUtils su = new SpanUtils();
        su.append("您目前只能管理" + count + "个" + title + "， 如果需管理更多门店，请前往会员商城或参与")
                .append("官方活动").setForegroundColor(Color.parseColor("#38b2fd"))
                .append("扩充拥有数。");
        TextView tips_title_tv = (TextView) window.findViewById(R.id.tips_title_tv);
        tips_title_tv.setText(su.create());
        TextView shop_count_tv = (TextView) window.findViewById(R.id.shop_count_tv);
        su = new SpanUtils();
        su.append(title + " ").append(ownCount).setForegroundColor(Color.parseColor("#38b2fd"));
        shop_count_tv.setText(su.create());
        su = new SpanUtils();
        TextView check_auth_tv = (TextView) window.findViewById(R.id.check_auth_tv);
        check_auth_tv.setText(su.append(" 查询其它").setForegroundColor(Color.parseColor("#929292")).append("会员权限").setForegroundColor(Color.parseColor("#38b2fd")).create());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReLoginUpdate(Event.ReLoginUpdate message) {
        userBaseBean = Credential.getInstance().getCurUser(this);
        initUserInfo(userBaseBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void walletUpdate(Event.WalletUpdate walletUpdate) {
        getmymessage();
        pocketmoney();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UserEvent(Event.UserEvent userEvent) {
        getmymessage();
        userBaseBean = Credential.getInstance().getCurUser(this);
        initUserInfo(userBaseBean);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MainIndex(Event.MainIndex mainIndex) {
        Logger.e("mainIndexmainIndexmainIndexmainIndexmainIndex");
        changeView(mainIndex.i);
    }


    private void initUserInfo(UserBaseBean userBaseBean) {
        if (null != userBaseBean && null != userBaseBean.getData()) {
            Logger.d("portrait_url:: " + BASE_USER_RESOURE + userBaseBean.getData().getPortrait_url());
            Glide.with(MainActivity.this)
                    .load(BASE_USER_RESOURE + userBaseBean.getData().getPortrait_url() + Utils.getThumbnail(100, 100))
                    //  .placeholder(R.mipmap.icon_usercenter_morentouxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //  .error(R.mipmap.icon_usercenter_morentouxiang)
                    .into(silde_user_pic_iv);

            silde_user_name_tv.setText(userBaseBean.getData().getNick_name() + "");
            silde_user_mobile_tv.setText(Utils.doMobile(userBaseBean.getData().getMobile()) + "");
            pocketmoney();

            CustomUtils.showMendian(MainActivity.this);

            //    popupWindow.showAsDropDown(findViewById(R.id.main_layout));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != drawer)
            drawer.closeDrawers(); // 关闭导航菜单
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != drawer)
            drawer.closeDrawers(); // 关闭导航菜单
    }

    private void initRightMenu() {
        mTopRightMenu = new TopRightMenu(MainActivity.this);
        //添加菜单项
        List<MenuItem> menuItems = new ArrayList<>();
        if (positionFlag == 1) {
            menuItems.add(new MenuItem(R.mipmap.icon_x_join, "新建群组"));
            menuItems.add(new MenuItem(R.mipmap.icon_group_join, "加入已有群组"));

        } else {
            menuItems.add(new MenuItem(R.mipmap.icon_shop_xinjian, "新建门店"));
            menuItems.add(new MenuItem(R.mipmap.icon_tianjiazhongduan_, "添加终端"));
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
                        if (positionFlag == 1) {
                            if (position == 0)
                                startActivity(new Intent(MainActivity.this, AddGroupActivity.class));
                            else
                                startActivity(new Intent(MainActivity.this, JoinGroupActivity.class));
                        } else {
                            if (position == 0) {
                                checkShopCntRight();
                            } else {
                                checkTerminalOwnCntRight();
                            }
                        }

                    }
                });
        int x = -imageView7.getWidth() * 10;


        Logger.e(x + "");
        if (positionFlag == 0 || positionFlag == 1) {
            mTopRightMenu.showAsDropDownM(imageView7, 50);  //新建门店或群
        } else {
            //消息列表
            startActivity(new Intent(MainActivity.this, MsgActivity.class));
        }

    }

    private void initViewpager() {

        viewpager = (CustomViewPager) findViewById(R.id.main_viewpager);
        //  fragmentList.add(TerminalFragment.newInstance());
        fragmentList.add(ShopTerminalFragment.newInstance());//门店终端
        fragmentList.add(GroupFragment.newInstance());//群组
        //fragmentList.add(AdvertFragment.newInstance());//接广告
        fragmentList.add(WorksFragment.newInstance());//作品
        fragmentList.add(PushManagerFragment.newInstance());//投播管理
        //fragmentList.add(PushFragment.newIn//投放
        //fragmentList.add(ScanFragment.newInstance());

        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewpager.setAdapter(adapter);

        tabLayout = (CommonTabLayout) findViewById(R.id.main_tablayout);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTextsize(12);
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 2)
                    if (Credential.getInstance().getCurUser(MainActivity.this).getData().getShared_platform() == 0) {
                        Utils.toast(MainActivity.this, "您需要在设置中开启共享计划才能开放此功能");
                        tabLayout.setCurrentTab(tablePostion);
                        return;
                    }
                tablePostion = position;
                changeView(position);
                LogUtil.d("当前位置");
                setTitleBar(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    //设置标题文字
    private void setTitleBar(int position)
    {
        switch (position){
            case 0:
                tv_title.setText("门店和终端");
                break;
            case 1:
                tv_title.setText("群组");
                break;
            case 2:
                tv_title.setText("作品");
                break;
            case 3:
                tv_title.setText("投播管理");
                break;
        }
    }

    private void changeView(int position) {
        LogUtil.d("选中当前位置："+position);

        //公用信息的展示
        viewpager.setCurrentItem(position);
        tabLayout.setCurrentTab(position);
        positionFlag = position;
        if (positionFlag == 0 || positionFlag == 1){
            imageView7.setImageResource(R.drawable.add_selector);
        }else{
            if(hasmsg){
                imageView7.setImageResource(R.mipmap.icon_usertouxiang_biaozhu);
            }else{
                imageView7.setImageResource(R.mipmap.icon_daren_tongzhi);
            }
        }

        //弹窗提示
          //旧规则
        /*try {
            if (position ==0 ||position ==1) {//门店终端   群组
                if (isZZ == false)
                    showPop(0);
                isZZ = true;
            } else if (position ==2 ){//作品  透播管理（不展示）
                if (isZZ == true)
                    showPop(1);
                isZZ = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        if (userBaseBean == null || userBaseBean.getData() == null)
            return;
         //新规则
            try {
            if(position==0){//门店终端
                CustomUtils.showMendian(MainActivity.this);
            }else if(position==1){//群组
                CustomUtils.showQunzu(MainActivity.this);
            }else if(position==2){//作品  透播管理（不展示）
                CustomUtils.showZuoping(MainActivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSlide() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
            navigationMenuView.setBackgroundResource(R.mipmap.img_usercenter_bg);
        }

        Logger.d((navigationView == null));
        // navigationView.getHeaderView()
        View header = navigationView.getHeaderView(0);

        //View headerLayout = navigationView.findViewById(R.id.slide_menu_account_rv);
        slideMenuAccount = (RelativeLayout) header.findViewById(R.id.slide_menu_account_rv);

        slideMenuWallet = (LinearLayout) header.findViewById(R.id.slide_menu_wallet_lv);
        slideMenuCoupons = (LinearLayout) header.findViewById(R.id.slide_menu_coupons_lv);
        slideMenuBonus = (LinearLayout) header.findViewById(R.id.slide_menu_bonus_lv);
        slideMenuRole = (LinearLayout) header.findViewById(R.id.slide_menu_role_lv);
        slideMenuPush = (LinearLayout) header.findViewById(R.id.slide_menu_push_lv);
        slideMenuSetting = (LinearLayout) header.findViewById(R.id.slide_menu_setting_lv);
        slideMenuShop = (LinearLayout) header.findViewById(R.id.slide_menu_shop_lv);
        slideMenuAuthorization = (LinearLayout) header.findViewById(R.id.slide_menu_authorization_lv);
        slideMenuOrder = (LinearLayout) header.findViewById(R.id.slide_menu_order_lv);
        slideMnuZizhuHistory = (LinearLayout) header.findViewById(R.id.slide_menu_zizhu_history_ll);
        slideMenuHelp = (LinearLayout) header.findViewById(R.id.slide_menu_help_lv);

        slideMenuAccount.setOnClickListener(this);
        slideMenuWallet.setOnClickListener(this);
        slideMenuCoupons.setOnClickListener(this);
        slideMenuBonus.setOnClickListener(this);
        slideMenuRole.setOnClickListener(this);
        slideMenuPush.setOnClickListener(this);
        slideMenuShop.setOnClickListener(this);
        slideMenuSetting.setOnClickListener(this);
        slideMenuAuthorization.setOnClickListener(this);
        slideMnuZizhuHistory.setOnClickListener(this);
        slideMenuOrder.setOnClickListener(this);
        slideMenuHelp.setOnClickListener(this);

        silde_user_pic_iv = (CircleImageView) header.findViewById(R.id.silde_user_pic_iv);
        silde_user_name_tv = (TextView) header.findViewById(R.id.silde_user_name_tv);
        silde_user_mobile_tv = (TextView) header.findViewById(R.id.silde_user_mobile_tv);
        walletTv = (TextView) header.findViewById(R.id.wallet_money_tv);
        slide_menu_version_tv = (TextView) header.findViewById(R.id.slide_menu_version_tv);
        slide_menu_version_tv.setText("版本号：v"+VersionUtil.getVersionName(MainActivity.this));

    }

    private void pocketmoney() {
        RxRetrofitClient.getInstance(this).pocketmoney(new Observer<WalletBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "请检查网络是否正常", Toast.LENGTH_SHORT).show();
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Logger.e(e.toString());
                }
            }

            @Override
            public void onNext(WalletBean walletBean) {
                if (walletBean.getCode() == 1) {
                    walletTv.setText("账户余额 " + NumberUtil.getDecimal(walletBean.getBalance()) + "元");
                }

            }
        });
    }

    private void initToolbar() {
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView8 = (ImageView) findViewById(R.id.imageView8);
        tv_title=(TextView) findViewById(R.id.textView6);
        tv_title.setText("门店和终端");
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.slide_menu_account_rv:
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                drawer.closeDrawers(); // 关闭导航菜单
                break;
            case R.id.imageView8:
                Credential.getInstance().updateUserInfo(this);
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.imageView7:
                initRightMenu();
                break;
            case R.id.slide_menu_setting_lv:
                startActivity(new Intent(MainActivity.this, AccountSettingActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_wallet_lv:
                startActivity(new Intent(MainActivity.this, WalletActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_shop_lv:
                startActivity(new Intent(MainActivity.this, MemberShopActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_authorization_lv:
                startActivity(new Intent(MainActivity.this, MemberAuthorizationActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_bonus_lv:
                startActivity(new Intent(MainActivity.this, BonusPointTokenActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_role_lv:
                startActivity(new Intent(MainActivity.this, MemberRoleActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_zizhu_history_ll:
                startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_order_lv:
                startActivity(new Intent(MainActivity.this, CopyrightActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_coupons_lv:
                startActivity(new Intent(MainActivity.this, CardCouponsActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_help_lv:
                startActivity(new Intent(MainActivity.this, HelperCenterActivity.class));
                drawer.closeDrawers();
                break;
            case R.id.slide_menu_push_lv://
                Intent intent=new Intent(MainActivity.this, PushHistoryActivity.class);
                intent.putExtra("title","展主历史投放");
                startActivity(intent);
                drawer.closeDrawers();
                break;
        }
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showRightMeunEventBus(Event.ShowRightMeunEvent message) {
        initRightMenu();
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            moveTaskToBack(false);
/*            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }*/
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void checkShopCntRight() {
        RxRetrofitClient.getInstance(this).checkShopCntRight(new Observer<ShopLimtBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MainActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ShopLimtBean shopLimtBean) {
                if (shopLimtBean.getCode() == 1) {
                    startActivity(new Intent(MainActivity.this, AddShopActivity.class));
                } else {
                    showShopPop(shopLimtBean.getShopcnt() + "", shopLimtBean.getShopowncnt() + "", "门店");
                }
            }
        });
    }

    private void checkTerminalOwnCntRight() {
        RxRetrofitClient.getInstance(this).checkTerminalOwnCntRight(new Observer<ShopLimtBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MainActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(ShopLimtBean shopLimtBean) {
                if (shopLimtBean.getCode() == 1) {
                    startActivity(new Intent(MainActivity.this, AddTerminalActivity.class));
                } else {
                    showShopPop(shopLimtBean.getTerminalcnt() + "", shopLimtBean.getTerminalowncnt() + "", "终端");
                }
            }
        });
    }

    private void getmymessage() {
        RxRetrofitClient.getInstance(this).getmymessage("0", new Observer<MsgBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(MainActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(final MsgBean msgBean) {
                if (msgBean.getCode() == 1) {
                    hasmsg = true;
                   if (msgBean.getData().size() > 0) {
                        hasmsg = true;
                    } else
                        hasmsg = false;
                }


            }
        });
    }


}
