package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/2/24.
 */

public class TerminalBean implements Serializable{


    /**
     * message : 查询成功
     * data : {"adv_seats":0,"circle_time":120,"create_time":null,"disk_available":0,"force_xpl":false,"nick_name":"朝天椒","portrait_url":"personal1487922174981f196aae86fadcb78073b1f41e5a6485b.jpg","repeat_weekday":null,"schedule_up_down":false,"shop":{"business_name":"蛋糕甜点饮品店","city":"深圳市","district":"南山区","group_name":"南头","province":"广东省","shop_address":"南新路3022号欢乐颂购物广场1楼F-03号商铺","shop_business_id":100010,"shop_group_id":10588,"shop_id":"shop009","shop_name":"许留山（深圳欢乐颂店）","shop_photos":[{"create_time":"2016-12-24 19:55:33","photo_id":"061a510f10f64e8fb7af21dce62ed54a","photo_url":"许留山（深圳欢乐颂店）004.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"498764cebbc64042b0d1cae37aac50fd","photo_url":"许留山（深圳欢乐颂店）001.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"79a4bac1c13d49ac8714427a5d8a5673","photo_url":"许留山（深圳欢乐颂店）005.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"c86c72669704473dbfca5e099d06d059","photo_url":"许留山（深圳欢乐颂店）002.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"eed20b0adeb5443eaeb88492faadfb70","photo_url":"许留山（深圳欢乐颂店）003.jpg","shop_id":"shop009"}],"shop_region_city":4403,"shop_region_id":440305,"shop_region_province":44,"shop_star_level":5,"shop_telephone":"0755-86646309"},"shop_id":"shop009","take_order":false,"term_orientation":0,"term_show_seats":18,"term_soft_version":"V1.0","term_wifi_verify_type":1,"terminal_id":"terminal012","terminal_module_list":[{"ids":"5d1a195807134dffa1eb18c845b0d450","module_id":"dt005","module_name":"哈根达斯品牌宣传片","module_no":2,"module_seats":4,"module_type":3,"play_order":1,"terminal_id":"terminal012"}],"terminal_no":"01","terminal_type":2,"up_end_time":null,"up_start_time":null,"user_id":"15dc1ad0e136471d82057368a737d67c"}
     * code : 1
     */

