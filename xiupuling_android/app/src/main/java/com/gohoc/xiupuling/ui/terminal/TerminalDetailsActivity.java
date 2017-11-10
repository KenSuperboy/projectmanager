package com.gohoc.xiupuling.ui.terminal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.PushNormlBean;
import com.gohoc.xiupuling.bean.TerminalBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.ui.BindVipActivity;
import com.gohoc.xiupuling.ui.WebViewActivity;
import com.gohoc.xiupuling.ui.order.OrderExecuteStatusListActivity;
import com.gohoc.xiupuling.ui.requirement.MyWorksActivity;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.orhanobut.logger.Logger;
import com.wuxiaolong.androidutils.library.VersionUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class TerminalDetailsActivity extends BasicActivity {


    @BindView(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.terminal_id_tv)
    TextView terminalIdTv;
    @BindView(R.id.paly_info_ll)
    TextView palyInfoLl;
    @BindView(R.id.terminal_shop_tv)
    TextView terminalShopTv;
    @BindView(R.id.terminal_shop_ll)
    LinearLayout terminalShopLl;
    @BindView(R.id.terminal_noid_tv)
    TextView terminalNoidTv;
    @BindView(R.id.terminal_noid_ll)
    LinearLayout terminalNoidLl;
    @BindView(R.id.terminal_loop_setting_lv)
    LinearLayout terminalLoopSettingLv;
    @BindView(R.id.shop_industry_tv)
    TextView shopIndustryTv;
    @BindView(R.id.terminal_push_lv)
    LinearLayout terminalPushLv;
    @BindView(R.id.terminal_curr_paly_dt_ll)
    LinearLayout terminalCurrPalyDtLl;
    @BindView(R.id.terminal_order_lv)
    LinearLayout terminalOrderLv;
    @BindView(R.id.termianl_msg_manger_ll)
    LinearLayout termianlMsgMangerLl;
    @BindView(R.id.terminal_setting_ll)
    LinearLayout terminalSettingLl;
    @BindView(R.id.terminal_rest_lv)
    LinearLayout terminalRestLv;
    @BindView(R.id.terminal_flage_ll)
    LinearLayout terminalFlageLl;
    @BindView(R.id.terminal_about_ll)
    LinearLayout terminalAboutLl;
    @BindView(R.id.tv_bind_status)
    TextView mTvBindStatus;
    @BindView(R.id.terminal_vip_ll)
    LinearLayout mTerminalVipLl;
    @BindView(R.id.iv_vip_status)
    ImageView mIvVipStatus;
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    private String terminaId;
    private TerminalBean terminal;
    private static int NO_REQUEST_RESULT = 1000;
    private static int SETTING_REQUEST_RESULT = 1001;
    private static int BINDVIP_REQUEST_RESULT = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_details);
        ButterKnife.bind(this);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("终端管理");
        terminaId = this.getIntent().getStringExtra("terminaId");
        getTerminalDetail();
        Logger.d(terminaId);
    }

    private void getTerminalDetail() {
        RxRetrofitClient.getInstance(TerminalDetailsActivity.this).getTerminalDetail(terminaId, new Observer<TerminalBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(TerminalDetailsActivity.this, "修改失败，请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(TerminalBean terminalBean) {
                if (terminalBean.getCode() == 1) {
                    terminal = terminalBean;
                    terminalIdTv.setText(terminalBean.getData().getTerminal_id() + "");
                    String size = terminalBean.getData().getDisk_available() + "";
                    if (size.isEmpty() || "null".equals(size) || null == size)
                        size = "0";
                    palyInfoLl.setText(Utils.getPrintSize(Long.parseLong(size)) + "(秀铺令可用空间大小)");
                    terminalShopTv.setText(terminalBean.getData().getShop().getShop_name() + "");
                    terminalNoidTv.setText(terminalBean.getData().getTerminal_no() + "");

                    if (terminalBean != null && terminalBean.getData() != null && terminalBean.getData().is_vip()) {
                        mTvBindStatus.setText("已绑定");
                        mIvVipStatus.setImageResource(R.mipmap.zhongduan);
                    } else {
                        mTvBindStatus.setText("");
                        mIvVipStatus.setImageResource(R.mipmap.icon_hyqx_zhongduan);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == NO_REQUEST_RESULT) {
                if (null != data) {
                    updateTerminal(data.getStringExtra("terminalNo") + "");
                }
            } else if (requestCode == SETTING_REQUEST_RESULT) {
                getTerminalDetail();
            } else if (requestCode == BINDVIP_REQUEST_RESULT) {
                mTvBindStatus.setText("已绑定");
            }
        }
    }

    @OnClick({R.id.toolbar_left_title, R.id.terminal_shop_ll, R.id.terminal_noid_ll, R.id.terminal_loop_setting_lv, R.id.terminal_push_lv, R.id.terminal_curr_paly_dt_ll, R.id.terminal_order_lv, R.id.termianl_msg_manger_ll, R.id.terminal_setting_ll, R.id.terminal_rest_lv, R.id.terminal_flage_ll, R.id.terminal_about_ll, R.id.terminal_vip_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                TerminalDetailsActivity.this.finish();
                break;
            case R.id.terminal_shop_ll:
                startActivityForResult(new Intent(TerminalDetailsActivity.this, ShopDetailsActivity.class).putExtra("shopId", terminal.getData().getShop().getShop_id())
                        .putExtra("terminal_id", terminal.getData().getTerminal_id()).putExtra("terminalNo", terminal.getData().getTerminal_no()), SETTING_REQUEST_RESULT);
                break;
            case R.id.terminal_noid_ll:
                startActivityForResult(new Intent(TerminalDetailsActivity.this, SettingTerminalNoActivity.class).putExtra("shopId", terminal.getData().getShop_id()).putExtra("terminalNo", terminal.getData().getTerminal_no())
                        .putExtra("isMatchNo", 1), NO_REQUEST_RESULT);
                break;
            case R.id.terminal_loop_setting_lv:
                startActivity(new Intent(TerminalDetailsActivity.this, TerminalPlayerSettingActivity.class).putExtra("terminal_id", terminal.getData().getTerminal_id()));
                break;
            case R.id.terminal_push_lv:
                PushNormlBean pushNormlBean = new PushNormlBean();
                pushNormlBean.setShopName(terminal.getData().getShop().getShop_name());
                pushNormlBean.setTermianlNo(terminal.getData().getTerminal_no());
                pushNormlBean.setShopStar(terminal.getData().getShop().getShop_star_level());
                pushNormlBean.setTerminanlId(terminal.getData().getTerminal_id());
                pushNormlBean.setOrientation(terminal.getData().getTerm_orientation());
                pushNormlBean.setFree(true);
                startActivity(new Intent(TerminalDetailsActivity.this, MyWorksActivity.class).putExtra("pushNormlBean", pushNormlBean).putExtra("type", 1));
                break;
            case R.id.terminal_curr_paly_dt_ll:
                startActivity(new Intent(TerminalDetailsActivity.this, PlayListActivity.class).putExtra("terminal_id", terminal.getData().getTerminal_id()));
                break;
            case R.id.terminal_order_lv:
                startActivity(new Intent(TerminalDetailsActivity.this, OrderExecuteStatusListActivity.class).putExtra("terminal_id", terminal.getData().getTerminal_id()));

                break;
            case R.id.termianl_msg_manger_ll:
                startActivity(new Intent(TerminalDetailsActivity.this, lableWallActivity.class).putExtra("terminal_id", terminal.getData().getTerminal_id()));
                break;
            case R.id.terminal_setting_ll:
                startActivityForResult(new Intent(TerminalDetailsActivity.this, TerminalSettingActivity.class).putExtra("terminal", terminal.getData()), SETTING_REQUEST_RESULT);
                break;
            case R.id.terminal_rest_lv:
                startActivity(new Intent(TerminalDetailsActivity.this, AddTerminalActivity.class).putExtra("terminalId", terminal.getData().getTerminal_id()));
                break;
            case R.id.terminal_flage_ll:
                startActivity(new Intent(TerminalDetailsActivity.this, FlagInstructionActivity.class).putExtra("terminalId", terminal.getData().getTerminal_id()).putExtra("leve", terminal.getData().getShop().getShop_star_level()));
                break;
            case R.id.terminal_about_ll:
                startActivity(new Intent(TerminalDetailsActivity.this, WebViewActivity.class).putExtra("url", Utils.getSystemInfoBean(this).getData().getAboutTerminadocument()).putExtra("title", "终端管理规范"));
                break;
            case R.id.terminal_vip_ll:
                Intent intent = new Intent(TerminalDetailsActivity.this, BindVipActivity.class);
                intent.putExtra("terminal_id", terminaId);
                startActivityForResult(intent, BINDVIP_REQUEST_RESULT);
                break;
        }
    }

    public void updateTerminal(final String tNo) {


        RxRetrofitClient.getInstance(TerminalDetailsActivity.this).updateTerminal(
                terminal.getData().getTerminal_id(), terminal.getData().getShop().getShop_id(), tNo, VersionUtil.getVersionCode(TerminalDetailsActivity.this) + "", terminal.getData().getTerm_orientation() + "", new Observer<VCodeBenan>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.toast(TerminalDetailsActivity.this, "请检查网络是否正常");
                        try {
                            throw e;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(VCodeBenan vCodeBenan) {
                        Utils.toast(TerminalDetailsActivity.this, vCodeBenan.getMessage());
                        if (vCodeBenan.getCode() == 1) {
                            terminal.getData().setTerminal_no(tNo);
                            terminalNoidTv.setText(tNo);
                            EventBus.getDefault().post(new Event.RefreshTerminalListEvent());
                        }
                    }
                });
    }

}
