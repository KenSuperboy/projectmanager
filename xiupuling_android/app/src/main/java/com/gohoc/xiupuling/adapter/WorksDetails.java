package com.gohoc.xiupuling.adapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 28713 on 2017/3/30.
 */

public class WorksDetails implements Serializable{

    /**
     * message : 查询成功
     * data : {"copyright_price":0,"create_time":"2017-02-26 18:44:05","internal_switch_type":null,"is_recommend":0,"materiallist":[{"is_cover":1,"material_height":1080,"material_order":1,"material_start_x":0,"material_start_y":0,"material_store_id":"d84df36138de4e059b916f88543111c8","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/095831488105820.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"938ef8b8cdf54b82aaf3873a6b754f0a"},{"is_cover":0,"material_height":1080,"material_order":2,"material_start_x":0,"material_start_y":0,"material_store_id":"71e7515d2add41d2a59646ecd8c94d54","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/195171488105820.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"acd4291f1c8041f785f7336f3db78934"},{"is_cover":0,"material_height":1080,"material_order":3,"material_start_x":0,"material_start_y":0,"material_store_id":"7cd54f3667064bfdb16a6df408c83032","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/287851488105820.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"3ed5847411634e619d53c2f0a970b7bc"},{"is_cover":0,"material_height":1080,"material_order":4,"material_start_x":0,"material_start_y":0,"material_store_id":"dc7b97fbd61a40a49a4a67bb090590f2","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/356191488105820.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"7bb50f4c611a47468976a1ecb8a8ca9e"},{"is_cover":0,"material_height":1080,"material_order":5,"material_start_x":0,"material_start_y":0,"material_store_id":"3e0cf40ed83e45cca7ae96e5dbfadacd","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/459451488105838.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"1f30462a2c3f41ccbeb74727e80054c9"}],"next_switch_type":null,"orientation":0,"owner__status":1,"playtime":30,"sale_status":0,"tips_switch_type":null,"use__status":1,"user_id":"15dc1ad0e136471d82057368a737d67c","work_cover_url":null,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_name":"测试画质1-数码相册1","work_seats":1,"work_size":1342126,"work_tag":null,"work_type":2,"xpl_status":null}
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
         * copyright_price : 0.0
         * create_time : 2017-02-26 18:44:05
         * internal_switch_type : null
         * is_recommend : 0
         * materiallist : [{"is_cover":1,"material_height":1080,"material_order":1,"material_start_x":0,"material_start_y":0,"material_store_id":"d84df36138de4e059b916f88543111c8","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/095831488105820.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"938ef8b8cdf54b82aaf3873a6b754f0a"},{"is_cover":0,"material_height":1080,"material_order":2,"material_start_x":0,"material_start_y":0,"material_store_id":"71e7515d2add41d2a59646ecd8c94d54","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/195171488105820.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"acd4291f1c8041f785f7336f3db78934"},{"is_cover":0,"material_height":1080,"material_order":3,"material_start_x":0,"material_start_y":0,"material_store_id":"7cd54f3667064bfdb16a6df408c83032","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/287851488105820.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"3ed5847411634e619d53c2f0a970b7bc"},{"is_cover":0,"material_height":1080,"material_order":4,"material_start_x":0,"material_start_y":0,"material_store_id":"dc7b97fbd61a40a49a4a67bb090590f2","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/356191488105820.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"7bb50f4c611a47468976a1ecb8a8ca9e"},{"is_cover":0,"material_height":1080,"material_order":5,"material_start_x":0,"material_start_y":0,"material_store_id":"3e0cf40ed83e45cca7ae96e5dbfadacd","material_store_url":"thumb-15dc1ad0e136471d82057368a737d67c/requirement/459451488105838.jpg","material_width":1920,"work_id":"54b405e6e8aa41f38112e109df1e702c","work_material_id":"1f30462a2c3f41ccbeb74727e80054c9"}]
         * next_switch_type : null
         * orientation : 0
         * owner__status : 1
         * playtime : 30
         * sale_status : 0
         * tips_switch_type : null
         * use__status : 1
         * user_id : 15dc1ad0e136471d82057368a737d67c
         * work_cover_url : null
         * work_id : 54b405e6e8aa41f38112e109df1e702c
         * work_name : 测试画质1-数码相册1
         * work_seats : 1
         * work_size : 1342126
         * work_tag : null
         * work_type : 2
         * xpl_status : null
         */

        private double copyright_price;
        private String create_time;
        private Object internal_switch_type;
        private int is_recommend;
        private Object next_switch_type;
        private int orientation;
        private int owner__status;
        private int playtime;
        private int sale_status;
        private Object tips_switch_type;
        private int use__status;
        private String user_id;
        private Object work_cover_url;
        private String work_id;
        private String work_name;
        private int work_seats;
        private int work_size;
        private Object work_tag;
        private int work_type;
        private Object xpl_status;
        private List<MateriallistBean> materiallist;

        public double getCopyright_price() {
            return copyright_price;
        }

