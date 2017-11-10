package com.gohoc.xiupuling.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/1.
 */

public class ShopDetailsBean implements Serializable{


    /**
     * message : 查询成功
     * data : {"business":{"business_expenditure_ratio":null,"business_id":100010,"business_level":2,"business_name":"蛋糕甜点饮品店","business_rank":null,"business_vistiors_ratio":null,"parent_id":1000},"city":{"city_rank":null,"id":4403,"level":true,"name":"深圳市","parentid":44},"create_time":null,"group":{"group_id":10588,"group_name":"南头","group_rank":null,"group_type":1,"region_id":440305},"province":{"city_rank":null,"id":44,"level":true,"name":"广东省","parentid":0},"region":{"city_rank":null,"id":440305,"level":true,"name":"南山区","parentid":4403},"shop_addr_latitude":22.541151,"shop_addr_longitude":113.926808,"shop_address":"南新路3022号欢乐颂购物广场1楼F-03号商铺","shop_brand":null,"shop_business_id":100010,"shop_group_id":10588,"shop_id":"shop009","shop_name":"许留山（深圳欢乐颂店）","shop_per_capita_expenditure":null,"shop_region_city":4403,"shop_region_id":440305,"shop_region_province":44,"shop_star_level":5,"shop_telephone":"0755-86646309","shop_week_visitors":null,"shop_xpl_certification":null,"shopphotos":[{"create_time":"2016-12-24 19:55:33","photo_id":"061a510f10f64e8fb7af21dce62ed54a","photo_url":"许留山（深圳欢乐颂店）004.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"498764cebbc64042b0d1cae37aac50fd","photo_url":"许留山（深圳欢乐颂店）001.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"79a4bac1c13d49ac8714427a5d8a5673","photo_url":"许留山（深圳欢乐颂店）005.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"c86c72669704473dbfca5e099d06d059","photo_url":"许留山（深圳欢乐颂店）002.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"eed20b0adeb5443eaeb88492faadfb70","photo_url":"许留山（深圳欢乐颂店）003.jpg","shop_id":"shop009"}],"user_id":"15dc1ad0e136471d82057368a737d67c","xpl_status":null}
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
         * business : {"business_expenditure_ratio":null,"business_id":100010,"business_level":2,"business_name":"蛋糕甜点饮品店","business_rank":null,"business_vistiors_ratio":null,"parent_id":1000}
         * city : {"city_rank":null,"id":4403,"level":true,"name":"深圳市","parentid":44}
         * create_time : null
         * group : {"group_id":10588,"group_name":"南头","group_rank":null,"group_type":1,"region_id":440305}
         * province : {"city_rank":null,"id":44,"level":true,"name":"广东省","parentid":0}
         * region : {"city_rank":null,"id":440305,"level":true,"name":"南山区","parentid":4403}
         * shop_addr_latitude : 22.541151
         * shop_addr_longitude : 113.926808
         * shop_address : 南新路3022号欢乐颂购物广场1楼F-03号商铺
         * shop_brand : null
         * shop_business_id : 100010
         * shop_group_id : 10588
         * shop_id : shop009
         * shop_name : 许留山（深圳欢乐颂店）
         * shop_per_capita_expenditure : null
         * shop_region_city : 4403
         * shop_region_id : 440305
         * shop_region_province : 44
         * shop_star_level : 5
         * shop_telephone : 0755-86646309
         * shop_week_visitors : null
         * shop_xpl_certification : null
         * shopphotos : [{"create_time":"2016-12-24 19:55:33","photo_id":"061a510f10f64e8fb7af21dce62ed54a","photo_url":"许留山（深圳欢乐颂店）004.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"498764cebbc64042b0d1cae37aac50fd","photo_url":"许留山（深圳欢乐颂店）001.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"79a4bac1c13d49ac8714427a5d8a5673","photo_url":"许留山（深圳欢乐颂店）005.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"c86c72669704473dbfca5e099d06d059","photo_url":"许留山（深圳欢乐颂店）002.jpg","shop_id":"shop009"},{"create_time":"2016-12-24 19:55:33","photo_id":"eed20b0adeb5443eaeb88492faadfb70","photo_url":"许留山（深圳欢乐颂店）003.jpg","shop_id":"shop009"}]
         * user_id : 15dc1ad0e136471d82057368a737d67c
         * xpl_status : null
         */

        private BusinessBean business;
        private CityBean city;
        private Object create_time;
        private GroupBean group;
        private ProvinceBean province;
        private RegionBean region;
        private double shop_addr_latitude;
        private double shop_addr_longitude;
        private String shop_address;
        private Object shop_brand;
        private int shop_business_id;
        private int shop_group_id;
        private String shop_id;
        private String shop_name;
        private Object shop_per_capita_expenditure;
        private int shop_region_city;
        private int shop_region_id;
        private int shop_region_province;
        private int shop_star_level;
        private String shop_telephone;
        private Object shop_week_visitors;
        private Object shop_xpl_certification;
        private String user_id;
        private Object xpl_status;
        private List<ShopphotosBean> shopphotos;

        public BusinessBean getBusiness() {
            return business;
        }

        public void setBusiness(BusinessBean business) {
            this.business = business;
        }

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public GroupBean getGroup() {
            return group;
        }

        public void setGroup(GroupBean group) {
            this.group = group;
        }

        public ProvinceBean getProvince() {
            return province;
        }

        public void setProvince(ProvinceBean province) {
            this.province = province;
        }

        public RegionBean getRegion() {
            return region;
        }

