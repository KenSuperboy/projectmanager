package com.gohoc.xiupuling.net;

import android.content.Context;

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
import com.gohoc.xiupuling.constant.Constant;
import com.orhanobut.logger.Logger;

import java.net.CookieStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sherlock-sky on 2017/1/27.
 */

public class RxRetrofitClient implements Constant {

    private static RxRetrofitClient instance;
    private static OkHttpClient.Builder builder;
    protected static XiuPuApi xiuPuApi;
    private CookieStore cookieStore;
    private Context context;

    public static RxRetrofitClient getInstance(Context context) {
        if (null == instance) {
            instance = new RxRetrofitClient(context);
        }
        return instance;
    }


    public RxRetrofitClient(Context context) {
        this.context = context;
        initClient();
    }

    private void initClient() {
        if (null != builder)
            return;
        // 创建OkHttpClient
        builder = new OkHttpClient.Builder()
                // 超时设置
                .connectTimeout(NetConstant.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NetConstant.DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NetConstant.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true)
                // 支持HTTPS
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)) //明文Http与比较新的Https
                // cookie管理
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        //cookieStore.put(url, cookies);
                        for (Cookie ck : cookies) {
                            Logger.d(ck);
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });


        // 添加各种插入器
        addInterceptor(builder);
        // 创建Retrofit实例
        Retrofit fishRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.NetConstant.BASE_URL)

                .build();
        // 创建API接口类
        xiuPuApi = fishRetrofit.create(XiuPuApi.class);

    }

    private void addInterceptor(OkHttpClient.Builder builder) {
        // 添加Header
        builder.addInterceptor(new HttpHeaderInterceptor(context));
        builder.addInterceptor(new HttpCacheInterceptor(context));
        builder.authenticator(new TokenAuthenticator(context));

        // 添加缓存控制策略

        // 添加http log
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();//new HttpLogger()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);


        builder.addInterceptor(logger);


    }

    //各种接口实现
    public static void login(String gid, String uid, String username, String password, String platform, String mphone_version, Observer<UserBean> observer) {
        setSubscribe(xiuPuApi.login(gid, uid, username, password, platform, mphone_version), observer);
    }

    public static void getauthcode(String mobile, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.getauthcode(mobile), observer);
    }

    public static void getMsgCode(String mobile, String authcode, String authtype, Observer<VCodeBenan> observer) {
        /*if (authtype.equals("3"))
            setSubscribe(xiuPuApi.getMsgCode(mobile, authcode, "2"), observer);
        else*/
            setSubscribe(xiuPuApi.getMsgCode(mobile, authcode, authtype), observer);
    }

    public static void getphonemsgcode(String mobile, String is_need_check, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.getphonemsgcode(mobile, is_need_check), observer);
    }

    public static void verifysmscode(String mobile, String smscode, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.verifysmscode(mobile, smscode), observer);
    }

    public static void verifysmscode2(String mobile, String smscode, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.verifysmscode2(mobile, smscode), observer);
    }

    public static void bindAuditUser(String mobile, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.bindAuditUser(mobile), observer);
    }

    public static void addVip(String vip_code, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.addvipcode(vip_code), observer);
    }

    public static void bindVip(String terminal_id,String vip_code, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.bindvipcode(terminal_id,vip_code), observer);
    }

    public static void unbindAuditUser( Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.unbindAuditUser(), observer);
    }

    public static void shenheTerminalAuditUser(String user_id,String terminal_id,String range_id,String dicision,Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.shenheTerminalAuditUser(user_id,terminal_id,range_id,dicision), observer);
    }

    public static void shenheWorkAuditUser(String user_id,String work_id,String dicision, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.shenheWorkAuditUser(user_id,work_id,dicision), observer);
    }

    public static void resetpwd(String mobile, String password, String duppassword, String platform, String phone_version, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.resetpwd(mobile, password, duppassword, platform, phone_version), observer);
    }

    public static void regist(String uid, String mobile, String password, String duppassword, String platform, String phone_version, String shared_platform
            , String promo_id, Observer<UserBean> observer) {
        setSubscribe(xiuPuApi.regist(uid, mobile, password, duppassword, platform, phone_version, shared_platform, promo_id), observer);
    }

    public static void updatepwd(String oldpwd, String newpwd, String duppwd, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updatepwd(oldpwd, newpwd, duppwd), observer);
    }

    public static void updatePortrait(String portraitURL, String nickname, String sex, String age, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updatePortrait(portraitURL, nickname, sex, age), observer);
    }

    public static void getTerminalList(String orderby, Observer<ShopBean> observer) {
        setSubscribe(xiuPuApi.terminalList(orderby), observer);
    }

    public static void getCitylist(String parentId, String level, Observer<LocationBean> observer) {
        setSubscribe(xiuPuApi.citylist(parentId, level), observer);
    }

    public static void getGroup(String region_id, String type, Observer<BusinessBean> observer) {
        setSubscribe(xiuPuApi.getgroup(region_id, type), observer);
    }

    public static void businessCategories(String business_level, String parent_id, Observer<BusinessTypeBean> observer) {
        setSubscribe(xiuPuApi.businessCategories(business_level, parent_id), observer);
    }

    public static void getUploadToken(Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.getUploadToken(), observer);
    }

    public static void updateCellphone(String mobile, String smscode, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updateCellphone(mobile, smscode), observer);
    }

    public static void accesstokencheck(String access_token, String client_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.accesstokencheck(access_token, client_id), observer);
    }

    public static void personalVerifyapply(String idno, String name, String idphoto_a, String idphoto_b, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.personalVerifyapply(idno, name, idphoto_a, idphoto_b), observer);
    }

    public static void companyVerifyapply(String business_code, String code_photo_url, String name
            , String org_id, String org_id_photo_url, String tax_no, String tax_photo_url, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.companyVerifyapply(business_code, code_photo_url, name, org_id, org_id_photo_url, tax_no, tax_photo_url), observer);
    }

    public static void companyVerifyapplyInfo(Observer<CompanyVerifyapplyInfoBean> observer) {
        setSubscribe(xiuPuApi.companyVerifyapplyInfo(), observer);
    }

    public static void personalVerifyapplyInfo(Observer<PersonalVerifyapplyInfoBean> observer) {
        setSubscribe(xiuPuApi.personalVerifyapplyInfo(), observer);
    }

    public static void securityqalist(String type, Observer<SecurityQsBean> observer) {
        setSubscribe(xiuPuApi.securityqalist(type), observer);
    }

    public static void getmysecurityqa(Observer<MySecurityQsBean> observer) {
        setSubscribe(xiuPuApi.getmysecurityqa(), observer);
    }

    public static void setsecurityqa(String question_a, String question_b, String question_c, String answer_a, String answer_b, String answer_c, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.setsecurityqa(question_a, question_b, question_c, answer_a, answer_b, answer_c), observer);
    }

    public static void updatesecurityqa(String question_a, String question_b, String question_c, String answer_a, String answer_b, String answer_c, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updatesecurityqa(question_a, question_b, question_c, answer_a, answer_b, answer_c), observer);
    }


    public static void userbasicinfo(Observer<UserBaseBean> observer) {
        setSubscribe(xiuPuApi.userbasicinfo(), observer);
    }

    public static void region(String region_name, String level, String city_name, Observer<RegionLocationBean> observer) {
        setSubscribe(xiuPuApi.region(region_name, level, city_name), observer);
    }

    public static void systemdictionarylist(Observer<ResponseBody> observer) {
        setSubscribe(xiuPuApi.systemdictionarylist(), observer);
    }

    public static void getTerminalDetail(String terminal_id, Observer<TerminalBean> observer) {
        setSubscribe(xiuPuApi.getTerminalDetail(terminal_id), observer);
    }

    public static void saveShop(String shop_name, String shop_address, String shop_region_province, String shop_region_city, String shop_region_id, String shop_group_id, String shop_addr_longitude, String shop_addr_latitude, String shop_telephone, String shop_business_id, String shop_photos, Observer<AddShopResultBean> observer) {
        setSubscribe(xiuPuApi.saveShop(shop_name, shop_address, shop_region_province, shop_region_city, shop_region_id, shop_group_id, shop_addr_longitude, shop_addr_latitude, shop_telephone, shop_business_id, shop_photos), observer);
    }

    public static void updateShop(String shop_id, String shop_name, String shop_address, String shop_region_province, String shop_region_city, String shop_region_id, String shop_group_id, String shop_addr_longitude, String shop_addr_latitude, String shop_telephone, String shop_business_id, String shop_photos, Observer<AddShopResultBean> observer) {
        setSubscribe(xiuPuApi.updateShop(shop_id, shop_name, shop_address, shop_region_province, shop_region_city, shop_region_id, shop_group_id, shop_addr_longitude, shop_addr_latitude, shop_telephone, shop_business_id, shop_photos), observer);
    }

    public static void getShopPhotos(String shop_id, Observer<BannerBean> observer) {
        setSubscribe(xiuPuApi.getShopPhotos(shop_id), observer);
    }

    public static void saveTerminal(String access_token, String client_id, String shop_id, String terminal_no, String term_soft_version, String term_orientation, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.saveTerminal(access_token, client_id, shop_id, terminal_no, term_soft_version, term_orientation), observer);
    }

    public static void updateTerminal(String terminal_id, String shop_id, String terminal_no, String term_soft_version, String term_orientation, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updateTerminal(terminal_id, shop_id, terminal_no, term_soft_version, term_orientation), observer);
    }


    public static void getTopModuleList(Observer<MoudlesBean> observer) {
        setSubscribe(xiuPuApi.getTopModuleList(), observer);
    }

    public static void getShopDetail(String shop_id, Observer<ShopDetailsBean> observer) {
        setSubscribe(xiuPuApi.getShopDetail(shop_id), observer);
    }

    public static void getShopTermList(String shop_id, String orientation, Observer<TerminalListBean> observer) {
        setSubscribe(xiuPuApi.getShopTermList(shop_id, orientation), observer);
    }

    //联动群组查询
    public static void getCombinationShopTermList(String shop_id, String orientation,String is_link, Observer<CombinationAddTerminalBean> observer) {
        setSubscribe(xiuPuApi.getCombinationShopTermList(shop_id, orientation,is_link), observer);
    }

    public static void recovery(String access_token, String client_id, String terminal_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.recovery(access_token, client_id
                , terminal_id), observer);
    }

    public static void getMyUnionList(String filterby, String orderby, Observer<GroupBean> observer) {
        setSubscribe(xiuPuApi.getMyUnionList(filterby, orderby), observer);
    }

    public static void unionDetailInfo(String union_id, Observer<GroupDetailsBean> observer) {
        setSubscribe(xiuPuApi.unionDetailInfo(union_id), observer);
    }

    public static void getUnionTerminalListofMine(String union_id, Observer<GroupTermianlListBean> observer) {
        setSubscribe(xiuPuApi.getUnionTerminalListofMine(union_id), observer);
    }

    public static void getUnUnionTermList(String union_id, Observer<GroupTermianlListBean> observer) {
        setSubscribe(xiuPuApi.getUnUnionTermList(union_id), observer);
    }

    public static void getUnionTerminalList(String union_id, Observer<GroupTermianlListBean> observer) {
        setSubscribe(xiuPuApi.getUnionTerminalList(union_id), observer);
    }

    public static void getUnionTerminalListofTheirs(String union_id, Observer<GroupTermianlListBean> observer) {
        setSubscribe(xiuPuApi.getUnionTerminalListofTheirs(union_id), observer);
    }

    public static void getPersonalModuleList(Observer<ModuleBean> observer) {
        setSubscribe(xiuPuApi.getPersonalModuleList(), observer);
    }

    public static void getCurrentPlayList(String terminal_id, Observer<PlayerList> observer) {
        setSubscribe(xiuPuApi.getCurrentPlayList(terminal_id), observer);
    }

    public static void getSelectedModuleList(String terminal_id, Observer<OpenModuleBean> observer) {
        setSubscribe(xiuPuApi.getSelectedModuleList(terminal_id), observer);
    }

    public static void terminalTagWallList(String terminal_id, String pageNumber, String pageSize, Observer<ModuleBean> observer) {
        setSubscribe(xiuPuApi.terminalTagWallList(terminal_id, pageNumber, pageSize), observer);
    }

    public static void updateSelectedModules(String terminal_id, String selected_module_ids, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updateSelectedModules(terminal_id, selected_module_ids), observer);
    }

    public static void updateUnion(String union_id, String union_name
            , String union_type, String union_brief_info,
                                   String union_portrait, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updateUnion(union_id, union_name, union_type, union_brief_info, union_portrait), observer);
    }

    public static void getMyUnionUserList(String union_id, Observer<GroupUserBean> observer) {
        setSubscribe(xiuPuApi.getMyUnionUserList(union_id), observer);
    }

    public static void resetinvitationcode(String union_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.resetinvitationcode(union_id), observer);
    }

    public static void getCanJoinUnionListByCond(String q, Observer<GroupSearchBean> observer) {
        setSubscribe(xiuPuApi.getCanJoinUnionListByCond(q), observer);
    }

    public static void saveUnion(String union_name, String union_type, String union_brief_info, String union_portrait, Observer<GroupDetailsBean> observer) {
        setSubscribe(xiuPuApi.saveUnion(union_name, union_type, union_brief_info, union_portrait), observer);
    }

    public static void doJoinUnion(String union_id, String invitecode, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.doJoinUnion(union_id, invitecode), observer);
    }

    public static void addMultiTermsToUnion(String union_id, String terminal_ids, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.addMultiTermsToUnion(union_id, terminal_ids), observer);
    }

    //联动群组加入设备终端
    public static void addCombinationMultiTermsToUnion(String union_id, String terminal_ids, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.addCombinationMultiTermsToUnion(union_id, terminal_ids), observer);
    }

    public static void shiftUnionOwn(String union_id, String to_user_id, String quitflag, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.shiftUnionOwn(union_id, to_user_id, quitflag), observer);
    }

    public static void deleteTermFromUnion(String ids, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.deleteTermFromUnion(ids), observer);
    }

    public static void autoReceivingOrderSwitch(String ids, String switcher, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.autoReceivingOrderSwitch(ids, switcher), observer);
    }

    public static void showrequirelist(String orderby, String pageNumber,
                                       String pageSize, String pagingquery, Observer<ReqBean> observer) {
        setSubscribe(xiuPuApi.showrequirelist(orderby, pageNumber, pageSize, pagingquery), observer);
    }

    public static void selectworklist(String orderby, String pageNumber,
                                      String pageSize, String pagingquery, Observer<ReqListBean> observer) {
        setSubscribe(xiuPuApi.selectworklist(orderby, pageNumber, pageSize, pagingquery), observer);
    }

    public static void getworkdesignerinfo(String rq_id, String work_id, Observer<DesignerBean> observer) {
        setSubscribe(xiuPuApi.getworkdesignerinfo(rq_id, work_id), observer);
    }

    public static void deleteTerminal(String terminal_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.deleteTerminal(terminal_id), observer);
    }

    public static void setterminalruntimestatus(String term_orientation, String terminal_id, String force_xpl, String schedule_up_down,
                                                String up_start_time, String up_end_time, String take_order, String repeat_weekday,String bg_audio_flag,String bg_audio_type,String roll_title_flag,String roll_titles,String weather_flag,String scancode_flag, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.setterminalruntimestatus(term_orientation, terminal_id, force_xpl, schedule_up_down, up_start_time, up_end_time, take_order, repeat_weekday,bg_audio_flag,bg_audio_type,roll_title_flag,roll_titles,weather_flag,scancode_flag), observer);
    }


    public static void requirementSave(String rq_type, String activity_title, String activity_onoff,
                                       String activity_brand, String activity_content, String activity_nav_type,
                                       String activity_nav_content, String activity_nav_longitude, String activity_nav_latitude,
                                       String activity_detail, String rq_orientation, String rq_design_spec, String material_urls, String material_types,
                                       ArrayList<String> remark_list, Observer<CreatRqResultBean> observer) {

        setSubscribe(xiuPuApi.requirementSave(rq_type, activity_title, activity_onoff, activity_brand,
                activity_content, activity_nav_type, activity_nav_content, activity_nav_longitude,
                activity_nav_latitude, activity_detail, rq_orientation, rq_design_spec, material_urls, material_types,
                remark_list != null && remark_list.size() == 1 ? remark_list.get(0) : null,
                remark_list != null && remark_list.size() == 2 ? remark_list.get(1) : null,
                remark_list != null && remark_list.size() == 3 ? remark_list.get(2) : null,
                remark_list != null && remark_list.size() == 4 ? remark_list.get(3) : null,
                remark_list != null && remark_list.size() == 5 ? remark_list.get(4) : null,
                remark_list != null && remark_list.size() == 6 ? remark_list.get(5) : null), observer);
    }

    public static void requirementUpdate(String rq_id, String rq_type, String activity_title, String activity_onoff,
                                         String activity_brand, String activity_content, String activity_nav_type,
                                         String activity_nav_content, String activity_nav_longitude, String activity_nav_latitude,
                                         String activity_detail, String rq_orientation, String rq_design_spec, String material_urls, String material_types,
                                         ArrayList<String> remark_list, Observer<CreatRqResultBean> observer) {

        setSubscribe(xiuPuApi.requirementUpdate(rq_id, rq_type, activity_title, activity_onoff, activity_brand,
                activity_content, activity_nav_type, activity_nav_content, activity_nav_longitude,
                activity_nav_latitude, activity_detail, rq_orientation, rq_design_spec, material_urls, material_types,
                remark_list != null && remark_list.size() == 1 ? remark_list.get(0) : null,
                remark_list != null && remark_list.size() == 2 ? remark_list.get(1) : null,
                remark_list != null && remark_list.size() == 3 ? remark_list.get(2) : null,
                remark_list != null && remark_list.size() == 4 ? remark_list.get(3) : null,
                remark_list != null && remark_list.size() == 5 ? remark_list.get(4) : null,
                remark_list != null && remark_list.size() == 6 ? remark_list.get(5) : null), observer);
    }

    public static void updateWorkname(String work_id, String work_name, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updateWorkname(work_id, work_name), observer);
    }

    public static void closeRequirement(String rq_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.closeRequirement(rq_id), observer);
    }

    public static void workdetail(String work_id, Observer<WorksDetails> observer) {
        setSubscribe(xiuPuApi.workdetail(work_id), observer);
    }

    public static void distributeLocationList(String rq_id, String work_id, String pageNumber
            , String pageSize, Observer<PushLocationShowBean> observer) {
        setSubscribe(xiuPuApi.distributeLocationList(rq_id, work_id, pageNumber, pageSize), observer);
    }

    public static void distributeLocationMapList(String rq_id, String work_id, String pageNumber
            , String pageSize, Observer<PushLocationShowMapBean> observer) {
        setSubscribe(xiuPuApi.distributeLocationMapList(rq_id, work_id, pageNumber, pageSize), observer);
    }


    public static void workDistributeDetail(String rq_id, String work_id, Observer<PushReqResultBean> observer) {
        setSubscribe(xiuPuApi.workDistributeDetail(rq_id, work_id), observer);
    }

    public static void workShowDetail(String rq_id, String work_id, Observer<PushReqShowBean> observer) {
        setSubscribe(xiuPuApi.workShowDetail(rq_id, work_id), observer);
    }

    public static void orderlist(String ordertype, Observer<OrderBean> observer) {
        setSubscribe(xiuPuApi.orderlist(ordertype), observer);
    }

    public static void getVipGoodsList(Observer<GoodsBean> observer) {
        setSubscribe(xiuPuApi.getVipGoodsList(), observer);
    }

    public static void getpointtradedtl(String pageNumber, String pageSize, Observer<TokenBean> observer) {
        setSubscribe(xiuPuApi.getpointtradedtl(pageNumber, pageSize), observer);
    }

    public static void dailycheckin(Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.dailycheckin(), observer);
    }

    public static void getmypoin(Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.getmypoin(), observer);
    }

    public static void historyOrderList(String pageNumber,
                                        String pageSize,
                                        Observer<OrderHistoryBean> observer) {
        setSubscribe(xiuPuApi.historyOrderList(pageNumber,pageSize), observer);
    }

    //当前接单
    public static void currentOrderList(String pageNumber,
                                        String pageSize,
                                        Observer<OrderHistoryBean> observer) {
        setSubscribe(xiuPuApi.currentOrderList(pageNumber,pageSize), observer);
    }

    public static void buyVipGoods(String goods_id, Observer<BuyVipGoodsBean> observer) {
        setSubscribe(xiuPuApi.buyVipGoods(goods_id), observer);
    }

    public static void copyrightdtl(String timeperiod, Observer<CopyrrightBean> observer) {
        setSubscribe(xiuPuApi.copyrightdtl(timeperiod), observer);
    }

    public static void mynewpwdcouponlist(Observer<CouponBean> observer) {
        setSubscribe(xiuPuApi.mynewpwdcouponlist(), observer);
    }

    public static void initorder(String refno, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.initorder(refno), observer);
    }

    public static void kqinitorder(String refno, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.kqinitorder(refno), observer);
    }


    public static void unifiedorder(String refno, Observer<WxPayBean> observer) {
        setSubscribe(xiuPuApi.unifiedorder(refno), observer);
    }

    public static void pocketmoney(Observer<WalletBean> observer) {
        setSubscribe(xiuPuApi.pocketmoney(), observer);
    }

    public static void banklist(Observer<BankBean> observer) {
        setSubscribe(xiuPuApi.banklist(), observer);
    }

    public static void mybankcardlist(Observer<MyBankBean> observer) {
        setSubscribe(xiuPuApi.mybankcardlist(), observer);
    }

    public static void deleteShop(String shop_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.deleteShop(shop_id), observer);
    }

    public static void switchsharedplatform(String shared_platform, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.switchsharedplatform(shared_platform), observer);
    }

    public static void terminalFlagStatus(String terminal_id, Observer<TerminalFlagBean> observer) {
        setSubscribe(xiuPuApi.terminalFlagStatus(terminal_id), observer);
    }

    public static void checksecurityqa(String question_a, String question_b, String question_c, String answer_a, String answer_b
            , String answer_c, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.checksecurityqa(question_a, question_b, question_c, answer_a, answer_b, answer_c), observer);
    }

    public static void bindnewpwdcoupon(String add_pwd, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.bindnewpwdcoupon(add_pwd), observer);
    }

    //卡券相关  先扫描
    public static void querycoupon(String invite_code, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.querycoupon(invite_code), observer);
    }

    //然后邀请码  和token
    public static void querybindcoupon(String invite_code,String token, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.querybindcoupon(invite_code,token), observer);
    }

    public static void orderExecuteStatusList(String terminal_id, Observer<OrderSBean> observer) {
        setSubscribe(xiuPuApi.orderExecuteStatusList(terminal_id), observer);
    }

    public static void historyrangemarket(Observer<PushHistory> observer) {
        setSubscribe(xiuPuApi.historyrangemarket(), observer);
    }


    /***
     *
     * @param rq_id       需求id
     * @param work_id    作品id
     * @param range_type   地域范围类型
     * @param startdate    开始时间
     * @param enddate      结束时间
     * @param addr_type    地址类型
     * @param region_id
     * @param group_type
     * @param group_id_a
     * @param group_id_b
     * @param group_id_c
     * @param bussiness_id_a
     * @param bussiness_id_b
     * @param bussiness_id_c
     * @param union_type
     * @param union_id
     * @param shop_type
     * @param shop_id
     * @param terminal_id
     * @param star_level
     * @param address
     * @param addr_longitude
     * @param addr_latitude
     * @param distance
     */
    public static void rangeMarket(String rq_id, String work_id, String range_type, String startdate, String enddate, String addr_type
            , String region_id, String group_type, String group_id_a, String group_id_b, String group_id_c, String bussiness_id_a, String bussiness_id_b, String bussiness_id_c
            , String union_type, String union_id, String shop_type, String shop_id, String terminal_id, String star_level, String address, String addr_longitude, String addr_latitude
            , String distance, Observer<PushResultBean> observer) {
        setSubscribe(xiuPuApi.rangeMarket(rq_id
                , work_id, range_type, startdate, enddate, addr_type
                , region_id, group_type, group_id_a, group_id_b, group_id_c, bussiness_id_a
                , bussiness_id_b, bussiness_id_c, union_type, union_id, shop_type, shop_id
                , terminal_id, star_level, address, addr_longitude, addr_latitude, distance), observer);
    }

    //组合包
    public static void packageRangeMarket(String package_id,  String range_type, String startdate, String enddate, String addr_type
            , String region_id, String group_type, String group_id_a, String group_id_b, String group_id_c, String bussiness_id_a, String bussiness_id_b, String bussiness_id_c
            , String union_type, String union_id, String shop_type, String shop_id, String terminal_id, String star_level, String address, String addr_longitude, String addr_latitude
            , String distance, Observer<PushResultBean> observer) {
        setSubscribe(xiuPuApi.packageRangeMarket(package_id
                , range_type, startdate, enddate, addr_type
                , region_id, group_type, group_id_a, group_id_b, group_id_c, bussiness_id_a
                , bussiness_id_b, bussiness_id_c, union_type, union_id, shop_type, shop_id
                , terminal_id, star_level, address, addr_longitude, addr_latitude, distance), observer);
    }

    public static void payRangeMarket(String range_id, String amount, Observer<PushOrderConfimResultBean> observer) {
        setSubscribe(xiuPuApi.payRangeMarket(range_id, amount), observer);
    }

    public static void buyworkcopyrightinit(String work_id, String rq_id, Observer<PushOrderConfimResultBean> observer) {
        setSubscribe(xiuPuApi.buyworkcopyrightinit(work_id, rq_id), observer);
    }


    public static void getfeedictlist(String dicttype, Observer<StarLeveMoney> observer) {
        setSubscribe(xiuPuApi.getfeedictlist(dicttype), observer);
    }

    public static void createInfoDistribute(String rq_id, String work_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.createInfoDistribute(rq_id, work_id), observer);
    }

    public static void orderDetail(String range_id, String terminal_id, String type, Observer<OrderDetailBean> observer) {
        setSubscribe(xiuPuApi.orderDetail(range_id, terminal_id, type), observer);
    }

    public static void orderFeeDetail(String range_id, String terminal_id, Observer<OrderMoneyListBean> observer) {
        setSubscribe(xiuPuApi.orderFeeDetail(range_id, terminal_id), observer);
    }

    public static void receiveOrder(String range_id, String range_detail_ids, String terminal_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.receiveOrder(range_id, range_detail_ids, terminal_id), observer);
    }

    public static void updateintent(String rq_id, String is_show_phone, String rq_telephone, String is_pay_money, String rq_intention_money, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.updateintent(rq_id, is_show_phone, rq_telephone, is_pay_money, rq_intention_money), observer);
    }

    public static void gettakebackorderreasonlist(Observer<SecurityQsBean> observer) {
        setSubscribe(xiuPuApi.gettakebackorderreasonlist(), observer);
    }

    public static void takebackorder(String terminal_id, String range_id, String remark, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.takebackorder(terminal_id, range_id, remark), observer);
    }

    public static void getmyavailableworklist(String terminal_id, Observer<MyWorksBean> observer) {
        setSubscribe(xiuPuApi.getmyavailableworklist(terminal_id), observer);
    }

    public static void getmymessage(String msg_version, Observer<MsgBean> observer) {
        setSubscribe(xiuPuApi.getmymessage(msg_version), observer);
    }

    public static void finishRangeMarket(String range_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.finishRangeMarket(range_id), observer);
    }

    public static void finishWorkRangeMarket(String work_id, String finish_type, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.finishWorkRangeMarket(work_id, finish_type), observer);
    }


    public static void selfdesignwork(String activity_onoff, String activity_brand, String activity_content,
                                      String activity_nav_type, String activity_nav_content, String activity_nav_longitude,
                                      String activity_nav_latitude, String activity_detail, String work_name, String rq_orientation, String rq_design_spec,
                                      String material_urls, String material_types,String filenames,String audio_file,String audio_file_name,String watermark,String watermark_name, String w, String h, String y, String z, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.selfdesignwork(activity_onoff,
                activity_brand, activity_content, activity_nav_type, activity_nav_content, activity_nav_longitude,
                activity_nav_latitude, activity_detail,
                work_name, rq_orientation, rq_design_spec, material_urls, material_types,filenames,audio_file,audio_file_name,watermark,watermark_name, w, h, y, z), observer);
    }


    public static void applyforjz(Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.applyforjz(), observer);
    }


    public static void applyforzz(Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.applyforzz(), observer);
    }

    public static void usenewpwdcoupon(String ids, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.usenewpwdcoupon(ids), observer);
    }

    public static void deleteAvailableWork(String work_id,String rq_id, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.deleteAvailableWork(work_id,rq_id), observer);
    }


    public static void getmyinoutlist(String pageNumber, String pageSize, Observer<WalletListBean> observer) {
        setSubscribe(xiuPuApi.getmyinoutlist(pageNumber, pageSize), observer);
    }

    public static void getmyinoutdetail(String src_type, String trade_id, Observer<WalletDetailsBean> observer) {
        setSubscribe(xiuPuApi.getmyinoutdetail(src_type, trade_id), observer);
    }

    public static void addbankcard(String bankcardno, String bank_id, String name, String subbranch_addr, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.addbankcard(bankcardno, bank_id, name, subbranch_addr), observer);
    }

    public static void withdrawapply(String bank_ids, String amount, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.withdrawapply(bank_ids, amount), observer);
    }

    public static void getTerminalStatusList(String terminal_ids, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.getTerminalStatusList(terminal_ids), observer);
    }

    public static void quitfromunion(String union_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.quitfromunion(union_id), observer);
    }

    public static void unionDelete(String union_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.unionDelete(union_id), observer);
    }

    public static void checkShopCntRight(Observer<ShopLimtBean> observer) {
        setSubscribe(xiuPuApi.checkShopCntRight(), observer);
    }

    public static void checkTerminalOwnCntRight(Observer<ShopLimtBean> observer) {
        setSubscribe(xiuPuApi.checkTerminalOwnCntRight(), observer);
    }

    public static void takebackterminalallorder(String terminal_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.takebackterminalallorder(terminal_id), observer);
    }


    public static void getTerminalPlayList(String terminal_ids, String range_id, String info_id, Observer<PlayLogBean> observer) {
        setSubscribe(xiuPuApi.getTerminalPlayList(terminal_ids, range_id, info_id), observer);
    }

    public static void getModuleMaterialList(String module_id, Observer<MaterialBean> observer) {
        setSubscribe(xiuPuApi.getModuleMaterialList(module_id), observer);
    }


    public static void historyrangemarketdetail(String info_id, Observer<PushHistoryDtBean> observer) {
        setSubscribe(xiuPuApi.historyrangemarketdetail(info_id), observer);
    }

    public static void logout(Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.logout(), observer);
    }

    public static void deleteUserFromUnion(String union_id, String delete_user_id, Observer<VCodeBenan> observer) {
        setSubscribe(xiuPuApi.deleteUserFromUnion(union_id, delete_user_id), observer);
    }

    public static void getdictrequirementhintlist(Observer<StarLeveMoney> observer) {
        setSubscribe(xiuPuApi.getdictrequirementhintlist(), observer);
    }

    public static void getmyinoutstatistic(String yearmonths, Observer<WalletListBean> observer) {
        setSubscribe(xiuPuApi.getmyinoutstatistic(yearmonths), observer);
    }

    /*获取音乐列表*/
    public static void getMusiclList(Observer<MusicListBean> observer) {
        setSubscribe(xiuPuApi.getmusiclList(), observer);
    }

    /*vip列表码*/
    public static void getViplList(Observer<VipListBean> observer) {
        setSubscribe(xiuPuApi.getVipList(), observer);
    }

    /*获取播放图片内容*/
    public static void getTerminalPiture(String id,Observer<TerminalPictureBean> observer) {
        setSubscribe(xiuPuApi.getTerminalPicture(id), observer);
    }

    /*获取音乐列表*/
    public static void getMyShoplList(Observer<MyshopListBean> observer) {
        setSubscribe(xiuPuApi.getMyshoplList(), observer);
    }

    /*获取多屏联动包*/
    public static void getLiandongbaolList(Observer<LiandongbaoBean> observer) {
        setSubscribe(xiuPuApi.getLiandongbaoList(), observer);
    }

    /*获取监管账户列表*/
    public static void getAuditList(Observer<AuditListBean> observer) {
        setSubscribe(xiuPuApi.getAuditList(), observer);
    }

    /*获取监管账户删除*/
    public static void deleteAuditUser(String mobile,Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.deleteAuditUser(mobile), observer);
    }

    /*未审核订单的终端列表*/
    public static void getAuditTerminalList(String user_id,Observer<AuditTerminalListBean> observer) {
        setSubscribe(xiuPuApi.getAuditTerminalList(user_id), observer);
    }

    /*未审核订单的作品列表*/
    public static void getAuditWorksList(String user_id,Observer<AuditWorkListBean> observer) {
        setSubscribe(xiuPuApi.getAuditWorksList(user_id), observer);
    }

    /*获取监管者信息*/
    public static void getAuditInfor(Observer<AuditUserInfor> observer) {
        setSubscribe(xiuPuApi.getAuditInfor(), observer);
    }

    /*获取被监管者信息*/
    public static void getBeiAuditInfor(String mobile,Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.getBeiAuditList(mobile), observer);
    }

    /*获取作品组合包列表*/
    public static void getCombinationPackageList(Observer<CombinationListBean> observer) {
        setSubscribe(xiuPuApi.getCombinationPackageList(), observer);
    }

    //删除联动包
    public static void deleteLinkPackage(String link_id, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.deleteLinkPackage(link_id), observer);
    }

    //删除作品组合包
    public static void deleteCombinationPackage(String package_id, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.deleteCombinationPackage(package_id), observer);
    }

    //编辑联动包基本信息
    public static void editLinkPackage(String link_id,String link_name,String cover_url, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.editLinkPackage(link_id,link_name,cover_url), observer);
    }

    //编辑作品组合包基本信息
    public static void editCombinationPackage(String package_id,String package_name,String cover_url,String orientation,String is_time_limit,String start_time,String end_time,String repeat_weekday,String is_ignore_other_work, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.editCombinationPackage(package_id,package_name,cover_url,orientation,is_time_limit,start_time,end_time,repeat_weekday,is_ignore_other_work), observer);
    }

    //清空联动包里面的所有作品
    public static void clearLinkPackage(String link_id, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.clearLinkPackage(link_id), observer);
    }

    //清空作品组合包里面的所有作品
    public static void clearCombinationPackage(String package_id, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.clearCombinationPackage(package_id), observer);
    }

    //删除联动包详情
    public static void deleteLinkDetailPackage(String link_id,String detail_ids, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.deleteLinkDetailPackage(link_id,detail_ids), observer);
    }

    //删除作品组合包详情(里面的一个详情)
    public static void deleteCombinationDetailPackage(String package_id,String detail_ids, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.deleteCombinationDetailPackage(package_id,detail_ids), observer);
    }

    //添加作品到联动包
    public static void addLinkPackage(String link_id,String work_ids,String rq_ids, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.addLinkPackage(link_id,work_ids,rq_ids), observer);
    }

    //添加作品到作品组合包
    public static void addCombinationPackage(String package_id,String work_ids,String rq_ids, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.addCombinationPackage(package_id,work_ids,rq_ids), observer);
    }

    /*获取多屏联动包详情*/
    public static void getLiandongbaoDetail(String link_id,Observer<LinkPackageDetailBean> observer) {
        setSubscribe(xiuPuApi.getLiandongbaoDetail(link_id), observer);
    }

    /*获取多屏联动包详情*/
    public static void getCombinationDetail(String link_id,Observer<CombinationListDetailBean> observer) {
        setSubscribe(xiuPuApi.getCombinationPackageDetail(link_id), observer);
    }

    //新建联动包
    public static void addLinkPackage(String link_name, Observer<AddlinkPackageBean> observer) {
        setSubscribe(xiuPuApi.addLinkPackage(link_name), observer);
    }

    //新增作品组合包
    public static void addCombinationPackage(String package_name, Observer<CombinationAddBean> observer) {
        setSubscribe(xiuPuApi.addCombinationPackage(package_name), observer);
    }

    //组合包排序
    public static void linkPackageSort(String package_id,String work_ids,String rq_ids, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.linkPackageSort(package_id,work_ids,rq_ids), observer);
    }

    //联动包包排序
    public static void linkPackageSort_(String link_id,String work_ids,String rq_ids, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.linkPackageSort_(link_id,work_ids,rq_ids), observer);
    }

    //设置领播作品
    public static void leaderLinkPackage(String link_id,String link_detail_id, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.leaderLinkPackage(link_id,link_detail_id), observer);
    }

    //保存联动关系
    public static void saveLiandongbaoGuanxi(String link_id,String link_snap_name,String union_id,String link_detail_ids,String terminal_ids,String leader_link_detail_id, Observer<SetLiandongbaoGuanxi> observer) {
        setSubscribe(xiuPuApi.saveLiandongbaoGuanxi(link_id,link_snap_name,union_id,link_detail_ids,terminal_ids,leader_link_detail_id), observer);
    }

    //设置联动关系
    public static void setLiandongbaoGuanxi(String link_snap_id,String range_type,String startdate,String enddate,String star_level, Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.setLiandongbaoGuanxi(link_snap_id,range_type,startdate,enddate,star_level), observer);
    }

    /*获取多屏联动投放历史记录*/
    public static void getLiandongbaoHistory(String link_id,Observer<LinkPackageHistoryListBean> observer) {
        setSubscribe(xiuPuApi.getLiandongbaoToufangHistory(link_id), observer);
    }

    /*获取多屏联动投放历史记录----下面的包作品*/
    public static void getLiandongbaoHistory_List(String link_snap_id,Observer<LinkPackageDetailBean> observer) {
        setSubscribe(xiuPuApi.getLiandongbaoHistory_List(link_snap_id), observer);
    }

    //删除联动包投放关系
    public static void deleteLinkToufangGuanxi(String link_snap_id,Observer<EmptyBean> observer) {
        setSubscribe(xiuPuApi.deleteLinkToufangGuangxi(link_snap_id), observer);
    }

    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        Subscription subscribe = observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