        public void setCopyright_price(double copyright_price) {
            this.copyright_price = copyright_price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getInternal_switch_type() {
            return internal_switch_type;
        }

        public void setInternal_switch_type(Object internal_switch_type) {
            this.internal_switch_type = internal_switch_type;
        }

        public int getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(int is_recommend) {
            this.is_recommend = is_recommend;
        }

        public Object getNext_switch_type() {
            return next_switch_type;
        }

        public void setNext_switch_type(Object next_switch_type) {
            this.next_switch_type = next_switch_type;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public int getOwner__status() {
            return owner__status;
        }

        public void setOwner__status(int owner__status) {
            this.owner__status = owner__status;
        }

        public int getPlaytime() {
            return playtime;
        }

        public void setPlaytime(int playtime) {
            this.playtime = playtime;
        }

        public int getSale_status() {
            return sale_status;
        }

        public void setSale_status(int sale_status) {
            this.sale_status = sale_status;
        }

        public Object getTips_switch_type() {
            return tips_switch_type;
        }

        public void setTips_switch_type(Object tips_switch_type) {
            this.tips_switch_type = tips_switch_type;
        }

        public int getUse__status() {
            return use__status;
        }

        public void setUse__status(int use__status) {
            this.use__status = use__status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public Object getWork_cover_url() {
            return work_cover_url;
        }

        public void setWork_cover_url(Object work_cover_url) {
            this.work_cover_url = work_cover_url;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public String getWork_name() {
            return work_name;
        }

        public void setWork_name(String work_name) {
            this.work_name = work_name;
        }

        public int getWork_seats() {
            return work_seats;
        }

        public void setWork_seats(int work_seats) {
            this.work_seats = work_seats;
        }

        public int getWork_size() {
            return work_size;
        }

        public void setWork_size(int work_size) {
            this.work_size = work_size;
        }

        public Object getWork_tag() {
            return work_tag;
        }

        public void setWork_tag(Object work_tag) {
            this.work_tag = work_tag;
        }

        public int getWork_type() {
            return work_type;
        }

        public void setWork_type(int work_type) {
            this.work_type = work_type;
        }

        public Object getXpl_status() {
            return xpl_status;
        }

        public void setXpl_status(Object xpl_status) {
            this.xpl_status = xpl_status;
        }

        public List<MateriallistBean> getMateriallist() {
            return materiallist;
        }

        public void setMateriallist(List<MateriallistBean> materiallist) {
            this.materiallist = materiallist;
        }

        public static class MateriallistBean implements Serializable{
            /**
             * is_cover : 1
             * material_height : 1080
             * material_order : 1
             * material_start_x : 0
             * material_start_y : 0
             * material_store_id : d84df36138de4e059b916f88543111c8
             * material_store_url : thumb-15dc1ad0e136471d82057368a737d67c/requirement/095831488105820.jpg
             * material_width : 1920
             * work_id : 54b405e6e8aa41f38112e109df1e702c
             * work_material_id : 938ef8b8cdf54b82aaf3873a6b754f0a
             */

            private int is_cover;
            private int material_height;
            private int material_order;
            private int material_start_x;
            private int material_start_y;
            private String material_store_id;
            private String material_store_url;
            private int material_width;
            private String work_id;
            private String filetype;
            private String work_material_id;

            public String getFiletype() {
                return filetype;
            }

            public void setFiletype(String filetype) {
                this.filetype = filetype;
            }

            public int getIs_cover() {
                return is_cover;
            }

            public void setIs_cover(int is_cover) {
                this.is_cover = is_cover;
            }

            public int getMaterial_height() {
                return material_height;
            }

            public void setMaterial_height(int material_height) {
                this.material_height = material_height;
            }

            public int getMaterial_order() {
                return material_order;
            }

            public void setMaterial_order(int material_order) {
                this.material_order = material_order;
            }

            public int getMaterial_start_x() {
                return material_start_x;
            }

            public void setMaterial_start_x(int material_start_x) {
                this.material_start_x = material_start_x;
            }

            public int getMaterial_start_y() {
                return material_start_y;
            }

            public void setMaterial_start_y(int material_start_y) {
                this.material_start_y = material_start_y;
            }

            public String getMaterial_store_id() {
                return material_store_id;
            }

            public void setMaterial_store_id(String material_store_id) {
                this.material_store_id = material_store_id;
            }

            public String getMaterial_store_url() {
                return material_store_url;
            }

            public void setMaterial_store_url(String material_store_url) {
                this.material_store_url = material_store_url;
            }

            public int getMaterial_width() {
                return material_width;
            }

            public void setMaterial_width(int material_width) {
                this.material_width = material_width;
            }

            public String getWork_id() {
                return work_id;
            }

            public void setWork_id(String work_id) {
                this.work_id = work_id;
            }

            public String getWork_material_id() {
                return work_material_id;
            }

            public void setWork_material_id(String work_material_id) {
                this.work_material_id = work_material_id;
            }
        }
    }
}
