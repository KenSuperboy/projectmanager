package com.gohoc.xiupuling.bean.linkpackage;

import java.util.List;

/**
 * Created by wjj on 2017/9/20.
 * 选择终端列表
 */
public class ChooseTerminalBean {

    /**
     * message : 查询成功
     * data : [{"ids":"e344ee41e5bc4685a99e51b7b9908dca","shop_id":"shop001","shop_name":"绿茶餐厅（海岸城店）","status":1,"term_orientation":0,"terminal_id":"22eade838c654874a1ab7fea71dacea9","terminal_no":"03"},{"ids":"49e4e1140f944801b73e4e339da45c54","shop_id":"shop001","shop_name":"绿茶餐厅（海岸城店）","status":1,"term_orientation":1,"terminal_id":"terminal001","terminal_no":"07"},{"ids":"f8f7efaba1104efd8b57fb1fdc5d15b6","shop_id":"shop001","shop_name":"绿茶餐厅（海岸城店）","status":1,"term_orientation":1,"terminal_id":"9dd2ec3a09c847d9bf970f4845601d63","terminal_no":"11"}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * ids : e344ee41e5bc4685a99e51b7b9908dca
     * shop_id : shop001
     * shop_name : 绿茶餐厅（海岸城店）
     * status : 1
     * term_orientation : 0
     * terminal_id : 22eade838c654874a1ab7fea71dacea9
     * terminal_no : 03
     */

    public List<DataBean> data;

    public static class DataBean {
        public String ids;
        public String shop_id;
        public String shop_name;
        public int status;
        public int term_orientation;
        public String terminal_id;
        public String terminal_no;
    }
}