    private String message;
    private DataBean data;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean implements Serializable{
        /**
         * adv_seats : 0
         * circle_time : 120
         * create_time : null
         * disk_available : 0
         * force_xpl : false
         * nick_name : 朝天椒
         * portrait_url : personal1487922174981f196aae86fadcb78073b1f41e5a6485b.jpg
         * repeat_weekday : null
         * schedule_up_down : false
         * shop : {"business_name":"蛋糕甜点饮品店","city":"深圳市","district":"南山区","group_name":"南头","province":"广东省","shop_address":"南新路3022号欢乐颂购物广场1楼F-03号商铺","shop_business_id":100010,"shop_group_id":10588,"shop_id":"shop009","shop_name":"许留山（深圳欢乐颂店）","shop_photos":[{"create_time":"2016-12-24 19:55:33","photo_id":"061a510f10f64e8fb7af21dce62ed54a","photo_url":"许留山（深圳欢乐颂店）004.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"498764cebbc64042b0d1cae37aac50fd","photo_url":"许留山（深圳欢乐颂店）001.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"79a4bac1c13d49ac8714427a5d8a5673","photo_url":"许留山（深圳欢乐颂店）005.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"c86c72669704473dbfca5e099d06d059","photo_url":"许留山（深圳欢乐颂店）002.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"eed20b0adeb5443eaeb88492faadfb70","photo_url":"许留山（深圳欢乐颂店）003.jpg","shop_id":"shop009"}],"shop_region_city":4403,"shop_region_id":440305,"shop_region_province":44,"shop_star_level":5,"shop_telephone":"0755-86646309"}
         * shop_id : shop009
         * take_order : false
         * term_orientation : 0
         * term_show_seats : 18
         * term_soft_version : V1.0
         * term_wifi_verify_type : 1
         * terminal_id : terminal012
         * terminal_module_list : [{"ids":"5d1a195807134dffa1eb18c845b0d450","module_id":"dt005","module_name":"哈根达斯品牌宣传片","module_no":2,"module_seats":4,"module_type":3,"play_order":1,"terminal_id":"terminal012"}]
         * terminal_no : 01
         * terminal_type : 2
         * up_end_time : null
         * up_start_time : null
         * user_id : 15dc1ad0e136471d82057368a737d67c
         */

        private int adv_seats;
        private int circle_time;
        private Object create_time;
        private String disk_available;
        private boolean force_xpl;
        private String nick_name;
        private String portrait_url;
        private Object repeat_weekday;
        private boolean schedule_up_down;
        private ShopBean shop;
        private String shop_id;
        private boolean take_order;
        private int term_orientation;
        private int term_show_seats;
        private String term_soft_version;
        private int term_wifi_verify_type;
        private String terminal_id;
        private String terminal_no;
        private int terminal_type;
        private Object up_end_time;
        private Object up_start_time;
        private String user_id;
        private boolean is_vip;
        private List<TerminalModuleListBean> terminal_module_list;

        private boolean bg_audio_flag;
        private String bg_audio_type;
        private String bg_audio_type_name;

        private boolean roll_title_flag;
        private String roll_titles;

        private boolean scancode_flag;
        private boolean weather_flag;

        public boolean is_vip() {
            return is_vip;
        }

        public void setIs_vip(boolean is_vip) {
            this.is_vip = is_vip;
        }

        public String getBg_audio_type() {
            return bg_audio_type;
        }

        public void setBg_audio_type(String bg_audio_type) {
            this.bg_audio_type = bg_audio_type;
        }

        public boolean isBg_audio_flag() {
            return bg_audio_flag;
        }

        public void setBg_audio_flag(boolean bg_audio_flag) {
            this.bg_audio_flag = bg_audio_flag;
        }

        public String getBg_audio_type_name() {
            return bg_audio_type_name;
        }

        public void setBg_audio_type_name(String bg_audio_type_name) {
            this.bg_audio_type_name = bg_audio_type_name;
        }

        public boolean isRoll_title_flag() {
            return roll_title_flag;
        }

        public void setRoll_title_flag(boolean roll_title_flag) {
            this.roll_title_flag = roll_title_flag;
        }

        public String getRoll_titles() {
            return roll_titles;
        }

        public void setRoll_titles(String roll_titles) {
            this.roll_titles = roll_titles;
        }

        public boolean isScancode_flag() {
            return scancode_flag;
        }

        public void setScancode_flag(boolean scancode_flag) {
            this.scancode_flag = scancode_flag;
        }

        public boolean isWeather_flag() {
            return weather_flag;
        }

        public void setWeather_flag(boolean weather_flag) {
            this.weather_flag = weather_flag;
        }

        public int getAdv_seats() {
            return adv_seats;
        }

        public void setAdv_seats(int adv_seats) {
            this.adv_seats = adv_seats;
        }

        public int getCircle_time() {
            return circle_time;
        }

        public void setCircle_time(int circle_time) {
            this.circle_time = circle_time;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getDisk_available() {
            return disk_available;
        }

        public void setDisk_available(String disk_available) {
            this.disk_available = disk_available;
        }

        public boolean isForce_xpl() {
            return force_xpl;
        }

        public void setForce_xpl(boolean force_xpl) {
            this.force_xpl = force_xpl;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPortrait_url() {
            return portrait_url;
        }

        public void setPortrait_url(String portrait_url) {
            this.portrait_url = portrait_url;
        }

        public Object getRepeat_weekday() {
            return repeat_weekday;
        }

        public void setRepeat_weekday(Object repeat_weekday) {
            this.repeat_weekday = repeat_weekday;
        }

        public boolean isSchedule_up_down() {
            return schedule_up_down;
        }

        public void setSchedule_up_down(boolean schedule_up_down) {
            this.schedule_up_down = schedule_up_down;
        }

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public boolean isTake_order() {
            return take_order;
        }

        public void setTake_order(boolean take_order) {
            this.take_order = take_order;
        }

        public int getTerm_orientation() {
            return term_orientation;
        }

        public void setTerm_orientation(int term_orientation) {
            this.term_orientation = term_orientation;
        }

        public int getTerm_show_seats() {
            return term_show_seats;
        }

        public void setTerm_show_seats(int term_show_seats) {
            this.term_show_seats = term_show_seats;
        }

        public String getTerm_soft_version() {
            return term_soft_version;
        }

        public void setTerm_soft_version(String term_soft_version) {
            this.term_soft_version = term_soft_version;
        }

        public int getTerm_wifi_verify_type() {
            return term_wifi_verify_type;
        }

        public void setTerm_wifi_verify_type(int term_wifi_verify_type) {
            this.term_wifi_verify_type = term_wifi_verify_type;
        }

        public String getTerminal_id() {
            return terminal_id;
        }

        public void setTerminal_id(String terminal_id) {
            this.terminal_id = terminal_id;
        }

        public String getTerminal_no() {
            return terminal_no;
        }

        public void setTerminal_no(String terminal_no) {
            this.terminal_no = terminal_no;
        }

        public int getTerminal_type() {
            return terminal_type;
        }

        public void setTerminal_type(int terminal_type) {
            this.terminal_type = terminal_type;
        }

        public Object getUp_end_time() {
            return up_end_time;
        }

        public void setUp_end_time(Object up_end_time) {
            this.up_end_time = up_end_time;
        }

        public Object getUp_start_time() {
            return up_start_time;
        }

        public void setUp_start_time(Object up_start_time) {
            this.up_start_time = up_start_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<TerminalModuleListBean> getTerminal_module_list() {
            return terminal_module_list;
        }

        public void setTerminal_module_list(List<TerminalModuleListBean> terminal_module_list) {
            this.terminal_module_list = terminal_module_list;
        }

        public static class ShopBean implements Serializable{
            /**
             * business_name : 蛋糕甜点饮品店
             * city : 深圳市
             * district : 南山区
             * group_name : 南头
             * province : 广东省
             * shop_address : 南新路3022号欢乐颂购物广场1楼F-03号商铺
             * shop_business_id : 100010
             * shop_group_id : 10588
             * shop_id : shop009
             * shop_name : 许留山（深圳欢乐颂店）
             * shop_photos : [{"create_time":"2016-12-24 19:55:33","photo_id":"061a510f10f64e8fb7af21dce62ed54a","photo_url":"许留山（深圳欢乐颂店）004.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"498764cebbc64042b0d1cae37aac50fd","photo_url":"许留山（深圳欢乐颂店）001.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"79a4bac1c13d49ac8714427a5d8a5673","photo_url":"许留山（深圳欢乐颂店）005.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"c86c72669704473dbfca5e099d06d059","photo_url":"许留山（深圳欢乐颂店）002.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"eed20b0adeb5443eaeb88492faadfb70","photo_url":"许留山（深圳欢乐颂店）003.jpg","shop_id":"shop009"}]
             * shop_region_city : 4403
             * shop_region_id : 440305
             * shop_region_province : 44
             * shop_star_level : 5
             * shop_telephone : 0755-86646309
             */

            private String business_name;
            private String city;
            private String district;
            private String group_name;
            private String province;
            private String shop_address;
            private int shop_business_id;
            private int shop_group_id;
            private String shop_id;
            private String shop_name;
            private int shop_region_city;
            private int shop_region_id;
            private int shop_region_province;
            private int shop_star_level;
            private String shop_telephone;
            private List<ShopPhotosBean> shop_photos;

            public String getBusiness_name() {
                return business_name;
            }

            public void setBusiness_name(String business_name) {
                this.business_name = business_name;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getShop_address() {
                return shop_address;
            }

            public void setShop_address(String shop_address) {
                this.shop_address = shop_address;
            }

            public int getShop_business_id() {
                return shop_business_id;
            }

            public void setShop_business_id(int shop_business_id) {
                this.shop_business_id = shop_business_id;
            }

            public int getShop_group_id() {
                return shop_group_id;
            }

            public void setShop_group_id(int shop_group_id) {
                this.shop_group_id = shop_group_id;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public int getShop_region_city() {
                return shop_region_city;
            }

            public void setShop_region_city(int shop_region_city) {
                this.shop_region_city = shop_region_city;
            }

            public int getShop_region_id() {
                return shop_region_id;
            }

            public void setShop_region_id(int shop_region_id) {
                this.shop_region_id = shop_region_id;
            }

            public int getShop_region_province() {
                return shop_region_province;
            }

            public void setShop_region_province(int shop_region_province) {
                this.shop_region_province = shop_region_province;
            }

            public int getShop_star_level() {
                return shop_star_level;
            }

            public void setShop_star_level(int shop_star_level) {
                this.shop_star_level = shop_star_level;
            }

            public String getShop_telephone() {
                return shop_telephone;
            }

            public void setShop_telephone(String shop_telephone) {
                this.shop_telephone = shop_telephone;
            }

            public List<ShopPhotosBean> getShop_photos() {
                return shop_photos;
            }

            public void setShop_photos(List<ShopPhotosBean> shop_photos) {
                this.shop_photos = shop_photos;
            }

            public static class ShopPhotosBean implements Serializable{
                /**
                 * create_time : 2016-12-24 19:55:33
                 * photo_id : 061a510f10f64e8fb7af21dce62ed54a
                 * photo_url : 许留山（深圳欢乐颂店）004.jpg
                 * shop_id : shop009
                 */

                private String create_time;
                private String photo_id;
                private String photo_url;
                private String shop_id;

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getPhoto_id() {
                    return photo_id;
                }

                public void setPhoto_id(String photo_id) {
                    this.photo_id = photo_id;
                }

                public String getPhoto_url() {
                    return photo_url;
                }

                public void setPhoto_url(String photo_url) {
                    this.photo_url = photo_url;
                }

                public String getShop_id() {
                    return shop_id;
                }

                public void setShop_id(String shop_id) {
                    this.shop_id = shop_id;
                }
            }
        }

        public static class TerminalModuleListBean implements Serializable{
            /**
             * ids : 5d1a195807134dffa1eb18c845b0d450
             * module_id : dt005
             * module_name : 哈根达斯品牌宣传片
             * module_no : 2
             * module_seats : 4
             * module_type : 3
             * play_order : 1
             * terminal_id : terminal012
             */

            private String ids;
            private String module_id;
            private String module_name;
            private int module_no;
            private int module_seats;
            private int module_type;
            private int play_order;
            private String terminal_id;

            public String getIds() {
                return ids;
            }

            public void setIds(String ids) {
                this.ids = ids;
            }

            public String getModule_id() {
                return module_id;
            }

            public void setModule_id(String module_id) {
                this.module_id = module_id;
            }

            public String getModule_name() {
                return module_name;
            }

            public void setModule_name(String module_name) {
                this.module_name = module_name;
            }

            public int getModule_no() {
                return module_no;
            }

            public void setModule_no(int module_no) {
                this.module_no = module_no;
            }

            public int getModule_seats() {
                return module_seats;
            }

            public void setModule_seats(int module_seats) {
                this.module_seats = module_seats;
            }

            public int getModule_type() {
                return module_type;
            }

            public void setModule_type(int module_type) {
                this.module_type = module_type;
            }

            public int getPlay_order() {
                return play_order;
            }

            public void setPlay_order(int play_order) {
                this.play_order = play_order;
            }

            public String getTerminal_id() {
                return terminal_id;
            }

            public void setTerminal_id(String terminal_id) {
                this.terminal_id = terminal_id;
            }
        }
    }
}
