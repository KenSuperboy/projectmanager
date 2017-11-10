package com.gohoc.xiupuling.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gohoc.xiupuling.R;
import com.gohoc.xiupuling.bean.ShopAndTerminal0Bean;
import com.gohoc.xiupuling.bean.ShopAndTerminal1Bean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.callback.Callback;
import com.gohoc.xiupuling.callback.Callback1;
import com.gohoc.xiupuling.net.RxRetrofitClient;
import com.gohoc.xiupuling.utils.Event;
import com.gohoc.xiupuling.utils.Utils;
import com.gohoc.xiupuling.widget.RatingBar;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bingoogolapple.alertcontroller.BGAAlertAction;
import cn.bingoogolapple.alertcontroller.BGAAlertController;
import rx.Observer;

/**
 * Created by 28713 on 2017/4/19.
 */

public class ShopAndTerminalAdater extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private Activity activity;
    private OnSubClickLitener onSubClickLitener;
    private Callback1 mCallback1;
    private Callback mCallback;

    public ShopAndTerminalAdater(Activity activity, List<MultiItemEntity> data) {
        super(data);
        this.activity = activity;
        addItemType(1, R.layout.item_terminal);//mBehindViews  android:id="@+id/swipeMenuLayout"
        addItemType(2, R.layout.item_terminal_sub);
    }


    public void setCallback(Callback1 callback1)
    {
        this.mCallback1=callback1;
    }

    public void setCallback(Callback callback)
    {
        this.mCallback=callback;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final MultiItemEntity multiItemEntity) {
        switch (baseViewHolder.getItemViewType()) {
            case 1:
                final ShopAndTerminal0Bean shopAndTerminal0Bean = (ShopAndTerminal0Bean) multiItemEntity;
                baseViewHolder.setText(R.id.terminal_name_tv, shopAndTerminal0Bean.getShopDateBean().getShop_name())
                        .setText(R.id.terminal_price, String.valueOf(shopAndTerminal0Bean.getShopDateBean().getTotalorderamt()))
                        .setImageResource(R.id.item_terminal_arr, shopAndTerminal0Bean.isExpanded() ? R.mipmap.icon_explain_open : R.mipmap.icon_explain_close);

                RatingBar ratingBar = baseViewHolder.getView(R.id.ratingbar);
                ratingBar.setEnabled(false);
                ratingBar.setmClickable(false);
                ratingBar.setStar(shopAndTerminal0Bean.getShopDateBean().getShop_star_level());

                /*baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = baseViewHolder.getAdapterPosition();
                        if (shopAndTerminal0Bean.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });*/

                baseViewHolder.getView(R.id.linear_parent_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG","点击响应");
                        int pos = baseViewHolder.getAdapterPosition();
                        if (shopAndTerminal0Bean.isExpanded()) {
                            if(mCallback!=null){
                                mCallback.callBack();
                            }
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });

                //增加的信息
                final SwipeMenuLayout myswipeMenuLayout=baseViewHolder.getView(R.id.myswipeMenuLayout);
                LinearLayout mBehindViews=baseViewHolder.getView(R.id.mBehindViews);
                mBehindViews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myswipeMenuLayout.smoothClose();
                        Log.d("TAG","点击修改按钮");
                        //ShopDetailsBean.DataBean.GroupBean groupBean=new ShopDetailsBean.DataBean.GroupBean();
                        if(mCallback1!=null){
                            mCallback1.callBack(shopAndTerminal0Bean.getShopDateBean().getShop_id());
                        }
                    }
                });

                break;
            case 2:
                final SwipeMenuLayout swipeMenuLayout = baseViewHolder.getView(R.id.swipeMenuLayout);
                final ShopAndTerminal1Bean shopAndTerminal1Bean = (ShopAndTerminal1Bean) multiItemEntity;

                //空门店情况
                if (shopAndTerminal1Bean.getTermlistBean() == null) {
                    swipeMenuLayout.setVisibility(View.GONE);
                    baseViewHolder.setVisible(R.id.terminal_sub_closed_lv, false)
                            .setVisible(R.id.terminal_sub_del_lv, true);

                    baseViewHolder.getView(R.id.terminal_sub_del_lv).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BGAAlertController alertController = new BGAAlertController(activity, "", "", BGAAlertController.AlertControllerStyle.ActionSheet);
                            // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
                            alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
                                @Override
                                public void onClick() {

                                }
                            }));
                            alertController.addAction(new BGAAlertAction("删除门店" + shopAndTerminal1Bean.getShop().getShop_name(), BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
                                @Override
                                public void onClick() {
                                    deleteShop(shopAndTerminal1Bean, baseViewHolder);
                                }
                            }));
                            alertController.setCancelable(true);
                            alertController.show();


                           /* if (onSubClickLitener != null)
                                onSubClickLitener.onShopDel(shopAndTerminal1Bean, baseViewHolder.getAdapterPosition());*/
                        }
                    });
                    break;
                } else {
                    baseViewHolder.setVisible(R.id.terminal_sub_closed_lv, false)
                            .setVisible(R.id.terminal_sub_del_lv, false);
                }


                baseViewHolder.setText(R.id.terminal_number_id_tv, shopAndTerminal1Bean.getTermlistBean().getTerminal_no())
                        .setVisible(R.id.terminal_sub_closed_lv, shopAndTerminal1Bean.isLast())
                        .setText(R.id.terminal_price_tv, String.valueOf("￥" + shopAndTerminal1Bean.getTermlistBean().getTotalamt()))
                        .setImageResource(R.id.terminal_battery_iv, shopAndTerminal1Bean.getTermlistBean().getFurtherorder() == 1 ? R.mipmap.port_order_on : R.mipmap.port_order_off);
                SeekBar seekBar = baseViewHolder.getView(R.id.seekBar);
                seekBar.setEnabled(false);
                if (shopAndTerminal1Bean.getTermlistBean().getTerm_orientation() == 0) {//横屏
                    baseViewHolder.setBackgroundRes(R.id.terminal_number_id_tv, shopAndTerminal1Bean.getTermlistBean().getOnline() == 1 ? R.mipmap.port_orientation_h2_off : R.mipmap.port_orientation_h_off);
                } else {
                    baseViewHolder.setBackgroundRes(R.id.terminal_number_id_tv, shopAndTerminal1Bean.getTermlistBean().getOnline() == 1 ? R.mipmap.port_orientation_s2_off : R.mipmap.port_orientation_s_off);
                }
                //收起门店
                baseViewHolder.getView(R.id.terminal_sub_closed_lv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeMenuLayout.quickClose();
                        collapse(getParentPosition(shopAndTerminal1Bean));
                    }
                });

                //点击终端
                baseViewHolder.getView(R.id.content_ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeMenuLayout.quickClose();
                        if (onSubClickLitener != null)
                            onSubClickLitener.onItemClick(shopAndTerminal1Bean);
                    }
                });
                //点击删除
                baseViewHolder.getView(R.id.mBehindViews).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeMenuLayout.quickClose();
                        BGAAlertController alertController = new BGAAlertController(activity, "", "一旦删除，数据不可恢复，目前该终端订单收益全部归零。", BGAAlertController.AlertControllerStyle.ActionSheet);
                        // 不管添加顺序怎样，AlertActionStyle.Cancel始终是在最底部的,AlertActionStyle.Default和AlertActionStyle.Destructive按添加的先后顺序显示
                        alertController.addAction(new BGAAlertAction("取消", BGAAlertAction.AlertActionStyle.Cancel, new BGAAlertAction.Delegate() {
                            @Override
                            public void onClick() {

                            }
                        }));
                        alertController.addAction(new BGAAlertAction("删除 " + shopAndTerminal1Bean.getTermlistBean().getTerminal_no() + "号机", BGAAlertAction.AlertActionStyle.Destructive, new BGAAlertAction.Delegate() {
                            @Override
                            public void onClick() {
                                deleteTerminal(shopAndTerminal1Bean, baseViewHolder);
                            }
                        }));
                        alertController.setCancelable(true);
                        alertController.show();

                    }
                });


                break;
        }

    }


    private void deleteShop(final ShopAndTerminal1Bean shopAndTerminal1Bean, final BaseViewHolder baseViewHolder) {
        RxRetrofitClient.getInstance(activity).deleteShop(shopAndTerminal1Bean.getShop().getShop_id(), new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Utils.toast(activity, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    //
   /*                  int cp = getParentPosition(shopAndTerminal1Bean);
                    ((ShopAndTerminal0Bean) getData().get(cp)).removeSubItem(shopAndTerminal1Bean);
                    getData().remove(baseViewHolder.getLayoutPosition());
                    getData().remove(cp);
                    notifyDataSetChanged();*/
                    int cp = getParentPosition(shopAndTerminal1Bean);
                    ShopAndTerminal0Bean s0b= (ShopAndTerminal0Bean) getData().get(cp);
                    getData().removeAll(s0b.getSubItems());
                    getData().remove(cp);
                    notifyDataSetChanged();

                    EventBus.getDefault().post(new Event.RefreshTerminalListEvent());
                } else {
                    Utils.toast(activity, vCodeBenan.getMessage());
                }

            }
        });
    }

    private void deleteTerminal(final ShopAndTerminal1Bean shopAndTerminal1Bean, final BaseViewHolder baseViewHolder) {
        RxRetrofitClient.getInstance(activity).deleteTerminal(String.valueOf(shopAndTerminal1Bean.getTermlistBean().getTerminal_id()), new Observer<VCodeBenan>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                Utils.toast(activity, "请检查网络是否正常");
                try {
                    throw e;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onNext(VCodeBenan vCodeBenan) {
                if (vCodeBenan.getCode() == 1) {
                    int cp = getParentPosition(shopAndTerminal1Bean);
                    ShopAndTerminal0Bean shopAndTerminal0Bean= (ShopAndTerminal0Bean) getData().get(cp);
                    shopAndTerminal0Bean.removeSubItem(shopAndTerminal1Bean);
                    getData().remove(baseViewHolder.getLayoutPosition());
                    if (shopAndTerminal0Bean.getSubItems().size() < 1)
                        shopAndTerminal0Bean.addSubItem(new ShopAndTerminal1Bean(shopAndTerminal0Bean.getShopDateBean()));
                    collapse(cp);
                    expand(cp);
                    notifyDataSetChanged();
                }
            }
        });
    }
    public OnSubClickLitener getOnSubClickLitener() {
        return onSubClickLitener;
    }
    public void setOnSubClickLitener(OnSubClickLitener onSubClickLitener) {
        this.onSubClickLitener = onSubClickLitener;
    }
    public interface OnSubClickLitener {
        void onItemClick(ShopAndTerminal1Bean shopAndTerminal1Bean);

        void onItemDel(ShopAndTerminal1Bean shopAndTerminal1Bean, int postion);

        void onShopDel(ShopAndTerminal1Bean shopAndTerminal1Bean, int postion);
    }

}