        public void setRegion(RegionBean region) {
            this.region = region;
        }

        public double getShop_addr_latitude() {
            return shop_addr_latitude;
        }

        public void setShop_addr_latitude(double shop_addr_latitude) {
            this.shop_addr_latitude = shop_addr_latitude;
        }

        public double getShop_addr_longitude() {
            return shop_addr_longitude;
        }

        public void setShop_addr_longitude(double shop_addr_longitude) {
            this.shop_addr_longitude = shop_addr_longitude;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public Object getShop_brand() {
            return shop_brand;
        }

        public void setShop_brand(Object shop_brand) {
            this.shop_brand = shop_brand;
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

        public Object getShop_per_capita_expenditure() {
            return shop_per_capita_expenditure;
        }

        public void setShop_per_capita_expenditure(Object shop_per_capita_expenditure) {
            this.shop_per_capita_expenditure = shop_per_capita_expenditure;
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

        public Object getShop_week_visitors() {
            return shop_week_visitors;
        }

        public void setShop_week_visitors(Object shop_week_visitors) {
            this.shop_week_visitors = shop_week_visitors;
        }

        public Object getShop_xpl_certification() {
            return shop_xpl_certification;
        }

        public void setShop_xpl_certification(Object shop_xpl_certification) {
            this.shop_xpl_certification = shop_xpl_certification;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public Object getXpl_status() {
            return xpl_status;
        }

        public void setXpl_status(Object xpl_status) {
            this.xpl_status = xpl_status;
        }

        public List<ShopphotosBean> getShopphotos() {
            return shopphotos;
        }

        public void setShopphotos(List<ShopphotosBean> shopphotos) {
            this.shopphotos = shopphotos;
        }

        public static class BusinessBean implements Serializable{
            /**
             * business_expenditure_ratio : null
             * business_id : 100010
             * business_level : 2
             * business_name : 蛋糕甜点饮品店
             * business_rank : null
             * business_vistiors_ratio : null
             * parent_id : 1000
             */

            private Object business_expenditure_ratio;
            private int business_id;
            private int business_level;
            private String business_name;
            private Object business_rank;
            private Object business_vistiors_ratio;
            private int parent_id;

            public Object getBusiness_expenditure_ratio() {
                return business_expenditure_ratio;
            }

            public void setBusiness_expenditure_ratio(Object business_expenditure_ratio) {
                this.business_expenditure_ratio = business_expenditure_ratio;
            }

            public int getBusiness_id() {
                return business_id;
            }

            public void setBusiness_id(int business_id) {
                this.business_id = business_id;
            }

            public int getBusiness_level() {
                return business_level;
            }

            public void setBusiness_level(int business_level) {
                this.business_level = business_level;
            }

            public String getBusiness_name() {
                return business_name;
            }

            public void setBusiness_name(String business_name) {
                this.business_name = business_name;
            }

            public Object getBusiness_rank() {
                return business_rank;
            }

            public void setBusiness_rank(Object business_rank) {
                this.business_rank = business_rank;
            }

            public Object getBusiness_vistiors_ratio() {
                return business_vistiors_ratio;
            }

            public void setBusiness_vistiors_ratio(Object business_vistiors_ratio) {
                this.business_vistiors_ratio = business_vistiors_ratio;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }
        }

        public static class CityBean implements Serializable{
            /**
             * city_rank : null
             * id : 4403
             * level : true
             * name : 深圳市
             * parentid : 44
             */

            private Object city_rank;
            private int id;
            private boolean level;
            private String name;
            private int parentid;

            public Object getCity_rank() {
                return city_rank;
            }

            public void setCity_rank(Object city_rank) {
                this.city_rank = city_rank;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isLevel() {
                return level;
            }

            public void setLevel(boolean level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }
        }

        public static class GroupBean implements Serializable{
            /**
             * group_id : 10588
             * group_name : 南头
             * group_rank : null
             * group_type : 1
             * region_id : 440305
             */

            private int group_id;
            private String group_name;
            private Object group_rank;
            private int group_type;
            private int region_id;

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public Object getGroup_rank() {
                return group_rank;
            }

            public void setGroup_rank(Object group_rank) {
                this.group_rank = group_rank;
            }

            public int getGroup_type() {
                return group_type;
            }

            public void setGroup_type(int group_type) {
                this.group_type = group_type;
            }

            public int getRegion_id() {
                return region_id;
            }

            public void setRegion_id(int region_id) {
                this.region_id = region_id;
            }
        }

        public static class ProvinceBean implements Serializable{
            /**
             * city_rank : null
             * id : 44
             * level : true
             * name : 广东省
             * parentid : 0
             */

            private Object city_rank;
            private int id;
            private boolean level;
            private String name;
            private int parentid;

            public Object getCity_rank() {
                return city_rank;
            }

            public void setCity_rank(Object city_rank) {
                this.city_rank = city_rank;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isLevel() {
                return level;
            }

            public void setLevel(boolean level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }
        }

        public static class RegionBean implements Serializable{
            /**
             * city_rank : null
             * id : 440305
             * level : true
             * name : 南山区
             * parentid : 4403
             */

            private Object city_rank;
            private int id;
            private boolean level;
            private String name;
            private int parentid;

            public Object getCity_rank() {
                return city_rank;
            }

            public void setCity_rank(Object city_rank) {
                this.city_rank = city_rank;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isLevel() {
                return level;
            }

            public void setLevel(boolean level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }
        }

        public static class ShopphotosBean implements Serializable{
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
}
