package com.gohoc.xiupuling.constant;

import com.gohoc.xiupuling.bean.PicBean;
import com.qiniu.android.http.ResponseInfo;

import org.json.JSONObject;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/7/20 0020
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public interface OnUploadListener {

    void complete(String key, ResponseInfo info, JSONObject res, PicBean picBean);

    void progress(String key, double percent, PicBean picBean, int potions);

}
