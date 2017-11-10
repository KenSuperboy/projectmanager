package com.gohoc.xiupuling.ui.group;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.ui.BasicActivity;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.roundimage.CircleImageView;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

import rx.Observer;

import static com.gohoc.xiupuling.constant.Constant.NetConstant.BASE_USER_RESOURE;

public class GroupInviteActivity extends BasicActivity {

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
    @BindView(R.id.qcode)
    ImageView qcode;
    @BindView(R.id.invite_code_tv)
    TextView inviteCodeTv;
    @BindView(R.id.rest_tv)
    TextView restTv;
    @BindView(R.id.toolbar_right)
    ImageView toolbarRight;

    private GroupDetailsBean groupDetailsBeans;
    private BottomSheetDialog dialogs;
    private IWXAPI wxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_invite);
        ButterKnife.bind(this);
        wxApi = WXAPIFactory.createWXAPI(this, BaseConstant.WX_APP_ID);
        wxApi.registerApp(BaseConstant.WX_APP_ID);
        setStatusColor(R.color.colorPrimary);
        toolbarTitle.setText("邀请加入");
        initDialog();
        toolbarRight.setImageResource(R.mipmap.icon_share_yaoqingjiaru);
        try {
            groupDetailsBeans = (GroupDetailsBean) getIntent().getExtras().get("groupDetailsBeans");

            Glide.with(this)
                    .load(BASE_USER_RESOURE + groupDetailsBeans.getData().getUnion_portrait()+ Utils.getThumbnail(100,100))
                    // .placeholder(R.mipmap.icon_dengru_touxiang)
                    //.error(R.mipmap.icon_dengru_touxiang)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(shopPicIv);
            groupNameTv.setText(groupDetailsBeans.getData().getUnion_name() + "");
            groupTypeTv.setText(groupDetailsBeans.getData().getUnion_num() + "");
            groupFlageTv.setText(groupDetailsBeans.getData().getUnion_brief_info() + "");
            inviteCodeTv.setText(groupDetailsBeans.getData().getUnion_invitation_code() + "");

            getQCoade(groupDetailsBeans.getData().getUnion_id() + "," + groupDetailsBeans.getData().getUnion_invitation_code());


        } catch (Exception e) {
            Logger.e(e.toString());
        }

    }

    @OnClick({R.id.toolbar_left_title, R.id.rest_tv, R.id.toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_title:
                GroupInviteActivity.this.finish();
                break;
            case R.id.rest_tv:
                resetinvitationcode();
                break;
            case R.id.toolbar_right:
                if (dialogs.isShowing())
                    dialogs.dismiss();
                else
                    dialogs.show();
                break;

        }
    }


    private void getQCoade(String code) {

        String context = code + "";
        Bitmap img = QRCodeEncoder.syncEncodeQRCode(code, BGAQRCodeUtil.dp2px(GroupInviteActivity.this, 150));

        qcode.setImageBitmap(img);
    }

    private void resetinvitationcode() {
        RxRetrofitClient.getInstance(this).resetinvitationcode(groupDetailsBeans.getData().getUnion_id() + "", new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(GroupInviteActivity.this, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                Utils.toast(GroupInviteActivity.this, vCodeBenan.getMessage());
                if (vCodeBenan.getCode() == 1) {
                    inviteCodeTv.setText(vCodeBenan.getData() + "");
                    getQCoade(groupDetailsBeans.getData().getUnion_id() + "," + vCodeBenan.getData() + "");
                }
            }
        });
    }

    public void initDialog() {
        dialogs = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.item_share_pop, null);
        dialogView.findViewById(R.id.wechat_f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare(0,"秀铺令","","https://www.xiupuling.com/",NetConstant.BASE_USER_RESOURE+groupDetailsBeans.getData().getUnion_portrait());
            }
        });
        dialogView.findViewById(R.id.wechat_q).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare(1,"秀铺令","","https://www.xiupuling.com/",NetConstant.BASE_USER_RESOURE+groupDetailsBeans.getData().getUnion_portrait());
            }
        });
        dialogView.findViewById(R.id.qq_f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialogView.findViewById(R.id.qq_k).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialogView.findViewById(R.id.sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        dialogs.setContentView(dialogView);

    }


    //在需要分享的地方添加代码：
    //wechatShare(0);//分享到微信好友
    // wechatShare(1);//分享到微信朋友圈
    private void wechatShare(final int flag, final String title, final String content, final String url, String cover) {


        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = content;
      //  msg.setThumbImage((Bitmap) resource);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);

        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.icon_usercenter_morentouxiang)
                .error(R.mipmap.icon_usercenter_morentouxiang)
                .listener(new RequestListener() {

                    @Override
                    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //在这里添加一些图片加载完成的操作

                        return false;
                    }
                });
    }

}
