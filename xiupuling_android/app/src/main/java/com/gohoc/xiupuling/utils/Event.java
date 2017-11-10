package com.gohoc.xiupuling.utils;

import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.OrderDetailBean;
import com.gohoc.xiupuling.bean.PromotionInfoBean;
import com.gohoc.xiupuling.bean.UserBean;

import java.util.List;

/**
 * Created by 28713 on 2017/2/16.
 */

public class Event {
    public static class ShowRightMeunEvent {
        public boolean isShow = false;

        public ShowRightMeunEvent(boolean isShow) {
            this.isShow = isShow;
        }
    }

    public static class UserEvent {
        public UserBean userBean;

        public UserEvent() {
        }
    }

    public static class VcodeEvent {
        public boolean isPass = false;

        public VcodeEvent(boolean isPass) {
            this.isPass = isPass;
        }
    }

    public static class RefreshTerminalListEvent {
        public RefreshTerminalListEvent() {

        }
    }

    public static class RefreshGroupeListEvent {
        public RefreshGroupeListEvent() {

        }
    }

    //定点投放事件
    public static class SetLiandongDingtouEvent {
        public String mlink_snap_id,mlink_snap_name;
        public SetLiandongDingtouEvent(String link_snap_id,String link_snap_name) {
            this.mlink_snap_id=link_snap_id;
            this.mlink_snap_name=link_snap_name;
        }
    }

    //新建联动群组---选择终端
    public static class SetLiandongChooseTerminalEvent {
        public String terminal_no,terminal_id;
        public int position;
        public SetLiandongChooseTerminalEvent(String myterminal_id,String myterminal_no,int myposition) {
            this.terminal_id=myterminal_id;
            this.terminal_no=myterminal_no;
            this.position=myposition;
        }
    }

    //定点投放事件选择
    public static class SetLiandongDingtouChooseEvent {
        public SetLiandongDingtouChooseEvent() {

        }
    }

    //定点投放历史记录事件
    public static class SetLiandongDingtouHistoryEvent {
        public SetLiandongDingtouHistoryEvent() {

        }
    }

    //销毁我的店铺
    public static class FinishMyShopActivityEvent {
        public FinishMyShopActivityEvent() {

        }
    }


    //销毁联动包列表
    public static class FinishLiandongListEvent {
        public FinishLiandongListEvent() {

        }
    }

    //添加监管账户状态
    public static class RefreshAuditUserStatusEvent {
        public RefreshAuditUserStatusEvent() {

        }
    }

    //添加监管账户
    public static class RefreshAuditUserListEvent {
        public RefreshAuditUserListEvent() {

        }
    }

    //添加账户1
    public static class RefreshAuditUserEvent_1 {
        public RefreshAuditUserEvent_1() {

        }
    }

    //添加账户2
    public static class RefreshAuditUserEvent_2 {
        public RefreshAuditUserEvent_2() {

        }
    }

    //销毁联动包列表
    public static class FinishLiandongDetailEvent {
        public FinishLiandongDetailEvent() {

        }
    }

    public static class RefreshPushListEvent {
        public PromotionInfoBean promotionInfoBean;

        public RefreshPushListEvent(PromotionInfoBean promotionInfoBean) {

        }
    }


    public static class GroupEvent {
        public GroupDetailsBean groupDetailsBeans;

        public GroupEvent(GroupDetailsBean groupDetailsBeans) {
            this.groupDetailsBeans = groupDetailsBeans;
        }
    }

    public static class ReLoginUpdate {
        public ReLoginUpdate() {
        }
    }

    public static class WalletUpdate {
        public WalletUpdate() {
        }
    }

    public static class MainIndex {
        public int i = 0;

        public MainIndex(int i) {
            this.i = i;
        }
    }

    public static class WxPayEvent {
        public int i = 0;

        public WxPayEvent(int i) {
            this.i = i;
        }
    }

    public static class WorksRequestEvent {
        public WorksRequestEvent() {
        }
    }


    public static class OrderUpdateEvent {
        public OrderUpdateEvent() {
        }
    }

    public static class PrderRushEvent {
        public int m = 0;
        public List<OrderDetailBean.DataBean.DatalistBean> datalist;

        public PrderRushEvent(int m, List<OrderDetailBean.DataBean.DatalistBean> datalist) {
            this.m = m;
            this.datalist = datalist;
        }
    }
}
