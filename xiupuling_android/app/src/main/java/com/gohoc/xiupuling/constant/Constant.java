package com.gohoc.xiupuling.constant;

/**
 * Created by sherlock-sky on 2016/12/31.
 */

public interface Constant {
    public class BaseConstant {
        public static String BUGLY_KEY = "4ad415cbd2";
        public static int SPLASH_TIME = 1000 * 2;
        public static String WX_APP_ID = "wx32e071c492b4ebf4";
        public static String USER_DOUCMENT = "http://shoplink.gohoc.com/index.php?r=site%2Farticle4&article_id=93";//用户协议
        static public int IMAGE_PICKER = 9527;// 请求图片
        public static boolean isTest=true;//true:测试环境    false：正式环境

        public static int AUDIO_REQUEST=10;//音乐文件的请求
        public static int AUDIO_RESULT=101;//音乐文件的请求
        public static long TITLETIME=300;//标题栏下拉统一时间
    }

    public class UpLoadConstant {
        public static String REQUIREMENT = "requirement";
        public static String PRODUCTION = "production";
        public static String CHANNEL = "channel";
        public static String TAGWALL = "tagwall";
        public static String SHOP = "shop";
        public static String PERSONAL = "personal";
        public static String UNION = "union";
    }

    public class NetConstant {
        //public static String BASE_URL="http://http-echo.jgate.de/"; //测试工具
        //public static String BASE_URL = "https://www.xiupuling.com/";  //正式服务器
        public static String BASE_URL = "http://120.25.57.13:8089/"; //测试服务器
        public static int DEFAULT_CONNECT_TIMEOUT = 10;
        public static int DEFAULT_READ_TIMEOUT = 10;
        public static int DEFAULT_WRITE_TIMEOUT = 10;
        public static String BASE_USER_AGREEMENT = "https://www.xiupuling.com/"; //用户协议
        public static String BASE_USER_RESOURE = "http://7xrbkc.com1.z0.glb.clouddn.com/"; //七牛测试
        //public static String BASE_USER_RESOURE = "https://resource.xiupuling.com/"; //七牛正式
    }

    public class TableConstant {
        public static String Table_TERMINAL = "门店和终端";
        public static String Table_GROUP = "群组";
        public static String Table_ADVERT = "接广告";
        public static String Table_WORKS = "作品";
        public static String Table_PUSH = "投播管理";
    }

    public class FlagConstant {
        public static String MENDIAN = "mendian";
        public static String QUNZU = "qunzu";
        public static String ZUOPING = "zuoping";
        public static String JIEGUANGGAO = "jieguanggao";
    }

}
