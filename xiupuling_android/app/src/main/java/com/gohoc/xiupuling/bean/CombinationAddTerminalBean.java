package com.gohoc.xiupuling.bean;

import java.util.List;

/**
 * Created by wjj on 2017/9/20.
 * 联动群组----选择店铺---添加终端
 */
public class CombinationAddTerminalBean {

    /**
     * message : 查询成功
     * data : [{"bg_audio_flag":null,"bg_audio_type":null,"force_xpl":false,"repeat_weekday":"0","roll_title_flag":null,"roll_titles":null,"schedule_up_down":false,"shop_id":"shop002","take_order":true,"term_orientation":1,"term_show_seats":18,"term_soft_version":"V1.0","terminal_id":"terminal005","terminal_no":"02","terminal_type":2,"up_end_time":null,"up_start_time":null,"user_id":"7c7f36aed0ff4b8ca2ad736258a3c466"}]
     * code : 1
     */

    public String message;
    public int code;
    /**
     * bg_audio_flag : null
     * bg_audio_type : null
     * force_xpl : false
     * repeat_weekday : 0
     * roll_title_flag : null
     * roll_titles : null
     * schedule_up_down : false
     * shop_id : shop002
     * take_order : true
     * term_orientation : 1
     * term_show_seats : 18
     * term_soft_version : V1.0
     * terminal_id : terminal005
     * terminal_no : 02
     * terminal_type : 2
     * up_end_time : null
     * up_start_time : null
     * user_id : 7c7f36aed0ff4b8ca2ad736258a3c466
     */

    public List<DataBean> data;

    public static class DataBean {
        public Object bg_audio_flag;
        public Object bg_audio_type;
        public boolean force_xpl;
        public String repeat_weekday;
        public Object roll_title_flag;
        public Object roll_titles;
        public boolean schedule_up_down;
        public String shop_id;
        public boolean take_order;
        public int term_orientation;
        public int term_show_seats;
        public String term_soft_version;
        public String terminal_id;
        public String terminal_no;
        public int terminal_type;
        public Object up_end_time;
        public Object up_start_time;
        public String user_id;
        public int flag;
    }
}
