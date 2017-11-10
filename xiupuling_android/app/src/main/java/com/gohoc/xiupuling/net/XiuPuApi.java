package com.gohoc.xiupuling.net;

import com.gohoc.xiupuling.adapter.WorksDetails;
import com.gohoc.xiupuling.bean.AddShopResultBean;
import com.gohoc.xiupuling.bean.BankBean;
import com.gohoc.xiupuling.bean.BannerBean;
import com.gohoc.xiupuling.bean.BusinessBean;
import com.gohoc.xiupuling.bean.BusinessTypeBean;
import com.gohoc.xiupuling.bean.BuyVipGoodsBean;
import com.gohoc.xiupuling.bean.CombinationAddTerminalBean;
import com.gohoc.xiupuling.bean.CompanyVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.CopyrrightBean;
import com.gohoc.xiupuling.bean.CouponBean;
import com.gohoc.xiupuling.bean.CreatRqResultBean;
import com.gohoc.xiupuling.bean.DesignerBean;
import com.gohoc.xiupuling.bean.EmptyBean;
import com.gohoc.xiupuling.bean.GoodsBean;
import com.gohoc.xiupuling.bean.GroupBean;
import com.gohoc.xiupuling.bean.GroupDetailsBean;
import com.gohoc.xiupuling.bean.GroupSearchBean;
import com.gohoc.xiupuling.bean.GroupTermianlListBean;
import com.gohoc.xiupuling.bean.GroupUserBean;
import com.gohoc.xiupuling.bean.LiandongbaoBean;
import com.gohoc.xiupuling.bean.LinkPackageDetailBean;
import com.gohoc.xiupuling.bean.LocationBean;
import com.gohoc.xiupuling.bean.MaterialBean;
import com.gohoc.xiupuling.bean.ModuleBean;
import com.gohoc.xiupuling.bean.MoudlesBean;
import com.gohoc.xiupuling.bean.MsgBean;
import com.gohoc.xiupuling.bean.MusicListBean;
import com.gohoc.xiupuling.bean.MyBankBean;
import com.gohoc.xiupuling.bean.MySecurityQsBean;
import com.gohoc.xiupuling.bean.MyWorksBean;
import com.gohoc.xiupuling.bean.MyshopListBean;
import com.gohoc.xiupuling.bean.OpenModuleBean;
import com.gohoc.xiupuling.bean.OrderBean;
import com.gohoc.xiupuling.bean.OrderDetailBean;
import com.gohoc.xiupuling.bean.OrderHistoryBean;
import com.gohoc.xiupuling.bean.OrderMoneyListBean;
import com.gohoc.xiupuling.bean.OrderSBean;
import com.gohoc.xiupuling.bean.PersonalVerifyapplyInfoBean;
import com.gohoc.xiupuling.bean.PlayLogBean;
import com.gohoc.xiupuling.bean.PlayerList;
import com.gohoc.xiupuling.bean.PushHistory;
import com.gohoc.xiupuling.bean.PushHistoryDtBean;
import com.gohoc.xiupuling.bean.PushLocationShowBean;
import com.gohoc.xiupuling.bean.PushLocationShowMapBean;
import com.gohoc.xiupuling.bean.PushOrderConfimResultBean;
import com.gohoc.xiupuling.bean.PushReqResultBean;
import com.gohoc.xiupuling.bean.PushReqShowBean;
import com.gohoc.xiupuling.bean.PushResultBean;
import com.gohoc.xiupuling.bean.RegionLocationBean;
import com.gohoc.xiupuling.bean.ReqBean;
import com.gohoc.xiupuling.bean.ReqListBean;
import com.gohoc.xiupuling.bean.SecurityQsBean;
import com.gohoc.xiupuling.bean.ShopBean;
import com.gohoc.xiupuling.bean.ShopDetailsBean;
import com.gohoc.xiupuling.bean.ShopLimtBean;
import com.gohoc.xiupuling.bean.StarLeveMoney;
import com.gohoc.xiupuling.bean.TerminalBean;
import com.gohoc.xiupuling.bean.TerminalFlagBean;
import com.gohoc.xiupuling.bean.TerminalListBean;
import com.gohoc.xiupuling.bean.TerminalPictureBean;
import com.gohoc.xiupuling.bean.TokenBean;
import com.gohoc.xiupuling.bean.UserBaseBean;
import com.gohoc.xiupuling.bean.UserBean;
import com.gohoc.xiupuling.bean.VCodeBenan;
import com.gohoc.xiupuling.bean.VipListBean;
import com.gohoc.xiupuling.bean.WalletBean;
import com.gohoc.xiupuling.bean.WalletDetailsBean;
import com.gohoc.xiupuling.bean.WalletListBean;
import com.gohoc.xiupuling.bean.WxPayBean;
import com.gohoc.xiupuling.bean.audit.AuditListBean;
import com.gohoc.xiupuling.bean.audit.AuditTerminalListBean;
import com.gohoc.xiupuling.bean.audit.AuditUserInfor;
import com.gohoc.xiupuling.bean.audit.AuditWorkListBean;
import com.gohoc.xiupuling.bean.combinationbean.CombinationAddBean;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListBean;
import com.gohoc.xiupuling.bean.combinationbean.CombinationListDetailBean;
import com.gohoc.xiupuling.bean.linkpackage.AddlinkPackageBean;
import com.gohoc.xiupuling.bean.linkpackage.LinkPackageHistoryListBean;
import com.gohoc.xiupuling.bean.linkpackage.SetLiandongbaoGuanxi;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sherlock-sky on 2017/1/27.
 */

public interface XiuPuApi {

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/account/login")
    Observable<UserBean> login(@Field("gid") String gid, @Field("uid") String uid,
                               @Field("username") String username, @Field("password") String password,
                               @Field("platform") String platform, @Field("mphone_version") String mphone_version);


    @GET("Pigeon4Server/pigeon/account/getauthcode")
    Observable<VCodeBenan> getauthcode(@Query("mobile") String mobile);

    @POST("Pigeon4Server/pigeon/account/verifysmscode")
    Observable<VCodeBenan> verifysmscode(@Query("mobile") String mobile, @Query("smscode") String smscode);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/verifysmscode")
    Observable<VCodeBenan> verifysmscode2(@Field("mobile") String mobile, @Field("smscode") String smscode);


    @POST("Pigeon4Server/pigeon/account/getMsgCode")
    Observable<VCodeBenan> getMsgCode(@Query("mobile") String mobile, @Query("authcode") String smscode, @Query("authtype") String authtype);

    @POST("Pigeon4Server/pigeon/account/getauthcode")
    Observable<VCodeBenan> getMsgCode2(@Query("mobile") String mobile, @Query("authcode") String smscode);

    /*绑定监管账户*/
    @POST("Pigeon4Server/pigeon/personal/bindaudituser")
    Observable<VCodeBenan> bindAuditUser(@Query("mobile") String mobile);

    /*解绑监管账户*/
    @POST("Pigeon4Server/pigeon/personal/unbindaudituser")
    Observable<VCodeBenan> unbindAuditUser();

    /*按终端审核*/
    @POST("Pigeon4Server/pigeon/personal/audituserterminalorder")
    Observable<VCodeBenan> shenheTerminalAuditUser(@Query("user_id") String user_id,@Query("terminal_id") String terminal_id,@Query("range_id") String range_id,@Query("dicision") String dicision);

    /*按作品审核*/
    @POST("Pigeon4Server/pigeon/personal/audituserwork")
    Observable<VCodeBenan> shenheWorkAuditUser(@Query("user_id") String user_id,@Query("work_id") String work_id,@Query("dicision") String dicision);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/addmyvipcode")
    Observable<VCodeBenan> addvipcode(@Field("vip_code") String vip_code);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/terminal/terminalbindvipcode")
    Observable<VCodeBenan> bindvipcode(@Field("vip_code") String vip_code,@Field("terminal_id") String terminal_id);

    @POST("Pigeon4Server/pigeon/personal/getphonemsgcode")
    Observable<VCodeBenan> getphonemsgcode(@Query("mobile") String mobile, @Query("is_need_check") String is_need_check);


    @POST("Pigeon4Server/pigeon/account/resetpwd")
    Observable<VCodeBenan> resetpwd(@Query("mobile") String mobile, @Query("password") String password, @Query("duppassword") String duppassword,
                                    @Query("platform") String platform, @Query("phone_version") String phone_version);

    @GET("Pigeon4Server/pigeon/account/regist")
    Observable<UserBean> regist(@Query("uid") String uid,
                                @Query("mobile") String mobile, @Query("password") String password, @Query("duppassword") String duppassword,
                                @Query("platform") String platform, @Query("phone_version") String phone_version, @Query("shared_platform") String shared_platform
            , @Query("promo_id") String promo_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/updatepwd")
    Observable<VCodeBenan> updatepwd(@Field("oldpwd") String oldpwd, @Field("newpwd") String newpwd, @Field("duppwd") String duppwd);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/updatePortrait")
    Observable<VCodeBenan> updatePortrait(@Field("portraitURL") String portraitURL, @Field("nickname") String nickname, @Field("sex") String sex, @Field("age") String age);


    @GET("Pigeon4Server/pigeon/jz/shop/list")
    Observable<ShopBean> terminalList(@Query("orderby") String orderby);

    @GET("Pigeon4Server/pigeon/dr/preferential/citylist")
    Observable<LocationBean> citylist(@Query("parentId") String parentId, @Query("level") String level);

    @GET("Pigeon4Server/pigeon/jz/shop/getgroup")
    Observable<BusinessBean> getgroup(@Query("region_id") String parentId, @Query("type") String level);

    @GET("Pigeon4Server/pigeon/jz/shop/businessCategories")
    Observable<BusinessTypeBean> businessCategories(@Query("business_level") String business_level, @Query("parent_id") String parent_id);

    @GET("Pigeon4Server/pigeon/account/getUploadToken")
    Observable<VCodeBenan> getUploadToken();

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/terminal/accesstokencheck")
    Observable<VCodeBenan> accesstokencheck(@Field("access_token") String access_token, @Field("client_id") String client_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/updateCellphone")
    Observable<VCodeBenan> updateCellphone(@Field("mobile") String mobile, @Field("smscode") String smscode);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/personalVerifyapply")
    Observable<VCodeBenan> personalVerifyapply(@Field("idno") String idno, @Field("name") String name, @Field("idphoto_a") String idphoto_a
            , @Field("idphoto_b") String idphoto_b);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/companyVerifyapply")
    Observable<VCodeBenan> companyVerifyapply(@Field("business_code") String business_code, @Field("code_photo_url") String code_photo_url, @Field("name") String name
            , @Field("org_id") String org_id, @Field("org_id_photo_url") String org_id_photo_url, @Field("tax_no") String tax_no, @Field("tax_photo_url") String tax_photo_url);

    @GET("Pigeon4Server/pigeon/personal/companyVerifyapplyInfo")
    Observable<CompanyVerifyapplyInfoBean> companyVerifyapplyInfo();

    @GET("Pigeon4Server/pigeon/personal/personalVerifyapplyInfo")
    Observable<PersonalVerifyapplyInfoBean> personalVerifyapplyInfo();

    @GET("Pigeon4Server/pigeon/personal/securityqalist")
    Observable<SecurityQsBean> securityqalist(@Query("type") String type);

    @GET("Pigeon4Server/pigeon/personal/getmysecurityqa")
    Observable<MySecurityQsBean> getmysecurityqa();


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/setsecurityqa")
    Observable<VCodeBenan> setsecurityqa(@Field("question_a") String question_a, @Field("question_b") String question_b
            , @Field("question_c") String question_c, @Field("answer_a") String answer_a
            , @Field("answer_b") String answer_b, @Field("answer_c") String answer_c
    );

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/updatesecurityqa")
    Observable<VCodeBenan> updatesecurityqa(@Field("question_a") String question_a, @Field("question_b") String question_b
            , @Field("question_c") String question_c, @Field("answer_a") String answer_a
            , @Field("answer_b") String answer_b, @Field("answer_c") String answer_c
    );

    @GET("Pigeon4Server/pigeon/personal/userbasicinfo")
    Observable<UserBaseBean> userbasicinfo();

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/dr/preferential/region")
    Observable<RegionLocationBean> region(@Field("region_name") String region_name, @Field("level") String level, @Field("city_name") String city_name);

    //获取系统信息
    @GET("Pigeon4Server/pigeon/personal/systemdictionarylist")
    Observable<ResponseBody> systemdictionarylist();

    @GET("Pigeon4Server/pigeon/jz/terminal/detail")
    Observable<TerminalBean> getTerminalDetail(@Query("terminal_id") String terminal_id);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/shop/save")
    Observable<AddShopResultBean> saveShop(@Field("jzShop.shop_name") String shop_name,
                                           @Field("jzShop.shop_address") String shop_address,
                                           @Field("jzShop.shop_region_province") String shop_region_province,
                                           @Field("jzShop.shop_region_city") String shop_region_city,
                                           @Field("jzShop.shop_region_id") String shop_region_id,
                                           @Field("jzShop.shop_group_id") String shop_group_id,
                                           @Field("jzShop.shop_addr_longitude") String shop_addr_longitude,
                                           @Field("jzShop.shop_addr_latitude") String shop_addr_latitude,
                                           @Field("jzShop.shop_telephone") String shop_telephone,
                                           @Field("jzShop.shop_business_id") String shop_business_id,
                                           @Field("shop_photos") String shop_photos
    );

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/shop/update")
    Observable<AddShopResultBean> updateShop(@Field("jzShop.shop_id") String shop_id, @Field("jzShop.shop_name") String shop_name,
                                             @Field("jzShop.shop_address") String shop_address,
                                             @Field("jzShop.shop_region_province") String shop_region_province,
                                             @Field("jzShop.shop_region_city") String shop_region_city,
                                             @Field("jzShop.shop_region_id") String shop_region_id,
                                             @Field("jzShop.shop_group_id") String shop_group_id,
                                             @Field("jzShop.shop_addr_longitude") String shop_addr_longitude,
                                             @Field("jzShop.shop_addr_latitude") String shop_addr_latitude,
                                             @Field("jzShop.shop_telephone") String shop_telephone,
                                             @Field("jzShop.shop_business_id") String shop_business_id,
                                             @Field("shop_photos") String shop_photos
    );


    @GET("Pigeon4Server/pigeon/jz/shop/shopPhotos")
    Observable<BannerBean> getShopPhotos(@Query("shop_id") String shop_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/terminal/save")
    Observable<VCodeBenan> saveTerminal(@Field("access_token") String access_token,
                                        @Field("client_id") String client_id,
                                        @Field("jzTerminal.shop_id") String shop_id,
                                        @Field("jzTerminal.terminal_no") String terminal_no,
                                        @Field("jzTerminal.term_soft_version") String term_soft_version,
                                        @Field("jzTerminal.term_orientation") String term_orientation
    );

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/terminal/update")
    Observable<VCodeBenan> updateTerminal(
            @Field("jzTerminal.terminal_id") String terminal_id,
            @Field("jzTerminal.shop_id") String shop_id,
            @Field("jzTerminal.terminal_no") String terminal_no,
            @Field("jzTerminal.term_soft_version") String term_soft_version,
            @Field("jzTerminal.term_orientation") String term_orientation
    );


    @GET("Pigeon4Server/pigeon/jz/terminal/getTopModuleList")
    Observable<MoudlesBean> getTopModuleList();

    @GET("Pigeon4Server/pigeon/jz/shop/detail")
    Observable<ShopDetailsBean> getShopDetail(@Query("shop_id") String shop_id);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/terminal/setterminalruntimestatus")
    Observable<VCodeBenan> setterminalruntimestatus(
            @Field("jzTerminal.term_orientation") String term_orientation,
            @Field("jzTerminal.terminal_id") String terminal_id,
            @Field("jzTerminal.force_xpl") String force_xpl,
            @Field("jzTerminal.schedule_up_down") String schedule_up_down,
            @Field("jzTerminal.up_start_time") String up_start_time,
            @Field("jzTerminal.up_end_time") String up_end_time,
            @Field("jzTerminal.take_order") String take_order,
            @Field("jzTerminal.repeat_weekday") String repeat_weekday,
            @Field("jzTerminal.bg_audio_flag") String bg_audio_flag,
            @Field("jzTerminal.bg_audio_type") String bg_audio_type,
            @Field("jzTerminal.roll_title_flag") String roll_title_flag,
            @Field("jzTerminal.roll_titles") String roll_titles,
            @Field("jzTerminal.weather_flag") String weather_flag,
            @Field("jzTerminal.scancode_flag") String scancode_flag
    );

    @GET("Pigeon4Server/pigeon/jz/terminal/getShopTermList")
    Observable<TerminalListBean> getShopTermList(@Query("shop_id") String shop_id, @Query("orientation") String orientation);

    @GET("Pigeon4Server/pigeon/jz/terminal/getShopTermList")
    Observable<CombinationAddTerminalBean> getCombinationShopTermList(@Query("shop_id") String shop_id, @Query("orientation") String orientation, @Query("is_link") String is_link);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/terminal/recovery")
    Observable<VCodeBenan> recovery(@Field("access_token") String access_token, @Field("client_id") String client_id
            , @Field("terminal_id") String terminal_id);


    @GET("Pigeon4Server/pigeon/jz/union/getMyUnionList")
    Observable<GroupBean> getMyUnionList(@Query("filterby") String filterby, @Query("orderby") String orderby);

    @GET("Pigeon4Server/pigeon/jz/union/unionDetailInfo")
    Observable<GroupDetailsBean> unionDetailInfo(@Query("union_id") String union_id);

    @GET("Pigeon4Server/pigeon/jz/union/getUnionTerminalListofMine")
    Observable<GroupTermianlListBean> getUnionTerminalListofMine(@Query("union_id") String union_id);

    @GET("Pigeon4Server/pigeon/jz/union/getUnionTerminalListofTheirs")
    Observable<GroupTermianlListBean> getUnionTerminalListofTheirs(@Query("union_id") String union_id);

    @GET("Pigeon4Server/pigeon/jz/union/getUnUnionTermList")
    Observable<GroupTermianlListBean> getUnUnionTermList(@Query("union_id") String union_id);

    @GET("Pigeon4Server/pigeon/jz/union/getUnionTerminalList")
    Observable<GroupTermianlListBean> getUnionTerminalList(@Query("union_id") String union_id);


    @GET("Pigeon4Server/pigeon/jz/terminal/getPersonalModuleList")
    Observable<ModuleBean> getPersonalModuleList();


    @GET("Pigeon4Server/pigeon/jz/terminal/getCurrentPlayList")
    Observable<PlayerList> getCurrentPlayList(@Query("terminal_id") String terminal_id);

    @GET("Pigeon4Server/pigeon/jz/terminal/getSelectedModuleList")
    Observable<OpenModuleBean> getSelectedModuleList(@Query("terminal_id") String terminal_id);


    @GET("Pigeon4Server/pigeon/jz/terminal/terminalTagWallList")
    Observable<ModuleBean> terminalTagWallList(@Query("terminal_id") String terminal_id,
                                               @Query("pageNumber") String pageNumber,
                                               @Query("pageSize") String pageSize);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/terminal/updateSelectedModules")
    Observable<VCodeBenan> updateSelectedModules(@Field("terminal_id") String terminal_id, @Field("selected_module_ids") String selected_module_ids);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/update")
    Observable<VCodeBenan> updateUnion(@Field("jzUnion.union_id") String union_id, @Field("jzUnion.union_name") String union_name
            , @Field("jzUnion.union_type") String union_type, @Field("jzUnion.union_brief_info") String union_brief_info,
                                       @Field("jzUnion.union_portrait") String union_portrait
    );

    @GET("Pigeon4Server/pigeon/jz/union/getMyUnionUserList")
    Observable<GroupUserBean> getMyUnionUserList(@Query("union_id") String union_id);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/resetinvitationcode")
    Observable<VCodeBenan> resetinvitationcode(@Field("union_id") String union_id);

    @GET("Pigeon4Server/pigeon/jz/union/getCanJoinUnionListByCond")
    Observable<GroupSearchBean> getCanJoinUnionListByCond(@Query("q") String q);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/doJoinUnion")
    Observable<VCodeBenan> doJoinUnion(@Field("union_id") String union_id, @Field("invitecode") String invitecode);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/save")
    Observable<GroupDetailsBean> saveUnion(@Field("jzUnion.union_name") String union_name
            , @Field("jzUnion.union_type") String union_type
            , @Field("jzUnion.union_brief_info") String union_brief_info
            , @Field("jzUnion.union_portrait") String union_portrait
    );


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/addMultiTermsToUnion")
    Observable<VCodeBenan> addMultiTermsToUnion(@Field("union_id") String union_id, @Field("terminal_ids") String terminal_ids
    );

    //联动群组加入终端设备
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/addMultiTermsToUnion")
    Observable<EmptyBean> addCombinationMultiTermsToUnion(@Field("union_id") String union_id, @Field("terminal_ids") String terminal_ids);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/shiftUnionOwn")
    Observable<VCodeBenan> shiftUnionOwn(@Field("union_id") String union_id, @Field("to_user_id") String to_user_id
            , @Field("quitflag") String quitflag
    );

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/deleteTermFromUnion")
    Observable<VCodeBenan> deleteTermFromUnion(@Field("ids") String ids);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/autoReceivingOrderSwitch")
    Observable<VCodeBenan> autoReceivingOrderSwitch(@Field("ids") String ids, @Field("switcher") String switcher);

    @GET("Pigeon4Server/pigeon/zz/requirement/showrequirelist")
    Observable<ReqBean> showrequirelist(@Query("orderby") String orderby, @Query("pageNumber") String pageNumber,
                                        @Query("pageSize") String pageSize, @Query("pagingquery") String pagingquery);

    @GET("Pigeon4Server/pigeon/zz/requirement/selectworklist")
    Observable<ReqListBean> selectworklist(@Query("orderby") String orderby, @Query("pageNumber") String pageNumber,
                                           @Query("pageSize") String pageSize, @Query("pagingquery") String pagingquery);

    @GET("Pigeon4Server/pigeon/zz/requirement/getworkdesignerinfo")
    Observable<DesignerBean> getworkdesignerinfo(@Query("rq_id") String rq_id, @Query("work_id") String work_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/terminal/delete")
    Observable<VCodeBenan> deleteTerminal(@Field("terminal_id") String terminal_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/save")
    Observable<CreatRqResultBean> requirementSave(@Field("zzRequirement.rq_type") String rq_type,
                                                  @Field("zzRequirement.activity_title") String activity_title,
                                                  @Field("zzRequirement.activity_onoff") String activity_onoff,
                                                  @Field("zzRequirement.activity_brand") String activity_brand,
                                                  @Field("zzRequirement.activity_content") String activity_content,
                                                  @Field("zzRequirement.activity_nav_type") String activity_nav_type,
                                                  @Field("zzRequirement.activity_nav_content") String activity_nav_content,
                                                  @Field("zzRequirement.activity_nav_longitude") String activity_nav_longitude,
                                                  @Field("zzRequirement.activity_nav_latitude") String activity_nav_latitude,
                                                  @Field("zzRequirement.activity_detail") String activity_detail,
                                                  @Field("zzRequirement.rq_orientation") String rq_orientation,
                                                  @Field("zzRequirement.rq_design_spec") String rq_design_spec,
                                                  @Field("material_urls") String material_urls,
                                                  @Field("material_types") String material_types,
                                                  @Field("remark_a") String remark_a,
                                                  @Field("remark_b") String remark_b,
                                                  @Field("remark_c") String remark_c,
                                                  @Field("remark_d") String remark_d,
                                                  @Field("remark_e") String remark_e,
                                                  @Field("remark_f") String remark_f
    );

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/update")
    Observable<CreatRqResultBean> requirementUpdate(@Field("zzRequirement.rq_id") String rq_id, @Field("zzRequirement.rq_type") String rq_type,
                                                    @Field("zzRequirement.activity_title") String activity_title,
                                                    @Field("zzRequirement.activity_onoff") String activity_onoff,
                                                    @Field("zzRequirement.activity_brand") String activity_brand,
                                                    @Field("zzRequirement.activity_content") String activity_content,
                                                    @Field("zzRequirement.activity_nav_type") String activity_nav_type,
                                                    @Field("zzRequirement.activity_nav_content") String activity_nav_content,
                                                    @Field("zzRequirement.activity_nav_longitude") String activity_nav_longitude,
                                                    @Field("zzRequirement.activity_nav_latitude") String activity_nav_latitude,
                                                    @Field("zzRequirement.activity_detail") String activity_detail,
                                                    @Field("zzRequirement.rq_orientation") String rq_orientation,
                                                    @Field("zzRequirement.rq_design_spec") String rq_design_spec,
                                                    @Field("material_urls") String material_urls,
                                                    @Field("material_types") String material_types,
                                                    @Field("remark_a") String remark_a,
                                                    @Field("remark_b") String remark_b,
                                                    @Field("remark_c") String remark_c,
                                                    @Field("remark_d") String remark_d,
                                                    @Field("remark_e") String remark_e,
                                                    @Field("remark_f") String remark_f
    );

    //修改作品标题
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/updateworkname")
    Observable<VCodeBenan> updateWorkname(@Field("work_id") String work_id,@Field("work_name") String work_name);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/closeRequirement")
    Observable<VCodeBenan> closeRequirement(@Field("rq_id") String rq_id);



    @GET("Pigeon4Server/pigeon/zz/requirement/workdetail")
    Observable<WorksDetails> workdetail(@Query("work_id") String work_id);

    @GET("Pigeon4Server/pigeon/zz/requirement/distributeLocationList")
    Observable<PushLocationShowBean> distributeLocationList(@Query("rq_id") String rq_id, @Query("work_id") String work_id, @Query("pageNumber") String pageNumber
            , @Query("pageSize") String pageSize);

    @GET("Pigeon4Server/pigeon/zz/requirement/workDistributeDetail")
    Observable<PushReqResultBean> workDistributeDetail(@Query("rq_id") String rq_id, @Query("work_id") String work_id);

    @GET("Pigeon4Server/pigeon/zz/requirement/workShowDetail")
    Observable<PushReqShowBean> workShowDetail(@Query("rq_id") String rq_id, @Query("work_id") String work_id);

    @GET("Pigeon4Server/pigeon/jz/order/list")
    Observable<OrderBean> orderlist(@Query("ordertype") String ordertype);

    @GET("Pigeon4Server/pigeon/personal/getVipGoodsList")
    Observable<GoodsBean> getVipGoodsList();


    @GET("Pigeon4Server/pigeon/personal/getpointtradedtl")
    Observable<TokenBean> getpointtradedtl(@Query("pageNumber") String pageNumber, @Query("pageSize") String pageSize);


    @POST("Pigeon4Server/pigeon/personal/dailycheckin")
    Observable<VCodeBenan> dailycheckin();

    @GET("Pigeon4Server/pigeon/personal/getmypoint")
    Observable<VCodeBenan> getmypoin();

    @GET("Pigeon4Server/pigeon/jz/order/historyOrderList")
    Observable<OrderHistoryBean> historyOrderList(@Query("pageNumber") String pageNumber,
                                                  @Query("pageSize") String pageSize);

    //当前接单
    @GET("Pigeon4Server/pigeon/jz/order/currentOrderList")
    Observable<OrderHistoryBean> currentOrderList(@Query("pageNumber") String pageNumber,
                                                  @Query("pageSize") String pageSize);

    @GET("Pigeon4Server/pigeon/personal/copyrightdtl")
    Observable<CopyrrightBean> copyrightdtl(@Query("timeperiod") String timeperiod);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/buyVipGoods")
    Observable<BuyVipGoodsBean> buyVipGoods(@Field("goods_id") String goods_id);

    @GET("Pigeon4Server/pigeon/coupon/mynewpwdcouponlist")
    Observable<CouponBean> mynewpwdcouponlist();


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zfb/initorder")
    Observable<VCodeBenan> initorder(@Field("refno") String refno);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/weixin/unifiedorder")
    Observable<WxPayBean> unifiedorder(@Field("refno") String refno);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/kq/initorder")
    Observable<VCodeBenan> kqinitorder(@Field("refno") String refno);

    @GET("Pigeon4Server/pigeon/personal/pocketmoney")
    Observable<WalletBean> pocketmoney();

    @GET("Pigeon4Server/pigeon/personal/banklist")
    Observable<BankBean> banklist();

    @GET("Pigeon4Server/pigeon/personal/getmyinoutlist")
    Observable<WalletListBean> getmyinoutlist(@Query("pageNumber") String pageNumber, @Query("pageSize") String pageSize);

    @GET("Pigeon4Server/pigeon/personal/mybankcardlist")
    Observable<MyBankBean> mybankcardlist();

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/shop/delete")
    Observable<VCodeBenan> deleteShop(@Field("shop_id") String shop_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/switchsharedplatform")
    Observable<VCodeBenan> switchsharedplatform(@Field("shared_platform") String shared_platform);

    @GET("Pigeon4Server/pigeon/jz/terminal/terminalFlagStatus")
    Observable<TerminalFlagBean> terminalFlagStatus(@Query("terminal_id") String terminal_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/checksecurityqa")
    Observable<VCodeBenan> checksecurityqa(@Field("question_a") String question_a, @Field("question_b") String question_b, @Field("question_c") String question_c, @Field("answer_a") String answer_a
            , @Field("answer_b") String answer_b
            , @Field("answer_c") String answer_c);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/coupon/bindnewpwdcoupon")
    Observable<VCodeBenan> bindnewpwdcoupon(@Field("add_pwd") String add_pwd);

    //扫描卡券邀请码 获取token
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/coupon/queryboxcoupon")
    Observable<VCodeBenan> querycoupon(@Field("invite_code") String invite_code);

    //邀请码  和token   绑定
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/coupon/bindboxcoupon")
    Observable<VCodeBenan> querybindcoupon(@Field("invite_code") String invite_code,@Field("token") String token);


    @GET("Pigeon4Server/pigeon/jz/order/orderExecuteStatusList")
    Observable<OrderSBean> orderExecuteStatusList(@Query("terminal_id") String terminal_id);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/rangeMarket")
    Observable<PushResultBean> rangeMarket(
            @Field("rq_id") String rq_id
            , @Field("work_id") String work_id
            , @Field("zzInfoRange.range_type") String range_type
            , @Field("zzInfoRange.startdate") String startdate
            , @Field("zzInfoRange.enddate") String enddate
            , @Field("zzInfoRange.addr_type") String addr_type
            , @Field("zzInfoRange.region_id") String region_id
            , @Field("zzInfoRange.group_type") String group_type
            , @Field("zzInfoRange.group_id_a") String group_id_a
            , @Field("zzInfoRange.group_id_b") String group_id_b
            , @Field("zzInfoRange.group_id_c") String group_id_c
            , @Field("zzInfoRange.bussiness_id_a") String bussiness_id_a
            , @Field("zzInfoRange.bussiness_id_b") String bussiness_id_b
            , @Field("zzInfoRange.bussiness_id_c") String bussiness_id_c
            , @Field("zzInfoRange.union_type") String union_type
            , @Field("zzInfoRange.union_id") String union_id
            , @Field("zzInfoRange.shop_type") String shop_type
            , @Field("zzInfoRange.shop_id") String shop_id
            , @Field("zzInfoRange.terminal_id") String terminal_id
            , @Field("zzInfoRange.star_level") String star_level
            , @Field("zzInfoRange.address") String address
            , @Field("zzInfoRange.addr_longitude") String addr_longitude
            , @Field("zzInfoRange.addr_latitude") String addr_latitude
            , @Field("zzInfoRange.distance") String distance
    );

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/packageRangeMarket")
    Observable<PushResultBean> packageRangeMarket(
            @Field("package_id") String package_id
            , @Field("zzInfoRange.range_type") String range_type
            , @Field("zzInfoRange.startdate") String startdate
            , @Field("zzInfoRange.enddate") String enddate
            , @Field("zzInfoRange.addr_type") String addr_type
            , @Field("zzInfoRange.region_id") String region_id
            , @Field("zzInfoRange.group_type") String group_type
            , @Field("zzInfoRange.group_id_a") String group_id_a
            , @Field("zzInfoRange.group_id_b") String group_id_b
            , @Field("zzInfoRange.group_id_c") String group_id_c
            , @Field("zzInfoRange.bussiness_id_a") String bussiness_id_a
            , @Field("zzInfoRange.bussiness_id_b") String bussiness_id_b
            , @Field("zzInfoRange.bussiness_id_c") String bussiness_id_c
            , @Field("zzInfoRange.union_type") String union_type
            , @Field("zzInfoRange.union_id") String union_id
            , @Field("zzInfoRange.shop_type") String shop_type
            , @Field("zzInfoRange.shop_id") String shop_id
            , @Field("zzInfoRange.terminal_id") String terminal_id
            , @Field("zzInfoRange.star_level") String star_level
            , @Field("zzInfoRange.address") String address
            , @Field("zzInfoRange.addr_longitude") String addr_longitude
            , @Field("zzInfoRange.addr_latitude") String addr_latitude
            , @Field("zzInfoRange.distance") String distance
    );


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/payRangeMarket")
    Observable<PushOrderConfimResultBean> payRangeMarket(@Field("range_id") String range_id, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/buyworkcopyrightinit")
    Observable<PushOrderConfimResultBean> buyworkcopyrightinit(@Field("work_id") String work_id, @Field("rq_id") String rq_id);


    @GET("Pigeon4Server/pigeon/personal/getfeedictlist")
    Observable<StarLeveMoney> getfeedictlist(@Query("dicttype") String dicttype);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/createInfoDistribute")
    Observable<VCodeBenan> createInfoDistribute(@Field("rq_id") String rq_id, @Field("work_id") String work_id);

    @GET("Pigeon4Server/pigeon/personal/historyrangemarket")
    Observable<PushHistory> historyrangemarket();

    @GET("Pigeon4Server/pigeon/jz/order/orderDetail")
    Observable<OrderDetailBean> orderDetail(@Query("range_id") String range_id, @Query("terminal_id") String terminal_id, @Query("type") String type);

    @GET("Pigeon4Server/pigeon/jz/order/orderFeeDetail")
    Observable<OrderMoneyListBean> orderFeeDetail(@Query("range_id") String range_id, @Query("terminal_id") String terminal_id);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/order/receiveOrder")
    Observable<VCodeBenan> receiveOrder(@Field("range_id") String range_id, @Field("range_detail_ids") String range_detail_ids, @Field("terminal_id") String terminal_id);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/updateintent")
    Observable<VCodeBenan> updateintent(@Field("rq_id") String rq_id, @Field("is_show_phone") String is_show_phone, @Field("rq_telephone") String rq_telephone
            , @Field("is_pay_money") String is_pay_money, @Field("rq_intention_money") String rq_intention_money);


    @GET("Pigeon4Server/pigeon/jz/order/gettakebackorderreasonlist")
    Observable<SecurityQsBean> gettakebackorderreasonlist();

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/order/takebackorder")
    Observable<VCodeBenan> takebackorder(@Field("terminal_id") String terminal_id, @Field("range_id") String range_id, @Field("remark") String remark);


    @GET("Pigeon4Server/pigeon/personal/getmyavailableworklist")
    Observable<MyWorksBean> getmyavailableworklist(@Query("terminal_id") String terminal_id);

    @GET("Pigeon4Server/pigeon/personal/getmymessage")
    Observable<MsgBean> getmymessage(@Query("msg_version") String msg_version);


    @GET("Pigeon4Server/pigeon/zz/requirement/finishRangeMarket")
    Observable<VCodeBenan> finishRangeMarket(@Query("range_id") String range_id);

    @GET("Pigeon4Server/pigeon/zz/requirement/finishWorkRangeMarket")
    Observable<VCodeBenan> finishWorkRangeMarket(@Query("work_id") String work_id, @Query("finish_type") String finish_type);


    @POST("Pigeon4Server/pigeon/personal/applyforjz")
    Observable<VCodeBenan> applyforjz();


    @POST("Pigeon4Server/pigeon/personal/applyforzz")
    Observable<VCodeBenan> applyforzz();

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/coupon/mynewpwdcouponlist")
    Observable<VCodeBenan> mynewpwdcouponlist(@Field("rq_id") String rq_id, @Field("work_id") String work_id);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/coupon/usenewpwdcoupon")
    Observable<VCodeBenan> usenewpwdcoupon(@Field("ids") String ids);

    //删除我的可用作品
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/deletemyavailablework")
    Observable<EmptyBean> deleteAvailableWork(@Field("work_id") String work_id,@Field("rq_id") String rq_id);


    @GET("Pigeon4Server/pigeon/personal/getmyinoutdetail")
    Observable<WalletDetailsBean> getmyinoutdetail(@Query("src_type") String src_type, @Query("trade_id") String trade_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/addbankcard")
    Observable<VCodeBenan> addbankcard(@Field("bankcardno") String bankcardno,
                                       @Field("bank_id") String bank_id,
                                       @Field("name") String name,
                                       @Field("subbranch_addr") String subbranch_addr);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/withdrawapply")
    Observable<VCodeBenan> withdrawapply(@Field("bank_ids") String bank_ids,
                                         @Field("amount") String amount);

    //获取音乐列表
    @GET("Pigeon4Server/pigeon/jz/terminal/getBgaudioTypeList")
    Observable<MusicListBean> getmusiclList();

    //获取vip码
    @GET("Pigeon4Server/pigeon/personal/getmyvipcodelist")
    Observable<VipListBean> getVipList();

    //获取播放图片内容
    @GET("Pigeon4Server/pigeon/jz/terminal/requestTerminalSnapshot")
    Observable<TerminalPictureBean> getTerminalPicture(@Query("terminal_id") String terminal_id);

    //获取我的店铺
    @GET("Pigeon4Server/pigeon/jz/shop/list")
    Observable<MyshopListBean> getMyshoplList();

    //多屏联动包里列表
    @GET("Pigeon4Server/pigeon/zz/work/worklinklist")
    Observable<LiandongbaoBean> getLiandongbaoList();

    //账户被监管信息
    @GET("Pigeon4Server/pigeon/personal/getbindaudituserinfo")
    Observable<EmptyBean> getBeiAuditList(@Query("mobile") String mobile);

    //账户监管列表
    @GET("Pigeon4Server/pigeon/personal/getmyaudituserlist")
    Observable<AuditListBean> getAuditList();

    //删除监管用户
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/personal/deleteaudituser")
    Observable<EmptyBean> deleteAuditUser(@Field("mobile") String mobile);

    //未审核订单终端列表
    @GET("Pigeon4Server/pigeon/personal/getaudituserterminalorderlist")
    Observable<AuditTerminalListBean> getAuditTerminalList(@Query("user_id") String user_id);

    //未审核订作品端列表
    @GET("Pigeon4Server/pigeon/personal/getaudituserworklist")
    Observable<AuditWorkListBean> getAuditWorksList(@Query("user_id") String user_id);

    //获取监管者信息
    @GET("Pigeon4Server/pigeon/personal/getauditoruserinfo")
    Observable<AuditUserInfor> getAuditInfor();

    //作品组合包列表
    @GET("Pigeon4Server/pigeon/zz/work/workpackagelist")
    Observable<CombinationListBean> getCombinationPackageList();

    //获取联动包详情
    @GET("Pigeon4Server/pigeon/zz/work/worklinkdetaillist")
    Observable<LinkPackageDetailBean> getLiandongbaoDetail(@Query("link_id") String link_id);

    //获取作品组合包详情
    @GET("Pigeon4Server/pigeon/zz/work/workpackagedetaillist")
    Observable<CombinationListDetailBean> getCombinationPackageDetail(@Query("package_id") String package_id);

    //新建多屏联动包
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/addworklink")
    Observable<AddlinkPackageBean> addLinkPackage(@Field("link_name") String link_name);

    //新建作品组合包
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/addworkpackage")
    Observable<CombinationAddBean> addCombinationPackage(@Field("package_name") String package_name);

    //组合包排序
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/updateworkpackagedetail")
    Observable<EmptyBean> linkPackageSort(@Field("package_id") String package_id,@Field("work_ids") String work_ids,@Field("rq_ids") String rq_ids);

    //联动包排序
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/updateworklinkdetail")
    Observable<EmptyBean> linkPackageSort_(@Field("link_id") String package_id,@Field("work_ids") String work_ids,@Field("rq_ids") String rq_ids);


    //删除多屏联动包
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/deleteworklink")
    Observable<EmptyBean> deleteLinkPackage(@Field("link_id") String link_id);

    //删除作品组合包
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/deleteworkpackage")
    Observable<EmptyBean> deleteCombinationPackage(@Field("package_id") String package_id);


    //编辑联动包的信息
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/updateworklink")
    Observable<EmptyBean> editLinkPackage(@Field("zzWorkLink.link_id") String link_id,@Field("zzWorkLink.link_name") String link_name,@Field("zzWorkLink.cover_url") String cover_url);

    //编辑组合包的信息
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/updateworkpackage")
    Observable<EmptyBean> editCombinationPackage(@Field("zzWorkPackage.package_id") String package_id,@Field("zzWorkPackage.package_name") String package_name,@Field("zzWorkPackage.cover_url") String cover_url,@Field("zzWorkPackage.orientation") String orientation,@Field("zzWorkPackage.is_time_limit") String is_time_limit,@Field("zzWorkPackage.start_time") String start_time,@Field("zzWorkPackage.end_time") String end_time,@Field("zzWorkPackage.repeat_weekday") String repeat_weekday,@Field("zzWorkPackage.is_ignore_other_work") String is_ignore_other_work);


    //清空联动包里面的所有作品
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/clearworklinkdetail")
    Observable<EmptyBean> clearLinkPackage(@Field("link_id") String link_id);

    //清空作品组合包里面的所有作品
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/clearworkpackagedetail")
    Observable<EmptyBean> clearCombinationPackage(@Field("package_id") String package_id);

    //删除多屏联动包详情内容
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/deleteworklinkdetail")
    Observable<EmptyBean> deleteLinkDetailPackage(@Field("link_id") String link_id,@Field("detail_ids") String detail_ids);

    //删除作品组合包详情内容
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/deleteworkpackagedetail")
    Observable<EmptyBean> deleteCombinationDetailPackage(@Field("package_id") String package_id,@Field("detail_ids") String detail_ids);


    //添加作品到联动包作品
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/addworklinkdetail")
    Observable<EmptyBean> addLinkPackage(@Field("link_id") String link_id, @Field("work_ids") String work_ids, @Field("rq_ids") String rq_ids);

    //添加作品到作品组合包
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/addworkpackagedetail")
    Observable<EmptyBean> addCombinationPackage(@Field("package_id") String package_id, @Field("work_ids") String work_ids, @Field("rq_ids") String rq_ids);


    //设置领播作品
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/updateworklinkdetailleader")
    Observable<EmptyBean> leaderLinkPackage(@Field("link_id") String link_id,@Field("link_detail_id") String link_detail_id);

    //保存联动投放关系  String link_id,String link_snap_name,String union_id,String link_detail_id,String terminal_ids,String leader_link_detail_id,
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/saveworklinkdistribute")
    Observable<SetLiandongbaoGuanxi> saveLiandongbaoGuanxi(@Field("link_id") String link_id, @Field("link_snap_name") String link_snap_name, @Field("union_id") String union_id, @Field("link_detail_ids") String link_detail_ids, @Field("terminal_ids") String terminal_ids, @Field("leader_link_detail_id") String leader_link_detail_id);

    //设置联动投放关系
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/requirement/linkRangeMarket")
    Observable<EmptyBean> setLiandongbaoGuanxi(@Field("link_snap_id") String link_snap_id,@Field("zzInfoRange.range_type") String range_type,@Field("zzInfoRange.startdate") String startdate,@Field("zzInfoRange.enddate") String enddate,@Field("zzInfoRange.star_level") String star_level);


    //获取联动包投放历史记录
    @GET("Pigeon4Server/pigeon/zz/work/hisworklinkdistribute")
    Observable<LinkPackageHistoryListBean> getLiandongbaoToufangHistory(@Query("link_id") String link_id);

    //获取联动包投放历史记录---下面的包作品
    @GET("Pigeon4Server/pigeon/zz/work/hisworklinkdetaillist")
    Observable<LinkPackageDetailBean> getLiandongbaoHistory_List(@Query("link_snap_id") String link_snap_id);

    //删除多屏联动包投放关系
    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/deleteworklinkdistribute")
    Observable<EmptyBean> deleteLinkToufangGuangxi(@Field("link_snap_id") String link_snap_id);


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/zz/work/selfdesignwork")
    Observable<VCodeBenan> selfdesignwork(@Field("zzRequirement.activity_onoff") String activity_onoff,
                                          @Field("zzRequirement.activity_brand") String activity_brand,
                                          @Field("zzRequirement.activity_content") String activity_content,
                                          @Field("zzRequirement.activity_nav_type") String activity_nav_type,
                                          @Field("zzRequirement.activity_nav_content") String activity_nav_content,
                                          @Field("zzRequirement.activity_nav_longitude") String activity_nav_longitude,
                                          @Field("zzRequirement.activity_nav_latitude") String activity_nav_latitude,
                                          @Field("zzRequirement.activity_detail") String activity_detail,
                                          @Field("ptWorks.work_type") String work_type,
                                          @Field("ptWorks.work_name") String work_name,
                                          @Field("ptWorks.orientation") String orientation,
                                          @Field("material_urls") String material_urls,
                                          @Field("material_types") String material_types,
                                          @Field("filenames") String filenames,
                                          @Field("audio_file") String audio_file,
                                          @Field("audio_file_name") String audio_file_name,
                                          @Field("watermark") String watermark,
                                          @Field("watermark_name") String watermark_name,
                                          @Field("W") String w,
                                          @Field("H") String h,
                                          @Field("X") String y,
                                          @Field("Y") String z

    );


    @GET("Pigeon4Server/pigeon/jz/shop/getTerminalStatusList")
    Observable<VCodeBenan> getTerminalStatusList(@Query("terminal_ids") String terminal_ids);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/quitfromunion")
    Observable<VCodeBenan> quitfromunion(@Field("union_id") String union_id);

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/delete")
    Observable<VCodeBenan> unionDelete(@Field("union_id") String union_id);

    @GET("Pigeon4Server/pigeon/jz/shop/checkShopCntRight")
    Observable<ShopLimtBean> checkShopCntRight();

    @GET("Pigeon4Server/pigeon/jz/terminal/checkTerminalOwnCntRight")
    Observable<ShopLimtBean> checkTerminalOwnCntRight();


    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/order/takebackterminalallorder")
    Observable<VCodeBenan> takebackterminalallorder(@Field("terminal_id") String terminal_id);


    @GET("Pigeon4Server/pigeon/jz/terminal/getTerminalPlayList")
    Observable<PlayLogBean> getTerminalPlayList(@Query("terminal_id") String terminal_ids, @Query("range_id") String range_id, @Query("info_id") String info_id);

    @GET("Pigeon4Server/pigeon/jz/terminal/getModuleMaterialList")
    Observable<MaterialBean> getModuleMaterialList(@Query("module_id") String module_id);


    @GET("Pigeon4Server/pigeon/zz/requirement/distributeLocationMapList")
    Observable<PushLocationShowMapBean> distributeLocationMapList(@Query("rq_id") String rq_id, @Query("work_id") String work_id, @Query("pageNumber") String pageNumber
            , @Query("pageSize") String pageSize);


    @GET("Pigeon4Server/pigeon/personal/historyrangemarketdetail")
    Observable<PushHistoryDtBean> historyrangemarketdetail(@Query("info_id") String info_id);

    @GET("Pigeon4Server/pigeon/account/logout")
    Observable<VCodeBenan> logout();

    @FormUrlEncoded
    @POST("Pigeon4Server/pigeon/jz/union/deleteUserFromUnion")
    Observable<VCodeBenan> deleteUserFromUnion(@Field("union_id") String union_id, @Field("delete_user_id") String delete_user_id);


    @GET("Pigeon4Server/pigeon/zz/requirement/getdictrequirementhintlist")
    Observable<StarLeveMoney> getdictrequirementhintlist();


    @GET("Pigeon4Server/pigeon/personal/getmyinoutstatistic")
    Observable<WalletListBean> getmyinoutstatistic(@Query("yearmonths") String yearmonths);

}
